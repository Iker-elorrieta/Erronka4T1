package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import controlador.ErroresDeRegistro;
import controlador.GestorPersona;
import controlador.GestorSeccion;
import controlador.GestorSupermercado;
import controlador.Metodos;
import modelo.Jefe;
import modelo.Seccion;
import modelo.Supermercado;
import modelo.tipoArticulo;
import modelo.tipoPersona;

class TestGestorSeccion {
	Metodos mc=new Metodos();
	@Test
	void test() {
		GestorSeccion g=new GestorSeccion();
		GestorSupermercado g2=new GestorSupermercado();
		GestorPersona g1=new GestorPersona();
		Jefe jefe=new Jefe("77777777C","Test","Test",Date.valueOf("2001-01-21"),"testJefe@gmail.com","12345",tipoPersona.Jefe,Date.valueOf("2019-09-09"),(float)55.5,0);
		Supermercado su=new Supermercado("ABCDE","PruebaEmpresa","Errekamari",4,null);
		Seccion se=new Seccion("OOOO",tipoArticulo.Herramienta,null);
		try {
			g.setListaSecciones(g.cargarSecciones());
			assertTrue(g.getListaSecciones().size()>0);
			int antesDeInsertar=g.getListaSecciones().size();
			assertEquals(g.getListaSecciones().get(0).getCodigoSeccion(),"A0001");
			assertEquals(g.getListaSecciones().get(0).getNombreSeccion(),tipoArticulo.Comida);
			
			g1.insertarPersona(jefe);
			g2.insertarSupermercado(jefe, su);
			g.insertarSeccion(su, se);
			g.setListaSecciones(g.cargarSecciones());
			assertTrue(g.getListaSecciones().size()>antesDeInsertar);
			assertEquals(g.getListaSecciones().get(g.getListaSecciones().size()-1).getCodigoSeccion(),"OOOO");
			assertEquals(g.getListaSecciones().get(g.getListaSecciones().size()-1).getNombreSeccion(),tipoArticulo.Herramienta);
			
			se=new Seccion("OOOO",tipoArticulo.Ropa,null);
			g.cambiarSeccion(su, se);
			g.setListaSecciones(g.cargarSecciones());
			assertEquals(g.getListaSecciones().get(g.getListaSecciones().size()-1).getCodigoSeccion(),"OOOO");
			assertEquals(g.getListaSecciones().get(g.getListaSecciones().size()-1).getNombreSeccion(),tipoArticulo.Ropa);
			
			g.borrarSeccion(se);
			se=g.getListaSecciones().get(g.getListaSecciones().size()-1);
			g.setListaSecciones(g.cargarSecciones());
			assertNotEquals(g.getListaSecciones().get(g.getListaSecciones().size()-1).getCodigoSeccion(),"OOOO");
			assertNotEquals(g.getListaSecciones().get(g.getListaSecciones().size()-1).getNombreSeccion(),se);
			g1.darseBajaPersona(jefe);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
