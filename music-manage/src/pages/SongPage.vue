<template>
    <div class="table">
        <div class="container">
            <div class="handle-box">
                <el-input class="handle-input" v-model="select_word" size="mini" placeholder="请输入歌曲名称"></el-input>
                <el-button type="primary" size="mini" @click="searchSinger">搜索</el-button><br/>
                <el-button type="primary" size="mini" @click="dialogFormVisible=true">添加歌曲</el-button>
                <el-button type="danger" size="mini" @click="deleteAll">批量删除</el-button>
            </div>
        </div>

        <el-dialog title="添加歌曲" :visible.sync="dialogFormVisible" width="400px" center="">
  <el-form :model="registerForm" label-width="50px" id="tf">
            <el-form-item prop="name" label="歌手-歌曲名" size="mini" style="font-size:5px">
                <el-input v-model="registerForm.name" placeholder="歌曲"/>
            </el-form-item>
             <el-form-item prop="introduction" label="专辑" size="mini">
                 <el-input v-model="registerForm.introduction" placeholder="歌曲介绍">
                 </el-input>
            </el-form-item>

            <el-form-item prop="lyric"
            label="歌词">
                <el-input type="textarea" v-model="registerForm.lyric" size="mini" >
                </el-input>
            </el-form-item>
            <el-form-item>
                <div><input type="file" name="file" ref="file"></div>
            </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false" type="primary">取 消</el-button>
    <el-button type="primary" @click="addSong">确 定</el-button>
  </div>
</el-dialog>
<!--修改页面-->

        <el-dialog title="编辑歌曲" :visible.sync="dialogEditVisible" width="400px" center="">
             <el-form :model="editForm" label-width="50px" id="tf">
            <el-form-item prop="name" label="歌手-歌曲名" size="mini" style="font-size:5px">
                <el-input v-model.lazy="editForm.name" placeholder="歌曲"/>
            </el-form-item>
             <el-form-item prop="introduction" label="专辑" size="mini">
                 <el-input v-model.lazy="editForm.introduction" placeholder="歌曲介绍">
                 </el-input>
            </el-form-item>

            <el-form-item prop="lyric"
            label="歌词">
                <el-input type="textarea" v-model.lazy="editForm.lyric" size="mini" >
                </el-input>
            </el-form-item>
    </el-form>

  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogEditVisible = false">取 消</el-button>
    <el-button type="primary" @click="updateSong">确 定</el-button>
  </div>
</el-dialog>
<el-table ref="multipleTable" size="mini" border style="width: 100%" height="550px" :data="data" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="40">
        </el-table-column>
        <el-table-column label="歌曲图片" width="110" align="center">
          <template slot-scope="scope">
              
            <div class="singer-img">
              <img :src="getUrl(scope.row.pic)" alt="" style="width: 100%;"/>
            </div>
            <div class="icon" @click="setMusicUrl(scope.row.url,scope.row.name)">
                <div class="play" v-if="(toggle===scope.row.name)">
                    <img src="@/assets/img/暂停.png" alt="">
                </div>
                <div class="pause" v-if="(toggle!=scope.row.name)">
                    <img src="@/assets/img/播放.png" alt="">
                </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="歌手-歌曲" width="120" align="center"></el-table-column>
        <el-table-column label="专辑" width="150" align="center" prop="introduction"></el-table-column>
        <el-table-column label="歌词"  width="500" style="height:100px;overflow:scroll;" align="center">
              <template slot-scope="scope">
                    <ul  style="height:100px;overflow:scroll;" >
                        <li v-for="(item,index) in parseLyric(scope.row.lyric)" :key="index">
                            {{item}}
                        </li>
                    </ul>
          </template>          
        </el-table-column>
      <el-table-column label="更新资源" width="150" align="center">
            <template slot-scope="scope">
            <!--图片更新-->
                    <el-upload
              class="upload-demo"
              :action="uploadUrl(scope.row.id)"
              :show-file-list="false"
              :on-success="handleSuccess"
              :before-upload="beforeAvatarUpload"
              >
              <el-button size="mini" type="primary">更新图片</el-button>
                    </el-upload>

            <!--更新歌曲-->
                <el-upload
              class="upload-demo"
              :action="uploadMusic(scope.row.id)"
              :show-file-list="false"
              :on-success="musicSuccess"
              :before-upload="beforeMusic"
              >
              <el-button size="mini" type="primary" style="margin-top:10px;">更新歌曲</el-button>
                    </el-upload>
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
import {addSong,getAllSongOfSinger,deleteSongById,updateSong} from "../api/index"
import {mixin} from "../mixins"
import {mapGetters} from "vuex"
export default {
    created(){
            this.singerId=this.$route.query.id
            this.singerName=this.$route.query.name
            this.getSongList();
    },
    destroyed(){
            this.$store.commit("setIsPlay",false)
            console.log('推出了!')
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
            select_word(){
                if(this.select_word===""){
                    this.songList=this.tmpData;
                }else{
                    this.songList=[]
                    for(let item of this.tmpData){
                        if(item.name.includes(this.select_word)){
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
             name:"",
             lyric:'',
             introduction:'',
             pic:"/img/songPic/tubiao.jpg",
             path:'/song'
        },
         registerForm: {
            name:"",
            singerName:"",
            introduction:"",
            lyric:"",
             pic:"/img/songPic/tubiao.jpg",

        },
        pageSize:5, //分页每页的大小
        currentPage:1,//当前页
        idx:-1,//当前选择项
        multipleSelection:[],//当前打钩的项
    }},
    watch:{
        toggle(val){
        }  
        },
    methods:{
       //上传图片
       uploadUrl(id){
           let url=`${this.$store.state.HOST}/song/updateSongPic?id=${id}`;
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
                alert(this.select_word)
        },
        
        //添加歌曲
        addSong(){
            var form=new FormData(document.getElementById("tf"));
            form.append('file',this.$refs.file.files[0])
            form.append("name",this.registerForm.name)
            form.append("introduction",this.registerForm.introduction)
            //如果没有输入歌词则处理一下即可
            if(this.registerForm.lyric===''){
                   this.registerForm.lyric='[00:00:00]暂无歌词'
            }
            form.append("lyric",this.registerForm.lyric)
            form.append("singerId",this.singerId)
            form.append("pic",this.registerForm.pic)
            if(form.get("file").name===''){
               this.$message.error("请添加一个文件!")
            return;
            }
            addSong(form,{
                headers:{'Content-Type':"multipart/form-data;"}
            }).then(r=>{
                if(r.code===1){
                    this.$message.success(r.msg)
                   }else{
                    this.$message.error(r.msg)
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
            getAllSongOfSinger(this.singerId).then(r=>{
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
             let query={id:id};
            deleteSongById(query).then(res=>{
                if(res===true){
                    this.$message.success("删除成功")
                }else{
                    this.$message.success("删除失败")
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
       updateSong(){
           if(this.editForm.lyric===''){
               this.editForm.lyric='[暂无歌词:00:00:00]'
           }
           updateSong(this.editForm).then(res=>{
               if(res===true){
                   this.$message.success("更新成功!")
               }else{
                   this.$message.error("更新失败!")
               }
               this.getSongList();
               console.log(this.editForm)
               this.dialogEditVisible=false;
           })
       },
       //解析歌词
       parseLyric(text){
            let line=text.split("\n")
            let rst=[]
            for(let item of line){
                item=item.replace(/\[\d{2}\:\d{2}\.(\d{2}|\d{3})\]/,"")
                rst.push(item)
            }
           return rst;

       },
      setMusicUrl(url,name){
            this.toggle=name
          this.$store.commit("setUrl",url)
          if(this.isPlay==true){
              this.$store.commit("setIsPlay",false);
              this.toggle=''
          }
          else{
              this.toggle=name;
            this.$store.commit("setIsPlay",true)
          }
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