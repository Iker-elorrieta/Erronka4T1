package Modelo;

import java.util.Objects;

public abstract class Articulo {
	public enum tipoArticulo {
		Herramienta, Ropa, Comida;
	}
	protected int idArticulo;
	protected String nombreArticulo;
	protected Double precio;
	protected int stockMaximo;
	protected int stockActual;
	protected tipoArticulo type;
	
	public Articulo(int idArticulo, String nombreArticulo, Double precio, int stockMaximo, int stockActual,
			tipoArticulo type) {
		super();
		this.idArticulo = idArticulo;
		this.nombreArticulo = nombreArticulo;
		this.precio = precio;
		this.stockMaximo = stockMaximo;
		this.stockActual = stockActual;
		this.type = type;
	}



	
	
	
	
	
	
	
	
	
	
	
	public int getIdArticulo() {
		return idArticulo;
	}
	public String getNombreArticulo() {
		return nombreArticulo;
	}
	public Double getPrecio() {
		return precio;
	}
	public int getStockMaximo() {
		return stockMaximo;
	}
	public int getStockActual() {
		return stockActual;
	}
	public tipoArticulo getType() {
		return type;
	}
	public void setIdArticulo(int idArticulo) {
		this.idArticulo = idArticulo;
	}
	public void setNombreArticulo(String nombreArticulo) {
		this.nombreArticulo = nombreArticulo;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public void setStockMaximo(int stockMaximo) {
		this.stockMaximo = stockMaximo;
	}
	public void setStockActual(int stockActual) {
		this.stockActual = stockActual;
	}
	public void setType(tipoArticulo type) {
		this.type = type;
	}
	
	
}
