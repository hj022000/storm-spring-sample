package org.apache.storm.jdbc;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import org.apache.storm.jdbc.bolt.JdbcInsertBolt;
import org.apache.storm.jdbc.common.ConnectionProvider;
import org.apache.storm.jdbc.common.HikariCPConnectionProvider;
import org.apache.storm.jdbc.mapper.JdbcMapper;
import org.apache.storm.jdbc.mapper.SimpleJdbcMapper;
import storm.sample.spout.jdbc.JdbcFieldsSpout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuzhong on 2016/5/10.
 */
public class JdbcTopology {

    private static Map hikariConfigMap = new HashMap(){{
        put("dataSourceClassName","com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        put("dataSource.url", "jdbc:mysql://10.10.30.200:3306/d_mob?zeroDateTimeBehavior=convertToNull");
        put("dataSource.user","mobtest");
        put("dataSource.password","tuniu520");
    }};

    private static ConnectionProvider connectionProvider = new HikariCPConnectionProvider(hikariConfigMap);

    public static void main(String[] args) {
        LocalCluster cluster = new LocalCluster();
        Config config1 = new Config();
        config1.setDebug(true);


        String tableName = "users";
        JdbcMapper simpleJdbcMapper = new SimpleJdbcMapper(tableName, connectionProvider);

        JdbcInsertBolt userPersistanceBolt = new JdbcInsertBolt(connectionProvider, simpleJdbcMapper)
                .withTableName("users")
                .withQueryTimeoutSecs(30);

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("jdbcFieldsSpout", new JdbcFieldsSpout());
        builder.setBolt("jdbcBolt", userPersistanceBolt,3)
                .shuffleGrouping("jdbcFieldsSpout");

        cluster.submitTopology("test", config1, builder.createTopology());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Killing topology per client's request");
        cluster.killTopology("test");
        cluster.shutdown();
    }
}
