package com.helloworld.babel.restaurant.servicios.platos;

import com.helloworld.babel.restaurant.daos.platos.PlatosDao;
import com.helloworld.babel.restaurant.model.Plato;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlatosServiceImpl implements PlatosService {

	private final PlatosDao platosDao;

	public PlatosServiceImpl(PlatosDao platosDao) {
		this.platosDao = platosDao;
	}

	@Override
	public List<Plato> getPlatos() {
		return platosDao.getPlatos().stream().map(Plato::fromPlatoDAO).toList();
	}


	@Override
	public Optional<Plato> getPlatosById(int id) {
		return platosDao.getPlatosById(id).map(Plato::fromPlatoDAO);
	}

	@Override
	public Optional<Plato> updatePlato(Plato plato) {
		int updates = platosDao.updatePlato(plato.toPlatoDAO());
		if (updates == 0) {
			return Optional.empty();
		} else {
			return Optional.of(plato);
		}
	}

	@Override
	@Transactional
	public boolean deletePlato(int id) {
		platosDao.removeRestaurantesFromPlato(id);
		return platosDao.deletePlato(id)>0;
	}

	@Override
	public long createPlato(Plato plato) {
		return platosDao.createPlato(plato.toPlatoDAO());
	}
}
