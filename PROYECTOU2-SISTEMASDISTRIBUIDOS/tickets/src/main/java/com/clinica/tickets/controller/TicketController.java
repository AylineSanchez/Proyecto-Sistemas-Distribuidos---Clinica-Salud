package com.clinica.tickets.controller;

import com.clinica.tickets.model.Ticket;
import com.clinica.tickets.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TicketController {
    
    @Autowired
    private TicketService ticketService;
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/panel")
    public String panel(Model model) {
        return "panel";
    }
    
    @GetMapping("/pantalla")
    public String pantalla() {
        return "pantalla";
    }
    
    @PostMapping("/api/tickets")
    @ResponseBody
    public Ticket crearTicket(@RequestBody Map<String, String> datos) {
        return ticketService.crearTicket(
            datos.get("tipoAtencion"),
            datos.get("nombrePaciente"),
            datos.get("rut"),
            datos.get("moduloDestino")
        );
    }
    
    @GetMapping("/api/cola/modulo")
    @ResponseBody
    public List<Ticket> getColaPorModulo(@RequestParam String modulo) {
        return ticketService.getTicketsPorModulo(modulo);
    }
    
    @GetMapping("/api/en-espera/modulo")
    @ResponseBody
    public Map<String, Object> getEnEsperaPorModulo(@RequestParam String modulo) {
        Map<String, Object> response = new HashMap<>();
        response.put("cantidad", ticketService.getCantidadEsperaPorModulo(modulo));
        response.put("modulo", modulo);
        response.put("ocupado", ticketService.isModuloOcupado(modulo));
        return response;
    }
    
    @PostMapping("/api/llamar/{id}")
    @ResponseBody
    public Ticket llamarTicketPorId(@PathVariable Long id, @RequestBody Map<String, String> datos) {
        String modulo = datos.get("modulo");
        return ticketService.llamarTicketPorId(id, modulo);
    }
    
    @PostMapping("/api/atender/{id}")
    @ResponseBody
    public Ticket atenderTicket(@PathVariable Long id) {
        return ticketService.atenderTicket(id);
    }
    
    @PostMapping("/api/resetear/{numeroTicket}")
    @ResponseBody
    public Ticket resetearTicket(@PathVariable String numeroTicket) {
        return ticketService.resetearTicket(numeroTicket);
    }
    
    @PostMapping("/api/limpiar-modulos")
    @ResponseBody
    public Map<String, String> limpiarModulos() {
        ticketService.limpiarTodosModulos();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Todos los módulos han sido liberados");
        return response;
    }
    
    @PostMapping("/api/limpiar-modulo/{modulo}")
    @ResponseBody
    public Map<String, String> limpiarModulo(@PathVariable String modulo) {
        ticketService.limpiarEstadoModulo(modulo);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Módulo " + modulo + " liberado");
        return response;
    }
    
    @GetMapping("/api/cola")
    @ResponseBody
    public List<Ticket> verCola() {
        return ticketService.verColaActual();
    }
    
    @GetMapping("/api/en-espera")
    @ResponseBody
    public Map<String, Long> cantidadEnEspera() {
        Map<String, Long> response = new HashMap<>();
        response.put("cantidad", ticketService.cantidadEnEspera());
        return response;
    }
}