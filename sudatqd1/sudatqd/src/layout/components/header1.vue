<template>
  <div style=" background: #AA7F55; width: 100%;  padding: 0 !important;">
    <el-row>
      <el-col :span="6">
        <img src="~@/assets/logo2.png" width="170px" height="150px" style="margin-top: -43px;margin-left: -40px;">
        <!-- <img src="~@/assets/logo2.png" height="150px" width="160px" style="margin-left:-90px;margin-top: -45px;" /> -->
      </el-col>
      <el-col :span="1">&nbsp;</el-col>
      <el-col :span="10">&nbsp;
         <el-button  style="font-family: 'STKaiti'; font-size: 24px;margin-top: 5px;margin-left: 1%;color: black;background-color:burlywood" @click="go"> 
          可视化大屏幕
          </el-button></el-col>
      <el-col :span="5">
        <span v-if="!verifyLogin()">
        </span>
        <span v-else>
         
          <el-dropdown trigger="click">
           
            <el-button type="text">
              <span style="font-family: 'STKaiti'; font-size: 24px;margin-top: 20px;">
                <i class="el-icon-user-solid"></i>{{ username }}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
            </el-button>
            <el-dropdown-menu slot="dropdown"  :append-to-body="false">
              <el-button size="large" style="margin: 0px 0px;border: 0cm;" round plain @click="logout">
                <span style="font-family: 'STKaiti'; font-size: 16px;">退出登录</span>
              </el-button>
              <el-button size="large" style="margin: 0px 0px;border: 0cm;" round plain @click.native="userInfo()">
                <span style="font-family: 'STKaiti'; font-size: 16px;">个人中心</span>
              </el-button>
              <!-- <el-dropdown-item @click.native="userInfo()">个人中心</el-dropdown-item>
              <el-dropdown-item @click.native="logout()">退出登录</el-dropdown-item> -->
            </el-dropdown-menu>
          </el-dropdown>
          
          &nbsp;&nbsp;&nbsp;
        </span>
      </el-col>
    </el-row>


  </div>
</template>
  
<script>
import Search from './Search'

export default {
  data() {
    return {
      activeIndex: '1',
      loginVisible: false,
      formLabelWidth: '100px',
      redirect: undefined,
      otherQuery: {},
      loginform: {
        act: '',
        pwd: '',
      },
      username: '',
    };
  },
  components: {
    Search
  },
  computed: {

  },
  methods: {
    verifyLogin() {
      let check = sessionStorage.getItem('SET_TOKEN')
      this.username = sessionStorage.getItem('SET_NAME')
      console.log(check)
      return check
    },
    // 登录
    handleLogin() {
      var obj = JSON.parse(JSON.stringify(this.loginform))
      this.$store.dispatch('user/login', obj).then(() => {
        this.loginVisible = false
        this.username = sessionStorage.getItem('SET_NAME')
        // this.$router.push({ path: this.redirect || '/', query: this.otherQuery })
      })
      this.$router.push('/management/projectManagement')
    },

    //退出登录
    logout() {
      this.$store.dispatch('user/logout')
      this.$router.push(`/dashboard?redirect=${this.$route.fullPath}`)
      // this.$router.push(/dashboard/)
    },

    //找回密码
    findpassport() {
      this.loginVisible = false;
      this.$router.push('/login/passportFind')
    },
    //个人中心
    userInfo() {
      this.$router.push('/login/userInfo')
    },
    go() {
        this.$router.push({
          path: '/management/index'
        })
      },//返回上一页

  }
}
</script>
  
<style scoped>
.menuclass {
  margin-top: 5px;
  line-height: 18px;
}

.zzz {
  font-size: 18px;
  font-family: "STKaiti";
  color: #cdc9ca;
  text-align: right;
  line-height: 15px;
  margin-top: 0px;
  margin-right: 0px;
}

.el-menu--horizontal>.el-menu-item {
  font-size: 20px;
  font-family: "STKaiti";
}

.el-menu-item.is-active:hover,
.el-submenu .el-menu-item:hover {
  background-color: #acacac55 !important;
}

.el-header {
  /* padding: 0 0px; */
  box-sizing: border-box;
  flex-shrink: 0;
}

.el-menu-item:hover {
  border-top: 0px solid #CC3333;
  border-right: 0px solid #CC3333;
  border-bottom: 0px;
}
</style>