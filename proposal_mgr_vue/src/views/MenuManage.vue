<template>
  <div id="menuManage" >
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
          <el-form-item label="菜单名">
            <el-input
              v-model="search.menuName"
              placeholder="请输入菜单名"
            ></el-input>
          </el-form-item>
          <el-form-item label="菜单状态">
            <el-select  v-model="search.menuState"  clearable  placeholder="请选择菜单状态"  >
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
          
        <el-table-column prop="menuName" label="菜单名" width="180">
        </el-table-column>
        <el-table-column prop="menuUrl" label="菜单URL" width="180">
        </el-table-column>
        <el-table-column label="菜单状态" width="180">
          <template slot-scope="scope">
            <span
              style="margin-left: 10px"
              v-html="returnState(scope.row.menuState)"
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
    <!-- 新增菜单信息弹窗 -->
    <el-dialog
      title="新增菜单信息"
      :visible.sync="addDialogVisible"
      width="20%"
    >
      <el-form
        :model="addMenu"
        :rules="rules"
        ref="addMenu"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="菜单名" prop="menuName">
          <el-input v-model="addMenu.menuName"></el-input>
        </el-form-item>
        <el-form-item label="菜单URL" prop="menuUrl">
          <el-input v-model.number="addMenu.menuUrl"></el-input>
        </el-form-item>
       <el-form-item label="状态">
          <el-select v-model="addMenu.menuState" placeholder="请选择状态">
            <el-option
              v-for="item in state"
              :key="item.typeId"
              :label="item.typeName"
              :value="item.typeId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="父菜单" >
          <el-select v-model="addMenu.menuPid" placeholder="请选择父菜单">
            <el-option
              v-for="item in pMenus"
              :key="item.menuId"
              :label="item.menuName"
              :value="item.menuId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="applyAdd('addMenu')"
            >立即创建</el-button
          >
          <el-button @click="exitAddForm('addMenu')">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  <el-dialog title="修改用户信息" :visible.sync="setDialogVisible" width="20%">

       <el-form
        :model="setMenu"
        :rules="rules"
        ref="setMenu"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="用户名" prop="adminName">
          <el-input v-model="setMenu.menuName"></el-input>
        </el-form-item>

        <el-form-item label="状态" prop="adminState">
          <el-select v-model="setMenu.menuState" placeholder="请选择状态">
            <el-option
              v-for="item in state"
              :key="item.typeId"
              :label="item.typeName"
              :value="item.typeId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button   @click="exitSetForm('setMenu')">取 消</el-button>
          <el-button type="primary" @click="applySet('setMenu')">确 定</el-button>
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
  name: "MenuManage",
  mounted() {
    this.state = SessionTools.getTypeName4Dict2("state");
    this.initMenu();
    this.initPmenus();
  },
  data() {
    var checkAccount = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("账号不能为空"));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error("请输入数字值"));
        }
      }, 1000);
      callback();
    };
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
        menuId:"",
        limit: 5,
        currPage: 1,    
        menuState: "",
        menuName: "",
        startTime: "",
        endTime: "",
      },
      addMenu: {
        menuName: "",
        menuUrl: "",
        menuPid: "",
        menuState: "",
      },
      setMenu: {
       
        menuName: "",
        menuState: "",
      },
      rules: {
        menuName: [
          { required: true, message: "请输入菜单名", trigger: "blur" },
          { min: 2, max: 5, message: "长度在 2 到 5 个字符", trigger: "blur" },
        ],
        menuUrl: [
          { required: true, message: "请输入菜单URL", trigger: "blur" },
          { min: 5, max: 15, message: "长度在 5 到 15 个字符", trigger: "blur" },
        ],
        menuState: [{ required: true, message: "请选择角色", trigger: "change" }],

        adminAccount: [{ validator: checkAccount, trigger: "blur" }],
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
      pMenus:[],
      addDialogVisible: false,
      setDialogVisible: false,
      checkVisible:false,
      
    };
  },
  methods: {
    //查询所有父菜单
    initPmenus() {
      ajax({
        url: "/selectPmenus",
        type: "get",
        data: {},
        success: (resp) => {
          this.pMenus = resp.list;
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
            url: "insertMenu",
            data: this.addMenu,
            success: (resp) => {
              this.$message(resp.message);
              if (resp.id == 0 && resp.message == "菜单新增成功") {
                  this.exitAddForm(formName);
                  this.initMenu();   
                  this.addDialogVisible = false;                       
              }
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
    initMenu() {
        console.log("currentPage=="+this.search.currPage);
        console.log("limit=="+this.search.limit);
        console.log("menuState=="+this.search.menuState);
        console.log("menuName=="+this.search.menuName);
        console.log("startTime=="+this.search.startTime);
        console.log("endTime=="+this.search.endTime);
        ajax({
            type:'get',
            url:'/selectMenu',
            data:{
                menuState:this.search.menuState,
                menuName:this.search.menuName,
                startTime:this.search.startTime,
                endTime:this.search.endTime,
                currentPage:this.search.currPage,
                limit:this.search.limit,         
            },
            success:(resp)=>{
                console.log('selectMenu',resp);
                this.tableData = resp.list;                 
                this.count = resp.count;
                this.totlePage=this.count%this.limit==0 ? this.count/this.limit :parseInt(this.count/this.limit+1);
               
            }
        })
    },
    //查询按钮查询信息
    query() {
      this.search.currPage=1;
      this.initMenu(); 
    },
    //删除
    del(item) {
      if (confirm("你确定删除吗？")) {
          ajax({
            type:'post',
            url: "deleteMenu",
            data: { menuId: item.menuId },
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
      this.setMenu.menuName = item.menuName;
      this.setMenu.menuState = item.menuState;
      this.search.menuId = item.menuId;
    },
    //确认修改
    applySet(formName) {
      ajax({
        type:'post',
        url: "updateMenu",
        data: {menuName:this.setMenu.menuName,
                menuState:this.setMenu.menuState,
                menuId:this.search.menuId},
        success: (resp) => {
          console.log(resp);
          this.$message(resp.message);
          this.initMenu();
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
        this.initMenu();
    },
    handleCurrentChange(currentPage) {
        console.log('当前页面发生改变了' + currentPage);
        this.search.currPage=currentPage;
        this.initMenu();
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
