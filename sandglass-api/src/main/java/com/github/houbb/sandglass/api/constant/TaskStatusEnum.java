package com.github.houbb.sandglass.api.constant;

/**
 * 任务执行状态
 *
 * @author binbin.hou
 * @since 0.0.9
 */
public enum TaskStatusEnum {
    /**
     * 状态
     */
    INIT("INIT", "初始化"),
    SUCCESS("SUCCESS", "成功"),
    FAILED("FAILED", "失败"),
    ;

    private final String code;
    private final String desc;

    TaskStatusEnum(String code, String desc) {
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
