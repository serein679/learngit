<template>
  <div style="margin-top: 8px">
    <div>
      <el-row>
        <el-col :span="24">
          <p class="title">纸张管理</p>
          <el-row style="margin-top: 15px; ">
            <el-col :span="11" class="filter" style="text-align: left;">
              <span>
                <el-input v-model="name" filterable remote reserve-keyword placeholder="请输入关键词" :loading="loading"
                  clearable style="font-family: STKaiti;font-size: 20px;width: 250px;">
                </el-input>
              </span>
              <span>
                <el-button type="primary" @click="filter"
                  style="background-color:peru;font-family: STKaiti;font-size: 18px;">搜索</el-button>
                <el-button plain @click="resetQuery" style="font-family: STKaiti;font-size: 18px;">全部</el-button>
              </span>
            </el-col>
            <el-col :span="13" style="text-align: right;">
              <el-button type="success" icon="el-icon-plus" @click="addPaper"
                style="background-color:peru;border: 0;font-family: STKaiti;font-size: 18px;text-align: right;">新增纸张
              </el-button>
            </el-col>

          </el-row>
          <el-row style="margin-top:5px ;">
            <el-col :span="8" class="filter" style="text-align: left;">
              <span style="font-family: STKaiti;font-size: 18px;">纸张类型：
                <el-select v-model="typefilter" clearable placeholder="请选择" filterable>
                  <el-option v-for="item in typelist" :key="item.paperType" :label="item.typeName" :value="item.paperType"
                    style="font-family: STKaiti;font-size: 16px;">
                  </el-option>
                </el-select>
              </span>
            </el-col>
            <el-col :span="6" class="filter">
              <span style="font-family: STKaiti;font-size: 18px;">纸张状态：
                <el-select v-model="typefilter1" clearable placeholder="请选择" filterable>
                  <el-option v-for="item in typelist1" :key="item.state" :label="item.typeName1" :value="item.state"
                    style="font-family: STKaiti;font-size: 16px;">
                  </el-option>
                </el-select>
              </span>
            </el-col>
            <el-col :span="2"  style="text-align: right;">
              <el-button type="primary" @click="filter"
                style="background-color:peru;border: 0;font-family: STKaiti;font-size: 18px;">筛选</el-button>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-table v-if="isShow" ref="table" :data="paperList" @filter-change="_filterChange"  empty-text="暂无资源"
        style="width:100%; margin: auto; margin-top:10px;font-family: STKaiti;font-size: 20px;text-align: center;background-color: antiquewhite;">
        <el-table-column label="序号" width="120px" type="index" :index="indexMethods" v-loading="loading"
          element-loading-text="拼命加载中" style="text-align: center;"></el-table-column>
        <el-table-column label="纸张名称" prop="paperName" width="280px">
          <template slot-scope="scope" v-for="item in paperList">
            <div style="font-size: 18px;">
              <el-link @click="select(scope.row.paperId)">
                <div style="font-size: 18px;">{{ scope.row.paperName }}</div>
              </el-link>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="纸张种类" prop="paperType" width="220px" sortable>
          <template slot-scope="props">
            {{ typeName2[props.row.paperType] }}
          </template>
        </el-table-column>
        <el-table-column label="纸张状态" prop="state" width="180px">
          <template slot-scope="props">
            {{ typeName1[props.row.state] }}
          </template>
        </el-table-column>
        <el-table-column label="上传人" prop="creator" width="220px">
        </el-table-column>
        <el-table-column label="上传时间" prop="creationTime" width="220px" sortable>
          <template slot-scope="scope" v-for="item in paperList">
            {{ new Date(scope.row.creationTime).toLocaleDateString('zh').replaceAll('/',
              '-') }}
          </template>
        </el-table-column>
        <el-table-column label="管理" width="220px" fixed="right" prop="state">
          <template slot-scope="props" style="font-size: 18px;">
            <el-button @click="updateClick(props.row.paperId)" type="text" size="big" style="font-size: 18px;"
              v-if="props.row.state != 1">
              编辑</el-button>
            <el-button @click="deleClick(props.row.paperId)" type="text" size="big" style="font-size: 18px;"
              v-if="props.row.state != 1">删除</el-button>
            <el-button @click="deleClick1(props.row.paperId)" type="text" size="big" style="font-size: 18px;"
              v-if="props.row.state != 0">
              取消删除</el-button>
          </template>
        </el-table-column>
      </el-table><!--表格-->
      <pagination style="width:100%; margin: auto;font-family: STKaiti;" v-show="total1 > 0" :page.sync="currentPage"
        :limit.sync="pageSize" :page-sizes="pageSizes" layout="total, sizes, prev, pager, next, jumper"
        :total="Number(total1)" @pagination="PageAllPaper"><!--分页-->
      </pagination><!--分页-->
      <!--资源详情管理编辑框-->
    </div>
  </div>
</template>
<script>
// import Carousel from '@/components/Carousel.vue';
import './filters.js'
import Pagination from "@/components/Pagination";
import { SelectPaper, getPaper, PageAllPaper, DelePaper, DelePaper1, addPaper } from "@/api/paper"
import errorLog from '@/store/modules/errorLog';
// import WangEditor from '@/components/WangEditor.vue'
// import {GetUsers} from '@api/users'
import { Loading } from 'element-ui';
let loading;

export default {
  name: 'Managenent',
  components: {
    // Carousel,
    Pagination
    // WangEditor,
  },
  data() {
    return {
      loading: true,
      activeName1: '1',
      activeName: '1',
      coursefilter: '',//筛选
      typefilter: '',
      typefilter1: '',
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
      teachers: [],
      smid: "",
      paperList: [],
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
      name: "",//检索词
      order: 0,//排序
      oneProjects: [],
      fileName: "",//上传的文件名称
      fileName1: "",//上传的文件名称
      file: null,
      dialogVisible5: false,
      adVisible5: false,
      total4: '',
      typeName2: {
        '0': "手工纸",
        '1': "机械纸",
        '2': "其他",
      },//将资源类型转换为对应文字
      typeName1: {
        0: "正常",
        1: "已删除",
      },
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
      typelist: [{
        paperType: "0",
        typeName: "手工纸",
      }, {
        paperType: "1",
        typeName: "机械纸",
      }, {
        paperType: "2",
        typeName: "半手工半机械纸",
      }], // 资源类型列表

      typelist1: [{
        state: 0,
        typeName1: "正常",
      }, {
        state: 1,
        typeName1: "已删除",
      },], // 资源类型列表
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
    this.tag = 0;
    this.PageAllPaper();
    this.startLoading()  // 开启加载
    loading.close(); // 关闭加载
  },
  computed: {
    listSet() {
      return this.paperList.map(item => {
        return { value: `${item.paperId}`, label: `${item.paperName}` };
      });
    },
  },
  methods: {
    // this.pictLoading = true
    // function() { }
    // this.pictLoading = false
    filterTag(value, row, column) {
      const property = column["property"];
      return row[property] === value;
    },
    startLoading() {  // 使用Element loading-start 方法
      loading = Loading.service({
        lock: true,
        text: '加载中……',
        background: 'rgba(0, 0, 0, 0.7)'
      });
    },

    select(paperId) {
      console.log(paperId)
      this.$router.push({
        path: '../introduction/select',
        query: {
          info: this.$Base64.encode(paperId)
        }
      })

    },
    remoteMethod1(query) {
      if (query !== '') {
        this.loading = true;
        SelectPaper(query).then((response) => {
          this.paperList1 = response.data;
        });
        setTimeout(() => {
          this.loading = false;
          this.options1 = this.listSet1.filter(item => {
            return item.label.toLowerCase()
              .indexOf(query.toLowerCase()) > -1;
          });
        }, 1000);
      } else {
        this.options1 = [];
      }
    },
    _filterChange() {
      this.totalSize = this.$refs.table.paperList.length;
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

    openAdd() {
      this.adVisible = true;
      this.addpaper = [];
      this.fileName = "";
    },

    selectList1() {
      getPaper().then((response) => {
        this.selectName = response.data;
      })
    },
    resetQuery() {
      //重置查询条件
      this.name = '';
      //分页刷新,回到首页
      this.currentPage = 1;
      this.pageSize = 10;
      //重置筛选条件
      this.typefilter = '';
      this.typefilter1 = '';
      this.coursefilter = '';
      // setTimeout(() => {
      this.PageAllPaper();
      // }, 1000);
    },
    filter() {
      //分页刷新,回到首页
      this.currentPage = 1;
      this.pageSize = 10;
      var name = this.name;
      console.log(this.name)
      this.PageAllPaper();
    },
    PageAllPaper() {
      var pageNum = this.currentPage
      var pageSize = this.pageSize
      var orderColumn = "paperId"
      var isAsc = true
      // var name = this.name;
      var paperName = this.name;//排序
      var paperDes = ""//排序
      var paperType = this.typefilter//排序
      var location = ""//排序
      var process = ""//排序
      var technology = ""//排序
      var creator = ""//排序
      var state = this.typefilter1//排序      
      PageAllPaper({pageSize, pageNum,orderColumn,isAsc,paperName,paperDes,paperType,location,process,technology,creator, state }).then(response => {
        if (response) {
          this.tag = 1;
          this.paperList = response.data.list;
          this.total1 = response.data.total;
          this.$forceUpdate();   // 强制更新
          console.log(this.paperList)
        } else {
          console.log(errorLog)
        }
      })

    },
    //列表序号
    indexMethods(index) {
      // 当前页数 - 1 * 每页数据条数 + index + 1 （ index 是索引值，从0开始）
      return (this.currentPage - 1) * this.pageSize + index + 1;
    },

    selectList() {
      AllResourceList().then((response) => {
        this.selectName = response.data;
      })
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

    updateClick(paperId) {
      console.log(paperId)
      this.$router.push({
        path: 'editorProject',
        query: {
          info: this.$Base64.encode(paperId)
        }
      });
    },
    // 添加主页项目
    addPaper() {
      this.$router.push('/management/addProject');
    },
    //删除
    deleClick(paperId) {
      this.$confirm('您确定要删除该纸张吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const formData = new FormData();
        // formData.append('paperId', paperId);
        formData.append('status', 1);
        console.log(formData);//已获取
        var id = paperId
        var status = 1
        DelePaper({id,status}).then(response => {
          if (response.message == "纸张状态已更新！") {
            this.$message({
              type: 'success',
              message: "纸张状态已更新！"
            })
            this.$forceUpdate();   // 强制更新
            // this.getAllResource(this.order)
            this.PageAllPaper(this.order)
          } else if (response.message == "操作失败，请重试") {
            this.$message({
              type: 'error',
              message: '操作失败，请重试！'
            })
          } else {
            this.$message({
              type: 'error',
              message: response.message
            })
          }
        })
      })
    },
    deleClick1(paperId) {
      console.log(paperId);//已获取
      this.$confirm('您确定要恢复该纸张吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const formData = new FormData();
        // formData.append('paperId', paperId);
        // console.log(paperId);//已获取
        // formData.append('status', 0);
        var id = paperId
        var status = 0
        DelePaper({id,status}).then(response => {
          if (response.message == "纸张状态已更新！") {
            this.$message({
              type: 'success',
              message: "纸张状态已更新！！"
            })
            this.$forceUpdate();   // 强制更新
            this.PageAllPaper(this.order)
          } else if (response.message == "暂无权限") {
            this.$message({
              type: 'error',
              message: '暂无删除权限！'
            })
          } else {
            this.$message({
              type: 'error',
              message: response.message
            })
          }
        })
      })
    },


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
.header {
  background: #523434;
  /*   margin-bottom: 10px; */
  padding: 10px;
  text-align: left;
}

.content {
  background: #b7abab;

  /deep/.table-center {
    text-align: center;
  }
}

.title-box {
  background: #EDD9AF;
  padding: 20px;
  font-size: 25px;
  font-family: STKaiti;
}

.location {
  font-size: 25px;
  color: #B03632;
  font-family: STKaiti;
  margin-top: 40px;
}

.title {
  font-size: 24px;
  font-family: STKaiti;
  text-align: left;
  font-weight: bold;
  margin-left: 2px;
}

p {
  width: 350px;
  text-overflow: ellipsis;
  word-wrap: break-word;
  -webkit-line-clamp: 2;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

h2 {
  border-bottom: 3px solid #B03632;
}

h3 {
  font-size: 25px;
  font-family: STKaiti;
}

h4 {
  font-size: 25px;
  font-family: STKaiti;
}

.main-area {
  border-top: solid #B03632;
}

.el-tabs--left .el-tabs__item.is-left {
  font-size: 20px;
  font-family: STKaiti;
}

.el-breadcrumb__item {
  font-size: 20px;
  font-family: STKaiti;
}
</style>