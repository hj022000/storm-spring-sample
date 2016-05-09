/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package storm.sample.spout.jdbc;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class JdbcFieldsSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    public static final List<Values> listValues = Lists.newArrayList(
            getValues("1"), getValues("2"), getValues("3"));

    private static Values getValues(String suf) {
        String suffix = System.currentTimeMillis() +"_test_" + suf;
        return new Values(
                Integer.parseInt(suf),
                "USER_LOGIN" + suffix,
                "USER_PASS" + suffix,
                Integer.parseInt(suf));
    }

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void nextTuple() {
        Utils.sleep(1000L);
        final Random rand = new Random();
        final Values values = listValues.get(rand.nextInt(listValues.size()));
        collector.emit(values);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(getOutputFields());
    }

    public Fields getOutputFields() {
        return new Fields("ID","USER_LOGIN","USER_PASS","AGE");
    }

    @Override
    public void close() {
        super.close();
    }
}
