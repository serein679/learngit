<template>
  <div style="margin-top: 8px">
    <div>
      <el-row>
        <el-col :span="24">
          <p class="title">用户管理</p>
          <el-row style="margin-top: 15px; ">
            <el-col :span="11" class="filter"  style="text-align: left;">
              <span>
                <el-input v-model="name" filterable remote reserve-keyword placeholder="请输入关键词" :loading="loading"
                  clearable style="font-family:STKaiti;font-size: 20px;width: 250px;">
                </el-input>
              </span>
              <span>
                <el-button type="primary" @click="filter"
                  style="background-color:peru;font-family: STKaiti;font-size: 18px;">搜索</el-button>
                <el-button plain @click="resetQuery" style="font-family: STKaiti;font-size: 18px;">全部</el-button>
              </span>
            </el-col>
            <el-col :span="13"  style="text-align: right">
              <el-button type="success" icon="el-icon-plus" @click="addPaper"
                style="background-color:peru;border: 0;font-family: STKaiti;font-size: 18px;margin-left: 22px;">新增管理员
              </el-button>
            </el-col>

          </el-row>
          <el-row style="margin-top:5px ;">
          </el-row>
          <el-row style="margin-top:5px ;">
            <el-col :span="6" class="filter" style="margin-left: 20px; text-align: left;">
              <span style="font-family: STKaiti;font-size: 18px;">用户类型：
                <el-select v-model="typefilter" clearable placeholder="请选择" filterable>
                  <el-option v-for="item in typelist2" :key="item.userType" :label="item.typeName2" :value="item.userType"
                    style="font-family: STKaiti;font-size: 16px;">
                  </el-option>
                </el-select>
              </span>
            </el-col>
            <el-col :span="6" class="filter">
              <span style="font-family: STKaiti;font-size: 18px;">用户状态：
                <el-select v-model="typefilter1" clearable placeholder="请选择" filterable>
                  <el-option v-for="item in typelist1" :key="item.state" :label="item.typeName1" :value="item.state"
                    style="font-family: STKaiti;font-size: 16px;">
                  </el-option>
                </el-select>
              </span>
            </el-col>
            <el-col :span="2" class="filter">
              <el-button type="primary" @click="filter"
                style="background-color:peru;border: 0;font-family: STKaiti;font-size: 18px;">筛选</el-button>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-table v-if="isShow" ref="table" :data="userList" @filter-change="_filterChange" class="table" empty-text="暂无资源"
        style="width:100%; margin: auto; margin-top:10px;font-family: STKaiti;font-size: 18px;text-align: center;background-color: antiquewhite;">
        <el-table-column label="序号" width="140px" type="index" :index="indexMethods"
          style="text-align: center;"></el-table-column>
        <el-table-column label="用户昵称" prop="userName" width="320px">
        </el-table-column>
        <el-table-column label="用户头像" prop="picture" width="330px">
            <template slot-scope="scope"> <img :src="scope.row.picture"min-width="90"height="90"/>
            </template>
          <!-- <img :src="picture" alt=""> -->
        </el-table-column>
        <el-table-column label="用户状态" prop="state" width="250px">
          <template slot-scope="props">
            {{ typeName1[props.row.state] }}
          </template>
        </el-table-column>
        <!-- <el-table-column label="一键修改用户状态" prop="state" width="300px">
          <template slot-scope="props">
            <el-switch v-model="props.row.state" :active-value="1" :inactive-value="2"
              @change="userStateChanged(props.row.userId)" active-color="#13ce66" inactive-color="#ff4949">
            </el-switch>
          </template>
        </el-table-column> -->
        <el-table-column label="用户类型" prop="userType" width="150px">
          <template slot-scope="props">
            {{ typeName2[props.row.userType] }}
          </template>
        </el-table-column>
        <el-table-column label="管理" width="220px" fixed="right" prop="state">
          <template slot-scope="props" style="font-size: 18px;">
            <el-button @click="deleClick(props.row.userId)" type="text" size="big" style="font-size: 18px;"
              v-if="props.row.state == 1">禁用</el-button>
            <el-button @click="deleClick1(props.row.userId)" type="text" size="big" style="font-size: 18px;"
              v-if="props.row.state == 2">
              启用</el-button>
          </template>
        </el-table-column>
      </el-table><!--表格-->
      <pagination style="width:100%; margin: auto;font-family: 楷体;text-align: center;" v-show="total1 > 0" :page.sync="currentPage"
        :limit.sync="pageSize" :page-sizes="pageSizes" layout="total, sizes, prev, pager, next, jumper"
        :total="Number(total1)" @pagination="PageAllUser"><!--分页-->
      </pagination><!--分页-->
      <!--资源详情管理编辑框-->
    </div>
  </div>
</template>
<script>
// import Carousel from '@/components/Carousel.vue';
import './filters.js'
import Pagination from "@/components/Pagination";
import { UpdateStatus, PageAllUser, DelePaper, DelePaper1, addPaper } from "@/api/users"


import errorLog from '@/store/modules/errorLog';
// import WangEditor from '@/components/WangEditor.vue'
// import {GetUsers} from '@api/users'


export default {
  name: 'Managenent',
  components: {
    // Carousel,
    Pagination
  },
  data() {
    return {
      activeName1: '1',
      activeName: '1',
      coursefilter: '',//筛选
      typefilter: '',
      typefilter1: '',
      users: [],
      options: [],
      value: [],
      institutionList: [],

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
      typeName: {
        '0': "手工纸",
        '1': "机械纸",
        '2': "半手工半机械纸",
      },//将资源类型转换为对应文字
      typeName1: {
        0:"注销",
        1: "正常",
        2: "已禁用"

      },
      userList: [],
      typeName2: {
        0: "用户",
        1: "管理员",

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
      typelist2: [{
        userType: 1,
        typeName2: "管理员",
      }, {
        userType: 0,
        typeName2: "用户",
      },], // 资源类型列表
      typelist1: [{
        state: 1,
        typeName1: "正常",
      }, {
        state: 0,
        typeName1: "已禁用",
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
    // this.getQuery();
    this.PageAllUser()

    // this.PageAllPaper();
    // this.getTeacherIntro();
    // this.getUsers();
  },
  computed: {
    listSet() {
      return this.paperList.map(item => {
        return { value: `${item.paperId}`, label: `${item.paperName}` };
      });
    },
  },
  methods: {

    filterTag(value, row, column) {
      const property = column["property"];
      return row[property] === value;
    },

    select(institutionId) {
      console.log(institutionId)
      this.$router.push({
        path: '../introduction/selectIn',
        query: {
          info: this.$Base64.encode(institutionId)
        }
      })

    },

    PageAllUser() {
      var pageNum = this.currentPage
      var pageSize = this.pageSize
      var state = ""//排序
      var username = ""//排序
      var orderColumn = "userId"//排序
      var isAsc = false //排序
      PageAllUser({ pageSize, state, pageNum, isAsc,orderColumn,username }).then((response) => {
        if (response) {
          this.total1 = response.data.total;
          this.reload();//局部刷新
          console.log(pageNum + "/" + pageSize + "/" + this.total);
          this.userList = response.data.list;
        } else {
          console.log(errorLog)
        }
      })
    },
    remoteMethod1(query) {
      if (query !== '') {
        this.loading = true;
        SelectInstitution(query).then((response) => {
          this.institutionList1 = response.data;
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
        // this.$refs.table.bodyWrapper.scrollTop = 0;
      })
    },

    openAdd() {
      this.adVisible = true;
      this.addpaper = [];
      this.fileName = "";
    },

    deleClick(userId) {
      this.$confirm('您确定要禁用该用户吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var id = userId
        var status = 2
        UpdateStatus({id,status}).then(response => {
          if (response.message == "用户状态已更新") {
            this.$message({
              type: 'success',
              message: "用户状态已更新"
            })
            this.PageAllUser()
            this.$forceUpdate();   // 强制更新
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
    deleClick1(userId) {
      console.log(userId);//已获取
      this.$confirm('您确定要启用该用户吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var id = userId
        var status = 1
        UpdateStatus({id,status}).then(response => {
          if (response.message == "用户状态已更新") {
            this.$message({
              type: 'success',
              message: "用户状态已更新"
            })
            this.$forceUpdate();   // 强制更新
            this.PageAllUser()
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
      this.PageAllUser();
      // }, 1000);
    },
    filter() {
      //分页刷新,回到首页
      this.currentPage = 1;
      this.pageSize = 10;
      // var name = this.name;
      console.log(this.name)

      this.PageAllUser();
    },

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

    updateClick(institutionId) {
      console.log(institutionId)
      this.$router.push({
        path: 'editorMien',
        query: {
          info: this.$Base64.encode(institutionId)
        }
      });
    },


    // 添加主页项目
    addPaper() {
      this.$router.push('/management/addUser');
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
  font-family: "STKaiti";
}

.location {
  font-size: 25px;
  color: #B03632;
  font-family: "STKaiti";
  margin-top: 40px;
}

.title {
  font-size: 24px;
  font-family: "STKaiti";
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
  font-family: "STKaiti";
}

h4 {
  font-size: 25px;
  font-family: "STKaiti";
}

.main-area {
  border-top: solid #B03632;
}

.el-tabs--left .el-tabs__item.is-left {
  font-size: 20px;
  font-family: "STKaiti";
}

.el-breadcrumb__item {
  font-size: 20px;
  font-family: "STKaiti";
}
</style>