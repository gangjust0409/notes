<template>
  <div class="pagetion">
    <a-pagination
      :current="num"
      :total="total"
      :page-size="size"
      @change="onChange"
      show-less-items
    />
  </div>
</template>

<script setup>
import { defineProps, ref, defineEmits } from "vue";
const n = ref(1);

const { num } = defineProps({
  num: {
    type: Number,
    default: 1,
  },
  size: {
    type: Number,
    default: 10,
  },
  total: {
    type: Number,
    default: 0,
  },
});

const emit = defineEmits(["pageChange"]);

function onChange(page) {
  emit("pageChange", page);
}
</script>

<style scoped>
.pagetion {
  margin-top: 20px;
  text-align: right;
}
</style>