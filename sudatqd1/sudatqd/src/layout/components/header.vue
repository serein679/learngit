<template class="header">
  <div>
    <el-row class="head" :gutter="20" type="flex" style="flex-wrap:wrap; margin-left: 0px;">
      <el-col :span="4" class="juxing">
        <div class="logo">
          <img src="~@/assets/logo1.png" width="170px" height="150px" style="margin-top: -60px;margin-left: 5%;">
        </div>
      </el-col>
      <el-col :span="14">
        <div class="menuclass">
          <el-menu :default-active="activeMenu" router mode="horizontal" active-text-color="white">
            <el-menu-item v-for="item in menuList" v-if="item.id == 1" :index="'/' + item.path" :key="item.id"
              style="width: 17%;">
              {{ item.name }}
            </el-menu-item>
            <el-menu-item v-for="item in  menuList " v-if="item.id == 2" :key="item.id" :index="'/' + item.path"
              style="width: 17%;">
              <el-dropdown trigger="hover" style="margin-top:-2px; ">
                <el-button type="text">
                  <span style="font-family: 'STKaiti'; font-size: 22px;float: left;
  /* height: 120px; */
  line-height: 60px;
  margin-top: -5px;
  border-bottom: 2px solid transparent;
  color: #ffffff;">
                    纸张基因
                  </span>
                </el-button>

                <el-dropdown-menu slot="dropdown" width="300px" :append-to-body="false" style="text-align: right;">
                  <!-- <el-dropdown-item @click.native="userInfo()">个人中心</el-dropdown-item> -->
                  <el-button size="large" style="margin: px 0px;" round plain @click="shujuku">
                    <span style="font-family: 'STKaiti'; font-size: 16px;">数据库</span>
                  </el-button>
                  <br>
                  <el-button size="large" style="margin: 0px 0px;" round plain @click.native="yuanliaoyun">
                    <span style="font-family: 'STKaiti'; font-size: 16px;">原料云</span>
                  </el-button>
                  <br>
                  <el-button size="large" style="margin: 0px 0px;" round plain @click.native="guanxitu">
                    <span style="font-family: 'STKaiti'; font-size: 16px;">关系图</span>
                  </el-button>
                </el-dropdown-menu>
              </el-dropdown>
            </el-menu-item>
            <el-menu-item v-for=" item  in  menuList " v-if="item.id >= 3" :index="'/' + item.path" :key="item.id"
              style="width: 17%;">
              {{ item.name }}
            </el-menu-item>
          </el-menu>
        </div>
      </el-col>
      <el-col :span="2" style="height: 80px;">
        <span v-if="!verifyLogin()" style="height: 80px;justify-items: center;">
          <div @click="handleLogin()" cursor: pointer style="height: 80px;justify-items: center;">
            <span class="denglu" cursor: pointer>登录</span>
          </div>
        </span>
        <span v-else  style="height: 80px;padding-top: 10%;position:relative;display:block;">
          <el-dropdown trigger="click" >
            <el-button type="text">
              <span style="font-family: 'STKaiti'; font-size: 24px; ">
                <i class="el-icon-user-solid"></i>{{ username }}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
            </el-button>
            <el-dropdown-menu slot="dropdown" :append-to-body="false">
              <el-button size="large" style="margin: 0px 0px;" round plain @click="logout">
                <span style="font-family: 'STKaiti'; font-size: 16px;">退出登录</span>
              </el-button>
              <el-button size="large" style="margin: 0px 0px;" round plain @click.native="userInfo()">
                <span style="font-family: 'STKaiti'; font-size: 16px;">个人中心</span>
              </el-button>
            </el-dropdown-menu>
          </el-dropdown>
          &nbsp;&nbsp;&nbsp;
        </span>
      </el-col>
      <el-col :span="4" >
        <div class="sousuo">
          <input type="text" class="kw" v-model="value" value="请输入关键词......"
            onfocus="if(this.value=='请输入关键词......'){ this.value= '' }">
          <input type="button" @click="windows(value)" class="ok">

        </div>

      </el-col>
    </el-row>


  </div>
</template>

<script>
import FullySearch from '@/views/dashboard/fullySearch.vue';
import Search from './Search'
import { Select } from "@/api/archive"; //引用


export default {
  data() {
    return {
      value: '',
      activeIndex: '1',
      loginVisible: false,
      formLabelWidth: '100px',
      redirect: undefined,
      otherQuery: {},
      menuList: [{
        id: 1,
        path: 'dashboard',
        name: '首页'
      }, {
        id: 2,
        path: 'introduction/index',
        name: '纸张基因',
        submenuLsit: [
          {
            title: '基因库',
            icon: 'el-icon-menu',
            // imgIconUrl: require('@/assets/icon/icon_project.png'),
            path: "introduction/index",
          },
          {
            title: '关联图',
            icon: 'el-icon-menu',
            // imgIconUrl: require('@/assets/icon/icon_project.png'),
            path: "introduction/echarts",
          }
        ]
      }, {
        id: 3,
        path: 'resource/index',
        name: '纵横时空'
      }, {
        id: 4,
        path: 'course/index',
        name: '循纸觅踪'
      }, {
        id: 5,
        path: 'teachers/index',
        name: '大事记'
      }
      ],
      loginform: {
        act: '',
        pwd: '',
      },
      username: '',
      Name: "",
      options: [],
      value: [],
      list: [],
      loading: false,
      quanjuList: [],
    };
  },
  created() {

  },
  components: {
    Search
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
    listSet() {
      return this.quanjuList.map(item => {
        // return { value: `${item.paperName}`, label: `${item.paperName}` }
        return { value: `${item.id}`, label: `${item.name}` };

      });
    },
  },
  methods: {
    remoteMethod(query) {
      if (query !== '') {
        this.loading = true;
        console.log(query)
        Select(query).then((response) => {
          this.quanjuList = response.data;
        });
        setTimeout(() => {
          this.loading = false;
          this.options = this.listSet.filter(item => {
            return item.label.toLowerCase()
              .indexOf(query.toLowerCase()) > -1;
          });
        }, 1000);
      } else {
        this.options = [];
      }
    },
    clearText() {
      console.log(777)

    },
    windows(params) {
      // console.log(this.value)
      if (params !== '') {
        console.log(params)
        let routeDate = this.$router.resolve({
          path: '/fullySearch',
          query: {
            info: params
          }
        });
        window.open(routeDate.href, '_blank')
        this.params = '';
        this.value = '';
        // value = '';
      }
      // window.open("/dashboard/fullySearch")
    },
    on1(paste) {
      var _this = (this);
      setTimeout(function () {
        var_kw = _this.val();
      }, 200);
    },
    on() {
      kw = ('.kw').val();
      if (kw == "" || kw == "搜索关键词...") {
        if (var_kw == '') {
          my_alert("请输入关键词");
          return false;
        } else {
          kw = var_kw;
        }
      }
      var souce_title = "全文检索";
      window.open("/fully_search/" + kw + '/' + souce_title, '', 'noopener');
    },
    select(Id) {
      if (Id !== '') {
        console.log(Id)
        if (Id.length == 8) {
          this.$router.push({
            path: './introduction/select',
            query: {
              info: this.$Base64.encode(Id)
            }
          })
          this.query = '';
          this.value = '';
          value = '';
        } else if (Id.length == 2) {
          this.$router.push({
            path: './introduction/selectIn',
            query: {
              info: this.$Base64.encode(Id)
            }
          })
          this.query = '';
          this.value = '';
          value = '';
        } else if (Id >= 200) {
          this.$router.push({
            path: './introduction/selectAr',
            query: {
              info: this.$Base64.encode(Id)
            }
          })
          this.query = '';
          this.value = '';
          value = '';
        } else {
          this.$router.push({
            path: './teachers/detail',
            query: {
              info: this.$Base64.encode(Id)
            }
          })
          this.query = '';
          this.value = '';
          value = '';
        }
        this.query = '';
        this.value = '';
        value = '';
      }
    },
    // 判断是否已登录
    verifyLogin() {
      let check = sessionStorage.getItem('SET_TOKEN')
      this.username = sessionStorage.getItem('SET_NAME')
      console.log(check)
      return check
    },
    // 登录

    handleLogin() {

      this.$router.push('/login/index')
    },
    shujuku() {
      this.$router.push('/introduction/index')
    },
    guanxitu() {
      this.$router.push('/introduction/echartsAr')
    },
    //退出登录
    logout() {
      this.$store.dispatch('user/logout')
      this.$router.push(`/dashboard?redirect=${this.$route.fullPath}`)
    },
    yuanliaoyun() {
      this.$router.push('/introduction/echarts')

    },
    //找回密码
    findpassport() {
      this.loginVisible = false;
      this.$router.push('/login/passportFind')
    },
    //个人中心
    userInfo() {
      this.$router.push('/login/userInfo')
    }


  }
}
</script>

<style scoped>
.logo {
  /* LOGO */

  position: absolute;
  left: 44px;
  top: 20px;
  width: 90px;
  height: 20px;
  opacity: 1;

  font-family: HelloFont ID DianKai;
  font-size: 24px;
  font-weight: normal;
  line-height: normal;
  letter-spacing: 0em;

  color: rgba(255, 255, 255, 0.7216);

}
.el-row{
    display:flex;
    flex-wrap: wrap;
}

.kw {
  -webkit-box-flex: 1;
  -ms-flex: 1;
  flex: 1;
  display: block;
  color: #707070;
  font-size: 14px;
  width: calc(100% - 62px);
}

input,
button,
textarea,
select {
  font: 14px/100% "Microsoft YaHei", Arial, Tahoma, Helvetica, sans-serif;
  outline: 0;
  border: 0;
  background: none;
  color: #707070;
}

.sousuo {
  /* bottom: 12px; */
  /* width: 100px; */
  position: relative;
  /* right: 5px; */
  top: 15%;
  border: 1px solid #ddd;
  width: 85%;
  height: 40px;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  /* visibility: hidden; */
  /* opacity: 0; */
  /* -webkit-transition: all 0.2s ease-out 0s; */
  /* transition: all 0.2s ease-out 0s; */
  padding-left: 10px;
  background: #fff;
  border-radius: 3px;
  /* overflow: hidden; */
  z-index: 2;
  -webkit-box-align: center;
  -webkit-align-items: center;
  -ms-flex-align: center;
  align-items: center;
}

.el-input__inner {
  /* -webkit-appearance: none; */
  background-color: #b39a9a !important;
  background-image: none;
  border-radius: 4px;
  border: 1px solid #DCDFE6;
  box-sizing: border-box;
  color: #606266;
  display: inline-block;
  font-size: inherit;
  height: 40px;
  line-height: 40px;
  outline: none;
  padding: 0 0px;
  transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  width: 100%;
}

.el-select-dropdown__item {
  height: 45px !important;
  font-size: 20px !important;
  line-height: 45px;
}

.denglu {
  /* 登录 */
  align-items: center;
  position: relative;
  right: 1%;
  /* left: 90px; */
  top: 25%;
  /* width: 56px; */
  height: 100%;
  opacity: 1;
  cursor: pointer;
  font-family: STKaiti;
  font-size: 24px;
  font-weight: normal;
  line-height: normal;
  letter-spacing: 0em;

  color: #793F08;

}

.head {
  /* 导航 */
  position: relative;
  width: 100%;
  height: 100%;
  /* opacity: 1; */
  background: #AA7F55;
  padding: 0 !important;
  display: flex; 
  flex-wrap: wrap; 
}
.head{
  min-width: 1787px;
}
.juxing {
  /* 矩形 7 */

  position: relative;

  /* height: 79px; */
  opacity: 1;

  background: #AA7F55;

  box-sizing: border-box;
  /* border: 1px solid rgba(255, 255, 255, 0.7216); */

}

.ok {
  color: #312d2d86;
  /* background-color: #d7ad6f00; */
  border-color: #d7ad6f00;
  background: url(../../assets/ico_search.png) no-repeat center center;
  background-size: 19px 19px;
  height: 20px;
  display: block;
  width: 20px;
  margin-right: 7px;
  margin-left: 7px;
  -webkit-flex-shrink: 0;
  -ms-flex-negative: 0;
  flex-shrink: 0;
  cursor: pointer;
}

.el-menu-item {
  transition-duration: 0s;
}

.el-menu-item.is-active {
  background-color: #C8A078 !important;
}

.hide {
  display: none;
}

.nav {
  background-color: #222222;
}

.nav-li>a {
  padding: 16px 20px;
  color: #eeeeee;
  display: block;
  text-decoration: none;
  font-size: 14px;
  line-height: 18px;
}

.myDIV:hover+.hide {
  display: block;
  color: red;
}

.menuclass {
  margin-top: 5px;
  /* line-height:auto; */
  /* 矩形 4 */
  position: relative;
  /* left: 3%; */
  /* width: 1120px; */
  height: 180px;
  opacity: 1;
  background: #AA7F55;

}

.zzz {
  font-size: 18px;
  font-family: "华文中宋";
  color: #cdc9ca;
  text-align: right;
  line-height: 15px;
  margin-top: 0px;
  margin-right: 0px;
}

.el-menu {
  list-style: none;
  position: relative;
  /* left: 3%; */
  border: 0 !important;
  margin: 0;
  padding-left: 22px;
  background-color: #AA7F55;
}

.menuclass {
  margin-top: 0px !important;
  line-height: 20px;
  position: relative;
  left: 3%;
  height: 80px;
  opacity: 1;
  background: #AA7F55;
}

.el-menu--horizontal>.el-menu-item {
  padding-top: 5px;
  font-size: 22px;
  font-family: STKaiti;
  color: azure;
  /* width: 230px; */
}

.el-menu-item.is-active:hover,
.el-submenu .el-menu-item:hover {
  background-color: #AA7F55 !important;
}

.el-menu--horizontal>.el-menu-item {
  float: left;
  height: 80px;
  line-height: 60px;
  margin: 0;
  border-bottom: 2px solid transparent;
  color: #ffffff;
}

/* //设置菜单鼠标经过的样式 */
.el-menu-item:hover {
  background: unset;
}

/* //设置二级菜单被选中的菜单 */
.el-sub-menu.is-active {
  border: unset;
  background: unset;
}

/* //设置二级菜单鼠标经过的样式 */
.el-sub-menu__title:hover {
  background: unset;
  border: unset !important;
}

/* //设置选中之后鼠标移开的背景颜色 */
.el-tooltip__trigger {
  border: unset !important;
}

.el-menu-item:hover {
  border-top: 0px solid #6e5022;
  border-right: 0px solid #6e5022;
  border-bottom: 0px;
  /* display: block; */

  color: rgb(255, 255, 255) !important;
  background-color: #C8A078 !important;
}
.header-new-drop li {
  position: absolute !important; 
    top: 68px;
    left: 22px  !important;
    transform-origin: center top;
    z-index: 101  !important;
}
</style>

