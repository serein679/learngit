<template>
  <div style="margin-top: 8px;background-color: antiquewhite;">
      <div style="margin:0px;top:140px ;background-color: antiquewhite;">
          <div>
              <el-row>

                  <el-col :span="3" :offset="1">
                      <h3 class="location">当前位置：</h3>
                  </el-col>
                  <el-col :span="10" style="margin-top: 43px;">
                      <el-breadcrumb separator="/">
                          <el-breadcrumb-item :to="{ path: '/introduction' }">纸张基因</el-breadcrumb-item>
                          <el-breadcrumb-item :to="{ path: '/introduction/echarts' }"> 原料库 </el-breadcrumb-item>
                          <el-breadcrumb-item><a href="/">原料详情</a></el-breadcrumb-item>
                      </el-breadcrumb>
                  </el-col>
              </el-row><!--导航-->
              <el-row style="height: auto;">
                  <el-col :span="18"
                      style="position: relative;font-family: STKaiti;font-size: 22px;text-align: left;top: 0%;width: 47%;height: auto;margin-left: 5%;background-color: white;margin-top: 5%; margin-bottom: 10%;border-radius: 30px;height: 440px;">
                      <div>
                          <table :data="oneMa.componentItem " style="margin-left:3%;width: 100% ;line-height: 290%;">
                              <tr>
                                  <div style="width: 90px;margin-top: 3%;margin-left: 0%;">
                                  </div>
                              </tr>
                              <tr>
                                  <div 
                                      style="width: 80%;margin-left: 0;margin-right: 5%;text-indent:0em;text-align: justify;font-family: STKaiti;font-size: 28px;color: rgb(217, 115, 41);">
                                      {{ oneMa.componentItem.componentName }}
                                  </div>
                              </tr>
                              <tr style="margin-left: 40px;">
                                  <div style="margin-left: 40px;">
                                      原料特点： {{ oneMa.componentItem.description }}
                                  </div>
                              </tr>
                              <tr style="margin-left: 40px;">
                                  <div style="margin-left: 40px;">
                                    原料类型： 
            {{ typeName[oneMa.componentItem.componentType] }}
            
                                  </div>
                              </tr>
                              <tr style="margin-left: 40px;">
                                  <div style="margin-left: 40px;float: left;height: 300px;">
                                      相关档案：
                                  </div>
                                  <div v-for="item in oneMa.archiveComponentList" 
                                      style="margin-left: 40px;width: 30%; vertical-align: top;float: left;">
                                      <font
                                          style="cursor: pointer;font-family: STKaiti;font-weight:200;color: rgb(205, 134, 63);display:inline-block;vertical-align: top; "
                                          @click="select(item.archiveId)">
                                          {{  item.archiveName }}</font>
                                  </div>
                              </tr>
                          </table>
                      </div>
                  </el-col>
                  <el-col :span="6"
                      style="position: relative;font-family: STKaiti;font-size: 22px;text-align: left;top: 0%;height: auto;margin-left: 15%;margin-top: 5%; margin-bottom: 5%;border-radius: 30px;height: 440px;">
                      <div>
                          <template>
                              <el-carousel indicator-position="outside" height="440px" width="640px">
                                  <el-carousel-item v-for="item in oneMa.pictureList" :key="item">
                                      <img preview :src="item.pictureLocation" width="640px" height="440px" >
                                      <!-- <el-image 
  style="width: 540px; height: 540px"
  :src="item.pictureLocation" 
  :preview-src-list="oneMa.pictureList.pictureLocation">
</el-image> -->
                                  </el-carousel-item>
                              </el-carousel>
                          </template>
                      </div>
                  </el-col>
              </el-row>
              <el-row>
                  <p style="text-align: right;" class="last-style" @click="goback"><el-button>返回</el-button></p>

              </el-row>
          </div>
      </div>
  </div>
</template>
<script >

import { getArchivePic, getArchives, PageAllArchive } from '@/api/archive'
import { getOneInstitution, getOneIn, getOnePa } from '@/api/institution'
import { getComponentDetail } from '@/api/coponent'


export default {
  name: "Resource",
  components: {
      
  },
  data() {
      return {
          oneMa: [],
          ins: [],
          fibers: [],
          fiberId: '',
          archiveList: [],
          institutionId: '',
          paperList: [],
          institutionList: [],
          typeName1: {
              '0': "手工纸",
              '1': "机械纸",
              '2': "半手工半机械纸",
          },
          typeName: {
                '0': "填料成分",
                '1': "染色成分",
                '2': "施胶成分",
                '3': "涂布成分",
            },
          url: '',
          previewVisible: false,
          pdfSrc: '', // 将从后台获取到的 PDF 文件地址赋值给这个字段
      }
  },
  created() {
      this.gettingData();
      // this.getOneInstitution()        //   this.downloadFile(resourceContent, resourceType,);
  },
  mounted() {
      // 页面加载就渲染列表
      this.getComponentDetail(this.componentId);
  },
  watch: { '$route': 'gettingData' },
  methods: {
      gettingData() {
          var queryComponentId = JSON.parse(this.$Base64.decode(this.$route.query.info))
          this.componentId = queryComponentId;
          // console.log(fiberId)
      },

      goback() {
          this.$router.go(-1)
      },//返回上一页
      getArchives() {
          getArchives().then(response => {
              this.archiveList = response.data
          })
      },
      select(archiveId) {
      if (archiveId !== '') {
        console.log(archiveId)
        this.$router.push({
          path: './selectAr',
          query: {
            info: this.$Base64.encode(archiveId)
          }
        })
      }
    },
      getOneInstitution() {
          getOneInstitution().then(response => {
              this.his = response.data
          })
      },


      getOnePa() {
          getOnePa().then(response => {
              this.paperList = response.data
          })
      },
      getComponentDetail(componentId) {
        getComponentDetail(componentId).then((response) => {
              this.oneMa = response.data;
              console.log(this.oneMa);
          });
      },

  },
}
</script>
<style scoped>
.content {
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;
}

.box1 {
  margin-top: 8px;
  position: relative;
  box-sizing: border-box;
}

.title-box {
  background: #ffffff;
  padding: 20px;
  font-size: 25px;
  font-family: "STKaiti";
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

.biao {
  /* 构皮纸 */

  position: absolute;
  width: 244px;
  height: 83px;
  opacity: 1;
  font-family: HelloFont ID XuanZhenSong;
  font-size: 36px;
  font-weight: bold;
  line-height: normal;
  display: flex;
  text-align: center;
  letter-spacing: 0.2em;
  top: 13%;
  right: 4%;
  color: #3D3D3D;



}

.navigation {
  padding: 0 20px;
  height: 40px;
  box-sizing: border-box;
  line-height: 40px;
  display: inline-block;
  list-style: none;
  font-size: 14px;
  font-weight: 500;
  color: #303133;
  position: relative;
  margin-right: 3px;
  margin-left: 54px;
}

.main-area {
  /* margin-left:  0px; */
  border-top: solid rgb(134, 116, 21);
  width: 100%;
}

.jianjie {
  /* 简介： 构皮纸，古法造纸的一种。因构树的广泛分布，以构树皮为原料的构皮纸至今仍散见于很多偏僻的乡村，尤其是贵州、云南和广西三省区。而在贵州的丹寨县石桥村，能工巧匠们为构皮纸注入了新鲜的血液，古老的纸张变得鲜活起来。 使用档案： MG19130201 票据 MG19130201 书信 MG19130201 票据 MG19130201 基建 MG19130201 票据 */

  position: absolute;
  left: 0px;
  top: 269px;
  width: 971px;
  height: 506px;
  opacity: 1;
  font-family: STKaiti;
  font-size: 24px;
  font-weight: normal;
  line-height: normal;
  display: flex;
  align-items: left;
  letter-spacing: 0.2em;

}

.el-breadcrumb__item {
  font-size: 20px;
  font-family: "STKaiti";
}

h2 {
  border-bottom: 3px solid #b03632;
}

.title {
  font-size: 24px;
  font-family: "STKaiti";
  font-weight: bold;
  color: black;

}

.label {
  font-size: 18px;
  font-family: "STKaiti";
  text-align: start;
  left: 0;
  line-height: 40px;
}

.introductionbox {
  font-size: 14px;
  font-family: "STKaiti";
  text-align: start;
  left: 0;
}


.last-style {
  font-family: "STKaiti";
  letter-spacing: 0em;
  margin-top: 8%;
  font-size: 18px;
  text-align: justify;
  color: bisque;

}
</style>
