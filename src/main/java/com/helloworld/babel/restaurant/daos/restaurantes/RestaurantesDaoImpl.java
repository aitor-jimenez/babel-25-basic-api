package com.helloworld.babel.restaurant.daos.restaurantes;

import com.helloworld.babel.restaurant.daos.model.Restaurante;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class RestaurantesDaoImpl implements RestaurantesDao {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private RowMapper<Restaurante> restauranteRowMapperRowMapper = (rs, rowNum) ->
	{
		String cif = rs.getString("cif");
		String nombre = rs.getString("nombre");
		String direccion = rs.getString("direccion");
		String telefono = rs.getString("telefono");

		return new Restaurante(cif, nombre, direccion, telefono);
	};

	public RestaurantesDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}



	@Override
	public List<Restaurante> getRestaurantes() {
		String sql = "SELECT cif, nombre, direccion, telefono FROM restaurante";
		return jdbcTemplate.query(sql, restauranteRowMapperRowMapper);
	}

	@Override
	public Optional<Restaurante> getRestauranteById(String id) {
		Map<String, Object> params = Map.of("cif", id);
		String sql = "SELECT cif, nombre, direccion, telefono FROM restaurante WHERE cif = :cif";
		try {
			return Optional.of(jdbcTemplate.queryForObject(sql, params, restauranteRowMapperRowMapper));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public int updateRestaurante(Restaurante restaurante) {
		Map<String, Object> params = new HashMap<>();
		params.put("cif", restaurante.cif());
		params.put("nombre", restaurante.nombre());
		params.put("direccion", restaurante.direccion());
		params.put("telefono", restaurante.telefono());
		String sql = "UPDATE restaurante SET nombre = :nombre, direccion = :direccion, telefono = :telefono WHERE cif = :cif";
		return jdbcTemplate.update(sql, params);
	}

	@Override
	public int deleteRestaurante(String cif) {
		Map<String, Object> params = Map.of("cif", cif);
		String sql = "DELETE FROM restaurante WHERE cif = :cif";
		return jdbcTemplate.update(sql, params);
	}

	public int removePlatosFomRestaurante(String cif) {
		Map<String, Object> params = Map.of("cif", cif);
		String sql = "DELETE FROM restaurante_plato WHERE cif_restaurante = :cif";
		return jdbcTemplate.update(sql, params);
	}

	@Override
	public int createRestaurante(Restaurante restaurante) {
		Map<String, Object> params = new HashMap<>();
		params.put("cif", restaurante.cif());
		params.put("nombre", restaurante.nombre());
		params.put("direccion", restaurante.direccion());
		params.put("telefono", restaurante.telefono());
		String sql = "INSERT INTO restaurante (cif, nombre, direccion, telefono) VALUES (:cif, :nombre, :direccion, :telefono)";
		return jdbcTemplate.update(sql, params);
	}

	@Override
	public int addPlato(String cif, int plato) {
		Map<String, Object> params = new HashMap<>();
		params.put("cif", cif);
		params.put("plato", plato);
		String sql = "INSERT INTO restaurante_plato (cif_restaurante, id_plato) VALUES (:cif, :plato)";
		return jdbcTemplate.update(sql, params);
	}

	@Override
	public int removePlato(String cif, int plato) {
		Map<String, Object> params = new HashMap<>();
		params.put("cif", cif);
		params.put("plato", plato);
		String sql = "DELETE FROM restaurante_plato WHERE cif_restaurante = :cif AND id_plato = :plato";
		return jdbcTemplate.update(sql, params);
	}

}
