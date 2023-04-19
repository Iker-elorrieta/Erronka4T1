package Modelo;

import java.util.ArrayList;
import java.util.Objects;

public class Supermercado {
	//El POJO tiene los tipos de datos correctos, implementa Serializable, tiene Getters&Setters, HasCode, Compare y ToString
	//1-const 2-he 3-ToStr 4-GyS
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
	if (getClass() != obj.getClass())
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
	

	
	
	
}
