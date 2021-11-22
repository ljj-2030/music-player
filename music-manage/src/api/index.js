/* eslint-disable*/
import { get, update,post, deleted } from './http'

// =======================> 管理员 API
// 是否登录成功
export const getLoginStatus = (params) => post(`/login/status`, params)

//添加歌手
export const addSinger=(data)=>{
    return post("/singer/add",data);
}
//更新歌手
export const upSinger=(data)=>{
    return update("/singer/update",data)
}


//获取所有歌手
export const getAllSinger=()=>{
    return get("/singer/getAllSinger",{})
}
//删除歌手
export const deleteSinger=(id)=>{
    return deleted("/singer/delete",{id:id})
}

//########################歌曲相关
export const addSong=(data,config)=>{
    return post("/song/add",data,config)
}
//根据歌曲id查到歌曲
export const getSongById=(id)=>{
    return get("/song/getSong?songId="+id)
}
//根据歌曲id删除歌单中的歌曲
export const deleteSongBySongId=(songId)=>{
    return deleted("/listSong/deleteSongBySongId?songId="+songId)
}

export const getAllSong=()=>{
    return get("/song/getAllSong");
}

export const getAllSongOfSinger=(singerId)=>{
    return get("/song/getSongOfSinger",{params:{singerId:singerId}})
}
//删除
export const deleteSongById=(query)=>{
    return deleted("/song/deleteSongById",query)
}
//更新
export const updateSong=(data)=>{
        return update("/song/update",data)
}
//#####################歌单相关
export const addSongList=(data,config)=>{
    return post("/songList/add",data,config)
}

export const getAllSongList=()=>{
    return get("/songList/getAllSongList")
}
//根据歌单id查找歌曲id
export const getSongBySongListId=(id)=>{
    return get("/listSong/songOfSongList?songListId="+id)
}


//删除
export const deleteSongListById=(id)=>{
    return deleted("/songList/deleteSongListById",{id})
}
//更新
export const updateSongList=(data)=>{
        return update("/songList/update",data)
}

//################################用户管理相关
export const addConsumer=(data,config)=>{
    return post("/consumer/add",data,config)
}

export const getAllConsumer=()=>{
    return get("/consumer/getAllConsumer")
}
//删除
export const deleteConsumerById=(id)=>{
    return deleted("/consumer/deleteConsumerById",{id})
}
//更新
export const updateConsumer=(data)=>{
        return update("/consumer/update",data)
}
//根据userid获取到用户的收藏列表

export const getCollectListByUserId=(userId)=>{
    return get("/collect/collectOfUserId?userId="+userId)
}

//根据歌曲id，和用户id删除收藏列表的歌曲

export const deleteCollectBySongId=(songId,userId)=>{
    return deleted(`/collect/deleteCollectBySongId?userId=${userId}&songId=${songId}`)
}
