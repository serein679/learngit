"use strict";(self["webpackChunksudatqd"]=self["webpackChunksudatqd"]||[]).push([[8970],{8970:function(e,t,i){i.r(t),i.d(t,{default:function(){return c}});var r=function(){var e=this,t=e._self._c;return t("div",[t("div",{staticStyle:{padding:"10px"}},[t("el-breadcrumb",{attrs:{separator:"/"}},[t("el-breadcrumb-item",{attrs:{to:{path:"/management/introManagement"}}},[e._v("纤维原料管理")]),t("el-breadcrumb-item",{attrs:{to:{path:"/management/addIntro"}}},[e._v("新增纤维原料")])],1)],1),t("div",{attrs:{slot:"title"},slot:"title"}),t("span",{staticStyle:{"font-family":"'楷体'","font-size":"26px"}},[e._v("新增纤维原料")]),t("el-tabs",{staticClass:"el-tab-div",staticStyle:{"font-family":"楷体","font-size":"26px"}},[t("el-form",{ref:"addFiber",staticStyle:{"font-size":"32px !important"},attrs:{model:e.addFiber,rules:e.rules}},[t("el-form-item",{attrs:{label:"纤维原料名称","label-width":e.formLabelWidth,prop:"fiberType"}},[t("el-input",{model:{value:e.addFiber.fiberType,callback:function(t){e.$set(e.addFiber,"fiberType",t)},expression:"addFiber.fiberType"}})],1),t("el-form-item",{attrs:{label:"原料特点","label-width":e.formLabelWidth,prop:"characteristic"}},[t("el-input",{attrs:{type:"textarea",rows:5,prop:"characteristic"},model:{value:e.addFiber.characteristic,callback:function(t){e.$set(e.addFiber,"characteristic",t)},expression:"addFiber.characteristic"}})],1)],1),t("div",{staticClass:"dialog-footer"},[t("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.addfiber()}}},[e._v("添 加")]),t("el-button",{attrs:{type:"primary"},on:{click:e.back}},[e._v("取 消")])],1)],1)],1)},a=[],s=(i(44114),i(53019),i(69053),i(77819),i(78010)),l={components:{},data(){return{rules:{fiberType:[{required:!0,message:"请输入纤维原料名称",trigger:"blur"}],characteristic:[{required:!0,message:"请输入纤维特点",trigger:"blur"}],process:[{required:!0,message:"请输入制作流程",trigger:"blur"}],institutionLocation:[{required:!0,message:"请输入地点",trigger:"blur"}],paperType:[{required:!0,message:"请选择纸张类型",trigger:"blur"}],fileName:[{required:!0,message:"请上传图片",trigger:"blur"}],location:[{required:!0,message:"请输入产地",trigger:"blur"}],institutionDate:[{required:!0,message:"请输入机构出现时间",trigger:"blur"}]},activeName1:"1",activeName:"1",users:[],options:[],value:[],list:[],loading:!1,isShow:!0,dialogVisible7:!1,adVisible7:!1,dialogVisible9:!1,adVisible9:!1,dialogVisible8:!1,adVisible8:!1,smid:"",currentPage:1,pageSizes:[2,5,10,20,30,50],pageSize:10,total:"",total1:"",total2:"",total5:"",selectName:[],selectName7:[],query:"",order:0,oneProjects:[],fileName:"",fileName1:"",file:null,dialogVisible5:!1,adVisible5:!1,total4:"",typeName:{0:"手工纸",1:"机械纸",2:"半手工半机械纸"},typelist1:[{paperType:0,typeName:"手工纸"},{paperType:1,typeName:"机械纸"},{paperType:2,typeName:"半手工半机械纸"}],fileList:[],fileList1:[],dialogVisible:!1,adVisible:!1,dialogVisible1:!1,adVisible1:!1,dialogVisible2:!1,adVisible2:!1,formLabelWidth:"120px",addFiber:[],firstTime:"",addPaper:{paperName:"",paperDes:"",paperType:"",paperId:"",site:"",location:"",process:"",picture:"",firstTime:""},pickerOptions:{shortcuts:[{text:"今天",onClick(e){e.$emit("pick",new Date)}},{text:"昨天",onClick(e){const t=new Date;t.setTime(t.getTime()-864e5),e.$emit("pick",t)}},{text:"一周前",onClick(e){const t=new Date;t.setTime(t.getTime()-6048e5),e.$emit("pick",t)}}]}}},created(){this.chooseTimeRange()},computed:{},methods:{chooseTimeRange(e){this.firstTime=e,console.log(this.firstTime)},filterTag(e,t,i){const r=i["property"];return t[r]===e},_filterChange(){this.totalSize=this.$refs.table.projectList.length,console.log(this.totalSize)},sendWangEditor(e){this.oneProjects.hyperlinks=e},reload(){this.isShow=!1,this.$nextTick((()=>{this.isShow=!0,this.$refs.table.bodyWrapper.scrollTop=0}))},back(){this.$router.push("/management/introManagement")},selectList1(){Projects().then((e=>{this.selectName=e.data}))},resetQuery(){this.query="",this.currentPage=1,this.pageSize=10,this.typefilter="",this.coursefilter="",this.getProjects()},indexMethods(e){return(this.currentPage-1)*this.pageSize+e+1},beforeFileUpload(e){const t=e.size/1024/1024<10;return t?(this.file=e,this.fileName=e.name,!1):(this.$message.error("上传的文件大小不能超过 10MB!"),!1)},addfiber(){if(console.log(this.addFiber),""==this.addFiber.fiberType)return this.$message({type:"warning",message:"原料名称为空，请填写！"}),!1;this.$refs.addFiber.validate((e=>{if(!e)return console.log("error submit!!"),!1;this.$confirm("您确定要添加该原料吗?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((()=>{const e=new FormData;e.append("file",this.file),e.append("fiberType",this.addFiber.fiberType),e.append("characteristic",this.addFiber.characteristic),(0,s.qF)(e).then((e=>{"操作成功"==e.message?(this.$router.push("/management/introManagement"),this.$message({type:"success",message:"添加成功！"}),this.adVisible=!1):500==e.code?(this.$message({type:"error",message:"纤维原料名重复！"}),this.adVisible=!1):this.$message({type:"error",message:e.message})}))}))}))},indexMethods4(e){return(this.currentPage4-1)*this.pageSize4+e+1}},filters:{formatData(e){return moment(e).format("YYYY-MM-DD")}}},o=l,n=i(81656),d=(0,n.A)(o,r,a,!1,null,"658ecdfd",null),c=d.exports},53019:function(e,t,i){var r=i(85471);r["default"].filter("filterAmount",(function(e,t){return t||(t=20),e&&e.length>t&&(e=e.substring(0,t)+"..."),e}))}}]);