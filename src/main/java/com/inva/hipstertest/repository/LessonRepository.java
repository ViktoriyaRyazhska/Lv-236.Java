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

    @Query(value = "select scd.homework from schedule as scd " +
        "where scd.enabled = 1 and scd.lesson_id =: lessonId and scd.form_id =: formId " +
        "and date(scd.jhi_date) <  curdate() order by scd.jhi_date desc limit 1", nativeQuery = true)
    String getLastHomeworkByLessonIdAndByFormId(@Param("lessonId") Long lessonId, @Param("formId") Long formId);

    @Query("select lessons from Lesson lessons join lessons.teachers teacher where teacher.id =:teacherId")
    List<Lesson> getAllLessonsByTeacherId(@Param("teacherId") Long teacherId);
}
