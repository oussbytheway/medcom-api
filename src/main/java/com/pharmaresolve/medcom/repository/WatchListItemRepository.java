package com.pharmaresolve.medcom.repository;

import com.pharmaresolve.medcom.domain.WatchListItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WatchListItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WatchListItemRepository extends JpaRepository<WatchListItem, Long> {}
