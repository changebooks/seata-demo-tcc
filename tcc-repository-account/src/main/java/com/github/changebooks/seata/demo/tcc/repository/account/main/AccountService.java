package com.github.changebooks.seata.demo.tcc.repository.account.main;

/**
 * 预存款
 *
 * @author 宋欢
 */
public interface AccountService {
    /**
     * 冻结
     *
     * @param userId     用户id
     * @param num        冻结的金额，单位：分
     * @param orderId    订单号
     * @param idempotent 幂等
     * @return 成功？
     */
    boolean freeze(Integer userId, Integer num, Integer orderId, String idempotent);

    /**
     * 解冻
     *
     * @param userId     用户id
     * @param num        解冻的金额，单位：分
     * @param orderId    订单号
     * @param idempotent 幂等
     * @return 成功？
     */
    boolean unfreeze(Integer userId, Integer num, Integer orderId, String idempotent);

    /**
     * 扣已冻
     *
     * @param userId     用户id
     * @param num        已冻结的金额，单位：分
     * @param orderId    订单号
     * @param idempotent 幂等
     * @return 成功？
     */
    boolean decreaseFrozen(Integer userId, Integer num, Integer orderId, String idempotent);

    /**
     * 是否冻结过
     *
     * @param userId     用户id
     * @param idempotent 幂等
     * @return 冻结过？
     */
    boolean isFrozen(Integer userId, String idempotent);

}
