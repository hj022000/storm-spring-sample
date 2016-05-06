storm-spring-sample
===================

A sample of a Storm topology wired in Spring XML
启动NIMBUS
./storm nimbus &
启动SUPERVISOR
./storm supervisor &
启动UI
./storm ui &

控制台的地址：http://master.spark.com:8080/index.html

一、在linux下面执行：
1、下面这个命令执行服务器和172.30.39.110不是一个服务器，可以执行成功
./storm jar storm-spring-sample-1.0-job.jar storm.contrib.spring.topology.TopologySubmitter spring/spring-context.xml exclamationTopologySubmission -c nimbus.host=172.30.39.110
2、构建时<mainClass>storm.contrib.spring.topology.TopologySubmitter</mainClass>已经设置了，但是必须命令中依然要指定mainClass
./storm jar storm-spring-sample-1.0-job.jar spring/spring-context.xml exclamationTopologySubmission -c nimbus.host=172.30.39.110
报错：
Error: Could not find or load main class spring.spring-context.xml


二、在windows下面执行：
1、storm.cmd jar storm-spring-sample-1.0-job.jar storm.contrib.spring.topology.TopologySubmitter spring/spring-context.xml exclamationTopologySubmission -c nimbus.host=master.spark.com
报错：
D:\resources\study\storm\apache-storm-0.9.3\bin>Exception in thread "main" org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named '-c' is defined
        at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanDefinition(DefaultListableBeanFactory.java:687)
        at org.springframework.beans.factory.support.AbstractBeanFactory.getMergedLocalBeanDefinition(AbstractBeanFactory.java:1168)
        at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:281)
        at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:194)
        at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:956)
        at storm.contrib.spring.topology.TopologySubmitter.main(TopologySubmitter.java:41)
2、storm.cmd jar storm-spring-sample-1.0-job.jar storm.contrib.spring.topology.TopologySubmitter spring/spring-context.xml exclamationTopologySubmission
在storm.contrib.spring.topology.TopologySubmitter加入如下代码：
        topologySubmission.getConfig().put(Config.NIMBUS_HOST, "master.spark.com");
        topologySubmission.getConfig().put(Config.NIMBUS_THRIFT_PORT, 6627);

        topologySubmission.getConfig().put(Config.STORM_ZOOKEEPER_PORT, 2181);
        List<String> zkServers = new ArrayList<String>();
        zkServers.add("master.spark.com");
        topologySubmission.getConfig().put(Config.STORM_ZOOKEEPER_SERVERS, zkServers);
报错：
D:\resources\study\storm\apache-storm-0.9.3\bin>Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 1
        at storm.contrib.spring.topology.TopologySubmitter.validateArgs(TopologySubmitter.java:27)
        at storm.contrib.spring.topology.TopologySubmitter.main(TopologySubmitter.java:39)

3、storm.cmd jar storm-spring-sample-1.0-job.jar storm.contrib.spring.topology.TopologySubmitter spring/spring-context.xml
在storm.contrib.spring.topology.TopologySubmitter加入如下代码：
        topologySubmission.getConfig().put(Config.NIMBUS_HOST, "master.spark.com");
        topologySubmission.getConfig().put(Config.NIMBUS_THRIFT_PORT, 6627);

        topologySubmission.getConfig().put(Config.STORM_ZOOKEEPER_PORT, 2181);
        List<String> zkServers = new ArrayList<String>();
        zkServers.add("master.spark.com");
        topologySubmission.getConfig().put(Config.STORM_ZOOKEEPER_SERVERS, zkServers);
另一个参数写死在代码中
执行正常。

总结：可见windows下的storm.cmd有问题，发布storm最好在linux环境中进行。



三、查看日志：apache-storm-0.9.3\logs\worker-*.log


scp apache-storm-0.9.3.tar.gz  root@192.168.120.129:/home/wuzhong/apache-storm-0.9.3.tar.gz

scp storm-spring-sample-1.0-job.jar  root@192.168.120.129:/home/wuzhong/apache-storm-0.9.3/bin/storm-spring-sample-1.0-job.jar

org.apache.http.client.HttpClient
ava.lang.NoClassDefFoundError: org/apache/http/client/HttpClient at storm.contrib.solr.SolrBolt.prepare(SolrBolt.java:41) at backtype.storm.daemon.executor$fn__3441$fn__3453.invoke(executor.clj:692

