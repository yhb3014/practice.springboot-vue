const path = require("path");

module.exports = {
  outputDir: "../backend/src/main/resources/static",
  devServer: {
    port: 7070,
  },
  configureWebpack: {
    resolve: {
      alias: {
        "@": path.join(__dirname, "src/"),
      },
    },
  },
};
