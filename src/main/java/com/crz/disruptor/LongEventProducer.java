package com.crz.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * {@link}
 *
 * @author crz
 * @date 2018/10/13 上午2:55
 */
public class LongEventProducer {

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer bb) {
        long sequence = ringBuffer.next();
        try {
            LongEvent event = ringBuffer.get(bb.getLong(0));
            event.setValue(sequence);
        } finally {
            ringBuffer.publish(sequence);
        }

    }

}
