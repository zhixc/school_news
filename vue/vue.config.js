module.exports = {
  devServer: {
    port: 8080,
    proxy: {  //配置跨域
      '/api': {
        target: 'http://localhost:8081',
        changOrigin: true,  //允许跨域
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
}