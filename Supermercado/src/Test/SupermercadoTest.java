package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Modelo.Seccion;
import Modelo.Supermercado;

class SupermercadoTest {
	ArrayList<Seccion> arraySecciones = new ArrayList<Seccion>();
Supermercado sup = new Supermercado(null, null, 0, 0, null, null);
	
	@Test
	void test_GettersSetters() {
		sup.setArraySecciones(arraySecciones);
		sup.setCodigoSuper("XDFDSF");
		sup.setDireccion("calle central");
		sup.setHorario("18:00 - 22:00");
		sup.setMetrosCuadrados((float) 23.1);
		sup.setNumEmpleados(4);
		
		assertEquals(sup.getArraySecciones(),arraySecciones);
		assertEquals(sup.getCodigoSuper(),"XDFDSF");
		assertEquals(sup.getDireccion(),"calle central");
		assertEquals(sup.getHorario(),"18:00 - 22:00");
		assertEquals(Float.valueOf(sup.getMetrosCuadrados()),(float) 23.1);
		assertEquals(sup.getNumEmpleados(),4);
	}
	
	@Test
	void test_equals() {
		Supermercado sup1 = new Supermercado(null, null, 0, 0, null, arraySecciones);
		Supermercado sup2 = new Supermercado(null, null, 0, 0, null, arraySecciones);
		
		sup1.setCodigoSuper("GASVGDVG");
		sup2.setCodigoSuper("GASVGDVG");
	boolean resultado = sup1.equals(sup2);
	assertTrue(resultado);
	
	sup1.setCodigoSuper("GASVGDVG");
	sup2.setCodigoSuper("DHVGDVG");
	boolean resultado2 = sup1.equals(sup2);
	assertFalse(resultado2);
	
	int notAObject=0;
	boolean resultado3 = sup1.equals(notAObject);
	assertFalse(resultado3);
	
	sup1.setCodigoSuper("GASVGDVG");
	sup2.setCodigoSuper(null);
	boolean resultado4 = sup1.equals(sup2);
	assertFalse(resultado4);
	
	boolean resultado5 = sup1.equals(sup1);
	assertTrue(resultado5);
	
	boolean resultado6 = sup1.equals(null);
	assertFalse(resultado6);
	
	sup1.hashCode();
	}
	
	@Test
	void test_toString() {
		assertEquals(sup.toString(),"Supermercado [codigoSuper=null, direccion=null, metrosCuadrados=0.0, numEmpleados=0, horario=null]");
	}

}
