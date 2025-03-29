package com.pharmaresolve.medcom.repository;

import com.pharmaresolve.medcom.domain.Pharmacy;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Pharmacy entity.
 */
@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    default Optional<Pharmacy> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Pharmacy> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Pharmacy> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select pharmacy from Pharmacy pharmacy left join fetch pharmacy.admin",
        countQuery = "select count(pharmacy) from Pharmacy pharmacy"
    )
    Page<Pharmacy> findAllWithToOneRelationships(Pageable pageable);

    @Query("select pharmacy from Pharmacy pharmacy left join fetch pharmacy.admin")
    List<Pharmacy> findAllWithToOneRelationships();

    @Query("select pharmacy from Pharmacy pharmacy left join fetch pharmacy.admin where pharmacy.id =:id")
    Optional<Pharmacy> findOneWithToOneRelationships(@Param("id") Long id);
}
