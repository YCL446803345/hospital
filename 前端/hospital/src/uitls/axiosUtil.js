import axios from 'axios'

export const request = (config) => {
  return axios(config)
}

// 请求前设置header  添加请求拦截器，在请求头中加token
axios.interceptors.request.use(
  config => {
    if (localStorage.getItem('tokenStr')) {
      config.headers.tokenStr = localStorage.getItem('tokenStr')
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 请求完成后 拦截器 token过期或失效处理
axios.interceptors.response.use(
  response => {
    console.log(response)
    return response
  },() => {
      // 获取错误状态码，token失效
      // 清除token
      localStorage.removeItem('tokenStr')
      // 重新跳转到login页面
      router.push('/login')
      alert("出错了")
  }
)

export default axios