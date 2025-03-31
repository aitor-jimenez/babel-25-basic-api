package com.helloworld.babel.restaurant.daos.platos;

import com.helloworld.babel.restaurant.daos.model.Plato;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PlatosDaoImpl implements PlatosDao {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private RowMapper<Plato> platoRowMapper = (rs, rowNum) ->
		{
			int id = rs.getInt("id");
			String nombre = rs.getString("nombre");
			double precio = rs.getDouble("precio");
			int categoria = rs.getInt("categoria");

			return new Plato(id, nombre, precio, categoria);
		};

	public PlatosDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Plato> getPlatos() {
		String query = "SELECT id, nombre, precio, categoria FROM plato";
		return jdbcTemplate.query(query, platoRowMapper);
	}

	@Override
	public Optional<Plato> getPlatosById(int platoId) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", platoId);
		String query = "SELECT * FROM plato WHERE id = :id";
		try {
			return Optional.of(jdbcTemplate.queryForObject(query, params, platoRowMapper));
		}catch(EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Plato> getPlatosByRestauranteCif(String cif) {
		Map<String, Object> params = new HashMap<>();
		params.put("cif", cif);
		String query = "SELECT p.id, p.nombre, p.precio, p.categoria " +
					   "FROM plato p JOIN restaurante_plato rp ON p.id = rp.id_plato WHERE rp.cif_restaurante = :cif";
		return jdbcTemplate.query(query, params, platoRowMapper);
	}

	@Override
	public int updatePlato(Plato plato) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", plato.id());
		params.put("nombre", plato.nombre());
		params.put("precio", plato.precio());
		params.put("categoria", plato.categoria());
		String query = "UPDATE plato SET nombre = :nombre, precio = :precio, categoria = :categoria WHERE id = :id";
		return jdbcTemplate.update(query, params);
	}

	@Override
	public int deletePlato(int id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		String query = "DELETE FROM plato WHERE id = :id";
		return jdbcTemplate.update(query, params);
	}

	@Override
	public int removeRestaurantesFromPlato(int id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		String query = "DELETE FROM restaurante_plato WHERE id_plato = :id";
		return jdbcTemplate.update(query, params);
	}

	@Override
	public long createPlato(Plato plato) {
		Map<String, Object> params = new HashMap<>();
		params.put("nombre", plato.nombre());
		params.put("precio", plato.precio());
		params.put("categoria", plato.categoria());

		KeyHolder kh = new GeneratedKeyHolder();

		String query = "INSERT INTO plato (nombre, precio, categoria) VALUES (:nombre, :precio, :categoria)";
		jdbcTemplate.update(query, new MapSqlParameterSource(params), kh);
		return kh.getKey().longValue();
	}
}
