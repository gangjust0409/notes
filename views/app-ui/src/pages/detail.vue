<template>
  <div class="detail_box">
    <my-article-header></my-article-header>
    <div class="detail_container">
      <div class="detail_left">
        <div class="detail_nav">
          <div
            class="detail_nav_item"
            v-for="anchor in titles"
            :key="anchor"
            :style="{ padding: `10px 0 10px ${anchor.indent * 20}px` }"
            @click="handleAnchorClick(anchor)"
          >
            <div style="cursor: pointer">{{ anchor.title }}</div>
          </div>
        </div>
      </div>
      <div class="detail_content">
        <!-- preview-class 为主题的样式类名，例如vuepress就是vuepress-markdown-body -->
        <div class="article_title">{{ article.title }}</div>
        <div class="article_author_name">作者：{{ article.author }}</div>
        <!-- <v-md-preview :text="text"></v-md-preview> -->
        <!-- <v-md-editor
          :model-value="article.content"
          mode="preview"
          :toc-nav-position-right="true"
          :default-show-toc="true"
          :include-level="[1, 2, 3, 4, 5, 6]"
          ref="preview"
        ></v-md-editor> -->
        <v-md-preview :text="article.content" ref="preview" />
        <!-- 发布日期和删除按钮 -->
        <div class="delete_article">
          <div>发布日期：{{ article.releaseDate }}</div>
          <delete-outlined
            v-if="article.delete"
            style="font-size: 18px; cursor: pointer"
            @click="showDelete"
          />
        </div>
        <!-- 点赞喜欢 -->
        <div class="setting_container">
          <div class="dianz" @click="articleSetting(5, 'giveLike')">
            <like-outlined v-if="!article.giveLike" style="font-size: 20px" />
            <like-filled v-else style="font-size: 20px" />
            点赞
          </div>
          <div class="other_setting">
            <div @click="articleSetting(4, 'love')">
              <heart-outlined v-if="!article.love" style="font-size: 20px" />
              <heart-filled v-else style="font-size: 20px" />
              喜欢
            </div>
            <div @click="articleSetting(3, 'collect')">
              <star-outlined v-if="!article.collect" style="font-size: 20px" />
              <star-filled v-else style="font-size: 20px" />
              收藏
            </div>
            <div @click="openCommon">
              <message-outlined style="font-size: 20px" />
              评论
            </div>
          </div>
        </div>
        <!-- 评论 -->
        <div
          class="common_container"
          v-if="commons.length > 0 || commons != null"
        >
          <div class="common_list">
            <div class="common_item" v-for="com in commons" :key="com.commonId">
              <div class="common_other_item">
                <div class="common_pic" @click="toUserInfo(com.user)">
                  <img v-if="com.user.pic" :src="com.user.pic" alt="" />
                  <a-avatar
                    v-else
                    shape="square"
                    size="large"
                    :style="{
                      backgroundColor: '#00a2ae',
                      verticalAlign: 'middle',
                    }"
                  >
                    {{ com.user.userName.substring(0, 1) }}
                  </a-avatar>
                </div>
                <div class="common_user_info">
                  <div class="common_user_setting">
                    <div class="common_user_name" @click="toUserInfo(com.user)">
                      {{ com.user.userName }}
                      <span class="author" v-if="com.author">[作者]</span>
                    </div>
                    <div class="common_reply">
                      <span @click="listCommon(com)">评论</span>&nbsp;<span
                        v-if="com.delete"
                        @click="commonDel(com.commonId)"
                        >删除</span
                      >
                    </div>
                  </div>
                  <div class="common_content_box">
                    <div class="common_content">{{ com.content }}</div>
                    <div class="common_datetime">{{ com.commonDate }}</div>
                  </div>
                </div>
              </div>
              <div class="common_current_list" v-show="com.children">
                <div
                  class="common_item"
                  v-for="e in com.children"
                  :key="e.commonId"
                >
                  <div class="common_other_item">
                    <div class="common_pic" @click="toUserInfo(e.user)">
                      <img v-if="e.user.pic" :src="e.user.pic" alt="" />
                      <a-avatar
                        v-else
                        shape="square"
                        size="large"
                        :style="{
                          backgroundColor: '#00a2ae',
                          verticalAlign: 'middle',
                        }"
                      >
                        {{ e.user.userName.substring(0, 1) }}
                      </a-avatar>
                    </div>
                    <div class="common_user_info">
                      <div class="common_user_setting">
                        <div
                          class="common_user_name"
                          @click="toUserInfo(e.user)"
                        >
                          <div v-if="e.reply === null || e.reply === ''">
                            {{ e.user.userName }}
                            <span class="author" v-if="e.author">[作者]</span>
                          </div>
                          <div v-else>
                            {{ e.user.userName }}
                            <span class="author" v-if="e.author">[作者]</span>
                            <b>回复</b>
                            {{ e.reply }}
                          </div>
                        </div>
                        <div class="common_reply">
                          <span @click="subCommon(com, e)">评论</span
                          >&nbsp;<span
                            @click="commonDel(e.commonId)"
                            v-if="e.delete"
                            >删除</span
                          >
                        </div>
                      </div>
                      <div class="common_content_box">
                        <div class="common_content">
                          {{ e.content }}
                        </div>
                        <div class="common_datetime">{{ e.commonDate }}</div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- 没有登录 -->
        <a-empty
          v-if="getToken() === underline"
          image="https://gw.alipayobjects.com/mdn/miniapp_social/afts/img/A*pevERLJC9v0AAAAAAAAAAABjAQAAAQ/original"
          :image-style="{
            height: '60px',
          }"
        >
          <template #description>
            <span>
              暂无评论，
              <router-link to="/login">请先登录</router-link>
            </span>
          </template>
        </a-empty>
        <!-- 已经登录，没有评论 -->
        <my-article-list-empty
          v-if="(getToken() != null && commons.length === 0) || comons === null"
          empty="评论"
        ></my-article-list-empty>
      </div>
      <div class="detail_right">
        <div class="detail_other_box">
          <div class="detail_other_title">作者其他文章</div>
          <div class="detail_other_list">
            <div
              class="detail_other_item"
              v-for="e in article.otherArticles"
              :key="e.articleId"
              @click="toArticleDetail(e.articleId)"
            >
              <div class="detail_other_item_name">{{ e.title }}</div>
              <!-- <div class="detail_other_item_datetime">2022-04-12</div> -->
            </div>
          </div>
        </div>
      </div>
    </div>
    <a-modal
      v-model:visible="isCommon"
      title="评论"
      @ok="handleOk"
      cancelText="取消"
      okText="评论"
    >
      <a-input v-model:value="common.content" placeholder="恶语伤人" />
    </a-modal>
  </div>
</template>

<script setup>
import {
  ref,
  onBeforeMount,
  nextTick,
  reactive,
  getCurrentInstance,
  createVNode,
  toRaw,
} from "vue";
import { ExclamationCircleOutlined } from "@ant-design/icons-vue";
import { message } from "ant-design-vue";
import { useRouter, useRoute } from "vue-router";
import articleRequest from "@/api/article";
import commonRequest from "@/api/common";
import { getToken } from "@/utils/token";

const router = useRouter();
const route = useRoute();
const { proxy } = getCurrentInstance();

let article = ref();
const isCommon = ref(false);
let common = reactive({
  content: "",
  articleId: 0,
  parentId: 0,
});
let titles = ref([]);
const preview = ref();
const commons = ref([]);

//加载当前文档
loadCurrentArticle(route.query.articleId);
loadCommons(route.query.articleId);
recommendIncrement(route.query.articleId);

//热度加加
function recommendIncrement(articleId) {
  //热度加加
  articleRequest.recommendArticle(articleId);
}

function openCommon() {
  isCommon.value = !isCommon.value;
  common.articleId = article.value.articleId;
  common.parentId = 0;
}

//点击跳转的文章地方，锚链接
function handleAnchorClick(anchor) {
  const { lineIndex } = anchor;

  const heading = preview.value.$el.querySelector(
    `[data-v-md-line="${lineIndex}"]`
  );

  if (heading) {
    preview.value.scrollToTarget({
      target: heading,
      scrollContainer: window,
      top: 60,
    });
  }
}
//加载数据并在渲染
async function loadCurrentArticle(id) {
  const res = await articleRequest.getArticleById(id);
  if (res.code === 200) {
    article.value = res.data;
  }
  //当数据加载完成时才进行渲染
  await nextTick();
  const anchors = preview.value.$el.querySelectorAll("h1,h2,h3,h4,h5,h6");
  const ts = Array.from(anchors).filter((title) => !!title.innerText.trim());

  if (!ts.length) {
    titles.value = [];
    return;
  }

  const hTags = Array.from(new Set(ts.map((title) => title.tagName))).sort();
  titles.value = ts.map((el) => ({
    title: el.innerText,
    lineIndex: el.getAttribute("data-v-md-line"),
    indent: hTags.indexOf(el.tagName),
  }));
}
//点击评论头像挑战用户
function toUserInfo(user) {
  if (user.attention) {
    router.push({ path: "/user/center", query: { userId: user.userId } });
  } else {
    router.push({ path: "/to/user", query: { userId: user.userId } });
  }
}
//显示删除弹框
function showDelete() {
  proxy.$modal.confirm({
    title: "温馨提示",
    icon: createVNode(ExclamationCircleOutlined),
    content: "确定是否删除该文章？",
    cancelText: "取消",
    okText: "确定",
    onOk() {
      return new Promise((resolve, reject) => {
        setTimeout(async () => {
          const res = await articleRequest.deleteArticle(
            article.value.articleId
          );
          if (res.code === 200) {
            proxy.$message.success("删除成功");
            resolve();
            router.go(-1);
          }
        }, 1500);
      }).catch(() => {});
    },
    // eslint-disable-next-line @typescript-eslint/no-empty-function
    onCancel() {},
  });
}

//是否登录
function isLogin() {
  if (!getToken()) {
    message.error("请先登录在进行操作！");
    return false;
  } else {
    return true;
  }
}

//收藏、喜欢、点赞
async function articleSetting(type, text) {
  if (!isLogin()) return;
  if (text === "collect" && article.value.collect) {
    await articleRequest.settingDelete(article.value.articleId + "-" + type);
    loadCurrentArticle(route.query.articleId);
  } else if (text === "love" && article.value.love) {
    await articleRequest.settingDelete(article.value.articleId + "-" + type);
    loadCurrentArticle(route.query.articleId);
  } else if (text === "giveLike" && article.value.giveLike) {
    await articleRequest.settingDelete(article.value.articleId + "-" + type);
    loadCurrentArticle(route.query.articleId);
  } else {
    const res = await articleRequest.settingArticle({
      type,
      articleId: article.value.articleId,
    });
    if (res.code === 200) {
      loadCurrentArticle(route.query.articleId);
    }
  }
}

function toArticleDetail(articleId) {
  router.push({ path: "detail", query: { articleId } });
  loadCurrentArticle(articleId);
  loadCommons(articleId);
}
//点击评论
async function handleOk() {
  if (!isLogin()) return;
  const res = await commonRequest.common(common);
  if (res.code === 200) {
    common.content = "";
    isCommon.value = false;
    loadCommons(route.query.articleId);
  }
}
//加载评论列表
async function loadCommons(articleId) {
  const res = await commonRequest.articleCommon(articleId);
  if (res.code === 200 && res.data != null) {
    commons.value = res.data;
  }
}
//点击列表评论
function listCommon(com) {
  common.parentId = com.commonId;
  common.articleId = com.articleId;
  isCommon.value = true;
}
function subCommon(com, e) {
  isCommon.value = true;
  common.parentId = com.commonId;
  common.articleId = com.articleId;
  common.reply = e.user.userName;
  console.log(e);
}
//点击删除评论
async function commonDel(id) {
  const res = await commonRequest.deleteArticleCommon(id);
  if (res.code === 200) {
    loadCommons();
  }
}
</script>

<style lang="scss" scoped>
.detail_box {
  .detail_container {
    display: flex;
    padding: 0 25px;
    .detail_left {
      flex: 1;
      .detail_nav {
        position: fixed;
        top: 60px;
        width: 200px;
        height: 553px;
        overflow: auto;
        .detail_nav_item {
          flex-wrap: wrap;
          a {
            font-size: 12px !important;
          }
        }
      }
    }
    .detail_content {
      flex: 3;
      width: 250px;
      .article_title {
        margin: 50px 0 25px 0;
        font-weight: 600;
        font-size: 18px;
        text-align: center;
      }
      .article_author_name {
        text-align: right;
        margin-bottom: 20px;
      }
      .setting_container {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 25px;
        user-select: none;
        .dianz {
          display: flex;
          text-align: center;
          justify-content: center;
          flex-direction: column;
          cursor: pointer;
        }

        .other_setting {
          display: flex;
          flex-direction: row !important;
          div {
            display: flex;
            text-align: center;
            justify-content: center;
            flex-direction: column;
            margin-left: 20px;
            cursor: pointer;
          }
        }
      }
      .common_container {
        .common_list {
          display: flex;
          flex-direction: column;
          .common_item {
            .common_other_item {
              display: flex;
              flex: 1;
              margin-bottom: 15px;
              .common_pic {
                width: 60px;
                height: 55px;
                border-radius: 50%;
                margin-right: 20px;
                cursor: pointer;
                img {
                  width: 100%;
                  height: 100%;
                  border-radius: 50%;
                }
              }
              .common_user_info {
                width: 100%;
                .common_user_setting {
                  display: flex;
                  justify-content: space-between;
                  padding-right: 52px;
                  margin-bottom: 5px;
                  .common_user_name {
                    font-size: 16px;
                    font-weight: 600;
                    cursor: pointer;
                    b {
                      margin: 0 5px;
                      font-size: 14px;
                    }
                  }
                  .common_reply {
                    font-size: 14px;
                    color: #409eff;
                    span {
                      cursor: pointer;
                      &:hover {
                        text-decoration: underline;
                      }
                    }
                  }
                }
                .common_content_box {
                  display: flex;
                  justify-content: space-between;
                  align-items: center;
                  .common_content {
                    float: left;
                  }
                  .common_datetime {
                    float: right;
                  }
                }
              }
            }
          }
        }
        .common_current_list {
          padding-left: 70px;
        }
      }
    }
    .detail_right {
      flex: 1;
      margin: 50px 0px 0 20px;
      .detail_other_box {
        width: 20%;
        height: 100%;
        position: fixed;
        .detail_other_title {
          height: 35px;
          line-height: 35px;
          background: linear-gradient(#efefef, #ddd);
          text-indent: 2em;
        }
        .detail_other_list {
          .detail_other_item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin: 10px 0;
            cursor: pointer;
          }
        }
      }
    }
  }
  .delete_article {
    display: flex;
    align-items: center;
    padding-bottom: 10px;
    border-bottom: 1px dashed #666;
    margin-bottom: 10px;
    div {
      margin-right: 15px;
    }
  }
}
.author {
  font-size: 10px;
  color: red;
}
.ant-empty a {
  font-size: 14px;
  color: var(--antd-wave-shadow-color);
}
</style>