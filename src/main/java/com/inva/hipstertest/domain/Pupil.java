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
 * A Pupil.
 */
@Entity
@Table(name = "pupil")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pupil implements Serializable {

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

    @OneToMany(mappedBy = "pupil", fetch = FetchType.LAZY)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Attendances> attendances = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Form form;

    @ManyToMany(mappedBy = "pupils")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Parent> parents = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Pupil enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public User getUser() {
        return user;
    }

    public Pupil user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Attendances> getAttendances() {
        return attendances;
    }

    public Pupil attendances(Set<Attendances> attendances) {
        this.attendances = attendances;
        return this;
    }

    public Pupil addAttendances(Attendances attendances) {
        this.attendances.add(attendances);
        attendances.setPupil(this);
        return this;
    }

    public Pupil removeAttendances(Attendances attendances) {
        this.attendances.remove(attendances);
        attendances.setPupil(null);
        return this;
    }

    public void setAttendances(Set<Attendances> attendances) {
        this.attendances = attendances;
    }

    public Form getForm() {
        return form;
    }

    public Pupil form(Form form) {
        this.form = form;
        return this;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Set<Parent> getParents() {
        return parents;
    }

    public Pupil parents(Set<Parent> parents) {
        this.parents = parents;
        return this;
    }

    public Pupil addParent(Parent parent) {
        this.parents.add(parent);
        parent.getPupils().add(this);
        return this;
    }

    public Pupil removeParent(Parent parent) {
        this.parents.remove(parent);
        parent.getPupils().remove(this);
        return this;
    }

    public void setParents(Set<Parent> parents) {
        this.parents = parents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pupil pupil = (Pupil) o;
        if (pupil.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pupil.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pupil{" +
            "id=" + id +
            ", enabled='" + enabled + "'" +
            '}';
    }
}
