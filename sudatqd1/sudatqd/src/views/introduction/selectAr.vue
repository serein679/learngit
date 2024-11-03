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
          <el-col :span="24" class="main-area"  :key="index">
          </el-col>
        </el-row>
        <el-row style="height: auto;">
          <el-col :span="14"
            style="position: relative;font-family: STKaiti;font-size: 22px;text-align: left;top: 0%;height: auto;margin-left: 10%;margin-left: 5%;margin-top: 5%; margin-bottom: 2%;border-radius: 30px;">
            <div style="position: relative;font-family: STKaiti;font-size: 22px;text-align: left;top: 0%;height: auto;margin-left: 10%;margin-left: 5%;background-color: white;margin-top: 0%; margin-bottom: 2%;border-radius: 30px;">
              <table  :data=oneArchive style="margin-left:5%;width: 100% ;line-height: 200%;">
                <tr>
                  <div style="width: 90%;margin-top: 2%;margin-left: 0%;font-weight: bold;">
                    题名：{{ oneArchive.recordingItem.archiveName }}
                  </div>
                </tr>
                <tr>
                  <div style="width: 90%;margin-top: 2%;margin-left: 0%;font-weight: bold;">
                    编号：{{ oneArchive.recordingItem.bianHao }}
                  </div>
                </tr>
                <tr>
                  <div style="width: 90%;margin-top: 2%;margin-left: 0%;font-weight: bold;">
                    诞生时间：{{ oneArchive.recordingItem.time }}
                  </div>
                </tr>
                <tr>
                  <div style="width: 90%;margin-top: 2%;margin-left: 0%;font-weight: bold;">
                    责任者：{{ oneArchive.recordingItem.author }}
                  </div>
                </tr>
                <tr>
                  <div style="width: 90%;margin-top: 2%;margin-left: 0%;font-weight: bold;">
                    语种：{{ oneArchive.recordingItem.language }}
                  </div>
                </tr>
                <tr>
                  <div style="width: 90%;margin-top: 2%;margin-left: 0%;font-weight: bold;">
                    分类号：{{ oneArchive.recordingItem.classification }}
                  </div>
                </tr>
                <tr>
                  <div style="width: 90%;margin-top: 2%;margin-left: 0%;font-weight: bold;">
                    档号：{{ oneArchive.recordingItem.reference }}
                  </div>
                </tr>
              </table>
            </div>
            <div style="position: relative;font-family: STKaiti;font-size: 22px;text-align: left;top: 0%;height: auto;margin-left: 10%;margin-left: 5%;background-color: white;margin-top: 0%; margin-bottom: 2%;border-radius: 30px;">
              <table  :data=oneArchive style="margin-left:5%;width: 100% ;line-height: 200%;">
                <tr>
                  <div style="width: 90px;margin-top: 2%;margin-left: 0%;font-weight: bold;">
                    简介：
                  </div>
                </tr>
                <tr>
                  <div :data=" oneArchive.archiveItem"
                    style="width: 90%;margin-top: 9px;margin-left: 5;margin-right: 5%;text-indent: 2em;line-height: 200%;text-align: justify;">
                    {{ oneArchive.archiveItem.archiveContent }}
                  </div>
                </tr>
                <tr>
                  <div style="margin-top: 15px;">
                    <font style="font-weight: bold;">信息采集时间：</font> {{ new
                      Date(oneArchive.archivePaperItem.creationTime).toLocaleDateString('zh').replaceAll('/', '-') }}
                  </div>
                </tr>
                <tr  style="margin-left: 0px;">
                  <div style="margin-left: 0px;float: left; font-weight: bold;margin-top: 10px;">
                    档案用纸情况：
                  </div>
                  <div style="margin-left: 40px;width: 60%; vertical-align: top;float: left;margin-top: 10px;">
                    <font
                      style="cursor: pointer;font-family: STKaiti;font-weight:200;color: rgb(205, 134, 63);display:inline-block;vertical-align: top; "
                      @click="select1(oneArchive.archivePaperItem.paperId)">
                      {{ oneArchive.archivePaperItem.paperName }}</font>
                  </div>
                </tr> 
                <tr>
                  <div style="margin-left: 0px;float: left;font-weight: bold;margin-top: 10px;">
                    目前收录机构：
                  </div>
                  <div v-for="item in institutionList" v-if="item.institutionId == oneArchive.archiveItem.institutionId"
                    style="margin-left: 40px;width: 60%; vertical-align: top;float: left;margin-top: 10px;">
                    <font
                      style="cursor: pointer;font-family: STKaiti;font-weight:200;color: rgb(205, 134, 63);display:inline-block;vertical-align: top; "
                      @click="select2(item.institutionId)">
                      {{ item.institutionName }}</font>
                  </div>
                </tr>
              </table>
            </div  >
         
            <div style="position: relative;font-family: STKaiti;font-size: 22px;text-align: left;top: 0%;height: auto;margin-left: 10%;margin-left: 5%;background-color: white;margin-top: 0%; margin-bottom: 2%;border-radius: 30px;">
              <table  :data=oneArchive style="margin-left:5%;width: 100% ;line-height: 200%;">
                <tr>
                  <td>
                    <div style="width: 90%;margin-top: 2%;margin-left: 0%;">
                    <font style="font-weight: bold;">档案用纸：{{ oneArchive.archivePaperItem.paperName }}</font>
                  </div>
                  </td>
                  <td>
                    <div style="width: 90%;margin-top: 2%;margin-left: 0%;">
                    <font style="font-weight: bold;">档案纸张ph值：</font>{{ oneArchive.archivePaperItem.ph }}
                  </div>
                  </td>
                </tr>
                <tr>
                  <td>
                    <div style="width: 90%;margin-top: 2%;margin-left: 0%;">
                    <font style="font-weight: bold;">档案纸张尺寸：</font>{{ oneArchive.archivePaperItem.size }}
                  </div>
                  </td>
                   <td>
                    <div style="width: 90%;margin-top: 2%;margin-left: 0%;">
                    <font style="font-weight: bold;">档案纸张厚度：</font>{{ oneArchive.archivePaperItem.thickness }}
                  </div>
                   </td>
                  
                </tr>
                <tr>
                  <td>
                    <div style="width: 90%;margin-top: 2%;margin-left: 0%;">
                    <font style="font-weight: bold;">档案纸张白度：</font>{{ oneArchive.archivePaperItem.whiteness }}
                  </div>
                  </td>
                  <td>
                    <div style="width: 90%;margin-top: 2%;margin-left: 0%;">
                    <font style="font-weight: bold;">档案纸张色度：</font>{{ oneArchive.archivePaperItem.color }}
                  </div>
                  </td>
                </tr>
                <tr>
                  <td>
                    <div style="width: 90%;margin-top: 2%;margin-left: 0%;">
                    <font style="font-weight: bold;">档案纸张横纹数量：</font>{{ oneArchive.archivePaperItem.band }}
                  </div>
                  </td>
                  <td>
                    <div style="width: 90%;margin-top: 2%;margin-left: 0%;">
                    <font style="font-weight: bold;">档案纸张纵纹间距：</font>{{ oneArchive.archivePaperItem.zong }}
                  </div>
                  </td>
                </tr>
                <tr>
                  <td>
                    <div style="width: 90%;margin-top: 2%;margin-left: 0%;">
                    <font style="font-weight: bold;">档案数量：</font>{{ oneArchive.archivePaperItem.shuLiang }}
                  </div>
                  </td>
                  <td>
                    <div style="width: 90%;margin-top: 2%;margin-left: 0%;">
                    <font style="font-weight: bold;">档案用纸层数：</font>{{ oneArchive.archivePaperItem.numbers }}
                  </div>
                  </td>
                </tr>
                <tr>
                  <td>
                    <div style="width: 90%;margin-top: 2%;margin-left: 0%;" v-if="oneArchive.archivePaperItem.distribution==0">
                    <font style="font-weight: bold;">档案用纸纤维分布：</font>均匀
                  </div>
                  
                    <div style="width: 90%;margin-top: 2%;margin-left: 0%;" v-if="oneArchive.archivePaperItem.distribution==1">
                    <font style="font-weight: bold;">档案用纸纤维分布：</font>不均匀
                  </div>
                  </td>
                  <td>
                    <div style="width: 90%;margin-top: 2%;margin-left: 0%;">
                    <font style="font-weight: bold;">档案纸张帘纹情况：</font>{{ oneArchive.archivePaperItem.lianWen }}
                  </div>
                  </td>
                </tr>
                
                
                <tr>
                  <td>
                    <div style="width: 90%;margin-top: 2%;margin-left: 0%;">
                    <font style="font-weight: bold;">档案纸张含水量：</font>{{ oneArchive.archivePaperItem.beatDeg }}
                  </div>
                  </td>
                  <td>
                    <div style="width: 90%;margin-top: 2%;margin-left: 0%;">
                    <font style="font-weight: bold;">档案纸张破损程度：</font>{{ oneArchive.archivePaperItem.degFiber }}
                  </div>
                  </td>
                  
                 
                  
                </tr>
              </table>
            </div>
            <div style="position: relative;font-family: STKaiti;font-size: 22px;text-align: left;top: 0%;height: auto;margin-left: 10%;margin-left: 5%;background-color: white;margin-top: 0%; margin-bottom: 2%;border-radius: 30px;">
              <table  :data=oneArchive style="margin-left:5%;width: 100% ;line-height: 200%;">
                <tr>
                  <div style="width: 90%;margin-top: 2%;margin-left: 0%;" >
                    <font style="font-weight: bold;">档案纸张成分：</font>
                    <font style="cursor: pointer;font-family: STKaiti;font-weight:200;color: rgb(205, 134, 63);display:inline-block;vertical-align: top; "
                     @click="select4(oneArchive.archiveComponentItem.componentId)">{{ oneArchive.archiveComponentItem.componentName}}</font>
                   
                  </div>
                </tr>
                <tr>
                  <div style="width: 90%;margin-top: 2%;margin-left: 0%;">
                    <font style="font-weight: bold;">档案纸张成分类型：</font>{{ typeName[oneArchive.archiveComponentItem.componentType] }}
                  </div>
                </tr>
                <tr>
                  <div style="width: 90%;margin-top: 2%;margin-left: 0%;">
                    <font style="font-weight: bold;">档案纸张成分描述：</font>{{ oneArchive.archiveComponentItem.description }}
                  </div>
                </tr>
              </table>
            </div>
          </el-col>
          <el-col :span="6" style="float: right;   margin-top: 5%;margin-right: 5%; ">
            <div>
              <table :data="oneArchive.archiveItem" style="margin-left:5%;width: 100% ;">
                <tr>
                  <div class="biao">
                    {{ oneArchive.archiveItem.archiveName }}
                  </div>
                </tr>
                <tr v-for="m in oneArchive.pictureList" >
                  <img :src="m.pictureLocation" width="350px" style="margin-top: 15%;"  >
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
              <el-empty description="暂无评论" v-if="oneArchive.commentList.length < 1"></el-empty>
              <table :data="oneArchive.commentList"
                style="margin-left:0%;width: 100% ; margin-left: 5%;margin-top:  2%;border-collapse: collapse;">
                <tr v-for="(t, i) in oneArchive.commentList" :key="i" 
                  style="font-family: STKaiti;font-size: 22px;text-align: left;color: rgba(0, 0, 0, 0.671); border-bottom: 1px dashed rgba(155, 97, 15, 0.503);margin: 3%;margin-top: 2%;line-height: 110%;">
                  <div style="line-height:44px ;">
                    <el-avatar :size="44" :src="t.picture" />
                    <font style="font-weight: bold;">{{ t.userName }}</font>：{{ t.commentContent }}
                  </div>
                  <br>
                  <font style="margin-left: 75%;margin-top: 5%;margin-bottom: 1%">发布时间：{{ new Date(t.commentTime).toLocaleString('zh').replaceAll('/', '-') }}
                  </font>
                  <div v-if="obj.userId == t.userId">
                    <el-button size="large" style="margin-left: 80%;float: left;margin-top: 1%;margin-bottom: 1%"
                      @click=" getComment(t.commentId); loginVisible = true;">
                      <i class=" el-icon-chat-square"></i> 回复
                    </el-button>
                    <el-button size="large" style="margin-left: 5%;float: left;margin-top:1%;margin-bottom: 1%" @click="deleComment(t.commentId)">
                      <i class="el-icon-delete"></i> 删除
                    </el-button>
                  </div> 
                  <div  v-if="obj.userId != t.userId">
                    <el-button size="large" style="margin-left: 80%;float: left;margin-top: 1%;margin-bottom: 1%"
                      @click=" getComment(t.commentId); loginVisible = true;">
                      <i class=" el-icon-chat-square"></i> 回复
                    </el-button>
                    <el-button size="large" style="margin-left: 5%;float: left;margin-top: 1%;margin-bottom: 1%"
                          @click="loginVisible1 = true; getComment(t.commentId)">
                          <i class="el-icon-warning-outline"></i> 举报
                        </el-button>
                  </div> 
                  <br>
                  <div class="listbox" v-for="(item, index1) in commentsList" v-if="item.replyyId==t.commentId" :key="index1">
          <!-- 顶级留言：这没什么好说的，就直接展示遍历的结果 -->
            <div class="listbox-top-user" style="font-family: STKaiti;font-size: 22px;color: rgba(0, 0, 0, 0.671); margin: 3%;margin-top: 2%;line-height: 110%;">
              <p>
                <span style="font-family: STKaiti;font-size: 22px;text-align: left;color: rgba(0, 0, 0, 0.671); margin: 3%;margin-top: 2%;line-height: 110%;">
                  <font style="font-weight: bold;">{{ item.userName }} </font>回复<font style="font-weight: bold;"> {{ t.userName }}</font>：{{item.replyContent }}</span>
              </p>
              <span style="margin-left: 77%;margin-top: 1%;margin-bottom: 1%">发布时间：{{ new Date(item.replyTime).toLocaleString('zh').replaceAll('/', '-') }}</span>
              <div class="listbox-bottom">
                <div v-if="obj.userId == item.userId">
                    <el-button size="large" style="margin-left: 82%;float: left;margin-top: 1%;margin-bottom: 1%"
                      @click=" getReply(item.replyId); loginVisible2 = true;">
                      <i class=" el-icon-chat-square"></i> 回复
                    </el-button>
                    <el-button size="large" style="margin-left: 5%;float: left;margin-top:1%;margin-bottom: 1%" @click="deleReply(item.replyId)">
                      <i class="el-icon-delete"></i> 删除
                    </el-button>
                  </div> 
                  <div  v-if="obj.userId != item.userId">
                    <el-button size="large" style="margin-left: 82%;float: left;margin-top: 1%;margin-bottom: 1%"
                      @click=" getReply(item.replyId); loginVisible2 = true;">
                      <i class=" el-icon-chat-square"></i> 回复
                    </el-button>
                    <el-button size="large" style="margin-left: 5%;float: left;margin-top: 1%;margin-bottom: 1%"
                          @click="loginVisible3 = true; getReply(item.replyId)">
                          <i class="el-icon-warning-outline"></i> 举报
                        </el-button>
                  </div> 
              </div>
              <div v-if="item.children && item.children.length">
                <ChildComment
                  :childComments="item.children"
                  :parentName="item.userName" 
                  style="margin-left: 65px"
                />
          </div>
            </div>
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
                  <el-dialog title="回复评论" :key="i" :visible.sync="loginVisible2" style="font-family:  STKaiti;">
                      <el-form>
                        <el-form-item label="评论" :label-width="formLabelWidth">
                          <el-input v-model="reply1" type="textarea" :rows="6"></el-input>
                        </el-form-item>
                      </el-form>
                      <div slot="footer" class="dialog-footer">
                        <el-button @click="loginVisible2 = false; reply1 = null">取 消</el-button>
                        <el-button type="primary" @click="addreply1()"
                          style="background-color: antiquewhite;border: 0;color: black;border: 1;">评论</el-button>
                      </div>
                    </el-dialog>
                  <el-dialog title="举报评论" :visible.sync="loginVisible3" style="font-family: STKaiti;">
                    <el-form>
                      <el-form-item label="举报原因" :label-width="formLabelWidth">
                        <el-radio-group v-model="radio1">
                          <el-radio :label="0" :value="0">违反法律法规</el-radio>
                          <el-radio :label="1" :value="1">谣言类不实消息</el-radio>
                          <el-radio :label="2" :value="2">有害社区环境</el-radio>
                          <el-radio :label="3" :value="3">侵犯个人权益</el-radio>
                          <el-radio :label="4" :value="4">其他</el-radio>
                        </el-radio-group>
                      </el-form-item>
                    </el-form>
                    举报详情：<el-input v-model="content1" type="textarea" :rows="6"></el-input>
                    <div slot="footer" class="dialog-footer">
                      <el-button @click="loginVisible3 = false; radio1 = 0">取 消</el-button>
                      <el-button type="primary" @click="addReportt1(radio1);"
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
import { getPaper, PageAllPaper, getOnePaper } from '@/api/paper'
import { getArchivePic, getArchives, getOneArchive, getArchive } from '@/api/archive'
import { getInstitutions, getOnePa } from '@/api/institution'
import { getCommentAll, getCommentReplyList, addcomment, deleComment } from '@/api/comment'
import { getReplyS, deleRe, addreply } from '@/api/reply'
import { addReport, addReport1 } from '@/api/report'
import ChildComment from  "@/components/front/ChildComment.vue";
import SecondComment from "@/components/front/SecondComment.vue";
import { Loading } from 'element-ui';
let loading;

export default {
  name: "Resource",
  components: {
    ChildComment
  },
  data() {
   
    return {
      oneArchive: [],
      obj: { userId: parseInt(sessionStorage.getItem('SET_USERID')) },
      paperList: [],
      commentId: '',
      addreport: [],
      entity: [],
      componentId:'',
      archiveList: [],
      loginVisible: false,
      loginVisible1: false,
      loginVisible2: false,
      loginVisible3: false,
      archiveId: '',
      content:'',
      typeName: {
                '0': "填料成分",
                '1': "染色成分",
                '2': "施胶成分",
                '3': "涂布成分",
            },
      content1:'',
      replys: [],
      formLabelWidth: 200,
      comment: undefined,
      reply: "",
      institutionList: [],
      typeName1: {
        '0': "手工纸",
        '1': "机械纸",
        '2': "半手工半机械纸",
      },
      radio: null,
      radio1: null,
      commentsList:[],
      reply:'',
      reply1:'',
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
    this.getCommentReplyList()
    this.startLoading()  // 开启加载
    loading.close(); // 关闭加载
  },
  mounted() {
    // 页面加载就渲染列表
    this.getOneArchive(this.archiveId);
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
    addReportt1(radio1) {
      // getComment(t.commentId)
      console.log(this.entity.replyId)
      this.entity.radio = radio1
      this.entity.replyId = this.entity.replyId
      console.log(radio1); // 打印 addresource 对象
      if (this.radio1 == null) {
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
          var id = this.entity.replyId
          var type =this.radio1
          var content = this.content1
          var contentType = 1
          addReport({id,type,content,contentType}).then(response => {
            if (response.message == "您的投诉已提交至管理员处，请耐心等待审核！") {
              this.$message({
                type: 'success',
                message: "您的投诉已提交至管理员处，请耐心等待审核！"
              })
              this.loginVisible3 = false
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
    getCommentReplyList() {
      var queryArchiveId = JSON.parse(this.$Base64.decode(this.$route.query.info))
      this.archiveId = queryArchiveId;
      var id = this.archiveId
      var status = 3
      getCommentReplyList({id,status}).then((response) => {
        this.commentsList = response.data;
        console.log(this.commentsList);
      });
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
    getOneComment(archiveId) {
      const obj = { userId: parseInt(sessionStorage.getItem('SET_USERID')) };
      console.log(obj.userId)
      getOneComment(archiveId).then(response => {
        this.commentList = response.data
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
    select4(componentId) {
      if (componentId !== '') {
        console.log(componentId)
        this.$router.push({
          path: './selectXin',
          query: {
            info: this.$Base64.encode(componentId)
          }
        })
      }
    },
    addcomment() {
      console.log(this.comment); // 打印 addresource 对象
      if (this.comment == undefined) {
        this.$message({
                type: 'error',
                message: "请输入评论"
              })
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
          var id = this.archiveId
          var type =3
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
              this.getOneArchive(this.archiveId)
            } else if (response.message == "您的内容中包含敏感词，请注意，我们不接受含有攻击性、侮辱性或不当内容的句子") {
              this.$message({
                type: 'error',
                message: '您的内容中包含敏感词，请注意，我们不接受含有攻击性、侮辱性或不当内容的句子'
              })
              this.loginVisible = false
              this.comment = '';
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
    addreply() {
      // 打印 addresource 对象
      this.entity.commentId = this.entity.commentId
      if (this.reply == "") {
        this.$message({
                type: 'error',
                message: "请输入回复"
              })
      }
      else {
        this.$confirm('您确定要添加该回复吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var id = this.entity.commentId
          var type =0
          var content = this.reply
          addreply({id,type,content}).then(response => {
            if (response.message == "您的回复已成功发布！") {
              // this.$router.push('/management/projectManagement')
              this.$message({
                type: 'success',
                message: "您的回复已成功发布！"
              })
              // this.$forceUpdate();   // 强制更新
              this.reply == ""
              this.loginVisible = false
             this.getCommentReplyList()
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
    addreply1() {
      // 打印 addresource 对象
      this.entity.replyId = this.entity.replyId
      console.log(this.entity.replyId); // 打印 addresource 对象
      if (this.reply1 == "") {
        return false;
      }
      else {
        this.$confirm('您确定要添加该回复吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var id = this.entity.replyId
          var type =1
          var content = this.reply1
          addreply({id,type,content}).then(response => {
            if (response.message == "您的回复已成功发布！") {
              // this.$router.push('/management/projectManagement')
              this.$message({
                type: 'success',
                message: "您的回复已成功发布！"
              })
              this.$forceUpdate();   // 强制更新
              // this.getReplyS()  // 强制更新
              this.reply1 == ""
              this.loginVisible2 = false
             this.getCommentReplyList()
            } else if (response.message == "您的内容中包含敏感词，请注意，我们不接受含有攻击性、侮辱性或不当内容的句子") {
              this.$message({
                type: 'error',
                message: '您的内容中包含敏感词，请注意，我们不接受含有攻击性、侮辱性或不当内容的句子'
              })
              this.loginVisible2 = false
              
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
    getReply(replyId) {
      console.log(replyId)
      this.entity.replyId = replyId
      console.log(this.entity.replyId)
    },
    deleComment(commentId) {
      console.log(commentId);//已获取
      this.$confirm('您确定要删除该评论吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var queryArchiveId = JSON.parse(this.$Base64.decode(this.$route.query.info))
      this.archiveId = queryArchiveId;
        deleComment(commentId).then(response => {
          if (response.message == "已成功删除") {
            this.$message({
              type: 'success',
              message: "已成功删除!"
            })
            this.$forceUpdate();   // 强制更新
            this.getOneArchive(this.archiveId)
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
    deleReply(replyId) {
      console.log(replyId);//已获取
      this.$confirm('您确定要删除该评论吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
 
        deleRe(replyId).then(response => {
          if (response.message == "已成功删除") {
            this.$message({
              type: 'success',
              message: "已成功删除!"
            })
            this.$forceUpdate();   // 强制更新
            this.getCommentReplyList()
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

  /* position: absolute; */
  /* width: 300px; */
  height: 83px;
  opacity: 1;
  font-family: STKaiti;
  font-size: 36px;
  font-weight: bold;
  line-height: normal;
  /* display: flex; */
  /* text-align: center; */
  letter-spacing: 0.2em;
  top: 10%;
  margin-left: 2%;
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
