package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Modelo.Articulo;
import Modelo.Compra;

class CompraTest {
	ArrayList<Articulo> arrayArticulos = new ArrayList<Articulo>();
Compra obj = new Compra(212, arrayArticulos, 12313, Date.valueOf("2034-02-03"));
	
	@Test
	void test_GettersSetters() {
		obj.setArrayArticulos(arrayArticulos);
		obj.setCodigoCompra(212);
		obj.setFecha(Date.valueOf("2034-02-03"));
		obj.setPrecioTotal(64.2);
		
		assertEquals(obj.getArrayArticulos(),arrayArticulos);
		assertEquals(obj.getCodigoCompra(),212);
		assertEquals(obj.getFecha(),Date.valueOf("2034-02-03"));
		assertEquals(Double.valueOf(obj.getPrecioTotal()),64.2);
	}
	
	@Test
	void test_equals() {
		Compra obj1 = new Compra(0, arrayArticulos, 0, null);
		Compra obj2 = new Compra(0, arrayArticulos, 0, null);
		
		obj1.setCodigoCompra(2131232);
		obj2.setCodigoCompra(2131232);
	boolean resultado = obj1.equals(obj2);
	assertTrue(resultado);
	
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
		assertEquals(obj.toString(),"Compra [codigoCompra=212, precioTotal=12313.0, fecha=2034-02-03]");
	}

}
