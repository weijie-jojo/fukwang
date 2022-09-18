<template>
  <div id="wwjPage">
    <el-row type="flex" justify="center">
      <el-col :span="24">
        <slot name="search"></slot>
      </el-col>
    </el-row>
    <el-row type="flex" justify="center">
      <el-col :span="24">
        <el-table :data="tableData" height="400" border style="width: 100%">
          <slot name="heads"></slot>
        </el-table>
      </el-col>
    </el-row>
    <br />
    <el-row type="flex" justify="center">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currPage"
        :page-sizes="[5, 10, 15, 20, 25, 30]"
        :page-size="limit"
        layout="total, sizes, prev, pager, next, jumper"
        :total="count"
      >
      </el-pagination>
    </el-row>
  </div>
</template>
<script>
export default {
  name: "WwjPage",
  props: ["tableData", "search", "count", "limit", "currPage"],
  methods: {
    handleSizeChange(limit) {
      this.$emit('limitchange', limit);
    },
    handleCurrentChange(currPage) {
      this.$emit('pagechange', currPage);
    },
  },
};
</script>

<style lang="less">
#admin {
  height: 100%;
}

.search-btn {
  width: 100%;
}
</style>
