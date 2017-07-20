package com.inva.hipstertest.service.mapper;

import com.inva.hipstertest.domain.*;
import com.inva.hipstertest.service.dto.PupilDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Pupil and its DTO PupilDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, FormMapper.class, ParentMapper.class, })
public interface PupilMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "form.id", target = "formId")
    @Mapping(source = "form.name", target = "formName")
//    @Mapping(source = "parents", target = "parents")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "form.school.enabled", target = "schoolEnabled")
    PupilDTO pupilToPupilDTO(Pupil pupil);

    List<PupilDTO> pupilsToPupilDTOs(List<Pupil> pupils);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "attendances", ignore = true)
    @Mapping(source = "formId",target="form")
    @Mapping(target = "parents",ignore = true)
    Pupil pupilDTOToPupil(PupilDTO pupilDTO);

    List<Pupil> pupilDTOsToPupils(List<PupilDTO> pupilDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */

    default Pupil pupilFromId(Long id) {
        if (id == null) {
            return null;
        }
        Pupil pupil = new Pupil();
        pupil.setId(id);
        return pupil;
    }


}
