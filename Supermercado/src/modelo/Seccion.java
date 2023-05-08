package modelo;

import java.util.ArrayList;
import java.util.Objects;

public class Seccion {
	private String codigoSeccion;
	private tipoArticulo nombreSeccion;
	private int numArticulo;
	private ArrayList<Articulo> arrayArticulos;
	
	public Seccion(String codigoSeccion, tipoArticulo nombreSeccion, int numArticulo, ArrayList<Articulo> arrayArticulos) {
		super();
		this.numArticulo=numArticulo;
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
		return "Seccion [codigoSeccion=" + codigoSeccion + ", nombreSeccion=" + nombreSeccion + ", numArticulo="+numArticulo+"]";
	}
	public String getCodigoSeccion() {
		return codigoSeccion;
	}
	public tipoArticulo getNombreSeccion() {
		return nombreSeccion;
	}
	public ArrayList<Articulo> getArrayArticulos() {
		return arrayArticulos;
	}
	public void setCodigoSeccion(String codigoSeccion) {
		this.codigoSeccion = codigoSeccion;
	}
	public void setNombreSeccion(tipoArticulo nombreSeccion) {
		this.nombreSeccion = nombreSeccion;
	}
	public void setArrayArticulos(ArrayList<Articulo> arrayArticulos) {
		this.arrayArticulos = arrayArticulos;
	}

	public int getNumArticulo() {
		return numArticulo;
	}
	public void setNumArticulo(int numArticulo) {
		this.numArticulo = numArticulo;
	}
	
}
