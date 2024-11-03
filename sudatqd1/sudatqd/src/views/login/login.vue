<template>
    <div id="background">
        <el-card class="box-card">
            <div class="demo-image">
                <div class="block">
                    <img src="~@/assets/header_logo.png" />
                </div>
            </div>

            <div>
                <h2 style="text-align:center;">{{ title }}</h2>
            </div>

            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="ve_form"
                style="margin-top:30px;margin-right:20px">
                <el-form-item label="用户名" prop="uid">
                    <el-input placeholder="请输入用户名" v-model="ruleForm.username" style="width:80%"></el-input>
                    <label style="vertical-align:central"> <input type="checkbox"
                            style="width:15px;height:15px;vertical-align:middle;margin-top:-3px;" title="记住用户"
                            v-model="rememberMe" />记住用户</label>
                </el-form-item>

                <el-form-item label="密码" prop="pass">
                    <el-input placeholder="请输入密码" type="password" v-model="ruleForm.password" style="width:80%"
                        show-password=show-password></el-input>
                    <label style="vertical-align:central"> <input type="checkbox"
                            style="width:15px;height:15px;vertical-align:middle;margin-top:-3px;" title="记住密码"
                            v-model="remember" />记住密码</label>
                </el-form-item>
                <el-form-item>
                    <div class="foot">
                        <el-button type="primary" id="my_button" @click="handleLogin">登录</el-button>
                    </div>
                </el-form-item>
                <el-form-item>
                    <div class="foot">
                        <el-button type="primary" id="my_button" @click="handleLogin">登录</el-button>
                    </div>
                </el-form-item>
            </el-form>
        </el-card>
    </div>
</template>

<script>

export default {
    name: 'Login',
    data() {
        return {
            remember: false,//记住密码是否勾选
            rememberMe: false,
            yzm: '',
            ruleForm: {
                username: '123456',
                password: '123456'
            },
            formLabelWidth: '',

            // 表单规则验证
            rules: {
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    { max: 18, message: '用户名格式错误', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' }
                ],
            },
            loginform: {
                act: '',
                pwd: '',
            },
            username: '',
            passwordType: 'password',
            capsTooltip: false,
            loading: false,
            showDialog: false,
            title: '系统登录',
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
        // 登录
        handleLogin() {
            var obj = JSON.parse(JSON.stringify(this.loginform))
            this.$store.dispatch('user/login', obj).then(() => {
                this.loginVisible = false
                this.username = sessionStorage.getItem('SET_NAME')
                // this.$router.push({ path: this.redirect || '/', query: this.otherQuery })
            })
            // this.$router.push('/management/projectManagement')
        }
    },


}
</script>

<style lang="scss" scoped>
#background {
    background: url("~@/assets/backphoto.jpg");
    width: 100%; //大小设置为100%
    height: 100%; //大小设置为100%
    position: fixed;
    background-size: 100% 100%;

}

.box-card {
    margin-top: 7%;
    margin-left: 50%;
    margin-bottom: auto;
    width: 580px;
    height: 500px;
    text-align: center;
}
</style>