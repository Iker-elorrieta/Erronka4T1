package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import modelo.ArticuloComprado;
import modelo.Compra;
import modelo.Herramienta;
import modelo.Ropa;
@SuppressWarnings("javadoc")
class CompraTest {
	LocalDateTime myObj = LocalDateTime.now();
	String tiempo=String.valueOf(myObj);
	Compra obj = new Compra(212, 12313, myObj);
	
	@Test
	void test_GettersSetters() {
		obj.setCodigoCompra(212);
		obj.setFechaCompra(myObj);
		obj.setPrecioTotal((float)64.2);
		Ropa ar=new Ropa(0, tiempo, tiempo, tiempo, 0, 0, tiempo, tiempo);
		obj.anadirArticulo(ar, 1);
		assertEquals(obj.getCodigoCompra(),212);
		assertEquals(obj.getFechaCompra(),myObj);
		assertEquals(Double.valueOf(obj.getPrecioTotal()),(float)64.2);
		assertTrue(obj.calcularPrecioTotal()!=obj.getPrecioTotal());
		obj=new Compra(22);
		Compra obj2=new Compra();
		assertNotEquals(obj.getPrecioTotal(),obj2.getPrecioTotal());
	}
	
	@Test
	void test_equals() {
		Compra obj1 = new Compra(0, 0, null);
		Compra obj2 = new Compra(0, 0, null);
		ArrayList<ArticuloComprado> lista=null;
		
		obj1.setCodigoCompra(2131232);
		obj2.setCodigoCompra(2131232);
	boolean resultado = obj1.equals(obj2);
	assertTrue(resultado);
	
	obj1.setListaCantidades(lista);
	assertEquals(obj1.getListaCantidades(),null);
	
	obj1.setCodigoCompra(234342);
	obj2.setCodigoCompra(11122);
	boolean resultado2 = obj1.equals(obj2);
	assertFalse(resultado2);
	
	obj1.setCodigoCompra(23656723);
	obj2.setCodigoCompra(0);
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
		assertEquals(obj.toString(),"Compra [codigoCompra=212, precioTotal=12313.0, fechaCompra="+tiempo+"]");
	}
	
	@Test
	void testAcciones() {
		Herramienta he=new Herramienta(9, "Desatornillador", "desatornillador.png", "", (float)8.99, 99, 1, 4);
		ArticuloComprado arc=new ArticuloComprado(17,9,3,18);
		ArrayList<ArticuloComprado> lista=new ArrayList<ArticuloComprado>();
		lista.add(arc);
		int tamano=lista.size();
		obj.setListaCantidades(lista);
		obj.anadirArticulo(he, 1);
		assertEquals(tamano,obj.getListaCantidades().size());
	}
	@Test
	void testAccionesDos() {
		Herramienta he=new Herramienta(9, "Desatornillador", "desatornillador.png", "", (float)8.99, 99, 1, 4);
		ArticuloComprado arc=new ArticuloComprado(17,9,3,18);
		ArrayList<ArticuloComprado> lista=new ArrayList<ArticuloComprado>();
		lista.add(arc);
		obj.setListaCantidades(lista);
		obj.cambiarArticulo(he, 2);
		assertEquals(obj.getListaCantidades().get(0).getCantidad(),2);
	}
}
