import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'
//权限控制
import './permiss'
import './style/element/index.scss'
import 'element-plus/theme-chalk/el-message.css'
// import VueMarkdownEditor from '@kangc/v-md-editor';
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';
//npm i --save-dev @types/prismjs
import Prism from 'prismjs';

VMdPreview.use(vuepressTheme, {
    Prism,
});

const store = createPinia();
const app = createApp(App);

//配置全局

//路由
app.use(router);
app.use(store);
app.use(VMdPreview);
app.mount('#app')
