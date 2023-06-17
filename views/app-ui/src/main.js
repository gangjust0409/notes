import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import * as antDesignIcons from '@ant-design/icons-vue';
import { message } from 'ant-design-vue';
import { Modal } from 'ant-design-vue';
import 'ant-design-vue/es/message/style/index.css'
import 'ant-design-vue/es/modal/style/index.css'
import router from './router'
import myFooter from './components/myFooter.vue'
import myHeader from './components/myHeader.vue'
import page from './components/pagetion.vue'
import articleHeader from './components/articleHeader.vue'
import articleList from './components/articleItemList.vue'
import listEmpty from './components/listEmpty.vue'
import userItem from './components/articleItem.vue'
import guanzhu from "@/components/guanzhu.vue";

import VMdPreview from '@kangc/v-md-editor/lib/preview';
import VueMarkdownEditor from '@kangc/v-md-editor';
import createCopyCodePreview from '@kangc/v-md-editor/lib/plugins/copy-code/preview';
import '@kangc/v-md-editor/lib/plugins/copy-code/copy-code.css';
// import VMdPreviewHtml from '@kangc/v-md-editor/lib/preview-html';
import createLineNumbertPlugin from '@kangc/v-md-editor/lib/plugins/line-number/index';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';
//高亮代码
import createHighlightLinesPlugin from '@kangc/v-md-editor/lib/plugins/highlight-lines/index';
import '@kangc/v-md-editor/lib/plugins/highlight-lines/highlight-lines.css';

import Prism from 'prismjs';
import hljs from 'highlight.js';


VueMarkdownEditor.use(vuepressTheme, {
    Prism
});
VueMarkdownEditor.use(createHighlightLinesPlugin());
// 引入使用主题的样式
VMdPreview.use(vuepressTheme, {
    Prism
});
VueMarkdownEditor.use(createLineNumbertPlugin());
VMdPreview.use(createLineNumbertPlugin());
VMdPreview.use(createCopyCodePreview());
VueMarkdownEditor.use(createCopyCodePreview());
const app = createApp(App);

app.component('my-header', myHeader);
app.component('my-footer', myFooter);
app.component('my-page', page);
app.component('my-article-header', articleHeader);
app.component('my-article-list', articleList);
app.component('my-article-list-empty', listEmpty);
app.component('my-user-item', userItem);
app.component('my-guanzhu', guanzhu);
app.use(VueMarkdownEditor);
app.use(VMdPreview);

app.config.globalProperties.$message = message;
app.config.globalProperties.$modal = Modal;
// app.use(VMdPreviewHtml);

for (const [key, component] of Object.entries(antDesignIcons)) {
    app.component(key, component)
}
app.use(router);
app.mount('#app')
