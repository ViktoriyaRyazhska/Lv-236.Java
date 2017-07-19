package com.inva.hipstertest.freemarker.searchcriteria;

import com.inva.hipstertest.domain.enums.ScheduleFilterType;

import java.time.ZonedDateTime;

/**
 * Created by slavkosoltys on 06.07.17.
 */
public class ScheduleSearchCriteria {

    private Long id;
    private ZonedDateTime date;
    private ScheduleFilterType scheduleFilterType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ScheduleFilterType getScheduleFilterType() {
        return scheduleFilterType;
    }

    public void setScheduleFilterType(ScheduleFilterType scheduleFilterType) {
        this.scheduleFilterType = scheduleFilterType;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }
}
