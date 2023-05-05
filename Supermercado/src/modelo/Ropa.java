package modelo;

import java.util.Objects;

public class Ropa extends Articulo{
	private String talla;
	private String marca;
	
	public Ropa(int idArticulo, String nombreArticulo, String rutaImagen,String descripcion, float precio,
			int stockActual, tipoArticulo tipo, String talla, String marca) {
		super(idArticulo, nombreArticulo, rutaImagen,descripcion, precio, stockActual, tipo);
		this.talla = talla;
		this.marca = marca;
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
		Articulo other = (Articulo) obj;
		return idArticulo == other.idArticulo;
	}
	@Override
	public String toString() {
		return "Ropa [talla=" + talla + ", marca=" + marca
				+ ", idArticulo=" + idArticulo + ", nombreArticulo=" + nombreArticulo + ", rutaImagen=" + rutaImagen
				+ ", precio=" + precio + ", stockActual=" + stockActual + ", tipo="
				+ tipo + "]";
	}
	public String getTalla() {
		return talla;
	}
	public String getMarca() {
		return marca;
	}
	public void setTalla(String talla) {
		this.talla = talla;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
}
