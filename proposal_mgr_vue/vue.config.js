const webpack = require("webpack");
module.exports = {
  configureWebpack: {
    plugins: [
      new webpack.ProvidePlugin({
        $: "jquery",
        jQuery: "jquery",
        "windows.jQuery": "jquery",
      }),
    ],
  },
  // 告诉系统我的项目应该如何访问
  publicPath: process.env.NODE_ENV === "production" ? "./" : "/",
}