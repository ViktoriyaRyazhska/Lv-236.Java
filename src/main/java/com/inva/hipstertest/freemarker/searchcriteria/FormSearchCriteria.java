package com.inva.hipstertest.freemarker.searchcriteria;

import java.time.ZonedDateTime;

/**
 * Created by slavkosoltys on 12.07.17.
 */
public class FormSearchCriteria {

    private Long formId;
    private Integer lessonPosition;
    private ZonedDateTime date;

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public Integer getLessonPosition() {
        return lessonPosition;
    }

    public void setLessonPosition(Integer lessonPosition) {
        this.lessonPosition = lessonPosition;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }
}
