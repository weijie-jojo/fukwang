<template>
  <div id="permission">
    <div class="body">
      <el-row
        ><div class="setting">
          <i class="el-icon-setting">权限配置</i>
        </div></el-row
      >

      <el-row class="title" :gutter="80">
        
        <el-col :span="20"
          ><div class="grid-content bg-purple">
            <el-select
              v-model="value"
              filterable
              placeholder="请选择"
              @change="selectMenu(value)"
            >
              <el-option
                v-for="item in options"
                :key="item.roleId"
                :label="item.roleName"
                :value="item.roleId"
              >
              </el-option>
            </el-select></div
        ></el-col>
      </el-row>

      <el-row :gutter="80" class="heard">
        <el-col :span="8"
          ><div class="grid-content bg-purple">
            <el-tag>当前角色拥有的菜单</el-tag>
          </div></el-col
        >
        <el-col :span="4"
          ><div class="grid-content bg-purple">
            <el-tag>可执行操作</el-tag>
          </div></el-col
        >
        <el-col :span="8"
          ><div class="grid-content bg-purple">
            <el-tag>当前角色未拥有菜单</el-tag>
          </div></el-col
        >
      </el-row>

      <el-row class="main" :gutter="80">
        <el-col :span="8">
          <div class="grid-content bg-purple">
            <el-tree
              :data="data"
              show-checkbox
              node-key="menuId"
              ref="treeNode"
              :default-expanded-keys="expanded"
              :default-checked-keys="checked"
              :props="defaultProps"
            >
            </el-tree>
          </div>
        </el-col>

        <el-col :span="4">
          <div class="buttonelone">
            <el-button
              type="primary"
              icon="el-icon-circle-plus"
              @click="addTerraceMenu()"
            >←</el-button>
          </div>
          <br />
          <div class="buttoneltwo">
            <el-button
              type="primary"
              icon="el-icon-remove"
              @click="deleteTerraceMenu()"
              >→</el-button
            >
          </div>
        </el-col>

        <el-col :span="8"
          ><div class="grid-content bg-purple">
            <el-tree
              :data="datatwo"
              show-checkbox
              node-key="menuId"
              ref="treeNodetwo"
              :default-expanded-keys="expanded"
              :default-checked-keys="checked"
              :props="defaultProps"
            >
            </el-tree>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import ajax from "../util/myAxios.js";
import axios from "axios";
export default {
  name: "Login",
  data() {
    return {
      options: [],
      expanded: [],
      expandedtwo: [],
      checked: [],
      checkedtwo: [],
      value: "管理员",
      message: "",
      tree: [],
      treetwo: [],
      menus: [],
      menustwo: [],
      data: [],
      datatwo: [],
      nodes: [],
      roleId: "1",
      defaultProps: {
        children: "children",
        label: "menuName",
      },
    };
  },
  mounted() {
    this.initPage();
   
  },

  methods: {
    initPage() {
      ajax({
        type: "get",
        url: "/initRole",
        success: (resp) => {
          console.log("options=="+resp.list);
          // for (var j in resp.list) {
          //   var item1 = resp.list[j];
          //   if (item1.roleName == '管理员') {
          //    this.roleId = item1.roleId;
          //   }
          // }
          // for (var i in resp.list) {
          //   var item = resp.list[i];
          //   if (item.roleId == 1) {
          //     resp.list.splice(i, 1);
          //   }
          // }
          
          this.options = resp.list;
          
          this.message = resp.message;
          this.selectMenu(this.roleId);
        },
      });
    },
    parseTree(menus, tree, pid) {
      for (var i = 0; i < menus.length; i++) {
        if (menus[i].menuPid == pid) {
          var obj = {
            menuId: menus[i].menuId,
            menuName: menus[i].menuName,
            menuUrl: menus[i].menuUrl,
            menuPid: menus[i].menuPid,
            children: [],
          };
          tree.push(obj);
          this.parseTree(menus, obj.children, obj.menuId);
        }
      }
    },
    selectMenu(roleId) {
      this.roleId = roleId;
      this.data = [];
      this.tree = [];
      this.treetwo = [];
      this.datatwo = [];
      ajax({
        type: "get",
        url: "/selectMenuById",
        data: {
          roleId: roleId,
        },
        success: (resp) => {
          this.menus = resp.list;
          if (resp.list.length > 0) {
            //找出最高级父菜单
            for (var i = 0; i < resp.list.length - 1; i++) {
              for (var j = 0; j < resp.list.length - 1 - i; j++) {
                if (resp.list[j].menuPid > resp.list[j + 1].menuPid) {
                  var temp = resp.list[j];
                  resp.list[j] = resp.list[j + 1];
                  resp.list[j + 1] = temp;
                }
              }
            }

            this.parseTree(this.menus, this.tree, resp.list[0].menuPid);
          }
          this.data = this.tree;
          this.selectAllMenu(roleId);
        },
      });
    },
    selectAllMenu(roleId) {
      this.datatwo = [];
      this.treetwo = [];
      ajax({
        type: "get",
        url: "/seMeByRIdNo",
        data: {
          roleId: roleId,
        },
        success: (resp) => {
          this.menustwo = resp.list;
          if (resp.list.length > 0) {
            for (var i = 0; i < resp.list.length - 1; i++) {
              for (var j = 0; j < resp.list.length - 1 - i; j++) {
                if (resp.list[j].menuPid > resp.list[j + 1].menuPid) {
                  var temp = resp.list[j];
                  resp.list[j] = resp.list[j + 1];
                  resp.list[j + 1] = temp;
                }
              }
            }
            this.parseTree(this.menustwo, this.treetwo, resp.list[0].menuPid);
          }

          this.datatwo = this.treetwo;
        },
      });
    },
    addTerraceMenu() {
      //  let checkNode  = this.$refs.treeNodetwo.getCheckedKeys().concat(this.$refs.treeNodetwo.getHalfCheckedKeys());
      let checkNode = this.$refs.treeNodetwo.getCheckedKeys();
      axios({
        method: "post",
        url: "/addTerraceMenu",
        baseURL: this.$store.state.baseURL,
        withCredentials: true,
        data: {
          roleId: this.roleId,
          checkNode: checkNode,
        },
        transformRequest: [
          function (data) {
            let str = "";
            for (let i in data) {
              str += i + "=" + data[i] + "&";
            }
            return str;
          },
        ],
      })
        .then((response) => {
          this.selectMenu(this.roleId);
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    deleteTerraceMenu() {
      // let checkNode  = this.$refs.treeNode.getCheckedKeys().concat(this.$refs.treeNode.getHalfCheckedKeys());
      let checkNode = this.$refs.treeNode.getCheckedKeys();
      axios({
        method: "post",
        url: "/deleteTerraceMenu",
        baseURL: this.$store.state.baseURL,
        withCredentials: true,
        data: {
          roleId: this.roleId,
          checkNode: JSON.stringify(checkNode),
        },
        transformRequest: [
          function (data) {
            let str = "";
            for (let i in data) {
              str += i + "=" + data[i] + "&";
            }
            return str;
          },
        ],
      })
        .then((response) => {
          this.selectMenu(this.roleId);
          let resp = response.list;
          console.log(resp);
        })
        .catch((error) => {
          console.log(error);
          this.$message("请联系管理员...");
        });
    },
  },
};
</script>

<style lang="less">
.body {
  width: 100%;
  height: 100%;
}

.main {
  margin-bottom: 20px;
  margin-top: 0;
  left: 2%;
  &:last-child {
    margin-bottom: 0;
  }
}
.title {
  margin-bottom: 20px;
  margin-top: 1%;
  left: 2%;

  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: white;
}
.main .bg-purple {
  background: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
.bg-purple-light {
  background: #f8f9fa;
}
.main .grid-content {
  border-radius: 4px;
  min-height: 500px;
}
.title .grid-content {
  border-radius: 4px;
  min-height: 50px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}

.heard {
  margin-bottom: 20px;
  left: 2%;
  &:last-child {
    margin-bottom: 0;
  }
}
.heard .el-col {
  border-radius: 4px;
}
.heard .bg-purple-dark {
  background: white;
}
.heard .bg-purple {
  background: white;
}
.heard .bg-purple-light {
  background: white;
}
.heard .grid-content {
  border-radius: 4px;
  min-height: 100%;
}
.heard .row-bg {
  padding: 10px 0;
  background-color: white;
}

.grid-content {
  text-align: center;
}
.el-ta {
  text-align: center;
  vertical-align: middle;
}
.buttoneltwo {
  text-align: center;
  margin-top: 30%;
}
.buttonelone {
  text-align: center;
  margin-top: 30%;
}
.setting {
  font-family: "Microsoft YaHei";
  font-size: 30px;
  float: left;
  margin-left: 2%;
  margin-top: 1%;
  color: #409eff;
}
</style>
