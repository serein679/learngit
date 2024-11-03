<template>
    <div slot="title">
        <div style="padding: 10px;">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/management/introManagement' }">纤维原料详情管理</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/management/editorIntro' }">编辑纤维原料</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <span style="font-family: '楷体'; font-size: 26px;">编辑纤维原料</span>
        <el-tabs class="el-tab-div" style="font-family: 楷体;font-size: 26px;">
            <el-form :model="oneFiber" ref="oneFiber" style="font-size: 32px !important" :rules="rules">
                <el-form-item label="原料名称" :label-width="formLabelWidth" prop="fiberType">
                    <el-input v-model="oneFiber.fiberType"></el-input>
                </el-form-item>
                <el-form-item label="原料特征" :label-width="formLabelWidth" prop="characteristic">
                    <el-input v-model="oneFiber.characteristic" type="textarea" :rows="5" prop="characteristic"></el-input>
                </el-form-item>

            </el-form>
            <div class="dialog-footer">
                <el-button type="primary" @click="changeProject()">修 改</el-button>
                <el-button type="primary" @click="back">取 消</el-button>
            </div>
        </el-tabs>
    </div>
</template>
<script>
// import Carousel from '@/components/Carousel.vue';
import './filters.js'
import { getOnePaper, editPaper } from "@/api/paper"
// import WangEditor from '@/components/WangEditor.vue'
import { getOneInstitution, getOneIn, getOnePa, editInstitution } from '@/api/institution'
import { getOneFiber, getOneFi, getFibers, editFiber } from '@/api/fiber'



export default {
    components: {
        // WangEditor
    },
    data() {
        return {
            rules: {
                fiberType: [
                    { required: true, message: '请输入纤维原料名称', trigger: 'blur' },
                ],
                characteristic: [
                    { required: true, message: '请输入纤维特点', trigger: 'blur' },
                    // { max: 18, message: '用户名格式错误', trigger: 'blur' }
                ],
                process: [
                    { required: true, message: '请输入制作流程', trigger: 'blur' },
                ],
                institutionLocation: [
                    { required: true, message: '请输入地点', trigger: 'blur' },
                    // { max: 18, message: '用户名格式错误', trigger: 'blur' }
                ],
                paperType: [
                    { required: true, message: '请选择纸张类型', trigger: 'blur' },
                    // { max: 18, message: '用户名格式错误', trigger: 'blur' }
                ],
                fileName: [
                    { required: true, message: '请上传图片', trigger: 'blur' },
                    // { max: 18, message: '用户名格式错误', trigger: 'blur' }
                ],
                location: [
                    { required: true, message: '请输入产地', trigger: 'blur' },
                    // { max: 18, message: '用户名格式错误', trigger: 'blur' }
                ],
                institutionDate: [
                    { required: true, message: '请输入机构出现时间', trigger: 'blur' },
                ],
            },
            activeName1: '1',
            activeName: '1',
            users: [],
            paperId: '',
            options: [],
            value: [],
            list: [],
            loading: false,
            isShow: true,
            dialogVisible7: false,
            adVisible7: false,
            dialogVisible9: false,
            adVisible9: false,
            dialogVisible8: false,
            adVisible8: false,
            teachers: [],
            oneFiber: [],
            smid: "",
            paperList: [],
            oneInstitution: [],
            teachers: [],
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
            editor: '',
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
            fileList: [], // 用于存储文件列表
            fileList1: [], // 用于存储文件列表
            headers: { /* 你的请求头信息，如果有的话 */ },
            dialogVisible: false,
            adVisible: false,
            dialogVisible1: false,
            adVisible1: false,
            dialogVisible2: false,
            adVisible2: false,
            formLabelWidth: '120px',
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
        // this.getQuery();
        this.gettingData();
        // this.getTeacherIntro();
        // this.getUsers();
        this.getOneFi(this.fiberId);

    },

    watch: { '$route': 'gettingData' },
    methods: {
        getTeacherIntro() {
            TeacherIntro().then(response => {
                this.teachers = response.data
            })
        },
        filterTag(value, row, column) {
            const property = column["property"];
            return row[property] === value;
        },
        gettingData() {
            var queryFiberId = JSON.parse(this.$Base64.decode(this.$route.query.info))
            this.fiberId = queryFiberId;
        },
        _filterChange() {
            this.totalSize = this.$refs.table.projectList.length;
            console.log(this.totalSize);
        },
        sendWangEditor(val) {
            this.oneProjects.hyperlinks = val;//去掉p标签
        },

        indexMethods(index) {
            // 当前页数 - 1 * 每页数据条数 + index + 1 （ index 是索引值，从0开始）
            return (this.currentPage - 1) * this.pageSize + index + 1;
        },

        // 文件上传前的处理
        beforeFileUpload(file) {
            // 这里可以添加对文件的一些检查，比如文件的大小和类型等
            const isLt10M = file.size / 1024 / 1024 < 10;
            if (!isLt10M) {
                this.$message.error('上传的文件大小不能超过 10MB!');
                return false;
            } else {
                // 保存文件对象
                this.file = file;
                this.fileName = file.name;
                return false; // 返回false不会自动上传
            }
        },

        getOneFi(fiberId) {
            getOneFi(fiberId).then((response) => {
                this.oneFiber = response.data[0];
                // this.fileName = this.oneFiber.institutionPicture;
                console.log(this.oneFiber);
            });
        },
        changeProject() {
            console.log(this.oneInstitution); // 打印 oneresource 对象
            if (this.oneInstitution.institutionName == "") {
                this.$message({
                    type: 'warning',
                    message: '机构名称为空，请填写！'
                })
                return false;
            }
            else {
                this.$refs.oneFiber.validate(valid => {
                    if (valid) {
                        this.$confirm('您确定要修改该原料吗?', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(() => {
                            // 创建一个 FormData 对象
                            const formData = new FormData();
                            formData.append('file', this.file);
                            // 添加其他表单数据
                            formData.append('fiberId', this.oneFiber.fiberId);
                            formData.append('fiberType', this.oneFiber.fiberType);
                            formData.append('characteristic', this.oneFiber.characteristic);
                            editFiber(formData).then(response => {
                                if (response.message == "操作成功") {
                                    this.$router.push('/management/introManagement')
                                    this.$message({
                                        type: 'success',
                                        message: "修改成功！"
                                    })
                                    this.dialogVisible = false
                                    // this.getProjects()
                                } else if (response.code == 500) {
                                    this.$message({
                                        type: 'error',
                                        message: '纤维原料名重复！'
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
                    } else {
                        console.log('error submit!!')
                        return false
                    }
                })
            }
        },
        back() {
            this.$router.push('/management/introManagement')
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