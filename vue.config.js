// vue.config.js
const path = require('path');

module.exports = {
  publicPath: './',
  configureWebpack: {
    resolve: {
      alias: {
        '@assets': path.resolve(__dirname, 'src/assets'),
        '@components': path.resolve(__dirname, 'src/components'),
        '@views': path.resolve(__dirname, 'src/views'),
        '@styles': path.resolve(__dirname, 'src/styles'),
        '@store': path.resolve(__dirname, 'src/store'),
        '@router': path.resolve(__dirname, 'src/router')
      }
    }
  },
  // ⬇️ 新增：让 pdf/doc/docx 用 file-loader 处理
  chainWebpack: config => {
    config.module
      .rule('docs')
      .test(/\.(pdf|docx?|PDF|DOCX?)$/)
      .use('file-loader')
      .loader('file-loader')
      .options({
        name: 'assets/docs/[name].[hash:8].[ext]'
      });
  },
  devServer: { port: 8080 }
};
