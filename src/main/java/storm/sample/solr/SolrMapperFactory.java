package storm.sample.solr;

import org.apache.storm.solr.mapper.SolrFieldsMapper;
import org.apache.storm.solr.mapper.SolrMapper;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by wuzhong on 2016/5/7.
 */
public class SolrMapperFactory implements FactoryBean<SolrMapper> {

    private final SolrFieldsMapper.Builder builder;

    public SolrMapperFactory(SolrFieldsMapper.Builder builder) {
        this.builder = builder;
    }

    @Override
    public SolrMapper getObject() throws Exception {
        return this.builder.build();
    }

    @Override
    public Class<?> getObjectType() {
        return SolrMapper.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
