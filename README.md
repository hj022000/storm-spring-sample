storm-spring-sample
===================

A sample of a Storm topology wired in Spring XML


storm jar storm-spring-sample-jar-with-dependencies.jar storm.contrib.spring.topology.TopologySubmitter spring/spring-context.xml exclamationTopologySubmission
storm jar storm.jar storm.starter.WordCountTopology WordCount -c nimbus.host=localhost
storm jar storm-spring-sample-1.0-job.jar storm.contrib.spring.topology.TopologySubmitter spring/spring-context.xml -c nimbus.host=localhost


storm jar storm-spring-sample-1.0-job.jar spring-context.xml -c nimbus.host=localhost


查看日志：apache-storm-0.9.3\logs\worker-*.log