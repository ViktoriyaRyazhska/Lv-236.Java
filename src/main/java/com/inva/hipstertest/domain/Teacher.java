package com.inva.hipstertest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Teacher.
 */
@Entity
@Table(name = "teacher")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;


    @OneToOne(optional = false)
    @JoinColumn(unique = true)
    private Form form;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "teacher_lesson",
               joinColumns = @JoinColumn(name="teachers_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="lessons_id", referencedColumnName="id"))
    private Set<Lesson> lessons = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private School school;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Schedule> schedules = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Teacher enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public User getUser() {
        return user;
    }

    public Teacher user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Form getForm() {
        return form;
    }

    public Teacher form(Form form) {
        this.form = form;
        return this;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public Teacher lessons(Set<Lesson> lessons) {
        this.lessons = lessons;
        return this;
    }

    public Teacher addLesson(Lesson lesson) {
        this.lessons.add(lesson);
        lesson.getTeachers().add(this);
        return this;
    }

    public Teacher removeLesson(Lesson lesson) {
        this.lessons.remove(lesson);
        lesson.getTeachers().remove(this);
        return this;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public School getSchool() {
        return school;
    }

    public Teacher school(School school) {
        this.school = school;
        return this;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public Teacher schedules(Set<Schedule> schedules) {
        this.schedules = schedules;
        return this;
    }

    public Teacher addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        schedule.setTeacher(this);
        return this;
    }

    public Teacher removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
        schedule.setTeacher(null);
        return this;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Teacher teacher = (Teacher) o;
        if (teacher.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, teacher.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Teacher{" +
            "id=" + id +
            ", enabled='" + enabled + "'" +
            '}';
    }
}
