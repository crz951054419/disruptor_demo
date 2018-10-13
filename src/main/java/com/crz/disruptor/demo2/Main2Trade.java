package com.crz.disruptor.demo2;

import com.lmax.disruptor.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * {@link}
 *
 * @author crz
 * @date 2018/10/13 下午3:13
 */
public class Main2Trade {

    public static void main(String[] args) throws Exception {


        int BUFFER_SIZE = 1024;
        int THREAD_NUM = 4;


        RingBuffer<Trade> ringBuffer = RingBuffer.createSingleProducer(Trade::new, BUFFER_SIZE, new YieldingWaitStrategy());

        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        ExecutorService executors = Executors.newFixedThreadPool(THREAD_NUM);

        WorkHandler<Trade> handler = new TradeHandler();

        WorkerPool<Trade> workerPool = new WorkerPool<Trade>(ringBuffer, sequenceBarrier, new IgnoreExceptionHandler(), handler);


        workerPool.start(executors);


        for (int i = 0; i < 8; i++) {
            long seq = ringBuffer.next();
            ringBuffer.get(seq).setPrice(Math.random() * 9999);
            ringBuffer.publish(seq);
        }
        TimeUnit.SECONDS.sleep(1);
        workerPool.halt();
        executors.shutdown();

    }
}
