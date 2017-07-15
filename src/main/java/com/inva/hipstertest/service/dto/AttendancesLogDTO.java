package com.inva.hipstertest.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AttendancesLog entity.
 */
public class AttendancesLogDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime date;

    private Integer oldGrade;

    private Integer newGrade;

    @NotNull
    private String reason;

    private Long teacherId;

    private Long attendancesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }
    public Integer getOldGrade() {
        return oldGrade;
    }

    public void setOldGrade(Integer oldGrade) {
        this.oldGrade = oldGrade;
    }
    public Integer getNewGrade() {
        return newGrade;
    }

    public void setNewGrade(Integer newGrade) {
        this.newGrade = newGrade;
    }
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getAttendancesId() {
        return attendancesId;
    }

    public void setAttendancesId(Long attendancesId) {
        this.attendancesId = attendancesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AttendancesLogDTO attendancesLogDTO = (AttendancesLogDTO) o;

        if ( ! Objects.equals(id, attendancesLogDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AttendancesLogDTO{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", oldGrade='" + oldGrade + "'" +
            ", newGrade='" + newGrade + "'" +
            ", reason='" + reason + "'" +
            '}';
    }
}
