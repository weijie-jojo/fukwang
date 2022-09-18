import Vue from "vue";
import Vuex from "vuex";
import VueCookies from 'vue-cookies'

Vue.use(Vuex);
Vue.use(VueCookies);

export default new Vuex.Store({
  // state: {baseURL:"http://61.131.6.141:11032/"},
  state: {baseURL:"http://localhost:11002/"},
  // state: {baseURL:"http://182.92.162.19:11002/"},
  mutations: {},
  actions: {},
  modules: {},
});
