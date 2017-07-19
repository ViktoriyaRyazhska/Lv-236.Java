package com.inva.hipstertest.freemarker.searchcriteria;

import java.time.ZonedDateTime;

/**
 * Created by slavkosoltys on 12.07.17.
 */
public class ClassroomSearchCriteria {

    private Long classroomId;
    private Integer lessonPosition;
    private ZonedDateTime date;

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
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
