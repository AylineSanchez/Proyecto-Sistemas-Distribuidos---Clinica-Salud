package servicio_bodega.dto;

import java.time.LocalDateTime;

public class AlertaStockDTO {

    private String tipo;
    private String mensaje;
    private Long insumoId;
    private String nombreInsumo;
    private Integer cantidadActual;
    private LocalDateTime timestamp;

    public AlertaStockDTO() {}

    public AlertaStockDTO(String tipo, String mensaje, Long insumoId,
                          String nombreInsumo, Integer cantidadActual, LocalDateTime timestamp) {
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.insumoId = insumoId;
        this.nombreInsumo = nombreInsumo;
        this.cantidadActual = cantidadActual;
        this.timestamp = timestamp;
    }

    public static AlertaStockDTO stockBajo(Long id, String nombre, int cantidad) {
        return new AlertaStockDTO("STOCK_BAJO",
            "⚠️ Stock bajo: " + nombre + " tiene solo " + cantidad + " unidades",
            id, nombre, cantidad, LocalDateTime.now());
    }

    public static AlertaStockDTO insumoCreado(Long id, String nombre, int cantidad) {
        return new AlertaStockDTO("INSUMO_CREADO",
            "✅ Nuevo insumo registrado: " + nombre,
            id, nombre, cantidad, LocalDateTime.now());
    }

    public static AlertaStockDTO insumoActualizado(Long id, String nombre, int cantidad) {
        return new AlertaStockDTO("INSUMO_ACTUALIZADO",
            "🔄 Insumo actualizado: " + nombre + " → " + cantidad + " unidades",
            id, nombre, cantidad, LocalDateTime.now());
    }

    public static AlertaStockDTO insumoEliminado(Long id, String nombre) {
        return new AlertaStockDTO("INSUMO_ELIMINADO",
            "🗑️ Insumo eliminado: " + nombre,
            id, nombre, 0, LocalDateTime.now());
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public Long getInsumoId() { return insumoId; }
    public void setInsumoId(Long insumoId) { this.insumoId = insumoId; }

    public String getNombreInsumo() { return nombreInsumo; }
    public void setNombreInsumo(String nombreInsumo) { this.nombreInsumo = nombreInsumo; }

    public Integer getCantidadActual() { return cantidadActual; }
    public void setCantidadActual(Integer cantidadActual) { this.cantidadActual = cantidadActual; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
