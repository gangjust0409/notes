import { defineStore } from 'pinia'

import menuRequest from "../api/menu";


export default defineStore('menu', {
    state: () => {
        return {
            menus: []
        }
    },
    actions: {
        //请求列表
        async loadMenus() {
            const res = await menuRequest.menuTreeList();
            this.menus = res.data;
        }
    },
    getters: {
        getterMenus: (state) => {
            return state.menus;
        }
    }
});


