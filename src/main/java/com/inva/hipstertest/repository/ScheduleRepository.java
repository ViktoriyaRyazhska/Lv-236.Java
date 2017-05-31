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
    @Query("select schedule from Schedule schedule where schedule.form.id =:formId")
    List<Schedule> findByFormIdAndMonth(@Param("formId") Long formId);
}
