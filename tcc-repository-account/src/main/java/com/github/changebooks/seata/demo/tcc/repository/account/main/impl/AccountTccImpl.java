package com.github.changebooks.seata.demo.tcc.repository.account.main.impl;

import com.github.changebooks.seata.demo.tcc.repository.account.main.AccountService;
import com.github.changebooks.seata.demo.tcc.repository.account.main.AccountTcc;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author 宋欢
 */
@Service
public class AccountTccImpl implements AccountTcc {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountTccImpl.class);

    @Autowired
    private AccountService service;

    @Override
    public boolean prepare(BusinessActionContext context, Integer userId, Integer num, Integer orderId) {
        String xid = context.getXid();
        if (StringUtils.isEmpty(xid)) {
            LOGGER.error("prepare failed, xid can't be empty, xid: {}, userId: {}, num: {}, orderId: {}", xid, userId, num, orderId);
            throw new RuntimeException("xid can't be empty");
        }

        try {
            boolean r = service.freeze(userId, num, orderId, xid);
            if (!r) {
                throw new RuntimeException("freeze failed");
            }
        } catch (Throwable tr) {
            LOGGER.error("prepare failed, freeze failed, xid: {}, userId: {}, num: {}, orderId: {}, throwable: ", xid, userId, num, orderId, tr);
            throw new RuntimeException(tr);
        }

        LOGGER.info("prepare success, freeze success, xid: {}, userId: {}, num: {}, orderId: {}", xid, userId, num, orderId);
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext context) {
        int userId = Integer.parseInt(context.getActionContext("userId").toString());
        int num = Integer.parseInt(context.getActionContext("num").toString());
        int orderId = Integer.parseInt(context.getActionContext("orderId").toString());

        String xid = context.getXid();
        if (StringUtils.isEmpty(xid)) {
            LOGGER.error("commit failed, xid can't be empty, xid: {}, userId: {}, num: {}, orderId: {}", xid, userId, num, orderId);
            throw new RuntimeException("xid can't be empty");
        }

        try {
            if (!service.isFrozen(userId, xid)) {
                LOGGER.info("commit trace, ignore, xid: {}, userId: {}, num: {}, orderId: {}", xid, userId, num, orderId);
                return true;
            }

            boolean r = service.decreaseFrozen(userId, num, orderId, xid);
            if (!r) {
                throw new RuntimeException("decreaseFrozen failed");
            }
        } catch (Throwable tr) {
            LOGGER.error("commit failed, decreaseFrozen failed, xid: {}, userId: {}, num: {}, orderId: {}, throwable: ", xid, userId, num, orderId, tr);
            throw new RuntimeException(tr);
        }

        LOGGER.info("commit success, decreaseFrozen success, xid: {}, userId: {}, num: {}, orderId: {}", xid, userId, num, orderId);
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext context) {
        int userId = Integer.parseInt(context.getActionContext("userId").toString());
        int num = Integer.parseInt(context.getActionContext("num").toString());
        int orderId = Integer.parseInt(context.getActionContext("orderId").toString());

        String xid = context.getXid();
        if (StringUtils.isEmpty(xid)) {
            LOGGER.error("rollback failed, xid can't be empty, xid: {}, userId: {}, num: {}, orderId: {}", xid, userId, num, orderId);
            throw new RuntimeException("xid can't be empty");
        }

        try {
            if (!service.isFrozen(userId, xid)) {
                LOGGER.info("rollback trace, ignore, xid: {}, userId: {}, num: {}, orderId: {}", xid, userId, num, orderId);
                return true;
            }

            boolean r = service.unfreeze(userId, num, orderId, xid);
            if (!r) {
                throw new RuntimeException("unfreeze failed");
            }
        } catch (Throwable tr) {
            LOGGER.error("rollback failed, unfreeze failed, xid: {}, userId: {}, num: {}, orderId: {}, throwable: ", xid, userId, num, orderId, tr);
            throw new RuntimeException(tr);
        }

        LOGGER.info("rollback success, unfreeze success, xid: {}, userId: {}, num: {}, orderId: {}", xid, userId, num, orderId);
        return true;
    }

}
