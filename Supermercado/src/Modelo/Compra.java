package Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Compra {
private int codigoCompra;
private ArrayList<Articulo> arrayArticulos;
private double precioTotal;
private Date fecha;


public Compra(int codigoCompra, ArrayList<Articulo> arrayArticulos, double precioTotal, Date fecha) {
	super();
	this.codigoCompra = codigoCompra;
	this.arrayArticulos = arrayArticulos;
	this.precioTotal = precioTotal;
	this.fecha = fecha;
}


@Override
public int hashCode() {
	return Objects.hash(codigoCompra);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	Compra other = (Compra) obj;
	return codigoCompra == other.codigoCompra;
}

@Override
public String toString() {
	return "Compra [codigoCompra=" + codigoCompra + ", precioTotal=" + precioTotal + ", fecha=" + fecha + "]";
}

public int getCodigoCompra() {
	return codigoCompra;
}
public ArrayList<Articulo> getArrayArticulos() {
	return arrayArticulos;
}
public double getPrecioTotal() {
	return precioTotal;
}
public Date getFecha() {
	return fecha;
}
public void setCodigoCompra(int codigoCompra) {
	this.codigoCompra = codigoCompra;
}
public void setArrayArticulos(ArrayList<Articulo> arrayArticulos) {
	this.arrayArticulos = arrayArticulos;
}
public void setPrecioTotal(double precioTotal) {
	this.precioTotal = precioTotal;
}
public void setFecha(Date fecha) {
	this.fecha = fecha;
}
	










}
