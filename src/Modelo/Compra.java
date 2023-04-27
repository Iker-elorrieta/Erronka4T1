package Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Compra {
private int codigoCompra;
private ArrayList<Articulo> arrayArticulos;
private double precioTotal;
private Date fechaCompra;


public Compra(int codigoCompra, double precioTotal, Date fechaCompra) {
	super();
	this.codigoCompra = codigoCompra;
	this.precioTotal = precioTotal;
	this.fechaCompra = fechaCompra;
	arrayArticulos = new ArrayList<Articulo>();
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
	return "Compra [codigoCompra=" + codigoCompra + ", precioTotal=" + precioTotal + ", fechaCompra=" + fechaCompra + "]";
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
public Date getFechaCompra() {
	return fechaCompra;
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
public void setFechaCompra(Date fechaCompra) {
	this.fechaCompra = fechaCompra;
}


}
