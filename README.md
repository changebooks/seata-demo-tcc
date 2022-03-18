# Seata Demo TCC
## 分布式事务示例 TCC模式

### eureka
```
1、安装 eureka（略）
2、配置 eureka = http://127.0.0.1:8761/eureka/
3、启动 eureka（略）
```

### MySQL
```
1、安装 MySQL（略）
2、配置 ip = 127.0.0.1:3306, username = root, password = 123456
3、建库：seata_server，脚本：seata-demo-tcc/script/seata_server.sql
4、建库：seata_demo，脚本：seata-tcc-demo/script/seata_demo.sql
```

### 启动 seata-server
```
1、下载：https://github.com/seata/seata/releases
2、解压：seata-server-1.4.2
3、拷贝：seata-demo-tcc/script/file.conf 替换 seata-server-1.4.2/conf/file.conf
4、拷贝：seata-demo-tcc/script/registry.conf 替换 seata-server-1.4.2/conf/registry.conf
5、启动：seata-server-1.4.2/bin/seata-server.bat 或 seata-server-1.4.2/bin/seata-server.sh
```

### 启动 seata-demo-tcc
```
1、启动
repository account - AccountApplication
repository inventory - InventoryApplication
repository order - OrderApplication
biz shop - ShopApplication

2、测试启动
repository account - http://127.0.0.1:8081/test/test
repository inventory - http://127.0.0.1:8082/test/test
repository order - http://127.0.0.1:8083/test/test
biz shop - http://127.0.0.1:8080/test/test
```

### 全局事务
```
http://127.0.0.1:8080/shop/create-order?orderId=1&userId=1&productId=1&productNum=10&payNum=10
```
