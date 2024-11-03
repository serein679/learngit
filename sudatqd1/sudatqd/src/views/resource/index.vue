<template>
  <div
    style="margin-top: 8px;background-color: antiquewhite;height: 850px;background-image: url(https://ts1.cn.mm.bing.net/th/id/R-C.e36f99cd6c7070296125c27c502861f2?rik=YNINR3N66knIBA&riu=http%3a%2f%2fimg.pptjia.com%2fimage%2f20180816%2f7344310da824b80830f5ddb08ed3149a.JPG&ehk=6OF%2bo8p8ZRQbJA%2b6hUJa4tx9mPTuQ7Bc%2bmGSsgMCt8s%3d&risl=&pid=ImgRaw&r=0);">
    <div class="shell">
      <div class="shell_body">
        <div class="button">
          <div class="prev"><i class="el-icon-back "></i></div>
          <div class="next"><i class="el-icon-right"></i></div>
        </div>
        <div class="shell_slider">
          <div class="item" v-for="i in papers">
            <div class="frame">
              <div class="box front" @click="select(i.paperId)" style="cursor:pointer">
                <h2> {{ i.paperName }} </h2>
                <span>{{ new
            Date(i.creationTime).toLocaleDateString('zh').replaceAll('/', '-') }}</span>
              </div>
              <div style="height: 60px;">

              </div>
              <div class="box left"></div>
              <div class="box right"> </div>
            </div>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>
<script>

import moment from 'moment';
// 需要引入下方这个文件
import "moment/dist/locale/zh-cn.js";
{/* <link rel="stylesheet" href="./font/iconfont.css">   */ }
import { getPaperss, getOnePaper, PageAllPaper } from '@/api/paper'


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
      // timeline: [],

    }
  },
  created() {
    this.getQuery();
    this.getPaperTime()
  },
  mounted() {
    setTimeout(() => {


      getPaperss().then(response => {
        this.papers = response.data

      })

      // var timeline_json = make_the_json();
      // window.timeline = new TL.Timeline('timelineDemo', ' timeline');
      var items = document.getElementsByClassName('item');
      // 循环遍历每个item
      console.log(items.length)

      for (var i = 0; i < items.length; i++) {
        var paper = [];
        var paper = this.papers[i];
        var picture = paper.pictureLocation;
        var item = items[i];
        var frame = item.getElementsByClassName('frame')[0];
        var frontBox = frame.getElementsByClassName('front')[0];
        var leftBox = frame.getElementsByClassName('left')[0];
        var rightBox = frame.getElementsByClassName('right')[0];
        // 设置背景图片
        frontBox.style.backgroundImage = `url('${picture}')`;

        leftBox.style.backgroundImage = `url('${picture}')`;
        rightBox.style.backgroundImage = `url('${picture}')`;

      }
      (function () {
        "use strict";
        var shell = document.getElementsByClassName('shell')[0];
        var slider = shell.getElementsByClassName('shell_slider')[0];
        var items = shell.getElementsByClassName('item');
        var prevBtn = shell.getElementsByClassName('prev')[0];
        var nextBtn = shell.getElementsByClassName('next')[0];
        // 定义变量
        var width, height, totalWidth, margin = 20,
          currIndex = 0,
          interval, intervalTime = 3000;
        function init() {
          // 初始化函数
          resize();
          move(Math.floor(items.length / 2));
          bindEvents();
          timer();
        }
        function resize() {
          // 窗口大小变化时调整大小
          width = Math.max(window.innerWidth * .20, 275);
          height = window.innerHeight * .5;
          totalWidth = width * items.length;
          // 设置slider宽度
          slider.style.width = totalWidth + "px";
          // 设置每个item的宽度和高度
          for (var i = 0; i < items.length; i++) {
            let item = items[i];
            item.style.width = (width - (margin * 2)) + "px";
            item.style.height = height + "px";
          }
        }
        function bindEvents() {
          // 窗口大小变化时调整大小
          window.onresize = resize;
          // 点击prev按钮切换item
          prevBtn.addEventListener('click', () => { prev(); });
          nextBtn.addEventListener('click', () => { next(); });
        }
        init();
        function move(index) {
          // 移动shell到指定的item
          if (index < 1) index = items.length;
          if (index > items.length) index = 1;
          currIndex = index;
          // 遍历所有item
          for (var i = 0; i < items.length; i++) {
            let item = items[i],
              box = item.getElementsByClassName('frame')[0];
            if (i == (index - 1)) {
              // 当前item添加active类并设置3D效果
              item.classList.add('item--active');
              box.style.transform = "perspective(1200px)";
            } else {
              // 其他item移除active类并设置3D效果
              item.classList.remove('item--active');
              box.style.transform = "perspective(1200px) rotateY(" + (i < (index - 1) ? 40 : -40) + "deg)";
            }
          }
          // 移动slider
          slider.style.transform = "translate3d(" + ((index * -width) + (width / 2) + window.innerWidth / 2) + "px, 0, 0)";
          // 设置body背景图片
          // var frontBox = items[index - 1].getElementsByClassName('front')[0];
          // console.log(frontBox.style.backgroundImage)
          // var _bk = frontBox.style.backgroundImage;
          // var ssrc = _bk.split('"')[1].split('"')[0];
          // console.log(ssrc)
          // alert(_src);
          // var body = frame.getElementsByClassName('body');
          // document.body.style.backgroundImage = frontBox.style.backgroundImage;
          // document.body.style.backgroundImage = `url('${ssrc}')`;
          // console.log(document.body.style.backgroundImage)
          // body.style.backgroundImage = `url('${ssrc}')`;
          // rightBox.style.backgroundImage = `url('${picture}')`;
        }
        function timer() {
          // 定时器，自动切换shell
          clearInterval(interval);
          interval = setInterval(() => {
            move(++currIndex);
          }, intervalTime);
        }
        // 切换item
        function prev() {
          move(--currIndex);
          timer();
        }
        function next() {
          move(++currIndex);
          timer();
        }
      })();
    }, 100);
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
      getPaperss().then(response => {
        this.papers = response.data
        console.log(this.papers)
      })
    },
    select(paperId) {
      if (paperId !== '') {
        console.log(paperId)
        this.$router.push({
          path: '/introduction/select',
          query: {
            info: this.$Base64.encode(paperId)
          }
        })
      }
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
        }, 100);
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
<style>
.shell {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  background: rgba(246, 234, 196, 0.5);
}

/* 设置.button元素为flex布局，两端对齐，宽度为380px，绝对定位，左侧偏移量为50%，水平居中，底部偏移量为-80px */
.button {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 480px;
  position: absolute;
  left: 50%;
  margin-top: 690px;
  transform: translateX(-50%);
  /* bottom: -80px; */
}

/* 设置.prev和.next元素过渡动画时间为0.25秒，层级为99999，底部偏移量为5px */
.prev {
  transition: transform 0.25s ease;
  z-index: 99999;
  bottom: 5px;
}

.next {
  transition: transform 0.25s ease;
  z-index: 99999;
  bottom: 5px;
}

/* 设置.prev和.next元素中的i元素字体大小为90px，颜色为#fff，光标为指针，文字阴影为0 0 10px #ffffff */
.prev i {
  top: 50px;
  font-size: 90px;
  color: #fff;
  cursor: pointer;
  text-shadow: 0 0 10px #ffffff;
}

.next i {
  font-size: 90px;
  color: #fff;
  cursor: pointer;
  text-shadow: 0 0 10px #ffffff;
}

/* 设置.shell_body元素宽度为100%，缩放为0.8倍，上内边距为20px，下内边距为150px */
.shell_body {
  width: 100%;
  transform: scale(.8);
  padding: 20px 0 150px 0;
}

.body {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 650px;
  background-size: cover;
  overflow: hidden;
  transition: background-image .7s ease-in-out;
}

/* 设置.shell_slider元素为相对定位，过渡动画时间为1秒，背景为透明 */
.shell_slider {
  position: relative;
  transition: transform 1s ease-in-out;
  background: transparent;
}

/* 设置.item元素为相对定位，左浮动，左右外边距为20px */
.item {
  position: relative;
  float: left;
  margin: 0 20px;
}

/* 设置.frame元素为相对定位，宽度和高度为100%，过渡动画时间为1秒，3D变换模式为保留3D效果 */
.frame {
  position: relative;
  width: 100%;
  height: 100%;
  transition: transform 1s ease-in-out;
  transform-style: preserve-3d;
}

/* 设置.frame元素的伪元素为绝对定位，底部偏移量为-16%，宽度为100%，高度为60px，背景颜色为#ffffff1c，盒阴影为0px 0px 15px 5px #ffffff1c，3D变换为绕X轴旋转90度并向上平移20px */
.frame:after {
  content: "";
  position: absolute;
  bottom: -16%;
  width: 120%;
  height: 60px;
  background: #fcfcfc1c;
  box-shadow: 0px 0px 15px 5px #ffffff1c;
  transform: rotateX(90deg) translate3d(0px, -20px, 0px);
}

/* 设置.box元素为flex布局，纵向排列，水平和垂直居中对齐，绝对定位，宽度和高度为100%，边框为4px实心白色，透视效果为1000px，3D变换模式为保留3D效果 */
.box {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: absolute;
  width: 120%;
  height: 120%;
  border: 6px solid #fff;
  perspective: 1000px;
  transform-style: preserve-3d;
}

/* 设置.box元素中的h1和span元素颜色为#fff，Z轴平移距离为20px */
.box h2,
.box span {
  color: #fff;
  transform: translateZ(20px);
}

/* 设置.box元素中的h1元素文字阴影为0 0 30px #1f05b4，字体大小为100px */
.box h2 {
  text-shadow: 0 0 30px #b5880a;
  font-size: 40px;
}

/* 设置.box元素中的span元素为绝对定位，底部偏移量为20px，左右内边距为25px，文字阴影为0 0 10px #1f05b4 */
.box span {
  position: absolute;
  bottom: 20px;
  padding: 0 65px;
  font-size: 25px;
  text-shadow: 0 0 10px #d3b11a;
}

/* 设置.front、.left和.right元素的s盒阴影为0 0 50px #ffffff，背景图大小为cover */
.front,
.left,
.right {
  box-shadow: 0 0 50px #ffffff;
  background-size: cover;
}

/* 设置.left和.right元素的顶部偏移量为0，宽度为60px，背面不可见 */
.right,
.left {
  top: 0;
  width: 60px;
  backface-visibility: hidden;
}

/* 设置.left元素的左侧偏移量为0，左边框宽度为5px，3D变换为向右平移1px，Z轴平移-60px，绕Y轴逆时针旋转90度，变换原点为左侧 */
.left {
  left: 0;
  border-left-width: 5px;
  transform: translate3d(1px, 0, -60px) rotateY(-90deg);
  transform-origin: 0%;
}

/* 设置.right元素的右侧偏移量为0，右边框宽度为5px，3D变换为向左平移1px，Z轴平移-60px，绕Y轴顺时针旋转90度，变换原点为右侧 */
.right {
  right: 0;
  border-right-width: 5px;
  transform: translate3d(-1px, 0, -60px) rotateY(90deg);
  transform-origin: 100%;
}
</style>
