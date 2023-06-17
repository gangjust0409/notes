<template>
  <div>
    <div class="top_meesage">
      <div class="fiexd">
        <Transition name="ming">
          <div v-if="ming === 'ming'">名人名言：书籍是人类进步的阶梯。</div>
          <div v-else-if="ming === 'yan'">名言：书是人类的长生果。</div>
          <div v-else-if="ming === 'u'">今日天气晴朗，适合玩耍。</div>
          <div v-else-if="ming === 'i'">每天都要开开心心的哦。</div>
        </Transition>
      </div>
      <el-icon style="cursor: pointer; position: absolute; right: 5px">
        <IEpCircleClose />
      </el-icon>
    </div>
    <div class="lqg_layout">
      <el-container>
        <div class="asid" :style="{ width: logw + 'px' }">
          <el-aside class="lqg-aside" :style="{ width: logw + 'px' }">
            <sidebar :isCollapse="isCollapse" :list="store.getterMenus" />
          </el-aside>
        </div>
        <section class="app-continer">
          <header class="app-header">
            <div>
              <el-icon @click="toggleShuo" v-show="!isCollapse">
                <IEpFold />
              </el-icon>
              <el-icon @click="toggleShuo" v-show="isCollapse">
                <IEpExpand />
              </el-icon>
            </div>
            <div class="user-info">
              <div class="fengche">
                <img src="@/assets/images/fengche.jpeg" alt="" />
              </div>
              <div class="pic">
                <el-dropdown>
                  <span class="user_name">
                    <img
                      :src="userInfo.pic == null ? '' : userInfo.pic"
                      :alt="userInfo.userName"
                    /><span>{{ userInfo.userName }}</span>
                    <el-icon class="el-icon--right">
                      <arrow-down />
                    </el-icon>
                  </span>
                  <template #dropdown>
                    <el-dropdown-menu>
                      <el-dropdown-item>个人信息</el-dropdown-item>
                      <el-dropdown-item>修改密码</el-dropdown-item>
                      <el-dropdown-item @click="loginOut"
                        >退出登录</el-dropdown-item
                      >
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </div>
              <div class="setting">
                <el-icon>
                  <IEpSetting />
                </el-icon>
              </div>
            </div>
          </header>
          <!-- <section class="narbar">
            <div class="left">
              <el-icon>
                <IEpArrowLeft />
              </el-icon>
            </div>
            <div class="narbar-box">
              <div class="narbar-item">
                <span>菜单管理菜单管理</span>
                <el-icon>
                  <IEpCircleClose />
                </el-icon>
              </div>
              <div class="narbar-item">
                <span>菜单管理菜单管理</span>
                <el-icon>
                  <IEpCircleClose />
                </el-icon>
              </div>
              <div class="narbar-item">
                <span>菜单管理菜单管理</span>
                <el-icon>
                  <IEpCircleClose />
                </el-icon>
              </div>
              <div class="narbar-item">
                <span>菜单管理</span>
                <el-icon>
                  <IEpCircleClose />
                </el-icon>
              </div>
              <div class="narbar-item">
                <span>菜单管理菜单管理</span>
                <el-icon>
                  <IEpCircleClose />
                </el-icon>
              </div>
              <div class="narbar-item">
                <span>菜单管理</span>
                <el-icon>
                  <IEpCircleClose />
                </el-icon>
              </div>
              <div class="narbar-item">
                <span>菜单管理</span>
                <el-icon>
                  <IEpCircleClose />
                </el-icon>
              </div>
              <div class="narbar-item">
                <span>菜单管理</span>
                <el-icon>
                  <IEpCircleClose />
                </el-icon>
              </div>
            </div>
            <div class="right">
              <el-icon>
                <IEpArrowRight />
              </el-icon>
            </div>
          </section> -->
          <section class="app-box">
            <RouterView v-slot="{ Component }">
              <Transition name="slide-fade">
                <component :is="Component" />
              </Transition>
            </RouterView>
          </section>
          <footer class="app-footer">
            &copy;本站使用于作者写的所有的网站后台系统
          </footer>
          <div class="time">
            <div>{{ week }}</div>
            <div>{{ time }}</div>
          </div>
        </section>
      </el-container>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { getAuth, removeAuth } from "../utils/auth";
import userRequest from "../api/user";
import { ref } from "vue";
import { useRouter } from "vue-router";
import sidebar from "../components/sidebar/index.vue";
import menuStore from "../store/menu";

const isCollapse = ref<boolean>(false);
const router = useRouter();
const store = menuStore();

const userInfo = ref({
  pic: null,
  userName: "",
});
let logw = ref<Number>(210);

function toggleShuo() {
  isCollapse.value = !isCollapse.value;
  logw.value = isCollapse.value === true ? 63 : 210;
}

const time = ref<String>();
let week = ref<string>();

// 用于控制名言名言展示
let ming = ref<string | undefined>("ming");

//时间
function loadCurrentTime() {
  const date = new Date();
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDay();
  const dat = date.getDate();
  let hours = date.getHours();
  let minutes = date.getMinutes();
  let seconds = date.getSeconds();
  //进行处理
  hours = handlerLing(hours);
  minutes = handlerLing(minutes);
  seconds = handlerLing(seconds);
  week.value = handlerWeek(day);

  return (time.value =
    year +
    "年" +
    month +
    "月" +
    dat +
    " " +
    hours +
    "时" +
    minutes +
    "分" +
    seconds +
    "秒");
}
setInterval(loadCurrentTime, 1000);

setInterval(() => {
  ming.value = re();
}, 3000);

function re() {
  if (ming.value === "ming") {
    return "yan";
  } else if (ming.value === "yan") {
    return "u";
  } else if (ming.value === "u") {
    return "i";
  } else if (ming.value === "i") {
    return "ming";
  }
}

//处理时间格式
function handlerLing(val: any) {
  if (val < 10) {
    return "0" + val;
  }
  return val;
}

function handlerWeek(val: number) {
  switch (val) {
    case 1:
      return "星期一";
    case 2:
      return "星期二";
    case 3:
      return "星期三";
    case 4:
      return "星期四";
    case 5:
      return "星期五";
    case 6:
      return "星期六";
    case 7:
      return "星期日";
  }
}

async function loadUserInfo() {
  const res = await userRequest.userInfo(getAuth() as string);
  userInfo.value = res.data;
}

async function loginOut() {
  const res = await loginOut();
  removeAuth();
  router.push("/login");
}

loadUserInfo();
</script>

<style lang="scss" scoped>
</style>