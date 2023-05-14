package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;

import org.junit.jupiter.api.Test;

import controlador.Metodos;
import excepciones.ErroresDeLogin;
import excepciones.ErroresDeOperaciones;
import excepciones.ErroresDeRegistro;
import gestores.GestorPersona;
import modelo.ArticuloComprado;
import modelo.Cliente;
import modelo.Comida;
import modelo.Compra;
import modelo.Herramienta;
import modelo.Jefe;
import modelo.Persona;
import modelo.Ropa;
import modelo.Seccion;
import modelo.Supermercado;
import otros.tipoArticulo;
import otros.tipoPersona;
import referencias.CONEXION;
import referencias.TABLAS;

class TestGestorPersona {
	Metodos mc=new Metodos();
	Connection conexion;
	GestorPersona gp=new GestorPersona();
	Jefe jefe=new Jefe("77777777C","Test","Test",Date.valueOf("2001-01-21"),"testJefe@gmail.com","12345",tipoPersona.Jefe,Date.valueOf("2019-09-09"),(float)55.5,0);
	Cliente cliente=new Cliente("77777777Z","Test","Test",Date.valueOf("2001-01-21"),"testCliente@gmail.com","12345",tipoPersona.Cliente,(float)100,0);
	Supermercado su=new Supermercado("ABCDE","PruebaEmpresa","Errekamari",4,null);
	Seccion se=new Seccion(su.getCodigoSuper()+"1",tipoArticulo.Herramienta,0,null);
	Seccion sec=new Seccion(su.getCodigoSuper()+"2",tipoArticulo.Comida,0,null);
	Seccion secc=new Seccion(su.getCodigoSuper()+"3",tipoArticulo.Ropa,0,null);
	Ropa ro=new Ropa(0, "Chanclas", "chanclas.png", "", (float)5.88, 99, "XL", "Supreme");
	Comida co=new Comida(0, "Fruta del dragon", "fdd.png", "", (float)3.49, 99, Date.valueOf("2023-12-31"),"Brasil");
	Herramienta he=new Herramienta(0, "Desatornillador", "desatornillador.png", "", (float)8.99, 99, 1, 4);
	@Test
	void testGetterSetter() {
		ArrayList<Persona> lista=null;
		gp.setListaPersonas(lista);
		assertEquals(lista,gp.getListaPersonas());
	}
	@Test
	void testInsertar() {
		Statement comando=null;
		try {
			conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
			comando= (Statement) conexion.createStatement();
			gp.insertarPersona(mc, conexion, cliente);
			gp.insertarPersona(mc, conexion, jefe);
			Cliente comprador=null;
			Jefe admin=null;
			ResultSet cuenta=comando.executeQuery("SELECT * FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+cliente.getDni()+"'");
			while(cuenta.next()) {
				comprador=new Cliente(cuenta.getString(TABLAS.DNI),
						cuenta.getString(TABLAS.NOMBRE), 
						cuenta.getString(TABLAS.APELLIDOS),
						cuenta.getDate(TABLAS.FECHANACIMIENTO),
						cuenta.getString(TABLAS.EMAIL),
						cuenta.getString(TABLAS.CONTRASENA),
						tipoPersona.valueOf(cuenta.getString(TABLAS.TIPO)),
						cuenta.getFloat(TABLAS.DINERO),
						cuenta.getInt(TABLAS.BLOQUEADO));
				}
			assertEquals(cliente.getContrasena(),comprador.getContrasena());
			assertEquals(cliente.isBloqueado(),comprador.isBloqueado());
			assertEquals(cliente.getApellidos(),comprador.getApellidos());
			assertEquals(cliente.getDni(),comprador.getDni());
			assertEquals(cliente.getEmail(),comprador.getEmail());
			assertEquals(cliente.getFechaNacimiento(),comprador.getFechaNacimiento());
			assertEquals(cliente.getNombre(),comprador.getNombre());
			cuenta=comando.executeQuery("SELECT * FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
			while(cuenta.next()) {
			admin=new Jefe(cuenta.getString(TABLAS.DNI), 
						cuenta.getString(TABLAS.NOMBRE), 
						cuenta.getString(TABLAS.APELLIDOS), 
						cuenta.getDate(TABLAS.FECHANACIMIENTO),
						cuenta.getString(TABLAS.EMAIL),
						cuenta.getString(TABLAS.CONTRASENA),
						tipoPersona.valueOf(cuenta.getString(TABLAS.TIPO)) ,
						cuenta.getDate(TABLAS.FECHAADQUISICION),
						cuenta.getFloat(TABLAS.PORCENTAJEEMPRESA),
						cuenta.getInt(TABLAS.DIOS));
			}
			assertEquals(jefe.getApellidos(),admin.getApellidos());
			assertEquals(jefe.getDni(),admin.getDni());
			assertEquals(jefe.getEmail(),admin.getEmail());
			assertEquals(jefe.getFechaNacimiento(),admin.getFechaNacimiento());
			assertEquals(jefe.getNombre(),admin.getNombre());
			assertEquals(jefe.getContrasena(),admin.getContrasena());
			assertEquals(jefe.isDios(),false);
			assertEquals(jefe.getFechaAdquisicion(),admin.getFechaAdquisicion());
			assertEquals(Float.valueOf(jefe.getPorcentajeEmpresa()),admin.getPorcentajeEmpresa());
			
			comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
			comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+cliente.getDni()+"'");
			comando.close();
			conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	@Test
	void testSeleccionar() {
		Statement comando=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `personas` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `dinero`, `bloqueado`) VALUES "
		+ "('"+cliente.getDni()+"', '"+cliente.getNombre()+"', '"+cliente.getApellidos()+"', '"+cliente.getFechaNacimiento()+"', '"+cliente.getEmail()+"', '"+cliente.getContrasena()+"', '"+cliente.getTipo()+"', "+cliente.getDinero()+", "+cliente.isBloqueado()+")");
		
		ArrayList<Persona> listaComprobar=new ArrayList<Persona>();
		ResultSet cuenta=comando.executeQuery("SELECT * FROM "+TABLAS.PERSONAS);
		Cliente comprador=null;
		Jefe admin=null;
		while(cuenta.next()) {
			if(cuenta.getString(TABLAS.TIPO).equals("Cliente")) {
			comprador=new Cliente(cuenta.getString(TABLAS.DNI),
					cuenta.getString(TABLAS.NOMBRE), 
					cuenta.getString(TABLAS.APELLIDOS),
					cuenta.getDate(TABLAS.FECHANACIMIENTO),
					cuenta.getString(TABLAS.EMAIL),
					cuenta.getString(TABLAS.CONTRASENA),
					tipoPersona.valueOf(cuenta.getString(TABLAS.TIPO)),
					cuenta.getFloat(TABLAS.DINERO),
					cuenta.getInt(TABLAS.BLOQUEADO));
			listaComprobar.add(comprador);
			}else {			
			admin=new Jefe(cuenta.getString(TABLAS.DNI), 
					cuenta.getString(TABLAS.NOMBRE), 
					cuenta.getString(TABLAS.APELLIDOS), 
					cuenta.getDate(TABLAS.FECHANACIMIENTO),
					cuenta.getString(TABLAS.EMAIL),
					cuenta.getString(TABLAS.CONTRASENA),
					tipoPersona.valueOf(cuenta.getString(TABLAS.TIPO)) ,
					cuenta.getDate(TABLAS.FECHAADQUISICION),
					cuenta.getFloat(TABLAS.PORCENTAJEEMPRESA),
					cuenta.getInt(TABLAS.DIOS));
			listaComprobar.add(admin);
			}
		}
		ArrayList<Persona> lista=gp.cargarPersonas(conexion);
		assertEquals(listaComprobar,lista);
		
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+cliente.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testCambiarPersona() {
		Statement comando=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `personas` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `dinero`, `bloqueado`) VALUES "
		+ "('"+cliente.getDni()+"', '"+cliente.getNombre()+"', '"+cliente.getApellidos()+"', '"+cliente.getFechaNacimiento()+"', '"+cliente.getEmail()+"', '"+cliente.getContrasena()+"', '"+cliente.getTipo()+"', "+cliente.getDinero()+", "+cliente.isBloqueado()+")");
		
		Cliente cliCambiado=cliente;
		cliCambiado.setApellidos("Diferente");
		cliCambiado.setContrasena("Contra");
		cliCambiado.setBloqueado(true);
		cliCambiado.setEmail("clientecambiado@gmail.com");
		Jefe jeCambiado=jefe;
		jeCambiado.setPorcentajeEmpresa((float)78);
		jeCambiado.setNombre("Jefe cambiado");
		jeCambiado.setApellidos("Diferente");
		jeCambiado.setFechaAdquisicion(new java.util.Date());
		
		gp.cambiarPerfilCliente(mc, conexion, jeCambiado);
		gp.cambiarPerfilCliente(mc, conexion, cliCambiado);
		
		Cliente comprador=null;
		Jefe admin=null;
		ResultSet cuenta=comando.executeQuery("SELECT * FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+cliente.getDni()+"'");
		while(cuenta.next()) {
			comprador=new Cliente(cuenta.getString(TABLAS.DNI),
					cuenta.getString(TABLAS.NOMBRE), 
					cuenta.getString(TABLAS.APELLIDOS),
					cuenta.getDate(TABLAS.FECHANACIMIENTO),
					cuenta.getString(TABLAS.EMAIL),
					cuenta.getString(TABLAS.CONTRASENA),
					tipoPersona.valueOf(cuenta.getString(TABLAS.TIPO)),
					cuenta.getFloat(TABLAS.DINERO),
					cuenta.getInt(TABLAS.BLOQUEADO));
			}
		assertEquals(cliCambiado.getContrasena(),comprador.getContrasena());
		assertEquals(cliCambiado.isBloqueado(),comprador.isBloqueado());
		assertEquals(cliCambiado.getApellidos(),comprador.getApellidos());
		assertEquals(cliCambiado.getDni(),comprador.getDni());
		assertEquals(cliCambiado.getEmail(),comprador.getEmail());
		assertEquals(cliCambiado.getFechaNacimiento(),comprador.getFechaNacimiento());
		assertEquals(cliCambiado.getNombre(),comprador.getNombre());
		cuenta=comando.executeQuery("SELECT * FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		while(cuenta.next()) {
		admin=new Jefe(cuenta.getString(TABLAS.DNI), 
					cuenta.getString(TABLAS.NOMBRE), 
					cuenta.getString(TABLAS.APELLIDOS), 
					cuenta.getDate(TABLAS.FECHANACIMIENTO),
					cuenta.getString(TABLAS.EMAIL),
					cuenta.getString(TABLAS.CONTRASENA),
					tipoPersona.valueOf(cuenta.getString(TABLAS.TIPO)) ,
					cuenta.getDate(TABLAS.FECHAADQUISICION),
					cuenta.getFloat(TABLAS.PORCENTAJEEMPRESA),
					cuenta.getInt(TABLAS.DIOS));
		}
		assertEquals(jeCambiado.getApellidos(),admin.getApellidos());
		assertEquals(jeCambiado.getDni(),admin.getDni());
		assertEquals(jeCambiado.getEmail(),admin.getEmail());
		assertEquals(jeCambiado.getFechaNacimiento(),admin.getFechaNacimiento());
		assertEquals(jeCambiado.getNombre(),admin.getNombre());
		assertEquals(jeCambiado.getContrasena(),admin.getContrasena());
		assertEquals(jeCambiado.isDios(),admin.isDios());
		assertEquals(jeCambiado.getFechaAdquisicion(),admin.getFechaAdquisicion());
		assertEquals(Float.valueOf(jefe.getPorcentajeEmpresa()),admin.getPorcentajeEmpresa());
		
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+cliente.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testBorrarPersona() {
		Statement comando=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `personas` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `dinero`, `bloqueado`) VALUES "
		+ "('"+cliente.getDni()+"', '"+cliente.getNombre()+"', '"+cliente.getApellidos()+"', '"+cliente.getFechaNacimiento()+"', '"+cliente.getEmail()+"', '"+cliente.getContrasena()+"', '"+cliente.getTipo()+"', "+cliente.getDinero()+", "+cliente.isBloqueado()+")");
		
		gp.darseBajaPersona(conexion, cliente);
		gp.darseBajaPersona(conexion, jefe);
		
		Cliente comprador=null;
		Jefe admin=null;
		ResultSet cuenta=comando.executeQuery("SELECT * FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+cliente.getDni()+"'");
		while(cuenta.next()) {
			comprador=new Cliente(cuenta.getString(TABLAS.DNI),
					cuenta.getString(TABLAS.NOMBRE), 
					cuenta.getString(TABLAS.APELLIDOS),
					cuenta.getDate(TABLAS.FECHANACIMIENTO),
					cuenta.getString(TABLAS.EMAIL),
					cuenta.getString(TABLAS.CONTRASENA),
					tipoPersona.valueOf(cuenta.getString(TABLAS.TIPO)),
					cuenta.getFloat(TABLAS.DINERO),
					cuenta.getInt(TABLAS.BLOQUEADO));
			}
		assertEquals(comprador,null);
		cuenta=comando.executeQuery("SELECT * FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		while(cuenta.next()) {
		admin=new Jefe(cuenta.getString(TABLAS.DNI), 
					cuenta.getString(TABLAS.NOMBRE), 
					cuenta.getString(TABLAS.APELLIDOS), 
					cuenta.getDate(TABLAS.FECHANACIMIENTO),
					cuenta.getString(TABLAS.EMAIL),
					cuenta.getString(TABLAS.CONTRASENA),
					tipoPersona.valueOf(cuenta.getString(TABLAS.TIPO)) ,
					cuenta.getDate(TABLAS.FECHAADQUISICION),
					cuenta.getFloat(TABLAS.PORCENTAJEEMPRESA),
					cuenta.getInt(TABLAS.DIOS));
		}
		assertEquals(admin,null);
		
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+cliente.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testAumentarDineroCliente() {
		Statement comando=null;
		float dineroAntes=(float)-1;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `personas` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `dinero`, `bloqueado`) VALUES "
		+ "('"+cliente.getDni()+"', '"+cliente.getNombre()+"', '"+cliente.getApellidos()+"', '"+cliente.getFechaNacimiento()+"', '"+cliente.getEmail()+"', '"+cliente.getContrasena()+"', '"+cliente.getTipo()+"', "+cliente.getDinero()+", "+cliente.isBloqueado()+")");
		
		dineroAntes=cliente.getDinero();
		gp.AumentarDineroCliente(conexion, cliente, 100);
		Cliente comprador=null;
		ResultSet cuenta=comando.executeQuery("SELECT * FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+cliente.getDni()+"'");
		while(cuenta.next()) {
			comprador=new Cliente(cuenta.getString(TABLAS.DNI),
					cuenta.getString(TABLAS.NOMBRE), 
					cuenta.getString(TABLAS.APELLIDOS),
					cuenta.getDate(TABLAS.FECHANACIMIENTO),
					cuenta.getString(TABLAS.EMAIL),
					cuenta.getString(TABLAS.CONTRASENA),
					tipoPersona.valueOf(cuenta.getString(TABLAS.TIPO)),
					cuenta.getFloat(TABLAS.DINERO),
					cuenta.getInt(TABLAS.BLOQUEADO));
			}
		assertTrue(comprador.getDinero()==(dineroAntes+100));
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+cliente.getDni()+"'");
		comando.close();
		conexion.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testBloquearCliente() {
		Statement comando=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `personas` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `dinero`, `bloqueado`) VALUES "
		+ "('"+cliente.getDni()+"', '"+cliente.getNombre()+"', '"+cliente.getApellidos()+"', '"+cliente.getFechaNacimiento()+"', '"+cliente.getEmail()+"', '"+cliente.getContrasena()+"', '"+cliente.getTipo()+"', "+cliente.getDinero()+", "+cliente.isBloqueado()+")");
		gp.cambiarEstadoUsuario(mc, conexion, cliente, true);
		Cliente comprador=null;
		ResultSet cuenta=comando.executeQuery("SELECT * FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+cliente.getDni()+"'");
		while(cuenta.next()) {
			comprador=new Cliente(cuenta.getString(TABLAS.DNI),
					cuenta.getString(TABLAS.NOMBRE), 
					cuenta.getString(TABLAS.APELLIDOS),
					cuenta.getDate(TABLAS.FECHANACIMIENTO),
					cuenta.getString(TABLAS.EMAIL),
					cuenta.getString(TABLAS.CONTRASENA),
					tipoPersona.valueOf(cuenta.getString(TABLAS.TIPO)),
					cuenta.getFloat(TABLAS.DINERO),
					cuenta.getInt(TABLAS.BLOQUEADO));
			}
		assertNotEquals(cliente.isBloqueado(),comprador.isBloqueado());
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+cliente.getDni()+"'");
		comando.close();
		conexion.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testComprobarCampos() {
		try {
			gp.comprobarCampos("", "", "", "", "", "");
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			assertEquals(e.getMessage(),"Alguno de los campos esta vacio");
		}
	}
	@Test
	void testComprobarNacimiento() {
		try {
			gp.comprobarNacimiento("2022-09-23");
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			assertEquals(e.getMessage(),"Los menores de edad no pueden registrarse");
		}
	}
	@Test
	void testComprobarEmail() {
		try {
			gp.comprobarEmail("nada");
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			assertEquals(e.getMessage(),"El email no tiene el patron correcto");
		}
	}
	@Test
	void testComprobarDNI() {
		try {
			gp.comprobarDNI("1234567AB");
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			assertEquals(e.getMessage(),"Los 8 primeros caracteres no son todos numeros");
			try {
				gp.comprobarDNI("123456N");
			} catch (ErroresDeRegistro e1) {
				// TODO Auto-generated catch block
				assertEquals(e1.getMessage(),"El dni no tiene 9 caracteres");
			}
		}
	}
	@Test
	void testIniciarSesion() {
		Statement comando=null;
		ArrayList<Persona> listaComprobar=new ArrayList<Persona>();
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `personas` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `dinero`, `bloqueado`) VALUES "
		+ "('"+cliente.getDni()+"', '"+cliente.getNombre()+"', '"+cliente.getApellidos()+"', '"+cliente.getFechaNacimiento()+"', '"+cliente.getEmail()+"', '"+cliente.getContrasena()+"', '"+cliente.getTipo()+"', "+cliente.getDinero()+", "+cliente.isBloqueado()+")");

		ResultSet cuenta=comando.executeQuery("SELECT * FROM "+TABLAS.PERSONAS);
		Cliente comprador=null;
		Jefe admin=null;
		while(cuenta.next()) {
			if(cuenta.getString(TABLAS.TIPO).equals("Cliente")) {
			comprador=new Cliente(cuenta.getString(TABLAS.DNI),
					cuenta.getString(TABLAS.NOMBRE), 
					cuenta.getString(TABLAS.APELLIDOS),
					cuenta.getDate(TABLAS.FECHANACIMIENTO),
					cuenta.getString(TABLAS.EMAIL),
					cuenta.getString(TABLAS.CONTRASENA),
					tipoPersona.valueOf(cuenta.getString(TABLAS.TIPO)),
					cuenta.getFloat(TABLAS.DINERO),
					cuenta.getInt(TABLAS.BLOQUEADO));
			listaComprobar.add(comprador);
			}else {			
			admin=new Jefe(cuenta.getString(TABLAS.DNI), 
					cuenta.getString(TABLAS.NOMBRE), 
					cuenta.getString(TABLAS.APELLIDOS), 
					cuenta.getDate(TABLAS.FECHANACIMIENTO),
					cuenta.getString(TABLAS.EMAIL),
					cuenta.getString(TABLAS.CONTRASENA),
					tipoPersona.valueOf(cuenta.getString(TABLAS.TIPO)) ,
					cuenta.getDate(TABLAS.FECHAADQUISICION),
					cuenta.getFloat(TABLAS.PORCENTAJEEMPRESA),
					cuenta.getInt(TABLAS.DIOS));
			listaComprobar.add(admin);
			}
		}
		Cliente cliComprobar=(Cliente) gp.iniciarSesion(listaComprobar, cliente.getEmail(), cliente.getContrasena());
		assertEquals(cliente.getContrasena(),cliComprobar.getContrasena());
		assertEquals(cliente.isBloqueado(),cliComprobar.isBloqueado());
		assertEquals(cliente.getApellidos(),cliComprobar.getApellidos());
		assertEquals(cliente.getDni(),cliComprobar.getDni());
		assertEquals(cliente.getEmail(),cliComprobar.getEmail());
		assertEquals(cliente.getFechaNacimiento(),cliComprobar.getFechaNacimiento());
		assertEquals(cliente.getNombre(),cliComprobar.getNombre());
		
		gp.iniciarSesion(listaComprobar, cliente.getEmail(), "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErroresDeLogin e) {
			// TODO Auto-generated catch block
			assertEquals(e.getMessage(),"Algun campo esta vacío.");
			try {
			gp.iniciarSesion(listaComprobar, cliente.getEmail(), cliente.getNombre());
			} catch (ErroresDeLogin e1) {
				// TODO Auto-generated catch block
				assertEquals(e1.getMessage(),"No esta registrado o campos incorrectos.");
				try {
				comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
				comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+cliente.getDni()+"'");
				comando.close();
				conexion.close();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		}
	}
	
	@Test
	void testBuscarPersona() {
		Statement comando=null;
		ArrayList<Persona> listaComprobar=new ArrayList<Persona>();
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `personas` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `dinero`, `bloqueado`) VALUES "
		+ "('"+cliente.getDni()+"', '"+cliente.getNombre()+"', '"+cliente.getApellidos()+"', '"+cliente.getFechaNacimiento()+"', '"+cliente.getEmail()+"', '"+cliente.getContrasena()+"', '"+cliente.getTipo()+"', "+cliente.getDinero()+", "+cliente.isBloqueado()+")");

		ResultSet cuenta=comando.executeQuery("SELECT * FROM "+TABLAS.PERSONAS);
		Cliente comprador=null;
		Jefe admin=null;
		while(cuenta.next()) {
			if(cuenta.getString(TABLAS.TIPO).equals("Cliente")) {
			comprador=new Cliente(cuenta.getString(TABLAS.DNI),
					cuenta.getString(TABLAS.NOMBRE), 
					cuenta.getString(TABLAS.APELLIDOS),
					cuenta.getDate(TABLAS.FECHANACIMIENTO),
					cuenta.getString(TABLAS.EMAIL),
					cuenta.getString(TABLAS.CONTRASENA),
					tipoPersona.valueOf(cuenta.getString(TABLAS.TIPO)),
					cuenta.getFloat(TABLAS.DINERO),
					cuenta.getInt(TABLAS.BLOQUEADO));
			listaComprobar.add(comprador);
			}else {			
			admin=new Jefe(cuenta.getString(TABLAS.DNI), 
					cuenta.getString(TABLAS.NOMBRE), 
					cuenta.getString(TABLAS.APELLIDOS), 
					cuenta.getDate(TABLAS.FECHANACIMIENTO),
					cuenta.getString(TABLAS.EMAIL),
					cuenta.getString(TABLAS.CONTRASENA),
					tipoPersona.valueOf(cuenta.getString(TABLAS.TIPO)) ,
					cuenta.getDate(TABLAS.FECHAADQUISICION),
					cuenta.getFloat(TABLAS.PORCENTAJEEMPRESA),
					cuenta.getInt(TABLAS.DIOS));
			listaComprobar.add(admin);
			}
		}
		Cliente cliComprobar=(Cliente) gp.buscarPersona(cliente.getDni(), listaComprobar);
		assertEquals(cliente.getContrasena(),cliComprobar.getContrasena());
		assertEquals(cliente.isBloqueado(),cliComprobar.isBloqueado());
		assertEquals(cliente.getApellidos(),cliComprobar.getApellidos());
		assertEquals(cliente.getDni(),cliComprobar.getDni());
		assertEquals(cliente.getEmail(),cliComprobar.getEmail());
		assertEquals(cliente.getFechaNacimiento(),cliComprobar.getFechaNacimiento());
		assertEquals(cliente.getNombre(),cliComprobar.getNombre());
		
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+cliente.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testCargarJefesSinSupermercado() {
		Statement comando=null;
		ArrayList<Persona> listaComprobar=new ArrayList<Persona>();
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");

		ResultSet cuenta=comando.executeQuery("SELECT * FROM "+TABLAS.PERSONAS);
		Cliente comprador=null;
		Jefe admin=null;
		while(cuenta.next()) {
			if(cuenta.getString(TABLAS.TIPO).equals("Cliente")) {
			comprador=new Cliente(cuenta.getString(TABLAS.DNI),
					cuenta.getString(TABLAS.NOMBRE), 
					cuenta.getString(TABLAS.APELLIDOS),
					cuenta.getDate(TABLAS.FECHANACIMIENTO),
					cuenta.getString(TABLAS.EMAIL),
					cuenta.getString(TABLAS.CONTRASENA),
					tipoPersona.valueOf(cuenta.getString(TABLAS.TIPO)),
					cuenta.getFloat(TABLAS.DINERO),
					cuenta.getInt(TABLAS.BLOQUEADO));
			listaComprobar.add(comprador);
			}else {			
			admin=new Jefe(cuenta.getString(TABLAS.DNI), 
					cuenta.getString(TABLAS.NOMBRE), 
					cuenta.getString(TABLAS.APELLIDOS), 
					cuenta.getDate(TABLAS.FECHANACIMIENTO),
					cuenta.getString(TABLAS.EMAIL),
					cuenta.getString(TABLAS.CONTRASENA),
					tipoPersona.valueOf(cuenta.getString(TABLAS.TIPO)) ,
					cuenta.getDate(TABLAS.FECHAADQUISICION),
					cuenta.getFloat(TABLAS.PORCENTAJEEMPRESA),
					cuenta.getInt(TABLAS.DIOS));
			listaComprobar.add(admin);
			}
		}
		ArrayList<Jefe> lista=gp.cargarJefesSinSupermercado(conexion, 1);
		Jefe nuevo=null;
		for(Jefe je:lista) {
			if(je.getDni().equals(jefe.getDni())) {
				nuevo=je;
			}
		}
		assertEquals(jefe.getApellidos(),nuevo.getApellidos());
		assertEquals(jefe.getDni(),nuevo.getDni());
		assertEquals(jefe.getEmail(),nuevo.getEmail());
		assertEquals(jefe.getFechaNacimiento(),nuevo.getFechaNacimiento());
		assertEquals(jefe.getNombre(),nuevo.getNombre());
		assertEquals(jefe.getContrasena(),nuevo.getContrasena());
		assertEquals(jefe.isDios(),nuevo.isDios());
		assertEquals(jefe.getFechaAdquisicion(),nuevo.getFechaAdquisicion());
		assertEquals(Float.valueOf(jefe.getPorcentajeEmpresa()),nuevo.getPorcentajeEmpresa());
		
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+cliente.getDni()+"'");
		
		gp.cargarJefesSinSupermercado(conexion, 2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErroresDeOperaciones e) {
			// TODO Auto-generated catch block
			assertEquals(e.getMessage(),"No hay Jefes disponibles");
			try {
				comando.close();
				conexion.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	@Test
	void testInsertarYCobrar() {
		Statement comando=null;
		Compra compraN=null;
		Compra compra=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `personas` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `dinero`, `bloqueado`) VALUES "
		+ "('"+cliente.getDni()+"', '"+cliente.getNombre()+"', '"+cliente.getApellidos()+"', '"+cliente.getFechaNacimiento()+"', '"+cliente.getEmail()+"', '"+cliente.getContrasena()+"', '"+cliente.getTipo()+"', "+cliente.getDinero()+", "+cliente.isBloqueado()+")");
		
		compra=new Compra((float)10);
		
		Persona per=cliente;
		gp.insertarCompraYCobrar(conexion, per, compra);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				ResultSet cargar=comando.executeQuery("SELECT * FROM "+TABLAS.COMPRAS+" ORDER BY "+TABLAS.FECHACOMPRA+" DESC LIMIT 1");
				while(cargar.next()) {
					compraN=new Compra(cargar.getInt(TABLAS.CODIGOCOMPRA),cargar.getFloat(TABLAS.PRECIOFINAL),mc.deStringALocalDateTime(cargar.getString(TABLAS.FECHACOMPRA)));
				}
				assertTrue(compraN.getCodigoCompra()>1);
				assertTrue(compra.getPrecioTotal()==compraN.getPrecioTotal());
				comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
				comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+cliente.getDni()+"'");
				comando.close();
				conexion.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testInsertarArticulos() {
		Statement comando=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `personas` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `dinero`, `bloqueado`) VALUES "
		+ "('"+cliente.getDni()+"', '"+cliente.getNombre()+"', '"+cliente.getApellidos()+"', '"+cliente.getFechaNacimiento()+"', '"+cliente.getEmail()+"', '"+cliente.getContrasena()+"', '"+cliente.getTipo()+"', "+cliente.getDinero()+", "+cliente.isBloqueado()+")");
		comando.executeUpdate("INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES "
		+ "('"+jefe.getDni()+"', '"+su.getCodigoSuper()+"', '"+su.getEmpresa()+"', '"+su.getDireccion()+"', "+su.getNumEmpleados()+");");
		comando.executeUpdate("INSERT INTO "+TABLAS.SECCION+" VALUES ('"+su.getCodigoSuper()+"','"+se.getCodigoSeccion()+"','"+se.getNombreSeccion()+"','"+se.getNumArticulo()+"')");
		comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULO+" (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`, `fechaCaducidad`, `procedencia`) VALUES "
		+ "(0, '"+se.getCodigoSeccion()+"', '"+co.getNombreArticulo()+"', '"+co.getRutaImagen()+"', '"+co.getDescripcion()+"', "+co.getPrecio()+", "+co.getStockActual()+", '"+co.getFechaCaducidad()+"', '"+co.getProcedencia()+"')");
		Compra compra=new Compra((co.getPrecio()*2));
		comando.executeUpdate("INSERT INTO "+TABLAS.COMPRAS+" (dni,precioFinal,fechaCompra) VALUES ('"+cliente.getDni()+"',"+compra.getPrecioTotal()+",current_timestamp())");
		ResultSet cargar=comando.executeQuery("SELECT * FROM "+TABLAS.COMPRAS+" ORDER BY "+TABLAS.FECHACOMPRA+" DESC LIMIT 1");
		while(cargar.next()) {
			compra=new Compra(cargar.getInt(TABLAS.CODIGOCOMPRA),cargar.getFloat(TABLAS.PRECIOFINAL),mc.deStringALocalDateTime(cargar.getString(TABLAS.FECHACOMPRA)));
		}
		ArticuloComprado arc=null;
		int idar=0;
		cargar=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"' AND "+TABLAS.NOMBREARTICULO+"='"+co.getNombreArticulo()+"'");
		while(cargar.next()) {
			idar=cargar.getInt(TABLAS.IDARTICULO);
		}
		arc=new ArticuloComprado(compra.getCodigoCompra(),idar,2,co.getPrecio());
		ArrayList<ArticuloComprado> lista=new ArrayList<ArticuloComprado>();
		lista.add(arc);
		gp.insertarArticulos(conexion, lista, compra.getCodigoCompra());
		
		cargar=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULOSCOMPRADOS+" WHERE "+TABLAS.CODIGOCOMPRA+"='"+compra.getCodigoCompra()+"'");
		ArticuloComprado arcPrueba=null;
		while(cargar.next()) {
			arcPrueba=new ArticuloComprado(cargar.getInt(TABLAS.CODIGOCOMPRA),cargar.getInt(TABLAS.IDARTICULO),cargar.getInt(TABLAS.CANTIDAD),cargar.getFloat(TABLAS.PRECIOART));
		}
		assertTrue(null!=arcPrueba);
		assertEquals(arcPrueba.getCodigoCompra(),compra.getCodigoCompra());
		assertEquals(arc.getIdArticulo(),idar);
		assertEquals(arc.getCantidad(),2);
		assertTrue(arc.getPrecioArt()==co.getPrecio());
		
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+cliente.getDni()+"'");
		comando.close();
		conexion.close();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
}
