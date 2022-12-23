<!--menus-->
<template>
    <el-scrollbar style="background: rgb(255,255,255);" >
        <div class="aside">
            <h3 style="text-align: center;color: #000;">{{collapse?'通用':'通用管理系统'}}</h3>
            <el-menu
                    router
                    :collapse="collapse"
                    active-text-color="rgb(0,160,255)"
                    :background-color="color"
                    class="el-menu-vertical-demo menu"
                    default-active="2"
                    text-color="#00000"
            >
            <menus  v-for="(item,index) in newlist" :data="item"></menus>
            </el-menu>
        </div>
    </el-scrollbar>
</template>

<script setup>
    import menus from "./menus.vue";
    import { reactive, ref,computed } from 'vue'
    import {routerStore} from "@/store/router";

    const userstore = routerStore();

    const props = defineProps({
        collapse:{
            type:Boolean,
            default:false
        },
        color:{
            type:String,
            default:'rgb(255,255,255)'
        }
    })

   const newlist = computed({
       get() {
           return userstore.menus;
       },
    })


</script>

<style scoped>

h3{
    color: white;
}
.menu{
    border: none;
}

</style>