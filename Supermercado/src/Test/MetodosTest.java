package Test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Controlador.ErroresDeRegistro;
import Controlador.Metodos;
import Modelo.Articulo;
import Modelo.ArticulosComprados;
import Modelo.Cliente;
import Modelo.Comida;
import Modelo.Compra;
import Modelo.Herramienta;
import Modelo.Jefe;
import Modelo.Ropa;
import Modelo.Seccion;
import Modelo.Supermercado;
import Modelo.tipoArticulo;

class MetodosTest {
Metodos mts = new Metodos();
	
//select, insert, update, delete

//select

	

	
	//ttttttttttttttttttttttttttttttt
	
	@Test
	void test_deStringADate(){
		
	}
	
	@Test
	void test_conectarBaseDatos(){
		
	}
	
	@Test
	void test_cargarClientes(){
			try {
				assertEquals(mts.cargarClientes().get(0).getDni(),"575464V");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Test
	void test_cargarJefes(){
		try {
			assertEquals(mts.cargarJefes().get(0).getDni(),"154352K");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void test_cargarSecciones(){
		try {
			assertEquals(mts.cargarSecciones().get(0).getNombreSeccion(),"Deportes");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	void test_cargarSupermercados(){
		try {
			assertEquals(mts.cargarSupermercados().get(0).getDireccion(),"Texas");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void test_cargarArticulosComprados(){
		try {
			assertEquals(mts.cargarArticulosComprados().get(0).getCantidad(),2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	void test_cargarCompras(){
		try {
			assertEquals(mts.cargarCompras().get(0).getCodigoCompra(),1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	void test_cargarArticulos(){
		try {
			assertEquals(mts.cargarArticulos().get(0).getNombreArticulo(),"tornillo");
			assertEquals(mts.cargarArticulos().get(2).getNombreArticulo(),"zapatillas de correr");
			assertEquals(mts.cargarArticulos().get(1).getNombreArticulo(),"sopa");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	void test_registrarseCliente(){
Cliente cli = new Cliente("FASDH", "Gal", "jast", Date.valueOf("2001-01-21"), "Wassdf@gmail.com", "1234", 10, false, false);
		
		try {
			mts.registrarse(cli);
			assertEquals(mts.cargarClientes().get(mts.cargarClientes().size()-1).getDni(),"FASDH");
			mts.darseBajaCliente(cli);
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	void test_registrarseJefe() {
Jefe jef = new Jefe("ADNJASD", "Will", "Miles", Date.valueOf("2001-01-21"), "Wassdf@gmail.com", "1234", "economia", Date.valueOf("2020-01-21"), (float) 62.2, true);
		
		try {
			mts.registrarseJefe(jef);
			assertEquals(mts.cargarJefes().get(mts.cargarJefes().size()-1).getDni(),"ADNJASD");
			mts.darseBajaJefe(jef);
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	void test_insertarSupermercado(){	
Supermercado sup = new Supermercado("YYDH", "EEUU", 45, 13, "11:00 - 16:00", null);
		
		try {
			mts.insertarSupermercado(mts.cargarJefes().get(0), sup);
			assertEquals(mts.cargarSupermercados().get(mts.cargarSupermercados().size()-1).getCodigoSuper(),"YYDH");
			mts.borrarSupermercado(sup);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void test_insertarSeccion(){
Seccion sec = new Seccion("WRSS", "reparaciones", null);
		
		try {
			mts.insertarSeccion(mts.cargarSupermercados().get(0), sec);
			assertEquals(mts.cargarSecciones().get(mts.cargarSecciones().size()-1).getCodigoSeccion(),"WRSS");
			mts.borrarSeccion(sec);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	void test_insertarCompra(){

		
		try {
			Compra com = new Compra(mts.cargarCompras().size()+1, 44, Date.valueOf("2010-01-21"));
			
			mts.insertarCompra(mts.cargarClientes().get(0), com);
			assertEquals(mts.cargarCompras().get(mts.cargarCompras().size()-1).getCodigoCompra(),mts.cargarCompras().size());
			mts.borrarCompra(com);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	void test_insertarArticuloComprado(){
		try {
			ArticulosComprados ac = new ArticulosComprados(0, 0, 2, 6);
			
			mts.insertarArticuloComprado(mts.cargarCompras().get(0),mts.cargarArticulos().get(0),ac);
			assertEquals(mts.cargarArticulosComprados().get(mts.cargarArticulosComprados().size()-1).getCantidad(),2);
			mts.borrarArticuloComprado(mts.cargarArticulosComprados().get(mts.cargarArticulosComprados().size()-1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	void test_insertarArticulo(){
//		try {
//			Comida com = new Comida(mts.cargarArticulos().size()+1, "burger", "sgd", "dd", (double) 20, 10, 8, tipoArticulo.Comida, Date.valueOf("2015-02-02"), "japon");
//			Herramienta her = new Herramienta(mts.cargarArticulos().size()+1, "palanca", "sgd", "dd", (double) 20, 10, 8, tipoArticulo.Herramienta, true, 12);
//			Ropa rop = new Ropa(mts.cargarArticulos().size()+1, "palanca", "sgd", "dd", (double) 20, 10, 8, tipoArticulo.Herramienta, "S", "negro", "cuero", "adidas");
//			
//			mts.insertarArticulo(mts.cargarSecciones().get(0), com);
//			assertEquals(mts.cargarArticulos().get(mts.cargarArticulos().size()-1).getNombreArticulo(),"burger");
//			mts.borrarArticulo(com);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
	@Test
	void test_cambiarPerfilCliente(){
		
	}
	
	
	@Test
	void test_cambiarPerfilJefe(){
//Jefe jef0 = new Jefe("ADNJASD", "Will", "Miles", Date.valueOf("2001-01-21"), "Wassdf@gmail.com", "1234", "economia", Date.valueOf("2020-01-21"), (float) 62.2, true);
//Jefe jef1 = new Jefe("NNNDJ", "Falko", "Jerr", Date.valueOf("2001-01-21"), "Wassdf@gmail.com", "1234", "economia", Date.valueOf("2020-01-21"), (float) 62.2, true);
//		
//		try {
//			mts.cambiarPerfilJefe(jef1);
//			assertEquals(mts.cargarJefes().get(mts.cargarJefes().size()-1).getDni(),"NNNDJ");
//			mts.cambiarPerfilJefe(jef0);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
	@Test
	void test_cambiarSupermercado(){
		
	}
	
	
	@Test
	void test_cambiarSeccion(){
		
	}
	
	
	@Test
	void test_cambiarCompra(){
		
	}
	
	
	@Test
	void test_cambiarArticuloComprado(){
		
	}
	
	
	@Test
	void test_cambiarArticulo(){
		
	}
	
	//bbb
	@Test
	void test_darseBajaCliente(){
Cliente cli = new Cliente("FASDH", "Gal", "jast", Date.valueOf("2001-01-21"), "Wassdf@gmail.com", "1234", 10, false, false);
		
		try {
			mts.registrarse(cli);
			assertEquals(mts.cargarClientes().get(mts.cargarClientes().size()-1).getDni(),"FASDH");
			mts.darseBajaCliente(cli);
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	void test_darseBajaJefe(){
Jefe jef = new Jefe("ADNJASD", "Will", "Miles", Date.valueOf("2001-01-21"), "Wassdf@gmail.com", "1234", "economia", Date.valueOf("2020-01-21"), (float) 62.2, true);
		
		try {
			mts.registrarseJefe(jef);
			assertEquals(mts.cargarJefes().get(mts.cargarJefes().size()-1).getDni(),"ADNJASD");
			mts.darseBajaJefe(jef);
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	void test_borrarSeccion(){
Seccion sec = new Seccion("WRSS", "reparaciones", null);
		
		try {
			mts.insertarSeccion(mts.cargarSupermercados().get(0), sec);
			assertEquals(mts.cargarSecciones().get(mts.cargarSecciones().size()-1).getCodigoSeccion(),"WRSS");
			mts.borrarSeccion(sec);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	void test_borrarArticulo(){
		//
	}
	
	
	@Test
	void test_borrarCompra(){
		try {
			Compra com = new Compra(mts.cargarCompras().size()+1, 44, Date.valueOf("2010-01-21"));
			
			mts.insertarCompra(mts.cargarClientes().get(0), com);
			assertEquals(mts.cargarCompras().get(mts.cargarCompras().size()-1).getCodigoCompra(),mts.cargarCompras().size());
			mts.borrarCompra(com);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	void test_borrarArticuloComprado(){
		try {
			ArticulosComprados ac = new ArticulosComprados(0, 0, 2, 6);
			
			mts.insertarArticuloComprado(mts.cargarCompras().get(0),mts.cargarArticulos().get(0),ac);
			assertEquals(mts.cargarArticulosComprados().get(mts.cargarArticulosComprados().size()-1).getCantidad(),2);
			mts.borrarArticuloComprado(mts.cargarArticulosComprados().get(mts.cargarArticulosComprados().size()-1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	void test_borrarSupermercado(){
Seccion sec = new Seccion("WRSS", "reparaciones", null);
		
		try {
			mts.insertarSeccion(mts.cargarSupermercados().get(0), sec);
			assertEquals(mts.cargarSecciones().get(mts.cargarSecciones().size()-1).getCodigoSeccion(),"WRSS");
			mts.borrarSeccion(sec);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//bbb
	
	
	@Test
	void test_cargarPersonas(){
		
		assertEquals(mts.cargarPersonas().get(0).getNombre(),"Arthur");
	}
	
	@Test
	void test_guardarInventario(){
		try {
			Comida com = new Comida(mts.cargarArticulos().size()+1, "burger", "sgd", "dd", (double) 20, 10, 8, tipoArticulo.Comida, Date.valueOf("2015-02-02"), "japon");
			ArrayList<Articulo> inventario = new ArrayList<Articulo>();
			inventario.add(com);
			mts.guardarInventario(inventario);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	void test_comprobarCampos(){
		try {
			mts.comprobarCampos("juan", "lopez", "1234", "55448K", "2004-03-01", "dasd@ssgd");
			mts.comprobarCampos("juan", "lopez", "1234", "55448K", "2004-03-01", "");
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	void test_comprobarNacimiento(){
		try {
			mts.comprobarNacimiento("1999-10-30");
			mts.comprobarNacimiento("2009-03-01");
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	void test_comprobarEmail(){
		try {
			mts.comprobarEmail("gsdsd@gmail.com");
			mts.comprobarEmail("dhdggd");
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	@Test
	void test_comprobarDNI(){
		try {
			mts.comprobarDNI("70124029L");
			mts.comprobarDNI("322LL");
			
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			mts.comprobarDNI("hsdsbdhsL");
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	@Test
	void test_AumentarDineroCliente(){
		Cliente cli = new Cliente("FASDH", "Gal", "jast", Date.valueOf("2001-01-21"), "Wassdf@gmail.com", "1234", 0, false, false);
		mts.AumentarDineroCliente(cli, 27);
		try {
			assertEquals(Double.valueOf(mts.cargarClientes().get(0).getDinero()),Double.valueOf(27));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	@Test
	void test_bloquearUsuario(){
		try {
			mts.bloquearUsuario(mts.cargarClientes().get(0));
			assertEquals(mts.cargarClientes().get(0).isBloqueado(),false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	void test_iniciarSesion(){
		
	}
	
	
	

}
