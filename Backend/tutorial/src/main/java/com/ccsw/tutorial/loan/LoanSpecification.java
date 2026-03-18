package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class LoanSpecification implements Specification<Loan> {

    private static final long serialVersionUID = 1L;

    private final LoanSearchDto criteria;

    public LoanSpecification(LoanSearchDto criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Loan> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Predicate predicate = builder.conjunction();

        // Filtro juego
        if (criteria.getGameId() != null) {
            predicate = builder.and(predicate, builder.equal(root.get("game").get("id"), criteria.getGameId()));
        }

        // Filtro cliente
        if (criteria.getClientId() != null) {
            predicate = builder.and(predicate, builder.equal(root.get("client").get("id"), criteria.getClientId()));
        }

        // Filtro Fecha
        if (criteria.getDate() != null && !criteria.getDate().isBlank()) {

            LocalDate searchDate = LocalDate.parse(criteria.getDate());
            Predicate loanDateIsBeforeOrEqual = builder.lessThanOrEqualTo(root.get("dateFrom"), searchDate);
            Predicate returnDateIsAfterOrEqual = builder.greaterThanOrEqualTo(root.get("dateTo"), searchDate);

            predicate = builder.and(predicate, loanDateIsBeforeOrEqual, returnDateIsAfterOrEqual);
        }

        return predicate;
    }
}
