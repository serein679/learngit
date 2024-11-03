<template>
  <div id="bottom">
    <!-- <el-row style="height: 33px;">

      </el-row> -->
    <el-row style="/* 矩形 40 */
margin-top: 20px;
position: absolute;
width: 100%;
height: 150px;
opacity: 1;	
background: #8C6846;
	">

      <el-col :span="24">
        <div style="height: 30px;color: rgb(255, 255, 255);text-align: center;margin-top: 30px;font-family: D-DIN;">
          版 权 声 明 | 联 系 我 们
        </div>
        <div style="height: 40px;text-align: center;margin-top: 20px;">
          <span style="height: 10px;">

          </span>
          <span style="text-align: center; color: rgb(223, 188, 160);" class="xxz">
            苏公网安备
            <a target="_blank" href="https://www.beian.gov.cn/portal/registerSystemInfo?recordcode=32050802010530"
              rel="noreferrer" style="text-decoration: none;color: rgb(223, 188, 160);">32050802010530</a>
            &nbsp;&nbsp;&nbsp;
          </span>

          <span style="">
            <a target="_blank" href="https://beian.miit.gov.cn/"
              style="text-decoration: none; color: rgb(223, 188, 160);">苏ICP备10229414号-1</a>
            &nbsp;&nbsp;
          </span>
          <span style="color: rgb(223, 188, 160);">
            地址：江苏省苏州市姑苏区十梓街1号
          </span>

        </div>
        <div style="height: 30px;color:  rgb(223, 188, 160);text-align: center;">
          版权所有©苏州大学
        </div>
      </el-col>

    </el-row>



  </div>
</template>
  
<script>
import { mapGetters } from 'vuex'

export default {
  name: 'name',
  data() {
    return {
      activeIndex: '1',
      menuList: [{
        id: 1,
        path: 'dashboard',
        name: '首页'
      }, {
        id: 2,
        path: 'introduction/index',
        name: '学科简介'
      }, {
        id: 3,
        path: 'resource/index',
        name: '教学资源'
      }, {
        id: 4,
        path: 'course/index',
        name: '专业课程'
      }, {
        id: 5,
        path: 'teachers/index',
        name: '师资力量'
      },
      ]
    };
  },
  computed: {
    activeMenu() {
      const route = this.$route
      const { meta, path } = route
      // if set path, the sidebar will highlight the path you set
      if (meta.activeMenu) {
        return meta.activeMenu
      }
      return path
    },
    /*...mapGetters([
      'permission_routes',
      'sidebar'
    ]),
    variables() {
      return variables
    },*/
  },
  methods: {
    mana() {
      this.$router.push('/login/');
    },
  }
}
</script>
  
<style scoped>
.xxz {
  line-height: 30px;


}
</style>