var webpack = require('webpack')
var ExtractTextPlugin = require('extract-text-webpack-plugin')
var _ = require('lodash')
var defaultConfig = require('./webpack.config.js')

module.exports = _.merge({}, defaultConfig, {
  devtool: 'cheap-module-eval-source-map',
  plugins: defaultConfig.plugins.concat([
  new webpack.HotModuleReplacementPlugin(),
  new ExtractTextPlugin('bundle.css'),
  new webpack.IgnorePlugin(/^\.\/locale$/, /moment$/),
    new webpack.DefinePlugin({
      'process.env': {
        'NODE_ENV': JSON.stringify('development')
      },
      'global.GENTLY': false
    })
  ]),
  devServer: {
    historyApiFallback: true,
    stats: {
      colors: true
    }
  }
})
