package com.tecylab.client_api.dto;

import com.tecylab.client_api.model.Status;

public record ClientResponse(

        Long id,
        String name,
        String lastName,
        String email,
        Status status

) {
}
