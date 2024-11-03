<template>
  <div> <!--添加专业对话框-->
    <div style="padding: 10px;">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/management/introManagement' }">纤维原料管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/management/addIntro' }">新增纤维原料</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div slot="title">
      <!-- <span style="font-family: '楷体'; font-size: 20px;">新增纸张</span> -->
    </div>
    <span style="font-family: '楷体'; font-size: 26px;">新增纤维原料</span>
    <el-tabs class="el-tab-div" style="font-family: 楷体;font-size: 26px;">
      <el-form :model="addFiber" ref="addFiber" style="font-size: 32px !important" :rules="rules">
        <el-form-item label="纤维原料名称" :label-width="formLabelWidth" prop="fiberType">
          <el-input v-model="addFiber.fiberType"></el-input>
        </el-form-item>
        <el-form-item label="原料特点" :label-width="formLabelWidth" prop="characteristic">
          <el-input v-model="addFiber.characteristic" type="textarea" :rows="5" prop="characteristic"></el-input>
        </el-form-item>
      </el-form>
      <div class="dialog-footer">
        <el-button type="primary" @click="addfiber()">添 加</el-button>
        <el-button type="primary" @click="back">取 消</el-button>
      </div>
    </el-tabs>
  </div>
</template>
<script>
import './filters.js'
import Pagination from "@/components/Pagination";
import errorLog from '@/store/modules/errorLog';
// import WangEditor from '@/components/WangEditor.vue'
import { addFiber } from "@/api/fiber"


export default {
  components: {
    // WangEditor
  },
  data() {
    return {
      rules: {
        fiberType: [
          { required: true, message: '请输入纤维原料名称', trigger: 'blur' },
        ],
        characteristic: [
          { required: true, message: '请输入纤维特点', trigger: 'blur' },
          // { max: 18, message: '用户名格式错误', trigger: 'blur' }
        ],
        process: [
          { required: true, message: '请输入制作流程', trigger: 'blur' },
        ],
        institutionLocation: [
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
        location: [
          { required: true, message: '请输入产地', trigger: 'blur' },
          // { max: 18, message: '用户名格式错误', trigger: 'blur' }
        ],
        institutionDate: [
          { required: true, message: '请输入机构出现时间', trigger: 'blur' },
        ],
      },
      activeName1: '1',
      activeName: '1',
      users: [],
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
      smid: "",
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
      oneProjects: [],
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
      fileList: [], // 用于存储文件列表
      fileList1: [], // 用于存储文件列表
      dialogVisible: false,
      adVisible: false,
      dialogVisible1: false,
      adVisible1: false,
      dialogVisible2: false,
      adVisible2: false,
      formLabelWidth: '120px',
      // addPaper: [],
      addFiber: [],
      firstTime: '',
      addPaper: {
        paperName: "",
        paperDes: '',
        paperType: '',
        paperId: '',
        site: '',
        location: '',
        process: '',
        picture: '',
        firstTime: '',
      },// 新增表单
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
    this.chooseTimeRange()


  },
  computed: {

  },
  methods: {

    chooseTimeRange(firstTime) {
      this.firstTime = firstTime;
      console.log(this.firstTime)
    },

    filterTag(value, row, column) {
      const property = column["property"];
      return row[property] === value;
    },
    _filterChange() {
      this.totalSize = this.$refs.table.projectList.length;
      console.log(this.totalSize);
    },
    sendWangEditor(val) {
      this.oneProjects.hyperlinks = val;//去掉p标签
    },

    reload() {//局部刷新
      this.isShow = false;
      this.$nextTick(() => {
        this.isShow = true;
        this.$refs.table.bodyWrapper.scrollTop = 0;
      })
    },
    back() {
      this.$router.push('/management/introManagement')
    },
    selectList1() {
      Projects().then((response) => {
        this.selectName = response.data;
      })
    },
    resetQuery() {
      //重置查询条件
      this.query = '';
      //分页刷新,回到首页
      this.currentPage = 1;
      this.pageSize = 10;
      //重置筛选条件
      this.typefilter = '';
      this.coursefilter = '';
      this.getProjects();
    },

    indexMethods(index) {
      // 当前页数 - 1 * 每页数据条数 + index + 1 （ index 是索引值，从0开始）
      return (this.currentPage - 1) * this.pageSize + index + 1;
    },
    // 获取所有课程
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
    // 编辑按钮点击事件
    addfiber() {
      console.log(this.addFiber); // 打印 addresource 对象
      if (this.addFiber.fiberType == "") {
        this.$message({
          type: 'warning',
          message: '原料名称为空，请填写！'
        })
        return false;
      }
      else {
        this.$refs.addFiber.validate(valid => {
          if (valid) {

            this.$confirm('您确定要添加该原料吗?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              const formData = new FormData();
              formData.append('file', this.file);
              // 添加其他表单数据
              // formData.append('institutionId', this.addInstitution.institutionId);
              formData.append('fiberType', this.addFiber.fiberType);
              formData.append('characteristic', this.addFiber.characteristic);
              // formData.append('institutionDate', this.addInstitution.institutionDate);
              // formData.append('institutionDescription', this.addInstitution.institutionDescription);
              // formData.append('institutionPicture', this.addInstitution.institutionPicture);
              addFiber(formData).then(response => {
                if (response.message == "操作成功") {
                  this.$router.push('/management/introManagement')
                  this.$message({
                    type: 'success',
                    message: "添加成功！"
                  })
                  // location.reload();//自动刷新
                  this.adVisible = false
                  // this.getProjects(order = 0)
                } else if (response.code == 500) {
                  this.$message({
                    type: 'error',
                    message: '纤维原料名重复！'
                  })
                  this.adVisible = false
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