
<template>
  <i class="iconfont" :class="iconClass"></i>
</template>
  
<script>
var map = {
  arrowUp: 'icon-KPI',
  search: 'icon-search'
};
export default {
  props: {
    type: {
      type: String,
      required: true,
    }
  },
  data() {
    return {}
  },
  created() { },
  computed: {
    iconClass() {
      return map[this.type];

    },
  },
  methods: {}
}
</script>
  
<style scoped>
@import "//at.alicdn.com/t/c/font_4102197_rylrirku3d.css";

.iconfont {
  font-family: "iconfont" !important;
  font-size: 20px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  margin-right: 0%;
}
</style>
  
  