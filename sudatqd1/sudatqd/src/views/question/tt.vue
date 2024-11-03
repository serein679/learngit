<!-- vue的三个模块 -->
<!-- 模板 html -->
<template>
    <div style="margin-top: 8px;background-color: antiquewhite;">
        <div>
            <!-- action：处理图像的后台接口地址; -->
            <!-- :on-preview="handlePictureCardPreview"：处理图片的预览 -->
            <!-- :on-remove="handleRemove"：处理图片的移除 -->
            <!-- :on-success="successHandler"：处理图像成功的响应 -->
            <div style="text-alicenter">
                <el-upload class="upload-demo" ref="upload" action="doUpload" :limit="1" :file-list="fileList"
                    :http-request="modeUpload" :auto-upload="false" :before-upload="beforeFileUpload">
                    <el-button slot="trigger" type="primary">
                        点击上传<i class="el-icon-upload el-icon--right"></i></el-button>
                    <div slot="tip" class="el-upload__tip">只能上传1M以内的文件</div>
                </el-upload>
                <el-button type="primary" @click="uploadOcr()">
                    提交 </el-button>
                <input type="file" id="id" name="image" class="getImgUrl_file" @change="uploadOcr()"
                    accept="image/jpg, image/jpeg, image/png" />
                <br />
            </div>
            <div>
                <h3>识别结果：</h3>
                <el-table :data="resp.result">
                    <el-table-column label="精确度" align="center" prop="resp.result"></el-table-column>
                </el-table>

            </div>
        </div>
    </div>
</template>

<!-- 脚本 js -->
<script>
import { uploadOcr } from "@/api/login"

export default {
    // 定义变量
    data() {
        return {
            resp: {},
            fileList: [],
            file: [],
        }
    },
    // 定义事件处理函数
    methods: {
        // successHandler(resp) {
        //     // 将后台返回的响应结果赋值给当前实例当中的resp
        //     this.resp = resp;
        // },
        modeUpload(item) {
            // console.log(item.file);
            this.file = item.file
            console.log(this.file)
        },
        beforeFileUpload(file) {
            // 这里可以添加对文件的一些检查，比如文件的大小和类型等
            const isLt1M = file.size / 1024 / 1024 < 1;

            if (!isLt1M) {
                this.$message.error('上传的文件大小不能超过 1MB!');
                return false;
            } else {
                // 保存文件对象
                this.file = file;
                console.log(this.file)
                this.fileName = file.name;
                return false; // 返回false不会自动上传
            }

        },
        uploadOcr() {
            let files = document.getElementById("id").files[0];
            let name = document.getElementById("id").files[0].name;

            // console.log(name)
            let fd = new FormData()
            fd.append('templateFile', this.file)
            console.log(fd)
            uploadOcr(fd).then(response => {
                if (response.message == "操作成功") {
                    this.resp = resp;
                    // this.getProjects(order = 0)
                } else {
                    this.$message({
                        type: 'error',
                        message: response.message
                    })
                }
            })
        }


    },
    // 生命周期钩子函数
    created() {

    }
}
</script>
<!-- 样式 css -->
<style lang="sass" scoped>

</style>
