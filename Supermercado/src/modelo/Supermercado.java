package modelo;

import java.util.ArrayList;
import java.util.Objects;

@SuppressWarnings("javadoc")
public class Supermercado {
	
private String codigoSuper;
private String empresa;
private String direccion;
private int numEmpleados;
private ArrayList<Seccion> arraySecciones;

public Supermercado(String codigoSuper,String empresa ,String direccion, int numEmpleados,ArrayList<Seccion> arraySecciones) {
	this.codigoSuper = codigoSuper;
	this.setEmpresa(empresa);
	this.direccion = direccion;
	this.numEmpleados = numEmpleados;
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
	return "Supermercado [codigoSuper=" + codigoSuper + ", empresa=" + empresa + ", direccion=" + direccion
			+ ", numEmpleados=" + numEmpleados + "]";
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
public int getNumEmpleados() {
	return numEmpleados;
}
public void setNumEmpleados(int numEmpleados) {
	this.numEmpleados = numEmpleados;
}
public ArrayList<Seccion> getArraySecciones() {
	return arraySecciones;
}
public void setArraySecciones(ArrayList<Seccion> arraySecciones) {
	this.arraySecciones = arraySecciones;
}
public String getEmpresa() {
	return empresa;
}
public void setEmpresa(String empresa) {
	this.empresa = empresa;
}

}
