import Vue from "vue";
import VueRouter from "vue-router";
// import SessionTools from "../util/SessionTools";
Vue.use(VueRouter);

// const VueRouterPush = VueRouter.prototype.push
// VueRouter.prototype.push = function push (to) {
//   return VueRouterPush.call(this, to).catch(err => err)
// }
const routes = [
  { path: "/", redirect: "/login" },
  // 地址栏上如果写上： http://localhost:8080/login , vue会帮助我们找login的url和这里的path去匹配
  // 如果匹配到了就渲染Login的组件
  {
    path: "/login",
    component: () => import("../views/Login.vue"),
    children: [
      { path: "*", redirect: "/login" }, // 其他界面
    ],
  },
  {
    path: "/main",
    component: () => import("../views/Main.vue"),
    children: [
      { path: "/", redirect: "welcome" }, // 欢迎界面
      {
        path: "welcome",
        component: () => import("../views/Welcome.vue"),
      },
      {
        path: "proposalManage",
        component: () => import("../views/ProposalManage.vue"),
      },
      {
        path: "showAdminInfo",
        component: () => import("../views/ShowAdminInfo.vue"),
      },
      {
        path: "menuManage",
        component: () => import("../views/MenuManage.vue"),
      }, 
     
      {
        path: "permission",
        component: () => import("../views/Permission.vue"),
      },
 
      {
        path: "proposalStat",
        component: () => import("../views/ProposalStat.vue"),
      },
      {
        path: "log",
        component: () => import("../views/Log.vue"),
      },
      {
        path: "role",
        component: () => import("../views/RoleManagement.vue"),
      },
      {
        path: "adminManage",
        component: () => import("../views/AdminManage.vue"),
      },
      { path: "*", redirect: "welcome" }, // 其他界面
    ],
  },
  { path: "/*", redirect: "/login" },
];

const router = new VueRouter({
  routes: routes,
});

// const routerPush = VueRouter.prototype.push;
// VueRouter.prototype.push = function push(location) {
//   return routerPush.call(this, location).catch((error) => error);
// };

// const originalPush = VueRouter.prototype.push;
// VueRouter.prototype.push = function push(location, onResolve, onReject) {
//   if (onResolve || onReject)
//     return originalPush.call(this, location, onResolve, onReject);
//   return originalPush.call(this, location).catch((err) => err);
// };
// router.beforeEach((to, from, next) => {
//   // 先拦截所有页面
//   var user = SessionTools.getUser();
//   if (user == null) {
//     if (to.path == "/login") {
//       next();
//     } else {
//       next({ path: "/login?" + Math.random() });
//     }
//   }
//   next();
// });
export default router;
