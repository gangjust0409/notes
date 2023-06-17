<template>
  <div class="add_container">
    <my-article-header></my-article-header>
    <div class="edit_article">
      <div class="article_title_input">
        <a-form
          name="basic"
          :label-col="{ span: 8 }"
          :wrapper-col="{ span: 16 }"
          autocomplete="off"
        >
          <a-form-item label="文章标题" name="articleTitle">
            <a-input v-model:value="form.title" />
          </a-form-item>
          <!-- <a-form-item label="文章类型">
            <a-tree-select
              v-model:value="form.catalogId"
              show-search
              style="width: 100%"
              :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
              placeholder="Please select"
              allow-clear
              tree-default-expand-all
              :tree-data="treeData"
            >
            </a-tree-select>
          </a-form-item> -->
        </a-form>
      </div>
      <a-layout>
        <a-layout>
          <a-layout-content>
            <v-md-editor v-model="form.content" height="400px"></v-md-editor>
            <div class="upload_file">
              <!-- 
              

             -->
              <a-upload
                v-model:file-list="fileList"
                action="http://admin-api.natapp1.cc/study/add-convert-file/mulit/upload"
                list-type="picture-card"
                :multiple="true"
                :maxCount="100"
                :headers="headers"
                @change="handleChange"
                @preview="handlePreview"
              >
                <div v-if="fileList.length < 100">
                  <plus-outlined />
                  <div style="margin-top: 8px">Upload</div>
                </div>
              </a-upload>
              <a-modal
                :visible="previewVisible"
                :title="previewTitle"
                :footer="null"
                @cancel="handleCancel"
              >
                <img alt="example" style="width: 100%" :src="previewImage" />
              </a-modal>
              <div class="img_title">上传后的图片可以作为笔记图片：</div>
              <div class="imgs">
                <div class="img_item" v-for="item in files" :key="item">
                  <img :src="item" alt="" />
                  <div class="diabox">
                    <copy-outlined
                      style="font-size: 20px; color: #fff; display: none"
                      @click="handleCopy(item)"
                    />
                    <delete-outlined
                      style="font-size: 20px; color: #fff; display: none"
                      @click="handleDelete(item)"
                    />
                  </div>
                </div>
              </div>
            </div>
          </a-layout-content>
        </a-layout>
      </a-layout>
      <div class="release_btn_add" @click="release">
        <a-button type="primary" shape="round" size="10">
          <template #icon>
            <cloud-upload-outlined />
          </template>
          发布
        </a-button>
      </div>
    </div>
    <my-footer></my-footer>
  </div>
</template>

<script setup>
import { reactive, ref, toRaw, getCurrentInstance, watch } from "vue";
import articleRequest from "@/api/article";
import { useRouter } from "vue-router";
import useClipboard from "vue-clipboard3";
import { getToken } from "@/utils/token";

const router = useRouter();
const { toClipboard } = useClipboard();
const { proxy } = getCurrentInstance();

const treeData = reactive([
  {
    title: "parent 1",
    value: "parent 1",
    children: [
      {
        title: "parent 1-0",
        value: "parent 1-0",
        children: [
          {
            title: "my leaf",
            value: "leaf1",
          },
          {
            title: "your leaf",
            value: "leaf2",
          },
        ],
      },
      {
        title: "parent 1-1",
        value: "parent 1-1",
      },
    ],
  },
]);

const previewVisible = ref(false);
const previewImage = ref("");
const previewTitle = ref("");

const headers = ref({
  "X-Token": getToken(),
});

const form = reactive({
  title: "",
  catalogId: "",
  content: "",
});
async function release() {
  const res = await articleRequest.saveArticle(toRaw(form));
  if (res.code === 200) {
    proxy.$message.success("发布成功");
    setTimeout(() => {}, 15000);
    router.replace({ path: "/user/center" });
  }
}

function getBase64(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = (error) => reject(error);
  });
}

//返回文件列表
const fileList = ref([]);
const files = ref([]);
//取消
const handleCancel = () => {
  previewVisible.value = false;
  previewTitle.value = "";
};
//预览
const handlePreview = async (file) => {
  if (!file.url && !file.preview) {
    file.preview = await getBase64(file.originFileObj);
  }
  previewImage.value = file.url || file.preview;
  previewVisible.value = true;
  previewTitle.value =
    file.name || file.url.substring(file.url.lastIndexOf("/") + 1);
};
//改变时触发
async function handleChange(info) {
  console.log(info);
  if (info.file.status === "done") {
    fileList.value.push(info.file);
  }
}
watch(
  fileList,
  () => {
    loadPreviewImage();
  },
  { deep: true, immediate: true }
);

async function loadPreviewImage() {
  const res = await articleRequest.previewImages();
  // fileList.value.length = 0;
  files.value = res.data;
}

//复制
async function handleCopy(url) {
  try {
    await toClipboard(url);
    proxy.$message.success("复制成功");
  } catch (e) {
    proxy.$message.warning("您的浏览器不支持复制：", e);
  }
}
//删除图片
async function handleDelete(url) {
  const res = await articleRequest.deletePreviewImage(
    url.substring(url.lastIndexOf("/") + 1)
  );
  if (res.code === 200) {
    loadPreviewImage();
  }
}
</script>

<style lang="scss" scoped>
.add_container {
  .edit_article {
    padding-top: 43px;
    padding-left: 25px;
    padding-right: 15px;
    .article_title_input {
      width: 520px;
      margin: 0 auto;
      margin-bottom: 20px;
    }
    .release_btn_add {
      float: right;
      margin-right: 12px;
      margin-top: 20px;
    }
  }
  .upload_file {
    padding: 20px;
  }
  .img_title {
    font-size: 20px;
    margin: 10px 0;
  }
  .imgs {
    display: flex;
    flex-wrap: wrap;
    .img_item {
      width: 200px;
      height: 150px;
      padding: 3px;
      border: 3px solid #ccc;
      border-radius: 5px;
      margin: 5px 10px;
      position: relative;
      img {
        width: 100%;
        height: 100%;
        z-index: 0;
      }
      .diabox {
        width: 100%;
        height: 100%;
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        text-align: center;
        padding: 50px 0;
        cursor: pointer;
        &:hover {
          background-color: rgba(0, 0, 0, 0.7);
          z-index: 9099;
          span {
            display: inline-block !important;
            margin: 0 10px;
          }
        }
      }
    }
  }
}
</style>