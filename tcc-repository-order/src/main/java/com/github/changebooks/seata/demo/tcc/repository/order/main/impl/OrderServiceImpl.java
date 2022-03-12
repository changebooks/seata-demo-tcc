package com.github.changebooks.seata.demo.tcc.repository.order.main.impl;

import com.github.changebooks.seata.demo.tcc.repository.order.database.OrdersDatabase;
import com.github.changebooks.seata.demo.tcc.repository.order.main.OrderService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 宋欢
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersDatabase database;

    @Override
    public boolean createOrder(Integer id, Integer userId, Integer productId, Integer productNum, Integer payNum) {
        checkArgument(id, userId, productId, productNum, payNum);

        int affectedRows = database.insert(id, userId, productId, productNum, payNum);
        return affectedRows > 0;
    }

    @Override
    public boolean changePay(Integer id, Integer payStatus) {
        checkArgument(id, payStatus);

        int affectedRows = database.update(id, payStatus);
        return affectedRows > 0;
    }

    @Override
    public boolean hasOrder(Integer id) {
        Preconditions.checkNotNull(id, "id can't be null");
        Preconditions.checkArgument(id > 0, "id can't be less or equal than 0");

        Integer payStatus = database.find(id);
        return payStatus != null;
    }

    /**
     * 校验参数
     */
    private void checkArgument(Integer id, Integer userId, Integer productId, Integer productNum, Integer payNum) {
        Preconditions.checkNotNull(id, "id can't be null");
        Preconditions.checkArgument(id > 0, "id can't be less or equal than 0");

        Preconditions.checkNotNull(userId, "userId can't be null");
        Preconditions.checkArgument(userId > 0, "userId can't be less or equal than 0");

        Preconditions.checkNotNull(id, "productId can't be null");
        Preconditions.checkArgument(productId > 0, "productId can't be less or equal than 0");

        Preconditions.checkNotNull(id, "productNum can't be null");
        Preconditions.checkArgument(productNum > 0, "productNum can't be less or equal than 0");

        Preconditions.checkNotNull(id, "payNum can't be null");
        Preconditions.checkArgument(payNum > 0, "payNum can't be less or equal than 0");
    }

    /**
     * 校验参数
     */
    private void checkArgument(Integer id, Integer payStatus) {
        Preconditions.checkNotNull(id, "id can't be null");
        Preconditions.checkArgument(id > 0, "id can't be less or equal than 0");

        Preconditions.checkNotNull(payStatus, "payStatus can't be null");
        Preconditions.checkArgument(payStatus > 0, "payStatus can't be less or equal than 0");
    }

}
