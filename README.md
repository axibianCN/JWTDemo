#JWT DEMO
这是一个快速的JWT Demo的实现，支持签名，加密的功能，仅需修改密钥即可快速上手。
##

## 功能

- 特性1: 对JWT的实现。
- 特性2: 支持签名和验签的基本实现，用户可根据需要自定义。

## 运行

本项目是基于JAVA的Spring Boot项目。
JDK17+
Spring Boot 3.5.3
Maven 3.10
```bash
git clone https://github.com/axibianCN/JWTDemo.git
cd JWTDemo
```

##说明
1.启动JWTDemoApplication后，默认项目部署在本地服务器localhost:8080
2.访问接口/access_Token/{con},con为路径参数，1表示使用正确的预设Token测试，其余值会预设一个错误的Token进行验证签名。返回值为true或者false；
3.修改JWTutil函数，添加支持的签名和加密算法。
4.KeyGenerator函数用于生成一对公私钥，私钥请保存在服务器当中。本项目公私钥均以提供。
