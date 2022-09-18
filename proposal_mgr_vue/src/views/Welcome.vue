<template>
  <div id="welcome" :style="conTop">
  </div>
</template>
<script>
export default {
  name: "Welcome",
  data() {
    return {
      conTop: {
        backgroundImage: "url(" + require("../img/福光background.jpeg") + ")",
        backgroundRepeat: "no-repeat",
        backgroundPosition: "center center",
        height: "100%",
        width: "100%",
      },
    };
  },
};
</script>
