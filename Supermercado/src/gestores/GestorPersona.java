package gestores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controlador.Metodos;
import excepciones.ErroresDeLogin;
import excepciones.ErroresDeOperaciones;
import excepciones.ErroresDeRegistro;
import modelo.ArticuloComprado;
import modelo.Cliente;
import modelo.Compra;
import modelo.Jefe;
import modelo.Persona;
import otros.tipoPersona;
import referencias.PROCEDIMIENTOS;
import referencias.TABLAS;

public class GestorPersona {
	Metodos mc=new Metodos();
	GestorSupermercado gsm=new GestorSupermercado();
	GestorArticuloComprado gac=new GestorArticuloComprado();
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
	public void insertarPersona(Persona persona, Connection conexion) throws ErroresDeRegistro, SQLException {
		Statement comando = (Statement) conexion.createStatement();
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
		comando.close();
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
			comando.executeUpdate("UPDATE "+TABLAS.PERSONAS+" SET "+TABLAS.BLOQUEADO+"='"+mc.pasarBoolean(opcion)+"' WHERE "+TABLAS.DNI+"='"+cliente.getDni()+"'");
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
	public ArrayList<Jefe> cargarJefesSinSupermercado(int numSuper) throws SQLException, ErroresDeOperaciones {
		ArrayList<Persona> lista=cargarPersonas();
		ArrayList<Jefe> jefeSinSuper=new ArrayList<Jefe>();
		String [] dniJefes=new String[numSuper];
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		ResultSet cuenta=comando.executeQuery("SELECT "+TABLAS.DNIJEFE+" FROM "+TABLAS.SUPERMERCADO);
		int i=0;
		while(cuenta.next()) {
			dniJefes[i]=cuenta.getString(TABLAS.DNIJEFE);
		}
		Jefe temporal=null;
		if((dniJefes[numSuper-1]==null)) {
			throw new ErroresDeOperaciones("No hay Jefes disponibles");
		}else{
			for(Persona per:lista) {
				if(per instanceof Jefe) {
					temporal=(Jefe)per;
					boolean noRepe=true;
					for(int x=0;x<dniJefes.length;x++) {
						if(dniJefes[x].equals(temporal.getDni())) {
						noRepe=false;
						}
					}
					if(noRepe==true) {
						jefeSinSuper.add(temporal);
					}
				}
			}
		}
		return jefeSinSuper;
	}
	public int insertarCompraYCobrar(Connection conexion,Persona per,Compra compra) throws SQLException, ParseException {
		// TODO Auto-generated method stub
		Statement comando;
			comando = (Statement) conexion.createStatement();
			comando.executeUpdate("CALL "+PROCEDIMIENTOS.INSERTACOMPRA+" ('"+per.getDni()+"',"+compra.getPrecioTotal()+")");
			ResultSet cargar=comando.executeQuery("SELECT "+TABLAS.CODIGOCOMPRA+" FROM "+TABLAS.COMPRAS+" WHERE "+TABLAS.DNI+"='"+per.getDni()+"' ORDER BY "+TABLAS.FECHACOMPRA+" DESC LIMIT 1");
			int cod=0;
			while(cargar.next()) {
				cod=cargar.getInt(TABLAS.CODIGOCOMPRA);
			}
			comando.close();
	
			return cod;
			
		
	}
	public void insertarArticulos(Connection conexion,ArrayList<ArticuloComprado> lista,int cod) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		for(ArticuloComprado ar:lista) {
			comando.executeUpdate("CALL "+PROCEDIMIENTOS.INSERTARTICULO+"("+ar.getIdArticulo()+","+ar.getCantidad()+")");
			}
		comando.close();
	}
	public void cancelarArticulos(Connection conexion,Compra com) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("CALL "+PROCEDIMIENTOS.DEVOLVERTODOSARTICULOS+"("+com.getCodigoCompra()+")");
	}
	public void cancelarCompra(Connection conexion,Persona per,Compra com) throws SQLException {
		Cliente cli=null;
		
		Statement comando = (Statement) conexion.createStatement();
		ResultSet cargaDinero=comando.executeQuery("SELECT * FROM "+TABLAS.COMPRAS+" WHERE "+TABLAS.CODIGOCOMPRA+"='"+com.getCodigoCompra()+"'");
		float dineroDevolver=-1;
		while(cargaDinero.next()) {
			dineroDevolver=cargaDinero.getFloat(TABLAS.PRECIOFINAL);
		}
		
		if(per instanceof Cliente) {
			cli=(Cliente)per;
			System.out.println("UPDATE "+TABLAS.PERSONAS+" SET "+TABLAS.DINERO+"=("+cli.getDinero()+"+"+dineroDevolver+") WHERE "+TABLAS.DNI+"='"+cli.getDni()+"'");
			comando.executeUpdate("UPDATE "+TABLAS.PERSONAS+" SET "+TABLAS.DINERO+"=("+cli.getDinero()+"+"+dineroDevolver+") WHERE "+TABLAS.DNI+"='"+cli.getDni()+"'");	
		}
		comando.executeUpdate("DELETE FROM "+TABLAS.COMPRAS+" WHERE "+TABLAS.CODIGOCOMPRA+"='"+com.getCodigoCompra()+"'");
	}
	public void devolverUnArticulo(Connection conexion,Persona per,ArticuloComprado arc,int numeroDevolver) throws SQLException, InterruptedException {
		Metodos mc=new Metodos();
		Cliente cli=null;
		Statement comando = (Statement) conexion.createStatement();
		if(per instanceof Cliente) {
			cli=(Cliente) per;
		comando.executeUpdate("UPDATE "+TABLAS.PERSONAS+" SET "+TABLAS.DINERO+"=("+TABLAS.DINERO+"+"+arc.getCantidad()*arc.getPrecioArt()+") WHERE "+TABLAS.DNI+"='"+cli.getDni()+"'");
		}	
		if(arc.getCantidad()<=numeroDevolver) {
				comando.execute("DELETE FROM "+TABLAS.ARTICULOSCOMPRADOS+" WHERE "+TABLAS.IDARTICULO+"="+arc.getIdArticulo()+" AND "+TABLAS.CODIGOCOMPRA+"="+arc.getCodigoCompra()+"");
		}else {
			comando.execute("UPDATE "+TABLAS.ARTICULOSCOMPRADOS+" SET "+TABLAS.CANTIDAD+"=("+arc.getCantidad()+"-"+numeroDevolver+")"
			+ " WHERE "+TABLAS.IDARTICULO+ "="+arc.getIdArticulo()+" AND "+TABLAS.CODIGOCOMPRA+"="+arc.getCodigoCompra());
		}
		
	}
	public void compruebaDevolucionArticulo(Connection conexion,ArticuloComprado arc) throws SQLException {
		int numArc=0;
		Statement comando = (Statement) conexion.createStatement();
		ResultSet cargatres=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULOSCOMPRADOS+" WHERE "+TABLAS.CODIGOCOMPRA+"="+arc.getCodigoCompra()+"");
		while(cargatres.next()) {
			numArc++;
		}
		if(numArc==0) {
			comando.execute("DELETE FROM "+TABLAS.COMPRAS+" WHERE "+TABLAS.CODIGOCOMPRA+"='"+arc.getCodigoCompra()+"'");
		}	
	}
}