package com.crz.disruptor.demo3;

import com.crz.disruptor.demo2.Trade;
import com.lmax.disruptor.EventHandler;

import java.util.concurrent.TimeUnit;

/**
 * {@link}
 *
 * @author crz
 * @date 2018/10/13 下午3:52
 */
public class handler2 implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("handler2");
        event.setPrice(0.3d);
        TimeUnit.SECONDS.sleep(1);

    }
}
