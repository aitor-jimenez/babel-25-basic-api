package com.helloworld.babel.restaurant.controllers.locales;

import com.helloworld.babel.restaurant.model.Local;
import com.helloworld.babel.restaurant.model.Plato;
import com.helloworld.babel.restaurant.servicios.exceptions.NotFoundException;
import com.helloworld.babel.restaurant.servicios.locales.LocalesService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("restaurante/locales")
public class LocalesControllerImpl implements LocalesController {

    private final LocalesService localesService;

    public LocalesControllerImpl(LocalesService localesService) {
        this.localesService = localesService;
    }


    @Override
    @GetMapping("")
    @Operation(summary = "Listado de locales")
    public List<Local> getLocales() {
        return localesService.getLocales();
    }

    @Override
    @GetMapping("/{cif}")
    @Operation(summary = "Obtiene un local")
    public Local getLocalByCif(@PathVariable String cif) {
        Optional<Local> local = localesService.getLocalByCif(cif);
        if (local.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Local no encontrado");
        } else {
            return local.get();
        }
    }

    @Override
    @PutMapping("/{cif}")
    @Operation(summary = "Actualiza un local")
    public ResponseEntity<Void> createOrUpdateLocal(@PathVariable String cif, @RequestBody Local local) {
        local.setCif(cif);
        Optional<Local> updatedLocal = localesService.updateLocal(local);
        if (updatedLocal.isEmpty()) {
            localesService.createLocal(local);
            return ResponseEntity.
                    created(URI.create("/restaurante/locales/" + local.getCif())).
                    build();
        } else {
            return ResponseEntity.
                    noContent().
                    header("Content-Location", "/restaurante/locales/" + local.getCif()).
                    build();
        }
    }

    @Override
    @DeleteMapping("/{cif}")
    @Operation(summary = "Elimina un local")
    public ResponseEntity<Void> deleteLocal(@PathVariable String cif) {
        localesService.deleteLocal(cif);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/{cif}/platos")
    @Operation(summary = "Listado de platos de un local")
    public List<Plato> getPlatos(@PathVariable String cif) {
        try {
            return localesService.getPlatosByLocal(cif);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    @PostMapping("/{cif}/platos")
    @Operation(summary = "Añade un plato a la carta de un local")
    public ResponseEntity<Void> addPlato(@PathVariable String cif, @RequestBody int plato) {
        try {
            if (localesService.addPlato(cif, plato) > 0) {
                return ResponseEntity
                        .created(URI.create("/restaurante/locales/" + cif + "/platos/" + plato))
                        .build();
            } else {
                return ResponseEntity
                        .noContent()
                        .build();
            }
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @Override
    @DeleteMapping("/{cif}/platos/{plato}")
    @Operation(summary = "Elimina un plato de la carta de un local")
    public ResponseEntity<Void> removePlato(@PathVariable String cif, @PathVariable int plato) {
        try {
            localesService.removePlato(cif, plato);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }
}
