package net.lizhaoweb.flink.aliyun.mns;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import org.apache.flink.annotation.VisibleForTesting;
import org.apache.flink.api.common.io.DefaultInputSplitAssigner;
import org.apache.flink.api.common.io.RichInputFormat;
import org.apache.flink.api.common.io.statistics.BaseStatistics;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.java.typeutils.GenericTypeInfo;
import org.apache.flink.api.java.typeutils.ResultTypeQueryable;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.io.GenericInputSplit;
import org.apache.flink.core.io.InputSplit;
import org.apache.flink.core.io.InputSplitAssigner;
import org.apache.flink.types.Row;
import org.apache.flink.util.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class ALiYunMNSInputFormat extends RichInputFormat<Message, InputSplit> implements ResultTypeQueryable<Row> {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(ALiYunMNSInputFormat.class);

    private String accessKeyId;
    private String accessKeySecret;
    private String accountEndpoint;
    private String queueName;

    private transient CloudAccount account;
    private transient MNSClient client;
    private transient CloudQueue queue;

    private int batchSize;
    private int waitSeconds;

    private transient List<Message> messageList;
    private int readIndex;

    private GenericTypeInfo<Message> typeInformation;

    public ALiYunMNSInputFormat() {
    }

    @Override
    public RowTypeInfo getProducedType() {
        return new RowTypeInfo(
                BasicTypeInfo.STRING_TYPE_INFO,
                BasicTypeInfo.STRING_TYPE_INFO,
                BasicTypeInfo.STRING_TYPE_INFO
        );
    }

//    @Override
//    public GenericTypeInfo<Message> getProducedType() {
//        return new GenericTypeInfo<Message>(Message.class);
//    }

    @Override
    public void configure(Configuration parameters) {
        //do nothing here
    }

    @Override
    public void openInputFormat() {
        account = new CloudAccount(accessKeyId, accessKeySecret, accountEndpoint);
        client = account.getMNSClient();
        queue = client.getQueueRef(queueName);
    }

    @Override
    public void closeInputFormat() {
//        try{
//            if (queue!=null){
//                queue.cl
//            }
//        }finally {
//            queue=null;
//        }
        try {
            if (client != null) {
                client.close();
            }
        } finally {
            client = null;
        }
//        try{
//            if (account!=null){
//                account.cl
//            }
//        }finally {
//            account=null;
//        }
    }

    @Override
    public void open(InputSplit inputSplit) throws IOException {
        messageList = queue.batchPopMessage(batchSize, waitSeconds);
        readIndex = 0;
    }

    /**
     * Closes all resources used.
     *
     * @throws IOException Indicates that a resource could not be closed.
     */
    @Override
    public void close() throws IOException {
        if (messageList == null) {
            return;
        }
        messageList.clear();
    }

    /**
     * Checks whether all data has been read.
     *
     * @return boolean value indication whether all data has been read.
     * @throws IOException
     */
    @Override
    public boolean reachedEnd() throws IOException {
        return readIndex >= messageList.size() - 1;
    }

    /**
     * Stores the next resultSet row in a tuple.
     *
     * @param message row to be reused.
     * @return row containing next {@link Row}
     * @throws java.io.IOException
     */
    @Override
    public Message nextRecord(Message message) throws IOException {
        if (readIndex >= messageList.size() - 1) {
            return null;
        }
        message = messageList.get(readIndex);
        readIndex++;
        return message;
    }

    @Override
    public BaseStatistics getStatistics(BaseStatistics cachedStatistics) throws IOException {
        return cachedStatistics;
    }

    @Override
    public InputSplit[] createInputSplits(int minNumSplits) throws IOException {
        return new GenericInputSplit[]{new GenericInputSplit(0, 1)};
    }

    @Override
    public InputSplitAssigner getInputSplitAssigner(InputSplit[] inputSplits) {
        return new DefaultInputSplitAssigner(inputSplits);
    }

    @VisibleForTesting
    CloudQueue getQueue() {
        return queue;
    }

    @VisibleForTesting
    MNSClient getClient() {
        return client;
    }

    @VisibleForTesting
    CloudAccount getAccount() {
        return account;
    }

    /**
     * A builder used to set parameters to the output format's configuration in a fluent way.
     *
     * @return builder
     */
    public static ALiYunMNSInputFormatBuilder buildALiYunMNSInputFormat() {
        return new ALiYunMNSInputFormatBuilder();
    }

    /**
     * Builder for a {@link ALiYunMNSInputFormat}.
     */
    public static class ALiYunMNSInputFormatBuilder {
        private final ALiYunMNSInputFormat format;

        public ALiYunMNSInputFormatBuilder() {
            this.format = new ALiYunMNSInputFormat();
            //using TYPE_FORWARD_ONLY for high performance reads
            this.format.batchSize = 50;
            this.format.waitSeconds = 10;
        }

        public ALiYunMNSInputFormatBuilder setAccessKeyId(String accessKeyId) {
            format.accessKeyId = accessKeyId;
            return this;
        }

        public ALiYunMNSInputFormatBuilder setAccessKeySecret(String accessKeySecret) {
            format.accessKeySecret = accessKeySecret;
            return this;
        }

        public ALiYunMNSInputFormatBuilder setAccountEndpoint(String accountEndpoint) {
            format.accountEndpoint = accountEndpoint;
            return this;
        }

        public ALiYunMNSInputFormatBuilder setQueueName(String queueName) {
            format.queueName = queueName;
            return this;
        }

        public ALiYunMNSInputFormatBuilder setBatchSize(int batchSize) {
            Preconditions.checkArgument(batchSize > 0, "Illegal value %s for batchSize, has to be positive.", batchSize);
            format.batchSize = batchSize;
            return this;
        }

        public ALiYunMNSInputFormatBuilder setWaitSeconds(int waitSeconds) {
            Preconditions.checkArgument(waitSeconds > 0, "Illegal value %s for waitSeconds, has to be positive.", waitSeconds);
            format.waitSeconds = waitSeconds;
            return this;
        }

        public ALiYunMNSInputFormat finish() {
            if (format.accessKeyId == null) {
                LOG.info("AccessKeyId was not supplied separately.");
            }
            if (format.accessKeySecret == null) {
                LOG.info("AccessKeySecret was not supplied separately.");
            }
            if (format.accountEndpoint == null) {
                throw new IllegalArgumentException("No endpoint URL supplied");
            }
            if (format.queueName == null) {
                throw new IllegalArgumentException("No query supplied");
            }
            return format;
        }

    }

}
