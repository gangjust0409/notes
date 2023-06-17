<template>
  <div class="app_container">
    <div class="shenghe_title">{{ article.title }}</div>
    <div class="shenghe_box">
      <div class="sheng-detail">
        <v-md-preview :text="article.content" ref="preview"></v-md-preview>
      </div>
      <div class="sheng-createdate">
        {{ article.releaseDate }}
      </div>
    </div>
    <div class="shenghe_bottom">
      <el-row>
        <el-col :offset="5" :span="10">
          <el-upload
            class="upload-demo"
            drag
            :action="uploadUrl"
            :headers="headers"
            :auto-upload="false"
            ref="uploadRef"
            @on-success="onSuccess"
          >
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">笔记封面<em>点击上传</em></div>
            <template #tip>
              <div class="el-upload__tip">
                jpg/png files with a size less than 500kb
              </div>
            </template>
          </el-upload>
        </el-col>
        <el-col :offset="5" :span="4" class="shenghe_btn">
          <el-button type="primary" :icon="UploadFilled" @click="submit"
            >提交</el-button
          >
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import articleRequest from "../../api/article";
import { ElMessage } from "element-plus";
import { useRouter, useRoute } from "vue-router";
import { UploadFilled } from "@element-plus/icons-vue";
import { getAuth } from "../../utils/auth";

const router = useRouter();
const route = useRoute();

const headers = ref<any>({
  "X-Token": getAuth(),
});
const uploadRef = ref();
const uploadUrl = ref<string>(
  "http://admin-api.natapp1.cc/study/add-convert-file/upload/article"
);

interface ArticleType {
  title: string;
  articleId: number;
  releaseDate: string;
  content: string;
}

const article = ref<ArticleType>({
  title: "",
  articleId: 0,
  releaseDate: "",
  content: "",
});

//加载详情
async function loadDetail() {
  const res = await articleRequest.getArticleById(route.query.articleId);
  article.value = res.data;
}
loadDetail();
async function submit() {
  uploadRef.value.submit();
  const res = await articleRequest.auditArticle(route.query.articleId);
  ElMessage.success("审核成功！");
  setTimeout(() => {}, 1500);
  router.push("/layout/article");
}
//上传成功
function onSuccess() {}
</script>

<style scoped lang="scss">
.app_container {
  margin-bottom: 10px;
  .shenghe_title {
    text-align: center;
    line-height: 30px;
    font-size: 20px;
    font-weight: 600;
  }
  .shenghe_box {
    width: 1060px;
    .sheng-detail {
    }
    .sheng-createdate {
      text-align: right;
      font-weight: 500;
      margin-right: 30px;
    }
  }
  .shenghe_bottom {
    .shenghe_btn {
      display: flex;
      justify-content: end;
      align-items: flex-end;
    }
  }
}
</style>