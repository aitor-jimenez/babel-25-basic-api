package com.helloworld.babel.restaurant.controllers.locales;

import com.helloworld.babel.restaurant.model.Local;
import com.helloworld.babel.restaurant.model.Plato;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LocalesController {
	List<Local> getLocales();
	Local getLocalByCif(String cif);
	ResponseEntity<Void> createOrUpdateLocal(String cif, Local local);
	ResponseEntity<Void> deleteLocal(String cif);
	List<Plato> getPlatos(String cif);
	ResponseEntity<Void> addPlato(String cif, int plato);
	ResponseEntity<Void> removePlato(String cif, int plato);
}
