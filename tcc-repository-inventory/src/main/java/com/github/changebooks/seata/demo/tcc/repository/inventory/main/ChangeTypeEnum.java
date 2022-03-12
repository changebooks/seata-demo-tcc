package com.github.changebooks.seata.demo.tcc.repository.inventory.main;

/**
 * 流水，改变方式
 *
 * @author 宋欢
 */
public enum ChangeTypeEnum {
    /**
     * 入库
     */
    IN_STOCK(1, "入库"),

    /**
     * 出库
     */
    OUT_STOCK(2, "出库"),

    ;

    /**
     * 改变方式
     */
    public final int id;

    /**
     * 备注
     */
    public final String remark;

    ChangeTypeEnum(int id, String remark) {
        this.id = id;
        this.remark = remark;
    }

}
