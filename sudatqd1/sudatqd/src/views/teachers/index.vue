<template slot-scope="{ row }">
    <div style="margin-top: 8px;background-color: antiquewhite;">
        <div style="margin:0px;top:140px ;background-color: antiquewhite;">
            <div style="margin: 0%;padding: 0%;background-color: antiquewhite;height: auto;">
                <el-row>
                    <el-col :span="24" style="bottom: 0px;text-align: right;">
                        <span class="sousuo">
                            <el-select v-model="value1" filterable remote reserve-keyword placeholder="搜索"
                                :remote-method="remoteMethod1"
                                style="background-color: rgb(173, 138, 92);width: 250px;">
                                <el-option v-for="item in options1" :key="item.value1" :label="item.label"
                                    :value="item.value1">
                                </el-option>
                            </el-select>
                        </span>
                        <span>
                            <el-button type="primary" @click="select(value1)"
                                style="width: 70px;color: blanchedalmond;background-color: rgb(197, 160, 92);border-color:rgb(165, 122, 42) ;">搜索</el-button>
                            <!-- <el-button type="primary" @click="resetQuery1"
                                style="width: 70px;color: blanchedalmond;background-color: rgb(197, 160, 92);border-color:rgb(165, 122, 42) ;">全部</el-button> -->
                        </span>
                        <div style="margin-top: 2%;font-size: 24px;">
                  <el-switch
                    v-model="value"
                    active-text="卡片样式"
                    inactive-text="列表样式"
                    >
                  </el-switch>
                </div>
                    </el-col>
                </el-row>
                <el-row v-if=" value==true ">
                    <el-col :span="6" v-for="item in his">
                        <el-card :body-style="{ padding: '2px', }" shadow="hover" class="box-card">
                            <img :src="item.eventPic" class="image" @click="select(item.eventId)" width="280px"
                                height="400px">
                            <div style="padding: 10px;">
                                <span style="font-family: STKaiti;font-size: 24px;">{{ item.eventName }}</span>
                                <div class="bottom clearfix">
                                    <span style="font-size: 20px;font-family: STKaiti; ">事记时间：{{ new
                        Date(item.eventDate).toLocaleDateString('zh').replaceAll('/', '-') }}
                                    </span>
                                    <el-button type="text" class="button" @click="select(item.eventId)">>>></el-button>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
                <el-row v-if=" value==false ">
              <el-table
      :data="his"
      :row-style="{ height: '70px','background-color': 'antiquewhite','margin-left':'5px'}" :header-cell-style="{color:'black','background-color': 'antiquewhite','font-family':'32px'}"
      style="width:90%; margin: auto; margin-top:10px;font-family: STKaiti;font-size: 24px;text-align: center;background-color: antiquewhite;margin-left: 8%;margin-bottom: 4%;">
  
      <el-table-column
        prop="eventName"
        label="事件名称"
        width="980">
        <template slot-scope="scope">
          <div style="font-size: 22px;cursor: pointer;">
              <el-link  @click="select(scope.row.eventId)" >
                <div style="font-size: 22px;">
                  {{ scope.row.eventName| filterAmount(25) }}
                </div>
              </el-link>
            </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="eventDate"
        label="日期"
        width="680">
        <template slot-scope="scope">
        {{ new
        Date(scope.row.eventDate).toLocaleDateString('zh').replaceAll('/',
          '-') }}
      </template>
      </el-table-column>
      <!-- <el-table-column
        prop="location"
        label="纸张产地">
      </el-table-column>
      <el-table-column
        prop="paperType"
        label="纸张类型">
        <template slot-scope="props">
            {{ typeName[props.row.paperType] }}
          </template>
      </el-table-column> -->
      
    </el-table>
            </el-row>
                <el-row style="height: 90px;">
                    <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                        :current-page="currentPage" :page-sizes="[5, 10, 20]" :page-size="20"
                        layout="total, sizes, prev, pager, next, jumper" :total="total">
                    </el-pagination>
                </el-row>
            </div>
        </div>
    </div>
</template>
<script>

import { getHistoricalEvents, SelectHis, PageAllHis } from '@/api/his'
import Pagination from "@/components/Pagination";

// let loading;

export default {
    name: 'Introduction',
    components: {
        Pagination,
    },

    data() {
        return {
            activeName: '1',
            teachers: [],
            his: [],
            total: 0,
            his1: [],
            eventId: '',
            options1: [],
            value1: '',
            value:  true,
            dialogVisible: false,
            formLabelWidth: '120px',
            currentPage: 1,// 默认显示第几页
            // currentPage1: 1,// 默认显示第几页
            pageSizes: [20],// 个数选择器（可修改）
            pageSize: 20,// 默认每页显示的条数（可修改）
        }
    },
    created() {

        this.PageAllHis()
        this.getQuery()
        // this.getHistoricalEvents()
        // this.startLoading()  // 开启加载
        // loading.close(); // 关闭加载

    },
    computed: {

        listSet1() {
            return this.his.map(item => {
                return { value1: `${item.eventId}`, label: `${item.eventName}` };
            });
        },

    },
    methods: {
        select(eventId) {
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

        remoteMethod1(query) {
            if (query !== '') {
                // this.loading = true;
                SelectHis(query).then((response) => {
                    this.his1 = response.data;
                });
                setTimeout(() => {
                    // this.loading = false;
                    this.options1 = this.listSet1.filter(item => {
                        return item.label.toLowerCase()
                            .indexOf(query.toLowerCase()) > -1;
                    });
                }, 10);
            } else {
                this.options1 = [];
            }
        },

        getQuery() {
            if (this.$route.query.activeName != null) {
                var params = JSON.parse(this.$Base64.decode(this.$route.query.activeName))
                if (params == 1) {
                    this.activeName = "1";
                } else if (params == 2) {
                    this.activeName = "2";
                } else if (params == 3) {
                    this.activeName = "3";
                }
                console.log(this.activeName);
            } else {
                this.activeName = "1";
            }

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
            this.getHistoricalEvents();
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
        onGoDetail(id) {
            // teacherId=this.teacherId

            this.$router.push(`/teacher_detail/${id}`)
            // this.$router.push({path:'teacher_detail',query:{id:this.teacherId}})
        },
        handleSizeChange(val) {
            this.pageSize = val
            this.PageAllHis();
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            this.currentPage = val
            console.log(`当前页: ${val}`);
            this.PageAllHis();

        },
        PageAllHis() {
            var pageNum = this.currentPage
            var pageSize = this.pageSize
            var isAsc = true
            var eventName = ""//排序
            var eventDate = ""//排序
            var eventPic = ""//排序
            var eventDescription = ""//排序
            var orderColumn = "eventId"
            var creator = ""//排序
            var state = 0//排序    
            PageAllHis({ pageSize,isAsc, pageNum,eventName,eventDate,eventPic,eventDescription,orderColumn,creator,state }).then((response) => {
                if (response) {
                    this.total = response.data.total;
                    this.reload();//局部刷新
                    console.log(pageNum + "/" + pageSize + "/" + this.total);
                    this.his = response.data.list;
                } else {
                    console.log(errorLog)
                }
            })
        },
    }

}
</script>

<style scoped>
.title-box {
    background: #ffffff;
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

h2 {
    border-bottom: 3px solid #B03632;
}

.main-area {
    border-top: solid #B03632;
}

.el-breadcrumb__item {
    font-size: 20px;
    font-family: "STKaiti";
}

.intro-span {
    margin-top: 10px;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    width: 220px;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 1;
    font-family: "STKaiti";
}

.intro-button {
    width: 100px;
    margin-top: 20px;
    height: 30px;
    background-color: #B03632;
    float: right;
    margin-block-end: 10px;
}

.name-style {
    font-family: "STKaiti";
    font-size: 25px;
    font-weight: bold;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    border-radius: 4px;
    width: 130px;
    margin-left: 60px;
}

.photo-size {
    width: 60%;
    height: 60%;
}

h2 {
    border-bottom: 3px solid #B03632;
}

.sousuo {
    top: 12px;
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
    border-radius: 5%;
    background-color: antiquewhite;
    border: 0cm;

}

.image {
    width: 100%;
    height: 380px;
    display: block;
    border-radius: 5%;
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
    width: 100px ! important;
    border-radius: 17px;
    top: 3px !important;
    left: 120px !important;
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
    width: 380px;
    box-sizing: border-box;
    display: inline-block;
    position: relative !important;
    color: #000000 !important;
    z-index: 2;
    font-size: 24px;

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