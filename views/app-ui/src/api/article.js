import request from '@/utils/request'

const baseUrl = '/study/article';

export default {
    //添加
    saveArticle(data) {
        return request({
            url: baseUrl,
            method: 'post',
            data
        })
    },
    //根据类型和用户id获取文章
    getArticle(type, params) {
        return request({
            url: `${baseUrl}/get/${type}`,
            method: 'get',
            params
        })
    },
    //根据id获取文章
    getArticleById(id) {
        return request({
            url: `${baseUrl}/${id}`,
            method: 'get'
        })
    },
    //点赞、收藏、喜欢
    settingArticle(data) {
        return request({
            url: "/study/article-link/setting",
            method: 'post',
            data
        })
    },
    //删除关联
    settingDelete(text) {
        return request({
            url: `/study/article-link/${text}`,
            method: 'delete'
        })
    },
    //每次访问，热度加加
    recommendArticle(articleId) {
        return request({
            url: `${baseUrl}/recommend/${articleId}`,
            method: 'get'
        })
    },
    //首页推荐
    recommendList(params) {
        return request({
            url: `${baseUrl}/recommend/list`,
            method: 'get',
            params
        })
    },
    //搜索
    searchArticleList(params) {
        return request({
            url: `${baseUrl}/search`,
            method: 'get',
            params
        })
    },
    //查看别人笔记
    otherSearchArticles(params) {
        return request({
            url: `${baseUrl}/other/user/article`,
            method: 'get',
            params
        })
    },
    //删除笔记
    deleteArticle(articleId) {
        return request({
            url: `${baseUrl}/${articleId}`,
            method: 'delete'
        })
    },
    //关注的笔记列表
    attentionList(params) {
        return request({
            url: `${baseUrl}/attention/list`,
            method: 'get',
            params
        })
    },
    //预览图片
    previewImages() {
        return request({
            url: '/study/add-convert-file/upload/pre',
            method: 'get'
        })
    },
    //删除预览图片
    deletePreviewImage(fileName) {
        return request({
            url: `/study/add-convert-file/${fileName}`,
            method: 'delete'
        });
    },
}