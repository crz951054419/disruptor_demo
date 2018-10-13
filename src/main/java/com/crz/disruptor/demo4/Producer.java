package com.crz.disruptor.demo4;

import com.lmax.disruptor.RingBuffer;

/**
 * {@link}
 *
 * @author crz
 * @date 2018/10/13 下午4:37
 */
public class Producer {
    private RingBuffer<Order> ringBuffer;

    Producer(RingBuffer ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(String uuid) {
        long seq = ringBuffer.next();

        Order o = ringBuffer.get(seq);
        o.setId(uuid);

        ringBuffer.publish(seq);

    }
}
