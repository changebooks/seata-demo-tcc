package com.github.changebooks.seata.demo.tcc.biz.shop.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 宋欢
 */
@RequestMapping("shop")
@Validated
@RestController
public class ShopController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopController.class);

    @Autowired
    private ShopService service;

    /**
     * 创建订单
     *
     * @param orderId    订单号
     * @param userId     用户id
     * @param productId  商品id
     * @param productNum 商品数
     * @param payNum     支付金额，单位：分
     * @return 成功？
     */
    @GetMapping(value = "/create-order")
    public boolean createOrder(@RequestParam("orderId") Integer orderId,
                               @RequestParam("userId") Integer userId,
                               @RequestParam("productId") Integer productId,
                               @RequestParam("productNum") Integer productNum,
                               @RequestParam("payNum") Integer payNum) {
        try {
            return service.createOrder(orderId, userId, productId, productNum, payNum);
        } catch (Throwable tr) {
            return false;
        }
    }

}
