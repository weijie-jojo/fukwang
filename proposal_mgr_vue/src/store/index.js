import Vue from "vue";
import Vuex from "vuex";
import VueCookies from 'vue-cookies'

Vue.use(Vuex);
Vue.use(VueCookies);

export default new Vuex.Store({
  state: {baseURL:"http://localhost:11001/"},
  // state: {baseURL:"http://182.92.162.19:11001/"},
  // state: {baseURL:"http://61.131.6.141:11031/"},
  mutations: {},
  actions: {},
  modules: {},
});
