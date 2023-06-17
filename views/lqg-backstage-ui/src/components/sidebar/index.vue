<template>
  <Transition name="slide-fade">
    <div
      class="lqg-menu-container"
      style="background-color: #545c64; color: #fff"
    >
      <div class="logo">
        <img src="@/assets/vite.svg" alt="vite logo" />
        <div class="logo_tit" v-show="!isCollapse">LQG后台管理系统</div>
      </div>
      <div class="menus">
        <el-menu
          active-text-color="#ffd04b"
          background-color="#545c64"
          class="el-menu-vertical-demo"
          default-active="2"
          text-color="#fff"
          :collapse="isCollapse"
        >
          <!-- 一层 -->
          <el-menu-item
            v-for="route in list"
            :key="route.path"
            :index="route.path"
            v-show="route.children.length == 0"
            @click="openRoute(route)"
          >
            <el-icon><Component :is="route.icon"></Component></el-icon>
            <span>{{ route.title }}</span>
          </el-menu-item>
          <!-- 二层 -->
          <el-sub-menu
            v-for="route in list"
            :key="route.title"
            :index="route.path"
            v-show="route.children.length > 0"
          >
            <template #title>
              <el-icon><Component :is="route.icon"></Component></el-icon>
              <span>{{ route.title }}</span>
            </template>
            <el-menu-item-group>
              <el-menu-item
                v-for="e in route.children"
                :key="e.path"
                :index="e.path"
                @click="openRoute(e)"
              >
                <el-icon><Component :is="e.icon"></Component></el-icon>
                <span>{{ e.title }}</span>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <!-- 三层 -->
          <!-- <el-sub-menu index="3">
            <template #title>
              <el-icon><IEplocation /></el-icon>
              <span>笔记系统</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="3-1">
                <el-icon><IEpsetting /></el-icon>
                <span>笔记列表</span>
              </el-menu-item>
              <el-menu-item index="3-2">
                <el-icon><IEpsetting /></el-icon>
                <span>笔记列表</span></el-menu-item
              >
              <el-sub-menu index="3-4">
                <template #title>
                  <el-icon><IEpsetting /></el-icon>
                  <span>三级菜单</span>
                </template>
                <el-menu-item index="3-4-1">
                  <el-icon><IEpsetting /></el-icon>
                  <span>笔记列表</span>
                </el-menu-item>
              </el-sub-menu>
            </el-menu-item-group>
          </el-sub-menu> -->
        </el-menu>
      </div>
    </div>
  </Transition>
</template>
<script lang="ts" setup>
import { defineProps, ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

interface Props {
  isCollapse: boolean;
  list?: any;
}

const { list } = defineProps<{ isCollapse: boolean; list: any }>();
// const props = withDefaults(defineProps<Props>(), {
//   isCollapse: true,
//   labels: () => ["one", "two"],
// });

let erji = ref(false);

let erjifl = ref(true);

function zhankai() {
  erji.value = !erji.value;
}

function overMenuBox() {
  erjifl.value = true;
}

function toMenu() {
  router.push("/home/article");
}

function openRoute(route: any) {
  router.push(route.path);
}
</script>
<style lang="scss" scoped>
:deep(.el-menu) {
  border-right: 0;
}
</style>