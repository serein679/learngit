<!-- import SwiperTest from './components/swiperTest.vue'; -->
<template>
  <div id="app">
    <router-view v-if="showRouter"></router-view>
    <!-- <SwiperTest/> -->
  </div>
</template>
<script>
import SwiperTest from './components/swiperTest.vue';
import { Loading } from 'element-ui';
 

let loading;

export default {
  provide (){
    return {
      reload: this.reload
    }
  },
  data (){
    return {
      showRouter: true
    }
  },
  components: {
    SwiperTest
  },
  mounted() {
    this.bodyScale();
    // 监听页面的窗口的变化，每次窗口变化调用等比例缩放方法
    window.addEventListener("resize", this.bodyScale, false);
  },

 
  methods: {
    startLoading() {  // 使用Element loading-start 方法
      loading = Loading.service({
        lock: true,
        text: '加载中……',
        background: 'rgba(0, 0, 0, 0.7)'
      });
    },
    bodyScale() {
      //获取当前分辨率下的可是区域宽度
      var deviceWidth = document.documentElement.clientWidth;
       // 分母——设计稿的尺寸
      var scale = deviceWidth / 1920;
       //放大缩小相应倍数
      document.documentElement.style.zoom = scale;
    },
    reload (){
      this.showRouter = false
      this.$nextTick(function(){
        this.showRouter = true
      })
    }
  },
}
</script>
<style lang="scss">
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0px;
  width: 100%;
}

body {
  margin: 0;
  padding: 0;
  border: 0
}

/* 解决页面dialog出现时，页面滚动条出现消失导致的Message消息提示抖动问题 */
body {
  overflow-y: auto !important;
  padding-right: 0 !important;
}
</style>
