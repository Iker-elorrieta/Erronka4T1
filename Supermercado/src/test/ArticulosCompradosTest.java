package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import modelo.ArticuloComprado;

class ArticulosCompradosTest {

ArticuloComprado obj = new ArticuloComprado(0, 0, 0, 0);
	
	@Test
	void test_GettersSetters() {
		obj.setCantidad(3);
		obj.setCodigoCompra(2);
		obj.setIdArticulo(1);
		obj.setPrecioArt(7);
		
		assertEquals(obj.getCantidad(),3);
		assertEquals(obj.getCodigoCompra(),2);
		assertEquals(obj.getIdArticulo(),1);
		assertEquals(Float.valueOf(obj.getPrecioArt()),7);
		
	
	}
	
	@Test
	void test_equals() {
		ArticuloComprado obj1 = new ArticuloComprado(0, 0, 0, 0);
		ArticuloComprado obj2 = new ArticuloComprado(0, 0, 0, 0);
		
		obj1.setIdArticulo(2132);
		obj2.setIdArticulo(2132);
	boolean resultado = obj1.equals(obj2);
	assertTrue(resultado);
	
	obj1.setIdArticulo(213123);
	obj2.setIdArticulo(12323);
	boolean resultado2 = obj1.equals(obj2);
	assertFalse(resultado2);
	
	obj1.setIdArticulo(234234);
	obj2.setIdArticulo(0);
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
		assertEquals(obj.toString(),"ArticulosComprados [codigoCompra=0, idArticulo=0, cantidad=0]");
	}

}
