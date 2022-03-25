import Vue from 'vue' //导入node_modules中vue.js库文件
import VueRouter from 'vue-router' //导入node_modules中vue-router.js库文件


//护士相关组件
import patientInfo from '../components/home/nurseHome/patient/patientInfo'
import patientChangeDoctorOrBed from '../components/home/nurseHome/patient/PatientChangeDoctorOrBed'
import patientWithNotBed from '../components/home/nurseHome/patient/PatientWithNotBed'
import bedManager from '../components/home/nurseHome/patient/BedManager'
import patientChangeDept from '../components/home/nurseHome/patient/PatientChangeDept'
import queryPatientInfo from '../components/home/nurseHome/patient/QueryPatientInfo'


//Vue安装router
Vue.use(VueRouter)

//医院入口登录相关组件
import Entry from '../components/entry/Entry'
import UserLogin from '../components/login/UserLogin' //用户登录组件
import WorkersLogin from '../components/login/WorkersLogin' //职工登录组件
import Home from '../components/home/Home' //职工主界面+导航栏

//医生组件
import PatientList from '../components/home/doctorHome/patient/PatientList' //住院患者列表
import ConsultationApplicationList from '../components/home/doctorHome/patient/ConsultationApplicationList' //会诊列表
import MedicalAdviceList from '../components/home/doctorHome/patient/MedicalAdviceList' //医嘱列表
import InHospitalTableList from '../components/home/doctorHome/patient/InHospitalTableList' //预约住院列表
import LeaveHospitalList from '../components/home/doctorHome/patient/LeaveHospitalList' //出院申请列表

//药房组件


import DrugInfo from '../components/home/drugHome/DrugInfo'   //药品信息列表
import DrugPre from '../components/home/drugHome/DrugPrescription'  //药方列表
import SendDrug from '../components/home/drugHome/SendDrug'    //发药列表



//人事组件
import HumanAffairs from '../components/home/hrHome/HumanAffairs ' // 职工管理
import Scheduling from '../components/home/hrHome/Scheduling' // 排班管理
import Perms from '../components/home/hrHome/Perms' // 权限管理

//收费组件
import costInfo from '../components/home/payHome/costInfo.vue' // 费用信息列表
import queryCost from '../components/home/payHome/queryCost.vue' //查询统计
import leaveHospital from '../components/home/payHome/leave.vue' //出院结算
import dataAnalysis from '../components/home/payHome/dataAnalysis.vue' //数据分析
import leaveCost from '../components/home/payHome/leaveCost.vue' //出院病人费用查询
import pay from '../components/home/payHome/pay.vue' //入院缴费


//配置路由规则
var router = new VueRouter({

    routes: [
        //登录界面及首组件
        { path: "/", redirect: "/entry" },
        { path: "/entry", component: Entry },
        { path: "/gotoUserLogin", component: UserLogin },
        { path: "/gotoWorkesLogin", component: WorkersLogin },
        //菜单栏路由
        {
            path: "/gotoHome",
            component: Home,


            children: [

                { path: "/medicine/home", component: DrugInfo },
                { path: "/worker/list", component: HumanAffairs },

                { path: "/nurse/admission", component: patientInfo },
                { path: "/nurse/changeDept", component: patientChangeDept },
                { path: "/nurse/information", component: queryPatientInfo },
                { path: "/nurse/patientManager", component: patientChangeDoctorOrBed },
                { path: "/nurse/waitPatientManager", component: patientWithNotBed },
                { path: "/nurse/bed", component: bedManager },


                { path: "/drug/prescriptionList", component: DrugPre },
                {path:"/drug/prescriptionList",component:DrugPre},
                {path:"/drug/send/drug",component:SendDrug},

                { path: "/pay/cost", component: costInfo },
				{path:"/pay/queryCost",component:queryCost},
                {path:"/pay/leaveHospital",component:leaveHospital},
                {path:"/pay/dataAnalysis",component:dataAnalysis},
                {path:"/pay/leaveCost",component:leaveCost},
                {path:"/pay/pay",component:pay},

                { path: "/doctor/PatientList", component: PatientList },
                { path: "/doctor/consultationApplicationList", component: ConsultationApplicationList },
                { path: "/doctor/medicalAdviceList", component: MedicalAdviceList },
                { path: "/doctor/inHospitalTableList", component: InHospitalTableList },
                { path: "/doctor/leaveHospitalList", component: LeaveHospitalList },

                { path: "/worker/scheduling", component: Scheduling },
                { path: "/user/perms", component: Perms },


            ]
        }



    ]

})

//路由守卫
router.beforeEach((to,from,next) =>{
    if (to.path === '/login' || to.path === '/' || to.path === '/entry' || to.path === '/gotoUserLogin' || to.path === '/gotoWorkesLogin') {
    next();
    }else{
        const token = localStorage.getItem('tokenStr');
        token ? next() : next('/gotoWorkesLogin')
   }
})

//导出路由
export default router