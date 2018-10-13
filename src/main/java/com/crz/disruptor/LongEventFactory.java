package com.crz.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * {@link}
 *
 * @author crz
 * @date 2018/10/13 上午2:23
 */
public class LongEventFactory implements EventFactory {
    @Override
    public Object newInstance() {
        return new LongEvent();
    }
}
