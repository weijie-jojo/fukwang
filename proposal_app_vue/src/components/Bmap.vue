<template>
  <div id="bmap">
    <baidu-map class="map" center="福州市" @click="clickEvent">
      <bm-navigation anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-navigation>
      <bm-geolocation
        anchor="BMAP_ANCHOR_BOTTOM_RIGHT"
        :showAddressBar="true"
        :autoLocation="true"
      ></bm-geolocation>
      <bm-view class="map"></bm-view>
      <bm-local-search
        :keyword="keyword"
        :location="location"
        :selectFirstResult="true"
        :autoViewport="true"
        :panel="false"
      ></bm-local-search>
    </baidu-map>
  </div>
</template>
<script lang="ts">
export default {
  name: "Bmap",
  props:["keyword"],
  data() {
    return {
      location: '北京',
    };
  },
  methods: {
    handler({ BMap, map }) {
      console.log(BMap, map);
      //这里是北京天安门
      this.center.lng = 116.404;
      this.center.lat = 39.915;
      this.zoom = 15;
    },
    clickEvent(e) {
      console.log(e.point);
    },
  },
};
</script>
<style>
/* 地图容器必须设置宽和高属性 */
.map {
  width: 400px;
  height: 300px;
}
</style>