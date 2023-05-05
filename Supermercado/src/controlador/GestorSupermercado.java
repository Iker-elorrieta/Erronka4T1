package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.Jefe;
import modelo.Supermercado;

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
}