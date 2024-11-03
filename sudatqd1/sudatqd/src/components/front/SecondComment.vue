<!-- 二级评论 -->

<template>
    <div v-if="props.secondComments && props.secondComments.length">
      <div
        class="sub-reply-container"
        id="child-reply"
        v-for="(child, childIndex) in props.secondComments"
        :key="childIndex"
      >
      <div class="listbox-top-user" style="font-family: STKaiti;font-size: 22px;text-align: left;color: rgba(0, 0, 0, 0.671); margin: 3%;margin-top: 2%;line-height: 110%;margin-left: 6%;">
          <!-- <el-avatar :size="30" :src="child.userImg" /> -->
          <p>
              <span style="font-family: STKaiti;font-size: 22px;text-align: left;color: rgba(0, 0, 0, 0.671); margin: 3%;margin-top: 2%;line-height: 110%;">
                       <font style="font-weight: bold;"> {{ child.userName }} </font>回复 <span> <font style="font-weight: bold;">{{ parentName }}</font></span>：{{child.replyContent }}</span>
            </p>
            <div class="listbox-bottom">
            <span style="margin-left: 76%;margin-top: 5%;margin-bottom: 1%">回复时间：{{ new Date(child.replyTime).toLocaleString('zh').replaceAll('/', '-') }}</span>
            <!-- <span @click="handleReply(item.id, item.id)">回复</span> -->
          </div>
        </div>
        <!-- <div class="listbox-bottom">  
          <span @click="handleReply(child.parentId, child.id)">回复</span>
        </div> -->
      </div>
    </div>
  </template>
  <script setup>
  // 接收父组件传过来的值：二级留言
  const props = defineProps({
    secondComments: {
      type: Array,
      default: [],
    },
    parentName: {
      type: String,
      reequire: true,
    }
  });
  
  const parentName = props.parentName;

  // 声明需要抛出的事件
  const emit = defineEmits(["handle-reply"]);
  
  const handleReply = (rootCommentId, parentId) => {
    // 【注意】这里不以对象形式包裹发送，会导致嵌套；因为父组件中回复一级评论与子级评论共用一个传值方法
    emit("handle-reply", rootCommentId, parentId);
  };
  </script>
  