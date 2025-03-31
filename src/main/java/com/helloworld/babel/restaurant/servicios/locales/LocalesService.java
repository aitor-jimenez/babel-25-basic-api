package com.helloworld.babel.restaurant.servicios.locales;

import com.helloworld.babel.restaurant.model.Local;
import com.helloworld.babel.restaurant.model.Plato;

import java.util.List;
import java.util.Optional;

public interface LocalesService {
	List<Local> getLocales();
	Optional<Local> getLocalByCif(String cif);
	List<Plato> getPlatosByLocal(String cif);
	Optional<Local> updateLocal(Local local);
	boolean createLocal(Local local);
	boolean deleteLocal(String cif);
	int addPlato(String cif, int plato);
	void removePlato(String cif, int plato);
}
