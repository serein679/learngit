<template>
  <div style="margin-top: 8px">
    <div style="margin-top: 8px;background-color: antiquewhite;">

      <div style="margin:0px;top:140px ;background-color: antiquewhite;">

        <div>
          <el-row>

            <el-col :span="3" :offset="1">

              <h3 class="location">当前位置：</h3>
            </el-col>
            <el-col :span="10" style="margin-top: 43px;">
              <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/teachers/index' }">大事记</el-breadcrumb-item>
                <el-breadcrumb-item><a href="/">事记详情</a></el-breadcrumb-item>
              </el-breadcrumb>
            </el-col>
          </el-row><!--导航-->

          <el-row style="height: 10px;">
            <el-col :span="24" class="main-area" v-for="(r, index) in oneEvent" :key="index">
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24" class="main" v-for="(r, index) in oneEvent">
              {{ r.eventName }}
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24" class="main1" v-for="(r, index) in oneEvent">
              <div style="margin-top: 10px;margin-right: 50px;" v-for="(r, index) in oneEvent">
                事记时间：{{ new Date(r.eventDate).toLocaleDateString('zh').replaceAll('/', '-') }}
              </div>
            </el-col>
          </el-row>

          <el-row style="height: auto;">
            <el-col :span="22"
              style="position: relative;font-family: STKaiti;font-size: 22px;text-align: left;top: 0%;width: 85%px;height: auto;margin-left: 10%;margin-left: 5%;background-color: white;margin-top: 3%; margin-bottom: 5%;border-radius: 30px;">
              <div>
                <table v-for="(r, index) in oneEvent"
                  style="margin-left:5%;width: 100% ;line-height: 200%;text-align: justify;">
                  <!-- <tr style="text-align: center;">
                    <div class="biao">
                      {{ r.eventName }}
                    </div>
                  </tr> -->
                  <tr>
                    <div v-for="(r, index) in oneEvent"
                      style="width: 90%;margin-left: 5;margin-right: 5%;text-indent: 2em;line-height: 200%;text-align: justify;margin-top: 2%;">
                      <p style="font-family: STKaiti" v-html="r.eventDescription" >
                      </p>
                      <!-- {{ r.eventDescription }} -->
                    </div>
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
  </div>
</template>
<script>

import { getHistoricalEvents, getOneHistoricalEvent } from '@/api/his'



export default {
  name: 'Introduction',
  components: {
  },

  data() {
    return {
      subjectList1: [],
      creatorId: '',
      his: [],
      oneEvent: [],
      typeName00: {
        '0': "教授",
        '1': "副教授",
        '2': "讲师",
      },//将资源类型转换
      typelist13: [{
        title: '0',
        typeName00: "教授",
      }, {
        title: '1',
        typeName00: "副教授",
      }, {
        title: '2',
        typeName00: "讲师",
      },], // 资源类型列表
      oneTeacher: [],
      dialogVisible: false,
      formLabelWidth: '120px',
      count: 0,
      eventId: '',
    }
  },

  created() {
    // this.getOneHistoricalEvent();
    this.gettingData()
  },

  mounted() {
    // 页面加载就渲染列表
    this.getOneHistoricalEvent(this.eventId);
  },

  watch: { '$route': 'gettingData' },

  methods: {
    gettingData() {
      var queryEventId = JSON.parse(this.$Base64.decode(this.$route.query.info))
      this.eventId = queryEventId;
    },

    getOneHistoricalEvent(eventId) {
      getOneHistoricalEvent(eventId).then((response) => {
        this.oneEvent = response.data;
        console.log(this.oneEvent);
      });
    },
    getTeacherIntro() {
      TeacherIntro().then(response => {
        this.teachers = response.data
      })
    },
    openDialog(id) {
      OneTeacherIntro({ id: id }).then(response => {
        this.dialogVisible = true
        this.oneteacher = response.data[0]
      })
    },

    getSubject1() {
      ResourceList(0).then((response) => {
        this.subjectList1 = response.data;
      });
    },

    getProjects() {
      Projects().then(response => {
        this.project = response.data
      })
    },

    select(courseId) {
      if (courseId !== '') {
        console.log(courseId)
        this.$router.push({
          path: '/course/detail',
          query: {
            info: this.$Base64.encode(courseId)
          }
        })
      }
    },

    goindex(activeName) {
      console.log(activeName)
      this.$router.push({
        path: './index',
        query: {
          activeName: this.$Base64.encode(activeName)
        }
      })
    },

    goback() {
      this.$router.go(-1)
    }//返回上一页
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


.last-style {
  font-family: "STKaiti";
  letter-spacing: 0em;
  margin-top: 8%;
  font-size: 18px;
  text-align: justify;

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
  /* width: 244px; */
  /* height: 83px; */
  opacity: 1;
  font-family: STKaiti;
  font-size: 36px;
  font-weight: bold;
  line-height: normal;
  /* display: flex; */
  text-align: center;
  letter-spacing: 0.2em;
  /* top: 13%; */
  /* right: 30%; */
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


.content {
  width: 100%;
  position: absolute;
  top: 0;
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

.main {
  font-family: STKaiti;
  font-size: 36px;
  font-weight: bold;
  line-height: normal;
  /* display: flex; */
  text-align: center;
  letter-spacing: 0.2em;
  margin-top: 50px;
}

.main1 {
  font-family: STKaiti;
  font-weight: bold;

  font-size: 22px;
  /* font-weight: bold; */
  line-height: normal;
  /* display: flex; */
  text-align: right;
  letter-spacing: 0.2em;
  margin-right: 15%;
  /* margin-top: 50px; */
}

hr {
  border: 0px;
  border-bottom: 3px solid #B03632;
}
</style>