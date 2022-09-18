<template>
    <div id="proposalApply" style="{ width: screenWidth + 'px' }"  >
       
            <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="100px" >
                <el-form-item  style="margin-left: 140px;margin-top:20px;margin-bottom:40px">
                    <span style="font-size:20px">GCWE提案书</span>  
                                
                </el-form-item>
                <!-- <el-form-item  style="margin-left: 300px;margin-top:20px">
                               
                </el-form-item> -->
                <el-form-item  label="提案编号" >
                    <el-form-item style="margin-left:20px">
                    {{ruleForm.proposalCode}}
                    </el-form-item>         
                </el-form-item>
                <el-form-item label="起案日" required style="margin-left:330px;margin-top:-60px;">  
                    <el-form-item style="margin-left:30px">                  
                        {{returnTime(new Date)}}
                    </el-form-item> 
                </el-form-item>
                <el-form-item label="提案人" prop="proposalPerson"  style="margin-top: 40px;">
                    <el-input  style="width:200px;margin-left:20px" v-model="ruleForm.proposalPerson"></el-input>
                </el-form-item>
                
                <!-- <el-form-item label="起案日"    style="margin-left:340px;margin-top:-60px;" required prop="proposalDate">
                    <el-date-picker value-format="yyyy-MM-dd" v-model="ruleForm.proposalDate" type="date" placeholder="请选择起案日"  style="width:200px;margin-left:20px" ></el-date-picker>
                </el-form-item>   -->
                
                <el-form-item label="案件名"  prop="proposalName" style="margin-left:330px;margin-top:-60px;">
                <el-input  style="width:200px;margin-left:30px" v-model="ruleForm.proposalName"></el-input>
                </el-form-item>

                <el-form-item label="据点名"  prop="companyId">
                    <el-select v-model="ruleForm.companyId" placeholder="请选择据点" clearable style="width:200px;margin-left:20px" @change="selectDepartmentByCompanyId">
                        <el-option v-for="item in searchCompanys" :key="item.companyId" :label="item.companyAlias" :value="item.companyId"> </el-option>
                    </el-select>
                </el-form-item>
           
                <el-form-item label="部门" prop="departmentId" style="margin-left:330px;margin-top:-60px;">
                    <el-select v-model="ruleForm.departmentId" placeholder="请选择部门" clearable style="width:200px;margin-left:30px" >
                        <el-option v-for="item in searchDepartments" :key="item.departmentId" :label="item.departmentName" :value="item.departmentId"> </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="改善前" prop="beforeImprovement" >
                    <el-input type="textarea"  v-model=ruleForm.beforeImprovement  style="width:420px;margin-left:20px"></el-input>
                    <el-button type="success" @click="dialogVisible2 = true" style="margin-left: 10px;">改善前图片</el-button> 
                </el-form-item>
            
                <el-form-item label="改善后" prop="afterImprovement" >
                    <el-input type="textarea"  v-model=ruleForm.afterImprovement  style="width:420px;margin-left:20px"></el-input>
                    <el-button type="success" @click="dialogVisible3 = true" style="margin-left: 10px;">改善后图片</el-button> 
                </el-form-item>

                <el-form-item label="月度效应值"  style="margin-top: 20px;" prop="saveMoney" >
                    <el-input v-model.number=ruleForm.saveMoney  style="width:150px;margin-left:20px" ></el-input><span style="margin-left:20px">元</span>
                </el-form-item>

                <el-form-item label="月节省时间"  style="margin-left:340px;margin-top:-60px;" prop="saveTime"> 
                    <el-input v-model.number=ruleForm.saveTime style="width:150px;margin-left:20px" ></el-input><span style="margin-left:20px">小时</span>
                </el-form-item>
                
                <el-form-item style="margin-left:120px;margin-top:50px">
                    <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
                    <el-button @click="resetForm('ruleForm')" style="margin-left: 120px">重置</el-button>
                </el-form-item>
            </el-form>
      
       
        <!-- 上传改善前图片 -->
        <el-dialog title="上传图片" :visible.sync="dialogVisible2" width="40%">
            <el-form :model="form">
                <el-form-item :label-width="formLabelWidth" ref="uploadElement">
                <el-upload
                    ref="upload"
                    :action="uploadUrl"
                    list-type="picture-card"
                    name="load"
                    accept="image/png,image/jpeg"
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
                <el-form-item style="margin-left:200px;">
                <!-- <el-button size="small" type="primary" @click="submitImage"
                    >立即上传</el-button
                > -->
                <el-button size="small" type="primary" @click="cancel" style="width:80px;">返回</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
      
       <!-- 上传改善后图片 -->
        <el-dialog title="上传图片" :visible.sync="dialogVisible3" width="40%">
            <el-form :model="form">
                <el-form-item :label-width="formLabelWidth" ref="uploadElement">
                <el-upload
                    ref="upload"
                    :action="uploadUrl"
                    list-type="picture-card"
                    name="load"
                    accept="image/png,image/jpeg"
                    :limit="limitNum"
                    :before-upload="handleBeforeUpload"
                    :on-preview="handlePictureCardPreview2"
                    :on-remove="handleRemove"
                    :on-success="success2"
                    :class="{ hide: hideUpload }"
                >
                    <i class="el-icon-plus"></i>
                </el-upload>
                <el-dialog :visible.sync="dialogVisible">
                    <img width="100%" :src="dialogImageUrl2" alt="" />
                </el-dialog>
                </el-form-item>
                <el-form-item style="margin-left:200px;">
                <!-- <el-button size="small" type="primary" @click="submitImage"
                    >立即上传</el-button
                > -->
                <el-button size="small" type="primary" @click="cancel" style="width:80px;">返回</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
    // import SessionTools from '../util/SessionTools';
    import ajax from "../util/myAxios";
    // import axios from 'axios';
    export default {
    name: 'ProposalApply',
    data() {
      return {
        screenWidth:document.documentElement.clientWidth,
        // labelPosition: "right",
        // formLabelAlign: {},
        // disabledIput: true,
        // hideUpload: false,
        // dialogImageUrl: "",
        // dialogVisible: false, //图片预览弹窗
        // formLabelWidth: "80px",
        // limitNum: 1,
        // form: {},
        // dialogVisible2: false, //弹窗
        // dialogVisiblePwd: false,
        // uploadUrl: this.$store.state.baseURL + "/doUpload",
       
        limitNum: 1,
        form: {},
        formLabelAlign:{
            proposalImage:'',
            proposalImage2:'',
        },
        formLabelWidth: "80px",
         hideUpload: false,
        dialogVisible:false,
        dialogVisible2:false,
        dialogVisible3:false,
        dialogImageUrl: '',
        dialogImageUrl2: '',
        uploadUrl: this.$store.state.baseURL + "/doUpload",
        // uploadUrl:"http://localhost:11001//doUpload",
        // conTop: {
        //     backgroundImage:'url(' + require('../img/福光background.jpeg') + ')',
        //     width:"100%",
        //     height:"100%",
        //     backgroundRepeat: "no-repeat",
        //     backgroundPosition: "center center",
        // },
        ruleForm: {
           proposalId:'',
            //提交的数据
            proposalPerson:'',
            proposalName:'',
            // proposalDate:'',
            companyId:'',
            departmentId:'',
            beforeImprovement:'',
            afterImprovement:'',
            saveMoney:'',//月度效应值
            saveTime:'',//月节省时间
            proposalCode:'',//案件编号
        },
        rules: {
            beforeImprovement: [
                { required: true, message: '改善前内容不能为空', trigger: 'blur' },
                // { min: 10, max: 100, message: '长度在 10 到 100 个字符', trigger: 'blur' }
            ],
            afterImprovement: [
                { required: true, message: '改善后内容不能为空', trigger: 'blur' },
                // { min: 10, max: 100, message: '长度在 10 到 100 个字符', trigger: 'blur' }
            ],
            proposalPerson: [
                { required: true, message: '请输入提案人', trigger: 'blur' },
                { min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur' }
            ],
            proposalName: [
                { required: true, message: '请输入案件名', trigger: 'blur' },
                { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
            ],
            proposalDate: [
                { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
            ],
            companyId: [
                { required: true, message: '请选择据点', trigger: 'change' }
            ],
            departmentId: [
                { required: true, message: '请选择部门', trigger: 'change' }
            ],
            saveMoney: [
                { required: true, message: '月度效应值不能为空', trigger: 'blur'},
                { type: 'number', message: '月度效应值必须为正整数'}
            ],
            saveTime: [
                { required: true, message: '月节省时间不能为空'},
                { type: 'number', message: '月节省时间必须为正整数'}
            ],
        },
        // pickerOptions: {
        //     // 提案日大于等于今天而且大于等于完成时间
        //     disabledDate: time => {
        //         return (
        //             time.getTime() < new Date( new Date()).getTime()
        //         ); 
        //     }
        // },
      
        //后端查询的数据
        searchCompanys:[],
        searchDepartments:[],
      
      }
    },
    created(){
        console.log("screenWidth=="+this.screenWidth);
        // window.addEventListener('resize', this.handleResize)
    },
    

    // created() {
    //     //获取html
    //     var html = document.getElementsByTagName("html")[0];
    //     if (window.innerWidth < 750) {
    //         //首次进入时获取屏幕宽度，宽度/750*100，手机端设计稿宽一般为750px，这里以750为基准调整基础大小，即当屏幕正好为750px时html字体大小为100px，p{font-size: 1rem}字体大小就为100px，屏幕宽为375px时，html50px、p50px，这里标签的宽高同样能使用rem表示
    //         html.style.fontSize = (window.innerWidth / 750) * 100 + "px";
    //         } else {
    //         html.style.fontSize = "16px";
    //         }
    //         //实时获取改变html根元素字体大小
    //         window.addEventListener("resize", function () {
    //         if (window.innerWidth < 750) {
    //             html.style.fontSize = (window.innerWidth / 750) * 100 + "px";
    //             // console.log(window.innerWidth/750*100 + "px");
    //         } else {
    //             html.style.fontSize = "16px";
    //         }
    //     });
    // },
    mounted() {

        // this.adjustFontsize();
        this.getAllCompany();
        this.getProposalCode();

        
        window.οnresize= () => {
        return (() => {
            window.screenWidth = document.body.clientWidth;
            this.screenWidth = window.screenWidth;
        })();

        };
    },
    // destroyed(){
    //     window.removeEventListener('resize', this.getHeight)
    // },
    watch:{
        screenHeight (val) {
        // 为了避免频繁触发resize函数导致页面卡顿，使用定时器
            if (!this.timer) {
                // 一旦监听到的screenWidth值改变，就将其重新赋给data里的screenWidth
                this.screenHeight = val
                this.timer = true
                let that = this
                setTimeout(function () {
                // 打印screenWidth变化的值
                    console.log(that.screenHeight)
                    that.timer = false
                }, 400)
            }
        }
    },
    // created() {
    //     //获取html
    //     var html = document.getElementsByTagName("html")[0];
    //     if (window.innerWidth < 750) {
    //         //首次进入时获取屏幕宽度，宽度/750*100，手机端设计稿宽一般为750px，这里以750为基准调整基础大小，即当屏幕正好为750px时html字体大小为100px，p{font-size: 1rem}字体大小就为100px，屏幕宽为375px时，html50px、p50px，这里标签的宽高同样能使用rem表示
    //         html.style.fontSize = (window.innerWidth / 750) * 100 + "px";
    //         } else {
    //         html.style.fontSize = "16px";
    //         }
    //         //实时获取改变html根元素字体大小
    //         window.addEventListener("resize", function () {
    //         if (window.innerWidth < 750) {
    //             html.style.fontSize = (window.innerWidth / 750) * 100 + "px";
    //             // console.log(window.innerWidth/750*100 + "px");
    //         } else {
    //             html.style.fontSize = "16px";
    //         }
    //     });
    // },
    methods: {
         handleResize () {
            this.fullWidth = document.documentElement.clientWidth
        },
        // getHeight(){
        //     this.height = window.innerHeight - 70 + 'px';
        // },
        // adjustFontsize() {
        //     var n = document.documentElement,
        //     e = screen.width > 0 && (window.innerWidth >= screen.width || 0 == window.innerWidth) ? screen.width: window.innerWidth;
        //     e && (e = Math.min(e, 750), n.style.fontSize = e / 375 * 20 + "px")
        // },
       
        // BlurText(e){
        //     let boolean =new RegExp("^[1-9][0-9]*$").test(e.ruleForm.saveMoney.value);
        //     if(!boolean){
        //         this.$message.warning('请输入正整数');
        //         e.ruleForm.saveMoney.value='';
        //     }
        // },
     
        //初始化下拉公司信息 
        getAllCompany() {
            ajax({
                type:'get',
                url:'/getAllCompany',
                data:{
                    
                },
                success:(resp)=>{
                    console.log('getAllCompany',resp);
                    this.searchCompanys=resp.list;
                }
            })
        }, 
         //初始化下拉公司信息 
        selectDepartmentByCompanyId() {
            ajax({
                type:'get',
                url:'/selectDepartmentByCompanyId',
                data:{
                   companyId:this.ruleForm.companyId,
                },
                success:(resp)=>{
                    console.log('selectDepartmentByCompanyId',resp);
                    this.searchDepartments=resp.list;
                    
                }
            })
        }, 
        //获取结算单号
        getProposalCode() {
            
            ajax({
                type:'get',
                url:'/getProposalCode',
                data:{
           
                },
                success:(resp)=>{
                    console.log('getProposalCode',resp.message);
                    this.ruleForm.proposalCode=resp.message;
                }
            })
        }, 
        //提交表单
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    console.log('submit!');
                    console.log("proposalPerson=="+this.ruleForm.proposalPerson);
                    console.log("proposalName=="+this.ruleForm.proposalName);
                    // console.log("proposalDate=="+this.ruleForm.proposalDate);
                    console.log("companyId=="+this.ruleForm.companyId);
                    console.log("departmentId=="+this.ruleForm.departmentId);
                    console.log("beforeImprovement=="+this.ruleForm.beforeImprovement);
                    console.log("afterImprovement=="+this.ruleForm.afterImprovement);
                    console.log("saveMoney=="+this.ruleForm.saveMoney);
                    console.log("saveTime=="+this.ruleForm.saveTime);
                    let match=new RegExp("^[1-9][0-9]*$");
                    if(match.test(this.ruleForm.saveMoney)==true&&match.test(this.ruleForm.saveTime)==true){
                        ajax({
                            type:'post',
                            url:'/addProposal',
                            data:{
                                proposalPerson:this.ruleForm.proposalPerson,
                                proposalName:this.ruleForm.proposalName,
                                gmtCreate: this.returnTime(new Date),
                                companyId: this.ruleForm.companyId,
                                departmentId:this.ruleForm.departmentId,
                                beforeImprovement:this.ruleForm.beforeImprovement,
                                afterImprovement:this.ruleForm.afterImprovement,
                                saveMoney:this.ruleForm.saveMoney,
                                saveTime:this.ruleForm.saveTime,
                                proposalCode:this.ruleForm.proposalCode,
                            },
                            success:(resp)=>{
                                console.log('addProposal',resp);                 
                                this.$message(resp.message) ;    
                                this.getProposalCode();   
                                this.resetForm('ruleForm');
                                console.log('proposalImage',this.formLabelAlign.proposalImage); 
                                console.log('proposalImage2',this.formLabelAlign.proposalImage2); 
                                ajax({
                                    type: "post",
                                    url: "/updateImage",
                                    data: {
                                        proposalImage: this.formLabelAlign.proposalImage,
                                        proposalImage2: this.formLabelAlign.proposalImage2,
                                        proposalCode: this.ruleForm.proposalCode,
                                    },
                                    withCredentials: true,
                                    success: (resp) => {
                                         console.log(resp.message);
                                        // this.dialogVisible2 = !this.dialogVisible2;
                                        // this.initPage();
                                    },
                                });               
                            }
                        })                       
                    }else{
                        this.$message.warning('月度效应值或者月节省时间必须为正整数');
                        this.getProposalCode();   
                        this.resetForm('ruleForm'); 
                    }                  
                }
                else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
       
        //重置
        resetForm(formName) {
            this.$refs[formName].resetFields();
            console.log(1111);
        },
       
        //返回当前时间
        returnTime(time2){
            var time=new Date(time2);
            return time.toLocaleDateString();
        },
       
        //上传图片
        //上传成功后
        success(file) {
            this.$message(file.message);
            var fileUrl = file.obj;
            this.formLabelAlign.proposalImage = fileUrl;
            // this.dialogVisible2=false;
            console.log("fileUrl=="+this.formLabelAlign.proposalImage);
        },
        //上传成功后
        success2(file) {
            this.$message(file.message);
            var fileUrl = file.obj;
            this.formLabelAlign.proposalImage2 = fileUrl;
            // this.dialogVisible2=false;
            console.log("fileUrl2=="+this.formLabelAlign.proposalImage2);
        },
        //上传前
        handleBeforeUpload(file) {
            const isLt10M = file.size / 1024 / 1024 < 10;
            if (!isLt10M) {
                this.$message.error('上传头像图片大小不能超过 10MB!');
            }
            return isLt10M;
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
         // 点击文件列表中已上传的文件时的钩子
        handlePictureCardPreview2(file) {
            this.dialogImageUrl2 = file.url;
            this.dialogVisible = true;
        },
        cancel() {
            this.dialogVisible2 = false;
            this.dialogVisible3 = false;
        },
        // uploadFile() {
        //     this.$refs.upload.submit();
        // },  
     
    },
  }
</script>

<style lang="less">
        // .tm {
        //     opacity: 0.8;
            
        // }; 
    //    @media  screen and (max-width:1200px){
    //     // #page{ width: 1100px; }#content,.div1{width: 730px;}#secondary{width:310px}
    //     .tm {
    //         opacity: 0.9;

    //     }; 

    // };
    // @media screen and (min-width:1200px){
    //     #page{ width: 1100px; }#content,.div1{width: 730px;}#secondary{width:310px}
    // };
    // @media screen and (min-width: 375px) and (max-width: 375px) {
    //     #proposalApply{
    //         width: 375px; 
    //         height: 812px;
    //     }
    // };
    // @media screen and (min-width: 768px) and (max-width: 959px) {
    //     #page{ width: 900px; }#content,.div1{width: 620px;}#secondary{width:220px}select{max-width:180px}
    // };
    // @media only screen and (min-width: 480px) and (max-width: 767px){
    //     #page{ width: 450px; }#content,.div1{width: 420px;position: relative; }#secondary{display:none}#access{width: 450px; }#access a {padding-right:5px}#access a img{display:none}#rss{display:none}#branding #s{display:none}
    // } ;
    // @media only screen and (max-width: 479px) {
    //     #page{ width: 300px; }#content,.div1{width: 300px;}#secondary{display:none}#access{width: 330px;} #access a {padding-right:10px;padding-left:10px}#access a img{display:none}#rss{display:none}#branding #s{display:none}#access ul ul a{width:100px}
    // }    ; 
</style>