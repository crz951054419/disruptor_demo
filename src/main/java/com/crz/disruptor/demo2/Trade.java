package com.crz.disruptor.demo2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link}
 *
 * @author crz
 * @date 2018/10/13 下午2:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trade {

    private String id;

    private String name;

    private double price;

    private AtomicInteger count = new AtomicInteger(0);
}
