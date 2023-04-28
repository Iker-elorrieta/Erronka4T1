package Modelo;

import java.util.Date;
import java.util.Objects;

public class Comida extends Articulo{
	private Date fechaCaducidad;
	private String procedencia;
	
	
	public Comida(int idArticulo, String nombreArticulo, String rutaImagen,String descripcion, Double precio, int stockMaximo,
			int stockActual, tipoArticulo tipo, Date fechaCaducidad, String procedencia) {
		super(idArticulo, nombreArticulo, rutaImagen,descripcion, precio, stockMaximo, stockActual, tipo);
		this.fechaCaducidad = fechaCaducidad;
		this.procedencia = procedencia;
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
		return "Comida [fechaCaducidad=" + fechaCaducidad + ", procedencia="
				+ procedencia + ", idArticulo=" + idArticulo + ", nombreArticulo=" + nombreArticulo + ", rutaImagen="
				+ rutaImagen + ", precio=" + precio + ", stockMaximo=" + stockMaximo + ", stockActual=" + stockActual
				+ ", tipo=" + tipo + "]";
	}


	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}
	public String getProcedencia() {
		return procedencia;
	}
	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}
	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}
	

	
}

