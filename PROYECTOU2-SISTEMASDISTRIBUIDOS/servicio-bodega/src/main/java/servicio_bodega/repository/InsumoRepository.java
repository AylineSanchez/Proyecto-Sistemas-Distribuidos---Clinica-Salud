package servicio_bodega.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import servicio_bodega.model.Insumo;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {

    // Buscar por categoría
    List<Insumo> findByCategoria(String categoria);

    // Buscar insumos con stock bajo (cantidad menor al mínimo)
    List<Insumo> findByCantidadLessThan(int cantidadMinima);

    // Buscar por nombre (ignora mayúsculas/minúsculas)
    List<Insumo> findByNombreContainingIgnoreCase(String nombre);

    // Insumos por vencer en los próximos N días
    @Query("SELECT i FROM Insumo i WHERE i.fechaVencimiento <= :fecha AND i.fechaVencimiento >= CURRENT_DATE")
    List<Insumo> findProximosAVencer(@Param("fecha") LocalDate fecha);
}
