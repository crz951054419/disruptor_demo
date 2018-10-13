package com.crz.disruptor.demo4;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * {@link}
 *
 * @author crz
 * @date 2018/10/13 下午4:30
 */
public class Main {

    public static void main(String[] args) throws Exception {


        RingBuffer<Order> ringBuffer = RingBuffer.create(ProducerType.MULTI, Order::new, 1024 * 1024, new YieldingWaitStrategy());

        SequenceBarrier barriers = ringBuffer.newBarrier();

        Consumer[] consumers = new Consumer[3];
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer("c" + i);
        }
        WorkerPool<Order> workPool = new WorkerPool<Order>(ringBuffer, barriers, new IgnoreExceptionHandler(), consumers);

        ringBuffer.addGatingSequences(workPool.getWorkerSequences());
        workPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
        final CountDownLatch countDownLatch = new CountDownLatch(1);


        for (int i = 0; i < 100; i++) {
            final Producer producer = new Producer(ringBuffer);
            new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i1 = 0; i1 < 100; i1++) {
                    producer.onData(UUID.randomUUID().toString());
                }


            }).start();


        }


    }
}
