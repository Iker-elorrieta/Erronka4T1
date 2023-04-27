package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Controlador.Metodos;
import Modelo.Jefe;
import Modelo.Persona;

class testCargarPersonas {
	Metodos mc=new Metodos();
	@Test
	void test() {
		ArrayList<Persona> listaUser=null;
		try {
			listaUser=mc.cargarPersonas();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(listaUser!=null);
		Jefe je=null; 
		for(Persona per: listaUser) {
			if(per.getDni().equals("154352K")) {
				je=(Jefe) per;
			}
		}
	assertTrue(je!=null);
	assertEquals(je.getDni(),"154352K");
	assertEquals(je.getEmail(),"sbdbhsd@gmail.com");
	assertEquals(je.getContrasena(),"1234");
	}

}
