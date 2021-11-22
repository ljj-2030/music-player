<template>
  <div class="login-wrap">
    <div class="ms-title">后台登陆管理</div>
    <div class="ms-login">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="ruleForm">
          <el-form-item prop="username">
            <el-input   v-model="ruleForm.username" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input  v-model="ruleForm.password" type="password" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-button type="primary" style="width: 100%" @click="submitForm">登录</el-button>

        </el-form>
     </div>
  </div>
</template>
<script>
import{getLoginStatus} from "../api/index"
 export default{
        data(){return{
      ruleForm:{
        username:"admin",
        password:"123"
      },
      rules:{
        username:[{required:true,message:"请输入用户名",trigger:"blur"}],
        password:[{required:true,message:"请输入密码",trigger:'blur'}],
      }
    }  
  },
  methods:{
    submitForm(){
        const data={name:this.ruleForm.username,password:this.ruleForm.password}
      getLoginStatus(data).then(r=>{
        localStorage.setItem("name",this.ruleForm.username)
       this.$router.push({
         path:"/home"
       })
       
       this.$message.success(r.msg)
        }).catch(e=>{
        this.$message.error(e.msg)
        })
    }
  }
 }   
</script>

<style scoped>
.login-wrap {
  position: relative;
  background: url('../assets/img/background.jpg');
  background-attachment: fixed;
  background-position: center;
  background-size: cover;
  width: 100%;
  height: 100%;
}
.ms-title {
  text-align: center;
  color: #fff;
  width: 100%;
  position: absolute;
  top:50%;
  margin-top: -230px;
  font-size: 30px;
}
.ms-login {
position: absolute;
  background-color: white;
  top: 50%;
  left: 50%;
  width: 300px;
  height: 200px;
  margin-top: -160px;
  margin-left: -150px;
  border-radius: 20px;
  background-clip: border-box;
  box-shadow: 0px 0px 50px white;
}
.login-btn {
  text-align: center;
}
.login-btn button {
  width: 100%;
  height: 36px;
}
.ruleForm{
  padding: 20px 30px 10px 30px;
}
</style>
