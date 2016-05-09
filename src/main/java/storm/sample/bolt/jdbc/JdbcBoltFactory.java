package storm.sample.bolt.jdbc;

import com.google.common.collect.Maps;
import org.apache.storm.jdbc.bolt.JdbcInsertBolt;
import org.apache.storm.jdbc.common.ConnectionProvider;
import org.apache.storm.jdbc.common.HikariCPConnectionProvider;
import org.apache.storm.jdbc.mapper.JdbcMapper;
import org.apache.storm.jdbc.mapper.SimpleJdbcMapper;
import org.springframework.beans.factory.FactoryBean;

import java.util.Map;

/**
 * Created by wuzhong on 2016/5/9.
 */
public class JdbcBoltFactory implements FactoryBean<JdbcInsertBolt> {
    @Override
    public JdbcInsertBolt getObject() throws Exception {
        Map hikariConfigMap = Maps.newHashMap();
        hikariConfigMap.put("dataSourceClassName","com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikariConfigMap.put("dataSource.url", "jdbc:mysql://10.10.30.200:3306/d_mob?zeroDateTimeBehavior=convertToNull");
        hikariConfigMap.put("dataSource.user","mobtest");
        hikariConfigMap.put("dataSource.password","tuniu520");
        ConnectionProvider connectionProvider = new HikariCPConnectionProvider(hikariConfigMap);

        String tableName = "users";
        JdbcMapper simpleJdbcMapper = new SimpleJdbcMapper(tableName, connectionProvider);

        JdbcInsertBolt userPersistanceBolt = new JdbcInsertBolt(connectionProvider, simpleJdbcMapper)
                .withTableName("users")
                .withQueryTimeoutSecs(30);
//        JdbcInsertBolt userPersistanceBolt = new JdbcInsertBolt(connectionProvider, simpleJdbcMapper)
//                .withInsertQuery("insert into user values (?,?)")
//                .withQueryTimeoutSecs(30);
        return userPersistanceBolt;
    }

    @Override
    public Class<?> getObjectType() {
        return JdbcInsertBolt.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
