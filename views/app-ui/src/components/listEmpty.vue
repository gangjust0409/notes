<script setup>
import { defineProps } from "vue";
const { empty } = defineProps({
  empty: String,
});
</script>
<template>
  <div class="empty">
    <a-empty
      image="https://gw.alipayobjects.com/mdn/miniapp_social/afts/img/A*pevERLJC9v0AAAAAAAAAAABjAQAAAQ/original"
      :image-style="{
        height: '60px',
      }"
    >
      <template #description>
        <span> 暂无{{ empty }} </span>
      </template>
    </a-empty>
  </div>
</template>

<style scped>
.empty {
  padding: 49px 0;
}
</style>