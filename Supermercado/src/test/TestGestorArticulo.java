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
import gestores.GestorArticulo;
import modelo.Articulo;
import modelo.Comida;
import modelo.Herramienta;
import modelo.Jefe;
import modelo.Ropa;
import modelo.Seccion;
import modelo.Supermercado;
import otros.tipoArticulo;
import otros.tipoPersona;
import referencias.CONEXION;
import referencias.TABLAS;

class TestGestorArticulo {
	Metodos mc=new Metodos();
	Connection conexion;
	GestorArticulo ga=new GestorArticulo();
	Jefe jefe=new Jefe("77777777C","Test","Test",Date.valueOf("2001-01-21"),"testJefe@gmail.com","12345",tipoPersona.Jefe,Date.valueOf("2019-09-09"),(float)55.5,0);
	Supermercado su=new Supermercado("ABCDE","PruebaEmpresa","Errekamari",4,null);
	Seccion se=new Seccion(su.getCodigoSuper()+"1",tipoArticulo.Herramienta,0,null);
	Seccion sec=new Seccion(su.getCodigoSuper()+"2",tipoArticulo.Comida,0,null);
	Seccion secc=new Seccion(su.getCodigoSuper()+"3",tipoArticulo.Ropa,0,null);
	Ropa ro=new Ropa(0, "Chanclas", "chanclas.png", "", (float)5.88, 99, "XL", "Supreme");
	Comida co=new Comida(0, "Fruta del dragon", "fdd.png", "", (float)3.49, 99, Date.valueOf("2023-12-31"),"Brasil");
	Herramienta he=new Herramienta(0, "Desatornillador", "desatornillador.png", "", (float)8.99, 99, 1, 4);
	@Test
	void testGetterSetter() {
		ArrayList<Articulo> lista=null;
		ga.setListaArticulos(lista);
		assertEquals(lista,ga.getListaArticulos());
	}
	@Test
	void testInsertar() {
		Statement comando=null;
			try {
			conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
			comando= (Statement) conexion.createStatement();
			comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
			+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
			comando.executeUpdate("INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES "
			+ "('"+jefe.getDni()+"', '"+su.getCodigoSuper()+"', '"+su.getEmpresa()+"', '"+su.getDireccion()+"', "+su.getNumEmpleados()+");");
			comando.executeUpdate("INSERT INTO "+TABLAS.SECCION+" VALUES ('"+su.getCodigoSuper()+"','"+se.getCodigoSeccion()+"','"+se.getNombreSeccion()+"','"+se.getNumArticulo()+"')");
			ga.insertarArticulo(mc, conexion, se, co);
			ga.insertarArticulo(mc, conexion, se, ro);
			ga.insertarArticulo(mc, conexion, se, he);
			Comida coPrueba=null;
			Ropa roPrueba=null;
			Herramienta hePrueba=null;
			ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.NOMBREARTICULO+"='"+co.getNombreArticulo()+"' AND "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
			while(carga.next()) {
				coPrueba=new Comida(carga.getInt(TABLAS.IDARTICULO),carga.getString(TABLAS.NOMBREARTICULO),
				carga.getString(TABLAS.RUTAIMAGEN),carga.getString(TABLAS.DESCRIPCION),
				carga.getFloat(TABLAS.PRECIO),carga.getInt(TABLAS.STOCK),
				carga.getDate(TABLAS.FECHACADUCIDAD),carga.getString(TABLAS.PROCEDENCIA));
			}
			assertEquals(co.getNombreArticulo(),coPrueba.getNombreArticulo());
			assertEquals(co.getDescripcion(),coPrueba.getDescripcion());
			assertEquals(co.getFechaCaducidad(),coPrueba.getFechaCaducidad());
			assertEquals(co.getPrecio(),coPrueba.getPrecio());
			assertEquals(co.getRutaImagen(),coPrueba.getRutaImagen());
			assertEquals(co.getProcedencia(),coPrueba.getProcedencia());
			assertEquals(co.getStockActual(),coPrueba.getStockActual());
			carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.NOMBREARTICULO+"='"+he.getNombreArticulo()+"' AND "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
			while(carga.next()) {
				hePrueba=new Herramienta(carga.getInt(TABLAS.IDARTICULO),carga.getString(TABLAS.NOMBREARTICULO),
						carga.getString(TABLAS.RUTAIMAGEN),carga.getString(TABLAS.DESCRIPCION),
						carga.getFloat(TABLAS.PRECIO),carga.getInt(TABLAS.STOCK),
						carga.getInt(TABLAS.ELECTRICA),carga.getInt(TABLAS.GARANTIA));
			}
			assertEquals(he.getNombreArticulo(),hePrueba.getNombreArticulo());
			assertEquals(he.getDescripcion(),hePrueba.getDescripcion());
			assertEquals(he.getPrecio(),hePrueba.getPrecio());
			assertEquals(he.getRutaImagen(),hePrueba.getRutaImagen());
			assertEquals(he.getStockActual(),hePrueba.getStockActual());
			assertEquals(he.getGarantia(),hePrueba.getGarantia());
			assertEquals(he.getElectrica(),hePrueba.getElectrica());
			carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.NOMBREARTICULO+"='"+ro.getNombreArticulo()+"' AND "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
			while(carga.next()) {
				roPrueba=new Ropa(carga.getInt(TABLAS.IDARTICULO),carga.getString(TABLAS.NOMBREARTICULO),
						carga.getString(TABLAS.RUTAIMAGEN),carga.getString(TABLAS.DESCRIPCION),
						carga.getFloat(TABLAS.PRECIO),carga.getInt(TABLAS.STOCK),
						carga.getString(TABLAS.TALLA),carga.getString(TABLAS.MARCA));
				}
			assertEquals(ro.getNombreArticulo(),roPrueba.getNombreArticulo());
			assertEquals(ro.getDescripcion(),roPrueba.getDescripcion());
			assertEquals(ro.getPrecio(),roPrueba.getPrecio());
			assertEquals(ro.getRutaImagen(),roPrueba.getRutaImagen());
			assertEquals(ro.getStockActual(),roPrueba.getStockActual());
			assertEquals(ro.getTalla(),roPrueba.getTalla());
			assertEquals(ro.getMarca(),roPrueba.getMarca());
			comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
			comando.close();
			conexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	@Test
	void testCargar() {
		Statement comando=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES "
		+ "('"+jefe.getDni()+"', '"+su.getCodigoSuper()+"', '"+su.getEmpresa()+"', '"+su.getDireccion()+"', "+su.getNumEmpleados()+");");
		comando.executeUpdate("INSERT INTO "+TABLAS.SECCION+" VALUES ('"+su.getCodigoSuper()+"','"+se.getCodigoSeccion()+"','"+se.getNombreSeccion()+"','"+se.getNumArticulo()+"')");
		comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULO+" (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`, `fechaCaducidad`, `procedencia`) VALUES "
		+ "(0, '"+se.getCodigoSeccion()+"', '"+co.getNombreArticulo()+"', '"+co.getRutaImagen()+"', '"+co.getDescripcion()+"', "+co.getPrecio()+", "+co.getStockActual()+", '"+co.getFechaCaducidad()+"', '"+co.getProcedencia()+"')");
		comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULO+" (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`,  `electrica`, `garantia`) VALUES "
		+ "(0, '"+se.getCodigoSeccion()+"', '"+he.getNombreArticulo()+"', '"+he.getRutaImagen()+"', '"+he.getDescripcion()+"', "+he.getPrecio()+", "+he.getStockActual()+", "+he.getElectrica()+", "+he.getGarantia()+")");
		comando.executeUpdate("INSERT INTO `articulos` (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`, `talla`, `marca`) VALUES "
		+ "(0, '"+se.getCodigoSeccion()+"', '"+ro.getNombreArticulo()+"', '"+ro.getRutaImagen()+"', '"+ro.getDescripcion()+"', "+ro.getPrecio()+", "+ro.getStockActual()+", '"+ro.getTalla()+"', '"+ro.getMarca()+"')");
		ArrayList<Articulo> lista=new ArrayList<Articulo>();
		ArrayList<Articulo> listaComprobar=new ArrayList<Articulo>();
		Herramienta hePrueba=null;
		Comida coPrueba=null;
		Ropa roPrueba=null;
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.ELECTRICA+" OR "+TABLAS.GARANTIA+" IS NOT NULL");
		while(carga.next()) {
		hePrueba=new Herramienta(carga.getInt(TABLAS.IDARTICULO),carga.getString(TABLAS.NOMBREARTICULO),
				carga.getString(TABLAS.RUTAIMAGEN),carga.getString(TABLAS.DESCRIPCION),
				carga.getFloat(TABLAS.PRECIO),carga.getInt(TABLAS.STOCK),
				carga.getInt(TABLAS.ELECTRICA),carga.getInt(TABLAS.GARANTIA));
		lista.add(hePrueba);
		}
		ResultSet cargaC=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.FECHACADUCIDAD+" OR "+TABLAS.PROCEDENCIA+" IS NOT NULL");
		while(cargaC.next()) {
		coPrueba=new Comida(cargaC.getInt(TABLAS.IDARTICULO),cargaC.getString(TABLAS.NOMBREARTICULO),
				cargaC.getString(TABLAS.RUTAIMAGEN),cargaC.getString(TABLAS.DESCRIPCION),
				cargaC.getFloat(TABLAS.PRECIO),cargaC.getInt(TABLAS.STOCK),
				cargaC.getDate(TABLAS.FECHACADUCIDAD),cargaC.getString(TABLAS.PROCEDENCIA));
		lista.add(coPrueba);
		}
		ResultSet cargaR=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.TALLA+" OR "+TABLAS.MARCA+" IS NOT NULL");
		while(cargaR.next()) {
		roPrueba=new Ropa(cargaR.getInt(TABLAS.IDARTICULO),cargaR.getString(TABLAS.NOMBREARTICULO),
				cargaR.getString(TABLAS.RUTAIMAGEN),cargaR.getString(TABLAS.DESCRIPCION),
				cargaR.getFloat(TABLAS.PRECIO),cargaR.getInt(TABLAS.STOCK),
				cargaR.getString(TABLAS.TALLA),cargaR.getString(TABLAS.MARCA));
		lista.add(roPrueba);
		}
		listaComprobar=ga.cargarArticulos(conexion);
		assertEquals(listaComprobar,lista);
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
			comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULO+" (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`, `fechaCaducidad`, `procedencia`) VALUES "
			+ "(0, '"+se.getCodigoSeccion()+"', '"+co.getNombreArticulo()+"', '"+co.getRutaImagen()+"', '"+co.getDescripcion()+"', "+co.getPrecio()+", "+co.getStockActual()+", '"+co.getFechaCaducidad()+"', '"+co.getProcedencia()+"')");
			comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULO+" (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`,  `electrica`, `garantia`) VALUES "
			+ "(0, '"+se.getCodigoSeccion()+"', '"+he.getNombreArticulo()+"', '"+he.getRutaImagen()+"', '"+he.getDescripcion()+"', "+he.getPrecio()+", "+he.getStockActual()+", "+he.getElectrica()+", "+he.getGarantia()+")");
			comando.executeUpdate("INSERT INTO `articulos` (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`, `talla`, `marca`) VALUES "
			+ "(0, '"+se.getCodigoSeccion()+"', '"+ro.getNombreArticulo()+"', '"+ro.getRutaImagen()+"', '"+ro.getDescripcion()+"', "+ro.getPrecio()+", "+ro.getStockActual()+", '"+ro.getTalla()+"', '"+ro.getMarca()+"')");
			int primerID=-1;
			int segundoID=-1;
			int tercerID=-1;
			ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.NOMBREARTICULO+"='"+co.getNombreArticulo()+"' AND "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
			while(carga.next()) {
				primerID=carga.getInt(TABLAS.IDARTICULO);
			}
			carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.NOMBREARTICULO+"='"+he.getNombreArticulo()+"' AND "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
			while(carga.next()) {
				segundoID=carga.getInt(TABLAS.IDARTICULO);
			}
			carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.NOMBREARTICULO+"='"+ro.getNombreArticulo()+"' AND "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
			while(carga.next()) {
				tercerID=carga.getInt(TABLAS.IDARTICULO);
			}
			Comida coPrueba=co;
			coPrueba.setIdArticulo(primerID);
			coPrueba.setNombreArticulo("Diferente");
			coPrueba.setDescripcion("Cambiada");
			coPrueba.setPrecio((float)10);
			coPrueba.setProcedencia("Madrid");
			coPrueba.setFechaCaducidad(new java.util.Date());
			Herramienta hePrueba=he;
			hePrueba.setIdArticulo(segundoID);
			hePrueba.setNombreArticulo("Otro");
			hePrueba.setElectrica(false);
			hePrueba.setGarantia(2);
			Ropa roPrueba=ro;
			roPrueba.setIdArticulo(tercerID);
			roPrueba.setNombreArticulo("Cambio");
			roPrueba.setMarca("Shein");
			roPrueba.setTalla("XS");
			roPrueba.setRutaImagen("cambiada.png");
			ga.cambiarArticulo(mc, conexion, hePrueba);
			ga.cambiarArticulo(mc, conexion, roPrueba);
			ga.cambiarArticulo(mc, conexion, coPrueba);
			Comida coComprobar=null;
			Ropa roComprobar=null;
			Herramienta heComprobar=null;
			carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.NOMBREARTICULO+"='"+coPrueba.getNombreArticulo()+"' AND "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
			while(carga.next()) {
				coComprobar=new Comida(carga.getInt(TABLAS.IDARTICULO),carga.getString(TABLAS.NOMBREARTICULO),
				carga.getString(TABLAS.RUTAIMAGEN),carga.getString(TABLAS.DESCRIPCION),
				carga.getFloat(TABLAS.PRECIO),carga.getInt(TABLAS.STOCK),
				carga.getDate(TABLAS.FECHACADUCIDAD),carga.getString(TABLAS.PROCEDENCIA));
			}
			assertEquals(coComprobar.getNombreArticulo(),coPrueba.getNombreArticulo());
			assertEquals(coComprobar.getDescripcion(),coPrueba.getDescripcion());
			assertEquals(coComprobar.getFechaCaducidad(),coPrueba.getFechaCaducidad());
			assertEquals(coComprobar.getPrecio(),coPrueba.getPrecio());
			assertEquals(coComprobar.getRutaImagen(),coPrueba.getRutaImagen());
			assertEquals(coComprobar.getProcedencia(),coPrueba.getProcedencia());
			assertEquals(coComprobar.getStockActual(),coPrueba.getStockActual());
			carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.NOMBREARTICULO+"='"+hePrueba.getNombreArticulo()+"' AND "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
			while(carga.next()) {
				heComprobar=new Herramienta(carga.getInt(TABLAS.IDARTICULO),carga.getString(TABLAS.NOMBREARTICULO),
						carga.getString(TABLAS.RUTAIMAGEN),carga.getString(TABLAS.DESCRIPCION),
						carga.getFloat(TABLAS.PRECIO),carga.getInt(TABLAS.STOCK),
						carga.getInt(TABLAS.ELECTRICA),carga.getInt(TABLAS.GARANTIA));
			}
			assertEquals(heComprobar.getNombreArticulo(),hePrueba.getNombreArticulo());
			assertEquals(heComprobar.getDescripcion(),hePrueba.getDescripcion());
			assertEquals(heComprobar.getPrecio(),hePrueba.getPrecio());
			assertEquals(heComprobar.getRutaImagen(),hePrueba.getRutaImagen());
			assertEquals(heComprobar.getStockActual(),hePrueba.getStockActual());
			assertEquals(heComprobar.getGarantia(),hePrueba.getGarantia());
			assertEquals(heComprobar.getElectrica(),hePrueba.getElectrica());
			carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.NOMBREARTICULO+"='"+roPrueba.getNombreArticulo()+"' AND "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
			while(carga.next()) {
				roComprobar=new Ropa(carga.getInt(TABLAS.IDARTICULO),carga.getString(TABLAS.NOMBREARTICULO),
						carga.getString(TABLAS.RUTAIMAGEN),carga.getString(TABLAS.DESCRIPCION),
						carga.getFloat(TABLAS.PRECIO),carga.getInt(TABLAS.STOCK),
						carga.getString(TABLAS.TALLA),carga.getString(TABLAS.MARCA));
				}
			assertEquals(roComprobar.getNombreArticulo(),roPrueba.getNombreArticulo());
			assertEquals(roComprobar.getDescripcion(),roPrueba.getDescripcion());
			assertEquals(roComprobar.getPrecio(),roPrueba.getPrecio());
			assertEquals(roComprobar.getRutaImagen(),roPrueba.getRutaImagen());
			assertEquals(roComprobar.getStockActual(),roPrueba.getStockActual());
			assertEquals(roComprobar.getTalla(),roPrueba.getTalla());
			assertEquals(roComprobar.getMarca(),roPrueba.getMarca());
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
			comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULO+" (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`, `fechaCaducidad`, `procedencia`) VALUES "
			+ "(0, '"+se.getCodigoSeccion()+"', '"+co.getNombreArticulo()+"', '"+co.getRutaImagen()+"', '"+co.getDescripcion()+"', "+co.getPrecio()+", "+co.getStockActual()+", '"+co.getFechaCaducidad()+"', '"+co.getProcedencia()+"')");
			int primerID=-1;
			ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.NOMBREARTICULO+"='"+co.getNombreArticulo()+"' AND "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
			while(carga.next()) {
				primerID=carga.getInt(TABLAS.IDARTICULO);
			}
			Comida coComprobar=co;
			coComprobar.setIdArticulo(primerID);
			ga.borrarArticulo(conexion, co);
			carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.NOMBREARTICULO+"='"+co.getNombreArticulo()+"' AND "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
			Comida coPrueba=null;
			while(carga.next()) {
				coPrueba=new Comida(carga.getInt(TABLAS.IDARTICULO),carga.getString(TABLAS.NOMBREARTICULO),
						carga.getString(TABLAS.RUTAIMAGEN),carga.getString(TABLAS.DESCRIPCION),
						carga.getFloat(TABLAS.PRECIO),carga.getInt(TABLAS.STOCK),
						carga.getDate(TABLAS.FECHACADUCIDAD),carga.getString(TABLAS.PROCEDENCIA));
			}
			assertNotEquals(coPrueba,co);
			assertEquals(coPrueba,null);
			comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
			comando.close();
			conexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	@Test
	void cargarArticulosPorSeccion() {
		Statement comando=null;
		ArrayList<Articulo> lista=new ArrayList<Articulo>();
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES "
		+ "('"+jefe.getDni()+"', '"+su.getCodigoSuper()+"', '"+su.getEmpresa()+"', '"+su.getDireccion()+"', "+su.getNumEmpleados()+");");
		
		comando.executeUpdate("INSERT INTO "+TABLAS.SECCION+" VALUES ('"+su.getCodigoSuper()+"','"+sec.getCodigoSeccion()+"','"+sec.getNombreSeccion()+"','"+sec.getNumArticulo()+"')");
		comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULO+" (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`, `fechaCaducidad`, `procedencia`) VALUES "
		+ "(0, '"+sec.getCodigoSeccion()+"', '"+co.getNombreArticulo()+"', '"+co.getRutaImagen()+"', '"+co.getDescripcion()+"', "+co.getPrecio()+", "+co.getStockActual()+", '"+co.getFechaCaducidad()+"', '"+co.getProcedencia()+"')");
		
		comando.executeUpdate("INSERT INTO "+TABLAS.SECCION+" VALUES ('"+su.getCodigoSuper()+"','"+se.getCodigoSeccion()+"','"+se.getNombreSeccion()+"','"+se.getNumArticulo()+"')");
		comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULO+" (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`,  `electrica`, `garantia`) VALUES "
		+ "(0, '"+se.getCodigoSeccion()+"', '"+he.getNombreArticulo()+"', '"+he.getRutaImagen()+"', '"+he.getDescripcion()+"', "+he.getPrecio()+", "+he.getStockActual()+", "+he.getElectrica()+", "+he.getGarantia()+")");
		
		comando.executeUpdate("INSERT INTO "+TABLAS.SECCION+" VALUES ('"+su.getCodigoSuper()+"','"+secc.getCodigoSeccion()+"','"+secc.getNombreSeccion()+"','"+secc.getNumArticulo()+"')");
		comando.executeUpdate("INSERT INTO `articulos` (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`, `talla`, `marca`) VALUES "
		+ "(0, '"+secc.getCodigoSeccion()+"', '"+ro.getNombreArticulo()+"', '"+ro.getRutaImagen()+"', '"+ro.getDescripcion()+"', "+ro.getPrecio()+", "+ro.getStockActual()+", '"+ro.getTalla()+"', '"+ro.getMarca()+"')");
		
		lista=ga.cargarArticulosPorSeccion(conexion, se);
		Herramienta heComprobar=(Herramienta)lista.get(0);
		
		assertEquals(heComprobar.getNombreArticulo(),he.getNombreArticulo());
		assertEquals(heComprobar.getDescripcion(),he.getDescripcion());
		assertEquals(heComprobar.getPrecio(),he.getPrecio());
		assertEquals(heComprobar.getRutaImagen(),he.getRutaImagen());
		assertEquals(heComprobar.getStockActual(),he.getStockActual());
		assertEquals(heComprobar.getGarantia(),he.getGarantia());
		
		lista=ga.cargarArticulosPorSeccion(conexion, sec);
		Comida coComprobar=(Comida)lista.get(0);
		
		assertEquals(coComprobar.getNombreArticulo(),co.getNombreArticulo());
		assertEquals(coComprobar.getDescripcion(),co.getDescripcion());
		assertEquals(coComprobar.getFechaCaducidad(),co.getFechaCaducidad());
		assertEquals(coComprobar.getPrecio(),co.getPrecio());
		assertEquals(coComprobar.getRutaImagen(),co.getRutaImagen());
		assertEquals(coComprobar.getProcedencia(),co.getProcedencia());
		assertEquals(coComprobar.getStockActual(),co.getStockActual());
		
		lista=ga.cargarArticulosPorSeccion(conexion, secc);
		Ropa roComprobar=(Ropa)lista.get(0);
		
		assertEquals(heComprobar.getElectrica(),he.getElectrica());
		assertEquals(roComprobar.getNombreArticulo(),ro.getNombreArticulo());
		assertEquals(roComprobar.getDescripcion(),ro.getDescripcion());
		assertEquals(roComprobar.getPrecio(),ro.getPrecio());
		assertEquals(roComprobar.getRutaImagen(),ro.getRutaImagen());
		assertEquals(roComprobar.getStockActual(),ro.getStockActual());
		assertEquals(roComprobar.getTalla(),ro.getTalla());
		assertEquals(roComprobar.getMarca(),ro.getMarca());
		
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testBuscarArticulosPorNombre() {
		Statement comando=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES "
		+ "('"+jefe.getDni()+"', '"+su.getCodigoSuper()+"', '"+su.getEmpresa()+"', '"+su.getDireccion()+"', "+su.getNumEmpleados()+");");
		comando.executeUpdate("INSERT INTO "+TABLAS.SECCION+" VALUES ('"+su.getCodigoSuper()+"','"+se.getCodigoSeccion()+"','"+se.getNombreSeccion()+"','"+se.getNumArticulo()+"')");
		comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULO+" (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`, `fechaCaducidad`, `procedencia`) VALUES "
		+ "(0, '"+se.getCodigoSeccion()+"', '"+co.getNombreArticulo()+"', '"+co.getRutaImagen()+"', '"+co.getDescripcion()+"', "+co.getPrecio()+", "+co.getStockActual()+", '"+co.getFechaCaducidad()+"', '"+co.getProcedencia()+"')");
		comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULO+" (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`,  `electrica`, `garantia`) VALUES "
		+ "(0, '"+se.getCodigoSeccion()+"', '"+he.getNombreArticulo()+"', '"+he.getRutaImagen()+"', '"+he.getDescripcion()+"', "+he.getPrecio()+", "+he.getStockActual()+", "+he.getElectrica()+", "+he.getGarantia()+")");
		comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULO+" (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`, `talla`, `marca`) VALUES "
		+ "(0, '"+se.getCodigoSeccion()+"', '"+ro.getNombreArticulo()+"', '"+ro.getRutaImagen()+"', '"+ro.getDescripcion()+"', "+ro.getPrecio()+", "+ro.getStockActual()+", '"+ro.getTalla()+"', '"+ro.getMarca()+"')");
		ArrayList<Articulo> lista=new ArrayList<Articulo>();
		Herramienta hePrueba=null;
		Comida coPrueba=null;
		Ropa roPrueba=null;
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.ELECTRICA+" OR "+TABLAS.GARANTIA+" IS NOT NULL");
		while(carga.next()) {
		hePrueba=new Herramienta(carga.getInt(TABLAS.IDARTICULO),carga.getString(TABLAS.NOMBREARTICULO),
				carga.getString(TABLAS.RUTAIMAGEN),carga.getString(TABLAS.DESCRIPCION),
				carga.getFloat(TABLAS.PRECIO),carga.getInt(TABLAS.STOCK),
				carga.getInt(TABLAS.ELECTRICA),carga.getInt(TABLAS.GARANTIA));
		lista.add(hePrueba);
		}
		ResultSet cargaC=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.FECHACADUCIDAD+" OR "+TABLAS.PROCEDENCIA+" IS NOT NULL");
		while(cargaC.next()) {
		coPrueba=new Comida(cargaC.getInt(TABLAS.IDARTICULO),cargaC.getString(TABLAS.NOMBREARTICULO),
				cargaC.getString(TABLAS.RUTAIMAGEN),cargaC.getString(TABLAS.DESCRIPCION),
				cargaC.getFloat(TABLAS.PRECIO),cargaC.getInt(TABLAS.STOCK),
				cargaC.getDate(TABLAS.FECHACADUCIDAD),cargaC.getString(TABLAS.PROCEDENCIA));
		lista.add(coPrueba);
		}
		ResultSet cargaR=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.TALLA+" OR "+TABLAS.MARCA+" IS NOT NULL");
		while(cargaR.next()) {
		roPrueba=new Ropa(cargaR.getInt(TABLAS.IDARTICULO),cargaR.getString(TABLAS.NOMBREARTICULO),
				cargaR.getString(TABLAS.RUTAIMAGEN),cargaR.getString(TABLAS.DESCRIPCION),
				cargaR.getFloat(TABLAS.PRECIO),cargaR.getInt(TABLAS.STOCK),
				cargaR.getString(TABLAS.TALLA),cargaR.getString(TABLAS.MARCA));
		lista.add(roPrueba);
		}
		ArrayList<Articulo> listaPrueba=ga.buscarArticulosPorNombre(co.getNombreArticulo(), lista);
		listaPrueba.get(0).setIdArticulo(co.getIdArticulo());
		assertEquals(listaPrueba.get(0),co);
		assertEquals(listaPrueba.get(0).getNombreArticulo(),co.getNombreArticulo());
		assertEquals(listaPrueba.get(0).getPrecio(),co.getPrecio());
		assertEquals(listaPrueba.get(0).getRutaImagen(),co.getRutaImagen());
		assertEquals(listaPrueba.get(0).getStockActual(),co.getStockActual());
		assertEquals(listaPrueba.get(0).getDescripcion(),co.getDescripcion());
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testDiferenciarCarrito() {
		Statement comando=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES "
		+ "('"+jefe.getDni()+"', '"+su.getCodigoSuper()+"', '"+su.getEmpresa()+"', '"+su.getDireccion()+"', "+su.getNumEmpleados()+");");
		comando.executeUpdate("INSERT INTO "+TABLAS.SECCION+" VALUES ('"+su.getCodigoSuper()+"','"+se.getCodigoSeccion()+"','"+se.getNombreSeccion()+"','"+se.getNumArticulo()+"')");
		comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULO+" (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`, `fechaCaducidad`, `procedencia`) VALUES "
		+ "(0, '"+se.getCodigoSeccion()+"', '"+co.getNombreArticulo()+"', '"+co.getRutaImagen()+"', '"+co.getDescripcion()+"', "+co.getPrecio()+", "+co.getStockActual()+", '"+co.getFechaCaducidad()+"', '"+co.getProcedencia()+"')");
		comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULO+" (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`,  `electrica`, `garantia`) VALUES "
		+ "(0, '"+se.getCodigoSeccion()+"', '"+he.getNombreArticulo()+"', '"+he.getRutaImagen()+"', '"+he.getDescripcion()+"', "+he.getPrecio()+", "+he.getStockActual()+", "+he.getElectrica()+", "+he.getGarantia()+")");
		comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULO+" (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`, `talla`, `marca`) VALUES "
		+ "(0, '"+se.getCodigoSeccion()+"', '"+ro.getNombreArticulo()+"', '"+ro.getRutaImagen()+"', '"+ro.getDescripcion()+"', "+ro.getPrecio()+", "+ro.getStockActual()+", '"+ro.getTalla()+"', '"+ro.getMarca()+"')");
		ArrayList<Articulo> lista=new ArrayList<Articulo>();
		Herramienta hePrueba=null;
		Comida coPrueba=null;
		Ropa roPrueba=null;
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.ELECTRICA+" OR "+TABLAS.GARANTIA+" IS NOT NULL");
		while(carga.next()) {
		hePrueba=new Herramienta(carga.getInt(TABLAS.IDARTICULO),carga.getString(TABLAS.NOMBREARTICULO),
				carga.getString(TABLAS.RUTAIMAGEN),carga.getString(TABLAS.DESCRIPCION),
				carga.getFloat(TABLAS.PRECIO),carga.getInt(TABLAS.STOCK),
				carga.getInt(TABLAS.ELECTRICA),carga.getInt(TABLAS.GARANTIA));
		lista.add(hePrueba);
		}
		ResultSet cargaC=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.FECHACADUCIDAD+" OR "+TABLAS.PROCEDENCIA+" IS NOT NULL");
		while(cargaC.next()) {
		coPrueba=new Comida(cargaC.getInt(TABLAS.IDARTICULO),cargaC.getString(TABLAS.NOMBREARTICULO),
				cargaC.getString(TABLAS.RUTAIMAGEN),cargaC.getString(TABLAS.DESCRIPCION),
				cargaC.getFloat(TABLAS.PRECIO),cargaC.getInt(TABLAS.STOCK),
				cargaC.getDate(TABLAS.FECHACADUCIDAD),cargaC.getString(TABLAS.PROCEDENCIA));
		lista.add(coPrueba);
		}
		ResultSet cargaR=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.TALLA+" OR "+TABLAS.MARCA+" IS NOT NULL");
		while(cargaR.next()) {
		roPrueba=new Ropa(cargaR.getInt(TABLAS.IDARTICULO),cargaR.getString(TABLAS.NOMBREARTICULO),
				cargaR.getString(TABLAS.RUTAIMAGEN),cargaR.getString(TABLAS.DESCRIPCION),
				cargaR.getFloat(TABLAS.PRECIO),cargaR.getInt(TABLAS.STOCK),
				cargaR.getString(TABLAS.TALLA),cargaR.getString(TABLAS.MARCA));
		lista.add(roPrueba);
		}
		Ropa nueva=new Ropa(0,"Inventada","Inventada","Inventada",(float)0,100,"Inventada","Inventada");
		int antesDelaNueva=lista.size();
		ga.diferenciarCarrito(nueva, lista);
		assertEquals(antesDelaNueva+1,lista.size());
		
		int despuesDelaNueva=lista.size();
		ga.diferenciarCarrito(nueva, lista);
		assertEquals(lista.size(), despuesDelaNueva);
		
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	void testBuscarArticulo() {
		Statement comando=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES "
		+ "('"+jefe.getDni()+"', '"+jefe.getNombre()+"', '"+jefe.getApellidos()+"', '"+jefe.getFechaNacimiento()+"', '"+jefe.getEmail()+"', '"+jefe.getContrasena()+"', '"+jefe.getTipo()+"', '"+jefe.getFechaAdquisicion()+"', "+jefe.getPorcentajeEmpresa()+","+jefe.isDios()+")");
		comando.executeUpdate("INSERT INTO `supermercados` (`dniJefe`, `codigoSuper`, `empresa`, `direccion`, `numEmpleados`) VALUES "
		+ "('"+jefe.getDni()+"', '"+su.getCodigoSuper()+"', '"+su.getEmpresa()+"', '"+su.getDireccion()+"', "+su.getNumEmpleados()+");");
		comando.executeUpdate("INSERT INTO "+TABLAS.SECCION+" VALUES ('"+su.getCodigoSuper()+"','"+se.getCodigoSeccion()+"','"+se.getNombreSeccion()+"','"+se.getNumArticulo()+"')");
		comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULO+" (`idArticulo`, `codigoSeccion`, `nombreArticulo`, `rutaImagen`, `descripcion`, `precio`, `stock`, `fechaCaducidad`, `procedencia`) VALUES "
		+ "(0, '"+se.getCodigoSeccion()+"', '"+co.getNombreArticulo()+"', '"+co.getRutaImagen()+"', '"+co.getDescripcion()+"', "+co.getPrecio()+", "+co.getStockActual()+", '"+co.getFechaCaducidad()+"', '"+co.getProcedencia()+"')");
		ArrayList<Articulo> lista=new ArrayList<Articulo>();
		Herramienta hePrueba=null;
		Comida coPrueba=null;
		Ropa roPrueba=null;
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.ELECTRICA+" OR "+TABLAS.GARANTIA+" IS NOT NULL");
		while(carga.next()) {
		hePrueba=new Herramienta(carga.getInt(TABLAS.IDARTICULO),carga.getString(TABLAS.NOMBREARTICULO),
				carga.getString(TABLAS.RUTAIMAGEN),carga.getString(TABLAS.DESCRIPCION),
				carga.getFloat(TABLAS.PRECIO),carga.getInt(TABLAS.STOCK),
				carga.getInt(TABLAS.ELECTRICA),carga.getInt(TABLAS.GARANTIA));
		lista.add(hePrueba);
		}
		ResultSet cargaC=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.FECHACADUCIDAD+" OR "+TABLAS.PROCEDENCIA+" IS NOT NULL");
		while(cargaC.next()) {
		coPrueba=new Comida(cargaC.getInt(TABLAS.IDARTICULO),cargaC.getString(TABLAS.NOMBREARTICULO),
				cargaC.getString(TABLAS.RUTAIMAGEN),cargaC.getString(TABLAS.DESCRIPCION),
				cargaC.getFloat(TABLAS.PRECIO),cargaC.getInt(TABLAS.STOCK),
				cargaC.getDate(TABLAS.FECHACADUCIDAD),cargaC.getString(TABLAS.PROCEDENCIA));
		lista.add(coPrueba);
		}
		ResultSet cargaR=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.TALLA+" OR "+TABLAS.MARCA+" IS NOT NULL");
		while(cargaR.next()) {
		roPrueba=new Ropa(cargaR.getInt(TABLAS.IDARTICULO),cargaR.getString(TABLAS.NOMBREARTICULO),
				cargaR.getString(TABLAS.RUTAIMAGEN),cargaR.getString(TABLAS.DESCRIPCION),
				cargaR.getFloat(TABLAS.PRECIO),cargaR.getInt(TABLAS.STOCK),
				cargaR.getString(TABLAS.TALLA),cargaR.getString(TABLAS.MARCA));
		lista.add(roPrueba);
		}
		Ropa roComprobar=(Ropa) ga.buscarArticulo(roPrueba, lista);
		assertEquals(roComprobar.getNombreArticulo(),roPrueba.getNombreArticulo());
		assertEquals(roComprobar.getDescripcion(),roPrueba.getDescripcion());
		assertEquals(roComprobar.getPrecio(),roPrueba.getPrecio());
		assertEquals(roComprobar.getRutaImagen(),roPrueba.getRutaImagen());
		assertEquals(roComprobar.getStockActual(),roPrueba.getStockActual());
		assertEquals(roComprobar.getTalla(),roPrueba.getTalla());
		assertEquals(roComprobar.getMarca(),roPrueba.getMarca());
		
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+jefe.getDni()+"'");
		comando.close();
		conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
