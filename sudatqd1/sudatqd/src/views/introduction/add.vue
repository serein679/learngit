<template>
    <div style="background-color:antiquewhite"> <!--添加专业对话框-->
        <div style="padding: 10px;">
            <!-- <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/management/forumManagement' }">档案详情管理</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/management/addTeacher' }">新增档案</el-breadcrumb-item>
            </el-breadcrumb> -->
        </div>
        <div slot="title">
            <!-- <span style="font-family: '楷体'; font-size: 20px;">新增纸张</span> -->
        </div>
        <span style="font-family: '楷体'; font-size: 26px;">新增档案</span>
        <el-tabs class="el-tab-div" style="font-family: 楷体;font-size: 26px;width: 80%;margin-left: 13%;">
            <el-form :model="addArchive" ref="addArchive" style="font-size: 32px !important" :rules="rules">
                <el-form-item label="档案名称" :label-width="formLabelWidth" prop="archiveName">
                    <el-input v-model="addArchive.archiveName"></el-input>
                </el-form-item>
                <el-form-item label="档案图片" :label-width="formLabelWidth" style="width: 630px;">
                    <el-input v-model="fileName" :readonly='true' placeholder="文件名" style="width:388px;" clearable>
                    </el-input>
                    <el-upload class="upload-demo" ref="upload" action="doUpload" :limit="1" :file-list="fileList"
                        style="float:right" :before-upload="beforeFileUpload">
                        <el-button slot="trigger" type="primary">上传文件<i
                                class="el-icon-upload el-icon--right"></i></el-button>
                        <div slot="tip" class="el-upload__tip">只能上传1M以内的文件</div>
                    </el-upload>
                    <img :src="addPaper.picture" width="40%" style="float: center;margin-top: 10px;">
                </el-form-item>
                <el-form-item label="档案编号" :label-width="formLabelWidth" prop="bianhao">
                    <el-input v-model="addArchive.bianhao"></el-input>
                </el-form-item>
                <el-form-item label="档案详情" :label-width="formLabelWidth" prop="archiveContent">
                    <el-input v-model="addArchive.archiveContent" type="textarea" :rows="5"
                        prop="archiveContent"></el-input>
                </el-form-item>
                <el-form-item label="所属机构" :label-width="formLabelWidth" prop="institutionId">
                    <template slot-scope="props">
                        <el-select v-model="addArchive.institutionId" :props="institutionId" placeholder="请选择"
                            style="width:100%" @click.native="getCourses" filterable>
                            <el-option v-for="item in institutions" :key="item.institutionId" :label="item.institutionName"
                                :value="item.institutionId">
                            </el-option>
                        </el-select>
                    </template>
                </el-form-item>
                <el-form-item label="使用纸张" :label-width="formLabelWidth" prop="paperId">
                    <template slot-scope="props">
                        <el-select v-model="addArchive.paperId" :props="paperId" placeholder="请选择" style="width:100%"
                            @click.native="getPaper" filterable>
                            <el-option v-for="item in papers" :key="item.paperId" :label="item.paperName"
                                :value="item.paperId">
                            </el-option>
                        </el-select>
                    </template>
                </el-form-item>
                <el-form-item label="纤维情况" :label-width="formLabelWidth" prop="degFiber">
                    <el-input v-model="addArchive.degFiber" prop="degFiber"></el-input>
                </el-form-item>
                <el-form-item label="纤维老化情况" :label-width="formLabelWidth" prop="fiber">
                    <el-input v-model="addArchive.fiber" prop="fiber"></el-input>
                </el-form-item>
                <el-form-item label="尺寸" :label-width="formLabelWidth" prop="size">
                    <el-input v-model="addArchive.size" prop="size"></el-input>
                </el-form-item>
                <el-form-item label="厚度" :label-width="formLabelWidth" prop="thickness">
                    <el-input v-model="addArchive.thickness" prop="thickness"></el-input>
                </el-form-item>
                <el-form-item label="白度" :label-width="formLabelWidth" prop="whiteness">
                    <el-input v-model="addArchive.whiteness" prop="whiteness"></el-input>
                </el-form-item>
                <el-form-item label="形制" :label-width="formLabelWidth" prop="form">
                    <el-input v-model="addArchive.form" prop="form"></el-input>
                </el-form-item>
                <el-form-item label="打浆度" :label-width="formLabelWidth" prop="beatDeg">
                    <el-input v-model="addArchive.beatDeg" prop="beatDeg"></el-input>
                </el-form-item>
                <el-form-item label="帘纹" :label-width="formLabelWidth" prop="lianwen">
                    <el-input v-model="addArchive.lianwen" prop="lianwen"></el-input>
                </el-form-item>
                <el-form-item label="染色材料" :label-width="formLabelWidth" prop="color">
                    <el-input v-model="addArchive.color" prop="color"></el-input>
                </el-form-item>
                <el-form-item label="添加成分" :label-width="formLabelWidth" prop="composition">
                    <el-input v-model="addArchive.composition" prop="composition"></el-input>
                </el-form-item>
                <el-form-item label="责任者" :label-width="formLabelWidth" prop="author">
                    <el-input v-model="addArchive.author"></el-input>
                </el-form-item>
                <el-form-item label="档案出现时间" :label-width="formLabelWidth" style="margin-left: 1px;width: 300px;"
                    prop="firstTime">
                    <!-- <el-input v-model="oneproject.projectStartTime"></el-input> -->
                    <el-date-picker v-model="addArchive.firstTime" type="date" placeholder="选择日期" align="right"
                        :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="档案采集时间" :label-width="formLabelWidth" style="margin-left: 1px;width: 300px;"
                    prop="recordTime">
                    <!-- <el-input v-model="oneproject.projectStartTime"></el-input> -->
                    <el-date-picker v-model="addArchive.recordTime" type="date" placeholder="选择日期" align="right"
                        :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>
            </el-form>
            <div class="dialog-footer">
                <el-button type="primary" @click="addarchive()">提 交</el-button>
                <el-button type="primary" @click="back">取 消</el-button>
            </div>
        </el-tabs>
    </div>
</template>
<script>
import './filters.js'
import Pagination from "@/components/Pagination";
import errorLog from '@/store/modules/errorLog';
// import WangEditor from '@/components/WangEditor.vue'
import { getInstitutions, getOneInstitution, PageAllInstitution, SelectInstitution } from '@/api/institution'
import { getPaper, PageAllPaper, SelectPaper } from '@/api/paper'
import { addArchivee } from "@/api/archive"


export default {
    components: {
        WangEditor
    },
    data() {
        return {
            rules: {
                archiveName: [
                    { required: true, message: '请输入档案名称', trigger: 'blur' },
                ],
                archiveContent: [
                    { required: true, message: '请输入档案介绍', trigger: 'blur' },
                    // { max: 18, message: '用户名格式错误', trigger: 'blur' }
                ],
                bianhao: [
                    { required: true, message: '请输入档案编号', trigger: 'blur' },
                ],
                paperId: [
                    { required: true, message: '请选择纸张', trigger: 'blur' },
                    // { max: 18, message: '用户名格式错误', trigger: 'blur' }
                ],
                institutionId: [
                    { required: true, message: '请选择机构', trigger: 'blur' },
                    // { max: 18, message: '用户名格式错误', trigger: 'blur' }
                ],
                fileName: [
                    { required: true, message: '请上传图片', trigger: 'blur' },
                    // { max: 18, message: '用户名格式错误', trigger: 'blur' }
                ],
                fiber: [
                    { required: true, message: '请输入纤维老化情况', trigger: 'blur' },
                    // { max: 18, message: '用户名格式错误', trigger: 'blur' }
                ],
                size: [
                    { required: true, message: '请输入尺寸', trigger: 'blur' },
                ],
                form: [
                    { required: true, message: '请输入形制', trigger: 'blur' },
                    // { max: 18, message: '用户名格式错误', trigger: 'blur' }
                ],
                thickness: [
                    { required: true, message: '请输入厚度', trigger: 'blur' },
                ],
                whiteness: [
                    { required: true, message: '请输入白度', trigger: 'blur' },
                ],
                beatDeg: [
                    { required: true, message: '请输入打浆度', trigger: 'blur' },
                ],
                lianwen: [
                    { required: true, message: '请输入帘纹', trigger: 'blur' },
                    // { type: 'number', message: '请输入数字', trigger: 'blur' }

                ],
                color: [
                    { required: true, message: '请输入染色材料', trigger: 'blur' },
                ],
                composition: [
                    { required: true, message: '请输入成分', trigger: 'blur' },
                    // { max: 18, message: '用户名格式错误', trigger: 'blur' }
                ],
                author: [
                    { required: true, message: '请输入责任者', trigger: 'blur' },
                ],
                firstTime: [
                    { required: true, message: '请输入出现时间', trigger: 'blur' },
                ],
                recordTime: [
                    { required: true, message: '请输入记录时间', trigger: 'blur' },
                ],
            },
            activeName1: '1',
            activeName: '1',
            users: [],
            options: [],
            datefilter: new Date(1912, 0, 1, 0, 0),
            datefilter1: new Date(1949, 9, 1, 0, 0),
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
            addArchive: [],
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
            institutions: [],
            papers: [],
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
            typeName: {
                '0': "手工纸",
                '1': "机械纸",
                '2': "半手工半机械纸",
            },//将资源类型转换为对应文字
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
        this.getCourses();
        this.getPaper();
        // this.getQuery();
        // this.getProjects();
        // this.getTeacherIntro();
        // this.getUsers();

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
        getCourses() {
            getInstitutions().then(response => {
                this.institutions = response.data
                console.log(this.institutions)
            })
        },
        getPaper() {
            getPaper().then(response => {
                this.papers = response.data
                console.log(this.papers)
            })
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
            this.$router.push('/introduction/index')
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
                this.$message.error('上传的文件大小不能超过 1MB!');
                return false;
            } else {
                // 保存文件对象
                this.file = file;
                this.fileName = file.name;
                return false; // 返回false不会自动上传
            }

        },
        // 编辑按钮点击事件
        addarchive() {
            console.log(this.addArchive); // 打印 addresource 对象
            if (this.addArchive.archiveName == "") {
                this.$message({
                    type: 'warning',
                    message: '纸张名称为空，请填写！'
                })
                return false;
            }
            else {
                this.$refs.addArchive.validate(valid => {
                    if (valid) {
                        this.$confirm('您确定要提交该档案吗?', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(() => {
                            const formData = new FormData();
                            formData.append('file', this.file);
                            // 添加其他表单数据
                            formData.append('paperId', this.addArchive.paperId);
                            // formData.append('archiveId', this.addArchive.archiveId);
                            formData.append('archiveName', this.addArchive.archiveName);
                            formData.append('author', this.addArchive.author);
                            formData.append('institutionId', this.addArchive.institutionId);
                            formData.append('archiveContent', this.addArchive.archiveContent);
                            formData.append('size', this.addArchive.size);
                            formData.append('thickness', this.addArchive.thickness);
                            formData.append('whiteness', this.addArchive.whiteness);
                            formData.append('composition', this.addArchive.composition);

                            formData.append('form', this.addArchive.form);
                            formData.append('archiveStateType', this.addArchive.archiveStateType);
                            formData.append('beatDeg', this.addArchive.beatDeg);
                            formData.append('fiber', this.addArchive.fiber);
                            formData.append('degFiber', this.addArchive.degFiber);
                            formData.append('bianhao', this.addArchive.bianhao);
                            formData.append('color', this.addArchive.color);
                            formData.append('lianwen', this.addArchive.lianwen);
                            formData.append('firstTime', new Date(this.addArchive.firstTime));
                            formData.append('recordTime', new Date(this.addArchive.recordTime));
                            // formData.append('firstTime', this.onePaper.firstTime);
                            // formData.append('composition', this.addArchive.composition);
                            addArchivee(formData).then(response => {
                                if (response.message == "操作成功") {
                                    this.$router.push('/introduction/index')
                                    this.$message({
                                        type: 'success',
                                        message: "添加成功！"
                                    })
                                    // location.reload();//自动刷新
                                    this.adVisible = false
                                    this.$router.push('/introduction/index')

                                    // this.getProjects(order = 0)
                                } else if (response.message == "暂无权限") {
                                    this.$message({
                                        type: 'error',
                                        message: '暂无编辑权限！'
                                    })
                                    this.adVisible = false
                                } else if (response.code == 500) {
                                    this.$message({
                                        type: 'error',
                                        message: '档案名重复！'
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