var webpack = require('webpack')
var path = require('path')

module.exports = {
  entry: './src/index',
  output: {
    path: __dirname,
    filename: 'bundle.js'
  },
  module: {
    loaders: [
      {
        test: /\.jsx?$/,
        exclude: /node_modules/,
        loader:
          'babel?presets[]=react,presets[]=stage-0,presets[]=es2015',
        include: path.join(__dirname, 'src')
      },
      {
        test: /\.less$/,
        include: path.join(__dirname, 'src', 'style'),
        loader: 'style!css?sourceMap!autoprefixer?browsers=last 2 versions!less?sourceMap=true'
      },
      {
        test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/,
        loader: "url-loader?limit=10000&minetype=application/font-woff"
      },
      {
        test: /\.(ttf|eot|svg)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
        loader: "file-loader"
      }
    ]
  },
  plugins: [
    new webpack.optimize.OccurenceOrderPlugin(),
    new webpack.NoErrorsPlugin()
  ],
  resolve: {
    extensions: ['', '.js', '.jsx', '.json', '.css', '.less']
  },
  node: {
    __dirname: true
  }
}
