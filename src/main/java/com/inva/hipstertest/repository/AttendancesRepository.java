package com.inva.hipstertest.repository;

import com.inva.hipstertest.domain.Attendances;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the Attendances entity.
 */
@SuppressWarnings("unused")
public interface AttendancesRepository extends JpaRepository<Attendances, Long> {

    @Query("select a from Attendances a join a.schedule s" +
        " where a.enabled = true and a.pupil.id = :pupilId and s.date between :startDate and :endDate")
    List<Attendances> findAllMembersByPupilIdAndDateBetween(@Param("pupilId") Long pupilId, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);

    @Query("select a from Attendances a where a.enabled = true and a.pupil.id = :pupilId and " +
        "a.schedule.id in (select s.id from Schedule s where s.course.lesson.id = :lessonId)")
    List<Attendances> findAllByPupilAndLessonId(@Param("pupilId") Long pupilId, @Param("lessonId") Long lessonId);

    @Query("select a from Attendances a where a.enabled = true and a.schedule.id in " +
        "(select s.id from Schedule s where s.enabled = true and s.course.teacher.id = :teacherId and s.course.form.id = :formId and s.course.lesson.id = :lessonId)")
    List<Attendances> findAllWherePupilIdInAndScheduleIdIn(@Param("teacherId") Long teacherId, @Param("formId") Long formId, @Param("lessonId") Long lessonId);

}
