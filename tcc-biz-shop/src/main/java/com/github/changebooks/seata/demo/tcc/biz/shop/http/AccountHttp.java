package com.github.changebooks.seata.demo.tcc.biz.shop.http;

import com.github.changebooks.seata.demo.tcc.repository.spi.AccountSpi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;

/**
 * 预存款领域
 *
 * @author 宋欢
 */
@FeignClient(name = "seata-demo-tcc-repository-account")
@Repository
public interface AccountHttp extends AccountSpi {
}
