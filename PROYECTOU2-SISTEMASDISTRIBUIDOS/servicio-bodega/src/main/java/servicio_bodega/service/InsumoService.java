package servicio_bodega.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import servicio_bodega.dto.AlertaStockDTO;
import servicio_bodega.model.Insumo;
import servicio_bodega.repository.InsumoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InsumoService {

    @Autowired
    private InsumoRepository insumoRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Value("${bodega.stock.minimo:10}")
    private int stockMinimo;

    public List<Insumo> obtenerTodos() {
        return insumoRepository.findAll();
    }

    public Optional<Insumo> obtenerPorId(Long id) {
        return insumoRepository.findById(id);
    }

    public List<Insumo> obtenerPorCategoria(String categoria) {
        return insumoRepository.findByCategoria(categoria);
    }

    public List<Insumo> buscarPorNombre(String nombre) {
        return insumoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Insumo> obtenerStockBajo() {
        return insumoRepository.findByCantidadLessThan(stockMinimo);
    }

    public List<Insumo> obtenerProximosAVencer(int dias) {
        LocalDate fechaLimite = LocalDate.now().plusDays(dias);
        return insumoRepository.findProximosAVencer(fechaLimite);
    }

    public Insumo guardarInsumo(Insumo insumo) {
        Insumo guardado = insumoRepository.save(insumo);

        // Notificar via WebSocket que se creó un insumo
        AlertaStockDTO alerta = AlertaStockDTO.insumoCreado(
            guardado.getId(), guardado.getNombre(), guardado.getCantidad()
        );
        messagingTemplate.convertAndSend("/topic/bodega", alerta);

        // Verificar si el stock es bajo y emitir alerta adicional
        if (guardado.getCantidad() < stockMinimo) {
            AlertaStockDTO alertaStock = AlertaStockDTO.stockBajo(
                guardado.getId(), guardado.getNombre(), guardado.getCantidad()
            );
            messagingTemplate.convertAndSend("/topic/alertas-stock", alertaStock);
        }

        return guardado;
    }

    public Optional<Insumo> actualizarInsumo(Long id, Insumo datosNuevos) {
        return insumoRepository.findById(id).map(insumo -> {
            insumo.setNombre(datosNuevos.getNombre());
            insumo.setCategoria(datosNuevos.getCategoria());
            insumo.setCantidad(datosNuevos.getCantidad());
            insumo.setLote(datosNuevos.getLote());
            insumo.setFechaVencimiento(datosNuevos.getFechaVencimiento());
            insumo.setUnidad(datosNuevos.getUnidad());
            insumo.setProveedor(datosNuevos.getProveedor());

            Insumo actualizado = insumoRepository.save(insumo);

            // Notificar actualización via WebSocket
            AlertaStockDTO alerta = AlertaStockDTO.insumoActualizado(
                actualizado.getId(), actualizado.getNombre(), actualizado.getCantidad()
            );
            messagingTemplate.convertAndSend("/topic/bodega", alerta);

            // Alerta si stock sigue bajo
            if (actualizado.getCantidad() < stockMinimo) {
                AlertaStockDTO alertaStock = AlertaStockDTO.stockBajo(
                    actualizado.getId(), actualizado.getNombre(), actualizado.getCantidad()
                );
                messagingTemplate.convertAndSend("/topic/alertas-stock", alertaStock);
            }

            return actualizado;
        });
    }

    public boolean eliminarInsumo(Long id) {
        return insumoRepository.findById(id).map(insumo -> {
            insumoRepository.deleteById(id);

            // Notificar eliminación via WebSocket
            AlertaStockDTO alerta = AlertaStockDTO.insumoEliminado(id, insumo.getNombre());
            messagingTemplate.convertAndSend("/topic/bodega", alerta);

            return true;
        }).orElse(false);
    }
}
