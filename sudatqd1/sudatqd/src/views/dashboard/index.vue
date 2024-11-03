<template>
  <div style="margin-top: 8px;background-color: antiquewhite;width: 100%;">
    <div>
      <el-row style="height: 980px;">
        <el-col :span="14" style="height:100%;">
          <Carousel1></Carousel1>
        </el-col>
        <el-col :span="10" style="height:100%;margin-top: 20px;">
          <el-table
    :data="data1"
    class="transparent-table"
    style="width: 100%;font-family: STKaiti;font-size: 24px;height: 880px;margin-left: 20px;text-align: center;color: black" :row-style="{ height: '70px','background-color': 'antiquewhite','margin-left':'5px'}" :header-cell-style="{color:'black','text-align':'center','background-color': 'antiquewhite','font-family':'32px'}" >
    <el-table-column
      prop="eventName" 
      label="最新事件"
      width="620">
      <template slot-scope="scope" v-for="item in data1">
            <div style="font-size: 22px;cursor: pointer;">
              <el-link @click="selectEvent(scope.row.eventId)">
                <div style="font-size: 22px;">
                  {{ scope.row.eventName| filterAmount(25) }}
                </div>
              </el-link>
            </div>
          </template>
    </el-table-column>
    <el-table-column prop="eventDate" align="center" label="事件时间" width="180">
      <template slot-scope="scope">
        {{ new
        Date(scope.row.eventDate).toLocaleDateString('zh').replaceAll('/',
          '-') }}
      </template>
    </el-table-column>
  </el-table>
        </el-col>
      </el-row>
      <el-row style="height: 680px;">
        <el-col :span="8">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
            <span style="font-family: STKaiti;font-size: 32px;">纸张概览</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="go">查看更多</el-button>
            </div>
            <div v-for="o in papers" :key="o" style="font-family: STKaiti;font-size: 26px;">
              <el-button style="float: left;margin-left: 6%;margin-bottom: 8%;" type="text"  @click="select(o.paperId)">{{o.paperName }}</el-button>
            </div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
            <span style="font-family: STKaiti;font-size: 32px;">档案概览</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="go">查看更多</el-button>
            </div>
            <div v-for="o in archive" :key="o" style="font-family: STKaiti;font-size: 26px;">
              <el-button style="float: left;margin-left: 5%;margin-bottom: 8%;width: 250px;" type="text" @click="select1(o.archiveId)">{{o.archiveName }}</el-button>
            </div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
            <span style="font-family: STKaiti;font-size: 32px;">纤维概览</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="go1">查看更多</el-button>
            </div>
            <div v-for="o in data2" :key="o" style="font-family: STKaiti;font-size: 26px;">
              <el-button style="float: left;margin-left: 6%;margin-bottom: 8%;" type="text" @click="select5(o.fiberId)" >{{o.fiberType }}</el-button>
            </div>
          </el-card>
        </el-col>
      
      </el-row>
      <el-row >
        <el-col :span="24">
          <div class="jigou" @click="go" style="cursor: pointer;">
            档案机构
          </div>
        </el-col>
        <el-col :span="24">
          <div class="ny-shzy-c pore">
            <div>
              <ul class="lnzl flex-left">
                <li v-for="(item, index) in data"  :key="index" class="bg-mask pore">
                  <!-- <a href="#" class="ablink"></a> -->
                  <div class="info flex-v-center" @click="select2(item.institutionId)">
                    <div class="flex-center">
                      <div>
                        <h4> {{ item.institutionName }}</h4>
                        <span>
                          {{ item.institutionDescription }}
                        </span>
                        <br>
                        <br>
                        <br>
                        <span>
                          所在地：
                          {{ item.institutionLocation }}
                        </span>
                      </div>
                    </div>
                  </div>
                  <div class="pic" @click="select2(item.institutionId)">
                    <span class="a" :style="{ backgroundImage: 'url(' + item.pictureLocation + ')' }">
                      <!-- <span class="a" style="background-image: url(item.institutionPicture);"> -->
                      <img :src="item.pictureLocation" width="100%" class="none">
                    </span>
                  </div>

                </li>
              </ul>
            </div>
          </div>
        </el-col>
        
      </el-row>
    </div>
  </div>
</template>

<script>
//  import { Swiper, SwiperSlide } from 'swiper/vue';
import './filters.js'
import Carousel1 from '@/components/Carousel1.vue'
import TreeMap from '@/components/treeMap.vue'
import { getInstitutions, getOneInstitution } from '@/api/institution'
import { getPaperTime, getOnePaper } from '@/api/paper'
import { getHistoryList } from '@/api/his'
import { getArchiveLists } from '@/api/archive'
import { getFiberLists } from '@/api/fiber'


import SwiperTest from '@/components/swiperTest.vue'
import { color } from 'echarts'

export default {
  name: 'Dashboard',
  components: {
    Carousel1,
    SwiperTest,
    TreeMap
  },
  data() {
    return {
      onePaper: [],
      data: [],
      data1: [],
      data2: [],
      archive:[],
      paperList: [],
      papers: [],
      loading: true,
      dialogVisible: false,
      formLabelWidth: '120px',
      value: '',
      fiberId:'',
      paperId: '',

    }
  },
  created() {
    this.getPaperTime()
    this.getInstitutions()
    this.getHistoryList()
    this.greet()
    this.getArchiveLists()
    this.getFiberLists()

  },
  methods: {
    getInstitutions() {
      getInstitutions().then(response => {
        this.data = response.data
      })
    },
    getFiberLists() {
      getFiberLists().then(response => {
        this.data2 = response.data
      })
    },
    getArchiveLists() {
      getArchiveLists().then(response => {
        this.archive = response.data
      })
    },
    go() {
      this.$router.push({
        path: '/introduction/index'
      })
    },//返回上一页
    getHistoryList() {
      getHistoryList().then(response => {
        this.data1 = response.data
      })
    },
    select2(institutionId) {
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
    select1(archiveId) {
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
    selectEvent(eventId) {
      if (eventId !== '') {
        console.log(eventId)
        this.$router.push({
          path: './teachers/detail',
          query: {
            info: this.$Base64.encode(eventId)
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
    menuNavigation(data) {
      // console.log(data.target.firstChild.data) 
      console.log(data.target.paperId)
    },
    greet: function (paperId = 12) {
      console.log(paperId);
      getOnePaper(paperId).then((response) => {
        this.onePaper = response.data;
        console.log(this.onePaper);
      });
    },

    getPaperTime() {
      getPaperTime().then(response => {
        this.papers = response.data
        console.log(this.papers)
      })
    },

    formatData(forumTime) {
      var s = new Date(forumTime).toLocaleString();
      return s;
    },

    goUrl(url) {
      window.open(src, "_blank")

    },
    go() {
        this.$router.push({
          path: '/introduction/index'
        })
      },//返回上一页
      go1() {
        this.$router.push({
          path: '/introduction/echarts'
        })
      },//返回上一页
    linkTo() {
      let activeIndex = this.$refs.carousel.activeIndex
      window.open(this.miens[activeIndex].content, "_blank")
    }
  }
}

</script>

<style scoped>
#main {
  width: 600px;
  height: 350px;
  margin: 0px auto;
}

.el-card .el-card__header {
  padding: 20px !important;
}

.el-card__header {
  padding: 0px 0px;
  border-bottom: 0px solid #fafafa;
  box-sizing: border-box;
}

.el-card__header {
  padding: 18px 20px;
  border-bottom: 0px solid #fefefe;
  box-sizing: border-box;
}

.el-button--primary {
  font-size: 16px;
  font-family: STKaiti;
  color: #eeecec;
  background-color: #894659;
  border-color: #c0bcbc;
}

.flex-left {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  list-style-type: none
}

.ny-shzy-c .lnzl li .pic {
  height: 480px;
  display: block;
  list-style-type: none
}

.bg-mask .pic .a {
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
  height: 100%;
  display: block;
  -webkit-transition: all .4s;
  -o-transition: all .4s;
  transition: all .4s;
}

.flex-center {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  cursor: pointer;
  font-family: STKaiti;
  font-size: large;
  line-height: 50px;
}
.transparent-table {
  background-color: transparent;
  /* opacity: 0.3; */
}
/* .el-table th, .el-table tr {
 background-color: transparent;
 } */
.bg-mask .pic {
  overflow: hidden;
}
.clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
    font-family: STKaiti;
    font-size: 32px;
  }
  .clearfix:after {
    clear: both
  }
.pore {
  position: relative;
}
.el-table tr {
    background-color: #131212 !important;
}
.ny-shzy-c .lnzl li {
  width: 24%;
  list-style-type: none
}

.none {
  display: none;
  transition: all 0.6s;

}

.pic :hover {
  transform: scale(1.2);
}
.el-table thead {
 color: rgb(0, 0, 0);
 font-weight: 500;
 /* background-color: rgba(148, 144, 144, 0.3) */
 }
 /* .el-table tr {
    background-color: #FFF;
} */
.bg-mask .pic .a {
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
  height: 100%;
  display: block;
  -webkit-transition: all .4s;
  -o-transition: all .4s;
  transition: all .4s;
  overflow: hidden;
  /* transform: scale(1.3); */
}

.bg-mask .pic:hover {
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
  height: 100%;
  display: block;
  -webkit-transition: all .4s;
  -o-transition: all .4s;
  transition: all .4s;
  overflow: hidden;
  transform: scale(1.3);
}

.bg-mask:hover .pic .a {
  -webkit-transform: scale(1.1);
  -ms-transform: scale(1.1);
  transform: scale(1.1);
}
.box-card {
    /* width: 500px; */
    /* height: 650px; */
    margin-left: 15px;
  }
.ny-shzy-c .lnzl li .info {
  position: absolute;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  z-index: 3;
  padding: 20px 5%;
  color: #fff;
  background: rgba(197, 66, 36, .7);
  opacity: 0;
  /* visibility: hidden; */
  transition: .3s;
}

.ablink {
  position: absolute;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  z-index: 5;
  background: rgba(255, 255, 255, 0);
}

.pic_item {
  position: relative;
  height: 100%;
}

.ny-shzy-c .lnzl li:hover .info {
  opacity: 1;
  visibility: inherit;
}

.fan {
  width: 450px;
  height: 500px;
  position: absolute;
  top: 10%;
  left: 9%;
  list-style: none;
  /* border: 1px solid rgb(0, 0, 0); */
}

.fan li {
  cursor: pointer;
  position: absolute;
  width: 60px;
  height: 350px;
  background-color: rgb(19, 57, 57);
  left: 50%;
  top: 30%;
  margin-left: -30px;
  margin-top: -100px;
  text-align: center;
  font-family: STKaiti;
  font-size: 36px;
  transform-origin: center bottom;
  transition: all 1s linear;
}

.fan li:nth-of-type(5n) {
  background-color: #dd967a;
  opacity: 80%;
}

.fan li:nth-of-type(5n+1) {
  background-color: rgb(196, 220, 226);
  opacity: 80%;

}

.fan li:nth-of-type(5n+2) {
  background-color: rgb(216, 224, 197);
  opacity: 80%;

}

.fan li:nth-of-type(5n+3) {
  background-color: rgb(153, 134, 134);

}
/* 直接在你的组件的<style>标签中 */


.fan li:nth-of-type(5n+4) {
  background-color: rgb(221, 198, 223);
  opacity: 80%;

}

.fan:hover li:nth-child(1) {
  transform: rotate(90deg);
}

.fan:hover li:nth-child(2) {
  transform: rotate(30deg);
}

.fan:hover li:nth-child(3) {
  transform: rotate(45deg);
}

.fan:hover li:nth-child(4) {
  transform: rotate(60deg);
}

.fan:hover li:nth-child(5) {
  transform: rotate(75deg);
}

.fan:hover li:nth-child(6) {
  transform: rotate(15deg);
}

.fan:hover li:nth-child(7) {
  transform: rotate(0deg);
}

.fan:hover li:nth-child(8) {
  transform: rotate(-15deg);
}

.fan:hover li:nth-child(9) {
  transform: rotate(-30deg);
}

.fan:hover li:nth-child(10) {
  transform: rotate(-45deg);
}

.fan:hover li:nth-child(11) {
  transform: rotate(-60deg);
}

.fan:hover li:nth-child(12) {
  transform: rotate(-75deg);
}

.fan:hover li:nth-child(13) {
  transform: rotate(-90deg);
}

button {
  /* margin-top: 70%; */
  /* margin-left: 15%; */
  /* height: 50px; */
  cursor: pointer;
  font-family: STKaiti;
  width: 150px;
  color: #AA7F55;
  background-color: #EACDB1;
  line-height: 50px;
  text-align: center;
  font-weight: 600;
  font-size: 18px;
  border: 0px solid #1E90FF;
  border-radius: 4px;
}

.one {
  /* 金丝纸 */
  /* position: absolute; */
  /* left: 855px; */
  margin-top: 5%;
  margin-left: 70%;
  width: 344px;
  height: 83px;
  opacity: 1;
  font-family: STKaiti;
  font-size: 36px;
  font-weight: normal;
  line-height: normal;
  display: flex;
  align-items: center;
  letter-spacing: 0.2em;
  color: #3D3D3D;
  cursor: pointer;

}

.two {
  /* 相关档案： 1.政府过渡文件 2.城市发展档案 3.图书馆手稿 4.国家安全文件 5.科学研究文献 */
  /* position: absolute; */
  right: 200px;
  top: 100px;
  margin-left: 60%;
  width: 35%;
  height: 200px;
  opacity: 1;
  text-align: justify;
  font-family: STKaiti;
  font-size: 20px;
  font-weight: normal;
  line-height: 29px;
  display: flex;
  text-indent: 2em;
  align-items: center;
  letter-spacing: 0em;
  font-feature-settings: "kern" on;
  color: #3D3D3D;
}

.to {
  /* 相关档案： 1.政府过渡文件 2.城市发展档案 3.图书馆手稿 4.国家安全文件 5.科学研究文献 */
  /* position: absolute; */
  margin-left: 60%;
  top: 1px;
  width: 224px;
  /* height: 213px; */
  opacity: 1;
  font-family: STKaiti;
  font-size: 24px;
  font-weight: normal;
  line-height: normal;
  display: flex;
  align-items: center;
  letter-spacing: 0em;
  font-feature-settings: "kern" on;
  color: #3D3D3D;
}

.wu {
 
  width:70%;
  /* float: left; */
  margin-left: 70%;
  white-space: pre-wrap;
  height: 183px;
  opacity: 1;
  font-family: STKaiti;
  white-space: pre-wrap;
  font-size: 20px;
  font-weight: normal;
  line-height: 20px;
  /* display: flex; */
  align-items: center;
  letter-spacing: 0em;
  /* font-feature-settings: "kern" on; */
  color: #3D3D3D;
}

.jigou {
  /* 档 案 机 构 */
  /* position: absolute; */
  /* left: 40%; */
  /* width: 357px; */
  /* height: 200px; */
  opacity: 1;
  text-align: center;
  color: #8C6846;
  font-family: STKaiti;
  font-size: 48px;
  font-weight: bold;
  line-height: normal;
  align-items: center;
  letter-spacing: 0.2em;

}
 
</style>