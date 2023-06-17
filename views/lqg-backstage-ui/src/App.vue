<script setup lang="ts">

</script>

<template>
  <router-view v-slot="{ Component }">
    <transition name="slide-fade">
      <component :is="Component" />
    </transition>
  </router-view>
</template>

<style lang="scss">
@import '@/assets/scss/style.scss';
</style>
