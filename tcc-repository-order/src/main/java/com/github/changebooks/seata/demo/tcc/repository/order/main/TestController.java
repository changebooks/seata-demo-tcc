package com.github.changebooks.seata.demo.tcc.repository.order.main;

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
@RequestMapping("test")
@Validated
@RestController
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private OrderService service;

    @GetMapping(value = "/test")
    public String test() {
        return "order-ok";
    }

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
    @GetMapping(value = "/create-order")
    public boolean createOrder(@RequestParam("id") Integer id,
                               @RequestParam("userId") Integer userId,
                               @RequestParam("productId") Integer productId,
                               @RequestParam("productNum") Integer productNum,
                               @RequestParam("payNum") Integer payNum) {
        LOGGER.info("createOrder trace, id: {}, userId: {}, productId: {}, productNum: {}, payNum: {}", id, userId, productId, productNum, payNum);

        try {
            return service.createOrder(id, userId, productId, productNum, payNum);
        } catch (Throwable tr) {
            LOGGER.error("createOrder failed, id: {}, userId: {}, productId: {}, productNum: {}, payNum: {}, throwable: ", id, userId, productId, productNum, payNum, tr);
            return false;
        }
    }

    /**
     * 修改支付状态
     *
     * @param id        订单号
     * @param payStatus 支付状态，0-未知、1-未支付、2-支付成功、3-支付失败
     * @return 成功？
     */
    @GetMapping(value = "/change-pay")
    public boolean changePay(@RequestParam("id") Integer id, @RequestParam("payStatus") Integer payStatus) {
        LOGGER.info("changePay trace, id: {}, payStatus: {}", id, payStatus);

        try {
            return service.changePay(id, payStatus);
        } catch (Throwable tr) {
            LOGGER.error("changePay failed, id: {}, payStatus: {}, throwable: ", id, payStatus, tr);
            return false;
        }
    }

}
