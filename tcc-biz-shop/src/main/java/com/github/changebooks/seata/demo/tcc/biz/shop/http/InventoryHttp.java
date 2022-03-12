package com.github.changebooks.seata.demo.tcc.biz.shop.http;

import com.github.changebooks.seata.demo.tcc.repository.spi.InventorySpi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;

/**
 * 库存领域
 *
 * @author 宋欢
 */
@FeignClient(name = "seata-demo-tcc-repository-inventory")
@Repository
public interface InventoryHttp extends InventorySpi {
}
