package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

import org.junit.jupiter.api.Test;

import controlador.Metodos;
import excepciones.ErroresDeRegistro;
import gestores.GestorCompra;
import gestores.GestorPersona;
import modelo.Cliente;
import modelo.Compra;
import otros.tipoPersona;

class TestGestorCompra {
	Metodos mc=new Metodos();
	@Test
	void test() {
		GestorCompra g=new GestorCompra();
		GestorPersona g1=new GestorPersona();
		Cliente cliente = new Cliente("99999999X","Test","Test",Date.valueOf("2001-01-21"),"test@gmail.com","12345",tipoPersona.Cliente,(float)99.9,0);
		Compra co=new Compra((float)1.98);
		try {
			g.setListaCompras(g.cargarCompras());
			assertTrue(g.getListaCompras().size()>0);
			int antesDeInsertar=g.getListaCompras().size();
			assertEquals(g.getListaCompras().get(0).getCodigoCompra(),1);
			assertEquals(g.getListaCompras().get(0).getPrecioTotal(),(float)8.98);
			
			g1.insertarPersona(cliente);
			g.insertarCompra(cliente, co);
			g.setListaCompras(g.cargarCompras());
			assertTrue(g.getListaCompras().size()>antesDeInsertar);
			
			g.setListaCompras(g.cargarCompras());
			co=g.getListaCompras().get(antesDeInsertar);
			co.setPrecioTotal((float)5);
			g.cambiarCompra(cliente, co);
			assertEquals(g.buscarCompraReciente().getPrecioTotal(),co.getPrecioTotal());
			
			g.borrarCompra(co);
			g.setListaCompras(g.cargarCompras());
			assertEquals(g.getListaCompras().size(),antesDeInsertar);
			g1.darseBajaPersona(cliente);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
