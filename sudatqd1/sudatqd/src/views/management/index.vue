<template>
    <div class="mainbox">
        <!-- 左边的图形展示 -->
        <div class="column">
            <!-- <div id="main1" style="width: 301px;height:250px;left: 0%; "></div> -->
            <div id="main" style="width: 501px;height:290px;left: 0%;margin-top: 20%;"></div>
            <div id="main2" style="width: 501px;height:290px;left: 0%;margin-top: 10%;"></div>
        </div>
        <!-- 中间的图形展示 -->
        <div class=" column" style="margin-left: -495px;">
            <!-- 首页数字展示 -->
            <div class="mainboxtop">
                <div class="tophd">
                    <ul>
                        <li>999999999+</li>
                        <li>200+</li>
                    </ul>
                </div>
                <div class="topbd">
                    <ul>
                        <li>网站访问总人数</li>
                        <li>目前访问人数</li>
                    </ul>
                </div>

            </div>
        </div>
        <!-- 右边的图形展示 -->
        <div class="column">
            <div id="main3" style="width: 401px;height:250px;left: 0%; "></div>
            <div id="main4" style="width: 401px;height:270px;left: 0%; "></div>
            <div id="main5" style="width: 401px;height:270px;left: 0%; "></div>
        </div>
    </div>
</template>
  
<script>
// import MyEcharts from "./MyEcharts.vue";
// import "../../node_modules/echarts/dist/china.js";
// import "../../../node_modules/echarts/map/js/china.js";
import { PageAllReCo3, PageAllReCo4, PageAllReCo33, PageAllReCo44, PageAllReRE, editRe, getOneC } from '@/api/report'

import { getPaper, PageAllPaper, SelectPaper } from '@/api/paper'
import Pagination from "@/components/Pagination";
import { getInstitutions, getOneInstitution, PageAllInstitution, SelectInstitution } from '@/api/institution'
import { getArchivePic, PageAllArchive, getArchiveee, getOneArchive, SelectArchive } from '@/api/archive'
import { PageAllMaterial } from '@/api/material'
import { PageAllFiber, SelectFiber, getFibers1 } from '@/api/fiber'
import * as echarts from "echarts";
import Panel from "@/components/Panel.vue";
import { getPapers } from "@/api/paper"; //引用方法接口
import { getUs } from "@/api/users"; //引用方法接口
import { getHistoricalEvents, SelectHis, getOneHistoricalEvent, deleHis, deleHis1, PageAllHiss } from '@/api/his'

var yearData = [
    {
        year: "2020", // 年份
        data: [
            // 两个数组是因为有两条线
            [24, 40, 101, 134, 90, 230, 210, 230, 120, 230, 210, 120],
            [40, 64, 191, 324, 290, 330, 310, 213, 180, 200, 180, 79],
        ],
    },
    {
        year: "2021", // 年份
        data: [
            // 两个数组是因为有两条线
            [123, 175, 112, 197, 121, 67, 98, 21, 43, 64, 76, 38],
            [143, 131, 165, 123, 178, 21, 82, 64, 43, 60, 19, 34],
        ],
    },
];

var geoCoordMap = {
    上海: [121.4648, 31.2891],
    东莞: [113.8953, 22.901],
    东营: [118.7073, 37.5513],
    中山: [113.4229, 22.478],
    临汾: [111.4783, 36.1615],
    临沂: [118.3118, 35.2936],
    丹东: [124.541, 40.4242],
    丽水: [119.5642, 28.1854],
    乌鲁木齐: [87.9236, 43.5883],
    佛山: [112.8955, 23.1097],
    保定: [115.0488, 39.0948],
    兰州: [103.5901, 36.3043],
    包头: [110.3467, 41.4899],
    北京: [116.4551, 40.2539],
    北海: [109.314, 21.6211],
    南京: [118.8062, 31.9208],
    南宁: [108.479, 23.1152],
    南昌: [116.0046, 28.6633],
    南通: [121.1023, 32.1625],
    厦门: [118.1689, 24.6478],
    台州: [121.1353, 28.6688],
    合肥: [117.29, 32.0581],
    呼和浩特: [111.4124, 40.4901],
    咸阳: [108.4131, 34.8706],
    哈尔滨: [127.9688, 45.368],
    唐山: [118.4766, 39.6826],
    嘉兴: [120.9155, 30.6354],
    大同: [113.7854, 39.8035],
    大连: [122.2229, 39.4409],
    天津: [117.4219, 39.4189],
    太原: [112.3352, 37.9413],
    威海: [121.9482, 37.1393],
    宁波: [121.5967, 29.6466],
    宝鸡: [107.1826, 34.3433],
    宿迁: [118.5535, 33.7775],
    常州: [119.4543, 31.5582],
    广州: [113.5107, 23.2196],
    廊坊: [116.521, 39.0509],
    延安: [109.1052, 36.4252],
    张家口: [115.1477, 40.8527],
    徐州: [117.5208, 34.3268],
    德州: [116.6858, 37.2107],
    惠州: [114.6204, 23.1647],
    成都: [103.9526, 30.7617],
    扬州: [119.4653, 32.8162],
    承德: [117.5757, 41.4075],
    拉萨: [91.1865, 30.1465],
    无锡: [120.3442, 31.5527],
    日照: [119.2786, 35.5023],
    昆明: [102.9199, 25.4663],
    杭州: [119.5313, 29.8773],
    枣庄: [117.323, 34.8926],
    柳州: [109.3799, 24.9774],
    株洲: [113.5327, 27.0319],
    武汉: [114.3896, 30.6628],
    汕头: [117.1692, 23.3405],
    江门: [112.6318, 22.1484],
    沈阳: [123.1238, 42.1216],
    沧州: [116.8286, 38.2104],
    河源: [114.917, 23.9722],
    泉州: [118.3228, 25.1147],
    泰安: [117.0264, 36.0516],
    泰州: [120.0586, 32.5525],
    济南: [117.1582, 36.8701],
    济宁: [116.8286, 35.3375],
    海口: [110.3893, 19.8516],
    淄博: [118.0371, 36.6064],
    淮安: [118.927, 33.4039],
    深圳: [114.5435, 22.5439],
    清远: [112.9175, 24.3292],
    温州: [120.498, 27.8119],
    渭南: [109.7864, 35.0299],
    湖州: [119.8608, 30.7782],
    湘潭: [112.5439, 27.7075],
    滨州: [117.8174, 37.4963],
    潍坊: [119.0918, 36.524],
    烟台: [120.7397, 37.5128],
    玉溪: [101.9312, 23.8898],
    珠海: [113.7305, 22.1155],
    盐城: [120.2234, 33.5577],
    盘锦: [121.9482, 41.0449],
    石家庄: [114.4995, 38.1006],
    福州: [119.4543, 25.9222],
    秦皇岛: [119.2126, 40.0232],
    绍兴: [120.564, 29.7565],
    聊城: [115.9167, 36.4032],
    肇庆: [112.1265, 23.5822],
    舟山: [122.2559, 30.2234],
    苏州: [120.6519, 31.3989],
    莱芜: [117.6526, 36.2714],
    菏泽: [115.6201, 35.2057],
    营口: [122.4316, 40.4297],
    葫芦岛: [120.1575, 40.578],
    衡水: [115.8838, 37.7161],
    衢州: [118.6853, 28.8666],
    西宁: [101.4038, 36.8207],
    西安: [109.1162, 34.2004],
    贵阳: [106.6992, 26.7682],
    连云港: [119.1248, 34.552],
    邢台: [114.8071, 37.2821],
    邯郸: [114.4775, 36.535],
    郑州: [113.4668, 34.6234],
    鄂尔多斯: [108.9734, 39.2487],
    重庆: [107.7539, 30.1904],
    金华: [120.0037, 29.1028],
    铜川: [109.0393, 35.1947],
    银川: [106.3586, 38.1775],
    镇江: [119.4763, 31.9702],
    长春: [125.8154, 44.2584],
    长沙: [113.0823, 28.2568],
    长治: [112.8625, 36.4746],
    阳泉: [113.4778, 38.0951],
    青岛: [120.4651, 36.3373],
    韶关: [113.7964, 24.7028],
};

var XAData = [
    [{ name: "西安" }, { name: "拉萨", value: 100 }],
    [{ name: "西安" }, { name: "上海", value: 100 }],
    [{ name: "西安" }, { name: "广州", value: 100 }],
    [{ name: "西安" }, { name: "西宁", value: 100 }],
    [{ name: "西安" }, { name: "银川", value: 100 }],
];

var XNData = [
    [{ name: "西宁" }, { name: "北京", value: 100 }],
    [{ name: "西宁" }, { name: "上海", value: 100 }],
    [{ name: "西宁" }, { name: "广州", value: 100 }],
    [{ name: "西宁" }, { name: "西安", value: 100 }],
    [{ name: "西宁" }, { name: "银川", value: 100 }],
];

var YCData = [
    [{ name: "拉萨" }, { name: "潍坊", value: 100 }],
    [{ name: "拉萨" }, { name: "哈尔滨", value: 100 }],
    [{ name: "银川" }, { name: "上海", value: 100 }],
    [{ name: "银川" }, { name: "西安", value: 100 }],
    [{ name: "银川" }, { name: "西宁", value: 100 }],
];

var planePath =
    "path://M1705.06,1318.313v-89.254l-319.9-221.799l0.073-208.063c0.521-84.662-26.629-121.796-63.961-121.491c-37.332-0.305-64.482,36.829-63.961,121.491l0.073,208.063l-319.9,221.799v89.254l330.343-157.288l12.238,241.308l-134.449,92.931l0.531,42.034l175.125-42.917l175.125,42.917l0.531-42.034l-134.449-92.931l12.238-241.308L1705.06,1318.313z";
//var planePath = 'arrow';
var convertData = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
        var dataItem = data[i];

        var fromCoord = geoCoordMap[dataItem[0].name];
        var toCoord = geoCoordMap[dataItem[1].name];
        if (fromCoord && toCoord) {
            res.push({
                fromName: dataItem[0].name,
                toName: dataItem[1].name,
                coords: [fromCoord, toCoord],
                value: dataItem[1].value,
            });
        }
    }
    return res;
};

var color = ["#a6c84c", "#ffa022", "#46bee9"]; //航线的颜色
var series = [];
[
    ["西安", XAData],
    ["西宁", XNData],
    ["银川", YCData],
].forEach(function (item, i) {
    series.push(
        {
            name: item[0] + " Top3",
            type: "lines",
            zlevel: 1,
            effect: {
                show: true,
                period: 6,
                trailLength: 0.7,
                color: "red", //arrow箭头的颜色
                symbolSize: 3,
            },
            lineStyle: {
                normal: {
                    color: color[i],
                    width: 0,
                    curveness: 0.2,
                },
            },
            data: convertData(item[1]),
        },
        {
            name: item[0] + " Top3",
            type: "lines",
            zlevel: 2,
            symbol: ["none", "arrow"],
            symbolSize: 10,
            effect: {
                show: true,
                period: 6,
                trailLength: 0,
                symbol: planePath,
                symbolSize: 15,
            },
            lineStyle: {
                normal: {
                    color: color[i],
                    width: 1,
                    opacity: 0.6,
                    curveness: 0.2,
                },
            },
            data: convertData(item[1]),
        },
        {
            name: item[0] + " Top3",
            type: "effectScatter",
            coordinateSystem: "geo",
            zlevel: 2,
            rippleEffect: {
                brushType: "stroke",
            },
            label: {
                normal: {
                    show: true,
                    position: "right",
                    formatter: "{b}",
                },
            },
            symbolSize: function (val) {
                return val[2] / 8;
            },
            itemStyle: {
                normal: {
                    color: color[i],
                },
                emphasis: {
                    areaColor: "#2B91B7",
                },
            },
            data: item[1].map(function (dataItem) {
                return {
                    name: dataItem[1].name,
                    value: geoCoordMap[dataItem[1].name].concat([dataItem[1].value]),
                };
            }),
        }
    );
});
var option = {
    tooltip: {
        trigger: "item",
        formatter: function (params, ticket, callback) {
            if (params.seriesType == "effectScatter") {
                return "线路：" + params.data.name + "" + params.data.value[2];
            } else if (params.seriesType == "lines") {
                return (
                    params.data.fromName +
                    ">" +
                    params.data.toName +
                    "<br />" +
                    params.data.value
                );
            } else {
                return params.name;
            }
        },
    },
    legend: {
        orient: "vertical",
        top: "bottom",
        left: "right",
        data: ["西安 Top3", "西宁 Top3", "银川 Top3"],
        textStyle: {
            color: "#aaa",
        },
        selectedMode: "multiple",
    },
    geo: {
        map: "china",
        label: {
            emphasis: {
                show: true,
                color: "#fff",
            },
        },
        // 把中国地图放大了1.2倍
        zoom: 1.2,
        roam: true,
        itemStyle: {
            normal: {
                // 地图省份的背景颜色
                areaColor: "rgba(20, 41, 87,0.6)",
                borderColor: "#195BB9",
                borderWidth: 1,
            },
            emphasis: {
                areaColor: "#2B91B7",
            },
        },
    },
    series: series,
};
export default {
    components: { Panel },
    name: "MainBox",
    data() {
        return {
            option: {
                color: ["#2f89cf"],
                tooltip: {
                    trigger: "axis",
                    axisPointer: {
                        // 坐标轴指示器，坐标轴触发有效
                        type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
                    },
                },
                grid: {
                    left: "0%",
                    top: "10px",
                    right: "0%",
                    bottom: "4%",
                    containLabel: true,
                },
                xAxis: [
                    {
                        // 设置x轴标签文字样式
                        // x轴的文字颜色和大小
                        axisLabel: {
                            color: "rgba(0,0,0,.6)",
                            fontSize: "12",
                        },
                        //  x轴样式不显示
                        axisLine: {
                            show: false,
                            // 如果想要设置单独的线条样式
                            //   lineStyle: {
                            //     color: "rgba(255,255,255,.1)",
                            //     width: 1,
                            //     type: "solid",
                            //   },
                        },
                        type: "category",
                        data: [
                            "手工纸",
                            "机械纸",
                            "半手工半机械纸",

                        ],
                        axisTick: {
                            alignWithLabel: true,
                        },
                    },
                ],
                yAxis: [
                    {
                        type: "value",
                        // y 轴文字标签样式
                        axisLabel: {
                            color: "rgba(0,0,0,.6)",
                            fontSize: "12",
                        },
                        // y轴线条样式
                        axisLine: {
                            lineStyle: {
                                color: "rgba(0,0,0,.9)",
                                // width: 1,
                                // type: "solid"
                            },
                        },
                        // y 轴分隔线样式
                        splitLine: {
                            lineStyle: {
                                color: "rgba(0,0,0,.9)",
                            },
                        },
                    },
                ],
                series: [
                    {
                        name: "直接访问",
                        type: "bar",
                        // 修改柱子宽度
                        barWidth: "35%",
                        data: [200, 300, 300, 900, 1500, 1200, 600],
                        itemStyle: {
                            // 修改柱子圆角
                            barBorderRadius: 5,
                        },
                    },
                ],
            },
            chartsList: [
                {
                    color: ["#2f89cf"],
                    tooltip: {
                        trigger: "axis",
                        axisPointer: {
                            // 坐标轴指示器，坐标轴触发有效
                            type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
                        },
                    },
                    grid: {
                        left: "0%",
                        top: "10px",
                        right: "0%",
                        bottom: "4%",
                        containLabel: true,
                    },
                    xAxis: [
                        {
                            // 设置x轴标签文字样式
                            // x轴的文字颜色和大小
                            axisLabel: {
                                color: "rgba(0,0,0,.6)",
                                fontSize: "12",
                            },
                            //  x轴样式不显示
                            axisLine: {
                                show: false,
                                // 如果想要设置单独的线条样式
                                //   lineStyle: {
                                //     color: "rgba(255,255,255,.1)",
                                //     width: 1,
                                //     type: "solid",
                                //   },
                            },
                            type: "category",
                            data: [
                                "旅游行业",
                                "教育培训",
                                "游戏行业",
                                "医疗行业",
                                "电商行业",
                                "社交行业",
                                "金融行业",
                            ],
                            axisTick: {
                                alignWithLabel: true,
                            },
                        },
                    ],
                    yAxis: [
                        {
                            type: "value",
                            // y 轴文字标签样式
                            axisLabel: {
                                color: "rgba(255,255,255,.6)",
                                fontSize: "12",
                            },
                            // y轴线条样式
                            axisLine: {
                                lineStyle: {
                                    color: "rgba(255,255,255,.1)",
                                    // width: 1,
                                    // type: "solid"
                                },
                            },
                            // y 轴分隔线样式
                            splitLine: {
                                lineStyle: {
                                    color: "rgba(255,255,255,.1)",
                                },
                            },
                        },
                    ],
                    series: [
                        {
                            name: "直接访问",
                            type: "bar",
                            // 修改柱子宽度
                            barWidth: "35%",
                            data: [200, 300, 300, 900, 1500, 1200, 600],
                            itemStyle: {
                                // 修改柱子圆角
                                barBorderRadius: 5,
                            },
                        },
                    ],
                },
                {
                    grid: {
                        top: "10%",
                        left: "22%",
                        bottom: "10%",
                        // containLabel: true
                    },
                    // 不显示x轴的相关信息
                    xAxis: {
                        show: false,
                    },
                    yAxis: [
                        {
                            type: "category",
                            inverse: true,
                            data: ["HTML5", "CSS3", "javascript", "VUE", "NODE"],
                            // 不显示y轴的线
                            axisLine: {
                                show: false,
                            },
                            // 不显示刻度
                            axisTick: {
                                show: false,
                            },
                            // 把刻度标签里面的文字颜色设置为白色
                            axisLabel: {
                                color: "#fff",
                            },
                        },
                        {
                            data: [702, 350, 610, 793, 664],
                            inverse: true,
                            // 不显示y轴的线
                            axisLine: {
                                show: false,
                            },
                            // 不显示刻度
                            axisTick: {
                                show: false,
                            },
                            // 把刻度标签里面的文字颜色设置为白色
                            axisLabel: {
                                color: "#fff",
                            },
                        },
                    ],
                    series: [
                        {
                            name: "条",
                            type: "bar",
                            data: [70, 34, 60, 78, 69],
                            yAxisIndex: 0,
                            // 修改第一组柱子的圆角
                            itemStyle: {
                                barBorderRadius: 20,
                                // 此时的color 可以修改柱子的颜色
                                color: function (params) {
                                    // params 传进来的是柱子对象
                                    // console.log(params);
                                    // dataIndex 是当前柱子的索引号
                                    return [
                                        "#1089E7",
                                        "#F57474",
                                        "#56D0E3",
                                        "#F8B448",
                                        "#8B78F6",
                                    ][params.dataIndex];
                                },
                            },
                            // 柱子之间的距离
                            barCategoryGap: 50,
                            //柱子的宽度
                            barWidth: 10,
                            // 显示柱子内的文字
                            label: {
                                show: true,
                                position: "inside",
                                // {c} 会自动的解析为 数据  data里面的数据
                                formatter: "{c}%",
                            },
                        },
                        {
                            name: "框",
                            type: "bar",
                            barCategoryGap: 50,
                            barWidth: 15,
                            yAxisIndex: 1,
                            data: [100, 100, 100, 100, 100],
                            itemStyle: {
                                color: "none",
                                borderColor: "#00c1de",
                                borderWidth: 3,
                                barBorderRadius: 15,
                            },
                        },
                    ],
                },
                {
                    // 通过这个color修改两条线的颜色
                    color: ["#00f2f1", "#ed3f35"],
                    tooltip: {
                        trigger: "axis",
                    },
                    legend: {
                        // 如果series 对象有name 值，则 legend可以不用写data
                        // 修改图例组件 文字颜色
                        textStyle: {
                            color: "#4c9bfd",
                        },
                        // 这个10% 必须加引号
                        right: "10%",
                    },
                    grid: {
                        top: "20%",
                        left: "3%",
                        right: "4%",
                        bottom: "3%",
                        show: true, // 显示边框
                        borderColor: "#012f4a", // 边框颜色
                        containLabel: true, // 包含刻度文字在内
                    },

                    xAxis: {
                        type: "category",
                        boundaryGap: false,
                        data: [
                            "1月",
                            "2月",
                            "3月",
                            "4月",
                            "5月",
                            "6月",
                            "7月",
                            "8月",
                            "9月",
                            "10月",
                            "11月",
                            "12月",
                        ],
                        axisTick: {
                            show: false, // 去除刻度线
                        },
                        axisLabel: {
                            color: "#4c9bfd", // 文本颜色
                        },
                        axisLine: {
                            show: false, // 去除轴线
                        },
                    },
                    yAxis: {
                        type: "value",
                        axisTick: {
                            show: false, // 去除刻度线
                        },
                        axisLabel: {
                            color: "#4c9bfd", // 文本颜色
                        },
                        axisLine: {
                            show: false, // 去除轴线
                        },
                        splitLine: {
                            lineStyle: {
                                color: "#012f4a", // 分割线颜色
                            },
                        },
                    },
                    series: [
                        {
                            name: "新增粉丝",
                            type: "line",
                            // true 可以让我们的折线显示带有弧度
                            smooth: true,
                            data: yearData[0].data[0],
                        },
                        {
                            name: "新增游客",
                            type: "line",
                            smooth: true,
                            data: yearData[0].data[1],
                        },
                    ],
                },
                {
                    tooltip: {
                        trigger: "axis",
                    },
                    legend: {
                        top: "0%",
                        data: ["邮件营销", "联盟广告", "视频广告", "直接访问", "搜索引擎"],
                        textStyle: {
                            color: "rgba(255,255,255,.5)",
                            fontSize: "12",
                        },
                    },

                    grid: {
                        left: "10",
                        top: "30",
                        right: "10",
                        bottom: "10",
                        containLabel: true,
                    },
                    xAxis: [
                        {
                            type: "category",
                            boundaryGap: false,
                            // x轴更换数据
                            data: [
                                "01",
                                "02",
                                "03",
                                "04",
                                "05",
                                "06",
                                "07",
                                "08",
                                "09",
                                "10",
                                "11",
                                "12",
                                "13",
                                "14",
                                "15",
                                "16",
                                "17",
                                "18",
                                "19",
                                "20",
                                "21",
                                "22",
                                "23",
                                "24",
                                "25",
                                "26",
                                "26",
                                "28",
                                "29",
                                "30",
                            ],
                            // 文本颜色为rgba(255,255,255,.6)  文字大小为 12
                            axisLabel: {
                                textStyle: {
                                    color: "rgba(255,255,255,.6)",
                                    fontSize: 12,
                                },
                            },
                            // x轴线的颜色为   rgba(255,255,255,.2)
                            axisLine: {
                                lineStyle: {
                                    color: "rgba(255,255,255,.2)",
                                },
                            },
                        },
                    ],
                    yAxis: [
                        {
                            type: "value",
                            axisTick: { show: false },
                            axisLine: {
                                lineStyle: {
                                    color: "rgba(255,255,255,.1)",
                                },
                            },
                            axisLabel: {
                                textStyle: {
                                    color: "rgba(255,255,255,.6)",
                                    fontSize: 12,
                                },
                            },
                            // 修改分割线的颜色
                            splitLine: {
                                lineStyle: {
                                    color: "rgba(255,255,255,.1)",
                                },
                            },
                        },
                    ],
                    series: [
                        {
                            name: "邮件营销",
                            type: "line",
                            smooth: true,
                            // 单独修改当前线条的样式
                            lineStyle: {
                                color: "#0184d5",
                                width: "2",
                            },
                            // 填充颜色设置
                            areaStyle: {
                                color: new echarts.graphic.LinearGradient(
                                    0,
                                    0,
                                    0,
                                    1,
                                    [
                                        {
                                            offset: 0,
                                            color: "rgba(1, 132, 213, 0.4)", // 渐变色的起始颜色
                                        },
                                        {
                                            offset: 0.8,
                                            color: "rgba(1, 132, 213, 0.1)", // 渐变线的结束颜色
                                        },
                                    ],
                                    false
                                ),
                                shadowColor: "rgba(0, 0, 0, 0.1)",
                            },
                            // 设置拐点
                            symbol: "circle",
                            // 拐点大小
                            symbolSize: 8,
                            // 开始不显示拐点， 鼠标经过显示
                            showSymbol: false,
                            // 设置拐点颜色以及边框
                            itemStyle: {
                                color: "#0184d5",
                                borderColor: "rgba(221, 220, 107, .1)",
                                borderWidth: 12,
                            },
                            data: [
                                30, 40, 30, 40, 30, 40, 30, 60, 20, 40, 30, 40, 30, 40, 30, 40,
                                30, 60, 20, 40, 30, 40, 30, 40, 30, 40, 20, 60, 50, 40,
                            ],
                        },
                        {
                            name: "联盟广告",
                            type: "line",
                            smooth: true,
                            lineStyle: {
                                normal: {
                                    color: "#00d887",
                                    width: 2,
                                },
                            },
                            areaStyle: {
                                normal: {
                                    color: new echarts.graphic.LinearGradient(
                                        0,
                                        0,
                                        0,
                                        1,
                                        [
                                            {
                                                offset: 0,
                                                color: "rgba(0, 216, 135, 0.4)",
                                            },
                                            {
                                                offset: 0.8,
                                                color: "rgba(0, 216, 135, 0.1)",
                                            },
                                        ],
                                        false
                                    ),
                                    shadowColor: "rgba(0, 0, 0, 0.1)",
                                },
                            },
                            // 设置拐点 小圆点
                            symbol: "circle",
                            // 拐点大小
                            symbolSize: 5,
                            // 设置拐点颜色以及边框
                            itemStyle: {
                                color: "#00d887",
                                borderColor: "rgba(221, 220, 107, .1)",
                                borderWidth: 12,
                            },
                            // 开始不显示拐点， 鼠标经过显示
                            showSymbol: false,
                            data: [
                                130, 10, 20, 40, 30, 40, 80, 60, 20, 40, 90, 40, 20, 140, 30,
                                40, 130, 20, 20, 40, 80, 70, 30, 40, 30, 120, 20, 99, 50, 20,
                            ],
                        },
                    ],
                },
                {
                    color: [
                        "#006cff",
                        "#60cda0",
                        "#ed8884",
                        "#ff9f7f",
                        "#0096ff",
                        "#9fe6b8",
                        "#32c5e9",
                        "#1d9dff",
                    ],
                    tooltip: {
                        trigger: "item",
                        formatter: "{a} <br/>{b} : {c} ({d}%)",
                    },
                    legend: {
                        bottom: "0%",
                        itemWidth: 10,
                        itemHeight: 10,
                        textStyle: {
                            color: "rgba(255,255,255,.5)",
                            fontSize: "12",
                        },
                    },
                    series: [
                        {
                            name: "地区分布",
                            type: "pie",
                            radius: ["10%", "70%"],
                            center: ["50%", "50%"],
                            roseType: "radius",
                            // 图形的文字标签
                            label: {
                                fontSize: 10,
                            },
                            // 链接图形和文字的线条
                            labelLine: {
                                // length 链接图形的线条
                                length: 6,
                                // length2 链接文字的线条
                                length2: 8,
                            },
                            data: [
                                { value: 20, name: "云南" },
                                { value: 26, name: "北京" },
                                { value: 24, name: "山东" },
                                { value: 25, name: "河北" },
                                { value: 20, name: "江苏" },
                                { value: 25, name: "浙江" },
                                { value: 30, name: "四川" },
                                { value: 42, name: "湖北" },
                            ],
                        },
                    ],
                },
                {
                    color: ["#065aab", "#066eab", "#0682ab", "#0696ab", "#06a0ab"],
                    tooltip: {
                        trigger: "item",
                        formatter: "{a} <br/>{b}: {c} ({d}%)",
                    },

                    legend: {
                        bottom: "0%",
                        // 修改小图标的大小
                        itemWidth: 10,
                        itemHeight: 10,
                        // 修改图例组件的文字为 12px
                        textStyle: {
                            color: "rgba(255,255,255,.5)",
                            fontSize: "12",
                        },
                    },
                    series: [
                        {
                            name: "年龄分布",
                            type: "pie",
                            // 这个radius可以修改饼形图的大小
                            // radius 第一个值是内圆的半径 第二个值是外圆的半径
                            radius: ["40%", "60%"],
                            center: ["50%", "45%"],
                            avoidLabelOverlap: false,
                            // 图形上的文字
                            label: {
                                show: false,
                                position: "center",
                            },
                            // 链接文字和图形的线是否显示
                            labelLine: {
                                show: false,
                            },
                            data: [
                                { value: 1, name: "0岁以下" },
                                { value: 4, name: "20-29岁" },
                                { value: 2, name: "30-39岁" },
                                { value: 2, name: "40-49岁" },
                                { value: 1, name: "50岁以上" },
                            ],
                        },
                    ],
                },
            ],
            total: 0,
            total2: 0,
            total11: 0,

            currentPage: 1,// 默认显示第几页
            // currentPage1: 1,// 默认显示第几页
            pageSizes: [20],// 个数选择器（可修改）
            pageSize: 20,// 默认每页显示的条数（可修改）
            pageSizes2: [20],// 个数选择器（可修改）
            pageSize2: 20,// 默认每页显示的条数（可修改）
            pageSizes1: [20],// 个数选择器（可修改）
            pageSize1: 20,// 默认每页显示的条数（可修改）
            pageSizes5: [20],// 个数选择器（可修改）
            pageSize5: 10,// 默认每页显示的条数（可修改）
            isShow: true,
            currentPage1: 1,// 默认显示第几页
            currentPage5: 1,// 默认显示第几页
            pageSize1: 20,// 默认每页显示的条数（可修改）
            currentPage2: 1,// 默认显示第几页
            currentPage5: 1,// 默认显示第几页
            // pageSizes: [ 20],// 个数选择器（可修改）
            pageSize2: 20,// 默认每页显示的条数（可修改）
            total1: 0,
            total5: 0,
            total: '',// 总记录数
            total1: '',
            total2: '',
            total5: '',
            currentPage: 1,// 默认显示第几页
            pageSizes: [2, 5, 10, 20, 30, 50],// 个数选择器（可修改）
            pageSize: 10,// 默认每页显示的条数（可修改
            mycharts: null,
            options1: [],
            chartFun: null,
            Number: [],
        };
    },
    mounted() {
        setTimeout(() => {
            var chartDom = document.getElementById('main');
            var myChart = echarts.init(chartDom);
            let option = {
                grid: {
                    top: "10%",
                    left: "22%",
                    bottom: "10%",
                    // containLabel: true
                },
                // 不显示x轴的相关信息
                xAxis: {
                    show: false,
                },
                yAxis: [
                    {
                        type: "category",
                        inverse: true,
                        data: ["评论管理", "回复管理", "档案管理"],
                        // 不显示y轴的线
                        axisLine: {
                            show: false,
                        },
                        // 不显示刻度
                        axisTick: {
                            show: false,
                        },
                        // 把刻度标签里面的文字颜色设置为白色
                        axisLabel: {
                            color: "#aaa",
                        },
                    },
                    {
                        data: [this.total23, this.total32, this.umbers1 + this.umbers2 + this.umbers3],
                        inverse: true,
                        // 不显示y轴的线
                        axisLine: {
                            show: false,
                        },
                        // 不显示刻度
                        axisTick: {
                            show: false,
                        },
                        // 把刻度标签里面的文字颜色设置为白色
                        axisLabel: {
                            color: "#aaa",
                        },
                    },
                ],
                series: [
                    {
                        name: "条",
                        type: "bar",
                        data: [(this.total232 / this.total23) * 100, (this.total323 / this.total32) * 100, ((this.umbers2 + this.umbers1) / (this.umbers1 + this.umbers2 + this.umbers3)) * 100],
                        yAxisIndex: 0,
                        // 修改第一组柱子的圆角
                        itemStyle: {
                            barBorderRadius: 20,
                            // 此时的color 可以修改柱子的颜色
                            color: function (params) {
                                // params 传进来的是柱子对象
                                // console.log(params);
                                // dataIndex 是当前柱子的索引号
                                return ["#1089E7", "#F57474", "#56D0E3", "#F8B448", "#8B78F6"][
                                    params.dataIndex
                                ];
                            },
                        },
                        // 柱子之间的距离
                        barCategoryGap: 50,
                        //柱子的宽度
                        barWidth: 12,
                        // 显示柱子内的文字
                        label: {
                            show: true,
                            position: "inside",
                            // {c} 会自动的解析为 数据  data里面的数据
                            formatter: "{c}%",
                        },
                    },
                    {
                        name: "框",
                        type: "bar",
                        barCategoryGap: 50,
                        barWidth: 15,
                        yAxisIndex: 1,
                        data: [100, 100, 100, 100, 100],
                        itemStyle: {
                            color: "none",
                            borderColor: "#00c1de",
                            borderWidth: 3,
                            barBorderRadius: 15,
                        },
                    },
                ],
            };
            myChart.setOption(option);
        }, 500);
        setTimeout(() => {
            var chartDom = document.getElementById('main2');
            var myChart = echarts.init(chartDom);
            let option = {
                color: [
                    "#006cff",
                    "#60cda0",
                    "#ed8884",
                    "#ff9f7f",
                    "#0096ff",
                    "#9fe6b8",
                    "#32c5e9",
                    "#1d9dff",
                ],
                tooltip: {
                    trigger: "item",
                    formatter: "{a} <br/>{b} : {c} ({d}%)",
                },
                legend: {
                    bottom: "0%",
                    itemWidth: 10,
                    itemHeight: 6,
                    textStyle: {
                        color: "rgba(0,0,0,.5)",
                        fontSize: "14",
                    },
                },
                series: [
                    {
                        name: "库存分布",
                        type: "pie",
                        radius: ["10%", "70%"],
                        center: ["50%", "50%"],
                        roseType: "radius",
                        // 图形的文字标签
                        label: {
                            fontSize: 14,
                        },
                        // 链接图形和文字的线条
                        labelLine: {
                            // length 链接图形的线条
                            length: 6,
                            // length2 链接文字的线条
                            length2: 12,
                        },
                        data: [
                            // { value: 20, name: "云南" },
                            // { value: 26, name: "北京" },
                            // { value: 24, name: "山东" },
                            { value: this.total5, name: "机构库" },
                            { value: this.total11, name: "纤维库" },
                            { value: this.total2, name: "档案库" },
                            { value: this.total, name: "纸张库" },
                            { value: this.total1, name: "大事记库" },
                        ],
                    },
                ],
            };
            myChart.setOption(option);
        }, 500);
        setTimeout(() => {
            var chartDom = document.getElementById('main3');
            var myChart = echarts.init(chartDom);
            let option = {
                tooltip: {
                    trigger: "axis",
                },
                title: {
                    id: '1', // 指定组件
                    show: true, //  是否显示标题
                    text: '全天活跃度', // 主标题文本内容，支持 \n 换行
                    link: '地址链接', // 指定主标题文本点击跳转的地址
                    target: 'self', // 指定打开主标题链接方式 self本窗口 或者 blank 新建窗口
                    // 主标题样式
                    textStyle: {
                        // color: 'red', // 颜色
                        fontStyle: 'normal', // 字体风格
                        fontWeight: 500, //或者 'noraml', //  字体粗细
                        fontSize: 16, // 字体大小
                        lineHeight: 20, // 行高

                        textBorderWidth: 20, // 文本描边宽度
                        // textBorderColor: 'red', //描边颜色
                        textBorderType: 'solid', //描边类型
                        textBorderDashOffset: 20, // 虚线偏移量

                        textShadowBlur: 20, // 文字阴影长度
                        textShadowOffsetX: 20, //阴影相当于 X 轴偏移量
                        textShadowOffsetY: 20, //阴影相当于 Y 轴偏移量
                        overflow: 'truncate', //文字超出宽度如何处理  break 换行
                        ellipsis: '...' // 配合 overflow 截断时，展示 ···
                    }
                },
                legend: {
                    top: "0%",
                    data: ["管理员", "用户", "视频广告", "直接访问", "搜索引擎"],
                    textStyle: {
                        color: "rgba(0,0,0,.5)",
                        fontSize: "12",
                    },
                },

                grid: {
                    left: "10",
                    top: "30",
                    right: "10",
                    bottom: "10",
                    containLabel: true,
                },
                xAxis: [
                    {
                        type: "category",
                        boundaryGap: false,
                        // x轴更换数据
                        data: [
                            "01",
                            "02",
                            "03",
                            "04",
                            "05",
                            "06",
                            "07",
                            "08",
                            "09",
                            "10",
                            "11",
                            "12",
                            "13",
                            "14",
                            "15",
                            "16",
                            "17",
                            "18",
                            "19",
                            "20",
                            "21",
                            "22",
                            "23",
                            "24",


                        ],
                        // 文本颜色为rgba(255,255,255,.6)  文字大小为 12
                        axisLabel: {
                            textStyle: {
                                color: "rgba(0,0,0,.6)",
                                fontSize: 12,
                                interval: 0,  //设置间隔为0
                                rotate: 45, //代表逆时针旋转
                            },
                        },
                        // x轴线的颜色为   rgba(255,255,255,.2)
                        axisLine: {
                            lineStyle: {
                                color: "rgba(0,0,0,.2)",
                            },
                        },
                    },
                ],
                yAxis: [
                    {
                        type: "value",
                        axisTick: { show: false },
                        axisLine: {
                            lineStyle: {
                                color: "rgba(0,0,0,.9)",
                            },
                        },
                        axisLabel: {
                            textStyle: {
                                color: "rgba(0,0,0,.6)",
                                fontSize: 12,
                            },
                        },
                        // 修改分割线的颜色
                        splitLine: {
                            lineStyle: {
                                color: "rgba(255,255,255,.1)",
                            },
                        },
                    },
                ],
                series: [
                    {
                        name: "管理员",
                        type: "line",
                        smooth: true,
                        // 单独修改当前线条的样式
                        lineStyle: {
                            color: "#0184d5",
                            width: "2",
                        },
                        // 填充颜色设置
                        areaStyle: {
                            color: new echarts.graphic.LinearGradient(
                                0,
                                0,
                                0,
                                1,
                                [
                                    {
                                        offset: 0,
                                        color: "rgba(1, 132, 213, 0.4)", // 渐变色的起始颜色
                                    },
                                    {
                                        offset: 0.8,
                                        color: "rgba(1, 132, 213, 0.1)", // 渐变线的结束颜色
                                    },
                                ],
                                false
                            ),
                            shadowColor: "rgba(0, 0, 0, 0.1)",
                        },
                        // 设置拐点
                        symbol: "circle",
                        // 拐点大小
                        symbolSize: 8,
                        // 开始不显示拐点， 鼠标经过显示
                        showSymbol: false,
                        // 设置拐点颜色以及边框
                        itemStyle: {
                            color: "#0184d5",
                            borderColor: "rgba(221, 220, 107, .1)",
                            borderWidth: 12,
                        },
                        data: [
                            30, 40, 30, 40, 30, 40, 30, 60, 20, 40, 30, 40, 30, 40, 30, 40,
                            30, 60, 20, 40, 30, 40, 30, 40, 30, 40, 20, 60, 50, 40,
                        ],
                    },
                    {
                        name: "用户",
                        type: "line",
                        smooth: true,
                        lineStyle: {
                            normal: {
                                color: "#00d887",
                                width: 2,
                            },
                        },
                        areaStyle: {
                            normal: {
                                color: new echarts.graphic.LinearGradient(
                                    0,
                                    0,
                                    0,
                                    1,
                                    [
                                        {
                                            offset: 0,
                                            color: "rgba(0, 216, 135, 0.4)",
                                        },
                                        {
                                            offset: 0.8,
                                            color: "rgba(0, 216, 135, 0.1)",
                                        },
                                    ],
                                    false
                                ),
                                shadowColor: "rgba(0, 0, 0, 0.1)",
                            },
                        },
                        // 设置拐点 小圆点
                        symbol: "circle",
                        // 拐点大小
                        symbolSize: 5,
                        // 设置拐点颜色以及边框
                        itemStyle: {
                            color: "#00d887",
                            borderColor: "rgba(221, 220, 107, .1)",
                            borderWidth: 12,
                        },
                        // 开始不显示拐点， 鼠标经过显示
                        showSymbol: false,
                        data: [
                            130, 10, 20, 40, 30, 40, 80, 60, 20, 40, 90, 40, 20, 140, 30, 40,
                            130, 20, 20, 40, 80, 70, 30, 40, 30, 120, 20, 99, 50, 20,
                        ],
                    },
                ],
            };
            myChart.setOption(option);
        }, 500);
        setTimeout(() => {
            var chartDom = document.getElementById('main4');
            var myChart = echarts.init(chartDom);
            let option = {
                color: ["#2f89cf"],
                tooltip: {
                    trigger: "axis",
                    axisPointer: {
                        // 坐标轴指示器，坐标轴触发有效
                        type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
                    },
                },
                grid: {
                    left: "0%",
                    top: "10px",
                    right: "0%",
                    bottom: "4%",
                    containLabel: true,
                },
                xAxis: [
                    {
                        // 设置x轴标签文字样式
                        // x轴的文字颜色和大小
                        axisLabel: {
                            color: "rgba(0,5,5,.6)",
                            fontSize: "12",
                            interval: 0  //设置间隔为0
                            // rotate: 45, //代表逆时针旋转
                        },
                        //  x轴样式不显示
                        axisLine: {
                            show: false,
                            // 如果想要设置单独的线条样式
                            //   lineStyle: {
                            //     color: "rgba(255,255,255,.1)",
                            //     width: 1,
                            //     type: "solid",
                            //   },
                        },
                        type: "category",
                        data: [
                            "手工纸",
                            "机械纸",
                            "半手工半机械纸",
                        ],
                        axisTick: {
                            alignWithLabel: true,
                        },
                    },
                ],
                yAxis: [
                    {
                        type: "value",
                        // y 轴文字标签样式
                        axisLabel: {
                            color: "rgba(0,5,5,.6)",

                            fontSize: "12",

                        },
                        // y轴线条样式
                        axisLine: {
                            lineStyle: {
                                color: "rgba(0,0,0,.1)",
                                // width: 1,
                                // type: "solid"
                            },
                        },
                        // y 轴分隔线样式
                        splitLine: {
                            lineStyle: {
                                color: "rgba(0,0,0,.1)",
                            },
                        },
                    },
                ],
                series: [
                    {
                        name: "总数",
                        type: "bar",
                        // 修改柱子宽度
                        barWidth: "35%",
                        data: [this.Numbers, this.Numbers1, this.Numbers2],
                        itemStyle: {
                            // 修改柱子圆角
                            barBorderRadius: 5,
                        },
                    },
                ],
            };
            myChart.setOption(option);
        }, 500);
        setTimeout(() => {
            var chartDom = document.getElementById('main5');
            var myChart = echarts.init(chartDom);
            let option = {
                color: ["#065aab", "#0696ab", "#06a0ab", "#066eab", "#0682ab"],
                tooltip: {
                    trigger: "item",
                    formatter: "{a} <br/>{b}: {c} ({d}%)",
                },
                title: {
                    id: '1', // 指定组件
                    show: true, //  是否显示标题

                    text: '用户比例', // 主标题文本内容，支持 \n 换行
                    link: '地址链接', // 指定主标题文本点击跳转的地址
                    target: 'self', // 指定打开主标题链接方式 self本窗口 或者 blank 新建窗口
                    // 主标题样式
                    textStyle: {
                        // color: 'red', // 颜色
                        fontStyle: 'normal', // 字体风格
                        fontWeight: 500, //或者 'noraml', //  字体粗细
                        fontSize: 16, // 字体大小
                        lineHeight: 20, // 行高

                        textBorderWidth: 20, // 文本描边宽度
                        // textBorderColor: 'red', //描边颜色
                        textBorderType: 'solid', //描边类型
                        textBorderDashOffset: 20, // 虚线偏移量

                        textShadowBlur: 20, // 文字阴影长度
                        textShadowOffsetX: 20, //阴影相当于 X 轴偏移量
                        textShadowOffsetY: 20, //阴影相当于 Y 轴偏移量
                        overflow: 'truncate', //文字超出宽度如何处理  break 换行
                        ellipsis: '...' // 配合 overflow 截断时，展示 ···
                    }
                },


                legend: {
                    bottom: "0%",
                    // 修改小图标的大小
                    itemWidth: 10,
                    itemHeight: 10,
                    // 修改图例组件的文字为 12px
                    textStyle: {
                        color: "rgba(0,5,5,.5)",
                        fontSize: "12",
                    },
                },
                series: [
                    {
                        name: "数量分布",
                        type: "pie",
                        // 这个radius可以修改饼形图的大小
                        // radius 第一个值是内圆的半径 第二个值是外圆的半径
                        radius: ["40%", "60%"],
                        center: ["50%", "45%"],
                        avoidLabelOverlap: false,
                        // 图形上的文字
                        label: {
                            show: false,
                            position: "center",
                        },
                        // 链接文字和图形的线是否显示
                        labelLine: {
                            show: false,
                        },
                        data: [
                            { value: this.numbers1, name: "管理员" },
                            { value: this.numbers, name: "普通用户" },

                        ],
                    },
                ],
            };
            myChart.setOption(option);
        }, 500);

        this.mycharts = echarts.init(this.$refs.chinamap);
        this.mycharts.setOption(option);
        let chart = this.mycharts;
        // 节流函数
        function throttle(func, wait, options) {
            let time, context, args, result;
            let previous = 0;
            if (!options) options = {};
            let later = function () {
                previous = options.leading === false ? 0 : new Date().getTime();
                time = null;
                func.apply(context, args);
                if (!time) context = args = null;
            };

            let throttled = function () {
                let now = new Date().getTime();
                if (!previous && options.leading === false) previous = now;
                let remaining = wait - (now - previous);
                context = this;
                args = arguments;
                if (remaining <= 0 || remaining > wait) {
                    if (time) {
                        clearTimeout(time);
                        time = null;
                    }
                    previous = now;
                    func.apply(context, args);
                    if (!time) context = args = null;
                } else if (!time && options.trailing !== false) {
                    time = setTimeout(later, remaining);
                }
            };
            return throttled;
        }
        this.chartFun = throttle(function () {
            chart.resize();
        }, 10);
        window.addEventListener("resize", this.chartFun);
    },
    beforeDestroy() {
        // 移除窗口改变监听
        window.removeEventListener("resize", this.chartFun);
    },
    created() {
        this.getPapers();
        this.getUs();
        this.PageAllHis()
        this.getList()
        // this.getArchivePic()
        this.PageAllArchive()
        this.PageAllInstitution()
        this.PageAllFiber()
        this.PageHi()
        this.PageAllHi()
        this.PageHi3()
        this.PageAllHi4()
        this.getArchiveee()

    },
    methods: {
        getPapers() {
            getPapers().then(response => {
                this.Numbers = response.data[0].number
                this.Numbers1 = response.data[1].number
                this.Numbers2 = response.data[2].number


                console.log(this.Numbers)
                console.log(this.Numbers1)
                console.log(this.Numbers2)

            })
        },
        getArchiveee() {
            getArchiveee().then(response => {
                this.umbers = response.data[0].number
                this.umbers1 = response.data[1].number
                this.umbers2 = response.data[2].number
                this.umbers3 = response.data[3].number
                console.log(this.umbers)
                console.log(this.umbers1)
                console.log(this.umbers2)
                console.log(this.umbers3)



            })
        },
        PageAllHis() {
            var pageNum = this.currentPage
            var pageSize = this.pageSize
            var order = 0//排序
            var name = this.name//排序
            PageAllHiss({ pageSize, order, pageNum, name }).then((response) => {
                if (response) {
                    this.total1 = response.data.total;
                    // this.reload();//局部刷新
                    console.log(pageNum + "/" + pageSize + "/" + this.total);
                    this.hisList = response.data.historicalEventsList;
                } else {
                    console.log(errorLog)
                }
            })
        },
        getList() {
            var pageNum = this.currentPage
            var pageSize = this.pageSize
            var order = 0//排序
            PageAllPaper({ pageSize, order, pageNum }).then((response) => {
                if (response) {
                    this.total = response.data.total;
                    // this.reload();//局部刷新
                    console.log(pageNum + "/" + pageSize + "/" + this.total);
                    this.paperList = response.data.paperList;
                } else {
                    console.log(errorLog)
                }
            })
        },
        PageAllInstitution() {
            var pageNum = this.currentPage1
            var pageSize = this.pageSize1
            var order = 0//排序
            PageAllInstitution({ pageSize, order, pageNum }).then((response) => {
                if (response) {
                    this.total5 = response.data.total;
                    // this.reload();//局部刷新
                    console.log(pageNum + "/" + pageSize + "/" + this.total);
                    this.institutionList = response.data.institutionList;
                } else {
                    console.log(errorLog)
                }
            })
        },
        PageAllFiber() {
            getFibers1().then((response) => {
                if (response) {
                    // this.total5 = response.data.total;
                    // this.reload();//局部刷新
                    // console.log(pageNum + "/" + pageSize + "/" + this.total);
                    this.materials = response.data;
                    console.log(this.materials)
                } else {
                    console.log(errorLog)
                }
            })
        },
        PageAllFiber() {
            var pageNum = this.currentPage
            var pageSize = this.pageSize
            var order = 0//排序
            var name = this.name//排序
            PageAllFiber({ pageSize, order, pageNum, name }).then((response) => {
                if (response) {
                    this.total11 = response.data.total;
                    // this.reload();//局部刷新
                    console.log(pageNum + "/" + pageSize + "/" + this.total);
                    this.fiberList = response.data.fiberList;
                } else {
                    console.log(errorLog)
                }
            })
        },
        PageHi() {
            var pageNum = this.currentPage
            var pageSize = this.pageSize
            var order = 0//排序
            var typefilter = this.typefilter;
            var typefilter1 = this.typefilter1;
            var name = this.name//排序
            PageAllReCo3({ pageSize, order, pageNum, name, typefilter, typefilter1 }).then((response) => {
                if (response) {
                    this.total23 = response.data.total;
                    // this.reload();//局部刷新
                    console.log(pageNum + "/" + pageSize + "/" + this.total);
                    this.hisList = response.data.reportCommentList;
                } else {
                    console.log(errorLog)
                }
            })
        },
        PageHi3() {
            var pageNum = this.currentPage
            var pageSize = this.pageSize
            var order = 0//排序
            var typefilter = this.typefilter;
            var typefilter1 = this.typefilter1;
            var name = this.name//排序
            PageAllReCo33({ pageSize, order, pageNum, name, typefilter, typefilter1 }).then((response) => {
                if (response) {
                    this.total232 = response.data.total;
                    // this.reload();//局部刷新
                    console.log(pageNum + "/" + pageSize + "/" + this.total);
                    this.hisList = response.data.reportCommentList;
                } else {
                    console.log(errorLog)
                }
            })
        },
        PageAllArchive() {
            var pageNum = this.currentPage2
            var pageSize = this.pageSize1
            var order = 0//排序
            var datefilter = this.datefilter;//类型筛选
            var datefilter1 = this.datefilter1;//类型筛选
            PageAllArchive({ pageSize, order, pageNum, datefilter, datefilter1 }).then((response) => {
                if (response) {
                    this.total2 = response.data.total;
                    // this.reload();//局部刷新
                    console.log(pageNum + "/" + pageSize + "/" + this.total);
                    this.archiveLists = response.data.archiveList;
                } else {
                    console.log(errorLog)
                }
            })
        },
        PageAllHi() {
            var pageNum = this.currentPage
            var pageSize = this.pageSize
            var order = 0//排序
            var typefilter = this.typefilter;
            var typefilter1 = this.typefilter1;
            var name = this.name//排序
            PageAllReCo4({ pageSize, order, pageNum, typefilter1 }).then((response) => {
                if (response) {
                    this.total32 = response.data.total;
                    // this.reload();//局/部刷新
                    console.log(pageNum + "/" + pageSize + "/" + this.total);
                    this.hisList = response.data.reportCommentList;
                } else {
                    console.log(errorLog)
                }
            })
        },
        PageAllHi4() {
            var pageNum = this.currentPage
            var pageSize = this.pageSize
            var order = 0//排序
            var typefilter = this.typefilter;
            var typefilter1 = this.typefilter1;
            var name = this.name//排序
            PageAllReCo44({ pageSize, order, pageNum, typefilter1 }).then((response) => {
                if (response) {
                    this.total323 = response.data.total;
                    // this.reload();//局部刷新
                    console.log(pageNum + "/" + pageSize + "/" + this.total);
                    this.hisList = response.data.reportCommentList;
                } else {
                    console.log(errorLog)
                }
            })
        },
        getUs() {
            getUs().then(response => {
                this.numbers = response.data[0].number
                this.numbers1 = response.data[1].number
                // this.Numbers2 = response.data[2].number
                console.log(this.numbers)
                console.log(this.numbers1)
                // console.log(this.Numbers2)

            })
        },


        // 编辑按钮点击事件
    },
};
</script>
  
<style scoped>
.mainbox {
    display: flex;
    /* background-color: pink; */
    margin-top: 0%;
    padding: 0.125rem 0.125rem 0;
    background-color: antiquewhite;

    height: 820px;
}

.column {
    /* height: 10rem; */
    /* background-color: red; */
    /* flex: 3; */
    /* margin-top: 10%; */
    height: 780px;
}

.mainbox .column:nth-child(2) {
    /* background-color: blue; */
    padding: 0 0.125rem 0.1875rem;
    /* background-color: blue; */
    flex: 5;
}

/* 以下是存放中国地图的容器样式 */
.mainboxtop {
    background-color: antiquewhite;

    padding: 0.1875rem;
}

.tophd {
    position: relative;
    border: 1px solid rgba(10, 10, 10, 0.17);
    font-size: 20px;
}

@font-face {
    font-family: electronicFont;
    src: url(@/assets/22.gif);
}

.tophd>ul,
.topbd>ul {
    display: flex;
    color: #000000;


}

.tophd>ul>li {
    flex: 1;
    display: inline-block;
    height: 1rem;
    color: #000000;
    font-size: 1.095rem;
    font-family: STKaiti;
    font-family: electronicFont;
}

.tophd>ul::after {
    position: absolute;
    right: 50%;
    width: 0.0125rem;
    height: 70%;
    top: 25%;
    /* background-color: antiquewhite; */
    color: #000000;

    content: "";
}

.tophd::before,
.tophd::after {
    position: absolute;
    content: "";
    width: 0.375rem;
    height: 0.125rem;
}

.tophd::before {
    top: 0;
    left: 0;
    border-top: 2px solid #02a6b5;
    border-left: 2px solid #02a6b5;
}

.el-main {
    display: block;
    flex: 1;
    flex-basis: auto;
    overflow: auto;
    box-sizing: border-box;
    padding: 0px !important;
}

.tophd::after {
    bottom: 0;
    right: 0;
    border-bottom: 2px solid #02a6b5;
    border-right: 2px solid #02a6b5;
}

.topbd>ul>li {
    flex: 1;
    height: 0.5rem;
    line-height: 0.5rem;
    /* color: rgba(255, 255, 255, 0.7); */
    color: #000000;

    font-size: 1.025rem;
    font-family: STKaiti;

    padding-top: 0.125rem;
}

.mainboxbody {
    position: relative;
    width: 100%;
    height: 120.125rem;
    color: #000000;

    /* background-color: pink; */
}

.chinamap {
    position: absolute;
    top: 20px;
    left: 0;
    z-index: 5;
    height: 40.125rem;
    width: 100%;
    color: #000000;

}
</style>
  