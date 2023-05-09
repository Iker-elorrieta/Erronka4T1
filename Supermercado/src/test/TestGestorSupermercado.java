package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import controlador.ErroresDeRegistro;
import controlador.GestorPersona;
import controlador.GestorSupermercado;
import controlador.Metodos;
import modelo.Jefe;
import modelo.Supermercado;
import modelo.tipoPersona;

class TestGestorSupermercado {
	Metodos mc=new Metodos();
	@Test
	void test() {
		Jefe jefe=new Jefe("77777777C","Test","Test",Date.valueOf("2001-01-21"),"testJefe@gmail.com","12345",tipoPersona.Jefe,Date.valueOf("2019-09-09"),(float)55.5,0);
		Supermercado su=new Supermercado("ABCDE","PruebaEmpresa","Errekamari",4,null);
		GestorSupermercado g=new GestorSupermercado();
		GestorPersona g1=new GestorPersona();
		try {
			g.setListaSupers(g.cargarSupermercados());
			assertTrue(g.getListaSupers().size()>0);
			int antesDeInsertar=g.getListaSupers().size();
			
			g1.insertarPersona(jefe);
			g.insertarSupermercado(jefe, su);
			g.setListaSupers(g.cargarSupermercados());
			assertTrue(antesDeInsertar<g.getListaSupers().size());
			assertEquals(g.getListaSupers().get(g.getListaSupers().size()-1).getCodigoSuper(),"ABCDE");
			assertEquals(g.getListaSupers().get(g.getListaSupers().size()-1).getEmpresa(),"PruebaEmpresa");
			
			su=new Supermercado("ABCDE","PruebaEmpresaCambio","Errekamari",4,null);
			g.cambiarSupermercado(su);
			g.setListaSupers(g.cargarSupermercados());
			assertEquals(g.getListaSupers().get(g.getListaSupers().size()-1).getCodigoSuper(),"ABCDE");
			assertEquals(g.getListaSupers().get(g.getListaSupers().size()-1).getEmpresa(),"PruebaEmpresaCambio");
			
			g.borrarSupermercado(su);
			g.setListaSupers(g.cargarSupermercados());
			assertNotEquals(g.getListaSupers().get(g.getListaSupers().size()-1).getCodigoSuper(),"ABCDE");
			assertNotEquals(g.getListaSupers().get(g.getListaSupers().size()-1).getEmpresa(),"PruebaEmpresaCambio");
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
