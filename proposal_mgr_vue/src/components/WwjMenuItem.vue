<template>
  <div id="wwjMenuItem">
    <span v-for="item in menus" :key="item.menuId">
      <span v-if="item.children.length > 0">
        <el-submenu :index="item.menuName">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span v-html="item.menuName"></span>
          </template>
          <wwj-menu-item
            :menus="item.children"
            @itemclick="doClick"
          ></wwj-menu-item>
        </el-submenu>
      </span>
      <span v-if="item.children.length == 0">
        <el-menu-item
          :index="item.menuName"
          v-html="item.menuName"
          @click="doClick(item)"
        ></el-menu-item>
      </span>
    </span>
  </div>
</template>
<script>
export default {
  name: "WwjMenuItem",
  props: ["menus"],
  methods: {
    doClick(item) {
      // console.log(item);
      this.$emit("itemclick", item);
    },
  },
};
</script>
<style lang="less"></style>
