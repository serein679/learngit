<template>
  <div> <!--添加专业对话框-->
    <div style="padding: 10px;">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/management/forumManagement' }">档案详情管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/management/addTeacher' }">新增档案</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div slot="title">
      <!-- <span style="font-family: '楷体'; font-size: 20px;">新增纸张</span> -->
    </div>
    <span style="font-family: 'STKaiti'; font-size: 26px;">新增档案</span>
    <el-tabs class="el-tab-div" style="font-family: STKaiti;font-size: 26px;">
      <el-form :model="addArchive" ref="addArchive" style="font-size: 32px !important" >
        <el-form-item  style="font-size: 32px !important">
          <div style="font-size: 24px !important;text-align: left;" >档案的基本信息:</div> 
        </el-form-item> 
        <el-row >
            <el-col :span="8">
              <el-form-item label="档案名称" :label-width="formLabelWidth" prop="archiveName">
                <el-input v-model="addArchive.archiveItem.archiveName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="档案编号" :label-width="formLabelWidth" prop="bianhao">
                <el-input v-model="addArchive.recordingItem.bianHao"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="档案责任者" :label-width="formLabelWidth"  >
                <el-input v-model=" addArchive.recordingItem.author"  prop="author"></el-input>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row >
          <el-col :span="8">
            <el-form-item label="语种" :label-width="formLabelWidth"  >
              <el-input v-model="addArchive.recordingItem.language"   ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="分类号" :label-width="formLabelWidth"  >
              <el-input v-model=" addArchive.recordingItem.classification"   ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="档号" :label-width="formLabelWidth"  >
              <el-input v-model="addArchive.recordingItem.reference"   ></el-input>
            </el-form-item>
          </el-col>
      </el-row>
      <el-row >
        <el-col :span="10">
          <el-form-item label="档案首页图片" :label-width="formLabelWidth" >
            <el-input v-model="fileName" :readonly='true' placeholder="文件名" style="width:388px;" clearable>
            </el-input>
            <el-upload class="upload-demo" ref="upload" action="doUpload" :limit="1" :file-list="fileList"
              style="float:right" :before-upload="beforeFileUpload">
              <el-button slot="trigger" type="primary">上传文件<i class="el-icon-upload el-icon--right"></i></el-button>
              <div slot="tip" class="el-upload__tip">只能上传1M以内的文件</div>
            </el-upload>
            <img :src="addArchive.pictureLocation" width="40%" style="float: center;margin-top: 10px;">
          </el-form-item>
        </el-col>
        
        <el-col :span="6">
          <el-form-item label="档案出现时间" :label-width="formLabelWidth"  
          prop="addArchive.time">
          <el-input v-model="addArchive.recordingItem.time"   ></el-input>
        </el-form-item> 
        </el-col>
        <el-col :span="8">
          <el-form-item label="档案数量" :label-width="formLabelWidth"  
          prop="addArchive.archivePaperItem.shuLiang">
          <el-input v-model="addArchive.archivePaperItem.shuLiang"   ></el-input>
        </el-form-item> 
        </el-col>
    </el-row>
    <el-form-item label="档案简介" :label-width="formLabelWidth"  >
      <el-input v-model="addArchive.archiveItem.archiveContent" type="textarea" :rows="5" ></el-input>
    </el-form-item>
    <el-row >
      <el-col :span="6">
        <el-form-item label="信息采集时间" :label-width="formLabelWidth" style="margin-left: 1px;width: 300px;"
          prop="addArchive.archivePaperItem.creationTime">
          <el-date-picker v-model="addArchive.archivePaperItem.creationTime" type="date" placeholder="选择日期" align="right"
            :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item> 
      </el-col>
      <el-col :span="6">
        <el-form-item label="使用纸张" :label-width="formLabelWidth">
          <template slot-scope="props">
            <el-select v-model="addArchive.archivePaperItem.paperId" :props="paperId" placeholder="请选择" style="width:100%"
              @click.native="getPaper" filterable>
              <el-option v-for="item in papers" :key="item.paperId" :label="item.paperName" :value="item.paperId">
              </el-option>
            </el-select>
          </template>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="所属机构" :label-width="formLabelWidth">
          <template slot-scope="props">
            <el-select v-model="addArchive.archiveItem.institutionId" :props="institutionId" placeholder="请选择" style="width:100%"
              @click.native="getCourses" filterable>
              <el-option v-for="item in institutions" :key="item.institutionId" :label="item.institutionName"
                :value="item.institutionId">
              </el-option>
            </el-select>
          </template>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="档案类型" :label-width="formLabelWidth">
          <template slot-scope="props">
            <el-select v-model="addArchive.archiveItem.archiveType"  placeholder="请选择" style="width:100%;font-family: 'STKaiti'; " filterable>
              <el-option v-for="item in typelist11" :key="item.archiveType" :label="item.typeName" :value="item.archiveType"
              style="font-family: 'STKaiti'; ">
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
          prop="addArchive.archivePaperItem.size ">
          <el-input v-model="addArchive.archivePaperItem.size" ></el-input>
        </el-form-item> 
      </el-col>
      <el-col :span="8">
        <el-form-item label="档案纸张ph值" :label-width="formLabelWidth"  
          prop="addArchive.archivePaperItem.ph ">
          <el-input v-model="addArchive.archivePaperItem.ph" ></el-input>
        </el-form-item> 
      </el-col>
      <!-- <el-col :span="8">
        <el-form-item label="档案纸张尺寸" :label-width="formLabelWidth"  
          prop="addArchive.archivePaperItem.size ">
          <el-input v-model="addArchive.archivePaperItem.size" ></el-input>
        </el-form-item> 
      </el-col> -->
  </el-row>
  <el-row >
    <el-col :span="8">
      <el-form-item label="档案纸张厚度" :label-width="formLabelWidth"  
        prop="addArchive.archivePaperItem.thickness ">
        <el-input v-model="addArchive.archivePaperItem.thickness" ></el-input>
      </el-form-item> 
    </el-col>
    <el-col :span="8">
      <el-form-item label="档案纸张白度" :label-width="formLabelWidth"  
        prop="addArchive.archivePaperItem.whiteness ">
        <el-input v-model="addArchive.archivePaperItem.whiteness" ></el-input>
      </el-form-item> 
    </el-col>
    <el-col :span="8">
      <el-form-item label="档案纸张色度" :label-width="formLabelWidth"  
        prop="addArchive.archivePaperItem.color ">
        <el-input v-model="addArchive.archivePaperItem.color" ></el-input>
      </el-form-item> 
    </el-col>
  </el-row>
  <el-row >
    <el-col :span="8">
      <el-form-item label="纸张横纹数量" :label-width="formLabelWidth"  
        prop="addArchive.archivePaperItem.band ">
        <el-input v-model="addArchive.archivePaperItem.band" ></el-input>
      </el-form-item> 
    </el-col>
    <el-col :span="8">
      <el-form-item label="纸张纵纹间距" :label-width="formLabelWidth"  
        prop="addArchive.archivePaperItem.zong">
        <el-input v-model="addArchive.archivePaperItem.zong" ></el-input>
      </el-form-item> 
    </el-col>
    <el-col :span="8">
      <el-form-item label="档案用纸层数" :label-width="formLabelWidth"  
        prop="addArchive.archivePaperItem.numbers ">
        <el-input v-model="addArchive.archivePaperItem.numbers" ></el-input>
      </el-form-item> 
    </el-col>
  </el-row>
  <el-row >
    <el-col :span="8">
      <el-form-item label="用纸纤维分布" :label-width="formLabelWidth"  
        prop="addArchive.archivePaperItem.distribution "  placeholder="请选择" style="width:100%;font-family: 'STKaiti'; " filterable>
        <el-select v-model="addArchive.archivePaperItem.distribution" placeholder="请选择" style="width:100%;font-family: 'STKaiti'; " filterable>
          <el-option v-for="item in typelist1" :key="item.distribution" :label="item.typeName" :value="item.distribution"
            style="font-family: 'STKaiti'; ">
          </el-option>
        </el-select>
      </el-form-item> 
    </el-col>
    <el-col :span="8">
      <el-form-item label="纸张帘纹情况" :label-width="formLabelWidth"  
        prop="addArchive.archivePaperItem.lianWen">
        <el-input v-model="addArchive.archivePaperItem.lianWen" ></el-input>
      </el-form-item> 
    </el-col>
    <el-col :span="8">
      <el-form-item label="档案纸张含水量" :label-width="formLabelWidth"  
        prop="oaddrchive.archivePaperItem.beatDeg">
        <el-input v-model="addArchive.archivePaperItem.beatDeg" ></el-input>
      </el-form-item> 
    </el-col>
  </el-row>
  <el-row  >
      <el-col :span="6">
        <el-form-item label="纸张破损程度" :label-width="formLabelWidth"  >
          <el-input v-model="addArchive.archivePaperItem.degFiber"  ></el-input>
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
          <el-select v-model="addArchive.archiveComponentItem.componentId " :props="{ key: 'componentId', label: 'componentName' }" multiple
            placeholder="请选择" @click.native="getComponentList" style="width:100%"  >
            <el-option  v-for="item in components" :key="item.componentId" :label="item.componentName" :value="item.componentId">
            </el-option>
          </el-select>
        </template>
      </el-form-item> 
    </el-col>
    <el-col :span="8">
      <el-form-item label="纸张成分类型" :label-width="formLabelWidth"  
        prop="addArchive.archiveComponentItem.componentType"  placeholder="请选择" style="width:100%;font-family: 'STKaiti'; " filterable>
        <el-select v-model="addArchive.archiveComponentItem.componentType" placeholder="请选择" style="width:100%;font-family: 'STKaiti'; " filterable>
          <el-option v-for="item in typelist" :key="item.componentType" :label="item.typeName" :value="item.componentType"
            style="font-family: 'STKaiti'; ">
          </el-option>
        </el-select>
      </el-form-item> 
    </el-col>
    <el-col :span="8">
      <el-form-item label="纸张成分描述" :label-width="formLabelWidth"  
        prop="addArchive.archiveComponentItem.description">
        <el-input v-model="addArchive.archiveComponentItem.description" ></el-input>
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
import './filters.js'
import Pagination from "@/components/Pagination";
import errorLog from '@/store/modules/errorLog';
// import WangEditor from '@/components/WangEditor.vue'
import { getInstitutions, getOneInstitution, PageAllInstitution, SelectInstitution } from '@/api/institution'
import { getPaper, PageAllPaper, SelectPaper } from '@/api/paper'
import { addArchive } from "@/api/archive"
import{getComponentList} from '@/api/coponent'


export default {
  components: {
    // WangEditor
  },
  data() {
    return {
      activeName1: '1',
      activeName: '1',
      users: [],
      options: [],
      datefilter: new Date(1912, 0, 1, 0, 0),
      datefilter1: new Date(1949, 9, 1, 0, 0),
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
      addArchive: [],
     
      fileName: "",//上传的文件名称
      fileName1: "",//上传的文件名称
      file: null,
      dialogVisible5: false,
      adVisible5: false,
      total4: '',
      institutionId: '',
      paperId: '',
      archiveItem:{

      },
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
      typelist11: [{
        archiveType: 0,
        typeName: "护照",
      }, {
        archiveType: 1,
        typeName: "地契",
      }, {
        archiveType: 2,
        typeName: "票据",
      },{
        archiveType: 3,
        typeName: "其他",
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
      firstTime: '',
      addArchive: {
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
        // projectContent: '',
        // status: '',
      },// 新增表单
      institutions: [],
      components:[],
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
        typeName: "涂布成分",}],
      papers: [],
      typelist1: [{
        distribution: 0,
        typeName: "均匀",
      }, {
        distribution: 1,
        typeName: "不均匀",
      }], // 资源类型列表
      typeName: {
        '0': "手工纸",
        '1': "机械纸",
        '2': "半手工半机械纸",
      },//将资源类型转换为对应文字
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
    this.getCourses();
    this.getPaper();
    this.getComponentList();

    // this.getQuery();
    // this.getProjects();
    // this.getTeacherIntro();
    // this.getUsers();

  },
  computed: {
    // listSet() {
    //   return this.projectList.map(item => {
    //     return { value: `${item.projectId}`, label: `${item.projectName}` };
    //   });
    // },
  },
  methods: {

    // getTeacherIntro() {
    //   TeacherIntro().then(response => {
    //     this.teachers = response.data
    //   })
    // },

    filterTag(value, row, column) {
      const property = column["property"];
      return row[property] === value;
    },
    _filterChange() {
      this.totalSize = this.$refs.table.projectList.length;
      console.log(this.totalSize);
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
    sendWangEditor(val) {
      this.oneProjects.hyperlinks = val;//去掉p标签
    },
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
      this.$router.push('/management/forumManagement')
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
        // 保存文件对象
        this.file = file;
        this.fileName = file.name;
        return false; // 返回false不会自动上传
      }

    },
    // 编辑按钮点击事件
    addarchive() {
      console.log(this.addArchive); // 打印 addresource 对象
      if (this.addArchive.archiveName == "") {
        this.$message({
          type: 'warning',
          message: '纸张名称为空，请填写！'
        })
        return false;
      }
      else {
        this.$refs.addArchive.validate(valid => {
          if (valid) {
            this.$confirm('您确定要添加该档案吗?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              // const formData = new FormData();
              var archiveName=this.addArchive.archiveItem.archiveName
              var institutionId=this.addArchive.archiveItem.institutionId
              var archiveType=this.addArchive.archiveItem.archiveType
              var archiveContent=this.addArchive.archiveItem.archiveContent
              var pictureLocation = this.pictureLocation
              var fileParam = this.imgs
              var paperId=this.addArchive.archivePaperItem.paperId
              var process=this.addPaper.process
              var technology=this.addPaper.technology
              var author = this.addArchive.recordingItem.author
              var size=this.addArchive.archivePaperItem.size
              var thickness=this.addPaper.archivePaperItem.thickness
              var whiteness=this.addPaper.archivePaperItem.whiteness
              var beatDeg = this.addArchive.archivePaperItem.beatDeg
              var bianhao=this.addPaper.recordingItem.bianhao 
              var language=this.addPaper.recordingItem.language
              var classification=this.addPaper.recordingItem.classification
              var reference=this.addPaper.recordingItem.reference
              var time = this.addArchive.recordingItem.time
              var shuLiang = this.addArchive.archivePaperItem.shuLiang
              var creationTime = this.addArchive.archivePaperItem.creationTime
              var ph = this.addArchive.archivePaperItem.ph
              var color = this.addArchive.archivePaperItem.color
              var band = this.addArchive.archivePaperItem.band
              var numbers = this.addArchive.archivePaperItem.numbers
              var distribution = this.addArchive.archivePaperItem.distribution
              var zong = this.addArchive.archivePaperItem.zong
              var lianwen = this.addArchive.archivePaperItem.degFiber
              var degFiber = this.addArchive.archivePaperItem.degFiber
              var componentType =this.addArchive.archiveComponentItem.componentType
              var componentId = this.componentId
              var description1 = this.addArchive.archiveComponentItem.description
              // formData.append('paperId', this.addArchive.paperId);
              // formData.append('author', this.addArchive.author);
              // formData.append('institutionId', this.addArchive.institutionId);
              // formData.append('archiveContent', this.addArchive.archiveContent);
              // formData.append('size', this.addArchive.size);
              // formData.append('thickness', this.addArchive.thickness);
              // formData.append('whiteness', this.addArchive.whiteness);
              formData.append('composition', this.addArchive.composition);
              formData.append('form', this.addArchive.form);
              formData.append('archiveStateType', this.addArchive.archiveStateType);
              formData.append('beatDeg', this.addArchive.beatDeg);
              formData.append('fiber', this.addArchive.fiber);
              formData.append('degFiber', this.addArchive.degFiber);
              formData.append('bianhao', this.addArchive.bianhao);
              formData.append('color', this.addArchive.color);
              formData.append('lianwen', this.addArchive.lianwen);
              formData.append('firstTime', new Date(this.addArchive.firstTime));
              formData.append('recordTime', new Date(this.addArchive.recordTime));
              // formData.append('firstTime', this.onePaper.firstTime);
              // formData.append('composition', this.addArchive.composition);
              addArchive(formData).then(response => {
                if (response.message == "操作成功") {
                  this.$router.push('/management/forumManagement')
                  this.$message({
                    type: 'success',
                    message: "添加成功！"
                  })
                  // location.reload();//自动刷新
                  this.adVisible = false
                  this.$router.push('/management/forumManagement')

                  // this.getProjects(order = 0)
                } else if (response.message == "暂无权限") {
                  this.$message({
                    type: 'error',
                    message: '暂无编辑权限！'
                  })
                  this.adVisible = false
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