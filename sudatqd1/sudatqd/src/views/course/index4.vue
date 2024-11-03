<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div style="margin-top: 8px;background-color: antiquewhite;height: 650px;">
        <div class="roleList">

            <!-- <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb">
          <el-breadcrumb-item :to="{ path: '/home/case' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>区域疫情防控</el-breadcrumb-item>
          <el-breadcrumb-item>确诊地点&小区查询</el-breadcrumb-item>
        </el-breadcrumb> -->

            <div class="formContent">
                <el-form :inline="true" class="demo-form-inline">
                    <el-form-item label="地点:">
                        <el-cascader size="large" :options="options" v-model="selectedOptions" placeholder="请选择要查询的区域"
                            @change="handleChange">
                        </el-cascader>
                    </el-form-item>

                </el-form>
            </div>

            <el-table :data="resultData" border row-key="id" v-if="isShow"
                style=" width:100%;margin:10px auto;height: 100%;" :header-cell-style="{ background: '#f5f6fa' }">
                <!-- <el-table-column type="index" label="序号" width="50px" header-align="center" align="center"></el-table-column>
          <el-table-column prop="locale" label="小区名" width="200px" align="center"></el-table-column>
          <el-table-column prop="address" label="详细地址" width="300px" align="center"></el-table-column> -->
            </el-table>

            <baidu-map v-else class="bm-view" ak="qSnlEuO9cBNRGwWXvEdyJagc6RpNlYZc" :center="center" :zoom="zoom"
                :scroll-wheel-zoom="true" @ready="handler">
                <!--缩放控件-->
                <bm-navigation anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-navigation>
                <!--定位-->
                <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true"
                    :autoLocation="true"></bm-geolocation>
                <!--点-->
                <div v-for="(point, index) in resultData">
                    <bm-marker :position="{ lng: point.lng, lat: point.lat }" :dragging="true">
                        <bm-label :content="point.locale" :labelStyle="{ color: 'red', fontSize: '10px' }"
                            :offset="{ width: -35, height: 30 }" />
                    </bm-marker>
                </div>

            </baidu-map>

        </div>
    </div>
</template>
  
<script>
import { regionData } from 'element-china-area-data'
import { getInstitutions, getOneInstitution, PageAllInstitution, SelectInstitution } from '@/api/institution'
export default {
    components: {
    },
    data() {
        return {
            options: regionData,
            selectedOptions: [],
            resultData: [],
            center: { lng: 0, lat: 0 },
            zoom: 10,
            institutionList: [],

            points: [],
            isShow: false
        }
    },

    mounted() {
    },
    // 事件处理
    created() {
        // this.getPaper()

        this.PageAllInstitution()
        // this.addLabel()
        // this.getFirstPin()
        // this.startLoading()  // 开启加载
        // loading.close(); // 关闭加载


    },
    methods: {
        handler({ BMap, map }) {
            console.log(BMap, map)
            this.center.lng = 116.404
            this.center.lat = 39.915
            this.zoom = 14
        },
        PageAllInstitution() {

            getInstitutions().then((response) => {
                if (response) {
                    this.institutionList = response.data.institutionList;
                    // this.title = response.data.institutionList.institutionName;
                    // console.log(response.data.institutionList.institutionName)
                } else {
                    console.log(errorLog)
                }
            })
        },
        handleChange(value) {
            let paramSel = {
                province: '',
                city: '',
                district: ''
            }
            //console.log(this.options)
            this.options.map(province => {
                if (province.value == value[0]) {
                    paramSel.province = province.label;
                    province.children.map(city => {
                        if (city.value == value[1]) {
                            if (city.label == '市辖区') {
                                paramSel.city = province.label
                            } else {
                                paramSel.city = city.label
                            }
                            if (city.hasOwnProperty('children')) {
                                city.children.map(area => {
                                    if (area.value == value[2]) {
                                        paramSel.district = area.label
                                    }
                                })
                            } else {

                            }
                        }
                    })
                }
            })
            //console.log(paramSel)
            //let url = `/bpi/apis/dst/ncov/fynearby?province=${paramSel.province}&city=${paramSel.city}&district=${paramSel.district}`
            this.center = paramSel.province + paramSel.city + paramSel.district;
            this.resultData.push({
                locale: "电报局街小区",
                address: "北京市丰台区丰台街道电报局街小区",
                lng: "116.286903",
                lat: "39.849232",
                source: "北京日报", show: false
            })
            this.resultData.push({
                locale: "西府颐园",
                address: "北京市丰台区卢沟桥街道西府颐园",
                lng: "116.267921",
                lat: "39.883171",
                source: "北京日报",
                show: false
            })
            //console.log(url);
            /*this.$axios.get(url, {headers:{apicode:'f1353145fd6c42ee9a9d10e61fc823ad'}}).then(result=>{
                console.log(result.data.newslist)
                this.resultData = result.data.newslist.map(res=>{
                    return {
                        locale:res.locale,
                        address:res.address,
                        lng:res.lng,
                        lat:res.lat,
                        source:res.source,
                        show:false
                    }
                })
            })*/
        },
    }

}
</script>
<style scoped>
.bm-view {
    margin-top: 0px;
    width: 100%;
    height: 550px;
}

.label {
    padding-left: 20px;
}

.role {
    width: 100%;
    height: 100%;
    overflow: scroll;
}

.seek {
    width: 100%;
    height: auto;
    margin: 10px auto;
    border: 1px solid #e6e6e6;
    background-color: #fff;
}

.roleList {
    width: 100%;
    height: auto;
    border: 1px solid #e6e6e6;
    margin: 5px auto;
    background-color: antiquewhite;
    padding-bottom: 100px;
}

.formContent {
    width: 100%;
    height: 100%;
    /* margin: 22 auto; */
    margin-top: 5px;
    margin-bottom: -10px;
    margin-left: 10%;
    text-align: left;
    font-family: 楷体;
    /* font-size: 33px; */

}

.roleTop {
    width: 100%;
    height: 59px;
    border-bottom: 1px solid #e6e6e6;
}

.addRole {
    padding-left: 30px;
    padding-top: 10px;
}

.tableForm {
    width: 100%;
    height: 100%;
}

.iconSize {
    font-size: 16px;
}

.paginationCon {
    float: right;
    margin-top: 12px;
    margin-right: 30px;
}

.text {
    font-size: 14px;
}

.item {
    padding: 18px 0;
}

.box-card {
    width: 480px;
}

#box_center {
    margin: 0 auto;
    margin-top: 30px;
}



.font {
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

.cen_h {
    text-align: center;
    margin-bottom: 30px;
}

.breadcrumb {
    padding: 20px;
}

.demonstration {
    display: inline-block;
    color: #606266;
    font-size: 14px;
    line-height: 40px;
    padding: 0 12px 0 0;
}
</style>
  
  