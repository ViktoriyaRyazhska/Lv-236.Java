package com.inva.hipstertest.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A AttendancesLog.
 */
@Entity
@Table(name = "attendances_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AttendancesLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private ZonedDateTime date;

    @Column(name = "old_grade")
    private Integer oldGrade;

    @Column(name = "new_grade")
    private Integer newGrade;

    @NotNull
    @Column(name = "reason", nullable = false)
    private String reason;

    @ManyToOne(optional = false)
    @NotNull
    private Teacher teacher;

    @ManyToOne(optional = false)
    @NotNull
    private Attendances attendances;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public AttendancesLog date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Integer getOldGrade() {
        return oldGrade;
    }

    public AttendancesLog oldGrade(Integer oldGrade) {
        this.oldGrade = oldGrade;
        return this;
    }

    public void setOldGrade(Integer oldGrade) {
        this.oldGrade = oldGrade;
    }

    public Integer getNewGrade() {
        return newGrade;
    }

    public AttendancesLog newGrade(Integer newGrade) {
        this.newGrade = newGrade;
        return this;
    }

    public void setNewGrade(Integer newGrade) {
        this.newGrade = newGrade;
    }

    public String getReason() {
        return reason;
    }

    public AttendancesLog reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public AttendancesLog teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Attendances getAttendances() {
        return attendances;
    }

    public AttendancesLog attendances(Attendances attendances) {
        this.attendances = attendances;
        return this;
    }

    public void setAttendances(Attendances attendances) {
        this.attendances = attendances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendancesLog attendancesLog = (AttendancesLog) o;
        if (attendancesLog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, attendancesLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AttendancesLog{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", oldGrade='" + oldGrade + "'" +
            ", newGrade='" + newGrade + "'" +
            ", reason='" + reason + "'" +
            '}';
    }
}
