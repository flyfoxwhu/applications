"use strict";



/**
 * 全局配置
 */

// FIS
fis.set("project.md5Connector", "-");
fis.set("project.ignore", fis.get("project.ignore").concat([
  "bower_components/**",
  //"*-INF/**",
  //"velocity/**",
  "bower.json",
  "package.json",
  ".*"
]));

// 项目
fis.set("proj_name", "mini");
fis.set("proj_ver", "1.1");



/**
 * 开发环境配置
 */

fis.set("dev_target", "../../../target/mini-web");

// 终端中执行 fis3 release dev -w
fis
  .media("dev")
  .match("_*.*", {
    release: false
  }, true)
  .match("*.*", {
    deploy: fis.plugin("local-deliver", {
      to: "."
    })
  })
  .match("*.scss", {
    rExt: ".css",
    parser: fis.plugin("node-sass"),
    deploy: [
      fis.plugin("local-deliver", {to: "."}),
      fis.plugin("local-deliver", {to: fis.get("dev_target")})
    ]
  }, true)
  .match("*.{png,jpg,jpeg,gif}", {
    deploy: fis.plugin("local-deliver", {to: fis.get("dev_target")})
  })
  .match("*.{vm,html,xml,properties}", {
    release: false
  });



/**
 * 测试环境配置
 */

// 终端中执行 fis3 release qa
fis
  .media("qa")
  .match("_*.*", {
    release: false
  }, true)
  .match("*.{scss,md}", {
    release: false
  }, true)
  .match("*.{css,js,jpg,png,gif}", {
    useHash: true
  })
  .match("*.css", {
    optimizer: fis.plugin("clean-css", {
      keepSpecialComments: 0
    })
  })
  .match("*.js", {
    optimizer: fis.plugin("uglify-js"),
    postprocessor: fis.plugin("jswrapper")
  })
  .match("*.png", {
    optimizer: fis.plugin("png-compressor")
  })
  .match("**", {
    release: "$0",
    domain: "//haimaiche.com",
    deploy: fis.plugin("local-deliver", {
      to: "../webapp_qa"
    })
  })
  .match("/bower_components/(**)", {
    useHash: false,
    release: "/assets/lib/$1"
  })
  .match("/template/{components,views}/**.js", {
    isMod: true
  })
  .match("/template/components/(**.{css,js,png,jpg,gif})", {
    release: "/assets/${proj_name}/${proj_ver}/c/$1"
  })
  .match("/template/views/(**.{css,js,png,jpg,gif})", {
    release: "/assets/${proj_name}/${proj_ver}/p/$1"
  })
  .match("/template/views/**", {
    useSameNameRequire: true
  });



/**
 * 线上环境配置（七牛）
 */

// 终端中执行 fis3 release prod_qiniu
fis
  .media("prod_qiniu")
  .match("_*.*", {
    release: false
  }, true)
  .match("*.{scss,md}", {
    release: false
  }, true)
  .match("*.{css,js,jpg,png,gif}", {
    useHash: true
  })
  .match("*.css", {
    optimizer: fis.plugin("clean-css", {
      keepSpecialComments: 0
    })
  })
  .match("*.js", {
    optimizer: fis.plugin("uglify-js"),
    postprocessor: fis.plugin("jswrapper")
  })
  .match("*.png", {
    optimizer: fis.plugin("png-compressor")
  })
  .match("**", {
    release: "$0",
    domain: "//img.maihaoche.com",
    deploy: fis.plugin("local-deliver", {
      to: "../webapp_qiniu"
    })
  })
  .match("/bower_components/(**)", {
    useHash: false,
    release: "/lib/$1"
  })
  .match("/template/{components,views}/**.js", {
    isMod: true
  })
  .match("/template/components/(**.{css,js,png,jpg,gif})", {
    release: "/${proj_name}/${proj_ver}/c/$1"
  })
  .match("/template/views/(**.{css,js,png,jpg,gif})", {
    release: "/${proj_name}/${proj_ver}/p/$1"
  })
  .match("/template/views/**", {
    useSameNameRequire: true
  });



/**
 * 线上环境配置（顽兔）
 */

// 终端中执行 fis3 release prod_wantu
fis
  .media("prod_wantu")
  .match("_*.*", {
    release: false
  }, true)
  .match("*.{scss,md}", {
    release: false
  }, true)
  .match("*.{css,js,jpg,png,gif}", {
    useHash: true
  })
  .match("*.css", {
    optimizer: fis.plugin("clean-css", {
      keepSpecialComments: 0
    })
  })
  .match("*.js", {
    optimizer: fis.plugin("uglify-js"),
    postprocessor: fis.plugin("jswrapper")
  })
  .match("*.png", {
    optimizer: fis.plugin("png-compressor")
  })
  .match("**", {
    release: "$0",
    domain: "//img1.maihaoche.com",
    deploy: fis.plugin("local-deliver", {
      to: "../webapp_wantu"
    })
  })
  .match("/bower_components/(**)", {
    useHash: false,
    release: "/lib/$1"
  })
  .match("/template/{components,views}/**.js", {
    isMod: true
  })
  .match("/template/components/(**.{css,js,png,jpg,gif})", {
    release: "/${proj_name}/${proj_ver}/c/$1"
  })
  .match("/template/views/(**.{css,js,png,jpg,gif})", {
    release: "/${proj_name}/${proj_ver}/p/$1"
  })
  .match("/template/views/**", {
    useSameNameRequire: true
  });
