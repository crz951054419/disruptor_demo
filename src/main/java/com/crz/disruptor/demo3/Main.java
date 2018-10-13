package com.crz.disruptor.demo3;

import com.crz.disruptor.demo2.Trade;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.*;

/**
 * {@link}
 *
 * @author crz
 * @date 2018/10/13 下午3:46
 */
public class Main {

    public static void main(String[] args) throws Exception {
        long beginTime = System.currentTimeMillis();

        int bufferSize = 1024;

        ExecutorService executor = Executors.newFixedThreadPool(8);

        Disruptor<Trade> disruptor = new Disruptor<Trade>(Trade::new, bufferSize, executor, ProducerType.SINGLE, new BusySpinWaitStrategy());

//
        EventHandlerGroup<Trade> handleGroup = disruptor.handleEventsWith(new Handler1(), new handler2());
        handleGroup.then(new handler3());

//        Handler1 handler1 = new Handler1();
//        handler2 handler2 = new handler2();
//        handler3 handler3 = new handler3();
//        disruptor.handleEventsWith(handler1, handler2);//并行
//
//        disruptor.after(handler1).handleEventsWith(handler3);

        //(1,2)->3


        disruptor.start();

        CountDownLatch latch = new CountDownLatch(1);
        Future<?> future = executor.submit(new TradePublisher(latch, disruptor));
        System.out.println("f:" + future.get());
        latch.await();

        disruptor.shutdown();
        executor.shutdown();
        System.out.println("总耗时" + (System.currentTimeMillis() - beginTime));

    }
}
