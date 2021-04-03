组件化开发

------------------1.作用------------------
1.解决编译速度

2.解耦

3.功能重用

4.便于团队开发


------------------2.作用------------------

1.要解决资源冲突和模块包名问题

2.gradle中版本号统一管理

3.Application，Library之间随意切换

4.AndroidManifest.xml文件 Application，Library 区分，去除测试代码以及其它资源文件

5.Library在build.gradle文件中不能有applicationId
  根据 MODULE_AS_APPLICATION 属性区分
  
6.App壳工程配置业务组件
