<template>
        <div class="header">

                <!--折叠图片-->
            <div class="collapse-btn" @click="collapseChange">
                    <i class="el-icon-menu"></i>
            </div>
            <div class="logo">music后台管理</div>

            <div class="header-right">
                    <div class="btn-screen" @click="handleFullScreen">
                        <el-tooltip :content="fullScreen ?'取消全屏':'全屏'" placement="bottom">
                        <i class="el-icon-rank"></i>
                        </el-tooltip>
                    </div>
                    <div class="user-avator">
                        <img src="../assets/image/img.png" alt="">
                    </div>
                    <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                        <span class="el-dropdown-link">
                            {{username}}
                        <i class="el-icon-caret-bottom"></i>

                        </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
            </div>
        </div>
</template>
<script>
import bus from '../api/bus'
export default {
    data(){return{
            collapse:false,
            fullScreen:false,
    }},
    computed:{
            username(){
                return localStorage.getItem("name")
            }
    },
    methods:{
        //退出登录事件
        handleCommand(command){
                    if(command==='logout'){
                        localStorage.removeItem("name")
                        this.$router.push("/")
                    }
        },
        collapseChange(){
            this.collapse=!this.collapse;
            bus.$emit("collapse",this.collapse)
        },
        //全屏事件
        handleFullScreen(){
            if(this.fullScreen){
                if(document.exitFullscreen){
                    document.exitFullscreen()
                }else if(document.webkitCancelFullScreen){
                    document.webkitCancelFullScreen()
                }else if(document.mozCancelFullScreen){
                    document.mozCancelFullScreen()
                }
            }else{
                let element=document.documentElement
                if(element.requestFullscreen){
                    element.requestFullscreen()
                }else if(element.webkitRequestFullScreen){
                    element.webkitRequestFullScreen()
                }else if(element.mozRequestFullScreen){
                    element.mozRequestFullScreen()
                }
            }
            this.fullScreen=!this.fullScreen
        }
    }
}
</script>
<style scoped>
    .header{
        position: relative;
        background-color: #253041;
        box-sizing: border-box;
        width: 100%;
        height: 70px;
        color:#fff;
        font-size: 22px;
    }
    .collapse-btn{
        float: left;
        padding: 0px 20px;
        line-height: 70px;
        cursor: pointer;
    }
    .logo{
        line-height: 70px;
        font-weight: bold;
        float: left;
        /* border: 1px solid white; */
        text-align: center;
    }
    .header-right{
        float: right;
        padding-right: 50px;
        display: flex;
        height: 70px;
        align-items: center;
    }
    .btn-screen{
        font-size: 24px;
        margin-right: 5px;
        transform: rotate(45deg);
    }
    .user-avator{
        margin-left: 20px;
    }
    .user-avator>img{
        width: 40px;
        height: 40px;
        border-radius: 50%;
    }
    .user-name{
        margin-left: 10px;
    }
    .el-dropdown-link{
            color: #fff;
            cursor: pointer;
    }
</style>