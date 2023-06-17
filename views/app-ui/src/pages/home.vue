<template>
  <a-layout>
    <a-layout-header> <my-header></my-header></a-layout-header>
    <div class="search_button" @click="toSearch">
      <div>搜索</div>
    </div>
    <!-- <a-layout class="home_section">
      <a-layout-content>
        <a-carousel arrows>
          <template #prevArrow>
            <div class="custom-slick-arrow" style="left: 10px; z-index: 1">
              <left-circle-outlined />
            </div>
          </template>
          <template #nextArrow>
            <div class="custom-slick-arrow" style="right: 10px">
              <right-circle-outlined />
            </div>
          </template>
          <div><h3>1</h3></div>
          <div><h3>2</h3></div>
          <div><h3>3</h3></div>
          <div><h3>4</h3></div>
        </a-carousel>
      </a-layout-content>
    </a-layout> -->
    <div class="home_article_box">
      <div class="home_article_left">
        <div class="home_article_title">
          <span>推荐</span>
          <span v-if="articleList.length > 0" @click="toHomeList('推荐')"
            >更多<right-outlined
          /></span>
        </div>
        <div class="home_article_list">
          <my-article-list :list="articleList"></my-article-list>
          <my-article-list-empty
            v-if="articleList.length === 0"
            empty="推荐的笔记"
          ></my-article-list-empty>
        </div>
      </div>
      <div class="home_article_right">
        <div class="home_article_title">
          <span>关注</span>
          <span v-if="attentionList.length > 0" @click="toHomeList('关注')"
            >更多<right-outlined
          /></span>
        </div>
        <div class="home_article_list">
          <my-article-list :list="attentionList"></my-article-list>
          <my-article-list-empty
            v-if="attentionList.length === 0"
            empty="关注的笔记"
          ></my-article-list-empty>
        </div>
      </div>
    </div>
    <div class="release_btn" @click="toRelease">发布</div>
    <my-footer></my-footer>
  </a-layout>
</template> 

<script setup>
import my_header from "../components/myHeader.vue";
import article_module from "@/components/articleModule.vue";
import articleRequest from "@/api/article.js";
import { ref } from "vue";

import { useRouter } from "vue-router";
const router = useRouter();
//推荐列表
const articleList = ref([]);
//关注列表
const attentionList = ref([]);

//加载首页推荐
async function loadHomeRecommend() {
  const res = await articleRequest.recommendList({ pageNum: 1, pageSize: 5 });
  if (res.code === 200 && res.data !== null) {
    articleList.value = res.data.records;
  }
}
//加载首页关注
async function loadAttentionList() {
  const res = await articleRequest.attentionList({ pageNum: 1, pageSize: 5 });
  if (res.code === 200 && res.data !== null) {
    attentionList.value = res.data.records;
  }
}
function toSearch() {
  router.push("/query");
}
function toRelease() {
  router.push("/release");
}
function toHomeList(type) {
  router.push({ path: "/home/list", query: { type } });
}
loadHomeRecommend();
loadAttentionList();
</script>

<style scoped lang="scss">
.search_button {
  div {
    height: 30px;
    line-height: 30px;
    border: 1px solid #ccc;
    background-color: #fff;
    text-align: center;
    border-radius: 5px;
    cursor: pointer;
  }
}
/* For demo */
.ant-carousel :deep(.slick-slide) {
  text-align: center;
  height: 320px;
  line-height: 290px;
  background: #364d79;
  overflow: hidden;
}

.ant-carousel :deep(.slick-arrow.custom-slick-arrow) {
  width: 25px;
  height: 25px;
  font-size: 25px;
  color: #fff;
  background-color: rgba(31, 45, 61, 0.11);
  opacity: 0.3;
  z-index: 1;
}
.ant-carousel :deep(.custom-slick-arrow:before) {
  display: none;
}
.ant-carousel :deep(.custom-slick-arrow:hover) {
  opacity: 0.5;
}

.ant-carousel :deep(.slick-slide h3) {
  color: #fff;
}
.home_article_box {
  padding: 0 84px;
  display: flex;
  margin-top: 15px;
  .home_article_left,
  .home_article_right {
    width: 50%;
    .home_article_title {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 15px;
      cursor: pointer;
      span {
        display: block;
        display: flex;
        align-items: center;
        &:first-child {
          font-size: 16px;
          font-weight: 600;
          font-family: "Trebuchet MS", "Lucida Sans Unicode", "Lucida Grande",
            "Lucida Sans", Arial, sans-serif;
        }
        &:last-child {
          font-size: 12px;
        }
      }
    }
    .home_article_list {
    }
  }
  .home_article_right {
    margin-left: 25px;
  }
}
.home_section {
  padding: 0 84px;
}
:deep(.article_title) {
  height: 65px;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-line-clamp: 2;
  word-spacing: 1px;
  display: -webkit-box;
  -webkit-box-orient: vertical;
}
:deep(.article_title) {
  margin-bottom: 0 !important;
}
:deep(.article_img) {
  height: 85px !important;
}
</style>