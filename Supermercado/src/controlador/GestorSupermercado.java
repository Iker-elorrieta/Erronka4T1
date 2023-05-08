package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.Jefe;
import modelo.Persona;
import modelo.Seccion;
import modelo.Supermercado;
import modelo.tipoArticulo;

public class GestorSupermercado {
	Metodos mc=new Metodos();
	private ArrayList<Supermercado> listaSupers;
	
	
	public GestorSupermercado() {
		super();
		listaSupers = new ArrayList<Supermercado>();
	}
	public ArrayList<Supermercado> getListaSupers() {
		return listaSupers;
	}

	public void setListaSupers(ArrayList<Supermercado> listaSupers) {
		this.listaSupers = listaSupers;
	}
	public ArrayList<Supermercado> cargarSupermercados() throws SQLException{
		ArrayList<Supermercado> lista=new ArrayList<Supermercado>();
		Supermercado su=null;
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO);
		while(carga.next()) {
		su=new Supermercado(carga.getString(TABLAS.CODIGOSUPER),carga.getString(TABLAS.EMPRESA),carga.getString(TABLAS.DIRECCION),carga.getInt(TABLAS.NUMEROEMPLEADOS),null);
		lista.add(su);
		}
		return lista;
	}
	public void insertarSupermercado(Jefe jefe,Supermercado su) throws SQLException {
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		comando.executeUpdate("INSERT INTO "+TABLAS.SUPERMERCADO+""
				+ " VALUES ('"+jefe.getDni()+"',"
				+ "'"+su.getCodigoSuper()+"',"
				+ "'"+su.getEmpresa()+"',"
				+ "'"+su.getDireccion()+"',"
				+ "'"+su.getNumEmpleados()+"')");
	}
	public void cambiarSupermercado(Jefe je,Supermercado su) throws SQLException {
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		comando.executeUpdate("UPDATE "+TABLAS.SUPERMERCADO+
				" SET "+TABLAS.CODIGOSUPER+"='"+su.getCodigoSuper()+"',"
				+ " "+TABLAS.DIRECCION+"='"+su.getDireccion()+"', "+TABLAS.EMPRESA+"='"+su.getEmpresa()+"',"
				+ " "+TABLAS.NUMEROEMPLEADOS+"='"+su.getNumEmpleados()+"',"
				+ " "+TABLAS.DNIJEFE+"='"+je.getDni()+"' WHERE "+TABLAS.CODIGOSUPER+"='"+su.getCodigoSuper()+"'");
	}
	public void borrarSupermercado(Supermercado su) throws SQLException {
		Statement comando;
			comando = (Statement) mc.conectarBaseDatos().createStatement();
			comando.executeUpdate("DELETE FROM "+TABLAS.SUPERMERCADO+" WHERE "+TABLAS.CODIGOSUPER+"='"+su.getCodigoSuper()+"'");
	}
	public Supermercado buscarSupermercado(Jefe je) throws SQLException {
		Supermercado su=null;
		Statement comando;
		comando = (Statement) mc.conectarBaseDatos().createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO+" WHERE "+TABLAS.DNIJEFE+"='"+je.getDni()+"'");
		while(carga.next()) {
			su=new Supermercado(carga.getString(TABLAS.CODIGOSUPER),carga.getString(TABLAS.EMPRESA),carga.getString(TABLAS.DIRECCION),carga.getInt(TABLAS.NUMEROEMPLEADOS),null);
		}
		cogerSeccionesSuper(su);
		return su;
	}
	public ArrayList<Supermercado> todoSupermercados(ArrayList<Persona> personas) throws SQLException{
		ArrayList<Supermercado> todos=new ArrayList<Supermercado>();
		Jefe temporal=null;
		for(Persona per:personas) {
			if(per instanceof Jefe) {
				temporal=(Jefe) per;
				todos.add(buscarSupermercado(temporal));
			}
		}
		return todos;
	}
	public ArrayList<Supermercado> buscarSuperEmpresa(String empresa) throws SQLException, ErroresDeOperaciones {
		ArrayList<Supermercado> lista=new ArrayList<Supermercado>();
		Supermercado su=null;
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO+" WHERE "+TABLAS.EMPRESA+"='"+empresa+"'");
		while(carga.next()) {
			su=new Supermercado(carga.getString(TABLAS.CODIGOSUPER),carga.getString(TABLAS.EMPRESA),carga.getString(TABLAS.DIRECCION),carga.getInt(TABLAS.NUMEROEMPLEADOS),null);
			lista.add(su);
		}
		if(su==null) {
			throw new ErroresDeOperaciones("No se pudo encontrar el supermercado escogido");
		}
		su=cogerSeccionesSuper(su);
		return lista;
	}
	public Supermercado buscarSuperEmpresaDireccion(String empresa,String direccion) throws SQLException, ErroresDeOperaciones {
		Supermercado su=null;
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO+" WHERE "+TABLAS.EMPRESA+"='"+empresa+"' AND "+TABLAS.DIRECCION+"='"+direccion+"'");
		while(carga.next()) {
			su=new Supermercado(carga.getString(TABLAS.CODIGOSUPER),carga.getString(TABLAS.EMPRESA),carga.getString(TABLAS.DIRECCION),carga.getInt(TABLAS.NUMEROEMPLEADOS),null);
		}
		if(su==null) {
			throw new ErroresDeOperaciones("No se pudo encontrar el supermercado escogido");
		}
		su=cogerSeccionesSuper(su);
		return su;
	}
	public Supermercado cogerSeccionesSuper(Supermercado su) throws SQLException{
		ArrayList<Seccion> listaSe=new ArrayList<Seccion>();
		Seccion se=null;
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SECCION+" WHERE "+TABLAS.CODIGOSUPER+"='"+su.getCodigoSuper()+"'");
		while(carga.next()) {
			se=new Seccion(carga.getString(TABLAS.CODIGOSECCION), tipoArticulo.valueOf(carga.getString(TABLAS.TIPO)),carga.getInt(TABLAS.NUMAR), null);
			listaSe.add(se);
		}
		su.setArraySecciones(listaSe);
		return su;
	}
}
