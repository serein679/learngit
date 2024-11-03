<template slot-scope="{ row }">
    <div style="margin-top: 8px;background-color: antiquewhite; padding-right: 0px !important;
  overflow: hidden;">
        <div style="background-color: antiquewhite;">
            <div class="header">
                <div class="inner-header flex">
                    <el-row>
                        <el-col :span="3" :offset="1">
                            <h3 class="location">当前位置：</h3>
                        </el-col>
                        <el-col :span="10" style="margin-top: 43px;">
                            <el-breadcrumb separator="/">
                                <el-breadcrumb-item >个人中心</el-breadcrumb-item>
                            </el-breadcrumb>
                        </el-col>
                        <!-- 主要内容 -->
                    </el-row>
                    <div class="main-area" :data=users.one>
                        <el-row>
                            <el-col :span="8">
                                <img :src="users.one.picture" 
                                    style="width: 280px;height: 280px; border-radius: 50%;margin-top: 15%;margin-left: 50%;">
                            </el-col>
                            <el-col :span="10" style="margin-left: 15%;margin-top: 5%;line-height: 80px;">
                                <el-row>
                                    <el-col>
                                        <div style="font-family: STKaiti;font-size: 24px;text-align: left;color: black;">
                                            <font style="font-size: 28px;color: black;">用户名：</font>{{ users.one.userName }}
                                        </div>
                                    </el-col>
                                </el-row>
                                <el-row>
                                    <el-col>
                                        <div style="font-family: STKaiti;font-size: 24px;text-align: left;color: black;">
                                            <font style="font-size: 28px;color: black;">用户状态：</font>
                                            {{ typeName1[users.one.state] }}
                                        </div>
                                    </el-col>
                                </el-row> 
                                <el-row>
                                    <el-col>
                                        <div style="font-family: STKaiti;font-size: 24px;text-align: left;color: black;">
                                            <font style="font-size: 28px;color: black;">用户角色：</font> {{ users.roleList[0].roleName }}
                                        </div>
                                    </el-col>
                                </el-row>
                                <el-row>
                                    <el-col>
                                        <div style="font-family: STKaiti;font-size: 24px;text-align: left;color: black;">
                                            <font style="font-size: 28px;color: black;">权限描述：</font> {{ users.roleList[0].description }}
                                        </div>
                                    </el-col>
                                </el-row>
                            </el-col>
                            <el-col :span="24" style="margin-left: 80%;">
                                <div style="float: left;">
                                    <el-button
                                        style="background-color:#aa8055c0;cursor: pointer; font-family: STKaiti;font-size: 20px;"
                                        type="primary" @click="loginVisible = true">
                                        编辑个人资料
                                    </el-button>
                                </div>
                                <div style=" float: left;margin-left: 3%;" v-if="users.roleList[0].roleId == 1">
                                    <el-button
                                        style="background-color:#aa8055c0;cursor: pointer; font-family: STKaiti;font-size: 20px;"
                                        type="primary" @click="mana()">
                                        管理中心
                                    </el-button>
                                </div>
                            </el-col>
                        </el-row>
                        <img src="@/assets/aixin5.png" height="30px" width="100%" style="margin-top: 15px;">
                    </div>
                    <el-dialog title="修改个人信息" :visible.sync="loginVisible" style="font-family: STKaiti;">
                        <el-form :model="usersq"  >
                            <el-form-item label="头像" :label-width="formLabelWidth" style="width:30%;">
                                <el-upload  class="upload-demo" ref="upload" action="doUpload"  
                                  style="float:right" :before-upload="beforeFileUpload" :file-list="fileList" :on-change="changeCarPicture1" :on-preview="handlePictureCardPreview"  
                                  :on-remove="handleRemove"  :limit="1">
                                  <el-button  slot="trigger" type="primary" @change="handleFileChange">上传文件<i class="el-icon-upload el-icon--right"></i></el-button>
                                  <div slot="tip" class="el-upload__tip">最多上传1张,只能上传1M以内的文件</div>
                                </el-upload>
                                <img :src="usersq.one.picture" height=150px  style="float: left;margin-top: 10px;">
                                <el-dialog 
                                  :visible.sync="dialogVisibles" 
                                  append-to-body 
                                  class="dialog1" 
                                  width="40%">
                                <img :src="dialogImageUrl" alt="" class="ims">
                              </el-dialog>
                              </el-form-item>
                            <!-- <el-form-item label="头像" :label-width="formLabelWidth"  >
                                <img :src="usersq.one.picture" min-width="70" height="100"  style="margin-top: 15px;" />
                                <el-input v-model="fileName" :readonly='true' placeholder="文件名" style="width:350px;">
                                </el-input>
                                <el-upload class="upload-demo" ref="upload" action="doUpload" :limit="1"
                                    :file-list="fileList" :before-upload="beforeFileUpload">
                                    <el-button slot="trigger" type="primary" style="margin-left: 99%;margin-top: 5%;">上传文件<i
                                            class="el-icon-upload el-icon--right"></i></el-button>
                                    <div slot="tip" class="el-upload__tip" style="margin-left: 40%;margin-top: 1%;">
                                        只能上传1M以内的文件</div>
                                </el-upload>
                            </el-form-item> -->
                            <el-form-item label="昵称" :label-width="formLabelWidth"  >
                                <el-input v-model="usersq.one.userName" placeholder="请输入昵称"></el-input>
                            </el-form-item>
                            <el-form-item label="密码" :label-width="formLabelWidth" prop="userPass">
                                <el-input v-model="usersq.one.userPass" placeholder="请输入密码" show-password></el-input>
                            </el-form-item>
                            <el-form-item label="密保问题" :label-width="formLabelWidth">
                                <el-select v-model="usersq.one.quesId" placeholder="请选择" style="width: 500px;margin-left: 0px;">
                                    <el-option v-for="item in options" :key="item.quesId" :label="item.question"
                                        :value="item.quesId">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="密保答案" :label-width="formLabelWidth"  >
                                <el-input v-model="usersq.one.answer" placeholder="请输入答案"></el-input>
                            </el-form-item>
                        </el-form>
                        <div slot="footer" class="dialog-footer">
                            <el-button @click="loginVisible = false">取 消</el-button>
                            <el-button type="primary" @click="editUser()"
                                style="background-color: antiquewhite;border: 0;color: black;border: 1;">修改</el-button>
                        </div>
                    </el-dialog>
                    <div class="main-area">
                        <el-row>
                            <table border-bottom="1"
                                style="line-height: 50px;font-family: STKaiti;font-size: 24px;margin-left: 3%;margin-top:2%;color: black;height: 350px;vertical-align: top;">
                                <tr style="vertical-align: top;">
                                    <td style="width:  100px;margin-top: 50px; ">
                                        <font style=" font-size: 28px; line-height: 10px;"><br>我<br>的<br>评<br>论<br>
                                        </font>
                                    </td>
                                    <td style="width: 94%;text-align: center;height: 230px;">
                                        <table cellspacing="0" s>
                                            <tr>
                                                <td
                                                    style="text-align: center;width: 15%;cursor: pointer;border-bottom :1px solid  ">
                                                    评论对象</td>
                                                <td
                                                    style="text-align: center;width: 20%;cursor: pointer;border-bottom :1px solid  ">
                                                    对象名称</td>
                                                <td style="text-align: center;width: 40%;border-bottom :1px solid">评论内容
                                                </td>
                                                <td style="width: 20%;border-bottom :1px solid">评论时间</td>
                                            </tr>
                                            <tr style="text-align: center;width: 900px;border-bottom :1px solid red;"
                                                v-for="item in comment">
                                                <td style="text-align: center;width: 15%;cursor: pointer;border-bottom :1px solid ">
                                                    {{ typeName2[item.objectType] }}
                                                </td>
                                                <td style="text-align: center;width: 20%;cursor: pointer;border-bottom :1px solid  "
                                                    @click="select(item.objectId)">
                                                    {{ item.objectName }}
                                                </td>
                                                <td style="text-align: center;width: 40%;border-bottom :1px solid">
                                                    {{ item.commentContent | filterAmount(20) }}
                                                </td>
                                                <td style="width: 500px;border-bottom :1px solid">
                                                    <font style="margin-left: 1%;width: 20%;">{{ new
                                                        Date(item.commentTime).toLocaleString('zh').replaceAll('/', '-') }}
                                                    </font>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                            <el-pagination background @size-change="handleSizeChange1"
                                @current-change="handleCurrentChange1" :current-page="currentPage1" :page-sizes="[2, 5]"
                                :page-size="5" layout="total, sizes, prev, pager, next, jumper" :total="total1"
                                v-if="comment.length > 0">
                            </el-pagination>
                            <el-empty description="暂无评论" v-if="comment.length < 1" style="margin-top: -200px;"></el-empty>
                        </el-row>
                    </div>
                    <div class="main-area">
                        <el-row>
                            <table border-bottom="1"
                                style="line-height: 50px;font-family: STKaiti;font-size: 24px;margin-left: 3%;margin-top:2%;color: black;height: 350px;vertical-align: top;">
                                <tr style="vertical-align: top;">
                                    <td style="width:  100px;margin-top: 50px;">
                                        <font style=" font-size: 28px; line-height: 10px;"><br>我<br>的<br>回<br>复<br>
                                        </font>
                                    </td>
                                    <td style="width: 94%;text-align: center;height: 230px;">
                                        <table cellspacing="0" s>
                                            <tr>
                                                <td
                                                    style="text-align: center;width: 15%;cursor: pointer;border-bottom :1px solid  ">
                                                    回复内容</td>
                                                <td style="width: 20%;border-bottom :1px solid">回复时间</td>
                                            </tr>
                                            <tr style="text-align: center;width: 900px;border-bottom :1px solid red;"
                                                v-for="item in reply">
                                                <td style="text-align: center;width: 40%;border-bottom :1px solid">
                                                    {{ item.replyContent | filterAmount(35) }}
                                                </td>
                                                <td style="width: 500px;border-bottom :1px solid">
                                                    <font style="margin-left: 1%;width: 20%;">{{ new
                                                        Date(item.replyTime).toLocaleString('zh').replaceAll('/', '-') }}
                                                    </font>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                            <el-pagination background @size-change="handleSizeChange3"
                                @current-change="handleCurrentChange3" :current-page="currentPage3" :page-sizes="[2, 5]"
                                :page-size="5" layout="total, sizes, prev, pager, next, jumper" :total="total3"
                                v-if="reply.length > 0">
                            </el-pagination>
                            <el-empty description="暂无评论" v-if="reply.length < 1" style="margin-top: -200px;"></el-empty>
                        </el-row>
                        <img src="@/assets/122.png" height="60px" width="100%" style="margin-top: 15px;">
                    </div>
                    <div class="main-area">
                        <el-row>
                            <table border-bottom="1"
                                style="line-height: 50px;font-family: STKaiti;font-size: 24px;margin-left: 3%;margin-top:2%;color: black;height: 350px;vertical-align: top;">
                                <tr style="vertical-align: top;">
                                    <td style="width:  100px;margin-top: 50px;">
                                        <font style=" font-size: 28px; line-height: 10px;"><br>我<br>的<br>举<br>报<br>
                                        </font>
                                    </td>
                                    <td style="width: 94%;text-align: center;height: 230px;">
                                        <table cellspacing="0" s>
                                            <tr>
                                                <td
                                                    style="text-align: center;width: 15%;cursor: pointer;border-bottom :1px solid  ">
                                                    举报详情</td>
                                                <td style="width: 20%;border-bottom :1px solid">举报时间</td>
                                                <td style="width: 20%;border-bottom :1px solid">举报审核状态</td>
                                                <td style="width: 20%;border-bottom :1px solid">举报原因类型</td>
                                            </tr>
                                            <tr style="text-align: center;width: 900px;border-bottom :1px solid red;"
                                                v-for="item in report">
                                                <td style="text-align: center;width: 40%;border-bottom :1px solid">
                                                    {{ item.content | filterAmount(35) }}
                                                </td>
                                                <td style="width: 500px;border-bottom :1px solid">
                                                    <font style="margin-left: 1%;width: 20%;">{{ new
                                                        Date(item.reportTime).toLocaleString('zh').replaceAll('/', '-') }}
                                                    </font>
                                                </td>
                                                <td style="text-align: center;width: 15%;cursor: pointer;border-bottom :1px solid ">
                                                    {{ typeName4[item.reportState] }}
                                                </td>
                                                <td style="text-align: center;width: 15%;cursor: pointer;border-bottom :1px solid ">
                                                    {{ typeName3[item.reportReasonType] }}
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                            <el-pagination background @size-change="handleSizeChange4"
                                @current-change="handleCurrentChange4" :current-page="currentPage4" :page-sizes="[2, 5]"
                                :page-size="5" layout="total, sizes, prev, pager, next, jumper" :total="total4"
                                v-if="report.length > 0">
                            </el-pagination>
                            <el-empty description="暂无评论" v-if="report.length < 1" style="margin-top: -200px;"></el-empty>
                        </el-row>
                    </div>
                    <div class="main-area">
                        <el-row>
                            <table border-bottom="1"
                                style="line-height: 50px;font-family: STKaiti;font-size: 24px;margin-left: 3%;margin-top:2%;color: black;height: 350px;vertical-align: top;">
                                <tr style="vertical-align: top;">
                                    <td style="width:  100px;margin-top: 50px;">
                                        <font style=" font-size: 28px; line-height: 10px;"><br>我<br>的<br>反<br>馈<br>
                                        </font>
                                    </td>
                                    <td style="width: 94%;text-align: center;height: 230px;">
                                        <table cellspacing="0" s>
                                            <tr>
                                                <td
                                                    style="text-align: center;width: 15%;cursor: pointer;border-bottom :1px solid  ">
                                                    反馈详情</td>
                                                <td style="width: 20%;border-bottom :1px solid">反馈时间</td>
                                                <td style="width: 20%;border-bottom :1px solid">反馈类型</td>
                                            </tr>
                                            <tr style="text-align: center;width: 900px;border-bottom :1px solid red;"
                                                v-for="item in feed">
                                                <td style="text-align: center;width: 40%;border-bottom :1px solid">
                                                    {{ item.content | filterAmount(35) }}
                                                </td>
                                                <td style="width: 500px;border-bottom :1px solid">
                                                    <font style="margin-left: 1%;width: 20%;">{{ new
                                                        Date(item.creationTime).toLocaleString('zh').replaceAll('/', '-') }}
                                                    </font>
                                                </td>
                                                <td style="text-align: center;width: 15%;cursor: pointer;border-bottom :1px solid ">
                                                    {{ typeName5[item.feedbackType] }}
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                            <el-pagination background @size-change="handleSizeChange5"
                                @current-change="handleCurrentChange5" :current-page="currentPage5" :page-sizes="[2, 5]"
                                :page-size="5" layout="total, sizes, prev, pager, next, jumper" :total="total5"
                                v-if="report.length > 0">
                            </el-pagination>
                            <el-empty description="暂无评论" v-if="report.length < 1" style="margin-top: -200px;"></el-empty>
                        </el-row>
                    </div>
                </div>
                <div style="margin-top: 180px;">
                    <svg class="waves" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
                        viewBox="0 24 150 28" preserveAspectRatio="none" shape-rendering="auto">
                        <defs>
                            <path id="gentle-wave"
                                d="M-160 44c30 0 58-18 88-18s 58 18 88 18 58-18 88-18 58 18 88 18 v44h-352z" />
                        </defs>
                        <g class="parallax">
                            <use xlink:href="#gentle-wave" x="48" y="0" fill="rgba(255,255,255,0.7" />
                            <use xlink:href="#gentle-wave" x="48" y="3" fill="rgba(255,255,255,0.5)" />
                            <use xlink:href="#gentle-wave" x="48" y="5" fill="rgba(255,255,255,0.3)" />
                            <use xlink:href="#gentle-wave" x="48" y="7" fill="#fff" />
                        </g>
                    </svg>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { getQuestions, getQuestionAnswer, setQuestionAnswer } from '@/api/question'
import { getOneUser,  editUser, SelectUser, SelectUser1, SelectUser2 } from '@/api/users'
import { getOneArchive1, PageAllArchiveq } from '@/api/archive'
import { getOneComment1, PageAllComment,getUserReplyList,getUserComplainList,getUserFeedbackList} from '@/api/comment'
import './filters.js'

export default {
    data() {
 
        var passwordreg = (rule, value, callback) => {
            const reg = /(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,12}/;
            if (reg.test(value)) {
                // var isValid = passwordreg.test(this.user.userPass);
                // if (isValid != true) {
                return callback();
 
            }
            callback(new Error("密码必须是大写字母，小写字母，数字，特殊字符组成，且长度为8到12位！"));
        };
        return {
            typeName1: {
                '0': "正常",
                '1': "被禁用",
            },
            typeName2: {
                '4': "纤维",
                '1': "档案",
                '2': "纸张",
                '3': "机构",
            },
            typeName3: {
                '0': "违反法律法规",
                '1': "谣言类不实消息",
                '2': "有害社区环境",
                '3': "侵犯个人权益"
            },
            typeName4: {
                '0': "待审核",
                '1': "审核通过",
                '2': "审核不通过"
            },
            typeName5: {
                '0': "纸张",
                '1': "档案",
                '2': "档案馆",
                '3': "评论"
            },
            fileName: "",//上传的文件名称
            file: null,
            imgs: [],
            fileList: [], // 用于存储文件列表
            loginVisible: false,
            typelist5: [{
                state: 1,
                typeName1: "正常",
            }, {
                state: 2,
                typeName1: "被禁用",
            }], // 资源类型列表
            ruleForm: {
                userAccount: '',
                passAnswer: '',
                quesId: '',
                rnewport: '',
            },
            achives: [],
            formLabelWidth: '',
            comment: [],
            pageSizes: [5],// 个数选择器（可修改）
            pageSizes1: [5],// 个数选择器（可修改）
            pageSizes3: [5],// 个数选择器（可修改）
            pageSizes4: [5],// 个数选择器（可修改）
            users: [],
            users1: [],
            total2: 0,
            reply:[],
            usersq:[],
            userList: [],
            userList1: [],
            userList2: [],
            archives: [],
            rules: {
               
                userPass: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    {
                        validator: passwordreg,
                    },
                ],
                passAnswer: [
                    { required: true, message: '请输入密保答案', trigger: 'blur' },

                ],
                picture: [
                    { required: true, message: '请选择头像', trigger: 'blur' },

                ],
                quesId: [
                    { required: true, message: '请选择密保问题', trigger: 'blur' },
                ],
                

            },
            report:[],
            namee: [],
            tota1: 0,
            pageSize: 5,// 默认每页显示的条数（可修改）
            currentPage: 1,
            pageSize1: 5,// 默认每页显示的条数（可修改）
            currentPage1: 1,
            pageSize3: 5,// 默认每页显示的条数（可修改）
            currentPage3: 1,
            pageSize4: 5,// 默认每页显示的条数（可修改）
            currentPage4: 1,
            pageSize5: 5,// 默认每页显示的条数（可修改）
            currentPage5: 1,
            nameee: [],
            userss: [],
            feed:[]
        };
    },
    methods: {
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    setQuestionAnswer(this.ruleForm).then(res => {
                        this.$message({
                            type: 'success',
                            message: "修改成功！"
                        });
                    });
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        beforeFileUpload(file) {
      // 这里可以添加对文件的一些检查，比如文件的大小和类型等
      const isLt1M = file.size / 1024 / 1024 < 1;
      if (!isLt1M) {
        this.$message.error('上传的文件大小不能超过 1MB!');
        return false;
      } else {
        let _this = this
      const reader = new FileReader()
      reader.readAsDataURL(file.raw)
      reader.onload = function(e) {
        undefined
        _this.imgs.push(this.result)
      }
      this.getimg(file)
      console.log(file);
        // 保存文件对象
        this.file = file;
        this.fileName = file.name;
        return false; // 返回false不会自动上传
      }
    },
    changeCarPicture1(file, fileList) {
      console.log(file.raw.name)
       	   // 图片转成base64上传
        	let reader = new FileReader();
        	reader.onload = () => {
          	 	let _base64 = reader.result;
          		let BASE64 = _base64.split(",");
         		this.pictureLocation = file.raw.name+":"+BASE64[1];  //赋值
       	    };
          reader.readAsDataURL(file.raw);
          let timer = setInterval(() => {
            // this.imgs.push(this.pictureLocation)/
          console.log( this.pictureLocation)
          //清除一次性定时器
          clearTimeout(timer)

     //需要定时执行的代码
},50)     

        },
        changeUpload(file, fileList) {
    if (fileList.length > 1 && file.status !== "fail") {
        fileList.splice(0, 1);
    // 多选并上传多个文件：
    // 方法一：fileList.splice(0, n)。n:多选文件的数量。--数组从前往后顺序截取。
    // 方法二：fileList.splice(0, -n)。n:多选文件的数量。--数组从后往前顺序截取（保留最新上传的，截去之前上传的。--方法一反之）
    } else if (file.status === "fail") {
        this._err("上传失败，请重新上传！");
        fileList.splice(0, 1);
    }
    },
    // 上传文件 - 文件上
        handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },
      handleRemove(file) {
        console.log(file);
      },
        handleSizeChange(val) {
            this.pageSize = val
            this.PageAllArchive();
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            this.currentPage = val
            console.log(`当前页: ${val}`);
            this.PageAllArchive();

        },
        handleSizeChange1(val) {
            this.pageSize1 = val
            this.PageAllComment();
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange1(val) {
            this.currentPage1 = val
            console.log(`当前页: ${val}`);
            this.PageAllComment();

        },
        goback() {
            this.$router.go(-1)
        },//返回上一页
        mana() {
            this.$router.push('/management/index')
        },//返
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
        //获取密保问题列表
        getQuestion() {
            getQuestions().then(res => {
                console.log(res.data, 520)
                this.options = res.data;
            })
        },
        //获取自己的密保信息
        getMiBao() {
            const obj = { "userAccount": this.ruleForm.userAccount };
            getQuestionAnswer(obj).then(res => {
                console.log(res, 5200);
                if (res.data != null) {
                    this.ruleForm.passAnswer = res.data.passAnswer;
                    this.ruleForm.quesId = res.data.quesId;
                }

            })
        },

        getOneUser() {
            // const obj = { userId: parseInt(sessionStorage.getItem('SET_NAME')) };
            // const obj = JSON.stringify(sessionStorage.getItem('SET_NAME'));
            const obj = { userId: parseInt(sessionStorage.getItem('SET_USERID')) };
            console.log(obj.userId)
            setTimeout(() => {
                getOneUser(obj.userId).then((response) => {
                this.usersq = response.data;
                this.users = response.data;
                console.log(this.usersq);
                // console.log(res, 5200);
            }) 
        }, 1);
        },
       
        getOneArchive1() {
            const obj = { userId: parseInt(sessionStorage.getItem('SET_USERID')) };
            console.log(obj.userId)
            getOneArchive1(obj.userId).then(res => {
                this.archives1 = res.data
                console.log(res, 5200);

            })
        },
        getOneComment1() {
            const obj = { userId: parseInt(sessionStorage.getItem('SET_USERID')) };
            console.log(obj.userId)
            var pageNum = this.currentPage2
            var pageSize = this.pageSize1
            var isAsc = false
            var orderColumn = "commentId"//排序
            var state = "0"//排序      
            var objectType = ""//排序
            var content = ''//排序    
            getOneComment1(obj.userId,pageNum,pageSize,isAsc,orderColumn,state,objectType,content).then(res => {
                this.comment1 = res.data
                console.log(res, 5200);
            })
        },
        editUser() {
            const obj = { userId: parseInt(sessionStorage.getItem('SET_USERID')) };
             
                    this.$confirm('您确定要修改个人资料吗?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        // 创建一个 FormData 对象
                        const formData = new FormData();
                        // if (this.fileName != this.usersq.one.picture) { // 文件修改后且非微课资源则添加文件
                        //     formData.append('file', this.file);  // 'file' 是文件的名字，file 是 File 对象
                        // }
                        formData.append('picture', this.pictureLocation);
                        formData.append('userId',obj.userId);
                        formData.append('quesId', this.usersq.one.quesId);
                        formData.append('userName', this.usersq.one.userName);
                        formData.append('userPass', this.usersq.one.userPass);
                        formData.append('answer', this.usersq.one.answer);
                        // console.log(this.file)
                        editUser(formData).then(response => {
                            if (response.message == "操作成功") {
                                // this.$router.push('/management/teacherManagement')
                                this.$message({
                                    type: 'success',
                                    message: "修改成功！"
                                })

                                this.loginVisible = false
                                // location.reload();//自动刷新  
                                this.$forceUpdate();   // 强制更新

                                // this.getAllResource()
                                // } else if (response.message == "暂无权限") {
                                //     this.$message({
                                //         type: 'error',
                                //         message: '暂无编辑权限！'
                                //     })
                                //     this.dialogVisible9 = false
                            } else {
                                this.$message({
                                    type: 'error',
                                    message: response.message
                                })
                            }
                        }
                        )
                    })


                    // }
                
            
        },
        PageAllArchive() {
            const obj = { userId: parseInt(sessionStorage.getItem('SET_USERID')) };
            console.log(obj.userId)
            var pageNum = this.currentPage
            var pageSize = this.pageSize
            var order = 0//排序
            var typefilter1 = obj.userId;//类型筛选
            // var datefilter1 = this.datefilter1;//类型筛选
            PageAllArchiveq({ pageSize, order, pageNum, typefilter1 }).then((response) => {
                if (response) {
                    this.total2 = response.data.total;
                    // this.reload();//局部刷新
                    console.log(pageNum + "/" + pageSize + "/" + this.total);
                    this.archives = response.data.archiveList;
                    // console.log(achives)
                } else {
                    console.log(errorLog)
                }
            })
        },
        PageAllComment() {
            const obj = { userId: parseInt(sessionStorage.getItem('SET_USERID')) };
            console.log(obj.userId)
            var pageNum = this.currentPage1
            var pageSize = this.pageSize1
            // var order = 0//排序
            var isAsc = false
            var orderColumn = "commentId"//排序
            var state = 0//排序      
            // var objectType = 1 //排序
            var content = ""//排序    
            // var typefilter1 = obj.userId;//类型筛选
            // var datefilter1 = this.datefilter1;//类型筛选
            PageAllComment({ pageSize, pageNum, isAsc,orderColumn,state,content }).then((response) => {
                if (response) {
                    this.total1 = response.data.total;
                    // this.reload();//局部刷新
                    console.log(pageNum + "/" + pageSize + "/" + this.total);
                    this.comment = response.data.list;
                    // console.log(achives)
                } else {
                    console.log(errorLog)
                }
            })
        },
        getUserReplyList() {
            const obj = { userId: parseInt(sessionStorage.getItem('SET_USERID')) };
            console.log(obj.userId)
            var pageNum = this.currentPage3
            var pageSize = this.pageSize3
            var isAsc = false
            var orderColumn = "replyId"//排序
            var state = 0 //排序      
            var objectType = "" //排序
            var content = ""//排序    
            getUserReplyList({ pageSize, pageNum, isAsc,orderColumn,state,content,objectType }).then((response) => {
                if (response) {
                    this.total3 = response.data.total;
                    // this.reload();//局部刷新
                    console.log(pageNum + "/" + pageSize + "/" + this.total3);
                    this.reply = response.data.list;
                    // console.log(achives)
                } else {
                    console.log(errorLog)
                }
            })
        },
        getUserComplainList() {
            const obj = { userId: parseInt(sessionStorage.getItem('SET_USERID')) };
            console.log(obj.userId)
            var pageNum = this.currentPage4
            var pageSize = this.pageSize4
            var isAsc = false
            var orderColumn = "reportId"//排序
            var state = 0 //排序      
            var objectType = "" //排序
            var content = ""//排序    
            getUserComplainList({ pageSize, pageNum, isAsc,orderColumn,state,content,objectType }).then((response) => {
                if (response) {
                    this.total4 = response.data.total;
                    // this.reload();//局部刷新
                    console.log(pageNum + "/" + pageSize + "/" + this.total4);
                    this.report = response.data.list;
                    console.log(this.report )
                } else {
                    console.log(errorLog)
                }
            })
        },
        getUserFeedbackList() {
            const obj = { userId: parseInt(sessionStorage.getItem('SET_USERID')) };
            console.log(obj.userId)
            var pageNum = this.currentPage5
            var pageSize = this.pageSize5
            var isAsc = false
            var orderColumn = "feedbackId"//排序
            var state = 1 //排序      
            var objectType = "" //排序
            var content = ""//排序    
            getUserFeedbackList({ pageSize, pageNum, isAsc,orderColumn,state,content,objectType }).then((response) => {
                if (response) {
                    this.total5 = response.data.total;
                    // this.reload();//局部刷新
                    console.log(pageNum + "/" + pageSize + "/" + this.total4);
                    this.feed = response.data.list;
                    console.log(this.feed )
                } else {
                    console.log(errorLog)
                }
            })
        },
    },
    // console.log(this.usersq); // 打印 oneresource 对象

    mounted() {
        this.getQuestion();
        this.ruleForm.userAccount = sessionStorage.getItem('SET_ACCOUNT');
        this.getMiBao();
        this.getOneUser();
        this.getUserComplainList();
        this.PageAllArchive();
        this.getOneArchive1();
        this.getOneComment1();
        this.PageAllComment();
        this.getUserReplyList();
        this.getUserFeedbackList()
    }


};


</script>

<style scoped>
body {
    padding-right: 0px !important;
    overflow: hidden;
}

.title-box {
    background: #EDD9AF;
    padding: 20px;
    font-size: 25px;
    font-family: "华文中宋";
}

body {
    padding-right: 0px !important;
    overflow: hidden;
}

.header {
    position: relative;
    text-align: center;
    background: linear-gradient(60deg, #f4e8d4 20%, antiquewhite 100%);
    color: white;
}


.inner-header {
    height: auto;
    width: 100%;
    margin: 0;
    padding: 0;
}



.waves {
    position: relative;
    width: 100%;
    height: 22vh;
    margin-top: 50px;
    margin-bottom: -7px;
    /*Fix for safari gap*/
    min-height: 100px;
    max-height: 150px;
}

.content {
    position: relative;
    height: 20vh;
    text-align: center;
    background-color: white;
}

.content a {
    margin: 0 5px;
    font-size: 12px;
    color: #333;
}

.content a:hover {
    color: #000;
}

/* Animation */

.parallax>use {
    animation: move-forever 25s cubic-bezier(0.55, 0.5, 0.45, 0.5) infinite;
}

.parallax>use:nth-child(1) {
    animation-delay: -2s;
    animation-duration: 7s;
}

.parallax>use:nth-child(2) {
    animation-delay: -3s;
    animation-duration: 10s;
}

.parallax>use:nth-child(3) {
    animation-delay: -4s;
    animation-duration: 13s;
}

.parallax>use:nth-child(4) {
    animation-delay: -5s;
    animation-duration: 20s;
}

@keyframes move-forever {
    0% {
        transform: translate3d(-90px, 0, 0);
    }

    100% {
        transform: translate3d(85px, 0, 0);
    }
}

/*Shrinking for mobile*/
@media (max-width: 768px) {
    .waves {
        height: 40px;
        min-height: 40px;
    }

    .content {
        height: 30vh;
    }

    h1 {
        font-size: 24px;
    }
}

.breadcrumb {
    font-size: 25px;
    color: #080100;
    font-family: "STKaiti";
    margin-top: 40px;
}

.location {
    font-size: 25px;
    color: rgb(160, 106, 5);
    font-family: "STKaiti";
    margin-top: 37px;
}

.main-area {
    width: 100%;
    height: auto;
    margin-bottom: -10px;
}

.el-breadcrumb__item {
    font-size: 20px;
    font-family: "STKaiti";
}
.el-button--primary {
    color: #FFF;
    /* background-color: #409EFF; */
    border-color: #121314;
}
</style>
