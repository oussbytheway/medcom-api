package com.pharmaresolve.medcom.repository;

import com.pharmaresolve.medcom.domain.WatchListLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WatchListLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WatchListLogRepository extends JpaRepository<WatchListLog, Long> {}
