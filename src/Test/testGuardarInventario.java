package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Controlador.Metodos;
import Modelo.Articulo;
import Modelo.Comida;
import Modelo.tipoArticulo;

class testGuardarInventario {
	Metodos mc=new Metodos();
	@Test
	void test() {
		Comida co=new Comida(77, "Torilla", "prueba/XXXXXXXX.jpg", "Una tortilla de 200 Gramos con productos artesanales", 2.55, 300, 250, tipoArticulo.Comida, Date.valueOf("2023-05-10"), "Espana");
		ArrayList<Articulo> listaAr=new ArrayList<Articulo>();
		listaAr.add(co);
		try {
			mc.guardarInventario(listaAr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileReader fich = null;
		try {
			fich = new FileReader(".\\Inventario.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader buf=new BufferedReader(fich);
		String linea = null;
		
		try {
			while ((linea = buf.readLine()) != null) {
				assertEquals(linea,co.toString());
				}
			buf.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
