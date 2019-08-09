Flink项目实战


1. 项目背景
2. 功能描述
3. 项目架构
4. 功能实现





接入的数据类型就是日志
离线：Flume ==> HDFS
实时：Kafka ==> 流处理引擎 ==> ES ==> Kibana




2. 项目功能描述
  1）统计一分钟内每个域名访问产生的流量
  	Flink接收Kafka的进行处理
  2）统计一分钟内每个用户产生的流量
  	域名和用户是有对应关系的
  	Flink接收Kafka的进行 + Flink读取域名和用户的配置数据  进行处理


数据：Mock   ****




3. 项目架构
	详见架构图


4. Mock数据之Kafka生产者代码主流程开发   务必掌握
	数据敏感
	多团队协作，你依赖了其他团队提供的服务和接口

	通过Mock的方式往Kafka的broker里面发送数据

	Java/Scala Code: producer
	kafka控制台消费者: consumer




5. ES 部署
下载：wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.3.0-linux-x86_64.tar.gz
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.3.0-darwin-x86_64.tar.gz
修改配置文件config/elasticsearch.yml: network.host: 0.0.0.0

mac 安装
brew install elasticsearch
brew install kibana

创建索引
	curl -XPUT 'http://127.0.0.1:9200/cdn'


curl -H "Content-Type: application/json" -XPOST 'http://127.0.0.1:9200/cdn/traffic/_mapping?pretty' -d '{
"traffic":{
    "properties":{
        "domain":{"type":"text"},
        "traffics":{"type":"long"},
        "time":{"type":"date","format": "yyyy-MM-dd HH:mm"}
        }
    }
}'

curl -XDELETE '127.0.0.1:9200/cdn'

curl -H "Content-Type: application/json" -XPOST 'http://127.0.0.1:9200/cdn/traffic/_mapping?pretty' -d '{
"traffic":{
	"properties":{
		"domain":{"type":"keyword"},
		"traffics":{"type":"long"},
		"time":{"type":"date","format": "yyyy-MM-dd HH:mm"}
		}
    }
}'






