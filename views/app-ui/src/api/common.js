import request from '@/utils/request';

const baseUrl = '/study/common';

export default {
    //评论
    common(data) {
        return request({
            url: `${baseUrl}`,
            method: 'post',
            data
        })
    },
    //当前文章的评论
    articleCommon(articleId) {
        return request({
            url: `${baseUrl}/article/${articleId}`,
            method: 'get'
        })
    },
    //删除评论
    deleteArticleCommon(commonId) {
        return request({
            url: `${baseUrl}/${commonId}`,
            method: 'delete'
        })
    },
}
