package com.just.study.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.just.study.components.FileComponent;
import com.just.study.constant.FileConstant;
import com.just.study.constant.RedisConstant;
import com.just.study.constant.UserConstant;
import com.just.study.entity.FileConvertAdd;
import com.just.study.entity.User;
import com.just.study.interceptor.LoginInterceptor;
import com.just.study.mapper.FileConvertAddMapper;
import com.just.study.service.IFileConvertAddService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.just.study.service.IUserService;
import com.just.study.utils.FileUtil;
import com.just.study.utils.Log;
import com.just.study.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author abc
 * @since 2023-02-09
 */
@Service
public class FileConvertAddServiceImpl extends ServiceImpl<FileConvertAddMapper, FileConvertAdd> implements IFileConvertAddService {

    private Log log = Log.getLogger(this.getClass());

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IUserService userService;

    @Autowired
    private FileComponent fileComponent;

    private String path = "/http/nginx/html/static/note/upload";

    //返回path
    public String rootPath(int type) {
       try {
           //本地测试用
           if (type == 1) {
               return ResourceUtils.getURL("classpath:").getPath() + "static/upload";
           } else if(type == 2) {
               //linux
               return fileComponent.url;
           } else {
               return null;
           }
       } catch (FileNotFoundException e) {
           return null;
       }
    }

    //上传用户头像
    @Override
    public int handlerUploadArticleImg(MultipartFile multipartFile, String token) {
        int res = 0;
        try {
            if(multipartFile.isEmpty()) return 0;
            //验证格式
            String originalFilename = multipartFile.getOriginalFilename(); //源文件名字
            String prefix = FileUtil.getPrefix(originalFilename);
            if (prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("jpeg")
                    || prefix.equalsIgnoreCase("png") || prefix.equalsIgnoreCase("webp")) {
                String uuid = StringUtils.randomUUID();
                File dirname = new File(path + "/");
                if(!dirname.exists()){
                    dirname.mkdir();
                }
                String newName = uuid + "." + prefix;
                //保存文件
                multipartFile.transferTo(new File(dirname.getAbsolutePath(), newName));
                //User user = LoginInterceptor.currentUser.get();
                User user = this.getUser(token);
                User u = userService.getById(user.getUserId());
                u.setPic(newName);
                userService.updateById(u);
                redisTemplate.opsForValue().set(UserConstant.CURRENT_LOGIN_USER + token, JSON.toJSONString(u));
                res = 1;
            } else {
                res = 2;
            }
        } catch (Exception e) {
            res = 9;
        }
        return res;
    }

    @Override
    public int uploadMultipartFiles(MultipartFile[] multipartFiles, String token) {
        for (MultipartFile multipartFile : multipartFiles) {
            return saveFile(multipartFile, token);
        }
        return 0;
    }

    //展示作为笔记中的图片
    @Override
    public Set<String> uploadPreview(String token) {
        //User user = LoginInterceptor.currentUser.get();
        User user = this.getUser(token);
        BoundHashOperations<String, String, String> ops = getOps(user);
        Set<String> keys = ops.keys();
        Set<String> urls = keys.stream().map(key -> {
            String val = ops.get(key);
            return FileConstant.SERVER_FILE_UPLOAD_PATH + "temp/" + val;
        }).collect(Collectors.toSet());
        return urls;
    }

    //删除展示作为笔记中的图片
    @Override
    public void deleteFile(String fileName, String token) {
        try {
            File file = new File(path + "/temp/", fileName);
            User user = this.getUser(token);
            BoundHashOperations<String, String, String> ops = this.getOps(user);
            String[] files = fileName.split("\\.");
            file.delete();
            Long delete = ops.delete(files[0]);
            log.info("delete" + delete + "\t" + files[0]);
        } catch (Exception e) {

        }
    }

    //后台：笔记封面上传
    @Override
    public void uploadArticleImage(MultipartFile file, String token) {
        try {
            if (!file.isEmpty()) {
                //获取上传的源文件名称
                String originalFilename = file.getOriginalFilename();
                //获取源文件名称后缀
                String prefix = FileUtil.getPrefix(originalFilename);
                //生成一个新的文件名称
                String uuid = StringUtils.randomUUID();
                String newName = uuid + "." + prefix;
                //将名称暂时保存进数据库中
                FileConvertAdd fileConvertAdd = new FileConvertAdd();
                fileConvertAdd.setFileUnique(uuid);
                fileConvertAdd.setFileName(newName);
                this.save(fileConvertAdd);
                //获取当前用户
                User user = this.getUser(token);
                redisTemplate.opsForValue().setIfAbsent(FileConstant.SAVE_FILE_KEY + user.getUserId(), uuid, 2L, TimeUnit.HOURS);
                file.transferTo(new File(path, newName));
            }
        } catch (Exception e) {

        }
    }

    /**
     * 保存文件
     *
     * @param multipartFile
     * @param token
     */
    private int saveFile(MultipartFile multipartFile, String token) {
        try {
            if (!multipartFile.isEmpty()) {
                String originalFilename = multipartFile.getOriginalFilename();
                String prefix = FileUtil.getPrefix(originalFilename);
                String uuid = StringUtils.randomUUID();
                File dirname = new File(path + "/temp/");
                if (!dirname.exists()) {
                    dirname.mkdirs();
                }
                String newFile = uuid + "." + prefix;
                File file = new File(dirname.getAbsolutePath(), newFile);
                multipartFile.transferTo(file);
                //保存进 redis
                //User user = LoginInterceptor.currentUser.get();
                User user = this.getUser(token);
                BoundHashOperations<String, String, String> ops = getOps(user);
                ops.put(uuid, newFile);
                return 1;
            }
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }

    private BoundHashOperations<String, String, String> getOps(User user) {
        return redisTemplate.boundHashOps(RedisConstant.EDIT_FILE_KEY + user.getUserId());
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
