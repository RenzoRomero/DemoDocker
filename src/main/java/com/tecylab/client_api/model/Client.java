package com.tecylab.client_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(length =  50, nullable = false)
    private String name;

    @Column(length =  50, nullable = false)
    private String lastName;

    @Column(length =  100, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Status status;

}
