<template>
  <div> <!--添加专业对话框-->
    <div style="padding: 10px;">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/management/projectManagement' }">纸张详情管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/management/addProject' }">新增纸张</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div slot="title">
    </div>
    <span style="font-family: 'STKaiti'; font-size: 26px;">新增纸张</span>
    <el-tabs class="el-tab-div" style="font-family: STKaiti;font-size: 26px;">
      <el-form :model="addPaper" ref="addPaper" style="font-size: 32px !important" >
        <el-form-item label="纸张名称" :label-width="formLabelWidth" prop="paperName">
          <el-input v-model="addPaper.paperName"></el-input>
        </el-form-item>
        <el-form-item label="纸张简介" :label-width="formLabelWidth" prop="paperDes">
          <el-input v-model="addPaper.paperDes" type="textarea" :rows="5" prop="paperDes"></el-input>
        </el-form-item>
        <el-form-item label="纸张首页图片" :label-width="formLabelWidth" style="width:30%;">
          <el-upload class="upload-demo" ref="upload" action="doUpload"   :file-list="fileList"
            style="float:right" :before-upload="beforeFileUpload" :on-change="changeCarPicture1" :on-preview="handlePictureCardPreview"  
            :on-remove="handleRemove" >
            <el-button slot="trigger" type="primary" @change="handleFileChange">上传文件<i class="el-icon-upload el-icon--right"></i></el-button>
            <div slot="tip" class="el-upload__tip">最多上传1张,只能上传1M以内的文件</div>
          </el-upload>
          <img :src="addPaper.pictureLocation" height=150px  style="float: left;margin-top: 10px;">
          <el-dialog 
        	:visible.sync="dialogVisibles" 
        	append-to-body 
        	class="dialog1" 
        	width="40%">
          <img :src="dialogImageUrl" alt="" class="ims">
        </el-dialog>
        </el-form-item>
        <el-form-item label="纸张图片" :label-width="formLabelWidth" style="width:30%;">
          <el-upload class="upload-demo" ref="upload" action="doUpload" :limit="5" :file-list="fileList"
            style="float:right" :before-upload="beforeFileUpload" :on-change="changeCarPicture" :on-preview="handlePictureCardPreview"  
            :on-remove="handleRemove" multiple>
            <el-button slot="trigger" type="primary" @change="handleFileChange">上传文件<i class="el-icon-upload el-icon--right"></i></el-button>
            <div slot="tip" class="el-upload__tip">最多上传5张,只能上传1M以内的文件</div>
          </el-upload>
          <!-- <img :src="addPaper.pictureList" height=150px  style="float: left;margin-top: 10px;"> -->
          <el-dialog 
        	:visible.sync="dialogVisibles" 
        	append-to-body 
        	class="dialog1" 
        	width="40%">
          <img :src="dialogImageUrl" alt="" class="ims">
        </el-dialog>
        </el-form-item>
        <el-form-item label="制作流程" :label-width="formLabelWidth" prop="process">
          <el-input v-model="addPaper.process" type="textarea" :rows="6"></el-input>
        </el-form-item>
        <el-form-item label="纸张加工工艺" :label-width="formLabelWidth" prop="process">
          <el-input v-model="addPaper.technology" type="textarea" :rows="6"></el-input>
        </el-form-item>
        <el-form-item label="纸张类型" :label-width="formLabelWidth" prop="paperType">
          <el-select v-model="addPaper.paperType" placeholder="请选择" style="width:100%;font-family: 'STKaiti'; " filterable>
            <el-option v-for="item in typelist1" :key="item.paperType" :label="item.typeName" :value="item.paperType"
              style="font-family: 'STKaiti'; ">
            </el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="地点" :label-width="formLabelWidth" prop="site" style="text-align: left;">
          <el-cascader size="large" :options="options1" v-model="selectedOptions" @change="handleChange"
            style="width: 500px;">
          </el-cascader>
        </el-form-item> -->
        <el-form-item label="纸张产地" :label-width="formLabelWidth" prop="location">
          <el-input v-model="addPaper.location" type="textarea" :rows="1" prop="location"></el-input>
        </el-form-item>
        <!-- <el-form-item label="相关档案" :label-width="formLabelWidth">
          <template slot-scope="props">
            <el-select v-model="addPaper.archivePaperList" :props="{ key: 'archiveId', label: 'archiveName' }" multiple
              placeholder="请选择" @click.native="getArchives" style="width:100%" filterable>
              <el-option  v-for="item in archives" :key="item.archiveId" :label="item.archiveName" :value="item.archiveId">
              </el-option>
            </el-select>
          </template>
        </el-form-item> -->
      </el-form>
      <div class="dialog-footer">
        <el-button type="primary" @click="addpaper()">添 加</el-button>
        <el-button type="primary" @click="back">取 消</el-button>
      </div>
    </el-tabs>
  </div>
</template>
<script>
import './filters.js'

import { regionData, CodeToText, TextToCode } from 'element-china-area-data'

// import WangEditor from '@/components/WangEditor.vue'
import { addPaper } from "@/api/paper"
import { getFibers1 } from '@/api/fiber';
import { getArchives } from '@/api/archive';


export default {
  components: {
    // WangEditor
  },
  data() {
    return {
      rules: {
        paperName: [
          { required: true, message: '请输入纸张名称', trigger: 'blur' },
        ],
        paperDes: [
          { required: true, message: '请输入纸张描述', trigger: 'blur' },
          // { max: 18, message: '用户名格式错误', trigger: 'blur' }
        ],
        process: [
          { required: true, message: '请输入制作流程', trigger: 'blur' },
        ],
        paperType: [
          { required: true, message: '请选择纸张类型', trigger: 'blur' },
          // { max: 18, message: '用户名格式错误', trigger: 'blur' }
        ],
        technology: [
          { required: true, message: '请输入纸张加工工艺', trigger: 'blur' },
          // { max: 18, message: '用户名格式错误', trigger: 'blur' }
        ],
      },
      dialogImageUrl: '',
        dialogVisible: false,
        disabled: false,
      activeName: '1',
      users: [],
      options1: regionData,
      selectedOptions: [],
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
        '2': "其他",
      },//将资源类型转换为对应文字
      typelist1: [{
        paperType: 0,
        typeName: "手工纸",
      }, {
        paperType: 1,
        typeName: "机械纸",
      }, {
        paperType: 2,
        typeName: "其他",
      }], // 资源类型列表
      fibers: [],
      archives: [],
      fileList: [], // 用于存储文件列表
      fileList1: [], // 用于存储文件列表
      dialogVisible: false,
      adVisible: false,
      dialogVisible1: false,
      adVisible1: false,
      dialogVisible2: false,
      adVisible2: false,
      formLabelWidth: '120px',
      imgs: [],
      firstTime: '',
      addPaper: {
        paperName: "",
        paperDes: '',
        paperType: '',
        picture:'',        
        location: '',
        technology: '',
        process: '',
        archiveIds:[]
        // projectContent: '',
        // status: '',
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
      // fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}]
    };
  },
  created() {
    this.chooseTimeRange()
    this.getFibers();
    this.getArchives();
  },
  computed: {

  },
  methods: {

    chooseTimeRange(firstTime) {
      this.firstTime = firstTime;
      console.log(this.firstTime)
    },
    getFibers() {
      getFibers1().then(response => {
        this.fibers = response.data
        console.log(this.fibers)
      })
    },
    getArchives() {
      getArchives().then(response => {
        this.archives = response.data
        console.log(this.archives)
      })
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
    remoteMethod(query) {
      if (query !== '') {
        this.loading = true;
        SelectProject(query).then((response) => {
          this.projectList = response.data;
        });
        setTimeout(() => {
          this.loading = false;
          this.options = this.listSet.filter(item => {
            return item.label.toLowerCase()
              .indexOf(query.toLowerCase()) > -1;
          });
        }, 1000);
      } else {
        this.options = [];
      }
    },

    back() {
      this.$router.push('/management/projectManagement')
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
      const isLt1M = file.size / 1024 / 1024 < 1;
      if (!isLt1M) {
        this.$message.error('上传的文件大小不能超过 1MB!');
        return false;
      } else {
        
        let _this = this
      const reader = new FileReader()
      reader.readAsDataURL(file.raw)
      reader.onload = function(e) {
        undefined
        _this.imgs.push(this.result)
      }
      this.getimg(file)
      console.log(file);
        // 保存文件对象
        this.file = file;
        this.fileName = file.name;
        return false; // 返回false不会自动上传
      }
    },
    changeCarPicture(file, fileList) {
      console.log(file.raw.name)
       	   // 图片转成base64上传
        	let reader = new FileReader();
        	reader.onload = () => {
          	 	let _base64 = reader.result;
          		let BASE64 = _base64.split(",");
         		this.carPicture = file.raw.name+":"+BASE64[1];  //赋值
       	    };
          reader.readAsDataURL(file.raw);
          let timer = setInterval(() => {
            this.imgs.push(this.carPicture)
          console.log( this.imgs)
          console.log(this.carPicture)

          //清除一次性定时器
          clearTimeout(timer)

     //需要定时执行的代码
},50)
        },
        changeCarPicture1(file, fileList) {
      console.log(file.raw.name)
       	   // 图片转成base64上传
        	let reader = new FileReader();
        	reader.onload = () => {
          	 	let _base64 = reader.result;
          		let BASE64 = _base64.split(",");
         		this.pictureLocation = file.raw.name+":"+BASE64[1];  //赋值
       	    };
          reader.readAsDataURL(file.raw);
          let timer = setInterval(() => {
            // this.imgs.push(this.pictureLocation)/
          console.log( this.pictureLocation)
          //清除一次性定时器
          clearTimeout(timer)

     //需要定时执行的代码
},50)
          
          
        },
    handleRemove(file) {
        console.log(file);
      },
      handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },
      handleDownload(file) {
        console.log(file);
      },
    handleChange(value) {
      console.log(value)
      console.log(value[0])
      console.log(value[1])
      console.log(value[2])
      this.getCodeToText(null, value)
    },
    getCodeToText(codeStr, codeArray) {
      console.log(codeArray)
      if (null === codeStr && null === codeArray) {
        return null;
      } else if (null === codeArray) {
        codeArray = codeStr.split(",");
      }
      let area = "";
      switch (codeArray.length) {
        case 1:
          area += CodeToText[codeArray[0]];
          break;
        case 2:
          area += CodeToText[codeArray[0]] + "/" + CodeToText[codeArray[1]];
          break;
        case 3:
          area +=
            CodeToText[codeArray[0]] +
            "/" +
            CodeToText[codeArray[1]] +
            "/" +
            CodeToText[codeArray[2]];
          break;
        default:
          break;
      }
      console.log(area)
      this.addPaper.site = area
      console.log(this.addPaper.site)
      return area;
    },
    // 编辑按钮点击事件
    // changeCarPicture(file, fileList) {
    //    	   // 图片转成base64上传
    //     	let reader = new FileReader();
    //     	reader.onload = () => {
    //       	 	let _base64 = reader.result;
    //       		let BASE64 = _base64.split(",");
    //      		this.carPicture = BASE64[1];  //赋值
    //    	    };
    //       reader.readAsDataURL(file.raw); 
    //       console.log(this.carPicture)
          
    //     },
        
    addpaper() {
      console.log(this.addPaper); // 打印 addresource 对象
      if (this.addPaper.paperName == "") {
        this.$message({
          type: 'warning',
          message: '纸张名称为空，请填写！'
        })
        return false;
      }
      else {
        this.$refs.addPaper.validate(valid => {
          if (valid) {
            this.$confirm('您确定要添加该项目吗?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              var paperName=this.addPaper.paperName
              var paperDes=this.addPaper.paperDes
              var paperType=this.addPaper.paperType
              var location=this.addPaper.location
              var pictureLocation = this.pictureLocation
              var fileParam = this.imgs
              // var picture="asdasd"
              var process=this.addPaper.process
              var technology=this.addPaper.technology
              // var archiveIds = this.addPaper.archivePaperList
              addPaper({technology,process,location,paperType,paperDes,paperName,pictureLocation,fileParam}).then(response => {
                if (response.message == "添加纸张成功！") {
                  this.$message({
                    type: 'success',
                    message: "添加成功！"
                  })
                  // location.reload();//自动刷新
                  this.adVisible = false
                  this.$router.push('/management/projectManagement')
                  // this.getProjects(order = 0)
                } else if (response.code == 500) {
                  this.$message({
                    type: 'error',
                    message: '错误！'
                  })
                  this.adVisible = false
                } else {
                  this.$message({
                    type: 'error',
                    message: '请填完！'
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
  font-family: "STKaiti";
  margin-top: 40px;
}

.el-breadcrumb__item {
  font-size: 22px;
  font-family: "STKaiti";
}
</style>