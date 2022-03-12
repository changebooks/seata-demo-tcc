package com.github.changebooks.seata.demo.tcc.repository.inventory.main.impl;

import com.github.changebooks.seata.demo.tcc.repository.inventory.database.InventoryDatabase;
import com.github.changebooks.seata.demo.tcc.repository.inventory.database.InventoryFlowDatabase;
import com.github.changebooks.seata.demo.tcc.repository.inventory.main.ChangeTypeEnum;
import com.github.changebooks.seata.demo.tcc.repository.inventory.main.InventoryService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author 宋欢
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryDatabase database;

    @Autowired
    private InventoryFlowDatabase flowDatabase;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean inStock(Integer productId, Integer num, Integer orderId, String idempotent) {
        checkArgument(productId, num, orderId, idempotent);

        if (checkIdempotent(productId, ChangeTypeEnum.IN_STOCK.id, idempotent)) {
            // 重复请求，忽略
            return true;
        }

        if (!saveIdempotent(productId, ChangeTypeEnum.IN_STOCK.id, num, orderId, idempotent)) {
            return false;
        }

        int affectedRows = database.inStock(productId, num);
        if (affectedRows <= 0) {
            // 回滚数据库
            throw new RuntimeException("inStock failed");
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean outStock(Integer productId, Integer num, Integer orderId, String idempotent) {
        checkArgument(productId, num, orderId, idempotent);

        if (checkIdempotent(productId, ChangeTypeEnum.OUT_STOCK.id, idempotent)) {
            // 重复请求，忽略
            return true;
        }

        if (!saveIdempotent(productId, ChangeTypeEnum.OUT_STOCK.id, num, orderId, idempotent)) {
            return false;
        }

        int affectedRows = database.outStock(productId, num);
        if (affectedRows <= 0) {
            // 回滚数据库
            throw new RuntimeException("outStock failed");
        }

        return true;
    }

    @Override
    public boolean isOutStock(Integer productId, String idempotent) {
        checkArgument(productId, idempotent);

        return checkIdempotent(productId, ChangeTypeEnum.OUT_STOCK.id, idempotent);
    }

    /**
     * 校验幂等
     */
    private boolean checkIdempotent(int productId, int changeType, String idempotent) {
        Integer flowId = flowDatabase.find(productId, changeType, idempotent);
        return flowId != null && flowId > 0;
    }

    /**
     * 保存幂等
     */
    private boolean saveIdempotent(int productId, int changeType, int num, int orderId, String idempotent) {
        int affectedRows = flowDatabase.insert(productId, changeType, num, orderId, idempotent);
        return affectedRows > 0;
    }

    /**
     * 校验参数
     */
    private void checkArgument(Integer productId, Integer num, Integer orderId, String idempotent) {
        Preconditions.checkNotNull(num, "num can't be null");
        Preconditions.checkArgument(num > 0, "num can't be less or equal than 0");

        Preconditions.checkNotNull(orderId, "orderId can't be null");
        Preconditions.checkArgument(orderId > 0, "orderId can't be less or equal than 0");

        checkArgument(productId, idempotent);
    }

    /**
     * 校验参数
     */
    private void checkArgument(Integer productId, String idempotent) {
        Preconditions.checkNotNull(productId, "productId can't be null");
        Preconditions.checkArgument(productId > 0, "productId can't be less or equal than 0");

        Preconditions.checkArgument(!StringUtils.isEmpty(idempotent), "idempotent can't be empty");
    }

}
