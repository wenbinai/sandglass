package com.github.houbb.sandglass.api.constant;

/**
 * 触发器类型枚举
 * @author binbin.hou
 * @since 1.0.0
 */
public enum TriggerTypeEnum {
    PERIOD("PERIOD", "阶段执行"),
    CRON("CRON", "定时表达式"),
    ;

    private final String code;
    private final String desc;

    TriggerTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String code() {
        return code;
    }

    public String desc() {
        return desc;
    }
}
