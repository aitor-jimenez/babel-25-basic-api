package com.helloworld.babel.restaurant.controllers.platos;

import com.helloworld.babel.restaurant.model.Plato;
import com.helloworld.babel.restaurant.servicios.platos.PlatosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("restaurante/platos")
public class PlatosControllerImpl implements PlatosController {

	private final PlatosService platosService;

	public PlatosControllerImpl(PlatosService platosService) {
		this.platosService = platosService;
	}


	@Override
	@GetMapping("")
	public List<Plato> getPlatos() {
		List<Plato> platos = platosService.getPlatos();
		return platos;
	}


	@Override
	@GetMapping("/{id}")
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
	public ResponseEntity<Void> updatePlato(@PathVariable int id, @RequestBody Plato plato) {
		plato.setId(id);
		Optional<Plato> updatedPlato = platosService.updatePlato(plato);
		if (updatedPlato.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plato a actualizar no encontrado");
		} else {
			return ResponseEntity.
					noContent().
					header("Content-Location", "/restaurante/platos/" + plato.getId()).
					build();
		}
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePlato(@PathVariable int id) {
		platosService.deletePlato(id);
		return ResponseEntity.noContent().build();
	}

	@Override
	@PostMapping("")
	public ResponseEntity<Long> createPlato(@RequestBody Plato plato) {
		long platoCreadoId = platosService.createPlato(plato);
		return ResponseEntity.
				created(URI.create("/restaurante/platos/" + platoCreadoId)).
				body(platoCreadoId);
	}
}
