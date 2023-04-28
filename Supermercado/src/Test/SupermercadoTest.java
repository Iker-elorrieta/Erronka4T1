package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Modelo.Articulo;
import Modelo.ArticulosComprados;
import Modelo.Jefe;
import Modelo.Ropa;
import Modelo.Seccion;
import Modelo.Supermercado;
import Modelo.tipoArticulo;

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
	@Test
	void testMetodos() {
//		Jefe je=new Jefe("154352K", null, null, null, null, null, null, null, 0, false);
//		ArrayList<Seccion> listaSec=new ArrayList<Seccion>();
//		Seccion se=new Seccion("DHYH","Deportes",null);
//		listaSec.add(se);
//		Supermercado sup1 = new Supermercado(null, null, 0, 0, null, listaSec);
//		Ropa ro=new Ropa(1,"zapatillas de correr","dgfftfgfg.png","incredible 10/10",23.0,10,90,tipoArticulo.Ropa,"S","morado","cuero","nike");
//		ArrayList<Articulo> listaAr=new ArrayList<Articulo>();
//		listaAr.add(ro);
//		ArticulosComprados ar=new ArticulosComprados(1,1,2,23);
//		ArrayList<ArticulosComprados> listaArc=new ArrayList<ArticulosComprados>();
//		listaArc.add(ar);
//		se.setArrayArticulos(listaAr);
//		try {
//			sup1.emergenciaSanitaria(je, listaSec, listaArc);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
}
}
