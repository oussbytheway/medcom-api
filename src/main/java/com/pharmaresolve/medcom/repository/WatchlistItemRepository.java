package com.pharmaresolve.medcom.repository;

import com.pharmaresolve.medcom.domain.WatchlistItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WatchlistItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WatchlistItemRepository extends JpaRepository<WatchlistItem, Long> {}
