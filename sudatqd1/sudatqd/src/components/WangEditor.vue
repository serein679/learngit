<template>
    <div style="border: 1px solid #ccc;">
        <!-- <button @click="insertText">insert text</button> -->
        <Toolbar style="border-bottom: 1px solid #ccc" :editor="editor" :defaultConfig="toolbarConfig" :mode="mode" />
        <Editor style="height: 1000px;
         overflow-y: hidden;" v-model="html" :defaultConfig="editorConfig" :mode="mode" @onCreated="onCreated"
            @onChange="onChange" @onBlur="onBlur" @customPaste="customPaste" />

    </div>
</template>
<script>
// import { upload } from "@/api/resource";
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
            url: "",
            editorConfig: '',
            editor: null,
            html: '',
            toolbarConfig: {},
            editorConfig: {
                placeholder: '请输入内容...',
                MENU_CONF: {
                    //配置上传图片
                    uploadImage: {
                        customUpload: this.uploadImg
                    }
                }
            },
            mode: 'default', // or 'simple'
            MENU_CONF: {
                uploadImage: {
                    customUpload: this.uploadImg
                }

            },
        }
    },
    created: {


    },
    methods: {
        uploadImg(file, insertFn) {
            let img = new FormData();
            img.append('file', file);
            //调接口，上传图片
            upload(img).then(response => {
                this.url = response.data
                //    if (response.data.success ){
                console.log(response);
                insertFn(response.data);
                //    }

            })
        },

        onCreated(editor) {
            this.editor = Object.seal(editor) // 一定要用 Object.seal() ，否则会报错
            editorConfig.MENU_CONF['uploadImage']
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

    },
    mounted() {

    },

    beforeDestroy() {
        const editor = this.editor
        if (editor == null) return
        editor.destroy() // 组件销毁时，及时销毁编辑器
    }
})

</script>
<!-- <style src="@wangeditor/editor/dist/css/style.css"></style> -->
<style></style>