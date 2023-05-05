package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import org.junit.jupiter.api.Test;

import controlador.ErroresDeLogin;
import controlador.ErroresDeOperaciones;
import controlador.ErroresDeRegistro;
import controlador.GestorArticulo;
import controlador.GestorPersona;
import controlador.Metodos;
import modelo.Cliente;
import modelo.Jefe;
import modelo.Persona;
import modelo.tipoPersona;

class TestGestorPersona {
	Metodos mc=new Metodos();
	GestorArticulo ga=new GestorArticulo();
	GestorPersona gp=new GestorPersona();
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

}
