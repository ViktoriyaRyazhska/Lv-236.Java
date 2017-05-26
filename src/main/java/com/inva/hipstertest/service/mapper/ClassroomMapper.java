package com.inva.hipstertest.service.mapper;

import com.inva.hipstertest.domain.*;
import com.inva.hipstertest.service.dto.ClassroomDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Classroom and its DTO ClassroomDTO.
 */
@Mapper(componentModel = "spring", uses = {SchoolMapper.class, })
public interface ClassroomMapper {

    @Mapping(source = "school.id", target = "schoolId")
    @Mapping(source = "school.name", target = "schoolName")
    ClassroomDTO classroomToClassroomDTO(Classroom classroom);

    List<ClassroomDTO> classroomsToClassroomDTOs(List<Classroom> classrooms);

    @Mapping(target = "schedules", ignore = true)
    @Mapping(source = "schoolId", target = "school")
    Classroom classroomDTOToClassroom(ClassroomDTO classroomDTO);

    List<Classroom> classroomDTOsToClassrooms(List<ClassroomDTO> classroomDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */

    default Classroom classroomFromId(Long id) {
        if (id == null) {
            return null;
        }
        Classroom classroom = new Classroom();
        classroom.setId(id);
        return classroom;
    }


}
