package com.github.changebooks.seata.demo.tcc.repository.inventory.database;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 库存流水
 *
 * @author 宋欢
 */
@Repository
public interface InventoryFlowDatabase {
    /**
     * 获取一条流水id
     *
     * @param productId  商品id
     * @param changeType 改变方式，0-未知、1-入库、2-出库
     * @param idempotent 幂等
     * @return 流水id
     */
    Integer find(@Param("productId") int productId,
                 @Param("changeType") int changeType,
                 @Param("idempotent") String idempotent);

    /**
     * 新增一条流水
     *
     * @param productId  商品id
     * @param changeType 改变方式，0-未知、1-入库、2-出库
     * @param stockNum   出入的库存
     * @param orderId    订单号
     * @param idempotent 幂等
     * @return 影响行数
     */
    int insert(@Param("productId") int productId,
               @Param("changeType") int changeType,
               @Param("stockNum") int stockNum,
               @Param("orderId") int orderId,
               @Param("idempotent") String idempotent);
}
