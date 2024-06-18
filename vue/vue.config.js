const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  // transpileDependencies: true
  devServer: {
    port: 8080
  },
  chainWebpack: config => {
    config.plugin('html').tap(args => {
      args[0].title = 'SSM项目'
      return args
    })
  }
})
