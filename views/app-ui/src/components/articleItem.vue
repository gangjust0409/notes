<template>
  <div class="article_list">
    <div
      class="article_item"
      v-for="item in list"
      :key="item.userId"
      @click.self="toUser(item)"
    >
      <div class="article_item_img" @click="toUser(item)">
        <img v-if="item.pic" :src="item.pic" :alt="item.userName" />
        <a-avatar
          v-else
          shape="square"
          size="large"
          :style="{ backgroundColor: '#f56a00', verticalAlign: 'middle' }"
        >
          {{ item.userName }}
        </a-avatar>
      </div>
      <div
        class="article_item_title"
        @click.self="toUser(item)"
        v-html="item.userName"
      ></div>
      <my-guanzhu :user="item" @load-user-change="loadUser"></my-guanzhu>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";
import { useRouter } from "vue-router";
// import useAttention from "@/hooks/attention";
import userRequest from "@/api/user.js";

const router = useRouter();
const emits = defineEmits(["loadUserChange"]);

function loadUser() {
  emits("loadUserChange");
}

defineProps({
  list: {
    type: Array,
  },
});

function toUser(item) {
  if (item.attention === null) {
    router.push({ path: "/user/center", query: { userId: item.userId } });
  } else {
    router.push({ path: "/to/user", query: { userId: item.userId } });
  }
}
</script>

<style lang="scss" scoped>
.article_list {
  display: flex;
  flex-wrap: wrap;
  .article_item {
    display: flex;
    flex-direction: column;
    justify-content: center;
    text-align: center;
    width: 150px;
    border: 1px solid #ccc;
    border-radius: 5px;
    padding: 5px;
    margin-right: 7px;
    margin-bottom: 20px;
    cursor: pointer;
    &:hover {
      transform: scale(1.1);
      transition: transform 0.5s;
    }
    .article_item_img {
      width: 80px;
      height: 80px;
      border-radius: 50%;
      margin: 0 auto;
      img {
        width: 100%;
        height: 100%;
        border-radius: 50%;
      }
    }
    .article_item_title {
      margin: 20px 0 10px 0;
    }
  }
}
</style>