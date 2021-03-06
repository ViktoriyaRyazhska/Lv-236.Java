package com.inva.hipstertest.domain;

import com.inva.hipstertest.domain.enums.NotificationType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;

/**
 * A Notification.
 */
@Entity
@Table(name = "notification")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(STRING)
    private NotificationType type;

    @NotNull
    private String message;

    @NotNull
    private ZonedDateTime date;

    @NotNull
    @ManyToOne(optional = false)
    private User user;

    public Notification() {
    }

    private Notification(Builder builder) {
        this.type = builder.type;
        this.message = builder.message;
        this.user = builder.user;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(message, that.message) &&
            Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, user);
    }

    @Override
    public String toString() {
        return "Notification{" +
            "id=" + id +
            ", message='" + message + '\'' +
            ", user=" + user +
            '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Long id;
        private NotificationType type;
        private String message;
        private User user;

        public Builder type(NotificationType type) {
            this.type = type;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }


        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }


    }


}
