package com.github.changebooks.seata.demo.tcc.repository.order.main;

/**
 * 订单，支付状态
 *
 * @author 宋欢
 */
public enum PayStatusEnum {
    /**
     * 未支付
     */
    PAY_PREPARE(1, "未支付"),

    /**
     * 支付成功
     */
    PAY_SUCCESS(2, "支付成功"),

    /**
     * 支付失败
     */
    PAY_FAILED(3, "支付失败"),

    ;

    /**
     * 支付状态
     */
    public final int id;

    /**
     * 备注
     */
    public final String remark;

    PayStatusEnum(int id, String remark) {
        this.id = id;
        this.remark = remark;
    }

}
