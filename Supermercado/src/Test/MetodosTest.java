package Test;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import Controlador.ErroresDeRegistro;
import Controlador.Metodos;
import Modelo.Jefe;

class MetodosTest {
Metodos mts = new Metodos();
	
//select, insert, update, delete

//select
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
	void test_registrarseJefe() {
Jefe jef = new Jefe("ADNJASD", "Will", "Miles", Date.valueOf("2001-01-21"), "Wassdf@gmail.com", "1234", "economia", Date.valueOf("2020-01-21"), (float) 62.2, true);
		
		try {
			mts.registrarseJefe(jef);
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
