requirejs.config({
    paths: {
        nirvana: '../build/bundle/nirvanaPlayer.bundle'
    }
});

requirejs(["nirvana"]);
