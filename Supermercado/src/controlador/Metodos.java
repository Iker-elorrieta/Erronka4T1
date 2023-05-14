package controlador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import gestores.GestorArticulo;
import gestores.GestorCompra;
import gestores.GestorPersona;
import gestores.GestorSeccion;
import gestores.GestorSupermercado;
import modelo.*;
import otros.tipoArticulo;
import otros.tipoPersona;
import referencias.TABLAS;

/**Esta pagina de metodos es la que se utilizara para hacer diferentes operaciones sobre
 * los datos y tablas.
 * @author Erlantz
 *
 */
public class Metodos {
	
	/**
	 * Para nadirle estos datos a las secciones.
	 */
	//Metodos mc=new Metodos();
	
	private String [] Tipos= {"Comida","Herramienta","Ropa"}; 
	/**
	 * Para formatear las Dates.
	 */
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * Para formatear la fechas y horas antes de insertar una compra
	 */
	DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
	/**
	 * El gestor de articulo que utilizaremos sobre las 
	 * operaciones con articulos.
	 */
	GestorArticulo ga=new GestorArticulo();
	/**
	 * El gestor de persona que utilizaremos sobre las 
	 * operaciones con las cuentas de las personas.
	 */
	GestorPersona gp=new GestorPersona();
	/**
	 * El gestor de supermercado que utilizaremos sobre las 
	 * operaciones con los supermercados.
	 */
	GestorSupermercado gsm=new GestorSupermercado();
	/**
	 * El gestor de secciones que utilizaremos sobre las 
	 * operaciones con las secciones y sus articulos.
	 */
	GestorSeccion gs=new GestorSeccion();
	/**
	 * El gestor de compras que utilizaremos sobre las 
	 * operaciones con las compras.
	 */
	GestorCompra gc=new GestorCompra();
	/**Metodo para recibir un string de fecha
	 * y convertirlo en un date, para almacenarlo
	 * en los objetos que utilizen fechas.
	 * @param fecha El String.
	 * @return El tipo Date que devuelve.
	 * @throws ParseException Si no es del formato correcto.
	 */
	public Date deStringADate(String fecha) throws ParseException {
		return formatter.parse(fecha);
	}
	/**
	 * Para inertarlo en la base de datos
	 * con el formato correcto.
	 * @param fecha El Date a cambiar.
	 * @return El String con el formato correcto.
	 */
	public String formatearFecha(Date fecha) {
		return formatter.format(fecha);
	}
	/**Para convetir un String de fecha
	 * en un DateTime, para cuando cogemos
	 * las compras de la BBDD.
	 * @param fecha La fecha a cambiar.
	 * @return El dato correcto.
	 * @throws ParseException El formato no es correcto.
	 */
	public LocalDateTime deStringALocalDateTime(String fecha) throws ParseException {
		LocalDateTime fechaS = LocalDateTime.parse(fecha, formatter1);
		return fechaS;
	}
	/**Para cuando insertamos booleans
	 * a la BBDD.
	 * @param statement EL boolean a cambiar.
	 * @return El 1 o 0 a insertar.
	 */
	public int pasarBoolean(Boolean statement) {
		int resul= 0;
		if(statement) {
			resul=1;
		}
		return resul;
	}
	/**Para coger el dato boolean de la BBDD.
	 * @param statement El numero.
	 * @return El boolean.
	 */
	public boolean pasarIntABoolean(int statement) {
		boolean resul=true ;
		if(statement==0) {
			resul=false;
		}
		return resul;
	}
	/**Cambiar las secciones que se muestran
	 * al crear las secciones de un supermercado.
	 * @param tipos Los tipos de secciones.
	 * @param seleccion El tipo escogido.
	 * @return El String sin la seleccion.
	 */
	public String[] modificarComboBox(String[] tipos,String seleccion) {
		String [] nuevo=null;
		if(seleccion==null) {
			 nuevo= Tipos;
		}else {
			 nuevo=new String [tipos.length-1];
		for(int i=0,x=0;i<tipos.length;i++) {
			if(!tipos[i].equals(seleccion)) {
				nuevo[x]=tipos[i];
				x++;
			}
		}
		}
		return nuevo;
	}
	/**Para cargar los nombres de los jefes
	 * cuando creamos los supermercados.
	 * @param lista La lista de los jefes disponibles.
	 * @return El String [] con el dni de los jefes.
	 */
	public String[] cargarNombreJefe(ArrayList<Jefe> lista) {
		String [] jefes=new String[lista.size()];
		for(int i=0;i<lista.size();i++) {
			jefes[i]=lista.get(i).getDni();
		}
		return jefes;
	}
	/**Para cargar los nombres de las empresas,
	 * se utiliza para seleccionar el supermercado
	 * al que anadir articulos.
	 * @param lista Todos los supermercados.
	 * @return Los nombres de las empresas.
	 */
	public String[] cargarEmpresa(ArrayList<Supermercado> lista) {
		String [] superm=new String[lista.size()];
		for(int i=0;i<lista.size();i++) {
			superm[i]=lista.get(i).getEmpresa();
		}
		return superm;
	}
	/**Para cargar los nombres de las direcciones,
	 * se utiliza para seleccionar el supermercado
	 * al que anadir articulos.
	 * @param lista Los supermercados a seleccionar.
	 * @return Las direcciones a mostrar.
	 */
	public String[] cargarDireccionSuper(ArrayList<Supermercado> lista) {
		String [] superm=new String[lista.size()];
		for(int i=0;i<lista.size();i++) {
			superm[i]=lista.get(i).getDireccion();
		}
		return superm;
	}
	/**Para cargar los nombres de las secciones
	 * a las que anadirle articulos.
	 * @param lista Las secciones.
	 * @return Los Strings para la JComboBox.
	 */
	public String[] cargarNombreSeccion(ArrayList<Seccion> lista) {
		String [] superm=new String[lista.size()];
		for(int i=0;i<lista.size();i++) {
			superm[i]=String.valueOf(lista.get(i).getNombreSeccion());
		}
		return superm;
	}
	/**Cogemos una seccion de la lista
	 * para la insercion a la BBDD.
	 * @param lista Las secciones a mirar.
	 * @param campo El nombre de la seccion.
	 * @return La seccion escogida.
	 */
	public Seccion cogeSeccion(ArrayList<Seccion> lista,String campo) {
		Seccion vuelta=null;
		for(Seccion se:lista) {
			if(se.getNombreSeccion().equals(tipoArticulo.valueOf(campo))) {
				vuelta=se;
			}
		}
		return vuelta;
	}
	/**Para cargar la tabla de articulos
	 * @param conexion la conexion a la BBDD.
	 * @param listaArticulos Los articulos a cargar
	 * @return Los String [][] para poner en la tabla.
	 * @throws SQLException Por si falla la conexion a la BBDD.
	 */
	public String [][] cargarArticulos(Connection conexion,ArrayList<Articulo> listaArticulos) throws SQLException {
		listaArticulos=ga.cargarArticulos(conexion);
		Comida co=null;
		Ropa ro=null;
		Herramienta he=null;
		String[][] datosTabla = new String[listaArticulos.size()][6];
		for(int i = 0;i<listaArticulos.size();i++){
			datosTabla[i][0] = String.valueOf(listaArticulos.get(i).getNombreArticulo());
			datosTabla[i][1] = String.valueOf(listaArticulos.get(i).getRutaImagen());
			datosTabla[i][2] = String.valueOf(listaArticulos.get(i).getDescripcion());
			datosTabla[i][3] = String.valueOf(listaArticulos.get(i).getPrecio());
			if(listaArticulos.get(i) instanceof Comida) {
				co=(Comida)listaArticulos.get(i);
				datosTabla[i][4] = co.getFechaCaducidad();
				datosTabla[i][5] = co.getProcedencia();
			}else if(listaArticulos.get(i) instanceof Ropa) {
				ro=(Ropa) listaArticulos.get(i);
				datosTabla[i][4] = ro.getTalla();
				datosTabla[i][5] = ro.getMarca();
			}else if(listaArticulos.get(i) instanceof Herramienta) {
				he=(Herramienta) listaArticulos.get(i);
				if(he.getElectrica()) {
					datosTabla[i][4] = "Si";
				}else {
					datosTabla[i][4] = "No";
				}
				datosTabla[i][5] = String.valueOf(he.getGarantia());
				}
		}
		return datosTabla;
	}
	/**Para cargar las compras al historial de compras para realizar
	 * operaciones con el.
	 * @param conexion La conexxion a la BBDD.
	 * @param listaCompras Las compras filtradas por el usuario.
	 * @return El String [][] de las compras para poner en la tabla.
	 * @throws SQLException Fallo en la conexion.
	 * @throws ParseException  Fallo al pasar de Float a String.
	 */
	public String [][] cargarHistorialCompras(Connection conexion,ArrayList<Compra> listaCompras) throws SQLException, ParseException {
		//listaCompras=gc.cargarCompras(mc,conexion);
		String[][] datosTabla = new String[listaCompras.size()][3];
		for(int i = 0;i<listaCompras.size();i++){
			datosTabla[i][0] = String.valueOf(listaCompras.get(i).getCodigoCompra());
			datosTabla[i][1] = String.valueOf(listaCompras.get(i).getPrecioTotal());
			datosTabla[i][2] = String.valueOf(listaCompras.get(i).getFechaCompra());
		}
		return datosTabla;
	}
	/**Para cargar los articulos comprados en base a un codigo de compra.
	 * @param conexion La conexion.
	 * @param listaArC La lista de articuloscomprados del codigo de compra.
	 * @return String [][] con los nombres de articulo, cantidad y precio.
	 * @throws SQLException Fallo en la conexion.
	 */
	public String [][]cargarArticulosComprados(Connection conexion,ArrayList<ArticuloComprado> listaArC) throws SQLException{
		String[][] datosTabla = new String[listaArC.size()][3];
		ArrayList<Articulo> nombres=ga.cargarArticulos(conexion);
		for(int i = 0;i<listaArC.size();i++){
			for(Articulo ar:nombres) {
				if(ar.getIdArticulo()==listaArC.get(i).getIdArticulo()) {
					datosTabla[i][0] = String.valueOf(ar.getNombreArticulo());
				}
			}
			datosTabla[i][1] = String.valueOf(listaArC.get(i).getCantidad());
			datosTabla[i][2] = String.valueOf(listaArC.get(i).getPrecioArt());
		}
		return datosTabla;
	}
	/**Para mostrar dar el numero de filas
	 * que la tabla de articulos a recargar tendra.
	 * @param conexion La conexion.
	 * @return Los String  [][] a mostrar.
	 * @throws SQLException Fallo en la conexion.
	 */
	public String [][] cargarRecargaArticulos(Connection conexion) throws SQLException{
		Statement comando = (Statement) conexion.createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULOSRECARGAR);
		int numFilas=0;
		while(carga.next()) {
			numFilas++;
		}
	String[][] datosTabla = new String[numFilas][6];
	int cuenta=0;
	carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULOSRECARGAR);
	while(carga.next()) {
		datosTabla[cuenta][0]=carga.getString(TABLAS.ENCARGADO);
		datosTabla[cuenta][1]=carga.getString(TABLAS.IDARTICULO);
		datosTabla[cuenta][2]=carga.getString(TABLAS.NOMBREARTICULO);
		datosTabla[cuenta][3]=carga.getString(TABLAS.PRECIO);
		datosTabla[cuenta][4]=carga.getString(TABLAS.STOCKNECESARIO);
		datosTabla[cuenta][5]=carga.getString(TABLAS.PRECIOTOTAL);
		cuenta++;
	}
	return datosTabla;
	}
	/**Para cargar todos los usuarios de la base de datos.
	 * @param conexion La conexion.
	 * @param listaUsuarios Los usuarios recogidos de la BBDD.
	 * @return Los usuarios ordenados.
	 * @throws SQLException Fallo en la conexion.
	 */
	public String [][] tablaUsuarios(Connection conexion,ArrayList<Persona> listaUsuarios) throws SQLException{
		listaUsuarios=gp.cargarPersonas(conexion);
		Cliente prueba =null;
		Jefe jefe=null;
		String[][] datosTabla = new String[listaUsuarios.size()][6];
		for(int i = 0;i<listaUsuarios.size();i++){
			datosTabla[i][0] = String.valueOf(listaUsuarios.get(i).getNombre());
			datosTabla[i][1] = String.valueOf(listaUsuarios.get(i).getApellidos());
			datosTabla[i][2] = String.valueOf(listaUsuarios.get(i).getEmail());
			datosTabla[i][3] = String.valueOf(listaUsuarios.get(i).getTipo());
			if(listaUsuarios.get(i).getTipo().equals(tipoPersona.Cliente)) {
				prueba=(Cliente)listaUsuarios.get(i);
				if(prueba.isBloqueado()) {
				datosTabla[i][4] = String.valueOf("Bloqueado");
				}else {
				datosTabla[i][4] = String.valueOf("Cliente");	
				}
			}else {
				jefe=(Jefe)listaUsuarios.get(i);
				if(jefe.isDios()) {
				datosTabla[i][4] = String.valueOf("Root");
				}else {
				datosTabla[i][4] = String.valueOf("Administrador");	
				}
			}
		}
		return datosTabla;
	}
	/**Para cargar los supermercados en la tabla.
	 * @param conexion La conexion.
	 * @param lista Los dstos de todos los supermercados.
	 * @return  El String [][] con los datos de la tabla.
	 * @throws SQLException Fallo en la conexion.
	 */
	public String [][] cargaSuper(Connection conexion,ArrayList<Supermercado> lista) throws SQLException{
		lista=gsm.cargarSupermercados(conexion);
		String[][] datosTabla = new String[lista.size()][4];
		for(int i = 0;i<lista.size();i++){
			datosTabla[i][0] = String.valueOf(lista.get(i).getCodigoSuper());
			datosTabla[i][1] = String.valueOf(lista.get(i).getEmpresa());
			datosTabla[i][2] = String.valueOf(lista.get(i).getDireccion());
			datosTabla[i][3] = String.valueOf(lista.get(i).getNumEmpleados());
		}
		return datosTabla;
	}
	/**Para cargar las secciones en las tablas.
	 * @param conexion La conexion.
	 * @param lista Las lista de secciones.
	 * @return Las secciones como String [][].
	 * @throws SQLException Fallo en la conexion.
	 */
	public String [][] cargaSecciones(Connection conexion,ArrayList<Seccion> lista) throws SQLException{
		lista=gs.cargarSecciones(conexion);
		String[][] datosTabla = new String[lista.size()][3];
		for(int i = 0;i<lista.size();i++) {
				datosTabla[i][0]=lista.get(i).getCodigoSeccion();
				datosTabla[i][1]=String.valueOf(lista.get(i).getNombreSeccion());
				datosTabla[i][2]=String.valueOf(lista.get(i).getNumArticulo());
		}
		return datosTabla;
	}
	/**Metodo para generar un ticket de compra.
	 * @param login El comprador.
	 * @param carrito La compra.
	 * @param listaArticulos 
	 * @throws IOException 
	 */
	public void generarTicket(Persona login, Compra carrito, ArrayList<Articulo> listaArticulos) throws IOException {
		FileWriter fich = new FileWriter(".\\Tickets\\"+login.getEmail()+".txt");
		BufferedWriter buf=new BufferedWriter(fich);
		buf.write("La persona "+login.getNombre()+" compro:\r\n");
		for(ArticuloComprado ar: carrito.getListaCantidades()) {
			for(Articulo arc: listaArticulos) {
				if(arc.getIdArticulo()==ar.getIdArticulo()) {
					buf.write(ar.getCantidad()+" de "+arc.getNombreArticulo()+" de un valor individual de "+arc.getPrecio()+"\r\n");
				}
			}
		}
		buf.write("Costandole un total de "+carrito.getPrecioTotal()+" euros");
		buf.close();
	}
	
	
}