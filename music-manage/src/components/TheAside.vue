<template>
        <div class="sidebar">
              <el-menu
      :default-active="defaultPath"   
      active-text-color="blue"
      :collapse="collapse"
      text-color="white"
      background-color="#332456"
      class="sidebar-el-menu"
      router
      >
    <template v-for="(item,index) in items">
         <el-menu-item :index="item.index" :key="index">
        <i :class="item.icon"></i>
        {{item.title}}
             </el-menu-item>

    </template>

        
    
    
    </el-menu>
        </div>
</template>
<script>
import bus from "../api/bus"
export default {
    data(){return{
        collapse:false,
        items:[
            {
                icon:"el-icon-document",
                index:"Info",
                title:"系统首页"
            },{
                icon:"el-icon-document",
                index:"consumer",
                title:"用户管理"
            },{
                icon:"el-icon-document",
                index:"singer",
                title:"歌手管理"
            },{
                icon:"el-icon-document",
                index:"songlist",
                title:"歌单管理"
            },
        ]
    }},
    computed:{
        defaultPath(){
            
            return this.items[0].index;
        }
},
created(){
    bus.$on("collapse",msg=>{
        this.collapse=msg;
        console.log(this.collapse)
    })
}


}
</script>
<style scoped>
    .sidebar{
        display: block;
        position: absolute;
        left: 0;
        top: 70px;
        bottom: 0;
        background-color: #334526;
    }

 .sidebar-el-menu:not(.el-menu--collapse){
     width: 150px;
 }
.sidebar>ul{
    height: 100%;
}
 .sidebar{
     overflow-x: hidden;
 }
</style>