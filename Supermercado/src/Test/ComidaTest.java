package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import Modelo.Comida;
import Modelo.tipoArticulo;

class ComidaTest {

Comida obj = new Comida(0, null, null, null, null, 0, 0, null, null, null);
	
	@Test
	void test_GettersSetters() {
		obj.setFechaCaducidad(Date.valueOf("2039-12-01"));
		obj.setIdArticulo(232);
		obj.setNombreArticulo("KitKat");
		obj.setPrecio(23.1);
		obj.setProcedencia("japon");
		obj.setStockActual(4);
		obj.setStockMaximo(10);
		obj.settipo(tipoArticulo.Comida);
		obj.setRutaImagen("imagen.png");
		obj.setDescripcion("des");
		
		assertEquals(obj.getFechaCaducidad(),Date.valueOf("2039-12-01"));
		assertEquals(obj.getIdArticulo(),232);
		assertEquals(obj.getNombreArticulo(),"KitKat");
		assertEquals(obj.getPrecio(),23.1);
		assertEquals(obj.getProcedencia(),"japon");
		assertEquals(obj.getStockActual(),4);
		assertEquals(obj.getStockMaximo(),10);
		assertEquals(obj.gettipo(),tipoArticulo.Comida);
		assertEquals(obj.getRutaImagen(),"imagen.png");
		assertEquals(obj.getDescripcion(),"des");
	
	}
	
	@Test
	void test_equals() {
		Comida obj1 = new Comida(0, null, null, null, null, 0, 0, null, null, null);
		Comida obj2 = new Comida(0, null, null, null, null, 0, 0, null, null, null);
		
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
		assertEquals(obj.toString(),"Comida [fechaCaducidad=null, procedencia=null, idArticulo=0, nombreArticulo=null, rutaImagen=null, precio=null, stockMaximo=0, stockActual=0, tipo=null]");
	}

}
