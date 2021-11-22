import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
        {
          name:'login',
          path:'/',
          component:()=>import("../pages/Login")
        },{
          name:"home",
          path:"/home",
          component:()=>import("../components/Home"),
          children:[
            {
                path:"",
                redirect:"info"
            },
          {name:"info",
          path:'info',
          component:()=>import("../pages/InfoPage")
        },
        {name:"consumer",
        path:'consumer',
        component:()=>import("../pages/ConsumerPage")
      },
      {name:"singer",
      path:'singer',
      component:()=>import("../pages/SingerPage")
    },
    {name:"songlist",
    path:'songlist',
    component:()=>import("../pages/SongListPage")
  },
  {
    name:"song",
    path:"song",
    component:()=>import("../pages/songPage")
  },{
    name:"listSong",
    path:'listSong',
    component:()=>import("../pages/ListSongPage")
  },
  {
    name:"collect",
    path:"collect",
    component:()=>import("../pages/CollectPage.vue")
  }
  
 ]
        },
      
        
        
  ]
})
