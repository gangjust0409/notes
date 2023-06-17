<template>
  <a-layout>
    <a-layout-header> <my-header></my-header></a-layout-header>
    <a-layout>
      <a-layout-sider>
        <div class="catalog_container">
          <ul>
            <li>
              <a href="">大学</a> <a href="">高中</a> <a href="">初中</a
              ><a href="">小学</a>
            </li>
            <li><a href="">大学</a> <a href="">高中</a> <a href="">初中</a></li>
            <li><a href="">大学</a> <a href="">高中</a> <a href="">初中</a></li>
            <li><a href="">大学</a> <a href="">高中</a> <a href="">初中</a></li>
            <li><a href="">大学</a> <a href="">高中</a> <a href="">初中</a></li>
            <li><a href="">大学</a> <a href="">高中</a> <a href="">初中</a></li>
            <li><a href="">大学</a> <a href="">高中</a> <a href="">初中</a></li>
          </ul>
        </div>
      </a-layout-sider>
      <a-layout-content>
        <div class="article_catalog_box">
          <my-article-list></my-article-list>
        </div>
        <my-page></my-page>
      </a-layout-content>
    </a-layout>
    <my-footer></my-footer>
  </a-layout>
</template>

<script setup>
</script>

<style lang="scss" scoped>
.catalog_container {
  ul {
    li {
      list-style: none;
      a {
        font-size: 14px;
        margin: 0 5px;
      }
    }
  }
}
// :deep 穿透组件的css
.article_catalog_box {
  :deep(.article_item) {
    margin-right: 33px;
  }
}
</style>