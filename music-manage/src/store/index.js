import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

// 创建vuex的store
const store = new Vuex.Store({
    state:{
        HOST:"http://localhost:8888",
        isPlay:false,//默认不播放
        url:'',
        id:"",//歌曲id
    },
    getters:{
        isPlay(state){return state.isPlay;},
        isUrl(state){return state.url;},
        id(state){return state.id;}
    },
    mutations:{
        setIsPlay(state,isPlay){
                state.isPlay=isPlay;
        },
        setUrl(state,url){state.url=url;},
        setId(state,id){state.id=id;}
    }

})
export default store;