<template>
    <div class="header">
        <h1>大屏数据可视化展示</h1>
        <div class="showtime">时间</div>
    </div>
</template>
  
<script>
export default {
    name: "Header",
    mounted() {
        var t = null;
        t = setTimeout(time, 1000); //開始运行
        function time() {
            clearTimeout(t); //清除定时器
            let dt = new Date();
            var y = dt.getFullYear();
            var mt = dt.getMonth() + 1;
            var day = dt.getDate();
            var h = dt.getHours(); //获取时
            var m = dt.getMinutes(); //获取分
            var s = dt.getSeconds(); //获取秒
            document.querySelector(".showtime").innerHTML =
                y + "/" + mt + "/" + day + " -" + h + ":" + m + ":" + s;
            t = setTimeout(time, 1000); //设定定时器，循环运行
        }
    },
};
</script>
  
<style scoped>
.header {
    position: relative;
    width: 100%;
    height: 1.25rem;
    /* background-color: pink; */
    /* background: url(../../public/images/head_bg.png) no-repeat; */
    background-position: top center;
    background-size: cover;
}

h1 {
    color: #fff;
    text-align: center;
    line-height: 1rem;
    font-size: 0.475rem;
}

.showtime {
    overflow: hidden;
    width: 4.5625rem;
    height: 1rem;
    position: absolute;
    top: 0;
    right: 0.375rem;
    line-height: 0.9375rem;
    font-size: 0.25rem;
    color: rgba(255, 255, 255, 0.7);
}
</style>
  