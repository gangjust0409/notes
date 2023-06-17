<template>
  <div class="my_header">
    <div class="logo">
      <a href=""><img src="@/assets/logo.png" alt="" /></a>
    </div>
    <div class="header_menu">
      <router-link to="/home">首页</router-link>
      <!-- <router-link to="/catalog">分类</router-link> -->
      <router-link to="/user/center" v-if="getToken() != null"
        >个人中心</router-link
      >
      <router-link to="/login" v-if="getToken() == null">登录</router-link>
      <router-link to="/regist" v-if="getToken() == null">注册</router-link>
      <a href="https://markdown.com.cn/editor/">教程（markdown官网）</a>
      <a href="" @click="aboutMr">关于我们</a>
      <!-- <router-link to="/query"><search-outlined /></router-link> -->
    </div>
  </div>
</template>

<script setup>
import { getToken } from "@/utils/token";
import { Modal } from "ant-design-vue";
import { h } from "vue";

const aboutMr = (e) => {
  Modal.info({
    title: "关于我们",
    content: h("div", {}, [
      h("p", "本站使用 SringBoot 2.7.8 +  Vue 3.0 构建。"),
      h(
        "p",
        "使用技术栈有 springboot、vue全家桶、redis、mysql等。后台系统使用 vue3 + Ts"
      ),
      h("p", "联系我们： 19907869342"),
      h("p", "邮箱：3139725211@qq.com、0409justgang@163.com"),
    ]),
    onOk() {
      console.log("ok");
    },
  });
  e.preventDefault();
};
</script>

<style lang="scss" scoped>
.my_header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  .logo {
    width: 155px;
    height: 100px;
    a {
      display: block;
      height: 100%;
      img {
        width: 100%;
        height: 100%;
      }
    }
  }
  .header_menu {
    a {
      margin-right: 15px;
    }
  }
}
:deep(a) {
  color: #000;
}
:deep(.router-link-active) {
  color: var(--antd-wave-shadow-color);
}
</style>