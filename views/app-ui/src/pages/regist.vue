<template>
  <div class="login_container">
    <div class="login_title">刚刚笔记 - 注册</div>
    <div class="login_box">
      <div class="logon_left">
        <video src="@/assets/images/management.mp4" autoplay loop></video>
      </div>
      <div class="login_right">
        <a-form
          :model="formState"
          name="basic"
          :label-col="{ span: 8 }"
          :wrapper-col="{ span: 16 }"
          autocomplete="off"
          :rules="rules"
          @finish="onFinish"
          @finishFailed="onFinishFailed"
        >
          <a-form-item label="用户名" name="userName">
            <a-input v-model:value="formState.userName" />
          </a-form-item>

          <a-form-item label="密码" name="password">
            <a-input-password v-model:value="formState.password" />
          </a-form-item>

          <a-form-item :wrapper-col="{ offset: 12, span: 16 }">
            已有账号？<router-link
              to="/login"
              replace
              tag="div"
              style="color: #409eff; cursor: pointer; font-size: 15px"
              >去登录</router-link
            >
          </a-form-item>
          <a-form-item :wrapper-col="{ offset: 8, span: 16 }">
            <a-button type="primary" html-type="submit">注册</a-button>
          </a-form-item>
        </a-form>
      </div>
    </div>
    <my-footer></my-footer>
  </div>
</template>

<script setup>
import { reactive, getCurrentInstance, ref } from "vue";
import { message } from "ant-design-vue";
import user from "@/api/user";
import { setToken } from "@/utils/token";
import { useRouter } from "vue-router";
const { proxy } = getCurrentInstance();
const router = useRouter();
const isForm = ref(false);

const formState = reactive({
  userName: "",
  password: "",
});
const onFinish = async (values) => {
  if (isForm.value) {
    const res = await user.register(values);
    if (res.code === 200) {
      proxy.$message.success("注册成功！");
      router.replace("/login");
    }
  } else {
    proxy.$message.error("注册失败！");
  }
};

const onFinishFailed = (errorInfo) => {
  console.log("Failed:", errorInfo);
};

let validUserName = async (_rule, value) => {
  if (value === "") {
    return Promise.reject("请输入用户名");
  } else {
    //验证用户名
    const res = await user.uniqueUser(value);
    if (res.code == 200) {
      if (res.data == 1) {
        isForm.value = false;
        return Promise.reject("用户名不可用，请重新输入！");
      } else {
        isForm.value = true;
        return Promise.resolve();
      }
    }
  }
};
let validatePass = async (_rule, value) => {
  if (value === "") {
    return Promise.reject("请输入密码");
  } else {
    return Promise.resolve();
  }
};
const rules = {
  userName: [{ required: true, validator: validUserName, trigger: "blur" }],
  password: [{ required: true, validator: validatePass, trigger: "change" }],
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