<template>
    <div id="background">
        <el-card class="box-card">
            <div class="demo-image">
                <div class="block">
                    <img src="~@/assets/1122.png" height="100%" width="600px" />
                </div>
            </div>
            <div>
                <h2 style="text-align:center;font-family: STKaiti;">{{ title }}</h2>
            </div>
            <el-form width="30%">
                <el-form ref="loginForm" :model="loginform">
                    <el-form-item label="用户名称:" :label-width="formLabelWidth">
                        <el-input v-model="loginform.act" autocomplete="off" placeholder="请输入学号或工号"
                            style="width: 65%;"></el-input>
                        <label style="vertical-align:central"> <input type="checkbox"
                                style="width:25px;height:15px;vertical-align:middle;margin-top:-3px;font-family: STKaiti;"
                                title="记住用户" v-model="rememberMe" />记住用户</label>
                    </el-form-item>
                    <el-form-item label="密　　码:" :label-width="formLabelWidth">
                        <el-input v-model="loginform.pwd" autocomplete="off" placeholder="请输入密码" show-password
                            style="width: 65%;"></el-input>
                        <label style="vertical-align:central"> <input type="checkbox"
                                style="width:25px;height:15px;vertical-align:middle;margin-top:-3px;font-family: STKaiti;"
                                title="记住密码" v-model="remember" />记住密码</label>
                    </el-form-item>
                    <el-form-item label="验证码　:" :label-width="formLabelWidth">
                         <div style="display: flex;width: 65%;">
                            <el-input prefix-icon="el-icon-key" v-model="loginform.validCode" style="width: 70%;margin-left: 27px;margin-right: 5px;" placeholder="请输入验证码">

                            </el-input>
                            <ValidCode @input="createValidCode" />
                         </div>
                    </el-form-item>
                </el-form>
                <div style="text-align: right;margin-bottom: 5%;float: left;">
                    <el-link @click="enroll()">没有账号？去注册</el-link>
                    <!-- <el-button>取 消</el-button> -->
                </div>
                <div style="text-align: right;">
                    <el-link @click="findpassport()">忘记密码？</el-link>
                    <!-- <el-button>取 消</el-button> -->
                </div>
                <div style="margin-top: 8%;">
                    <el-button @click="home()">取 消</el-button>
                    <el-button type="primary" @click="handleLogin()"
                        style="background-color: antiquewhite;border: 0;color: black;border: 1;">登 录</el-button>
                </div>
            </el-form>
        </el-card>
    </div>
</template>

<script>
import { Loading } from 'element-ui';
import ValidCode from '@/components/ValidCode'
let loading;
export default {
    name: 'Login',
    components:{
        ValidCode
    },
    data() {
        return {
            remember: false,//记住密码是否勾选
            rememberMe: false,
            yzm: '',
            formLabelWidth: '',
            loginform: {
                act: '',
                pwd: '',
            },
            ruleForm: {
                username: '',
                userpass: ''
            },
            // 表单规则验证
            rules: {
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    { max: 18, message: '用户名格式错误', trigger: 'blur' }
                ],
                userpass: [
                    { required: true, message: '请输入密码', trigger: 'blur' }
                ],
            },
            validCode:'',
            passwordType: 'password',
            capsTooltip: false,
            loading: false,
            showDialog: false,
            title: '登录',
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
    created() {

        this.startLoading()  // 开启加载
        loading.close(); // 关闭加载

        //   this.downloadFile(resourceContent, resourceType,);

    },
    methods: {
        verifyLogin() {
            let check = sessionStorage.getItem('SET_TOKEN')
            this.username = sessionStorage.getItem('SET_NAME')
            console.log(check)
            return check
        },
        createValidCode(data){
            this.validCode = data
        },

        findpassport() {
            this.$router.push('/login/passportFind')
        },
        startLoading() {  // 使用Element loading-start 方法
            loading = Loading.service({
                lock: true,
                text: '加载中……',
                background: 'rgba(0, 0, 0, 0.7)'
            });
        },
        enroll() {
            this.$router.push('/login/enroll')
        },
        home() {
            this.$router.push('/')
        },
        handleLogin() {
            if (!this.loginform.validCode) {
                this.$message.error("请填写验证码")
                return
            }
            if(this.loginform.validCode.toLowerCase() !== this.validCode.toLowerCase()) {
                this.$message.error("验证码错误")
                return
            }
            var obj = JSON.parse(JSON.stringify(this.loginform))
            this.$store.dispatch('user/login', obj).then(() => {
                // this.loginVisible = false
                this.username = sessionStorage.getItem('SET_NAME')
                this.userType = sessionStorage.getItem('SET_USERTYPE')
                if (this.userType == 0) {
                    this.$router.push({ path: this.redirect || '/', query: this.otherQuery })

                }
                else {
                    this.$router.push('/management/index')
                }
            })
        },
    },
}
</script>

<style lang="scss" scoped>
#background {
    background: url("~@/assets/11225.jpg");
    width: 100%; //大小设置为100%
    height: 100%; //大小设置为100%
    //position: fixed;
    background-size: 100% 100%;

}

.box-card {
    margin-top: 0%;
    margin-left: 52%;
    margin-bottom: auto;
    width: 620px;
    height: 100%;
    text-align: center;
}
</style>