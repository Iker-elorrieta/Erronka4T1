package Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Cliente extends Persona {
	
	private double dinero;
	private boolean tarjetaCliente;
	private ArrayList<Compra> arrayCompras;
	
	public Cliente(String dni, String nombre, String apellidos, Date fechaNacimiento, String email, double dinero,
			boolean tarjetaCliente, ArrayList<Compra> arrayCompras) {
		super(dni, nombre, apellidos, fechaNacimiento, email);
		this.dinero = dinero;
		this.tarjetaCliente = tarjetaCliente;
		this.arrayCompras = arrayCompras;
	}

	

	@Override
	public String toString() {
		return "Cliente [dinero=" + dinero + ", tarjetaCliente=" + tarjetaCliente + ", dni=" + dni + ", nombre="
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
	
	

	
	public double getDinero() {
		return dinero;
	}
	public boolean isTarjetaCliente() {
		return tarjetaCliente;
	}
	public ArrayList<Compra> getArrayCompras() {
		return arrayCompras;
	}
	public void setDinero(double dinero) {
		this.dinero = dinero;
	}
	public void setTarjetaCliente(boolean tarjetaCliente) {
		this.tarjetaCliente = tarjetaCliente;
	}
	public void setArrayCompras(ArrayList<Compra> arrayCompras) {
		this.arrayCompras = arrayCompras;
	}



	@Override
	public void comprarArticulos(ArrayList<Object> carrito) {
		// TODO Auto-generated method stub
		
	}

}
