<template>
    <el-form :inline="true"  label-width="68px" >
        <el-form-item label="用户名">
            <el-input v-model="query.roleName" placeholder="请输入用户名" />
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
                    v-if="hasAuth('system:sysRole:add')"
            >新增</el-button>
        </el-col>
        <el-col :span="1.5">
            <el-button
                    type="success"
                    plain
                    :icon="Edit"
                    size="mini"
                    @click="handleUpdate"
                    v-if="hasAuth('system:sysRole:edit')"
            >修改</el-button>
        </el-col>
        <el-col :span="1.5">
            <el-button
                    type="danger"
                    plain
                    :icon="Delete"
                    size="mini">删除</el-button>
        </el-col>
    </el-row>

    <div style="padding-top: 10px">
        <el-table v-loading="loading" :data="list.arr" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
            <el-table-column prop="roleName" label="角色名称"  />
            <el-table-column prop="roleKey" label="角色介绍"  />
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button size="small"
                               type="success"
                               text
                               :icon="Edit"
                               @click="handleEdit(scope.row)"
                    >修改</el-button
                    >
                    <el-button
                            size="small"
                            type="danger"
                            text
                            :icon="Delete"
                            @click="handleDelete(scope.row)"
                            v-if="hasAuth('system:sysRole:remove')"
                    >删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <Pagination  :total="total" @pageChange="page"></Pagination>

        <el-dialog
                v-model="dialogVisible"
                :title="title"
                width="500px">
            <el-form ref="form"
                     :model="ruleForm"
                     :label-position="labelPosition"
                     label-width="80px"
                     :rules="rules">
                <el-form-item label="角色名称" prop="roleName">
                    <el-input v-model="ruleForm.roleName" placeholder="请输入角色名称" />
                </el-form-item>
                <el-form-item label="角色介绍" prop="roleKey">
                    <el-input v-model="ruleForm.roleKey" placeholder="请输入角色介绍"/>
                </el-form-item>
                <el-form-item label="菜单权限" prop="perms">
                    <el-tree
                            :default-checked-keys="treekeys"
                            ref="treeRef"
                            :data="tree.arr"
                            show-checkbox
                            :check-strictly="true"
                            default-expand-all
                            node-key="id"
                            highlight-current
                            :props="defaultProps"
                    />
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
    import { onMounted,reactive, ref,toRefs  } from 'vue';
    import type { FormInstance, FormRules } from 'element-plus'
    import Pagination from '@/componets/pagination.vue';
    import {roles,getroles,addrole,queryrole,editrole,deleterole} from "@/api/sys/role";
    import {menus} from "@/api/sys/menu";


    const defaultProps = {
        children: 'children',
        label: 'menuName',
    }
    const treekeys = ref([]);
    const treeRef = ref();
    const form = ref();
    const type = ref(1);
    const tree = reactive({arr:[]});
    const title = ref('');
    const dialogVisible = ref(false);
    const loading = ref(true);
    const labelPosition = ref('right');
    const total = ref(0);
    const ruleForm = reactive({});
    const query =reactive({
        roleName:'',
        pageNum:1,
        pageSize:10,
    });
    const list = reactive({arr:[]});

    const rules = reactive<FormRules>({
        roleName:[
            { required: true, message: '请输入用户名', trigger: 'blur' }
        ]
    })

    const submitForm = ()=>{
        //获取树形数据
       let treelist =  treeRef.value.getCheckedNodes();
        treelist = treelist.map(res=>{
            return res.id
        });
        ruleForm.treelist = treelist;
        form.value.validate(valid=>{
            if (valid){
                if (type.value ==1){
                    //添加
                    addrole(ruleForm).then(res=>{
                        getlist();
                        dialogVisible.value = false;
                    })
                }else {
                    editrole(ruleForm).then(res=>{
                        getlist();
                        dialogVisible.value = false;
                    })
                }
            }
        })
    }
    const page = (e)=>{
        query.pageNum = e.currentPage;
        query.pageSize = e.pageSize;
        getlist()
    }
    const handleEdit = (data)=>{
        title.value = '修改';
        dialogVisible.value = true;
        type.value = 2;
        getquery(data);
    }
    const handleDelete = (data)=>{
        deleterole(data.id).then(res=>{
            dialogVisible.value = false;
            getlist();
        })
    }

   async function getquery(data) {
      let item =  await queryrole(data.id)
       setform(item.data)
    }

    const setform = (data)=>{
        treeRef.value.setCheckedKeys([], false);
        ruleForm.id = data.itme.id;
        ruleForm.roleName = data.itme.roleName;
        ruleForm.roleKey = data.itme.roleKey;
        treekeys.value = data.list;
    }

    const reset = ()=>{
        ruleForm.roleName = null;
        ruleForm.roleKey = null;
    }
    const handleAdd = ()=>{
        title.value = '新增';
        dialogVisible.value = true;
        type.value = 1;
        treeRef.value.setCheckedKeys([], false);
        reset()
    }
    const getlist = ()=>{
        getroles(query).then(res=>{
            list.arr = res.data;
            total.value = res.total;
            loading.value = false;
        })
    };
    const getmenus = ()=>{
        menus().then(res=>{
            tree.arr =  res.data
        })
    }
    onMounted(()=>{
        getlist();
        getmenus();
    });
</script>

<style scoped>

</style>