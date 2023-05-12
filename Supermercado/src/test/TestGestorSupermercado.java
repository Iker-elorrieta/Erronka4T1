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
import gestores.GestorSupermercado;
import modelo.Jefe;
import modelo.Supermercado;
import otros.tipoPersona;
import referencias.CONEXION;

class TestGestorSupermercado {
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
		Jefe jefe=new Jefe("77777777C","Test","Test",Date.valueOf("2001-01-21"),"testJefe@gmail.com","12345",tipoPersona.Jefe,Date.valueOf("2019-09-09"),(float)55.5,0);
		Supermercado su=new Supermercado("ABCDE","PruebaEmpresa","Errekamari",4,null);
		GestorSupermercado g=new GestorSupermercado();
		GestorPersona g1=new GestorPersona();
		try {
			g.setListaSupers(g.cargarSupermercados(conexion));
			assertTrue(g.getListaSupers().size()>0);
			int antesDeInsertar=g.getListaSupers().size();
			
			g1.insertarPersona(mc,conexion,jefe);
			g.insertarSupermercado(conexion,jefe, su);
			g.setListaSupers(g.cargarSupermercados(conexion));
			assertTrue(antesDeInsertar<g.getListaSupers().size());
			assertEquals(g.getListaSupers().get(g.getListaSupers().size()-1).getCodigoSuper(),"ABCDE");
			assertEquals(g.getListaSupers().get(g.getListaSupers().size()-1).getEmpresa(),"PruebaEmpresa");
			
			su=new Supermercado("ABCDE","PruebaEmpresaCambio","Errekamari",4,null);
			g.cambiarSupermercado(conexion,su);
			g.setListaSupers(g.cargarSupermercados(conexion));
			assertEquals(g.getListaSupers().get(g.getListaSupers().size()-1).getCodigoSuper(),"ABCDE");
			assertEquals(g.getListaSupers().get(g.getListaSupers().size()-1).getEmpresa(),"PruebaEmpresaCambio");
			
			g.borrarSupermercado(conexion,su);
			g.setListaSupers(g.cargarSupermercados(conexion));
			assertNotEquals(g.getListaSupers().get(g.getListaSupers().size()-1).getCodigoSuper(),"ABCDE");
			assertNotEquals(g.getListaSupers().get(g.getListaSupers().size()-1).getEmpresa(),"PruebaEmpresaCambio");
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
