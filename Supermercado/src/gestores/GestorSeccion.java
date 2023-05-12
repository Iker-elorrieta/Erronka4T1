package gestores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.Seccion;
import modelo.Supermercado;
import otros.tipoArticulo;
import referencias.TABLAS;

public class GestorSeccion {
	private ArrayList<Seccion> listaSecciones;
	
	public GestorSeccion() {
		super();
		listaSecciones=new ArrayList<Seccion>();
	}
	public ArrayList<Seccion> getListaSecciones() {
		return listaSecciones;
	}
	public void setListaSecciones(ArrayList<Seccion> listaSecciones) {
		this.listaSecciones = listaSecciones;
	}
	public ArrayList<Seccion> cargarSecciones(Connection conexion) throws SQLException{
		ArrayList<Seccion> lista=new ArrayList<Seccion>();
		Seccion sec=null;
		Statement comando = (Statement) conexion.createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SECCION);
		while(carga.next()) {
		sec=new Seccion(carga.getString(TABLAS.CODIGOSECCION),tipoArticulo.valueOf(carga.getString(TABLAS.TIPO)),carga.getInt(TABLAS.NUMAR),null);
		lista.add(sec);
		}
		comando.close();
		return lista;
	}
	public void insertarSeccion(Connection conexion,Supermercado su,Seccion se) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO "+TABLAS.SECCION+" VALUES ('"+su.getCodigoSuper()+"','"+se.getCodigoSeccion()+"','"+se.getNombreSeccion()+"','"+se.getNumArticulo()+"')");
		comando.close();
	}
	public void cambiarSeccion(Connection conexion,Seccion se) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("UPDATE "+TABLAS.SECCION+" SET "
				+ " "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"', "+TABLAS.TIPO+"='"+se.getNombreSeccion()+"',"
				+ " "+TABLAS.NUMAR+"='"+se.getNumArticulo()+"'"
				+ " WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
		comando.close();
	}
	public void borrarSeccion(Connection conexion,Seccion se) throws SQLException {
		Statement comando;
		comando = (Statement) conexion.createStatement();
		comando.executeUpdate("DELETE FROM "+TABLAS.SECCION+" WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
		comando.close();
	}
}
