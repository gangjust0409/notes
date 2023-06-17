import request from '../utils/request'

export default {
    //树形菜单
    menuTreeList() {
        return request({
            url: `/menu/tree/list`,
            method: 'get'
        })
    },
}
