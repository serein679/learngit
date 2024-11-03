<template>
  <!-- <div class="box1" :style="ratioStyle"> -->
  <div style="margin-top: 8px;background-color: antiquewhite;">
    <div style="margin:0px;top:140px ;background-color: antiquewhite;">
      <el-tabs v-model="activeName" @tab-click="handleClick"
        style=" font-family:STKaiti;font-size: 26px;top: 30px;margin: 0%;">
        <el-tab-pane label="纸样库" name="first" style="margin: 0%;">
          <div style="margin: 0%;padding: 0%;background-color: antiquewhite;height: auto;">
            <el-row>
              <el-col :span="23" style="bottom: 0px;text-align: right;">
                <span class="sousuo">
                  <input type="text" v-model="value" style="width: 250px;font-size: 24px;vertical-align: middle">
                  <el-button type="large" @click="select()"
      style="width: 70px;color: blanchedalmond;background-color: rgb(197, 160, 92);border-color:rgb(165, 122, 42) ;vertical-align: middle">搜索</el-button>
                </span>
                <div style="margin-top: 2%;font-size: 24px;">
                  <el-switch
                    v-model="valuee"
                    active-text="卡片样式"
                    inactive-text="列表样式"
                    >
                  </el-switch>
                </div>
              </el-col>
            </el-row>
            <el-row v-if=" valuee==true ">
              <el-col :span="6" v-for="item in paperList">
                <el-card :body-style="{ padding: '22px', }" shadow="hover" class="box-card">
                  <img :src="item.pictureLocation" class="image" @click="selectt(item.paperId)" >
                  <div style="padding: 10px;">
                    <span>{{ item.paperName | filterAmount(8) }}</span>
                    <div class="bottom clearfix">
                      <span style="font-size: 18px;">诞生时间：{{ new
        Date(item.creationTime).toLocaleDateString('zh').replaceAll('/', '-') }} </span>
                      <el-button type="text" class="button" @click="selectt(item.paperId)">>>></el-button>
                    </div>
                  </div>
                </el-card>
              </el-col>
            </el-row>
            <el-row v-if=" valuee==false ">
              <el-table
      :data="paperList"
      :row-style="{ height: '70px','background-color': 'antiquewhite','margin-left':'5px'}" :header-cell-style="{color:'black','background-color': 'antiquewhite','font-family':'32px'}"
      style="width:90%; margin: auto; margin-top:10px;font-family: STKaiti;font-size: 24px;text-align: center;background-color: antiquewhite;margin-left: 8%;margin-bottom: 4%;">
      <el-table-column
        prop="creationTime"
        label="日期"
        width="380">
        <template slot-scope="scope">
        {{ new
        Date(scope.row.creationTime).toLocaleDateString('zh').replaceAll('/',
          '-') }}
      </template>
      </el-table-column>
      
      <el-table-column
        prop="paperName"
        label="纸张名称"
        width="380">
        <template slot-scope="scope">
          <div style="font-size: 22px;cursor: pointer;">
              <el-link  @click="selectt(scope.row.paperId)" >
                <div style="font-size: 22px;">
                  {{ scope.row.paperName| filterAmount(25) }}
                </div>
              </el-link>
            </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="location"
        label="纸张产地">
      </el-table-column>
      <el-table-column
        prop="paperType"
        label="纸张类型">
        <template slot-scope="props">
            {{ typeName[props.row.paperType] }}
          </template>
      </el-table-column>
      
    </el-table>
            </el-row>
            <el-row style="height: 90px;">
              <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="currentPage" :page-sizes="[5, 10, 20]" :page-size="20"
                layout="total, sizes, prev, pager, next, jumper" :total="total">
              </el-pagination>
            </el-row>
          </div>
        </el-tab-pane>
        <el-tab-pane label="档案库" name="third">
          <div style="margin: 0%;padding: 0%;background-color: antiquewhite;height: auto;">
            <el-row>
              <el-col :span="11" class="filter" style="margin-left: 0px;">
                <span>档案年份：
                  <el-date-picker v-model="datefilter" type="date" :default-value="currentDate">
                  </el-date-picker> --
                  <el-date-picker v-model="datefilter1" type="date" :default-value="currentDate1">
                  </el-date-picker>
                </span>
              </el-col>
              <el-col :span="6" class="filter">
                <el-button type="primary" @click="filter">筛选</el-button>
                <el-button plain @click="resetQueryE">全部</el-button>
                <!-- <span class="el-icon-upload" style="width: 70px;"></span> -->
                <el-button icon="el-icon-upload" @click="addArchive()">上传档案</el-button>
              </el-col>
              <el-col :span="6" style="bottom: 0px;text-align: right;">
                <span class="sousuo">
                  <input type="text" v-model="value1" style="width: 250px;font-size: 24px;vertical-align: middle">
                 <!--
      --><el-button type="large" @click="selectt3()"
      style="width: 70px;color: blanchedalmond;background-color: rgb(197, 160, 92);border-color:rgb(165, 122, 42) ;vertical-align: middle">搜索</el-button>
                </span>
                <div style="margin-top: 7.5%;font-size: 24px;">
                  <el-switch
                    v-model="valuee2"
                    active-text="卡片样式"
                    inactive-text="列表样式"
                    >
                  </el-switch>
                </div>
              </el-col>
            </el-row>
            <el-row v-if=" valuee2==true ">
              <el-col :span="6" v-for="item in archiveLists">
                <el-card :body-style="{ padding: '22px', }" shadow="hover" class="box-card">
                  <img :src="item.pictureLocation" class="image" @click="select2(item.archiveId)">
                  <div style="padding: 10px;">
                    <span style="font-size: 25px;">{{ item.archiveName | filterAmount(10) }}</span>
                    <div class="bottom clearfix">
                      <span style="font-size: 18px;">诞生时间：{{ new
        Date(item.firstTime).toLocaleDateString('zh').replaceAll('/',
          '-') }} </span>
                      <el-button type="text" class="button" @click="select2(item.archiveId)">>>></el-button>
                    </div>
                  </div>
                </el-card>
              </el-col>
            </el-row>
            <el-row v-if=" valuee2==false ">
              <el-table
      :data="archiveLists"
      :row-style="{ height: '70px','background-color': 'antiquewhite','margin-left':'5px'}" :header-cell-style="{color:'black','background-color': 'antiquewhite','font-family':'32px'}"
      style="width:90%; margin: auto; margin-top:10px;font-family: STKaiti;font-size: 24px;text-align: center;background-color: antiquewhite;margin-left: 10%;margin-bottom: 4%;">
      <el-table-column
        prop="firstTime"
        label="日期"
        width="380">
        <template slot-scope="scope">
        {{ new
        Date(scope.row.firstTime).toLocaleDateString('zh').replaceAll('/',
          '-') }}
      </template>
      </el-table-column>
      <el-table-column
        prop="archiveName"
        label="档案名称"
        width="580">
        <template slot-scope="scope">
          <div style="font-size: 22px;cursor: pointer;">
              <el-link  @click="select2(scope.row.archiveId)" >
                <div style="font-size: 22px;">
                  {{ scope.row.archiveName| filterAmount(25) }}
                </div>
              </el-link>
            </div>
        </template>
      </el-table-column>
      <!-- <el-table-column
        prop="location"
        label="纸张产地">
      </el-table-column> -->
      <el-table-column
        prop="archiveType"
        label="档案类型">
        <template slot-scope="props">
            {{ typeName1[props.row.archiveType] }}
          </template>
      </el-table-column>
    </el-table>
            </el-row>
            <el-row style="height: 90px;">
              <el-pagination background @size-change="handleSizeChange2" @current-change="handleCurrentChange2"
                :current-page="currentPage2" :page-sizes="[5, 10, 20]" :page-size="20"
                layout="total, sizes, prev, pager, next, jumper" :total="total2">
              </el-pagination>
            </el-row>
          </div>
        </el-tab-pane>
        <el-tab-pane label="机构库" name="second">
          <div style="margin: 0%;padding: 0%;background-color: antiquewhite;height: auto;">
            <el-row>
              <!-- <el-col :span="24" style="bottom: 0px;text-align: right;">
                <span class="sousuo">
                  <el-select v-model="value2" filterable remote reserve-keyword placeholder="搜索"
                    :remote-method="remoteMethod2" clearable @clear="select1(value2)"
                    style="background-color: rgb(173, 138, 92);width: 250px;">
                    <el-option v-for="item in options2" :key="item.value2" :label="item.label2" :value="item.value2">
                    </el-option>
                  </el-select>
                </span>
                <span>
                  <el-button type="primary" @click="select1(value2)"
                    style="width: 70px;color: blanchedalmond;background-color: rgb(197, 160, 92);border-color:rgb(165, 122, 42) ;">搜索</el-button>
                </span>
              </el-col> -->
              <el-row>
              <el-col :span="23" style="bottom: 0px;text-align: right;">
                <span class="sousuo">
                  <input type="text" v-model="value2" style="width: 250px;font-size: 24px;vertical-align: middle">
                 <!--
      --><el-button type="large" @click="selectt2()"
      style="width: 70px;color: blanchedalmond;background-color: rgb(197, 160, 92);border-color:rgb(165, 122, 42) ;vertical-align: middle">搜索</el-button>
                </span>
                <div style="margin-top: 2%;font-size: 24px;">
                  <el-switch
                    v-model="valuee3"
                    active-text="卡片样式"
                    inactive-text="列表样式"
                    >
                  </el-switch>
                </div>
              </el-col>
            </el-row>
            </el-row>
            <el-row v-if=" valuee3==true ">
              <el-col :span="6" v-for="item in institutionList">
                <el-card :body-style="{ padding: '22px', }" shadow="hover" class="box-card">
                  <img :src="item.pictureLocation" class="image" @click="select1(item.institutionId)">
                  <div style="padding: 10px;">
                    <span>{{ item.institutionName | filterAmount(8) }}</span>
                    <div class="bottom clearfix">
                      <span style="font-size: 16px;">机构地点：{{ item.institutionLocation }} </span>
                      <!-- <time class="time">{{ currentDate }}</time> -->
                      <el-button type="text" class="button" @click="select1(item.institutionId)">>>></el-button>
                    </div>
                  </div>
                </el-card>
              </el-col>
            </el-row>
            <el-row v-if=" valuee3==false ">
              <el-table
      :data="institutionList"
      :row-style="{ height: '70px','background-color': 'antiquewhite','margin-left':'5px'}" :header-cell-style="{color:'black','background-color': 'antiquewhite','font-family':'32px'}"
      style="width:90%; margin: auto; margin-top:10px;font-family: STKaiti;font-size: 24px;text-align: center;background-color: antiquewhite;margin-left: 8%;margin-bottom: 4%;">
      <el-table-column
        prop="institutionDate"
        label="日期"
        width="380">
        <template slot-scope="scope">
        {{ new
        Date(scope.row.institutionDate).toLocaleDateString('zh').replaceAll('/',
          '-') }}
      </template>
      </el-table-column>
      <el-table-column
        prop="institutionName"
        label="档案名称"
        width="580">
        <template slot-scope="scope">
          <div style="font-size: 22px;cursor: pointer;">
              <el-link  @click="select1(scope.row.institutionId)" >
                <div style="font-size: 22px;">
                  {{ scope.row.institutionName| filterAmount(25) }}
                </div>
              </el-link>
            </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="institutionLocation"
        label="机构地址">
      </el-table-column>
      <!-- <el-table-column
        prop="archiveType"
        label="档案类型">
        <template slot-scope="props">
            {{ typeName1[props.row.archiveType] }}
          </template>
      </el-table-column> -->
    </el-table>
            </el-row>
            <el-row style="height: 90px;">
              <el-pagination background @size-change="handleSizeChange1" @current-change="handleCurrentChange1"
                :current-page="currentPage1" :page-sizes="[5, 10, 20]" :page-size="20"
                layout="total, sizes, prev, pager, next, jumper" :total="total1">
              </el-pagination>
            </el-row>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import './filters.js'
import Carousel from '@/components/Carousel.vue'
import moment from 'moment'
import {  PageAllPaper  } from '@/api/paper'
import Pagination from "@/components/Pagination";
import {  PageAllInstitution  } from '@/api/institution'
import {  PageAllArchive  } from '@/api/archive'
import {   getFibers1 } from '@/api/fiber'
import wordcloud from 'vue-wordcloud'

export default {
  name: 'Introduction',
  components: {
    Carousel,
    Pagination,
    wordcloud
  },
  data() {
    return {
      activeName: 'first',
      obj: { userId: parseInt(sessionStorage.getItem('SET_USERID')) },
      datefilter: new Date(1912, 0, 1, 0, 0),
      datefilter1: new Date(1949, 9, 1, 0, 0),
      materials: [],
      typeName: {
        '0': "手工纸",
        '1': "机械纸",
        '2': "半手工半机械纸",
      },//将资源类型转换为对应文字
      typeName1: {
        0: "护照",
        1: "地契",
        2: "票据",
        3: "其他"
      },
      paperList: [],
      archiveLists: [],
      paperList1: [],
      archiveLists1: [],
      currentDate: '',
      currentDate1: '',
      // currentDate: new Date(),
      institutionList: [],
      institutionList1: [],
      total: 0,
      total2: 0,
      materials: [],
      total1: 0,
      total5: 0,
      valuee: true,
      valuee2: true,
      valuee3: true,
      dialogVisible: false,
      adVisible: false,
      formLabelWidth: '120px',
      users: [], // 用户列表
      smid: "",
      value: '',
      value1: '',
      value2: '',
      myColors: ['#392f41', '#629fc9', '#94bedb', '#c9e0ef', '#622a1d', '#BC8F8F', '#6B8E23', '#7B68EE', '#800000', '#8B4513', '#9370D8', '#D3D3D3', '#CD853F', '#8f4b4a', '#AAAAAA', '#3D9970', '#1f77b4', '#F08080', '#F0FFFF', '#FF8C00', '#0074D9', '#111111'],
      value5: '',
      options: [],
      options1: [],
      options2: [],
      options5: [],
      fiberList1: [],
      currentPage: 1,// 默认显示第几页
      // currentPage1: 1,// 默认显示第几页
      pageSizes: [20],// 个数选择器（可修改）
      pageSize: 20,// 默认每页显示的条数（可修改）
      pageSizes2: [20],// 个数选择器（可修改）
      pageSize2: 20,// 默认每页显示的条数（可修改）
      pageSizes1: [20],// 个数选择器（可修改）
      pageSize1: 20,// 默认每页显示的条数（可修改）
      pageSizes5: [20],// 个数选择器（可修改）
      pageSize5: 10,// 默认每页显示的条数（可修改）
      isShow: true,
      currentPage1: 1,// 默认显示第几页
      currentPage5: 1,// 默认显示第几页
      // pageSizes: [ 20],// 个数选择器（可修改）
      pageSize1: 20,// 默认每页显示的条数（可修改）
      currentPage2: 1,// 默认显示第几页
      currentPage5: 1,// 默认显示第几页
      // pageSizes: [ 20],// 个数选择器（可修改）
      pageSize2: 20,// 默认每页显示的条数（可修改）
      letters: ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"],

    }
  },
  mounted() {
        // 页面加载就渲染列表
        this.getOneFi(this.fiberId);
    },

  created() {
    // this.getPaper()
    this.getList()
 
    this.PageAllArchive()
    this.PageAllInstitution()
    this.PageAllFiber()

  },
  computed: {
    listSet() {
      return this.archiveLists1.map(item => {
        return { value1: `${item.archiveId}`, label1: `${item.archiveName}` };
      });
    },
    listSet1() {
      return this.paperList1.map(item => {
        return { value: `${item.paperId}`, label: `${item.paperName}` };
      });
    },
    listSet2() {
      return this.institutionList1.map(item => {
        return { value2: `${item.institutionId}`, label2: `${item.institutionName}` };
      });
    },
    addArchive() {
      let check = sessionStorage.getItem('SET_TOKEN')
      console.log(check)
      if (check === '') {
        alert('请先登录！')
        this.$router.push({
          path: '/login/index',
          // query: {
          //   info: this.$Base64.encode(institutionId)
          // }
        })
        // next({ path: '/login/index' })
        return
        // this.$message({
        //   type: 'error',
        //   message: "请先登录"
        // })
      }
      else {
        this.$router.push('/introduction/add');
      }
      // console.log(1)
    },
    listSet5() {
      return this.fiberList1.map(item => {
        return { value5: `${item.fiberId}`, label5: `${item.fiberType}` };
      });
    },

  },
  methods: {
    verifyLogin() {
      let check = sessionStorage.getItem('SET_TOKEN')
      return check
    },
    wordClickHandler(name, value, vm) {
      console.log('wordClickHandler', name, value, vm);
      if (value !== '') {
        console.log(value)
        this.$router.push({
          path: './selectMa',
          query: {
            info: this.$Base64.encode(value)
          }
        })
      }
    },
    filterTag(value, row, column) {
      const property = column["property"];
      return row[property] === value;
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
      this.PageAllArchive();
    },
    resetQueryE() {
      //重置查询条件
      // this.datefilter = '';
      // this.datefilter1 = '';
      this.datefilter = new Date(1912, 0, 1, 0, 0),
        this.datefilter1 = new Date(1949, 9, 1, 0, 0),
        //分页刷新,回到首页
        this.currentPage = 1;
      this.pageSize = 10;
      //重置筛选条件
      // this.typefilter = '';
      // this.coursefilter = '';
      this.PageAllArchive();
    },
    resetQuery1() {
      //重置查询条件
      this.query = '';
      //分页刷新,回到首页
      this.currentPage = 1;
      this.pageSize = 10;
      //重置筛选条件
      this.typefilter = '';
      this.coursefilter = '';
      this.getList();
    },
    resetQuery2() {
      //重置查询条件
      this.query = '';
      //分页刷新,回到首页
      this.currentPage = 1;
      this.pageSize = 10;
      //重置筛选条件
      this.typefilter = '';
      this.coursefilter = '';
      this.PageAllInstitution();
    },
    handleClick(tab, event) {
      console.log(tab, event);
    },
    reload() {//局部刷新
      this.isShow = false;
      this.$nextTick(() => {
        this.isShow = true;
        // this.$refs.table.bodyWrapper.scrollTop = 0;
      })
    },
    handleClick(tab, event) {
      console.log(tab, event);
    },

 
    handleSizeChange(val) {
      this.pageSize = val
      this.getList();
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      this.currentPage = val
      console.log(`当前页: ${val}`);
      this.getList();
    },
    handleSizeChange1(val) {
      this.pageSize1 = val
      this.PageAllInstitution();
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange1(val) {
      this.currentPage1 = val
      console.log(`当前页: ${val}`);
      this.PageAllInstitution();

    },
    handleSizeChange2(val) {
      this.pageSize2 = val
      this.PageAllArchive();
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange2(val) {
      this.currentPage2 = val
      console.log(`当前页: ${val}`);
      this.PageAllArchive();

    },

    getList() {
      var pageNum = this.currentPage
      var pageSize = this.pageSize
      var orderColumn = "paperId"
      var isAsc = true
      var paperName = ""//排序
      var paperDes = ""//排序
      var paperType = ""//排序
      var location = ""//排序
      var process = ""//排序
      var technology = ""//排序
      var creator = ""//排序
      var state = 0//排序      
      PageAllPaper({ pageSize, pageNum,orderColumn,isAsc,paperName,paperDes,paperType,location,process,technology,creator, state}).then((response) => {
        if (response) {
          this.total = response.data.total;
          this.reload();//局部刷新
          console.log(pageNum + "/" + pageSize + "/" + this.total);
          this.paperList = response.data.list;
        } else {
          console.log(errorLog)
        }
      })
    },
    PageAllInstitution() {
      var pageNum = this.currentPage1
      var pageSize = this.pageSize1
      var isAsc = true
      var institutionName = ""//排序
      var institutionLocation = ""//排序
      var startDate = ""//排序
      var endDate = ""//排序
      var institutionDescription = ""//排序
      var orderColumn = "institutionId"
      var creator = ""//排序
      var state = 0//排序      
      PageAllInstitution({ pageSize, pageNum,creator, state,isAsc,orderColumn,institutionName,institutionLocation, startDate,endDate,institutionDescription}).then((response) => {
        if (response) {
          this.total1 = response.data.total;
          this.reload();//局部刷新
          console.log(pageNum + "/" + pageSize + "/" + this.total);
          this.institutionList = response.data.list;
        } else {
          console.log(errorLog)
        }
      })
    },
    PageAllFiber() {
      getFibers1().then((response) => {
        if (response) {
          this.materials = response.data;
          console.log(this.materials)
        } else {
          console.log(errorLog)
        }
      })
    },
    PageAllArchive() {
      var pageNum = this.currentPage2
      var pageSize = this.pageSize1
      var isAsc = true
      var archiveName = ""//排序
      var institutionName = ""//排序
      var archiveContent = ""//排序
      var orderColumn = "archiveId"
      var creator = ""//排序
      var state = 0//排序 
      var datefilter =this.datefilter
      var datefilter1=this.datefilter1
      PageAllArchive({ pageSize,pageNum,orderColumn,isAsc,creator, state,archiveName,institutionName,archiveContent,datefilter,datefilter1}).then((response) => {
        if (response) {
          this.total2 = response.data.total;
          this.reload();//局部刷新
          console.log(pageNum + "/" + pageSize + "/" + this.total);
          this.archiveLists = response.data.list;
        } else {
          console.log(errorLog)
        }
      })
    },
    filter() {
      //分页刷新,回到首页
      this.currentPage = 1;
      this.pageSize = 10;
      this.PageAllArchive();
    },

    openDialog(id) {
      OneSubjectIntro({ id: id }).then(response => {
        this.dialogVisible = true
        this.onesubject = response.data[0]
      })
    },


    select() {
      console.log(this.value)
      var pageNum = this.currentPage
      var pageSize = this.pageSize
      var orderColumn = "paperId"
      var isAsc = true
      var paperName = this.value//排序
      var paperDes = ""//排序
      var paperType = ""//排序
      var location = ""//排序
      var process = ""//排序
      var technology = ""//排序
      var creator = ""//排序
      var state = 0//排序      
      PageAllPaper({ pageSize, pageNum,orderColumn,isAsc,paperName,paperDes,paperType,location,process,technology,creator, state}).then((response) => {
        if (response) {
          this.total = response.data.total;
          this.reload();//局部刷新
          console.log(pageNum + "/" + pageSize + "/" + this.total);
          this.paperList = response.data.list;
        } else {
          console.log(errorLog)
        }
      })
    },
    selectt2() {
      console.log(this.value2)
      var pageNum = this.currentPage1
      var pageSize = this.pageSize1
      var isAsc = true
      var institutionName = this.value2//排序
      var institutionLocation = ""//排序
      var startDate = ""//排序
      var endDate = ""//排序
      var institutionDescription = ""//排序
      var orderColumn = "institutionId"
      var creator = ""//排序
      var state = 0//排序      
      PageAllInstitution({ pageSize, pageNum,creator, state,isAsc,orderColumn,institutionName,institutionLocation, startDate,endDate,institutionDescription}).then((response) => {
        if (response) {
          this.total1 = response.data.total;
          this.reload();//局部刷新
          console.log(pageNum + "/" + pageSize + "/" + this.total);
          this.institutionList = response.data.list;
        } else {
          console.log(errorLog)
        }
      })
    },
    selectt3(){
      console.log(this.value1)
      var pageNum = this.currentPage2
      var pageSize = this.pageSize1
      var isAsc = true
      var archiveName = this.value1//排序
      var institutionName = ""//排序
      var archiveContent = ""//排序
      var orderColumn = "archiveId"
      var creator = ""//排序
      var state = 0//排序 
      var datefilter =this.datefilter
      var datefilter1=this.datefilter1
      PageAllArchive({ pageSize,pageNum,orderColumn,isAsc,creator, state,archiveName,institutionName,archiveContent,datefilter,datefilter1}).then((response) => {
        if (response) {
          this.total2 = response.data.total;
          this.reload();//局部刷新
          console.log(pageNum + "/" + pageSize + "/" + this.total);
          this.archiveLists = response.data.list;
        } else {
          console.log(errorLog)
        }
      })
    },
    selectt(paperId) {
      if (paperId !== '') {
        console.log(paperId)
        this.$router.push({
          path: './select',
          query: {
            info: this.$Base64.encode(paperId)
          }
        })
      }
    },

    handle() {
      //  路径/home对应我在router目录下index.js中定义的path属性值
      this.$router.push('./echartsIn');
    },
    handle1() {
      //  路径/home对应我在router目录下index.js中定义的path属性值
      this.$router.push('./echartsAr');
    },
    select2(archiveId) {
      if (archiveId !== '') {
        console.log(archiveId)
        this.$router.push({
          path: './selectAr',
          query: {
            info: this.$Base64.encode(archiveId)
          }
        })
      }
    },
 
    select1(institutionId) {
      if (institutionId !== '') {
        console.log(institutionId)
        this.$router.push({
          path: './selectIn',
          query: {
            info: this.$Base64.encode(institutionId)
          }
        })
      }
    },
   
  },
  filters: {
    formatData(data) {
      return moment(data).format('YYYY-MM-DD')
    }
  },
  formatData(firstTime) {
    var s = new Date(firstTime).toLocaleString();
    return s;
  },
  formatData(time) {
    var s = new Date(time).toLocaleString();
    return s;
  },
}

</script>

<style scoped>
.content {
  width: 100%;
  height: 100%;
  position: relative;
  top: 0;
  left: 0;
}

h2 {
  border-bottom: 3px solid #B03632;
}

.sousuo {
  /* bottom: 12px; */
  width: 100px;
  
}

.name {
  font-family: "STKaiti";
  font-weight: bold;
  font-size: 20px;
}


.el-dialog-div {
  max-height: 120vh;
}

.head-box {
  background: #fff4dd;
  padding: 15px;
  margin: auto;
  width: 100%;
  text-align: center;
  margin-top: 20px;
  font-size: 25px;
  font-family: "STKaiti";
  letter-spacing: 3px;
}


.table-a {
  font-weight: bold;
  font-family: STKaiti;
  font-size: 20px;
  text-decoration: none;
  color: #463131;
}

.tabs {
  text-align: center;
  top: 50px;
  left: 0px;
  ;
  position: relative;
}

.el-tabs {
  background-color: antiquewhite;
  color: #000;
  border-radius: 17px;
  left: 0px;
  top: 0px;
  position: relative;
}

::v-deep .el-tabs__nav-wrap::after {
  position: static !important;
}

.time {
  font-size: 13px;
  color: #999;
}

.filter {
  font-size: 16px;
  font-family: "STKaiti";
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
  /* margin-top: 2%; */
}

.box-card {
  width: 200px;
  height: 160px;
  margin-top: 10%;
  margin-bottom: 10%;
  cursor: pointer;

}

.image {
  width: 100%;
  height: 480px;
  display: block;
}

.datelist li {
  position: relative;
  padding: 10px 150px 21px 0;
  color: #252525;
  font-size: 20px;
  border-bottom: 1px dashed rgb(216, 176, 123);
  width: 100%;
  text-align: center;
  text-indent: 10em;
  background: url(../../assets/list_cir.png) left no-repeat;

}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

/* 设置当前选中样式 */
::v-deep .el-tabs__item.is-active {
  width: 300px;
  color: #AA7F55 !important;
  z-index: 2;
  font-size: 24px;
}

/* 设置未被选中样式 */
::v-deep .el-tabs__item {
  padding: 0 30% !important;
  width: 300px;
  box-sizing: border-box;
  display: inline-block;
  position: relative !important;
  color: #000000 !important;
  z-index: 2;
  font-size: 24px;

}

.el-card {
  min-width: 880px;
  margin-right: 20px;
  transition: all .5s;
}

.el-card:hover {
  transform: scale(1.1);
}

.el-row .el-card {
  min-width: 100%;
  height: 100%;
  margin-right: 20px;
  transition: all .5s;

}
</style>