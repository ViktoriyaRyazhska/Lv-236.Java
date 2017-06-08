package com.inva.hipstertest.repository;

import com.inva.hipstertest.domain.Schedule;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Schedule entity.
 */
@SuppressWarnings("unused")
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    @Query("select schedule from Schedule schedule where schedule.form.id = :formId")
    List<Schedule> findByFormId(@Param("formId") Long formId);

    @Query("select schedule from Schedule schedule where schedule.teacher.id =:teacherId")
    List<Schedule> findAllByTeacherId(@Param("teacherId") Long teacherId);

    @Query("update Schedule schedule set schedule.homework =:homework where schedule.id =:scheduleId")
    void updateHomeworkById(@Param("homework") String homeWork, @Param("scheduleId") Long scheduleId);

    @Query("select schedule from Schedule schedule, Teacher teacher, School school where schedule.teacher.id = teacher.id and teacher.school.id = school.id and school.id = :schoolId")
    List<Schedule> findAllBySchoolId(@Param("schoolId") Long schoolId);

}
