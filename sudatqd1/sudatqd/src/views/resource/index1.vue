<template>
    <div style="margin-top: 8px;background-color: antiquewhite;height: 650px;">
        <div id='timelineDemo' style="width: 100%; height: 650px"></div>
        <!-- <iframe
        src="https://cdn.knightlab.com/libs/timeline3/latest/embed/index.html?source=1xuY4upIooEeszZ_lCmeNx24eSFWe0rHe9ZdqH2xqVNk&font=Default&lang=en&initial_zoom=2&height=100%"
        width="100%" frameborder="0"></iframe> -->
    </div>
</template>
<script>

import moment from 'moment';
// 需要引入下方这个文件
import "moment/dist/locale/zh-cn.js";

import { getPaperTime, getOnePaper, PageAllPaper } from '@/api/paper'
import TL from '@/timeline/js/timeline.js'
import '@/timeline/css/timeline.css'

export default {

    name: "Resource",
    components: {//组件

    },

    data() {
        return {
            subjectList1: [],//本科，专业资源列表
            subjectList2: [],//研究生，专业资源列表
            paperList: [],
            subjectId: '',
            papers: [],
            frontBox: '',
            id: '',
            text: '',
            timelineList: [],
            carouselList: [],
            item: '',
            rightBox: '',
            leftBox: '',
            options: [],
            value: [],
            list: [],
            loading: false,
            timeline: [],
        }
    },
    created() {
        this.getQuery();
        this.getPaperTime()
    },
    mounted() {
        timeline = new TL.Timeline('timelineDemo', 'https://docs.google.com/spreadsheets/d/e/2PACX-1vTvJC99kCRcA2d1BQkwXHUrGQgzOL_3SWKeStvS4h0l1_E8vMymeb8WYnNdbqFsXRH9H-OoDEDgVlaB/pubhtml');
    },
    computed: {//计算属性
        listSet() {
            return this.resourceList.map(item => {
                return { value: `${item.resourceId}`, label: `${item.resourceName}` };
            });
        }
    },
    methods: {

        //本科生资源
        getPaperTime() {
            getPaperTime().then(response => {
                this.papers = response.data

            })
        },
        //远程查询
        remoteMethod(query) {
            if (query !== '') {
                this.loading = true;
                SelectResource(query).then((response) => {
                    this.resourceList = response.data;
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
        //资源详情页
        //导航分页
        getQuery() {
            if (this.$route.query.activeName != null) {
                var params = JSON.parse(this.$Base64.decode(this.$route.query.activeName))
                if (params == 1) {
                    this.activeName = "1";
                } else if (params == 2) {
                    this.activeName = "2";
                }
                console.log(this.activeName);
            } else {
                this.activeName = "1";
            }
        },
    }
}
</script>
<style></style>
  
  