package com.just.study.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.just.study.constant.FileConstant;
import com.just.study.constant.RedisConstant;
import com.just.study.constant.UserConstant;
import com.just.study.entity.*;
import com.just.study.interceptor.LoginInterceptor;
import com.just.study.mapper.ArticleMapper;
import com.just.study.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.just.study.to.ConvertArticleSort;
import com.just.study.utils.Log;
import com.just.study.utils.StringUtils;
import com.just.study.vo.ArticleDetailVo;
import com.just.study.vo.ArticleParamVo;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author abc
 * @since 2023-02-08
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    private Log log = Log.getLogger(this.getClass());

    @Autowired
    private IFileConvertAddService fileConvertAddService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IArticleLinkService articleLinkService;

    @Autowired
    private ICommonService commonService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IUserCorrelationService userCorrelationService;

    //发布文章
    @Override
    public void addArticle(Article article, String token) {
        User user = LoginInterceptor.currentUser.get();
        System.out.println(user + "添加文章");
        article.setCatalogId(2L);
        article.setCreatedDate(LocalDate.now());
        //获取当前的登录用户
        User currentUser = this.getUser(token);
        article.setUserId(currentUser.getUserId());
        article.setType(0);
        this.save(article);
    }

    /**
     * 获取当前用户
     *
     * @param token
     * @return
     */
    private User getUser(String token) {
        String val = redisTemplate.opsForValue().get(UserConstant.CURRENT_LOGIN_USER + token);
        User user = JSON.parseObject(val, User.class);
        return user;
    }

    @Override
    public Page<Article> userIdGetArticleList(Integer type, Integer num, Integer size, String token) {
        //User user = LoginInterceptor.currentUser.get();

        //获取当前的登录用户
        User user = this.getUser(token);
        //文章 page
        Page<Article> page = Page.of(num, size);
        //类型是 待审核、我的作品展示
        if (type == 0 || type == 1) {
            Page<Article> articlePage = this.baseMapper.selectPage(page, new QueryWrapper<Article>()
                    .eq("user_id", user.getUserId()).eq("type", type).orderByDesc("created_date"));
            List<Article> articles = this.getArticles(articlePage.getRecords());
            articlePage.setRecords(articles);
            return articlePage;
            //其他类型，进入关联表中查询
        } else {
            Page<ArticleLink> articleLinkPage = Page.of(num, size);
            QueryWrapper<ArticleLink> articleLinkQueryWrapper = new QueryWrapper<ArticleLink>().eq("user_id", user.getUserId()).eq("type", type);
            Page<ArticleLink> linkPage = articleLinkService.getBaseMapper().selectPage(articleLinkPage, articleLinkQueryWrapper);
            List<Long> ids = linkPage.getRecords().stream().map(ArticleLink::getArticleId).collect(Collectors.toList());
            if (ids.size() > 0) {
                List<Article> articles = this.listByIds(ids);
                List<Article> articleList = this.getArticles(articles);
                page.setTotal(articleLinkPage.getTotal());
                page.setRecords(articleList);
                page.setPages(articleLinkPage.getPages());
                page.setSize(articleLinkPage.getSize());
                page.setCurrent(articleLinkPage.getCurrent());
            }
            return page;
        }
    }

    //详情页展示
    @Override
    public ArticleDetailVo articleGetById(Long id, String token) {
        ArticleDetailVo articleDetailVo = new ArticleDetailVo();
        //User user1 = LoginInterceptor.currentUser.get();
        User user1 = this.getUser(token);
        //根据id得到当前文章详细信息
        Article article = this.getById(id);
        //设置文章id和标题
        articleDetailVo.setArticleId(article.getArticleId()).setTitle(article.getTitle())
                .setReleaseDate(article.getCreatedDate()).setContent(article.getContent());
        //查询当前用户id
        User user = userService.getById(article.getUserId());
        articleDetailVo.setAuthor(user.getUserName())
                .setDelete(user1 == null ? false : user.getUserId().equals(user1.getUserId()));
        //查询登录的用户是否已经点赞过或者收藏、喜欢过这个文章
        if (user1 != null) {
            List<ArticleLink> articleLinks = articleLinkService.list();
            if (articleLinks.size() > 0) {
                for (ArticleLink articleLink : articleLinks) {
                    if (user1.getUserId().equals(articleLink.getUserId()) && articleLink.getArticleId().equals(article.getArticleId())) {
                        //收藏
                        if (articleLink.getType() == 3) {
                            articleDetailVo.setCollect(true);
                            //喜欢
                        } else if (articleLink.getType() == 4) {
                            articleDetailVo.setLove(true);
                            //点赞
                        } else if (articleLink.getType() == 5) {
                            articleDetailVo.setGiveLike(true);
                        }
                    }
                }
            }
        }
        //评论
//        List<Common> commons = commonService.treeList();
//        articleDetailVo.setCommons(commons);
        //作者的其他文章
        Page<Article> page = Page.of(1, 10);
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("user_id", article.getUserId())
                .notIn("article_id", article.getArticleId())
                .eq("type", 1).orderByDesc("created_date");

        Page<Article> articlePage = this.baseMapper.selectPage(page, articleQueryWrapper);
        List<Article> articles = articlePage.getRecords().stream().map(article1 -> {
            //设置封面
            article1.setImg(article1.getImg() == null ? null : FileConstant.SERVER_FILE_UPLOAD_PATH + article1.getImg());
            return article1;
        }).collect(Collectors.toList());
        articleDetailVo.setOtherArticles(articles);

        return articleDetailVo;
    }

    @Override
    public void countRecommend(Long articleId) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String oldKey = RedisConstant.RECOMMEND + articleId;
        Set<String> keys = redisTemplate.keys(RedisConstant.RECOMMEND + "*");
        Integer count = 1;
        for (String key : keys) {
            String val = ops.get(oldKey);
            if (val != null && oldKey.equals(key)) {
                count += Integer.parseInt(val);
            }
        }
        ops.set(RedisConstant.RECOMMEND + articleId, count.toString());
    }

    @Override
    public Page<Article> recommendList(Integer pageNum, Integer pageSize) {
        //收集数据
        List<Article> articleList = new ArrayList<>();
        Page<Article> page = Page.of(pageNum, pageSize);
        //查询审核成功后的笔记
        Page<Article> articlePage = baseMapper.selectPage(page, new QueryWrapper<Article>()
                .eq("type", 1).orderByDesc("created_date"));
        //封装成集合并排序
        List<Article> articles = articlePage.getRecords().stream().map(article -> {
                    ConvertArticleSort convertArticleSort = new ConvertArticleSort();
                    String val = redisTemplate.opsForValue().get(RedisConstant.RECOMMEND + article.getArticleId());
                    if (val == null) {
                        return null;
                    }
                    convertArticleSort.setArticleId(article.getArticleId());
                    convertArticleSort.setValue(Integer.parseInt(val));
                    return convertArticleSort;
                }).filter(convertArticleSort -> convertArticleSort != null)
                .sorted(Comparator.comparing(ConvertArticleSort::getValue).reversed())
                .collect(Collectors.toList()).stream().filter(convertArticleSort -> convertArticleSort != null)
                .map(convertArticleSort -> {
                    Article a = this.getById(convertArticleSort.getArticleId());
                    return a;
                }).collect(Collectors.toList());
        //先有热度先收集
        articleList.addAll(articles);
        //随机前几个笔记
        List<Article> collect = articlePage.getRecords().stream().map(a -> {
            for (Article article : articleList) {
                if (a.getArticleId().equals(article.getArticleId())) {
                    return null;
                }
            }
            return a;
        }).filter(article -> article != null).collect(Collectors.toList());

        articleList.addAll(collect);
        //查询当前的阅读量、点赞、评论
        List<Article> collect1 = getArticles(articleList);

        articlePage.setRecords(collect1);

        return articlePage;
    }

    /**
     * 给列表赋值上评论、点赞、阅读量
     *
     * @param articleList
     * @return
     */
    private List<Article> getArticles(List<Article> articleList) {
        List<Article> collect1 = articleList.stream().map(article -> {
            if (article == null) return null;
            long count = articleLinkService.count(new QueryWrapper<ArticleLink>().eq("article_id", article.getArticleId())
                    .eq("type", 5));
            long commonCount = commonService.count(new QueryWrapper<Common>().eq("article_id", article.getArticleId()));
            article.setLove(count);
            article.setCommon(commonCount);
            //设置阅读量
            Set<String> keys = redisTemplate.keys("*");
            for (String key : keys) {
                if (key.equals(RedisConstant.RECOMMEND + article.getArticleId())) {
                    article.setReading(Long.parseLong(redisTemplate.opsForValue().get(key)));
                }
            }
            //设置封面
            article.setImg(article.getImg() == null ? null : FileConstant.SERVER_FILE_UPLOAD_PATH + article.getImg());

            return article;
        }).collect(Collectors.toList());
        return collect1;
    }

    @Override
    public Page<Article> searchArticles(String keyword, Integer pageNum, Integer pageSize) {
        //转换异常则是 id
        Page<Article> page = Page.of(pageNum, pageSize);
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        try {
            articleQueryWrapper.eq("article_id", Integer.parseInt(keyword));
        } catch (NumberFormatException e) {
            articleQueryWrapper.like("title", keyword);
        }
        articleQueryWrapper.eq("type", 1);
        Page<Article> articlePage = baseMapper.selectPage(page, articleQueryWrapper);

        List<Article> collect = articlePage.getRecords().stream().map(article -> {
            article.setTitle(article.getTitle().replace(keyword, "<b style='color:red;'>" + keyword + "</b>"));
            return article;
        }).collect(Collectors.toList());

        List<Article> articles = this.getArticles(collect);

        articlePage.setRecords(articles);

        return articlePage;
    }

    @Override
    public Page<Article> otherUserArticles(Long userId) {
        Page<Article> page = Page.of(1, 5);
        Page<Article> articlePage = baseMapper.selectPage(page, new QueryWrapper<Article>().eq("user_id", userId)
                .eq("type", 1));
        List<Article> articles = this.getArticles(articlePage.getRecords());
        articlePage.setRecords(articles);
        return articlePage;
    }

    @Transactional
    @Override
    public void del(Long articleId) {
        //刪除评论等信息
        List<Long> ids = commonService.list(new QueryWrapper<Common>().eq("article_id", articleId)).stream().map(Common::getCommonId).collect(Collectors.toList());
        commonService.removeBatchByIds(ids);
        //关联也要删除
        List<Long> alids = articleLinkService.list(new QueryWrapper<ArticleLink>().eq("article_id", articleId)).stream().map(ArticleLink::getAlId).collect(Collectors.toList());
        articleLinkService.removeBatchByIds(alids);
        this.removeById(articleId);
    }

    @Override
    public Page<Article> attentionList(Integer pageNum, Integer pageSize, String token) {
        Page<Article> page = Page.of(pageNum, pageSize);
        //查询当前用户关注的用户笔记列表
        //User user = LoginInterceptor.currentUser.get();
        User user = this.getUser(token);
        if (user == null) return null;
        List<UserCorrelation> users = userCorrelationService.list(new QueryWrapper<UserCorrelation>().eq("current_user_id", user.getUserId()));
        List<Long> attentionIds = users.stream().map(UserCorrelation::getAttentionUserId).collect(Collectors.toList());
        if (attentionIds == null || attentionIds.size() == 0) {
            return null;
        }
        Page<Article> articlePage = baseMapper.selectPage(page, new QueryWrapper<Article>()
                .in("user_id", attentionIds).eq("type", 1).orderByDesc("created_date"));

        List<Article> articles = this.getArticles(articlePage.getRecords());
        articlePage.setRecords(articles);

        return articlePage;
    }

    @Override
    public Page<Article> backstrageArticles(ArticleParamVo paramVo) {
        Page<Article> page = Page.of(paramVo.getPageNum(), paramVo.getPageSize());
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        //条件
        if (StringUtils.isNotNull(paramVo.getTitle())) {
            articleQueryWrapper.like("title", paramVo.getTitle());
        }
        if (paramVo.getType() != null) {
            articleQueryWrapper.eq("type", paramVo.getType());
        }
        if (paramVo.getUserId() != null) {
            articleQueryWrapper.eq("user_id", paramVo.getUserId());
        }
        Page<Article> articlePage = baseMapper.selectPage(page, articleQueryWrapper);

        return articlePage;
    }

    /**
     * 因为上传文件和添加同时执行，可能添加比上传文件块，那么给添加睡眠3秒
     * @param articleId
     * @param token
     */
    @Transactional
    @Override
    public void auditArticle(Long articleId, String token) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        User user = this.getUser(token);
        String key = FileConstant.SAVE_FILE_KEY + user.getUserId();
        //查询当前文章的信息
        Article article = this.getById(articleId);
        CompletableFuture<FileConvertAdd> unique = CompletableFuture.supplyAsync(() -> {
            //修改审核成功
            article.setType(1);
            //拿到 redis 中存入的 唯一标识 uuid
            //睡眠三分钟
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //拿到 redis 中存入的 唯一标识 uuid
            String uuid = redisTemplate.opsForValue().get(key);
            //在到数据库中查询
            FileConvertAdd file_unique = fileConvertAddService.getOne(new QueryWrapper<FileConvertAdd>()
                    .eq("file_unique", uuid));
            log.info("convert file " + file_unique);
            //请求图片
            article.setImg(file_unique.getFileName());
            return file_unique;
        }, executorService);
        CompletableFuture<Void> updateArticleFuture = unique.thenAccept(a -> {
            //修改文章信息
            this.updateById(article);
            // 删除 redis 中的 key
            redisTemplate.delete(key);
            //删除数据库当前项，保证数据库中只有一项数据
            fileConvertAddService.remove(new QueryWrapper<FileConvertAdd>()
                    .eq("file_unique", a.getFileUnique()));
        });
        try {
            CompletableFuture.anyOf(unique, updateArticleFuture).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private BoundHashOperations<String, String, String> getOps(User user) {
        return redisTemplate.boundHashOps(RedisConstant.EDIT_FILE_KEY + user.getUserId());
    }


//    private List<Common> settingUserInfo(List<Common> commons) {
//        return commons.stream().map(common -> {
//            if (common.getChildren() != null) {
//                this.settingUserInfo(common.getChildren());
//            }
//            common.setUser(this.commonGetUserInfo(common.getUserId()));
//            return common;
//        }).collect(Collectors.toList());
//    }

}
