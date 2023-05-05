package test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

import controlador.*;
import modelo.*;


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
			Comida com = new Comida(ga.cargarArticulos().size()+1, "burger", "sgd", "dd", 10, 8, tipoArticulo.Comida, Date.valueOf("2015-02-02"), "japon");
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
}