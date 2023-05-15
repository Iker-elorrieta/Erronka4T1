package test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.mysql.jdbc.Connection;

import controlador.Metodos;
import gestores.GestorCompra;
import modelo.Compra;
import modelo.Jefe;
import otros.tipoPersona;
import referencias.CONEXION;
import referencias.TABLAS;

@SuppressWarnings("javadoc")
class TestGestorCompra {
	Metodos mc=new Metodos();
	GestorCompra gc=new GestorCompra();
	Jefe jefe=new Jefe("77777777C","Test","Test",Date.valueOf("2001-01-21"),"testJefe@gmail.com","12345",tipoPersona.Jefe,Date.valueOf("2019-09-09"),(float)55.5,0);
	Compra compra=new Compra((float)10);
	Connection conexion;
	@Test 
	void testInsertarCompra() {
		Statement comando=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		gc.insertarCompra(conexion, jefe, compra);
		Compra com=null;
		ResultSet cargar=comando.executeQuery("SELECT * FROM "+TABLAS.COMPRAS+" ORDER BY "+TABLAS.FECHACOMPRA+" DESC LIMIT 1");
		while(cargar.next()) {
			com=new Compra(cargar.getInt(TABLAS.CODIGOCOMPRA),cargar.getFloat(TABLAS.PRECIOFINAL),mc.deStringALocalDateTime(cargar.getString(TABLAS.FECHACOMPRA)));
		}
		assertEquals(compra.getPrecioTotal(),com.getPrecioTotal());
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testCargarCompras() {
		Statement comando=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `compras` (`dni`, `codigoCompra`, `precioFinal`, `fechaCompra`) VALUES "
				+ "('"+jefe.getDni()+"', 0, "+compra.getPrecioTotal()+", '"+LocalDateTime.now()+"')");
		Compra com=null;
		ArrayList<Compra> lista=gc.cargarCompras(mc, conexion);
		assertTrue(lista.size()>1);
		com=lista.get(lista.size()-1);
		assertEquals(compra.getPrecioTotal(),com.getPrecioTotal());
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testBorrarCompra() {
		Statement comando=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `compras` (`dni`, `codigoCompra`, `precioFinal`, `fechaCompra`) VALUES "
				+ "('"+jefe.getDni()+"', 0, "+compra.getPrecioTotal()+", '"+LocalDateTime.now()+"')");
		Compra com=null;
		
		ResultSet cargar=comando.executeQuery("SELECT * FROM "+TABLAS.COMPRAS+" ORDER BY "+TABLAS.FECHACOMPRA+" DESC LIMIT 1");
		while(cargar.next()) {
			com=new Compra(cargar.getInt(TABLAS.CODIGOCOMPRA),cargar.getFloat(TABLAS.PRECIOFINAL),mc.deStringALocalDateTime(cargar.getString(TABLAS.FECHACOMPRA)));
		}
		gc.borrarCompra(conexion, com);
		Compra comprobar=null;
		cargar=comando.executeQuery("SELECT * FROM "+TABLAS.COMPRAS+" ORDER BY "+TABLAS.FECHACOMPRA+" DESC LIMIT 1");
		while(cargar.next()) {
			comprobar=new Compra(cargar.getInt(TABLAS.CODIGOCOMPRA),cargar.getFloat(TABLAS.PRECIOFINAL),mc.deStringALocalDateTime(cargar.getString(TABLAS.FECHACOMPRA)));
		}
		assertNotEquals(comprobar.getPrecioTotal(),com.getPrecioTotal());
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testCambiarCompra() {
		Statement comando=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `compras` (`dni`, `codigoCompra`, `precioFinal`, `fechaCompra`) VALUES "
				+ "('"+jefe.getDni()+"', 0, "+compra.getPrecioTotal()+", '"+LocalDateTime.now()+"')");
		Compra com=null;
		
		ResultSet cargar=comando.executeQuery("SELECT * FROM "+TABLAS.COMPRAS+" ORDER BY "+TABLAS.FECHACOMPRA+" DESC LIMIT 1");
		while(cargar.next()) {
			com=new Compra(cargar.getInt(TABLAS.CODIGOCOMPRA),cargar.getFloat(TABLAS.PRECIOFINAL),mc.deStringALocalDateTime(cargar.getString(TABLAS.FECHACOMPRA)));
		}
		Compra comprobar=com;
		comprobar.setPrecioTotal((float)8);
		gc.cambiarCompra(conexion, jefe, comprobar);
		
		cargar=comando.executeQuery("SELECT * FROM "+TABLAS.COMPRAS+" ORDER BY "+TABLAS.FECHACOMPRA+" DESC LIMIT 1");
		while(cargar.next()) {
			comprobar=new Compra(cargar.getInt(TABLAS.CODIGOCOMPRA),cargar.getFloat(TABLAS.PRECIOFINAL),mc.deStringALocalDateTime(cargar.getString(TABLAS.FECHACOMPRA)));
		}
		assertNotEquals(comprobar.getPrecioTotal(),compra.getPrecioTotal());
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void buscarCompraReciente() {
		Statement comando=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `compras` (`dni`, `codigoCompra`, `precioFinal`, `fechaCompra`) VALUES "
				+ "('"+jefe.getDni()+"', 0, "+compra.getPrecioTotal()+", '"+LocalDateTime.now()+"')");
		Compra com=null;
		
		ResultSet cargar=comando.executeQuery("SELECT * FROM "+TABLAS.COMPRAS+" ORDER BY "+TABLAS.FECHACOMPRA+" DESC LIMIT 1");
		while(cargar.next()) {
			com=new Compra(cargar.getInt(TABLAS.CODIGOCOMPRA),cargar.getFloat(TABLAS.PRECIOFINAL),mc.deStringALocalDateTime(cargar.getString(TABLAS.FECHACOMPRA)));
		}
		Compra comprobar=gc.buscarCompraReciente(mc, conexion);
		assertEquals(comprobar,com);
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void buscarCompraPersona() {
		Statement comando=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `compras` (`dni`, `codigoCompra`, `precioFinal`, `fechaCompra`) VALUES "
				+ "('"+jefe.getDni()+"', 0, "+compra.getPrecioTotal()+", '"+LocalDateTime.now()+"')");
		Compra com=null;
		
		ResultSet cargar=comando.executeQuery("SELECT * FROM "+TABLAS.COMPRAS+" ORDER BY "+TABLAS.FECHACOMPRA+" DESC LIMIT 1");
		while(cargar.next()) {
			com=new Compra(cargar.getInt(TABLAS.CODIGOCOMPRA),cargar.getFloat(TABLAS.PRECIOFINAL),mc.deStringALocalDateTime(cargar.getString(TABLAS.FECHACOMPRA)));
		}
		ArrayList<Compra> lista=gc.buscarComprasPersona(mc, conexion, jefe);
		Compra comprobar=lista.get(0);
		assertEquals(comprobar,com);
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
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
	

}


