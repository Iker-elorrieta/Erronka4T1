package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;

import org.junit.jupiter.api.Test;

import com.mysql.jdbc.Connection;

import controlador.Metodos;
import gestores.GestorArticulo;
import gestores.GestorArticuloComprado;
import gestores.GestorCompra;
import gestores.GestorPersona;
import modelo.ArticuloComprado;
import modelo.Cliente;
import modelo.Compra;
import otros.tipoPersona;
import referencias.CONEXION;

@SuppressWarnings("javadoc")
class TestGestorArticuloComprado {
	Metodos mc=new Metodos();
	GestorArticuloComprado gac=new GestorArticuloComprado();
	Connection conexion;
	@Test
	void test() {
		try {
			conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		GestorCompra g=new GestorCompra();
		GestorPersona g1=new GestorPersona();
		GestorArticuloComprado gac=new GestorArticuloComprado();
		Cliente cliente = new Cliente("99999999X","Test","Test",Date.valueOf("2001-01-21"),"test@gmail.com","12345",tipoPersona.Cliente,(float)99.9,0);
		Compra co=new Compra((float)1.98);

		try {
			gac.setListaArticulosComprados(gac.cargarArticulosComprados(conexion));
			assertTrue(gac.getListaArticulosComprados().size()>0);
			int antesDeInsertar=gac.getListaArticulosComprados().size();

			g1.insertarPersona(mc,conexion,cliente);
			g.insertarCompra(conexion,cliente, co);
			g.setListaCompras(g.cargarCompras(mc,conexion));
			co=g.buscarCompraReciente(mc,conexion);
			ArticuloComprado ac=new ArticuloComprado(co.getCodigoCompra(), 1, 1, (float)3.99);
			ArticuloComprado ac1=null;
			gac.insertarArticuloComprado(conexion,co, ac);

			gac.setListaArticulosComprados(gac.cargarArticulosComprados(conexion));
			ac1=gac.getListaArticulosComprados().get(antesDeInsertar);
			assertEquals(ac1.getCantidad(),ac.getCantidad());
			assertEquals(ac1.getCodigoCompra(),ac.getCodigoCompra());
			assertEquals(ac1.getIdArticulo(),ac.getIdArticulo());

			Compra coBusqueda=new Compra(1,(float)3.99,null);
			gac.setListaArticulosComprados(gac.cargarArticulosDeUnaCompra(conexion,coBusqueda));
			assertEquals(gac.getListaArticulosComprados().get(0).getPrecioArt(),coBusqueda.getPrecioTotal());
			assertEquals(gac.getListaArticulosComprados().get(0).getCodigoCompra(),coBusqueda.getCodigoCompra());

			ac=new ArticuloComprado(co.getCodigoCompra(), 1, 2, (float)9.49);
			gac.cambiarArticuloComprado(conexion,ac);
			gac.setListaArticulosComprados(gac.cargarArticulosComprados(conexion));
			ac1=gac.getListaArticulosComprados().get(antesDeInsertar);
			assertEquals(ac1.getCantidad(),ac.getCantidad());
			assertEquals(ac1.getCodigoCompra(),ac.getCodigoCompra());
			assertEquals(ac1.getIdArticulo(),ac.getIdArticulo());
			assertEquals(ac1.getPrecioArt(),ac.getPrecioArt());

			gac.borrarArticuloComprado(conexion,ac1);
			g1.darseBajaPersona(conexion,cliente);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//
	
	@Test
	void test_buscarArticuloComprado() {
		try {
			conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
			GestorArticulo ga = new GestorArticulo();
			
			assertEquals(gac.buscarArticuloComprado(conexion, ga.cargarArticulos(conexion).get(2)).getCantidad(),2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void test_cargarArticuloCompradoCod() {
	try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		
		assertEquals(gac.cargarArticuloCompradoCod(conexion, 1).get(0).getCantidad(),2);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	}
	
	

}
