package com.helloworld.babel.restaurant.servicios.platos;

import com.helloworld.babel.restaurant.model.Plato;

import java.util.List;
import java.util.Optional;

public interface PlatosService {
	List<Plato> getPlatos();
	Optional<Plato> getPlatosById(int id);
	Optional<Plato> updatePlato(Plato plato);
	boolean deletePlato(int id);
	long createPlato(Plato plato);
}
