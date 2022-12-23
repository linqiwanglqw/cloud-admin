import axios from 'axios'
import { ElMessage } from 'element-plus'
import {userStore} from "@/store/user";
import { useRouter } from 'vue-router'
// 创建axios实例
const service = axios.create({
    // axios中请求配置有baseURL选项，表示请求URL公共部分
    baseURL: 'http://127.0.0.1:8080/',
    // 超时
    timeout: 10000
  })
//请求拦截
service.interceptors.request.use((config)=>{
    const token = localStorage.getItem("linToken");

    if (token){
        config.headers['lin-token'] = token
    }
    return config;
},(error)=>{
    return Promise.reject(error);
})


service.interceptors.response.use((response)=>{
    if (response){
        return response.data
    }

},(error)=>{
    if (error){
        if (error.response.status == 500){
            let data = error.response.data;
            if (data.errno == 501){
                ElMessage.error("请重新登录！！！")
                window.location.href = "/login"
            }

            ElMessage.error(data.errmsg)
        }
        return Promise.reject(error)
    }



})

export default service