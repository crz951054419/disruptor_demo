package com.crz.disruptor.demo2;

import com.lmax.disruptor.*;

import java.util.concurrent.*;

/**
 * {@link}
 *
 * @author crz
 * @date 2018/10/13 下午2:43
 */
public class MainTrade {

    public static void main(String[] args) throws Exception {
        int BUFFER_SIZE = 1024;
        int THREAD_NUM = 4;


        RingBuffer<Trade> ringBuffer = RingBuffer.createSingleProducer(Trade::new, BUFFER_SIZE, new YieldingWaitStrategy());

        ExecutorService executors = Executors.newFixedThreadPool(THREAD_NUM);

        SequenceBarrier seq = ringBuffer.newBarrier();

        BatchEventProcessor<Trade> batch = new BatchEventProcessor<Trade>(ringBuffer, seq, new TradeHandler());

        ringBuffer.addGatingSequences(batch.getSequence());
        executors.submit(batch);

        Future<Void> future = executors.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                long seq;
                for (int i = 0; i < 10; i++) {
                    seq = ringBuffer.next();
                    ringBuffer.get(seq).setPrice(Math.random() * 9999);
                    ringBuffer.publish(seq);
                }

                return null;
            }
        });

        future.get();
        TimeUnit.SECONDS.sleep(1);
        batch.halt();
        executors.shutdown();


    }
}
