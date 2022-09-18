<template>
  <div id="robotManage" >
    <div class="box">
      <wwj-page
        class="tm"
        :tableData="tableData"
        :count="count"
        :currPage="search.currPage"
        :limit="search.limit"
        @limitchange="handleSizeChange"
        @pagechange="handleCurrentChange"
      >
        <!-- 以下是搜索框插槽内容 -->
        <template v-slot:search>
          <el-form :inline="true" :model="search" class="demo-form-inline">
            <el-form-item label="操作人">
              <el-input
                v-model="search.adminName"
                placeholder="请输入操作人名"
              ></el-input>
            </el-form-item>
            <el-form-item label="创建时间">
              <el-date-picker
                v-model="search.startTime"
                type="date"
                placeholder="开始日期"
                value-format="yyyy-MM-dd"
              >
              </el-date-picker>
              至
              <el-date-picker
                v-model="search.endTime"
                type="date"
                placeholder="结束日期"
                value-format="yyyy-MM-dd"
              >
              </el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" @click="query"
                >查询</el-button
              >
            </el-form-item>
          </el-form>
        </template>
        <template v-slot:heads>
          <!-- 以下是表头插槽内容 -->
          <el-table-column prop="logId" label="ID" width="100" sortable>
          </el-table-column>
          <el-table-column prop="logUrl" label="请求Url" width="200">
          </el-table-column>
          <el-table-column prop="adminName" label="操作人" width="140">
          </el-table-column>
          <el-table-column label="操作时间" width="300">
            <template slot-scope="scope">
              <span v-html="returnTime(scope.row.gmtCreate)"></span>
            </template>
          </el-table-column>
        </template>
      </wwj-page>
    </div>
  </div>
</template>
<script>
import wwjPage from "../components/WwjPage.vue";
import ajax from "../util/myAxios";
export default {
  name: "RobotManage",
  mounted() {
    this.initLog();
  },
  data() {
    return {
      search: {
        limit: 5,
        currPage: 1, 
        startTime: "",
        endTime: "",
        adminName: "",
      },
      count: 100, // 一共多少条数据
      tableData: [
        //表体数据
      ],
      admins: [],
      // conTop: {
      //   backgroundImage: "url(" + require("../img/加菲头像.jpeg") + ")",
      //   backgroundRepeat: "no-repeat",
      //   backgroundPosition: "center center",
      //   height: "100%",
      //   width: "100%",
      // },
    };
  },
  methods: {
    returnTime(times) {
      var date = new Date(times);
      return date.toLocaleDateString() + " " + date.toLocaleTimeString();
    },

   //初始化表格信息（条件查询日志数据）
    initLog() {
        console.log("currentPage=="+this.search.currPage);
        console.log("limit=="+this.search.limit);
        console.log("adminState=="+this.search.adminState);
        console.log("adminName=="+this.search.adminName);
        console.log("startTime=="+this.search.startTime);
        console.log("endTime=="+this.search.endTime);
        ajax({
            type:'get',
            url:'/selectAdminLog',
            data:{
                adminName:this.search.adminName,
                startTime:this.search.startTime,
                endTime:this.search.endTime,
                currentPage:this.search.currPage,
                limit:this.search.limit,         
            },
            success:(resp)=>{
                console.log('selectAdminLog',resp);
                this.tableData = resp.list;                 
                this.count = resp.count;
                this.totlePage=this.count%this.limit==0 ? this.count/this.limit :parseInt(this.count/this.limit+1);
               
            }
        })
    },
    //查询按钮查询信息
    query() {
      this.search.currPage=1;
      this.initLog(); 
    },
   //分页变化
    handleSizeChange(limit) {
        console.log('limit尺寸发生改变' + limit);
        this.search.limit=limit;
        this.initLog();
    },
    handleCurrentChange(currentPage) {
        console.log('当前页面发生改变了' + currentPage);
        this.search.currPage=currentPage;
        this.initLog();
    },
  },
  components: {
    wwjPage,
  },
};
</script>
<style lang="less" scoped>
.tm {
  opacity: 0.9;
}
</style>
