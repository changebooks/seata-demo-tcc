package com.github.changebooks.seata.demo.tcc.biz.shop.http;

import com.github.changebooks.seata.demo.tcc.repository.spi.OrderSpi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;

/**
 * 订单领域
 *
 * @author 宋欢
 */
@FeignClient(name = "seata-demo-tcc-repository-order")
@Repository
public interface OrderHttp extends OrderSpi {
}
