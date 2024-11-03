<template style="height:2400px">
  <div slot="title">
    <div style="padding: 10px;">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/management/projectManagement' }">档案详情管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/management/editorProject' }">编辑档案</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <span style="font-family: 'STKaiti'; font-size: 26px;">编辑档案</span>
    <el-tabs class="el-tab-div" style="font-family: STKaiti;font-size: 26px;">
      <el-form :model="oneArchive" ref="oneArchive" style="font-size: 32px !important" >
        <el-form-item  style="font-size: 32px !important">
          <div style="font-size: 24px !important;text-align: left;" >档案的基本信息:</div> 
        </el-form-item> 
        <el-row >
            <el-col :span="8">
              <el-form-item label="档案名称" :label-width="formLabelWidth"  >
                <el-input v-model="oneArchive.archiveItem.archiveName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="档案编号" :label-width="formLabelWidth"  >
                <el-input v-model="oneArchive.recordingItem.bianHao"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="档案责任者" :label-width="formLabelWidth"  >
                <el-input v-model=" oneArchive.recordingItem.author"  ></el-input>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row >
          <el-col :span="8">
            <el-form-item label="语种" :label-width="formLabelWidth"  >
              <el-input v-model="oneArchive.recordingItem.language"   ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="分类号" :label-width="formLabelWidth"  >
              <el-input v-model=" oneArchive.recordingItem.classification"   ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="档号" :label-width="formLabelWidth"  >
              <el-input v-model="oneArchive.recordingItem.reference"   ></el-input>
            </el-form-item>
          </el-col>
      </el-row>
      <el-row >
        <el-col :span="10">
          <el-form-item label="档案图片" :label-width="formLabelWidth" >
            <el-input v-model="fileName" :readonly='true' placeholder="文件名" style="width:388px;" clearable>
            </el-input>
            <el-upload class="upload-demo" ref="upload" action="doUpload" :limit="1" :file-list="fileList"
              style="float:right" :before-upload="beforeFileUpload">
              <el-button slot="trigger" type="primary">上传文件<i class="el-icon-upload el-icon--right"></i></el-button>
              <div slot="tip" class="el-upload__tip">只能上传1M以内的文件</div>
            </el-upload>
            <img :src="oneArchive.pictureLocation" width="40%" style="float: center;margin-top: 10px;">
          </el-form-item>
        </el-col>
        
        <el-col :span="6">
          <el-form-item label="档案出现时间" :label-width="formLabelWidth"  
           >
    
          <el-input v-model="oneArchive.recordingItem.time"   ></el-input>
        </el-form-item> 
        </el-col>
        <el-col :span="8">
          <el-form-item label="档案数量" :label-width="formLabelWidth"  
           >
         
          <el-input v-model="oneArchive.archivePaperItem.shuLiang"   ></el-input>
        </el-form-item> 
        </el-col>
    </el-row>
    <el-form-item label="档案简介" :label-width="formLabelWidth"  >
      <el-input v-model="oneArchive.archiveItem.archiveContent" type="textarea" :rows="5" ></el-input>
    </el-form-item>
    <el-row >
      <el-col :span="6">
        <el-form-item label="信息采集时间" :label-width="formLabelWidth" style="margin-left: 1px;width: 300px;"
          prop="oneArchive.archivePaperItem.creationTime">
        
          <el-date-picker v-model="oneArchive.archivePaperItem.creationTime" type="date" placeholder="选择日期" align="right"
            :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item> 
      </el-col>
      <el-col :span="8">
        <el-form-item label="使用纸张" :label-width="formLabelWidth">
          <template slot-scope="props">
            <el-select v-model="oneArchive.archivePaperItem.paperId" :props="paperId" placeholder="请选择" style="width:100%"
              @click.native="getPaper" filterable>
              <el-option v-for="item in papers" :key="item.paperId" :label="item.paperName" :value="item.paperId">
              </el-option>
            </el-select>
          </template>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="所属机构" :label-width="formLabelWidth">
          <template slot-scope="props">
            <el-select v-model="oneArchive.archiveItem.institutionId" :props="institutionId" placeholder="请选择" style="width:100%"
              @click.native="getCourses" filterable>
              <el-option v-for="item in institutions" :key="item.institutionId" :label="item.institutionName"
                :value="item.institutionId">
              </el-option>
            </el-select>
          </template>
        </el-form-item>
      </el-col>
  </el-row>
    <el-form-item  style="font-size: 32px !important">
      <div style="font-size: 24px !important;text-align: left;" >档案的纸张信息:</div> 
    </el-form-item> 
    <el-row >
      <el-col :span="8">
        <el-form-item label="档案纸张尺寸" :label-width="formLabelWidth"  
          prop="oneArchive.archivePaperItem.size ">
        
          <el-input v-model="oneArchive.archivePaperItem.size" ></el-input>
        </el-form-item> 
      </el-col>
      <el-col :span="8">
        <el-form-item label="档案纸张ph值" :label-width="formLabelWidth"  
          prop="oneArchive.archivePaperItem.ph ">
        
          <el-input v-model="oneArchive.archivePaperItem.ph" ></el-input>
        </el-form-item> 
      </el-col>
      <el-col :span="8">
        <el-form-item label="档案纸张尺寸" :label-width="formLabelWidth"  
          >
         
          <el-input v-model="oneArchive.archivePaperItem.size" ></el-input>
        </el-form-item> 
      </el-col>
  </el-row>
  <el-row >
    <el-col :span="8">
      <el-form-item label="档案纸张厚度" :label-width="formLabelWidth"  
        prop="oneArchive.archivePaperItem.thickness ">
       
        <el-input v-model="oneArchive.archivePaperItem.thickness" ></el-input>
      </el-form-item> 
    </el-col>
    <el-col :span="8">
      <el-form-item label="档案纸张白度" :label-width="formLabelWidth"  
        prop="oneArchive.archivePaperItem.whiteness ">
      
        <el-input v-model="oneArchive.archivePaperItem.whiteness" ></el-input>
      </el-form-item> 
    </el-col>
    <el-col :span="8">
      <el-form-item label="档案纸张色度" :label-width="formLabelWidth"  
        prop="oneArchive.archivePaperItem.color ">
     
        <el-input v-model="oneArchive.archivePaperItem.color" ></el-input>
      </el-form-item> 
    </el-col>
  </el-row>  
    <el-row >
    <el-col :span="8">
      <el-form-item label="纸张横纹数量" :label-width="formLabelWidth"  
        prop="oneArchive.archivePaperItem.band ">
      
        <el-input v-model="oneArchive.archivePaperItem.band" ></el-input>
      </el-form-item> 
    </el-col>
    <el-col :span="8">
      <el-form-item label="纸张纵纹间距" :label-width="formLabelWidth"  
        prop="oneArchive.archivePaperItem.zong">
 
        <el-input v-model="oneArchive.archivePaperItem.zong" ></el-input>
      </el-form-item> 
    </el-col>
    <el-col :span="8">
      <el-form-item label="档案用纸层数" :label-width="formLabelWidth"  
        prop="oneArchive.archivePaperItem.numbers ">
      
        <el-input v-model="oneArchive.archivePaperItem.numbers" ></el-input>
      </el-form-item> 
    </el-col>
  </el-row>
  <el-row >
    <el-col :span="8">
      <el-form-item label="用纸纤维分布" :label-width="formLabelWidth"  
        prop="oneArchive.archivePaperItem.distribution "  placeholder="请选择" style="width:100%;font-family: 'STKaiti'; " filterable>
       
        <el-select v-model="oneArchive.archivePaperItem.distribution" placeholder="请选择" style="width:100%;font-family: 'STKaiti'; " filterable>
          <el-option v-for="item in typelist1" :key="item.distribution" :label="item.typeName" :value="item.distribution"
            style="font-family: 'STKaiti'; ">
          </el-option>
        </el-select>
      
      </el-form-item> 
 
    </el-col>
    <el-col :span="8">
      <el-form-item label="纸张帘纹情况" :label-width="formLabelWidth"  
        prop="oneArchive.archivePaperItem.lianWen">
       
        <el-input v-model="oneArchive.archivePaperItem.lianWen" ></el-input>
      </el-form-item> 
    </el-col>
    <el-col :span="8">
      <el-form-item label="档案纸张含水量" :label-width="formLabelWidth"  
        prop="oneArchive.archivePaperItem.beatDeg">
    
        <el-input v-model="oneArchive.archivePaperItem.beatDeg" ></el-input>
      </el-form-item> 
    </el-col>
  </el-row>
  <el-row  >
      <el-col :span="6">
        <el-form-item label="纸张破损程度" :label-width="formLabelWidth" prop="fiber">
          <el-input v-model="oneArchive.archivePaperItem.degFiber" prop="fiber"></el-input>
        </el-form-item>
      </el-col>
  </el-row>
  <el-form-item  style="font-size: 32px !important">
    <div style="font-size: 24px !important;text-align: left;" >档案的纸张纤维信息:</div> 
  </el-form-item> 
  <el-row >
    <el-col :span="8">
      <el-form-item label="档案纸张成分" :label-width="formLabelWidth">
        <template slot-scope="props">
          <el-select v-model="oneArchive.archiveComponentItem.componentId " :props="componentId" 
            placeholder="请选择" @click.native="getComponentList" style="width:100%"  >
            <el-option  v-for="item in components" :key="item.componentId" :label="item.componentName" :value="item.componentId">
            </el-option>
          </el-select>
        </template>
      </el-form-item> 
    </el-col>
    <el-col :span="8">
      <el-form-item label="纸张成分类型" :label-width="formLabelWidth"  
        prop="oneArchive.archiveComponentItem.componentType"  placeholder="请选择" style="width:100%;font-family: 'STKaiti'; " filterable>
      
        <el-select v-model="oneArchive.archiveComponentItem.componentType" placeholder="请选择" style="width:100%;font-family: 'STKaiti'; " filterable>
          <el-option v-for="item in typelist" :key="item.componentType" :label="item.typeName" :value="item.componentType"
            style="font-family: 'STKaiti'; ">
          </el-option>
        </el-select>
      
      </el-form-item> 
    </el-col>
    <el-col :span="8">
      <el-form-item label="纸张成分描述" :label-width="formLabelWidth"  
        prop="oneArchive.archiveComponentItem.description">
      
        <el-input v-model="oneArchive.archiveComponentItem.description" ></el-input>
      </el-form-item> 
    </el-col>
  </el-row> 

        
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
import { getOneArchive, editArchive } from "@/api/archive"
// import WangEditor from '@/components/WangEditor.vue'
import { getInstitutions, getOneInstitution, PageAllInstitution, SelectInstitution } from '@/api/institution'
import { getPaper, PageAllPaper, SelectPaper } from '@/api/paper'
import{getComponentList} from '@/api/coponent'


export default {
  components: {
    // WangEditor
  },
  data() {
    return {
      // rules: {
      //   archiveName: [
      //     { required: true, message: '请输入档案名称', trigger: 'blur' },
      //   ],
      //   archiveContent: [
      //     { required: true, message: '请输入档案介绍', trigger: 'blur' },
      //     // { max: 18, message: '用户名格式错误', trigger: 'blur' }
      //   ],
      //   bianhao: [
      //     { required: true, message: '请输入档案编号', trigger: 'blur' },
      //   ],
      //   paperId: [
      //     { required: true, message: '请选择纸张', trigger: 'blur' },
      //     // { max: 18, message: '用户名格式错误', trigger: 'blur' }
      //   ],
      //   institutionId: [
      //     { required: true, message: '请选择机构', trigger: 'blur' },
      //     // { max: 18, message: '用户名格式错误', trigger: 'blur' }
      //   ],
      //   fileName: [
      //     { required: true, message: '请上传图片', trigger: 'blur' },
      //     // { max: 18, message: '用户名格式错误', trigger: 'blur' }
      //   ],
      //   fiber: [
      //     { required: true, message: '请输入纤维老化情况', trigger: 'blur' },
      //     // { max: 18, message: '用户名格式错误', trigger: 'blur' }
      //   ],
      //   size: [
      //     { required: true, message: '请输入尺寸', trigger: 'blur' },
      //   ],
      //   form: [
      //     { required: true, message: '请输入形制', trigger: 'blur' },
      //     // { max: 18, message: '用户名格式错误', trigger: 'blur' }
      //   ],
      //   thickness: [
      //     { required: true, message: '请输入厚度', trigger: 'blur' },
      //   ],
      //   whiteness: [
      //     { required: true, message: '请输入白度', trigger: 'blur' },
      //   ],
      //   beatDeg: [
      //     { required: true, message: '请输入打浆度', trigger: 'blur' },
      //     // { max: 18, message: '用户名格式错误', trigger: 'blur' }
      //   ],
      //   lianwen: [
      //     { required: true, message: '请输入帘纹', trigger: 'blur' },
      //   ],
      //   color: [
      //     { required: true, message: '请输入染色材料', trigger: 'blur' },
      //   ],
      //   composition: [
      //     { required: true, message: '请输入成分', trigger: 'blur' },
      //     // { max: 18, message: '用户名格式错误', trigger: 'blur' }
      //   ],
      //   author: [
      //     { required: true, message: '请输入责任者', trigger: 'blur' },
      //   ],
      //   firstTime: [
      //     { required: true, message: '请输入出现时间', trigger: 'blur' },
      //   ],
      //   recordTime: [
      //     { required: true, message: '请输入记录时间', trigger: 'blur' },
      //   ],
      // },
      activeName1: '1',
      activeName: '1',
      users: [],
      paperId: '',
      options: [],
      value: [],
      // typelist1: [{
      //   paperType: 0,
      //   typeName: "手工纸",
      // }, {
      //   paperType: 1,
      //   typeName: "机械纸",
      // }, {
      //   paperType: 2,
      //   typeName: "其他",
      // }], // 资源类型列表

      typelist: [{
        componentType: 0,
        typeName: "填料成分",
      }, {
        componentType: 1,
        typeName: "染色成分",
      }, {
        componentType: 2, 
        typeName: "施胶成分",
      },{
        componentType: 3, 
        typeName: "涂布成分",
      }], // 资源类型列表
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
      institutionId: '',
      paperList: [],
      oneArchive:{
        archiveItem:{
          // archiveName
        },
        archivePaperItem:{},
        recordingItem:{},
        archiveComponentItem:{},
        
        // archiveName: "",
        paperDes: '',
        paperType: '',
        paperId: '',
        site: '',
        location: '',
        process: '',
        picture: '',
        firstTime: '',
      },
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
      typeName: {
        '0': "手工纸",
        '1': "机械纸",
        '2': "半手工半机械纸",
      },//将资源类型转换为对应文字
      fileList: [], // 用于存储文件列表
      fileList1: [], // 用于存储文件列表
      headers: { /* 你的请求头信息，如果有的话 */ },
      dialogVisible: false,
      institutions: [],
      papers: [],
      components:[],
      adVisible: false,
      dialogVisible1: false,
      adVisible1: false,
      dialogVisible2: false,
      adVisible2: false,
      formLabelWidth: '120px',
      typelist1: [{
        distribution: 0,
        typeName: "均匀",
      }, {
        distribution: 1,
        typeName: "不均匀",
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
    this.getComponentList()

    this.getCourses();
    this.getPaper();
    this.getOneArchivee(this.archiveId);

  },

  watch: { '$route': 'gettingData' },
  methods: {
    getCourses() {
      getInstitutions().then(response => {
        this.institutions = response.data
        console.log(this.institutions)
      })
    },
    getPaper() {
      getPaper().then(response => {
        this.papers = response.data
        console.log(this.papers)
      })
    },
    getComponentList() {
            getComponentList().then((response) => {
                if (response) {
                    this.components = response.data;
                    console.log(this.components)
                } else {
                    console.log(errorLog)
                }
            })
        },
    filterTag(value, row, column) {
      const property = column["property"];
      return row[property] === value;
    },
    gettingData() {
      var queryArchiveId = JSON.parse(this.$Base64.decode(this.$route.query.info))
      this.archiveId = queryArchiveId;
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

    getOneArchivee(archiveId) {
      getOneArchive(archiveId).then((response) => {
        this.oneArchive = response.data;
        this.fileName = this.oneArchive.pictureLocation;
        console.log(this.oneArchive);
      });
    },
    changeProject() {
      console.log(this.oneArchive); // 打印 oneresource 对象
      if (this.oneArchive.archiveName == "") {
        this.$message({
          type: 'warning',
          message: '纸张名称为空，请填写！'
        })
        return false;
      }
      else {
        this.$refs.oneArchive.validate(valid => {
          if (valid) {
            this.$confirm('您确定要修改该纸张吗?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              // 创建一个 FormData 对象
              const formData = new FormData();
              formData.append('file', this.file);
              // 添加其他表单数据
              formData.append('paperId', this.oneArchive.paperId);
              formData.append('pictureId', this.oneArchive.pictureId);
              formData.append('pictureLocation', this.oneArchive.pictureLocation);
              formData.append('archiveId', this.oneArchive.archiveId);
              formData.append('archiveName', this.oneArchive.archiveName);
              formData.append('author', this.oneArchive.author);
              formData.append('institutionId', this.oneArchive.institutionId);
              formData.append('archiveContent', this.oneArchive.archiveContent);
              formData.append('size', this.oneArchive.size);
              formData.append('thickness', this.oneArchive.thickness);
              formData.append('whiteness', this.oneArchive.whiteness);
              formData.append('composition', this.oneArchive.composition);
              formData.append('form', this.oneArchive.form);
              formData.append('archiveStateType', this.oneArchive.archiveStateType);
              formData.append('beatDeg', this.oneArchive.beatDeg);
              formData.append('fiber', this.oneArchive.fiber);
              formData.append('degFiber', this.oneArchive.degFiber);
              formData.append('bianhao', this.oneArchive.bianhao);
              formData.append('color', this.oneArchive.color);
              formData.append('lianwen', this.oneArchive.lianwen);
              formData.append('firstTime', new Date(this.oneArchive.firstTime));
              formData.append('recordTime', new Date(this.oneArchive.recordTime));
              // formData.append('firstTime', this.onePaper.firstTime);
              // formData.append('composition', this.oneArchive.composition);
              // formData.append('picture', this.onePaper.picture);
              // location.reload();//自动刷新  
              editArchive(formData).then(response => {
                if (response.message == "操作成功") {
                  this.$router.push('/management/forumManagement')
                  this.$message({
                    type: 'success',
                    message: "修改成功！"
                  })
                  this.dialogVisible = false
                  // this.getProjects()
                } else if (response.code == 500) {
                  this.$message({
                    type: 'error',
                    message: '档案名重复！'
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
    back() {
      this.$router.push('/management/forumManagement')
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