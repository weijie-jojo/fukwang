<template>
  <div id="login" :style="conTop">
    <el-row type="flex" justify="center" align="middle" class="login-layout">
      <el-col :span="6">
        <el-card>
          <div slot="header">
            <span style="font-size:25px">登录界面</span>
          </div>
          <div>
            <el-input
              class="field"
              placeholder="请输入账户"
              v-model="admin.account"     
              clearable
            >
            </el-input>
            <el-input
              class="field"
              placeholder="请输入密码"
              v-model="admin.pwd"
              show-password
            ></el-input>

            <el-input
              class="field"
              placeholder="请输入验证码"
              v-model="admin.code"
              clearable
            ></el-input>
            <el-image
              class="codeImg"
              :src="baseURL + codeUrl"
              @click="res"
            ></el-image>
            <el-button class="login-btn" type="primary" plain @click="doLogin"
              >登录</el-button
            >
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import urls from "../store/index";
import ajax from "../util/myAxios";
export default {
  name: "Login",
  data() {
    return {
      message: "Login",
      admin: {
        account: "",
        pwd: "",
        code: "",
      },
      baseURL: urls.state.baseURL,
      codeUrl: "code",
      conTop: {
        backgroundImage: "url(" + require("../img/福光background.jpeg") + ")",
        backgroundPosition: "center center",
        height: "100%",
        width: "100%",
      },
    };
  },
  methods: {
    doLogin() {
      var data = this.admin;
      ajax({
        url: "/doLogin",
        data: data,
        success: (resp) => {
          if (resp.id === 0) {
            this.$emit("setadmin", resp.obj);
            this.$message(resp.message);
            this.$router.push(resp.location);
          }
        },
      });
    },
    res() {
      this.codeUrl = this.codeUrl + "?" + Math.random();
    },
  },
};
</script>
<style lang="less" scoped>
#login,
.login-layout {
  height: 100%;
}
.login-panel {
  background-color: red;
}
#login .login-layout .field {
  margin-top: 5px;
}
#login .login-layout .codeImg {
  width: 100%;
  height: 40px;
  margin-top: 5px;
}
#login .login-layout .login-btn {
  width: 100%;
}
</style>
