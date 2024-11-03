<template>
    <div style="margin-top: 8px;background-color: antiquewhite;">
        <div>
            <input type="file" id="id" name="image" class="getImgUrl_file" @change="shangc($event)"
                accept="image/jpg, image/jpeg, image/png" />
            <br />
            <img :src="picPath" alt="">
            <h4>识别结果:</h4>
            <table>
                <tr>
                    <th>物品名称</th>
                    <th>所属类目</th>
                    <th>识别度</th>
                </tr>
                <tr v-if="data" v-for="item in data">
                    <td>{{ item.keyword }}</td>
                    <td>{{ item.root }}</td>
                    <td>{{ item.score }}</td>
                </tr>
            </table>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default {
    data() {
        return {
            data: "",
            picPath: ''
        };
    },
    methods: {
        shangc(e) {
            let files = document.getElementById("id").files[0];
            let name = document.getElementById("id").files[0].name;
            // this.picPath=document.getElementById("id").value
            let arr = name.split(".");
            let fileSize = 0;
            let fileMaxSize = 10240; //1M
            if (files) {
                fileSize = files.size;
                if (fileSize > 10 * 1024 * 1024) {
                    alert("文件大小不能大于10M！");
                    files.value = "";
                    return false;
                } else if (fileSize <= 0) {
                    alert("文件大小不能为0M！");
                    files.value = "";
                    return false;
                }
            } else {
                return false;
            }
            //转码base64
            let reader = new FileReader();
            let imgFile;
            // let that = this
            reader.readAsDataURL(files);
            reader.onload = async e => {
                imgFile = e.target.result;
                let arr = imgFile.split(",");
                this.faceBase64 = arr[1];
                this.picPath = 'data:image/png;base64,' + arr[1]
                console.log(this.picPath)
                console.log(arr[1]);
                const { data: data } = await axios.post(
                    "https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general",
                    {
                        access_token:
                            "24.f9ba9c5241b67688bb4adbed8bc91dec.2592000.1485570332.282335-8574074",
                        image: arr[1]
                    }
                );
                this.data = data.result;
                console.log(this.data);
            };
        }
    }
};
</script>

<style scoped>
table,
th,
td {
    border: 1px solid orangered;

}

img {
    width: 400px;
    height: 400px;
    border: 1px solid red;
}
</style>
