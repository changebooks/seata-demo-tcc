package com.github.changebooks.seata.demo.tcc.repository.account.main;

/**
 * 流水，改变方式
 *
 * @author 宋欢
 */
public enum ChangeTypeEnum {
    /**
     * 冻结
     */
    FREEZE(1, "冻结"),

    /**
     * 解冻
     */
    UNFREEZE(2, "解冻"),

    /**
     * 扣已冻
     */
    DECREASE_FROZEN(3, "扣已冻"),

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
