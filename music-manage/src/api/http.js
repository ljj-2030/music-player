import Axios from "axios";
import router from "vue-router"
Axios.defaults.baseURL="http://localhost:8888"
Axios.defaults.timeout=5000;//超时5秒
// // //Content-Type 响应头,后台要求的响应头
Axios.defaults.headers.post['Content-Type']="application/x-www-form-urlencode;charset=UTF-8";
// //基础url
Axios.defaults.baseURL="http://localhost:8888";
//相应拦截器
Axios.interceptors.response.use(response=>{
  //如果response里的status是200，说明访问到了，否则错误
  if(response.status===200){
    return response.data;
  }else{
    return Promise.reject(response)
  }
},error=>{
  if(error.response.status){
    switch (error.response.status){
      case 401:router.replace({ //未登录
        path:"/",
        query:{
          redirct:router.currentRoute.fullPath
        }
      });
      break;
      case 404:break;
    }
    return Promise.reject(error.response)
  }
})




//post请求
export function post(url,data,config){
      return Axios.post(url,data,config);
}


//get请求
export function get(url,query){
  return Axios.get(url,query)
}

//更新请求
export function update(url,data){
return Axios.put(url,data)
}
//删除请求
export function deleted(url,query){
  return Axios.delete(url,{
    params:query
  })
}


// let axios=Axios
// axios.defaults.timeout=5000;//超时5秒
// axios.defaults.withCredentials=true;//允许跨域
// //Content-Type 响应头,后台要求的响应头
// axios.defaults.headers.post['Content-Type']="application/x-www-form-urlencode;charset=UTF-8";
// //基础url
// axios.defaults.baseURL="http://localhost:8888";
// //相应拦截器
// axios.interceptors.response.use(response=>{
//   //如果response里的status是200，说明访问到了，否则错误
//   if(response.status===200){
//     return response;
//   }else{
//     return Promise.reject(response)
//   }
// },error=>{
//   if(error.response.status){
//     switch (error.response.status){
//       case 401:router.replace({ //未登录
//         path:"/",
//         query:{
//           redirct:router.currentRoute.fullPath
//         }
//       });
//       break;
//       case 404:break;
//     }
//     return Promise.reject(error.response)
//   }
// })

// /****
//  * 封装get方法
//  */
// export function get(url,params){
//   return new Promise((resolve,reject)=>{
//     axios.get(url,{params:params}).then(r=>{
//       resolve(r.data)
//     }).catch(e=>{
//       reject(e)
//     })
//   })
// }

// /****
//  * 封装post方法
//  */
// export function post(url,data={}){
//   return new Promise((resolve,reject)=>{
//     axios.post(url,data).then(r=>{
//       resolve(r.data)
//     }).catch(e=>{
//       reject(e)
//     })
//   })
// }
