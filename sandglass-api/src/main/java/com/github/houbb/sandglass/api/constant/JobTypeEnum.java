package com.github.houbb.sandglass.api.constant;

/**
 * 任务类型枚举
 * @author binbin.hou
 * @since 1.0.0
 */
public enum JobTypeEnum {
    COMMON("COMMON", "普通"),
    SPRING("SPRING", "容器反射"),
    ;

    private final String code;
    private final String desc;

    JobTypeEnum(String code, String desc) {
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
