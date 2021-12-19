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
     * 触发优先级
     * @since 1.3.2
     */
    private int triggerOrder;

    /**
     * 下一次触发时间
     * @since 0.0.2
     */
    private long nextTime;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public long getNextTime() {
        return nextTime;
    }

    public void setNextTime(long nextTime) {
        this.nextTime = nextTime;
    }

    public int getTriggerOrder() {
        return triggerOrder;
    }

    public void setTriggerOrder(int triggerOrder) {
        this.triggerOrder = triggerOrder;
    }

    @Override
    public int compareTo(JobTriggerDto o) {
        long nextTimeOther = o.nextTime;
        long orderOther = o.triggerOrder;

        //如果时间不等
        if(this.nextTime != nextTimeOther) {
            return (int) (nextTime - nextTimeOther);
        }
        // 如果时间相等，则比较 order
        return (int) (this.triggerOrder - orderOther);
    }

    @Override
    public String toString() {
        return "JobTriggerDto{" +
                "jobId='" + jobId + '\'' +
                ", triggerId='" + triggerId + '\'' +
                ", triggerOrder=" + triggerOrder +
                ", nextTime=" + nextTime +
                "} " + super.toString();
    }

}
