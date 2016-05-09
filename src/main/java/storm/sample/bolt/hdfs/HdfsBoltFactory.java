package storm.sample.bolt.hdfs;

import backtype.storm.tuple.Fields;
import org.apache.storm.hdfs.bolt.HdfsBolt;
import org.apache.storm.hdfs.bolt.format.DefaultFileNameFormat;
import org.apache.storm.hdfs.bolt.format.DelimitedRecordFormat;
import org.apache.storm.hdfs.bolt.format.FileNameFormat;
import org.apache.storm.hdfs.bolt.format.RecordFormat;
import org.apache.storm.hdfs.bolt.rotation.FileRotationPolicy;
import org.apache.storm.hdfs.bolt.rotation.FileSizeRotationPolicy;
import org.apache.storm.hdfs.bolt.sync.CountSyncPolicy;
import org.apache.storm.hdfs.bolt.sync.SyncPolicy;
import org.apache.storm.hdfs.common.rotation.MoveFileAction;
import org.springframework.beans.factory.FactoryBean;

import java.util.Map;

/**
 * Created by wuzhong on 2016/5/9.
 */
public class HdfsBoltFactory implements FactoryBean<HdfsBolt> {

    private Map<String,Object> config;

    @Override
    public HdfsBolt getObject() throws Exception {
        RecordFormat format = new DelimitedRecordFormat()
                .withFieldDelimiter(config.get("bolt.hdfs.field.delimiter")
                                .toString())
                .withFields(new Fields(config.get("bolt.hdfs.field.names")
                        .toString().split(",")));
        SyncPolicy syncPolicy = new CountSyncPolicy(Integer.parseInt((String)config.get("bolt.hdfs.batch.size")));

        FileRotationPolicy rotationPolicy = new FileSizeRotationPolicy(Float.parseFloat((String)config.get("bolt.hdfs.file.rotation.size.in.mb")),
                FileSizeRotationPolicy.Units.MB);

        FileNameFormat fileNameFormat = new DefaultFileNameFormat()
                .withPath(config.get("bolt.hdfs.wip.file.path").toString());

        MoveFileAction moveFileAction = (new MoveFileAction())
                .toDestination(config.get("bolt.hdfs.finished.file.path").toString());

        HdfsBolt hdfsBolt = new HdfsBolt()
                .withFsUrl(config.get("bolt.hdfs.file.system.url").toString())
                .withFileNameFormat(fileNameFormat)
                .withRecordFormat(format)
                .withRotationPolicy(rotationPolicy)
                .withSyncPolicy(syncPolicy)
                .addRotationAction(moveFileAction);

        return hdfsBolt;
    }

    @Override
    public Class<?> getObjectType() {
        return HdfsBolt.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public Map<String, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }
}
