package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import controlador.ErroresDeRegistro;
import controlador.GestorArticulo;
import controlador.GestorArticuloComprado;
import controlador.GestorCompra;
import controlador.GestorPersona;
import controlador.Metodos;
import modelo.Articulo;
import modelo.Cliente;
import modelo.Compra;
import modelo.tipoPersona;

class TestCliente {
	DateFormat formateador= new SimpleDateFormat("dd/M/yy");
	Metodos mc=new Metodos();
	GestorArticulo ga=new GestorArticulo();
	GestorArticuloComprado gac=new GestorArticuloComprado();
	GestorPersona gp=new GestorPersona();
	GestorCompra gc=new GestorCompra();
	@Test
	void test() {
		Cliente primero=new Cliente("798981A","Pepe","Perez Navarros",Date.valueOf("2343-01-02"),"perez@gmail.com","contrasena",tipoPersona.Cliente,(float)55.67, 0);
		Cliente segundo=new Cliente("","","",null,"","",null,0, 0);
		assertFalse(primero.equals(segundo));
		assertFalse(primero.equals(null));
		segundo.setDni("798981A");
		segundo.setNombre("Pepe");
		segundo.setApellidos("Perez Navarros");
		segundo.setFechaNacimiento(Date.valueOf("2343-01-02"));
		segundo.setEmail("perez@gmail.com");
		segundo.setDinero((float)55.67);
		primero.setArrayCompras(null);
		segundo.setArrayCompras(null);
		primero.setContrasena("1234");
		primero.setBloqueado(false);
		segundo.setTipo(tipoPersona.Cliente);
		assertEquals(primero.getContrasena(),"1234");
		assertEquals(primero.isBloqueado(),false);
		assertEquals(primero.getApellidos(),segundo.getApellidos());
		assertEquals(primero.getDinero(),segundo.getDinero());
		assertEquals(primero.getDni(),segundo.getDni());
		assertEquals(primero.getEmail(),segundo.getEmail());
		assertEquals(primero.getFechaNacimiento(),segundo.getFechaNacimiento());
		assertEquals(primero.getNombre(),segundo.getNombre());
		assertEquals(primero.getArrayCompras(),segundo.getArrayCompras());
		assertEquals(primero.hashCode(),segundo.hashCode());
		assertEquals(primero.toString(),"Cliente [dinero=55.67, dni=798981A, nombre=Pepe, apellidos=Perez Navarros, fechaNacimiento=2343-01-02, email=perez@gmail.com]");
	}
	
	@Test
	void test_equals() {
		Cliente obj1 = new Cliente("","","",null,"","",null,0, 0);
		Cliente obj2 = new Cliente("","","",null,"","",null,0, 0);
		
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
		Cliente cliente = new Cliente("99999999X","Test","Test",Date.valueOf("2001-01-21"),"test@gmail.com","12345",tipoPersona.Cliente,(float)99.9,0);
		Cliente prueba=null;
		Compra co=new Compra();
		Articulo ar=null;
		ArrayList<Articulo> articulos=null;
		try {
			ga.setListaArticulos(ga.cargarArticulos());
			articulos=ga.getListaArticulos();
			ar=ga.getListaArticulos().get(0);
			co.anadirArticulo(ga.getListaArticulos().get(0),2);
			gp.insertarPersona(cliente);
			co.setPrecioTotal(co.calcularPrecioTotal());
			
			cliente.comprarArticulos(co,co.getListaCantidades());
			Compra c=gc.buscarCompraReciente();
			assertNotEquals(co.getCodigoCompra(),c.getCodigoCompra());
			assertNotEquals(co.getFechaCompra(),c.getFechaCompra());
			assertNotEquals(co.calcularPrecioTotal(),c.calcularPrecioTotal());
			assertNotEquals(ar.getStockActual(),ar.getStockActual()-1);
			
			gp.setListaPersonas(gp.cargarPersonas());
			int posicion=gp.getListaPersonas().indexOf(cliente);
			prueba=(Cliente) gp.getListaPersonas().get(posicion);
			assertNotEquals(prueba.getDinero(),cliente.getDinero());
			
			cliente.devolverUnArticulo(gac.buscarArticuloComprado(ga.buscarArticulo(ar)), 1);
			gp.setListaPersonas(gp.cargarPersonas());
			posicion=gp.getListaPersonas().indexOf(cliente);
			prueba=(Cliente) gp.getListaPersonas().get(posicion);
			cliente.setDinero(cliente.getDinero()-co.calcularPrecioTotal()/2);
			assertEquals(prueba.getDinero(),cliente.getDinero());
			
			cliente.cancelarUltimaCompra();
			gp.setListaPersonas(gp.cargarPersonas());
			posicion=gp.getListaPersonas().indexOf(cliente);
			prueba=(Cliente) gp.getListaPersonas().get(posicion);
			cliente.setDinero(cliente.getDinero());
			assertEquals(prueba.getDinero(),cliente.getDinero());
			ga.setListaArticulos(ga.cargarArticulos());
			assertEquals(articulos,ga.getListaArticulos());
			
			c=gc.buscarCompraReciente();
			assertNotEquals(co.getCodigoCompra(),c.getCodigoCompra());
			assertNotEquals(co.getFechaCompra(),c.getFechaCompra());
			assertNotEquals(co.calcularPrecioTotal(),c.calcularPrecioTotal());
			assertNotEquals(ar.getStockActual(),ar.getStockActual()-1);
			
			cliente.comprarArticulos(co,co.getListaCantidades());
			cliente.devolverUnArticulo(gac.buscarArticuloComprado(ga.buscarArticulo(ar)), 2);
			
			gp.darseBajaPersona(cliente);
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
