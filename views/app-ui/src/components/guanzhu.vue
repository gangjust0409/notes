<template>
  <div class="guanzhu">
    <span
      class="primary"
      v-if="user.attention === false"
      @click="attentionFun(user)"
      >关注</span
    >
    <span
      class="default"
      v-else-if="user.attention === true"
      @click="unAttention(user)"
      >取消关注</span
    >
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";
import userRequest from "@/api/user.js";
const emits = defineEmits(["loadUserChange"]);

defineProps({
  user: {
    type: Object,
  },
});

//关注
async function attentionFun(item) {
  const res = await userRequest.attention({ attentionUserId: item.userId });
  if (res.code === 200) {
    emits("loadUserChange");
  }
}
//取消关注
async function unAttention(item) {
  const res = await userRequest.cancelAttention(item.userId);
  if (res.code === 200) {
    emits("loadUserChange");
  }
}
</script>

<style lang="scss" scoped>
.guanzhu {
  cursor: pointer;
  span {
    padding: 5px 10px;
    border-radius: 5px;
    margin-right: 5px;
    &.primary {
      background-color: var(--antd-wave-shadow-color);
      color: #fff;
    }
    &.default {
      background-color: #efefef;
      color: var(--antd-wave-shadow-color);
    }
  }
}
</style>