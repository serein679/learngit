<template>
  <div style="margin-top: 8px">
    <div>
      <el-row>
        <el-col :span="24">
          <p class="title">审核管理</p>
          <el-row style="margin-top:5px ;">
          </el-row>
        </el-col>
      </el-row>
      <el-row style="margin-top:5px ;">
        <el-col :span="6" class="filter"  style="text-align: left;">
          <span style="font-family: STKaiti;font-size: 18px;">审核状态：
            <el-select v-model="typefilter1" clearable placeholder="请选择" filterable>
              <el-option v-for="item in typelist1" :key="item.reportAs" :label="item.typeName1" :value="item.reportAs"
                style="font-family: STKaiti;font-size: 16px;">
              </el-option>
            </el-select>
          </span>
        </el-col>
        <el-col :span="2" class="filter" style="text-align: right">
          <el-button type="primary" @click="filter"
            style="background-color:peru;border: 0;font-family: STKaiti;font-size: 18px;">筛选</el-button>
        </el-col>
      </el-row>
      <el-table v-if="isShow" ref="table" :data="hisList" @filter-change="_filterChange" class="table" empty-text="暂无资源"
        style="width:100%; margin: auto; margin-top:10px;font-family: STKaiti;font-size: 18px;text-align: center;background-color: antiquewhite;">
        <el-table-column label="序号" width="120px" type="index" :index="indexMethods"
          style="text-align: center;"></el-table-column>
        <el-table-column label="评论内容" show-overflow-tooltip prop="commentContent" width="550px">
          <template slot-scope="scope" v-for="item in hisList">
            <div style="font-size: 18px;cursor: pointer;">
              <el-link @click="select(scope.row.archiveId)">
                <div style="font-size: 18px;">
                  {{ scope.row.commentContent| filterAmount(25) }}
                </div>
              </el-link>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="举报原因" prop="content" width="340px" style="text-align: center; " sortable>
        </el-table-column> 
        <el-table-column label="举报类型" prop="reportReasonType" width="160px">
          <template slot-scope="props">
            {{ typeName2[props.row.reportReasonType] }}
          </template>
        </el-table-column>
        <el-table-column label="审核状态" prop="reportState" width="140px">
          <template slot-scope="props">
            {{ typeName1[props.row.reportState] }}
          </template>
        </el-table-column>
        <!-- <el-table-column label="被举报次数" prop="number" width="140px" style="text-align: center; " sortable>
        </el-table-column> -->
        <!-- <el-table-column label="举报详情" width="140px" style="text-align: center;">
          <template slot-scope="props" style="font-size: 18px;">
            <el-button @click="dialogVisible3 = true; getOneC(props.row.commentId)" type="
            text" size="big" style="font-size: 18px;">
              点击查看</el-button>
          </template>
        </el-table-column> -->
        <el-table-column label="举报时间" prop="reportTime" width="180px" sortable>
          <template slot-scope="scope" >
            {{ new Date(scope.row.reportTime).toLocaleDateString('zh').replaceAll('/',
              '-') }}
          </template>
        </el-table-column>
        <el-table-column label="管理" width="200px" fixed="right" prop="reportState">
          <template slot-scope="props" style="font-size: 18px;">
            <el-button @click="dialogVisible = true; getComment1(props.row.commentId)" type="text" size="big"
              style="font-size: 18px;" v-if="props.row.reportState == 1">
              审核</el-button>
            <el-button @click="dialogVisible = true; getComment1(props.row.commentId)" type="text" size="big"
              style="font-size: 18px;" v-if="props.row.reportState != 1">重新审核</el-button>
          </template>
        </el-table-column>
      </el-table><!--表格-->
      <pagination style="width:100%; margin: auto;font-family: STKaiti;" v-show="total1 > 0" :page.sync="currentPage"
        :limit.sync="pageSize" :page-sizes="pageSizes" layout="total, sizes, prev, pager, next, jumper"
        :total="Number(total1)" @pagination="PageAllHis"><!--分页-->
      </pagination><!--分页-->
      <el-dialog title="提示" :visible.sync="dialogVisible" width="30%" :before-close="handleClose"
        style="font-family: STKaiti;font-size: 24px;">
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
      <el-dialog title="举报详情" :visible.sync="dialogVisible3" width="55%" :before-close="handleClose"
        style="font-family: STKaiti;font-size: 24px;">
        <el-table v-if="isShow" ref="table" :data="comment" @filter-change="_filterChange" class="table" empty-text="暂无资源"
          style="width:1000px; margin: auto; margin-top:10px;font-family: STKaiti;font-size: 18px;text-align: center; ">
          <el-table-column label="举报人" prop="userName" width="220px" style="text-align: center;">
          </el-table-column>
          <el-table-column label="举报原因" prop="reportReasonType" width="250px">
            <template slot-scope="props">
              {{ typeName[props.row.reportReasonType] }}
            </template>
          </el-table-column>
          <el-table-column label="举报时间" prop="eventDate" width="140px" sortable>
            <template slot-scope="scope" v-for="item in comment">
              {{ new Date(scope.row.reportTime
              ).toLocaleDateString('zh').replaceAll('/',
                '-') }}
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>
    </div>
  </div>
</template>
<script>
// import Carousel from '@/components/Carousel.vue';
import './filters.js'
import Pagination from "@/components/Pagination";
import { SelectPaper, getPaper, PageAllPaper, DelePaper, DelePaper1, addPaper } from "@/api/paper"
// import { getHistoricalEvents, SelectHis, getOneHistoricalEvent, deleHis, deleHis1, PageAllHis } from '@/api/his'
import { getOneComment1, PageAllComment,getUserReplyList,getUserComplainList,getUserFeedbackList} from '@/api/comment'
import { PageAllReCo3, PageAllReRE, editRe, getOneC,getCommentShenList } from '@/api/report'
import errorLog from '@/store/modules/errorLog';
// import WangEditor from '@/components/WangEditor.vue'
import { getAuditPageList } from '@/api/report'


export default {
  name: 'Managenent',
  components: {
    // Carousel,
    Pagination
  },
  data() {
    return {
      activeName1: '1',
      dialogVisible: false,
      radio: '4',
      activeName: '1',
      coursefilter: '',//筛选
      typefilter: '',
      typefilter1: '',
      users: [],
      typeName2: {
                '0': "违反法律法规",
                '1': "谣言类不实消息",
                '2': "有害社区环境",
                '3': "侵犯个人权益",
                '4':"其他"
            },
      options: [],
      value: [],
      institutionList: [],
      list: [],
      loading: false,
      isShow: true,
      dialogVisible7: false,
      dialogVisible3: false,
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
        '0': "违反法律法规",
        '1': "谣言类不实消息",
        '2': "有害社区环境",
        '3': "侵犯个人权益",
      },//将资源类型转换为对应文字
      typeName1: {
        1: "待审核",
        0: "审核通过",
        2: "审核不通过",


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
        reportReasonType: "0",
        typeName: "违反法律法规",
      }, {
        reportReasonType: "1",
        typeName: "谣言类不实消息",
      }, {
        reportReasonType: "2",
        typeName: "有害社区环境",
      },
      {
        reportReasonType: "3",
        typeName: "侵犯个人权益",
      }], // 资源类型列表
      entity: [],
      hisList: [],
      comment: [],
      typelist1: [{
        reportAs: 0,
        typeName1: "待审核",
      }, {
        reportAs: 1,
        typeName1: "审核通过",
      }, {
        reportAs: 2,
        typeName1: "审核不通过",
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
    this.PageAllHis()

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
    getComment1(commentId) {
      console.log(commentId)
      this.entity.commentId = commentId


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
          formData.append('file', this.file);
          // 添加其他表单数据
          formData.append('commentId', this.entity.commentId);
          formData.append('reportAs', this.entity.radio);
          // formData.append('reportContentType', this.entity.reportContentType);
          // formData.append('reportReasonType', this.entity.reportReasonType);
          // formData.append('reporterId', this.entity.reporterId);
          // location.reload();//自动刷新  
          editRe(formData).then(response => {
            if (response.message == "操作成功") {
              this.$router.push('/management/achvManagement')
              this.$message({
                type: 'success',
                message: "修改成功！"
              })
              this.dialogVisible = false
              this.$forceUpdate();   // 强制更新
              // this.getSoochowForum(this.order)
              this.PageAllHis(this.order)
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
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => { });
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

    getOneC(commentId) {
      getOneC(commentId).then((response) => {
        this.comment = response.data;
        console.log(this.comment);
      });
    },


    PageAllHis() {
      var pageNum = this.currentPage
      var pageSize = this.pageSize
            var isAsc = true
            var orderColumn = "reportTime"//排序
            getCommentShenList ({ pageSize, pageNum, isAsc,orderColumn  }).then((response) => {
        if (response) {
          this.total1 = response.data.total;
          this.reload();//局部刷新
          console.log(pageNum + "/" + pageSize + "/" + this.total);
          this.hisList = response.data.list;
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
        // this.$refs.table.bodyWrapper.scrollTop = 0;dy
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
      this.PageAllHis();
      // }, 1000);
    },
    filter() {
      //分页刷新,回到首页
      this.currentPage = 1;
      this.pageSize = 10;
      // var name = this.name;
      console.log(this.name)

      this.PageAllHis();
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

    updateClick(eventId) {
      console.log(eventId)
      this.$router.push({
        path: 'editorAchv',
        query: {
          info: this.$Base64.encode(eventId)
        }
      });
    },


    // 添加主页项目
    addPaper() {
      this.$router.push('/management/addAchv');
    },
    //删除
    deleClick(eventId) {
      console.log(eventId);//已获取
      this.$confirm('您确定要删除该大事记吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const formData = new FormData();
        formData.append('eventId', eventId);
        console.log(eventId);//已获取
        deleHis(formData).then(response => {
          if (response.message == "操作成功") {
            this.$message({
              type: 'success',
              message: "删除成功！"
            })
            this.$forceUpdate();   // 强制更新
            // this.getSoochowForum(this.order)
            this.PageAllReCo3(this.order)
            // this.PageAllReCo(order = 0)
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
    deleClick1(eventId) {
      console.log(eventId);//已获取
      this.$confirm('您确定要恢复该大事记吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const formData = new FormData();
        formData.append('eventId', eventId);
        console.log(eventId);//已获取
        deleHis1(formData).then(response => {
          if (response.message == "操作成功") {
            this.$message({
              type: 'success',
              message: "操作成功！"
            })
            setTimeout(function (

            ) { location.reload() }, 100);
            this.PageAllHis(order = 0)
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