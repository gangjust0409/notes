<template>
  <div class="mianbao_box">
    <a-page-header
      title="返回"
      class="mianbao"
      sub-title=""
      @back="() => router.back()"
    />
  </div>
</template>

<script setup>
import { defineProps } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();

const props = defineProps({
  title: String,
});
</script>

<style scoped lang="scss">
.mianbao_box {
  .mianbao {
    position: fixed;
  }
}
</style>