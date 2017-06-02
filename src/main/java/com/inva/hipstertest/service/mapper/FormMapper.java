package com.inva.hipstertest.service.mapper;

import com.inva.hipstertest.domain.*;
import com.inva.hipstertest.service.dto.FormDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Form and its DTO FormDTO.
 */
@Mapper(componentModel = "spring", uses = {SchoolMapper.class, PupilMapper.class })
public interface FormMapper {

    @Mapping(source = "school.id", target = "schoolId")
    @Mapping(source = "form.pupils", target = "pupilsId")
    @Mapping(source = "school.name", target = "schoolName")
    FormDTO formToFormDTO(Form form);

    List<FormDTO> formsToFormDTOs(List<Form> forms);



    @Mapping(target = "pupils", ignore = true)
    @Mapping(target = "schedules", ignore = true)
    @Mapping(source = "schoolId", target = "school")
    @Mapping(source = "id", target = "id")
    Form formDTOToForm(FormDTO formDTO);


    List<Form> formDTOsToForms(List<FormDTO> formDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */

    default Form formFromId(Long id) {
        if (id == null) {
            return null;
        }
        Form form = new Form();
        form.setId(id);
        return form;
    }


}
