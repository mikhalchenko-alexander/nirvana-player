config.module.rules.push(
  { test: /\.styl$/,
    loader: 'style-loader!css-loader!stylus-loader'
  });
