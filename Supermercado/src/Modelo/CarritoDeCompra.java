package Modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class CarritoDeCompra {
	private ArrayList<Object> carrito;
	private int [] cantidades;
	private Double precioTotal;
	private Date fechaCompra;
	private ArrayList<CarritoDeCompra> historial;
	
	public CarritoDeCompra(ArrayList<Object> carrito, int[] cantidades, Double precioTotal, Date fechaCompra,
			ArrayList<CarritoDeCompra> historial) {
		super();
		this.carrito = carrito;
		this.cantidades = cantidades;
		this.precioTotal = precioTotal;
		this.fechaCompra = fechaCompra;
		this.historial = historial;
	}
	public ArrayList<Object> getCarrito() {
		return carrito;
	}
	public void setCarrito(ArrayList<Object> carrito) {
		this.carrito = carrito;
	}
	public int[] getCantidades() {
		return cantidades;
	}
	public void setCantidades(int[] cantidades) {
		this.cantidades = cantidades;
	}
	public Double getPrecioTotal() {
		return precioTotal;
	}
	public void setPrecioTotal(Double precioTotal) {
		this.precioTotal = precioTotal;
	}
	public Date getFechaCompra() {
		return fechaCompra;
	}
	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}
	public ArrayList<CarritoDeCompra> getHistorial() {
		return historial;
	}
	public void setHistorial(ArrayList<CarritoDeCompra> historial) {
		this.historial = historial;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(cantidades);
		result = prime * result + Objects.hash(carrito, fechaCompra, historial, precioTotal);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarritoDeCompra other = (CarritoDeCompra) obj;
		return Arrays.equals(cantidades, other.cantidades) && Objects.equals(carrito, other.carrito)
				&& Objects.equals(fechaCompra, other.fechaCompra) && Objects.equals(historial, other.historial)
				&& Objects.equals(precioTotal, other.precioTotal);
	}
	@Override
	public String toString() {
		return "CarritoDeCompra [carrito=" + carrito + ", cantidades=" + Arrays.toString(cantidades) + ", precioTotal="
				+ precioTotal + ", fechaCompra=" + fechaCompra + ", historial=" + historial + "]";
	}
	
}
