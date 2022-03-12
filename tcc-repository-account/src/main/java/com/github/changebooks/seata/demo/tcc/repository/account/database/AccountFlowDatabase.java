package com.github.changebooks.seata.demo.tcc.repository.account.database;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 预存款流水
 *
 * @author 宋欢
 */
@Repository
public interface AccountFlowDatabase {
    /**
     * 获取一条流水id
     *
     * @param userId     用户id
     * @param changeType 改变方式，0-未知、1-冻结、2-解冻、3-扣已冻
     * @param idempotent 幂等
     * @return 流水id
     */
    Integer find(@Param("userId") int userId,
                 @Param("changeType") int changeType,
                 @Param("idempotent") String idempotent);

    /**
     * 新增一条流水
     *
     * @param userId        用户id
     * @param changeType    改变方式，0-未知、1-冻结、2-解冻、3-扣已冻
     * @param usableBalance 增减的可用余额，单位：分
     * @param frozenBalance 增减的冻结余额，单位：分
     * @param orderId       订单号
     * @param idempotent    幂等
     * @return 影响行数
     */
    int insert(@Param("userId") int userId,
               @Param("changeType") int changeType,
               @Param("usableBalance") int usableBalance,
               @Param("frozenBalance") int frozenBalance,
               @Param("orderId") int orderId,
               @Param("idempotent") String idempotent);
}
