<template>
    <div class="coverpanel">
        <h2>{{ title }}</h2>
        <my-echarts :ref="mychart"></my-echarts>
        <div class="panelfooter"></div>
    </div>
</template>
<script>
export default {
    props: {
        mychart: String,
        option: Object,
        title: String,
    },
    mounted() {
        //console.log(this.mychart);
        // console.log(this.$refs[this.mychart].setOption);
        // this.$refs[this.mychart].setOption(this.option);
    },
};
</script>
<style scoped>
/* 以下是存储echarts图表的容器样式 */
.coverpanel {
    position: relative;
    height: 13.875rem;
    background-color: rgba(255, 255, 255, 0.04);
    /* background: url(../assets/line1.jpg) no-repeat; */
    /* background-position: top center; */
    background-size: cover;
    margin-bottom: 0.1875rem;
    border: 1px solid rgba(25, 186, 139, 0.17);
    padding: 0.9rem 0.1875rem 0rem 0.1875rem;
    margin-top: 15px;

}

.coverpanel>h2 {
    /* float: top; */
    /* 将h2定位到中间 */
    overflow: hidden;
    position: absolute;
    top: 0;
    left: 50%;
    transform: translate(-50%);
    height: 5.5rem;
    line-height: 0.99rem;
    text-align: center;
    color: #fff;
    font-size: 0.9125rem;
    font-weight: 400;
    font-family: 楷体;

}

.coverpanel::before,
.coverpanel::after,
.panelfooter::before,
.panelfooter::after {
    position: absolute;
    content: "";
    width: 0.125rem;
    height: 2.125rem;
    font-family: 楷体;

}

.coverpanel::before {
    top: 0;
    left: 0;
    border-top: 2px solid #02a6b5;
    border-left: 2px solid #02a6b5;
}

.coverpanel::after {
    top: 0;
    right: 0;
    border-top: 2px solid #02a6b5;
    border-right: 2px solid #02a6b5;
}

.panelfooter {
    position: absolute;
    left: 0;
    bottom: 0;
    height: 2.125rem;
    width: 100%;
}

.panelfooter::before {
    bottom: 0;
    left: 0;
    border-bottom: 2px solid #02a6b5;
    border-left: 2px solid #02a6b5;
}

.panelfooter::after {
    bottom: 0;
    right: 0;
    border-bottom: 2px solid #02a6b5;
    border-right: 2px solid #02a6b5;
}
</style>