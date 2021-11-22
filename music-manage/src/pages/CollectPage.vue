<template>
    <div class="table">
        <div class="container">
           
        </div>


<el-table ref="multipleTable" size="mini" border style="width: 100%" height="550px" :data="data" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="40">
        </el-table-column>
        <el-table-column prop="songName" label="收藏歌曲" width="900" align="center">
            <template slot-scope="scope">
                    {{scope.row.name}}
            </template>
        </el-table-column>
 
        <el-table-column label="操作" width="220" align="center">
          <template slot-scope="scope">
              <el-button @click="handleDel(scope.row)" type="danger" size="mini">删除</el-button>
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
        >
        </el-pagination>
    </div>
    </div>
</template>
<script>
import {getCollectListByUserId,getSongById,deleteCollectBySongId} from "../api/index"
import {mixin} from "../mixins"
import {mapGetters} from "vuex"
export default {
    created(){
        this.userId=this.$route.query.userId
        this.getCollectList();

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
   
    data(){return{
        userId:'',
        songListId:"",
        tmpData:[],
        pageSize:5,
        currentPage:1,
        select_word:'',
        dialogFormVisible:false,
        dialogEditVisible:false,
        songList:[],
         registerForm: {
            singerName:'',
            songName:"",
         },
        idx:-1,//当前选择项
        multipleSelection:[],//当前打钩的项
    }},
    methods:{
        getCollectList(){
                getCollectListByUserId(this.userId).then(res=>{
                    console.log(res,'---')
                   for(let collect of res){
                       getSongById(collect.songId).then(ress=>{
                           this.songList.push(ress)
                       })
                   }
                })
            },
    
        handleDel(item){
              this.$confirm('此操作将'+item.name+'歌曲移出您的收藏列表, 是否继续?', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
                this.deleteRow(item.id)
        }).catch(()=>{this.$message.error("取消删除")})
            
        },
        deleteRow(id){
                deleteCollectBySongId(id,this.userId).then(res=>{
                    if(res>0){
                        this.$message.success("移出成功!")
                        this.$router.go(0)
                    }else{
                        this.$message.error("移出失败!")
                                                this.$router.go(0)


                    }
                })
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