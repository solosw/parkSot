<script>
import MyNav from "@/pages/mNav.vue";
import mapContainer from "@/components/map.vue";
import myTable from "@/components/sl-table.vue";
import axios from "axios";
export default {
  components: {myTable, MyNav,mapContainer},
  data() {
    return {
      key:"",
      jsSafe:"",
      colName: [{prop:'id',label:'编号'},{prop:'address',label:'地址'},{label: "价格(元/小时)",prop: "price"},{label:"超时价格(元/小时)",prop: "extraPrice"},{label: "停车位",prop: "slotNumber"},{label: "已使用",prop: "useNumber"},{label: "经度",prop: "lng"},{label: "纬度",prop: "lat"}],
      tableData:[
      ],
      vis2:false,
      visible: false,
      loading: false,
      formData: {
        lng: "",
        lat: "",
        slotNumber:"",
        address: "",
        price:"",
        extraPrice:""
        //地址简称
      },
      //默认的地址和经纬度
      point:['106.26667','38.46667'],
      address: "南宁民歌湖",
      chooseItem:null,
    }
  },
  created(){
    this.init()
    axios.post("/getSlots").then((res)=>{
      this.tableData=res.data

    })


  },
  mounted() {

  },
  props: {},

  methods: {
    getLoctionUpdate(point, address) {
      this.chooseItem.lng = point[0];
      this.chooseItem.lat = point[1];
      this.chooseItem.address = address;
    },
    init() {
      this.defaultLngAndLat()
      this.visible = false;
    },
    //获取经纬度-子组件给父组件传的值
    getLoction(point, address) {
      this.formData.lng = point[0];
      this.formData.lat = point[1];
      this.formData.address = address;
    },
    //默认地址和经纬度
    defaultLngAndLat(){
      this.formData.lng = this.point[0];
      this.formData.lat = this.point[1];
      this.formData.address = this.address;
    },
    // 表单提交
    dataFormSubmit() {
      this.submit();
    },
    submit() {
          axios.post("/addSlot",this.formData).then((res)=>{
            if(res.status==200)location.reload()

          })
    },
    deleteSlot(){

      const item=JSON.parse(localStorage.getItem("tableRow"));
      axios.post("/deleteSlot",{id:item.id}).then((res)=>{
        if(res.status==200)location.reload()

      })
    },
    updateSlot(){

      axios.post("/updateSlot",this.chooseItem).then((res)=>{
        if(res.status==200)location.reload()

      })

    },
    edit(){
      this.chooseItem=JSON.parse(localStorage.getItem("tableRow"));
      this.vis2=true
    }


  },

}
</script>

<template>
  <my-nav>
    <div class="box-card" style="width: 90%;margin-left: 5%;margin-top: 10px">
      <div style="float: right;margin-right: 10px">
        <el-space style="margin-top: 5%;margin-bottom: -5%" size="large">
          <el-button type="success" @click="visible=true">添加</el-button>
        </el-space>
      </div >
      <div style="margin: 0 auto">
        <my-table :col-name="colName" :table-data="tableData" style="width: 100%; margin: 0 auto ">
          <template #operation>
            <el-space wrap direction="vertical">
              <el-button size="small" type="primary" @click="edit" style="width: 60px">编辑</el-button>
              <el-button size="small" type="primary" @click="deleteSlot" style="width: 60px">删除</el-button>
            </el-space>
          </template>
          <template #header>
          </template>
        </my-table>
      </div>

    </div>



  </my-nav>

  <el-dialog
      :close-on-click-modal="false"
      append-to-body
      v-model="visible"
      width="80%"
      title="新建"
  >
    <el-form :model="formData" size="small" label-width="100px">
      <el-row :gutter="0">
        <el-col :span="8">
          <el-form-item label="经度:">
            <el-input  v-model="formData.lng" placeholder="请选择"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="经度:">
            <el-input  v-model="formData.lat" placeholder="请选择"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="0">
        <el-col :span="12">
          <el-form-item label="地址:">
            <el-input
                v-model="formData.address"
                placeholder="请输入"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="5">
          <el-form-item label="停车位:">
            <el-input type="number"
                      v-model="formData.slotNumber"
                      placeholder="请输入"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="0">
        <el-col :span="8">
          <el-form-item label="价格:">
            <el-input
                v-model="formData.price"
                placeholder="请输入"
                type="number"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="超时价格:">
            <el-input
                v-model="formData.extraPrice"
                placeholder="请输入"
                type="number"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <map-container
        :defaultPoint="point"
        :defaultAddress="address"
        @sendLoction="getLoction"
    ></map-container>
    <template #footer>

      <el-button @click="visible = false"> 取 消</el-button>
      <el-button type="primary" @click="dataFormSubmit">
        确 定</el-button
      >
    </template>

  </el-dialog>


  <el-dialog
      :close-on-click-modal="false"
      append-to-body
      v-model="vis2"
      width="80%"
      title="编辑"
  >
    <el-form :model="chooseItem" size="small" label-width="100px" >
      <el-row :gutter="0">
        <el-col :span="8">
          <el-form-item label="编号:">
            <el-input disabled v-model="chooseItem.id" placeholder="请选择"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="0">
        <el-col :span="8">
          <el-form-item label="经度:">
            <el-input  v-model="chooseItem.lng" placeholder="请选择"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="经度:">
            <el-input  v-model="chooseItem.lat" placeholder="请选择"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="0">
        <el-col :span="12">
          <el-form-item label="地址:">
            <el-input
                v-model="chooseItem.address"
                placeholder="请输入"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="5">
          <el-form-item label="停车位:">
            <el-input type="number"
                      v-model="chooseItem.slotNumber"
                      placeholder="请输入"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="0">
        <el-col :span="10">
          <el-form-item label="价格:">
            <el-input
                v-model="chooseItem.price"
                placeholder="请输入"
                type="number"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="超时价格:">
            <el-input
                v-model="chooseItem.extraPrice"
                placeholder="请输入"
                type="number"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <map-container
        :defaultPoint="[chooseItem.lng,chooseItem.lat]"
        @sendLoction="getLoctionUpdate"
     ></map-container>
    <template #footer>

      <el-button @click="vis2 = false"> 取 消</el-button>
      <el-button type="primary" @click="updateSlot">
        确 定</el-button
      >
    </template>

  </el-dialog>
</template>

<style scoped>

</style>
