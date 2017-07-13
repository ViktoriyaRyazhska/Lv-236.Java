package com.inva.hipstertest.service.mapper;

import com.inva.hipstertest.domain.*;
import com.inva.hipstertest.service.dto.AttendancesLogDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity AttendancesLog and its DTO AttendancesLogDTO.
 */
@Mapper(componentModel = "spring", uses = {TeacherMapper.class, AttendancesMapper.class, })
public interface AttendancesLogMapper {

    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "attendances.id", target = "attendancesId")
    AttendancesLogDTO attendancesLogToAttendancesLogDTO(AttendancesLog attendancesLog);

    List<AttendancesLogDTO> attendancesLogsToAttendancesLogDTOs(List<AttendancesLog> attendancesLogs);

    @Mapping(source = "teacherId", target = "teacher")
    @Mapping(source = "attendancesId", target = "attendances")
    AttendancesLog attendancesLogDTOToAttendancesLog(AttendancesLogDTO attendancesLogDTO);

    List<AttendancesLog> attendancesLogDTOsToAttendancesLogs(List<AttendancesLogDTO> attendancesLogDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default AttendancesLog attendancesLogFromId(Long id) {
        if (id == null) {
            return null;
        }
        AttendancesLog attendancesLog = new AttendancesLog();
        attendancesLog.setId(id);
        return attendancesLog;
    }
    

}
