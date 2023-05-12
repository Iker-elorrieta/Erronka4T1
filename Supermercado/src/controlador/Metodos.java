package controlador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
import gestores.GestorPersona;
import gestores.GestorSeccion;
import gestores.GestorSupermercado;
import modelo.*;
import otros.tipoArticulo;
import otros.tipoPersona;
import referencias.CONEXION;
import referencias.TABLAS;

public class Metodos {
	private String [] Tipos= {"Comida","Herramienta","Ropa"}; 
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
	GestorArticulo ga=new GestorArticulo();
	GestorPersona gp=new GestorPersona();
	GestorSupermercado gsm=new GestorSupermercado();
	GestorSeccion gs=new GestorSeccion();
	public Date deStringADate(String fecha) throws ParseException {
		return formatter.parse(fecha);
	}
	public String formatearFecha(Date fecha) {
		return formatter.format(fecha);
	}
	public LocalDateTime deStringALocalDateTime(String fecha) throws ParseException {
		LocalDateTime fechaS = LocalDateTime.parse(fecha, formatter1);
		return fechaS;
	}
	public Connection conectarBaseDatos() throws SQLException {
		Connection conexion = (Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		return conexion;
	}
	public int pasarBoolean(Boolean statement) {
		int resul= 0;
		if(statement) {
			resul=1;
		}
		return resul;
	}
	public boolean pasarIntABoolean(int statement) {
		boolean resul=true ;
		if(statement==0) {
			resul=false;
		}
		return resul;
	}
	public void guardarInventario(ArrayList<Articulo> lista) throws IOException {
			FileWriter fich = new FileWriter(".\\Inventario.txt");
			BufferedWriter buf=new BufferedWriter(fich);
			for(Articulo ar: lista) {
				buf.write(ar.toString());
			}
			buf.close();
	}
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
	public String[] cargarNombreJefe(ArrayList<Jefe> lista) {
		String [] jefes=new String[lista.size()];
		for(int i=0;i<lista.size();i++) {
			jefes[i]=lista.get(i).getDni();
		}
		return jefes;
	}
	public String[] cargarEmpresa(ArrayList<Supermercado> lista) {
		String [] superm=new String[lista.size()];
		for(int i=0;i<lista.size();i++) {
			superm[i]=lista.get(i).getEmpresa();
		}
		return superm;
	}
	public String[] cargarDireccionSuper(ArrayList<Supermercado> lista) {
		String [] superm=new String[lista.size()];
		for(int i=0;i<lista.size();i++) {
			superm[i]=lista.get(i).getDireccion();
		}
		return superm;
	}
	public String[] cargarNombreSeccion(ArrayList<Seccion> lista) {
		String [] superm=new String[lista.size()];
		for(int i=0;i<lista.size();i++) {
			superm[i]=String.valueOf(lista.get(i).getNombreSeccion());
		}
		return superm;
	}
	public Seccion cogeSeccion(ArrayList<Seccion> lista,String campo) {
		Seccion vuelta=null;
		for(Seccion se:lista) {
			if(se.getNombreSeccion().equals(tipoArticulo.valueOf(campo))) {
				vuelta=se;
			}
		}
		return vuelta;
	}
	public String [][] cargarArticulos(ArrayList<Articulo> listaArticulos) throws SQLException {
		listaArticulos=ga.cargarArticulos();
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
	public String [][] cargarHistorialCompras(ArrayList<Compra> listaCompras) throws SQLException {
		String[][] datosTabla = new String[listaCompras.size()][3];
		for(int i = 0;i<listaCompras.size();i++){
			datosTabla[i][0] = String.valueOf(listaCompras.get(i).getCodigoCompra());
			datosTabla[i][1] = String.valueOf(listaCompras.get(i).getPrecioTotal());
			datosTabla[i][2] = String.valueOf(listaCompras.get(i).getFechaCompra());
		}
		return datosTabla;
	}
	public String [][]cargarArticulosComprados(ArrayList<ArticuloComprado> listaArC) throws SQLException{
		String[][] datosTabla = new String[listaArC.size()][3];
		ArrayList<Articulo> nombres=ga.cargarArticulos();
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
	public String [][] cargarRecargaArticulos() throws SQLException{
		Statement comando = (Statement) conectarBaseDatos().createStatement();
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
	public String [][] tablaUsuarios(ArrayList<Persona> listaUsuarios) throws SQLException{
		listaUsuarios=gp.cargarPersonas();
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
	public String [][] cargaSuper(ArrayList<Supermercado> lista) throws SQLException{
		lista=gsm.cargarSupermercados();
		String[][] datosTabla = new String[lista.size()][4];
		for(int i = 0;i<lista.size();i++){
			datosTabla[i][0] = String.valueOf(lista.get(i).getCodigoSuper());
			datosTabla[i][1] = String.valueOf(lista.get(i).getEmpresa());
			datosTabla[i][2] = String.valueOf(lista.get(i).getDireccion());
			datosTabla[i][3] = String.valueOf(lista.get(i).getNumEmpleados());
		}
		return datosTabla;
	}
	public String [][] cargaSecciones(ArrayList<Seccion> lista) throws SQLException{
		lista=gs.cargarSecciones();
		String[][] datosTabla = new String[lista.size()][3];
		for(int i = 0;i<lista.size();i++) {
				datosTabla[i][0]=lista.get(i).getCodigoSeccion();
				datosTabla[i][1]=String.valueOf(lista.get(i).getNombreSeccion());
				datosTabla[i][2]=String.valueOf(lista.get(i).getNumArticulo());
		}
		return datosTabla;
	}
	
	
}