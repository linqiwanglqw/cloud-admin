import {defineStore} from "pinia";
import { getRouters } from "@/api/router";

export const routerStore = defineStore("router",{
    state:()=>{
        return{
            routers:[],
            menus:[],
            isrouters:false,
            titles:[],
            authorites:[],
        }
    },
    getters:{

    },
    actions:{
         router(){
             return getRouters()
        }
    }

})