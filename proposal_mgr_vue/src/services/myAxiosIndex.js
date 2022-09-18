import axios from 'axios'
import store from '@/store'
import router from '@/router'

// 接口调用基准路径
export const baseURL = ''

// 创建一个独立的实例对象
const instance = axios.create({
  baseURL: baseURL
})

// 响应拦截器的作用:数据脱壳
instance.interceptors.response.use(res => {
  // 把返回的数据去掉一层data属性
  return res.data
}, err => {
  // 处理token过期问题
  if (err.response && err.response.status === 401) {
    // token失效了，跳转到登录页
    return router.push('/login')
  }
  return Promise.reject(err)
})

// 请求拦截器
instance.interceptors.request.use(config => {
  // 统一添加请求头
  const token = store.state.user.profile.token
  if (token) {
    // 已经登录成功，统一添加token
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
}, err => {
  return Promise.reject(err)
})

// 封装通用的接口调用方法
export default (options) => {
  return instance({
    method: options.method || 'GET',
    url: options.url,
    // ES6规则：对象的key可以是动态的变量
    [options.method.toUpperCase() === 'GET' ? 'params' : 'data']: options.data
  })
}