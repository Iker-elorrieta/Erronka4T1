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

/**Para realizar operaciones de las secciones.
 * @author Erlantz
 *
 */
public class GestorSeccion {
	/**
	 * La lista de secciones.
	 */
	private ArrayList<Seccion> listaSecciones;
	
	/**
	 * El constructor.
	 */
	public GestorSeccion() {
		super();
		listaSecciones=new ArrayList<Seccion>();
	}
	/**Para coger la lista del gestor.
	 * @return La lista.
	 */
	public ArrayList<Seccion> getListaSecciones() {
		return listaSecciones;
	}
	/**Para dar datos a la lista.
	 * @param listaSecciones
	 */
	public void setListaSecciones(ArrayList<Seccion> listaSecciones) {
		this.listaSecciones = listaSecciones;
	}
	/**Para cargar todas las secciones de la BBDD.
	 * @param conexion La conexion.
	 * @return La lista de seciones cargada.
	 * @throws SQLException Fallo en la conexion
	 */
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
	/**Para insertar un objeto de seccion a la BBDD.
	 * @param conexion La conexion.
	 * @param su El supermercado al que pertenecen las secciones.
	 * @param se La seccion a insertar.
	 * @throws SQLException Fallo en la conexion.
	 */
	public void insertarSeccion(Connection conexion,Supermercado su,Seccion se) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO "+TABLAS.SECCION+" VALUES ('"+su.getCodigoSuper()+"','"+se.getCodigoSeccion()+"','"+se.getNombreSeccion()+"','"+se.getNumArticulo()+"')");
		comando.close();
	}
	/**Para cambiar un seccion en la BBDD.
	 * @param conexion La conexion.
	 * @param se La seccion cambiada.
	 * @throws SQLException Fallo en la conexion.
	 */
	public void cambiarSeccion(Connection conexion,Seccion se) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("UPDATE "+TABLAS.SECCION+" SET "
				+ " "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"', "+TABLAS.TIPO+"='"+se.getNombreSeccion()+"',"
				+ " "+TABLAS.NUMAR+"='"+se.getNumArticulo()+"'"
				+ " WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
		comando.close();
	}
	/**Para borrar una seccion de la BBDD.
	 * @param conexion La conexion.
	 * @param se La seccion a borrar.
	 * @throws SQLException Fallo en la conexion.
	 */
	public void borrarSeccion(Connection conexion,Seccion se) throws SQLException {
		Statement comando;
		comando = (Statement) conexion.createStatement();
		comando.executeUpdate("DELETE FROM "+TABLAS.SECCION+" WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
		comando.close();
	}
}
