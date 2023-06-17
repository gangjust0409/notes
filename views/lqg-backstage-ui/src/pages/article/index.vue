<template>
  <div class="app_container">
    <el-row>
      <el-col>
        <el-form :inline="true" :model="params" class="demo-form-inline">
          <el-form-item label="笔记标题">
            <el-input v-model="params.title" placeholder="笔记标题" />
          </el-form-item>
          <el-form-item label="审核状态">
            <el-select v-model="params.type" placeholder="审核状态">
              <el-option label="待审核" value="0" />
              <el-option label="审核成功" value="1" />
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="用户">
            <el-select v-model="params.userId" placeholder="用户">
              <el-option label="Zone one" value="shanghai" />
              <el-option label="Zone two" value="beijing" />
            </el-select>
          </el-form-item> -->
          <el-form-item>
            <el-button type="primary" @click="onSubmit">查询</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row :gutter="30">
      <el-col :span="24">
        <el-table :data="articles" stripe>
          <el-table-column prop="articleId" label="ID" width="80" />
          <el-table-column prop="title" label="title" width="200" />
          <el-table-column prop="userId" label="userId" width="80" />
          <el-table-column prop="type" label="type" width="80" />
          <el-table-column prop="createdDate" label="createdDate" width="180" />
          <el-table-column label="操作" width="200">
            <template #default="scope">
              <!-- 编辑 -->
              <el-button size="small" :icon="Edit" circle />
              <!-- 待审失败 -->
              <el-button
                v-if="scope.row.type === 6"
                type="success"
                size="small"
                :icon="WarnTriangleFilled"
                circle
              />
              <!-- 待审核 -->
              <el-button
                type="primary"
                v-else-if="scope.row.type === 0"
                size="small"
                :icon="Clock"
                @click="onAudit(scope.row)"
                circle
              />
              <!-- 审核成功 -->
              <el-button
                v-else-if="scope.row.type === 1"
                type="success"
                size="small"
                :icon="SuccessFilled"
                circle
              />
              <el-button type="warning" size="small" :icon="View" circle />
              <el-button type="danger" size="small" :icon="Delete" circle />
              <!-- <el-button
                size="small"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)"
                >Delete</el-button
              > -->
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <el-row :gutter="30" style="margin-top: 25px">
      <el-col :offset="16" :span="5">
        <el-pagination
          small
          background
          layout="prev, pager, next"
          :total="total"
          v-model:page-size="params.pageSize"
          class="mt-4"
          @current-change="currentChange"
        />
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import articleRequest from "../../api/article";
import { reactive, ref } from "vue";
import {
  WarnTriangleFilled,
  Delete,
  Edit,
  SuccessFilled,
  Clock,
  View,
} from "@element-plus/icons-vue";
import { useRouter } from "vue-router";

const router = useRouter();

const formInline = reactive({
  userId: 0,
  title: "",
  type: "",
});

const articles = ref([]);
const params = reactive({
  pageNum: 1,
  pageSize: 5,
  userId: "",
  title: "",
  type: "",
});
//总条数
const total = ref(0);

//加载笔记列表
const loadNotes = async () => {
  const res = await articleRequest.loadArticles(params);
  articles.value = res.data.records;
  total.value = res.data.total;
};
loadNotes();
const onSubmit = () => {
  console.log(formInline);
  loadNotes();
};

//点击审核
function onAudit(article: any) {
  router.push({
    path: "/layout/shenghe",
    query: { articleId: article.articleId },
  });
}

//分页
function currentChange(pageNum: number) {
  params.pageNum = pageNum;
  loadNotes();
}
</script>

<style scoped lang="scss">
.app_container {
  margin-bottom: 5px;
}
</style>