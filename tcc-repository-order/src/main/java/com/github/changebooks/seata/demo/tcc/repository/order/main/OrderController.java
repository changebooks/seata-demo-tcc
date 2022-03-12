package com.github.changebooks.seata.demo.tcc.repository.order.main;

import com.github.changebooks.seata.demo.tcc.repository.spi.OrderSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 宋欢
 */
@RestController
public class OrderController implements OrderSpi {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderTcc orderTcc;

    @Override
    public boolean createOrder(Integer id, Integer userId, Integer productId, Integer productNum, Integer payNum) {
        LOGGER.info("createOrder trace, id: {}, userId: {}, productId: {}, productNum: {}, payNum: {}",
                id, userId, productId, productNum, payNum);

        return orderTcc.prepare(null, id, userId, productId, productNum, payNum);
    }

}
