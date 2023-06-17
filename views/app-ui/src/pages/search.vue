<template>
  <my-article-header></my-article-header>
  <div class="search_container">
    <div class="search_comb search_row">
      <a-input-search
        v-model:value="params.keyword"
        placeholder="文章标题"
        enter-button
        @search="searchArticle"
      />
    </div>
    <!-- 笔记 -->
    <div class="search_tit">笔记：</div>
    <div class="nav_tit search_row">查找到 {{ total }} 条记录</div>
    <div class="article_list">
      <my-article-list :list="articleList"></my-article-list>
      <my-page
        :total="total"
        :num="params.pageNum"
        :size="params.pageSize"
        @page-change="pageChange"
      ></my-page>
    </div>
    <!-- 人物 -->
    <div class="search_tit">用户：</div>
    <div class="nav_tit search_row">查找到 {{ userTotal }} 条记录</div>
    <div class="user_list">
      <my-user-item
        :list="userList"
        @load-user-change="searchUser"
      ></my-user-item>
      <my-page
        :total="userTotal"
        :num="userParams.pageNum"
        :size="userParams.pageSize"
        @page-change="pageChange"
      ></my-page>
    </div>
  </div>
  <my-footer></my-footer>
</template>

<script>
import {
  ref,
  defineComponent,
  defineAsyncComponent,
  reactive,
  onMounted,
  toRef,
} from "vue";
import articleRequest from "@/api/article";
import userRequest from "@/api/user";

export default defineComponent({
  setup() {
    const params = reactive({
      keyword: "",
      pageNum: 1,
      pageSize: 10,
    });
    const userParams = reactive({
      keyword: "",
      pageNum: 1,
      pageSize: 10,
    });
    //总数
    let total = ref(0);
    const userTotal = ref(0);
    //搜索的列表
    const articleList = ref([]);
    const userList = ref([]);
    const articleItem = defineAsyncComponent(() =>
      import("@/components/articleItem.vue")
    );
    //搜索
    const searchArticle = async () => {
      const res = await articleRequest.searchArticleList(params);
      if (res.code === 200) {
        articleList.value = res.data.records;
        total.value = res.data.total;
      }
      searchUser();
    };

    const searchUser = async () => {
      userParams.keyword = params.keyword;
      const userRes = await userRequest.searchUsers(userParams);
      if (userRes.code === 200) {
        userList.value = userRes.data.records;
        userTotal.value = userRes.data.total;
      }
    };

    //分页
    const pageChange = (page) => {
      params.pageNum = page;
      searchArticle();
    };

    onMounted(() => {
      //加载显示全部
      searchArticle();
    });

    return {
      params,
      userParams,
      articleItem,
      articleList,
      total,
      userTotal,
      userList,
      searchArticle,
      searchUser,
      pageChange,
    };
  },
});
</script>

<style scoped lang="scss">
.search_container {
  padding: 30px 100px 0 7pc;
  margin: 0px 0 10px 0;
  .search_comb {
    padding-top: 20px;
  }
  .search_row {
    margin-bottom: 20px;
  }
  .nav_tit {
    font-size: 13px;
  }
  .user_list,
  .article_list {
    margin-left: 20px;
  }
  .search_tit {
    font-weight: 500;
    font-size: 16px;
    margin-bottom: 5px;
  }
}
</style>