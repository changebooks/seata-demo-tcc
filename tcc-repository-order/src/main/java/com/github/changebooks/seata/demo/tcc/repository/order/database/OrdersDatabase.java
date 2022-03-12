package com.github.changebooks.seata.demo.tcc.repository.order.database;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 订单
 *
 * @author 宋欢
 */
@Repository
public interface OrdersDatabase {
    /**
     * 查询支付状态
     *
     * @param id 订单号
     * @return 支付状态
     */
    Integer find(@Param("id") int id);

    /**
     * 新增一条记录
     *
     * @param id         订单号
     * @param userId     用户id
     * @param productId  商品id
     * @param productNum 商品数
     * @param payNum     支付金额，单位：分
     * @return 影响行数
     */
    int insert(@Param("id") int id,
               @Param("userId") int userId,
               @Param("productId") int productId,
               @Param("productNum") int productNum,
               @Param("payNum") int payNum);

    /**
     * 修改支付状态
     *
     * @param id        订单号
     * @param payStatus 支付状态，0-未知、1-未支付、2-支付成功、3-支付失败
     * @return 影响行数
     */
    int update(@Param("id") int id, @Param("payStatus") int payStatus);

}
