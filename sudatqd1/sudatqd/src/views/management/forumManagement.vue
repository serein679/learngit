<template>
  <div style="margin-top: 8px">
    <div>
      <el-row>
        <el-col :span="24"> 
          <p class="title">档案管理</p>
          <el-row style="margin-top: 15px; ">
            <el-col :span="11" class="filter" style="text-align: left;">
              <span>
                <el-input v-model="name" filterable remote reserve-keyword placeholder="请输入关键词"
                  :remote-method="remoteMethod" :loading="loading" clearable
                  style="font-family:STKaiti;font-size: 20px;width: 250px;">
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
                style="background-color:peru;border: 0;font-family: STKaiti;font-size: 18px;margin-left: 22px;">新增档案
              </el-button>
            </el-col>

          </el-row>
          <el-row style="margin-top:5px ;text-align: left;" >
            <el-col :span="6" class="filter" style="margin-left: 0px;font-family: STKaiti;font-size: 18px;">
              <span style="font-family: STKaiti;font-size: 18px;">档案类型：
                <el-select v-model="typefilter2" clearable placeholder="请选择" filterable>
                  <el-option v-for="item in typelist2" :key="item.archiveType" :label="item.typeName3"
                    :value="item.archiveType" style="font-family: STKaiti;font-size: 16px;">
                  </el-option>
                </el-select>
              </span>
            </el-col>
            <el-col :span="6" class="filter">
              <span style="font-family: STKaiti;font-size: 18px;">档案状态：
                <el-select v-model="typefilter1" clearable placeholder="请选择" filterable>
                  <el-option v-for="item in typelist1" :key="item.archiveStateType" :label="item.typeName1"
                    :value="item.archiveStateType" style="font-family: STKaiti;font-size: 16px;">
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
      <el-table v-if="isShow" ref="table" :data="archiveList" @filter-change="_filterChange" class="table"
        empty-text="暂无资源"
        style="width:100%; margin: auto; margin-top:10px;font-family: STKaiti;font-size: 18px;text-align: center;background-color: antiquewhite;">
        <el-table-column label="序号" width="120px" type="index" :index="indexMethods"
          style="text-align: center;"></el-table-column>
        <el-table-column label="档案名称" prop="archiveName" width="320px">
          <template slot-scope="scope" v-for="item in archiveList">
            <div style="font-size: 18px;">
              <el-link @click="select(scope.row.archiveId)">
                <div style="font-size: 18px;">{{ scope.row.archiveName }}</div>
              </el-link>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="档案类型" prop="archiveType" width="180px">
          <template slot-scope="props">
            {{ typeName3[props.row.archiveType] }}
          </template>
        </el-table-column>
        <el-table-column label="档案状态" prop="archiveStateType" width="180px">
          <template slot-scope="props">
            {{ typeName1[props.row.archiveStateType] }}
          </template>
        </el-table-column>
        <el-table-column label="上传人" prop="creator" width="180px">
        </el-table-column>
        <el-table-column label="上传时间" prop="creationTime" width="200px" sortable>
          <template slot-scope="scope" v-for="item in archiveList">
            {{ new Date(scope.row.creationTime).toLocaleDateString('zh').replaceAll('/',
              '-') }}
          </template>
        </el-table-column>
        <!-- <el-table-column label="是否放在首页" prop="pictureState" width="200px">
          <template slot-scope="props">
            <el-switch v-model="props.row.pictureState" :active-value="0" :inactive-value="1"
              @change="userStateChanged(props.row.archiveId)" active-color="#13ce66" inactive-color="#ff4949">
            </el-switch>
          </template>
        </el-table-column> -->
        <el-table-column label="管理" width="150px" fixed="right" prop="state">
          <template slot-scope="props" style="font-size: 18px;">
            <el-button @click="updateClick(props.row.archiveId)" type="text" size="big" style="font-size: 18px;"
              v-if="props.row.archiveStateType == 0 || props.row.archiveStateType == 1">
              编辑</el-button>
            <el-button @click="deleClick(props.row.archiveId)" type="text" size="big" style="font-size: 18px;"
              v-if="props.row.archiveStateType == 0 || props.row.archiveStateType == 1">删除</el-button>
            <el-button @click="deleClick1(props.row.archiveId)" type="text" size="big" style="font-size: 18px;"
              v-if="props.row.archiveStateType == 4">
              取消删除</el-button>

            <el-button @click="select(props.row.archiveId)" type="text" size="big" style="font-size: 18px;"
              v-if="props.row.archiveStateType == 3">
              查看</el-button>
            <el-button @click="dialogVisible = true; getComment1(props.row.archiveId)" type="text" size="big"
              style="font-size: 18px;" v-if="props.row.archiveStateType == 3">
              审核</el-button>
            <el-button @click="dialogVisible = true; getComment1(props.row.archiveId)" type="text" size="big"
              style="font-size: 18px;" v-if="props.row.archiveStateType == 2">
              重新审核</el-button>
          </template>
        </el-table-column>
      </el-table><!--表格-->
      <pagination style="width:100%; margin: auto;font-family: STKaiti;" v-show="total2 > 0" :page.sync="currentPage"
        :limit.sync="pageSize" :page-sizes="pageSizes" layout="total, sizes, prev, pager, next, jumper"
        :total="Number(total2)" @pagination="getSoochowForum"><!--分页-->
      </pagination><!--分页-->
      <el-dialog title="提示" :visible.sync="dialogVisible" width="30%" style="font-family:STKaiti;font-size: 24px;">
        <el-form :model="{ entity }">
          <el-form-item label="举报审核：" :label-width="formLabelWidth">
            <el-radio-group v-model="radio">
              <el-radio :label="1">审核通过</el-radio>
              <el-radio :label="2">审核不通过</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="editRe(radio)">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
// import Carousel from '@/components/Carousel.vue';

import Pagination from "@/components/Pagination";
import errorLog from '@/store/modules/errorLog';
import { DeleArchive, DeleArchive1, PageAllArchive, editRe, getOneArchive, getArchive, SelectArchive } from '@/api/archive'
import { SelectPaper, getPaper, UpdateStatus } from "@/api/archive"
export default {
  name: 'Managenent',
  components: {
    // Carousel,
    Pagination,
  },
  data() {
    return {
      activeName1: '1',
      radio: '4',
      // handleClose: '',
      activeName: '1',
      currentDate: '',
      currentDate1: '',

      datefilter: new Date(1912, 0, 1, 0, 0),
      datefilter1: new Date(1949, 9, 1, 0, 0),
      typelist1: [{
        archiveStateType: 0,
        typeName1: "管理员上传",
      }, {
        archiveStateType: 1,
        typeName1: "待审核",
      }, {
        archiveStateType: 2,
        typeName1: "审核不通过",
      }, {
        archiveStateType: 3,
        typeName1: "审核通过",
      }, {
        archiveStateType: 4,
        typeName1: "删除",
      }], // 资源类型列表
      typelist2: [{
        archiveType: 0,
        typeName3: "护照",
      }, {
        archiveType: 1,
        typeName3: "地契",
      }, {
        archiveType: 2,
        typeName3: "票据",
      }, {
        archiveType: 3,
        typeName3: "其他",
      }], // 资源类型列表
      options: [],
      value: [],
      coursefilter: '',//筛选
      typefilter2: '',
      typefilter1: '',
      name: '',
      list: [],
      loading: false,
      isShow: true,
      dialogVisible7: false,
      adVisible7: false,
      dialogVisible9: false,
      adVisible9: false,
      dialogVisible8: false,
      adVisible8: false,
      typeName1: {
        0: "管理员上传",
        1: "待审核",
        2: "审核不通过",
        3: "审核通过",
        4: "删除",
      },
      typeName3: {
        0: "护照",
        1: "地契",
        2: "票据",
        3: "其他"
      },
      smid: "",
      archiveList: [],
      currentPage: 1,// 默认显示第几页
      pageSizes: [2, 5, 10, 20, 30, 50],// 个数选择器（可修改）
      pageSize: 10,// 默认每页显示的条数（可修改）
      oneForum: [],
      total2: '',
      selectName: [],
      selectName7: [],
      entity: [],
      query: "",//检索词
      order: 0,//排序
      addforum: {
        forumName: "",
        forumTime: '',
        hyperlinks: '',
        soochowForumId: '',
      },// 新增表单
      fileName: "",//上传的文件名称
      fileName1: "",//上传的文件名称
      file: null,
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
    this.getSoochowForum();
    // this.getUsers();

  },
  computed: {
    listSet() {
      return this.archiveList.map(item => {
        return { value: `${item.archiveId}`, label: `${item.archiveName}` };
      });
    },

  },
  methods: {
    getComment1(archiveId) {
      console.log(archiveId)
      this.entity.archiveId = archiveId
    },
    userStateChanged(archiveId) {
      UpdateStatus(archiveId).then(response => {
        console.log(archiveId)
        if (response.message == "操作成功") {
          this.$message({
            type: 'success',
            message: "改变成功！"
          })
          this.getSoochowForum()
        } else if (response.message == "暂无权限") {
          this.$message({
            type: 'error',
            message: '暂无用户管理权限！'
          })
        } else {
          this.$message({
            type: 'error',
            message: response.message
          })
        }
      })
    },
    editRe(radio) {
      this.entity.radio = radio
      console.log(radio); // 打印 addresource 对象
      if (this.reportAs == "") {
        this.$message({
          type: 'warning',
          message: '审核状态为空，请填写！'
        })
        return false;
      }
      else {
        this.$confirm('您确定要提交该审核吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 创建一个 FormData 对象
          const formData = new FormData();
          // formData.append('file', this.file);
          // 添加其他表单数据
          formData.append('archiveId', this.entity.archiveId);
          formData.append('archiveStateType', this.entity.radio);
          editRe(formData).then(response => {
            if (response.message == "操作成功") {
              this.$router.push('/management/forumManagement')
              this.$message({
                type: 'success',
                message: "修改成功！"
              })
              this.dialogVisible = false
              this.$forceUpdate();   // 强制更新
              // this.getSoochowForum(this.order)
              this.getSoochowForum(this.order)
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
      }
    },
    filterTag(value, row, column) {
      const property = column["property"];
      return row[property] === value;
    },

    _filterChange() {
      this.totalSize = this.$refs.table.archiveList.length;
      console.log(this.totalSize);
    },
    reload() {//局部刷新
      this.isShow = false;
      this.$nextTick(() => {
        this.isShow = true;
        this.$refs.table.bodyWrapper.scrollTop = 0;
      })
    },
    addPaper() {
      this.$router.push('/management/addTeacher');
    },
    remoteMethod(query) {
      if (query !== '') {
        this.loading = true;
        SelectArchive(query).then((response) => {
          this.archiveList = response.data;
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
    updateClick(archiveId) {
      console.log(archiveId)
      this.$router.push({
        path: 'editorTeacher',
        query: {
          info: this.$Base64.encode(archiveId)
        }
      });
    },
    select(archiveId) {
      if (archiveId !== '') {
        console.log(archiveId)
        this.$router.push({
          path: '../introduction/selectAr',
          query: {
            info: this.$Base64.encode(archiveId)
          }
        })
      }
    },

    resetQuery() {
      //重置查询条件
      this.name = '';
      //分页刷新,回到首页
      this.currentPage = 1;
      this.pageSize = 10;
      //重置筛选条件
      this.typefilter2 = '';
      this.typefilter1 = '';
      this.datefilter = '';
      this.datefilter = new Date(1912, 0, 1, 0, 0),
        this.datefilter1 = new Date(1949, 9, 1, 0, 0),
        //   this.datefilter1 = '';
        // this.coursefilter = '';
        this.getSoochowForum();
    },

    filter() {
      //分页刷新,回到首页
      this.currentPage = 1;
      this.pageSize = 10;
      this.getSoochowForum();
    },

    getSoochowForum() {
      console.log(this.query);
      var pageNum = this.currentPage
      var pageSize = this.pageSize
      var isAsc = true
      var archiveName = this.name//排序
      var institutionName = ""//排序
      var archiveContent = ""//排序
      var orderColumn = "archiveId"
      var creator = ""//排序
      var archiveType = this.typefilter2//排序
      var state = this.typefilter1//排序      
      PageAllArchive({ pageSize,pageNum,orderColumn,isAsc,creator, state,archiveName,institutionName,archiveContent,archiveType }).then(response => {
        if (response) {
          this.archiveList = response.data.list;
          this.total2 = response.data.total;
          this.$forceUpdate();   // 强制更新
          console.log(this.archiveList)
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

    openAdd2() {
      this.adVisible2 = true;
      this.addforum = [];
    },
    updateClick2(archiveId) {
      this.dialogVisible2 = true
      this.oneForum = [];
      ChooseOneForum(archiveId).then(response => {
        this.oneForum = response.data[0];
        this.fileName = this.oneForum.forumName;
        console.log(this.oneForum)
      })
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

    deleClick(archiveId) {
      console.log(archiveId);//已获取
      this.$confirm('您确定要删除该档案吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const formData = new FormData();
        formData.append('archiveId', archiveId);
        console.log(archiveId);//已获取
        var id = archiveId
        var status = 4
        DeleArchive({id,status}).then(response => {
          if (response.message == "档案已删除") {
            this.$message({
              type: 'success',
              message: "删除成功！"
            })
            this.$forceUpdate();   // 强制更新
            this.getSoochowForum(this.order)
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
    deleClick1(archiveId) {
      console.log(archiveId);//已获取
      this.$confirm('您确定要取消删除该档案吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const formData = new FormData();
        formData.append('archiveId', archiveId);
        console.log(archiveId);//已获取
        var id = archiveId
        var status = 0
        DeleArchive1({id,status}).then(response => {
          if (response.message == "档案已删除") {
            this.$message({
              type: 'success',
              message: "操作成功！"
            })
            this.$forceUpdate();   // 强制更新
            this.getSoochowForum(this.order)
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
  background: #fff;
  /*   margin-bottom: 10px; */
  padding: 10px;
  text-align: left;
}

.content {
  background: #fff;

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