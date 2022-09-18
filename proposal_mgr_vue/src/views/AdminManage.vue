<template>
  <div id="adminManage" >
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
          <el-form-item label="用户">
            <el-input
              v-model="search.adminName"
              placeholder="请输入用户名"
            ></el-input>
          </el-form-item>
          <el-form-item label="用户状态">
            <el-select  v-model="search.adminState"  clearable  placeholder="请选择用户状态"  >
              <el-option
                v-for="item in state"
                :key="item.typeId"
                :label="item.typeName"
                :value="item.typeId"
              >
              </el-option>
            </el-select>
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
            <el-button type="primary" @click="query">查询</el-button>
            <el-button type="primary" @click="add">新增</el-button>

          </el-form-item>
        </el-form>
      </template>
      <!-- 以下是表头插槽内容 -->
      <template v-slot:heads>             
          
        <el-table-column prop="adminName" label="姓名" width="180">
        </el-table-column>
        <el-table-column prop="adminAccount" label="账号" width="180">
        </el-table-column>
        <el-table-column prop="adminTel" label="电话" width="180">
        </el-table-column>
        <el-table-column label="角色" width="180">
          <template slot-scope="scope">
            <span
              style="margin-left: 10px"
              v-html="scope.row.roleName"
            ></span>
          </template>
        </el-table-column>
        <el-table-column label="用户状态" width="180">
          <template slot-scope="scope">
            <span
              style="margin-left: 10px"
              v-html="returnState(scope.row.adminState)"
            ></span>
          </template>
        </el-table-column>
   
      
        <el-table-column label="创建时间" width="180"  >
          <template slot-scope="scope" >
            <span
              style="margin-left: 10px"
              v-html="returnTime(scope.row.gmtCreate)"
            ></span>
          </template>
        </el-table-column>  

        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" type="danger" @click="del(scope.row)">删除</el-button>
            <el-button size="mini" type="success" @click="set(scope.row)">修改</el-button>
          </template>
        </el-table-column>
      </template>
    </wwj-page>
    <!-- 新增用户信息弹窗 -->
    <el-dialog
      title="新增用户信息"
      :visible.sync="addDialogVisible"
      width="20%"
    >
      <el-form
        :model="addAdmin"
        :rules="rules"
        ref="addAdmin"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="姓名" prop="adminName">
          <el-input v-model="addAdmin.adminName"></el-input>
        </el-form-item>
        <el-form-item label="账号" prop="adminAccount">
          <el-input v-model="addAdmin.adminAccount"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="adminPwd">
          <el-input
            type="password"
            v-model="addAdmin.adminPwd"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="adminPwdAgin">
          <el-input
            type="password"
            v-model="addAdmin.adminPwdAgin"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="roleId">
          <el-select v-model="addAdmin.roleId" placeholder="请选择角色">
            <el-option
              v-for="item in roles"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话" prop="adminTel">
          <el-input v-model="addAdmin.adminTel"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="applyAdd('addAdmin')"
            >立即创建</el-button
          >
          <el-button @click="exitAddForm('addAdmin')">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  <el-dialog title="修改用户信息" :visible.sync="setDialogVisible" width="20%">

       <el-form
        :model="setAdmin"
        :rules="rules"
        ref="setAdmin"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="用户名" prop="adminName">
          <el-input v-model="setAdmin.adminName"></el-input>
        </el-form-item>

        <el-form-item label="状态" prop="adminState">
          <el-select v-model="setAdmin.adminState" placeholder="请选择状态">
            <el-option
              v-for="item in state"
              :key="item.typeId"
              :label="item.typeName"
              :value="item.typeId"
            >
            </el-option>
          </el-select>
        </el-form-item>

         <el-form-item label="角色" >
          <el-select v-model="setAdmin.roleId" placeholder="请选择角色">
            <el-option
              v-for="item in roles"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话" prop="adminTel">
          <el-input v-model="setAdmin.adminTel"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button   @click="exitSetForm('setAdmin')">取 消</el-button>
          <el-button type="primary" @click="applySet('setAdmin')">确 定</el-button>
        </el-form-item>
      </el-form>
    
    </el-dialog>
   
  </div>
</template>
<script>
import SessionTools from "../util/SessionTools";
import ajax from "../util/myAxios";
import wwjPage from '../components/WwjPage.vue';
export default {
  name: "AdminManage",
  mounted() {
    this.state = SessionTools.getTypeName4Dict2("state");
    this.initAdmin();
    this.initRoles();
  },
  data() {
    // var checkAccount = (rule, value, callback) => {
    //   if (!value) {
    //     return callback(new Error("账号不能为空"));
    //   }
    //   // setTimeout(() => {
    //   //   if (!Number.isInteger(value)) {
    //   //     callback(new Error("请输入数字值"));
    //   //   }
    //   // }, 1000);
    //   callback();
    // };
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.addAdmin.adminPwdAgin !== "") {
          this.$refs.addAdmin.validateField("adminPwdAgin");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.addAdmin.adminPwd) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      // conTop: {
      //     backgroundImage:'url(' + require('../img/加菲猫背景2.jpg') + ')',
      //     width:"100%",
      //     height:"100%",
      // },
 
      search: {
        limit: 5,
        currPage: 1,    
        adminState: "",
        adminName: "",
        startTime: "",
        endTime: "",
      },
      addAdmin: {
        adminAccount: "",
        adminPwd: "",
        adminPwdAgin: "",
        adminName: "",
        roleId: "",
        adminTel: "",
      },
      setAdmin: {
        adminName: "",
        roleId: "",
        adminState: "",
        adminTel: "",
        adminId:"",
      },
      rules: {
        adminName: [
          { required: true, message: "请输入姓名", trigger: "blur" },
          { min: 2, max: 5, message: "长度在 2 到 5 个字符", trigger: "blur" },
        ],
        adminState: [{ required: true, message: "请选择角色", trigger: "change" }],

        adminAccount: [
          { required: true, message: "请填写帐号", trigger: "blur" },
        ],
        adminPwd: [
          { required: true, validator: validatePass, trigger: "blur" },
        ],
        adminPwdAgin: [
          { required: true, validator: validatePass2, trigger: "blur" },
        ],

        adminTel: [
          { required: true, message: "请输入电话", trigger: "blur" },
          { min: 11, max: 11, message: "长度在11个字符", trigger: "blur" },
        ],
      },

      count: 100,
      tableData: [],
      state: [],
      roles: [],
      addDialogVisible: false,
      setDialogVisible: false,
      checkVisible:false,
      
    };
  },
  methods: {
    //查询所有角色
    initRoles() {
      ajax({
        url: "/initRole",
        type: "get",
        data: {},
        success: (resp) => {
          this.roles = resp.list;
        },
      });
    },
     //增加用户   
    add(){
        this.addDialogVisible=true;
    },
    //确认新增用户
    applyAdd(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          ajax({
            type:'post',
            url: "insertAdmin",
            data: this.addAdmin,
            success: (resp) => {
              this.$message(resp.message);
              if (resp.id == 0 && resp.message == "新增成功") {
                this.initAdmin();
                this.addDialogVisible = false;
                this.exitAddForm(formName);
              }
              console.log(resp);
            },
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    //取消新增用户
    exitAddForm(formName) {
      this.addDialogVisible = false;
      this.$refs[formName].resetFields();
    },
   
    //初始化表格信息（条件查询提案数据）
    initAdmin() {
        console.log("currentPage=="+this.search.currPage);
        console.log("limit=="+this.search.limit);
        console.log("adminState=="+this.search.adminState);
        console.log("adminName=="+this.search.adminName);
        console.log("startTime=="+this.search.startTime);
        console.log("endTime=="+this.search.endTime);
        ajax({
            type:'get',
            url:'/selectAdmin',
            data:{
                adminState:this.search.adminState,
                adminName:this.search.adminName,
                startTime:this.search.startTime,
                endTime:this.search.endTime,
                currentPage:this.search.currPage,
                limit:this.search.limit,         
            },
            success:(resp)=>{
                console.log('selectAdmin',resp);
                this.tableData = resp.list;                 
                this.count = resp.count;
                this.totlePage=this.count%this.limit==0 ? this.count/this.limit :parseInt(this.count/this.limit+1);
               
            }
        })
    },
    //查询按钮查询信息
    query() {
      this.search.currPage=1;
      this.initAdmin(); 
    },
    //删除
    del(item) {
      if (confirm("你确定删除吗？")) {
          ajax({
            url: "deleteAdmin",
            data: { adminId: item.adminId },
            success: (resp) => {
              console.log(resp);             
              this.$message("删除成功");
              this.query();           
            },
          });
        }
    },
    //修改
    set(item) {
      console.log(item);
      this.setDialogVisible = true;
      this.setAdmin.adminId = item.adminId;
      this.setAdmin.adminName = item.adminName;
      this.setAdmin.roleId = item.roleId;
      this.setAdmin.adminState = item.adminState;
      this.setAdmin.adminTel = item.adminTel;
    },
    //确认修改
    applySet(formName) {
      ajax({
        url: "updateAdmin",
        data: this.setAdmin,
        success: (resp) => {
          console.log(resp);
          this.$message(resp.message);
          this.initAdmin();
          this.setDialogVisible = false;
          this.exitSetForm(formName);
        },
      });
    },
    //取消修改
    exitSetForm(formName) {
      this.setDialogVisible = false;
      this.$refs[formName].resetFields();
    },
   //分页变化
    handleSizeChange(limit) {
        console.log('limit尺寸发生改变' + limit);
        this.search.limit=limit;
        this.initAdmin();
    },
    handleCurrentChange(currentPage) {
        console.log('当前页面发生改变了' + currentPage);
        this.search.currPage=currentPage;
        this.initAdmin();
    },
    //返回日期字符串    
    returnTime(time2) {
      var time = new Date(time2);
      return time.toLocaleDateString();
    },
    //根据状态去字典表查询状态名
    returnState(stateId) {
      return SessionTools.getTypeName4Dict("state", stateId);
    },

  },
  components: {
    wwjPage,
  },
};
</script>
