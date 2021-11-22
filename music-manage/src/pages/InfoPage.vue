<template>
    <div>
        <el-row :gutter="20" class="mgb20">
            <el-col :span="6">
                <el-card>
                    <div class="grid-content">
                        <div>{{consumerCount}}</div>
                        <div>用户总数</div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card>
                    <div class="grid-content">
                        <div>{{SingerCount}}</div>
                        <div>歌手总数</div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card>
                    <div class="grid-content">
                        <div>{{SongCount}}</div>
                        <div>歌曲总数</div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card>
                    <div class="grid-content">
                        <div>{{SongListCount}}</div>
                        <div>歌单总数</div>
                    </div>
                </el-card>
            </el-col>


        </el-row>
        <el-row :gutter="20">
            <el-col :span="12">
               <h4 class="mgb20">用户男女比例饼图</h4>
                <div style="background-color:white;">
                    <ve-pie :data="consumerSex"></ve-pie>
                </div>
            </el-col>
            <el-col :span="12">
               <h4 class="mgb20">歌单风格比例柱形图</h4>
                <div style="background-color:white;">
                    <ve-histogram  :data="songListStyle"></ve-histogram>
                </div>
            </el-col>  
        </el-row>
        <el-row :gutter="20">
            <el-col :span="12">
                <h4 class="mgb20">歌手性别比列饼图</h4>
                <div style="background-color:white;">
                    <ve-pie  :data="singerSexCount"></ve-pie>
                </div>
            </el-col>
                <el-col :span="12">
               <h4 class="mgb20">歌手地区比例柱形图</h4>
                <div style="background-color:white;">
                    <ve-histogram  :data="singerCountryCount"></ve-histogram>
                </div>
            </el-col>  
        </el-row>
    </div>

</template>
<script>
import{getAllConsumer,getAllSong,getAllSinger,getAllSongList} from "../api/index"
export default {
    data(){return{
        consumerCount:0,
        SongCount:0,
        SingerCount:0,
        SongListCount:0,
        consumer:[],
        song:[],
        singer:[],
        songList:[],
        consumerSex:{//按性别分类
            columns:['性别',"总数"],
            rows:[
                {'性别':"男","总数":0},
                {'性别':"女","总数":0},
            ]
        },
        songListStyle:{
            columns:['风格','总数'],
            rows:[
                 {"风格":"华语","总数":0},
                 {"风格":"粤语","总数":0},
                 {"风格":"欧美","总数":0},
                 {"风格":"日韩","总数":0},
                 {"风格":"BGM","总数":0},
                 {"风格":"轻音乐","总数":0},
                 {"风格":"乐器","总数":0},
            ]
        },
        singerSexCount:{
            columns:['性别',"总数"],
            rows:[
                {'性别':'男','总数':0},
                {'性别':'女','总数':0},
                {'性别':'组合','总数':0},
                {'性别':'不明','总数':0},
            ]
        },
        singerCountryCount:{
             columns:['国籍',"总数"],
            rows:[
                {'国籍':'中国','总数':0},
                {'国籍':'美国','总数':0},
                {'国籍':'日本','总数':0},
                {'国籍':'韩国','总数':0},
                {'国籍':'新加坡','总数':0},
                {'国籍':'英国','总数':0},
            ]
        }
    }},
    created(){
            this.getConsumer();
            this.getSong();
            this.getSinger();
            this.getSongList();
    },
    mounted(){
    },
    methods:{
        getConsumer(){
            getAllConsumer().then(r=>{
               this.consumer=r;
               this.consumerCount=r.length;
               this.consumerSex.rows[0]['总数']=this.getSexCount(1,this.consumer)
               this.consumerSex.rows[1]['总数']=this.getSexCount(0,this.consumer)
            })
        },
        //即将完成，根据歌单风格，柱状图


        //根据性别查找用户个数
        getSexCount(sex,val){
                let count=0;
                for(let item of val){
                    if(sex===item.sex){
                        count++;
                    }
                }
                return count;
            },
        getSong(){
         getAllSong().then(r=>{
                this.song=r;
                this.SongCount=r.length;
            })
        },
        getSinger(){
            getAllSinger().then(r=>{
                this.singer=r;
                this.SingerCount=r.length;
        //根据歌手的性别进行饼状图
               this.singerSexCount.rows[0]['总数']=this.getSexCount(0,this.singer)
               this.singerSexCount.rows[1]['总数']=this.getSexCount(1,this.singer)
               this.singerSexCount.rows[2]['总数']=this.getSexCount(2,this.singer)
               this.singerSexCount.rows[3]['总数']=this.getSexCount(3,this.singer)


               //根据歌手的国籍
               for(let item of this.singerCountryCount.rows){
                let country=item['国籍']
                let count=this.countByLocation(country,this.singer)
                item['总数']=count;
                }
            })
        },
        //根据地区统计个数
        countByLocation(location,singer){
                let count=0;
                for(let item of singer){
                    if(item.location.includes(location))
                    {count++;}
                }
                return count;
        },
        //根据风格统计个数
        countByStyle(style,songList){
            let count=0;
            for(let item of songList){
                if(item.style.includes(style)){
                    count++;
                }
            }
            return count;
        },
        getSongList(){
            getAllSongList().then(r=>{
            this.songList=r;
            this.SongListCount=r.length;
                    for(let item of this.songListStyle.rows){
                        //取出风格
                        let style=item['风格']
                           let count=this.countByStyle(style,this.songList)
                            item['总数']=count;
                    }
            })
        }
        
    }
}
</script>
<style scoped>
        .grid-content{
            font-size: 12px;
            padding-left: 100px;
            color: gray;
        }
        .grid-content>div:first-child{
            font-size: 15px;
            font-weight: bold;
        }
        .grid-content>div:nth-child(2){
            margin-left: -10px;
        }
</style>