package storm.sample.bolt.jdbc;

import org.apache.storm.jdbc.bolt.JdbcInsertBolt;
import org.apache.storm.jdbc.bolt.JdbcInsertBoltSingleton;
import org.springframework.beans.factory.FactoryBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuzhong on 2016/5/9.
 */
public class JdbcBoltFactory implements FactoryBean<JdbcInsertBoltSingleton>,Serializable {

    private static final long serialVersionUID = -5686230406414221605L;

    @Override
    public JdbcInsertBoltSingleton getObject() throws Exception {
        Map hikariConfigMap = new HashMap(){{
            put("dataSourceClassName","com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
            put("dataSource.url", "jdbc:mysql://10.10.30.200:3306/d_mob?zeroDateTimeBehavior=convertToNull");
            put("dataSource.user","mobtest");
            put("dataSource.password","tuniu520");
        }};
        JdbcInsertBoltSingleton userPersistanceBolt = new JdbcInsertBoltSingleton()
                .withTableName("users")
                .withMapperTableName("users")
                .withHikariConfigMap(hikariConfigMap)
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
        return true;
    }
}
