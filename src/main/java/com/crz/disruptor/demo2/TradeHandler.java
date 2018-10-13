package com.crz.disruptor.demo2;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * {@link}
 *
 * @author crz
 * @date 2018/10/13 下午2:49
 */

public class TradeHandler implements EventHandler<Trade>, WorkHandler<Trade> {


    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        this.onEvent(event);
    }

    @Override
    public void onEvent(Trade event) throws Exception {
        event.setId(UUID.randomUUID().toString());
        System.out.println(event.toString());
    }
}
