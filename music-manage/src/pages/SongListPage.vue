<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-input class="handle-input" v-model="select_word" size="mini" placeholder="请输入歌单名称"></el-input>
                <el-button type="primary" size="mini" @click="searchSinger">搜索</el-button><br/>
                <el-button type="primary" size="mini" @click="dialogFormVisible=true">添加歌单</el-button>
                <el-button type="danger" size="mini" @click="deleteAll">批量删除</el-button>
            </div>
        </div>

        <el-dialog title="添加歌单" :visible.sync="dialogFormVisible" width="400px" center="">
  <el-form :model="registerForm" label-width="50px" id="tf">
            <el-form-item prop="title" label="标题" size="mini" style="font-size:5px">
                <el-input v-model="registerForm.title" placeholder="标题"/>
            </el-form-item>
             <el-form-item prop="introduction" label="介绍" size="mini">
                 <el-input type="textarea" v-model="registerForm.introduction" placeholder="歌曲介绍">
                 </el-input>
            </el-form-item>
             <el-form-item prop="style" label="类型" size="mini">
                 <el-input v-model="registerForm.style" placeholder="歌单类型">
                 </el-input>
            </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false" type="primary">取 消</el-button>
    <el-button type="primary" @click="addSongList">确 定</el-button>
  </div>
</el-dialog>
<!--修改页面-->

        <el-dialog title="编辑歌单" :visible.sync="dialogEditVisible" width="400px" center="">
             <el-form :model="editForm" label-width="50px" id="tf">
            <el-form-item prop="title" label="标题" size="mini" style="font-size:5px">
                <el-input v-model.lazy="editForm.title" placeholder="歌单"/>            </el-form-item>
             <el-form-item prop="introduction" label="介绍" size="mini">
                 <el-input v-model.lazy="editForm.introduction" type="textarea" placeholder="歌单介绍">
                 </el-input>
            </el-form-item>
             <el-form-item prop="style" label="风格" size="mini">
                 <el-input v-model.lazy="editForm.style" placeholder="歌单风格">
                 </el-input>
            </el-form-item>
            
    </el-form>

  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogEditVisible = false">取 消</el-button>
    <el-button type="primary" @click="updateSongList">确 定</el-button>
  </div>
</el-dialog>
<el-table ref="multipleTable" size="mini" border style="width: 100%" height="550px" :data="data" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="40">
        </el-table-column>
        <el-table-column label="歌单图片" width="110" align="center">
          <template slot-scope="scope">
            <div class="singer-img">
              <img :src="getUrl(scope.row.pic)" alt="" style="width: 100%;"/>          
            </div>
                      <el-upload
              class="upload-demo"
              :action="uploadUrl(scope.row.id)"
              :show-file-list="false"
              :on-success="handleSuccess"
              :before-upload="beforeAvatarUpload"
              >
              <el-button size="mini" type="primary">更新图片</el-button>
                    </el-upload>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="标题" width="120" align="center"></el-table-column>
        <el-table-column label="简介" width="450" align="center">
            <template slot-scope="scope">
                    <p style="height:100px;overflow:scroll;">{{scope.row.introduction}}</p>
            </template>
        </el-table-column>
        <el-table-column label="风格"  width="200"  align="center" prop="style"></el-table-column>
        <el-table-column label="歌单管理" width="110">
            <template slot-scope="scope">
                <el-button  @click="toListSong(scope.row.id)" size="mini">歌单管理</el-button>
            </template>
        </el-table-column>
        <el-table-column label="操作" width="120" align="center">
          <template slot-scope="scope">
              <div style="display:flex;justify-content:space-around;">
            <el-button  size="mini" @click="handleEdit(scope.row)" type="primary">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDel(scope.row.id)">删除</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    <div class="pagination">
        <el-pagination
        background
        layout="total,prev,pager,next"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="songList.length"
        @current-change="handleCurrent"
        >
        </el-pagination>
    </div>
    </div>
</template>
<script>
import {addSongList,getAllSongList,deleteSongListById,updateSongList} from "../api/index"
import {mixin} from "../mixins"
import {mapGetters} from "vuex"
export default {
    created(){
            this.getSongList();
    },
    destroyed(){
            this.$store.commit("setIsPlay",false)
    },
    computed:{
        ...mapGetters({
            isPlay:"isPlay",
            isUrl:"isUrl",
        }),
        preHost(){
            return this.$store.state.HOST;
        },
        data(){
            return this.songList.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize)
        }
    },
    mixins:[mixin],
    watch:{
        //搜索框里面的内容发生变化的时候，表格发生变化--->后续可以用到接口模糊查询
            select_word(va){
                if(this.select_word===""){
                    this.songList=this.tmpData;
                }else{
                    this.songList=[]
                    for(let item of this.tmpData){
                        if(item.title.includes(this.select_word)){
                            this.songList.push(item)
                        }
                    }
                }
            }
    },
    
    data(){return{
        select_word:"",
        singerId:"",
        singerName:"",
        tmpData:[],
        dialogFormVisible:false,
        dialogEditVisible:false,
        toggle:'',
        songList:[],
         editForm: {
             id:"",
             title:'',
             introduction:"",
             style:'',
            },
         registerForm: {
        
          title:'',
          introduction:'',
          style:'',
        },
        pageSize:5, //分页每页的大小
        currentPage:1,//当前页
        idx:-1,//当前选择项
        multipleSelection:[],//当前打钩的项
    }},
    methods:{
       //上传图片
       uploadUrl(id){
           let url=`${this.$store.state.HOST}/songList/updateSongListPic?id=${id}`;
           return url;
       },
       uploadMusic(id){
           let url= `${this.$store.state.HOST}/song/updateSong?id=${id}`;
           return url;

       },
        //上传歌曲
        beforeMusic(file){
                let sp=file.name.split(".")
                //得到后缀名
                let  suffix=sp[sp.length-1];
                if(suffix==="mp3"){
                    return true;
                }else{
                    this.$message({
                        message:"请选择mp3的文件",
                        type:"error"
                    })
                    return false;
                }
        },
        musicSuccess(){
            this.$message.success("修改音乐成功!")
            this.getSongList();
        },

       handleSuccess(){
            this.$message.success("上传成功!")
            this.getSongList();
       },
   
        //搜索歌手
        searchSinger(){
               
        },
        
        //添加歌曲
        addSongList(){
            var form=new FormData(document.getElementById("tf"));
            form.append("title",this.registerForm.title)
            form.append("style",this.registerForm.style)
            form.append("introduction",this.registerForm.introduction)
            form.append("pic","/img/songList/123.jpg")//默认上传图片
            console.log(form.get('title'))
            addSongList(form,{}).then(r=>{
                if(r===true){
                    this.$message.success("添加成功")
                }else{
                    this.$message.error("添加失败!")
                }
                this.getSongList();
                this.dialogFormVisible=false;
            })

        },
        //改变当前页
        handleCurrent(val){
            this.currentPage=val;
        },
        //编辑的对象
        handleEdit(obj){
               this.editForm=obj;
               this.dialogEditVisible=true;
        },
       
        //获取当前歌手的歌曲
        getSongList(){
            this.tmpData=[];
            this.songList=[];
            getAllSongList().then(r=>{
                this.songList=r;
                this.tmpData=r;
                
            })
            
        },
        //点击删除一个歌手
        handleDel(val){
            this.$confirm('此操作将永久删除此信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
           this.delteRow(val)
        }).catch(()=>{this.$message.error("取消删除")})


        },
        //删除歌曲的具体方法
        delteRow(id){
                deleteSongListById(id).then(r=>{
                    if(r===true){
                        this.$message.success("删除成功!")
                    }else{
                        this.$message.error("删除失败")
                    }
                    this.getSongList();
                })            
        },
        //点击选择框，会将选中的内容添加到数组中。
        handleSelectionChange(val){
            
            this.multipleSelection=val;
        },
        //批量删除
        deleteAll(){
            this.$confirm('此操作将永久批量删除此'+this.multipleSelection.length+'信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
                for(let item of this.multipleSelection){
                    this.delteRow(item.id)
                }
        }).catch(()=>{this.$message.error("取消删除")})
            
        },
       //编辑歌曲
       updateSongList(){
           updateSongList(this.editForm).then(r=>{
               if(r===true){
                   this.$message.success("修改成功!")
               }else{
                   this.$message.error("修改失败!")
               }
               this.getSongList();
               this.dialogEditVisible=false;
           })
       },
       //跳转路由
       toListSong(id){
           this.$router.push("listSong?id="+id)

       }
      
    }
    
}
</script>
<style >
*{
    margin: 0;padding: 0;
    
}

.el-form-item__label {
    text-align: right;
    float: left;
    font-size: 10px;
    color: #606266;
    line-height: 40px;
    padding: 0 12px 0 0;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
}
.el-upload-list,.el-upload-list--text{
    display: none !important;
}
.handle-input{
    width: 200px;
}
.pagination{
    display: flex;
    justify-content: center;
}
#tf input{
    width: 200px;
}
.el-button--default{
  background-color: #409EFF;
  
}
.icon{
    width: 40px;
    height: 40px;
    border-radius: 50%;
    background: rgba(255,255,255,0.2);
    position: relative;
    left: 25px;
    top: -70px;
    z-index: 10;
    
}
.icon>div>img{
    width: 100%;
}

</style>