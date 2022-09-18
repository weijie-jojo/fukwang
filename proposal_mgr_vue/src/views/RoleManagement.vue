<template>
  <div id="role" class="back" >
    <div style=" opacity: 0.90">
      <el-row type="flex" justify="center" id="myCondition">
        <el-col :span="4" class="timeCol">
          <el-select
            v-model="roleId"
            placeholder="请选择角色"
            clearable
            style="width: 93%"
          >
            <el-option
              v-for="item in searchRole"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleId"
            >
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="11">
          <!-- <el-tag class="timeCol" id="timeRole">创建角色时间：</el-tag> -->
          <el-date-picker
            v-model="startTime"
            type="date"
            placeholder="请选择创建开始时间"
            value-format="yyyy-MM-dd"
            class="timeCol"
          ></el-date-picker>
          <el-date-picker
            v-model="endTime"
            type="date"
            placeholder="请选择创建结束时间"
            value-format="yyyy-MM-dd"
            class="timeCol"
          ></el-date-picker>
        </el-col>
        <el-col :span="3" style="margin-left:-120px">
          <el-select
            v-model="roleState"
            placeholder="请选择状态"
            clearable
            style="width: 93%"
            id="state"
          >
            <el-option
              v-for="item in states"
              :key="item.dictId"
              :label="item.typeName"
              :value="item.typeId"
            >
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="3" >
          <el-button
            style="margin-left:40px"
            type="primary"
            plain
            @click="queryRole"
            >搜索</el-button
          >
        </el-col>
        <el-col :span="3" id="addRole">
          <el-button type="primary" plain  @click="addRole"
            >新增</el-button
          >
        </el-col>
      </el-row>
      <br />
      <el-row type="flex" justify="center">
        <el-col :span="24">
          <el-table :data="tableData" style="width: 100%" border id="myTable">
            <el-table-column label="角色名" width="180">
              <template slot-scope="scope">
                <span style="margin-left: 10px">{{ scope.row.roleName }}</span>
              </template>
            </el-table-column>

            <el-table-column label="创建角色时间" width="180">
              <template slot-scope="scope">
                <span
                  style="margin-left: 10px"
                  v-html="returnTime(scope.row.gmtCreate)"
                ></span>
              </template>
            </el-table-column>
            <el-table-column label="状态" width="180">
              <template slot-scope="scope">
                <span style="margin-left: 10px">
                  {{ getTypeName(scope.row.roleState) }}</span
                >
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <!-- :class="{'active':isActive}" -->
                <!-- :style="{color:activeColor}"  -->
                <el-button size="mini" type="success" @click="changeState(scope.row)">{{
                  scope.row.roleState == 0 ? "启用" : "禁用"
                }}</el-button>
                <!-- <el-button size="mini" @click="edit(scope.row)">修改</el-button> -->
                <el-button size="mini" type="danger" @click="del(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
      <br />
      <!-- 分页 -->
      <el-row type="flex" justify="center">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[3, 6, 9, 12]"
          :page-size="limit"
          layout="total, sizes, prev, pager, next, jumper"
          :total="count"
        >
        </el-pagination>
      </el-row>
    </div>
    <!-- 新增弹窗 -->
    <el-dialog title="新增角色信息" :visible.sync="dialogVisible" width="30%">
      <el-input
        placeholder="请输入角色名"
        v-model="addRoleName"
        clearable
      ></el-input>

      <el-select v-model="addRoleState" placeholder="请选择">
        <el-option
          v-for="item in states"
          :key="item.dictId"
          :label="item.typeName"
          :value="item.typeId"
        >
        </el-option>
      </el-select>

      <span slot="footer" class="dialog-footer">
        <el-button @click="exitForm('addRoleName')">取 消</el-button>
        <el-button type="primary" @click="confirm">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 修改弹窗 -->
    <!-- <el-dialog title="修改角色信息" :visible.sync="dialogEdit" width="30%">
      <el-input
        placeholder="请输入角色名"
        v-model="editRoleName"
        clearable
      ></el-input>

      <el-select v-model="editRoleState" placeholder="请选择">
        <el-option
          v-for="item in states"
          :key="item.dictId"
          :label="item.typeName"
          :value="item.typeId"
        >
        </el-option>
      </el-select>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogEdit = false">取 消</el-button>
        <el-button type="primary" @click="editConfirm">确 定</el-button>
      </span>
    </el-dialog> -->
  </div>
</template>
<script>
// import axios from 'axios';
import SessionTools from "../util/SessionTools";
import ajax from "../util/myAxios";
export default {
  name: "Role",
  data() {
    return {
      // conTop: {
      //   backgroundImage:'url(' + require('../img/福光background.jpeg') + ')',
      //   width:"100%",
      //   height:"100%",
      // },
      //查询条件
      startTime: "",
      endTime: "",
      roleId: "",
      roleState: "",
      //分页信息
      count: 0, //总记录数
      limit: 3, //每页显示条目数
      currentPage: 1, //当前页
      totlePage: 3, //总页数
      //前端传给后端的查询数据
      searchRole: [],
      //字典表中查询出来的别名为state的信息
      states: [],
      //从后端查询出来的角色信息
      tableData: [],
      //新增弹窗默认关闭
      dialogVisible: false,
      //修改弹窗默认关闭
      dialogEdit: false,
      //新增数据
      addRoleName: "",
      addRoleState: "",
      //修改信息
      // editRoleName: "",
      // ediRoleId: "",
      // editRoleState: "",
      // isActive:true,
      // activeColor:"danger",
    };
  },
  mounted() {
    this.initTableRole();
  },
  methods: {
    //初始化下拉框状态
    initState() {
      ajax({
        type: "get",
        url: "/initState",
        data: {},
        success: (resp) => {
          this.states = resp.list;
        },
      });
    },
    //初始化下拉框角色
    initRole() {
      ajax({
        type: "get",
        url: "/initRole",
        data: {},
        success: (resp) => {
          this.searchRole = resp.list;
        },
      });
    },
    //初始化表格角色信息
    initTableRole() {
      ajax({
        type: "get",
        url: "/queryRole",
        data: {
          startTime: this.startTime,
          endTime: this.endTime,
          currentPage: this.currentPage,
          limit: this.limit,
          roleId: this.roleId,
          roleState: this.roleState,
        },
        success: (resp) => {
          this.tableData = resp.list;
          this.count = resp.count;
          this.totlePage =
          this.count % this.limit == 0
            ? this.count / this.limit
            : parseInt(this.count / this.limit + 1);
          this.initState();
          this.initRole();
        },
      });
    },
    //查询按钮查询角色信息
    queryRole() {
      this.currentPage = 1;
      this.initTableRole();
    },
    //点击新增按钮
    addRole() {
      this.dialogVisible = true;
    },
    //清空弹窗数据
    exitForm(formName) {
      this.dialogVisible = false;
      this.$refs(formName).resetFields();
    },
    //点击新增弹窗确认按钮
    confirm() {
      if (this.addRoleName != "") {
        ajax({
          type: "post",
          url: "/addRole",
          data: {
            roleName: this.addRoleName,
            roleState: this.addRoleState,
          },
          success: (resp) => {
            this.$message(resp.message);
            this.initTableRole();
            this.dialogVisible = false;
          },
        });
      } else {
        this.$message("请填写数据");
      }
    },
    //从字典表获取状态别名
    getTypeName(state) {
      return SessionTools.getStateList().find((item) => item.typeId == state)
        .typeName;
    },

    //改变状态（启用/禁用）
    changeState(role) {
      ajax({
        type: "post",
        url: "/changeState",
        data: {
          roleState: role.roleState,
          roleId: role.roleId,
        },
        success: (resp) => {
          if(resp.id==0){
            this.$message({
                message: resp.message,
                type: 'warning',
            });
          }else{
            this.$message({
                message: resp.message,
                type: 'success',
            });
          }
          this.initTableRole();
        },
      });
    },
    //点击修改按钮
    // edit(role) {
    //   this.dialogEdit = true;
    //   this.ediRoleId = role.roleId;
    //   this.editRoleState = role.roleState;
    //   this.editRoleName = role.roleName;
    // },
    //点击删除按钮
    del(role) {
      if(confirm("你确定删除吗？")){
          ajax({
          type: "post",
          url: "/delRole",
          data: {
            roleId: role.roleId,
          },
          success: (resp) => {
            if(resp.id==0){
              this.$message({
                  message: resp.message,
                  type: 'warning',
              });
            }else{
              this.$message({
                  message: resp.message,
                  type: 'success',
              });
            }
            this.initTableRole();
          },
        });
      } 
    },
    //点击修改弹窗确认按钮
    editConfirm() {
      ajax({
        type: "post",
        url: "/editRole",
        data: {
          editRoleState: this.editRoleState,
          ediRoleId: this.ediRoleId,
          editRoleName: this.editRoleName,
        },
        success: (resp) => {
          this.$message(resp.message);
          this.initTableRole();
          this.dialogEdit = false;
        },
      });
    },
    //返回当前时间
    returnTime(time2) {
      var time = new Date(time2);
      return time.toLocaleDateString();
    },
    //分页变化
    handleSizeChange(limit) {
      console.log("limit尺寸发生改变" + limit);
      this.limit = limit;
      this.initTableRole();
    },
    handleCurrentChange(currentPage) {
      console.log("当前页面发生改变了" + currentPage);
      this.currentPage = currentPage;
      this.initTableRole();
    },
  },
};
</script>

<style lang="less">
#admin {
  height: 100%;
}

.timeCol {
  margin-left: 60px;
}

// #search {
//   width: 100px;
//   margin-left: 40px;
// }

#myTable {
  //   border: red 1px solid;
  margin-top: 50px;
  th {
    padding: 0 !important;
    height: 70px;
    line-height: 30px;
  }
  td {
    padding: 0 !important;
    height: 70px;
    line-height: 30px;
  }
}
#myCondition {
  // border: red 1px solid;
  margin-left: -30px;
  margin-top: 20px;
}
// .back {
//   background: url(../img/加菲猫.png);
//   background-size: 600px;
//   background-repeat: no-repeat;
//   background-position: 30% 30%;
//   // height: 80%;
// }
.tm {
  opacity: 0.98;
}
.active {
	background: #8cd8da;
  border-color: #8cd8da;
  color: #FFF;
}
.active {
  background: #f5d7b9;
  border-color: #f5d7b9;
  color: #FFF;
}
</style>
