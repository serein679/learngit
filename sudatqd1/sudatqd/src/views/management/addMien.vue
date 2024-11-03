<template>
  <div> <!--添加专业对话框-->
    <div style="padding: 10px;">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/management/mienManagement' }">机构详情管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/management/addMien' }">新增机构</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div slot="title">
      <!-- <span style="font-family: '楷体'; font-size: 20px;">新增纸张</span> -->
    </div>
    <span style="font-family: '楷体'; font-size: 26px;">新增机构</span>
    <el-tabs class="el-tab-div" style="font-family: 楷体;font-size: 26px;">
      <el-form :model="addInstitution" ref="addInstitution" style="font-size: 32px !important" :rules="rules">
        <el-form-item label="机构名称" :label-width="formLabelWidth" prop="institutionName">
          <el-input v-model="addInstitution.institutionName"></el-input>
        </el-form-item>
        <el-form-item label="机构简介" :label-width="formLabelWidth" prop="institutionDescription">
          <el-input v-model="addInstitution.institutionDescription" type="textarea" :rows="5"
            prop="institutionDescription"></el-input>
        </el-form-item>
        <el-form-item label="机构图片" :label-width="formLabelWidth" style="width: 630px;">
          <el-input v-model="fileName" :readonly='true' placeholder="文件名" style="width:388px;" clearable>
          </el-input>
          <el-upload class="upload-demo" ref="upload" action="doUpload" :limit="1" :file-list="fileList"
            style="float:right" :before-upload="beforeFileUpload">
            <el-button slot="trigger" type="primary">上传文件<i class="el-icon-upload el-icon--right"></i></el-button>
            <div slot="tip" class="el-upload__tip">只能上传1M以内的文件</div>
          </el-upload>
          <img :src="addInstitution.institutionPicture" width="40%" style="float: center;margin-top: 10px;">
        </el-form-item>
        <el-form-item label="相关纸张" :label-width="formLabelWidth">
          <template slot-scope="props">
            <el-select v-model="addInstitution.paperlist" :props="{ key: 'paperId', label: 'paperName' }" multiple
              placeholder="请选择" style="width:100%" @click.native="getPapers" filterable>
              <el-option v-for="item in papers" :key="item.paperId" :label="item.paperName" :value="item.paperId">
              </el-option>
            </el-select>
          </template>
        </el-form-item>
        <el-form-item label="相关档案" :label-width="formLabelWidth">
          <template slot-scope="props">
            <el-select v-model="addInstitution.archivelist" :props="{ key: 'archiveId', label: 'archiveName' }" multiple
              placeholder="请选择" style="width:100%" @click.native="getArchives" filterable>
              <el-option v-for="item in archives" :key="item.archiveId" :label="item.archiveName" :value="item.archiveId">
              </el-option>
            </el-select>
          </template>
        </el-form-item>
        <el-form-item label="机构地点" :label-width="formLabelWidth" prop="institutionLocation" style="text-align: left;">
          <!-- <el-input v-model="addInstitution.institutionLocation"></el-input> -->
          <el-cascader size="large" :options="options1" v-model="selectedOptions" @change="handleChange"
            style="width: 500px;">
          </el-cascader>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="middle" @click="addbaidu" style="margin-left: -60%;"
        >添加详细地址信息</el-button
      >
      <el-dialog title="地图定位" :visible.sync="dialogVisible" @close="close" width="70%" style="margin-left: 10%;">
        <el-form ref="addInstitution" :model="addInstitution" label-width="100px">
          <el-form-item label="归属地址：" prop="building">
            <el-autocomplete
              style="width: 100%"
              v-model="form.building"
              :fetch-suggestions="querySearchAsync"
              :trigger-on-focus="false"
              placeholder="请先搜索地址"
              clearable
              @select="handleSelect"
            >
              <template slot-scope="{ item }">
                <i class="el-icon-search fl mgr10" />
                <div style="overflow: hidden">
                  <div class="title">{{ item.title }}</div>
                  <span class="address ellipsis">{{ item.address }}</span>
                </div>
              </template>
            </el-autocomplete>
          </el-form-item>
          <el-form-item label="地图定位：">
            <div id="map-container" style="width: 100%; height: 300px" />
          </el-form-item>
          <el-form-item label="经度：">
            <el-input v-model="addInstitution.longitude" ></el-input>
          </el-form-item>
          <el-form-item label="维度：">
            <el-input v-model="addInstitution.latitude" ></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false"
            >确 定</el-button
          >
        </span>
      </el-dialog>
        </el-form-item>
        <el-form-item label="机构出现时间" :label-width="formLabelWidth" style="margin-left: 1px;width: 300px;"
          prop="institutionDate" @change="chooseTimeRange">
          <!-- <el-input v-model="oneproject.projectStartTime"></el-input> -->
          <el-date-picker v-model="addInstitution.institutionDate" type="date" placeholder="选择日期" align="right"
            :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div class="dialog-footer">
        <el-button type="primary" @click="addinstitution()">添 加</el-button>
        <el-button type="primary" @click="back">取 消</el-button>
      </div>
    </el-tabs>
  </div>
</template>
<script>
import './filters.js'
import Pagination from "@/components/Pagination";
import { regionData, CodeToText, TextToCode } from 'element-china-area-data'

import errorLog from '@/store/modules/errorLog';
// import WangEditor from '@/components/WangEditor.vue'
import { addPaper, getPaper } from "@/api/paper"
import { getOneInstitution, getOneIn, getOnePa, addInstitution } from '@/api/institution'
import { getArchives } from '@/api/archive';
import loadBMap from '@/utils/loadBMap.js'

export default {
  components: {
    // WangEditor
  },
  data() {
    return {
      rules: {
        institutionName: [
          { required: true, message: '请输入机构名称', trigger: 'blur' },
        ],
        institutionDescription: [
          { required: true, message: '请输入机构简介', trigger: 'blur' },
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
      map: null,
        // Marker实例
        mk: null,
        // 定时器
        initMapeq: null,
        // 表单开关
        dialogVisible: false,
        // 表单
        form: {
            longitude:'',
            latitude:''

        },
      options1: regionData,
      selectedOptions: [],
      activeName1: '1',
      activeName: '1',
      users: [],
      options: [],
      value: [],
      paperlist: [],

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
      archives: [],
      papers: [],
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
      addInstitution:{
        longitude:'',
        latitude:'',
      },
      firstTime: '',
     
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
    this.getPapers()
    this.getArchives();
  },
  mounted() {
      // 页面加载完之后，加载百度地图
      // 加载引入BMap
      loadBMap('RiyTMxuMEPkKAoMHW47HD3XmG0pEhXNr')
    },
  methods: {
    close(){
       this.form = {}
      },
      // 添加开关
      addbaidu() {
        // 打开表单
        this.dialogVisible = true
        // 加载地图
        this.getbaidu()
      },
      // 加载地图方法
      getbaidu() {
        this.showDialog = true
        this.initMapeq = setInterval(() => {
          if (this.initMap()) {
            clearInterval(this.initMapeq)
          }
        })
      },
      // 百度地图封装方法
      // 初始化地图
      initMap() {
        try {
          var that = this
          // 1、挂载地图
          // 创建地图实例
          this.map = new BMap.Map('map-container')
          // 设置中心经纬度  这里我们使用BMapGL命名空间下的Point类来创建一个坐标点。Point类描述了一个地理坐标点，其中120.872845表示经度，32.021089表示纬度。（为南通濠河风景区坐标） 作者：星银色飞行船
          var point = new BMap.Point(
            this.addInstitution.longitude || 116.41338729034514,
            this.addInstitution.latitude || 39.910923647957596
          )
          // 3.在创建地图实例后，我们需要对其进行初始化，BMapGL.Map.centerAndZoom()方法要求设置中心点坐标和地图级别。 地图必须经过初始化才可以执行其他操作
          this.map.centerAndZoom(point, 14)
          // 滚轮缩放
          this.map.enableScrollWheelZoom()
          // 3、设置图像标注并绑定拖拽标注结束后事件
          this.mk = new BMap.Marker(point, { enableDragging: true })
          this.map.addOverlay(this.mk)
        } catch (err) {
          return false
        }
        // 4、添加（右上角）平移缩放控件
        this.map.addControl(
          new BMap.NavigationControl({
            anchor: BMAP_ANCHOR_TOP_RIGHT,
            type: BMAP_NAVIGATION_CONTROL_SMALL
          })
        )
        // 7、绑定点击地图任意点事件
        this.map.addEventListener('click', function (e) {
          console.log('点击了', e)
          that.getAddrByPoint(e.point)
        })
        return true
      },
      // 2、逆地址解析函数 根据坐标点获取详细地址  point   百度地图坐标点，必传
      getAddrByPoint(point) {
        var that = this
        var geco = new BMap.Geocoder()
        geco.getLocation(point, function (res) {
          // console.log(res);
          that.mk.setPosition(point)
          that.map.panTo(point)
          that.form.building = res.address
          that.addInstitution.longitude = res.point.lng
          that.addInstitution.latitude = res.point.lat
          console.log(that.addInstitution.longitude)
          console.log(that.addInstitution.latitude)

        })
      },
      // 8-1、地址搜索
      querySearchAsync(str, cb) {
        // 根据状态码
        var options = {
          onSearchComplete: function (res) {
            var s = []
            if (local.getStatus() == BMAP_STATUS_SUCCESS) {
              for (var i = 0; i < res.getCurrentNumPois(); i++) {
                s.push(res.getPoi(i))
              }
              cb(s)
            } else {
              cb(s)
            }
          }
        }
        var local = new BMap.LocalSearch(this.map, options)
        local.search(str)
      },
      // 8-2、选择地址
      handleSelect(item) {
        this.form.building = item.address + item.title
        console.log(item);
        this.addInstitution.longitude = item.point.lng
        this.addInstitution.latitude = item.point.lat
        console.log('lng', item.point.lng)
        console.log('lat', item.point.lat)
        this.map.clearOverlays()
        this.mk = new BMap.Marker(item.point)
        this.map.addOverlay(this.mk)
        this.map.panTo(item.point)
      },
 
    handleChange(value) {
      console.log(value)
      console.log(value[0])
      console.log(value[1])
      console.log(value[2])

      // console.log(codeArray)

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
      this.addInstitution.institutionLocation = area
      console.log(this.addInstitution.institutionLocation)
      return area;
    },
    chooseTimeRange(firstTime) {
      this.firstTime = firstTime;
      console.log(this.firstTime)
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
    back() {
      this.$router.push('/management/mienManagement')
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
    addinstitution() {
      console.log(this.addInstitution); // 打印 addresource 对象
      if (this.addInstitution.paperName == "") {
        this.$message({
          type: 'warning',
          message: '机构名称为空，请填写！'
        })
        return false;
      }
      else {
        this.$refs.addInstitution.validate(valid => {
          if (valid) {
            this.$confirm('您确定要添加该机构吗?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              const formData = new FormData();
              formData.append('file', this.file);
              // 添加其他表单数据
              // formData.append('institutionId', this.addInstitution.institutionId);
              formData.append('institutionName', this.addInstitution.institutionName);
              formData.append('institutionLocation', this.addInstitution.institutionLocation);
              formData.append('longitude', this.addInstitution.longitude);
              formData.append('latitude', this.addInstitution.latitude);
              formData.append('paperIds', this.addInstitution.paperlist);
              formData.append('archiveIds', this.addInstitution.archivelist);
              formData.append('institutionDate', this.addInstitution.institutionDate);
              formData.append('institutionDescription', this.addInstitution.institutionDescription);
              formData.append('institutionPicture', this.addInstitution.institutionPicture);
              addInstitution(formData).then(response => {
                if (response.message == "操作成功") {
                  this.$router.push('/management/mienManagement')
                  this.$message({
                    type: 'success',
                    message: "添加成功！"
                  })
                  // location.reload();//自动刷新
                  this.adVisible = false
                  this.$router.push('/management/mienManagement')
                  // this.getProjects(order = 0)
                } else if (response.code == 500) {
                  this.$message({
                    type: 'error',
                    message: '机构名重复！'
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
    getPapers() {
      getPaper().then(response => {
        this.papers = response.data
        console.log(this.papers)
      })
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