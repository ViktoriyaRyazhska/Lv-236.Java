package com.inva.hipstertest.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Parent.
 */
@Entity
@Table(name = "parent")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Parent implements Serializable {

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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "parent_pupil",
               joinColumns = @JoinColumn(name="parents_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="pupils_id", referencedColumnName="id"))
    private Set<Pupil> pupils;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Parent enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public User getUser() {
        return user;
    }

    public Parent user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Pupil> getPupils() {
        return pupils;
    }

    public Parent pupils(Set<Pupil> pupils) {
        this.pupils = pupils;
        return this;
    }

    public Parent addPupil(Pupil pupil) {
        this.pupils.add(pupil);
        pupil.getParents().add(this);
        return this;
    }

    public Parent removePupil(Pupil pupil) {
        this.pupils.remove(pupil);
        pupil.getParents().remove(this);
        return this;
    }

    public void setPupils(Set<Pupil> pupils) {
        this.pupils = pupils;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Parent parent = (Parent) o;
        if (parent.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, parent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Parent{" +
            "id=" + id +
            ", enabled='" + enabled + "'" +
            '}';
    }
}
