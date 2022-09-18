<template>
  <div id="proposalManage" >
    <wwj-page
       style=" opacity: 0.95"
      :tableData="tableData"
      :count="count"
      :currPage="search.currPage"
      :limit="search.limit"
      @limitchange="handleSizeChange"
      @pagechange="handleCurrentChange"
    >
      <!-- 以下是搜索框插槽内容 -->
      <template v-slot:search>
        <el-form :inline="true"  class="demo-form-inline">
          <el-form-item label="提案人">
            <el-input
              v-model="search.proposalPerson"
              placeholder="请输入提案人"
            ></el-input>
          </el-form-item>
          <el-form-item label="审核状态">
            <el-select  v-model="search.proposalCheck"  clearable  placeholder="请选择提案状态"  >
              <el-option
                v-for="item in check"
                :key="item.typeId"
                :label="item.typeName"
                :value="item.typeId"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="据点" >
            <el-select
              v-model="search.companyId"
              clearable
              placeholder="请选择据点"
              @change="selectDepartmentByCompanyId"
            >
              <el-option
                v-for="item in company"
                :key="item.companyId"
                :label="item.companyAlias"
                :value="item.companyId"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="部门">
            <el-select
              v-model="search.departmentId"
              clearable
              placeholder="请选择部门"
            >
              <el-option
                v-for="item in department"
                :key="item.departmentId"
                :label="item.departmentName"
                :value="item.departmentId"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="起案日">
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
            <el-button type="primary" @click="query">查询</el-button>
            <!-- 导出Excel -->            
            <download-excel
              style="margin-left:90px;margin-top:-40px;"
              :data = "excelData"
              :fields="exportExcel"
              name = "报表.xls">
              <el-button type="primary"  >导出Excel</el-button>
            </download-excel>
          </el-form-item>
        </el-form>
      </template>
      <!-- 以下是表头插槽内容 -->
      <template v-slot:heads> 
            
         <el-table-column prop="proposalCode" label="提案编号" width="180" sortable>
        </el-table-column>
        <el-table-column prop="proposalPerson" label="提案人" width="180">
        </el-table-column>
       <el-table-column label="审核状态" width="180">
          <template slot-scope="scope">
            <span
              style="margin-left: 10px"
              v-html="returnState(scope.row.proposalCheck)"
            ></span>
          </template>
        </el-table-column>
        <el-table-column prop="companyAlias" label="据点" width="180">
        </el-table-column>
        <el-table-column prop="departmentName" label="部门" width="180">
        </el-table-column>
        <el-table-column label="起案日" width="180"  >
          <template slot-scope="scope" >
            <span
              style="margin-left: 10px"
              v-html="returnTime(scope.row.gmtCreate)"
            ></span>
          </template>
        </el-table-column>  
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" @click="getDetail(scope.row)">明细</el-button>
            <el-button size="mini" type="primary" @click="getImage(scope.row)">改善前图片</el-button>
            <el-button size="mini" type="primary" @click="getImage2(scope.row)">改善后图片</el-button>
            <el-button size="mini" type="success"  v-if="checkBtn" @click="checkProposal(scope.row)">审核</el-button>
            <el-button size="mini" type="danger" v-if="delBtn" @click="delProposal(scope.row)">删除</el-button>   
            <el-button size="mini"  type="success" @click="getPrint(scope.row)" v-print="'#printTest'">打印</el-button>
          </template>
        </el-table-column>
      </template>
    </wwj-page>
    <div  id="printTest" v-show="printVisible" >  
      <el-descriptions title="GCWE提案书"  :column="2" border >
        <el-descriptions-item label="提案编号" label-class-name="my-label" content-class-name="my-content">{{detail.proposalCode}}</el-descriptions-item>
        <el-descriptions-item label="起案日">{{detail.gmtCreate}}</el-descriptions-item>
        <el-descriptions-item label="提案人">{{detail.proposalPerson}}</el-descriptions-item>
        <el-descriptions-item label="案件名">{{detail.proposalName}}</el-descriptions-item>
        <el-descriptions-item label="据点名">{{detail.companyAlias}}</el-descriptions-item>
        <el-descriptions-item label="部门">{{detail.departmentName}}</el-descriptions-item> 
        <el-descriptions-item label="改善前" :contentStyle="{'size': '30px'}">{{detail.beforeImprovement}}</el-descriptions-item>
        <el-descriptions-item label="改善后">{{detail.afterImprovement}}</el-descriptions-item>
        <el-descriptions-item label="月度效应值">{{detail.saveMoney}}元</el-descriptions-item>
        <el-descriptions-item label="月节省时间">{{detail.saveTime}}小时</el-descriptions-item>      
      </el-descriptions> 
    </div>

    <!-- 明细 -->   
    <el-dialog title="明细" :visible.sync="detailVisible" width="40%" >
      <el-descriptions :column="1" border id="main">
        <el-descriptions-item label="案件名">{{detail.proposalName}}</el-descriptions-item>
        <el-descriptions-item label="改善前">{{detail.beforeImprovement}}</el-descriptions-item>
        <el-descriptions-item label="改善后">{{detail.afterImprovement}}</el-descriptions-item>
        <el-descriptions-item label="月度效应值">{{detail.saveMoney}}元</el-descriptions-item>
        <el-descriptions-item label="月节省时间">{{detail.saveTime}}小时</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
   
    <!-- 图片 -->
    <el-dialog title="图片" :visible.sync="imageVisible" width="35%">
      <div class="demo-image">
        <!-- <div class="block" v-for="fit in fits" :key="fit">
          <span class="demonstration">{{ fit }}</span> -->
          <el-image
            style="width: 500px; height: 500px"
            :src="Imgpath"
            ></el-image>
        <!-- </div> -->
      </div>
    </el-dialog>
     <!-- 审核 -->
    <el-dialog title="审核提案"  :visible.sync="checkVisible"  width="30%">
      <!-- <span>审核提案</span> -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="passCheck">通过</el-button>
        <el-button  @click="failCheck">未通过</el-button>
      </span>
    </el-dialog>
   
  </div>
</template>
<script>
import SessionTools from "../util/SessionTools";
import ajax from "../util/myAxios";
import wwjPage from '../components/WwjPage.vue';
export default {
  name: "ProposalManage",
  
  mounted() {
    if(SessionTools.getUser().roleId==11||SessionTools.getUser().roleId==1){
      this.delBtn=true;
    }
    if(SessionTools.getUser().roleId==11){
      this.checkBtn=false;
    }
    this.check = SessionTools.getTypeName4Dict2("check");
    this.getAllCompany();
    this.initProposal();
    this.selectExcelData();
  },
  data() {
    
    return {
      // conTop: {
      //     backgroundImage:'url(' + require('../img/加菲猫背景2.jpg') + ')',
      //     width:"100%",
      //     height:"100%",
      // },
      delBtn:false,
      checkBtn:true,
      // fits: ['fill', 'contain', 'cover', 'none', 'scale-down'],
      Imgpath:"",
      exportExcel:{   
          '编号':'proposalCode', 
          '案件名':'proposalName',
          '提案人':'proposalPerson',
          '审核状态':'stateName', 
          '据点':'companyAlias',
          '部门':'departmentName', 
          '起案日':'gmtCreate',          
          '改善前':'afterImprovement',     
          '改善后':'beforeImprovement', 
          '月度效应值':'saveMoney',
          '月节省时间':'saveTime', 
          '改善前图片信息':'proposalImage', 
          '改善后图片信息':'proposalImage2',         
      },
      search: {
        limit: 5,
        start: 0, 
        currPage: 1,    
        proposalCheck: "",
        proposalPerson: "",
        startTime: "",
        endTime: "",
        companyId:"",
        departmentId:'',
        proposalId:'',
      },
      detail:{
        proposalName:'',
        beforeImprovement:'',
        afterImprovement:'',
        saveMoney:'',
        saveTime:'',
        //打印内容需要
        proposalCode:'',
        gmtCreate:'',
        proposalPerson:'',
        departmentName:'',
        companyAlias:'',
      },
      rules: {
        proposalPerson: [
          { required: true, message: "请输入姓名", trigger: "blur" },
          { min: 3, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" },
        ],
        proposalCode: [{ required: true, message: "请选择角色", trigger: "change" }],
      },

      count: 100,
      excelData:[],
      tableData: [],
      company: [],
      department:[],
      check: [],
      detailVisible: false,
      checkVisible:false,
      imageVisible:false,
      printVisible:true,
    };
  },
  methods: {
    printProposal(item){
      console.log(item.proposalId);
      this.detailVisible = true;
    },
    //获取改善前图片
    getImage(formName){
      this.imageVisible=true;
      if (formName.proposalImage!= "") {
          this.Imgpath =
            this.$store.state.baseURL +
            "showImg?imgPath=" +
            formName.proposalImage;
        } else {
          this.Imgpath =
            this.$store.state.baseURL +
            "showImg?imgPath=" +
            "福光logo.png";
        }
    },
    //获取改善后图片
    getImage2(formName){
      this.imageVisible=true;
      if (formName.proposalImage2!= "") {
          this.Imgpath =
            this.$store.state.baseURL +
            "showImg?imgPath=" +
            formName.proposalImage2;
        } else {
          this.Imgpath =
            this.$store.state.baseURL +
            "showImg?imgPath=" +
            "福光logo.png";
        }
    },
    //删除提案   
    delProposal(formName){
        if(confirm("你确定删除吗？")){
          ajax({
            url: "/delProposal",
            type: "post",
            data: {
              proposalId:formName.proposalId,
            },
            success: (resp) => {
              console.log('删除',resp);
              this.$message({
                message: resp.message,
                type: 'success'
              });
              // this.$message(resp.message);
              this.initProposal();
            },
          });
        }     
    },
    //审核
    checkProposal(formName) {
        console.log(formName);
        if(formName.proposalCheck==0){
          this.checkVisible=true;
          this.proposalId=formName.proposalId;
        }else{
          this.$message({
            message: '只能审核待审核的提案',
            type: 'warning'
          });
        }                      
    },
    //审核通过
    passCheck(){
      ajax({
        url: "/passCheck",
        type: "post",
        data: {
          proposalId:this.proposalId,
        },
        success: (resp) => {
          console.log('审核通过',resp);
          this.checkVisible=false;
          this.$message({
            message: resp.message,
            type: 'success'
          });
          this.initProposal();
        },
      });
    },
    //审核不通过
    failCheck(){
      ajax({
        url: "/failCheck",
        type: "post",
        data: {
          proposalId:this.proposalId,
        },
        success: (resp) => {
          console.log('审核不通过',resp);
          this.checkVisible=false;
           this.$message({
              message: resp.message,
              type: 'warning'
            });
          this.initProposal();
        },
      });
    },

    //获取所有据点
    getAllCompany() {
      ajax({
        url: "/getAllCompany",
        type: "get",
        data: {},
        success: (resp) => {
          this.company = resp.list;
        },
      });
    },
    //通过据点id获取部门
    selectDepartmentByCompanyId() {
      ajax({
        url: "/selectDepartmentByCompanyId",
        type: "get",
        data: {
          companyId:this.search.companyId,
        },
        success: (resp) => {
          this.department = resp.list;
        },
      });
    },
    //初始化表格信息（条件查询提案数据）
    initProposal() {
        console.log("currentPage=="+this.search.currPage);
        console.log("limit=="+this.search.limit);
        console.log("proposalCheck=="+this.search.proposalCheck);
        console.log("startTime=="+this.search.startTime);
        console.log("endTime=="+this.search.endTime);
        ajax({
            type:'get',
            url:'/selectProposal',
            data:{
                proposalPerson:this.search.proposalPerson,
                proposalCheck:this.search.proposalCheck,
                companyId:this.search.companyId,
                departmentId:this.search.departmentId,
                startTime:this.search.startTime,
                endTime:this.search.endTime,
                currentPage:this.search.currPage,
                limit:this.search.limit,
           
            },
            success:(resp)=>{
                console.log('selectProposal',resp);
                this.tableData = resp.list;                 
                this.count = resp.count;
                this.totlePage=this.count%this.limit==0 ? this.count/this.limit :parseInt(this.count/this.limit+1);
               
            }
        })
    },
    //查询excel表数据（所有提案数据）
    selectExcelData(){
      ajax({
            type:'get',
            url:'/selectExcelData',
            data:{           
            },
            success:(resp)=>{
                console.log('selectExcelData',resp);
                this.excelData = resp.list;                                
            }
        })
    },
    //查询按钮查询信息
    query() {
      this.search.currPage=1;
      this.initProposal(); 
    },
    //获取明细
    getDetail(item) {
      // console.log(item);
      this.detailVisible = true;
      this.detail.beforeImprovement = item.beforeImprovement;
      this.detail.afterImprovement = item.afterImprovement;
      this.detail.saveMoney = item.saveMoney;
      this.detail.saveTime = item.saveTime;
      this.detail.proposalName = item.proposalName;
    },
    //获取打印内容
    getPrint(item) {
      // console.log(item);
      this.printVisible = true;
      this.detail.beforeImprovement = item.beforeImprovement;
      this.detail.afterImprovement = item.afterImprovement;
      this.detail.saveMoney = item.saveMoney;
      this.detail.saveTime = item.saveTime;
      this.detail.proposalName = item.proposalName;
      this.detail.companyAlias=item.companyAlias;
      this.detail.departmentName=item.departmentName;
      this.detail.proposalCode=item.proposalCode;
      this.detail.gmtCreate=item.gmtCreate;
      this.detail.proposalPerson=item.proposalPerson;
    },
   //分页变化
    handleSizeChange(limit) {
        console.log('limit尺寸发生改变' + limit);
        this.search.limit=limit;
        this.getAllExpense();
    },
    handleCurrentChange(currentPage) {
        console.log('当前页面发生改变了' + currentPage);
        this.search.currPage=currentPage;
        this.getAllExpense();
    },
    //返回日期字符串    
    returnTime(time2) {
      var time = new Date(time2);
      return time.toLocaleDateString();
    },
    //根据状态去字典表查询状态名
    returnState(stateId) {
      return SessionTools.getTypeName4Dict("check", stateId);
    },
    
  },
  components: {
    wwjPage,
  },
};
</script>
<style lang="less" scoped>
  .proposalImage {
    align-content: center;
    margin-left: 20%;
    margin-top: 20px;
  }
  .my-label {
    background: #E1F3D8;
  }

  .my-content {
    background: #FDE2E2;
  }
</style>
