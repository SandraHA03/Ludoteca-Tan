package com.ccsw.tutorial.client;

import com.ccsw.tutorial.client.exception.ClientAlreadyExistsException;
import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientTest {

    public static final Long EXISTING_ID = 1L;
    public static final Long NOT_EXISTING_ID = 99L;

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientServiceImpl clientService;

    @Test
    public void getExistingIdShouldReturnClient() {

        Client client = new Client();
        client.setId(EXISTING_ID);

        when(clientRepository.findById(EXISTING_ID)).thenReturn(Optional.of(client));

        Client result = clientService.get(EXISTING_ID);

        assertNotNull(result);
        assertEquals(EXISTING_ID, result.getId());
    }

    @Test
    public void getNotExistingIdShouldReturnNull() {

        when(clientRepository.findById(NOT_EXISTING_ID)).thenReturn(Optional.empty());

        Client result = clientService.get(NOT_EXISTING_ID);

        assertNull(result);
    }

    @Test
    public void saveWithExistingNameShouldThrowException() {

        ClientDto dto = new ClientDto();
        dto.setName("Duplicado");

        when(clientRepository.existsByName("Duplicado")).thenReturn(true);

        assertThrows(ClientAlreadyExistsException.class, () -> clientService.save(null, dto));
    }

    @Test
    public void saveNewClientShouldPersist() {

        ClientDto dto = new ClientDto();
        dto.setName("Nuevo");

        when(clientRepository.existsByName("Nuevo")).thenReturn(false);

        clientService.save(null, dto);

        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    public void deleteExistingIdShouldDelete() throws Exception {

        Client client = new Client();
        client.setId(EXISTING_ID);

        when(clientRepository.findById(EXISTING_ID)).thenReturn(Optional.of(client));

        clientService.delete(EXISTING_ID);

        verify(clientRepository, times(1)).deleteById(EXISTING_ID);
    }

    @Test
    public void deleteNotExistingIdShouldThrowException() {

        when(clientRepository.findById(NOT_EXISTING_ID)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> clientService.delete(NOT_EXISTING_ID));
    }
}