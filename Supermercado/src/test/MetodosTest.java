package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

import gestores.GestorArticulo;
import gestores.GestorPersona;
import otros.tipoArticulo;
import otros.tipoPersona;

import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Statement;
import controlador.Metodos;
import modelo.Articulo;
import modelo.ArticuloComprado;
import modelo.Cliente;
import modelo.Comida;
import modelo.Compra;
import modelo.Jefe;
import modelo.Persona;
import modelo.Seccion;
import modelo.Supermercado;
import referencias.CONEXION;


@SuppressWarnings("javadoc")
class MetodosTest {
	Connection conexion;
Metodos mts = new Metodos();
GestorArticulo ga=new GestorArticulo();
GestorPersona gp=new GestorPersona();
	@Test
	void test_deStringADate(){
		String fecha="2019-11-08";
	try {
		Calendar cal=Calendar.getInstance();
		cal.set(2019, 10, 8, 0, 0, 0);
		assertEquals(mts.deStringADate(fecha).toString(),cal.getTime().toString());
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	@Test
	public void test_ModificarComboBox() {

		String[] tipos = { "Tipo 1", "Tipo 2", "Tipo 3", "Tipo 4", "Tipo 5" };

		String seleccion = "Tipo 3";

		assertEquals(mts.modificarComboBox(tipos, seleccion)[0], "Tipo 1");
		assertEquals(mts.modificarComboBox(tipos, null)[0], "Comida");
	}


	@Test
	void test_deArrayListAStringArrayJefe(){
				ArrayList<Jefe> listaT = new ArrayList<Jefe>();
				listaT.add(new Jefe("11111111A", "Jefe 1", null, null, null, null, null, null, 0, 0));
				listaT.add(new Jefe("22222222B", "Jefe 2", null, null, null, null, null, null, 0, 0));
				listaT.add(new Jefe("33333333C", "Jefe 3", null, null, null, null, null, null, 0, 0));

				assertEquals(mts.cargarNombreJefe(listaT)[0], "11111111A");
	}

	@Test
	void test_deArrayListAStringArraySuper(){
		ArrayList<Supermercado> listaT = new ArrayList<Supermercado>();
		listaT.add(new Supermercado("AUSDHL", "Eroski", null, 0, null));
		listaT.add(new Supermercado("ASDF", "Mercadona", null, 0, null));
		listaT.add(new Supermercado("DJSADH", "Coviran", null, 0, null));

		assertEquals(mts.cargarEmpresa(listaT)[0], "Eroski");
	}

	@Test
	void test_deArrayListAStringArrayDireccion(){
		ArrayList<Supermercado> listaT = new ArrayList<Supermercado>();
		listaT.add(new Supermercado("AUSDHL", "Eroski", "ohio", 0, null));
		listaT.add(new Supermercado("ASDF", "Mercadona", "wahsington", 0, null));
		listaT.add(new Supermercado("DJSADH", "Coviran", "canada", 0, null));

		assertEquals(mts.cargarDireccionSuper(listaT)[0], "ohio");
	}

	@Test
	void test_deArrayListAStringArrayNombreSeccion(){
		ArrayList<Seccion> listaT = new ArrayList<Seccion>();
		listaT.add(new Seccion(null, tipoArticulo.Comida, 0, null));
		listaT.add(new Seccion(null, tipoArticulo.Ropa, 0, null));
		listaT.add(new Seccion(null, tipoArticulo.Herramienta, 0, null));

		assertEquals(mts.cargarNombreSeccion(listaT)[0].toString(), String.valueOf(tipoArticulo.Comida));
	}

	@Test
	void test_cogeSeccion(){
		ArrayList<Seccion> listaT = new ArrayList<>();
		listaT.add(new Seccion("Electrónica", tipoArticulo.Comida, 1234, null));
		listaT.add(new Seccion("Moda", tipoArticulo.Ropa, 5678, null));
		listaT.add(new Seccion("Hogar", tipoArticulo.Herramienta, 91011, null));

		Seccion seccion = mts.cogeSeccion(listaT, "Comida");
		assertNotNull(seccion);
		assertEquals("Comida", seccion.getNombreSeccion().toString());
	}

	@Test
	void test_pasarBoolean(){

		assertEquals(mts.pasarBoolean(true),1);
		assertEquals(mts.pasarBoolean(false),0);
	}

//
	
	@Test
	void test_cargarArticulos(){
		try {
			conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
			ArrayList<Articulo> listaArticulos = new ArrayList<Articulo>();
			
			assertEquals(mts.cargarArticulos(conexion, listaArticulos)[0][0],"Martillo");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void test_cargarHistorialCompras(){
			try {
				conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
				ArrayList<Compra> lista = new ArrayList<Compra>();
				Compra obj = new Compra(1, 23, null);
				lista.add(obj);
				
				assertEquals(mts.cargarHistorialCompras(conexion, lista)[0][0],"1");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Test
	void test_cargarArticulosComprados(){
		try {
			conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
			ArrayList<ArticuloComprado> lista = new ArrayList<ArticuloComprado>();
			ArticuloComprado obj = new ArticuloComprado(1, 1, 4, 12);
			lista.add(obj);
			
			assertEquals(mts.cargarArticulosComprados(conexion, lista)[0][1],"4");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
	}
	
	@Test
	void test_cargarRecargaArticulos(){
		try {
			conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
			Statement comando = (Statement) conexion.createStatement();
			comando.executeUpdate("INSERT INTO `articulosrecargar` (`encargado`, `idArticulo`, `nombreArticulo`, `precio`, `stockNecesario`, `precioTotal`) VALUES ('alan', '20', 'tornillo', '8', '10', '27');");
			assertTrue(mts.cargarRecargaArticulos(conexion).length>1);
			comando.executeUpdate("DELETE FROM `articulosrecargar` WHERE `articulosrecargar`.`encargado` = 'alan';");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void test_tablaUsuarios(){
		try {
			conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
			ArrayList<Persona> lista = new ArrayList<Persona>();
			
			assertEquals(mts.tablaUsuarios(conexion, lista)[0][0],"Administrador");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Test
	void test_cargaSuper(){
		try {
			conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
			ArrayList<Supermercado> lista = new ArrayList<Supermercado>();
			
			assertEquals(mts.cargaSuper(conexion, lista)[0][0],"AAAAA");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Test
	void test_cargaSecciones(){
		try {
			conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
			ArrayList<Seccion> lista = new ArrayList<Seccion>();
			
			assertEquals(mts.cargaSecciones(conexion, lista)[0][0],"A0001");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	@Test
	void testGenerarTicket() {
		Cliente primero=new Cliente("798981A","Pepe","Perez Navarros",Date.valueOf("2343-01-02"),"perez@gmail.com","contrasena",tipoPersona.Cliente,(float)55.67, 0);
		Comida co=new Comida(0, "Fruta del dragon", "fdd.png", "", (float)3.49, 99, Date.valueOf("2023-12-31"),"Brasil");
		ArrayList<Articulo> lista=new ArrayList<Articulo>();
		ArrayList<ArticuloComprado> listaArc=new ArrayList<ArticuloComprado>();
		Compra compra=new Compra(10,co.getPrecio()*2,LocalDateTime.now());
		
		ArticuloComprado arc=new ArticuloComprado(compra.getCodigoCompra(),co.getIdArticulo(),2,co.getPrecio());
		listaArc.add(arc);
		lista.add(co);
		compra.setListaCantidades(listaArc);
		try {
			mts.generarTicket(primero, compra, lista);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileReader fich;
		try {
			fich = new FileReader(".\\Tickets\\"+primero.getEmail()+".txt");
			BufferedReader buffer=new BufferedReader(fich);
			 String line=null;
			 String comprobar="";
	            while ((line =  buffer.readLine()) != null) {
	            	comprobar+=line;
	            }
	            assertEquals(comprobar,"La persona Pepe compro:2 de Fruta del dragon de un valor individual de 3.49Costandole un total de 6.98 euros");
	            buffer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}