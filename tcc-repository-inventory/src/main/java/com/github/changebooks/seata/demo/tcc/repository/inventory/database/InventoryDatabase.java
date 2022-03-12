package com.github.changebooks.seata.demo.tcc.repository.inventory.database;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 库存
 *
 * @author 宋欢
 */
@Repository
public interface InventoryDatabase {
    /**
     * 入库
     *
     * @param productId 商品id
     * @param num       加的库存
     * @return 影响行数
     */
    int inStock(@Param("productId") int productId, @Param("num") int num);

    /**
     * 出库
     *
     * @param productId 商品id
     * @param num       减的库存
     * @return 影响行数
     */
    int outStock(@Param("productId") int productId, @Param("num") int num);

}
