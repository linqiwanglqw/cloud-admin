<template>
    <el-form  :inline="true"  label-width="68px" >
        <el-form-item label="菜单名称">
            <el-input placeholder="请输入菜单名称" />
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
                    v-if="hasAuth('system:sysMenu:add')"
            >新增</el-button>
        </el-col>
        <el-col :span="1.5">
            <el-button
                    type="success"
                    plain
                    :icon="Edit"
                    size="mini"
                    @click="handleUpdate"
                    v-if="hasAuth('system:sysMenu:edit')"
            >修改</el-button>
        </el-col>
        <el-col :span="1.5">
            <el-button
                    type="danger"
                    plain
                    :icon="Delete"
                    size="mini"
                    v-if="hasAuth('system:sysMenu:remove')"
            >删除</el-button>
        </el-col>
    </el-row>
    <div style="padding-top: 10px">
        <el-table  v-loading="loading" row-key="id" :data="list" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
            <el-table-column prop="menuName" label="菜单名称"  />
            <el-table-column prop="path" label="路径"  />
            <el-table-column prop="menuType" label="类型">
                <template #default="scope">
                    <el-tag v-if="scope.row.menuType == 'M'" class="ml-2">目录</el-tag>
                    <el-tag v-if="scope.row.menuType == 'C'" class="ml-2" type="success">菜单</el-tag>
                    <el-tag v-if="scope.row.menuType == 'F'" class="ml-2" type="danger">按钮</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="perms" label="权限信息"  />
            <el-table-column prop="icon" label="图标">
            </el-table-column>
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button size="small"
                               type="success"
                               text
                               :icon="Edit"
                               @click="handleEdit(scope.row)"
                               v-if="hasAuth('system:sysMenu:edit')">修改</el-button>
                    <el-button
                            size="small"
                            type="danger"
                            text
                            :icon="Delete"
                            @click="handleDelete(scope.row)"
                            v-if="hasAuth('system:sysMenu:remove')">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog
                v-model="dialogVisible"
                :title="title"
                width="500px">
            <el-form ref="form"
                     :model="ruleForm"
                     :label-position="labelPosition"
                     label-width="80px"
                     :rules="rules">
                <el-form-item label="上级菜单" prop="parentId">
                    <el-tree-select
                            clearable
                            placeholder="请选择上级菜单"
                            :props="props"
                            v-model="ruleForm.parentId"
                            :data="treelist.arr"
                            check-strictly
                            :render-after-expand="false"
                    />
                </el-form-item>
                <el-form-item label="菜单类型" prop="menuType">
                    <el-radio-group v-model="ruleForm.menuType" class="ml-4">
                        <el-radio label="M">目录</el-radio>
                        <el-radio label="C">菜单</el-radio>
                        <el-radio label="F">按钮</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="菜单图标" prop="icon" v-if="ruleForm.menuType != 'F' ">
                    <el-input v-model="ruleForm.icon" placeholder="请输入菜单图标" />
                </el-form-item>
                <el-form-item label="菜单名称" prop="menuName">
                    <el-input v-model="ruleForm.menuName" placeholder="请输入菜单名称" />
                </el-form-item>
                <el-form-item label="路由地址" prop="path" v-if="ruleForm.menuType == 'C' ">
                    <el-input v-model="ruleForm.path" placeholder="请输入路由地址" />
                </el-form-item>
                <el-form-item label="组件路径" prop="component" v-if="ruleForm.menuType == 'C'">
                    <el-input v-model="ruleForm.component" placeholder="请输入组件路径" />
                </el-form-item>
                <el-form-item label="权限字符" prop="perms" v-if="ruleForm.menuType != 'M'">
                    <el-input v-model="ruleForm.perms" placeholder="请输入权限字符" />
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
    import {menus,addmenu,editmenu,deletemenu} from "@/api/sys/menu";
    import { onMounted,reactive, ref } from 'vue';
    import type { FormInstance, FormRules } from 'element-plus'

    const loading = ref(true);
    const type = ref();
    const list = ref([]);
    const treelist = reactive({arr:[]});
    const dialogVisible = ref(false);
    const title = ref('');
    const form = ref(null);
    const ruleForm  = reactive({
        menuType:'M'
    })
    const labelPosition = ref('right');
    const props = {  label: 'menuName',value:'id'}


    const rules = reactive<FormRules>({
        parentId:[
            { required: true, message: '请选择上级菜单', trigger: 'blur' }
        ],
        menuType:[
            { required: true, message: '请选择菜单类型', trigger: 'blur' }
        ]
    })

    const query = reactive({
        menuName:null,
    });


    const reset = ()=>{
        ruleForm.id = null;
        ruleForm.parentId = null;
        ruleForm.menuType = 'M';
        ruleForm.icon = null;
        ruleForm.menuName = null;
        ruleForm.path = null;
        ruleForm.component = null;
        ruleForm.perms = null;
    }

    const handleAdd = ()=>{
       dialogVisible.value = true;
       title.value = '添加';
       type.value = 1;
       reset();
    }

    const addform = (data)=>{
        ruleForm.id = data.id;
        ruleForm.parentId = data.parentId;
        ruleForm.menuType = data.menuType;
        ruleForm.icon = data.icon;
        ruleForm.menuName = data.menuName;
        ruleForm.path = data.path;
        ruleForm.component = data.component;
        ruleForm.perms = data.perms;
    }
    const handleEdit = (data)=>{
        dialogVisible.value = true;
        title.value = '修改';
        type.value = 2;
        addform(data);
    }
    const handleDelete = (data)=>{
        deletemenu(data.id).then(res=>{
            dialogVisible.value = false;
            getlist();
        })
    }

    const handleQuery = ()=>{
        getlist()
    }
    const submitForm = ()=>{
        form.value.validate(valid=>{
            if (valid){
                if (type.value == 1){
                    //添加
                    addmenu(ruleForm).then(res=>{
                        dialogVisible.value = false;
                        getlist();
                    })
                }else {
                    //修改
                    editmenu(ruleForm).then(res=>{
                        dialogVisible.value = false;
                        getlist();
                    })
                }
            }
        })
    }

    const getlist = ()=>{
        loading.value = true
        menus(query).then(res=>{
            list.value = res.data
            treelist.arr = [{id:0,menuName:'主菜单',children:[]}]
            treelist.arr[0].children = res.data
            loading.value = false
        })
    }
    onMounted(()=>{
        getlist()
    })

</script>

<style scoped>

</style>