import Vue from 'vue' //导入node_modules中vue.js库文件
import VueRouter from 'vue-router' //导入node_modules中vue-router.js库文件

//import patientInfo from '../components/patient/patientInfo'
//import patientChangeDoctorOrBed from '../components/patient/PatientChangeDoctorOrBed'

//护士相关组件
import patientInfo from '../components/home/nurseHome/patient/patientInfo'
import patientChangeDoctorOrBed from '../components/home/nurseHome/patient/PatientChangeDoctorOrBed'
import patientWithNotBed from '../components/home/nurseHome/patient/PatientWithNotBed'
import bedManager from '../components/home/nurseHome/patient/BedManager'


//Vue安装router
Vue.use(VueRouter)

//医院入口登录相关组件
import Entry from '../components/entry/Entry'
import UserLogin from '../components/login/UserLogin' //用户登录组件
import WorkersLogin from '../components/login/WorkersLogin' //职工登录组件
import Home from '../components/home/Home' //职工主界面+导航栏


//药房组件
import DrugInfo from '../components/home/drugHome/DrugInfo'   //药品信息列表


//人事组件
import HumanAffairs from '../components/home/hrHome/HumanAffairs ' // 职工信息列表


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
	            {path: "/nurse/admission", component: patientInfo},
	            {path:"/nurse/patientManager",component:patientChangeDoctorOrBed},
	            {path:"/nurse/waitPatientManager",component:patientWithNotBed},
	            {path:"/nurse/bed",component:bedManager},
            ]
        }



    ]

})

//路由守卫
// router.beforeEach((to,from,next) =>{
//     if (to.path === '/login' || to.path === '/' || to.path === '/entry' || to.path === '/gotoUserLogin' || to.path === '/gotoWorkesLogin') {
//         next();
//     }else{
//         const token = localStorage.getItem('tokenStr');
//         token ? next() : next('/login')
//     }
// })

//导出路由
export default router