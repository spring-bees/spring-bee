
## 自签名证书

生成 PKCS12 证书
```shell script
$ keytool -genkeypair -alias springbee -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore springbee.p12 -storepass 123456 -validity 3650

Enter keystore password:
Re-enter new password:
What is your first and last name?
  [Unknown]:  zhanglei
What is the name of your organizational unit?
  [Unknown]:  springbee
What is the name of your organization?
  [Unknown]:  springbee
What is the name of your City or Locality?
  [Unknown]:
What is the name of your State or Province?
  [Unknown]:
What is the two-letter country code for this unit?
  [Unknown]:
Is CN=yong, OU=mkyong, O=mkyong, L=Unknown, ST=Unknown, C=Unknown correct?
  [no]:  yes
```


## 配置
```properties
server.host=0.0.0.0 # 服务IP地址
server.port=8088 # 服务监听端口
server.secondary.port=0 # 服务次要监听端口，默认无
server.ssl.enabled=false # 启用SSL，默认禁用
server.http2.enabled=true
server.undertow.direct-buffers=true
server.undertow.buffer-size=1024
server.undertow.threads.io=16
server.undertow.threads.worker=256
server.undertow.buffer-pool=1024
```

