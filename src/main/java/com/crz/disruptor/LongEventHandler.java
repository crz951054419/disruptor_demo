package com.crz.disruptor;


import com.lmax.disruptor.EventHandler;

import java.util.concurrent.TimeUnit;

/**
 * {@link}
 *
 * @author crz
 * @date 2018/10/13 上午2:47
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    int i = 0;

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println(event.getValue());
//        TimeUnit.SECONDS.sleep(1);
//        System.out.println(i++);
    }
}
