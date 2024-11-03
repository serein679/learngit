<template >
    <!-- <div class="box1" :style="ratioStyle"> -->
    <div style="margin-top: 8px;background-color: antiquewhite;">
        <div style="margin:0px;top:140px ;background-color: antiquewhite;">
            <el-row>
                <el-col :span="3" :offset="1">
                    <h3 class="location">当前位置：</h3>
                </el-col>
                <el-col :span="10" style="margin-top: 43px;">
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                        <el-breadcrumb-item><a href="/">搜索</a></el-breadcrumb-item>
                    </el-breadcrumb>
                </el-col>
            </el-row><!--导航-->
            <div class="border-wrap" style="width: 220px;margin-left: 800px;top: 5px;">
                <span style="font-size: 24px;color: rgba(0, 0, 0, 0.613);">检索词：</span>
                <div style="float: right;font-size: 22px;color: rgba(0, 0, 0, 0.613);">
                    {{ this.name }}
                </div>
            </div>
            <div style=" font-family:楷体;font-size: 20px;left: 460px;text-align: left;margin-left: 2%;">
                搜索分类：
                共{{ total + total1 + total2 + total4 + total5 }}个结果
            </div>
            <el-tabs v-model="activeName" @tab-click="handleClick"
                style=" font-family:楷体;font-size: 18px;top: 2px;margin: 0%;">
                <el-tab-pane :label="'纸样库（' + total + ')'" name="first">
                    <div
                        style="margin: 0%;padding: 0%;background-color: antiquewhite;height: auto;min-height: calc(60vh -10px);">
                        <div
                            style="margin: 0%;padding: 0%;background-color: antiquewhite;height: auto;min-height: calc(60vh - 20px);">
                            <div style="margin-left: 20px;width: 300px;left: 30px;padding: 0%;">
                                相关结果共：{{ total }}个
                            </div>
                            <div class=" part">
                                <ul class="datelist" style="padding: 0;">
                                    <li v-for="item in paperList">{{ item.paperName }} <div
                                            style="float: left;width: 400px;">
                                            {{ new
                                                Date(item.firstTime).toLocaleDateString('zh').replaceAll('/', '-') }}
                                        </div>
                                        <el-button type="text" class="button" @click="select(item.paperId)">>>></el-button>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <!-- <el-row style="min-height: calc(50vh - 20px);">
                            <el-col :span="6" v-for="item in paperList">
                                <el-card :body-style="{ padding: '2px', }" shadow="hover" class="box-card">
                                    <img :src="item.picture" class="image" @click="select(item.paperId)">
                                    <div style="padding: 10px;">
                                        <span>{{ item.paperName }}</span>
                                        <div class="bottom clearfix">
                                            <span style="font-size: 18px;">诞生时间：{{ new
                                                Date(item.firstTime).toLocaleDateString('zh').replaceAll('/', '-') }}
                                            </span>
                                            <el-button type="text" class="button"
                                                @click="select(item.paperId)">>>></el-button>
                                        </div>
                                    </div>
                                </el-card>
                            </el-col>
                        </el-row> -->
                        <el-row style="height: 90px;">
                            <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                                :current-page="currentPage" :page-sizes="[5, 10, 20]" :page-size="5"
                                layout="total, sizes, prev, pager, next, jumper" :total="total">
                            </el-pagination>
                        </el-row>
                    </div>
                </el-tab-pane>
                <el-tab-pane :label="'档案库（' + total2 + ')'" name="third">
                    <div
                        style="margin: 0%;padding: 0%;background-color: antiquewhite;height: auto;min-height: calc(60vh - 20px);">
                        <div
                            style="margin: 0%;padding: 0%;background-color: antiquewhite;height: auto;min-height: calc(60vh - 20px);">
                            <div style="margin-left: 20px;width: 300px;left: 30px;padding: 0%;">
                                相关结果共：{{ total2 }}个
                            </div>
                            <div class=" part">
                                <ul class="datelist" style="padding: 0;">
                                    <li v-for="item in archiveLists">{{ item.archiveName }} <div
                                            style="float: left;width: 400px;">
                                            {{ new
                                                Date(item.firstTime).toLocaleDateString('zh').replaceAll('/', '-') }}
                                        </div>
                                        <el-button type="text" class="button"
                                            @click="select2(item.archiveId)">>>></el-button>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <!-- <el-row style="min-height: calc(50vh - 20px);">
                            <el-col :span="6" v-for="item in archiveLists">
                                <el-card :body-style="{ padding: '2px', }" shadow="hover" class="box-card">
                                    <img :src="item.pictureLocation" class="image" @click="select2(item.archiveId)">
                                    <div style="padding: 10px;">
                                        <span style="font-size: 25px;">{{ item.archiveName }}</span>
                                        <div class="bottom clearfix">
                                            <span style="font-size: 18px;">诞生时间：{{ new
                                                Date(item.firstTime).toLocaleDateString('zh').replaceAll('/',
                                                    '-') }} </span>

                                            <el-button type="text" class="button"
                                                @click="select2(item.archiveId)">>>></el-button>
                                        </div>
                                    </div>
                                </el-card>
                            </el-col>
                        </el-row> -->
                        <el-row style="height: 90px;">
                            <el-pagination background @size-change="handleSizeChange2"
                                @current-change="handleCurrentChange2" :current-page="currentPage2"
                                :page-sizes="[5, 10, 20]" :page-size="5" layout="total, sizes, prev, pager, next, jumper"
                                :total="total2">
                            </el-pagination>
                        </el-row>
                    </div>
                </el-tab-pane>
                <el-tab-pane :label="'机构库（' + total1 + ')'" name="second">
                    <div
                        style="margin: 0%;padding: 0%;background-color: antiquewhite;height: auto;min-height: calc(60vh - 20px);">
                        <div
                            style="margin: 0%;padding: 0%;background-color: antiquewhite;height: auto;min-height: calc(60vh - 20px);">
                            <div style="margin-left: 20px;width: 300px;left: 30px;padding: 0%;">
                                相关结果共：{{ total1 }}个
                            </div>
                            <div class=" part">
                                <ul class="datelist" style="padding: 0;">
                                    <li v-for="item in institutionList">{{ item.institutionName }} <div
                                            style="float: left;width: 400px;">
                                            {{ item.institutionLocation }}
                                        </div>
                                        <el-button type="text" class="button"
                                            @click="select1(item.institutionId)">>>></el-button>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <!-- <el-row style="min-height: calc(50vh - 20px);">
                            <el-col :span="6" v-for="item in institutionList">
                                <el-card :body-style="{ padding: '2px', }" shadow="hover" class="box-card">
                                    <img :src="item.institutionPicture" class="image" @click="select1(item.institutionId)">
                                    <div style="padding: 10px;">
                                        <span>{{ item.institutionName }}</span>
                                        <div class="bottom clearfix">
                                            <span style="font-size: 16px;">机构地点：{{ item.institutionLocation }} </span>

                                            <el-button type="text" class="button"
                                                @click="select1(item.institutionId)">>>></el-button>
                                        </div>
                                    </div>
                                </el-card>
                            </el-col>
                        </el-row> -->
                        <el-row style="height: 90px;">
                            <el-pagination background @size-change="handleSizeChange1"
                                @current-change="handleCurrentChange1" :current-page="currentPage1"
                                :page-sizes="[5, 10, 20]" :page-size="5" layout="total, sizes, prev, pager, next, jumper"
                                :total="total1">
                            </el-pagination>
                        </el-row>
                    </div>
                </el-tab-pane>
                <el-tab-pane :label="'大事记（' + total4 + ')'" name="forth">
                    <div style="margin:0px;top:140px ;background-color: antiquewhite;min-height: calc(60vh - 20px);">
                        <div
                            style="margin: 0%;padding: 0%;background-color: antiquewhite;height: auto;min-height: calc(60vh - 20px);">
                            <div style="margin-left: 20px;width: 300px;left: 30px;padding: 0%;">
                                相关结果共：{{ total4 }}个
                            </div>
                            <div class=" part">
                                <ul class="datelist" style="padding: 0;">
                                    <li v-for="item in his">{{ item.eventName }} <div style="float: left;width: 400px;">
                                            {{ new
                                                Date(item.eventDate).toLocaleDateString('zh').replaceAll('/', '-')
                                            }}
                                        </div>
                                        <el-button type="text" class="button" @click="select4(item.eventId)">>>></el-button>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div style="margin: 0%;padding: 0%;background-color: antiquewhite;height: auto;">
                            <el-row style="height: 90px;">
                                <el-pagination background @size-change="handleSizeChange4"
                                    @current-change="handleCurrentChange4" :current-page="currentPage4"
                                    :page-sizes="[5, 10, 20]" :page-size="5"
                                    layout="total, sizes, prev, pager, next, jumper" :total="total4">
                                </el-pagination>
                            </el-row>
                        </div>
                    </div>
                </el-tab-pane>
                <el-tab-pane :label="'纤维原料（' + total5 + ')'" name="fif">
                    <div style="margin:0px;top:140px ;background-color: antiquewhite;min-height: calc(60vh - 20px);">
                        <div
                            style="margin: 0%;padding: 0%;background-color: antiquewhite;height: auto;min-height: calc(60vh - 20px);">
                            <div style="margin-left: 20px;width: 300px;left: 30px;padding: 0%;">
                                相关结果共：{{ total5 }}个
                            </div>
                            <div class=" part">
                                <ul class="datelist" style="padding: 0;">
                                    <li v-for="item in fiberList1">{{ item.characteristic }} <div
                                            style="float: left;width: 400px;">
                                            {{ item.fiberType
                                            }}
                                        </div>
                                        <el-button type="text" class="button" @click="select5(item.fiberId)">>>></el-button>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div style="margin: 0%;padding: 0%;background-color: antiquewhite;height: auto;">
                            <el-row style="height: 90px;">
                                <el-pagination background @size-change="handleSizeChange5"
                                    @current-change="handleCurrentChange5" :current-page="currentPage5"
                                    :page-sizes="[5, 10, 20]" :page-size="5"
                                    layout="total, sizes, prev, pager, next, jumper" :total="total5">
                                </el-pagination>
                            </el-row>
                        </div>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>
  
<script>
import Carousel from '@/components/Carousel.vue'
import moment from 'moment'
import { getPaper, PageAllPaper, SelectPaper } from '@/api/paper'
import Pagination from "@/components/Pagination";
import { getInstitutions, getOneInstitution, PageAllInstitution, SelectInstitution } from '@/api/institution'
import { getArchivePic, PageAllArchive, getOneArchive, SelectArchive } from '@/api/archive'
import pinyin from 'pinyin'
import { getHistoricalEvents, SelectHis, PageAllHis } from '@/api/his'
import { PageAllFiber, SelectFiber } from '@/api/fiber'

// var pinyin = require("pinyin");


export default {
    name: 'Introduction',
    components: {
        Carousel,
        Pagination,
    },
    data() {
        return {
            activeName: 'first',
            datefilter: new Date(1912, 0, 1, 0, 0),
            datefilter1: new Date(1949, 9, 1, 0, 0),
            name: '',

            params: '',
            paperList: [],
            archiveLists: [],
            fiberList: [],
            fiberList1: [],
            paperList1: [],
            archiveLists1: [],
            his: [],
            currentDate: '',
            currentDate1: '',
            // currentDate: new Date(),
            institutionList: [],
            institutionList1: [],
            total: 0,
            total2: 0,
            total1: 0,
            total4: 0,
            total5: 0,
            archivePics: [],
            dialogVisible: false,
            adVisible: false,
            formLabelWidth: '120px',
            users: [], // 用户列表
            smid: "",
            value: '',
            value1: '',
            value2: '',
            value5: '',
            options: [],
            options1: [],
            options2: [],
            options5: [],
            currentPage: 1,// 默认显示第几页
            his1: [],
            eventId: '',
            // currentPage1: 1,// 默认显示第几页
            pageSizes: [20],// 个数选择器（可修改）
            pageSize: 5,// 默认每页显示的条数（可修改）
            pageSizes2: [20],// 个数选择器（可修改）
            pageSize2: 5,// 默认每页显示的条数（可修改）
            pageSizes4: [20],// 个数选择器（可修改）
            pageSize4: 5,// 默认每页显示的条数（可修改）
            pageSizes5: [20],// 个数选择器（可修改）
            pageSize5: 5,// 默认每页显示的条数（可修改）
            pageSizes1: [20],// 个数选择器（可修改）
            pageSize1: 5,// 默认每页显示的条数（可修改）
            isShow: true,
            currentPage1: 1,// 默认显示第几页
            currentPage5: 1,// 默认显示第几页
            // pageSizes: [ 20],// 个数选择器（可修改）
            pageSize1: 20,// 默认每页显示的条数（可修改）
            currentPage2: 1,// 默认显示第几页
            pageSize4: 5,// 默认每页显示的条数（可修改）
            currentPage4: 1,// 默认显示第几页
            // pageSizes: [ 20],// 个数选择器（可修改）
            pageSize2: 20,// 默认每页显示的条数（可修改）
            letters: ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"],

        }
    },
    mounted() {
        // 页面加载就渲染列表
        // this.getList(this.name);
    },

    created() {
        // this.getPaper()

        this.getArchivePic()
        this.gettingData()
        this.PageAllInstitution()
        this.PageAllArchive()
        this.PageAllFiber()
        this.getList()
        this.PageAllHis()

        // this.getFirstPin()
        // this.params = JSON.parse(this.$route.query.params) // 获取从router中传过来的参数
        // console.log('数据是：', this.params)

    },
    // computed: {
    //     listSet() {
    //         return this.archiveLists1.map(item => {
    //             return { value1: `${item.archiveId}`, label1: `${item.archiveName}` };
    //         });
    //     },
    //     listSet1() {
    //         return this.paperList1.map(item => {
    //             return { value: `${item.paperId}`, label: `${item.paperName}` };
    //         });
    //     },
    //     listSet2() {
    //         return this.institutionList1.map(item => {
    //             return { value2: `${item.institutionId}`, label2: `${item.institutionName}` };
    //         });
    //     },

    // },
    watch: { '$route': 'gettingData' },
    methods: {
        gettingData() {
            var queryName = this.$route.query.info
            // var queryName = this.$route.params.info
            this.name = queryName;
            console.log(this.name)
        },
        windows(params) {
            // console.log(this.value)
            if (params !== '') {
                console.log(params)
                let routeDate = this.$router.resolve({
                    path: './fullySearch',
                    query: {
                        info: params
                    }
                });
                window.open(routeDate.href, '_blank')
                this.params = '';
                // this.value = '';
                // value = '';
            }
        },
        verifyLogin() {
            let check = sessionStorage.getItem('SET_TOKEN')
            return check
        },
        filterTag(value, row, column) {
            const property = column["property"];
            return row[property] === value;
        },

        reload() {//局部刷新
            this.isShow = false;
            this.$nextTick(() => {
                this.isShow = true;
                // this.$refs.table.bodyWrapper.scrollTop = 0;
            })
        },
        remoteMethod(query) {
            if (query !== '') {
                this.loading = true;
                SelectArchive(query).then((response) => {
                    this.archiveLists1 = response.data;
                });
                setTimeout(() => {
                    this.loading = false;
                    this.options = this.listSet.filter(item => {
                        return item.label1.toLowerCase()
                            .indexOf(query.toLowerCase()) > -1;
                    });
                }, 1000);
            } else {
                this.options = [];
            }
        },
        remoteMethod1(query) {
            if (query !== '') {
                this.loading = true;
                SelectPaper(query).then((response) => {
                    this.paperList1 = response.data;
                });
                setTimeout(() => {
                    this.loading = false;
                    this.options1 = this.listSet1.filter(item => {
                        return item.label.toLowerCase()
                            .indexOf(query.toLowerCase()) > -1;
                    });
                }, 1000);
            } else {
                this.options1 = [];
            }
        },
        remoteMethod2(query) {
            if (query !== '') {
                this.loading = true;
                SelectInstitution(query).then((response) => {
                    this.institutionList1 = response.data;
                });
                setTimeout(() => {
                    this.loading = false;
                    this.options2 = this.listSet2.filter(item => {
                        return item.label2.toLowerCase()
                            .indexOf(query.toLowerCase()) > -1;
                    });
                }, 1000);
            } else {
                this.options2 = [];
            }
        },
        remoteMethod5(query) {
            if (query !== '') {
                this.loading = true;
                SelectFiber(query).then((response) => {
                    this.fiberList = response.data;
                });
                setTimeout(() => {
                    this.loading = false;
                    this.options5 = this.listSet5.filter(item => {
                        return item.label5.toLowerCase()
                            .indexOf(query.toLowerCase()) > -1;
                    });
                }, 1000);
            } else {
                this.options5 = [];
            }
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
            this.PageAllArchive();
        },
        resetQueryE() {
            //重置查询条件
            this.datefilter = '';
            this.datefilter1 = '';
            //分页刷新,回到首页
            this.currentPage = 1;
            this.pageSize = 10;
            //重置筛选条件
            // this.typefilter = '';
            // this.coursefilter = '';
            this.PageAllArchive();
        },
        resetQuery1() {
            //重置查询条件
            this.query = '';
            //分页刷新,回到首页
            this.currentPage = 1;
            this.pageSize = 10;
            //重置筛选条件
            this.typefilter = '';
            this.coursefilter = '';
            this.getList();
        },
        resetQuery2() {
            //重置查询条件
            this.query = '';
            //分页刷新,回到首页
            this.currentPage = 1;
            this.pageSize = 10;
            //重置筛选条件
            this.typefilter = '';
            this.coursefilter = '';
            this.PageAllInstitution();
        },
        resetQuery5() {
            //重置查询条件
            this.query = '';
            //分页刷新,回到首页
            this.currentPage = 1;
            this.pageSize = 10;
            //重置筛选条件
            this.typefilter = '';
            this.coursefilter = '';
            this.PageAllFiber();
        },
        handleClick(tab, event) {
            console.log(tab, event);
        },
        reload() {//局部刷新
            this.isShow = false;
            this.$nextTick(() => {
                this.isShow = true;
                // this.$refs.table.bodyWrapper.scrollTop = 0;
            })
        },

        getArchivePic() {
            getArchivePic().then(response => {
                this.archivePics = response.data
            })
        },

        handleSizeChange(val) {
            this.pageSize = val
            this.getList();
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            this.currentPage = val
            console.log(`当前页: ${val}`);
            this.getList();

        },
        handleSizeChange1(val) {
            this.pageSize1 = val
            this.PageAllInstitution();
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange1(val) {
            this.currentPage1 = val
            console.log(`当前页: ${val}`);
            this.PageAllInstitution();

        },
        handleSizeChange5(val) {
            this.pageSize5 = val
            this.PageAllFiber();
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange5(val) {
            this.currentPage5 = val
            console.log(`当前页: ${val}`);
            this.PageAllFiber();

        },
        handleSizeChange2(val) {
            this.pageSize2 = val
            this.PageAllArchive();
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange2(val) {
            this.currentPage2 = val
            console.log(`当前页: ${val}`);
            this.PageAllArchive();
        },
        handleSizeChange4(val) {
            this.pageSize4 = val
            this.PageAllHis();
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange4(val) {
            this.currentPage4 = val
            console.log(`当前页: ${val}`);
            this.PageAllHis();

        },
        getList() {
            // setTimeout(() => {
            var name = this.name
            var pageNum = this.currentPage
            var pageSize = this.pageSize
            var order = 0//排序
            console.log(name)
            PageAllPaper({ pageSize, order, pageNum, name }).then((response) => {
                if (response) {
                    this.total = response.data.total;
                    this.reload();//局部刷新
                    console.log(pageNum + "/" + pageSize + "/" + this.total + name);
                    this.paperList = response.data.paperList;
                } else {
                    console.log(errorLog)
                }
            })

            // }, 1000);

        },
        PageAllInstitution() {
            var name = this.name
            var pageNum = this.currentPage1
            var pageSize = this.pageSize1
            var order = 0//排序
            console.log(name)
            PageAllInstitution({ pageSize, order, pageNum, name }).then((response) => {
                if (response) {
                    this.total1 = response.data.total;
                    this.reload();//局部刷新
                    console.log(pageNum + "/" + pageSize + "/" + this.total);
                    this.institutionList = response.data.institutionList;
                } else {
                    console.log(errorLog)
                }
            })
        },
        PageAllFiber() {
            var name = this.name

            var pageNum = this.currentPage5
            var pageSize = this.pageSize5
            var order = 0//排序
            PageAllFiber({ pageSize, order, pageNum, name }).then((response) => {
                if (response) {
                    this.total5 = response.data.total;
                    this.reload();//局部刷新
                    console.log(pageNum + "/" + pageSize + "/" + this.total);
                    this.fiberList1 = response.data.fiberList;
                } else {
                    console.log(errorLog)
                }
            })
        },
        PageAllArchive() {
            var name = this.name
            var pageNum = this.currentPage2
            var pageSize = this.pageSize2
            var order = 0//排序
            var datefilter = this.datefilter;//类型筛选
            var datefilter1 = this.datefilter1;//类型筛选
            console.log(name)
            PageAllArchive({ pageSize, order, pageNum, datefilter, datefilter1, name }).then((response) => {
                if (response) {
                    this.total2 = response.data.total;
                    this.reload();//局部刷新
                    console.log(pageNum + "/" + pageSize + "/" + this.total);
                    this.archiveLists = response.data.archiveList;
                } else {
                    console.log(errorLog)
                }
            })
        },
        filter() {
            //分页刷新,回到首页
            this.currentPage = 1;
            this.pageSize = 10;
            this.PageAllArchive();
        },

        openDialog(id) {
            OneSubjectIntro({ id: id }).then(response => {
                this.dialogVisible = true
                this.onesubject = response.data[0]
            })
        },


        select(paperId) {
            if (paperId !== '') {
                console.log(paperId)
                this.$router.push({
                    path: './introduction/select',
                    query: {
                        info: this.$Base64.encode(paperId)
                    }
                })
            }
        },
        select5(fiberId) {
            if (fiberId !== '') {
                console.log(fiberId)
                this.$router.push({
                    path: './introduction/selectMa',
                    query: {
                        info: this.$Base64.encode(fiberId)
                    }
                })
            }
        },
        select2(archiveId) {
            if (archiveId !== '') {
                console.log(archiveId)
                this.$router.push({
                    path: './introduction/selectAr',
                    query: {
                        info: this.$Base64.encode(archiveId)
                    }
                })
            }
        },
        select1(institutionId) {
            if (institutionId !== '') {
                console.log(institutionId)
                this.$router.push({
                    path: './introduction/selectIn',
                    query: {
                        info: this.$Base64.encode(institutionId)
                    }
                })
            }
        },
        select4(eventId) {
            if (eventId !== '') {
                console.log(eventId)
                this.$router.push({
                    path: '/teachers/detail',
                    query: {
                        info: this.$Base64.encode(eventId)
                    }
                })
            }
        },
        PageAllHis() {
            var name = this.name
            var pageNum = this.currentPage4
            var pageSize = this.pageSize4
            var order = 0//排序
            PageAllHis({ pageSize, order, pageNum, name }).then((response) => {
                if (response) {
                    this.total4 = response.data.total;
                    this.reload();//局部刷新
                    console.log(pageNum + "/" + pageSize + "/" + this.total4);
                    this.his = response.data.historicalEventsList;
                } else {
                    console.log(errorLog)
                }
            })
        },
    },
    filters: {
        formatData(data) {
            return moment(data).format('YYYY-MM-DD')
        }
    },
    formatData(firstTime) {
        var s = new Date(firstTime).toLocaleString();
        return s;
    },
    formatData(time) {
        var s = new Date(time).toLocaleString();
        return s;
    },
}

</script>
  
<style scoped>
.content {
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
}

h2 {
    border-bottom: 3px solid #B03632;
}

.sousuo {
    bottom: 12px;
    width: 100px;
}

.name {
    font-family: "宋体";
    font-weight: bold;
    font-size: 20px;
}


.el-dialog-div {
    max-height: 120vh;
}

.head-box {
    background: #fff4dd;
    padding: 15px;
    margin: auto;
    width: 900px;
    text-align: center;
    margin-top: 20px;
    font-size: 25px;
    font-family: "华文中宋";
    letter-spacing: 3px;
}


.table-a {
    font-weight: bold;
    font-family: 华文中宋;
    font-size: 20px;
    text-decoration: none;
    color: #463131;
}

.tabs {
    text-align: center;
    top: 50px;
    left: 0px;
    ;
    position: relative;
}

.el-tabs {
    background-color: antiquewhite;
    color: #000;
    border-radius: 17px;
    left: 0px;
    top: 0px;
    width: 100%;
    /* height: 50px; */
    position: relative;
}

.part {
    max-width: 0;
}

.datelist li {
    position: relative;
    padding: 21px 150px 21px 0;
    color: #252525;
    font-size: 20px;
    border-bottom: 1px dashed rgb(216, 176, 123);
    width: 1100px;
    text-align: left;
    text-indent: 8em;
    background: url(../../assets/list_cir.png) left no-repeat;

}

::v-deep .el-tabs__nav-wrap::after {
    position: static !important;
}

.time {
    font-size: 13px;
    color: #999;
}

.breadcrumb {
    font-size: 25px;
    color: #080100;
    font-family: "华文中宋";
    margin-top: 40px;
}

.el-breadcrumb__item {
    font-size: 20px;
    font-family: "华文中宋";
}

.filter {
    font-size: 16px;
    font-family: "华文中宋";
}

.border-wrap {
    /* background: url(../../assets/border_side.png); */
    width: 2px;
    /* top: 10px; */
    height: 30px;
    content: "";
    /* position: absolute; */
    left: 80%;
    font-family: "楷体";
    font-size: 20px;
    border: 0;
    /* top: 200px; */
    vertical-align: middle;
    display: inline-block;
    /* background: url(../../assets/border.png) repeat-x; */
    /* background: url(../../assets/border_side.png) left center no-repeat; */
}

.location {
    font-size: 25px;
    color: rgb(160, 106, 5);
    font-family: "华文中宋";
    margin-top: 37px;
}

.bottom {
    margin-top: 13px;
    line-height: 12px;
}

.button {
    padding: 0;
    float: right;
}

input,
button,
textarea,
select {
    font: 20px/100% "楷体", Arial, Tahoma, Helvetica, sans-serif;
    outline: 0;
    border: 0;
    background: none;
    color: #100f0fc2;
}

.box-card {
    width: 280px;
    height: 160px;
    margin-left: 20px;
    margin-top: 10%;
    margin-bottom: 10%;
    cursor: pointer;

}

.image {
    width: 100%;
    height: 380px;
    display: block;
}

.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}

.clearfix:after {
    clear: both
}

/* 设置滑块颜色 */
::v-deep .el-tabs__active-bar {
    background-color: #AA7F55 !important;
}

/* 设置滑块停止位置 */
::v-deep .el-tabs__active-bar.is-top {
    height: 60px;
    width: 180px ! important;
    border-radius: 17px;
    top: 3px !important;
    left: 12px !important;
    position: absolute !important;
    z-index: 1;
}

/* 设置当前选中样式 */
::v-deep .el-tabs__item.is-active {
    color: #ffffff !important;
    z-index: 2;
    font-size: 24px;
}

/* 设置未被选中样式 */
::v-deep .el-tabs__item {
    padding: 0 20px !important;
    width: 250px;
    box-sizing: border-box;
    display: inline-block;
    position: relative !important;
    color: #000000 !important;
    z-index: 2;
    font-size: 24px;

}

.kw {
    -webkit-box-flex: 1;
    -ms-flex: 1;
    flex: 1;
    display: block;
    color: #707070;
    font-size: 14px;
    width: calc(100% - 62px);
}

.ok {
    color: #312d2d86;
    float: right;
    /* background-color: #d7ad6f00; */
    bottom: -150px;
    right: 30px;
    border-color: #d7ad6f00;
    background: url(../../assets/ico_search.png) no-repeat center center;
    background-size: 19px 19px;
    height: 25px;
    display: block;
    width: 24px;
    margin-right: 0px;
    margin-left: 0px;
    -webkit-flex-shrink: 0;
    -ms-flex-negative: 0;
    flex-shrink: 0;
    cursor: pointer;
}

.el-card {
    min-width: 380px;
    margin-right: 20px;
    transition: all .5s;
}

.el-card:hover {
    transform: scale(1.1);
}

.el-row .el-card {
    min-width: 75%;
    height: 100%;
    margin-right: 20px;
    transition: all .5s;

}
</style>