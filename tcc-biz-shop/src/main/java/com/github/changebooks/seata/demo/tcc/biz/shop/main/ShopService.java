package com.github.changebooks.seata.demo.tcc.biz.shop.main;

/**
 * 业务
 *
 * @author 宋欢
 */
public interface ShopService {
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
    boolean createOrder(Integer orderId, Integer userId, Integer productId, Integer productNum, Integer payNum);

}
