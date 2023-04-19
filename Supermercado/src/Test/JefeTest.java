package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Modelo.Jefe;
import Modelo.Persona;
import Modelo.Seccion;
import Modelo.Supermercado;

class JefeTest {
	
Jefe obj = new Jefe("sunburn", "362358H", "astdf@gmail.com", Date.valueOf("2023-01-01"), "james", "fotografo");
	
	@Test
	void test_GettersSetters() {
		obj.setApellidos("sunburn");
		obj.setDni("362358H");
		obj.setEmail("astdf@gmail.com");
		obj.setFechaNacimiento(Date.valueOf("2023-01-01"));
		obj.setNombre("james");
		obj.setTitulo("fotografo");
		
		assertEquals(obj.getApellidos(),"sunburn");
		assertEquals(obj.getDni(),"362358H");
		assertEquals(obj.getEmail(),"astdf@gmail.com");
		assertEquals(obj.getFechaNacimiento(),Date.valueOf("2023-01-01"));
		assertEquals(obj.getNombre(),"james");
		assertEquals(obj.getTitulo(),"fotografo");
	}
	
	@Test
	void test_equals() {
		Jefe obj1 = new Jefe(null, null, null, null, null, null);
		Jefe obj2 = new Jefe(null, null, null, null, null, null);
		
		obj1.setDni("362358H");
		obj2.setDni("362358H");
	boolean resultado = obj1.equals(obj2);
	assertTrue(resultado);
	
	obj1.setDni("362358H");
	obj2.setDni("1323258L");
	boolean resultado2 = obj1.equals(obj2);
	assertFalse(resultado2);
	
	int notAObject=0;
	boolean resultado3 = obj1.equals(notAObject);
	assertFalse(resultado3);
	
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
		assertEquals(obj.toString(),"Jefe [titulo=fotografo, dni=sunburn, nombre=362358H, apellidos=astdf@gmail.com, fechaNacimiento=2023-01-01, email=james]");
	}

	@Test
	void test_equalsPersona() {
		Jefe obj1 = new Jefe(null, null, null, null, null, null);
		Jefe obj2 = new Jefe(null, null, null, null, null, null);
		
		obj1.setDni("362358H");
		obj2.setDni("362358H");
	boolean resultado = ((Persona)obj1).equals((Persona)obj2);
	assertTrue(resultado);
	
	obj1.setDni("362358H");
	obj2.setDni("1323258L");
	boolean resultado2 = obj1.equals(obj2);
	assertFalse(resultado2);
	
	int notAObject=0;
	boolean resultado3 = obj1.equals(notAObject);
	assertFalse(resultado3);
	
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
}
