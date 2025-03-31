package com.helloworld.babel.restaurant.daos.restaurantes;

import com.helloworld.babel.restaurant.daos.model.Restaurante;

import java.util.List;
import java.util.Optional;

public interface RestaurantesDao {
	List<Restaurante> getRestaurantes();
	Optional<Restaurante> getRestauranteById(String cif);
	int updateRestaurante(Restaurante restaurante);
	int deleteRestaurante(String cif);
	int removePlatosFomRestaurante(String cif);
	int createRestaurante(Restaurante restaurante);
	int addPlato(String cif, int plato);
	int removePlato(String cif, int plato);
}
