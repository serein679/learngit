<template>
    <div> <!--添加专业对话框-->
        <div style="padding: 10px;">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/management/userManagement' }">用户管理</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/management/addUser' }">新增管理员</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div slot="title">
            <!-- <span style="font-family: '楷体'; font-size: 20px;">新增纸张</span> -->
        </div>
        <span style="font-family: '楷体'; font-size: 26px;">新增管理员</span>
        <el-tabs class="el-tab-div" style="font-family: 楷体;font-size: 26px;">
            <el-form ref="ruleForm" :model="ruleForm" :rules="rules" style="font-size: 32px !important">
                <el-form-item label="用户账号:" :label-width="formLabelWidth" prop="userAccount">
                    <el-input v-model="ruleForm.userAccount" autocomplete="off" placeholder="请输入邮箱"
                        style="width: 65%;"></el-input>
                </el-form-item>
                <el-form-item label="头　　像:" :label-width="formLabelWidth">
                    <!-- <el-input v-model="addteacher.photo"></el-input> -->
                    <el-input v-model="fileName" :readonly='true' placeholder="文件名" style="width:65%;" clearable>
                    </el-input>
                    <span>
                        <el-upload class="upload-demo" ref="upload" action="doUpload" :limit="1" :file-list="fileList" :auto-upload="true"
                        :before-upload="beforeFileUpload">
                            <el-button slot="trigger" type="primary" style="margin-left: 100%;margin-top: 9px;border: 1;">
                                上传文件<i class="el-icon-upload el-icon--right"></i></el-button>
                            <div slot="tip" class="el-upload__tip" style="margin-left: 230px;">只能上传1M以内的文件</div>
                        </el-upload>
                    </span>
                    <!-- <img :src="addteacher.photo" min-width="70" height="90" /> -->
                </el-form-item>
                <el-form-item label="昵　　称:" :label-width="formLabelWidth" prop="userName">
                    <el-input v-model="ruleForm.userName" autocomplete="off" placeholder="请输入昵称"
                        style="width: 65%;"></el-input>
                </el-form-item>
                <el-form-item label="密　　码:" :label-width="formLabelWidth" prop="userPass">
                    <el-input v-model="ruleForm.userPass" autocomplete="off" placeholder="请输入密码" show-password
                        style="width: 65%;"></el-input>

                </el-form-item>
                <!-- <el-form-item label="邮　　箱:" prop="email" :label-width="formLabelWidth">
                    <el-input v-model="ruleForm.email" autocomplete="off" placeholder="请输入邮箱"
                        style="width: 65%;"></el-input>
                </el-form-item> -->
                <el-form-item label="密保问题:" :label-width="formLabelWidth" prop="quesId">
                    <el-select v-model="ruleForm.quesId" placeholder="请选择" style="width: 65%;margin-left: 0px;">
                        <el-option v-for="item in options" :key="item.quesId" :label="item.question" :value="item.quesId">
                        </el-option>
                    </el-select>

                </el-form-item>
                <el-form-item label="密保答案:" :label-width="formLabelWidth" prop="passAnswer">
                    <el-input v-model="ruleForm.passAnswer" autocomplete="off" placeholder="请输入答案"
                        style="width: 65%;"></el-input>
                </el-form-item>
            </el-form>
            <div class="dialog-footer">
                <el-button type="primary" @click="back">取 消</el-button>
                <el-button type="primary" style="border: 0; border: 1;"
                    @click="submitForm('ruleForm'), SelectUser(ruleForm.userName), SelectUser1(ruleForm.userAccount), SelectUser2(ruleForm.email)">注册</el-button>
            </div>
        </el-tabs>
    </div>
</template>
<script>
import './filters.js'
import Pagination from "@/components/Pagination";
import errorLog from '@/store/modules/errorLog';
// import WangEditor from '@/components/WangEditor.vue'
import { addPaper } from "@/api/paper"
import { addUser1, GetUsers, SelectUser, SelectUser1, SelectUser2 } from '@/api/users'
import { getQuestions } from '@/api/question'


export default {
    components: {
        // WangEditor
    },
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

            rules: {
                userAccount: [
                    { required: true, message: '请输入账号', trigger: 'blur' },
                    { max: 18, message: '账号少于18位', trigger: 'blur' },
                    { min: 8, message: '账号最少8位', trigger: 'blur' }

                ],
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
                passAnswer: [
                    { required: true, message: '请输入密保答案', trigger: 'blur' },

                ],
                photo: [
                    { required: true, message: '请选择头像', trigger: 'blur' },

                ],
                quesId: [
                    { required: true, message: '请选择密保问题', trigger: 'blur' },
                ],
                email: [
                    { required: true, message: "请输入邮箱", trigger: "blur" },
                    {
                        validator: checkEmail,
                        min: 9,
                        max: 18,
                        message: "邮箱格式错误",
                        trigger: "blur",
                    },
                ],

            },
            activeName1: '1',
            activeName: '1',
            users: [],
            options: [],
            value: [],
            list: [],
            ruleForm: {
                file: " ",
                userName: '',
                userPass: '',
                email: '',
                userAccount: '',
                quesId: '',
                passAnswer: '',
            },
            loading: false,
            isShow: true,
            dialogVisible7: false,
            adVisible7: false,
            dialogVisible9: false,
            adVisible9: false,
            dialogVisible8: false,
            adVisible8: false,
            smid: "",
            currentPage: 1,// 默认显示第几页
            pageSizes: [2, 5, 10, 20, 30, 50],// 个数选择器（可修改）
            pageSize: 10,// 默认每页显示的条数（可修改）
            total: '',// 总记录数
            total1: '',
            total2: '',
            total5: '',
            selectName: [],
            selectName7: [],
            query: "",//检索词
            order: 0,//排序
            oneProjects: [],
            fileName: "",//上传的文件名称
            fileName1: "",//上传的文件名称
            file: null,
            dialogVisible5: false,
            adVisible5: false,
            total4: '',
            typeName: {
                '0': "手工纸",
                '1': "机械纸",
                '2': "半手工半机械纸",
            },//将资源类型转换为对应文字
            typelist1: [{
                paperType: 0,
                typeName: "手工纸",
            }, {
                paperType: 1,
                typeName: "机械纸",
            }, {
                paperType: 2,
                typeName: "半手工半机械纸",
            }], // 资源类型列表
            fileList: [], // 用于存储文件列表
            fileList1: [], // 用于存储文件列表
            dialogVisible: false,
            adVisible: false,
            dialogVisible1: false,
            adVisible1: false,
            dialogVisible2: false,
            adVisible2: false,
            formLabelWidth: '120px',
            // addPaper: [],
            firstTime: '',
            addPaper: {
                paperName: "",
                paperDes: '',
                paperType: '',
                paperId: '',
                site: '',
                location: '',
                process: '',
                picture: '',
                firstTime: '',
                // projectContent: '',
                // status: '',
            },// 新增表单
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
        this.chooseTimeRange();
        this.getQuestions();
        this.GetUsers();


    },
    computed: {
        // listSet() {
        //   return this.projectList.map(item => {
        //     return { value: `${item.projectId}`, label: `${item.projectName}` };
        //   });
        // },
    },
    methods: {

        // getTeacherIntro() {
        //   TeacherIntro().then(response => {
        //     this.teachers = response.data
        //   })
        // },
        chooseTimeRange(firstTime) {
            this.firstTime = firstTime;
            console.log(this.firstTime)
        },

        filterTag(value, row, column) {
            const property = column["property"];
            return row[property] === value;
        },
        _filterChange() {
            this.totalSize = this.$refs.table.projectList.length;
            console.log(this.totalSize);
        },
        sendWangEditor(val) {
            this.oneProjects.hyperlinks = val;//去掉p标签
        },

        reload() {//局部刷新
            this.isShow = false;
            this.$nextTick(() => {
                this.isShow = true;
                this.$refs.table.bodyWrapper.scrollTop = 0;
            })
        },
        remoteMethod(query) {
            if (query !== '') {
                this.loading = true;
                SelectProject(query).then((response) => {
                    this.projectList = response.data;
                });
                setTimeout(() => {
                    this.loading = false;
                    this.options = this.listSet.filter(item => {
                        return item.label.toLowerCase()
                            .indexOf(query.toLowerCase()) > -1;
                    });
                }, 1000);
            } else {
                this.options = [];
            }
        },

        back() {
            this.$router.push('/management/userManagement')
        },
        selectList1() {
            Projects().then((response) => {
                this.selectName = response.data;
            })
        },
        resetQuery() {
            //重置查询条件
            this.query = '';
            //分页刷新,回到首页
            this.currentPage = 1;
            this.pageSize = 10;
            //重置筛选条件
            this.typefilter = '';
            this.coursefilter = '';
            this.getProjects();
        },

        indexMethods(index) {
            // 当前页数 - 1 * 每页数据条数 + index + 1 （ index 是索引值，从0开始）
            return (this.currentPage - 1) * this.pageSize + index + 1;
        },
        // 获取所有课程
        // 文件上传前的处理
        beforeFileUpload(file) {
            // 这里可以添加对文件的一些检查，比如文件的大小和类型等
            const isLt1M = file.size / 1024 / 1024 < 1;
            if (!isLt1M) {
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
            // this.$router.push('/management/projectManagement')
            // this.$router.push('/dashborad/index')
        },
        beforeFileUpload(file) {
            // 这里可以添加对文件的一些检查，比如文件的大小和类型等
            const isLt1M = file.size / 1024 / 1024 < 1;
            console.log(isLt1M)
            if (!isLt1M) {
                this.$message.error('上传的文件大小不能超过 1MB!');
                return false;
            } else {
                // 保存文件对象
                this.file = file;
                this.fileName = file.name;
                console.log(fileName)
                // return false; // 返回false不会自动上传
            }

        },
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    SelectUser(this.ruleForm.userName).then(response => {
                        this.userList = response.data
                        this.name = response.data.length
                        console.log(this.name)
                    })
                    const userAccount1 = JSON.stringify(this.ruleForm.userAccount)
                    SelectUser1(JSON.stringify(this.ruleForm.userAccount)).then(response => {
                        this.userList1 = response.data
                        this.name1 = response.data.length
                        console.log(this.name1)
                    })
                    
                    setTimeout(() => {
                        if (this.name1 != 0) {
                            this.$message({
                                type: 'false',
                                message: "账号已存在！"
                            });
                            console.log('账号已存在！')
                            return false;
                        } else {
                            if (this.name != 0) {
                                this.$message({
                                    type: 'false',
                                    message: "用户名已存在！"
                                });
                                console.log('用户名已存在！')
                                return false;
                            }
                            else {
                                

                                    this.$confirm('您确定要注册吗?', '提示', {
                                        confirmButtonText: '确定',
                                        cancelButtonText: '取消',
                                        type: 'warning'
                                    }).then(() => {
                                        // console.log(this.ruleForm)
                                        const formData = new FormData();
                                        // 'file' 是文件的名字，file 是 File 对象
                                        formData.append('email', this.ruleForm.email);
                                        formData.append('userName', this.ruleForm.userName);
                                        formData.append('userAccount', this.ruleForm.userAccount);
                                        formData.append('userPass', this.ruleForm.userPass);
                                        formData.append('quesId', this.ruleForm.quesId);
                                        formData.append('photo', this.ruleForm.photo);
                                        formData.append('passAnswer', this.ruleForm.passAnswer);
                                        formData.append('file', this.file);
                                        console.log(formData.get('email'))
                                        addUser1(formData).then(res => {
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
                                            this.$router.push('./userManagement')
                                        })
                                    }
                                    )

                                

                            }

                        }
                    }, 100);
                }
                else {
                    console.log('error submit!!');
                    return false;
                }

            }

            );

        },

        //删除
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
.breadcrumb {
    font-size: 25px;
    color: #080100;
    font-family: "楷体";
    margin-top: 40px;
}

.el-breadcrumb__item {
    font-size: 22px;
    font-family: "楷体";
}
</style>