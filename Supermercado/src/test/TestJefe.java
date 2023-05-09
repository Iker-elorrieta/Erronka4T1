package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

import controlador.Metodos;
import gestores.GestorArticulo;
import gestores.GestorArticuloComprado;
import gestores.GestorCompra;
import gestores.GestorPersona;
import modelo.Jefe;
import modelo.Supermercado;

class TestJefe {
	DateFormat formateador= new SimpleDateFormat("dd/M/yy");
	Metodos mc=new Metodos();
	GestorArticulo ga=new GestorArticulo();
	GestorArticuloComprado gac=new GestorArticuloComprado();
	GestorPersona gp=new GestorPersona();
	GestorCompra gc=new GestorCompra();
Jefe obj = new Jefe(null, null, null, null, null, null, null, null, 0, 0);
	Supermercado su=new Supermercado(null, null, null, 0, null);
	@Test
	void test_GettersSetters() {
		obj.setApellidos("sunburn");
		obj.setDni("362358H");
		obj.setEmail("astdf@gmail.com");
		obj.setFechaNacimiento(Date.valueOf("2023-01-01"));
		obj.setNombre("james");
		obj.setContrasena("1234");
		obj.setSupermercado(su);
		obj.setDios(false);
		obj.setFechaAdquisicion(Date.valueOf("2023-01-01"));
		obj.setPorcentajeEmpresa(54);
		
		assertEquals(obj.getApellidos(),"sunburn");
		assertEquals(obj.getDni(),"362358H");
		assertEquals(obj.getEmail(),"astdf@gmail.com");
		assertEquals(obj.getFechaNacimiento(),"2023-01-01");
		assertEquals(obj.getNombre(),"james");
		assertEquals(obj.getContrasena(),"1234");
		assertEquals(obj.getSupermercado(),su);
		assertEquals(obj.isDios(),false);
		assertEquals(obj.getFechaAdquisicion(),"2023-01-01");
		assertEquals(Float.valueOf(obj.getPorcentajeEmpresa()),(float)54);
		assertEquals(obj.comprarArticulos((float)7.12),"El precio de recargar los articulos es de 7.12");
	}
	
	@Test
	void test_equals() {
		Jefe obj1 = new Jefe(null, null, null, null, null, null, null, null, 0, 0);
		Jefe obj2 = new Jefe(null, null, null, null, null, null, null, null, 0, 0);
		
		obj1.setDni("362358H");
		obj2.setDni("362358H");
	boolean resultado = obj1.equals(obj2);
	assertTrue(resultado);
	
	obj1.setDni("362358H");
	obj2.setDni("1323258L");
	boolean resultado2 = obj1.equals(obj2);
	assertFalse(resultado2);
	
	obj1.setDni("362358H");
	obj2.setDni(null);
	boolean resultado4 = obj1.equals(obj2);
	assertFalse(resultado4);
	
	boolean resultado5 = obj1.equals(obj1);
	assertTrue(resultado5);
	
	boolean resultado6 = obj1.equals(null);
	assertFalse(resultado6);
	
	obj1.hashCode();
	}
	@Test
	void test_toString() {
		assertEquals(obj.toString(),"Jefe [dni=null, nombre=null, apellidos=null, fechaNacimiento=null, email=null]");
	}
}
