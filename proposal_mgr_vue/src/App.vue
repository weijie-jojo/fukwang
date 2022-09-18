<template>
  <!-- template标签下默认会带一个div标签，div标签相当于根标签，根标签有一个id，
					id名字是文件名，首字母小写 -->
  <div id="app">
    <router-view @setadmin="setAdmin" :admin2="admin"></router-view>
  </div>
</template>
<script>
export default {
  name:'App',
  methods:{
    setAdmin(admin){
      console.log('父组件', admin);
      this.admin = admin;
    }
  },
  data(){
    return {
      admin:{},
    }
  }
}
</script>
<style lang="less" scoped>
@import "./css/Base.css";
#app {
  width: 100%;
  height: 100%;
}
</style>
