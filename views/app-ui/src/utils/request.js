import axios from 'axios'
import { getToken } from './token'

const localUrl = 'http://localhost:9003';

const yuanchengUrl = 'http://admin-api.natapp1.cc/';

const request = axios.create({
    baseURL: yuanchengUrl,
    timeout: 15000
})

request.interceptors.request.use(config => {
    if (getToken()) {
        config.headers['X-Token'] = getToken();
    }
    return config;
}, error => {
    Promise.reject(error);
})

request.interceptors.response.use(response => {
    if (response.status === 200) {
        return response.data;
    } else if (response.status === 400) {
        return '客户端发送的请求是语法错误';
    }
}, error => {
    Promise.reject(error);
})


export default request;