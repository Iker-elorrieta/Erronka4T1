package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
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
import referencias.CONEXION;

class TestGestorCompra {
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
		GestorCompra g=new GestorCompra();
		GestorPersona g1=new GestorPersona();
		Cliente cliente = new Cliente("99999999X","Test","Test",Date.valueOf("2001-01-21"),"test@gmail.com","12345",tipoPersona.Cliente,(float)99.9,0);
		Compra co=new Compra((float)1.98);
		try {
			g.setListaCompras(g.cargarCompras(mc,conexion));
			assertTrue(g.getListaCompras().size()>0);
			int antesDeInsertar=g.getListaCompras().size();
			assertEquals(g.getListaCompras().get(0).getCodigoCompra(),1);
			assertEquals(g.getListaCompras().get(0).getPrecioTotal(),(float)8.98);
			
			g1.insertarPersona(mc,conexion,cliente);
			g.insertarCompra(conexion,cliente, co);
			g.setListaCompras(g.cargarCompras(mc,conexion));
			assertTrue(g.getListaCompras().size()>antesDeInsertar);
			
			g.setListaCompras(g.cargarCompras(mc,conexion));
			co=g.getListaCompras().get(antesDeInsertar);
			co.setPrecioTotal((float)5);
			g.cambiarCompra(conexion,cliente, co);
			assertEquals(g.buscarCompraReciente(mc,conexion).getPrecioTotal(),co.getPrecioTotal());
			
			g.borrarCompra(conexion,co);
			g.setListaCompras(g.cargarCompras(mc,conexion));
			assertEquals(g.getListaCompras().size(),antesDeInsertar);
			g1.darseBajaPersona(conexion,cliente);
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
