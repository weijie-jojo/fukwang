<template>
    <div id="proposalStat">
        <el-container>
            <!-- 结算金额统计 -->
            <div id="account" style="width: 1000px;height:500px;margin-top:60px"></div>
            <div id="condition"> 
                <el-select
                    v-model="search.companyId"
                    clearable
                    placeholder="请选择据点"
                >
                    <el-option
                        v-for="item in company"
                        :key="item.companyId"
                        :label="item.companyAlias"
                        :value="item.companyId"
                    >
                    </el-option>
                </el-select>               
                <el-date-picker v-model="search.startTime" type="date" placeholder="请选择提案开始时间"  value-format="yyyy-MM-dd" class="timeCol" style="margin-left:30px ;margin-top:20px"></el-date-picker>
                <el-date-picker v-model="search.endTime" type="date" placeholder="请选择提案结束时间"  value-format="yyyy-MM-dd" class="timeCol" style="margin-left:30px"></el-date-picker>
                
                <el-button type="primary" plain class="search-btn" @click="initProposal" id="search" style="margin-left:20px;width:70px">查询</el-button>             
            </div>
                    
    </el-container>  
             
    </div>
</template>
<script>
 import ajax from "../util/myAxios";
// import baseUrl from "../store/index"
import * as echarts from 'echarts'
// import JsonExcel from "vue-json-excel";
export default {
    // components: {
    //     DownloadExcel: JsonExcel
    // },
    name: 'ProposalStat',
    data(){
        return{
            search: {
                startTime: "",
                endTime: "",
                companyId:1,
            },
            company: [],
            department:[],
            accountName:[],
            accountData:[],
        }; 
    },
        mounted() {  
            this.getAllCompany();
            this.initProposal();    
        },
        methods: {            
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
          
            //查询结算单信息
            initProposal(){
               
                 ajax({
                    type:'get',
                    url:'/proposalStatistic',
                    data:{
                        companyId:this.search.companyId,
                        endTime:this.search.endTime,
                        startTime:this.search.startTime,
                    },
                    success:(resp)=>{
                        let accounts=resp.list;
                        console.log('accounts',accounts);
                        let name=[];
                        let data=[];                       
                        for (let i = 0; i < accounts.length; i++) {
                            data.push(accounts[i].count);
                            name.push(accounts[i].departmentName);                      
                        };
                        this.accountName=name;
                        this.accountData=data;                       
                        this.drawAccount();
                    }
                })    
            },
            //返回当前时间
            returnTime(time2){
                var time=new Date(time2);
                return time.toLocaleDateString();
            },
            //结算统计
            drawAccount(){        
                // 为charts选定父容器并初始化charts画布
                var myChart = echarts.init(document.getElementById('account')); 
                // 为图表添加数据
                myChart.setOption({
                    title: {
                        text: '各部门提案数量统计',
                        subtext: '',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                        type: 'shadow'
                        }
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                     xAxis: [
                        {
                        type: 'category',
                        data: this.accountName,
                        axisTick: {
                            alignWithLabel: true
                        }
                        }
                    ],
                    yAxis: [
                        {
                        type: 'value'
                        }
                    ],
                    series: [
                       {
                        name: '数量',
                        type: 'bar',
                        barWidth: '60%',
                        data: this.accountData
                        }
                    ]          
                })                
            },
        }
        
}
</script>
<style lang="less">
    #condition {
        // border:1px solid black;
        width:1000px; 
        height: 100px;  
        margin-left: -970px;    
        margin-top: 600px;     
    }
</style>
