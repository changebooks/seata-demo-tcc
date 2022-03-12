package com.github.changebooks.seata.demo.tcc.repository.account.main;

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
    private AccountService service;

    @GetMapping(value = "/test")
    public String test() {
        return "account-ok";
    }

    /**
     * 冻结
     *
     * @param userId     用户id
     * @param num        冻结的金额，单位：分
     * @param orderId    订单号
     * @param idempotent 幂等
     * @return 成功？
     */
    @GetMapping(value = "/freeze")
    public boolean freeze(@RequestParam("userId") Integer userId,
                          @RequestParam("num") Integer num,
                          @RequestParam("orderId") Integer orderId,
                          @RequestParam("idempotent") String idempotent) {
        LOGGER.info("freeze trace, userId: {}, num: {}, orderId: {}, idempotent: {}", userId, num, orderId, idempotent);

        try {
            return service.freeze(userId, num, orderId, idempotent);
        } catch (Throwable tr) {
            LOGGER.error("freeze failed, userId: {}, num: {}, orderId: {}, idempotent: {}, throwable: ", userId, num, orderId, idempotent, tr);
            return false;
        }
    }

    /**
     * 解冻
     *
     * @param userId     用户id
     * @param num        解冻的金额，单位：分
     * @param orderId    订单号
     * @param idempotent 幂等
     * @return 成功？
     */
    @GetMapping(value = "/unfreeze")
    public boolean unfreeze(@RequestParam("userId") Integer userId,
                            @RequestParam("num") Integer num,
                            @RequestParam("orderId") Integer orderId,
                            @RequestParam("idempotent") String idempotent) {
        LOGGER.info("unfreeze trace, userId: {}, num: {}, orderId: {}, idempotent: {}", userId, num, orderId, idempotent);

        try {
            return service.unfreeze(userId, num, orderId, idempotent);
        } catch (Throwable tr) {
            LOGGER.error("unfreeze failed, userId: {}, num: {}, orderId: {}, idempotent: {}, throwable: ", userId, num, orderId, idempotent, tr);
            return false;
        }
    }

    /**
     * 扣已冻
     *
     * @param userId     用户id
     * @param num        已冻结的金额，单位：分
     * @param orderId    订单号
     * @param idempotent 幂等
     * @return 成功？
     */
    @GetMapping(value = "/decrease-frozen")
    public boolean decreaseFrozen(@RequestParam("userId") Integer userId,
                                  @RequestParam("num") Integer num,
                                  @RequestParam("orderId") Integer orderId,
                                  @RequestParam("idempotent") String idempotent) {
        LOGGER.info("decreaseFrozen trace, userId: {}, num: {}, orderId: {}, idempotent: {}", userId, num, orderId, idempotent);

        try {
            return service.decreaseFrozen(userId, num, orderId, idempotent);
        } catch (Throwable tr) {
            LOGGER.error("decreaseFrozen failed, userId: {}, num: {}, orderId: {}, idempotent: {}, throwable: ", userId, num, orderId, idempotent, tr);
            return false;
        }
    }

}
