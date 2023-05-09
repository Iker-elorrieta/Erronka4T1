package modelo;

import java.util.Date;
import java.util.Objects;

public class Jefe extends Persona{
	private Date fechaAdquisicion;
	private float porcentajeEmpresa;
	private boolean dios;
	private Supermercado supermercado;

	public Jefe(String dni, String nombre, String apellidos, Date fechaNacimiento, String email,String contrasena, tipoPersona tipo,Date fechaAdquisicion, float porcentajeEmpresa,int dios) {
		super(dni, nombre, apellidos, fechaNacimiento, email,contrasena,tipo);
		this.fechaAdquisicion=fechaAdquisicion;
		this.porcentajeEmpresa=porcentajeEmpresa;
		this.dios=mc.pasarIntABoolean(dios);
	}
	public Supermercado getSupermercado() {
		return supermercado;
	}
	public void setSupermercado(Supermercado supermercado) {
		this.supermercado = supermercado;
	}
	public boolean isDios() {
		return dios;
	}
	public void setDios(boolean dios) {
		this.dios = dios;
	}
	public String getFechaAdquisicion() {
		return mc.formatearFecha(fechaAdquisicion);
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
		return "Jefe [dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos+ ", fechaNacimiento=" + fechaNacimiento + ", email=" + email + "]";
	}
	@Override
	public String comprarArticulos(float dinero) {
		// TODO Auto-generated method stub
		return "El precio de recargar los articulos es de "+dinero;
	}
}
