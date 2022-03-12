package com.github.changebooks.seata.demo.tcc.repository.inventory.main;

import com.github.changebooks.seata.demo.tcc.repository.spi.InventorySpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 宋欢
 */
@RestController
public class InventoryController implements InventorySpi {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private InventoryTcc inventoryTcc;

    @Override
    public boolean outStock(Integer productId, Integer num, Integer orderId) {
        LOGGER.info("outStock trace, productId: {}, num: {}, orderId: {}", productId, num, orderId);

        return inventoryTcc.prepare(null, productId, num, orderId);
    }

}
