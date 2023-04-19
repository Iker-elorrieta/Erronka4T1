package Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Cliente extends Persona {
	
	private double dinero;
	private boolean tarjetaCliente;
	private CarritoDeCompra carrito;
	
	

	public Cliente(String dni, String nombre, String apellidos, Date fechaNacimiento, String email, double dinero,
			boolean tarjetaCliente) {
		super(dni, nombre, apellidos, fechaNacimiento, email);
		this.dinero = dinero;
		this.tarjetaCliente = tarjetaCliente;
	}
	
	
	public double getDinero() {
		return dinero;
	}

	public void setDinero(double dinero) {
		this.dinero = dinero;
	}

	public boolean isTarjetaCliente() {
		return tarjetaCliente;
	}

	public void setTarjetaCliente(boolean tarjetaCliente) {
		this.tarjetaCliente = tarjetaCliente;
	}

	@Override
	public void comprarArticulos(ArrayList<Object> carrito) {
		// TODO Auto-generated method stub
		
	}

	public CarritoDeCompra getCarrito() {
		return carrito;
	}

	public void setCarrito(CarritoDeCompra carrito) {
		this.carrito = carrito;
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
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(dni, other.dni);
	}
	
	

	@Override
	public String toString() {
		return "Cliente [dinero=" + dinero + ", tarjetaCliente=" + tarjetaCliente + ", carrito=" + carrito + ", dni="
				+ dni + ", nombre=" + nombre + ", apellidos=" + apellidos + ", fechaNacimiento=" + fechaNacimiento
				+ ", email=" + email + "]";
	}

}
