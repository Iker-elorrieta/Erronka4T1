package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import Modelo.Cliente;

class testCliente {
	DateFormat formateador= new SimpleDateFormat("dd/M/yy");
	@Test
	void test() {
		Date fecha;
		try {
			fecha = formateador.parse("16/07/2000");
			Cliente primero=new Cliente("798981A","Pepe","Perez Navarros",fecha,"perez@gmail.com",55.67,true, null);
		Cliente segundo=new Cliente("","","",null,"",0,false, null);
		assertFalse(primero.equals(segundo));
		assertFalse(primero.equals(null));
		segundo.setDni("798981A");
		segundo.setNombre("Pepe");
		segundo.setApellidos("Perez Navarros");
		segundo.setFechaNacimiento(fecha);
		segundo.setEmail("perez@gmail.com");
		segundo.setDinero(55.67);
		segundo.setTarjetaCliente(true);
		primero.setArrayCompras(null);
		segundo.setArrayCompras(null);
		assertEquals(primero.getApellidos(),segundo.getApellidos());
		assertEquals(primero.getDinero(),segundo.getDinero());
		assertEquals(primero.getDni(),segundo.getDni());
		assertEquals(primero.getEmail(),segundo.getEmail());
		assertEquals(primero.getFechaNacimiento(),segundo.getFechaNacimiento());
		assertEquals(primero.getNombre(),segundo.getNombre());
		assertEquals(primero.getArrayCompras(),segundo.getArrayCompras());
		assertEquals(primero.isTarjetaCliente(),segundo.isTarjetaCliente());
		assertEquals(primero.hashCode(),segundo.hashCode());
		assertEquals(primero.toString(),"Cliente [dinero=55.67, tarjetaCliente=true, dni=798981A, nombre=Pepe, apellidos=Perez Navarros, fechaNacimiento=Sun Jul 16 00:00:00 CEST 2000, email=perez@gmail.com]");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	void test_equals() {
		Cliente obj1 = new Cliente(null, null, null, null, null, 0, false, null);
		Cliente obj2 = new Cliente(null, null, null, null, null, 0, false, null);
		
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
	
	
	

}
