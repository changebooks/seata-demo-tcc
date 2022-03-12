package com.github.changebooks.seata.demo.tcc.repository.order.main;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * 订单
 *
 * @author 宋欢
 */
@LocalTCC
public interface OrderTcc {
    /**
     * 创建订单
     *
     * @param context    BusinessActionContext
     * @param id         订单号
     * @param userId     用户id
     * @param productId  商品id
     * @param productNum 商品数
     * @param payNum     支付金额，单位：分
     * @return 成功？
     */
    @TwoPhaseBusinessAction(name = "orderTcc")
    boolean prepare(BusinessActionContext context,
                    @BusinessActionContextParameter(paramName = "id") Integer id,
                    @BusinessActionContextParameter(paramName = "userId") Integer userId,
                    @BusinessActionContextParameter(paramName = "productId") Integer productId,
                    @BusinessActionContextParameter(paramName = "productNum") Integer productNum,
                    @BusinessActionContextParameter(paramName = "payNum") Integer payNum);

    /**
     * 支付成功
     *
     * @param context BusinessActionContext
     * @return 成功？
     */
    boolean commit(BusinessActionContext context);

    /**
     * 支付失败
     *
     * @param context BusinessActionContext
     * @return 成功？
     */
    boolean rollback(BusinessActionContext context);

}
