package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.mysql.jdbc.Connection;

import controlador.Metodos;
import gestores.GestorCompra;
import gestores.GestorPersona;
import modelo.Compra;
import modelo.Jefe;
import otros.tipoPersona;
import referencias.CONEXION;

@SuppressWarnings("javadoc")
class TestGestorCompra {
	Metodos mc=new Metodos();
	GestorCompra gc=new GestorCompra();
	Jefe jefe=new Jefe("77777777C","Test","Test",Date.valueOf("2001-01-21"),"testJefe@gmail.com","12345",tipoPersona.Jefe,Date.valueOf("2019-09-09"),(float)55.5,0);
	Connection conexion;
	@Test
	void test_insertarCompra() {
		try {
			conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
			GestorPersona gp = new GestorPersona();
			Compra com = new Compra(gc.cargarCompras(mc, conexion).get(gc.cargarCompras(mc, conexion).size()-1).getCodigoCompra()+1, 25, null);
			gc.insertarCompra(conexion, gp.cargarPersonas(conexion).get(0), com);
			assertEquals(gc.cargarCompras(mc, conexion).get(gc.cargarCompras(mc, conexion).size()-1).getPrecioTotal(),25);
			gc.borrarCompra(conexion, com);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void test_borrarCompra() {
		
		try {
			conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
			GestorPersona gp = new GestorPersona();
			Compra com = new Compra(gc.cargarCompras(mc, conexion).get(gc.cargarCompras(mc, conexion).size()-1).getCodigoCompra()+1, 25, null);
			gc.insertarCompra(conexion, gp.cargarPersonas(conexion).get(0), com);
			assertEquals(gc.cargarCompras(mc, conexion).get(gc.cargarCompras(mc, conexion).size()-1).getPrecioTotal(),25);
			gc.borrarCompra(conexion, com);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void test_cambiarCompra() {
		try {
			conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
			GestorPersona gp = new GestorPersona();
			Compra com0 = new Compra(gc.cargarCompras(mc, conexion).get(gc.cargarCompras(mc, conexion).size()-1).getCodigoCompra()+1, 21, null);
			Compra com1 = new Compra(gc.cargarCompras(mc, conexion).get(gc.cargarCompras(mc, conexion).size()-1).getCodigoCompra()+1, 25, null);
			gc.cambiarCompra(conexion, gp.cargarPersonas(conexion).get(0), com1);
			//assertEquals(gc.cargarCompras(mc, conexion).get(gc.cargarCompras(mc, conexion).size()-1).getPrecioTotal(),25);
			gc.cambiarCompra(conexion, gp.cargarPersonas(conexion).get(0), com0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void test_buscarCompraReciente() {
		try {
			conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
			gc.buscarCompraReciente(mc, conexion);
			//assert
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	void test_gettyset() {
		GestorCompra gcP=new GestorCompra();
		ArrayList<Compra> lista=null;
		gcP.setListaCompras(lista);
		assertEquals(lista,gcP.getListaCompras());
	}
	
	
	
	@Test
	void test_buscarComprasPersona() {
		try {
			conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
			
			GestorPersona gp = new GestorPersona();
			assertEquals(gc.buscarComprasPersona(mc, conexion, gp.cargarPersonas(conexion).get(1)).get(0).getCodigoCompra(),1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}


