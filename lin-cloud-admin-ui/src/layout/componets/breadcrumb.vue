<!--面包糠-->
<template>
    <el-breadcrumb :separator-icon="ArrowRight" class="breadcrumb">
        <el-breadcrumb-item v-for="(item,index) in list" :key="index">{{item}}</el-breadcrumb-item>
    </el-breadcrumb>
</template>

<script setup>
    import { onBeforeRouteUpdate } from "vue-router";
    import { ref } from 'vue';
    import {routerStore} from "@/store/router";

    const userstore = routerStore();
    const list = ref(['主页'])

    onBeforeRouteUpdate((to)=>{
        let name = to.path.replace("/","");
        if (name != 'index'){
            list.value =  userstore.titles[name];
        }else {
            list.value = ['主页']
        }
    })
</script>

<style scoped>

</style>