package vista;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

import excepciones.ErroresDeRegistro;
import gestores.GestorPersona;
import modelo.Cliente;
import modelo.Compra;
import otros.tipoPersona;

public class prueba {

	public static void main(String[] args) throws ErroresDeRegistro {
		// TODO Auto-generated method stub
		Cliente cliente = new Cliente("99999999X","Test","Test",Date.valueOf("2001-01-21"),"test@gmail.com","12345",tipoPersona.Cliente,(float)99.9,0);
		Compra co=new Compra();
		co.setPrecioTotal(7);
		GestorPersona gp=new GestorPersona();
		try {
			gp.insertarPersona(cliente);
			gp.insertarCompraYCobrar(cliente, co);
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
