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

/**El gestor de personas.
 * @author Erlantz
 *
 */
public class GestorPersona {
	/**
	 * Para cambiar el articulo devuelto.
	 */
	GestorArticuloComprado gac=new GestorArticuloComprado();
	/**
	 * La lista de personas.
	 */
	private ArrayList<Persona> listaPersonas;
	
	/**
	 * El constructor.
	 */
	public GestorPersona() {
		super();
		 listaPersonas=new ArrayList<Persona>();
	}

	/**Coger la lista de personas.
	 * @return La lista del gestor.
	 */
	public ArrayList<Persona> getListaPersonas() {
		return listaPersonas;
	}

	/**Copia la lista del parametro.
	 * @param listaPersonas La lista.
	 */
	public void setListaPersonas(ArrayList<Persona> listaPersonas) {
		this.listaPersonas = listaPersonas;
	}
	/**Para cargar las personas de la BBDD.
	 * @param conexion LA conexion.
	 * @return La lista de personas.
	 * @throws SQLException Fallo en la conexion.
	 */
	public ArrayList<Persona> cargarPersonas(Connection conexion) throws SQLException{
		ArrayList<Persona> listaPersonas=new ArrayList<Persona>();
		Cliente comprador=null;
		Jefe jefe=null;
		Statement comando = (Statement) conexion.createStatement();
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
	/**Inserta una persona a la BBDD.
	 * @param mc Para cambiar el boolean a un int.
	 * @param conexion La conexion.
	 * @param persona La persona a insertar.
	 * @throws SQLException Fallo en al conexion.
	 */
	public void insertarPersona(Metodos mc,Connection conexion,Persona persona ) throws SQLException {
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
	/**Para borrar una persona de la BBDD.
	 * @param conexion La conexion.
	 * @param persona La persona a borrar.
	 * @throws SQLException Fallo en la conexion.
	 */
	public void darseBajaPersona(Connection conexion,Persona persona) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("DELETE FROM "+TABLAS.PERSONAS+" WHERE "+TABLAS.DNI+"='"+persona.getDni()+"'");
		comando.close();
}
	/**Cambiar el perfil de una persona.
	 * @param mc Coger el int y pasarlo a boolean.
	 * @param conexion LA conexion.
	 * @param persona La persona.
	 * @throws SQLException Fallo de conexion.
	 */
	public void cambiarPerfilCliente(Metodos mc,Connection conexion,Persona persona) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
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
		comando.close();
}
	/**Aumentar el dinero de un cliente.
	 * @param conexion AL coenxion.
	 * @param cliente El cliente.
	 * @param dinero El dinero a aumentar.
	 * @throws SQLException Fallo en la conexion.
	 */
	public void AumentarDineroCliente(Connection conexion,Cliente cliente,int dinero) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("UPDATE "+TABLAS.PERSONAS+" SET "+TABLAS.DINERO+"=("+TABLAS.DINERO+"+"+dinero+") WHERE "+TABLAS.DNI+"='"+cliente.getDni()+"'");
		comando.close();
	}
	/**Metodo para bloquear desbloquear un usuario.
	 * @param mc Ccambiar el int a un boolean.
	 * @param conexion La conexion.
	 * @param cliente Cliente  a cambiar.
	 * @param opcion Un boolean.
	 * @throws SQLException Fallo de la conexion.
	 */
	public void cambiarEstadoUsuario(Metodos mc,Connection conexion,Cliente cliente,boolean opcion) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("UPDATE "+TABLAS.PERSONAS+" SET "+TABLAS.BLOQUEADO+"='"+mc.pasarBoolean(opcion)+"' WHERE "+TABLAS.DNI+"='"+cliente.getDni()+"'");
	}
	/**Para comprobar los datos del registro.
	 * @param nombre
	 * @param apellidos
	 * @param contrasena
	 * @param DNI
	 * @param fechaNa
	 * @param email
	 * @throws ErroresDeRegistro Si estan vacios lanza error.
	 */
	public void comprobarCampos(String nombre,String apellidos, String contrasena,String DNI,String fechaNa,String email) throws ErroresDeRegistro {
		if(nombre.equals("")|apellidos.equals("")|contrasena.equals("")|DNI.equals("")|fechaNa.equals("")|email.equals("")) {
			throw new ErroresDeRegistro("Alguno de los campos esta vacio");
		}
	}
	/**Para comprobar el nacimiento.
	 * @param fechaNa Fecha de nacimiento.
	 * @throws ErroresDeRegistro Si es menor de edad.
	 */
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
	/**Comprobar el Email.
	 * @param email Campo.
	 * @throws ErroresDeRegistro Patron incorrecto.
	 */
	public void comprobarEmail(String email) throws ErroresDeRegistro {
		Pattern p = Pattern.compile("^(.+)@(\\S+)$");
		 Matcher m = p.matcher(email);
		 boolean b = m.matches();
		 if(!b) {
			  throw new ErroresDeRegistro("El email no tiene el patron correcto");
		 }
	}
	/**Comprobar formato del DNI.
	 * @param DNI El DNIl.
	 * @throws ErroresDeRegistro No tiene 9 caracteres, 8 numeros.
	 */
	public void comprobarDNI(String DNI) throws ErroresDeRegistro {
		if(DNI.length()!=9) {
			throw new ErroresDeRegistro("El dni no tiene 9 caracteres");
		}
		String numeros=DNI.substring(0,8);
		try {
		@SuppressWarnings("unused")
		int num=Integer.parseInt(numeros);
		}catch(Exception e) {
			throw new ErroresDeRegistro("Los 8 primeros caracteres no son todos numeros");
		}
	}
	/**Para iniciar una sesion.
	 * @param lista Lista de usuarios.
	 * @param email El Emails
	 * @param contrasena La contrasena.
	 * @return Devuelve la persona
	 * @throws ErroresDeLogin O da un error.
	 */
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
	/**Coge una persona de la lista.
	 * @param campo Campo de busqueda(DNI).
	 * @param lista Lista de usaurios
	 * @return La pesona.
	 */
	public Persona buscarPersona(String campo,ArrayList<Persona> lista) {
		Persona nueva=null;
		for(Persona per:lista) {
			if(per.getDni().equals(campo)) {
				nueva=per;
			}
		}
		return nueva;
	}
	/**Para caoger los jefes si supermercados.
	 * @param conexion La conexion.
	 * @param numSuper Numero de supermercados existente.
	 * @return Lista de jefes sin supermercado.
	 * @throws SQLException Fallo conexion.
	 * @throws ErroresDeOperaciones No lo encuentra.
	 */
	public ArrayList<Jefe> cargarJefesSinSupermercado(Connection conexion,int numSuper) throws SQLException, ErroresDeOperaciones {
		ArrayList<Persona> lista=cargarPersonas(conexion);
		ArrayList<Jefe> jefeSinSuper=new ArrayList<Jefe>();
		String [] dniJefes=new String[numSuper];
		Statement comando = (Statement) conexion.createStatement();
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
		comando.close();
		return jefeSinSuper;
	}
	/**Metodo para insertar una compra formada y cobrar.
	 * @param conexion La conexion.
	 * @param per Usuario.
	 * @param compra La compra a realizar.
	 * @return El codigo de compra.
	 * @throws SQLException Fallo de conexion.
	 * @throws ParseException
	 */
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
	/**Insertar los articulos de la compra.
	 * @param conexion La conexion
	 * @param lista Lista de articulos a insertar en articulo comprado.
	 * @param cod El codigo de la compra.
	 * @throws SQLException Fallo de conexion.
	 */
	public void insertarArticulos(Connection conexion,ArrayList<ArticuloComprado> lista,int cod) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		for(ArticuloComprado ar:lista) {
			comando.executeUpdate("CALL "+PROCEDIMIENTOS.INSERTARTICULO+"("+ar.getIdArticulo()+","+ar.getCantidad()+")");
			}
		comando.close();
	}
	/**Cancelar los articulos de una compra, devolver el dinero.
	 * @param conexion La conexion.
	 * @param com Compra a devolver.
	 * @throws SQLException Fallo de conexion.
	 */
	public void cancelarArticulos(Connection conexion,Compra com) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("CALL "+PROCEDIMIENTOS.DEVOLVERTODOSARTICULOS+"("+com.getCodigoCompra()+")");
		comando.close();
	}
	/**Comprobar una compra.
	 * @param conexion La conexion.
	 * @param per Perosna a la que devolver el dinero.
	 * @param com La compra.
	 * @throws SQLException Fallo de conexion.
	 */
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
			comando.executeUpdate("UPDATE "+TABLAS.PERSONAS+" SET "+TABLAS.DINERO+"=("+cli.getDinero()+"+"+dineroDevolver+") WHERE "+TABLAS.DNI+"='"+cli.getDni()+"'");	
		}
		comando.executeUpdate("DELETE FROM "+TABLAS.COMPRAS+" WHERE "+TABLAS.CODIGOCOMPRA+"='"+com.getCodigoCompra()+"'");
		comando.close();
	}
	/**Devolver un articulo.
	 * @param conexion La coenxion.
	 * @param per Persona a la que pertenece la compra.
	 * @param arc Articulo a devolver.
	 * @param numeroDevolver Cantidad a devolver.
	 * @throws SQLException Fallo de conexion.
	 * @throws ParseException Si no consigur el datos de la fecha de la tabla.
	 */
	public void devolverUnArticulo(Connection conexion,Persona per,ArticuloComprado arc,int numeroDevolver) throws SQLException, ParseException {
		Cliente cli=null;
		Statement comando = (Statement) conexion.createStatement();
		if(per instanceof Cliente) {
			cli=(Cliente) per;
		comando.executeUpdate("UPDATE "+TABLAS.PERSONAS+" SET "+TABLAS.DINERO+"=("+TABLAS.DINERO+"+"+arc.getCantidad()*arc.getPrecioArt()+") WHERE "+TABLAS.DNI+"='"+cli.getDni()+"'");
		}
		if(arc.getCantidad()<=numeroDevolver) {
				comando.execute("DELETE FROM "+TABLAS.ARTICULOSCOMPRADOS+" WHERE "+TABLAS.IDARTICULO+"="+arc.getIdArticulo()+" AND "+TABLAS.CODIGOCOMPRA+"="+arc.getCodigoCompra()+"");
		}else {
			ArticuloComprado arcCambiado=arc;
			arcCambiado.setCantidad(arcCambiado.getCantidad()-numeroDevolver);
			gac.cambiarArticuloComprado(conexion, arc);
		}
		comando.close();
	}
	/**Comprueba si hay que borrar la compra.
	 * @param conexion La conexion.
	 * @param arc Articulo comprado a camprobar.
	 * @throws SQLException Fallo de conexion.
	 */
	public void compruebaDevolucionArticulo(Connection conexion,ArticuloComprado arc) throws SQLException {
		int numArc=0;
		Statement comando = (Statement) conexion.createStatement();
		ResultSet cargatres=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULOSCOMPRADOS+" WHERE "+TABLAS.CODIGOCOMPRA+"="+arc.getCodigoCompra()+" AND "+TABLAS.IDARTICULO+"="+arc.getIdArticulo()+"");
		while(cargatres.next()) {
			numArc++;
		}
		if(numArc==0) {
			comando.execute("DELETE FROM "+TABLAS.COMPRAS+" WHERE "+TABLAS.CODIGOCOMPRA+"='"+arc.getCodigoCompra()+"'");
		}
		comando.close();
	}
}
