<template>
    <el-carousel :interval="4000" type="card" height="350px">
        <el-carousel-item v-for="(item, index) in idList" :key="index">
            <el-image :src="item.idView" style="height:100%;width:100%;" alt="图片丢失了"></el-image>
        </el-carousel-item>
        <el-carousel-item v-for="(item, index) in imgList" :key="index">
            <el-image :src="item.idView" style="height:100%;width:100%;" alt="图片丢失了"></el-image>
        </el-carousel-item>
        <el-carouserl-item>
            <div id="bottom">
                <el-row style="background-color: brown; height: 75px;width: 100%;">
                    <el-col :span="5">

                        <div style="height: 70px;text-align: justify;margin-top: 5px;margin-left: 5px;">
                            <img src="~@/assets/header_logo.png" width="270px" height="60px">

                        </div>
                    </el-col>
                    <el-col :span="18">
                        <div style="height: 40px;text-align: right;">
                            <span style="height: 10px;">

                            </span>
                            <span style="text-align: center; color: aliceblue;" class="xxz">
                                苏公网安备
                                <a target="_blank"
                                    href="https://www.beian.gov.cn/portal/registerSystemInfo?recordcode=32050802010530"
                                    rel="noreferrer" style="text-decoration: none;color: aliceblue;">32050802010530</a>
                                &nbsp;&nbsp;&nbsp;
                            </span>

                            <span style="">
                                <a target="_blank" href="https://beian.miit.gov.cn/"
                                    style="text-decoration: none; color: aliceblue;">苏ICP备10229414号-1</a>
                                &nbsp;&nbsp;
                            </span>
                            <span style="color: aliceblue;">
                                地址：江苏省苏州市姑苏区十梓街1号
                            </span>

                        </div>
                        <div style="height: 30px;color: aliceblue;text-align: right;">
                            版权所有©苏州大学
                        </div>
                    </el-col>

                </el-row>
            </div>
        </el-carouserl-item>
    </el-carousel>
</template>

<script>
export default {
    name: 'Carousel',
    mounted() {
        // 首次加载时,初始化高度
        this.screenWidth = window.innerWidth
        this.bannerHeight = 370 / 1550 * this.screenWidth
        // 窗口大小发生改变
        window.onresize = () => {
            this.screenWidth = window.innerWidth
            this.bannerHeight = 370 / 1550 * this.screenWidth
        }
    },
    data() {
        return {
            idList: [
                {
                    name: '1',
                    idView: require('@/assets/carousel/百年苏大欢迎你！.jpg'),
                }, {
                },
            ],
            imgList: [{
                name: '1',
                idView: require('@/assets/carousel/百年苏大欢迎你！.jpg'),
            }, {
                name: '2',
                idView: require('@/assets/carousel/炳麟秋景.jpg'),
            }, {
                name: '3',
                idView: require('@/assets/carousel/博物馆守护的漫长岁月.jpg'),
            }, {
                name: '4',
                idView: require('@/assets/carousel/养天地正气，法古今完人.jpg'),
            }],
            bannerHeight: 0,
            screenWidth: 0,
        }
    },
}
</script>

<style scoped></style>