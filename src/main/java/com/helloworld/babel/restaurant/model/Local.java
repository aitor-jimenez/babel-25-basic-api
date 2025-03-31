package com.helloworld.babel.restaurant.model;

import com.helloworld.babel.restaurant.daos.model.Restaurante;

import java.util.ArrayList;
import java.util.List;

public class Local {
	private String cif;
	private String nombre;
	private String direccion;
	private String telefono;
	private List<Plato> carta = new ArrayList<>();

	public Local(String cif, String nombre, String direccion, String telefono) {
		this.cif = cif;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
	}

	public String getCif() {
		return cif;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public List<Plato> getCarta() {
		return carta;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void addPlato(Plato plato) {
		carta.add(plato);
	}

	public static Local fromRestaurante(Restaurante restaurante) {
		return new Local(
				restaurante.cif(),
				restaurante.nombre(),
				restaurante.direccion(),
				restaurante.telefono());
	}

	public Restaurante toRestaurante() {
		return new Restaurante(cif, nombre, direccion, telefono);
	}
}
