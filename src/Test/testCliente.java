package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Controlador.Metodos;
import Modelo.ArticulosComprados;
import Modelo.Cliente;
import Modelo.Compra;

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
	}
	@Test
	void testMetodos() {
		Cliente cli = new Cliente("575464V", null, null, null, null,"", 0, false, false);
	Metodos mc=new Metodos();
	Compra com=null;
	try {
		com=new Compra(0,98.1,mc.deStringADate("2019-07-09"));
	} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	ArticulosComprados ar=new ArticulosComprados(13, 1, 3, 23);
	ArrayList<ArticulosComprados> lista=new ArrayList<ArticulosComprados>();
	lista.add(ar);
	try {
		cli.comprarArticulos(com, lista);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		cli.devolverUnArticulo(ar, 3);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		cli.devolverUnArticulo(ar, 1);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		cli.cancelarCompra(com,lista);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
	
	

}
