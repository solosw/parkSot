<template>
    <div class="main">
        <div class="header">
            <div class="logo">

               <img src="@/assets/停车场.png" style="width: 60px;height: 60px"/>
                <div style=" height: 60px;background: linear-gradient(to right, white, #737c83);">
                  <div style="font-size:large;margin-bottom: 10px; color: #3a8ee6;letter-spacing: 3px;">停车场管理后台</div>
                </div>

            </div>
            <div style="width: 500px;height: 60px;">
                <span style="color:white;letter-spacing: 5px"></span>
            </div>

            <div  style="margin-right: 10%">
               <el-button type="text" @click="logout">登出</el-button>
                <div class="time-box" style="margin-left: 20px">
                    <span class="time-time">{{datetime.time}}</span>
                </div>
            </div>
        </div>
        <div class="app">
            <div class="aside">
                <div class="index">
                    <router-view></router-view>
                </div>
            </div>
        </div>

    </div>
</template>
<script>
  import myNav from "@/pages/mNav.vue"
    export default {
    components:{
      myNav
    },

    data(){
      return{

       datetime:{date:"",time:""}




      }
    },

        methods: {
             logout(){

               localStorage.clear();
               location.href="/"
            },

            getDatetime(){
                setInterval(()=>{
                    let date = new Date();
                    this.datetime.date = date.getFullYear()+"-"+((date.getMonth()+1)<10?"0"+(date.getMonth()+1):(date.getMonth()+1))+"-"+(date.getDate()<10?"0"+date.getDate():date.getDate());
                    this.datetime.time = (date.getHours()<10?"0"+date.getHours():date.getHours())+":"+(date.getMinutes()<10?"0"+date.getMinutes():date.getMinutes())+":"+(date.getSeconds()<10?"0"+date.getSeconds():date.getSeconds());
                },1000);
            },

        },
        computed:{

        },
        created(){
            this.getDatetime();
        },

    }
</script>
<style lang="less">
    .main {
        display: flex;
        .app {
            width: 100%;
        }
        .aside {
            width: 100%;
            margin-top: 60px;
            z-index: 10;
            transition: all 0.3s ease-in-out;
            height: calc(100% - 60px);
            overflow-x: auto;
            .index{
                width: 100%;
                font-size: 20px;
            }
        }
    }

    .header {
        width: 100%;
        position: fixed;
        display: flex;
        height: 60px;
        line-height: 60px;
        background-color: #737c83;
        justify-content: space-between;
        z-index: 10;
        .logo {
            display: flex;
            flex-direction: row;
            width: 300px;
            height: 60px;
            text-align: center;
            line-height: 60px;
            color: #FFF;
            font-weight: 600;
            -webkit-transition: width 0.35s;
            transition: all 0.3s ease-in-out;
        }
        .right {
            img{
                height: 48px;
                width: 48px;
                border-radius: 50%;
            }
        }
    }
    .time-box{
        display: flex;
        flex-direction: column;
        float: right;
        .time-time{
            font-size: 20px;
            color: #8EF3FA;
            letter-spacing: 1.66px;
        }
        .time-date{
            opacity: 0.48;
            font-size: 10px;
            -webkit-transform: scale(0.83333333) translate(-8.33333333%, 0);
            transform: scale(0.83333333) translate(-8.33333333%, 0);
            color: #8EF3FA;
            letter-spacing: 0.83px;
            text-indent: 34px;
        }
    }


    .el-menu--vertical {
        min-width: 190px;
    }
    .setting-category{
        padding:10px 0;
        border-bottom: 1px solid #eee;
    }
    ::-webkit-scrollbar
    {
        width: 5px;
        height: 5px;
        background-color: #F5F5F5;
    }
    /*定义滚动条轨道 内阴影+圆角*/
    ::-webkit-scrollbar-track
    {
        -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
        border-radius: 10px;
        background-color: #F5F5F5;
    }
    /*定义滑块 内阴影+圆角*/
    ::-webkit-scrollbar-thumb
    {
        border-radius: 10px;
        -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
        background-color: #bdbdbd;
    }
    /*滑块效果*/
    ::-webkit-scrollbar-thumb:hover
    {
        border-radius: 5px;
        -webkit-box-shadow: inset 0 0 5px rgba(0,0,0,0.2);
        background: rgba(0,0,0,0.4);
    }
    /*IE滚动条颜色*/
    html {
        scrollbar-face-color:#bfbfbf;/*滚动条颜色*/
        scrollbar-highlight-color:#000;
        scrollbar-3dlight-color:#000;
        scrollbar-darkshadow-color:#000;
        scrollbar-Shadow-color:#adadad;/*滑块边色*/
        scrollbar-arrow-color:rgba(0,0,0,0.4);/*箭头颜色*/
        scrollbar-track-color:#eeeeee;/*背景颜色*/
    }

</style>
