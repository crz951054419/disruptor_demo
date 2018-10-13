package com.crz.disruptor.demo3;

import com.crz.disruptor.demo2.Trade;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * {@link}
 *
 * @author crz
 * @date 2018/10/13 下午3:55
 */
public class TradePublisher implements Runnable {

    Disruptor<Trade> disruptor;

    CountDownLatch countDownLatch;

    private static int LOOP = 10;

    public TradePublisher(CountDownLatch countDownLatch, Disruptor<Trade> disruptor) {
        this.disruptor = disruptor;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        TradeEventTranslator tradeEventTranslator = new TradeEventTranslator();
        for (int i = 0; i < LOOP; i++) {
            disruptor.publishEvent(tradeEventTranslator);
        }
        countDownLatch.countDown();

    }
}

class TradeEventTranslator implements EventTranslator<Trade> {
    private Random random = new Random();

    @Override
    public void translateTo(Trade event, long sequence) {
        this.generateTrade(event);
    }

    private Trade generateTrade(Trade trade) {
        trade.setPrice(random.nextDouble() * 9999);
        return trade;
    }

}
