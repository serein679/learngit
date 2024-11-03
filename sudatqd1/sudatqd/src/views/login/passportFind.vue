<template  >
    <div style="margin-top: 8px;background-color: antiquewhite;">
        <div style="background-color: antiquewhite;">
            <el-row>
                <el-col :span="3" :offset="1">
                    <h3 class="location">当前位置：</h3>
                </el-col>
                <el-col :span="10" style="margin-top: 43px;">
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item :to="{ path: '/introduction' }">找回密码</el-breadcrumb-item>
                        <!-- <el-breadcrumb-item><a href="/">纸张——档案</a></el-breadcrumb-item> -->
                    </el-breadcrumb>
                </el-col>
            </el-row><!--导航-->
            <div class="main-area">
                <el-row>
                    <el-col :span="16" style="margin-left: 15%;">
                        <el-form   label-width="100px" class="demo-ruleForm"
                            size="large" style="margin-top: 10%;">
                            <el-form-item label="用户名" prop="userName" label-width="100px" placeholder="请输入用户名"
                                size="large">
                                <el-input v-model="ruleForm.username" style="width: 500px;margin-left: 0px;"></el-input>
                            </el-form-item>
                            <el-form-item label="密保问题" prop="quesId" label-width="100px">
                                <el-select v-model="ruleForm.quesId" placeholder="请选择"
                                    style="width: 500px;margin-left: 0px;">
                                    <el-option v-for="item in options" :key="item.quesId" :label="item.question"
                                        :value="item.quesId">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="答案" prop="answer" label-width="100px" placeholder="请输入答案">
                                <el-input v-model="ruleForm.answer" style="width: 500px;"></el-input>
                            </el-form-item>
                        </el-form>
                        <el-form>
                            <el-form-item style="margin-top: 10%;">
                                <el-button type="primary" @click="submitForm('ruleForm')" style="">确定</el-button>
                                <el-button @click="resetForm('ruleForm')">重置</el-button>
                                <el-button @click="goback">取消</el-button>
                            </el-form-item>
                        </el-form>
                    </el-col>
                </el-row>
            </div>
        </div>
    </div>
</template>

<script>
import { getQuestions, updateUserPwdByMiBao } from '@/api/question'
import { checkQues } from '@/api/login'

export default {
    data() {
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
        let validatePass2 = (rule, value, callback) => {
            if (value === "") {
                callback(new Error("请再次输入密码"));
            } else if (value !== this.ruleForm.newport) {
                callback(new Error("两次输入密码不一致!"));
            } else {
                callback();
            }
        };
        return {
            ruleForm: {
                username: '',
                answer: '',
                quesId: '',
                
            },
            options: [],
            rules: {
                userAccount: [
                    { required: true, message: '请输入你的用户名', trigger: 'blur' },
                    { min: 8, max: 18, message: '账号在8-18位之间', trigger: 'blur' }
                ],
                answer: [
                    { required: true, message: '请输入你密保答案', trigger: 'blur' },
                    { min: 2, max: 60, message: '', trigger: 'blur' }
                ],
                questionId: [{ required: true, message: '请选择你密保问题', trigger: 'change' },],
                newport: [
                    { required: true, message: '请输入新密码', trigger: 'change' },
                    {
                        validator: passwordreg,
                    },
                ],
                rnewport: [
                    { required: true, trigger: 'blur', validator: validatePass2 },

                ]
            }
        };
    },
    methods: {
        submitForm() {
                    checkQues(this.ruleForm).then(res => {
                        if (res.code != 200) {
                            this.$message({
                                type: 'error',
                                message: res.message
                            });
                            return;
                        }
                        this.$message({
                            type: 'success',
                            message: "验证成功！"
                        });
                        this.$router.push('/login/passportFin')
                    });
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },

        goback() {
            this.$router.push('/')
        },//返回上一页
        //获取密保问题列表
        getQuestions() {
            getQuestions().then(res => {
                console.log(res.data, 520);
                this.options = res.data;
            })
        },
    },
    mounted() {
        this.getQuestions();
    }
}
</script>

<style scoped>
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

.el-breadcrumb__item {
    font-size: 20px;
    font-family: "STKaiti";
}

.main-area {
    /* margin-left:  0px; */
    border-top: solid rgb(134, 116, 21);
    width: 100%;
    height: 780px;
    background-color: antiquewhite;
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

.el-breadcrumb__item {
    font-size: 20px;
    font-family: "STKaiti";
}

.app-main {
    background-color: antiquewhite;
}

/* .el-main {
    display: block;
    flex: 1;
    flex-basis: auto;
    overflow: auto;
    box-sizing: border-box;
    padding: 20px;
    background-color: antiquewhite;
} */

.box-card {
    margin-top: 0%;
    margin-left: 52%;
    margin-bottom: auto;
    width: 620px;
    height: 850px;
    text-align: center;
}
</style>
