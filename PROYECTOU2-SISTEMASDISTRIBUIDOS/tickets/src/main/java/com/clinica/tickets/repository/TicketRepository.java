package com.clinica.tickets.repository;

import com.clinica.tickets.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
    Optional<Ticket> findByNumeroTicket(String numeroTicket);
    
    List<Ticket> findByEstadoOrderByFechaCreacion(String estado);
    
    @Query("SELECT t FROM Ticket t WHERE t.estado = 'pendiente' ORDER BY t.fechaCreacion ASC")
    List<Ticket> findPendingTickets();
    
    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.estado = 'pendiente'")
    Long countPendingTickets();
    
    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.estado = 'pendiente' AND t.moduloDestino = :modulo")
    Long countPendingTicketsByModuloDestino(@Param("modulo") String modulo);
    
    @Query("SELECT t FROM Ticket t WHERE t.estado IN ('pendiente', 'llamado', 'en_atencion') ORDER BY t.fechaCreacion ASC")
    List<Ticket> findActiveTickets();
    
    @Query("SELECT t FROM Ticket t WHERE t.moduloDestino = :modulo AND t.estado IN ('pendiente', 'llamado', 'en_atencion') ORDER BY t.fechaCreacion ASC")
    List<Ticket> findActiveTicketsByModuloDestino(@Param("modulo") String modulo);

    @Query("SELECT t.numeroTicket FROM Ticket t WHERE t.numeroTicket LIKE :prefix ORDER BY CAST(SUBSTRING(t.numeroTicket, 2) AS integer) DESC LIMIT 1")
    String findLastNumeroTicketByPrefix(@Param("prefix") String prefix);
}