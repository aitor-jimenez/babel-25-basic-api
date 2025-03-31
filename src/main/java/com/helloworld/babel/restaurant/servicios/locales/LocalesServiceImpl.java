package com.helloworld.babel.restaurant.servicios.locales;

import com.helloworld.babel.restaurant.daos.platos.PlatosDao;
import com.helloworld.babel.restaurant.servicios.exceptions.NotFoundException;
import com.helloworld.babel.restaurant.daos.restaurantes.RestaurantesDao;
import com.helloworld.babel.restaurant.model.Local;
import com.helloworld.babel.restaurant.model.Plato;
import com.helloworld.babel.restaurant.servicios.platos.PlatosService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LocalesServiceImpl implements LocalesService {

	private final RestaurantesDao restaurantesDao;
	private final PlatosDao platosDao;
	private final PlatosService platosService;

	public LocalesServiceImpl(RestaurantesDao restaurantesDao, PlatosDao platosDao, PlatosService platosService) {
		this.restaurantesDao = restaurantesDao;
		this.platosDao = platosDao;
		this.platosService = platosService;
	}


	@Override
	public List<Local> getLocales() {
		List<Local> locales = restaurantesDao
				.getRestaurantes()
				.stream()
				.map(Local::fromRestaurante)
				.toList();
		locales.forEach(this::fullFillWithPlatos);
		return locales;
	}

	@Override
	public Optional<Local> getLocalByCif(String cif) {
		Optional<Local> local = restaurantesDao.getRestauranteById(cif).map(Local::fromRestaurante);
		local.ifPresent(this::fullFillWithPlatos);
		return local;
	}

	@Override
	public List<Plato> getPlatosByLocal(String cif) {
		Optional<Local> local = getLocalByCif(cif);
		if (local.isEmpty()) {
			throw new NotFoundException("Local no encontrado");
		}
		return local.get().getCarta();
	}

	private void fullFillWithPlatos(Local local) {
		platosDao
				.getPlatosByRestauranteCif(local.getCif())
				.stream()
				.map(Plato::fromPlatoDAO)
				.forEach(local::addPlato);
	}

	@Override
	public Optional<Local> updateLocal(Local local) {
		int updates = restaurantesDao.updateRestaurante(local.toRestaurante());
		if (updates == 0) {
			return Optional.empty();
		} else {
			return Optional.of(local);
		}
	}

	@Override
	public boolean createLocal(Local local) {
		return restaurantesDao.createRestaurante(local.toRestaurante())>0;
	}

	@Transactional
	@Override
	public boolean deleteLocal(String cif) {
		restaurantesDao.removePlatosFomRestaurante(cif);
		return restaurantesDao.deleteRestaurante(cif) > 0;
	}

	@Override
	public int addPlato(String cif, int plato) {
		Optional<Local> local = getLocalByCif(cif);
		if (local.isEmpty()) {
			throw new NotFoundException("Local no encontrado");
		}

		if (local.get().getCarta().stream().anyMatch(p -> p.getId() == plato)) {
			//El plato ya está en la carta
			return 0;
		}

		Optional<Plato> platoDisponible = platosService.getPlatosById(plato);
		if (platoDisponible.isEmpty()) {
			throw new NotFoundException("Plato no encontrado");
		}

		return restaurantesDao.addPlato(cif, plato);
	}

	@Override
	public void removePlato(String cif, int plato) {
		Optional<Local> local = getLocalByCif(cif);
		if (local.isEmpty()) {
			throw new NotFoundException("Local no encontrado");
		}

		if (local.get().getCarta().stream().noneMatch(p -> p.getId() == plato)) {
			//El plato no está en la carta
			return;
		}

		restaurantesDao.removePlato(cif, plato);
	}
}
