package com.github.houbb.sandglass.api.dto;

/**
 * 任务触发器对象
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public class JobTriggerDto extends BaseDto implements Comparable<JobTriggerDto> {

    /**
     * 任务标识
     * @since 0.0.2
     */
    private String jobId;

    /**
     * 触发器标识
     * @since 0.0.2
     */
    private String triggerId;

    /**
     * 下一次触发时间
     * @since 0.0.2
     */
    private long nextTime;

    /**
     * 优先级
     * @since 0.0.2
     */
    private int order;

    public String jobId() {
        return jobId;
    }

    public JobTriggerDto jobId(String jobId) {
        this.jobId = jobId;
        return this;
    }

    public String triggerId() {
        return triggerId;
    }

    public JobTriggerDto triggerId(String triggerId) {
        this.triggerId = triggerId;
        return this;
    }

    public long nextTime() {
        return nextTime;
    }

    public JobTriggerDto nextTime(long nextTime) {
        this.nextTime = nextTime;
        return this;
    }

    public int order() {
        return order;
    }

    public JobTriggerDto order(int order) {
        this.order = order;
        return this;
    }

    @Override
    public int compareTo(JobTriggerDto o) {
        long nextTimeOther = o.nextTime;
        long orderOther = o.order;

        //如果时间不等
        if(this.nextTime != nextTimeOther) {
            return (int) (nextTime - nextTimeOther);
        }
        // 如果时间相等，则比较 order
        return (int) (this.order - orderOther);
    }

    @Override
    public String toString() {
        return "JobTriggerDto{" +
                "jobId='" + jobId + '\'' +
                ", triggerId='" + triggerId + '\'' +
                ", nextTime=" + nextTime +
                ", order=" + order +
                "} " + super.toString();
    }

}
