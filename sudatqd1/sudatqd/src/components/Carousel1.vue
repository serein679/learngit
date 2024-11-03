<template>
  <el-carousel indicator-position="outside" height="900px">
    <el-carousel-item v-for="item in data1" :key="item.eventId">
      <h3>
        <img :src="item.eventPic" style="height: 845px;width: 1120px;cursor: pointer;margin-top: 0%;"
      @click="selectEvent(item.eventId)">
      <div style="background-color: aliceblue;width: 1120px;margin: 0%">
        <span style="font-size: 28px;background-color: aliceblue;width: 1120px;">{{item.eventName}}</span>
      </div>

      </h3>
      
    </el-carousel-item>
  </el-carousel>
</template>

<script>
import { getArchiveHomeList } from '@/api/archive'
import { getHistoryList } from '@/api/his'
export default {
  name: 'Carousel',
  data() {
    return {
      archives: [],
      archiveList: [],
      data1: [],
    }
  },
  created() {
    this.getArchives()
    this.getHistoryList()

  },
  methods: {

    getArchives() {
      getArchiveHomeList().then(response => {
        this.archiveList = response.data
      })
    },
    getHistoryList() {
      getHistoryList().then(response => {
        this.data1 = response.data
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
}
</script>

<style scoped>
  .el-carousel__item:nth-child(2n) {
    background-color: #808c9c00;
  }
  
  .el-carousel__item:nth-child(2n+1) {
    background-color: #ffffff00;
  }
  .el-carousel__container{
  /*height: 456px;*/
  height: 100% !important;
}

  .el-carousel__item {
    width: 100%;
    /* height: 900px; */
    /* width: 980px; */
    display: inline-block;
    /* overflow: hidden; */
    z-index: 0;
}
.el-carousel__arrow {
    border: none;
    outline: 0;
    padding: 0;
    margin: 0;
    height: 46px;
    width: 46px;
    cursor: pointer;
    transition: .3s;
    border-radius: 50%;
    background-color: rgba(31, 45, 61, .11);
    color: #FFF;
    position: absolute !important;
    top: 90% !important;
    z-index: 10;
    transform: translateY(-50%);
    text-align: center;
    font-size: 24px;
    }
</style>