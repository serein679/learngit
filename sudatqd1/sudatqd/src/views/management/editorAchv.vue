<template>
  <div slot="title">
    <div style="padding: 10px;">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/management/cooperationManagement' }">大事记详情管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/management/editorAchv' }">编辑大事记</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <span style="font-family: '楷体'; font-size: 26px;">编辑大事记</span>
    <el-tabs class="el-tab-div" style="font-family: 楷体;font-size: 26px;">
      <el-form :model="oneHis" ref="oneHis" style="font-size: 32px !important" :rules="rules">
        <el-form-item label="事记名称" :label-width="formLabelWidth" prop="eventName">
          <el-input v-model="oneHis.eventName"></el-input>
        </el-form-item>

        <el-form-item label="事记图片" :label-width="formLabelWidth" style="width: 630px;">
          <el-input v-model="fileName" :readonly='true' placeholder="文件名" style="width:388px;" clearable>
          </el-input>
          <el-upload class="upload-demo" ref="upload" action="doUpload" :limit="1" :file-list="fileList"
            style="float:right" :before-upload="beforeFileUpload">
            <el-button slot="trigger" type="primary">上传文件<i class="el-icon-upload el-icon--right"></i></el-button>
            <div slot="tip" class="el-upload__tip">只能上传1M以内的文件</div>
          </el-upload>
          <img :src="oneHis.eventPic" width="40%" style="float: center;margin-top: 10px;">
        </el-form-item>

        <el-form-item label="大事记时间" :label-width="formLabelWidth" style="margin-left: 1px;width: 300px;" prop="eventDate">
          <!-- <el-input v-model="oneproject.projectStartTime"></el-input> -->
          <el-date-picker v-model="oneHis.eventDate" type="date" placeholder="选择日期" align="right"
            :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="事记详情" :label-width="formLabelWidth" prop="eventDescription" style="height: 1200px;">
          <WangEditor @sendWangEditor="changeOutline" :editorData="editor"></WangEditor>
        </el-form-item>
      </el-form>
      <div class="dialog-footer">
        <el-button type="primary" @click="changeEvent()">修 改</el-button>
        <el-button type="primary" @click="back">取 消</el-button>
      </div>
    </el-tabs>
  </div>
</template>
<script>
// import Carousel from '@/components/Carousel.vue';
import './filters.js'
import { getOnePaper, editPaper } from "@/api/paper"
// import WangEditor from '@/components/WangEditor.vue'
import { getHistoricalEvents, getOneHistoricalEvent, editEvent, PageAllHis } from '@/api/his'



export default {
  components: {
    // WangEditor
  },
  data() {
    return {
      rules: {
        eventName: [
          { required: true, message: '请输入事记名称', trigger: 'blur' },
        ],
        eventDescription: [
          { required: true, message: '请输入事记详情', trigger: 'blur' },
          // { max: 18, message: '用户名格式错误', trigger: 'blur' }
        ],
        process: [
          { required: true, message: '请输入制作流程', trigger: 'blur' },
        ],
        site: [
          { required: true, message: '请输入地点', trigger: 'blur' },
          // { max: 18, message: '用户名格式错误', trigger: 'blur' }
        ],
        paperType: [
          { required: true, message: '请选择纸张类型', trigger: 'blur' },
          // { max: 18, message: '用户名格式错误', trigger: 'blur' }
        ],
        fileName: [
          { required: true, message: '请上传图片', trigger: 'blur' },
          // { max: 18, message: '用户名格式错误', trigger: 'blur' }
        ],
        institutionDescription: [
          { required: true, message: '请输入机构描述', trigger: 'blur' },
          // { max: 18, message: '用户名格式错误', trigger: 'blur' }
        ],
        eventDate: [
          { required: true, message: '请输入事记出现时间', trigger: 'blur' },
        ],
      },
      activeName1: '1',
      activeName: '1',
      users: [],
      paperId: '',
      options: [],
      value: [],
      list: [],
      loading: false,
      isShow: true,
      dialogVisible7: false,
      adVisible7: false,
      dialogVisible9: false,
      adVisible9: false,
      dialogVisible8: false,
      adVisible8: false,
      teachers: [],
      smid: "",
      paperList: [],
      oneInstitution: [],
      teachers: [],
      currentPage: 1,// 默认显示第几页
      pageSizes: [2, 5, 10, 20, 30, 50],// 个数选择器（可修改）
      pageSize: 10,// 默认每页显示的条数（可修改）
      total: '',// 总记录数
      total1: '',
      total2: '',
      total5: '',
      selectName: [],
      selectName7: [],
      query: "",//检索词
      order: 0,//排序
      oneHis: [],
      editor: '',
      fileName: "",//上传的文件名称
      fileName1: "",//上传的文件名称
      file: null,
      dialogVisible5: false,
      adVisible5: false,
      total4: '',
      typeName: {
        '0': "手工纸",
        '1': "机械纸",
        '2': "半手工半机械纸",
      },//将资源类型转换为对应文字
      fileList: [], // 用于存储文件列表
      fileList1: [], // 用于存储文件列表
      headers: { /* 你的请求头信息，如果有的话 */ },
      dialogVisible: false,
      adVisible: false,
      dialogVisible1: false,
      adVisible1: false,
      dialogVisible2: false,
      adVisible2: false,
      formLabelWidth: '120px',
      typelist1: [{
        paperType: 0,
        typeName: "手工纸",
      }, {
        paperType: 1,
        typeName: "机械纸",
      }, {
        paperType: 2,
        typeName: "半手工半机械纸",
      }], // 资源类型列表
      pickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
    }
  },
  created() {
    // this.getQuery();
    this.gettingData();
    // this.getTeacherIntro();
    // this.getUsers();
    this.getOneHistoricalEvent(this.eventId);

  },

  watch: { '$route': 'gettingData' },
  methods: {
    getTeacherIntro() {
      TeacherIntro().then(response => {
        this.teachers = response.data
      })
    },
    filterTag(value, row, column) {
      const property = column["property"];
      return row[property] === value;
    },
    gettingData() {
      var queryEventId = JSON.parse(this.$Base64.decode(this.$route.query.info))
      this.eventId = queryEventId;
    },
    _filterChange() {
      this.totalSize = this.$refs.table.projectList.length;
      console.log(this.totalSize);
    },
    sendWangEditor(val) {
      this.oneProjects.hyperlinks = val;//去掉p标签
    },

    indexMethods(index) {
      // 当前页数 - 1 * 每页数据条数 + index + 1 （ index 是索引值，从0开始）
      return (this.currentPage - 1) * this.pageSize + index + 1;
    },
    changeOutline(val) {
      this.oneHis.eventDescription = val;
    },
    // 文件上传前的处理
    beforeFileUpload(file) {
      // 这里可以添加对文件的一些检查，比如文件的大小和类型等
      const isLt10M = file.size / 1024 / 1024 < 10;
      if (!isLt10M) {
        this.$message.error('上传的文件大小不能超过 10MB!');
        return false;
      } else {
        // 保存文件对象
        this.file = file;
        this.fileName = file.name;
        return false; // 返回false不会自动上传
      }
    },

    getOneHistoricalEvent(eventId) {
      getOneHistoricalEvent(eventId).then((response) => {
        this.oneHis = response.data[0];
        this.editor = this.oneHis.eventDescription;
        this.fileName = this.oneHis.eventPic;
        console.log(this.oneHis);
      });
    },
    changeEvent() {
      console.log(this.oneHis); // 打印 oneresource 对象
      if (this.oneHis.eventName == "") {
        this.$message({
          type: 'warning',
          message: '事件名称为空，请填写！'
        })
        return false;
      }
      else {
        this.$refs.oneHis.validate(valid => {
          if (valid) {
            this.$confirm('您确定要修改该事件吗?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              // 创建一个 FormData 对象
              const formData = new FormData();
              formData.append('file', this.file);
              // 添加其他表单数据
              formData.append('eventId', this.oneHis.eventId);
              formData.append('eventName', this.oneHis.eventName);
              formData.append('eventDescription', this.oneHis.eventDescription);

              formData.append('eventDate', new Date(this.oneHis.eventDate));
              // formData.append('firstTime', this.onePaper.firstTime);
              // formData.append('process', this.onePaper.process);
              formData.append('eventPic', this.oneHis.eventPic);
              // location.reload();//自动刷新  
              editEvent(formData).then(response => {
                if (response.message == "操作成功") {
                  this.$router.push('/management/cooperationManagement')
                  this.$message({
                    type: 'success',
                    message: "修改成功！"
                  })
                  this.dialogVisible = false
                  // this.getProjects()
                } else if (response.message == "暂无权限") {
                  this.$message({
                    type: 'error',
                    message: '暂无编辑权限！'
                  })
                  this.dialogVisible = false
                } else {
                  this.$message({
                    type: 'error',
                    message: response.message
                  })
                }
              })
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      }
    },
    back() {
      this.$router.push('/management/cooperationManagement')
    },
    //删除
    indexMethods4(index) {
      // 当前页数 - 1 * 每页数据条数 + index + 1 （ index 是索引值，从0开始）
      return (this.currentPage4 - 1) * this.pageSize4 + index + 1;
    },
  },
  filters: {
    formatData(data) {
      return moment(data).format('YYYY-MM-DD')
    }
  }
}

</script>
<style scoped>
.breadcrumb {
  font-size: 25px;
  color: #080100;
  font-family: "楷体";
  margin-top: 40px;
}

.el-breadcrumb__item {
  font-size: 22px;
  font-family: "楷体";
}
</style>