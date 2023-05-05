package controlador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import modelo.Seccion;
import modelo.Supermercado;
import modelo.tipoArticulo;

public class GestorSeccion {
	Metodos mc=new Metodos();
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
	public ArrayList<Seccion> cargarSecciones() throws SQLException{
		ArrayList<Seccion> lista=new ArrayList<Seccion>();
		Seccion sec=null;
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SECCION);
		while(carga.next()) {
		sec=new Seccion(carga.getString(TABLAS.CODIGOSECCION),tipoArticulo.valueOf(carga.getString(TABLAS.TIPO)),null);
		lista.add(sec);
		}
		return lista;
	}
	public void insertarSeccion(Supermercado su,Seccion se) throws SQLException {
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		comando.executeUpdate("INSERT INTO "+TABLAS.SECCION+" VALUES ('"+su.getCodigoSuper()+"','"+se.getCodigoSeccion()+"','"+se.getNombreSeccion()+"')");
	}
	public void cambiarSeccion(Supermercado su,Seccion se) throws SQLException {
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		comando.executeUpdate("UPDATE "+TABLAS.SECCION+" SET "+TABLAS.CODIGOSUPER+"='"+su.getCodigoSuper()+"',"
				+ " "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"', "+TABLAS.TIPO+"='"+se.getNombreSeccion()+"'"
				+ " WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
	}
	public void borrarSeccion(Seccion se) throws SQLException {
		Statement comando;
		comando = (Statement) mc.conectarBaseDatos().createStatement();
		comando.executeUpdate("DELETE FROM "+TABLAS.SECCION+" WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
	}
	
}
