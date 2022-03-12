package com.github.changebooks.seata.demo.tcc.repository.order.main.impl;

import com.github.changebooks.seata.demo.tcc.repository.order.main.OrderService;
import com.github.changebooks.seata.demo.tcc.repository.order.main.OrderTcc;
import com.github.changebooks.seata.demo.tcc.repository.order.main.PayStatusEnum;
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
public class OrderTccImpl implements OrderTcc {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderTccImpl.class);

    @Autowired
    private OrderService service;

    @Override
    public boolean prepare(BusinessActionContext context, Integer id, Integer userId, Integer productId, Integer productNum, Integer payNum) {
        String xid = context.getXid();
        if (StringUtils.isEmpty(xid)) {
            LOGGER.error("prepare failed, xid can't be empty, xid: {}, id: {}, userId: {}, productId: {}, productNum: {}, payNum: {}",
                    xid, id, userId, productId, productNum, payNum);
            throw new RuntimeException("xid can't be empty");
        }

        try {
            boolean r = service.createOrder(id, userId, productId, productNum, payNum);
            if (!r) {
                throw new RuntimeException("createOrder failed");
            }
        } catch (Throwable tr) {
            LOGGER.error("prepare failed, createOrder failed, xid: {}, id: {}, userId: {}, productId: {}, productNum: {}, payNum: {}, throwable: ",
                    xid, id, userId, productId, productNum, payNum, tr);
            throw new RuntimeException(tr);
        }

        LOGGER.error("prepare success, createOrder success, xid: {}, id: {}, userId: {}, productId: {}, productNum: {}, payNum: {}",
                xid, id, userId, productId, productNum, payNum);
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext context) {
        int id = Integer.parseInt(context.getActionContext("id").toString());

        String xid = context.getXid();
        if (StringUtils.isEmpty(xid)) {
            LOGGER.error("commit failed, xid can't be empty, xid: {}, id: {}", xid, id);
            throw new RuntimeException("xid can't be empty");
        }

        try {
            if (!service.hasOrder(id)) {
                LOGGER.info("commit trace, ignore, xid: {}, id: {}", xid, id);
                return true;
            }

            boolean r = service.changePay(id, PayStatusEnum.PAY_SUCCESS.id);
            if (!r) {
                throw new RuntimeException("changePay failed");
            }
        } catch (Throwable tr) {
            LOGGER.error("commit failed, changePay failed, xid: {}, id: {}, throwable: ", xid, id, tr);
            throw new RuntimeException(tr);
        }

        LOGGER.info("commit success, changePay success, xid: {}, id: {}", xid, id);
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext context) {
        int id = Integer.parseInt(context.getActionContext("id").toString());

        String xid = context.getXid();
        if (StringUtils.isEmpty(xid)) {
            LOGGER.error("rollback failed, xid can't be empty, xid: {}, id: {}", xid, id);
            throw new RuntimeException("xid can't be empty");
        }

        try {
            if (!service.hasOrder(id)) {
                LOGGER.info("rollback trace, ignore, xid: {}, id: {}", xid, id);
                return true;
            }

            boolean r = service.changePay(id, PayStatusEnum.PAY_FAILED.id);
            if (!r) {
                throw new RuntimeException("changePay failed");
            }
        } catch (Throwable tr) {
            LOGGER.error("rollback failed, changePay failed, xid: {}, id: {}, throwable: ", xid, id, tr);
            throw new RuntimeException(tr);
        }

        LOGGER.info("rollback success, changePay success, xid: {}, id: {}", xid, id);
        return true;
    }

}
