import request from '../utils/request'

export default {
    //加载笔记列表
    loadArticles(params: any) {
        return request({
            url: '/article/backstage',
            params
        })
    },
    //审核
    auditArticle(articleId: any) {
        return request({
            url: 'article/audit/' + articleId,
            method: 'get'
        })
    },
    //根据id获取文章
    getArticleById(id: any) {
        return request({
            url: `article/${id}`,
            method: 'get'
        })
    },
}

