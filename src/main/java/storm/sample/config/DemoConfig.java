package storm.sample.config;

import backtype.storm.Config;

import java.util.Map;

/**
 * Created by wuzhong on 2016/5/9.
 */
public class DemoConfig extends Config {

    public DemoConfig(Map<String, Object> configs) {
        super.putAll(configs);
    }

}
