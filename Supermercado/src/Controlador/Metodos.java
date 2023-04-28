package Controlador;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Modelo.*;

public class Metodos {
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	public Date deStringADate(String fecha) throws ParseException {
		return formatter.parse(fecha);
	}
	public Connection conectarBaseDatos() throws SQLException {
		Connection conexion = (Connection) DriverManager.getConnection(Conexion.URL, Conexion.USER, Conexion.PASS);
		return conexion;
	}
	public ArrayList<Cliente> cargarClientes() throws SQLException{
		ArrayList<Cliente> listaClientes=new ArrayList<Cliente>();
		Cliente comprador=null;
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		ResultSet cuenta=comando.executeQuery("SELECT * FROM "+Tablas.Cliente);
		while(cuenta.next()) {
			comprador=new Cliente(cuenta.getString(Tablas.DNI), cuenta.getString(Tablas.Nombre), 
					cuenta.getString(Tablas.Apellidos), cuenta.getDate(Tablas.FechaNacimineto),cuenta.getString(Tablas.Email),
					cuenta.getString(Tablas.Contrasena),cuenta.getDouble(Tablas.Dinero),cuenta.getBoolean(Tablas.TarjetaCliente)
					,cuenta.getBoolean(Tablas.bloqueado));
			listaClientes.add(comprador);
		}
		return listaClientes;
	}
	public ArrayList<Jefe> cargarJefes() throws SQLException{
		ArrayList<Jefe> listaJefes=new ArrayList<Jefe>();
		Jefe jefes=null;
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		ResultSet cuentaJefe=comando.executeQuery("SELECT * FROM "+Tablas.Jefe);
		while(cuentaJefe.next()) {
		jefes=new Jefe(cuentaJefe.getString(Tablas.DNI), cuentaJefe.getString(Tablas.Nombre), 
				cuentaJefe.getString(Tablas.Apellidos), cuentaJefe.getDate(Tablas.FechaNacimineto),cuentaJefe.getString(Tablas.Email),
				cuentaJefe.getString(Tablas.Contrasena),cuentaJefe.getString(Tablas.Titulo),cuentaJefe.getDate(Tablas.fechaAdquisicion)
				,cuentaJefe.getFloat(Tablas.porcentajeEmpresa),cuentaJefe.getBoolean(Tablas.dios));
		listaJefes.add(jefes);
		}
		return listaJefes;
	}
	public ArrayList<Seccion> cargarSecciones() throws SQLException{
		ArrayList<Seccion> lista=new ArrayList<Seccion>();
		Seccion sec=null;
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+Tablas.Seccion);
		while(carga.next()) {
		sec=new Seccion(carga.getString(Tablas.CodigoSeccion),carga.getString(Tablas.NombreSeccion),null);
		lista.add(sec);
		}
		return lista;
	}
	public ArrayList<Supermercado> cargarSupermercados() throws SQLException{
		ArrayList<Supermercado> lista=new ArrayList<Supermercado>();
		Supermercado su=null;
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+Tablas.Supermercado);
		while(carga.next()) {
		su=new Supermercado(carga.getString(Tablas.CodigoSuper),carga.getString(Tablas.Direccion),carga.getFloat(Tablas.MetrosCuadrados),
				carga.getInt(Tablas.NumeroEmpleados),carga.getString(Tablas.Horario),null);
		lista.add(su);
		}
		return lista;
	}
	public ArrayList<ArticulosComprados> cargarArticulosComprados() throws SQLException{
		ArrayList<ArticulosComprados> lista=new ArrayList<ArticulosComprados>();
		ArticulosComprados arc=null;
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+Tablas.ArticulosComprados);
		while(carga.next()) {
		arc=new ArticulosComprados(carga.getInt(Tablas.codigoCompra),carga.getInt(Tablas.IdArticulo),
				carga.getInt(Tablas.Cantidad),carga.getFloat(Tablas.PrecioArt));
		lista.add(arc);
		}
		return lista;
	}
	public ArrayList<Compra> cargarCompras() throws SQLException{
		ArrayList<Compra> lista=new ArrayList<Compra>();
		Compra arc=null;
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+Tablas.Compras);
		while(carga.next()) {
		arc=new Compra(carga.getInt(Tablas.codigoCompra),carga.getDouble(Tablas.precioFinal),carga.getDate(Tablas.fechaCompra));
		lista.add(arc);
		}
		return lista;
	}
	public ArrayList<Articulo> cargarArticulos() throws SQLException{
		ArrayList<Articulo> lista=new ArrayList<Articulo>();
		Herramienta he=null;
		Comida co=null;
		Ropa ro=null;
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+Tablas.Articulo+" WHERE "+Tablas.Tipo+"='"+Tablas.Herramienta+"'");
		while(carga.next()) {
		he=new Herramienta(carga.getInt(Tablas.IdArticulo),carga.getString(Tablas.NombreArticulo),
				carga.getString(Tablas.RutaImagen),carga.getString(Tablas.Descripcion),
				carga.getDouble(Tablas.Precio),carga.getInt(Tablas.StockMaximo),
				carga.getInt(Tablas.StockActual),tipoArticulo.valueOf(carga.getString(Tablas.Tipo)),
				carga.getBoolean(Tablas.Electrica),carga.getInt(Tablas.Garantia));
		lista.add(he);
		}
		ResultSet cargaC=comando.executeQuery("SELECT * FROM "+Tablas.Articulo+" WHERE "+Tablas.Tipo+"='"+Tablas.Comida+"'");
		while(cargaC.next()) {
		co=new Comida(cargaC.getInt(Tablas.IdArticulo),cargaC.getString(Tablas.NombreArticulo),
				cargaC.getString(Tablas.RutaImagen),cargaC.getString(Tablas.Descripcion),
				cargaC.getDouble(Tablas.Precio),cargaC.getInt(Tablas.StockMaximo),
				cargaC.getInt(Tablas.StockActual),tipoArticulo.valueOf(cargaC.getString(Tablas.Tipo)),
				cargaC.getDate(Tablas.FechaCaducidad),cargaC.getString(Tablas.Procedencia));
		lista.add(co);
		}
		ResultSet cargaR=comando.executeQuery("SELECT * FROM "+Tablas.Articulo+" WHERE "+Tablas.Tipo+"='"+Tablas.Ropa+"'");
		while(cargaR.next()) {
		ro=new Ropa(cargaR.getInt(Tablas.IdArticulo),cargaR.getString(Tablas.NombreArticulo),
				cargaR.getString(Tablas.RutaImagen),cargaR.getString(Tablas.Descripcion),
				cargaR.getDouble(Tablas.Precio),cargaR.getInt(Tablas.StockMaximo),
				cargaR.getInt(Tablas.StockActual),tipoArticulo.valueOf(cargaR.getString(Tablas.Tipo)),
				cargaR.getString(Tablas.Talla),cargaR.getString(Tablas.Color),cargaR.getString(Tablas.Material),
				cargaR.getString(Tablas.Marca));
		lista.add(ro);
		}
		return lista;
	}
	
	public int pasarBoolean(Boolean statement) {
		int resul= 0;
		if(statement) {
			resul=1;
		}
		return resul;
		
	}
	
	public void registrarse(Cliente cli) throws ErroresDeRegistro, SQLException {
			Statement comando = (Statement) conectarBaseDatos().createStatement();
			comando.executeUpdate("INSERT INTO "+Tablas.Cliente+" VALUES ('"+cli.getDni()+"','"+cli.getNombre()+"','"+cli.getApellidos()+"',"
									+ "'"+cli.getFechaNacimiento()+"','"+cli.getEmail()+"','"+cli.getDinero()+"','"+pasarBoolean(cli.isTarjetaCliente())+"',"
									+ "'"+cli.getContrasena()+"','"+pasarBoolean(cli.isBloqueado())+"')");		
}
	public void registrarseJefe(Jefe jefe) throws ErroresDeRegistro, SQLException {
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		comando.executeUpdate("INSERT INTO "+Tablas.Jefe+" VALUES ('"+jefe.getDni()+"','"+jefe.getNombre()+"',"
				+ "'"+jefe.getApellidos()+"','"+jefe.getFechaNacimiento()+"','"+jefe.getEmail()+"',"
				+ "'"+jefe.getTitulo()+"','"+jefe.getContrasena()+"','"+jefe.getFechaAdquisicion()+"',"
						+ "'"+jefe.getPorcentajeEmpresa()+"','"+pasarBoolean(jefe.isDios())+"')");
		
}	
	public void insertarSupermercado(Jefe jefe,Supermercado su) throws SQLException {
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		comando.executeUpdate("INSERT INTO "+Tablas.Supermercado+" VALUES ('"+jefe.getDni()+"','"+su.getCodigoSuper()+"',"
				+ "'"+su.getDireccion()+"','"+su.getMetrosCuadrados()+"','"+su.getNumEmpleados()+"','"+su.getHorario()+"')");
	}
	public void insertarSeccion(Supermercado su,Seccion se) throws SQLException {
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		comando.executeUpdate("INSERT INTO "+Tablas.Seccion+" VALUES ('"+se.getCodigoSeccion()+"','"+se.getNombreSeccion()+"',"
				+ "'"+su.getCodigoSuper()+"')");
	}
	public void insertarCompra(Cliente cli,Compra com) throws SQLException {
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		comando.executeUpdate("INSERT INTO "+Tablas.Compras+" VALUES ('"+cli.getDni()+"','"+com.getCodigoCompra()+"',"
				+ "'"+com.getPrecioTotal()+"','"+com.getFechaCompra()+"')");
	}
	public void insertarArticuloComprado(Compra com,Articulo art,ArticulosComprados arc) throws SQLException {
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		comando.executeUpdate("INSERT INTO "+Tablas.ArticulosComprados+" VALUES ('"+com.getCodigoCompra()+"',"
				+ "'"+art.getIdArticulo()+"','"+arc.getCantidad()+"','"+arc.getPrecioArt()+"')");
	}
	public void insertarArticulo(Seccion se,Articulo ar) throws SQLException {
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		comando.executeUpdate("INSERT INTO "+Tablas.Articulo+" "
				+ "("+Tablas.IdArticulo+","+Tablas.CodigoSeccion+","+Tablas.NombreArticulo+","
				+ ""+Tablas.RutaImagen+","+Tablas.Descripcion+","+Tablas.Precio+","
				+ ""+Tablas.StockMaximo+","+Tablas.StockActual+","+Tablas.Tipo+") "
				+ "VALUES ('"+ar.getIdArticulo()+"','"+se.getCodigoSeccion()+"','"+ar.getNombreArticulo()+"',"
				+ "'"+ar.getRutaImagen()+"','"+ar.getDescripcion()+"','"+ar.getPrecio()+"','"+ar.getStockMaximo()+"',"
				+ "'"+ar.getStockActual()+"','"+ar.getPrecio()+"','"+ar.gettipo()+"')");
		if (ar instanceof Comida){
			Comida co=(Comida) ar;
			comando.executeUpdate("UPDATE "+Tablas.Articulo+" SET "+Tablas.FechaCaducidad+"='"+co.getFechaCaducidad()+"',"
					+ " "+Tablas.Procedencia+"='"+co.getProcedencia()+"' WHERE "+Tablas.IdArticulo+"='"+co.getIdArticulo()+"'");
		}else if(ar instanceof Herramienta) {
			Herramienta he=(Herramienta) ar;
			comando.executeUpdate("UPDATE "+Tablas.Articulo+" SET "+Tablas.Electrica+"='"+he.getElectrica()+"',"
					+ " "+Tablas.Garantia+"='"+he.getGarantia()+"' WHERE "+Tablas.IdArticulo+"='"+he.getIdArticulo()+"'");
		}else {
			 Ropa ro=(Ropa) ar;
			comando.executeUpdate("UPDATE "+Tablas.Articulo+" SET "+Tablas.Talla+"='"+ro.getTalla()+"',"
					+ " "+Tablas.Color+"='"+ro.getColor()+"', "+Tablas.Material+"='"+ro.getMaterial()+"',"
					+ " "+Tablas.Marca+"='"+ro.getMarca()+"' WHERE "+Tablas.IdArticulo+"='"+ro.getIdArticulo()+"'");
		}
	}
	public void cambiarPerfilCliente(Cliente cli) throws SQLException {
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		comando.executeUpdate("UPDATE "+Tablas.Cliente+" SET "+Tablas.DNI+"='"+cli.getDni()+"',"
				+ " "+Tablas.Nombre+"='"+cli.getNombre()+"', "+Tablas.Apellidos+"='"+cli.getApellidos()+"',"
				+ " "+Tablas.FechaNacimineto+"='"+cli.getFechaNacimiento()+"', "+Tablas.Email+"='"+cli.getEmail()+"',"
				+ " "+Tablas.Dinero+"='"+cli.getDinero()+"', "+Tablas.TarjetaCliente+"='"+pasarBoolean(cli.isTarjetaCliente())+"',"
				+ " "+Tablas.Contrasena+"='"+cli.getContrasena()+"', "+Tablas.bloqueado+"='"+pasarBoolean(cli.isBloqueado())+"'"
				+ " WHERE "+Tablas.DNI+"='"+cli.getDni()+"'");
		
		
	}
	public void cambiarPerfilJefe(Jefe je) throws SQLException {
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		comando.executeUpdate("UPDATE "+Tablas.Jefe+" SET "+Tablas.DNI+"='"+je.getDni()+"',"
				+ " "+Tablas.Nombre+"='"+je.getNombre()+"', "+Tablas.Apellidos+"='"+je.getApellidos()+"',"
				+ " "+Tablas.FechaNacimineto+"='"+je.getFechaNacimiento()+"', "+Tablas.Email+"='"+je.getEmail()+"',"
				+ " "+Tablas.Titulo+"='"+je.getTitulo()+"', "+Tablas.fechaAdquisicion+"='"+je.getFechaAdquisicion()+"',"
				+ " "+Tablas.Contrasena+"='"+je.getContrasena()+"', "+Tablas.porcentajeEmpresa+"='"+je.getPorcentajeEmpresa()+"'"
				+ " "+Tablas.dios+"="+pasarBoolean(je.isDios())+" WHERE "+Tablas.DNI+"='"+je.getDni()+"'");
		
	}
	public void cambiarSupermercado(Jefe je,Supermercado su) throws SQLException {
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		comando.executeUpdate("UPDATE "+Tablas.Supermercado+" SET "+Tablas.CodigoSuper+"='"+su.getCodigoSuper()+"',"
				+ " "+Tablas.Direccion+"='"+su.getDireccion()+"', "+Tablas.MetrosCuadrados+"='"+su.getMetrosCuadrados()+"',"
				+ " "+Tablas.NumeroEmpleados+"='"+su.getNumEmpleados()+"', "+Tablas.Horario+"='"+su.getHorario()+"',"
				+ " "+Tablas.DNIJEFE+"='"+je.getDni()+"' WHERE "+Tablas.CodigoSuper+"='"+su.getCodigoSuper()+"'");
	}
	public void cambiarSeccion(Supermercado su,Seccion se) throws SQLException {
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		comando.executeUpdate("UPDATE "+Tablas.Seccion+" SET "+Tablas.CodigoSuper+"='"+su.getCodigoSuper()+"',"
				+ " "+Tablas.CodigoSeccion+"='"+se.getCodigoSeccion()+"', "+Tablas.NombreSeccion+"='"+se.getNombreSeccion()+"',"
				+ " WHERE "+Tablas.CodigoSeccion+"='"+se.getCodigoSeccion()+"'");
	}
	public void cambiarCompra(Cliente cli, Compra com) throws SQLException {
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		comando.executeUpdate("UPDATE "+Tablas.Compras+" SET "+Tablas.DNI+"='"+cli.getDni()+"',"
				+ " "+Tablas.codigoCompra+"='"+com.getCodigoCompra()+"', "+Tablas.precioFinal+"='"+com.getPrecioTotal()+"',"
				+ " "+Tablas.fechaCompra+"='"+com.getFechaCompra()+"' WHERE "+Tablas.codigoCompra+"='"+com.getCodigoCompra()+"'");
	}
	public void cambiarArticuloComprado(ArticulosComprados arc) throws SQLException {
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		comando.executeUpdate("UPDATE "+Tablas.ArticulosComprados+" SET "+Tablas.codigoCompra+"='"+arc.getCodigoCompra()+"',"
				+ " "+Tablas.IdArticulo+"='"+arc.getIdArticulo()+"', "+Tablas.Cantidad+"='"+arc.getCantidad()+"',"
				+ " "+Tablas.PrecioArt+"='"+arc.getPrecioArt()+"' WHERE "+Tablas.codigoCompra+"='"+arc.getCodigoCompra()+"'"
				+ " AND "+Tablas.IdArticulo+"='"+arc.getIdArticulo()+"'");
	}
	public void cambiarArticulo(Seccion se, Articulo ar) throws SQLException {
		Ropa ro=null;
		Comida co=null;
		Herramienta he=null;
		Statement comando = (Statement) conectarBaseDatos().createStatement();
		comando.executeUpdate("UPDATE "+Tablas.Articulo+" SET "+Tablas.IdArticulo+"='"+ar.getIdArticulo()+"',"
				+ " "+Tablas.CodigoSeccion+"='"+se.getCodigoSeccion()+"', "+Tablas.NombreArticulo+"='"+ar.getNombreArticulo()+"',"
				+ " "+Tablas.RutaImagen+"='"+ar.getRutaImagen()+"', "+Tablas.Descripcion+"='"+ar.getDescripcion()+"',"
				+ " "+Tablas.Precio+"='"+ar.getPrecio()+"', "+Tablas.StockMaximo+"='"+ar.getStockMaximo()+"',"
				+ " "+Tablas.StockActual+"='"+ar.getStockActual()+"', "+Tablas.Tipo+"='"+ar.gettipo()+"'"
				+ " WHERE "+Tablas.IdArticulo+"='"+ar.getIdArticulo()+"'");
		if(ar instanceof Ropa) {
			ro=(Ropa) ar;
			comando.executeUpdate("UPDATE "+Tablas.Articulo+" SET "+Tablas.Talla+"='"+ro.getTalla()+"',"
					+ " "+Tablas.Color+"='"+ro.getColor()+"', "+Tablas.Material+"='"+ro.getMaterial()+"',"
					+ " "+Tablas.Material+"='"+ro.getMaterial()+"', "+Tablas.Marca+"='"+ro.getMarca()+"' "
					+ " WHERE "+Tablas.IdArticulo+"='"+ro.getIdArticulo()+"'");
		}else if( ar instanceof Herramienta) {
			he=(Herramienta) ar;
			comando.executeUpdate("UPDATE "+Tablas.Articulo+" SET "+Tablas.Electrica+"='"+he.getElectrica()+"',"
					+ " "+Tablas.Garantia+"='"+he.getGarantia()+" WHERE "+Tablas.IdArticulo+"='"+he.getIdArticulo()+"'");
		}else {
			co=(Comida) ar;
			comando.executeUpdate("UPDATE "+Tablas.Articulo+" SET "+Tablas.FechaCaducidad+"='"+co.getFechaCaducidad()+"',"
					+ " "+Tablas.Procedencia+"='"+co.getProcedencia()+" WHERE "+Tablas.IdArticulo+"='"+co.getIdArticulo()+"'");
		}
	}
	public void darseBajaCliente(Cliente cliente) throws SQLException {
		Statement comando;
			comando = (Statement) conectarBaseDatos().createStatement();
			comando.executeUpdate("DELETE FROM "+Tablas.Cliente+" WHERE "+Tablas.DNI+"='"+cliente.getDni()+"'");
	}
	public void darseBajaJefe(Jefe jefe) throws SQLException {
		Statement comando;
			comando = (Statement) conectarBaseDatos().createStatement();
			comando.executeUpdate("DELETE FROM "+Tablas.Jefe+" WHERE "+Tablas.DNI+"='"+jefe.getDni()+"'");
	}
	public void borrarSeccion(Seccion se) throws SQLException {
		Statement comando;
		comando = (Statement) conectarBaseDatos().createStatement();
		comando.executeUpdate("DELETE FROM "+Tablas.Seccion+" WHERE "+Tablas.CodigoSeccion+"='"+se.getCodigoSeccion()+"'");
	}
	public void borrarArticulo(Articulo ar) throws SQLException {
		Statement comando;
		comando = (Statement) conectarBaseDatos().createStatement();
		comando.executeUpdate("DELETE FROM "+Tablas.Articulo+" WHERE "+Tablas.IdArticulo+"='"+ar.getIdArticulo()+"'");
	}
	public void borrarCompra(Compra co) throws SQLException {
		Statement comando;
		comando = (Statement) conectarBaseDatos().createStatement();
		comando.executeUpdate("DELETE FROM "+Tablas.Compras+" WHERE "+Tablas.codigoCompra+"='"+co.getCodigoCompra()+"'");
	}
	public void borrarArticuloComprado(ArticulosComprados arc) throws SQLException {
		Statement comando;
		comando = (Statement) conectarBaseDatos().createStatement();
		comando.executeUpdate("DELETE FROM "+Tablas.ArticulosComprados+" WHERE "+Tablas.codigoCompra+"='"+arc.getCodigoCompra()+"'"
				+ " AND "+Tablas.IdArticulo+"='"+arc.getIdArticulo()+"'");
	}
	public void borrarSupermercado(Supermercado su) throws SQLException {
		Statement comando;
			comando = (Statement) conectarBaseDatos().createStatement();
			comando.executeUpdate("DELETE FROM "+Tablas.Supermercado+" WHERE "+Tablas.CodigoSuper+"='"+su.getCodigoSuper()+"'");
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
					cuenta.getString(Tablas.Contrasena),cuenta.getDouble(Tablas.Dinero),cuenta.getBoolean(Tablas.TarjetaCliente)
					,cuenta.getBoolean(Tablas.bloqueado));
			listaPersonas.add(comprador);
		}
		ResultSet cuentaJefe=comando.executeQuery("SELECT * FROM "+Tablas.Jefe);
		while(cuentaJefe.next()) {
		jefes=new Jefe(cuentaJefe.getString(Tablas.DNI), cuentaJefe.getString(Tablas.Nombre), 
				cuentaJefe.getString(Tablas.Apellidos), cuentaJefe.getDate(Tablas.FechaNacimineto),cuentaJefe.getString(Tablas.Email),
				cuentaJefe.getString(Tablas.Contrasena),cuentaJefe.getString(Tablas.Titulo),cuentaJefe.getDate(Tablas.fechaAdquisicion)
				,cuentaJefe.getFloat(Tablas.porcentajeEmpresa),cuentaJefe.getBoolean(Tablas.dios));
		listaPersonas.add(jefes);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return listaPersonas;
}
	public void guardarInventario(ArrayList<Articulo> lista) throws IOException {
			FileWriter fich = new FileWriter(".\\Inventario.txt");
			BufferedWriter buf=new BufferedWriter(fich);
			for(Articulo ar: lista) {
				buf.write(ar.toString());
			}
			buf.close();
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
	
	public void AumentarDineroCliente(Cliente cliente,int dinero) {
		Statement comando;
		try {
			comando = (Statement) conectarBaseDatos().createStatement();
			comando.executeUpdate("UPDATE "+Tablas.Cliente+" SET "+Tablas.Dinero+"='"+dinero+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void bloquearUsuario(Cliente cliente) {
		Statement comando;
		try {
			comando = (Statement) conectarBaseDatos().createStatement();
			comando.executeUpdate("UPDATE "+Tablas.Cliente+" SET "+Tablas.bloqueado+"='"+pasarBoolean(true)+"'");
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