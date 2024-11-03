<template>
  <div style="margin-top: 8px;background-color: antiquewhite;">
    <!-- <div class="loading"></div> -->
    <div style="margin:0px;top:140px ;background-color: antiquewhite;">
      <div>
        <el-row>
          <el-col :span="3" :offset="1">
            <h3 class="location">当前位置：</h3>
          </el-col>
          <el-col :span="10" style="margin-top: 43px;">
            <el-breadcrumb separator="/">
              <el-breadcrumb-item :to="{ path: '/introduction' }">纸张基因</el-breadcrumb-item>
              <el-breadcrumb-item :to="{ path: '/introduction' }"> 档案库 </el-breadcrumb-item>
              <el-breadcrumb-item><a href="/">档案详情</a></el-breadcrumb-item>
            </el-breadcrumb>
          </el-col>
        </el-row><!--导航-->
        <el-row style="height: 10px;">
          <el-col :span="18" class="main-area" v-for="(r, index) in oneArchive" :key="index">
          </el-col>
        </el-row>
        <el-row style="height: auto;">
          <el-col :span="9"
            style="position: relative;font-family: 楷体;font-size: 22px;text-align: left;top: 0%;width: 800px;height: auto;margin-left: 10%;margin-left: 5%;background-color: white;margin-top: 5%; margin-bottom: 5%;border-radius: 30px;">
            <div>
              <table v-for="(r, index) in oneArchive" style="margin-left:5%;width: 100% ;line-height: 200%;">
                <tr>
                  <div style="margin-top: 30px;">
                    <font style="font-weight: bold;">编号：</font> {{ r.bianhao }}
                  </div>

                </tr>
                <tr>
                  <div style="width: 90px;margin-top: 2%;margin-left: 0%;font-weight: bold;">
                    简介：
                  </div>
                </tr>
                <tr>
                  <div v-for="(r, index) in oneArchive"
                    style="width: 90%;margin-top: 9px;margin-left: 5;margin-right: 5%;text-indent: 2em;line-height: 200%;text-align: justify;">
                    {{ r.archiveContent }}
                  </div>
                </tr>
                <tr>
                  <div style="margin-top: 15px;">
                    <font style="font-weight: bold;">责任者：</font> {{ r.author }}
                  </div>
                </tr>
                <tr>
                  <div style="margin-top: 15px;">
                    <font style="font-weight: bold;">时间：</font> {{ new
                      Date(r.firstTime).toLocaleDateString('zh').replaceAll('/', '-') }}
                  </div>
                </tr>
                <tr>
                  <div style="margin-top: 15px;">
                    <font style="font-weight: bold;">信息采集时间：</font>{{ new
                      Date(r.recordTime).toLocaleDateString('zh').replaceAll('/', '-') }}
                  </div>
                </tr>
                <tr>
                  <div style="margin-top: 15px;">
                    <font style="font-weight: bold;">尺寸：</font> {{ r.size }}
                  </div>
                </tr>
                <tr>
                  <div style="margin-top: 15px;">
                    <font style="font-weight: bold;">厚度：</font> {{ r.thickness }}
                  </div>
                </tr>
                <tr>
                  <div style="margin-top: 15px;">
                    <font style="font-weight: bold;">白度：</font> {{ r.whiteness }}
                  </div>
                </tr>
                <tr>
                  <div style="margin-top: 15px;">
                    <font style="font-weight: bold;">形制：</font> {{ r.form }}
                  </div>
                </tr>
                <tr>
                  <div style="margin-top: 15px;">
                    <font style="font-weight: bold;">打浆度：</font> {{ r.beatDeg }}
                  </div>
                </tr>
                <tr>
                  <div style="margin-top: 15px;">
                    <font style="font-weight: bold;">帘纹：</font> {{ r.lianwen }} 道/cm
                  </div>
                </tr>
                <tr>
                  <div style="margin-top: 15px;">
                    <font style="font-weight: bold;">染色材料：</font> {{ r.color }}
                  </div>
                </tr>
                <tr>
                  <div style="margin-top: 15px;">
                    <font style="font-weight: bold;">添加成分：</font> {{ r.composition }}
                  </div>
                </tr>
                <tr>
                  <div style="margin-top: 15px;">
                    <font style="font-weight: bold;">纤维情况：</font> {{ r.fiber }}
                  </div>
                </tr>
                <tr>
                  <div style="margin-top: 15px;">
                    <font style="font-weight: bold;">纤维老化情况： </font> {{ r.degFiber }}
                  </div>
                </tr>
                <tr v-for="item in paperList" v-if="item.paperId == r.paperId" style="margin-left: 0px;">
                  <div style="margin-left: 0px;float: left; font-weight: bold;margin-top: 10px;">
                    档案用纸情况：
                  </div>
                  <div style="margin-left: 40px;width: 60%; vertical-align: top;float: left;margin-top: 10px;">
                    <font
                      style="cursor: pointer;font-family: 楷体;font-weight:200;color: rgb(205, 134, 63);display:inline-block;vertical-align: top; "
                      @click="select1(item.paperId)">
                      {{ item.paperName }}</font>
                  </div>
                </tr>
                <tr>
                  <div style="margin-left: 0px;float: left;font-weight: bold;margin-top: 10px;">
                    目前收录机构：
                  </div>
                  <div v-for="item in institutionList" v-if="item.institutionId == r.institutionId"
                    style="margin-left: 40px;width: 60%; vertical-align: top;float: left;margin-top: 10px;">
                    <font
                      style="cursor: pointer;font-family: 楷体;font-weight:200;color: rgb(205, 134, 63);display:inline-block;vertical-align: top; "
                      @click="select(item.institutionId)">
                      {{ item.institutionName }}</font>
                  </div>
                </tr>
              </table>
            </div>
          </el-col>
          <el-col :span="6" style="float: right;   margin-top: 15%;margin-right: 5%; ">
            <div>
              <table v-for="(r, index) in oneArchive" style="margin-left:0%;width: 85% ;line-height: 152%;">
                <tr>
                  <div class="biao">
                    {{ r.archiveName }}
                  </div>
                </tr>
                <tr v-for="m in archiveList" v-if="m.archiveId == r.archiveId" style="margin-top: 23%;">
                  <img :src="m.pictureLocation" width="350px" style="margin-top: 33%;" v-viewer>
                </tr>
              </table>
            </div>
          </el-col>
        </el-row>
        <el-row style="height: auto;">
          <el-col :span="20" style="">
            <div>
              <table
                style="margin-left:0%;width: 100% ;line-height: 100%; margin-left: 11%;margin-top:  3%;border-collapse: collapse;">
                <tr class="head">
                  <div>
                    <input v-model="comment" type="text" placeholder="请输入评论 . . ."
                      style="cursor: pointer;font-family: 楷体;" />
                    <form action="" onsubmit="return false">
                      <button type="button"
                        style="cursor: pointer;font-family: 楷体;margin-right: 70px;background-color:rgba(236, 161, 136, 0.77);color: black;margin-top: 7px;"
                        @click="addcomment">发表评论</button>
                    </form>
                  </div>
                </tr>
                <!-- <tr style="margin-top: 20px;line-height: 20px;">
                  <div>
                    共有条评论
                  </div>

                </tr> -->
              </table>
              <div style="margin-top: 60px;line-height: 20px;font-family: 楷体;font-size: 18px;text-align: right;">
                共有{{ this.len }}条评论 | <button v-if="this.len > 2" style="border: 0cap;cursor: pointer;"> 展开所有评论</button>
                <button v-if="this.len > 4">收起评论</button>
              </div>
              <table v-for="(r, index) in oneArchive"
                style="margin-left:0%;width: 100% ;line-height: 152%; margin-left: 11%;margin-top:  2%;border-collapse: collapse;">
                <el-empty description="暂无评论" v-if="commentList.length == 0"></el-empty>
                <tr v-for="(t, i) in commentList" :key="i" v-if="t.commentState == 0"
                  style="font-family: 楷体;font-size: 18px;text-align: left;color: rgba(0, 0, 0, 0.671); border-bottom: 1px dashed rgba(155, 97, 15, 0.503);margin: 5%;">
                  <img :src="t.photo" v-viewer style="width: 60px;height: 60px; border-radius: 50%;margin-top: 30px;" />
                  {{ t.commentor }}：{{ t.commentContent }}
                  <br>
                  <font style="margin-left: 8%;">{{ new Date(t.commentTime).toLocaleString('zh').replaceAll('/', '-') }}
                  </font>
                  <div v-if="obj.userId >= 0" :key="i" style="float: left;margin-left: 60%;">
                    <el-dialog title="回复评论" :key="i" :visible.sync="loginVisible" style="font-family: 楷体;">
                      <el-form>
                        <el-form-item label="评论" :label-width="formLabelWidth">
                          <el-input v-model="reply" type="textarea" :rows="6"></el-input>
                        </el-form-item>
                      </el-form>
                      <div slot="footer" class="dialog-footer">
                        <el-button @click="loginVisible = false; reply = null">取 消</el-button>
                        <el-button type="primary" @click="addreply()"
                          style="background-color: antiquewhite;border: 0;color: black;border: 1;">评论</el-button>
                      </div>
                    </el-dialog>

                  </div>
                  <div v-if="obj.userId != t.userId && obj.userId >= 0">
                    <el-button size="mini" style="margin-left: 60%;float: left;"
                      @click=" getComment(t.commentId); loginVisible = true;">
                      <i class=" el-icon-chat-square"></i> 回复
                    </el-button>
                    <el-button size="mini" style="margin-left: 5%;float: left;"
                      @click="loginVisible1 = true; getComment(t.commentId)">
                      <i class="el-icon-warning-outline"></i> 举报
                    </el-button>
                  </div>
                  <el-dialog title="举报评论" :visible.sync="loginVisible1" style="font-family: 楷体;">
                    <el-form>
                      <el-form-item label="举报原因" :label-width="formLabelWidth">
                        <!-- <el-input v-model="reply" type="textarea" :rows="6"></el-input> -->
                        <el-radio-group v-model="radio">
                          <el-radio :label="0" :value="0">违反法律法规</el-radio>
                          <el-radio :label="1" :value="1">谣言类不实消息</el-radio>
                          <el-radio :label="2" :value="2">有害社区环境</el-radio>
                          <el-radio :label="3" :value="3">侵犯个人权益</el-radio>
                        </el-radio-group>
                      </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                      <el-button @click="loginVisible1 = false; radio = 0">取 消</el-button>
                      <el-button type="primary" @click="addReportt(radio);"
                        style="background-color: antiquewhite;border: 0;color: black;border: 1;">举报</el-button>
                    </div>
                  </el-dialog>
                  <el-dialog title="举报回复" :visible.sync="loginVisible2" style="font-family: 楷体;">
                    <el-form :model="entity">
                      <el-form-item label="举报原因" :label-width="formLabelWidth">
                        <!-- <el-input v-model="reply" type="textarea" :rows="6"></el-input> -->
                        <el-radio-group v-model="radio">
                          <el-radio :label="0" :value="0">违反法律法规</el-radio>
                          <el-radio :label="1" :value="1">谣言类不实消息</el-radio>
                          <el-radio :label="2" :value="2">有害社区环境</el-radio>
                          <el-radio :label="3" :value="3">侵犯个人权益</el-radio>
                        </el-radio-group>
                      </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                      <el-button @click="loginVisible2 = false; radio = 0">取 消</el-button>
                      <el-button type=" primary" @click="addReport1(radio);"
                        style="background-color: antiquewhite;border: 0;color: black;border: 1;">举报</el-button>
                    </div>
                  </el-dialog>
                  <div v-if="obj.userId == t.userId">
                    <el-button size="mini" style="margin-left: 60%;float: left;"
                      @click=" getComment(t.commentId); loginVisible = true;">
                      <i class=" el-icon-chat-square"></i> 回复
                    </el-button>
                    <el-button size="mini" style="margin-left: 5%;float: left;" @click="deleComment(t.commentId)">
                      <i class="el-icon-delete"></i> 删除
                    </el-button>
                  </div>
                  <!-- <el-input></el-input> -->
                  <table style="margin-left:0%;width: 100% ;line-height: 152%; margin-left: 2%;margin-top:  0%;">
                    <tr v-for="(m, index) in replys" v-if="m.commentId == t.commentId && m.replyType == 0"
                      style="font-family: 楷体;font-size: 18px;text-align: left;margin-left: 300px;text-indent: 5em; ">
                      <!-- <img :src="m.photo" v-viewer
                        style="width: 60px;height: 60px; border-radius: 50%;margin-top: 30px;" /> -->
                      {{ m.userName }} 回复 {{ t.commentor }}：{{ m.replyContent }}
                      <br>
                      <font style="margin-left: 10%;"> {{ new Date(m.replyTime).toLocaleString('zh').replaceAll('/', '-')
                      }}</font>
                      <div v-if="obj.userId != m.userId && obj.userId >= 0">
                        <el-button size="mini" style="margin-left: 71%;float: left;"
                          @click="loginVisible2 = true; getComment1(m.replyId)">
                          <i class="el-icon-warning-outline"></i> 举报
                        </el-button>
                      </div>
                      <div v-if="obj.userId == m.userId">
                        <el-button size="mini" style="margin-left: 71%;float: left;" @click="deleRe(m.replyId)">
                          <i class="el-icon-delete"></i> 删除
                        </el-button>
                      </div>
                    </tr>
                  </table>
                </tr>
              </table>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <p style="text-align: right;" class="last-style" @click="goback"><el-button>返回</el-button></p>
        </el-row>
      </div>
    </div>

  </div>
</template>
<script >
//   import Carousel from "@/components/Carousel.vue";
import { getPaper, PageAllPaper, getOnePaper } from '@/api/paper'
import { getArchivePic, getArchives, getOneArchive, getArchive } from '@/api/archive'
import { getInstitutions, getOnePa } from '@/api/institution'
import { getCommentAll, getOneComment, addcomment, deleComment } from '@/api/comment'
import { getReplyS, deleRe, addreply } from '@/api/reply'
import { addReport, addReport1 } from '@/api/report'
import { Loading } from 'element-ui';
let loading;



export default {
  name: "Resource",
  components: {
    //   Carousel,
  },
  data() {
    const obj = { userId: parseInt(sessionStorage.getItem('SET_USERID')) };
    return {
      oneArchive: [],
      pageNum: 1, // 当前页码
      pageSize: 2, // 每页数据量
      loading: false, // 是否正在加载数据
      obj: { userId: parseInt(sessionStorage.getItem('SET_USERID')) },
      paperList: [],
      commentId: '',
      addreport: [],
      entity: [],
      archiveList: [],
      loginVisible: false,
      loginVisible1: false,
      loginVisible2: false,
      archiveId: '',
      addComment: [],
      replys: [],
      comment: undefined,
      reply: "",
      len: 0,
      institutionList: [],
      typeName1: {
        '0': "手工纸",
        '1': "机械纸",
        '2': "半手工半机械纸",
      },
      radio: null,
      url: '',
      commentList: [],
      previewVisible: false,
      pdfSrc: '', // 将从后台获取到的 PDF 文件地址赋值给这个字段
    }
  },
  created() {
    this.gettingData();
    this.getInstitutions()
    this.getPaper()
    this.getReplyS()
    this.getArchive()
    this.addcomment()
    this.getOneComment()
    // this.addReport1()
    this.startLoading()  // 开启加载
    loading.close(); // 关闭加载
    //   this.downloadFile(resourceContent, resourceType,);

  },
  mounted() {
    // 页面加载就渲染列表
    // this.addReport(this.radio)
    this.getOneArchive(this.archiveId);
    this.getOneComment1(this.archiveId);
    const obj = { userId: parseInt(sessionStorage.getItem('SET_USERID')) };

  },
  watch: { '$route': 'gettingData' },
  methods: {
    startLoading() {  // 使用Element loading-start 方法
      loading = Loading.service({
        lock: true,
        text: '加载中……',
        background: 'rgba(0, 0, 0, 0.7)'
      });
    },
    gettingData() {
      var queryArchiveId = JSON.parse(this.$Base64.decode(this.$route.query.info))
      this.archiveId = queryArchiveId;
    },
    getReport(radio) {
      console.log(radio)
    },
    addReportt(radio) {
      // getComment(t.commentId)
      console.log(this.entity.commentId)
      this.entity.radio = radio
      this.entity.commentId = this.entity.commentId
      console.log(radio); // 打印 addresource 对象
      if (this.radio == "") {
        this.$message({
          type: 'warning',
          message: '原料名称为空，请填写！'
        })
        return false;
      }
      else {
        this.$confirm('您确定要举报该评论吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const formData = new FormData();
          formData.append('reportReasonType', this.entity.radio);
          formData.append('commentId', this.entity.commentId);
          addReport(formData).then(response => {
            if (response.message == "操作成功") {
              // this.$router.push('/management/introManagement')
              this.$message({
                type: 'success',
                message: "举报成功！"
              })
              // location.reload();//自动刷新
              this.loginVisible1 = false
              // this.getProjects(order = 0)
            } else if (response.message == "暂无权限") {
              this.$message({
                type: 'error',
                message: '暂无编辑权限！'
              })
              this.loginVisible = false
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
    addReport1(radio) {
      // getComment(t.commentId)
      console.log(this.entity.commentId)
      this.entity.radio = radio
      this.entity.commentId = this.entity.commentId
      console.log(radio); // 打印 addresource 对象
      setTimeout(() => {
        if (this.radio == "") {
          this.$message({
            type: 'warning',
            message: '举报名称为空，请填写！'
          })
          return false;
        }
        else {
          this.$confirm('您确定要举报该回复吗?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            const formData = new FormData();
            formData.append('reportReasonType', this.entity.radio);
            formData.append('commentId', this.entity.commentId);
            addReport1(formData).then(response => {
              if (response.message == "操作成功") {
                // this.$router.push('/management/introManagement')
                this.$message({
                  type: 'success',
                  message: "举报成功！"
                })
                // location.reload();//自动刷新
                this.loginVisible2 = false
                // this.getProjects(order = 0)
              } else if (response.message == "暂无权限") {
                this.$message({
                  type: 'error',
                  message: '暂无编辑权限！'
                })
                this.loginVisible = false
              } else {
                this.$message({
                  type: 'error',
                  message: response.message
                })
              }
            })
          })
        }
      }, 1000);
    },
    goback() {
      this.$router.go(-1)
    },//返回上一页
    getInstitutions() {
      getInstitutions().then(response => {
        this.institutionList = response.data
      })
    },
    getOneArchive(archiveId) {
      getOneArchive(archiveId).then((response) => {
        this.oneArchive = response.data;
        console.log(this.oneArchive);
      });
    },
    getPaper() {
      getPaper().then(response => {
        this.paperList = response.data
      })
    },
    getOneComment1(archiveId) {
      const obj = { userId: parseInt(sessionStorage.getItem('SET_USERID')) };
      console.log(obj.userId)
      var pageNum = this.pageNum
      var pageSize = this.pageSize
      // var order = 0//排序
      console.log(pageNum)
      console.log(pageSize)
      const formData = new FormData();
      formData.append('archiveId', archiveId);
      // 添加其他表单数据
      // formData.append('institutionId', this.addInstitution.institutionId);
      formData.append('pageSize', pageSize);
      // formData.append('institutionLocation', this.addInstitution.institutionLocation);
      formData.append('pageNum', pageNum);
      console.log(formData)
      // console.log(order)
      getOneComment(formData).then(response => {
        this.commentList = response.data.commentList
        this.len = response.data.total
        console.log(this.len)
        const obj = { userId: parseInt(sessionStorage.getItem('SET_USERID')) };
        console.log(obj.userId)
      })
    },
    _inputShow(i) {
      return this.commentList[i].inputShow
    },
    getReplyS() {
      getReplyS().then(response => {
        this.replys = response.data
      })
    },
    getComment(commentId) {
      console.log(commentId)
      this.entity.commentId = commentId
      console.log(this.entity.commentId)

    },
    getComment1(replyId) {
      console.log(replyId)
      this.entity.commentId = replyId
      console.log(this.entity.commentId)

    },

    getArchive() {
      getArchive().then(response => {
        this.archiveList = response.data
      })
    },
    select1(paperId) {
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
    select2(institutionId) {
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
    addcomment() {
      console.log(this.comment); // 打印 addresource 对象

      if (this.comment == undefined) {
        return false;
      }
      else {
        this.$confirm('您确定要添加该评论吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const formData = new FormData();
          formData.append('file', this.file);
          // 添加其他表单数据
          formData.append('userId', parseInt(sessionStorage.getItem('SET_USERID')));
          formData.append('archiveId', this.archiveId);
          formData.append('commentContent', this.comment);
          addcomment(formData).then(response => {
            if (response.message == "操作成功") {
              // this.$router.push('/management/projectManagement')
              this.$message({
                type: 'success',
                message: "评论成功！"
              })
              setTimeout(() => {
                location.reload();//自动刷新
              }, 1000);
              this.adVisible = false
              // this.getProjects(order = 0)
            } else if (response.message == "暂无权限") {
              this.$message({
                type: 'error',
                message: '暂无编辑权限！'
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
      }
    },
    addreply() {
      // 打印 addresource 对象
      this.entity.commentId = this.entity.commentId
      console.log(this.entity.commentId); // 打印 addresource 对象
      if (this.reply == "") {
        return false;
      }
      else {
        this.$confirm('您确定要添加该回复吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const formData = new FormData();
          // formData.append('file', this.file);
          // 添加其他表单数据
          formData.append('userId', parseInt(sessionStorage.getItem('SET_USERID')));
          formData.append('commentId', this.entity.commentId);
          formData.append('replyContent', this.reply);
          addreply(formData).then(response => {
            if (response.message == "操作成功") {
              // this.$router.push('/management/projectManagement')
              this.$message({
                type: 'success',
                message: "评论成功！"
              })
              location.reload();//自动刷新
              this.loginVisible = false
              // this.getProjects(order = 0)
            } else if (response.message == "暂无权限") {
              this.$message({
                type: 'error',
                message: '暂无编辑权限！'
              })
              this.loginVisible = false
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
    deleComment(commentId) {
      console.log(commentId);//已获取
      this.$confirm('您确定要删除该评论吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const formData = new FormData();
        formData.append('commentId', commentId);
        console.log(commentId);//已获取
        deleComment(formData).then(response => {
          if (response.message == "操作成功") {
            this.$message({
              type: 'success',
              message: "删除成功！"
            })
            setTimeout(function (
            ) { location.reload() }, 100);
            // this.PageAllArchive()
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
    deleRe(replyId) {
      console.log(replyId);//已获取
      this.$confirm('您确定要删除该回复吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const formData = new FormData();
        formData.append('replyId', replyId);
        console.log(replyId);//已获取
        deleRe(formData).then(response => {
          if (response.message == "操作成功") {
            this.$message({
              type: 'success',
              message: "删除成功！"
            })
            location.reload()
            // this.PageAllArchive()
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

  },
}
</script>
<style scoped>
.content {
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;
}

.box1 {
  margin-top: 8px;
  position: relative;
  box-sizing: border-box;
}

.title-box {
  background: #ffffff;
  padding: 20px;
  font-size: 25px;
  font-family: "华文中宋";
}

.location {
  font-size: 25px;
  color: rgb(160, 106, 5);
  font-family: "华文中宋";
  margin-top: 37px;
}

.breadcrumb {
  font-size: 25px;
  color: #080100;
  font-family: "华文中宋";
  margin-top: 40px;
}

.biao {
  /* 构皮纸 */

  position: absolute;
  width: 264px;
  height: 83px;
  opacity: 1;
  font-family: HelloFont ID XuanZhenSong;
  font-size: 36px;
  font-weight: bold;
  line-height: normal;
  display: flex;
  text-align: center;
  letter-spacing: 0.2em;
  top: 14%;
  right: 5%;
  color: #3D3D3D;
}

.head input {
  position: absolute;
  top: 20px;
  left: 80px;
  height: 45px;
  border-radius: 5px;
  outline: none;
  width: 65%;
  font-size: 20px;
  padding: 0 20px;
  border: 2px solid #f8f8f8;

}

.head button {
  position: absolute;
  top: 13px;
  right: 20px;
  width: 120px;
  height: 48px;
  border: 0;
  border-radius: 5px;
  font-size: 20px;
  font-weight: 500;
  color: #fff;
  background-color: rgb(118, 211, 248);
  cursor: pointer;
  letter-spacing: 2px;
}

.head img {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  /* position: absolute; */
  top: 90px;
  left: 40px;
}

.head1 img {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  position: absolute;
  top: 150px;
  left: 170px;
}

.navigation {
  padding: 0 20px;
  height: 40px;
  box-sizing: border-box;
  line-height: 40px;
  display: inline-block;
  list-style: none;
  font-size: 14px;
  font-weight: 500;
  color: #303133;
  position: relative;
  margin-right: 3px;
  margin-left: 54px;
}

.main-area {
  /* margin-left:  0px; */
  border-top: solid rgb(134, 116, 21);
  width: 100%;
}

.jianjie {
  /* 简介： 构皮纸，古法造纸的一种。因构树的广泛分布，以构树皮为原料的构皮纸至今仍散见于很多偏僻的乡村，尤其是贵州、云南和广西三省区。而在贵州的丹寨县石桥村，能工巧匠们为构皮纸注入了新鲜的血液，古老的纸张变得鲜活起来。 使用档案： MG19130201 票据 MG19130201 书信 MG19130201 票据 MG19130201 基建 MG19130201 票据 */

  position: absolute;
  left: 0px;
  top: 269px;
  width: 971px;
  height: 506px;
  opacity: 1;
  font-family: 楷体;
  font-size: 24px;
  font-weight: normal;
  line-height: normal;
  display: flex;
  align-items: left;
  letter-spacing: 0.2em;

}

.el-breadcrumb__item {
  font-size: 20px;
  font-family: "华文中宋";
}

h2 {
  border-bottom: 3px solid #b03632;
}

.title {
  font-size: 24px;
  font-family: "楷体";
  font-weight: bold;
  color: black;

}

.label {
  font-size: 18px;
  font-family: "宋体";
  text-align: start;
  left: 0;
  line-height: 40px;
}

.introductionbox {
  font-size: 14px;
  font-family: "宋体";
  text-align: start;
  left: 0;
}


.last-style {
  font-family: "宋体";
  letter-spacing: 0em;
  margin-top: 8%;
  font-size: 18px;
  text-align: justify;
  color: bisque;
  /* top: 30px; */

}

.loading {
  position: relative;
  width: 100px;
  height: 90px;
  left: 10px;
  top: 10px;

  animation: heart infinite 0.85s linear;
}

.loading:before,
.loading:after {
  position: absolute;
  top: 0;
  left: 30px;
  width: 30px;
  height: 50px;
  content: "";
  transform: rotate(-45deg);
  transform-origin: 0 100%;
  border-radius: 30px 30px 0 0;
  background: #000;
}

.loading:after {
  left: 0;
  transform: rotate(45deg);
  transform-origin: 100% 100%;
}

@keyframes heart {
  0% {
    transform: scale(0.8);
  }

  50% {
    transform: scale(1);
  }

  100% {
    transform: scale(0.8);
  }
}
</style>
