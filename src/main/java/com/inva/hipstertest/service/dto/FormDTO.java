package com.inva.hipstertest.service.dto;




import com.inva.hipstertest.domain.Pupil;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Form entity.
 */
public class FormDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Boolean enabled;

    private Long schoolId;

    private String schoolName;

    private Set<PupilDTO> pupilsId;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Set<PupilDTO> getPupilsId() {
        return pupilsId;
    }

    public void setPupilsId(Set<PupilDTO> pupilsId) {
        this.pupilsId = pupilsId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }


    @Override
    public String toString() {
        return "FormDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", enabled=" + enabled +
            ", schoolId=" + schoolId +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormDTO formDTO = (FormDTO) o;

        if (id != null ? !id.equals(formDTO.id) : formDTO.id != null) return false;
        if (name != null ? !name.equals(formDTO.name) : formDTO.name != null) return false;
        if (enabled != null ? !enabled.equals(formDTO.enabled) : formDTO.enabled != null) return false;
        return schoolId != null ? schoolId.equals(formDTO.schoolId) : formDTO.schoolId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
        result = 31 * result + (schoolId != null ? schoolId.hashCode() : 0);
        return result;
    }
}
