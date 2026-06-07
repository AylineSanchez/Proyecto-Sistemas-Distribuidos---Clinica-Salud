package servicio_bodega.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import servicio_bodega.model.Insumo;
import servicio_bodega.service.InsumoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/insumos")
public class InsumoController {

    @Autowired
    private InsumoService insumoService;

    @GetMapping
    public ResponseEntity<List<Insumo>> listarTodos() {
        return ResponseEntity.ok(insumoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Insumo> obtenerPorId(@PathVariable Long id) {
        return insumoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Insumo>> porCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(insumoService.obtenerPorCategoria(categoria));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Insumo>> buscar(@RequestParam String nombre) {
        return ResponseEntity.ok(insumoService.buscarPorNombre(nombre));
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<List<Insumo>> stockBajo() {
        return ResponseEntity.ok(insumoService.obtenerStockBajo());
    }

    @GetMapping("/proximos-vencer")
    public ResponseEntity<List<Insumo>> proximosAVencer(
            @RequestParam(defaultValue = "30") int dias) {
        return ResponseEntity.ok(insumoService.obtenerProximosAVencer(dias));
    }

    @PostMapping
    public ResponseEntity<Insumo> crear(@Valid @RequestBody Insumo insumo) {
        Insumo creado = insumoService.guardarInsumo(insumo);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Insumo> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Insumo insumo) {
        return insumoService.actualizarInsumo(id, insumo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        boolean eliminado = insumoService.eliminarInsumo(id);
        if (eliminado) {
            return ResponseEntity.ok(Map.of("mensaje", "Insumo eliminado correctamente"));
        }
        return ResponseEntity.notFound().build();
    }
}
