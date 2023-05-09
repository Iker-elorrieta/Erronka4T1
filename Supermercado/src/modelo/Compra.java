package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Compra {
private int codigoCompra;
private float precioFinal;
private LocalDateTime fechaCompra;
private ArrayList<ArticuloComprado> listaCantidades;
private ArrayList<Articulo> arrayArticulos;

public Compra(int codigoCompra, float precioTotal, LocalDateTime fechaCompra) {
	super();
	this.codigoCompra = codigoCompra;
	this.precioFinal = precioTotal;
	this.fechaCompra = fechaCompra;
	arrayArticulos = new ArrayList<Articulo>();
	listaCantidades=new ArrayList<ArticuloComprado>();
}
public Compra(float f) {
	// TODO Auto-generated constructor stub
	this.precioFinal = f;
	arrayArticulos = new ArrayList<Articulo>();
	listaCantidades=new ArrayList<ArticuloComprado>();
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
	return "Compra [codigoCompra=" + codigoCompra + ", precioTotal=" + precioFinal + ", fechaCompra=" + fechaCompra + "]";
}

public ArrayList<ArticuloComprado> getListaCantidades() {
	return listaCantidades;
}
public void setListaCantidades(ArrayList<ArticuloComprado> listaCantidades) {
	this.listaCantidades = listaCantidades;
}
public int getCodigoCompra() {
	return codigoCompra;
}
public ArrayList<Articulo> getArrayArticulos() {
	return arrayArticulos;
}
public float calcularPrecioTotal() {
	float calcularPrecio=0;
	for(ArticuloComprado arc:listaCantidades) {
		calcularPrecio+=(arc.getCantidad()*arc.getPrecioArt());
	}
	return calcularPrecio;
}
public float getPrecioTotal() {
	return precioFinal;
}
public LocalDateTime getFechaCompra() {
	return fechaCompra;
}
public void setCodigoCompra(int codigoCompra) {
	this.codigoCompra = codigoCompra;
}
public void setArrayArticulos(ArrayList<Articulo> arrayArticulos) {
	this.arrayArticulos = arrayArticulos;
}
public void setPrecioTotal(float precioTotal) {
	this.precioFinal = precioTotal;
}
public void setFechaCompra(LocalDateTime fechaCompra) {
	this.fechaCompra = fechaCompra;
}
public void anadirArticulo(Articulo ar,int cantidad) {
	arrayArticulos.add(ar);
	ArticuloComprado arc=new ArticuloComprado(0, ar.getIdArticulo(), cantidad, ar.getPrecio());
	listaCantidades.add(arc);
}
}
