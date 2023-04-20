package Modelo;

import java.util.Objects;

public class Herramienta extends Articulo{
	private Boolean electrica;
	private int garantia;
	
	public Herramienta(int idArticulo, String nombreArticulo, Double precio, int stockMaximo, int stockActual,
			tipoArticulo type, Boolean electrica, int garantia) {
		super(idArticulo, nombreArticulo, precio, stockMaximo, stockActual, type);
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
		if (getClass() != obj.getClass())
			return false;
		Articulo other = (Articulo) obj;
		return idArticulo == other.idArticulo;
	}


	@Override
	public String toString() {
		return "Herramienta [electrica=" + electrica + ", garantia=" + garantia + "]";
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
