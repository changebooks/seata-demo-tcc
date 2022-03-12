package com.github.changebooks.seata.demo.tcc.repository.account.main;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * 预存款
 *
 * @author 宋欢
 */
@LocalTCC
public interface AccountTcc {
    /**
     * 冻结
     *
     * @param context BusinessActionContext
     * @param userId  用户id
     * @param num     冻结的金额，单位：分
     * @param orderId 订单号
     * @return 成功？
     */
    @TwoPhaseBusinessAction(name = "accountTcc")
    boolean prepare(BusinessActionContext context,
                    @BusinessActionContextParameter(paramName = "userId") Integer userId,
                    @BusinessActionContextParameter(paramName = "num") Integer num,
                    @BusinessActionContextParameter(paramName = "orderId") Integer orderId);

    /**
     * 扣已冻
     *
     * @param context BusinessActionContext
     * @return 成功？
     */
    boolean commit(BusinessActionContext context);

    /**
     * 解冻
     *
     * @param context BusinessActionContext
     * @return 成功？
     */
    boolean rollback(BusinessActionContext context);

}
