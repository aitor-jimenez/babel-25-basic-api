package com.helloworld.babel.restaurant.daos.platos;

import com.helloworld.babel.restaurant.daos.model.Plato;

import java.util.List;
import java.util.Optional;

public interface PlatosDao {
	List<Plato> getPlatos();
	Optional<Plato> getPlatosById(int id);
	List<Plato> getPlatosByRestauranteCif(String cif);
	int updatePlato(Plato plato);
	int deletePlato(int id);
	int removeRestaurantesFromPlato(int id);
	long createPlato(Plato plato);
}
