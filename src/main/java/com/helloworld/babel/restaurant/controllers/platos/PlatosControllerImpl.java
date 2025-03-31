package com.helloworld.babel.restaurant.controllers.platos;

import com.helloworld.babel.restaurant.model.Plato;
import com.helloworld.babel.restaurant.servicios.platos.PlatosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Controlador de platos", description = "Operaciones con platos")
@RequestMapping("restaurante/platos")
public class PlatosControllerImpl implements PlatosController {

    private final PlatosService platosService;

    public PlatosControllerImpl(PlatosService platosService) {
        this.platosService = platosService;
    }


    @Override
    @GetMapping("")
    @Operation(summary = "Listado de platos",
            responses = {
                    @ApiResponse(description = "La lista de platos",
                            content = @Content(mediaType = "*/*",
                                    schema = @Schema(implementation = Plato.class),
                                    examples = {
                                            @ExampleObject(name = "Plato de ejemplo",
                                                    value = "[\n  {\n    id: 1,\n    nombre: \"Croquetas de jamón\",\n    precio: 8.5,\n    categoria: \"Entrante\"\n  }\n]",
                                                    summary = "Ejemplo de un plato de la lista")}))})
    public List<Plato> getPlatos() {
        List<Plato> platos = platosService.getPlatos();
        return platos;
    }


    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un plato",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Plato.class))),
                    @ApiResponse(responseCode = "404", description = "Plato no encontrado")})
    public Plato getPlatosById(@PathVariable String id) {
        Optional<Plato> plato = platosService.getPlatosById(Integer.parseInt(id));
        if (plato.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plato no encontrado");
        } else {
            return plato.get();
        }
    }

    @Override
    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un plato",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Plato actualizado"),
                    @ApiResponse(responseCode = "404", description = "Plato a actualizar no encontrado")})
    public ResponseEntity<Void> updatePlato(@PathVariable int id, @RequestBody Plato plato) {
        plato.setId(id);
        Optional<Plato> updatedPlato = platosService.updatePlato(plato);
        if (updatedPlato.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plato no encontrado");
        } else {
            return ResponseEntity.
                    noContent().
                    header("Content-Location", "/restaurante/platos/" + plato.getId()).
                    build();
        }
    }

    @Override
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un plato",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Plato eliminado"),
                    @ApiResponse(responseCode = "404", description = "Plato no encontrado")})
    public ResponseEntity<Void> deletePlato(@PathVariable int id) {
        platosService.deletePlato(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("")
    @Operation(summary = "Añade un plato",
            responses = {
                    @ApiResponse(description = "El ID del plato añadido",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = int.class)))})
    public ResponseEntity<Long> createPlato(@RequestBody Plato plato) {
        long platoCreadoId = platosService.createPlato(plato);
        return ResponseEntity.
                created(URI.create("/restaurante/platos/" + platoCreadoId)).
                body(platoCreadoId);
    }
}
