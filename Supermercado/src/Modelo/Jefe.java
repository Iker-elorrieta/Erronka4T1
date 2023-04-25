package Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Jefe extends Persona{
	private String titulo;

	public Jefe(String dni, String nombre, String apellidos, Date fechaNacimiento, String email,String contrasena, String titulo) {
		super(dni, nombre, apellidos, fechaNacimiento, email,contrasena);
		this.titulo = titulo;
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
		return "Jefe [titulo=" + titulo + ", dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos
				+ ", fechaNacimiento=" + fechaNacimiento + ", email=" + email + "]";
	}
	

	
	
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	@Override
	public void comprarArticulos(ArrayList<Object> carrito) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
}
