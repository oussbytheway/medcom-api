package com.pharmaresolve.medcom.repository;

import com.pharmaresolve.medcom.domain.Watchlist;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Watchlist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {}
