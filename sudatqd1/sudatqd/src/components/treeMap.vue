<template>
  <div :class="className" :style="{ height: height, width: width }" />
</template>
<script>
import * as echarts from 'echarts/core';
import { getInstitutions } from '@/api/institution'


// 按需引入 引入 ECharts 主模块
// var echarts = require('echarts/lib/echarts'
// 引入提示框和标题组件
require('echarts/lib/component/tooltip')
require('echarts/lib/component/title')
//格式化echarts数据
const formatUtil = echarts.format
export default {
  props: {
    institutionName: {
      type: String,
      default: 'chart',
    },
    width: {
      type: String,
      default: '100%',
    },
    height: {
      type: String,
      default: '60vh',
    },
    // 父组件传递过来的图表数据
    chartData: {
      type: Array,
    },
  },
  data() {
    return {
      // Echarts实例
      chart: null,
      dataList: [],
    }
  },
  watch: {
    /* 如果图表数据是后台获取的，监听父组件中的数据变化，重新触发Echarts */
    chartData: {
      immediate: true,
      deep: true,
      handler(newVal, oldVal) {
        if (this.chartData !== oldVal) {
          this.setOptions(newVal)
          this.chartData = newVal
        }
      },
    },
  },
  mounted() {
    /* 图表初始化 */
    this.$nextTick(() => {
      this.initChart()
    })
  },
  created() {
    this.getInstitutions()

  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    /* 释放图表实例 */
    this.chart.dispose()
    /* dispose 会释放内部占用的一些资源和事件绑定，但是解除实例的引用我们是做不到的，所以需要重新赋值为null */
    this.chart = null
  },
  methods: {
    getInstitutions() {
      getInstitutions().then(response => {
        this.dataList = response.data
      })
    },
    initChart() {
      this.chart = echarts.init(this.$el)
      this.setOptions(this.chartData)
    },
    setOptions({ } = {}) {

      getInstitutions().then(response => {
        this.dataList = response.data
      })

      // 重新定义数据（这里我的数据需要自己格式化，你们根据需要去做，如果自己的数据已经是[{'key:'',value:''}，{'key:'',value:''}]，则可以忽略这一步）
      var dataList = this.chartData
      // 转换为['key':'value','key':'value',]形式
      var dataObj = Object.assign(...dataList)
      // 重新拼接为[{'key:'',value:''}，{'key:'',value:''}]格式
      var serviceData = Object.entries(dataObj).map(([name, value]) => ({
        name,
        value,
      }))

      this.chart.setOption(
        {
          //这一步是图表上显示数字的样式
          label: {
            formatter: function (info) {
              var value = info.value
              var treePathInfo = info.treePathInfo
              var treePath = []
              var a = info.treeAncestors[0].value
              for (var i = 1; i < treePathInfo.length; i++) {
                treePath.push(treePathInfo[i].name)
              }
              return [formatUtil.encodeHTML(treePath.join('/')) + ':' + ((value / a) * 100).toFixed(2) + ' %'].join('')
            },
          },
          //鼠标悬停时显示的样式
          tooltip: {
            formatter: function (info) {
              var value = info.value
              var treePathInfo = info.treePathInfo
              var treePath = []
              var a = info.treeAncestors[0].value
              for (var i = 1; i < treePathInfo.length; i++) {
                treePath.push(treePathInfo[i].name)
              }
              return [
                '<div class="tooltip-title">' + formatUtil.encodeHTML(treePath.join('/')) + '</div>',
                '占比: ' + ((value / a) * 100).toFixed(2) + ' %' + '<br>',
                '总量: ' + value,
              ].join('')
            },
          },
          series: [
            {
              type: 'treemap',
              data: serviceData,
            },
          ],
        },
        true //在数据发生更新时，刷新echart
      )
    },
  },
}
</script>