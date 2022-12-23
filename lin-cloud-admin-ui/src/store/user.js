import {defineStore} from "pinia";
import { login} from "@/api/login";

export const userStore = defineStore("user",{
    state:()=>{
        return{
            token:localStorage.getItem("linToken") || '',
        }
    },
    getters:{

    },
    actions:{
        login(data){
            return new Promise((resolve, reject)=>{
                login(data).then(res=>{
                    localStorage.setItem("linToken",res.data);
                    resolve()
                }).catch(onerror=>{
                    reject(onerror)
                })

            })
        },
        out(){
            localStorage.removeItem('linToken')
        }
    }

})