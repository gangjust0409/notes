<template>
  <div class="login_container">
    <div class="login_title">刚刚笔记 - 登录</div>
    <div class="login_box">
      <div class="logon_left">
        <video src="@/assets/images/management.mp4" autoplay></video>
      </div>
      <div class="login_right">
        <a-form
          :model="formState"
          name="basic"
          :label-col="{ span: 8 }"
          :wrapper-col="{ span: 16 }"
          autocomplete="off"
          @finish="onFinish"
          @finishFailed="onFinishFailed"
        >
          <a-form-item
            label="用户名"
            name="userName"
            :rules="[
              { required: true, message: 'Please input your username!' },
            ]"
          >
            <a-input v-model:value="formState.userName" />
          </a-form-item>

          <a-form-item
            label="密码"
            name="password"
            :rules="[
              { required: true, message: 'Please input your password!' },
            ]"
          >
            <a-input-password v-model:value="formState.password" />
          </a-form-item>
          <a-form-item :wrapper-col="{ offset: 12, span: 16 }">
            还没有账号？<router-link
              to="/regist"
              replace
              tag="div"
              style="color: #409eff; cursor: pointer; font-size: 15px"
              >去注册</router-link
            >
          </a-form-item>
          <a-form-item :wrapper-col="{ offset: 8, span: 16 }">
            <a-button type="primary" html-type="submit">登录</a-button>
          </a-form-item>
        </a-form>
      </div>
    </div>
    <my-footer></my-footer>
  </div>
</template>

<script setup>
import { reactive, getCurrentInstance } from "vue";
import { message } from "ant-design-vue";
import user from "@/api/user";
import { setToken } from "@/utils/token";
import { useRouter } from "vue-router";
const { proxy } = getCurrentInstance();
const router = useRouter();

const formState = reactive({
  userName: "",
  password: "",
});
const onFinish = async (values) => {
  const res = await user.login(values);
  if (res.code === 200 && res.data != null) {
    proxy.$message.success("登录成功！");
    setToken(res.data);
    router.replace("/");
  }
};

const onFinishFailed = (errorInfo) => {
  console.log("Failed:", errorInfo);
};
</script>

<style scoped lang="scss">
.login_container {
  .login_title {
    line-height: 50px;
    height: 45px;
    text-align: center;
    font-size: 22px;
    font-weight: 800;
    font-family: monospace;
  }
  .login_box {
    display: flex;
    justify-content: space-around;
    align-items: center;
    .logon_left {
      flex: 1;
    }
    .login_right {
      flex: 1;
      margin-right: 50px;
      :deep(.ant-col-16) {
        max-width: 250px;
      }
    }
  }
}
</style>