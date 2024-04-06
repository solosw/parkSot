<script>
import myNav from "@/pages/mNav.vue"
import axios from "axios";
import myDescription from "@/components/sl-description.vue";
import myTable from "@/components/sl-table.vue";
import myForm from "@/components/sl-form.vue"
import MarkdownIt from "markdown-it";
export default {


  data() {
    return {
      markdownContent:"",
      colName: [{prop:'id',label:'编号'},{prop:'title',label:'标题'},{label: "时间",prop: "time"}],
      tableData:[
      ],
      visable:false,
      vis2:false,
      markdownText:"",
      title:"",
    }
  },
  components:{
    myNav,myDescription, myTable,myForm
  },
  props: {},

  methods: {

    deleteKnowledge(){
      axios.post("/deleteKTips",{id:JSON.parse(localStorage.getItem("tableRow")).id}).then((res)=>{

        if(res.status==200){
          location.reload()
        }

      })
    },
    lookKnowledge(){
      this.markdownText=JSON.parse(localStorage.getItem("tableRow")).content
      this.visable=true;
    },
    addKnowledge(){
      axios.post("/addTips",{title:this.title,content:this.markdownContent}).then((res)=>{

        if(res.status==200){
          location.reload()
        }

      })

    }


  },
  created() {

    axios.post("/getTips").then((res)=>{

      this.tableData=res.data
    })

  },
  computed: {
    parsedMarkdown() {
      const md = new MarkdownIt();
      return md.render(this.markdownText);
    }
  }
}
</script>

<template>
  <el-dialog v-model="visable" title="内容" style="height: 500px;overflow: auto">
    <div v-html="parsedMarkdown" style="overflow: auto">

    </div>
  </el-dialog>

  <my-nav>

    <div class="box-card" style="width: 90%;margin-left: 5%;margin-top: 10px">
      <div style="float: right;margin-right: 10px">
        <el-space style="margin-top: 5%;margin-bottom: -5%" size="large">
          <el-button type="success" @click="vis2=true">添加</el-button>
        </el-space>
      </div >
      <div style="margin: 0 auto">
        <my-table :col-name="colName" :table-data="tableData" style="width: 100%; margin: 0 auto ">
          <template #operation>
            <el-space wrap direction="vertical">
              <el-button size="small" type="primary" @click="deleteKnowledge" style="width: 60px">删除</el-button>
              <el-button size="small" type="primary" @click="lookKnowledge" style="width: 60px">查看内容</el-button>
            </el-space>
          </template>
          <template #header>
          </template>
        </my-table>
      </div>

    </div>
  </my-nav>
  <el-dialog title="公告" v-model="vis2">
    <el-input placeholder="标题" v-model="title"></el-input>
    <v-md-editor v-model="markdownContent" height="400px"></v-md-editor>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="addKnowledge">
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped>
.box-card{
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  border-radius: 10px;

}
</style>
