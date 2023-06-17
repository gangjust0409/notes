<template>
  <div class="articles">
    <div
      class="article_item"
      v-for="item in list"
      :key="item.articleId"
      @click="toDetail(item)"
    >
      <div class="article_info">
        <div class="article_title" v-html="item.title"></div>
        <div class="other_info">
          <div class="article_yuedu">
            <a href="">{{ item.reading }} 阅读</a>
            <a href="">{{ item.common }} 评论</a>
            <a href="">{{ item.love }} 点赞</a>
          </div>
          <div class="article_time">{{ item.createdDate }}</div>
        </div>
      </div>
      <div class="article_img">
        <img :src="item.img" alt="" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { defineProps, getCurrentInstance } from "vue";
import { getToken } from "@/utils/token";
const router = useRouter();
const { proxy } = getCurrentInstance();

const { list } = defineProps({
  list: Array,
});

function toDetail(article) {
  router.push({
    path: "/detail",
    query: { articleId: article.articleId },
  });
}
</script>

<style scoped lang="scss">
.articles {
  .article_item {
    display: flex;
    justify-content: space-between;
    line-height: 2;
    border-bottom: 1px dashed #666;
    margin-bottom: 10px;
    cursor: pointer;
    padding-bottom: 10px;
    .article_info {
      width: 100%;
      .article_title {
        font-size: 18px;
        font-weight: 600;
        margin-bottom: 37px;
        padding-right: 25px;
      }
      .other_info {
        display: flex;
        justify-content: space-between;
        align-items: center;
        .article_time {
          padding-right: 25px;
        }
        .article_yuedu {
          a {
            font-size: 12px;
            margin-right: 20px;
          }
        }
      }
    }
    .article_img {
      width: 200px;
      height: 100px;
      img {
        width: 100%;
        height: 100%;
      }
    }
  }
}
:deep(a) {
  color: #000;
}
</style>