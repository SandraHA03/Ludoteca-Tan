package com.ccsw.tutorial.client;

import com.ccsw.tutorial.client.model.Client;
import org.springframework.data.repository.CrudRepository;

/**
 * @author sandra
 *
 */
public interface ClientRepository extends CrudRepository<Client, Long> {

    // Devuelve true si existe un cliente con ese nombre (exacto)
    boolean existsByName(String name);

}
