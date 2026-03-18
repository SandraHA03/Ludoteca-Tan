package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.model.GameDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * @author sandra
 *
 */
public class LoanDto {

    private Long id;
    private GameDto game;
    private ClientDto client;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTo;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id new value of{@link #getId}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return game
     */
    public GameDto getGame() {
        return game;
    }

    /**
     * @param game new value of {@link #getGame}.
     */
    public void setGame(GameDto game) {
        this.game = game;
    }

    /**
     * @return client
     */
    public ClientDto getClient() {
        return client;
    }

    /**
     * @param client new value of {@link #getClient}.
     */
    public void setClient(ClientDto client) {
        this.client = client;
    }

    /**
     * @return dateFrom
     */
    public LocalDate getDateFrom() {
        return dateFrom;
    }

    /**
     * @param dateFrom new value of {@link #getDateFrom}.
     */
    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * @return dateTo
     */
    public LocalDate getDateTo() {
        return dateTo;
    }

    /**
     * @param dateTo new value of {@link #getDateTo}.
     */
    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }
}
