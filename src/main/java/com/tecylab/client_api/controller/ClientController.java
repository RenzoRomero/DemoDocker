package com.tecylab.client_api.controller;

import com.tecylab.client_api.dto.ClientRequest;
import com.tecylab.client_api.dto.ClientResponse;
import com.tecylab.client_api.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Client Controller", description = "Controller for managing clients")
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    // Inject the ClientService using constructor injection
    private final ClientService clientService;

    // Constructor for ClientController
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Create a new client", description = "Creates a new client and returns the created client object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/create")
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody ClientRequest clientRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.createClient(clientRequest));
    }

    @Operation(summary = "Get all clients", description = "Retrieves a list of all clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of clients")
    })
    @GetMapping("/all")
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        return ResponseEntity.ok(clientService.findAllClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClientById(@Positive(message = "Client ID must be a positive number")
                                                            @PathVariable Long id) {
        return ResponseEntity.ok(clientService.findClientById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ClientResponse> getClientByName(@PathVariable String name) {
        return ResponseEntity.ok(clientService.findClientByName(name));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClientResponse> getClientByEmail(@Email(message = "Invalid email format")
                                                               @PathVariable String email) {
        return ResponseEntity.ok(clientService.findClientByEmail(email));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable Long id,
                                                       @Valid @RequestBody ClientRequest clientRequest) {
        return ResponseEntity.ok(clientService.updateClient(id, clientRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClientById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/disable/{id}")
    public ResponseEntity<ClientResponse> disableClient(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.disableClient(id));
    }

    @GetMapping("/status")
    public ResponseEntity<List<ClientResponse>> getClientsByStatus() {
        return ResponseEntity.ok(clientService.findClientsByStatus());
    }

}
