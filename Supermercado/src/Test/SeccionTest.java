package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Modelo.Articulo;
import Modelo.Jefe;
import Modelo.Seccion;

class SeccionTest {

	ArrayList<Articulo> arrayArticulos = new ArrayList<Articulo>();
Seccion obj = new Seccion(null, null, null);
	
	@Test
	void test_GettersSetters() {
		obj.setArrayArticulos(arrayArticulos);
		obj.setCodigoSeccion("HDGHAGS");
		obj.setNombreSeccion("Videojuegos");
		
		assertEquals(obj.getArrayArticulos(),arrayArticulos);
		assertEquals(obj.getCodigoSeccion(),"HDGHAGS");
		assertEquals(obj.getNombreSeccion(),"Videojuegos");
	}
	
	@Test
	void test_equals() {
		Seccion obj1 = new Seccion(null, null, null);
		Seccion obj2 = new Seccion(null, null, null);
		
		obj1.setCodigoSeccion("HDGHAGS");
		obj2.setCodigoSeccion("HDGHAGS");
	boolean resultado = obj1.equals(obj2);
	assertTrue(resultado);
	
	obj1.setCodigoSeccion("HDGHAGS");
	obj2.setCodigoSeccion("GDSSDSD");
	boolean resultado2 = obj1.equals(obj2);
	assertFalse(resultado2);
	
	int notAObject=0;
	boolean resultado3 = obj1.equals(notAObject);
	assertFalse(resultado3);
	
	obj1.setCodigoSeccion("HDGHAGS");
	obj2.setCodigoSeccion(null);
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
		System.out.println(obj);
		assertEquals(obj.toString(),"Seccion [codigoSeccion=null, nombreSeccion=null]");
	}

}
