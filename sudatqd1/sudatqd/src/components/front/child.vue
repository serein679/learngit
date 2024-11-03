<!-- 三级及以上评论 -->
<template>
    <div class="sub-reply-container" v-if="childComments !=null" style="margin-left: 0%;">
      <div class="sub-reply" v-for="(child, index) in childComments" :key="index">
        <!-- 渲染内容 -->
        <div class="listbox-top-user" style="font-family: STKaiti;font-size: 22px;text-align: left;color: rgba(0, 0, 0, 0.671); margin: 3%;margin-top: 2%;line-height: 110%;">
          <div>
            <p>
              <span style="font-family: STKaiti;font-size: 22px;color: rgba(0, 0, 0, 0.671); margin: 3%;margin-top: 2%;line-height: 110%;">
                <font style="font-weight: bold;">   {{ child.userName }}</font> 回复
                        <span><font style="font-weight: bold;"> {{ parentName }}</font></span>：{{child.replyContent }}</span>
            </p>
          </div>
          <!-- <el-avatar :size="30" :src="child.userImg" /> -->
            <div class="listbox-bottom">
            <span style="margin-left: 76%;margin-top: 5%;margin-bottom: 1%">发布时间：{{ new Date(child.replyTime).toLocaleString('zh').replaceAll('/', '-') }}</span>

          </div>
                <div v-if="obj.userId == child.userId">
                    <el-button size="large" style="margin-left: 82%;float: left;margin-top: 1%;margin-bottom: 1%"
                      @click=" getComment(child.replyId); loginVisible = true;">
                      <i class=" el-icon-chat-square"></i> 回复
                    </el-button>
                    <el-button size="large" style="margin-left: 5%;float: left;margin-top:1%;margin-bottom: 1%" @click="deleComment(child.replyId)">
                      <i class="el-icon-delete"></i> 删除
                    </el-button>
                  </div> 
                  <div  v-if="obj.userId != child.userId">
                    <el-button size="large" style="margin-left: 82%;float: left;margin-top: 1%;margin-bottom: 1%"
                      @click=" getComment(child.replyId); loginVisible = true;">
                      <i class=" el-icon-chat-square"></i> 回复
                    </el-button>
                    <el-button size="large" style="margin-left: 5%;float: left;margin-top: 1%;margin-bottom: 1%"
                          @click="loginVisible1 = true; getComment(item.replyId)">
                          <i class="el-icon-warning-outline"></i> 举报
                        </el-button>
                  </div> 
              </div>
  
        <!-- 递归地渲染子评论的子评论：调用自己 -->
        <ChildComment
          :childComments="child.children"
          :parentName="child.userName"
          
        />
      </div>
    </div>
  </template>
  <script setup>
    // 接收父组件传过来的值
    const props = defineProps({
    childComments: {
      type: Array,
      default: [],
    },
    parentName: {
      type: String,
      reequire: true,
    }
  });
  const childComments = props.childComments;
  const parentName = props.parentName;
  const obj={ userId: parseInt(sessionStorage.getItem('SET_USERID')) };
  // console.log("🚀 ~ parentName:", parentName);
  // console.log("🚀 ~ childComments:", childComments);
  
  // 声明需要抛出的事件
  const emit = defineEmits(["to-reply"]);
  
  const handleReply = (rootCommentId, parentId) => {
    // 【注意】这里不以对象形式包裹发送，会导致嵌套；父组件中回复一级评论与子级评论共用一个传值方法
    emit("to-reply", rootCommentId, parentId);
  };
  

  </script>
  