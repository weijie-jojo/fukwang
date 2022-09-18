<template>
  <div id="main">
    <el-container style="height: 100%; border: 1px solid #eee">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <el-menu
          default-active="2"
          background-color="rgb(238, 241, 246)"
          active-text-color="#ffd04b"
        >
        <wwj-menu-item :menus="treeMenus" @itemclick="skip"></wwj-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header>
          <el-row>
            <el-col :span="3">
              <img src="../img/福光logo.jpg" class="ti" />
            </el-col>
            <el-col :span="3">
              <div class="title" >GCWE提案后台管理系统</div>
            </el-col>
            <el-col :span="3" :offset="18" class="adminSet" >
              <span >
                <el-dropdown @command="handleCommand" >
                  <i class="el-icon-setting" style="margin-right: 15px"></i>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="query">查看</el-dropdown-item>
                    <el-dropdown-item command="exit">注销</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
                <span v-html="admin2.adminName"></span>
              </span>
            </el-col>
          </el-row>
        </el-header>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
import ajax from "../util/myAxios";
import wwjMenuItem from "../components/WwjMenuItem.vue";
import SessionTools from "../util/SessionTools";
export default {
  name: "Main",
  props: ["admin2"],
  data() {
    return {
      editableTabsValue: "2",
      editableTabs: [],
      tabIndex: 2,
      menus: [],
      treeMenus: [],
    };
  },
  mounted() {
    SessionTools.saveUser(this.admin2);
    this.initMenus();
  },
  components: {
    wwjMenuItem,
  },
  methods: {
    initMenus() {
      ajax({
        url: "/getMenus",
        type: "get",
        success: (resp) => {
          let menus = resp.list;
          let tree = []; // 用来保存树状的数据形式
          this.parseTree(menus, tree, 0);
          this.treeMenus = tree;
          // console.log("menus==",menus);
          // console.log("treeMenus==",this.treeMenus);
          //第一次获取状态列表
          ajax({
            type: "get",
            url: "/initMain",
            success: (resp) => {
              SessionTools.saveDict(resp.list);
            },
          });
        },
      });
    },
    skip(item) {
      if (item.menuUrl) {
        console.log(item.menuUrl);
        this.$router.push(item.menuUrl);
      }
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
    handleCommand(command) {
      if (command == "exit") {
        ajax({
          url: "/doExit",
          success: (resp) => {
            console.log(resp);
            SessionTools.saveDict("");
            location.href = resp.location;
          },
        });
      }
      if (command == "query") {
        this.$router.push("showAdminInfo");
      }
    },
  },
};
</script>
<style lang="less" scoped>
#main {
  height: 100%;
}
#main .el-header {
  background-color: #409eff;
  color: #333;
  line-height: 60px;
}
#main .main-header {
  display: flex;
}
.ti {
  height: 50px;
  width: 80px;
  margin-top: 5px;

}
.title{
  font-size:24px;
  margin-left: -60px;
}
.adminSet{
  margin-top:-80px;
  margin-right:160px;
  // border:1px solid red;
  font-size:18px;
}
</style>
