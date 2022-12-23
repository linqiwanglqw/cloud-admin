<template>
    <div style="margin-top: 20px">
        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageParams.currentPage"
                :page-size="pageParams.pagesize"
                :page-sizes="pageSizes"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total"
                background="true"
                :hide-on-single-page="true"
        ></el-pagination>
    </div>
</template>

<script setup>
    import { ref, reactive, defineEmits, defineProps } from 'vue';
    const emits  = defineEmits(['pageChange']);
    const props = defineProps({
        total: {
            type: Number,
            default: ''
        },
    })

    const pageParams = reactive({
        currentPage: 1,
        pageSize: 10,
    })

    const pageSizes = ref([10,20,30,100]);

    //改变每页展示的数据条数
    const handleSizeChange = (val)=>{
        pageParams.pageSize = val;
        emits('pageChange', pageParams);
    }
    //改变当前页的页码
    const handleCurrentChange = (val)=>{
        pageParams.currentPage = val;
        emits('pageChange', pageParams);
    }


</script>

<style scoped>

</style>