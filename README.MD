组件化开发

------------------1.作用------------------
1.解决编译速度

2.解耦

3.功能重用

4.便于团队开发


------------------2.解决的问题------------------

1.模块包名问题，要解决资源冲突

2.gradle中版本号统一管理

3.Application，Library之间随意切换

4.AndroidManifest.xml文件根据Application，Library 区分，正式打包去除测试代码以及其它资源文件

5.Library在build.gradle文件中不能有applicationId，根据 isModuleApplication 属性区分
  
6.App壳工程可对业务组件进行配置化；聚合库组件可对库子组件进行配置化


------------------3.加快编译速度------------------
1.本地代码productConfig中只开启一个产品(只有一个产品设置为true)，更改不要提交到git

2.本地代码productConfig中只开启一个渠道(只有一个渠道设置为true)，更改不要提交到git

2.本地代码signEnableConfig中只开启一个签名(只有一个签名设置为true,但是默认好像有debug和release)，更改不要提交到git