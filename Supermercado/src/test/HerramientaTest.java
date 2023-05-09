package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import modelo.Herramienta;

class HerramientaTest {

Herramienta obj = new Herramienta(0, null, null, null, 0, 0, 0, 0);
	
	@Test
	void test_GettersSetters() {
		obj.setElectrica(true);
		obj.setGarantia(45);
		obj.setIdArticulo(232);
		obj.setNombreArticulo("KitKat");
		obj.setPrecio((float)23.1);
		obj.setStockActual(4);
		obj.setRutaImagen("imagen.png");
		obj.setDescripcion("des");
		
		assertEquals(obj.getElectrica(),true);
		assertEquals(obj.getGarantia(),45);
		
		assertEquals(obj.getIdArticulo(),232);
		assertEquals(obj.getNombreArticulo(),"KitKat");
		assertTrue(obj.getPrecio()>0);
		assertEquals(obj.getStockActual(),4);
		assertEquals(obj.getRutaImagen(),"imagen.png");
		assertEquals(obj.getDescripcion(),"des");
	
	}
	
	@Test
	void test_equals() {
		Herramienta obj1 = new Herramienta(0, null, null, null, 0, 0, 0, 0);
		Herramienta obj2 = new Herramienta(0, null, null, null, 0, 0, 0, 0);
		
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
		assertEquals(obj.toString(),"Herramienta [electrica=false, garantia=0, idArticulo=0, nombreArticulo=null, rutaImagen=null, precio=0.0, stockActual=0 ]");
	}

}
