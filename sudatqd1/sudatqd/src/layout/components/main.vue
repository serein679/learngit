<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <router-view :key="key" />
    </transition>
  </section>
</template>
  
<script lang="ts">
export default {
  name: 'AppMain',
  computed: {
    /*cachedViews() {
      return this.$store.state.tagsView.cachedViews
    },*/
    key() { 
      return this.$route.path
    }
  }
}
</script>
  
<style lang="scss" scoped>
.app-main {
  /* 50= navbar  50  */
  // min-height: calc(100vh - 50px);
  width: 100%;
  position: relative;
  overflow: hidden;

  .el-popup-parent--hidden {
    .fixed-header {
      padding-right: 15px;
    }
  }
}
</style>
  
<style lang="scss">
// fix css style bug in open el-dialog
</style>
  