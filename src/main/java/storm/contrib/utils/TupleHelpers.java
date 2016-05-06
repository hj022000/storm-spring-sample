/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package storm.contrib.utils;

import backtype.storm.Config;
import backtype.storm.Constants;
import backtype.storm.tuple.Tuple;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class TupleHelpers {

    private TupleHelpers() {
    }

    public static boolean isTickTuple(Tuple tuple) {
        return tuple != null
                && Constants.SYSTEM_COMPONENT_ID.equals(tuple.getSourceComponent())
                && Constants.SYSTEM_TICK_STREAM_ID.equals(tuple.getSourceStreamId());
    }

    public static boolean isSource(Tuple tuple, String componentId, String streamId) {
        if (componentId == null || componentId.isEmpty()) {
            return false;
        }
        return tuple.getSourceComponent().equals(componentId)
                && (
                streamId == null
                        || streamId.isEmpty()
                        || tuple.getSourceStreamId().equals(streamId));
    }

    public static <T> int listHashCode(List<T> alist) {
        if (alist == null) {
            return 1;
        } else {
            return Arrays.deepHashCode(alist.toArray());
        }
    }

    public static Map<String, Object> putTickFrequencyIntoComponentConfig(Map<String, Object> conf, int tickFreqSecs) {
        if (conf == null) {
            conf = new Config();
        }

        if (tickFreqSecs > 0) {
            conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, tickFreqSecs);
        }

        return conf;
    }
}
