package modelo;

import java.util.Date;
import java.util.Objects;

import controlador.Metodos;

public class Comida extends Articulo{
	Metodos mc=new Metodos();
	private Date fechaCaducidad;
	private String procedencia;
	
	public Comida(int idArticulo, String nombreArticulo, String rutaImagen,String descripcion, float precio,int stockActual, tipoArticulo tipo, Date fechaCaducidad, String procedencia) {
		super(idArticulo, nombreArticulo, rutaImagen,descripcion, precio, stockActual, tipo);
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
				+ rutaImagen + ", precio=" + precio + ", stockActual=" + stockActual
				+ ", tipo=" + tipo + "]";
	}
	public String getFechaCaducidad() {
		return mc.formatearFecha(fechaCaducidad);
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

