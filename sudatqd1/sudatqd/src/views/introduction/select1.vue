<template>
    <div style="margin-top: 8px;background-color: antiquewhite;">
  
      <div style="margin:0px;top:140px ;background-color: antiquewhite;">
        <div>
          <el-row>
            <el-col :span="3" :offset="1">
              <h3 class="location">当前位置：</h3>
            </el-col>
            <el-col :span="10" style="margin-top: 43px;">
              <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/introduction' }">纸张基因</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/introduction' }"> 纸样库 </el-breadcrumb-item>
                <el-breadcrumb-item> 纸张详情 </el-breadcrumb-item>
              </el-breadcrumb>
            </el-col>
          </el-row><!--导航-->
          <el-row style="height: 10px;">
            <el-col :span="24" class="main-area"  :key="index">
            </el-col>
          </el-row>
          <el-row style="height: auto;">
            <el-col :span="14" style="position: relative;font-family: STKaiti;font-size: 22px;text-align: left;top: 0%;width: 60%;height: auto;margin-left: 10%;margin-left: 5%;background-color: white;margin-top: 5%; margin-bottom: 5%;border-radius: 30px;">
              <div>
                <table :data="onePaper.paperItem" style="margin-left:5%;width: 100% ;line-height: 200%;">
                  <tr>
                    <div style="width: 190px;margin-top: 5%;margin-left: 0%;font-weight: bold;">
                      纸张历史：
                    </div>
                  </tr>
                  <tr>
                    <div 
                      style="width: 90%;margin-left: 5;margin-right: 5%;text-indent: 2em;line-height: 200%;text-align: justify;margin-top: 15px;"
                      white-space:pre>
                      {{ onePaper.paperItem.paperDes }}
                      <br>
                    </div>
                  </tr>
                  <tr>
                    <div style="font-weight: bold;">制作流程：</div>
                  </tr>
                  <tr>
                    <div 
                      style="width: 90%;margin-left: 5;margin-right: 5%;text-indent: 2em;line-height: 200%;text-align: justify;margin-top: 5px;">
                      {{ onePaper.paperItem.process }}
                    </div>
                  </tr>
                  <tr>
                    <div style="font-weight: bold;">纸张加工工艺：</div>
                  </tr>
                  <tr>
                    <div 
                      style="width: 90%;margin-left: 5;margin-right: 5%;text-indent: 2em;line-height: 200%;text-align: justify;margin-top: 5px;">
                      {{ onePaper.paperItem.technology }}
                    </div>
                  </tr>
                  <tr>
                    <div style="margin-top: 30px;font-weight: bold;float: left;">产地：</div>
                    <div style="float: left;margin-top: 30px;"> {{ onePaper.paperItem.location }}</div>
                  </tr>
                  <tr>
                    <div style="margin-top: 30px;font-weight: bold;float: left;">纸张类型：
                    </div>
                    <div style="float: left;margin-top: 30px;">
                      <template>
                        {{ typeName[onePaper.paperItem.paperType] }}
                      </template>
                    </div>
                  </tr>
                  <tr>
                    <div style="margin-left: 0px;float: left;height: auto;font-weight: bold;margin-top: 10px;">
                      相关档案：
                    </div>
                    <div v-for="item in onePaper.archivePaperList" 
                      style="margin-left:10%;width: 100%; vertical-align: top;float: left;margin-top: 10px;">
                      <font
                        style="cursor: pointer;font-family: STKaiti;font-weight:200;color: rgb(205, 134, 63);display:inline-block;vertical-align: top; "
                        @click="select(item.archiveId)">
                        {{item.archiveName }}</font>
                    </div>
                  </tr>
                  <tr>
                    <div style="margin-top: 30px;font-weight: bold;float: left;">浏览量：</div>
                    <div style="float: left;margin-top: 30px;"> {{ onePaper.paperItem.views }}</div>
                  </tr>
                </table>
              </div>
            </el-col>
            <el-col :span="6" style="float: right;margin-top: 10%;margin-right: 5%;">
              <div>
                <table :data="onePaper.paperItem" style="margin-left:5%;width: 100% ;">
                  <tr>
                    <div class="biao">
                      {{ onePaper.paperItem.paperName }}
                    </div>
                  </tr>
                </table>
              </div>
              <div>
                <table v-for="r in onePaper.pictureList" style="margin-left:5%;">
                  <tr>
                    <img :src="r.pictureLocation" width="340px" height="250px" v-viewer style="margin-top: 15%;">
                  </tr>
                </table>
              </div>
            </el-col>
          </el-row>
          <el-row style="height: auto;">
            <el-col :span="20" style="">
              <div>
                <table
                  style="margin-left:0%;width: 100% ;line-height: 152%; margin-left: 15%;margin-top:  3%;border-collapse: collapse;">
                  <tr class="head">
                    <div>
                      <input v-model="comment" type="text" placeholder="请输入评论 . . ."
                        style="cursor: pointer;font-family: STKaiti;" />
                      <form action="" onsubmit="return false">
                        <button type="button"
                          style="cursor: pointer;font-family: STKaiti;margin-right: 70px;background-color:rgba(236, 161, 136, 0.77);color: black;margin-top: 7px;"
                          @click="addcomment">发表评论</button>
                      </form>
                    </div>
                  </tr>
                </table>
                <el-empty description="暂无评论" v-if="commentList.length < 1"></el-empty>
                <table :data="commentList"
                  style="margin-left:0%;width: 100% ; margin-left: 5%;margin-top:  2%;border-collapse: collapse;">
                  <tr v-for="(t, i) in commentList" :key="i" 
                    style="font-family: STKaiti;font-size: 22px;text-align: left;color: rgba(0, 0, 0, 0.671); border-bottom: 1px dashed rgba(155, 97, 15, 0.503);margin: 3%;margin-top: 2%;line-height: 110%;">
                    <div style="line-height:110% ;">
                      {{ t.userName }}：{{ t.replyContent }}
                    </div>
                    <br>
                    <font style="margin-left: 65%;margin-top: 5%;margin-bottom: 1%">{{ new Date(t.replyTime).toLocaleString('zh').replaceAll('/', '-') }}
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
                    <div v-if="obj.userId == t.userId">
                      <el-button size="large" style="margin-left: 65%;float: left;margin-top: 1%;margin-bottom: 1%"
                        @click=" getComment(t.commentId); loginVisible = true;">
                        <i class=" el-icon-chat-square"></i> 回复
                      </el-button>
                      <el-button size="large" style="margin-left: 5%;float: left;margin-top:1%;margin-bottom: 1%" @click="deleComment(t.commentId)">
                        <i class="el-icon-delete"></i> 删除
                      </el-button>
                    </div> 
                    <div  v-if="obj.userId != t.userId">
                      <el-button size="large" style="margin-left: 65%;float: left;margin-top: 1%;margin-bottom: 1%"
                        @click=" getComment(t.commentId); loginVisible = true;">
                        <i class=" el-icon-chat-square"></i> 回复
                      </el-button>
                      <el-button size="large" style="margin-left: 5%;float: left;margin-top: 1%;margin-bottom: 1%"
                            @click="loginVisible1 = true; getComment(t.commentId)">
                            <i class="el-icon-warning-outline"></i> 举报
                          </el-button>
                    </div> 
                    <el-dialog title="回复评论" :key="i" :visible.sync="loginVisible" style="font-family:  STKaiti;">
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
                    <el-dialog title="举报评论" :visible.sync="loginVisible1" style="font-family: STKaiti;">
                      <el-form>
                        <el-form-item label="举报原因" :label-width="formLabelWidth">
                          <el-radio-group v-model="radio">
                            <el-radio :label="0" :value="0">违反法律法规</el-radio>
                            <el-radio :label="1" :value="1">谣言类不实消息</el-radio>
                            <el-radio :label="2" :value="2">有害社区环境</el-radio>
                            <el-radio :label="3" :value="3">侵犯个人权益</el-radio>
                            <el-radio :label="4" :value="4">其他</el-radio>
                          </el-radio-group>
                        </el-form-item>
                      </el-form>
                      举报详情：<el-input v-model="content" type="textarea" :rows="6"></el-input>
                      <div slot="footer" class="dialog-footer">
                        <el-button @click="loginVisible1 = false; radio = 0">取 消</el-button>
                        <el-button type="primary" @click="addReportt(radio);"
                          style="background-color: antiquewhite;border: 0;color: black;border: 1;">举报</el-button>
                      </div>
                    </el-dialog>
  
                    
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
  import {  getOnePaper } from '@/api/paper'
  import { getCommentAll, getCommentReplyList, addcomment, deleComment } from '@/api/comment'
  import { addReport, addReport1 } from '@/api/report'
  import { getReplyS, deleRe, addreply } from '@/api/reply'
  import { Loading } from 'element-ui';
  let loading;
  
  export default {
    name: "Resource",
    components: {
      //   Carousel,
    },
    data() {
      return {
        onePaper: [],
        commentList:[],
        loginVisible: false,
        obj: { userId: parseInt(sessionStorage.getItem('SET_USERID')) },
        paperId: '',
        typeName: {
          '0': "手工纸",
          '1': "机械纸",
          '2': "其他",
        },
        reply: "",
        commentId: '',
        content:"",
        loginVisible1: false,
        addreport: [],
        entity: [],
        comment: undefined,
        radio: null,
        commentList:[],
        previewVisible: false,
        pdfSrc: '', // 将从后台获取到的 PDF 文件地址赋值给这个字段
      }
    },
    created() {
      this.gettingData();
      this.getCommentReplyList()
      this.startLoading()  // 开启加载
      loading.close(); // 关闭加载
    },
    mounted() {
      // 页面加载就渲染列表
      this.getOnePaper(this.paperId);
    },
    watch: { '$route': 'gettingData' },
    methods: {
      gettingData() {
        var queryPaperId = JSON.parse(this.$Base64.decode(this.$route.query.info))
        this.paperId = queryPaperId;
      },
      goback() {
        this.$router.go(-1)
      },//返回上一页
  
      startLoading() {  // 使用Element loading-start 方法
        loading = Loading.service({
          lock: true,
          text: '加载中……',
          background: 'rgba(0, 0, 0, 0.7)'
        });
      },
      addcomment() {
        console.log(this.comment); // 打印 addresource 对象
        if (this.comment == undefined) {
          return false;
        }
        let check = sessionStorage.getItem('SET_TOKEN')
        console.log(check)
        if (check === null || check === '') {
          alert('请先登录！')
          this.$router.push({
            path: '/login/index',
          })
          return
        }
        else {
          this.$confirm('您确定要添加该评论吗?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            var id = this.paperId
            var type =1
            var content = this.comment
            addcomment({id,type,content}).then(response => {
              if (response.message == "您的评论已成功发布！") {
                // this.$router.push('/management/projectManagement')
                this.$message({
                  type: 'success',
                  message: "您的评论已成功发布！"
                })
                this.comment = '';
                this.$forceUpdate();   // 强制更新
                this.getOnePaper(this.paperId)
              } else if (response.message == "您的内容中包含敏感词，请注意，我们不接受含有攻击性、侮辱性或不当内容的句子") {
                this.$message({
                  type: 'error',
                  message: '您的内容中包含敏感词，请注意，我们不接受含有攻击性、侮辱性或不当内容的句子'
                })
                this.$forceUpdate();   // 强制更新
                this.adVisible = false
              } else {
                this.$message({
                  type: 'error',
                  message: response.message
                })
                this.$forceUpdate();   // 强制更新
              }
            })
          })
        }
      },
      getComment(commentId) {
        console.log(commentId)
        this.entity.commentId = commentId
        console.log(this.entity.commentId)
      },
  
      getCommentReplyList() {
        var queryPaperId = JSON.parse(this.$Base64.decode(this.$route.query.info))
        this.paperId = queryPaperId;
        var id = this.paperId
        var status = 1
        getCommentReplyList({id,status}).then((response) => {
          this.commentList = response.data;
          console.log(this.onePaper);
        });
      },
      select(archiveId) {
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
      addReportt(radio) {
        // getComment(t.commentId)
        console.log(this.entity.commentId)
        this.entity.radio = radio
        this.entity.commentId = this.entity.commentId
        console.log(radio); // 打印 addresource 对象
        if (this.radio == null) {
          this.$message({
            type: 'warning',
            message: '请选择举报原因'
          })
          return false;
        }
        else {
          this.$confirm('您确定要举报该评论吗?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            var id = this.entity.commentId
            var type =this.radio
            var content = this.content
            var contentType = 0
            addReport({id,type,content,contentType}).then(response => {
              if (response.message == "您的投诉已提交至管理员处，请耐心等待审核！") {
                this.$message({
                  type: 'success',
                  message: "您的投诉已提交至管理员处，请耐心等待审核！"
                })
                this.loginVisible1 = false
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
      getOnePaper(paperId) {
        getOnePaper(paperId).then((response) => {
          this.onePaper = response.data;
          console.log(this.onePaper);
        });
      },
      deleComment(commentId) {
        console.log(commentId);//已获取
        this.$confirm('您确定要删除该评论吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var queryPaperId = JSON.parse(this.$Base64.decode(this.$route.query.info))
        this.paperId = queryPaperId;
          deleComment(commentId).then(response => {
            if (response.message == "已成功删除") {
              this.$message({
                type: 'success',
                message: "已成功删除!"
              })
              this.$forceUpdate();   // 强制更新
              this.getOnePaper(this.paperId)
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
            var id = this.entity.commentId
            var type =1
            var content = this.reply
            addreply({id,type,content}).then(response => {
              if (response.message == "您的回复已成功发布！") {
                // this.$router.push('/management/projectManagement')
                this.$message({
                  type: 'success',
                  message: "您的回复已成功发布！"
                })
                this.$forceUpdate();   // 强制更新
                // this.getReplyS()  // 强制更新
                this.loginVisible = false
                this.reply = '';
              } else if (response.message == "您的内容中包含敏感词，请注意，我们不接受含有攻击性、侮辱性或不当内容的句子") {
                this.$message({
                  type: 'error',
                  message: '您的内容中包含敏感词，请注意，我们不接受含有攻击性、侮辱性或不当内容的句子'
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
  
  .sousuo {
    bottom: 12px;
    width: 100px;
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
    font-family: "STKaiti";
  }
  
  .location {
    font-size: 25px;
    color: rgb(160, 106, 5);
    font-family: "STKaiti";
    margin-top: 37px;
  }
  
  .breadcrumb {
    font-size: 25px;
    color: #080100;
    font-family: "STKaiti";
    margin-top: 40px;
  }
  
  .biao {
    /* 构皮纸 */
  
    /* position: flex; */
    width: 294px;
    height: 83px;
    opacity: 1;
    font-family: STKaiti;
    font-size: 36px;
    font-weight: bold;
    line-height: normal;
    /* display: flex; */
    /* text-align: center; */
    letter-spacing: 0.2em;
    top: 20%;
    right: 4%;
    /* left: 34%; */
    color: #3D3D3D;
  
  
  
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
    font-family: STKaiti;
    font-size: 24px;
    font-weight: normal;
    line-height: normal;
    display: flex;
    align-items: left;
    letter-spacing: 0.2em;
  
  }
  
  .el-breadcrumb__item {
    font-size: 20px;
    font-family: "STKaiti";
  }
  
  h2 {
    border-bottom: 3px solid #b03632;
  }
  
  .title {
    font-size: 24px;
    font-family: "STKaiti";
    font-weight: bold;
    color: black;
  
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
  .label {
    font-size: 18px;
    font-family: "STKaiti";
    text-align: start;
    left: 0;
    line-height: 40px;
  }
  
  .introductionbox {
    font-size: 14px;
    font-family: "STKaiti";
    text-align: start;
    left: 0;
  }
  
  
  .last-style {
    font-family: "STKaiti";
    letter-spacing: 0em;
    margin-top: 8%;
    font-size: 18px;
    text-align: justify;
    color: bisque;
  
  }
  </style>
        