package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import org.junit.jupiter.api.Test;

import Modelo.Jefe;

class JefeTest {
	
Jefe obj = new Jefe(null, null, null, null, null, null, null, null, 0, false);
	
	@Test
	void test_GettersSetters() {
		obj.setApellidos("sunburn");
		obj.setDni("362358H");
		obj.setEmail("astdf@gmail.com");
		obj.setFechaNacimiento(Date.valueOf("2023-01-01"));
		obj.setNombre("james");
		obj.setTitulo("fotografo");
		obj.setContrasena("1234");
		
		obj.setDios(false);
		obj.setFechaAdquisicion(Date.valueOf("2023-01-01"));
		obj.setPorcentajeEmpresa(54);
		
		assertEquals(obj.getApellidos(),"sunburn");
		assertEquals(obj.getDni(),"362358H");
		assertEquals(obj.getEmail(),"astdf@gmail.com");
		assertEquals(obj.getFechaNacimiento(),Date.valueOf("2023-01-01"));
		assertEquals(obj.getNombre(),"james");
		assertEquals(obj.getTitulo(),"fotografo");
		assertEquals(obj.getContrasena(),"1234");
		
		assertEquals(obj.isDios(),false);
		assertEquals(obj.getFechaAdquisicion(),Date.valueOf("2023-01-01"));
		assertEquals(Float.valueOf(obj.getPorcentajeEmpresa()),54);
		
		
	}
	
	@Test
	void test_equals() {
		Jefe obj1 = new Jefe(null, null, null, null, null, null, null, null, 0, false);
		Jefe obj2 = new Jefe(null, null, null, null, null, null, null, null, 0, false);
		
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
	obj1.comprarArticulos(null, null);
	}
	
	@Test
	void test_toString() {
		assertEquals(obj.toString(),"Jefe [titulo=null, dni=null, nombre=null, apellidos=null, fechaNacimiento=null, email=null]");
	}


}
