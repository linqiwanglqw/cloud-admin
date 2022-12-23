<template>
    <el-form  :inline="true"  label-width="68px" >
        <el-form-item label="用户名">
            <el-input v-model="query.userName" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" :icon="Search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button :icon="Refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
    </el-form>

    <el-row :gutter="10">
        <el-col :span="1.5">
            <el-button
                    type="primary"
                    plain
                    :icon="Plus"
                    size="mini"
                    @click="handleAdd"
                    v-if="hasAuth('system:sysUser:add')"
            >新增</el-button>
        </el-col>
        <el-col :span="1.5">
            <el-button
                    type="success"
                    plain
                    :icon="Edit"
                    size="mini"
                    @click="handleUpdate"
                    v-if="hasAuth('system:sysUser:edit')"
            >修改</el-button>
        </el-col>
        <el-col :span="1.5">
            <el-button
                    type="danger"
                    plain
                    :icon="Delete"
                    size="mini"
                    v-if="hasAuth('system:sysUser:remove')"
            >删除</el-button>
        </el-col>
    </el-row>
    <div style="padding-top: 10px">
        <el-table v-loading="loading" :data="list" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
            <el-table-column prop="nickName" label="姓名"  />
            <el-table-column prop="email" label="电子邮件"  />
            <el-table-column prop="userName" label="用户名"  />
            <el-table-column prop="phonenumber" label="手机号"  />
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button size="small"
                               type="success"
                               text
                               :icon="Edit"
                               @click="handleEdit(scope.row)"
                               v-if="hasAuth('system:sysUser:edit')"
                    >修改</el-button>
                    <el-button
                            size="small"
                            type="danger"
                            text
                            :icon="Delete"
                            @click="handleDelete(scope.row)"
                            v-if="hasAuth('system:sysUser:remove')">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <Pagination  :total="total" @pageChange="page"></Pagination>

        <el-dialog
                v-model="dialogVisible"
                :title="title"
                width="500px">
           <el-form  ref="form"
                     :rules="rules"
                     :label-position="labelPosition"
                     label-width="80px"
                     :model="ruleForm" >

                <el-form-item label="用户名" prop="userName">
                    <el-input v-model="ruleForm.userName" />
                </el-form-item>
                <el-form-item label="密码" prop="password" v-if="type==1">
                    <el-input v-model="ruleForm.password"  type="password"/>
                </el-form-item>
                <el-form-item label="电子邮件" prop="email">
                    <el-input v-model="ruleForm.email" />
                </el-form-item>
                <el-form-item label="手机号" prop="phonenumber">
                    <el-input v-model="ruleForm.phonenumber" />
                </el-form-item>
                <el-form-item label="昵称" prop="nickName">
                    <el-input v-model="ruleForm.nickName" />
                </el-form-item>
               <el-form-item label="角色" prop="role">
                   <el-select v-model="ruleForm.role" clearable placeholder="请选择角色">
                       <el-option
                               v-for="item in options"
                               :key="item.value"
                               :label="item.label"
                               :value="item.value"
                       />
                   </el-select>
               </el-form-item>
            </el-form>

      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </span>
      </template>
      </el-dialog>


    </div>
</template>

<script setup  lang="ts">
    import { Search,Refresh,Plus,Edit,Delete } from '@element-plus/icons-vue'
    import { onMounted,reactive, ref } from 'vue';
    import type { FormInstance, FormRules } from 'element-plus'
    import Pagination from '@/componets/pagination.vue';
    import {listUser,adduser,edituser,deleteuser} from "@/api/sys/user";
    import {roles} from "@/api/sys/role";


    const options = ref([]);
    const type = ref(1);
    const form = ref(null);
    const list = ref([]);
    const total = ref(0);
    const loading = ref(true);
    const title = ref("");
    const labelPosition = ref('right');
    const dialogVisible = ref(false);
    const ruleForm  = reactive({
        userName:null,
        nickName:null,
        email:null,
        phonenumber:null,
        role:null,
    })
    const query = reactive({
        pageNum:1,
        pageSize:10,
        userName:null,
    });

    const rules = reactive<FormRules>({
        userName: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        password:[
            { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        role:[
        { required: true, message: '请选择角色', trigger: 'blur' },
        ]
    })




    const handleQuery = ()=>{
        query.pageNum = 1;
        getlist()
    };

    const page = (e)=>{
        query.pageNum = e.currentPage;
        query.pageSize = e.pageSize;
        getlist()
    }

    const getlist = ()=>{
        loading.value = true;
        listUser(query).then(res=>{
            
            list.value = res.data;
            total.value = res.total;
            loading.value = false;
        })
    }
    const reset = ()=>{
        ruleForm.id = null;
        ruleForm.userName = null;
        ruleForm.password = '';
        ruleForm.email = null;
        ruleForm.phonenumber = null;
        ruleForm.nickName = null;
        ruleForm.role = null;
    }

    const getform = (data)=>{
        
        ruleForm.id = data.id;
        ruleForm.userName = data.userName;
        ruleForm.email = data.email;
        ruleForm.phonenumber = data.phonenumber;
        ruleForm.nickName = data.nickName;
        ruleForm.role = data.role;
    }

    const handleEdit = (data)=>{
        title.value = "修改";
        dialogVisible.value = true;
        type.value = 2;
        getform(data)
    }

    const handleDelete = (data)=>{
        deleteuser(data.id).then(res=>{
            getlist();
            dialogVisible.value = false;
        })
    }

    const handleAdd = ()=>{
        title.value = "添加";
        dialogVisible.value = true;
        type.value = 1;
        reset();
    }
    const submitForm = () => {
        form.value.validate(valid=>{
            if (valid){
                 if (type.value == 1){
                     //添加
                     adduser(ruleForm).then(res=>{
                         getlist();
                         dialogVisible.value = false;
                     })
                 }else {
                     //修改
                     edituser(ruleForm).then(res=>{
                         getlist();
                         dialogVisible.value = false;
                     })
                 }
            }
        })
    }
    const getroles = () =>{
        roles().then(res=>{
            options.value =  res.data
        })
    }

    onMounted(()=>{
        getlist();
        getroles();
    })

</script>

<style scoped>

</style>