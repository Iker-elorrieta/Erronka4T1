package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

import org.junit.jupiter.api.Test;

import controlador.ErroresDeRegistro;
import controlador.GestorArticuloComprado;
import controlador.GestorCompra;
import controlador.GestorPersona;
import controlador.Metodos;
import modelo.ArticuloComprado;
import modelo.Cliente;
import modelo.Compra;
import modelo.tipoPersona;

class TestGestorArticuloComprado {
	Metodos mc=new Metodos();
	@Test
	void test() {
		GestorCompra g=new GestorCompra();
		GestorPersona g1=new GestorPersona();
		GestorArticuloComprado gac=new GestorArticuloComprado();
		Cliente cliente = new Cliente("99999999X","Test","Test",Date.valueOf("2001-01-21"),"test@gmail.com","12345",tipoPersona.Cliente,(float)99.9,0);
		Compra co=new Compra((float)1.98);
		
		try {
			gac.setListaArticulosComprados(gac.cargarArticulosComprados());
			assertTrue(gac.getListaArticulosComprados().size()>0);
			int antesDeInsertar=gac.getListaArticulosComprados().size();
			
			g1.insertarPersona(cliente);
			g.insertarCompra(cliente, co);
			g.setListaCompras(g.cargarCompras());
			co=g.buscarCompraReciente();
			ArticuloComprado ac=new ArticuloComprado(co.getCodigoCompra(), 1, 1, (float)3.99);
			ArticuloComprado ac1=null;
			gac.insertarArticuloComprado(co, ac);
			
			gac.setListaArticulosComprados(gac.cargarArticulosComprados());
			ac1=gac.getListaArticulosComprados().get(antesDeInsertar);
			assertEquals(ac1.getCantidad(),ac.getCantidad());
			assertEquals(ac1.getCodigoCompra(),ac.getCodigoCompra());
			assertEquals(ac1.getIdArticulo(),ac.getIdArticulo());
			
			Compra coBusqueda=new Compra(1,(float)3.99,null);
			gac.setListaArticulosComprados(gac.cargarArticulosDeUnaCompra(coBusqueda));
			assertEquals(gac.getListaArticulosComprados().get(0).getPrecioArt(),coBusqueda.getPrecioTotal());
			assertEquals(gac.getListaArticulosComprados().get(0).getCodigoCompra(),coBusqueda.getCodigoCompra());
			
			ac=new ArticuloComprado(co.getCodigoCompra(), 1, 2, (float)9.49);
			gac.cambiarArticuloComprado(ac);
			gac.setListaArticulosComprados(gac.cargarArticulosComprados());
			ac1=gac.getListaArticulosComprados().get(antesDeInsertar);
			assertEquals(ac1.getCantidad(),ac.getCantidad());
			assertEquals(ac1.getCodigoCompra(),ac.getCodigoCompra());
			assertEquals(ac1.getIdArticulo(),ac.getIdArticulo());
			assertEquals(ac1.getPrecioArt(),ac.getPrecioArt());
			
			gac.borrarArticuloComprado(ac1);
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
