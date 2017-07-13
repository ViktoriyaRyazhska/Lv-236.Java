package com.inva.hipstertest.repository;

import com.inva.hipstertest.domain.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the Schedule entity.
 */
@SuppressWarnings("unused")
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select s from Schedule s where s.form.id = :formId")
    List<Schedule> findByFormId(@Param("formId") Long formId);

    @Query("select s from Schedule s where s.enabled = true and s.teacher.id = :teacherId order by s.date")
    List<Schedule> findAllByTeacherIdOrderByDate(@Param("teacherId") Long teacherId);

    @Query("select schedule from Schedule schedule, Teacher teacher, School school where schedule.teacher.id = teacher.id and teacher.school.id = school.id and school.id = :schoolId")
    List<Schedule> findAllBySchoolId(@Param("schoolId") Long schoolId);

    @Query("select s from Schedule s where s.form.id = :formId and s.date between :startDate and :endDate")
    List<Schedule> findAllByFormIdAndDateBetween(@Param("formId") Long formId, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);

    @Query("select s from Schedule s where s.enabled = true and s.teacher.id = :teacherId group by s.form.id, s.lesson.id")
    List<Schedule> findFormsAndLessonsByTeacherId(@Param("teacherId") Long teacherId);

    @Query("select s from Schedule s where s.enabled = true and s.form.id = :formId and s.lesson.id = :lessonId and s.date <= :maxDate order by s.date")
    Page<Schedule> findAllByFormIdLessonIdMaxDate(Pageable pageable, @Param("formId") Long formId, @Param("lessonId") Long lessonId, @Param("maxDate") ZonedDateTime maxDate);

    @Query("select count(s) from Schedule s where s.enabled = true and s.form.id = :formId and s.lesson.id = :lessonId and s.date <= :maxDate order by s.date")
    Long countAllByFormIdLessonIdMaxDate(@Param("formId") Long formId, @Param("lessonId") Long lessonId, @Param("maxDate") ZonedDateTime maxDate);

}
