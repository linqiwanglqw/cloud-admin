<!--主页面-->
<template>
        <Transition name="slide-fade">
            <router-view></router-view>
        </Transition>
</template>

<script>

</script>

<style scoped>
    /*
      进入和离开动画可以使用不同
      持续时间和速度曲线。
    */
    .slide-fade-enter-active {
        transition: all 0.5s ease-out;
    }

    .slide-fade-leave-active {
        transition: all 0.5s cubic-bezier(1, 0.5, 0.8, 1);
    }

    .slide-fade-enter-from,
    .slide-fade-leave-to {
        transform: translateX(20px);
        opacity: 0;
    }

</style>