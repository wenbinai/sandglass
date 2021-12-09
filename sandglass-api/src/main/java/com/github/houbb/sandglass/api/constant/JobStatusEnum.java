package com.github.houbb.sandglass.api.constant;

/**
 * 任务状态
 *
 * @author binbin.hou
 * @since 0.0.4
 */
public enum JobStatusEnum {
    /**
     * 状态
     */
    NORMAL("NORMAL", "正常"),
    PAUSE("PAUSE", "已暂停"),
    ;

    private final String code;
    private final String desc;

    JobStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
