package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Compra {
private int codigoCompra;
private float precioFinal;
private LocalDateTime fechaCompra;
private ArrayList<ArticuloComprado> listaCantidades;

public Compra(int codigoCompra, float precioTotal, LocalDateTime fechaCompra) {
	super();
	this.codigoCompra = codigoCompra;
	this.precioFinal = precioTotal;
	this.fechaCompra = fechaCompra;
	listaCantidades=new ArrayList<ArticuloComprado>();
}
public Compra(float f) {
	// TODO Auto-generated constructor stub
	this.precioFinal = f;
	listaCantidades=new ArrayList<ArticuloComprado>();
}
public Compra() {
	// TODO Auto-generated constructor stub;
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
public void setPrecioTotal(float precioTotal) {
	this.precioFinal = precioTotal;
}
public void setFechaCompra(LocalDateTime fechaCompra) {
	this.fechaCompra = fechaCompra;
}
public void anadirArticulo(Articulo ar,int cantidad) {
	boolean repetido=false;
	for(ArticuloComprado arc:listaCantidades) {
		if(ar.getIdArticulo()==arc.getIdArticulo()) {
			arc.setCantidad(arc.getCantidad()+cantidad);
			repetido=true;
			calcularPrecioTotal();
		}
	}
	if(!repetido) {
	ArticuloComprado arc=new ArticuloComprado(0, ar.getIdArticulo(), cantidad, ar.getPrecio());
	listaCantidades.add(arc);
	calcularPrecioTotal();
	}
}
public void cambiarArticulo(Articulo ar,int cantidad) {
	for(ArticuloComprado arc:listaCantidades) {
		if(ar.getIdArticulo()==arc.getIdArticulo()) {
			if(arc.getCantidad()==cantidad) {
				listaCantidades.remove(arc);
			}else {
			arc.setCantidad(cantidad);
			calcularPrecioTotal();
			}
		}
	}
	}
}
