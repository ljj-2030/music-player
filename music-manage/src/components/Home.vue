<template>
<div>
    <the-header></the-header>
    <the-aside></the-aside>
        <router-view class="content-box" :class="{'collapse':collapse}"/>
</div>
</template>
<script>
import TheAside from "../components/TheAside.vue"
import TheHeader from "../components/TheHeader.vue"
import bus from "../api/bus"
export default {
    data(){return{
       collapse:false,
    }},
    components:{
        TheAside,
        TheHeader,
    },created(){
        bus.$on("collapse",msg=>{
            this.collapse=msg;
        })


    }
}
</script>
<style scoped>
    .collapse{
        left: 65px;
    }
</style>