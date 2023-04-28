package Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Jefe extends Persona{
	private String titulo;
	private Date fechaAdquisicion;
	private float porcentajeEmpresa;
	private boolean dios;

	public Jefe(String dni, String nombre, String apellidos, Date fechaNacimiento, String email,String contrasena, String titulo,
			Date fechaAdquisicion, float porcentajeEmpresa,boolean dios) {
		super(dni, nombre, apellidos, fechaNacimiento, email,contrasena);
		this.titulo = titulo;
		this.fechaAdquisicion=fechaAdquisicion;
		this.porcentajeEmpresa=porcentajeEmpresa;
		this.dios=dios;
	}
	
	
	public boolean isDios() {
		return dios;
	}


	public void setDios(boolean dios) {
		this.dios = dios;
	}


	public Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}


	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}


	public float getPorcentajeEmpresa() {
		return porcentajeEmpresa;
	}


	public void setPorcentajeEmpresa(float porcentajeEmpresa) {
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
	public void comprarArticulos(Compra compra,ArrayList<ArticulosComprados> arc) {
		// TODO Auto-generated method stub
		
	}
}
