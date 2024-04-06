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
      colName: [{prop:'id',label:'编号'},{prop:'openId',label:'openId'},{label: "内容",prop: "content"},{label:"停车场编号",prop: "slotId"}],
      tableData:[
      ],
      total:1,
      status:null,
      orderId:"",
      page:1,
      dialogVisible:false,
      chooseOrder:null,
      feedContent:"",
    }
  },
  created(){

    this.search()
    axios.post("/getDenunciationCount").then((res)=>{

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
        openId:this.orderId
      }
      axios.post("/getDenunciationByPage",data).then((res)=>{

        this.tableData=res.data

      })

    },
    deleteSlot(){
      axios.post("/deleteDenunciation",{id:JSON.parse(localStorage.getItem("tableRow")).id}).then((res)=>{

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
    sure(){
      axios.post("/addMessage",{openId:this.chooseOrder.openId,content:this.feedContent,type:1,messageId:this.chooseOrder.id}).then((res)=>{

        alert("回复成功")
        this.dialogVisible=false

      })
    }
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
              <el-button size="small" type="primary" @click="look" style="width: 60px">回复</el-button>
              <el-button size="small" type="primary" @click="deleteSlot" style="width: 60px">删除</el-button>
            </el-space>
          </template>
          <template #header>
            <el-space direction="horizontal">
              <el-select size="default" placeholder="回复状态"  v-model="status" style="width: 200px" @change="search">
                <el-option label="未回复" :value="0">

                </el-option>
                <el-option label="已回复" :value="1">

                </el-option>
              </el-select >
              <el-input size="small" v-model="orderId" placeholder="用户号">
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
              <el-pagination layout="prev, pager, next" :total="total" @current-change="currentChange" :page-size="10"/>
            </div>
          </template>
        </my-table>
      </div>

    </div>



  </my-nav>
  <el-dialog v-model="dialogVisible" title="回复" width="500" >

    <el-input type="textarea" v-model="feedContent" placeholder="回复内容">

    </el-input>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="this.dialogVisible=false">
          取消
        </el-button>
        <el-button type="primary" @click="sure">
          确认
        </el-button>
      </div>
    </template>
  </el-dialog>

</template>

<style scoped>

</style>
