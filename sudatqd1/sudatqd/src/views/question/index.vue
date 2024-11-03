<!-- vue的三个模块 -->
<!-- 模板 html -->
<template>
    <div style="margin-top: 8px;background-color: antiquewhite;min-height: 800px;">
        <div style="background-color: antiquewhite;">
            <div>
                <el-row>
                    <el-col :span="3" :offset="1">
                        <h3 class="location">当前位置：</h3>
                    </el-col>
                    <el-col :span="10" style="margin-top: 43px;">
                        <el-breadcrumb separator="/">
                            <el-breadcrumb-item>图像识别</el-breadcrumb-item>
                            <!-- <el-breadcrumb-item :to="{ path: '/introduction' }"> 档案库 </el-breadcrumb-item>
                            <el-breadcrumb-item><a href="/">档案详情</a></el-breadcrumb-item> -->
                        </el-breadcrumb>
                    </el-col>
                </el-row><!--导航-->
                <el-row style="height: 10px;">
                    <el-col :span="18" class="main-area" :key="index">
                    </el-col>
                </el-row>
            </div>
            <!-- action：处理图像的后台接口地址; -->
            <!-- :on-preview="handlePictureCardPreview"：处理图片的预览 -->
            <!-- :on-remove="handleRemove"：处理图片的移除 -->
            <!-- :on-success="successHandler"：处理图像成功的响应 -->
            <div>
                <div style="height: 80px;"></div>
                <el-upload drag action="doUpload" :limit="1" :file-list="fileList" id="id" :http-request="modeUpload"
                    :auto-upload="true" :on-preview="handlePictureCardPreview" :on-change="handleAvatarChangeIcon"
                    :before-upload="beforeFileUpload" :headers="headers">
                    <img v-if="imageUrl" :src="imageUrl" class="avatar">
                    <i v-else class="el-icon-upload"></i>
                    <div class="el-upload__text" style="margin-top: 2px;">将文件拖到此处，或<em>点击上传</em> </div>
                    <!-- <i v-else class="el-icon-plus"></i> -->
                    <!-- <el-button slot="trigger" type="primary">
                        点击上传<i class="el-icon-upload el-icon--right"></i></el-button>-->
                    <div slot="tip" class="el-upload__tip">只能上传1M以内的文件</div>
                </el-upload>
                <el-button type="primary" @click="uploadOcr()" style="margin-top: 20px;">
                    提交 </el-button>

            </div>
            <el-dialog :modal-append-to-body="false" :visible.sync="imgVisible" width="30%">
                <img width="100%" :src="dialogImageUrl" alt="图片未找到" />
            </el-dialog>

            <div>

                <!-- <el-upload action="上传接口" :limit="1" :on-preview="handlePictureCardPreview"
                    :before-upload="beforeAvatarUpload" :on-progress="onProgress" :on-success="imgSuccess"
                    :on-error="imgError" list-type="picture-card" :on-remove="handleRemove"
                    :class="{ disabled: uploadDisabled }" :file-list="fileList">
                    <i class="el-icon-plus"></i>
                    <div slot="tip" class="el-upload__tip">只能上传不超过4MB的图片</div>
                </el-upload> -->


            </div>
            <div :data="this.resp" style="text-align: left;margin-left: 35px;">
                <div style="font-family: 楷体;font-size: 24px;">识别结果：</div>
                <!-- <el-form ref="this.resp">
                    <el-form-item label="邮箱账号:" prop="this.resp" :label-width="formLabelWidth">
                        <el-input v-model="this.resp"></el-input>
                    </el-form-item>

                </el-form> -->

                <textarea cols="140" rows="12" v-model="this.resp" style="margin-left: 2%;font-family: 楷体;"> </textarea>

                <!-- <el-table :data="resp.result">
                    <el-table-column label="精确度" align="center" prop="resp.result"></el-table-column>
                </el-table> -->

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
            imgVisible: false,
            // 预览图片url
            dialogImageUrl: "",
            file: [],
            headers: { "Content-Type": "multipart/form-data" },
            resp: '',
            imageUrl: ''
        }
    },
    // 定义事件处理函数
    methods: {
        handleAvatarChangeIcon(file, fileList) {//选中文件触发的change事件
            // console.log(file)
            const isJPG = file.raw.type === 'image/jpeg'
            const isPNG = file.raw.type === 'image/png'
            // const isLt2M = file.raw.size / 1024 / 1024 < 0.5  //限制上传文件的大小
            if (!isPNG && !isJPG) {
                this.$message.error('上传图片只能是 JPG/PNG 格式!')
                return false
            } else {
                this.imageUrl = URL.createObjectURL(file.raw);//赋值图片的url，用于图片回显功能
                this.uploadOcr(file)//调用上传文件api接口
            }
        },
        modeUpload(item) {
            // console.log(item.file);
            this.file = item.file
            console.log(this.file)
        },
        handlePictureCardPreview(e) {
            this.imgVisible = true;
            this.dialogImageUrl = e.target.currentSrc;
            this.uploadDisabled = true;
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
            // let file = document.getElementById("id").files[0];
            // let name = document.getElementById("id").files[0].name;
            console.log(this.file)
            const formData = new FormData();
            // 'file' 是文件的名字，file 是 File 对象
            // formData.append('image', file)
            formData.append('file', this.file);
            // let fd = new FormData()
            // fd.append('templateFile', files)
            console.log(formData.get('file'))
            uploadOcr(formData).then(response => {
                // if (response.message == "操作成功") {
                //     this.resp = resp;
                // } else {
                //     this.$message({
                //         type: 'error',
                //         message: response.message
                //     })
                // }
                this.resp = response;
                console.log(this.resp)
            })
        }


    },
    // 生命周期钩子函数
    created() {
        // this.uploadOcr()

    }
}
</script>
<!-- 样式 css -->
<style scoped>
.el-upload-dragger {
    background-color: #fff;
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    box-sizing: border-box;
    width: 460px;
    height: 230px;
    text-align: center;
    cursor: pointer;
    overflow: hidden;
}

.location {
    font-size: 25px;
    color: rgb(160, 106, 5);
    font-family: "华文中宋";
    margin-top: 37px;
}

.breadcrumb {
    font-size: 25px;
    color: #080100;
    font-family: "华文中宋";
    margin-top: 40px;
}

.main-area {
    /* margin-left:  0px; */
    border-top: solid rgb(134, 116, 21);
    width: 100%;
}

.el-breadcrumb__item {
    font-size: 20px;
    font-family: "华文中宋";
}

.avatar {
    width: 360px;
    height: 180px;
    display: block;
}
</style>
