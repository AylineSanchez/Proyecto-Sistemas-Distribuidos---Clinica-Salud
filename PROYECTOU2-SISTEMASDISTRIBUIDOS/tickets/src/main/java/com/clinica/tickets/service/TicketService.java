package com.clinica.tickets.service;

import com.clinica.tickets.model.Ticket;
import com.clinica.tickets.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TicketService {
    
    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    private final ConcurrentHashMap<String, Boolean> moduloEnLlamada = new ConcurrentHashMap<>();
    
    @PostConstruct
    public void init() {
        moduloEnLlamada.clear();
        System.out.println("✅ Estado de módulos inicializado");
    }
    
    @Transactional
    public Ticket crearTicket(String tipoAtencion, String nombrePaciente, String rut, String moduloDestino) {
        String prefix = switch (tipoAtencion) {
            case "Consulta Médica" -> "A";
            case "Toma de Muestras" -> "B";
            case "Retiro de Resultados" -> "C";
            default -> "Z";
        };
        
        // Buscar el último número de ticket para este prefijo
        String lastNumeroTicket = ticketRepository.findLastNumeroTicketByPrefix(prefix + "%");
        int siguienteNumero = 1;
        
        if (lastNumeroTicket != null && lastNumeroTicket.length() > 1) {
            try {
                String numeroParte = lastNumeroTicket.substring(1);
                siguienteNumero = Integer.parseInt(numeroParte) + 1;
            } catch (NumberFormatException e) {
                siguienteNumero = 1;
            }
        }
        
        String numeroTicket = String.format("%s%03d", prefix, siguienteNumero);
        
        Ticket ticket = new Ticket();
        ticket.setNumeroTicket(numeroTicket);
        ticket.setTipoAtencion(tipoAtencion);
        ticket.setNombrePaciente(nombrePaciente);
        ticket.setRut(rut);
        ticket.setEstado("pendiente");
        ticket.setFechaCreacion(LocalDateTime.now());
        ticket.setModuloDestino(moduloDestino);
        
        Ticket saved = ticketRepository.save(ticket);
        messagingTemplate.convertAndSend("/topic/nuevo-ticket", saved);
        
        System.out.println("✅ Ticket creado: " + numeroTicket + " (siguiente número para " + prefix + " es " + siguienteNumero + ")");
        
        return saved;
    }
    
    public List<Ticket> getTicketsPorModulo(String modulo) {
        return ticketRepository.findActiveTicketsByModuloDestino(modulo);
    }
    
    public Long getCantidadEsperaPorModulo(String modulo) {
        return ticketRepository.countPendingTicketsByModuloDestino(modulo);
    }
    
    public boolean isModuloOcupado(String modulo) {
        return moduloEnLlamada.getOrDefault(modulo, false);
    }
    
    public void limpiarEstadoModulo(String modulo) {
        moduloEnLlamada.remove(modulo);
    }
    
    public void limpiarTodosModulos() {
        moduloEnLlamada.clear();
    }
    
    @Transactional
    public Ticket llamarTicketPorId(Long id, String modulo) {
        if (moduloEnLlamada.getOrDefault(modulo, false)) {
            throw new RuntimeException("OCUPADO:" + modulo);
        }
        
        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
        
        if (!modulo.equals(ticket.getModuloDestino())) {
            throw new RuntimeException("NO_AUTORIZADO: Este ticket no está asignado a " + modulo);
        }
        
        if (!"pendiente".equals(ticket.getEstado())) {
            throw new RuntimeException("El ticket no está pendiente");
        }
        
        moduloEnLlamada.put(modulo, true);
        
        ticket.setEstado("llamado");
        ticket.setFechaLlamado(LocalDateTime.now());
        ticket.setModuloAsignado(modulo);
        
        Ticket updated = ticketRepository.save(ticket);
        messagingTemplate.convertAndSend("/topic/llamado-ticket", updated);
        
        return updated;
    }
    
    @Transactional
    public Ticket atenderTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
        
        String modulo = ticket.getModuloAsignado();
        
        ticket.setEstado("atendido");
        ticket.setFechaAtencion(LocalDateTime.now());
        
        Ticket updated = ticketRepository.save(ticket);
        
        if (modulo != null && !modulo.isEmpty()) {
            moduloEnLlamada.remove(modulo);
        }
        
        messagingTemplate.convertAndSend("/topic/atendido-ticket", updated);
        
        return updated;
    }
    
    @Transactional
    public Ticket resetearTicket(String numeroTicket) {
        Ticket ticket = ticketRepository.findByNumeroTicket(numeroTicket)
            .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
        
        String modulo = ticket.getModuloAsignado();
        
        if ("llamado".equals(ticket.getEstado()) || "en_atencion".equals(ticket.getEstado())) {
            ticket.setEstado("pendiente");
            ticket.setFechaLlamado(null);
            ticket.setModuloAsignado(null);
            
            Ticket updated = ticketRepository.save(ticket);
            
            if (modulo != null && !modulo.isEmpty()) {
                moduloEnLlamada.remove(modulo);
            }
            
            messagingTemplate.convertAndSend("/topic/nuevo-ticket", updated);
            messagingTemplate.convertAndSend("/topic/atendido-ticket", updated);
            
            return updated;
        }
        
        throw new RuntimeException("El ticket no está en estado llamado o en_atencion");
    }
    
    public List<Ticket> verColaActual() {
        return ticketRepository.findActiveTickets();
    }
    
    public Long cantidadEnEspera() {
        return ticketRepository.countPendingTickets();
    }
}