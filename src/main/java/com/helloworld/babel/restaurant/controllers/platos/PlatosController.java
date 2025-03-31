package com.helloworld.babel.restaurant.controllers.platos;

import com.helloworld.babel.restaurant.model.Plato;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PlatosController {
	List<Plato> getPlatos();
	Plato getPlatosById(String id);
	ResponseEntity<Void> updatePlato(int id, Plato plato);
	ResponseEntity<Void> deletePlato(int id);
	ResponseEntity<Long> createPlato(Plato plato);
}
