import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
// 导入ElementUI的插件
import ElementUI from "element-ui";
// 导入ElementUI的css样式
import "element-ui/lib/theme-chalk/index.css";
import BaiduMap from "vue-baidu-map";
import JsonExcel from 'vue-json-excel'
// ‘downloadExcel’ 为 组册组件名字 
Vue.component('downloadExcel', JsonExcel)
import Print from 'vue-print-nb'
Vue.use(Print);
// 让ElementUI能够在全局使用（static）
Vue.use(ElementUI);

Vue.use(BaiduMap, {
  ak: "r7sjTGHZucSMO8NRn0w4Gt42uunpGR3Z",
});
// ---------------------------------------
Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
