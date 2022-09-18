import Vue from "vue";
import VueRouter from "vue-router";
// import SessionTools from "../util/SessionTools";
Vue.use(VueRouter);

const routes = [
  { path: "/", redirect: "/ProposalApply" },
  // 地址栏上如果写上： http://localhost:8080/login , vue会帮助我们找login的url和这里的path去匹配
  // 如果匹配到了就渲染Login的组件
  { path: "/ProposalApply", component: () => import("../views/ProposalApply.vue")},
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
