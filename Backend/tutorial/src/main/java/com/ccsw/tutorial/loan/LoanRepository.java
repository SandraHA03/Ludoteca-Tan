package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface LoanRepository extends CrudRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {

    /**
     * Método para recuperar un listado paginado de {@link Loan}
     *
     * @param pageable pageable
     * @return {@link Page} de {@link Loan}
     */
    Page<Loan> findAll(Pageable pageable);

    @Query("SELECT COUNT(1) FROM Loan l " + "WHERE l.client.id = :clientId " + "AND l.dateFrom <= :end " + "AND l.dateTo >= :start " + "AND (:excludeId IS NULL OR l.id <> :excludeId)")
    Long countOverlappingLoansForClient(@Param("clientId") Long clientId, @Param("start") LocalDate start, @Param("end") LocalDate end, @Param("excludeId") Long excludeId);

    @Query("SELECT CASE WHEN COUNT(1) > 0 THEN true ELSE false END FROM Loan l " + "WHERE l.game.id = :gameId " + "AND l.dateFrom <= :end " + "AND l.dateTo >= :start " + "AND (:excludeId IS NULL OR l.id <> :excludeId)")
    boolean countOverlappingLoansForGame(@Param("gameId") Long gameId, @Param("start") LocalDate start, @Param("end") LocalDate end, @Param("excludeId") Long excludeId);

}
