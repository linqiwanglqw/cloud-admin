<template>
    <el-form  :inline="true"  label-width="68px" >
        <el-form-item label="表名称">
            <el-input placeholder="请输入菜单名称" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" :icon="Search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button :icon="Refresh" size="mini" @click="resetQuery">生成</el-button>
        </el-form-item>
    </el-form>


    <div style="padding-top: 10px">
        <el-table @selection-change="handleSelectionChange"  v-loading="loading" row-key="id" :data="list" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
            <el-table-column type="selection" width="55" />
            <el-table-column prop="tableName" label="表名称"  />
            <el-table-column prop="tableComment" label="备注"  />
            <el-table-column prop="ENGINE" label="引擎" />
            <el-table-column prop="createTime" label="日期"  />
        </el-table>
    </div>


</template>

<script setup  lang="ts">
    import { onMounted,reactive, ref } from 'vue';
    import {generateList,generateCode} from "../../api/sys/generate";


    const loading = ref(true);
    const type = ref();
    const list = ref([]);
    const treelist = reactive({arr:[]});
    const dialogVisible = ref(false);
    const title = ref('');
    const form = ref(null);
    const  tableList = reactive({arr:[]})

    const query = reactive({
        pageNum:1,
        pageSize:10,
        name:null,
    });

    const handleSelectionChange = (val) => {
        tableList.arr = val;
    }

    const resetQuery = ()=>{
        let list = tableList.arr.map(res=>{return res.tableName});

        if (list.length != 0){
         let tables =list.join(",")
            generateCode({"tables":tables}).then((res)=>{
                let blob = new Blob([res], {type: 'application/zip'})
                let url = window.URL.createObjectURL(blob)
                const link = document.createElement('a') // 创建a标签
                link.href = url
                link.download = 'lin' // 重命名文件
                link.click()
                URL.revokeObjectURL(url) // 释放内存
            })
        }

    }

    const handleQuery = ()=>{
        query.pageNum = 1;
        getlist()
    };

    const getlist = ()=>{
        loading.value = true
        generateList(query).then(res=>{
            list.value = res.data
            loading.value = false
        })
    }
    onMounted(()=>{
        getlist()
    })

</script>

<style scoped>

</style>