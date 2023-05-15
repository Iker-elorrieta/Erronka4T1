package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import gestores.GestorArticulo;
import gestores.GestorArticuloComprado;
import gestores.GestorPersona;
import modelo.ArticuloComprado;
import modelo.Comida;
import modelo.Compra;
import modelo.Jefe;
import modelo.Seccion;
import modelo.Supermercado;
import otros.tipoArticulo;
import otros.tipoPersona;
import referencias.CONEXION;
import referencias.TABLAS;

@SuppressWarnings("javadoc")
class TestGestorArticuloComprado {
	Metodos mc=new Metodos();
	GestorArticuloComprado gac=new GestorArticuloComprado();
	Connection conexion;
	GestorPersona gp=new GestorPersona();
	Jefe jefe=new Jefe("77777777C","Test","Test",Date.valueOf("2001-01-21"),"testJefe@gmail.com","12345",tipoPersona.Jefe,Date.valueOf("2019-09-09"),(float)55.5,0);
	Supermercado su=new Supermercado("ABCDE","PruebaEmpresa","Errekamari",4,null);
	Seccion se=new Seccion(su.getCodigoSuper()+"1",tipoArticulo.Herramienta,0,null);
	Comida co=new Comida(0, "Fruta del dragon", "fdd.png", "", (float)3.49, 70, Date.valueOf("2023-12-31"),"Brasil");
	ArticuloComprado arc=new ArticuloComprado(0,0,2,co.getPrecio());
	@Test
	void testGetterSetter() {
		GestorArticuloComprado gac=new GestorArticuloComprado();
		ArrayList<ArticuloComprado> lista= null;
		gac.setListaArticulosComprados(lista);
		assertEquals(gac.getListaArticulosComprados(), null);
	}
	@Test
	void testInsertarArticuloComprado() {
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
		int idar=-1;
		ResultSet cargar=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"' AND "+TABLAS.NOMBREARTICULO+"='"+co.getNombreArticulo()+"'");
		while(cargar.next()) {
			idar=cargar.getInt(TABLAS.IDARTICULO);
		}
		comando.executeUpdate("INSERT INTO `compras` (`dni`, `codigoCompra`, `precioFinal`, `fechaCompra`) VALUES "
		+ "('"+jefe.getDni()+"', 0, "+co.getPrecio()*2+", '"+LocalDateTime.now()+"')");
		Compra compra=null;
		cargar=comando.executeQuery("SELECT * FROM "+TABLAS.COMPRAS+" ORDER BY "+TABLAS.FECHACOMPRA+" DESC LIMIT 1");
		while(cargar.next()) {
			compra=new Compra(cargar.getInt(TABLAS.CODIGOCOMPRA),cargar.getFloat(TABLAS.PRECIOFINAL),mc.deStringALocalDateTime(cargar.getString(TABLAS.FECHACOMPRA)));
		}
		arc.setIdArticulo(idar);
		arc.setCodigoCompra(compra.getCodigoCompra());
		gac.insertarArticuloComprado(conexion, compra, arc);
		ArticuloComprado comprobar=null;
		cargar=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULOSCOMPRADOS+" WHERE "+TABLAS.CODIGOCOMPRA+"="+compra.getCodigoCompra()+" AND "+TABLAS.IDARTICULO+"="+arc.getIdArticulo()+"");
		while(cargar.next()) {
			comprobar=new ArticuloComprado(cargar.getInt(TABLAS.CODIGOCOMPRA),cargar.getInt(TABLAS.IDARTICULO),
					cargar.getInt(TABLAS.CANTIDAD),cargar.getFloat(TABLAS.PRECIOART));
		}
		assertEquals(arc.getIdArticulo(),comprobar.getIdArticulo());
		assertEquals(arc.getCantidad(),comprobar.getCantidad());
		assertEquals(arc.getCodigoCompra(),comprobar.getCodigoCompra());
		assertEquals(arc.getPrecioArt(),comprobar.getPrecioArt());
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
	void testCargarArticulosComprado() {
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
		int idar=-1;
		ResultSet cargar=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"' AND "+TABLAS.NOMBREARTICULO+"='"+co.getNombreArticulo()+"'");
		while(cargar.next()) {
			idar=cargar.getInt(TABLAS.IDARTICULO);
		}
		comando.executeUpdate("INSERT INTO `compras` (`dni`, `codigoCompra`, `precioFinal`, `fechaCompra`) VALUES "
		+ "('"+jefe.getDni()+"', 0, "+co.getPrecio()*2+", '"+LocalDateTime.now()+"')");
		Compra compra=null;
		cargar=comando.executeQuery("SELECT * FROM "+TABLAS.COMPRAS+" ORDER BY "+TABLAS.FECHACOMPRA+" DESC LIMIT 1");
		while(cargar.next()) {
			compra=new Compra(cargar.getInt(TABLAS.CODIGOCOMPRA),cargar.getFloat(TABLAS.PRECIOFINAL),mc.deStringALocalDateTime(cargar.getString(TABLAS.FECHACOMPRA)));
		}
		arc.setIdArticulo(idar);
		arc.setCodigoCompra(compra.getCodigoCompra());
		comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULOSCOMPRADOS+" VALUES ('"+arc.getCodigoCompra()+"',"
				+ "'"+arc.getIdArticulo()+"','"+arc.getCantidad()+"','"+arc.getPrecioArt()+"')");
		ArrayList<ArticuloComprado>lista=gac.cargarArticulosComprados(conexion);
		assertTrue(lista.size()>=1);
		ArticuloComprado comprobar=null;
		comprobar=lista.get(lista.size()-1);
		assertEquals(arc.getIdArticulo(),comprobar.getIdArticulo());
		assertEquals(arc.getCantidad(),comprobar.getCantidad());
		assertEquals(arc.getCodigoCompra(),comprobar.getCodigoCompra());
		assertEquals(arc.getPrecioArt(),comprobar.getPrecioArt());
		
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
	void testBorrarArticuloComprado() {
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
		int idar=-1;
		ResultSet cargar=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"' AND "+TABLAS.NOMBREARTICULO+"='"+co.getNombreArticulo()+"'");
		while(cargar.next()) {
			idar=cargar.getInt(TABLAS.IDARTICULO);
		}
		comando.executeUpdate("INSERT INTO `compras` (`dni`, `codigoCompra`, `precioFinal`, `fechaCompra`) VALUES "
		+ "('"+jefe.getDni()+"', 0, "+co.getPrecio()*2+", '"+LocalDateTime.now()+"')");
		Compra compra=null;
		cargar=comando.executeQuery("SELECT * FROM "+TABLAS.COMPRAS+" ORDER BY "+TABLAS.FECHACOMPRA+" DESC LIMIT 1");
		while(cargar.next()) {
			compra=new Compra(cargar.getInt(TABLAS.CODIGOCOMPRA),cargar.getFloat(TABLAS.PRECIOFINAL),mc.deStringALocalDateTime(cargar.getString(TABLAS.FECHACOMPRA)));
		}
		arc.setIdArticulo(idar);
		arc.setCodigoCompra(compra.getCodigoCompra());
		comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULOSCOMPRADOS+" VALUES ('"+arc.getCodigoCompra()+"',"
				+ "'"+arc.getIdArticulo()+"','"+arc.getCantidad()+"','"+arc.getPrecioArt()+"')");
		gac.borrarArticuloComprado(conexion, arc);
		ArticuloComprado arcComprobar=null;
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULOSCOMPRADOS+" WHERE "+TABLAS.CODIGOCOMPRA+"="+arc.getCodigoCompra()+" AND "+TABLAS.IDARTICULO+"="+arc.getIdArticulo()+"");
		while(carga.next()) {
		arcComprobar=new ArticuloComprado(carga.getInt(TABLAS.CODIGOCOMPRA),carga.getInt(TABLAS.IDARTICULO),
				carga.getInt(TABLAS.CANTIDAD),carga.getFloat(TABLAS.PRECIOART));
		}
		assertEquals(arcComprobar,null);
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
	void testCambiarArticuloComprado() {
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
		int idar=-1;
		ResultSet cargar=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"' AND "+TABLAS.NOMBREARTICULO+"='"+co.getNombreArticulo()+"'");
		while(cargar.next()) {
			idar=cargar.getInt(TABLAS.IDARTICULO);
		}
		comando.executeUpdate("INSERT INTO `compras` (`dni`, `codigoCompra`, `precioFinal`, `fechaCompra`) VALUES "
		+ "('"+jefe.getDni()+"', 0, "+co.getPrecio()*2+", '"+LocalDateTime.now()+"')");
		Compra compra=null;
		cargar=comando.executeQuery("SELECT * FROM "+TABLAS.COMPRAS+" ORDER BY "+TABLAS.FECHACOMPRA+" DESC LIMIT 1");
		while(cargar.next()) {
			compra=new Compra(cargar.getInt(TABLAS.CODIGOCOMPRA),cargar.getFloat(TABLAS.PRECIOFINAL),mc.deStringALocalDateTime(cargar.getString(TABLAS.FECHACOMPRA)));
		}
		arc.setIdArticulo(idar);
		arc.setCodigoCompra(compra.getCodigoCompra());
		comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULOSCOMPRADOS+" VALUES ('"+arc.getCodigoCompra()+"',"
				+ "'"+arc.getIdArticulo()+"','"+arc.getCantidad()+"','"+arc.getPrecioArt()+"')");
		ArticuloComprado cambiado=arc;
		
		cambiado.setCantidad(3);
		cambiado.setPrecioArt((float)10);
		gac.cambiarArticuloComprado(conexion, cambiado);
		ArticuloComprado comprobar=null;
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULOSCOMPRADOS+" WHERE "+TABLAS.CODIGOCOMPRA+"="+arc.getCodigoCompra()+" AND "+TABLAS.IDARTICULO+"="+arc.getIdArticulo()+"");
		while(carga.next()) {
		comprobar=new ArticuloComprado(carga.getInt(TABLAS.CODIGOCOMPRA),carga.getInt(TABLAS.IDARTICULO),
				carga.getInt(TABLAS.CANTIDAD),carga.getFloat(TABLAS.PRECIOART));
		}
		assertEquals(cambiado.getIdArticulo(),comprobar.getIdArticulo());
		assertEquals(cambiado.getCantidad(),comprobar.getCantidad());
		assertEquals(cambiado.getCodigoCompra(),comprobar.getCodigoCompra());
		assertEquals(cambiado.getPrecioArt(),comprobar.getPrecioArt());
		
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
	void testCargarArticulosDeUnaCompra() {
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
		int idar=-1;
		ResultSet cargar=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"' AND "+TABLAS.NOMBREARTICULO+"='"+co.getNombreArticulo()+"'");
		while(cargar.next()) {
			idar=cargar.getInt(TABLAS.IDARTICULO);
		}
		comando.executeUpdate("INSERT INTO `compras` (`dni`, `codigoCompra`, `precioFinal`, `fechaCompra`) VALUES "
		+ "('"+jefe.getDni()+"', 0, "+co.getPrecio()*2+", '"+LocalDateTime.now()+"')");
		Compra compra=null;
		cargar=comando.executeQuery("SELECT * FROM "+TABLAS.COMPRAS+" ORDER BY "+TABLAS.FECHACOMPRA+" DESC LIMIT 1");
		while(cargar.next()) {
			compra=new Compra(cargar.getInt(TABLAS.CODIGOCOMPRA),cargar.getFloat(TABLAS.PRECIOFINAL),mc.deStringALocalDateTime(cargar.getString(TABLAS.FECHACOMPRA)));
		}
		arc.setIdArticulo(idar);
		arc.setCodigoCompra(compra.getCodigoCompra());
		comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULOSCOMPRADOS+" VALUES ('"+arc.getCodigoCompra()+"',"
				+ "'"+arc.getIdArticulo()+"','"+arc.getCantidad()+"','"+arc.getPrecioArt()+"')");
		
		ArrayList<ArticuloComprado> lista=gac.cargarArticulosDeUnaCompra(conexion, compra);
		assertTrue(lista.size()>=1);
		ArticuloComprado comprobar=lista.get(lista.size()-1);
		assertEquals(arc.getIdArticulo(),comprobar.getIdArticulo());
		assertEquals(arc.getCantidad(),comprobar.getCantidad());
		assertEquals(arc.getCodigoCompra(),comprobar.getCodigoCompra());
		assertEquals(arc.getPrecioArt(),comprobar.getPrecioArt());
		
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
