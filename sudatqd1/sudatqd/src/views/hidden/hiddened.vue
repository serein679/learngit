<template>
    <div style="margin-top: 8px;height: 690px;background-color: antiquewhite;">

        <!-- <el-divider style="margin-top: 8px;height: 690px;"><i class="el-icon-collection"></i></el-divider> -->
        <div height="350px;" style="margin-top: 8px;font-family: 楷体;padding: 15%; font-size: 48px;">
            您没有权限
        </div>
        <el-row>
            <p style="text-align: right;" class="last-style" @click="goback"><el-button>返回</el-button></p>

        </el-row>

    </div>
</template>
  
<script>


export default {
    name: 'Dashboard',
    components: {
        Carousel
    },
    data() {
        return {
            subjects: [], // 专业介绍            
            cooperations: [],
            forums: [],
            mienId: '',
            miens: [],
            miens1: [],
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
            projects: [],
            typeName1: {
                '0': "国家级",
                '1': "省级",
                '2': "市级",
                '3': "校级",
                '4': "其他",
            },
            typelist5: [{
                projectNote: 0,
                typeName1: "国家级",
            }, {
                projectNote: 1,
                typeName1: "省级",
            },
            {
                projectNote: 2,
                typeName1: "市级",
            }, {
                projectNote: 3,
                typeName1: "校级",
            }, {
                projectNote: 4,
                typeName1: "其他",
            }], // 资源类型列表
            loading: true,

            dialogVisible: false,
            formLabelWidth: '120px'
        }
    },
    created() {
        this.getSubjectIntro()
        this.getSoochowForum()
        this.getCooperation()
        this.getMien()
        this.getMien1()
        this.getProjects()
    },


    methods: {
        // 获取专业介绍
        getSubjectIntro() {
            SubjectIntro().then(response => {
                this.subjects = response.data
            })
        },
        goback() {
            this.$router.go(-2)
        },//返回上一页
        formatData(forumTime) {
            var s = new Date(forumTime).toLocaleString();
            return s;
        },

        getMien() {
            getMien0().then(response => {
                this.miens = response.data
            })
        },
        getMien1() {
            getMien1().then(response => {
                this.miens1 = response.data
            })
        },

        getSoochowForum() {
            getSoochowForum1().then(response => {
                this.forums = response.data
            })
        },
        select1(projectId) {
            console.log(projectId)
            this.$router.push({
                path: './projectDetail',
                query: {
                    info: this.$Base64.encode(projectId)
                }
            })

        },
        select(mienId) {
            console.log(mienId)
            this.$router.push({
                path: './teacherDetail',
                query: {
                    info: this.$Base64.encode(mienId)
                }
            })

        },
        getProjects() {
            this.loading = true;

            getProjects1().then(response => {
                this.projects = response.data
                this.loading = false;
            })
        },
        getCooperation() {
            Cooperation().then(response => {
                this.cooperations = response.data
            })
        },

        openDialog(id) {
            OneSubjectIntro({ id: id }).then(response => {
                this.dialogVisible = true
                this.onesubject = response.data[0]
            })
        },
        goUrl(url) {
            window.open(src, "_blank")

        },


        linkTo() {
            let activeIndex = this.$refs.carousel.activeIndex
            window.open(this.miens[activeIndex].content, "_blank")
        }


    }
}


function fileView(url) {
    window.open('http://172.75.200.149:8012/onlinePreview?url=' + encodeURIComponent(BASE64.encode(url)));
}

</script>
  
<style scoped>
#main {
    width: 600px;
    height: 350px;
    margin: 40px auto;
}

.el-card .el-card__header {
    padding: 20px !important;
}

.text {
    font-family: Microsoft YaHei;
    font-size: 16px;
    margin: 15px;
    line-height: 24px;
    color: #676767;
    float: left;
    width: auto;
    text-align: justify;
}

.text:first-letter {
    font-size: 72px;
    color: #cd6a0e;
}

.el-card__header {
    padding: 0px 0px;
    border-bottom: 0px solid #fafafa;
    box-sizing: border-box;
}

.el-card__header {
    padding: 18px 20px;
    border-bottom: 0px solid #fefefe;
    box-sizing: border-box;
}

.el-button--primary {
    font-size: 16px;
    font-family: 宋体;
    color: #eeecec;
    background-color: #894659;
    border-color: #c0bcbc;
}

.title-span {
    font-family: "华文中宋";
    font-size: 26px;
    color: #B03632;
    text-align: right;


}

.h2 {
    font-family: 宋体;
}

.el-carousel__item.title h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
}

.el-carousel__item:nth-child(2n) {
    background-color: #cfd5dd;
}

.el-carousel__item:nth-child(2n+1) {
    background-color: #e2e4e7;
}

.qj>>>.el-table__row>td {
    /* 去除表格线 */
    border: none;
}

.qj>>>.el-table th.is-leaf {
    /* 去除上边框 */
    border: none;
}

.qj>>>.el-table::before {
    /* 去除下边框 */
    height: 0;
}


.clearfix:before {
    border: none;
}

.clearfix:after {
    display: table;
    content: "";
}

.table-router {
    float: right;
    font-family: "PingFang SC";
    color: #000000;
    text-decoration: none;
}

.clearfix:after {
    clear: both;

    border: none;

}

.pic_item {
    position: relative;
    height: 100%;
    border: none;
}




.card {

    border: 10px;
    bottom: 0rem;
}

.pic_item h3 {
    background-color: #d8dce1;
    opacity: 0.75;
    color: rgb(0, 0, 0);
    height: 30px;
    width: 100%;
    align-content: center;
    text-align: center;
    position: absolute;
    left: 0rem;
    bottom: 0em;
    padding: 0%;

}

.pic_item h3[data-v-106c86ed] {
    position: absolute;
    left: 0rem;
    bottom: 0.5rem;
}

.pic_item .pic_item {
    position: relative;
    height: 100%;
}

.pic_item:hover {
    cursor: pointer;
}

.pic_item img {
    width: 100%;
    height: 100%;
}

.pic_item h3 {
    position: absolute;
    left: 5rem;

    bottom: 2rem;
}
</style>