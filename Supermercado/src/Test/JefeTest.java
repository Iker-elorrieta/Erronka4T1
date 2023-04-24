package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import org.junit.jupiter.api.Test;

import Modelo.Jefe;

class JefeTest {
	
Jefe obj = new Jefe("sunburn", "362358H", "astdf@gmail.com", Date.valueOf("2023-01-01"), "james", "fotografo",Date.valueOf("2023-03-01"),(float) 55.5);
	
	@Test
	void test_GettersSetters() {
		obj.setApellidos("sunburn");
		obj.setDni("362358H");
		obj.setEmail("astdf@gmail.com");
		obj.setFechaNacimiento(Date.valueOf("2023-01-01"));
		obj.setNombre("james");
		obj.setTitulo("fotografo");
		obj.setPorcentajeEmpresa((float) 20.1);
		obj.setFechaAdquisicion(Date.valueOf("2023-04-12"));
		
		assertEquals(obj.getApellidos(),"sunburn");
		assertEquals(obj.getDni(),"362358H");
		assertEquals(obj.getEmail(),"astdf@gmail.com");
		assertEquals(obj.getFechaNacimiento(),Date.valueOf("2023-01-01"));
		assertEquals(obj.getNombre(),"james");
		assertEquals(obj.getTitulo(),"fotografo");
		assertEquals(obj.getPorcentajeEmpresa(),(float) 20.1);
		assertEquals(obj.getFechaAdquisicion(),Date.valueOf("2023-04-12"));
	}
	
	@Test
	void test_equals() {
		Jefe obj1 = new Jefe(null, null, null, null, null, null, null, null);
		Jefe obj2 = new Jefe(null, null, null, null, null, null, null, null);
		
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
	
	obj1.comprarArticulos(null);
	}
	
	@Test
	void test_toString() {
		assertEquals(obj.toString(),"Jefe [titulo=fotografo, fechaAdquisicion=2023-03-01, porcentajeEmpresa=55.5, dni=sunburn, nombre=362358H, apellidos=astdf@gmail.com, fechaNacimiento=2023-01-01, email=james]");
	}


}
