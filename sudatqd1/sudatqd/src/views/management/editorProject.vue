<template>
  <div slot="title">
    <div style="padding: 10px;">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/management/projectManagement' }">纸张详情管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/management/editorProject' }">编辑纸张</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <span style="font-family: 'STKaiti'; font-size: 26px;">编辑纸张</span>
    <el-tabs class="el-tab-div" style="font-family: STKaiti;font-size: 26px;">
      <el-form :model="onePaper" ref="onePaper" style="font-size: 32px !important" >
        <el-form-item label="纸张名称" :label-width="formLabelWidth" prop="paperName">
          <el-input v-model="onePaper.paperItem.paperName"></el-input>
        </el-form-item>
        <el-form-item label="纸张简介" :label-width="formLabelWidth" prop="paperDes">
          <el-input v-model="onePaper.paperItem.paperDes" type="textarea" :rows="5" prop="paperDes"></el-input>
        </el-form-item>
        <el-form-item label="纸张首页图片" :label-width="formLabelWidth" style="width:30%;">
          <el-upload class="upload-demo" ref="upload" action="doUpload"   :file-list="fileList"
            style="float:right" :before-upload="beforeFileUpload" :on-change="changeCarPicture1" :on-preview="handlePictureCardPreview"  
            :on-remove="handleRemove" >
            <el-button slot="trigger" type="primary" @change="handleFileChange">上传文件<i class="el-icon-upload el-icon--right"></i></el-button>
            <div slot="tip" class="el-upload__tip">最多上传1张,只能上传1M以内的文件</div>
          </el-upload>
          <img :src="onePaper.paperItem.pictureLocation" height=150px  style="float: left;margin-top: 10px;">
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
          <img :src="onePaper.paperItem.pictureList" height=150px  style="float: left;margin-top: 10px;">
          <el-dialog 
        	:visible.sync="dialogVisibles" 
        	append-to-body 
        	class="dialog1" 
        	width="40%">
          <img :src="dialogImageUrl" alt="" class="ims">
        </el-dialog>
        </el-form-item>
        <el-form-item label="制作流程" :label-width="formLabelWidth" prop="process">
          <el-input v-model="onePaper.paperItem.process" type="textarea" :rows="6"></el-input>
        </el-form-item>
        <el-form-item label="纸张加工工艺" :label-width="formLabelWidth" prop="process">
          <el-input v-model="onePaper.paperItem.technology" type="textarea" :rows="6"></el-input>
        </el-form-item>
        <el-form-item label="纸张类型" :label-width="formLabelWidth" prop="paperType">
          <el-select v-model="onePaper.paperItem.paperType" placeholder="请选择" style="width:100%;font-family: 'STKaiti'; " filterable>
            <el-option v-for="item in typelist1" :key="item.paperType" :label="item.typeName" :value="item.paperType"
              style="font-family: 'STKaiti'; ">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="产地" :label-width="formLabelWidth" prop="location">
          <el-input v-model="onePaper.paperItem.location"></el-input>
        </el-form-item>
      </el-form>
      <div class="dialog-footer">
        <el-button type="primary" @click="changeProject()">修 改</el-button>
        <el-button type="primary" @click="back">取 消</el-button>
      </div>
    </el-tabs>
  </div>
</template>
<script>
// import Carousel from '@/components/Carousel.vue';
import './filters.js'
import { regionData, CodeToText, TextToCode } from 'element-china-area-data'
import { getOnePaper, editPaper } from "@/api/paper"
// import WangEditor from '@/components/WangEditor.vue'
import { getFibers1 } from '@/api/fiber';
import { getArchives } from '@/api/archive';
export default {
  components: {
    // WangEditor
  },
  data() {
    return {
      dialogImageUrl: '',   //浏览图片的路径
      options1: regionData,
      selectedOptions: [],
      dialogVisibles: false,  //控制浏览图片的对话框显示隐藏
      activeName1: '1',
      activeName: '1',
      users: [],
      fiberList: [],
      fibers: [],
      base64Image: null,
      archives: [],
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
      fiberlist1: [],
      archivelist1: [],
      onePaper: [],
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
      oneProjects: [],
      editor: '',
      fileName: "",//上传的文件名称
      fileName1: "",//上传的文件名称
      file: null,
      dialogVisible5: false,
      adVisible5: false,
      total4: '',
      codeArray: [],
      typeName: {
        '0': "手工纸",
        '1': "机械纸",
        '2': "其他",
      },//将资源类型转换为对应文字
      fileList: [], // 用于存储文件列表
      headers: { /* 你的请求头信息，如果有的话 */ },
      dialogVisible: false,
      adVisible: false,
      dialogVisible1: false,
      adVisible1: false,
      dialogVisible2: false,
      adVisible2: false,
      formLabelWidth: '120px',
      imgs:[],
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
    this.getArchives();
    this.getOnePaper(this.paperId);
  },

  watch: { '$route': 'gettingData' },
  methods: {
    getTeacherIntro() {
      TeacherIntro().then(response => {
        this.teachers = response.data
      })
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
      this.onePaper.site = area
      console.log(this.onePaper.site)
      return area;
    },
    filterTag(value, row, column) {
      const property = column["property"];
      return row[property] === value;
    },
    gettingData() {
      var queryPaperId = JSON.parse(this.$Base64.decode(this.$route.query.info))
      this.paperId = queryPaperId;
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
    handlePreview(file) {
        console.log(file);
      },
      handleRemove(file, fileList) {//文件移除
      if (this.isAdd) {
        const reader = new FileReader()
        reader.readAsDataURL(file.raw)
        let _this = this
        reader.onload = function(e) {
          undefined
        }
      } else {
        if (file.raw) {
          const reader = new FileReader()
          reader.readAsDataURL(file.raw)
          let _this = this
          reader.onload = function(e) {
            undefined
          }
        } else {
          //回显图片，执行删除并添加到图片列表（后端服务器接受的图片列表中）
          this.construction.delImages.push(file.url)
        }
      }
      console.log(fileList);
      this.delimg(fileList)
    },
    delimg(event) {
      this.img64=[]
      for(var i=0;i<event.length;i++){
        const file = event[i].raw;
      if (!file) {
        return;
      }
      const reader = new FileReader();
      reader.onload = (e) => {//加载图片转base64
        this.getimgbase64 = e.target.result;
        // console.log(this.getimgbase64);
        this.img64.push(this.getimgbase64)
        // console.log(this.img64);//base64文件
      };
      reader.readAsDataURL(file);//①读取文件
      }
      
    },
      submitUpload() {
        console.log( fileList);
        this.$refs.upload.submit();
      },
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
    getOnePaper(paperId) {
      getOnePaper(paperId).then((response) => {
        this.onePaper = response.data;
        this.fiberList1 = this.onePaper.fiberlist1;
        this.paperList1 = this.onePaper.archivePaperList;
        // this.fileName = this.onePaper.paperItem.pictureLocation;
        // this.selectedOptions = TextToCode[this.onePaper.site.split('/')[0]][this.onePaper.site.split('/')[1]][this.onePaper.site.split('/')[2]].code;
        console.log(this.paperList1);
      });
    },
    changeProject() {
      console.log(this.onePaper); // 打印 oneresource 对象
      if (this.onePaper.paperName == "") {
        this.$message({
          type: 'warning',
          message: '纸张名称为空，请填写！'
        })
        return false;
      }
      else {
        this.$refs.onePaper.validate(valid => {
          console.log(this.imgs)
          if (valid) {
            this.$confirm('您确定要修改该纸张吗?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              // 创建一个 FormData 对象
              var paperId = this.onePaper.paperItem.paperId
              var paperName = this.onePaper.paperItem.paperName
              var paperDes = this.onePaper.paperItem.paperDes
              var paperType = this.onePaper.paperItem.paperType
              var location = this.onePaper.paperItem.location
              var fileParam = this.imgs
              var pictureLocation = this.pictureLocation
              // fileParam.push(this.imgs)
              var process = this.onePaper.paperItem.process
              var technology = this.onePaper.paperItem.technology
              editPaper({process,paperId,paperName,paperDes,paperType,location,technology,fileParam,pictureLocation}).then(response => {
                console.log(fileParam)
                if (response.message == "纸张信息已更新！") {
                  this.$router.push('/management/projectManagement')
                  this.$message({
                    type: 'success',
                    message: "纸张信息已更新！"
                  })
                  this.dialogVisible = false
                  // this.getProjects()
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
      this.$router.push('/management/projectManagement')
    },
    //删除
    indexMethods4(index) {
      // 当前页数 - 1 * 每页数据条数 + index + 1 （ index 是索引值，从0开始）
      return (this.currentPage4 - 1) * this.pageSize4 + index + 1;
    },
    handleFileChange(event) {
      const file = event.target.files[0]
      const fileName =event.target.files[0].name
      console.log(fileName)
      console.log(event.target.files)
      if (file) {
        this.convertToBase64(file)
      }
    },
    convertToBase64(file) {
      const reader = new FileReader()
      reader.onload = (e) => {
        var imgArr = [];
        imgArr = e.target.result
      // var imgArr = [];
      this.onePaper.paperItem.pictureLocation =  imgArr.split(',')
      console.log(this.onePaper.paperItem.pictureLocation[1]) }
      const files = file.name+":"+this.onePaper.paperItem.pictureLocation[1];
      console.log(files)
      reader.readAsDataURL(file)
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