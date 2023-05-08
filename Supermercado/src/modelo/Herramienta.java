package modelo;

import java.util.Objects;

import controlador.Metodos;

public class Herramienta extends Articulo{
	Metodos mc=new Metodos();
	private boolean electrica;
	private int garantia;
	
	public Herramienta(int idArticulo, String nombreArticulo, String rutaImagen,String descripcion, float precio,
			int stockActual, int electrica, int garantia) {
		super(idArticulo, nombreArticulo, rutaImagen,descripcion, precio, stockActual);
		this.electrica = mc.pasarIntABoolean(electrica);
		this.garantia = garantia;
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
		return "Herramienta [electrica=" + electrica + ", garantia=" + garantia + ", idArticulo=" + idArticulo
				+ ", nombreArticulo=" + nombreArticulo + ", rutaImagen=" + rutaImagen + ", precio=" + precio
				+ ", stockActual=" + stockActual + " ]";
	}
	public Boolean getElectrica() {
		return electrica;
	}
	public int getGarantia() {
		return garantia;
	}
	public void setElectrica(Boolean electrica) {
		this.electrica = electrica;
	}
	public void setGarantia(int garantia) {
		this.garantia = garantia;
	}
}
