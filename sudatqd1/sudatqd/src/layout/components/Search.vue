
<template>
  <form class="search-area" @submit.prevent="handleSearch">
    <input type="text" v-model="searchValue" :placeHolder="placeHolder" />
    <div class="icon" @click="handleSearch">
      <Icon type="search" />
    </div>

  </form>
</template>
  
<script>
import Icon from './Icon';
export default {
  components: {
    Icon
  },
  data() {
    return {
      searchValue: "",
    }
  },
  props: {
    placeHolder: {

      default: "搜索"
    }
  },
  methods: {
    handleSearch() {
      this.$emit("search", this.searchValue);
    }
  }


}
</script>
  
<style scoped>
.search-area {
  margin-top: 15px;
  margin-right: 5px;
  width: 90%;
  height: 100%;
  box-sizing: border-box;
  border-color: rgb(210, 213, 213);
  border: 2px solid #787777;
  border-radius: 2px;
  position: relative;
}

.search-area input {
  border: none;
  outline: none;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  color: rgb(102, 103, 105);
  padding-right: 45px;
  padding-left: 6px;



}

.icon {
  position: absolute;
  width: 50px;
  height: 90%;

  /* background-color: rgb(255, 255, 255); */
  right: 0px;
  padding-right: 0%;
  top: 2px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: rgb(222, 222, 222);
  cursor: pointer;
}
</style>
  
  