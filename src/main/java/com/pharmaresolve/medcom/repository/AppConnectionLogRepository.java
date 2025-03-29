package com.pharmaresolve.medcom.repository;

import com.pharmaresolve.medcom.domain.AppConnectionLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AppConnectionLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppConnectionLogRepository extends JpaRepository<AppConnectionLog, Long> {}
