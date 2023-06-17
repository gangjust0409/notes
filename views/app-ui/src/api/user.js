import request from '@/utils/request'
const baseUrl = '/study/user';
export default {
    //登录
    login(data) {
        return request({
            url: `${baseUrl}/login`,
            method: 'post',
            data
        })
    },
    //用户信息
    userInfo(token) {
        return request({
            url: `${baseUrl}/user/info/${token}`,
            method: 'get'
        })
    },
    //用户搜索
    searchUsers(params) {
        return request({
            url: `${baseUrl}/search`,
            method: 'get',
            params
        })
    },
    //根据id查询用户信息
    getUser(userId) {
        return request({
            url: `${baseUrl}/other/user/${userId}`,
            method: 'get'
        })
    },
    //关注
    attention(data) {
        return request({
            url: `/study/user-correlation`,
            method: 'post',
            data
        })
    },
    //取消关注
    cancelAttention(userId) {
        return request({
            url: `/study/user-correlation/${userId}`,
            method: 'delete'
        })
    },
    //注册
    register(data) {
        return request({
            url: `${baseUrl}/register`,
            method: 'post',
            data
        })
    },
    //注销
    loginOut() {
        return request({
            url: `${baseUrl}/loginOut`,
            method: 'get'
        })
    },
    //验证唯一
    uniqueUser(name) {
        return request({
            url: `${baseUrl}/unique/${name}`,
            method: 'get'
        })
    },
}