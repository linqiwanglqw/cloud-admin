<template>
    <div class="cont">
        <el-form
                ref="ruleFormRef"
                :model="ruleForm"
                :rules="rules"
                class="login-form"
                status-icon>
            <h3 class="title">登录</h3>
            <el-form-item  prop="username">
                <el-input class="input-with-select" 
                v-model="ruleForm.username" 
                placeholder="请输入账号"
                clearable>
                    <template #prefix>
                        <el-icon><User /></el-icon>
                    </template>
                </el-input>
            </el-form-item>
            <el-form-item  prop="password">
                <el-input
                        type="password"
                        clearable
                        v-model="ruleForm.password"
                        placeholder="请输入密码"
                        class="input-with-select">
                    <template #prefix>
                        <el-icon><Lock /></el-icon>
                    </template>
                </el-input>
            </el-form-item>

            <el-form-item  prop="code">

                <div class="login-code">
                    <el-input
                            clearable
                            v-model="ruleForm.code"
                            placeholder="验证码"
                    >
                    </el-input>
                    <img :src="image" @click="getimage">
                </div>
            </el-form-item>

            <el-form-item>
                <el-button :loading="load" type="primary" @click="submitForm(ruleFormRef)" style="width: 100%">登录</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script lang="ts" setup>
    import { onMounted,reactive, ref } from 'vue'
    import { User,Unlock } from '@element-plus/icons-vue'
    import type { FormInstance, FormRules,ElMessage } from 'element-plus'
    import {getcode} from "@/api/login"
    import {userStore} from "@/store/user";
    import {routerStore} from "@/store/router";
    import { useRouter } from 'vue-router'
    import { login} from "@/api/login";
    import {getRouters} from "@/api/router";



    const router = useRouter();
    const formSize = ref('default');
    const ruleFormRef = ref<FormInstance>();
    const userstore = userStore();
    const routerstore = routerStore();


    const load = ref(false);
    const image = ref('');
    const ruleForm = reactive({
        username:'',
        password:'',
        code:'',
        uuid:'',
    })

    const rules = reactive<FormRules>({
        username: [
            { required: true, message: '用户名不能为空', trigger: 'blur' },
        ],
        password: [
            { required: true, message: '密码不能为空', trigger: 'blur' },
        ],
        code: [
            { required: true, message: '验证码不能为空', trigger: 'blur' },
        ],
    })




    const submitForm = async (formEl: FormInstance | undefined) => {
        if (!formEl) return
        await formEl.validate((valid, fields) => {
            if (valid) {
                    load.value = true
                    userstore.login(ruleForm).then(res=>{
                        routerstore.isrouters = false;
                        router.replace("/");
                    }).catch(err=>{
                        load.value = false
                        getimage();
                    })
            } else {
                ElMessage.error('有数据未填写')
            }
        })
    }

    const getimage = ()=>{
           getcode().then(res=>{
            image.value = "data:image/gif;base64,"+res.data.img
            ruleForm.uuid = res.data.uuid
        })
    }

    onMounted(()=>{
        getimage();
    })



</script>

<style scoped>
.cont{
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    background-image: url("../../assets/bg1.png");

    background-repeat: no-repeat;
    overflow: hidden;
    background-size: cover;
}
    .login-form{
        border-radius: 6px;
        background: #ffffff;
        width: 300px;
        padding: 25px 25px 5px 25px;
    }

.title {
    margin: 0px auto 30px auto;
    text-align: center;
    color: #707070;
}
.login-code {
    display: flex;
}

.login-code>img{
    height: 35px;
    margin-left: 10px;
}
</style>
