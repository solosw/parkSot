<script>
import myNav from "@/components/sl-asideWithoutChoose.vue"
export default {
  data() {
    return {
      chooseLIst:[
          "停车场管理",
          "公告",
          "系统设置",
          "订单管理",
          "用户反馈",
          "举报管理"
      ],
      urls:[
          "/index",
          "/tip",
          "/system",
          "/order",
          "/feedback",
          "/denunciate"
      ],

    }
  },

  props: {},

  methods: {},
  created() {

  },
  components:{
    myNav
  }
}
</script>

<template>
  <my-nav title="学生健康监察管理员后台" :urls="urls" :choose-list="chooseLIst">
    <slot></slot>
  </my-nav>
</template>

<style scoped>

</style>
