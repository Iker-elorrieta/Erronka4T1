package modelo;

import java.util.Date;

import controlador.Metodos;
import otros.Comprador;
import otros.tipoPersona;

@SuppressWarnings("javadoc")
public abstract class Persona implements Comprador{
	Metodos mc=new Metodos();
	protected String dni;
	protected String nombre;
	protected String apellidos;
	protected Date fechaNacimiento;
	protected String email;
	protected String contrasena;
	protected tipoPersona tipo;
	
	public Persona(String dni, String nombre, String apellidos, Date fechaNacimiento, String email,String contrasena,tipoPersona tipo) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
		this.contrasena=contrasena;
		this.tipo=tipo;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getFechaNacimiento() {
		return mc.formatearFecha(fechaNacimiento);
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public tipoPersona getTipo() {
		return tipo;
	}
	public void setTipo(tipoPersona tipo) {
		this.tipo = tipo;
	}
	
}
