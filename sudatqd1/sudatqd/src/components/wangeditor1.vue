<template>
  <div style="border: 1px solid #ccc;">
    <!-- <button @click="insertText">insert text</button> -->
    <Toolbar style="border-bottom: 1px solid #ccc" :editor="editor" :defaultConfig="toolbarConfig" :mode="mode" />
    <Editor style="height: 300px;
       overflow-y: hidden;" v-model="html" :defaultConfig="editorConfig" :mode="mode" @onCreated="onCreated"
      @onChange="onChange" @onBlur="onBlur" @customPaste="customPaste" />
    <textarea v-model="html" readonly style="width: 100%; height: 200px; outline: none">
      </textarea>

  </div>
</template>

<script>
// import { upload } from '"@/api/resource';
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import Vue from 'vue'
export default Vue.extend({
  props: ['editorData'],
  components: { Editor, Toolbar },
  watch: {
    editorData(val) {
      console.log(val);
      this.html = val;
    }
  },
  data() {
    return {
      editor: null,
      html: '<p>hello</p>',
      toolbarConfig: {},
      editorConfig: { placeholder: '请输入内容...' },
      mode: 'default', // or 'simple'
      MENU_CONF: {
        uploadImage: {
          // server: 'http://:9998/Resource/uploadResource',

          // 超时时间，默认为 10 秒
          timeout: 30 * 1000, // 5s
          fieldName: "file",
          // 将 meta 拼接到 url 参数中，默认 false
          metaWithUrl: false, // join params to url
          // 自定义上传参数，例如传递验证的 token 等。参数会被添加到 formData 中，一起上传到服务端。
          meta: { dataKey: 0, bizType: "common" },
          // 自定义增加 http  header
          // headers: {
          //   Accept: "text/x-json",
          //   otherKey: "xxx"
          // },
          // 跨域是否传递 cookie ，默认为 false
          withCredentials: true,
          // 自定义增加 http  header
          // headers: {
          //   Accept: "text/x-json",
          //   otherKey: "xxx"
          // },
          maxFileSize: 10 * 1024 * 1024, // 10M
          base64LimitSize: 5 * 1024, // insert base64 format, if file's size less than 5kb
          // 选择文件时的类型限制，默认为 ['image/*'] 。如不想限制，则设置为 []
          allowedFileTypes: ["image/*"],
          // 最多可上传几个文件，默认为 100
          maxNumberOfFiles: 30,
          onBeforeUpload(file) {
            console.log("onBeforeUpload", file);
            return file; // will upload this file
            // return false // prevent upload
          },
          onProgress(progress) {
            console.log("onProgress", progress);
          },
          onSuccess(file, res) {
            console.log("onSuccess", file, res);
          },
          onFailed(file, res) {
            alert(res.message);
            console.log("onFailed", file, res);
          },
          onError(file, err, res) {
            alert(err.message);
            console.error("onError", file, err, res);
          }
        },

      },
    }
  },
  methods: {

    onCreated(editor) {
      this.editor = Object.seal(editor) // 一定要用 Object.seal() ，否则会报错
    },
    printEditorHtml() {
      const editor = this.editor;
      if (editor == null) return;

      console.log(editor.getHtml()); // 执行 editor API
    },
    onChange(editor) {
      const html = editor.getHtml()
      console.log('onChange', html)
      this.$emit('sendWangEditor', html)
    },
    onBlur(editor) { console.log('onBlur', editor) },
    insertText() {
      const editor = this.editor // 获取 editor 实例
      if (editor == null) return

      // 调用 editor 属性和 API
      // editor.insertText('一段文字')
      console.log(editor.children)
    },
    customUpload(file, insertFn) {
      var axios = require("axios");
      var FormData = require("form-data");
      var data = new FormData();
      data.append("image", file); // file 即选中的文件
      var config = {
        method: "post",
        // url: "http://localhost:9998/Resource/uploadResource", //上传图片地址
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: "Bearer " + localStorage.getItem("token")
        },
        data: data
      };
      axios(config)
        .then(function (res) {
          let url = "http://localhost/suda/" + res.data.data.path; //拼接成可浏览的图片地址
          insertFn(url, "使用说明", url); //插入图片
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    //   <button @click="insertText">insert text</button>
    // customAlert(info: string, type: string) { window.alert(`customAlert in Vue demo\n${type}:\n${info}`) },

    customPaste(editor, event, callback) {
      console.log('ClipboardEvent 粘贴事件对象', event)
      const html = event.clipboardData.getData('text/html') // 获取粘贴的 html
      const text = event.clipboardData.getData('text/plain') // 获取粘贴的纯文本
      const rtf = event.clipboardData.getData('text/rtf') // 获取 rtf 数据（如从 word wsp 复制粘贴）
      // 关闭粘贴样式的过滤
      editor.customConfig.pasteFilterStyle = false
      // 自定义插入内容
      editor.insertText(html)
      // 自定义处理粘贴的文本内容
      editor.customConfig.pasteTextHandle = function (html) {
        // content 即粘贴过来的内容（html 或 纯文本），可进行自定义处理然后返回
        if (html == '' && !html) return ''
        var str = html
        str = str.replace(/<xml>[\s\S]*?<\/xml>/ig, '')
        str = str.replace(/<style>[\s\S]*?<\/style>/ig, '')
        str = str.replace(/<\/?[^>]*>/g, '')
        str = str.replace(/[ | ]*\n/g, '\n')
        str = str.replace(/&nbsp;/ig, '')
        str = str.replace(/<([a-z]+?)(?:\s+?[^>]*?)?>\s*?<\/\1>/, '')
        str = str.trim();
        str = str.replace(/\n\s*/g, '<br/>')
        str = str.replace(/<p><br><br><\/p><p><br><\/p>/, '')
        console.log('****', str)
        return str
      }
      // 返回 false ，阻止默认粘贴行为
      event.preventDefault()
      callback(false) // 返回值（注意，vue 事件的返回值，不能用 return）
      // 返回 true ，继续默认的粘贴行为
      // callback(true)
    },

    // uploadImg(file,insertFn){
    //     let formData = new FormData()
    //     formData.set("file",file)

    //     uploadApi.upload(formData).then(response => {
    //         const res = response.data
    //         if(res.flag){
    //             insertFn(res.data,file.name,'D:/upload/'+res.Location)

    //         }else {
    //             this.$message.error('图片插入失败')
    //         }
    //     })
    // }
  },
  mounted() {
    // 模拟 ajax 请求，异步渲染编辑器
    /*         setTimeout(() => {
              this.html = '<p>模拟 Ajax 异步设置内容 HTML</p>'
          }, 1500) */
    // this.editor.txt.html(`${res.src_desc}`)//编辑器回显
    //     setTimeout(() => {
    //         this.html = '<p>模拟 Ajax 异步设置内容 HTML</p>'
    //     }, 1500)
  },

  beforeDestroy() {
    const editor = this.editor
    if (editor == null) return
    editor.destroy() // 组件销毁时，及时销毁编辑器
  }
})

</script>
<style src="@wangeditor/editor/dist/css/style.css"></style>
<style></style>