<template>
  <div class="user_container">
    <div class="user_zhanshi">
      <div class="user_row">
        <slot></slot>
      </div>
      <div class="user_row">
        <div class="user_fengshi">
          <div>关注</div>
          <div>{{ userInfo.fans }}</div>
        </div>
        <div class="user_fengshi">
          <div>粉丝</div>
          <div>{{ userInfo.follow }}</div>
        </div>
      </div>
      <div class="user_row username">
        {{ userInfo.userName }} &nbsp;<span style="cursor: pointer"
          ><edit-outlined
        /></span>
      </div>
      <slot name="guanzhu"></slot>
    </div>

    <div class="user_article_row">
      <a-tabs v-model:activeKey="activeKey" v-if="currentUser">
        <a-tab-pane v-for="item in navs" :key="item.key">
          <template #tab>
            <span>
              <component :is="item.icon"></component>
              {{ item.title }}
            </span>
          </template>
          <!-- <article_item></article_item> -->
          <my-article-list
            :list="articles"
            v-if="articles.length > 0"
          ></my-article-list>
          <my-page
            v-if="articles.length > 0"
            :num="num"
            :size="size"
            :total="total"
            @page-change="onChange"
          >
          </my-page>
          <my-article-list-empty
            v-else
            :empty="item.empty + '的作品'"
          ></my-article-list-empty>
        </a-tab-pane>
      </a-tabs>
      <a-tabs v-model:activeKey="activeKey" v-else>
        <a-tab-pane v-for="item in otherNav" :key="item.key">
          <template #tab>
            <span>
              <component :is="item.icon"></component>
              {{ item.title }}
            </span>
          </template>
          <!-- <article_item></article_item> -->
          <my-article-list
            :list="articles"
            v-if="articles.length > 0"
          ></my-article-list>
          <my-page
            v-if="articles.length > 0"
            :num="num"
            :size="size"
            :total="total"
            @page-change="onChange"
          >
          </my-page>
          <my-article-list-empty
            v-else
            :empty="item.empty + '的作品'"
          ></my-article-list-empty>
        </a-tab-pane>
      </a-tabs>
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps, watch, reactive } from "vue";
import { useRoute } from "vue-router";
import article_item from "@/components/articleItem.vue";
import articleRequest from "@/api/article.js";

const route = useRoute();
let activeKey = ref(1);
//列表
const articles = ref([]);
//导航
const navs = reactive([
  {
    key: 2,
    title: "待审核",
    icon: "safety-outlined",
    empty: "审核",
  },
  {
    key: 1,
    title: "我的文章",
    icon: "solution-outlined",
    empty: "文章",
  },
  {
    key: 3,
    title: "我的收藏",
    icon: "star-outlined",
    empty: "收藏",
  },
  {
    key: 4,
    title: "喜欢",
    icon: "heart-outlined",
    empty: "喜欢",
  },
  {
    key: 5,
    title: "点赞",
    icon: "like-outlined",
    empty: "点赞",
  },
]);
const otherNav = reactive([
  {
    key: 1,
    title: "他的文章",
    icon: "solution-outlined",
    empty: "文章",
  },
]);
//分页
const num = ref(1);
const size = ref(5);
const total = ref(0);

const { audit, userInfo, currentUser } = defineProps({
  audit: {
    type: Boolean,
    default: false,
  },
  userInfo: {
    type: Object,
  },
  currentUser: {
    type: Boolean,
    default: false,
  },
});

watch(
  activeKey,
  (val) => {
    num.value = 1;
    if (currentUser) {
      getArticleList(val);
    } else {
      getUserArticle();
    }
  },
  { deep: true, immediate: true }
);

//返回文章列表
async function getArticleList(val) {
  const res = await articleRequest.getArticle(typeConvertDataType(val), {
    num: num.value,
    size: size.value,
  });
  if (res.code === 200) {
    articles.value = res.data.records;
    num.value = res.data.current;
    size.value = res.data.size;
    total.value = res.data.total;
  }
}

//返回文章类型
function typeConvertDataType(type) {
  if (type === 1 || type === 3 || type === 4 || type === 5) {
    return type;
  } else if (2) {
    return 0;
  }
}

//分页
function onChange(page) {
  num.value = page;
  getArticleList(activeKey.value);
}

//查看其它用户
async function getUserArticle() {
  let obj = {
    num: num.value,
    size: size.value,
    userId: route.query.userId,
  };
  const res = await articleRequest.otherSearchArticles(obj);
  if (res.code === 200) {
    articles.value = res.data.records;
  }
}
</script>

<style scoped lang="scss">
.user_container {
  flex: 2;
  .user_zhanshi {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background: linear-gradient(340deg, cadetblue, #fff);
    height: 290px;
    .user_row {
      margin: 10px 0;
      display: flex;
      .user_fengshi {
        text-align: center;
        padding: 0 15px;
        &:first-of-type {
          border-right: 1px solid #ccc;
        }
        div {
        }
      }
    }
  }
}

:deep(.ant-upload.ant-upload-select-picture-card) {
  background-color: transparent;
}
.pagetion {
  margin-top: 20px;
  text-align: right;
}
</style>