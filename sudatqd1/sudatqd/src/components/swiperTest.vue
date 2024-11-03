<template>
  <div id="swiperTest">
<!-- 
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
    </div> -->
  
    <div class="swiper-container swiper2" style="display:inline-block;width: 50%;">
      <div class="swiper-wrapper" style="display:inline-block;width: 100%;">
        <div class="swiper-slide" v-for="item in archiveList" :key="item.archiveId" >
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
      autoplay: {
          delay: 3000, // 自动播放间隔时间，单位为毫秒
          disableOnInteraction: false, // 用户操作后是否停止自动播放，默认为true，设为false则不会停止
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
        observer:true,//修改swiper自己或子元素时，自动初始化swiper 
        observeParents:true,//修改swiper的父元素时，自动初始化swiper 
        classList: this.classList,
        autoplay: {
          delay: 1500, // 自动播放间隔时间，单位为毫秒
          disableOnInteraction: false, // 用户操作后是否停止自动播放，默认为true，设为false则不会停止
          stopOnLastSlide: false,
        },
        centeredSlides : true,
        pagination: {
                            el: '.swiper-pagination',
                            clickable: true,//设置小原店面可以切换
                        },
                        scrollbar: {
                            el: '.swiper-scrollbar',
                        },

        loop: true, // 启用无限循环
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
.swiper-container .swiper-pagination-bullet {
    width: 25px;
    height: 3px;
    display: inline-block;
    border-radius: 0;
    background: #000;
    opacity: .2;
}
.swiper-container .swiper-pagination-bullet-active {
    opacity: 1;
    background-color: #FF3939!important;
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
  