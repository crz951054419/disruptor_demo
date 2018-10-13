package com.crz.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * {@link}
 *
 * @author crz
 * @date 2018/10/13 下午2:24
 */
public class LongEventProducerWithTranslator {


    private static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR = (x, y, z) -> x.setValue(z.getLong(0));
//            new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
//                @Override
//                public void translateTo(LongEvent event, long sequence, ByteBuffer byteBuffer) {
//                    event.setValue(byteBuffer.getLong(0));
//                }
//            };

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer byteBuffer) {
        ringBuffer.publishEvent(TRANSLATOR, byteBuffer);

    }

}
