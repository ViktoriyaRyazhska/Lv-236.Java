package com.inva.hipstertest.service.mapper;

import com.inva.hipstertest.domain.*;
import com.inva.hipstertest.service.dto.TeacherDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Teacher and its DTO TeacherDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, FormMapper.class, LessonMapper.class, SchoolMapper.class, ScheduleMapper.class,})
public interface TeacherMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "form.id", target = "formId")
    @Mapping(source = "school.id", target = "schoolId")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.email", target = "email")
    TeacherDTO teacherToTeacherDTO(Teacher teacher);

    List<TeacherDTO> teachersToTeacherDTOs(List<Teacher> teachers);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "formId", target = "form")
    @Mapping(source = "schoolId", target = "school")
    @Mapping(target = "schedules", ignore = true)
    @Mapping(source = "firstName", target = "user.firstName")
    @Mapping(source = "lastName", target = "user.lastName")
    @Mapping(source = "email", target = "user.email")
    Teacher teacherDTOToTeacher(TeacherDTO teacherDTO);

    List<Teacher> teacherDTOsToTeachers(List<TeacherDTO> teacherDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */

    default Teacher teacherFromId(Long id) {
        if (id == null) {
            return null;
        }
        Teacher teacher = new Teacher();
        teacher.setId(id);
        return teacher;
    }


}
