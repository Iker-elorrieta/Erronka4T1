package gestores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import excepciones.ErroresDeOperaciones;
import modelo.Jefe;
import modelo.Persona;
import modelo.Seccion;
import modelo.Supermercado;
import otros.tipoArticulo;
import referencias.TABLAS;

public class GestorSupermercado {
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
	public ArrayList<Supermercado> cargarSupermercados(Connection conexion) throws SQLException{
		ArrayList<Supermercado> lista=new ArrayList<Supermercado>();
		Supermercado su=null;
		Statement comando = (Statement) conexion.createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO);
		while(carga.next()) {
		su=new Supermercado(carga.getString(TABLAS.CODIGOSUPER),carga.getString(TABLAS.EMPRESA),carga.getString(TABLAS.DIRECCION),carga.getInt(TABLAS.NUMEROEMPLEADOS),null);
		lista.add(su);
		}
		comando.close();
		return lista;
	}
	public void insertarSupermercado(Connection conexion,Jefe jefe,Supermercado su) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO "+TABLAS.SUPERMERCADO+""
				+ " VALUES ('"+jefe.getDni()+"',"
				+ "'"+su.getCodigoSuper()+"',"
				+ "'"+su.getEmpresa()+"',"
				+ "'"+su.getDireccion()+"',"
				+ "'"+su.getNumEmpleados()+"')");
		comando.close();
	}
	public void cambiarSupermercado(Connection conexion,Supermercado su) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("UPDATE "+TABLAS.SUPERMERCADO+
				" SET "+TABLAS.DIRECCION+"='"+su.getDireccion()+"', "+TABLAS.EMPRESA+"='"+su.getEmpresa()+"',"
				+ " "+TABLAS.NUMEROEMPLEADOS+"='"+su.getNumEmpleados()+"' "
				+ " WHERE "+TABLAS.CODIGOSUPER+"='"+su.getCodigoSuper()+"'");
		comando.close();
	}
	public void borrarSupermercado(Connection conexion,Supermercado su) throws SQLException {
		Statement comando;
			comando = (Statement) conexion.createStatement();
			comando.executeUpdate("DELETE FROM "+TABLAS.SUPERMERCADO+" WHERE "+TABLAS.CODIGOSUPER+"='"+su.getCodigoSuper()+"'");
			comando.close();
	}
	public Supermercado buscarSupermercado(Connection conexion,Jefe je) throws SQLException {
		Supermercado su=null;
		Statement comando;
		comando = (Statement) conexion.createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO+" WHERE "+TABLAS.DNIJEFE+"='"+je.getDni()+"'");
		while(carga.next()) {
			su=new Supermercado(carga.getString(TABLAS.CODIGOSUPER),carga.getString(TABLAS.EMPRESA),carga.getString(TABLAS.DIRECCION),carga.getInt(TABLAS.NUMEROEMPLEADOS),null);
		}
		cogerSeccionesSuper(conexion,su);
		comando.close();
		return su;
	}
	public ArrayList<Supermercado> todoSupermercados(Connection conexion,ArrayList<Persona> personas) throws SQLException{
		ArrayList<Supermercado> todos=new ArrayList<Supermercado>();
		Jefe temporal=null;
		for(Persona per:personas) {
			if(per instanceof Jefe) {
				temporal=(Jefe) per;
				todos.add(buscarSupermercado(conexion,temporal));
			}
		}
		return todos;
	}
	public ArrayList<Supermercado> buscarSuperEmpresa(Connection conexion,String empresa) throws SQLException, ErroresDeOperaciones {
		ArrayList<Supermercado> lista=new ArrayList<Supermercado>();
		Supermercado su=null;
		Statement comando = (Statement) conexion.createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO+" WHERE "+TABLAS.EMPRESA+"='"+empresa+"'");
		while(carga.next()) {
			su=new Supermercado(carga.getString(TABLAS.CODIGOSUPER),carga.getString(TABLAS.EMPRESA),carga.getString(TABLAS.DIRECCION),carga.getInt(TABLAS.NUMEROEMPLEADOS),null);
			lista.add(su);
		}
		if(su==null) {
			throw new ErroresDeOperaciones("No se pudo encontrar el supermercado escogido");
		}else {
		su=cogerSeccionesSuper(conexion,su);
		}
		comando.close();
		return lista;
	}
	public Supermercado buscarSuperEmpresaDireccion(Connection conexion,String empresa,String direccion) throws SQLException, ErroresDeOperaciones {
		Supermercado su=null;
		Statement comando = (Statement) conexion.createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO+" WHERE "+TABLAS.EMPRESA+"='"+empresa+"' AND "+TABLAS.DIRECCION+"='"+direccion+"'");
		while(carga.next()) {
			su=new Supermercado(carga.getString(TABLAS.CODIGOSUPER),carga.getString(TABLAS.EMPRESA),carga.getString(TABLAS.DIRECCION),carga.getInt(TABLAS.NUMEROEMPLEADOS),null);
		}
		if(su==null) {
			throw new ErroresDeOperaciones("No se pudo encontrar el supermercado escogido");
		}else {
		su=cogerSeccionesSuper(conexion,su);
		comando.close();
		}
		return su;
	}
	public Supermercado cogerSeccionesSuper(Connection conexion,Supermercado su) throws SQLException{
		ArrayList<Seccion> listaSe=new ArrayList<Seccion>();
		Seccion se=null;
		Statement comando = (Statement) conexion.createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SECCION+" WHERE "+TABLAS.CODIGOSUPER+"='"+su.getCodigoSuper()+"'");
		while(carga.next()) {
			se=new Seccion(carga.getString(TABLAS.CODIGOSECCION), tipoArticulo.valueOf(carga.getString(TABLAS.TIPO)),carga.getInt(TABLAS.NUMAR), null);
			listaSe.add(se);
		}
		su.setArraySecciones(listaSe);
		comando.close();
		return su;
	}
	public ArrayList<Supermercado> cogerSeccionesMultiplesSu(Connection conexion,ArrayList<Supermercado> lista) throws SQLException{
		for(Supermercado su:lista) {
			su=cogerSeccionesSuper(conexion,su);
		}
		return lista;
	}
}
