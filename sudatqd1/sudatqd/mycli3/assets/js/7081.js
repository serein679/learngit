"use strict";(self["webpackChunksudatqd"]=self["webpackChunksudatqd"]||[]).push([[7081],{57081:function(e,t,r){r.r(t),r.d(t,{default:function(){return b}});var i=function(){var e=this,t=e._self._c;return t("div",{attrs:{id:"background"}},[t("el-card",{staticClass:"box-card"},[t("div",{staticClass:"demo-image"},[t("div",{staticClass:"block"},[t("img",{attrs:{src:r(48314),height:"100%",width:"600px"}})])]),t("div",[t("h2",{staticStyle:{"text-align":"center","font-family":"STKaiti"}},[e._v(e._s(e.title))])]),t("el-form",{attrs:{width:"30%"}},[t("el-form",{ref:"loginForm",attrs:{model:e.loginform}},[t("el-form-item",{attrs:{label:"用户名称:","label-width":e.formLabelWidth}},[t("el-input",{staticStyle:{width:"65%"},attrs:{autocomplete:"off",placeholder:"请输入学号或工号"},model:{value:e.loginform.act,callback:function(t){e.$set(e.loginform,"act",t)},expression:"loginform.act"}}),t("label",{staticStyle:{"vertical-align":"central"}},[t("input",{directives:[{name:"model",rawName:"v-model",value:e.rememberMe,expression:"rememberMe"}],staticStyle:{width:"25px",height:"15px","vertical-align":"middle","margin-top":"-3px","font-family":"STKaiti"},attrs:{type:"checkbox",title:"记住用户"},domProps:{checked:Array.isArray(e.rememberMe)?e._i(e.rememberMe,null)>-1:e.rememberMe},on:{change:function(t){var r=e.rememberMe,i=t.target,a=!!i.checked;if(Array.isArray(r)){var o=null,l=e._i(r,o);i.checked?l<0&&(e.rememberMe=r.concat([o])):l>-1&&(e.rememberMe=r.slice(0,l).concat(r.slice(l+1)))}else e.rememberMe=a}}}),e._v("记住用户")])],1),t("el-form-item",{attrs:{label:"密　　码:","label-width":e.formLabelWidth}},[t("el-input",{staticStyle:{width:"65%"},attrs:{autocomplete:"off",placeholder:"请输入密码","show-password":""},model:{value:e.loginform.pwd,callback:function(t){e.$set(e.loginform,"pwd",t)},expression:"loginform.pwd"}}),t("label",{staticStyle:{"vertical-align":"central"}},[t("input",{directives:[{name:"model",rawName:"v-model",value:e.remember,expression:"remember"}],staticStyle:{width:"25px",height:"15px","vertical-align":"middle","margin-top":"-3px","font-family":"STKaiti"},attrs:{type:"checkbox",title:"记住密码"},domProps:{checked:Array.isArray(e.remember)?e._i(e.remember,null)>-1:e.remember},on:{change:function(t){var r=e.remember,i=t.target,a=!!i.checked;if(Array.isArray(r)){var o=null,l=e._i(r,o);i.checked?l<0&&(e.remember=r.concat([o])):l>-1&&(e.remember=r.slice(0,l).concat(r.slice(l+1)))}else e.remember=a}}}),e._v("记住密码")])],1),t("el-form-item",{attrs:{label:"验证码　:","label-width":e.formLabelWidth}},[t("div",{staticStyle:{display:"flex",width:"65%"}},[t("el-input",{staticStyle:{width:"70%","margin-left":"27px","margin-right":"5px"},attrs:{"prefix-icon":"el-icon-key",placeholder:"请输入验证码"},model:{value:e.loginform.validCode,callback:function(t){e.$set(e.loginform,"validCode",t)},expression:"loginform.validCode"}}),t("ValidCode",{on:{input:e.createValidCode}})],1)])],1),t("div",{staticStyle:{"text-align":"right","margin-bottom":"5%",float:"left"}},[t("el-link",{on:{click:function(t){return e.enroll()}}},[e._v("没有账号？去注册")])],1),t("div",{staticStyle:{"text-align":"right"}},[t("el-link",{on:{click:function(t){return e.findpassport()}}},[e._v("忘记密码？")])],1),t("div",{staticStyle:{"margin-top":"8%"}},[t("el-button",{on:{click:function(t){return e.home()}}},[e._v("取 消")]),t("el-button",{staticStyle:{"background-color":"antiquewhite",border:"1",color:"black"},attrs:{type:"primary"},on:{click:function(t){return e.handleLogin()}}},[e._v("登 录")])],1)],1)],1)],1)},a=[],o=(r(44114),r(8671)),l=function(){var e=this,t=e._self._c;return t("div",{staticClass:"ValidCode disabled-select",style:`width:${e.width}; height:${e.height}`,on:{click:e.refreshCode}},e._l(e.codeList,(function(r,i){return t("span",{key:i,style:e.getStyle(r)},[e._v(e._s(r.code))])})),0)},s=[],n={name:"ValidCode",model:{prop:"value",event:"input"},props:{width:{type:String,default:"100px"},height:{type:String,default:"40px"},length:{type:Number,default:4},refresh:{type:Number}},data(){return{codeList:[]}},watch:{refresh(){this.createdCode()}},mounted(){this.createdCode()},methods:{refreshCode(){this.createdCode()},createdCode(){const e=this.length,t=[],r="ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz0123456789",i=r.length;for(let a=0;a<e;a++){const e=[Math.round(220*Math.random()),Math.round(240*Math.random()),Math.round(200*Math.random())];t.push({code:r.charAt(Math.floor(Math.random()*i)),color:`rgb(${e})`,fontSize:+[Math.floor(10*Math.random())]+6+10+"px",padding:`${[Math.floor(10*Math.random())]}px`,transform:`rotate(${Math.floor(90*Math.random())-Math.floor(90*Math.random())}deg)`})}this.codeList=t,this.$emit("input",t.map((e=>e.code)).join(""))},getStyle(e){return`color: ${e.color}; font-size: ${e.fontSize}; padding: ${e.padding}; transform: ${e.transform}`}}},d=n,c=r(81656),m=(0,c.A)(d,l,s,!1,null,"0e12f0aa",null),h=m.exports;let u;var p={name:"Login",components:{ValidCode:h},data(){return{remember:!1,rememberMe:!1,yzm:"",formLabelWidth:"",loginform:{act:"",pwd:""},ruleForm:{username:"",userpass:""},rules:{username:[{required:!0,message:"请输入用户名",trigger:"blur"},{max:18,message:"用户名格式错误",trigger:"blur"}],userpass:[{required:!0,message:"请输入密码",trigger:"blur"}]},validCode:"",passwordType:"password",capsTooltip:!1,loading:!1,showDialog:!1,title:"登录",redirect:void 0,otherQuery:{},dialogVisible:!1,active:0,findForm:{find_userid:"",pwd_question:"",find_answer:"",newpass:"",checknew_pwd:""}}},watch:{$route:{handler:function(e){const t=e.query;t&&(this.redirect=t.redirect)},immediate:!0}},created(){this.startLoading(),u.close()},methods:{verifyLogin(){let e=sessionStorage.getItem("SET_TOKEN");return this.username=sessionStorage.getItem("SET_NAME"),console.log(e),e},createValidCode(e){this.validCode=e},findpassport(){this.$router.push("/login/passportFind")},startLoading(){u=o.Loading.service({lock:!0,text:"加载中……",background:"rgba(0, 0, 0, 0.7)"})},enroll(){this.$router.push("/login/enroll")},home(){this.$router.push("/")},handleLogin(){if(this.loginform.validCode)if(this.loginform.validCode.toLowerCase()===this.validCode.toLowerCase()){var e=JSON.parse(JSON.stringify(this.loginform));this.$store.dispatch("user/login",e).then((()=>{this.username=sessionStorage.getItem("SET_NAME"),this.userType=sessionStorage.getItem("SET_USERTYPE"),0==this.userType?this.$router.push({path:this.redirect||"/",query:this.otherQuery}):this.$router.push("/management/index")}))}else this.$message.error("验证码错误");else this.$message.error("请填写验证码")}}},g=p,f=(0,c.A)(g,i,a,!1,null,"07d47c92",null),b=f.exports},48314:function(e,t,r){e.exports=r.p+"assets/img/1122.png"}}]);