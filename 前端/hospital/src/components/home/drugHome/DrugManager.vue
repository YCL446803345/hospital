<template>
    <div class="divClass" >
            <!-- 面包屑导航 -->
        <el-breadcrumb separator-class="el-icon-arrow-right" class="mianbao">
            <el-breadcrumb-item :to="{ path: '/gotoHome' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{path: '/drug/storage/list'}">药库管理</el-breadcrumb-item>
            <el-breadcrumb-item>药品详情</el-breadcrumb-item>
        </el-breadcrumb>
        <!-- 添加查询搜索栏 -->
        <el-row style="margin-top:24px">
            <el-col :span="6">
                <el-input placeholder="请输入药品名称" v-model="name" class="input-with-select" clearable style="width:200px;margin-left:9px"></el-input>
            </el-col>
            <el-col :span="6">
                <el-select v-model="drugType" clearable placeholder="请选择药品类别" style="width:147px;margin-left:-213px">
                    <el-option 
                    v-for="type in typeList"
                    :key="type.id"
                    :label="type.name"
                    :value="type.name">
                    </el-option>
                </el-select>
            </el-col>
            <el-col :span="8" style="margin-left:-483px;margin-top:1px">
                <el-button size="medium" type="primary" @click="createMethods()">查询<i class="el-icon-search el-icon--right"></i></el-button>
                <el-button size="medium" type="warning" @click="batchUnloadDrug">批量上架<i class="el-icon-upload2 el-icon--right"></i></el-button>
                <el-button size="medium" type="success" @click="purTableVisible=true;">新增药品<i class="el-icon-plus el-icon--right"></i></el-button>
                <el-button size="medium" type="success" @click="addpurTableVisible=true;">新增类别<i class="el-icon-plus el-icon--right"></i></el-button>
            </el-col>
        </el-row>

        <!-- 药品列表 -->        
        <el-table
            ref="multipleTable"
            :data="drugData"
            tooltip-effect="dark"
            style="width: 100%"
            @selection-change="handleSelectionChange">

            <el-table-column type="selection" ></el-table-column>
            <el-table-column label="药品名称" prop="name">
                <template slot-scope="scope">
                    <el-popover trigger="hover" placement="right" offset="10">
                          <el-descriptions class="margin-top" :column="3" border>
                            <el-descriptions-item>
                            <template slot="label">
                                <i class="el-icon-user"></i>
                                进价
                            </template>
                            {{scope.row.makePrice}}
                            </el-descriptions-item>
                            <el-descriptions-item>
                            <template slot="label">
                                <i class="el-icon-mobile-phone"></i>
                                销售价格
                            </template>
                            {{scope.row.salePrice}}
                            </el-descriptions-item>
                            <el-descriptions-item>
                            <template slot="label">
                                <i class="el-icon-location-outline"></i>
                                包装规格
                            </template>
                            {{scope.row.specifications}}
                            </el-descriptions-item>
                            <el-descriptions-item :span="3">
                            <template slot="label">
                                <i class="el-icon-tickets"></i>
                                功效主治
                            </template>
                            {{scope.row.spare1}}
                            </el-descriptions-item>
                            <el-descriptions-item :span="3">
                            <template slot="label">
                                <i class="el-icon-office-building"></i>
                                生产厂商
                            </template>
                            {{scope.row.spare2}}
                            </el-descriptions-item>
                        </el-descriptions>
                        <div slot="reference" class="name-wrapper">
                        <el-tag size="medium">{{ scope.row.name }}</el-tag>
                        </div>
                    </el-popover>
                </template>
            </el-table-column>

            <el-table-column label="药品类别"  prop="drugType"></el-table-column>

            <el-table-column label="库存量"  prop="stock"></el-table-column>
        
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button size="mini" type="info"    @click="gotoUpdateDrug(scope.row)">编辑<i class="el-icon-edit el-icon--right"></i></el-button>
                    <el-button size="mini" type="primary" @click="oppenAddStock(scope.row.id)">添加库存<i class="el-icon-plus el-icon--right"></i></el-button>
                    <el-button size="mini" type="warning" @click="drugUnload(scope.row.id)">上架<i class="el-icon-top el-icon--right"></i></el-button>
                    <el-button size="mini" type="danger" @click="deleteDrug(scope.row.id)">删除<i class="el-icon-top el-icon--right"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 编辑模态框 -->
        <el-dialog title="编辑药品信息" :visible.sync="updatedialogTableVisible" center>
            <!-- Form -->
            <el-form :model="updateDrug"  ref="updateForm" status-icon :rules="rules2">

                <el-form-item label="药品名称" :label-width="formLabelWidth" prop="name">
                <el-input v-model="updateDrug.name" autocomplete="off"></el-input>
                </el-form-item>

                <el-form-item label="药品类别" :label-width="formLabelWidth" prop="drugType">
                    <el-select v-model="updateDrug.drugType" clearable placeholder="请选择">
                        <el-option 
                        v-for="type in typeList"
                        :key="type.value"
                        :label="type.name"
                        :value="type.name" >
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="进价" :label-width="formLabelWidth" prop="makePrice">
                <el-input-number v-model="updateDrug.makePrice" :disabled="true"></el-input-number>   
                </el-form-item>                    
                
                <el-form-item label="销售价格" :label-width="formLabelWidth" prop="salePrice">
                <el-input-number v-model="updateDrug.salePrice" :precision="2" :step="0.1"></el-input-number>
                </el-form-item>

                <el-form-item label="包装规格" :label-width="formLabelWidth" prop="specifications">
                <el-input v-model="updateDrug.specifications" autocomplete="off"></el-input>
                </el-form-item>

                <el-form-item label="功效主治" :label-width="formLabelWidth" prop="spare1">
                <el-input v-model="updateDrug.spare1" autocomplete="off"></el-input>
                </el-form-item>

                <el-form-item label="生产厂商" :label-width="formLabelWidth" prop="spare2">
                <el-input v-model="updateDrug.spare2" autocomplete="off"></el-input>
                </el-form-item>

                <el-form-item label="图片修改" :label-width="formLabelWidth">
                <el-upload
                    class="avatar-uploader"
                    action="api/teacherAvatarUplod"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :headers="header">
                    <img v-if="imageUrl" :src="imageUrl" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="updatedialogTableVisible=false;">取 消</el-button>
                <el-button type="primary" @click="sumitUpdateFor('updateForm')">确 定</el-button>
            </div>
        </el-dialog>

        <!-- 添加库存模态框 -->
        <el-dialog title="添加库存" :visible.sync="addStockdialogTableVisible" center :close-on-click-modal="false" center>
            <!-- Form -->
            <el-form :model="addDrugStock"  ref="addStockForm" :rules="addRules">            
                <el-form-item label="库存数量" :label-width="formLabelWidth" prop="stock">
                <el-input-number v-model="addDrugStock.stock"  :min="1" ></el-input-number>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button @click="addStockdialogTableVisible=false;addDrugStock={};">取消</el-button>
                <el-button type="primary" @click="updateStock('addStockForm')">确定</el-button>
            </div>
        </el-dialog>

        <!-- 添加药品模态框 -->
        <el-dialog title="新加药品" :visible.sync="purTableVisible" center :close-on-click-modal="false">
            <el-form :model="purchaseDrug" :rules="rules" ref="Form" label-width="100px" class="demo-ruleForm">

                <el-form-item label="药品名称" prop="name">
                    <el-input v-model="purchaseDrug.name"></el-input>
                </el-form-item>

                <el-form-item label="选择类型" prop="drugType">
                    <el-select v-model="purchaseDrug.drugType">
                            <el-option
                            v-for="type in typeList"
                            :key="type.value"
                            :label="type.name"
                            :value="type.name">
                            </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="输入进价" prop="makePrice">
                    <el-input-number v-model="purchaseDrug.makePrice" :min="1" :precision="2" :step="0.1"></el-input-number>
                </el-form-item>

                <el-form-item label="输入销售价" prop="salePrice">
                    <el-input-number v-model="purchaseDrug.salePrice" :min="1" :precision="2" :step="0.1"></el-input-number>
                </el-form-item>

                <el-form-item label="规格" prop="specifications">
                    <el-input v-model="purchaseDrug.specifications"></el-input>
                </el-form-item>

                <el-form-item label="添加库存" prop="stock">
                    <el-input-number v-model="purchaseDrug.stock" :min="1" ></el-input-number>
                </el-form-item>

                <el-form-item label="生产厂商" prop="spare2">
                    <el-input v-model="purchaseDrug.spare2"></el-input>
                </el-form-item>

                <el-form-item label="主治功效">
                    <el-input type="textarea" v-model="purchaseDrug.spare1"></el-input>
                </el-form-item>

                <el-form-item label="添加图片" :label-width="formLabelWidth">
                    <el-upload
                        class="avatar-uploader"
                        action="api/teacherAvatarUplod"
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess2"
                        :headers="header">
                        <img v-if="addImageUrl" :src="addImageUrl" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </el-form-item>
                    <span v-if="flag == 1" style="color:red">请上传药品图片</span>
                <el-form-item>
                
                    <el-button type="primary" @click="sumitAdd('Form')">确定</el-button>
                    <el-button @click="purTableVisible = false;purchaseDrug={};addImageUrl='';flag=0;">返回</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

            <!-- 添加药品类别模态框 -->
        <el-dialog title="新增类别" :visible.sync="addpurTableVisible" center width="500px" :close-on-click-modal="false">
            <el-form :model="drugtypes" :rules="rules5" ref="Form" label-width="100px" class="demo-ruleForm">
                <el-form-item label="类别剂型" prop="name">
                    <el-input v-model="drugtypes.name"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="sumitAddTypes('Form')">确定</el-button>
                    <el-button @click="addpurTableVisible = false;drugtypes={};">返回</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <!-- 分页 -->
        <el-pagination 
        :current-page="pageNum"
        :page-size="pageSize"
        background
        layout="prev, pager, next,sizes, total"
        :page-sizes="[5,15,20,25]"
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
        flag:0,
        //分页属性
        pageNum:1,  //当前页
        total:0,  //总页数
        pageSize:5,  //页尺寸
        name:'',
        drugType:'',
        drugData:[],
        multipleSelection:[],
        updatedialogTableVisible:false,
        formLabelWidth: '120px',
        imageUrl:'',
        addImageUrl:'',
        header:{},
        types:[],
        drugtypes:{},
        addpurTableVisible:false,
        updateDrug:{    //编辑药品对象
            id:'',
            name:'',
            drugType:'',
            makePrice:'',
            salePrice:'',
            stock:'',
            specifications:'',
            status:'',
            spare1:'',
            spare2:'',
            spare3:''
        },
        purchaseDrug:{
            id:'',
            name:'',
            drugType:'',
            makePrice:'',
            salePrice:'',
            stock:'',
            specifications:'',
            status:'',
            spare1:'',
            spare2:'',
            spare3:''
        },   //添加药品对象
        purTableVisible:false,
        addStockdialogTableVisible:false,
        addDrugStock:{
            id:'',
            stock:''
        },
        typeList:[],
        updateForm:{
            name:'',
            drugType:'',
            salePrice:'',
            specifications:'',
            spare1:'',
            spare2:''
        },
        //编辑表单验证
        rules2:{
            name:[
                {required: true, message: '不能为空哦', trigger: 'blur'},
            ],
            drugType:[
                {required: true, message: '请选择类别', trigger: 'blur'},
            ],
            salePrice:[
                { required:true, message:'不能为空哦', trigger: 'blur'},
                { pattern: /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/, message: '请输入正确的价格'}
            ],
            specifications:[
                {required: true, message: '不能为空哦', trigger: 'blur'},
            ],
            spare1:[
                {required: true, message: '不能为空哦', trigger: 'blur'},
            ],
            spare2:[
                {required: true, message: '不能为空哦', trigger: 'blur'},
            ],
        },
        //添加表单验证
        rules:{
            name:[
                {required: true, message: '不能为空哦', trigger: 'blur'},
            ],
            drugType:[
                {required: true, message: '请选择类别', trigger: 'blur'},
            ],
            makePrice:[
                { required:true, message:'不能为空哦', trigger: 'blur'},
                { pattern: /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/, message: '请输入正确的价格'}
            ],
            salePrice:[
                { required:true, message:'不能为空哦', trigger: 'blur'},
                { pattern: /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/, message: '请输入正确的价格'}
            ],
            specifications:[
                {required: true, message: '不能为空哦', trigger: 'blur'},
            ],
            stock:[
                { required:true, message:'不能为空哦', trigger: 'blur'},
                { pattern: /^[+]{0,1}(\d+)$/, message: '请输入正确库存'}
            ],
            spare1:[
                {required: true, message: '不能为空哦', trigger: 'blur'},
            ],
            spare2:[
                {required: true, message: '不能为空哦', trigger: 'blur'},
            ]
        },
        rules5:{
            name:[
                    {required: true, message: '不能为空哦', trigger: 'blur'},
                ]
        },
        //添加库存表单验证
        addRules:{
                stock:[
                { required:true, message:'不能为空哦', trigger: 'blur'},
                { pattern: /^[+]{0,1}(\d+)$/, message: '请输入正确库存'}
            ],
        }   
      }
   },
   methods:{
    //执行添加库存
    updateStock(forName){
        this.$refs[forName].validate((valid) =>{
            if (valid) {    
                this.$axios.post("api/drug/byIdUpdateStock",this.addDrugStock).then(res=>{
                if (res.data.status == 200) {
                    this.$message({
                    showClose: true,
                    message: '操作成功',
                    type: 'success',
                    duration:2000
                    });
                    this.createMethods(1);
                    this.addStockdialogTableVisible=false;
                    this.addDrugStock={};
                }else{
                    this.$message({
                    showClose: true,
                    message: '操作失败,系统维护中',
                    type: 'warning',
                    duration:2000
                    });
                    this.createMethods(1);
                    this.addStockdialogTableVisible=false;
                    this.addDrugStock={};
                }
        })
          
               }else{
                   return false
               }
           })
    },
    //头像上传成功处理
    handleAvatarSuccess(res, file) {
        if (res.status == 200) {
            this.updateDrug.spare3=res.data
        }
        this.imageUrl = URL.createObjectURL(file.raw);
    },
    handleAvatarSuccess2(res, file) {
        this.addImageUrl = URL.createObjectURL(file.raw);
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
    //序号
    indexMethod(index){
        return (index +1)+this.pageSize*(this.pageNum-1)
    },
    //页面加载时的钩子函数
    createMethods(pageNum = 1){
        this.$axios.get("api/drug/findAllDownDrug",{
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
    },
    //进入编辑药品信息
    gotoUpdateDrug(row){
        let {id,name,drugType,makePrice,salePrice,stock,specifications,status,spare1,spare2,spare3}=row
        this.updateDrug.id=id
        this.updateDrug.name=name
        this.updateDrug.drugType=drugType
        this.updateDrug.makePrice=makePrice
        this.updateDrug.salePrice=salePrice
        this.updateDrug.stock=stock
        this.updateDrug.specifications=specifications
        this.updateDrug.status=status
        this.updateDrug.spare1=spare1
        this.updateDrug.spare2=spare2
        this.updateDrug.name=name
        this.imageUrl=spare3
        this.updatedialogTableVisible=true
    },
           //提交编辑模态框
    sumitUpdateFor(forName){
            //编辑表单验证
           this.$refs[forName].validate((valid) =>{
               if (valid) {
                    this.$axios.post("api/drug/updateDrugInfo",this.updateDrug).then(res =>{
                        if (res.data.status == 200) {
                            this.updatedialogTableVisible=false;
                            this.createMethods(1);
                                this.$message({
                                showClose: true,
                                message: '修改成功',
                                type: 'success',
                                duration:1500
                                });
                        }else{
                            this.updatedialogTableVisible=false;
                            this.createMethods(1);
                                this.$message({
                                showClose: true,
                                message: '修改失败,系统维护中',
                                type: 'warning',
                                duration:2000
                            });
                        }
                    })
               }else{
                   return false
               }
           })
       },
    //单个药品上架
    drugUnload(id){
            this.$confirm('是否上架该药品', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
        let idStr = id+","
        this.$axios.post("api/drug/batchDown",qs.stringify({'idStrs':idStr})).then(res=>{
            if (res.data.status == 200) {
                this.$message({
                type: 'success',
                message: '操作成功!',
                duration:2000
                });
                this.createMethods(1);
            }else{
                this.$message({
                    showClose: true,
                    message: '很抱歉,'+res.data.msg,
                    type: 'warning',
                    duration:2000
                });
                this.createMethods(1);
            }
        })
        })

    },
    //批量上架
    batchUnloadDrug(){
        if (this.multipleSelection == '') {
                this.$message({
                showClose: true,
                message: '请至少选择一个药品',
                type: 'warning',
                duration:2000
            });
        }else{
            this.$confirm('是否批量上架', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            let idList = []
            this.multipleSelection.forEach(item=>{
                idList.push(item.id)
            })
            let idStr = idList.join(",")
            this.$axios.post("api/drug/batchDown",qs.stringify({'idStrs':idStr})).then(res=>{
                if (res.data.status == 200) {
                    this.$message({
                    type: 'success',
                    message: '操作成功!',
                    duration:2000
                    });
                    this.createMethods(1);
                }else{
                    this.$message({
                        showClose: true,
                        message: '很抱歉,'+res.data.msg,
                        type: 'warning',
                        duration:2000
                    });
                    this.createMethods(1);
                }
            })
        })
        }
    },
    //用于批量选择
    handleSelectionChange(val) {
        this.multipleSelection = val;
    },
    //添加药品
    sumitAdd(forName){
           this.$refs[forName].validate((valid) =>{
               if (valid) {
                   if(this.addImageUrl == ''){
                       this.flag=1
                   }else{
                   this.purchaseDrug.spare3=this.addImageUrl
                    this.$axios.post("api/drug/addDrug",this.purchaseDrug).then(res =>{
                        if (res.data.status == 200) {
                            this.updatedialogTableVisible=false;
                            this.createMethods(1);
                                this.$message({
                                showClose: true,
                                message: '添加成功',
                                type: 'success',
                                duration:2000
                                });
                            this.createMethods(1);
                            this.purchaseDrug={};
                            this.addImageUrl='';
                            this.purTableVisible=false
                        }else{
                            this.createMethods(1);
                                this.$message({
                                showClose: true,
                                message: '很抱歉,'+res.data.msg,
                                type: 'warning',
                                duration:2000
                            });
                            this.createMethods(1);
                            this.purchaseDrug={};
                            this.addImageUrl='';
                            this.purTableVisible=false
                        }
                    })
                    }
               }else{
                   return false
               }
           })
    },
    //添加类别
        sumitAddTypes(forName){
           this.$refs[forName].validate((valid) =>{
               if (valid) {
                    this.$axios.post("api/drug/addType",this.drugtypes).then(res =>{
                        if (res.data.status == 200) {
                                this.$message({
                                showClose: true,
                                message: '操作成功',
                                type: 'success',
                                duration:2000
                                });
                            this.addpurTableVisible=false;
                            this.createMethods(1);
                            this.drugtypes={};
                            this.findAllType();
                        }else{
                                this.$message({
                                showClose: true,
                                message: '很抱歉,'+res.data.msg,
                                type: 'warning',
                                duration:2000
                            });
                            this.createMethods(1);
                            this.findAllType();
                            this.addpurTableVisible=false
                            this.drugtypes={};
                        }
                    })
               }else{
                   return false
               }
           })
    },
    //打开添加库存模态框
    oppenAddStock(id){
        this.addStockdialogTableVisible=true
        this.addDrugStock.id=id
    },
    //删除药品
    deleteDrug(id){
        this.$confirm('是否删除该药品', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            this.$axios.post("api/drug/delete",qs.stringify({'id':id})).then(res=>{
                if (res.data.status == 200) {
                    this.$message({
                    showClose: true,
                    message: '操作成功',
                    type: 'success',
                    duration:2000
                    });
                    this.createMethods(1);
                }else{
                    this.createMethods(1);
                    this.$message({
                    showClose: true,
                    message: '很抱歉,'+res.data.msg,
                    type: 'warning',
                    duration:2000
                });
                }
            })
        })
    },
    findAllType(){
        this.$axios.get("api/drug/findAllType").then(res=>{
                this.typeList=res.data.data
        })
    },
    

   },
    created(){
        this.findAllType();
        this.createMethods();
        this.header={"tokenStr":window.localStorage.getItem("tokenStr")}
        //查询所有药品类别
        this.$axios.get("api/drug/findAllDrugType").then(res=>{
            if (res.data.status === 200) {
                this.types = res.data.data
            }
        })
   },
}
</script>

<style scoped>
  .avatar-uploader {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    width: 300px;
  }
  .avatar-uploader:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 200px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .mianbao{
    margin-left: 10px;
}
.divClass{
    margin-top: 30px;
    margin-bottom: 15px;
    margin-left: 5px;
    margin-right: 15px;
}
</style>
