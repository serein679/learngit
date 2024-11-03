<template >
    <!-- <div class="box1" :style="ratioStyle"> -->
    <div style="margin-top: 8px;background-color: antiquewhite;">
        <el-row>
            <el-col :span="3" :offset="1">
                <h3 class="location">当前位置：</h3>
            </el-col>
            <el-col :span="10" style="margin-top: 43px;">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item :to="{ path: '/introduction' }">纸张基因</el-breadcrumb-item>
                    <el-breadcrumb-item><a href="/">关系图</a></el-breadcrumb-item>
                </el-breadcrumb>
            </el-col>
        </el-row><!--导航-->
        <div style="margin:0px;top:140px ;background-color: antiquewhite;">
            <el-tabs v-model="activeName" @tab-click="handleClick"
                style=" font-family:楷体;font-size: 26px;top: 10px;margin: 0%;">
                <el-tab-pane label="纸张——档案关系图" name="first" style="margin: 0%;height: 1800px;">
                    <!-- <el-tab-pane label="详情图" name="first" style="margin: 0%;"> -->
                    <div class="main-area" style="width: 100%;height:85s0px;margin-top: 1%;">
                        <el-card class="box">
                            <div id="main" style="width: 90%;height:1200px;background-color: antiquewhite;left: 5%; ">
                            </div>
                        </el-card>
                    </div>
                    <el-row>
                        <p style="text-align: right;" class="last-style" @click="goback"><el-button>返回</el-button></p>
                    </el-row>
                </el-tab-pane>
                <el-tab-pane label="机构——档案关系图" name="second" style="margin: 0%;height: 1800px;">
                    <div class="main-area" style="width: 100%px;height:85s0px;margin-top: 1%;">
                        <el-card class="box">
                            <div id="main1" style="width: 1101px;height:1200px;background-color: antiquewhite;left: 5%; ">
                            </div>
                        </el-card>
                    </div>
                    <el-row>
                        <p style="text-align: right;" class="last-style" @click="goback"><el-button>返回</el-button></p>
                    </el-row>
                </el-tab-pane>
            </el-tabs>


        </div>
    </div>
</template>
 
<script>
import * as echarts from 'echarts/core';
// 引入柱状图图表，图表后缀都为 Chart

import Carousel from '@/components/Carousel.vue'
import moment from 'moment'
// import { getFibers1, getOneFiber, SelectPaper } from '@/api/fiber'
import Pagination from "@/components/Pagination";
import { getPaperVo } from '@/api/paper'
import { getOneInstitution, getOneIn, getOnePa } from '@/api/institution'
import { Loading } from 'element-ui';
let loading;

// var pinyin = require("pinyin");


export default {
    name: 'Introduction',
    components: {
        Carousel,
        Pagination,
    },
    data() {
        return {
            paperList: [],
            archiveLists: [],
            paperList1: [],
            archiveLists1: [],
            currentDate: new Date(),
            institutionList: [],
            institutionList1: [],
            myGraphData: [],
            isShow: true,
            oneFiber: [],
            activeName: 'first',
            paperList: [],
            archiveLists: [],
            paperList1: [],
            archiveLists1: [],
            currentDate: new Date(),
            institutionList: [],
            institutionList1: [],
            archivePics: [],
            dialogVisible: false,
            adVisible: false,
            formLabelWidth: '120px',
            users: [], // 用户列表
            his: [],
            hisl: [],
            smid: "",
            value: '',
            value1: '',
            value2: '',
            options: [],
            options1: [],
            options2: [],
            myGraphData: [],
            activeName: 'first',
            isShow: true,
        }
    },

    created() {
        this.getPaperVo();
        this.getOneInstitution();
        this.startLoading()  // 开启加载
        loading.close(); // 关闭加载

    },
    computed: {


    },
    mounted() {
        // this.getOneFiber(this.fiberId);

        setTimeout(() => {
            var chartDom = document.getElementById('main');
            var myChart = echarts.init(chartDom);
            var listdata = [];
            var linksdata = [];
            var m = 0;
            var source = [];
            for (var i = 0; i < this.his.length; i++) {
                var node = this.his[i].paperName;
                var tx = [node];
                setNodeData(tx, m, 1, listdata);
                source.push(m);
                var Data = this.his[i].paperList1;
                setNodeData(Data, m + 1, 2, listdata);
                var sourceList = [];
                for (var n = m + 1; n < m + Data.length + 1; n++) {
                    sourceList.push(n);
                }
                setLinkData(sourceList, m, linksdata);
                m = m + Data.length + 1;
            }
            function setNodeData(arr, m, n, listdata) {
                var size = 33;
                for (var i = 0; i < arr.length; i++) {
                    listdata.push({
                        id: m++,
                        category: n,
                        name: arr[i],
                        symbolSize: size,
                        draggable: "true"
                    });
                }
            }
            function setLinkData(sourceList, m, links) {
                for (var i = 0; i < sourceList.length; i++) {
                    links.push({
                        "source": sourceList[i],
                        "target": m,
                        lineStyle: {
                            color: 'source',
                        }
                    })
                }
            }
            var tx7 = [];
            tx7.push('中心');
            setNodeData(tx7, m, 0, listdata);
            setLinkData(source, m, linksdata);
            let option = {
                title: {
                    text: "纸张——档案",
                    top: 60,
                    left: 440,
                    textStyle: {
                        color: '#292421',
                        fontSize: 25,
                    }
                },
                tooltip: {
                    formatter: '{b}'
                },
                backgroundColor: '#FFFFFF',
                legend: {
                    show: true,
                    data: [{
                        name: '中心',
                        icon: 'rect'
                    },
                    {
                        name: '纸张',
                        icon: 'roundRect'
                    }, {
                        name: '档案',
                        icon: 'circle'
                    }],
                    textStyle: {
                        color: '#292421'
                    },
                    icon: 'circle',
                    type: 'scroll',
                    orient: 'horizontal',
                    left: 60,
                    top: 70,
                    bottom: 20,
                    itemWidth: 25,
                    itemHeight: 25
                },
                animationDuration: 0,
                animationEasingUpdate: 'quinticInOut',
                series: [{
                    name: '知识图谱',
                    type: 'graph',
                    layout: 'force',
                    force: {
                        repulsion: 400,
                        gravity: 0.04,
                        edgeLength: 20,
                        layoutAnimation: true,
                    },
                    data: listdata,
                    links: linksdata,
                    categories: [
                        {
                            name: '中心',
                            symbol: 'rect',
                            label: {
                            }
                        }, {
                            name: '纸张',
                            symbol: 'roundRect'
                        }, {
                            name: '档案',
                            symbol: 'circle'
                        }],
                    roam: true,
                    label: {
                        show: true,
                        position: 'bottom',
                        formatter: '{b}',
                        fontSize: 15,
                        fontStyle: '600',
                    },
                    lineStyle: {
                        opacity: 0.9,
                        width: 2,
                        curveness: 0

                    }
                }]
            };
            myChart.setOption(option);
        }, 500)
        setTimeout(() => {
            var chartDom = document.getElementById('main1');
            var myChart = echarts.init(chartDom);
            var listdata = [];
            var linksdata = [];
            var m = 0;
            var source = [];
            for (var i = 0; i < this.hisl.length; i++) {
                var node = this.hisl[i].institutionName;
                var tx = [node];
                setNodeData(tx, m, 1, listdata);
                source.push(m);
                var Data = this.hisl[i].archiveList;
                setNodeData(Data, m + 1, 2, listdata);
                var sourceList = [];
                for (var n = m + 1; n < m + Data.length + 1; n++) {
                    sourceList.push(n);
                }
                setLinkData(sourceList, m, linksdata);
                m = m + Data.length + 1;
            }
            function setNodeData(arr, m, n, listdata) {
                var size = 33;
                for (var i = 0; i < arr.length; i++) {
                    listdata.push({
                        id: m++,
                        category: n,
                        name: arr[i],
                        symbolSize: size,
                        draggable: "true"
                    });
                }
            }
            function setLinkData(sourceList, m, links) {
                for (var i = 0; i < sourceList.length; i++) {
                    links.push({
                        "source": sourceList[i],
                        "target": m,
                        lineStyle: {
                            color: 'source',
                        }
                    })
                }
            }
            var tx7 = [];
            tx7.push('中心');
            setNodeData(tx7, m, 0, listdata);
            setLinkData(source, m, linksdata);
            let option = {
                title: {
                    text: "机构——档案",
                    top: 60,
                    left: 440,
                    textStyle: {
                        color: '#292421',
                        fontSize: 25,
                    }
                },
                tooltip: {
                    formatter: '{b}'
                },
                backgroundColor: '#FFFFFF',
                legend: {
                    show: true,
                    data: [{
                        name: '中心',
                        icon: 'rect'
                    },
                    {
                        name: '机构',
                        icon: 'roundRect'
                    }, {
                        name: '档案',
                        icon: 'circle'
                    }],
                    textStyle: {
                        color: '#292421'
                    },
                    icon: 'circle',
                    type: 'scroll',
                    orient: 'horizontal',
                    left: 60,
                    top: 70,
                    bottom: 20,
                    itemWidth: 25,
                    itemHeight: 25
                },
                animationDuration: 0,
                animationEasingUpdate: 'quinticInOut',
                series: [{
                    name: '知识图谱',
                    type: 'graph',
                    layout: 'force',
                    force: {
                        repulsion: 400,
                        gravity: 0.04,
                        edgeLength: 20,
                        layoutAnimation: true,
                    },
                    data: listdata,
                    links: linksdata,
                    categories: [
                        {
                            name: '中心',
                            symbol: 'rect',
                            label: {
                            }
                        }, {
                            name: '机构',
                            symbol: 'roundRect'
                        }, {
                            name: '档案',
                            symbol: 'circle'
                        }],
                    roam: true,
                    label: {
                        show: true,
                        position: 'bottom',
                        formatter: '{b}',
                        fontSize: 15,
                        fontStyle: '600',
                    },
                    lineStyle: {
                        opacity: 0.9,
                        width: 2,
                        curveness: 0

                    }
                }]
            };
            myChart.setOption(option);
        }, 500)

    },
    // watch: { '$route': 'gettingData' },
    methods: {

        goback() {
            this.$router.go(-1)
        },//返回上一页
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
                this.$refs.table.bodyWrapper.scrollTop = 0;
            })
        },
        getOneInstitution() {
            getOneInstitution().then(response => {
                this.hisl = response.data
                this.hisl.length = response.data.length
                console.log(this.hisl.length)
            })
        },
        getPaperVo() {
            getPaperVo().then(response => {
                this.his = response.data
                console.log(this.his)
            })
        },
        startLoading() {  // 使用Element loading-start 方法
            loading = Loading.service({
                lock: true,
                text: '加载中……',
                background: 'rgba(0, 0, 0, 0.7)'
            });
        },
        handleClick(tab, event) {
            console.log(tab, event);
        },
        reload() {//局部刷新
            this.isShow = false;
            this.$nextTick(() => {
                this.isShow = true;
                this.$refs.table.bodyWrapper.scrollTop = 0;
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
    right: 50px;
    ;
    position: relative;
}

.box {
    margin: auto;
    width: 100%;
    height: auto;
    text-align: center;
    /* margin-left: 70px; */
    background-color: antiquewhite;
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

::v-deep .el-tabs__nav-wrap::after {
    position: static !important;
}

.time {
    font-size: 13px;
    color: #999;
}

.bottom {
    margin-top: 13px;
    line-height: 12px;
}

.button {
    padding: 0;
    float: right;
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

.location {
    font-size: 25px;
    color: rgb(160, 106, 5);
    font-family: "华文中宋";
    margin-top: 37px;
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
    width: 300px ! important;
    border-radius: 17px;
    top: 3px !important;
    left: 100px !important;
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
    width: 550px;
    box-sizing: border-box;
    display: inline-block;
    position: relative !important;
    color: #000000 !important;
    z-index: 2;
    font-size: 24px;

}

.main-area {
    height: 1400px;
    /* border-top: solid #B03632; */
    background-color: antiquewhite;
}

.el-card {
    min-width: 380px;
    margin-right: 20px;
    transition: all .5s;
}

.el-card:hover {
    transform: scale(1.1);
}

.last-style {
    font-family: "宋体";
    letter-spacing: 0em;
    margin-top: -10%;
    font-size: 18px;
    text-align: justify;
    color: bisque;

}

.el-row .el-card {
    min-width: 75%;
    height: 100%;
    margin-right: 20px;
    transition: all .5s;

}
</style>