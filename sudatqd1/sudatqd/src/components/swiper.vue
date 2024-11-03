<template>
  <div id="swiperTest">

    <div class="swiper-container swiper1" style="display:inline-block;width:60%;">
      <div class="swiper-wrapper">
        <div class="swiper-slide" v-for="item in archiveList" :key="item.archiveId">
          <h3 class="mingzi" @click="select(item.archiveId)">{{ item.archiveName }}</h3>
          <p class="duanluo">
            {{ item.archiveContent }}
          </p>
          <p class="duanluo1" style="cursor: pointer;" @click="select1(item.paperId)">
            该档案用纸为：<font style="font-weight: bold;">{{ item.paperName }}</font>
          </p>
        </div>
      </div>
      <div class="swiper-button-prev swiper-button-white"></div>
      <div class="swiper-button-next swiper-button-black"></div>
    </div>
    <div class="swiper-container swiper2" style="display:inline-block;width: 40%;">
      <div class="swiper-wrapper">
        <div class="swiper-slide" v-for="item in archiveList" :key="item.archiveId">
          <!-- <img src="../assets/backphoto.jpg" style="width: 100%;height: 100%;"> -->
          <img :src="item.pictureLocation" style="width: 100%;height: 100%;cursor: pointer;"
            @click="select(item.archiveId)">
        </div>
      </div>
      <!-- 如果需要分页器 -->
      <div class="swiper-pagination"></div>
    </div>

  </div>
</template>
<script>
import 'swiper/css/swiper.min.css'  //引入swiper样式
import Swiper from 'swiper';	//引入swiper
import { getArchiveHomeList } from '@/api/archive'

export default {
  name: "Swiper",
  data() {
    return {
      archives: [],
      archiveList: [],
      imgs: [
        { pic: require('../assets/backphoto.jpg') },
        { pic: require('../assets/奖状.png') },
        { pic: require('../assets/backphoto.jpg') }
      ],
      classList: ["one", "two", "three", "four", "five"],

    }
  },
  created() {
    this.getArchives()
 

  },
  methods: {

    getArchives() {
      getArchiveHomeList().then(response => {
        this.archiveList = response.data
      })
    },

    select(archiveId) {
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
    select1(paperId) {
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

    go() {
      this.$router.push({
        path: '/introduction/index'
      })
    },//返回上一页
 
  },
  mounted() {
    new Swiper('.swiper1', {
      observer: true,
      // direction: 'vertical', // 垂直切换选项
      loop: true, // 循环模式选项
      observer: true,//修改swiper自己或子元素时，自动初始化swiper
      observeParents: false,//修改swiper的父元素时，自动初始化swiper
      pagination: {
        //   el: '.swiper-pagination',
        //   clickable: true,
      },
      navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
      },
      // 如果需要滚动条
      scrollbar: {
        el: '.swiper-scrollbar',
      },
    }),

      new Swiper('.swiper2', {
        observer: true,
        direction: 'vertical', // 垂直切换选项
        loop: true, // 循环模式选项
        observer: true,//修改swiper自己或子元素时，自动初始化swiper
        observeParents: false,//修改swiper的父元素时，自动初始化swiper
        classList: this.classList,
        pagination: {
          //   el: '.swiper-pagination',
          //   clickable: true,
        },
        // clickable: true,
        // 如果需要前进后退按钮
        coverflowEffect: {
          rotate: 0,
          stretch: 0,
          depth: 500,
          modifier: 1,
          slideShadows: false,
        },

        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev',
        },
        // 如果需要滚动条
        scrollbar: {
          el: '.swiper-scrollbar',
        },
      })
  },

}
</script>
  <!-- lang="less" -->
<style scoped >
.swiper-container {
  width: 100%;
  height: 580PX;
  border-radius: 0.1rem;
}

h3 {
  /* 中美友好通商 航海条约 */

  position: absolute;
  left: 170px;
  /* top: 300px; */
  width: 250px;
  height: 112px;
  opacity: 1;
  font-family: STKaiti;
  font-size: 36px;
  font-weight: 300;
  line-height: normal;
  letter-spacing: 0em;

  color: #000000;

}

.duanluo {

  /* position: absolute; */
  margin-left: 10%;
  text-indent: 2em;
  text-align: justify;
  margin-top: 15%;
  width: 75%;
  height: 285px;
  opacity: 1;
  font-family: STKaiti;
  font-size: 19px;
  font-weight: normal;
  line-height: 50px;
  display: flex;
  align-items: center;
  letter-spacing: 0em;
  font-feature-settings: "kern" on;
  font-weight: 400;
  font-size: 18;
  /* 1946年蒋介石政府与美国政府签订的一个包括通商航海设领等内容的不平等条约。简称《中美商约》。1946年全面内战爆发后，国民党为了在内战中取得美国更大的支持和援助，由外交部长王世杰与美国驻华大使司徒雷登在南京签署该条约。该档案用纸为： */
  color: #595858
}

.duanluo1 {

  /* position: absolute; */
  margin-left: 10%;
  /* text-indent: 2em; */
  text-align: justify;
  margin-top: 10%;
  /* width: 90px; */
  height: auto;
  opacity: 1;
  font-family: STKaiti;
  font-size: 18px;
  font-weight: normal;
  line-height: 50px;
  display: flex;
  align-items: center;
  letter-spacing: 0em;
  font-feature-settings: "kern" on;
  font-weight: 400;
  font-size: 18;
  font-weight: 400;
  font-size: 18;
  /* 1946年蒋介石政府与美国政府签订的一个包括通商航海设领等内容的不平等条约。简称《中美商约》。1946年全面内战爆发后，国民党为了在内战中取得美国更大的支持和援助，由外交部长王世杰与美国驻华大使司徒雷登在南京签署该条约。该档案用纸为： */
  color: #201f1f
}

.duanluo2 {

  /* position: absolute; */
  margin-left: 25%;
  /* text-indent: 2em; */
  text-align: justify;
  /* margin-top: 10%; */
  width: 500px;
  display: right;
  font-family: STKaiti;
  font-size: 20px;
  cursor: pointer;
  color: #AA7F55;
}

.mingzi {
 
  top: 30px;
  width: 600px;
  height: 150px;
  opacity: 1;
  font-family: STKaiti;
  font-size: 36px;
  font-weight: 300;
  line-height: 60px;
  letter-spacing: 0em;
 
  color: #000000;
  cursor: pointer;

}

button {
  margin-top: 80%;
  margin-left: 0%;
  height: 50px;
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
</style>
  