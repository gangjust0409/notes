package com.just.study.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.just.study.constant.FileConstant;
import com.just.study.constant.UserConstant;
import com.just.study.entity.Article;
import com.just.study.entity.Common;
import com.just.study.entity.User;
import com.just.study.mapper.CommonMapper;
import com.just.study.service.IArticleService;
import com.just.study.service.ICommonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.just.study.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author abc
 * @since 2023-02-13
 */
@Service
public class CommonServiceImpl extends ServiceImpl<CommonMapper, Common> implements ICommonService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IArticleService articleService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public List<Common> list(Long articleId, String token) {
        if (token == null) return null;
        List<Common> list = this.list(new QueryWrapper<Common>().eq("article_id", articleId));
        articleService.getById(articleId);
        List<Common> collect = list.stream().filter(common -> common.getParentId() == 0).map(common -> {
            return getCommon(list, common, token);
        }).collect(Collectors.toList());
        return collect;
    }

    private Common getCommon(List<Common> list, Common common, String token) {
        Article article = articleService.getById(common.getArticleId());
        User user = this.getUser(token);
        common.setChildren(getChildrenCommons(list, common.getCommonId(), token));
        common.setUser(getUserServiceById(common, token));
        common.setAuthor(common.getUserId().equals(article.getUserId()));
        common.setDelete(common.getUserId().equals(user.getUserId()));
        return common;
    }

    private User getUserServiceById(Common common, String token) {
        User user = userService.getById(common.getUserId());
        if (user.getPic() != null) {
            user.setPic(FileConstant.SERVER_FILE_UPLOAD_PATH+user.getPic());
        }
        //设置是否是当前用户
        //User u = LoginInterceptor.currentUser.get();
        User u = getUser(token);
        user.setAttention(u.getUserId().equals(user.getUserId()));
        return user;
    }

    @Override
    public void common(Common common, String token) {
        //User user = LoginInterceptor.currentUser.get();
        User user = this.getUser(token);
        common.setCommonDate(LocalDateTime.now());
        common.setStatus(1);
        common.setUserId(user.getUserId());
        this.save(common);
    }

    @Override
    public void deleteCommon(Long commonId) {
        //查询当前评论是否是子评论，如果是父评论，子评论也删除
        Common common = this.getById(commonId);
        List<Common> children = this.list(new QueryWrapper<Common>().eq("parent_id", common.getCommonId()));
        if (children != null || children.size() >= 0) {
            List<Long> ids = children.stream().map(Common::getCommonId).collect(Collectors.toList());
            this.removeBatchByIds(ids);
        }
        this.removeById(commonId);
    }

    /**
     * 获取子评论
     * @param list
     * @param commonId
     * @return
     */
    private List<Common> getChildrenCommons(List<Common> list, Long commonId, String token) {
        List<Common> collect = list.stream().filter(common -> common.getParentId().equals(commonId))
                .map(common -> {
                    return getCommon(list, common, token);
                }).collect(Collectors.toList());
        return collect;
    }


    /**
     * 获取当前用户
     * @param token
     * @return
     */
    private User getUser(String token) {
        String val = redisTemplate.opsForValue().get(UserConstant.CURRENT_LOGIN_USER + token);
        User user = JSON.parseObject(val, User.class);
        return user;
    }
}
