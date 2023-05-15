package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import otros.tipoPersona;

@SuppressWarnings("javadoc")
public class Cliente extends Persona {
	private float dinero;
	private boolean bloqueado;
	private ArrayList<Compra> arrayCompras;
	
	public Cliente(String dni, String nombre, String apellidos, Date fechaNacimiento, String email,String contrasena,tipoPersona tipo, float dinero,int bloqueado) {
		super(dni, nombre, apellidos, fechaNacimiento, email, contrasena,tipo);
		this.dinero = dinero;
		this.bloqueado=mc.pasarIntABoolean(bloqueado);
		arrayCompras=new ArrayList<Compra>();
	}
	@Override
	public String toString() {
		return "Cliente [dinero=" + dinero + ", dni=" + dni + ", nombre="
				+ nombre + ", apellidos=" + apellidos + ", fechaNacimiento=" + fechaNacimiento + ", email=" + email
				+ "]";
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
	
	public float getDinero() {
		return dinero;
	}
	public ArrayList<Compra> getArrayCompras() {
		return arrayCompras;
	}
	public void setDinero(float dinero) {
		this.dinero = dinero;
	}
	public void setArrayCompras(ArrayList<Compra> arrayCompras) {
		this.arrayCompras = arrayCompras;
	}
	public boolean isBloqueado() {
		return bloqueado;
	}
	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}
	@Override
	public String comprarArticulos(float dinero) {
		// TODO Auto-generated method stub
		setDinero(getDinero()-dinero);
		return "El coste de la compra es de "+dinero+" dejandole "+getDinero();
	}
}
