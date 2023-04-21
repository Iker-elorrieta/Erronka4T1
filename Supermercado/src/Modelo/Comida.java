package Modelo;

import java.util.Date;
import java.util.Objects;

public class Comida extends Articulo{
	private Date fechaCaducidad;
	private String nombreAlimento;
	private String procedencia;
	
	
	public Comida(int idArticulo, String nombreArticulo, String rutaImagen, Double precio, int stockMaximo,
			int stockActual, tipoArticulo type, Date fechaCaducidad, String nombreAlimento, String procedencia) {
		super(idArticulo, nombreArticulo, rutaImagen, precio, stockMaximo, stockActual, type);
		this.fechaCaducidad = fechaCaducidad;
		this.nombreAlimento = nombreAlimento;
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
		return "Comida [fechaCaducidad=" + fechaCaducidad + ", nombreAlimento=" + nombreAlimento + ", procedencia="
				+ procedencia + ", idArticulo=" + idArticulo + ", nombreArticulo=" + nombreArticulo + ", rutaImagen="
				+ rutaImagen + ", precio=" + precio + ", stockMaximo=" + stockMaximo + ", stockActual=" + stockActual
				+ ", type=" + type + "]";
	}


	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}
	public String getNombreAlimento() {
		return nombreAlimento;
	}
	public String getProcedencia() {
		return procedencia;
	}
	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}
	public void setNombreAlimento(String nombreAlimento) {
		this.nombreAlimento = nombreAlimento;
	}
	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}
	

	
}

