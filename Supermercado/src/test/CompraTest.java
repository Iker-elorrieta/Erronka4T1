package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import modelo.Articulo;
import modelo.ArticuloComprado;
import modelo.Compra;

class CompraTest {
	ArrayList<Articulo> arrayArticulos = new ArrayList<Articulo>();
	LocalDateTime myObj = LocalDateTime.now();
	String tiempo=String.valueOf(myObj);
Compra obj = new Compra(212, 12313, myObj);
	
	@Test
	void test_GettersSetters() {
		obj.setArrayArticulos(arrayArticulos);
		obj.setCodigoCompra(212);
		obj.setFechaCompra(myObj);
		obj.setPrecioTotal((float)64.2);
		
		assertEquals(obj.getArrayArticulos(),arrayArticulos);
		assertEquals(obj.getCodigoCompra(),212);
		assertEquals(obj.getFechaCompra(),myObj);
		assertEquals(Double.valueOf(obj.getPrecioTotal()),(float)64.2);
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

}
