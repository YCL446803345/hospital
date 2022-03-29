<template>
    <div>
        <!-- 面包屑导航 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/gotoHome' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{path: '/medicine/home'}">药品信息</el-breadcrumb-item>
            <el-breadcrumb-item>药品详情</el-breadcrumb-item>
        </el-breadcrumb>

        <!--  查询 -->
        <el-row style="margin-top:10px">
            <el-col :span="7"  >
                <!-- 带查询查询文本框 -->
                <el-input placeholder="请输入药品名称" v-model="name" class="input-with-select" clearable></el-input>
            </el-col>
            <el-col :span="7"  style="margin-left:10px">
                <el-select v-model="drugType" clearable placeholder="请选择药品类型">
                    <el-option label="中药" value="中药"></el-option>
                <el-option label="西药" value="西药"></el-option>
                </el-select>
            </el-col>
            <el-col :span="8"  style="margin-left:10px">
                <el-button  icon="el-icon-search" @click="createMethods()"></el-button>
                <!-- <el-button size="mini" type="primary" @click="purTableVisible=true;">通知采购药品</el-button> -->
            </el-col>
        </el-row>
         <div f_l main>
        <div class="cont clearfix">
            <ul >    
                <li v-for="drug in drugData" :key="drug.id" style="overflow: hidden;" class="li" >
                    <div :class="{'imgRed':drug.stock <=0,'img':drug.stock >0 && drug.stock <= 10}">
                        <el-link :underline="false" @click="lookDrugInfo(drug.id)">
                            <!-- imgNormal -->
                            <!--  -->
                        <el-image style="width: 230px; height: 230px" :src="drug.spare3" fit="scale-down" class="imgNormal"></el-image>
                        </el-link>
                    </div>
                    <p>{{drug.name}}<span v-if="drug.stock <=0 " style="color:red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已售空</span>
                                    <span v-if="drug.stock >0 && drug.stock <= 10 " style="color:red">&nbsp;&nbsp;&nbsp;&nbsp;警告,库存不足</span></p> 
                    <p class="brown">价格:<b>￥{{drug.salePrice}}</b></p>
                </li>
            </ul>
        </div>
        </div> 
        
        <!-- <el-form>
            <el-form-item>
                    <el-upload
                        action="api/testUpload"
                        :show-file-list="true"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload"
                        :headers="header">
                        <img v-if="imageUrl" :src="imageUrl" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
            </el-form-item>
        </el-form> -->

        <!-- 单个药品信息模态框 -->
        <el-dialog title="药品详情" :visible.sync="dialogTableVisible" center>
            <span>
                  <el-descriptions class="margin-top" :column="3" border>
                    <template slot="extra">
                        <el-button type="primary" size="small" @click="close(drugInfo.id)">下架</el-button>
                    </template>
                    <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-user"></i>
                        药品名称
                    </template>
                    {{drugInfo.name}}
                    </el-descriptions-item>

                    <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-mobile-phone"></i>
                        药品类别
                    </template>
                    {{drugInfo.drugType}}
                    </el-descriptions-item>

                    <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-tickets"></i>
                        包装规格
                    </template>
                    {{drugInfo.specifications}}
                    </el-descriptions-item>

                    <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-location-outline"></i>
                        进价
                    </template>
                    {{'￥'+drugInfo.makePrice}}
                    </el-descriptions-item>

                    <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-tickets"></i>
                        销售价格
                    </template>
                    {{'￥'+drugInfo.salePrice}}
                    </el-descriptions-item>

                    <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-tickets"></i>
                        库存量
                    </template>
                    {{drugInfo.stock}}
                    </el-descriptions-item>

                    <el-descriptions-item :span="3">
                    <template slot="label">
                        <i class="el-icon-tickets"></i>
                        功效主治
                    </template>
                    {{drugInfo.spare1}}
                    </el-descriptions-item>

                    
                    <el-descriptions-item :span="3">
                    <template slot="label">
                        <i class="el-icon-tickets"></i>
                        生产厂商
                    </template>
                    {{drugInfo.spare2}}
                    </el-descriptions-item>
                </el-descriptions>
            </span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogTableVisible = false;drugInfo={};">返回</el-button>
            </span>
        </el-dialog>

        <!-- 采购清单模态框 -->
        <!-- <el-dialog title="采购信息" :visible.sync="purTableVisible" center>
            <el-form :model="purchaseDrug" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">

                <el-form-item label="药品名称" prop="drugName">
                    <el-input v-model="purchaseDrug.drugName"></el-input>
                </el-form-item>

                <el-form-item label="选择类型" prop="type">
                    <el-select v-model="purchaseDrug.type">
                        <el-option label="新增药品" value="0"></el-option>
                        <el-option label="添加库存" value="1"></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="添加数量" prop="purchaseNum">
                    <el-input-number v-model="purchaseDrug.purchaseNum" :min="1" ></el-input-number>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="sumitAdd('ruleForm')">确定</el-button>
                    <el-button @click="purTableVisible = false;purchaseDrug={};">返回</el-button>
                </el-form-item>
            </el-form>
        </el-dialog> -->

        <!-- 分页 -->
        <el-pagination 
        :current-page="pageNum"
        :page-size="pageSize"
        background
        layout="prev,pager,next,sizes,total"
        :page-sizes="[5,10,15,20]"
        :total="total"
        @current-change="changePage"
        @size-change="handleSizeChange">
        </el-pagination>
    </div>
</template>

<script>
import qs from 'qs'
export default {
   data() {
      return {
        // imageUrl:'',
        // uploadAvatar:'',
        // formLabelWidth: '120px',
        // header:{},  
        //条件查询所需条件
        name:'',    //药品名称
        drugType:'',  //药品类型
        //分页所所需条件
        pageNum:1,  //当前页
        total:0,  //总页数
        pageSize:10,  //页尺寸
        drugData:[], //所有药品列表
        drugInfo:{},     //单个药品信息对象
        dialogTableVisible:false, //单个药品模态框控件
        // purchaseDrug:{},    //单个采购对象
        // purTableVisible:false,
        //采购表单验证
        // rules:{
        //     drugName:[
        //         {required: true, message: '不能为空哦', trigger: 'blur'},
        //     ],
        //     type:[
        //         {required: true, message: '请选择类别', trigger: 'blur'},
        //     ],
        //     purchaseNum:[
        //         {required: true, message: '不能为空哦', trigger: 'blur'},
        //     ],
        // } 
      }
   },
   methods:{
           //头像上传成功处理
    //   handleAvatarSuccess(res, file) {
    //     this.imageUrl = URL.createObjectURL(file.raw);
    //     if (res.status == 200) {
    //         this.teacher.avatar = res.data
    //     }
    //     this.imageUrl = URL.createObjectURL(file.raw);
    //   },
    // sumitAdd(forName){
    //     this.$refs[forName].validate((valid) =>{
    //         if (valid) {
    //             this.$axios.post("api/purchase/addPurchase",this.purchaseDrug).then(res=>{
    //                 if (res.data.status == 200) {
    //                     this.$message({
    //                     type: 'success',
    //                     message: '操作成功!',
    //                     duration:2000
    //                     });
    //                     this.createMethods(1);
    //                     this.purTableVisible=false;
    //                     this.purchaseDrug={}
    //                 }else{
    //                     this.$message({
    //                     showClose: true,
    //                     message: '操作失败, 系统维护中',
    //                     type: 'warning',
    //                     duration:2000
    //                     });
    //                     this.createMethods(1);
    //                     this.purTableVisible=false;
    //                     this.purchaseDrug={}
    //                 }
    //             })
    //         }else{
    //             return false
    //         }
    //     })
    // },
    //下架药品
    close(id){
        console.log(window.localStorage.getItem("redisToken"))
        this.$axios.post("api/drug/updateDrugStatus",qs.stringify({'id':id,'redisToken':window.localStorage.getItem("redisToken")})).then(res=>{
            if (res.data.status == 200) {
                this.$message({
                showClose: true,
                message: '操作成功',
                type: 'success',
                duration:2000
                });
                this.createMethods()
                this.drugInfo={}
                this.dialogTableVisible=false
            }else{
                this.$message({
                showClose: true,
                message: '操作失败, '+res.data.msg,
                type: 'warning',
                duration:2000
                });
            }
        })
    },
    //查看单个药品详细信息
    lookDrugInfo(id){
        this.$axios.get("api/drug/addRedisToken").then(res=>{
                window.localStorage.setItem('redisToken',res.data.data)
                this.$axios.get("api/drug/findDrugById?id="+id).then(res=>{
                    if(res.data.status ==200){
                        this.drugInfo=res.data.data
                        this.dialogTableVisible=true
                    }else{
                        this.$message({
                        showClose: true,
                        message: '查看失败, 系统维护中',
                        type: 'warning',
                        duration:2000
                        });
                    }
                })
        })
    },
    //翻页事件
    changePage(value){
        this.createMethods(value)
    },
    //页尺寸改变事件
    handleSizeChange(value){
        this.pageSize=value
        //加载改变页尺寸大小和当前页后的列表
        this.createMethods()
    },
    //进入该页面加载的钩子函数
    createMethods(pageNum = 1){
        this.$axios.get("api/drug/findAllDrug",{
            params:{
                name:this.name,
                drugType:this.drugType,
                pageNum:pageNum,
                pageSize:this.pageSize
            }
        }).then(res=>{
            if (res.data.status == 200) {
                this.drugData=res.data.data.list
                this.total=res.data.data.total
                this.pageNum=res.data.data.pageNum
                this.pageSize=res.data.data.pageSize
            }
        })
    }
   },
    created(){
        this.createMethods();
        // this.header={"tokenStr":window.localStorage.getItem("tokenStr")}
   }
}
</script>

<style>
.cont {
    overflow: hidden;
}
.clearfix:after {
    display: block;
    visibility: hidden;
    clear: both;
    height: 0;
    content: "."
}
.f_l {
    float: left
}
.main {
    width: 750px
}
.li{
    float: left; 
    margin-left: 60px;
        padding-top: 10px;
        padding-bottom: 10px;
}
.brown{
    color: #a40000
}
.img{
    background: yellow;
}
.imgRed{
    background: red;
}
.imgNormal{
    border:dashed 3px rgb(17, 17, 17);
    border-right:solid 3px rgb(17, 17, 17); 
    border-top: solid 3px rgb(17, 17, 17);
    border-bottom: solid 3px rgb(17, 17, 17);
    border-left:  solid 3px rgb(17, 17, 17);
}
</style>
