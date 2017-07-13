package com.inva.hipstertest.service.mapper;

import com.inva.hipstertest.domain.*;
import com.inva.hipstertest.service.dto.LessonTypeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LessonType and its DTO LessonTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LessonTypeMapper {

    LessonTypeDTO lessonTypeToLessonTypeDTO(LessonType lessonType);

    List<LessonTypeDTO> lessonTypesToLessonTypeDTOs(List<LessonType> lessonTypes);

    LessonType lessonTypeDTOToLessonType(LessonTypeDTO lessonTypeDTO);

    List<LessonType> lessonTypeDTOsToLessonTypes(List<LessonTypeDTO> lessonTypeDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default LessonType lessonTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        LessonType lessonType = new LessonType();
        lessonType.setId(id);
        return lessonType;
    }
    

}
