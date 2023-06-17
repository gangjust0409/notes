import request from '../utils/request';

const baseUrl = '/user';

export default {
    //登录
    login(data: any) {
        return request({
            url: `${baseUrl}/login`,
            method: 'post',
            data
        })
    },
    //用户信息
    userInfo(token: string) {
        return request({
            url: `${baseUrl}/user/info/${token}`,
            method: 'get'
        })
    },
    //注销
    loginOut() {
        return request({
            url: `${baseUrl}/loginOut`,
            method: 'get'
        })
    }
}
