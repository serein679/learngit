<template>
    <div class="hello" style="margin-top: 8px;background-color: antiquewhite;">
        <div style="margin-top: 8px;">
            <el-tabs v-model="activeName" @tab-click="handleClick"
            style=" font-family:STKaiti;font-size: 26px;top: 25px;margin: 0%;">
                <el-tab-pane label="纤维原料云" name="first" style="height:auto;font-family: STKaiti;">
                    <div>
                        <div style="margin-top: 2%;font-size: 24px;">
                  <el-switch
                    v-model="value"
                    active-text="词云样式"
                    inactive-text="列表样式"
                    >
                  </el-switch>
                </div>
                    </div>
                    <div v-if="value==true">
                        <wordcloud :data="materials" nameKey="fiberType" valueKey="fiberId" :color="myColors" :showTooltip="false"
                :wordClick="wordClickHandler" style="height: 900px;cursor: pointer;" @click="select5(materials.fiberId)">
            </wordcloud> 
                    </div>
                    <el-row v-if=" value==false ">
              <el-table
      :data="materials"
      :row-style="{ height: '70px','background-color': 'antiquewhite','margin-left':'5px'}" :header-cell-style="{color:'black','background-color': 'antiquewhite','font-family':'32px'}"
      style="width:90%; margin: auto; margin-top:10px;font-family: STKaiti;font-size: 24px;text-align: center;background-color: antiquewhite;margin-left: 8%;margin-bottom: 4%;">
      <el-table-column
        prop="creationTime"
        label="日期"
        width="480">
        <template slot-scope="scope">
        {{ new
        Date(scope.row.creationTime).toLocaleDateString('zh').replaceAll('/',
          '-') }}
      </template>
      </el-table-column>
      <el-table-column
      prop="fiberType" 
      label="纤维名称"
      width="620">
      <template slot-scope="scope" v-for="item in materials">
            <div style="font-size: 22px;cursor: pointer;">
              <el-link @click="select5(scope.row.fiberId)">
                <div style="font-size: 22px;"> 
                  {{ scope.row.fiberType| filterAmount(25) }}
                </div>
              </el-link>
            </div>
          </template>
    </el-table-column>
    <el-table-column
        prop="characteristic"
        label="纤维特点">
      </el-table-column>
    </el-table>
            </el-row>
                </el-tab-pane>
                <el-tab-pane label="成分原料云" name="second" sstyle="height:auto;">
                    <div style="margin-top: 2%;font-size: 24px;">
                  <el-switch
                    v-model="valuee"
                    active-text="词云样式"
                    inactive-text="列表样式"
                    >
                  </el-switch>
                </div>
                <div v-if="valuee==true">
                    <wordcloud :data="components" nameKey="componentName" valueKey="componentId"  :showTooltip="false"
                :wordClick="wordClickHandler" style="height: 900px;cursor: pointer;" @click="select4(materials.componentId)">
            </wordcloud> 
                </div>
                <el-row v-if=" valuee==false ">
              <el-table
      :data="components"
      :row-style="{ height: '70px','background-color': 'antiquewhite','margin-left':'5px'}" :header-cell-style="{color:'black','background-color': 'antiquewhite','font-family':'32px'}"
      style="width:90%; margin: auto; margin-top:10px;font-family: STKaiti;font-size: 24px;text-align: center;background-color: antiquewhite;margin-left: 8%;margin-bottom: 4%;">
      <el-table-column
        prop="creationTime"
        label="日期"
        width="480">
        <template slot-scope="scope">
        {{ new
        Date(scope.row.creationTime).toLocaleDateString('zh').replaceAll('/',
          '-') }}
      </template>
      </el-table-column>
      <el-table-column
      prop="componentName" 
      label="成分名称"
      width="620">
      <template slot-scope="scope" v-for="item in components">
            <div style="font-size: 22px;cursor: pointer;">
              <el-link @click="select4(scope.row.componentId)">
                <div style="font-size: 22px;"> 
                  {{ scope.row.componentName| filterAmount(25) }}
                </div>
              </el-link>
            </div>
          </template>
    </el-table-column>
    <el-table-column
        prop="description"
        label="成分特点">
      </el-table-column>
    </el-table>
            </el-row>
                </el-tab-pane>
            </el-tabs>
           
        </div>
 
       
    </div>
</template>
<script>
import wordcloud from 'vue-wordcloud'
import { PageAllFiber, getFibers1 } from '@/api/fiber'
import{getComponentList} from '@/api/coponent'

export default {
    name: 'app',
    components: {
        wordcloud
    },
    created() {
        this.getComponentList()
        this.PageAllFiber()

    },
    methods: {
        wordClickHandler(name, value, vm) {
            console.log('wordClickHandler', name, value, vm);
            if (value !== '') {
                console.log(value)
                this.$router.push({
                    path: './selectMa',
                    query: {
                        info: this.$Base64.encode(value)
                    }
                })
            }
        },

        PageAllFiber() {
            getFibers1().then((response) => {
                if (response) {
                    this.materials = response.data;
                    console.log(this.materials)
                } else {
                    console.log(errorLog)
                }
            })
        },
        getComponentList() {
            getComponentList().then((response) => {
                if (response) {
                    this.components = response.data;
                    console.log(this.components)
                } else {
                    console.log(errorLog)
                }
            })
        },
        select5(fiberId) {
            if (fiberId !== '') {
                console.log(fiberId)
                this.$router.push({
                    path: './selectMa',
                    query: {
                        info: this.$Base64.encode(fiberId)
                    }
                })
            }
        },
        select4(componentId) {
            if (componentId !== '') {
                console.log(componentId)
                this.$router.push({
                    path: './selectXin',
                    query: {
                        info: this.$Base64.encode(componentId)
                    }
                })
            }
        },
    },
    data() {
        return {
            myColors: ['#392f41', '#629fc9', '#94bedb', '#c9e0ef', '#622a1d', '#BC8F8F', '#6B8E23', '#7B68EE', '#800000', '#8B4513', '#9370D8', '#D3D3D3', '#CD853F', '#8f4b4a', '#AAAAAA', '#3D9970', '#1f77b4', '#F08080', '#F0FFFF', '#FF8C00', '#0074D9', '#111111'],
            fiberList1: [],
            materials: [],
            pageSizes5: [20],// 个数选择器（可修改）
            pageSize5: 10,// 默认每页显示的条数（可修改）
            currentPage5: 1,// 默认显示第几页
            activeName: 'first',
            components:[],
            value:true,
            valuee:true,
        }
    }
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
    font-family: "STKaiti";
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
    font-family: "STKaiti";
    letter-spacing: 3px;
}


.table-a {
    font-weight: bold;
    font-family: STKaiti;
    font-size: 20px;
    text-decoration: none;
    color: #463131;
}

.tabs {
    text-align: center;
    top: 50px;
    right: 150px;
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
    font-family: "STKaiti";
    margin-top: 37px;
}

.breadcrumb {
    font-size: 25px;
    color: #080100;
    font-family: "STKaiti";
    margin-top: 40px;
}

.el-breadcrumb__item {
    font-size: 20px;
    font-family: "STKaiti";
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
    width: 35% ! important;
    border-radius: 17px;
    top: 3px !important;
    left: 8% !important;
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
    width: 800px;
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
    font-family: "STKaiti";
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