package com.inva.hipstertest.service.mapper;

import com.inva.hipstertest.domain.*;
import com.inva.hipstertest.service.dto.AttendancesDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Attendances and its DTO AttendancesDTO.
 */
@Mapper(componentModel = "spring", uses = {PupilMapper.class, ScheduleMapper.class, })
public interface AttendancesMapper {

    @Mapping(source = "pupil.id", target = "pupilId")
    @Mapping(source = "schedule.id", target = "scheduleId")
    @Mapping(source = "schedule.date", target = "date")
    AttendancesDTO attendancesToAttendancesDTO(Attendances attendances);

    List<AttendancesDTO> attendancesToAttendancesDTOs(List<Attendances> attendances);

    @Mapping(source = "pupilId", target = "pupil")
    @Mapping(source = "scheduleId", target = "schedule")
    Attendances attendancesDTOToAttendances(AttendancesDTO attendancesDTO);

    List<Attendances> attendancesDTOsToAttendances(List<AttendancesDTO> attendancesDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */

    default Attendances attendancesFromId(Long id) {
        if (id == null) {
            return null;
        }
        Attendances attendances = new Attendances();
        attendances.setId(id);
        return attendances;
    }


}
