package net.lizhaoweb.flink.aliyun.mns;

import com.aliyun.mns.model.Message;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.Test;

public class TestALiYunMNSInputFormat {

    @Test
    public void read() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Message> dataResult = env.createInput(
                ALiYunMNSInputFormat
                        .buildALiYunMNSInputFormat()
                        .setAccessKeyId("")
                        .setAccessKeySecret("")
                        .setAccountEndpoint("https://1379506082945137.mns.cn-beijing.aliyuncs.com")
                        .setQueueName("queue-oss-log-box-put-dev")
                        .finish()
        );
        SingleOutputStreamOperator<Object> aaa = dataResult.map(new MapFunction<Message, Object>() {
            @Override
            public Object map(Message value) throws Exception {
                return null;
            }
        });
        aaa.print();
    }
}
