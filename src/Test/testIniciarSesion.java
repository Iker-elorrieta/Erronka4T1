package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Controlador.ErroresDeLogin;
import Controlador.Metodos;
import Modelo.Cliente;
import Modelo.Jefe;
import Modelo.Persona;

class testIniciarSesion {
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
			Jefe je=null;
			try {
				je=(Jefe) mc.iniciarSesion(listaUser, "sbdbhsd@gmail.com", "1234");
			} catch (ErroresDeLogin e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals(je.getDni(),"154352K");
			Cliente cli=null;
			try {
				cli=(Cliente) mc.iniciarSesion(listaUser, "sbdbhsd@gmail.com", "1234");
			} catch (ErroresDeLogin e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals(cli.getDni(),"575464V");
			try {
				 mc.iniciarSesion(listaUser, "", "");
			} catch (ErroresDeLogin e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				 mc.iniciarSesion(listaUser, "falloseguro@gmail.com", "fallo");
			} catch (ErroresDeLogin e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
