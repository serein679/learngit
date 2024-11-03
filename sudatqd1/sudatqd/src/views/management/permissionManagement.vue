<template>
    <div style="margin-top: 8px">
      <div>
        <el-row>
          <el-col :span="24">
            <p class="title">权限管理</p>
            <el-row style="margin-top:5px ;">
            </el-row>
          </el-col>
        </el-row>
        <el-row style="margin-top:5px ;">
        </el-row>
        <el-table v-if="isShow" ref="table" :data="hisList" @filter-change="_filterChange" class="table" empty-text="暂无资源"
          style="width:100%; margin: auto; margin-top:10px;font-family: STKaiti;font-size: 18px;text-align: center;background-color: antiquewhite;">
          <el-table-column label="序号" width="150px" type="index" :index="indexMethods"
            style="text-align: center;"></el-table-column>
          <el-table-column label="权限名称" show-overflow-tooltip prop="permissionName" width="180px">
            <template slot-scope="scope" v-for="item in hisList">
              <div style="font-size: 18px;cursor: pointer;">
                  <div style="font-size: 18px;">{{ scope.row.permissionName
                  }}</div>
              </div>
            </template>
          </el-table-column>
  
          <el-table-column label="角色描述" prop="description" width="500px">
          </el-table-column>
          <el-table-column label="上传人" prop="creator" width="220px">
          </el-table-column>
          <el-table-column label="上传时间" prop="creationTime" width="220px" sortable>
            <template slot-scope="scope" v-for="item in hisList">
              {{ new Date(scope.row.creationTime).toLocaleDateString('zh').replaceAll('/',
                '-') }}
            </template>
          </el-table-column>
          <el-table-column label="权限状态" prop="state" width="180px">
            <template slot-scope="props">
              {{ typeName1[props.row.state] }}
            </template>
          </el-table-column>
          <el-table-column label="管理" width="150px" fixed="right" prop="state">
            <template slot-scope="props" style="font-size: 18px;">
              <el-button  @click="loginVisible1 = true;getRole(props.row.roleId)" type="text" size="big" style="font-size: 18px;"
                >
                编辑</el-button>
              
            </template>
          </el-table-column>
        </el-table><!--表格-->
        <pagination style="width:100%; margin: auto;font-family: STKaiti;" v-show="total1 > 0" :page.sync="currentPage"
          :limit.sync="pageSize" :page-sizes="pageSizes" layout="total, sizes, prev, pager, next, jumper"
          :total="Number(total1)" @pagination="getRolePageList"><!--分页-->
        </pagination><!--分页-->
        <el-dialog title="编辑角色状态" :visible.sync="loginVisible1" style="font-family: STKaiti;">
                      <el-form>
                        <el-form-item label="举报原因" :label-width="formLabelWidth">
                          <el-radio-group v-model="radio">
                            <el-radio :label="0" :value="0">删除</el-radio>
                            <el-radio :label="1" :value="1">恢复正常</el-radio>
                            <el-radio :label="2" :value="2">禁用</el-radio>
                          </el-radio-group>
                        </el-form-item>
                      </el-form>
                      <div slot="footer" class="dialog-footer">
                        <el-button @click="loginVisible1 = false; radio = 0">取 消</el-button>
                        <el-button type="primary" @click="changeRoleStatus(radio);"
                          style="background-color: antiquewhite;border: 0;color: black;border: 1;">提交</el-button>
                      </div>
                    </el-dialog>
  
      </div>
    </div>
  </template>
  <script>
  // import Carousel from '@/components/Carousel.vue';
  import './filters.js'
  import Pagination from "@/components/Pagination";
  import { getPermissionPageList } from '@/api/permission'
  
  import errorLog from '@/store/modules/errorLog';
  // import WangEditor from '@/components/WangEditor.vue'
  
  
  export default {
    name: 'Managenent',
    components: {
      // Carousel,
      Pagination  
    },
    data() {
      return {
        activeName1: '1',
        loginVisible1: false,
        radio: '4',
        activeName: '1',
        coursefilter: '',//筛选
        typefilter: '',
        typefilter1: '',
        users: [],
        radio: null,
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
        smid: "",
        audit:[],
        currentPage: 1,// 默认显示第几页
        pageSizes: [2, 5, 10, 20, 30, 50],// 个数选择器（可修改）
        pageSize: 5,// 默认每页显示的条数（可修改）
        total: '',// 总记录数
        total1: '',
        total2: '',
        total5: '',
        entity: [],
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
          0: "删除",
          1: "正常",
          2: "禁用",
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
        hisList: [],
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
      this.getRolePageList()
      this.getAuditList()
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
  
            editRe1(formData).then(response => {
              if (response.message == "操作成功") {
                this.$router.push('/management/resourceManagement')
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
      getRole(roleId) {
        console.log(roleId)
        this.entity.roleId = roleId
        console.log(this.entity.roleId)
      },
      changeRoleStatus(radio) {
        // getComment(t.commentId)
        console.log(this.entity.roleId)
        this.entity.radio = radio
        this.entity.roleId = this.entity.roleId
        console.log(radio); // 打印 addresource 对象
        if (this.radio == null) {
          this.$message({
            type: 'warning',
            message: '请选择角色状态'
          })
          return false;
        }
        else {
          this.$confirm('您确定要更改吗?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            var id = this.entity.roleId
            var status =this.radio
            changeRoleStatus({id,status}).then(response => {
              if (response.message == "角色状态已更新") {
                this.$message({
                  type: 'success',
                  message: "角色状态已更新！"
                })
                this.loginVisible1 = false
                this.getRolePageList()
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
  
      getRolePageList() {

        var pageNum = this.currentPage
        var pageSize = this.pageSize
        var isAsc = true  
        var orderColumn = "permissionId"//排序
        var state = ''//排序      
        var permissioName = ''//排序    
        var description = ''//排序  
        var creator = ''//排序
        getPermissionPageList({ pageSize, creator,permissioName, description, pageNum, orderColumn,isAsc,state }).then((response) => {
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
          // this.$refs.table.bodyWrapper.scrollTop = 0;
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
  
      getAuditList() {
        getAuditList().then((response) => {
          this.audit = response.data;
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