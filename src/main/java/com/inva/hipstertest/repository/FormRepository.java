package com.inva.hipstertest.repository;

import com.inva.hipstertest.domain.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the Form entity.
 */
@SuppressWarnings("unused")
public interface FormRepository extends JpaRepository<Form, Long> {

    @Query("select distinct forms from Form forms join forms.schedules schedules where schedules.teacher.id =:teacherId")
    List<Form> findAllByTeacherId(@Param("teacherId") Long teacherId);

    @Query("select t from Form t where t.school.id =:id")
    List<Form> findAllFormsByCurrentSchool(@Param("id") long id);

    @Query("select f from Form f where f.school.id =:id and f.teacher.id is null")
    List<Form> findAllUnassignedFormsByCurrentSchool(@Param("id") long id);

    @Query("select form from Form form where form.teacher.id =:teacherId")
    Form findOneByTeacherId(@Param("teacherId") long teacherId);

    @Query("select teacher.form from Teacher teacher where teacher.id =:teacherId")
    Form findFormByTeacherId(@Param("teacherId") long teacherId);

    @Query("select distinct form from Form form join form.schedules schedule where form.enabled = true and form.school.id = :schoolId" +
        " and schedule.lessonPosition <> :lessonPosition and schedule.date between :date and :endDate")
    List<Form> findAllAvailableByCurrentSchoolAndSearchCriteria(@Param("schoolId") Long schoolId,
                                                                @Param("lessonPosition") Integer lessonPosition,
                                                                @Param("date") ZonedDateTime date,
                                                                @Param("endDate") ZonedDateTime endDate);
}
