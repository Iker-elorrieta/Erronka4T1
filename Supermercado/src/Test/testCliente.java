package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


import org.junit.jupiter.api.Test;

import Modelo.Cliente;

class testCliente {
	DateFormat formateador= new SimpleDateFormat("dd/M/yy");
	@Test
	void test() {
		
			Cliente primero=new Cliente("798981A","Pepe","Perez Navarros",Date.valueOf("2343-01-02"),"perez@gmail.com","contrasena",55.67,true, false);
		Cliente segundo=new Cliente("","","",null,"","",0,false, false);
		assertFalse(primero.equals(segundo));
		assertFalse(primero.equals(null));
		segundo.setDni("798981A");
		segundo.setNombre("Pepe");
		segundo.setApellidos("Perez Navarros");
		segundo.setFechaNacimiento(Date.valueOf("2343-01-02"));
		segundo.setEmail("perez@gmail.com");
		segundo.setDinero(55.67);
		segundo.setTarjetaCliente(true);
		primero.setArrayCompras(null);
		segundo.setArrayCompras(null);
		primero.setContrasena("1234");
		primero.setBloqueado(false);
		assertEquals(primero.getContrasena(),"1234");
		assertEquals(primero.isBloqueado(),false);
		assertEquals(primero.getApellidos(),segundo.getApellidos());
		assertEquals(primero.getDinero(),segundo.getDinero());
		assertEquals(primero.getDni(),segundo.getDni());
		assertEquals(primero.getEmail(),segundo.getEmail());
		assertEquals(primero.getFechaNacimiento(),segundo.getFechaNacimiento());
		assertEquals(primero.getNombre(),segundo.getNombre());
		assertEquals(primero.getArrayCompras(),segundo.getArrayCompras());
		assertEquals(primero.isTarjetaCliente(),segundo.isTarjetaCliente());
		assertEquals(primero.hashCode(),segundo.hashCode());
		assertEquals(primero.toString(),"Cliente [dinero=55.67, tarjetaCliente=true, dni=798981A, nombre=Pepe, apellidos=Perez Navarros, fechaNacimiento=2343-01-02, email=perez@gmail.com]");
	
		
	}
	
	@Test
	void test_equals() {
		Cliente obj1 = new Cliente(null, null, null, null, null,"", 0, false, false);
		Cliente obj2 = new Cliente(null, null, null, null, null,"",0, false, false);
		
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
	
	
	

}
