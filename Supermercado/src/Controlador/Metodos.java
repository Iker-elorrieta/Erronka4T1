package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Modelo.Cliente;
import Modelo.Jefe;
import Modelo.Persona;

public class Metodos {
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public Connection conectarBaseDatos() throws SQLException {
		Connection conexion = (Connection) DriverManager.getConnection(Conexion.URL, Conexion.USER, Conexion.PASS);
		return conexion;
	}
	public ArrayList<Persona> cargarPersonas() {
	 ArrayList<Persona> listaPersonas=new ArrayList<Persona>();
	Cliente comprador=null;
	Jefe jefes=null;
	try {
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		ResultSet cuenta=comando.executeQuery("SELECT * FROM "+Tablas.Cliente);
		while(cuenta.next()) {
			comprador=new Cliente(cuenta.getString(Tablas.DNI), cuenta.getString(Tablas.Nombre), 
					cuenta.getString(Tablas.Apellidos), cuenta.getDate(Tablas.FechaNacimineto),cuenta.getString(Tablas.Email),
					cuenta.getString(Tablas.Contrasena),cuenta.getDouble(Tablas.Dinero),cuenta.getBoolean(Tablas.TarjetaCliente));
			listaPersonas.add(comprador);
		}
		ResultSet cuentaJefe=comando.executeQuery("SELECT * FROM "+Tablas.Jefe);
		while(cuentaJefe.next()) {
		jefes=new Jefe(cuentaJefe.getString(Tablas.DNI), cuentaJefe.getString(Tablas.Nombre), 
				cuentaJefe.getString(Tablas.Apellidos), cuentaJefe.getDate(Tablas.FechaNacimineto),cuentaJefe.getString(Tablas.Email),
				cuentaJefe.getString(Tablas.Contrasena),cuentaJefe.getString(Tablas.Titulo));
		listaPersonas.add(jefes);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return listaPersonas;
}
	public void comprobarCampos(String nombre,String apellidos, String contrasena,String DNI,String fechaNa,String email) throws ErroresDeRegistro {
		if(nombre.equals("")|apellidos.equals("")|contrasena.equals("")|DNI.equals("")|fechaNa.equals("")|email.equals("")) {
			throw new ErroresDeRegistro("Alguno de los campos esta vacio");
		}
	}
	public void comprobarNacimiento(String fechaNa) throws ErroresDeRegistro {
		Calendar fechas=Calendar.getInstance();
		String [] separado=fechaNa.split("-");
		int ano=Integer.parseInt(separado[0]);
		int anios=fechas.get(Calendar.YEAR);
		int resul=anios-ano;
		if(resul<18) {
			throw new ErroresDeRegistro("Los menores de edad no pueden registrarse");
		}
	}
	public void comprobarEmail(String email) throws ErroresDeRegistro {
		Pattern p = Pattern.compile("^(.+)@(\\S+)$");
		 Matcher m = p.matcher(email);
		 boolean b = m.matches();
		 if(!b) {
			  throw new ErroresDeRegistro("El email no tiene el patron correcto");
		 }
	}
	public void comprobarDNI(String DNI) throws ErroresDeRegistro {
		if(DNI.length()!=9) {
			throw new ErroresDeRegistro("El dni no tiene 9 caracteres");
		}
		String numeros=DNI.substring(0,8);
		int num=0;
		try {
		num=Integer.parseInt(numeros);
		}catch(Exception e) {
			throw new ErroresDeRegistro("Los 8 primeros caracteres no son todos numeros"+num);
		}
	}
	public void registrarse(String nombre,String apellidos, String contrasena,String DNI,String fechaNa,String email) throws ErroresDeRegistro {
				try {
					Statement comando = (Statement) conectarBaseDatos().createStatement();
					comando.executeUpdate("INSERT INTO "+Tablas.Cliente+" VALUES ('"+DNI+"','"+nombre+"','"+apellidos+"',"
											+ "'"+fechaNa+"','"+email+"','"+0+"','"+0+"','"+contrasena+"')");		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Persona iniciarSesion(ArrayList<Persona> lista,String email, String contrasena) throws ErroresDeLogin {
		// TODO Auto-generated method stub
		if(email.equals("") | contrasena.equals("")) {
			throw new ErroresDeLogin("Algun campo esta vacío.");
		}
		Persona inicio=null;
		for (Persona per:lista) {
			if(per.getEmail().equals(email) && per.getContrasena().equals(contrasena)) {
				inicio=per;
			}
		}
		if (inicio==null) {
			throw new ErroresDeLogin("No esta registrado o campos incorrectos.");
		}
		return inicio;
	}
	
}
