package Modelo;

import java.util.ArrayList;
import java.util.Objects;

public class Seccion {
	private String codigoSeccion;
	private String nombreSeccion;
	private ArrayList<Articulo> arrayArticulos;
	
	public Seccion(String codigoSeccion, String nombreSeccion, ArrayList<Articulo> arrayArticulos) {
		super();
		this.codigoSeccion = codigoSeccion;
		this.nombreSeccion = nombreSeccion;
		this.arrayArticulos = arrayArticulos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigoSeccion);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Seccion other = (Seccion) obj;
		return Objects.equals(codigoSeccion, other.codigoSeccion);
	}






	@Override
	public String toString() {
		return "Seccion [codigoSeccion=" + codigoSeccion + ", nombreSeccion=" + nombreSeccion + "]";
	}



	public String getCodigoSeccion() {
		return codigoSeccion;
	}
	public String getNombreSeccion() {
		return nombreSeccion;
	}
	public ArrayList<Articulo> getArrayArticulos() {
		return arrayArticulos;
	}
	public void setCodigoSeccion(String codigoSeccion) {
		this.codigoSeccion = codigoSeccion;
	}
	public void setNombreSeccion(String nombreSeccion) {
		this.nombreSeccion = nombreSeccion;
	}
	public void setArrayArticulos(ArrayList<Articulo> arrayArticulos) {
		this.arrayArticulos = arrayArticulos;
	}
	
	
	
}
