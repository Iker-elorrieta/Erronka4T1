package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modelo.Cliente;
import modelo.Jefe;
import modelo.Persona;
import modelo.tipoPersona;

public class GestorPersona {
	Metodos mc=new Metodos();
	private ArrayList<Persona> listaPersonas;
	
	public GestorPersona() {
		super();
		 listaPersonas=new ArrayList<Persona>();
	}

	public ArrayList<Persona> getListaPersonas() {
		return listaPersonas;
	}

	public void setListaPersonas(ArrayList<Persona> listaPersonas) {
		this.listaPersonas = listaPersonas;
	}
	public ArrayList<Persona> cargarPersonas() throws SQLException{
		ArrayList<Persona> listaPersonas=new ArrayList<Persona>();
		Cliente comprador=null;
		Jefe jefe=null;
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		ResultSet cuenta=comando.executeQuery("SELECT * FROM "+TABLAS.PERSONAS);
		while(cuenta.next()) {
			if(cuenta.getString(TABLAS.TIPO).equals("Cliente")) {
			comprador=new Cliente(cuenta.getString(TABLAS.DNI),
					cuenta.getString(TABLAS.NOMBRE), 
					cuenta.getString(TABLAS.APELLIDOS),
					cuenta.getDate(TABLAS.FECHANACIMIENTO),
					cuenta.getString(TABLAS.EMAIL),
					cuenta.getString(TABLAS.CONTRASENA),
					tipoPersona.valueOf(cuenta.getString(TABLAS.TIPO)),
					cuenta.getFloat(TABLAS.DINERO),
					cuenta.getInt(TABLAS.BLOQUEADO));
			listaPersonas.add(comprador);
			}else {			
			jefe=new Jefe(cuenta.getString(TABLAS.DNI), 
					cuenta.getString(TABLAS.NOMBRE), 
					cuenta.getString(TABLAS.APELLIDOS), 
					cuenta.getDate(TABLAS.FECHANACIMIENTO),
					cuenta.getString(TABLAS.EMAIL),
					cuenta.getString(TABLAS.CONTRASENA),
					tipoPersona.valueOf(cuenta.getString(TABLAS.TIPO)) ,
					cuenta.getDate(TABLAS.FECHAADQUISICION),
					cuenta.getFloat(TABLAS.PORCENTAJEEMPRESA),
					cuenta.getInt(TABLAS.DIOS));
			listaPersonas.add(jefe);
			}
		}
		return listaPersonas;
}
	public void insertarPersona(Persona persona) throws ErroresDeRegistro, SQLException {
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		Cliente c=null;
		Jefe j=null;
		if(persona instanceof Cliente) {
			c=(Cliente) persona;
		comando.executeUpdate("INSERT INTO "+TABLAS.PERSONAS+" "
		+ "("+TABLAS.DNI+","+TABLAS.NOMBRE+","+TABLAS.APELLIDOS+","+TABLAS.FECHANACIMIENTO+","+TABLAS.EMAIL+","+TABLAS.CONTRASENA+","+TABLAS.TIPO+","+TABLAS.DINERO+","+TABLAS.BLOQUEADO+")"
		+ " VALUES ('"+c.getDni()+"','"+c.getNombre()+"','"+c.getApellidos()+"','"+String.valueOf(c.getFechaNacimiento())+"','"+c.getEmail()+"','"+c.getContrasena()+"','"+c.getTipo()+"','"+c.getDinero()+"','"+mc.pasarBoolean(c.isBloqueado())+"')");
		}else {
			j=(Jefe) persona;
			comando.executeUpdate("INSERT INTO "+TABLAS.PERSONAS+" "
					+ "("+TABLAS.DNI+","+TABLAS.NOMBRE+","+TABLAS.APELLIDOS+","+TABLAS.FECHANACIMIENTO+","+TABLAS.EMAIL+","+TABLAS.CONTRASENA+","+TABLAS.TIPO+","+TABLAS.FECHAADQUISICION+","+TABLAS.PORCENTAJEEMPRESA+","+TABLAS.DIOS+")"
					+ " VALUES ('"+j.getDni()+"','"+j.getNombre()+"','"+j.getApellidos()+"','"+String.valueOf(j.getFechaNacimiento())+"','"+j.getEmail()+"','"+j.getContrasena()+"','"+j.getTipo()+"','"+j.getFechaAdquisicion()+"','"+j.getPorcentajeEmpresa()+"',"+j.isDios()+")");
		}
}
	public void darseBajaPersona(Persona persona) throws SQLException {
		Statement comando;
			comando = (Statement) mc.conectarBaseDatos().createStatement();
			comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+persona.getDni()+"'");
}
	public void cambiarPerfilCliente(Persona persona) throws SQLException {
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		Cliente cli=null;
		Jefe je=null;
		if(persona instanceof Cliente) {
			cli=(Cliente) persona;
		comando.executeUpdate("UPDATE "+TABLAS.PERSONAS+" SET "+TABLAS.DNI+"='"+cli.getDni()+"',"
				+ " "+TABLAS.NOMBRE+"='"+cli.getNombre()+"', "+TABLAS.APELLIDOS+"='"+cli.getApellidos()+"',"
				+ " "+TABLAS.FECHANACIMIENTO+"='"+cli.getFechaNacimiento()+"', "+TABLAS.EMAIL+"='"+cli.getEmail()+"',"
				+ " "+TABLAS.DINERO+"='"+cli.getDinero()+"', "+TABLAS.CONTRASENA+"='"+cli.getContrasena()+"',"
				+ " "+TABLAS.BLOQUEADO+"='"+mc.pasarBoolean(cli.isBloqueado())+"', "+TABLAS.TIPO+"='"+cli.getTipo()+"'"
				+ " WHERE "+TABLAS.DNI+"='"+cli.getDni()+"'");	
		}else {
			je=(Jefe) persona;
			comando.executeUpdate("UPDATE "+TABLAS.PERSONAS+" SET "+TABLAS.DNI+"='"+je.getDni()+"',"
					+ " "+TABLAS.NOMBRE+"='"+je.getNombre()+"', "+TABLAS.APELLIDOS+"='"+je.getApellidos()+"',"
					+ " "+TABLAS.FECHANACIMIENTO+"='"+je.getFechaNacimiento()+"', "+TABLAS.EMAIL+"='"+je.getEmail()+"',"
					+ " "+TABLAS.CONTRASENA+"='"+je.getContrasena()+"', "+TABLAS.FECHAADQUISICION+"='"+je.getFechaAdquisicion()+"',"
					+ " "+TABLAS.DIOS+"='"+mc.pasarBoolean(je.isDios())+"', "+TABLAS.TIPO+"='"+je.getTipo()+"', "+TABLAS.PORCENTAJEEMPRESA+"='"+je.getPorcentajeEmpresa()+"'"
					+ " WHERE "+TABLAS.DNI+"='"+je.getDni()+"'");
		}
}
	public void AumentarDineroCliente(Cliente cliente,int dinero) throws SQLException, ErroresDeOperaciones {
		Statement comando;
			comando = (Statement) mc.conectarBaseDatos().createStatement();
			if(dinero<0) {
				throw new ErroresDeOperaciones("No puede retirar dinero");
			}else {
				comando.executeUpdate("UPDATE "+TABLAS.PERSONAS+" SET "+TABLAS.DINERO+"=("+TABLAS.DINERO+"+"+dinero+") WHERE "+TABLAS.DNI+"='"+cliente.getDni()+"'");
			}
	}
	public void cambiarEstadoUsuario(Cliente cliente,boolean opcion) throws SQLException {
		Statement comando;
			comando = (Statement) mc.conectarBaseDatos().createStatement();
			comando.executeUpdate("UPDATE "+TABLAS.PERSONAS+" SET "+TABLAS.BLOQUEADO+"='"+mc.pasarBoolean(opcion)+"'");
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
	public Persona buscarPersona(String campo,ArrayList<Persona> lista) {
		Persona nueva=null;
		for(Persona per:lista) {
			if(per.getDni().equals(campo)) {
				nueva=per;
			}
		}
		return nueva;
	}
}
