package test;

import static org.junit.Assert.assertEquals;
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
import modelo.Compra;
import modelo.Jefe;
import modelo.Supermercado;
import modelo.tipoPersona;

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
	@Test
	void testMetodos() {
		Jefe jefe = new Jefe("99999999X","Test","Test",Date.valueOf("2001-01-21"),"test@gmail.com","12345",tipoPersona.Jefe,Date.valueOf("2019-06-01"), (float)99.9,0);
		Compra co=new Compra();
		Articulo ar=null;
		ArrayList<Articulo> articulos=null;
		try {
			ga.setListaArticulos(ga.cargarArticulos());
			articulos=ga.getListaArticulos();
			ar=ga.getListaArticulos().get(0);
			co.anadirArticulo(ga.getListaArticulos().get(0),2);
			gp.insertarPersona(jefe);
			co.setPrecioTotal(co.calcularPrecioTotal());
			
			jefe.comprarArticulos(co,co.getListaCantidades());
			Compra c=gc.buscarCompraReciente();
			assertNotEquals(co.getCodigoCompra(),c.getCodigoCompra());
			assertNotEquals(co.getFechaCompra(),c.getFechaCompra());
			assertNotEquals(co.calcularPrecioTotal(),c.calcularPrecioTotal());
			assertNotEquals(ar.getStockActual(),ar.getStockActual()-1);
			
			gp.setListaPersonas(gp.cargarPersonas());
			
			jefe.devolverUnArticulo(gac.buscarArticuloComprado(ga.buscarArticulo(ar)), 1);
			gp.setListaPersonas(gp.cargarPersonas());

			
			jefe.cancelarUltimaCompra();
			gp.setListaPersonas(gp.cargarPersonas());
			ga.setListaArticulos(ga.cargarArticulos());
			assertEquals(articulos,ga.getListaArticulos());
			
			jefe.comprarArticulos(co,co.getListaCantidades());
			c=gc.buscarCompraReciente();
			assertNotEquals(co.getCodigoCompra(),c.getCodigoCompra());
			assertNotEquals(co.getFechaCompra(),c.getFechaCompra());
			assertNotEquals(co.calcularPrecioTotal(),c.calcularPrecioTotal());
			assertNotEquals(ar.getStockActual(),ar.getStockActual()-1);
			
			gp.setListaPersonas(gp.cargarPersonas());
			
			jefe.comprarArticulos(co,co.getListaCantidades());
			jefe.devolverUnArticulo(gac.buscarArticuloComprado(ga.buscarArticulo(ar)), 2);
			gp.setListaPersonas(gp.cargarPersonas());
			
			gp.darseBajaPersona(jefe);
			
			
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
