 package modelo;

import java.util.Objects;

public class ArticuloComprado {
	private int codigoCompra;
	private int idArticulo;
	private int cantidad;
	private float precioArt;
	
	public ArticuloComprado(int codigoCompra, int idArticulo, int cantidad,float precioArt) {
		super();
		this.codigoCompra = codigoCompra;
		this.idArticulo = idArticulo;
		this.precioArt=precioArt;
		this.cantidad = cantidad;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idArticulo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		ArticuloComprado other = (ArticuloComprado) obj;
		return idArticulo == other.idArticulo;
	}

	@Override
	public String toString() {
		return "ArticulosComprados [codigoCompra=" + codigoCompra + ", idArticulo=" + idArticulo + ", cantidad="
				+ cantidad + "]";
	}
	public int getCodigoCompra() {
		return codigoCompra;
	}
	public int getIdArticulo() {
		return idArticulo;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCodigoCompra(int codigoCompra) {
		this.codigoCompra = codigoCompra;
	}
	public void setIdArticulo(int idArticulo) {
		this.idArticulo = idArticulo;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public float getPrecioArt() {
		return precioArt;
	}
	public void setPrecioArt(float precioArt) {
		this.precioArt = precioArt;
	}	
}
