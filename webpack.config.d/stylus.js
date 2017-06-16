config.resolve.modules.push("/Users/aam/Documents/idea_projects/home_nirvana/nirvana-player/stylus");

config.module.rules.push(
  { test: /\.styl$/,
    loader: 'style-loader!css-loader!stylus-loader'
  });

config.module.rules.push(
  { test: /\.css$/,
    loader: 'style-loader!css-loader'
  });
