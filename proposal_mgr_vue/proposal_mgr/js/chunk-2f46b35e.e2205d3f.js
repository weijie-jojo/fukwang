(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2f46b35e"],{e1da:function(e,t,s){},ea4b:function(e,t,s){"use strict";s.r(t);var n=function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{attrs:{id:"permission"}},[s("div",{staticClass:"body"},[s("el-row",[s("div",{staticClass:"setting"},[s("i",{staticClass:"el-icon-setting"},[e._v("权限配置")])])]),s("el-row",{staticClass:"title",attrs:{gutter:80}},[s("el-col",{attrs:{span:20}},[s("div",{staticClass:"grid-content bg-purple"},[s("el-select",{attrs:{filterable:"",placeholder:"请选择"},on:{change:function(t){return e.selectMenu(e.value)}},model:{value:e.value,callback:function(t){e.value=t},expression:"value"}},e._l(e.options,(function(e){return s("el-option",{key:e.roleId,attrs:{label:e.roleName,value:e.roleId}})})),1)],1)])],1),s("el-row",{staticClass:"heard",attrs:{gutter:80}},[s("el-col",{attrs:{span:8}},[s("div",{staticClass:"grid-content bg-purple"},[s("el-tag",[e._v("当前角色拥有的菜单")])],1)]),s("el-col",{attrs:{span:4}},[s("div",{staticClass:"grid-content bg-purple"},[s("el-tag",[e._v("可执行操作")])],1)]),s("el-col",{attrs:{span:8}},[s("div",{staticClass:"grid-content bg-purple"},[s("el-tag",[e._v("当前角色未拥有菜单")])],1)])],1),s("el-row",{staticClass:"main",attrs:{gutter:80}},[s("el-col",{attrs:{span:8}},[s("div",{staticClass:"grid-content bg-purple"},[s("el-tree",{ref:"treeNode",attrs:{data:e.data,"show-checkbox":"","node-key":"menuId","default-expanded-keys":e.expanded,"default-checked-keys":e.checked,props:e.defaultProps}})],1)]),s("el-col",{attrs:{span:4}},[s("div",{staticClass:"buttonelone"},[s("el-button",{attrs:{type:"primary",icon:"el-icon-circle-plus"},on:{click:function(t){return e.addTerraceMenu()}}},[e._v("←")])],1),s("br"),s("div",{staticClass:"buttoneltwo"},[s("el-button",{attrs:{type:"primary",icon:"el-icon-remove"},on:{click:function(t){return e.deleteTerraceMenu()}}},[e._v("→")])],1)]),s("el-col",{attrs:{span:8}},[s("div",{staticClass:"grid-content bg-purple"},[s("el-tree",{ref:"treeNodetwo",attrs:{data:e.datatwo,"show-checkbox":"","node-key":"menuId","default-expanded-keys":e.expanded,"default-checked-keys":e.checked,props:e.defaultProps}})],1)])],1)],1)])},a=[],l=s("3141"),r=s("bc3a"),i=s.n(r),o={name:"Login",data:function(){return{options:[],expanded:[],expandedtwo:[],checked:[],checkedtwo:[],value:"管理员",message:"",tree:[],treetwo:[],menus:[],menustwo:[],data:[],datatwo:[],nodes:[],roleId:"1",defaultProps:{children:"children",label:"menuName"}}},mounted:function(){this.initPage()},methods:{initPage:function(){var e=this;Object(l["a"])({type:"get",url:"/initRole",success:function(t){console.log("options=="+t.list),e.options=t.list,e.message=t.message,e.selectMenu(e.roleId)}})},parseTree:function(e,t,s){for(var n=0;n<e.length;n++)if(e[n].menuPid==s){var a={menuId:e[n].menuId,menuName:e[n].menuName,menuUrl:e[n].menuUrl,menuPid:e[n].menuPid,children:[]};t.push(a),this.parseTree(e,a.children,a.menuId)}},selectMenu:function(e){var t=this;this.roleId=e,this.data=[],this.tree=[],this.treetwo=[],this.datatwo=[],Object(l["a"])({type:"get",url:"/selectMenuById",data:{roleId:e},success:function(s){if(t.menus=s.list,s.list.length>0){for(var n=0;n<s.list.length-1;n++)for(var a=0;a<s.list.length-1-n;a++)if(s.list[a].menuPid>s.list[a+1].menuPid){var l=s.list[a];s.list[a]=s.list[a+1],s.list[a+1]=l}t.parseTree(t.menus,t.tree,s.list[0].menuPid)}t.data=t.tree,t.selectAllMenu(e)}})},selectAllMenu:function(e){var t=this;this.datatwo=[],this.treetwo=[],Object(l["a"])({type:"get",url:"/seMeByRIdNo",data:{roleId:e},success:function(e){if(t.menustwo=e.list,e.list.length>0){for(var s=0;s<e.list.length-1;s++)for(var n=0;n<e.list.length-1-s;n++)if(e.list[n].menuPid>e.list[n+1].menuPid){var a=e.list[n];e.list[n]=e.list[n+1],e.list[n+1]=a}t.parseTree(t.menustwo,t.treetwo,e.list[0].menuPid)}t.datatwo=t.treetwo}})},addTerraceMenu:function(){var e=this,t=this.$refs.treeNodetwo.getCheckedKeys();i()({method:"post",url:"/addTerraceMenu",baseURL:this.$store.state.baseURL,withCredentials:!0,data:{roleId:this.roleId,checkNode:t},transformRequest:[function(e){var t="";for(var s in e)t+=s+"="+e[s]+"&";return t}]}).then((function(t){e.selectMenu(e.roleId),console.log(t)})).catch((function(e){console.log(e)}))},deleteTerraceMenu:function(){var e=this,t=this.$refs.treeNode.getCheckedKeys();i()({method:"post",url:"/deleteTerraceMenu",baseURL:this.$store.state.baseURL,withCredentials:!0,data:{roleId:this.roleId,checkNode:JSON.stringify(t)},transformRequest:[function(e){var t="";for(var s in e)t+=s+"="+e[s]+"&";return t}]}).then((function(t){e.selectMenu(e.roleId);var s=t.list;console.log(s)})).catch((function(t){console.log(t),e.$message("请联系管理员...")}))}}},c=o,d=(s("ed90"),s("2877")),u=Object(d["a"])(c,n,a,!1,null,null,null);t["default"]=u.exports},ed90:function(e,t,s){"use strict";s("e1da")}}]);
//# sourceMappingURL=chunk-2f46b35e.e2205d3f.js.map