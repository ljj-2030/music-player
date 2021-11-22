<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-button type="primary" size="mini" class="handle-del mr10" @click="delAll">批量删除</el-button>
        <el-input v-model="select_word" size="mini" placeholder="筛选关键词" class="handle-input mr10"></el-input>
        <el-button type="primary" size="mini" @click="centerDialogVisible = true">添加用户</el-button>
      </div>
      <el-table ref="multipleTable" size="mini" border style="width: 100%" height="550px" :data="data" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="40"></el-table-column>
        <el-table-column label="用户图片" width="110" align="center">
          <template slot-scope="scope">
            <div class="singer-img">
              <img :src="getUrl(scope.row.avator)" alt="" style="width: 100%;"/>
            </div>
            <el-upload
              class="upload-demo"
              :action="uploadUrl(scope.row.id)"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
              >
              <el-button size="mini">更新图片</el-button>
            </el-upload>
          </template>
        </el-table-column>
        <el-table-column prop="username" label="用户名" width="120" align="center"></el-table-column>
        <el-table-column label="性别" width="50" align="center">
          <template slot-scope="scope">
            <div>{{changeSex(scope.row.sex) }}</div>
          </template>
        </el-table-column>
      <el-table-column label="手机号" width="100" align="center" prop="phoneNum"></el-table-column>
      <el-table-column label="电子邮件" width="130" align="center" prop="email"></el-table-column>
        <el-table-column label="生日" width="120" align="center">
          <template slot-scope="scope">
              <div>{{attachBirth(scope.row.birth) }}</div>
          </template>
        </el-table-column>
      <el-table-column label="签名" width="170" align="center" prop="introduction"></el-table-column>
        <el-table-column prop="location" label="地区" width="150" align="center"></el-table-column>
        <el-table-column label="收藏" width="80" align="center">
          <template slot-scope="scope">
          <el-button size="mini" @click="getCollect(scope.row)">收藏</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="scope">
            <div style="display:flex;justify-content:space-around;">
            <el-button size="mini" @click="handleEdit(scope.row)" type="primary">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          @current-change="handleCurrentChange"
          background
          layout="total, prev, pager, next"
          :current-page="currentPage"
          :page-size="pageSize"
          :total="tableData.length">
        </el-pagination>
      </div>
    </div>

    <el-dialog title="添加用户" :visible.sync="centerDialogVisible" width="400px" center>
      <el-form
        :model="registerForm"
        ref="registerForm"
        label-width="80px"
        class="demo-ruleForm"
        :rules="rules"
        >
        <el-form-item prop="username" label="用户名" size="mini">
          <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
        </el-form-item>
         <el-form-item prop="password" label="密码" size="mini">
          <el-input v-model="registerForm.password" type="password" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item label="性别" size="mini">
          <el-radio-group v-model="registerForm.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-input v-model="registerForm.location" placeholder="地区"></el-input>
        </el-form-item>
        <el-form-item prop="birth" label="出生" size="mini">
          <el-date-picker
            type="date"
            placeholder="选择日期"
            v-model="registerForm.birth"
            style="width: 100%;"
          ></el-date-picker>
        </el-form-item>
           <el-form-item prop="phoneNum" label="手机号" size="mini">
          <el-input v-model="registerForm.phoneNum"  placeholder="手机号"></el-input>
        </el-form-item>
           <el-form-item prop="email" label="电子邮件" size="mini">
          <el-input v-model="registerForm.email"  placeholder="电子邮件"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="签名" size="mini">
          <el-input v-model="registerForm.introduction"  placeholder="签名"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="centerDialogVisible = false" type="primary">取 消</el-button>
        <el-button type="primary" size="mini" @click="addConsumer">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑用户" :visible.sync="editVisible" width="400px">
      <el-form :rules="rules" ref="form" :model="form" label-width="60px">
          <el-form-item prop="username" label="用户名" size="mini">
          <el-input v-model="form.username" placeholder="用户名"></el-input>
        </el-form-item>
         <el-form-item prop="password" label="密码" size="mini">
          <el-input v-model="form.password" type="password" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item label="性别" size="mini">
          <el-radio-group v-model="form.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-input v-model="form.location" placeholder="地区"></el-input>
        </el-form-item>
        <el-form-item prop="birth" label="出生" size="mini">
          <el-date-picker
            type="date"
            placeholder="选择日期"
            v-model="form.birth"
            style="width: 100%;"
          ></el-date-picker>
        </el-form-item>
           <el-form-item prop="phoneNum" label="手机号" size="mini">
          <el-input v-model="form.phoneNum"  placeholder="手机号"></el-input>
        </el-form-item>
           <el-form-item prop="email" label="电子邮件" size="mini">
          <el-input v-model="form.email"  placeholder="电子邮件"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="签名" size="mini">
          <el-input v-model="form.introduction"  placeholder="签名"></el-input>
        </el-form-item>



       </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini"  @click="editVisible = false">取 消</el-button>
        <el-button type="primary" size="mini" @click="saveEdit">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 删除提示框 -->
    <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
      <div class="del-dialog-cnt" align="center">删除不可恢复，是否确定删除？</div>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="delVisible = false" type="primary">取 消</el-button>
        <el-button type="primary" size="mini" @click="deleteRow">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { mixin } from '../mixins'
import {addConsumer,updateConsumer,getAllConsumer,deleteConsumerById} from "../api/index"
export default {
  name: 'singer-page',
  mixins: [mixin],
  data () {
    return {
      registerForm: {
        // 添加框信息
        username:'',
        password:"",
        sex:1,
        phoneNum:'',
        email:'',
        birth:'',
        introduction:'',
        location:'',
      },
      tableData: [],
      tempDate: [],
      multipleSelection: [],
      centerDialogVisible: false,
      editVisible: false,
      delVisible: false,
      select_word: '',
      form: {
        // 编辑框信息
        id: '',
        username:'',
        password:"",
        sex:'',
        phoneNum:'',
        email:'',
        birth:'',
        introduction:'',
        location:'',
      },
      rules:{//校验
      username:[{required:true,message:"用户名不能为空",trigger:"blur"}],
      password:[{required:true,message:"密码不能为空",trigger:"blur"}],
      phoneNum:[{required:true,message:"手机号",trigger:"blur"}],
      email:[{required:true,message:"电子邮箱不能为空",trigger:"blur"},
      {type:"email",message:"请输入正确格式的电子邮箱",trigger:['blur','change']}],
      introduction:[{required:true,message:"请输入签名",trigger:"blur"}],
      location:[{required:true,message:"请输入地区",trigger:"blur"}],



      },
      pageSize: 5, // 页数
      currentPage: 1, // 当前页
      idx: -1
    }
  },
  computed: {
    // 计算当前表格中的数据
    data () {
      return this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    }
  },
  watch: {
    select_word: function () {
      if (this.select_word === '') {
        this.tableData = this.tempDate
      } else {
        this.tableData = []
        for (let item of this.tempDate) {
          if (item.username.includes(this.select_word)) {
            this.tableData.push(item)
          }
        }
      }
    }
  },
  created () {
    this.getData()
  },
  methods: {
    // 获取当前页
    handleCurrentChange (val) {
      this.currentPage = val
    },
    uploadUrl (id) {
      return this.$store.state.HOST+`/consumer/updateConsumerPic?id=`+id;
    },
    // 添加用户
    addConsumer () {
      this.$refs.registerForm.validate(vali=>{
        if(vali){
           let d = this.registerForm.birth
      let datetime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
      let params = new FormData();
      params.append('username', this.registerForm.username)
      params.append("password",this.registerForm.password)
      params.append('sex', this.registerForm.sex)
      params.append('phoneNum', this.registerForm.phoneNum)
      params.append('email', this.registerForm.email)
      params.append('avator', '/img/singerPic/hhh.jpg')
      params.append('birth', datetime)
      params.append('location', this.registerForm.location)
      params.append('introduction', this.registerForm.introduction)
      addConsumer(params,{}).then(r=>{
        if(r===true){
          this.$message.success("添加成功!")
        }else{
          this.$message.error("添加失败")
        }
      this.getData();

      })
      this.centerDialogVisible = false
        }else{
         
        }
      })
     
    },
    // 获取用户
    getData () {
      this.tableData = []
      this.tempDate = []
      getAllConsumer().then(r=>{
          this.tableData=r;
          this.tempDate=r;
      })
     
    },
    // 编辑
    handleEdit (row) {
      this.editVisible = true
      this.form = {
        id:row.id,
        username:row.username,
        password:row.password,
        sex:row.sex,
        phoneNum:row.phoneNum,
        email:row.email,
        birth:row.birth,
        introduction:row.introduction,
        location:row.location,
      }
    },
    // 保存编辑
    saveEdit () {
      this.$refs.form.validate(vali=>{
        if(vali){
let d = this.form.birth
    //   let datetime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
      let params = new FormData();

      params.append('username', this.form.username)
      params.append('id', this.form.id)
      params.append("password",this.form.password)
      params.append('sex', this.form.sex)
      params.append('phoneNum', this.form.phoneNum)
      params.append('email', this.form.email)
      params.append('birth', d)
      params.append('location', this.form.location)
      params.append('introduction', this.form.introduction)
      /**
       * 更新接口
       */
        updateConsumer(params).then(r=>{
          if(r===true){
            this.$message.success("修改成功!")
          }else{
            this.$message.error("修改失败!")
          }
      this.getData();
        })
      console.log(this.currentPage,'当前页数')
      this.editVisible = false
        }
      })
      
    },

    //删除
    handleDelete(id){
          /**
       * 弹出是否框，然后操作
       */
      this.$confirm('此操作将删除该条数据 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'error',
          }).then(() => {
            //点击确定的操作(调用接口)
            this.deleteRow(id);
          }).catch(() => {
            //几点取消的提示
            this.$message.error("取消删除了!")
          });
        
    },
    // 确定删除
    deleteRow (id) {
        deleteConsumerById(id).then(r=>{
            if(r===true){
                this.$message.success("删除成功!")
            }else{
                this.$message.error("删除失败!")
            }
        })
      this.getData();
      this.delVisible = false
    },
    delAll(){
           /**
       * 弹出是否框，然后操作
       */
      this.$confirm('此操作将删除该条数据 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'error',
          }).then(() => {
            //点击确定的操作(调用接口)
            for(let item of this.multipleSelection){
              this.deleteRow(item.id)
            }


          }).catch(() => {
            //几点取消的提示
            this.$message.error("取消删除了!")
          });




    },
    songEdit (id, name) {
      this.$router.push({path: `song`, query: {id: id, name: name}})
    },
    //转向用户的收藏列表
    getCollect(item){
      this.$router.push({
        path:"collect?userId="+item.id,
      })
    }
  }
}
</script>

<style>
.handle-box {
  margin-bottom: 20px;
}

.handle-input {
  width: 300px;
  display: inline-block;
}

.singer-img {
  width: 100%;
  height: 80px;
  border-radius: 5px;
  margin-bottom: 5px;
  overflow: hidden;
}

.pagination {
  display: flex;
  justify-content: center;
}
.el-button--default{
  background-color: #409EFF;
}
.lay{
  display: flex;
}
</style>