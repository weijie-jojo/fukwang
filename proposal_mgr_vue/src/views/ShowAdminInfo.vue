<template>
  <div id="ShowAdminInfo">
    <div class="main">
      <div class="adminImage">
        <el-avatar
          @click="changImage()"
          :src="Imgpath"
          :size="100"
          :preview-src-list="Imgpath"
        >
        </el-avatar
        >
        <el-button type="success" @click="dialogVisible2 = true"
          >修改头像</el-button
        >
      </div>

      <div>
        <el-form
          :label-position="labelPosition"
          label-width="80px"
          :model="formLabelAlign"
        >
          <el-form-item label="姓名">
            <el-input
              :disabled="disabledIput"
              v-model="formLabelAlign.adminName"
            ></el-input>
          </el-form-item>
          <el-form-item label="电话">
            <el-input
              :disabled="disabledIput"
              v-model="formLabelAlign.adminTel"
            ></el-input>
          </el-form-item>

          <el-form-item label="角色" v-if="formLabelAlign.terraceRole">
            <span v-html="formLabelAlign.terraceRole.roleName"></span>
          </el-form-item>

          <el-form-item label="状态">
            <span v-html="returnState(formLabelAlign.adminState)"></span>



          </el-form-item>
          <el-form-item label="账号">
            <span v-html="formLabelAlign.adminAccount"></span>
          </el-form-item>
          <div class="btn">
            <el-button type="primary" @click="disabledIputChange()"
              >修改</el-button
            >
            <el-button type="primary" @click="submitAdmin()">提交</el-button>
            <el-button type="primary" @click="ChangePwd(formLabelAlign.adminId)"
              >修改密码</el-button
            >
          </div>
        </el-form>
      </div>
    </div>

    <!-- <div class="adminImage" style="margin: 20px"> 

  </div> -->

    <!-- 修改密码 -->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisiblePwd"
      width="50%"
    >
      <el-form
        :model="ruleForm"
        status-icon
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="密码" prop="pass">
          <el-input
            type="password"
            v-model="ruleForm.pass"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input
            type="password"
            v-model="ruleForm.checkPass"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')"
            >提交</el-button
          >
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 修改头像 -->
    <el-dialog title="上传图片" :visible.sync="dialogVisible2" width="30%">
      <el-form :model="form">
        <el-form-item :label-width="formLabelWidth" ref="uploadElement">
          <el-upload
            ref="upload"
            :action="uploadUrl"
            accept="image/png,image/jpg"
            list-type="picture-card"
            name="load"
            :limit="limitNum"
            :before-upload="handleBeforeUpload"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove"
            :on-success="success"
            :class="{ hide: hideUpload }"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="" />
          </el-dialog>
        </el-form-item>
        <el-form-item>
          <el-button size="small" type="primary" @click="submitImage"
            >立即上传</el-button
          >
          <el-button size="small" @click="tocancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import ajax from "../util/myAxios.js";
import SessionTools from "../util/SessionTools";
export default {
  data() {
    var checkAge = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("年龄不能为空"));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error("请输入数字值"));
        } else {
          if (value < 18) {
            callback(new Error("必须年满18岁"));
          } else {
            callback();
          }
        }
      }, 1000);
    };
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      labelPosition: "right",
      formLabelAlign: {},
      disabledIput: true,
      hideUpload: false,
      dialogImageUrl: "",
      dialogVisible: false, //图片预览弹窗
      formLabelWidth: "80px",
      limitNum: 1,
      form: {},
      dialogVisible2: false, //弹窗
      dialogVisiblePwd: false,
      uploadUrl: this.$store.state.baseURL + "/doUpload",
      Imgpath:
        "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
      setAdmin: {
        adminImg: "",
      },
      adminId: "",
      ruleForm: {
        pass: "",
        checkPass: "",
        age: "",
      },
      rules: {
        pass: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }],
        age: [{ validator: checkAge, trigger: "blur" }],
      },
    };
  },
  mounted() {
    this.initPage();
  },
  methods: {
     returnState(stateId) {
      return SessionTools.getTypeName4Dict("state", stateId);
    },
    success(file) {
      this.$message(file.message);
      var fileUrl = file.obj;
      this.formLabelAlign.adminImage = fileUrl;
    },
    //查询用户信息
    initPage() {
      ajax({
        type: "get",
        url: "/selectAdminRoleVo",
        // data: {
        //   roleId: roleId,
        // },
        withCredentials: true,
        success: (resp) => {
          console.log('adminRoleVo'+resp.list);
          this.formLabelAlign = resp.list[0];
          if (this.formLabelAlign.adminImage != "") {
            this.Imgpath =
              this.$store.state.baseURL +
              "showImg?imgPath=" +
              this.formLabelAlign.adminImage;
          } else {
            this.Imgpath =
              "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png";
          }
        },
      });
    },
    disabledIputChange() {
      this.disabledIput = !this.disabledIput;
    },
    submitAdmin() {
      ajax({
        type: "post",
        url: "/updateAdmin",
        data: {
          adminName: this.formLabelAlign.adminName,
          adminTel: this.formLabelAlign.adminTel,
          adminId: this.formLabelAlign.adminId,
        },
        withCredentials: true,
        success: (resp) => {
          alert(resp.message);
          this.disabledIput = !this.disabledIput;
        },
      });
    },
    submitImage() {
      ajax({
        type: "post",
        url: "/updateAdmin",
        data: {
          adminImage: this.formLabelAlign.adminImage,
          adminId: this.formLabelAlign.adminId,
        },
        withCredentials: true,
        success: (resp) => {
          alert(resp.message);
          this.dialogVisible2 = !this.dialogVisible2;
          this.initPage();
        },
      });
    },
    //打开密码修改界面
    ChangePwd(id) {
      this.dialogVisiblePwd = true;
      this.adminId = id;
    },
    //提交修改密码
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          ajax({
            type: "post",
            url: "/changAdminPwd",
            data: {
              pass: this.ruleForm.pass,
              adminId: this.adminId,
            },
            withCredentials: true,
            success: (resp) => {
              alert(resp.message);
            },
          });
          this.dialogVisiblePwd = false;
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    //清空
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    //上传图片
    handleBeforeUpload(file) {
      if (!(file.type === "image/png" || file.type === "image/jpg")) {
        this.$notify.warning({
          title: "警告",
          message: "请上传格式为image/png,image/jpg,的图片",
        });
      }
      let size = file.size / 1024 / 1024 / 2;
      if (size > 2) {
        this.$notify.warning({
          title: "警告",
          message: "图片大小必须小于2M",
        });
      }
    },
    // 文件列表移除文件时的钩子
    handleRemove(file, fileList) {
      this.hideUpload = fileList.length >= this.limitNum;
    },
    // 点击文件列表中已上传的文件时的钩子
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    uploadFile() {
      this.$refs.upload.submit();
    },
   
    tocancel() {
      this.dialogVisible2 = false;
    },
  },
};
</script>
<style lang="less" scoped>
.main {
  margin-left: 30%;
  width: 40%;
  height: 600px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  float: left;
}
.el-form {
  margin-left: 18%;
  margin-top: 7%;
}
.adminImage {
  align-content: center;
  margin-left: 20%;
  margin-top: 20px;
}
.el-input {
  width: 200px;
}

.btn .el-button {
  margin-left: 15px;
}
.hide .el-upload--picture-card {
  display: none;
}
</style>
