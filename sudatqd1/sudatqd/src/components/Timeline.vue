<template>
  <div class="timeline-wrap">
    <div class="timeline-header">
      <span>{{ title }}</span>
    </div>
    <div class="timeline-item" v-for="(item, index) in dataList" :key="index">
      <div class="item-version">
        <span>{{ item.year }}</span>
      </div>
      <!-- <div class="timeline-item-time">
          <code>{{ item.date }}</code>
          <span class="item-weather" v-if="showWeather">{{ item.weather }}</span>
        </div> -->
      <div class="timeline-item-content">
        <div v-for="(subItem, subIndex) in item.body" :key="subIndex">
          <span class="subItem-title">{{ subItem.month }}</span>
          <div class="subItem-children">
            <span v-for="(threeItem, threeIndex) in subItem.children" :key="threeIndex">{{ threeItem.title }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
  
<script>
export default {
  name: 'Timeline',
  data() {
    return {

    }
  },
  props: {
    title: {
      type: String
    },
    showWeather: {
      type: Boolean
    },
    dataList: {
      type: Array
    }
  }
}
</script>
  
<style lang="scss" scoped>
.timeline-wrap {
  padding: 20px 40px;

  .timeline-header {
    padding-bottom: 16px;

    span {
      font-size: 18px;
      font-weight: bold;
    }
  }

  .timeline-item {
    padding: 0px 10px 20px 20px;
    border-left: 1px solid;
    line-height: 1;
    position: relative;

    &::before {
      content: '';
      display: inline-block;
      width: 6px;
      height: 6px;
      position: absolute;
      left: -4px;
      top: 0px;
      border: 1px solid;
      border-radius: 50%;
      background: #fff;
      background: red;
    }

    .item-version {
      font-size: 24px;
      font-weight: bold;
      margin-bottom: 0.6em;
    }

    .timeline-item-time {
      margin-bottom: 12px;
      width: 200px;
      display: flex;
      justify-content: space-between;
      align-items: center;

      code {
        margin: 0 1px;
        padding: .2em .4em;
        font-size: .9em;
        background: #f2f4f5;
        border: 1px solid #f0f0f0;
        border-radius: 3px
      }

      .item-weather {
        font-size: 14px;
      }
    }

    .timeline-item-content {
      font-size: 14px;

      .subItem-title {
        padding: 8px 0 8px 0;
        display: block;
        color: #000;
        font-size: 16px;
        font-weight: bold;
        // margin-left: 12px;

      }

      .subItem-children {
        line-height: 1;
        text-indent: 1em;
        padding: 7px 0 0 7px;

        span {
          display: block;
          position: relative;
          margin-bottom: 8px;
          text-indent: 2em;
          line-height: 1.2;
          cursor: pointer;
          text-decoration: underline;

          &::before {
            content: '';
            display: inline-block;
            width: 5px;
            height: 5px;
            position: absolute;
            left: 14px;
            top: 4px;
            border: 1px solid;
            border-radius: 50%;
            background: #fff;
          }

          &:hover {
            color: red;
          }
        }
      }
    }
  }
}</style>
  