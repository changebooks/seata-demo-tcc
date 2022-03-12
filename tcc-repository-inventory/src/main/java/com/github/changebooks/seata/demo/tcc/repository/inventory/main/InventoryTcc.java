package com.github.changebooks.seata.demo.tcc.repository.inventory.main;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * 库存
 *
 * @author 宋欢
 */
@LocalTCC
public interface InventoryTcc {
    /**
     * 出库
     *
     * @param context   BusinessActionContext
     * @param productId 商品id
     * @param num       减的库存
     * @param orderId   订单号
     * @return 成功？
     */
    @TwoPhaseBusinessAction(name = "inventoryTcc")
    boolean prepare(BusinessActionContext context,
                    @BusinessActionContextParameter(paramName = "productId") Integer productId,
                    @BusinessActionContextParameter(paramName = "num") Integer num,
                    @BusinessActionContextParameter(paramName = "orderId") Integer orderId);

    /**
     * 提交，忽略
     *
     * @param context BusinessActionContext
     * @return 成功？
     */
    boolean commit(BusinessActionContext context);

    /**
     * 入库
     *
     * @param context BusinessActionContext
     * @return 成功？
     */
    boolean rollback(BusinessActionContext context);

}
