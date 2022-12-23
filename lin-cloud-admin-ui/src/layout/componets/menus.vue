<template>
    <!--    单级菜单-->
    <el-menu-item  v-if="data.children == null || data.children.length==0 " :index="data.url || data.id +''">
        <el-icon>
            <component :is="data.icon"></component>
        </el-icon>

        <span>{{data.title}}</span>
    </el-menu-item>

    <!-- 多级-->
    <el-sub-menu v-else :index="data.url || data.id +''">
        <template #title>
            <el-icon>
                <component :is="data.icon"></component>
            </el-icon>
            <span>{{data.title}}</span>
        </template>
        <menus v-for="(item,index) in data.children" :key="index" :data="item"></menus>
    </el-sub-menu>
</template>

<script>
    export default {
        name: "menus",
        props:{
            data:{
                type:Object
            }
        }
    }
</script>

<style scoped>

</style>