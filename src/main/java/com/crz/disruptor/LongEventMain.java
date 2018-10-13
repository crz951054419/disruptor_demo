package com.crz.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * {@link}
 *
 * @author crz
 * @date 2018/10/13 上午2:25
 */
public class LongEventMain {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        ExecutorService executor1 = Executors.newFixedThreadPool(10);

        LongEventFactory longEventFactory = new LongEventFactory();

        int ringBufferSize = 1024 * 1024;

        /**
         *
         */
        WaitStrategy wait = new BlockingWaitStrategy();
        WaitStrategy sleep = new SleepingWaitStrategy();
        WaitStrategy yield = new YieldingWaitStrategy();

        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(longEventFactory, ringBufferSize, executor, ProducerType.SINGLE, new YieldingWaitStrategy());
        //连接消费事件的方法 handle 消费者
        disruptor.handleEventsWith(new LongEventHandler());

        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        for (long l = 0; l < 10000; l++) {
            byteBuffer.putLong(0, l);
            executor1.execute(() -> producer.onData(byteBuffer));
        }

        disruptor.shutdown();
        executor.shutdown();


    }


}
