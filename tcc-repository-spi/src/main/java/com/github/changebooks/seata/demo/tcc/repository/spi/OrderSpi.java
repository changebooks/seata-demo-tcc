package com.github.changebooks.seata.demo.tcc.repository.spi;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 订单
 *
 * @author 宋欢
 */
@RequestMapping("order")
public interface OrderSpi {
    /**
     * 创建订单
     *
     * @param id         订单号
     * @param userId     用户id
     * @param productId  商品id
     * @param productNum 商品数
     * @param payNum     支付金额，单位：分
     * @return 成功？
     */
    @PostMapping(value = "/create-order")
    boolean createOrder(@RequestParam("id") Integer id,
                        @RequestParam("userId") Integer userId,
                        @RequestParam("productId") Integer productId,
                        @RequestParam("productNum") Integer productNum,
                        @RequestParam("payNum") Integer payNum);

}
