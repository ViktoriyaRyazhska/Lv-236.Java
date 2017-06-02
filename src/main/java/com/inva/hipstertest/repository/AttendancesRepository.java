package com.inva.hipstertest.repository;

import com.inva.hipstertest.domain.Attendances;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the Attendances entity.
 */
@SuppressWarnings("unused")
public interface AttendancesRepository extends JpaRepository<Attendances, Long> {

    @Query("select attendances from Attendances attendances left join attendances.pupil left join attendances.schedule " +
        "where attendances.pupil.id = :pupilId and attendances.schedule.date between :fromDate and :toDate")
    List<Attendances> findAllByPupilIdAndScheduleDateBetween(@Param("pupilId") Long pupilId,
                                                             @Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    //query to find all attendances for lesson and pupil
    @Query(value = "select * from attendances where pupil_id =:pupilId and\n" +
        "schedule_id in(select id from schedule where lesson_id =:lessonId)", nativeQuery = true)
    List<Attendances> findAllByPupilAndLessonId(@Param("pupilId") Long pupilId, @Param("lessonId") Long lessonId);

}
