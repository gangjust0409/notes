<template>
  <div class="other_user_container">
    <my-article-header></my-article-header>
    <div class="other_user">
      <user-info :userInfo="user" :currentUser="false">
        <template #default>
          <img class="user_pic" :src="user.pic" :alt="user.userName" />
        </template>
        <template #guanzhu>
          <my-guanzhu :user="user" @load-user-change="userFn"></my-guanzhu>
        </template>
      </user-info>
    </div>
  </div>
</template>

<script setup>
import { useRoute } from "vue-router";
import { ref } from "vue";
import userInfo from "@/components/userInfo.vue";
import userRequest from "@/api/user";

const route = useRoute();
const user = ref({});
const userFn = async () => {
  const res = await userRequest.getUser(route.query.userId);
  if (res.code === 200) {
    user.value = res.data;
  }
};
userFn();
</script>

<style scoped lang="scss">
.other_user_container {
  .other_user {
    padding: 60px 30px;
  }
}
</style>