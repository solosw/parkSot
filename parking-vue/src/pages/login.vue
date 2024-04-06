<script>
import myLogin from "@/components/sl-login.vue"
export default {
  data() {
    return {}
  },

  props: {},

  methods: {
    afterFun(data){
      if(!data){
        alert("账户异常");

      }else
      {
        console.log(data)
        localStorage.setItem("token",JSON.stringify(data))
        location.href="/index";
      }
    }
  },
  created() {

  },
  components:{
    myLogin
  }
}
</script>

<template>
  <my-login bg-src="/src/assets/bg.jpg" :after-fun="afterFun" :check-fun="()=>{return true}" title="管理员登陆界面" success-page="/managerIndex" request-login-url="/managerLogin" :is-has-register="false">

  </my-login>
</template>

<style scoped>

</style>
