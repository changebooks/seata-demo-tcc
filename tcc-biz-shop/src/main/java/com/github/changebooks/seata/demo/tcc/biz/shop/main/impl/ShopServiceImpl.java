package com.github.changebooks.seata.demo.tcc.biz.shop.main.impl;

import com.github.changebooks.seata.demo.tcc.biz.shop.main.ShopRepository;
import com.github.changebooks.seata.demo.tcc.biz.shop.main.ShopService;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 宋欢
 */
@Service
public class ShopServiceImpl implements ShopService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopServiceImpl.class);

    @Autowired
    private ShopRepository repository;

    @GlobalTransactional
    @Override
    public boolean createOrder(Integer orderId, Integer userId, Integer productId, Integer productNum, Integer payNum) {
        LOGGER.info("createOrder trace, orderId: {}, userId: {}, productId: {}, productNum: {}, payNum: {}", orderId, userId, productId, productNum, payNum);

        if (!repository.outStock(productId, productNum, orderId)) {
            throw new RuntimeException("outStock failed");
        }

        if (!repository.createOrder(orderId, userId, productId, productNum, payNum)) {
            throw new RuntimeException("createOrder failed");
        }

        if (!repository.decreaseBalance(userId, payNum, orderId)) {
            throw new RuntimeException("decreaseBalance failed");
        }

        return true;
    }

}
