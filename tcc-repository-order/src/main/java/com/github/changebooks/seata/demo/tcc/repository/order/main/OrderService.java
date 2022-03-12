package com.github.changebooks.seata.demo.tcc.repository.order.main;

/**
 * 订单
 *
 * @author 宋欢
 */
public interface OrderService {
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
    boolean createOrder(Integer id, Integer userId, Integer productId, Integer productNum, Integer payNum);

    /**
     * 修改支付状态
     *
     * @param id        订单号
     * @param payStatus 支付状态，0-未知、1-未支付、2-支付成功、3-支付失败
     * @return 成功？
     */
    boolean changePay(Integer id, Integer payStatus);

    /**
     * 是否存在订单
     *
     * @param id 订单号
     * @return 存在订单？
     */
    boolean hasOrder(Integer id);

}
