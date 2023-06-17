<template>
  <div class="user_container">
    <my-article-header></my-article-header>
    <div class="user_info_box">
      <div class="user_menus">
        <ul>
          <li>个人中心</li>
          <li @click="loginout">退出登录</li>
        </ul>
      </div>

      <user-info :audit="true" :userInfo="user" :currentUser="true">
        <template #default>
          <a-upload
            :file-list="fileList"
            name="avatar"
            list-type="picture-card"
            class="avatar-uploader"
            :show-upload-list="false"
            :headers="headers"
            action="http://admin-api.natapp1.cc/study/add-convert-file/upload"
            :before-upload="beforeUpload"
            @change="handleChange"
          >
            <img
              v-if="imageUrl"
              class="user_pic"
              :src="imageUrl"
              alt="avatar"
            />
            <div v-else>
              <loading-outlined v-if="loading"></loading-outlined>
              <plus-outlined v-else></plus-outlined>
              <div class="ant-upload-text">Upload</div>
            </div>
          </a-upload>
        </template>
      </user-info>
      <div class="release_btn" @click="toRelease">发布</div>
    </div>
    <my-footer></my-footer>
  </div>
</template>

<script setup>
import { reactive, ref, watch, onBeforeUpdate } from "vue";
import userInfo from "@/components/userInfo.vue";
import userRequest from "@/api/user.js";
import { getToken, removeToken } from "@/utils/token";

import { useRouter } from "vue-router";
const router = useRouter();

const fileList = ref([]);
const loading = ref(false);
const imageUrl = ref();
const headers = ref({
  "X-Token": getToken(),
});

//监控上传文件
watch(
  fileList,
  (val) => {
    loadUserInfo();
  },
  { deep: true }
);

//用户信息
const user = ref({});

//加载用户信息
async function loadUserInfo() {
  const res = await userRequest.userInfo(getToken());
  if (res.data === null) {
    router.replace("/login");
  } else {
    if (res.code === 200) {
      user.value = res.data;
      imageUrl.value = res.data.pic;
    }
  }
}

function getBase64(img, callback) {
  const reader = new FileReader();
  reader.addEventListener("load", () => callback(reader.result));
  reader.readAsDataURL(img);
}

const handleChange = (info) => {
  if (info.file.status === "uploading") {
    loading.value = true;
    fileList.value.push(info.file);
    return;
  }
  if (info.file.status === "done") {
    // Get this url from response in real world.
    getBase64(info.file.originFileObj, (base64Url) => {
      imageUrl.value = base64Url;
      loading.value = false;
    });
  }
  if (info.file.status === "error") {
    loading.value = false;
    message.error("upload error");
  }
};

const beforeUpload = (file) => {
  const isJpgOrPng =
    file.type === "image/jpeg" ||
    file.type === "image/png" ||
    file.type === "image/webp";
  if (!isJpgOrPng) {
    message.error("You can only upload JPG file!");
  }
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (!isLt2M) {
    message.error("Image must smaller than 2MB!");
  }
  return isJpgOrPng && isLt2M;
};
function toRelease() {
  router.push("/release");
}

loadUserInfo();

//退出登录
function loginout() {
  userRequest.loginOut();
  removeToken();
  router.push("/login");
}
</script>

<style scoped lang="scss">
.user_container {
  .user_info_box {
    padding: 52px 80px 0 80px;
    display: flex;
    .user_menus {
      text-align: center;
      width: 200px;
      ul {
        width: 200px;
        position: fixed;
        li {
          list-style: none;
          height: 35px;
          line-height: 35px;
          cursor: pointer;
        }
      }
    }
  }
}
</style>