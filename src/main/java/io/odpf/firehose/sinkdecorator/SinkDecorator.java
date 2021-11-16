package io.odpf.firehose.sinkdecorator;

import io.odpf.firehose.message.Message;
import io.odpf.firehose.exception.DeserializerException;
import io.odpf.firehose.sink.Sink;

import java.io.IOException;
import java.util.List;

/**
 * Sink decorator provides internal processing on the use provided sink type.
 */
public class SinkDecorator implements Sink {

    private final Sink sink;

    /**
     * Instantiates a new Sink decorator.
     *
     * @param sink wrapped sink object
     */
    public SinkDecorator(Sink sink) {
        this.sink = sink;
    }

    @Override
    public List<Message> pushMessage(List<Message> message) throws IOException, DeserializerException {
        return sink.pushMessage(message);
    }

    @Override
    public void close() throws IOException {
        sink.close();
    }

    @Override
    public void calculateCommittableOffsets() {
        sink.calculateCommittableOffsets();
    }

    @Override
    public boolean canManageOffsets() {
        return sink.canManageOffsets();
    }

    @Override
    public void addOffsetsAndSetCommittable(List<Message> messageList) {
        sink.addOffsetsAndSetCommittable(messageList);
    }
}
