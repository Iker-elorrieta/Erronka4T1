package Modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import com.mysql.jdbc.Statement;

import Controlador.Metodos;
import Controlador.Tablas;

public class Supermercado {
	
private String codigoSuper;
private String direccion;
private float metrosCuadrados;
private int numEmpleados;
private String horario;
private ArrayList<Seccion> arraySecciones;

public Supermercado(String codigoSuper, String direccion, float metrosCuadrados, int numEmpleados, String horario,
		ArrayList<Seccion> arraySecciones) {
	this.codigoSuper = codigoSuper;
	this.direccion = direccion;
	this.metrosCuadrados = metrosCuadrados;
	this.numEmpleados = numEmpleados;
	this.horario = horario;
	this.arraySecciones = arraySecciones;
}
@Override
public int hashCode() {
	return Objects.hash(codigoSuper);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	Supermercado other = (Supermercado) obj;
	return Objects.equals(codigoSuper, other.codigoSuper);
}
@Override
public String toString() {
	return "Supermercado [codigoSuper=" + codigoSuper + ", direccion=" + direccion + ", metrosCuadrados="
			+ metrosCuadrados + ", numEmpleados=" + numEmpleados + ", horario=" + horario + "]";
}
public String getCodigoSuper() {
	return codigoSuper;
}
public void setCodigoSuper(String codigoSuper) {
	this.codigoSuper = codigoSuper;
}
public String getDireccion() {
	return direccion;
}
public void setDireccion(String direccion) {
	this.direccion = direccion;
}
public float getMetrosCuadrados() {
	return metrosCuadrados;
}
public void setMetrosCuadrados(float metrosCuadrados) {
	this.metrosCuadrados = metrosCuadrados;
}
public int getNumEmpleados() {
	return numEmpleados;
}
public void setNumEmpleados(int numEmpleados) {
	this.numEmpleados = numEmpleados;
}
public String getHorario() {
	return horario;
}
public void setHorario(String horario) {
	this.horario = horario;
}
public ArrayList<Seccion> getArraySecciones() {
	return arraySecciones;
}
public void setArraySecciones(ArrayList<Seccion> arraySecciones) {
	this.arraySecciones = arraySecciones;
}

public void emergenciaSanitaria(Jefe je,ArrayList<Seccion> listaSecciones,ArrayList<ArticulosComprados> listaArtComprados) throws SQLException {
	Metodos mc=new Metodos();
	Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
	for(Seccion se: listaSecciones) {
		for(Articulo ar:se.getArrayArticulos()) {
	comando.executeUpdate("UPDATE "+Tablas.Articulo+" SET "+Tablas.StockActual+"=0 WHERE "+Tablas.IdArticulo+"='"+ar.getIdArticulo()+"'");
	for(ArticulosComprados arc: listaArtComprados) {
		comando.executeUpdate("DELETE FROM "+Tablas.ArticulosComprados+" WHERE "+Tablas.IdArticulo+"='"+arc.getIdArticulo()+"' AND "+Tablas.codigoCompra+"='"+arc.getCodigoCompra()+"'");
		comando.executeUpdate("UPDATE "+Tablas.Compras+" SET "+Tablas.precioFinal+"=("+Tablas.precioFinal+"-"+(arc.getCantidad()*arc.getPrecioArt())+") WHERE "+Tablas.codigoCompra+"='"+arc.getCodigoCompra()+"'");
			}	
		}
	}
}
}
