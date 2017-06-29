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

    @Query("select schedule from Schedule schedule where schedule.form.id = :formId")
    List<Schedule> findByFormId(@Param("formId") Long formId);

    @Query("select schedule from Schedule schedule where schedule.enabled = true and " +
        "schedule.teacher.id = :teacherId order by schedule.date")
    List<Schedule> findAllByTeacherIdOrderByDate(@Param("teacherId") Long teacherId);

    @Query("select schedule from Schedule schedule, Teacher teacher, School school where schedule.teacher.id = teacher.id and teacher.school.id = school.id and school.id = :schoolId")
    List<Schedule> findAllBySchoolId(@Param("schoolId") Long schoolId);

//    @Query(value = "SELECT * FROM Schedule schedule WHERE schedule.form_id = :formId AND " +
//        "YEAR(schedule.jhi_date) = YEAR (:date) AND MONTH(schedule.jhi_date) = MONTH (:date) AND DAY(schedule.jhi_date) = DAY (:date)", nativeQuery = true)
//    List<Schedule> findAllByFormIdAndDate(@Param("date") String date, @Param("formId") Long formId);

    @Query("select schedule from Schedule schedule where schedule.form.id = :formId and " +
        "schedule.date between :startDate and :endDate")
    List<Schedule> findAllMembersByFormIdAndDateBetween(@Param("formId") Long formId, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);

//    Identify specific subjects for specific classes where the specific teacher gives lessons
//    @Query("select s from Schedule s where s.enabled = true and s.teacher.id = :teacherId group by s.form.id, s.lesson.id")
//    PostgreSql fix, need correction of database
    @Query(value="select * from schedule s1 right join ( select form_id, lesson_id, max(id) as max_id, max(jhi_date), length(homework), max(lesson_position), count(*), max(classroom_id), max(teacher_id) from schedule where enabled = true and teacher_id = :teacherId group by form_id, lesson_id ) s2 on s1.form_id = s2.form_id and s1.lesson_id = s2.lesson_id and s1.id = s2.max_id", nativeQuery = true)
    List<Schedule> findFormsAndLessonsByTeacherId(@Param("teacherId") Long teacherId);

    // Identify dates, when for specific class, on specific subject, specific teacher gives lessons
    @Query("select s from Schedule s where s.enabled = true and s.teacher.id = :teacherId and s.form.id = :formId and s.lesson.id = :lessonId and s.date <= :today order by s.date")
    Page<Schedule> findSchedulesByTeacherIdFormIdSubjectIdMaxDate(Pageable pageable, @Param("teacherId") Long teacherId, @Param("formId") Long formId, @Param("lessonId") Long lessonId, @Param("today") ZonedDateTime today);

    @Query("select COUNT(s) from Schedule s where s.enabled = true and s.teacher.id = :teacherId and s.form.id = :formId and s.lesson.id = :lessonId and s.date <= :today order by s.date")
    Long countSchedulesForGradeBook(@Param("teacherId") Long teacherId, @Param("formId") Long formId, @Param("lessonId") Long lessonId, @Param("today") ZonedDateTime today);

}
