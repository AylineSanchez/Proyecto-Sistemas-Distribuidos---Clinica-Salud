package com.clinica.tickets.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String numeroTicket;
    
    private String tipoAtencion;
    private String nombrePaciente;
    private String rut;
    
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaLlamado;
    private LocalDateTime fechaAtencion;
    
    private String estado;
    
    @Column(length = 50)
    private String moduloAsignado;
    
    @Column(length = 50)
    private String moduloDestino;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (estado == null) estado = "pendiente";
    }
}