package Modelo;

import java.util.Objects;

public class Herramienta extends Articulo{
	private Boolean electrica;
	private int garantia;
	

	public Herramienta(int idArticulo, String nombreArticulo, String rutaImagen,String descripcion, Double precio, int stockMaximo,
			int stockActual, tipoArticulo tipo, Boolean electrica, int garantia) {
		super(idArticulo, nombreArticulo, rutaImagen,descripcion, precio, stockMaximo, stockActual, tipo);
		this.electrica = electrica;
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
				+ ", stockMaximo=" + stockMaximo + ", stockActual=" + stockActual + ", tipo=" + tipo + "]";
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
