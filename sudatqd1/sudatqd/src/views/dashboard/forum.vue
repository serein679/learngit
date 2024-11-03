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
              <el-breadcrumb-item><a href="/">纸张详情</a></el-breadcrumb-item>
            </el-breadcrumb>
          </el-col>
        </el-row><!--导航-->
        <el-row style="height: 10px;">
          <el-col :span="24" class="main-area" v-for="(r, index) in onePaper" :key="index">
          </el-col>
        </el-row>
        <el-row style="height: auto;">
          <el-col :span="9" style="position: relative;
    font-family: 楷体;
    font-size: 22px;
    text-align: left;
    top: 0%;
    width: 800px;
    height: auto;
    margin-left: 10%;
    margin-left: 5%;
    background-color: white;
    margin-top: 5%; 
    margin-bottom: 5%;
    border-radius: 30px;">
            <div>
              <table v-for="(r, index) in onePaper" style="margin-left:5%;width: 100% ;line-height: 200%;">
                <tr>
                  <div style="width: 90px;margin-top: 5%;margin-left: 0%;font-weight: bold;">
                    简介：
                  </div>
                </tr>
                <tr>
                  <div v-for="(r, index) in onePaper"
                    style="width: 90%;margin-left: 5;margin-right: 5%;text-indent: 2em;line-height: 200%;text-align: justify;margin-top: 15px;"
                    white-space:pre>
                    {{ r.paperDes }}
                    <br>
                  </div>
                </tr>
                <tr>
                  <div style="margin-top: 30px;font-weight: bold;">制作流程：</div>
                </tr>
                <tr>
                  <div v-for="(r, index) in onePaper"
                    style="width: 90%;margin-left: 5;margin-right: 5%;text-indent: 2em;line-height: 200%;text-align: justify;margin-top: 5px;">
                    {{ r.process }}
                  </div>
                </tr>
                <tr>
                  <div style="margin-top: 30px;font-weight: bold;float: left;">产地：</div>
                  <div style="float: left;margin-top: 30px;"> {{ r.location }}</div>
                </tr>
                <tr>
                  <div style="margin-top: 30px;font-weight: bold;float: left;">纤维：</div>
                  <div style="float: left;margin-top: 30px;cursor: pointer;" v-for="z in fibers"
                    v-if="z.paperId == r.paperId"> {{
                      z.fiberType }} 　</div>
                </tr>
                <tr>
                  <div style="margin-top: 30px;font-weight: bold;float: left;">原料：</div>
                  <div style="float: left;margin-top: 30px;cursor: pointer;" v-for="t in material"
                    v-if="t.paperId == r.paperId"> {{
                      t.materialName }} 　</div>
                </tr>
                <tr>
                  <div style="margin-top: 30px;font-weight: bold;float: left;">纸张类型：
                  </div>
                  <div style="float: left;margin-top: 30px;">
                    <template>
                      {{ typeName1[r.paperType] }}
                    </template>
                  </div>

                </tr>
                <tr>
                  <div style="width: 90%;margin-top: 30px;font-weight: bold;">
                    相关档案：
                  </div>
                </tr>
                <tr v-for="item in archiveList" v-if="item.paperId == r.paperId" style="margin-left: 25px;">
                  <div style="margin-left: 15%;cursor: pointer;color: peru;" @click="select(item.archiveId)">
                    {{ item.archiveName }}
                  </div>
                </tr>
              </table>
            </div>
          </el-col>
          <el-col :span="6" style="float: right;   margin-top: 15%;margin-right: 5%; ">
            <div>
              <table v-for="(r, index) in onePaper" style="margin-left:5%;width: 100% ;line-height: 152%;">
                <tr>
                  <div class="biao">
                    {{ r.paperName }}
                  </div>
                </tr>
                <tr>
                  <!-- <viewer style="height: 100%;width: 100%">
                    <img :src="r.picture" width="350px" height="250px">
                  </viewer> -->
                  <img :src="r.picture" width="340px" height="250px" v-viewer style="margin-top: 35%;">
                  <!-- <el-image ref=" elImageRef" v-show="false" class="img" :src="imgUrl" :preview-src-
                  list="imgList">
                </el-image> -->

                </tr>
              </table>
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
//   import Carousel from "@/components/Carousel.vue";
import { getPaper, PageAllPaper, getOnePaper } from '@/api/paper'
import { getArchivePic, getArchives, PageAllArchive } from '@/api/archive'
import { getPaperRawMaterials } from '@/api/material'
import { getFibers } from '@/api/fiber'



//  var app = new Vue({ el:'app', )} 
//   import { Base64 } from "js-base64";
export default {
  name: "Resource",
  components: {
    //   Carousel,
  },
  data() {
    return {
      onePaper: [],
      paperId: '',
      fibers: [],
      material: [],
      archiveList: [],
      typeName1: {
        '0': "手工纸",
        '1': "机械纸",
        '2': "半手工半机械纸",
      },
      url: '',
      previewVisible: false,
      pdfSrc: '', // 将从后台获取到的 PDF 文件地址赋值给这个字段
    }
  },
  created() {
    this.gettingData();
    this.getArchives()
    this.getFibers()

    this.getPaperRawMaterials()


    //   this.downloadFile(resourceContent, resourceType,);

  },
  mounted() {
    // 页面加载就渲染列表
    this.getOnePaper(this.paperId);
  },
  watch: { '$route': 'gettingData' },
  methods: {
    gettingData() {
      var queryPaperId = JSON.parse(this.$Base64.decode(this.$route.query.info))
      this.paperId = queryPaperId;
    },
    goback() {
      this.$router.go(-1)
    },//返回上一页
    getArchives() {
      getArchives().then(response => {
        this.archiveList = response.data
      })
    },
    getFibers() {
      getFibers().then(response => {
        this.fibers = response.data
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
    getPaperRawMaterials() {
      getPaperRawMaterials().then(response => {
        this.material = response.data
      })
    },
    // getPaperRawMaterials (){

    // },

    getOnePaper(paperId) {
      getOnePaper(paperId).then((response) => {
        this.onePaper = response.data;
        console.log(this.onePaper);
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

.sousuo {
  bottom: 12px;
  width: 100px;
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
  font-family: "华文中宋";
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
  top: 20%;
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
  font-family: 楷体;
  font-size: 24px;
  font-weight: normal;
  line-height: normal;
  display: flex;
  align-items: left;
  letter-spacing: 0.2em;

}

.el-breadcrumb__item {
  font-size: 20px;
  font-family: "华文中宋";
}

h2 {
  border-bottom: 3px solid #b03632;
}

.title {
  font-size: 24px;
  font-family: "楷体";
  font-weight: bold;
  color: black;

}

.label {
  font-size: 18px;
  font-family: "宋体";
  text-align: start;
  left: 0;
  line-height: 40px;
}

.introductionbox {
  font-size: 14px;
  font-family: "宋体";
  text-align: start;
  left: 0;
}


.last-style {
  font-family: "宋体";
  letter-spacing: 0em;
  margin-top: 8%;
  font-size: 18px;
  text-align: justify;
  color: bisque;

}
</style>
      