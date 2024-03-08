                                  高频面试题：canal+mq数据一致性
1、什么是canal
canal 是阿里巴巴的一个开源项目，基于java实现，整体已经在很多大型的互联网项目生产环境中使用，包括阿里、美团等都有广泛的应用， 是一个非常成熟的数据库同步方案，
基础的使用只需要进行简单的配置即可。canal是通过模拟成为mysql 的slave的方式， 监听mysql 的binlog日志来获取数据，binlog设置为row模式以后，
不仅能获取到执行的每一个增删改的脚本，同时还能获取到修改前和修改后的数据，基于这个特性，canal就能高性能的获取到mysql数据数据的变更。

2、打开mysql binlog
1）安装mysql
2）查看mysql是否开启binlog，ON：开启，OFF：关闭，如果默认已经开启，忽略这一步，查看是否开启命令如下：
mysql> show variables like 'log_bin';
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| log_bin       | OFF   |
+---------------+-------+
1 row in set (0.00 sec)

如果log_bin关闭，修改MySQL配置开启log_bin，步骤如下：
修改 mysql 的配置文件 my.cnf
vi /etc/my.cnf
追加内容：
log-bin=mysql-bin     #binlog文件名
binlog_format=ROW     #选择row模式
server_id=1           #mysql实例id,不能和canal的slaveId重复

2）重启 mysql：
service mysql restart

3）登录 mysql 客户端，查看 log_bin 变量
mysql> show variables like 'log_bin';
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| log_bin       | ON|
+---------------+-------+
1 row in set (0.00 sec)

3、下载canal（github）
下载地址: https://github.com/alibaba/canal/releases
目前

4、修改canal配置，打开kafka

5、新建一个项目dancechar-canal-data-service

6、消费对应表的kafka消息










