import { createRouter,createWebHistory} from "vue-router";
import {routerStore} from "@/store/router";
import { nprogress } from '@/utils/nporgress'
import Layout from '@/layout/index.vue'

// 路由信息
const routes = [
    {
        path: "/login",
        name: "login",
        component:  () => import('../views/login/index.vue'),
    },
    {
        path: "",
        name: "layout",
        component:  Layout,
        redirect: 'index',
        children:[
            {
                path:"index",
                name:"Index",
                component:()=>import('../views/index.vue'),
            }
        ]
    },
];

// 导出路由
const router = createRouter({
    history: createWebHistory(),
    routes
});


router.beforeEach(((to, from, next) => {
        nprogress.start();
        const userstore = routerStore();
        var token = localStorage.getItem("linToken");  //获取token信息
        if (to.name != 'login'){
            if (!token){
                next({name:"login"});
            }else {
                if (!userstore.isrouters){
                    userstore.router().then(res=>{
                       let routers =  res.data.routers
                        routers.forEach(value => {
                            router.addRoute("layout",{
                                path:value.path,
                                name:value.name,
                                component:()=> import(value.component)
                            })
                        })
                        userstore.menus = res.data.menus;
                        userstore.authorites = res.data.authorites;
                        userstore.menus.unshift({
                            url:'index',
                            icon:"House",
                            title:"主页"
                        });
                        userstore.titles = res.data.titles;
                        userstore.isrouters = true;
                        router.addRoute( {
                            path: "/:pathMatch(.*)*",
                            name: "404",
                            component: ()=> import("../views/404/404.vue")
                        })
                        next({ ...to, replace: true }) // hack方法 确保addRoutes已完成
                    })
                }else {
                    next();
                }
            }
        }else {
            next();
        }
    }
))

router.afterEach(() => {
    nprogress.done();
})



export default router;
