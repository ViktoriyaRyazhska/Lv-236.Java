package com.inva.hipstertest.repository;

import com.inva.hipstertest.domain.AttendancesLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AttendancesLog entity.
 */
@SuppressWarnings("unused")
public interface AttendancesLogRepository extends JpaRepository<AttendancesLog,Long> {

}
