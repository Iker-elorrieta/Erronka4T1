package Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Jefe extends Persona{
	private String titulo;
	private Date fechaAdquisicion;
	private Float porcentajeEmpresa;

	public Jefe(String dni, String nombre, String apellidos, Date fechaNacimiento, String email, String titulo,
			Date fechaAdquisicion, Float porcentajeEmpresa) {
		super(dni, nombre, apellidos, fechaNacimiento, email);
		this.titulo = titulo;
		this.fechaAdquisicion = fechaAdquisicion;
		this.porcentajeEmpresa = porcentajeEmpresa;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(dni, other.dni);
	}

	
	@Override
	public String toString() {
		return "Jefe [titulo=" + titulo + ", fechaAdquisicion=" + fechaAdquisicion + ", porcentajeEmpresa="
				+ porcentajeEmpresa + ", dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos
				+ ", fechaNacimiento=" + fechaNacimiento + ", email=" + email + "]";
	}

	
	
	


	public String getTitulo() {
		return titulo;
	}

	public Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	public Float getPorcentajeEmpresa() {
		return porcentajeEmpresa;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	public void setPorcentajeEmpresa(Float porcentajeEmpresa) {
		this.porcentajeEmpresa = porcentajeEmpresa;
	}

	
	
	@Override
	public void comprarArticulos(ArrayList<Articulo> carrito) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
}
