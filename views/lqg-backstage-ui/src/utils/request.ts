import axios from 'axios';
import { getAuth } from '../utils/auth'

const request = axios.create({
    baseURL: 'http://admin-api.natapp1.cc/study',
    timeout: 15000
})

request.interceptors.request.use(config => {
    if (getAuth()) {
        config.headers['X-Token'] = getAuth();
    }
    return config;
}, error => {
    Promise.reject(error);
})

request.interceptors.response.use(res => {
    if (res.status === 200) {
        return res.data;
    }
}, error => {
    Promise.reject(error);
})
export default request;

/**
 * ### 数据类型？
```java
String、int、chat、boolean、short、double
```
### 请编写一个 0-10的随机数？
```java
int num = (int)Math.ramdom() * 10;
```
### 面向对象三大特征？
```java
继承、多态、封装
```
### 写出一个1-100的循环？
```
public class Demo{
    public static void main(String[] args) {
        int count = 1;
        while(count <= 100) {
          System.out.print(count + "\t");
        }
    }
}
```
-------- end ---------

 */