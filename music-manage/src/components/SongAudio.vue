<template>
   <div class="song-audio">
        <audio :src="this.$store.state.HOST+isUrl" id="player"
        autoplay="false"
        controls="controls"
        preload="true"
        @canplay="startPlay"
        @ended="ended"
        ></audio>       
    </div> 
</template>
<script>
import {mapGetters} from "vuex"

export default {
    data(){return{
    }},
    watch:{
        isPlay(val){
                this.togglePlay();
        }
    },
    updated(){
    },
    computed:{
        ...mapGetters({
            isUrl:"isUrl",
            isPlay:"isPlay",
        }),
    },
    methods:{
        //获取连接后准备播放
        startPlay(){
            let player=document.querySelector("#player")
            //开始播放
            player.play();

        },
        ended(){
            //播放完之后
            this.isPlay=false;
        },
        //开始暂停
        togglePlay(){
            let player=document.querySelector("#player")
            if(this.isPlay){
                player.play();
            }else{
                player.pause();
            }
        },
    
    },
    
}
</script>
<style scoped>
    .song-audio{
        display: none;
        
    }
</style>