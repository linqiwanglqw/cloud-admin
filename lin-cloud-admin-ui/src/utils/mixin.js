import {routerStore} from "@/store/router";

export const mix = {
    methods:{
        hasAuth(perm){
            const userstore = routerStore();
            var authority = userstore.authorites
            return authority.indexOf(perm)>-1
        }
    }
}