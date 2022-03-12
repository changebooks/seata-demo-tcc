package com.github.changebooks.seata.demo.tcc.repository.account.main;

import com.github.changebooks.seata.demo.tcc.repository.spi.AccountSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 宋欢
 */
@RestController
public class AccountController implements AccountSpi {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountTcc accountTcc;

    @Override
    public boolean decreaseBalance(Integer userId, Integer num, Integer orderId) {
        LOGGER.info("decreaseBalance trace, userId: {}, num: {}, orderId: {}", userId, num, orderId);

        return accountTcc.prepare(null, userId, num, orderId);
    }

}
