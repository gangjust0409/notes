<template>
  <div class="article-app-container">
    <div class="titl">
      <my-article-header></my-article-header>
      <span>{{ title }}列表</span>
    </div>
    <div class="article_list">
      <div class="total">发现了 {{ total }} 条</div>
      <my-article-list :list="list"></my-article-list>
      <my-page
        :total="total"
        :num="page.pageNum"
        :size="page.pageSize"
        @page-change="pageChange"
      ></my-page>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from "vue";
import { useRoute } from "vue-router";
import articleRequest from "@/api/article";

const route = useRoute();

const page = reactive({
  pageNum: 1,
  pageSize: 10,
});
//总条数
const total = ref(0);
//列表
const list = ref([]);
//类型
const title = ref("");

const loadHomeList = async () => {
  title.value = route.query.type;
  let res = null;
  if (title.value === "推荐") {
    res = await articleRequest.recommendList(page);
  } else {
    res = await articleRequest.attentionList(page);
  }
  if (res.code === 200) {
    total.value = res.data.total;
    list.value = res.data.records;
  }
};

function pageChange(page) {
  page.pageNum = page;
  loadHomeList();
}

loadHomeList();
</script>

<style lang="scss" scoped>
.article-app-container {
  .titl {
    height: 50px;
    line-height: 75px;
    text-align: center;
    font-size: 18px;
    font-weight: 600;
  }
  .article_list {
    padding: 20px 100px;
  }
}
</style>