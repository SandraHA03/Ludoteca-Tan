package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.game.model.Game;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * @author Sandra
 *
 */

@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game", nullable = false)
    private Game game;

    @ManyToOne  //Un cliente puede tener 2 préstamos activos solo
    @JoinColumn(name = "client", nullable = false)
    private Client client;

    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;

    @Column(name = "date_to", nullable = false)
    private LocalDate dateTo;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id new value of {@link #getId}.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return game
     */
    public Game getGame() {
        return game;
    }

    /**
     * @param game new value of {@link #getGame}.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * @return client
     */
    public Client getClient() {
        return client;
    }

    /**
     * @param client new value of {@link #getClient}.
     */
    public void setClient(Client client) {
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
     * @param datoTo new value of {@link #getDateTo}.
     */
    public void setDateTo(LocalDate datoTo) {
        this.dateTo = datoTo;
    }
}
