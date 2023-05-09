package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.ResultSet;

import org.junit.jupiter.api.Test;

import controlador.Metodos;
import excepciones.ErroresDeLogin;
import excepciones.ErroresDeOperaciones;
import excepciones.ErroresDeRegistro;
import gestores.GestorArticulo;
import gestores.GestorArticuloComprado;
import gestores.GestorCompra;
import gestores.GestorPersona;
import modelo.Articulo;
import modelo.ArticuloComprado;
import modelo.Cliente;
import modelo.Compra;
import modelo.Jefe;
import modelo.Persona;
import otros.tipoPersona;
import referencias.TABLAS;

class TestGestorPersona {
	Metodos mc=new Metodos();
	GestorArticulo ga=new GestorArticulo();
	GestorArticuloComprado gac=new GestorArticuloComprado();
	GestorPersona gp=new GestorPersona();
	GestorCompra gc=new GestorCompra();
	@Test
	void test() {
		GestorPersona g=new GestorPersona();
		try {
			Cliente cliente = new Cliente("99999999X","Test","Test",Date.valueOf("2001-01-21"),"test@gmail.com","12345",tipoPersona.Cliente,(float)99.9,0);
			g.setListaPersonas(g.cargarPersonas());
			
			assertTrue(g.getListaPersonas()!=null);
			assertTrue(g.getListaPersonas().size()>0);
			int antesInsert=g.getListaPersonas().size();
			assertEquals(g.getListaPersonas().get(0).getDni(),"00000000A");
			assertEquals(g.getListaPersonas().get(0).getEmail(),"admin@gmail.com");
			
			g.insertarPersona(cliente);
			g.setListaPersonas(g.cargarPersonas());
			assertTrue(g.getListaPersonas().size()>antesInsert);
			assertEquals(g.getListaPersonas().get(g.getListaPersonas().size()-1).getDni(),"99999999X");
			assertEquals(g.getListaPersonas().get(g.getListaPersonas().size()-1).getEmail(),"test@gmail.com");
			
			
			cliente=new Cliente("99999999X","Test","Test",Date.valueOf("2001-01-21"),"testCambio@gmail.com","12345",tipoPersona.Cliente,(float)99.9,0);
			g.cambiarPerfilCliente(cliente);
			
			g.setListaPersonas(g.cargarPersonas());
			assertEquals(g.getListaPersonas().get(g.getListaPersonas().size()-1).getDni(),"99999999X");
			assertEquals(g.getListaPersonas().get(g.getListaPersonas().size()-1).getEmail(),"testCambio@gmail.com");
			
			Cliente cliDinero=(Cliente)g.getListaPersonas().get(g.getListaPersonas().size()-1);
			try {
				g.AumentarDineroCliente(cliente, 10);
			} catch (ErroresDeOperaciones e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.cambiarEstadoUsuario(cliente, true);
			g.setListaPersonas(g.cargarPersonas());
			Cliente dineroCambio=(Cliente)g.getListaPersonas().get(g.getListaPersonas().size()-1);
			assertNotEquals(cliDinero.getDinero(),dineroCambio.getDinero());
			assertNotEquals(cliDinero.isBloqueado(),dineroCambio.isBloqueado());
			
			g.darseBajaPersona(cliente);
			g.setListaPersonas(g.cargarPersonas());
			assertNotEquals(g.getListaPersonas().get(g.getListaPersonas().size()-1).getDni(),"99999999X");
			assertNotEquals(g.getListaPersonas().get(g.getListaPersonas().size()-1).getEmail(),"testCambio@gmail.com");
			
			Jefe jefe=new Jefe("77777777C","Test","Test",Date.valueOf("2001-01-21"),"testJefe@gmail.com","12345",tipoPersona.Jefe,Date.valueOf("2019-09-09"),(float)55.5,0);
			g.insertarPersona(jefe);
			g.setListaPersonas(g.cargarPersonas());
			assertTrue(g.getListaPersonas().size()>antesInsert);
			
			
			jefe=new Jefe("77777777C","Test","Test",Date.valueOf("2001-01-21"),"testJefeCambio@gmail.com","12345",tipoPersona.Jefe,Date.valueOf("2019-09-09"),(float)55.5,0);
			g.cambiarPerfilCliente(jefe);
			g.setListaPersonas(g.cargarPersonas());
			Persona buscada=g.buscarPersona("77777777C", g.getListaPersonas());
			assertEquals(buscada.getDni(),"77777777C");
			assertEquals(buscada.getEmail(),"testJefeCambio@gmail.com");
			
			g.darseBajaPersona(jefe);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	void test_comprobarCampos(){
		try {
			gp.comprobarCampos("juan", "lopez", "1234", "55448K", "2004-03-01", "dasd@ssgd");
			gp.comprobarCampos("juan", "lopez", "1234", "55448K", "2004-03-01", "");
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	@Test
	void test_comprobarNacimiento(){
		try {
			gp.comprobarNacimiento("1999-10-30");
			gp.comprobarNacimiento("2009-03-01");
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	@Test
	void test_comprobarEmail(){
		try {
			gp.comprobarEmail("gsdsd@gmail.com");
			gp.comprobarEmail("dhdggd");
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	@Test
	void test_comprobarDNI(){
		try {
			gp.comprobarDNI("70124029L");
			gp.comprobarDNI("322LL");
			
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		try {
			gp.comprobarDNI("hsdsbdhsL");
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	@Test
	void test_iniciarSesion(){
		ArrayList<Persona> listaUser=null;
		try {
			listaUser=gp.cargarPersonas();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Jefe je=null;
		try {
			je=(Jefe) gp.iniciarSesion(listaUser, "admin@gmail.com", "Elorrieta00");
		} catch (ErroresDeLogin e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(je.getDni(),"00000000A");
		Cliente cli=null;
		try {
			cli=(Cliente) gp.iniciarSesion(listaUser, "prueba@gmail.com", "Elorrieta00");
		} catch (ErroresDeLogin e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(cli.getDni(),"11111111A");
		try {
			gp.iniciarSesion(listaUser, "", "");
		} catch (ErroresDeLogin e) {
			// TODO Auto-generated catch block
			
		}
		try {
			gp.iniciarSesion(listaUser, "falloseguro@gmail.com", "fallo");
		} catch (ErroresDeLogin e) {
			// TODO Auto-generated catch block
			
		}
	}
	@Test
	void testMetodos() {
		Cliente cliente = new Cliente("99999999X","Test","Test",Date.valueOf("2001-01-21"),"test@gmail.com","12345",tipoPersona.Cliente,(float)99.9,0);
		Cliente prueba=null;
		Compra co=new Compra((float)10);
		Articulo ar=null;
		try {
			ga.setListaArticulos(ga.cargarArticulos());
			ar=ga.getListaArticulos().get(0);
			co.anadirArticulo(ga.getListaArticulos().get(0),2);
			gp.insertarPersona(cliente);
			co.setPrecioTotal(co.calcularPrecioTotal());
			
			ArrayList<ArticuloComprado> lista=new ArrayList<ArticuloComprado>();
			int codCompra= gp.insertarCompraYCobrar(cliente, co);
			ArticuloComprado arc=new ArticuloComprado(codCompra,1,2,(float)1.99);
			lista.add(arc);
			gp.insertarArticulos(lista,codCompra);
			
			Compra c=gc.buscarCompraReciente();
			assertNotEquals(co.getCodigoCompra(),c.getCodigoCompra());
			assertNotEquals(co.getFechaCompra(),c.getFechaCompra());
			assertNotEquals(co.calcularPrecioTotal(),c.calcularPrecioTotal());
			assertNotEquals(ar.getStockActual(),ar.getStockActual()-1);
			
			gp.cancelarArticulos(c);
			gp.setListaPersonas(gp.cargarPersonas());
			int posicion=gp.getListaPersonas().indexOf(cliente);
			prueba=(Cliente) gp.getListaPersonas().get(posicion);
			assertNotEquals(prueba.getDinero(),cliente.getDinero());
			
			gp.cancelarCompra(c);
			Compra c1=gc.buscarCompraReciente();
			assertNotEquals(c.getCodigoCompra(),c1.getCodigoCompra());
			assertNotEquals(c.getFechaCompra(),c1.getFechaCompra());
			assertNotEquals(ar.getStockActual(),ar.getStockActual()+1);
			
			lista=new ArrayList<ArticuloComprado>();
			codCompra= gp.insertarCompraYCobrar(cliente, co);
			arc=new ArticuloComprado(codCompra,1,2,(float)1.99);
			lista.add(arc);
			gp.insertarArticulos(lista,codCompra);
			
			c=gc.buscarCompraReciente();
			assertNotEquals(co.getCodigoCompra(),c.getCodigoCompra());
			assertNotEquals(co.getFechaCompra(),c.getFechaCompra());
			assertNotEquals(co.calcularPrecioTotal(),c.calcularPrecioTotal());
			assertNotEquals(ar.getStockActual(),ar.getStockActual()-1);
			
			gp.devolverUnArticulo(cliente, arc, 1);
			
			Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
			ResultSet carga=comando.executeQuery("SELECT "+TABLAS.CANTIDAD+" FROM "+TABLAS.ARTICULOSCOMPRADOS+" WHERE "+TABLAS.CODIGOCOMPRA+"='"+arc.getCodigoCompra()+"'");
			int cant=arc.getCantidad();
			while(carga.next()) {
				cant=carga.getInt(TABLAS.CANTIDAD);
			}
			assertNotEquals(cant,arc.getCantidad());
			
			gp.devolverUnArticulo(cliente, arc, 1);
			carga=comando.executeQuery("SELECT "+TABLAS.CANTIDAD+" FROM "+TABLAS.ARTICULOSCOMPRADOS+" WHERE "+TABLAS.CODIGOCOMPRA+"='"+arc.getCodigoCompra()+"'");
			int existe=-1;
			while(carga.next()) {
				existe++;
			}
			assertEquals(-1,existe);
			carga=comando.executeQuery("SELECT * FROM "+TABLAS.COMPRAS+" WHERE "+TABLAS.CODIGOCOMPRA+"='"+arc.getCodigoCompra()+"'");
			existe=-1;
			while(carga.next()) {
				existe++;
			}
			assertEquals(-1,existe);
			
			gp.darseBajaPersona(cliente);
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
