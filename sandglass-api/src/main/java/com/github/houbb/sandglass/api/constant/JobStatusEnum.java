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
    WAIT_TRIGGER("WAIT_TRIGGER", "待触发"),
    WAIT_EXECUTE("WAIT_EXECUTE", "待执行"),
    EXECUTING("EXECUTING", "执行中"),
    EXECUTED("EXECUTED", "已执行"),
    COMPLETE("COMPLETE", "已完成"),
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

    /**
     * 任务是否处于执行中
     * @param jobStatusEnum 任务状态枚举
     * @return 是否
     * @since 0.0.8
     */
    public static boolean isInProgress(JobStatusEnum jobStatusEnum) {
        if(JobStatusEnum.WAIT_EXECUTE.equals(jobStatusEnum)
            || JobStatusEnum.EXECUTING.equals(jobStatusEnum)) {
            return true;
        }
        return false;
    }

}
