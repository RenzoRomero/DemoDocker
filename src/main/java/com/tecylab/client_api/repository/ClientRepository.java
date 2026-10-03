package com.tecylab.client_api.repository;

import com.tecylab.client_api.dto.ClientResponse;
import com.tecylab.client_api.model.Client;
import com.tecylab.client_api.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findClientByName(String name);
    Optional<Client> findClientByEmail(String email);
    List<Client> findClientByStatus(Status status);

}
