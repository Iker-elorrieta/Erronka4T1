package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import controlador.Metodos;
import excepciones.ErroresDeOperaciones;
import gestores.GestorSupermercado;
import modelo.Cliente;
import modelo.Jefe;
import modelo.Persona;
import modelo.Seccion;
import modelo.Supermercado;
import otros.tipoArticulo;
import otros.tipoPersona;
import referencias.CONEXION;
import referencias.TABLAS;

class TestGestorSupermercado {
	Metodos mc=new Metodos();
	GestorSupermercado g=new GestorSupermercado();
	Jefe jefe=new Jefe("77777777C","Test","Test",Date.valueOf("2001-01-21"),"testJefe@gmail.com","12345",tipoPersona.Jefe,Date.valueOf("2019-09-09"),(float)55.5,0);
	Supermercado su=new Supermercado("ABCDE","PruebaEmpresa","Errekamari",4,null);
	ArrayList<Supermercado> lista=new ArrayList<Supermercado>();
	Connection conexion;
	@Test
	void testGetterSetter() {
	ArrayList<Supermercado> lista=null;
	g.setListaSupers(lista);
	assertEquals(lista,g.getListaSupers());
	}
	@Test
	void testInsertarSupermercado() {
		Supermercado nuevo=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		Statement comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
				+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		g.insertarSupermercado(conexion, jefe, su);
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO+" WHERE "+TABLAS.CODIGOSUPER+"='"+su.getCodigoSuper()+"'");
		while(carga.next()) {
			nuevo=new Supermercado(carga.getString(TABLAS.CODIGOSUPER),carga.getString(TABLAS.EMPRESA),carga.getString(TABLAS.DIRECCION), carga.getInt(TABLAS.NUMEROEMPLEADOS), null);
		}
		assertEquals(nuevo.getCodigoSuper(),su.getCodigoSuper());
		assertEquals(nuevo.getDireccion(),su.getDireccion());
		assertEquals(nuevo.getEmpresa(),su.getEmpresa());
		assertEquals(nuevo.getNumEmpleados(),su.getNumEmpleados());
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	@Test
	void testSeleccionarSupermercado() {
		try {
			conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
			Statement comando= (Statement) conexion.createStatement();
			lista=g.cargarSupermercados(conexion);
			int cantidad=lista.size();
			comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
					+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
			comando.executeUpdate("INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES "
					+ "('"+jefe.getDni()+"', '"+su.getCodigoSuper()+"', '"+su.getEmpresa()+"', '"+su.getDireccion()+"', "+su.getNumEmpleados()+");");
			lista=g.cargarSupermercados(conexion);
			assertNotEquals(cantidad,lista.size());
			assertEquals(cantidad+1,lista.size());
			comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
			comando.close();
			conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		@Test
	void testCambiarSupermercado() {
			try {
		Supermercado comprobar=null;
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		Statement comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES "
		+ "('"+jefe.getDni()+"', '"+su.getCodigoSuper()+"', '"+su.getEmpresa()+"', '"+su.getDireccion()+"', "+su.getNumEmpleados()+");");
		Supermercado cambio=su;
		cambio.setEmpresa("Cambio");
		cambio.setDireccion("Calle cambio");
		cambio.setNumEmpleados(100);
		g.cambiarSupermercado(conexion, cambio);
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO+" WHERE "+TABLAS.CODIGOSUPER+"='"+cambio.getCodigoSuper()+"'");
		while(carga.next()) {
		comprobar=new Supermercado(carga.getString(TABLAS.CODIGOSUPER),carga.getString(TABLAS.EMPRESA),carga.getString(TABLAS.DIRECCION), carga.getInt(TABLAS.NUMEROEMPLEADOS), null);
		}
		assertEquals(comprobar.getCodigoSuper(),cambio.getCodigoSuper());
		assertEquals(comprobar.getDireccion(),cambio.getDireccion());
		assertEquals(comprobar.getEmpresa(),cambio.getEmpresa());
		assertEquals(comprobar.getNumEmpleados(),cambio.getNumEmpleados());
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	@Test
	void testBorrarSupermercado() {
		try {
		int existe=-1;
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		Statement comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES "
		+ "('"+jefe.getDni()+"', '"+su.getCodigoSuper()+"', '"+su.getEmpresa()+"', '"+su.getDireccion()+"', "+su.getNumEmpleados()+");");
		g.borrarSupermercado(conexion, su);
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO+" WHERE "+TABLAS.CODIGOSUPER+"='"+su.getCodigoSuper()+"'");
		while(carga.next()) {
		existe++;
		}
		assertNotEquals(existe,0);
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testBuscarSupermercado() {
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		Statement comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES "
		+ "('"+jefe.getDni()+"', '"+su.getCodigoSuper()+"', '"+su.getEmpresa()+"', '"+su.getDireccion()+"', "+su.getNumEmpleados()+");");
		Supermercado buscado=g.buscarSupermercado(conexion, jefe);
		assertEquals(buscado.getCodigoSuper(),su.getCodigoSuper());
		assertEquals(buscado.getDireccion(),su.getDireccion());
		assertEquals(buscado.getEmpresa(),su.getEmpresa());
		assertEquals(buscado.getNumEmpleados(),su.getNumEmpleados());
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testTodosSupermercado() {
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		Statement comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES "
		+ "('"+jefe.getDni()+"', '"+su.getCodigoSuper()+"', '"+su.getEmpresa()+"', '"+su.getDireccion()+"', "+su.getNumEmpleados()+");");
		ArrayList<Persona> listaPersonas=new ArrayList<Persona>();
		Cliente comprador=null;
		Jefe jefe=null;
		ResultSet cuenta=comando.executeQuery("SELECT * FROM "+TABLAS.PERSONAS);
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
			listaPersonas.add(comprador);
			}else {			
			jefe=new Jefe(cuenta.getString(TABLAS.DNI), 
					cuenta.getString(TABLAS.NOMBRE), 
					cuenta.getString(TABLAS.APELLIDOS), 
					cuenta.getDate(TABLAS.FECHANACIMIENTO),
					cuenta.getString(TABLAS.EMAIL),
					cuenta.getString(TABLAS.CONTRASENA),
					tipoPersona.valueOf(cuenta.getString(TABLAS.TIPO)) ,
					cuenta.getDate(TABLAS.FECHAADQUISICION),
					cuenta.getFloat(TABLAS.PORCENTAJEEMPRESA),
					cuenta.getInt(TABLAS.DIOS));
			listaPersonas.add(jefe);
			}
		}
		ArrayList<Supermercado> listaTodosSuper=new ArrayList<Supermercado>();
		listaTodosSuper=g.todoSupermercados(conexion, listaPersonas);
		ArrayList<Supermercado> lista=new ArrayList<Supermercado>();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO);
		while(carga.next()) {
			Supermercado comprobar=new Supermercado(carga.getString(TABLAS.CODIGOSUPER),carga.getString(TABLAS.EMPRESA),carga.getString(TABLAS.DIRECCION), carga.getInt(TABLAS.NUMEROEMPLEADOS), null);
			lista.add(comprobar);
		}
		assertEquals(lista,listaTodosSuper);
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testBuscarSupermercadoEmpresa() {
		Statement comando = null;
		try {
		ArrayList<Supermercado> listaBuscaSuperEmpresa=new ArrayList<Supermercado>();
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES "
		+ "('"+jefe.getDni()+"', '"+su.getCodigoSuper()+"', '"+su.getEmpresa()+"', '"+su.getDireccion()+"', "+su.getNumEmpleados()+");");
		listaBuscaSuperEmpresa=g.buscarSuperEmpresa(conexion, su.getEmpresa());
		ArrayList<Supermercado> listaComprobar=new ArrayList<Supermercado>();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO+" WHERE "+TABLAS.EMPRESA+"='"+su.getEmpresa()+"'");
		while(carga.next()) {
			Supermercado comprobar=new Supermercado(carga.getString(TABLAS.CODIGOSUPER),carga.getString(TABLAS.EMPRESA),carga.getString(TABLAS.DIRECCION),carga.getInt(TABLAS.NUMEROEMPLEADOS),null);
			listaComprobar.add(comprobar);
		}
		assertEquals(listaComprobar,listaBuscaSuperEmpresa);
		listaBuscaSuperEmpresa=g.buscarSuperEmpresa(conexion, "Fallo");
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErroresDeOperaciones e) {
			// TODO Auto-generated catch block
			assertEquals("No se pudo encontrar el supermercado escogido",e.getMessage());
			try {
				comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
				comando.close();
				conexion.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	@Test
	void testCogerSeccionSuper() {
		Statement comando = null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES "
		+ "('"+jefe.getDni()+"', '"+su.getCodigoSuper()+"', '"+su.getEmpresa()+"', '"+su.getDireccion()+"', "+su.getNumEmpleados()+");");
		comando.executeUpdate("INSERT INTO "+TABLAS.SECCION+" VALUES ('"+su.getCodigoSuper()+"', '"+su.getCodigoSuper()+"1"+"', 'Comida',1)");
		Supermercado comprobar=null;
		Supermercado prueba=null;
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO+" WHERE "+TABLAS.EMPRESA+"='"+su.getEmpresa()+"'");
		while(carga.next()) {
			comprobar=new Supermercado(carga.getString(TABLAS.CODIGOSUPER),carga.getString(TABLAS.EMPRESA),carga.getString(TABLAS.DIRECCION),carga.getInt(TABLAS.NUMEROEMPLEADOS),null);
		}
		prueba=comprobar;
		prueba=g.cogerSeccionesSuper(conexion, prueba);
		ArrayList<Seccion> listaSe=new ArrayList<Seccion>();
		Seccion se=null;
		carga=comando.executeQuery("SELECT * FROM "+TABLAS.SECCION+" WHERE "+TABLAS.CODIGOSUPER+"='"+su.getCodigoSuper()+"'");
		while(carga.next()) {
			se=new Seccion(carga.getString(TABLAS.CODIGOSECCION), tipoArticulo.valueOf(carga.getString(TABLAS.TIPO)),carga.getInt(TABLAS.NUMAR), null);
			listaSe.add(se);
		}
		comprobar.setArraySecciones(listaSe);
		assertEquals(prueba,comprobar);
		assertEquals(prueba.getArraySecciones(),comprobar.getArraySecciones());
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testBuscarSuperEmpresaDireccion() {
		Statement comando = null;
		Supermercado comprobar=null;
		Supermercado prueba=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES "
		+ "('"+jefe.getDni()+"', '"+su.getCodigoSuper()+"', '"+su.getEmpresa()+"', '"+su.getDireccion()+"', "+su.getNumEmpleados()+");");
		prueba=g.buscarSuperEmpresaDireccion(conexion, su.getEmpresa(), su.getDireccion());
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO+" WHERE "+TABLAS.EMPRESA+"='"+su.getEmpresa()+"' AND "+TABLAS.DIRECCION+"='"+su.getDireccion()+"'");
		while(carga.next()) {
			comprobar=new Supermercado(carga.getString(TABLAS.CODIGOSUPER),carga.getString(TABLAS.EMPRESA),carga.getString(TABLAS.DIRECCION),carga.getInt(TABLAS.NUMEROEMPLEADOS),null);
		}
		assertEquals(comprobar.getCodigoSuper(),prueba.getCodigoSuper());
		assertEquals(comprobar.getEmpresa(),prueba.getEmpresa());
		assertEquals(comprobar.getDireccion(),prueba.getDireccion());
		assertEquals(comprobar.getNumEmpleados(),prueba.getNumEmpleados());
		g.buscarSuperEmpresaDireccion(conexion, "Fallo", "Fallo");
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
		}catch(SQLException e) {
			e.printStackTrace();
		} catch (ErroresDeOperaciones e) {
			// TODO Auto-generated catch block
			assertEquals(e.getMessage(),"No se pudo encontrar el supermercado escogido");
			try {
				comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
				comando.close();
				conexion.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	@Test
	void testCogerSeccionesMultiplesSupermercados() {
		Statement comando = null;
		Supermercado prueba=null;
		ArrayList<Supermercado> listaFuncion=new ArrayList<Supermercado>();
		ArrayList<Supermercado> listaPrueba=new ArrayList<Supermercado>();
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES "
		+ "('"+jefe.getDni()+"', '"+su.getCodigoSuper()+"', '"+su.getEmpresa()+"', '"+su.getDireccion()+"', "+su.getNumEmpleados()+");");
		comando.executeUpdate("INSERT INTO "+TABLAS.SECCION+" VALUES ('"+su.getCodigoSuper()+"', '"+su.getCodigoSuper()+"1"+"', 'Comida',1)");
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO+" WHERE "+TABLAS.EMPRESA+"='"+su.getEmpresa()+"' AND "+TABLAS.DIRECCION+"='"+su.getDireccion()+"'");
		while(carga.next()) {
			prueba=new Supermercado(carga.getString(TABLAS.CODIGOSUPER),carga.getString(TABLAS.EMPRESA),carga.getString(TABLAS.DIRECCION),carga.getInt(TABLAS.NUMEROEMPLEADOS),null);
			listaFuncion.add(prueba);
			listaPrueba.add(prueba);
		}
		for(Supermercado anadir:listaPrueba) {
		ArrayList<Seccion> listaSe=new ArrayList<Seccion>();
		carga=comando.executeQuery("SELECT * FROM "+TABLAS.SECCION+" WHERE "+TABLAS.CODIGOSUPER+"='"+anadir.getCodigoSuper()+"'");
		while(carga.next()) {
			Seccion se=new Seccion(carga.getString(TABLAS.CODIGOSECCION), tipoArticulo.valueOf(carga.getString(TABLAS.TIPO)),carga.getInt(TABLAS.NUMAR), null);
			listaSe.add(se);
			
		}
		anadir.setArraySecciones(listaSe);
		}
		listaFuncion=g.cogerSeccionesMultiplesSu(conexion, listaFuncion);
		assertEquals(listaFuncion,listaPrueba);
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
