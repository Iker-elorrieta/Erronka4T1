package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import controlador.Metodos;
import excepciones.ErroresDeRegistro;
import gestores.GestorPersona;
import gestores.GestorSeccion;
import gestores.GestorSupermercado;
import modelo.Jefe;
import modelo.Seccion;
import modelo.Supermercado;
import otros.tipoArticulo;
import otros.tipoPersona;
import referencias.CONEXION;

class TestGestorSeccion {
	Metodos mc=new Metodos();
	Connection conexion;
	@Test
	void test() {
		try {
			conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		GestorSeccion g=new GestorSeccion();
		GestorSupermercado g2=new GestorSupermercado();
		GestorPersona g1=new GestorPersona();
		Jefe jefe=new Jefe("77777777C","Test","Test",Date.valueOf("2001-01-21"),"testJefe@gmail.com","12345",tipoPersona.Jefe,Date.valueOf("2019-09-09"),(float)55.5,0);
		Supermercado su=new Supermercado("ABCDE","PruebaEmpresa","Errekamari",4,null);
		Seccion se=new Seccion("OOOO",tipoArticulo.Herramienta,0,null);
		try {
			g.setListaSecciones(g.cargarSecciones(conexion));
			assertTrue(g.getListaSecciones().size()>0);
			int antesDeInsertar=g.getListaSecciones().size();
			assertEquals(g.getListaSecciones().get(0).getCodigoSeccion(),"A0001");
			assertEquals(g.getListaSecciones().get(0).getNombreSeccion(),tipoArticulo.Comida);
			
			g1.insertarPersona(mc,conexion,jefe);
			g2.insertarSupermercado(conexion,jefe, su);
			g.insertarSeccion(conexion,su, se);
			g.setListaSecciones(g.cargarSecciones(conexion));
			assertTrue(g.getListaSecciones().size()>antesDeInsertar);
			assertEquals(g.getListaSecciones().get(g.getListaSecciones().size()-1).getCodigoSeccion(),"OOOO");
			assertEquals(g.getListaSecciones().get(g.getListaSecciones().size()-1).getNombreSeccion(),tipoArticulo.Herramienta);
			
			se=new Seccion("OOOO",tipoArticulo.Ropa,0,null);
			g.cambiarSeccion(conexion,se);
			g.setListaSecciones(g.cargarSecciones(conexion));
			assertEquals(g.getListaSecciones().get(g.getListaSecciones().size()-1).getCodigoSeccion(),"OOOO");
			assertEquals(g.getListaSecciones().get(g.getListaSecciones().size()-1).getNombreSeccion(),tipoArticulo.Ropa);
			
			g.borrarSeccion(conexion,se);
			se=g.getListaSecciones().get(g.getListaSecciones().size()-1);
			g.setListaSecciones(g.cargarSecciones(conexion));
			assertNotEquals(g.getListaSecciones().get(g.getListaSecciones().size()-1).getCodigoSeccion(),"OOOO");
			assertNotEquals(g.getListaSecciones().get(g.getListaSecciones().size()-1).getNombreSeccion(),se);
			g1.darseBajaPersona(conexion,jefe);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
