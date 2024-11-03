<template>
    <div slot="title">
        <div style="padding: 10px;">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/management/mienManagement' }">机构详情管理</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/management/editorMien' }">编辑机构</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <span style="font-family: '楷体'; font-size: 26px;">编辑机构</span>
        <el-tabs class="el-tab-div" style="font-family: 楷体;font-size: 26px;">
            <el-form :model="oneInstitution" ref="oneInstitution" style="font-size: 32px !important" :rules="rules">
                <el-form-item label="机构名称" :label-width="formLabelWidth" prop="institutionName">
                    <el-input v-model="oneInstitution.institutionName"></el-input>
                </el-form-item>
                <el-form-item label="机构简介" :label-width="formLabelWidth" prop="institutionDescription">
                    <el-input v-model="oneInstitution.institutionDescription" type="textarea" :rows="5"
                        prop="institutionDescription"></el-input>
                </el-form-item>
                <el-form-item label="机构图片" :label-width="formLabelWidth" style="width: 630px;">
                    <el-input v-model="fileName" :readonly='true' placeholder="文件名" style="width:388px;" clearable>
                    </el-input>
                    <el-upload class="upload-demo" ref="upload" action="doUpload" :limit="1" :file-list="fileList"
                        style="float:right" :before-upload="beforeFileUpload">
                        <el-button slot="trigger" type="primary">上传文件<i
                                class="el-icon-upload el-icon--right"></i></el-button>
                        <div slot="tip" class="el-upload__tip">只能上传1M以内的文件</div>
                    </el-upload>
                    <img :src="oneInstitution.picture" width="40%" style="float: center;margin-top: 10px;">
                </el-form-item>
                <el-form-item label="相关纸张" :label-width="formLabelWidth">
                    <template slot-scope="props">
                        <el-select v-model="oneInstitution.paperList1" :props="{ key: 'paperId', label: 'paperName' }"
                            multiple placeholder="请选择" @click.native="getPapers" style="width:100%" filterable>
                            <el-option v-for="item in papers" :key="item.paperId" :label="item.paperName"
                                :value="item.paperId">
                            </el-option>
                        </el-select>
                    </template>
                    <!-- <el-input v-model="oneteacher.majorId"></el-input> -->
                </el-form-item>
                <el-form-item label="相关档案" :label-width="formLabelWidth">
                    <template slot-scope="props">
                        <el-select v-model="oneInstitution.archiveList1" :props="{ key: 'paperId', label: 'paperName' }"
                            multiple placeholder="请选择" @click.native="getArchives" style="width:100%" filterable>
                            <el-option v-for="item in archives" :key="item.archiveId" :label="item.archiveName"
                                :value="item.archiveId">
                            </el-option>
                        </el-select>
                    </template>
                    <!-- <el-input v-model="oneteacher.majorId"></el-input> -->
                </el-form-item>
                <el-form-item label="机构地点" :label-width="formLabelWidth" prop="institutionLocation"
                    style="text-align: left;">
                    <!-- <el-input v-model="oneInstitution.institutionLocation"></el-input> -->
                    <el-cascader size="large" :options="options1" v-model="selectedOptions" @change="handleChange"
                        style="width: 500px;">
                    </el-cascader>

                </el-form-item>
                <el-col :span="7">
                    <el-form-item label="位置经度">
                        <el-input size="small" type="text" v-model="oneInstitution.longitude" readonly />
                    </el-form-item>
                </el-col>
                <el-col :span="7">
                    <el-form-item label="位置纬度">
                        <el-input size="small" type="text" v-model="oneInstitution.latitude" readonly />
                    </el-form-item>
                </el-col>

                <el-form-item>
                    <el-input size="small" type="text" readonly />

                    <baidu-map class="bm-view" ak="qSnlEuO9cBNRGwWXvEdyJagc6RpNlYZc" :center="center" :zoom="zoom"
                        :scroll-wheel-zoom="true" @ready="handler" style="height: 500px;">
                        <bm-navigation anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-navigation>
                        <!--定位-->
                        <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true"
                            :autoLocation="true"></bm-geolocation>
                        <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true"
                            :autoLocation="true"></bm-geolocation>
                    </baidu-map>
                </el-form-item>

                <!-- <el-form-item>
            <div class="txt_list u-f u-f-ac">
              <p class="lable u-f u-f-ac">
                <span class="font14 text_danger" style="margin-right: 3px;">*</span>
                <span class="font14 text_black">经纬度：</span>
              </p>
              <span class="font14 text_black">{{
                addMerFrom.lng ? addMerFrom.lng + "," + addMerFrom.lat : ""
              }}</span>
            </div>
            <div class="map_box u-f">
              <p class="lable u-f">
                <span class="font14 text_black">点击选择位置：</span>
              </p>
              <el-amap ref="map" vid="amapDemo" :amap-manager="mapFrom.amapManager" :center="mapFrom.center"
                :zoom="mapFrom.zoom" :events="mapFrom.events" class="map" style="height: 500px;">
              </el-amap>
            </div>
          </el-form-item> -->
                <el-form-item label="机构出现时间" :label-width="formLabelWidth" style="margin-left: 1px;width: 300px;"
                    prop="institutionDate">
                    <!-- <el-input v-model="oneproject.projectStartTime"></el-input> -->
                    <el-date-picker v-model="oneInstitution.institutionDate" type="date" placeholder="选择日期" align="right"
                        :picker-options="pickerOptions">
                    </el-date-picker>
                </el-form-item>
            </el-form>
            <div class="dialog-footer">
                <el-button type="primary" @click="changeProject()">修 改</el-button>
                <el-button type="primary" @click="back">取 消</el-button>
            </div>
        </el-tabs>
    </div>
</template>
<script>
// import Carousel from '@/components/Carousel.vue';
import './filters.js'
// import { getOnePaper, editPaper } from "@/api/paper"
import { regionData, CodeToText, TextToCode } from 'element-china-area-data'

// import WangEditor from '@/components/WangEditor.vue'
import { getOneInstitution, getOneIn, getOnePa, editInstitution } from '@/api/institution'
import { addPaper, getPaper } from "@/api/paper"
import { getArchives } from '@/api/archive';
import VueAMap from "vue-amap";
let amapManager = new VueAMap.AMapManager();
export default {
    components: {
        // WangEditor
    },
    data() {
        let that = this;
        return {
            addMerFrom: {
                merchant: "",
                areaCode: ["440000", "440300", "440305"],
                area: "广东省深圳市南山区",
                address: "",
                lng: "113.923552",
                lat: "22.528499",
            },
            mapFrom: {
                amapManager,
                zoom: 15,
                center: [113.923552, 22.528499],
                events: {
                    click: (e) => {
                        let cen = [e.lnglat.lng, e.lnglat.lat];
                        that.mapFrom.center = cen;
                        that.addMerFrom.lng = e.lnglat.lng;
                        that.addMerFrom.lat = e.lnglat.lat;
                        that.getAdress(cen);
                    },
                },
            },
            rules: {
                institutionName: [
                    { required: true, message: '请输入机构名称', trigger: 'blur' },
                ],
                institutionLocation: [
                    { required: true, message: '请输入机构地点', trigger: 'blur' },
                    // { max: 18, message: '用户名格式错误', trigger: 'blur' }
                ],
                process: [
                    { required: true, message: '请输入制作流程', trigger: 'blur' },
                ],
                site: [
                    { required: true, message: '请输入地点', trigger: 'blur' },
                    // { max: 18, message: '用户名格式错误', trigger: 'blur' }
                ],
                paperType: [
                    { required: true, message: '请选择纸张类型', trigger: 'blur' },
                    // { max: 18, message: '用户名格式错误', trigger: 'blur' }
                ],
                fileName: [
                    { required: true, message: '请上传图片', trigger: 'blur' },
                    // { max: 18, message: '用户名格式错误', trigger: 'blur' }
                ],
                institutionDescription: [
                    { required: true, message: '请输入机构描述', trigger: 'blur' },
                    // { max: 18, message: '用户名格式错误', trigger: 'blur' }
                ],
                institutionDate: [
                    { required: true, message: '请输入机构出现时间', trigger: 'blur' },
                ],
            },
            options1: regionData,
            selectedOptions: [],
            activeName1: '1',
            activeName: '1',
            users: [],
            paperId: '',
            options: [],
            value: [],
            list: [],
            loading: false,
            isShow: true,
            dialogVisible7: false,
            adVisible7: false,
            dialogVisible9: false,
            adVisible9: false,
            dialogVisible8: false,
            adVisible8: false,
            teachers: [],
            smid: "",
            // paperList: [],
            oneInstitution: [],
            teachers: [],
            currentPage: 1,// 默认显示第几页
            pageSizes: [2, 5, 10, 20, 30, 50],// 个数选择器（可修改）
            pageSize: 10,// 默认每页显示的条数（可修改）
            total: '',// 总记录数
            total1: '',
            total2: '',
            total5: '',
            selectName: [],
            selectName7: [],
            query: "",//检索词
            order: 0,//排序
            oneProjects: [],
            editor: '',
            fileName: "",//上传的文件名称
            fileName1: "",//上传的文件名称
            file: null,
            dialogVisible5: false,
            adVisible5: false,
            total4: '',
            typeName: {
                '0': "手工纸",
                '1': "机械纸",
                '2': "半手工半机械纸",
            },//将资源类型转换为对应文字
            fileList: [], // 用于存储文件列表
            fileList1: [], // 用于存储文件列表
            headers: { /* 你的请求头信息，如果有的话 */ },
            dialogVisible: false,
            adVisible: false,
            dialogVisible1: false,
            adVisible1: false,
            dialogVisible2: false,
            adVisible2: false,
            formLabelWidth: '120px',
            papers: [],
            archives: [],
            // paperList1: [],
            typelist1: [{
                paperType: 0,
                typeName: "手工纸",
            }, {
                paperType: 1,
                typeName: "机械纸",
            }, {
                paperType: 2,
                typeName: "半手工半机械纸",
            }], // 资源类型列表
            pickerOptions: {
                shortcuts: [{
                    text: '今天',
                    onClick(picker) {
                        picker.$emit('pick', new Date());
                    }
                }, {
                    text: '昨天',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() - 3600 * 1000 * 24);
                        picker.$emit('pick', date);
                    }
                }, {
                    text: '一周前',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', date);
                    }
                }]
            },
            center: { lng: 0, lat: 0 },
            zoom: 10,

        }
    },
    created() {
        // this.getQuery();
        this.gettingData();
        // this.getTeacherIntro();
        this.getPapers();
        this.getArchives();

        this.getOneIn(this.institutionId);

    },

    watch: { '$route': 'gettingData' },
    methods: {
        handler({ BMap, map }) {
            console.log(BMap, map)
            this.center.lng = 116.404
            this.center.lat = 39.915
            this.zoom = 14
        },
        getAdress(cen) {
            AMap.plugin("AMap.Geocoder", () => {
                let geocoder = new AMap.Geocoder();
                geocoder.getAddress(cen, (status, result) => {
                    if (status === "complete" && result.regeocode) {
                        let address = result.regeocode.formattedAddress;
                        let areaCode = this.handleAreaCode(
                            result.regeocode.addressComponent.adcode
                        );
                        this.addMerFrom.areaCode = areaCode;
                        let obj = this.handleAddress(address);
                        this.addMerFrom.area = obj.area; this.addMerFrom.address = obj.address;
                    }
                });
            });
        },
        getTeacherIntro() {
            TeacherIntro().then(response => {
                this.teachers = response.data
            })
        },
        handleChange(value) {
            this.getCodeToText(null, value)
            let paramSel = {
                province: '',
                city: '',
                district: ''
            }
            //console.log(this.options)
            this.options1.map(province => {
                if (province.value == value[0]) {
                    paramSel.province = province.label;
                    province.children.map(city => {
                        if (city.value == value[1]) {
                            if (city.label == '市辖区') {
                                paramSel.city = province.label
                            } else {
                                paramSel.city = city.label
                            }
                            if (city.hasOwnProperty('children')) {
                                city.children.map(area => {
                                    if (area.value == value[2]) {
                                        paramSel.district = area.label
                                    }
                                })
                            } else {

                            }
                        }
                    })
                }
            })
            this.center = paramSel.province + paramSel.city + paramSel.district;
            console.log(this.center)
        },
        getCodeToText(codeStr, codeArray) {
            console.log(codeArray)
            if (null === codeStr && null === codeArray) {
                return null;
            } else if (null === codeArray) {
                codeArray = codeStr.split(",");
            }
            let area = "";
            switch (codeArray.length) {
                case 1:
                    area += CodeToText[codeArray[0]];
                    break;
                case 2:
                    area += CodeToText[codeArray[0]] + "/" + CodeToText[codeArray[1]];
                    break;
                case 3:
                    area +=
                        CodeToText[codeArray[0]] +
                        "/" +
                        CodeToText[codeArray[1]] +
                        "/" +
                        CodeToText[codeArray[2]];
                    break;
                default:
                    break;
            }
            console.log(area)
            this.oneInstitution.institutionLocation = area
            console.log(this.oneInstitution.institutionLocation)
            return area;
        },
        filterTag(value, row, column) {
            const property = column["property"];
            return row[property] === value;
        },
        getArchives() {
            getArchives().then(response => {
                this.archives = response.data
                console.log(this.archives)
            })
        },
        gettingData() {
            var queryInstitutionId = JSON.parse(this.$Base64.decode(this.$route.query.info))
            this.institutionId = queryInstitutionId;
        },
        _filterChange() {
            this.totalSize = this.$refs.table.projectList.length;
            console.log(this.totalSize);
        },
        sendWangEditor(val) {
            this.oneProjects.hyperlinks = val;//去掉p标签
        },

        indexMethods(index) {
            // 当前页数 - 1 * 每页数据条数 + index + 1 （ index 是索引值，从0开始）
            return (this.currentPage - 1) * this.pageSize + index + 1;
        },

        // 文件上传前的处理
        beforeFileUpload(file) {
            // 这里可以添加对文件的一些检查，比如文件的大小和类型等
            const isLt10M = file.size / 1024 / 1024 < 10;
            if (!isLt10M) {
                this.$message.error('上传的文件大小不能超过 10MB!');
                return false;
            } else {
                // 保存文件对象
                this.file = file;
                this.fileName = file.name;
                return false; // 返回false不会自动上传
            }
        },
        getPapers() {
            getPaper().then(response => {
                this.papers = response.data
                console.log(this.papers)
            })
        },
        getOneIn(institutionId) {
            getOneIn(institutionId).then((response) => {
                this.oneInstitution = response.data[0];
                this.paperList1 = this.oneInstitution.paperList1;

                this.fileName = this.oneInstitution.institutionPicture;
                this.selectedOptions = TextToCode[this.oneInstitution.institutionLocation.split('/')[0]][this.oneInstitution.institutionLocation.split('/')[1]][this.oneInstitution.institutionLocation.split('/')[2]].code;
                console.log(this.oneInstitution);
            });
        },
        changeProject() {
            console.log(this.oneInstitution); // 打印 oneresource 对象
            if (this.oneInstitution.institutionName == "") {
                this.$message({
                    type: 'warning',
                    message: '机构名称为空，请填写！'
                })
                return false;
            }
            else {
                this.$refs.oneInstitution.validate(valid => {
                    if (valid) {
                        this.$confirm('您确定要修改该机构吗?', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(() => {
                            // 创建一个 FormData 对象
                            const formData = new FormData();
                            formData.append('file', this.file);
                            // 添加其他表单数据
                            formData.append('institutionId', this.oneInstitution.institutionId);
                            formData.append('institutionName', this.oneInstitution.institutionName);
                            formData.append('institutionLocation', this.oneInstitution.institutionLocation);
                            formData.append('paperIds', this.oneInstitution.paperList1);
                            formData.append('archiveIds', this.oneInstitution.archiveList1);

                            formData.append('institutionDescription', this.oneInstitution.institutionDescription);
                            formData.append('institutionDate', new Date(this.oneInstitution.institutionDate));
                            formData.append('institutionPicture', this.oneInstitution.institutionPicture);
                            // location.reload();//自动刷新  
                            editInstitution(formData).then(response => {
                                if (response.message == "操作成功") {
                                    this.$router.push('/management/mienManagement')
                                    this.$message({
                                        type: 'success',
                                        message: "修改成功！"
                                    })
                                    this.dialogVisible = false
                                    // this.getProjects()
                                } else if (response.code == 500) {
                                    this.$message({
                                        type: 'error',
                                        message: '机构名重复！'
                                    })
                                    this.adVisible = false
                                } else {
                                    this.$message({
                                        type: 'error',
                                        message: response.message
                                    })
                                }
                            })
                        })
                    } else {
                        console.log('error submit!!')
                        return false
                    }
                })
            }
        },
        back() {
            this.$router.push('/management/mienManagement')
        },
        //删除
        indexMethods4(index) {
            // 当前页数 - 1 * 每页数据条数 + index + 1 （ index 是索引值，从0开始）
            return (this.currentPage4 - 1) * this.pageSize4 + index + 1;
        },
    },
    filters: {
        formatData(data) {
            return moment(data).format('YYYY-MM-DD')
        }
    }
}

</script>
<style scoped>
.breadcrumb {
    font-size: 25px;
    color: #080100;
    font-family: "楷体";
    margin-top: 40px;
}

.el-breadcrumb__item {
    font-size: 22px;
    font-family: "楷体";
}</style>