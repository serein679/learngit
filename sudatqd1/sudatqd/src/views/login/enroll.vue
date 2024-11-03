<template>
    <div id="background">

        <el-card class="box-card">
            <div class="demo-image">
                <div class="block">
                    <img src="~@/assets/6208c26411e471644741220880.png" height="300px" width="500px" />
                </div>
            </div>
            <div>
                <h2 style="text-align:center;font-family: STKaiti;">{{ title }}</h2>
            </div>
            <el-form width="30%" :rules="rules">
                <el-form ref="ruleForm" :model="ruleForm" :rules="rules">
                    <el-form-item label="头像" :label-width="formLabelWidth" style="width:60%;">
                        <el-upload  class="upload-demo" ref="upload" action="doUpload"  
                          style="float:right" :before-upload="beforeFileUpload" :file-list="fileList" :on-change="changeCarPicture1" :on-preview="handlePictureCardPreview"  
                          :on-remove="handleRemove"  :limit="1">
                          <el-button  slot="trigger" type="primary" @change="handleFileChange">上传文件<i class="el-icon-upload el-icon--right"></i></el-button>
                          <div slot="tip" class="el-upload__tip">最多上传1张,只能上传1M以内的文件</div>
                        </el-upload>
                        <!-- <img :src="usersq.one.picture" height=150px  style="float: left;margin-top: 10px;"> -->
                        <el-dialog 
                          :visible.sync="dialogVisibles" 
                          append-to-body 
                          class="dialog1" 
                          width="40%">
                        <img :src="dialogImageUrl" alt="" class="ims">
                      </el-dialog>
                      </el-form-item>
                    <!-- <el-form-item label="头　　像:" :label-width="formLabelWidth">
                        <el-input v-model="fileName" :readonly='true' placeholder="文件名" style="width:65%;" clearable>
                        </el-input>
                        <span>
                            <el-upload class="upload-demo" ref="upload" action="doUpload" :limit="1"
                                :file-list="fileList" :auto-upload="true" :before-upload="beforeFileUpload">
                                <el-button slot="trigger" type="primary"
                                    style="margin-left: 100%;margin-top: 9px;background-color: antiquewhite;border: 0;color: black;border: 1;">
                                    上传文件<i class="el-icon-upload el-icon--right"></i></el-button>
                                <div slot="tip" class="el-upload__tip" style="margin-left: 230px;">只能上传1M以内的文件</div>
                            </el-upload>
                        </span>
                    </el-form-item> -->
                    <el-form-item label="用户名　:" :label-width="formLabelWidth" prop="userName">
                        <el-input v-model="ruleForm.userName" autocomplete="off" placeholder="请输入用户名"
                            style="width: 65%;"></el-input>
                    </el-form-item>
                    <el-form-item label="密　　码:" :label-width="formLabelWidth" prop="userPass">
                        <el-input v-model="ruleForm.userPass" autocomplete="off" placeholder="请输入密码" show-password
                            style="width: 65%;"></el-input>
                    </el-form-item>
                    <el-form-item label="密保问题:" :label-width="formLabelWidth" prop="quesId">
                        <el-select v-model="ruleForm.quesId" placeholder="请选择" style="width: 65%;margin-left: 0px;">
                            <el-option v-for="item in options" :key="item.quesId" :label="item.question"
                                :value="item.quesId">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="密保答案:" :label-width="formLabelWidth" prop="answer">
                        <el-input v-model="ruleForm.answer" autocomplete="off" placeholder="请输入答案"
                            style="width: 65%;"></el-input>
                    </el-form-item>
                </el-form>
                <div style="margin-top: 8%;">
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                    <el-button type="primary" style="background-color: antiquewhite;border: 0;color: black;border: 1;"
                        @click="submitForm('ruleForm')">注册</el-button>
                </div>
            </el-form>
        </el-card>
    </div>
</template>

<script>

import { getQuestions } from '@/api/question'
import { addUser, GetUsers, SelectUser, SelectUser1 } from '@/api/users'
import { Loading } from 'element-ui';
let loading;
export default {
    name: 'Login',
    data() {
        var checkEmail = (rule, value, cb) => {
            //验证邮箱的正则表达式
            const regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
            if (regEmail.test(value)) {
                //合法的邮箱
                return cb();
            }
            cb(new Error("请输入合法的邮箱"));
        };
        var passwordreg = (rule, value, callback) => {
            const reg = /(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,12}/;
            if (reg.test(value)) {
                // var isValid = passwordreg.test(this.user.userPass);
                // if (isValid != true) {
                return callback();
                // this.msgError("密码必须是大写字母，小写字母，数字，特殊字符组成，且长度为8到12位！");
                // return;
            }
            callback(new Error("密码必须是大写字母，小写字母，数字，特殊字符组成，且长度为8到12位！"));
        };

        return {
            remember: false,//记住密码是否勾选
            rememberMe: false,
            yzm: '',
            userName: '',
            userAccount: " ",
            fileName: "",//上传的文件名称
            file: null,
            fileList: [], // 用于存储文件列表
            users: [],
            imgs: [],
            loginform: {
                act: '',
                pwd: '',
            },
            formLabelWidth: '',

            ruleForm: {
                // file: " ",
                userName: '',
                userPass: '',
                // email: '',
                quesId: '',
                answer: '',
            },
            // 表单规则验证
            rules: {
                userName: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    { max: 18, message: '用户名格式错误', trigger: 'blur' }
                ],
                userPass: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    {
                        validator: passwordreg,
                    },
                ],
                answer: [
                    { required: true, message: '请输入密保答案', trigger: 'blur' },

                ],
                picture: [
                    { required: true, message: '请选择头像', trigger: 'blur' },
                ],
                quesId: [
                    { required: true, message: '请选择密保问题', trigger: 'blur' },
                ],

            },
            userList: [],
            options: [],
            userList1: [],
            userList2: [],
            passwordType: 'password',
            capsTooltip: false,
            loading: false,
            showDialog: false,
            title: '注册',
            redirect: undefined,
            otherQuery: {},
            dialogVisible: false,
            active: 0, // 步骤条
            findForm: {
                find_userid: '',
                pwd_question: '',
                find_answer: '',
                newpass: '',
                checknew_pwd: ''
            } // 忘记密码填写的表单
        }
    },
    watch: {
        $route: {
            handler: function (route) {
                const query = route.query
                if (query) {
                    this.redirect = query.redirect
                    // this.otherQuery = this.getOtherQuery(query)
                }
            },
            immediate: true
        }
    },

    methods: {
        verifyLogin() {
            let check = sessionStorage.getItem('SET_TOKEN')
            this.username = sessionStorage.getItem('SET_NAME')
            console.log(check)
            return check
        },

        getQuestions() {
            getQuestions().then(res => {
                console.log(res.data, 520);
                this.options = res.data;
            })
        },

        GetUsers() {
            GetUsers().then(res => {
                console.log(res.data, 520);
                this.users = res.data;
            })
        },
        findpassport() {
            this.$router.push('/login/passportFind')
        },
        enroll() {
            this.$router.push('/login/enroll')
        },
        home() {
            this.$router.push('/')
        },
        handleLogin() {
            var obj = JSON.parse(JSON.stringify(this.loginform))
            this.$store.dispatch('user/login', obj).then(() => {
                this.loginVisible = false
                this.username = sessionStorage.getItem('SET_NAME')
                this.$router.push({ path: this.redirect || '/', query: this.otherQuery })
            })

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
        handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },
      handleRemove(file) {
        console.log(file);
      },

        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    setTimeout(() => {
                                this.$confirm('您确定要注册吗?', '提示', {
                                    confirmButtonText: '确定',
                                    cancelButtonText: '取消',
                                    type: 'warning'
                                }).then(() => {
                                    // console.log(this.ruleForm)
                                    const formData = new FormData();
                                    // 'file' 是文件的名字，file 是 File 对象
                                    formData.append('username', this.ruleForm.userName);
                                    formData.append('userpass', this.ruleForm.userPass);
                                    formData.append('quesId', this.ruleForm.quesId);
                                    formData.append('picture', this.pictureLocation);
                                    formData.append('answer', this.ruleForm.answer);
                                    // formData.append('file', this.file);
                                    // console.log(formData.get('email'))
                                    addUser(formData).then(res => {
                                        if (res.code != 200) {
                                            this.$message({
                                                type: 'error',
                                                message: res.message
                                            });
                                            return;
                                        }
                                        this.$message({
                                            type: 'success',
                                            message: "注册成功！"
                                        });
                                        this.$router.push('/login/index')
                                    })
                                }
                                )
                        
                        // }
                    }, 20);
                }
                else {
                    console.log('error submit!!');
                    return false;
                }

            }

            );

        },
        startLoading() {  // 使用Element loading-start 方法
            loading = Loading.service({
                lock: true,
                text: '加载中……',
                background: 'rgba(0, 0, 0, 0.7)'
            });
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }

    },
    mounted() {
        this.getQuestions();
        this.GetUsers();
        this.startLoading()  // 开启加载
        loading.close(); // 关闭加载
    }


}
</script>

<style lang="scss" scoped>
#background {
    background: url("~@/assets/11224.jpg");
    width: 100%; 
    /* //大小设置为100% */
    height: 100%; 
    /* //大小设置为100% */
    /* //position: fixed; */
    background-size: 100% 100%;

}

.box-card {
    margin-top: 0%;
    margin-left: 52%;
    margin-bottom: auto;
    width: 620px;
    height: 1050px;
    text-align: center;
}
</style>