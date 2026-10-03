package com.tecylab.client_api.service;

import com.tecylab.client_api.dto.ClientRequest;
import com.tecylab.client_api.dto.ClientResponse;
import com.tecylab.client_api.model.Client;
import com.tecylab.client_api.model.Status;
import com.tecylab.client_api.repository.ClientRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    // Inject the ClientRepository using constructor injection
    private final ClientRepository clientRepository;

    // Constructor for ClientService
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @CacheEvict(value = "clients", key = "'allClients'")
    public ClientResponse createClient(ClientRequest clientRequest) {
        Client client = Client.builder()
                .name(clientRequest.name())
                .lastName(clientRequest.lastName())
                .email(clientRequest.email())
                .status(clientRequest.status())
                .build();

        Client savedClient = clientRepository.save(client);
        return new ClientResponse(
                savedClient.getId(),
                savedClient.getName(),
                savedClient.getLastName(),
                savedClient.getEmail(),
                savedClient.getStatus()
        );
    }

    @Cacheable(value = "clients", key = "'allClients'")
    public List<ClientResponse> findAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(client -> new ClientResponse(
                        client.getId(),
                        client.getName(),
                        client.getLastName(),
                        client.getEmail(),
                        client.getStatus()
                ))
                .collect(Collectors.toList());
    }

    public List<ClientResponse> findClientsByStatus() {
        return clientRepository.findClientByStatus(Status.ACTIVE)
                .stream()
                .map(client -> new ClientResponse(
                        client.getId(),
                        client.getName(),
                        client.getLastName(),
                        client.getEmail(),
                        client.getStatus()
                ))
                .toList();
    }

    @Cacheable(value = "clients", key = "#id")
    public ClientResponse findClientById(Long id) {
        return clientRepository.findById(id)
                .map(client -> new ClientResponse(
                        client.getId(),
                        client.getName(),
                        client.getLastName(),
                        client.getEmail(),
                        client.getStatus()
                ))
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
    }

    @CacheEvict(value = "clients", key = "#id")
    public ClientResponse updateClient(Long id, ClientRequest updatedClient) {
        Client existingClient = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found with id: " + id));

        existingClient.setName(updatedClient.name());
        existingClient.setLastName(updatedClient.lastName());
        existingClient.setEmail(updatedClient.email());
        existingClient.setStatus(updatedClient.status());

        Client savedClient = clientRepository.save(existingClient);
        return new ClientResponse(
                savedClient.getId(),
                savedClient.getName(),
                savedClient.getLastName(),
                savedClient.getEmail(),
                savedClient.getStatus()
        );
    }

    @CacheEvict(value = "clients", allEntries = true)
    public void deleteClientById(Long id) {
        Client existingClient = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
        clientRepository.delete(existingClient);
    }

    public ClientResponse findClientByName(String name) {
        return clientRepository.findClientByName(name)
                .map(client -> new ClientResponse(
                        client.getId(),
                        client.getName(),
                        client.getLastName(),
                        client.getEmail(),
                        client.getStatus()
                ))
                .orElseThrow(() -> new RuntimeException("Client not found with name: " + name));
    }

    public ClientResponse findClientByEmail(String email) {
        return clientRepository.findClientByEmail(email)
                .map(client -> new ClientResponse(
                        client.getId(),
                        client.getName(),
                        client.getLastName(),
                        client.getEmail(),
                        client.getStatus()
                ))
                .orElseThrow(() -> new RuntimeException("Client not found with email: " + email));
    }

    public ClientResponse disableClient(Long id) {
        Client existingClient = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
        existingClient.setStatus(Status.INACTIVE);
        Client savedClient = clientRepository.save(existingClient);
        return new ClientResponse(
                savedClient.getId(),
                savedClient.getName(),
                savedClient.getLastName(),
                savedClient.getEmail(),
                savedClient.getStatus()
        );
    }

}
