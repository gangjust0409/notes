<template>
  <div class="login_container">
    <div class="login_box">
      <div class="left">
        <video src="@/assets/images/management.mp4" autoplay loop></video>
      </div>
      <div class="right">
        <div class="login_title">刚刚笔记后台登录</div>
        <el-form
          ref="ruleFormRef"
          :model="ruleForm"
          status-icon
          :rules="rules"
          label-width="120px"
          class="demo-ruleForm"
        >
          <el-form-item label="用户名" prop="userName">
            <el-input v-model="ruleForm.userName" autocomplete="off" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input
              v-model="ruleForm.password"
              type="password"
              autocomplete="off"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm(ruleFormRef)"
              >登录</el-button
            >
            <!-- <el-button @click="resetForm(ruleFormRef)">Reset</el-button> -->
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import type { FormInstance } from "element-plus";
import userRequest from "../api/user";
import { setAuth } from "../utils/auth";
import { ElMessage } from "element-plus";

const ruleFormRef = ref<FormInstance>();
const router = useRouter();

interface resInter {
  code: number;
  data: Object;
}

const validatePassword = (rule: any, value: any, callback: any) => {
  if (value === "") {
    callback(new Error("请输入密码"));
  } else {
    callback();
  }
};
const validateUserName = (rule: any, value: any, callback: any) => {
  if (value === "") {
    callback(new Error("请输入用户名"));
  } else {
    callback();
  }
};

const ruleForm = reactive({
  password: "",
  userName: "",
});

const rules = reactive({
  password: [{ validator: validatePassword, trigger: "blur" }],
  userName: [{ validator: validateUserName, trigger: "blur" }],
});

const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate(async (valid) => {
    if (valid) {
      const res = await userRequest.login(ruleForm);
      if (res.data !== null) {
        ElMessage({
          message: "登录成功",
          grouping: true,
          type: "success",
        });
        setAuth(res.data);
        router.push("/layout/dashbard");
      } else {
        ElMessage({
          message: "登录失败",
          grouping: true,
          type: "error",
        });
      }
    } else {
      return false;
    }
  });
};

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
};
</script>

<style lang="scss" scoped>
.login_container {
  .login_box {
    display: flex;
    .left {
      flex: 1;
    }
    .right {
      display: flex;
      flex-direction: column;
      flex: 1;
      margin: 0 20px;
      justify-content: center;
      text-align: center;
      padding: 0 150px;
      .login_title {
        margin-bottom: 35px;
      }
    }
  }
}
</style>