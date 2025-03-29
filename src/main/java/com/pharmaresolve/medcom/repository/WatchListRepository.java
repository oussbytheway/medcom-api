package com.pharmaresolve.medcom.repository;

import com.pharmaresolve.medcom.domain.WatchList;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WatchList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WatchListRepository extends JpaRepository<WatchList, Long> {}
