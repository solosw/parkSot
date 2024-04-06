<script>
import MyNav from "@/pages/mNav.vue";
import mapContainer from "@/components/map.vue";
import myTable from "@/components/sl-table.vue";
import axios from "axios";
import myDescription from "@/components/sl-description.vue"
export default {
  components: {myTable, MyNav,mapContainer,myDescription},
  data() {
    return {
      colName: [{prop:'strId',label:'订单号'},{prop:'openId',label:'openId'},{label: "预计价格(元)",prop: "price"},{label: "预约时间",prop: "time"},{label: "预约时长",prop: "howLong"}],
      tableData:[
      ],
      total:1,
      status:null,
      orderId:"",
      page:1,
      dialogVisible:false,
      chooseOrder:null
    }
  },
  created(){

    this.search()
    axios.post("/getOrderCount").then((res)=>{

      this.total=res.data

    })
  },
  props: {},

  methods: {

    currentChange(page){

      const data={
        pageSize:10,
        start:(page-1)*10,
        status:this.status,
        id:this.orderId
      }
      axios.post("/getOrderByPage",data).then((res)=>{

        this.tableData=res.data

      })

    },
    deleteSlot(){
      axios.post("/deleteOrder",{id:JSON.parse(localStorage.getItem("tableRow")).strId}).then((res)=>{

        location.reload()

      })
    },
    look(){
        this.chooseOrder=JSON.parse(localStorage.getItem("tableRow"));
        this.dialogVisible=true
    },
    search(){

      this.currentChange(1)
    },
    getRealMessage(content){
      if(content){
        return content
      }

      return "无数据"
    },
    getRealTime(s,e){
      // 定义两个日期时间
      let startDate = new Date(s);
      let endDate = new Date(e); // 结束时间调整为14:45:00

      // 计算时间差（单位：毫秒）
      let timeDiff = endDate.getTime() - startDate.getTime();

      // 将毫秒转换为分钟
      let minutesDiff = timeDiff / (1000 * 60);

      // 如果时间差大于30分钟，则按照大于30分钟算作一小时的规则处理
      if (minutesDiff > 30) {
        minutesDiff = Math.ceil(minutesDiff / 60) * 60; // 向上取整到最接近的一小时
      }
      console.log(minutesDiff)
      // 将分钟转换为小时
      let hoursDiff = minutesDiff / 60;
      hoursDiff=Math.floor(hoursDiff);
      if(hoursDiff<1) hoursDiff=1
      return hoursDiff; // 输出：24（相差24小时）

    },


  },

}
</script>

<template>
  <my-nav>
    <div class="box-card" style="width: 90%;margin-left: 5%;margin-top: 10px">

      <div style="margin: 0 auto">
        <my-table :col-name="colName" :table-data="tableData" style="width: 100%; margin: 0 auto ">
          <template #operation>
            <el-space wrap direction="vertical">
              <el-button size="small" type="primary" @click="look" style="width: 60px">查看</el-button>
              <el-button size="small" type="primary" @click="deleteSlot" style="width: 60px">删除</el-button>
            </el-space>
          </template>
          <template #header>
            <el-space direction="horizontal">
              <el-select size="default" placeholder="订单状态"  v-model="status" style="width: 200px" @change="search">
                <el-option label="未开始" :value="0">

                </el-option>
                <el-option label="未完成" :value="1">

                </el-option>
                <el-option label="待支付" :value="2">

                </el-option>
                <el-option label="已支付" :value="3">

                </el-option>
              </el-select >
              <el-input size="small" v-model="orderId" placeholder="订单号">
                <template #append>
                  <el-button @click="search()">
                    搜索
                  </el-button>
                </template>
              </el-input>
            </el-space>

          </template>
          <template #footer>
            <div style="display: flex;justify-content: center;margin-top: -2%">
              <el-pagination layout="prev, pager, next" :total="total" @current-change="currentChange" :page-size="6"/>
            </div>
          </template>
        </my-table>
      </div>

    </div>



  </my-nav>
  <el-dialog v-model="dialogVisible" title="Tips" width="500" draggable>
    <my-description title="订单信息" :items="[]">
     <template #append>
       <el-descriptions-item  label="停车时间" >
         {{getRealMessage(chooseOrder.startTime)}}
       </el-descriptions-item>
       <el-descriptions-item  label="结束时间" >
         {{getRealMessage(chooseOrder.endTime)}}
       </el-descriptions-item>
       <el-descriptions-item v-if="chooseOrder.status==2||chooseOrder.status==3" label="实际时长" >
         {{getRealTime(chooseOrder.startTime,chooseOrder.endTime)}}
       </el-descriptions-item>
       <el-descriptions-item  label="是否超时" >
         {{getRealTime(chooseOrder.startTime,chooseOrder.endTime)>chooseOrder.howLong?'超时':'未超时'}}
       </el-descriptions-item>
     </template>
    </my-description>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">
          退出查看
        </el-button>
      </div>
    </template>
  </el-dialog>

</template>

<style scoped>

</style>
