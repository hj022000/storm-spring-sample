package org.apache.storm.hdfs;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
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
import storm.sample.spout.hdfs.HdfsFieldsSpout;

/**
 * Created by wuzhong on 2016/5/9.
 */
public class HdfsTopology {

    public static void main(String[] args) {
        LocalCluster cluster = new LocalCluster();
        Config config1 = new Config();
        config1.setDebug(true);

        RecordFormat format = new DelimitedRecordFormat()
                .withFieldDelimiter(",")
                .withFields(new Fields("id,date,title,content".split(",")));
        SyncPolicy syncPolicy = new CountSyncPolicy(3);

        FileRotationPolicy rotationPolicy = new FileSizeRotationPolicy(5.0F,
                FileSizeRotationPolicy.Units.MB);

        FileNameFormat fileNameFormat = new DefaultFileNameFormat()
                .withPath("/apps/output/wip/");

        MoveFileAction moveFileAction = (new MoveFileAction())
                .toDestination("/apps/output/finished/");

        HdfsBolt hdfsBolt = new HdfsBolt()
                .withFsUrl("hdfs://master.spark.com:8020")
                .withFileNameFormat(fileNameFormat)
                .withRecordFormat(format)
                .withRotationPolicy(rotationPolicy)
                .withSyncPolicy(syncPolicy)
                .addRotationAction(moveFileAction);

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("hdfsFieldsSpout", new HdfsFieldsSpout());
        builder.setBolt("hdfsBolt", hdfsBolt,3)
                .shuffleGrouping("hdfsFieldsSpout");

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
