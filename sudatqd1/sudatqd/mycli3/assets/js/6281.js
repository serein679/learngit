"use strict";(self["webpackChunksudatqd"]=self["webpackChunksudatqd"]||[]).push([[6281],{6281:function(t,e,i){i.r(e),i.d(e,{default:function(){return d}});var a=function(){var t=this,e=t._self._c;return e("div",{staticStyle:{"margin-top":"8px","background-color":"antiquewhite"}},[e("div",{staticStyle:{margin:"0px",top:"140px","background-color":"antiquewhite"}},[e("div",[e("el-row",[e("el-col",{attrs:{span:3,offset:1}},[e("h3",{staticClass:"location"},[t._v("当前位置：")])]),e("el-col",{staticStyle:{"margin-top":"43px"},attrs:{span:10}},[e("el-breadcrumb",{attrs:{separator:"/"}},[e("el-breadcrumb-item",{attrs:{to:{path:"/introduction"}}},[t._v("纸张基因")]),e("el-breadcrumb-item",{attrs:{to:{path:"/introduction/echarts"}}},[t._v(" 纤维库 ")]),e("el-breadcrumb-item",[e("a",{attrs:{href:"/"}},[t._v("纤维详情")])])],1)],1)],1),e("el-row",{staticStyle:{height:"auto"}},[e("el-col",{staticStyle:{position:"relative","font-family":"STKaiti","font-size":"22px","text-align":"left",top:"0%",width:"47%",height:"440px","margin-left":"5%","background-color":"white","margin-top":"5%","margin-bottom":"10%","border-radius":"30px"},attrs:{span:18}},[e("div",[e("table",{staticStyle:{"margin-left":"3%",width:"100%","line-height":"290%"},attrs:{data:t.oneMa.fiberItem}},[e("tr",[e("div",{staticStyle:{width:"90px","margin-top":"3%","margin-left":"0%"}})]),e("tr",[e("div",{staticStyle:{width:"80%","margin-left":"0","margin-right":"5%","text-indent":"0em","text-align":"justify","font-family":"STKaiti","font-size":"28px",color:"rgb(217, 115, 41)"}},[t._v(" "+t._s(t.oneMa.fiberItem.fiberType)+" ")])]),e("tr",{staticStyle:{"margin-left":"40px"}},[e("div",{staticStyle:{"margin-left":"40px"}},[t._v(" 纤维原料特点： "+t._s(t.oneMa.fiberItem.characteristic)+" ")])]),e("tr",{staticStyle:{"margin-left":"40px"}},[e("div",{staticStyle:{"margin-left":"40px",float:"left",height:"300px"}},[t._v(" 相关纸张： ")]),t._l(t.oneMa.paperFiberList,(function(i){return e("div",{staticStyle:{"margin-left":"40px",width:"20%","vertical-align":"top",float:"left"}},[e("font",{staticStyle:{cursor:"pointer","font-family":"STKaiti","font-weight":"200",color:"rgb(205, 134, 63)",display:"inline-block","vertical-align":"top"},on:{click:function(e){return t.select(i.paperId)}}},[t._v(" "+t._s(i.paperName))])],1)}))],2)])])]),e("el-col",{staticStyle:{position:"relative","font-family":"STKaiti","font-size":"22px","text-align":"left",top:"0%",height:"440px","margin-left":"15%","margin-top":"5%","margin-bottom":"5%","border-radius":"30px"},attrs:{span:6}},[e("div",[[e("el-carousel",{attrs:{"indicator-position":"outside",height:"440px",width:"640px"}},t._l(t.oneMa.pictureList,(function(t){return e("el-carousel-item",{key:t},[e("img",{attrs:{preview:"",src:t.pictureLocation,width:"640px",height:"440px"}})])})),1)]],2)])],1),e("el-row",[e("p",{staticClass:"last-style",staticStyle:{"text-align":"right"},on:{click:t.goback}},[e("el-button",[t._v("返回")])],1)])],1)])])},r=[],n=(i(44114),i(76678)),o=i(30558),s=i(78010),l={name:"Resource",components:{},data(){return{oneMa:[],ins:[],fibers:[],fiberId:"",archiveList:[],institutionId:"",paperList:[],institutionList:[],typeName1:{0:"手工纸",1:"机械纸",2:"半手工半机械纸"},url:"",previewVisible:!1,pdfSrc:""}},created(){this.gettingData()},mounted(){this.getOneFi(this.fiberId)},watch:{$route:"gettingData"},methods:{gettingData(){var t=JSON.parse(this.$Base64.decode(this.$route.query.info));this.fiberId=t},goback(){this.$router.go(-1)},getArchives(){(0,n.WM)().then((t=>{this.archiveList=t.data}))},select(t){""!==t&&(console.log(t),this.$router.push({path:"./select",query:{info:this.$Base64.encode(t)}}))},getOneInstitution(){(0,o.he)().then((t=>{this.his=t.data}))},getOnePa(){(0,o.ge)().then((t=>{this.paperList=t.data}))},getOneFi(t){(0,s.Gi)(t).then((t=>{this.oneMa=t.data,console.log(this.oneMa)}))}}},c=l,p=i(81656),h=(0,p.A)(c,a,r,!1,null,"66c4aa50",null),d=h.exports}}]);