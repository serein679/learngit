<template>
  <el-container>
    <el-header style="padding: 0%;">
      <Header />
    </el-header>
    <el-container style="background-color:antiquewhite">
      <el-aside width="250px" style="background-color: #AA7F55;" class="el-aside">
        <el-menu style="height: auto;background-color: #AA7F55;" :default-active="activeMenu" router mode="horizontal"
          class="cd">
          <el-menu-item v-for="item in menuList" :index="'/' + item.path" :key="item.id"
            style="height: 100px;width: 250px;text-align: center;font-family:STKaiti;font-size: 20px;margin-top: 0px;color: black;">
            {{ item.name }}
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-main style="width: 300px;height:auto">
          <AppMain />
        </el-main>
      </el-container>
    </el-container>
    <el-footer style="padding: 0%;">
      <Bottom />

    </el-footer>

  </el-container>
</template>

<script>
import Header from './components/header1.vue'
import AppMain from './components/main.vue'
import { mapState } from 'vuex';
import Bottom from './components/bottom.vue';
var yj = document.getElementsByClassName('cd');
for (var i = 0; i < yj.length; i++) {
  yj[i].onmouseover = function () {
    //console.log(i)
    this.style.backgroundColor = "red"
  }
  yj[i].onmouseout = function () {
    this.style.backgroundColor = "";
  }
};

export default {
  data() {
    return {
      activeIndex: '1',
      menuList: [{
        id: 1,
        path: 'management/projectManagement',
        name: '纸张管理'
      }, {
        id: 2,
        path: 'management/forumManagement',
        name: '档案管理'
      }, {
        id: 3,
        path: 'management/mienManagement',
        name: '机构管理'
      }, {
        id: 4,
        path: 'management/cooperationManagement',
        name: '成分管理'
      }, {
        id: 5,
        path: 'management/introManagement',
        name: '纤维原料管理'
      },
      {
        id: 6,
        path: 'management/achvManagement',
        name: '审核管理'
      },
      {
        id: 7,
        path: 'management/resourceManagement',
        name: '角色管理'
      },
      {
        id: 8,
        path: 'management/permissionManagement',
        name: '权限管理'
      },
      {
        id: 9,
        path: 'management/wordManagement',
        name: '敏感词管理'
      },
      {
        id: 11,
        path: 'management/userManagement',

        name: '用户管理'
      },

      ],
    }
  },
  components: {
    Header,
    AppMain,
    Bottom
  },
  computed: {
    ...mapState({
    }),
    classObj() {
      return {
      }
    },
    activeMenu() {
      const route = this.$route
      const { meta, path } = route
      // if set path, the sidebar will highlight the path you set
      if (meta.activeMenu) {
        return meta.activeMenu
      }
      return path
    },
  },


}
</script>
<style>
.cd {
  width: 200px;
  height: 20px;
  text-align: center;
  float: left;
  margin: 5px;
}

.el-menu-item.is-active {
  background-color: #C8A078 !important;
}

/* .el-submenu .el-menu-item:hover {
  background-color: #00000055 !important;
} */
.el-menu-item:hover {
  background: unset;
}

.el-menu-item:hover {
  border-top: 0px solid #6e5022;
  border-right: 0px solid #6e5022;
  border-bottom: 0px;
  /* display: block; */

  color: rgb(255, 255, 255) !important;
  background-color: #C8A078 !important;
}

/* .el-tabs__item:hover {
  color: #303133;
}

.el-menu-item:hover {
  color: #05173c;
} */

.el-aside {
  background-color: #313743;

  .el-menu {
    border-right: none;
  }
}
</style>