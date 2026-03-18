package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author sandra
 *
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    GameService gameService;
    @Autowired
    ClientService clientService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Loan get(Long id) {
        return this.loanRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Loan> findAll() {
        return (List<Loan>) this.loanRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Loan> findPage(LoanSearchDto dto) {

        LoanSpecification spec = new LoanSpecification(dto);
        Pageable pageable = PageRequest.of(dto.getPageable().getPageNumber(), dto.getPageable().getPageSize());

        return this.loanRepository.findAll(spec, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, LoanDto dto) {

        // Validación de juego y cliente si existen
        var game = gameService.get(dto.getGame().getId());
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "GAME_NOT_FOUND");
        }

        var client = clientService.get(dto.getClient().getId());
        if (client == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CLIENT_NOT_FOUND");
        }

        // Validación de rango de fechas
        if (dto.getDateTo().isBefore(dto.getDateFrom())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de fin no puede ser anterior a la de inicidio");
        }

        long daysBetween = ChronoUnit.DAYS.between(dto.getDateFrom(), dto.getDateTo());
        if (daysBetween > 14) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El préstamo no puede durar más de 14 días.");
        }

        boolean gameLent = loanRepository.countOverlappingLoansForGame(dto.getGame().getId(), dto.getDateFrom(), dto.getDateTo(), id);
        if (gameLent) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El juego ya está prestado");
        }

        //Verificar número de préstamos de los clientes
        Long clientLoansObj = loanRepository.countOverlappingLoansForClient(dto.getClient().getId(), dto.getDateFrom(), dto.getDateTo(), id);
        long clientLoans = clientLoansObj == null ? 0L : clientLoansObj;
        if (clientLoans >= 2) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este cliente no puede tener más de 2 préstamos");
        }

        // Crear/recuperar entidad Loan
        Loan loan;
        if (id == null) {
            loan = new Loan();
        } else {
            loan = this.loanRepository.findById(id).orElse(null);
            if (loan == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LOAN_NOT_FOUND");
            }
        }
        loan.setGame(game);
        loan.setClient(client);
        loan.setDateFrom(dto.getDateFrom());
        loan.setDateTo(dto.getDateTo());
        // 7) Guardar
        this.loanRepository.save(loan);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void delete(Long id) throws Exception {

        if (this.loanRepository.findById(id).orElse(null) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El préstamo no existe");
        }

        this.loanRepository.deleteById(id);
    }
}
