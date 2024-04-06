<script>
import myDescription from "@/components/sl-description.vue"
import MyNav from "@/pages/mNav.vue";
import axios from "axios";
export default {
  components:{
    myDescription,MyNav
  },
  data() {
    return {
      items:[
        {label:"密码",content:""},
        {label:"vip折扣",content:""},
        {label:"提前预约天数",content:""},
        {label:"微信服务密钥",content:""},
        {label:"web服务密钥",content:""},
        {label:"JSLocation服务密钥",content:""},
        {label:"JSLocation安全密钥",content:""},
        {label:"小程序appid",content:""},
        {label:"小程序密钥",content:""}

      ],
      user:null

    }
  },

  props: {},

  methods: {
    summbit(){

      const data={
        username:this.user.username,
        password:this.items[0].content,
        vipDiscount:this.items[1].content,
        antecedentDay:this.items[2].content,
        wxKey:this.items[3].content,
        webKey:this.items[4].content,
        jsKey:this.items[5].content,
        codeSafeMsy:this.items[6].content,
        wxAppid: this.items[7].content,
       wxSecret:this.items[8].content

      }
      axios.post("/updateManager",data)
          .then((res)=>{
            if(res.status==200){
              this.user=res.data;
              localStorage.setItem("token",JSON.stringify(this.user))
              alert("修改成功")
            }
          })
    }
  },
  created() {

    this.user=JSON.parse(localStorage.getItem("token"))
    this.items[0].content=this.user.password
    this.items[1].content=this.user.vipDiscount
    this.items[2].content=this.user.antecedentDay
    this.items[3].content=this.user.wxKey
    this.items[4].content=this.user.webKey
    this.items[5].content=this.user.jsKey
    this.items[6].content=this.user.codeSafeMsy
    this.items[7].content=this.user.wxAppid
    this.items[8].content=this.user.wxSecret

  }
}
</script>

<template>
  <my-nav>
      <my-description title="基本设置" :items="items" :is-need-change="true" :sumbit-fun="summbit">
        <template #front>
          <el-descriptions-item label="用户名">
            {{user.username}}
          </el-descriptions-item>
        </template>
      </my-description>
  </my-nav>
</template>

<style scoped>

</style>
