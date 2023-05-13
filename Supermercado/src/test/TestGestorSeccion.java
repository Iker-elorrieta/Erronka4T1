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
import gestores.GestorSeccion;
import modelo.Jefe;
import modelo.Seccion;
import modelo.Supermercado;
import otros.tipoArticulo;
import otros.tipoPersona;
import referencias.CONEXION;
import referencias.TABLAS;

class TestGestorSeccion {
	Metodos mc=new Metodos();
	GestorSeccion gse=new GestorSeccion();
	Jefe jefe=new Jefe("77777777C","Test","Test",Date.valueOf("2001-01-21"),"testJefe@gmail.com","12345",tipoPersona.Jefe,Date.valueOf("2019-09-09"),(float)55.5,0);
	Supermercado su=new Supermercado("ABCDE","PruebaEmpresa","Errekamari",4,null);
	Seccion se=new Seccion(su.getCodigoSuper()+"1",tipoArticulo.Herramienta,0,null);
	Connection conexion;
	@Test
	void testGetterSetter() {
		ArrayList<Seccion> lista=null;
		gse.setListaSecciones(lista);
		assertEquals(lista,gse.getListaSecciones());
	}
	@Test
	void testInsertar() {
		Statement comando=null;
		Seccion sec=null;
		try {
			conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
			comando= (Statement) conexion.createStatement();
			comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
			+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
			comando.executeUpdate("INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES "
			+ "('"+jefe.getDni()+"', '"+su.getCodigoSuper()+"', '"+su.getEmpresa()+"', '"+su.getDireccion()+"', "+su.getNumEmpleados()+");");
			gse.insertarSeccion(conexion, su, se);
			ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SECCION+" WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
			while(carga.next()) {
				sec=new Seccion(carga.getString(TABLAS.CODIGOSECCION),tipoArticulo.valueOf(carga.getString(TABLAS.TIPO)),carga.getInt(TABLAS.NUMAR),null);
			}
			assertEquals(se.getCodigoSeccion(),sec.getCodigoSeccion());
			assertEquals(se.getNombreSeccion(),sec.getNombreSeccion());
			assertEquals(se.getNumArticulo(),sec.getNumArticulo());
			comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
			comando.close();
			conexion.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	@Test
	void testSeleccionar() {
		Statement comando=null;
		ArrayList<Seccion> listaNormal=new ArrayList<Seccion>();
		ArrayList<Seccion> listaComprobar=new ArrayList<Seccion>();
		Seccion sec=null;
			try {
			conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
			comando= (Statement) conexion.createStatement();
			comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
			+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
			comando.executeUpdate("INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES "
			+ "('"+jefe.getDni()+"', '"+su.getCodigoSuper()+"', '"+su.getEmpresa()+"', '"+su.getDireccion()+"', "+su.getNumEmpleados()+");");
			comando.executeUpdate("INSERT INTO "+TABLAS.SECCION+" VALUES ('"+su.getCodigoSuper()+"','"+se.getCodigoSeccion()+"','"+se.getNombreSeccion()+"','"+se.getNumArticulo()+"')");
			ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SECCION);
			while(carga.next()) {
				sec=new Seccion(carga.getString(TABLAS.CODIGOSECCION),tipoArticulo.valueOf(carga.getString(TABLAS.TIPO)),carga.getInt(TABLAS.NUMAR),null);
				listaNormal.add(sec);
			}
			listaComprobar=gse.cargarSecciones(conexion);
			assertEquals(listaComprobar,listaNormal);
			comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
			comando.close();
			conexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	@Test
	void testCambiar() {
		Statement comando=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES "
		+ "('"+jefe.getDni()+"', '"+su.getCodigoSuper()+"', '"+su.getEmpresa()+"', '"+su.getDireccion()+"', "+su.getNumEmpleados()+");");
		comando.executeUpdate("INSERT INTO "+TABLAS.SECCION+" VALUES ('"+su.getCodigoSuper()+"','"+se.getCodigoSeccion()+"','"+se.getNombreSeccion()+"','"+se.getNumArticulo()+"')");
		Seccion sec=se;
		Seccion comprobar=null;
		sec.setNombreSeccion(tipoArticulo.Herramienta);
		sec.setNumArticulo(100);
		gse.cambiarSeccion(conexion, sec);
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SECCION+" WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
		while(carga.next()) {
			comprobar=new Seccion(carga.getString(TABLAS.CODIGOSECCION),tipoArticulo.valueOf(carga.getString(TABLAS.TIPO)),carga.getInt(TABLAS.NUMAR),null);
		}
		assertEquals(sec.getCodigoSeccion(),comprobar.getCodigoSeccion());
		assertEquals(sec.getNombreSeccion(),comprobar.getNombreSeccion());
		assertEquals(sec.getNumArticulo(),comprobar.getNumArticulo());
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testBorrar() {
		Statement comando=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES "
		+ "('"+jefe.getDni()+"', '"+su.getCodigoSuper()+"', '"+su.getEmpresa()+"', '"+su.getDireccion()+"', "+su.getNumEmpleados()+");");
		comando.executeUpdate("INSERT INTO "+TABLAS.SECCION+" VALUES ('"+su.getCodigoSuper()+"','"+se.getCodigoSeccion()+"','"+se.getNombreSeccion()+"','"+se.getNumArticulo()+"')");
		gse.borrarSeccion(conexion, se);
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SECCION+" WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
		boolean existe=true;
		while(carga.next()) {
			existe=false;
		}
		assertEquals(existe,true);
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
