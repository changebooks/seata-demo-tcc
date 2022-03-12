package com.github.changebooks.seata.demo.tcc.repository.inventory.main.impl;

import com.github.changebooks.seata.demo.tcc.repository.inventory.main.InventoryService;
import com.github.changebooks.seata.demo.tcc.repository.inventory.main.InventoryTcc;
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
public class InventoryTccImpl implements InventoryTcc {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryTccImpl.class);

    @Autowired
    private InventoryService service;

    @Override
    public boolean prepare(BusinessActionContext context, Integer productId, Integer num, Integer orderId) {
        String xid = context.getXid();
        if (StringUtils.isEmpty(xid)) {
            LOGGER.error("prepare failed, xid can't be empty, xid: {}, productId: {}, num: {}, orderId: {}", xid, productId, num, orderId);
            throw new RuntimeException("xid can't be empty");
        }

        try {
            boolean r = service.outStock(productId, num, orderId, xid);
            if (!r) {
                throw new RuntimeException("outStock failed");
            }
        } catch (Throwable tr) {
            LOGGER.error("prepare failed, outStock failed, xid: {}, productId: {}, num: {}, orderId: {}, throwable: ",
                    xid, productId, num, orderId, tr);
            throw new RuntimeException(tr);
        }

        LOGGER.info("prepare success, outStock success, xid: {}, productId: {}, num: {}, orderId: {}", xid, productId, num, orderId);
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext context) {
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext context) {
        int productId = Integer.parseInt(context.getActionContext("productId").toString());
        int num = Integer.parseInt(context.getActionContext("num").toString());
        int orderId = Integer.parseInt(context.getActionContext("orderId").toString());

        String xid = context.getXid();
        if (StringUtils.isEmpty(xid)) {
            LOGGER.error("rollback failed, xid can't be empty, xid: {}, productId: {}, num: {}, orderId: {}", xid, productId, num, orderId);
            throw new RuntimeException("xid can't be empty");
        }

        try {
            if (!service.isOutStock(productId, xid)) {
                LOGGER.info("rollback trace, ignore, xid: {}, productId: {}, num: {}, orderId: {}", xid, productId, num, orderId);
                return true;
            }

            boolean r = service.inStock(productId, num, orderId, xid);
            if (!r) {
                throw new RuntimeException("inStock failed");
            }
        } catch (Throwable tr) {
            LOGGER.error("rollback failed, inStock failed, xid: {}, productId: {}, num: {}, orderId: {}, throwable: ", xid, productId, num, orderId, tr);
            throw new RuntimeException(tr);
        }

        LOGGER.info("rollback success, inStock success, xid: {}, productId: {}, num: {}, orderId: {}", xid, productId, num, orderId);
        return true;
    }

}
