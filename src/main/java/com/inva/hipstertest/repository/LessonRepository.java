package com.inva.hipstertest.repository;

import com.inva.hipstertest.domain.Lesson;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Lesson entity.
 */
@SuppressWarnings("unused")
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("select l from Lesson l join l.teachers t where t.id = :teacherId")
    List<Lesson> getAllByTeacherId(@Param("teacherId") Long teacherId);

    @Query("select distinct l from Lesson l left join Schedule s on l.id = s.lesson.id where s.form.id = :formId")
    List<Lesson> findAllByFormId(@Param("formId") Long formId);

}
