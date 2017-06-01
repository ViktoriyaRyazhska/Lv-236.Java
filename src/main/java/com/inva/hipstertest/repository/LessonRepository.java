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

    @Query("select lessons from Lesson lessons join lessons.teachers teacher where teacher.id =:teacherId")
    List<Lesson> getAllLessonsByTeacherId(@Param("teacherId") Long teacherId);

    //TODO: need to rewrite HQL
    @Query(value = "SELECT DISTINCT lesson.id, lesson.name, lesson.enabled FROM lesson LEFT JOIN schedule\n" +
        "ON lesson.id = schedule.lesson_id WHERE form_id =:formId", nativeQuery = true)
    List<Lesson> getDistinctLessonsForForm(@Param("formId") Long formId);

}
