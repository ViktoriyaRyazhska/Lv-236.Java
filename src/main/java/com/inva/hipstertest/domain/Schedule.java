package com.inva.hipstertest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Schedule.
 */
@Entity
@Table(name = "schedule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private ZonedDateTime date;

    @Column(name = "homework")
    private String homework;

    @NotNull
    @Column(name = "lesson_position", nullable = false)
    private Integer lessonPosition;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Attendances> attendances = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Lesson lesson;

    @ManyToOne(optional = false)
    @NotNull
    private Form form;

    @ManyToOne(optional = false)
    @NotNull
    private Classroom classroom;

    @ManyToOne(optional = false)
    @NotNull
    private Teacher teacher;

    @ManyToOne
    private LessonType lessonType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Schedule date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getHomework() {
        return homework;
    }

    public Schedule homework(String homework) {
        this.homework = homework;
        return this;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    public Integer getLessonPosition() {
        return lessonPosition;
    }

    public Schedule lessonPosition(Integer lessonPosition) {
        this.lessonPosition = lessonPosition;
        return this;
    }

    public void setLessonPosition(Integer lessonPosition) {
        this.lessonPosition = lessonPosition;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Schedule enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Attendances> getAttendances() {
        return attendances;
    }

    public Schedule attendances(Set<Attendances> attendances) {
        this.attendances = attendances;
        return this;
    }

    public Schedule addAttendances(Attendances attendances) {
        this.attendances.add(attendances);
        attendances.setSchedule(this);
        return this;
    }

    public Schedule removeAttendances(Attendances attendances) {
        this.attendances.remove(attendances);
        attendances.setSchedule(null);
        return this;
    }

    public void setAttendances(Set<Attendances> attendances) {
        this.attendances = attendances;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public Schedule lesson(Lesson lesson) {
        this.lesson = lesson;
        return this;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Form getForm() {
        return form;
    }

    public Schedule form(Form form) {
        this.form = form;
        return this;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public Schedule classroom(Classroom classroom) {
        this.classroom = classroom;
        return this;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Schedule teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public LessonType getLessonType() {
        return lessonType;
    }

    public Schedule lessonType(LessonType lessonType) {
        this.lessonType = lessonType;
        return this;
    }

    public void setLessonType(LessonType lessonType) {
        this.lessonType = lessonType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Schedule schedule = (Schedule) o;
        if (schedule.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, schedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Schedule{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", homework='" + homework + "'" +
            ", lessonPosition='" + lessonPosition + "'" +
            ", enabled='" + enabled + "'" +
            '}';
    }
}
