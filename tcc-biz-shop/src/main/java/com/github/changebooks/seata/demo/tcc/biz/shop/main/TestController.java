package com.github.changebooks.seata.demo.tcc.biz.shop.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 宋欢
 */
@RequestMapping("test")
@Validated
@RestController
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @GetMapping(value = "/test")
    public String test() {
        return "shop-ok";
    }

}
