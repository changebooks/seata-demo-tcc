package com.github.changebooks.seata.demo.tcc.repository.account.main.impl;

import com.github.changebooks.seata.demo.tcc.repository.account.database.AccountDatabase;
import com.github.changebooks.seata.demo.tcc.repository.account.database.AccountFlowDatabase;
import com.github.changebooks.seata.demo.tcc.repository.account.main.AccountService;
import com.github.changebooks.seata.demo.tcc.repository.account.main.ChangeTypeEnum;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author 宋欢
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDatabase database;

    @Autowired
    private AccountFlowDatabase flowDatabase;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean freeze(Integer userId, Integer num, Integer orderId, String idempotent) {
        checkArgument(userId, num, orderId, idempotent);

        if (checkIdempotent(userId, ChangeTypeEnum.FREEZE.id, idempotent)) {
            // 重复请求，忽略
            return true;
        }

        if (!saveIdempotent(userId, ChangeTypeEnum.FREEZE.id, num, num, orderId, idempotent)) {
            return false;
        }

        int affectedRows = database.freeze(userId, num);
        if (affectedRows <= 0) {
            // 回滚数据库
            throw new RuntimeException("freeze failed");
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean unfreeze(Integer userId, Integer num, Integer orderId, String idempotent) {
        checkArgument(userId, num, orderId, idempotent);

        if (checkIdempotent(userId, ChangeTypeEnum.UNFREEZE.id, idempotent)) {
            // 重复请求，忽略
            return true;
        }

        if (!saveIdempotent(userId, ChangeTypeEnum.UNFREEZE.id, num, num, orderId, idempotent)) {
            return false;
        }

        int affectedRows = database.unfreeze(userId, num);
        if (affectedRows <= 0) {
            // 回滚数据库
            throw new RuntimeException("unfreeze failed");
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean decreaseFrozen(Integer userId, Integer num, Integer orderId, String idempotent) {
        checkArgument(userId, num, orderId, idempotent);

        if (checkIdempotent(userId, ChangeTypeEnum.DECREASE_FROZEN.id, idempotent)) {
            // 重复请求，忽略
            return true;
        }

        if (!saveIdempotent(userId, ChangeTypeEnum.DECREASE_FROZEN.id, 0, num, orderId, idempotent)) {
            return false;
        }

        int affectedRows = database.decreaseFrozen(userId, num);
        if (affectedRows <= 0) {
            // 回滚数据库
            throw new RuntimeException("decreaseFrozen failed");
        }

        return true;
    }

    @Override
    public boolean isFrozen(Integer userId, String idempotent) {
        checkArgument(userId, idempotent);

        return checkIdempotent(userId, ChangeTypeEnum.FREEZE.id, idempotent);
    }

    /**
     * 校验幂等
     */
    private boolean checkIdempotent(int userId, int changeType, String idempotent) {
        Integer flowId = flowDatabase.find(userId, changeType, idempotent);
        return flowId != null && flowId > 0;
    }

    /**
     * 保存幂等
     */
    private boolean saveIdempotent(int userId, int changeType, int usableBalance, int frozenBalance, int orderId, String idempotent) {
        int affectedRows = flowDatabase.insert(userId, changeType, usableBalance, frozenBalance, orderId, idempotent);
        return affectedRows > 0;
    }

    /**
     * 校验参数
     */
    private void checkArgument(Integer userId, Integer num, Integer orderId, String idempotent) {
        Preconditions.checkNotNull(num, "num can't be null");
        Preconditions.checkArgument(num > 0, "num can't be less or equal than 0");

        Preconditions.checkNotNull(orderId, "orderId can't be null");
        Preconditions.checkArgument(orderId > 0, "orderId can't be less or equal than 0");

        checkArgument(userId, idempotent);
    }

    /**
     * 校验参数
     */
    private void checkArgument(Integer userId, String idempotent) {
        Preconditions.checkNotNull(userId, "userId can't be null");
        Preconditions.checkArgument(userId > 0, "userId can't be less or equal than 0");

        Preconditions.checkArgument(!StringUtils.isEmpty(idempotent), "idempotent can't be empty");
    }

}
