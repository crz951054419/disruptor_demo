package com.crz.disruptor.demo4;

import com.lmax.disruptor.WorkHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@link}
 *
 * @author crz
 * @date 2018/10/13 下午4:33
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Consumer implements WorkHandler<Order> {
    private String name;

    @Override
    public void onEvent(Order event) throws Exception {

    }
}
