package com.github.changebooks.seata.demo.tcc.repository.account.database;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 预存款
 *
 * @author 宋欢
 */
@Repository
public interface AccountDatabase {
    /**
     * 冻结
     *
     * @param userId 用户id
     * @param num    冻结的金额，单位：分
     * @return 影响行数
     */
    int freeze(@Param("userId") int userId, @Param("num") int num);

    /**
     * 解冻
     *
     * @param userId 用户id
     * @param num    解冻的金额，单位：分
     * @return 影响行数
     */
    int unfreeze(@Param("userId") int userId, @Param("num") int num);

    /**
     * 扣已冻
     *
     * @param userId 用户id
     * @param num    已冻结的金额，单位：分
     * @return 影响行数
     */
    int decreaseFrozen(@Param("userId") int userId, @Param("num") int num);

}
