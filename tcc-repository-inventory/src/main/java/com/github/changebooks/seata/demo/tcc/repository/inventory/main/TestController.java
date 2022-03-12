package com.github.changebooks.seata.demo.tcc.repository.inventory.main;

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
    private InventoryService service;

    @GetMapping(value = "/test")
    public String test() {
        return "inventory-ok";
    }

    /**
     * 入库
     *
     * @param productId  商品id
     * @param num        加的库存
     * @param orderId    订单号
     * @param idempotent 幂等
     * @return 成功？
     */
    @GetMapping(value = "/in-stock")
    public boolean inStock(@RequestParam("productId") Integer productId,
                           @RequestParam("num") Integer num,
                           @RequestParam("orderId") Integer orderId,
                           @RequestParam("idempotent") String idempotent) {
        LOGGER.info("inStock trace, productId: {}, num: {}, orderId: {}, idempotent: {}", productId, num, orderId, idempotent);

        try {
            return service.inStock(productId, num, orderId, idempotent);
        } catch (Throwable tr) {
            LOGGER.error("inStock failed, productId: {}, num: {}, orderId: {}, idempotent: {}, throwable: ", productId, num, orderId, idempotent, tr);
            return false;
        }
    }

    /**
     * 出库
     *
     * @param productId  商品id
     * @param num        减的库存
     * @param orderId    订单号
     * @param idempotent 幂等
     * @return 成功？
     */
    @GetMapping(value = "/out-stock")
    public boolean outStock(@RequestParam("productId") Integer productId,
                            @RequestParam("num") Integer num,
                            @RequestParam("orderId") Integer orderId,
                            @RequestParam("idempotent") String idempotent) {
        LOGGER.info("outStock trace, productId: {}, num: {}, orderId: {}, idempotent: {}", productId, num, orderId, idempotent);

        try {
            return service.outStock(productId, num, orderId, idempotent);
        } catch (Throwable tr) {
            LOGGER.error("outStock failed, productId: {}, num: {}, orderId: {}, idempotent: {}, throwable: ", productId, num, orderId, idempotent, tr);
            return false;
        }
    }

}
