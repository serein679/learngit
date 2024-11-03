<!-- 三级及以上评论 -->
<template>
    <div class="sub-reply-container" v-if="childComments !=null" style="margin-left: 0%;" >
      <div class="sub-reply" v-for="(child, index) in childComments" :key="index">
        <!-- 渲染内容 -->
        <div class="listbox-top-user" style="font-family: STKaiti;font-size: 22px;text-align: left;color: rgba(0, 0, 0, 0.671); margin: 3%;margin-top: 2%;line-height: 110%;">
          <div  v-if="isReloadData">
            <p>
              <span style="font-family: STKaiti;font-size: 22px;color: rgba(0, 0, 0, 0.671); margin: 3%;margin-top: 2%;line-height: 110%;">
                <font style="font-weight: bold;">   {{ child.userName }}</font> 回复
                        <span><font style="font-weight: bold;"> {{ parentName }}</font></span>：{{child.replyContent }}</span>
            </p>
          </div>
          <!-- <el-avatar :size="30" :src="child.userImg" /> -->
            <div class="listbox-bottom">
            <span style="margin-left: 76%;margin-top: 5%;margin-bottom: 1%">发布时间：{{ new Date(child.replyTime).toLocaleString('zh').replaceAll('/', '-') }}</span>

          </div>
                <div v-if="obj.userId == child.userId">
                   
                    <el-button size="large" style="margin-left: 95%;float: left;margin-top:1%;margin-bottom: 1%" @click="deleReply(child.replyId);refresh">
                      <i class="el-icon-delete"></i> 删除
                    </el-button>
                  </div> 
                  <div  v-if="obj.userId != child.userId">
                 
                    <el-button size="large" style="margin-left: 95%;float: left;margin-top: 1%;margin-bottom: 1%"
                          @click="loginVisible1 = true; getReply(child.replyId)">
                          <i class="el-icon-warning-outline"></i> 举报
                        </el-button>
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
                        <el-button type="primary" @click="addreply();refreshbtn"
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
  
        <!-- 递归地渲染子评论的子评论：调用自己 -->
        <ChildComment
          :childComments="child.children"
          :parentName="child.userName"
          
        />
      </div>
    </div>
  </template>
  <script >
  import { getReplyS, deleRe, addreply } from '@/api/reply'
  import { addReport, addReport1 } from '@/api/report'
  import { getCommentAll, getCommentReplyList, addcomment, deleComment } from '@/api/comment'
  export default {
    inject: ['reload'],
  name: 'ChildComment',
  props: {
    childComments: {
      type: Array,
      default: []
    },
    parentName: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      obj: {
        userId: parseInt(sessionStorage.getItem('SET_USERID'))
      },
      loginVisible: false,
      loginVisible1: false,
      reply: "",
      commentList:[],
      entity:[],
      radio:'',
      content:'',
      refresh:true,
      isReloadData: true,
      childComments:[]
    };
  },
  created() {
    this.getCommentReplyList()
  
  },
  watch:{
		props(){
			this.refresh = false;
			this.$nextTick(() => {
		        // 重新渲染组件
		    	this.refresh = true;
		    });
		}
	},
  methods: {
    getReply(replyId) {
      console.log(replyId)
      this.entity.replyId = replyId
      console.log(this.entity.replyId)
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
    addReportt(radio) {
      // getComment(t.commentId)
      console.log(this.entity.replyId)
      this.entity.radio = radio
      this.entity.replyId = this.entity.replyId
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
          var id = this.entity.replyId
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
    getCommentReplyList() {
      var queryPaperId = JSON.parse(this.$Base64.decode(this.$route.query.info))
      this.paperId = queryPaperId;
      var id = this.paperId
      var status = 1
      getCommentReplyList({id,status}).then((response) => {
        this.commentsList = response.data;
        console.log(this.commentsList);
      });
    }, 
    reload () {
      this.isReloadData = false;
      this.$nextTick(() => {
        this.isReloadData = true;
      })
    }
    // handleReply 方法保持不变
  }
};
</script>