config.resolve.modules.push("stylus");

config.module.rules.push(
  { test: /\.styl$/,
    loader: 'style-loader!css-loader!stylus-loader'
  });

config.module.rules.push(
  { test: /\.css$/,
    loader: 'style-loader!css-loader'
  });
