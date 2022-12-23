<template>
    <el-container style="height: 100%">
        <el-aside :class="[collapse?'aside-small':'aside','cartoon'] ">
            <Aside :collapse="collapse"></Aside>
        </el-aside>
        <el-container>
            <el-header>
                <div class="header">
                    <div class="top">
                        <div class="top-left">
                            <component :is="icon" @click="aside" style="cursor:pointer;"></component>
                            <breadcrumb></breadcrumb>
                        </div>
                        <div class="top-right">
                            <el-link href="https://github.com/linqiwanglqw" :underline="false">
                                <svg xmlns="http://www.w3.org/2000/svg" aria-hidden="true" focusable="false" viewBox="0 0 24 24" class="icon" data-v-398c9053=""><path d="M12 .297c-6.63 0-12 5.373-12 12 0 5.303 3.438 9.8 8.205 11.385.6.113.82-.258.82-.577 0-.285-.01-1.04-.015-2.04-3.338.724-4.042-1.61-4.042-1.61C4.422 18.07 3.633 17.7 3.633 17.7c-1.087-.744.084-.729.084-.729 1.205.084 1.838 1.236 1.838 1.236 1.07 1.835 2.809 1.305 3.495.998.108-.776.417-1.305.76-1.605-2.665-.3-5.466-1.332-5.466-5.93 0-1.31.465-2.38 1.235-3.22-.135-.303-.54-1.523.105-3.176 0 0 1.005-.322 3.3 1.23.96-.267 1.98-.399 3-.405 1.02.006 2.04.138 3 .405 2.28-1.552 3.285-1.23 3.285-1.23.645 1.653.24 2.873.12 3.176.765.84 1.23 1.91 1.23 3.22 0 4.61-2.805 5.625-5.475 5.92.42.36.81 1.096.81 2.22 0 1.606-.015 2.896-.015 3.286 0 .315.21.69.825.57C20.565 22.092 24 17.592 24 12.297c0-6.627-5.373-12-12-12"></path></svg>
                            </el-link>
                            
                            <el-tooltip
                                    class="box-item"
                                    effect="dark"
                                    content="全屏"
                                    placement="bottom"
                            >
                            <component @click="screen" is="FullScreen"  style="cursor:pointer;margin-right: 20px"></component>
                            </el-tooltip>
                            <!-- <div class="top-righ-item">
                                <el-tooltip
                                        class="box-item"
                                        effect="dark"
                                        content="切换模式"
                                        placement="bottom"
                                >
                                <el-switch
                                        v-model="pattern"
                                        class="mt-2"
                                        inline-prompt
                                        active-icon="Sunny"
                                        inactive-icon="Moon"
                                        @change="toggleDark"
                                />
                                </el-tooltip>
                            </div> -->
                            <div>
                                <el-dropdown @command="user">
                                    <span class="el-dropdown-link">
                                       <el-avatar :size="30" src="https://t14.baidu.com/it/u=851152851,2194286831&fm=179&app=42&size=w931&n=0&f=JPEG&fmt=auto&maxorilen2heic=2000000?s=F3B415639C91FBE16A9B6F6C0200306E" />
                                    </span>
                                     <template #dropdown>
                                        <el-dropdown-menu>
                                            <el-dropdown-item command="a">个人中心</el-dropdown-item>
                                            <el-dropdown-item command="b">退出登录</el-dropdown-item>
                                        </el-dropdown-menu>
                                    </template>
                                </el-dropdown>
                            </div>
                        </div>
                    </div>
                </div>
            </el-header>
            <el-main>
                <Main></Main>
            </el-main>
        </el-container>
    </el-container>
</template>

<script setup>
    import Aside from "./componets/aside.vue"
    import Main from "./componets/main.vue"
    import breadcrumb from './componets/breadcrumb.vue'
    import { reactive, ref } from 'vue'
    import screenfull from 'screenfull'
    import { useDark, useToggle } from '@vueuse/core'
    import { useRouter } from 'vue-router'
    import  { ElMessage } from 'element-plus'
    import {userStore} from "@/store/user";


    const userstore = userStore();
    const router = useRouter();
    const collapse = ref(false); //控制展开缩放
    const icon = ref("Fold"); // 控制展开缩放的图标
    const pattern = ref(false);//控制模式
    const isDark = useDark()



    const aside = ()=>{
        collapse.value = !collapse.value
        if (collapse.value){
            icon.value = "Expand"
        }else {
            icon.value = "Fold"
        }
    }

    //全屏
    const screen = ()=>{
        screenfull.toggle()
    }
    //模式切换
    const toggleDark = useToggle(isDark)
    // 用户操作
    const user = (command)=>{
        if (command == 'b'){
            userstore.out();
            ElMessage({
                message: '登出成功!!!',
                type: 'success',
            })
            router.push("/login")
        }
    }


</script>

<style scoped>
    .cartoon{
        transition: width 0.3s ease-out;
    }
    .aside{
        width: 201px;
    }
    .aside-small{
        width: 70px;
    }
    :deep(.el-header){
        padding: 0;
        height: 50px;
    }
    :deep(.el-main){
        padding: 20px;
    }
    .header{
        width: 100%;
        display: flex;
        flex-direction: column;

    }
    .top{
        display:flex;
        align-items:center;
        border-bottom: 1px solid #cccccc;
        justify-content: space-between;
        height: 50px;
    }

    svg{
        width: 20px;
        margin-left: 20px;
    }
    .top-right{
        display: flex;
        margin-right: 20px;
    }
    .top-righ-item{
        margin-right: 20px;
    }
    .top-left{
        display: flex;
        align-items:center;
    }
    .breadcrumb{
        margin-left: 10px;
    }

    .tags{
        display: flex;
        margin-top:3px;
    }


</style>