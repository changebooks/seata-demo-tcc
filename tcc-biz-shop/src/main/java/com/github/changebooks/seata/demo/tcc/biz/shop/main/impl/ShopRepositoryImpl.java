package com.github.changebooks.seata.demo.tcc.biz.shop.main.impl;

import com.github.changebooks.seata.demo.tcc.biz.shop.http.AccountHttp;
import com.github.changebooks.seata.demo.tcc.biz.shop.http.InventoryHttp;
import com.github.changebooks.seata.demo.tcc.biz.shop.http.OrderHttp;
import com.github.changebooks.seata.demo.tcc.biz.shop.main.ShopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 宋欢
 */
@Service
public class ShopRepositoryImpl implements ShopRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopRepositoryImpl.class);

    @Autowired
    private AccountHttp accountHttp;

    @Autowired
    private InventoryHttp inventoryHttp;

    @Autowired
    private OrderHttp orderHttp;

    @Override
    public boolean decreaseBalance(Integer userId, Integer num, Integer orderId) {
        try {
            return accountHttp.decreaseBalance(userId, num, orderId);
        } catch (Throwable tr) {
            LOGGER.error("decreaseBalance failed, rpc err, userId: {}, num: {}, orderId: {}, throwable: ", userId, num, orderId, tr);
            throw new RuntimeException(tr);
        }
    }

    @Override
    public boolean outStock(Integer productId, Integer num, Integer orderId) {
        try {
            return inventoryHttp.outStock(productId, num, orderId);
        } catch (Throwable tr) {
            LOGGER.error("outStock failed, rpc err, productId: {}, num: {}, orderId: {}, throwable: ", productId, num, orderId, tr);
            throw new RuntimeException(tr);
        }
    }

    @Override
    public boolean createOrder(Integer id, Integer userId, Integer productId, Integer productNum, Integer payNum) {
        try {
            return orderHttp.createOrder(id, userId, productId, productNum, payNum);
        } catch (Throwable tr) {
            LOGGER.error("createOrder failed, rpc err, id: {}, userId: {}, productId: {}, productNum: {}, payNum: {}, throwable: ", id, userId, productId, productNum, payNum, tr);
            throw new RuntimeException(tr);
        }
    }

}
