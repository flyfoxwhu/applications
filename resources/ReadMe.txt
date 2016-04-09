
前置条件
    确保本机已经安装maven和jdk

部署步骤:

1.复制本系统里里/resources/auto-mini.properties配置文件到本地用户目录下(配置文件里的mini.log.path的值路径记得改成自己的目录)

2.复制本系统里里/resources/mini-item.xml 配置文件到本地用户目录下jetty的item目录下(务必把mini-item.xml里的路径改成自己本机相应目录不然启动不了)

3.运行 build.sh（执行之前务必把该脚本里的路径改成自己本地的路径） 启动环境



ps:
  1.系统  springMVC + mybatis + velocity +mysql

  2.若需要增加新表，在 generate_tables.config 中加表名(一行一个表),然后执行Mian.java中的main方法一键生成mybatis所需要
    的dao,model,mapper等配置文件到mini-dal工程中

  3.所有单表的基本查询以及查询query对象全部自动生成,无需手动编写,涉及复杂sql或多表的查询写在各自对应的扩展ExtMapper.xml和*ExtMpper中
    (mybatis和ibatis语法稍有不同，尤其在动态sql的语法和变量赋值方面请勿混淆)




