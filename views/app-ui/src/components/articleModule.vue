<template>
  <a-layout-content class="section_box">
    <div class="article_box_title">{{ title }}</div>
    <article_item />
  </a-layout-content>
</template>

<script setup>
import article_item from "@/components/articleItem.vue";
import { defineProps } from "vue";

const props = defineProps({
  title: {
    type: String,
  },
});
</script>

<style scoped lang="scss">
.section_box {
  .article_box_title {
    font-size: 18px;
    margin-bottom: 15px;
  }
}
</style>