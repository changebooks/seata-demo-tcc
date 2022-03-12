package com.github.changebooks.seata.demo.tcc.repository.inventory.main;

/**
 * 库存
 *
 * @author 宋欢
 */
public interface InventoryService {
    /**
     * 入库
     *
     * @param productId  商品id
     * @param num        加的库存
     * @param orderId    订单号
     * @param idempotent 幂等
     * @return 成功？
     */
    boolean inStock(Integer productId, Integer num, Integer orderId, String idempotent);

    /**
     * 出库
     *
     * @param productId  商品id
     * @param num        减的库存
     * @param orderId    订单号
     * @param idempotent 幂等
     * @return 成功？
     */
    boolean outStock(Integer productId, Integer num, Integer orderId, String idempotent);

    /**
     * 是否出过库
     *
     * @param productId  商品id
     * @param idempotent 幂等
     * @return 已出库？
     */
    boolean isOutStock(Integer productId, String idempotent);

}
