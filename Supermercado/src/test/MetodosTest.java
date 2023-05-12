package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

import controlador.*;
import gestores.GestorArticulo;
import gestores.GestorPersona;
import modelo.*;
import otros.tipoArticulo;


class MetodosTest {
Metodos mts = new Metodos();
GestorArticulo ga=new GestorArticulo();
GestorPersona gp=new GestorPersona();
	@Test
	void test_deStringADate(){
		String fecha="2019-11-08";
	try {
		System.out.println(mts.deStringADate(fecha).toString());
		Calendar cal=Calendar.getInstance();
		cal.set(2019, 10, 8, 0, 0, 0);
		assertEquals(mts.deStringADate(fecha).toString(),cal.getTime().toString());	
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	@Test
	void test_guardarInventario(){
		try {
			Comida com = new Comida(ga.cargarArticulos().size()+1, "burger", "sgd", "dd", 10, 8, Date.valueOf("2015-02-02"), "japon");
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
	void test_TablaArt(){
		MetodosVista cv = new MetodosVista();
		ArrayList<Articulo> listaArticulos = new ArrayList<Articulo>();
		try {
			cv.cargarTabla(listaArticulos).getModel();
			int columnas = cv.cargarTabla(listaArticulos).getModel().getRowCount();
			assertEquals(columnas,ga.cargarArticulos().size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//
	
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
	
	

	
	
}





