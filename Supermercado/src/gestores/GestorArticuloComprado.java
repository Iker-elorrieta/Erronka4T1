package gestores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.Articulo;
import modelo.ArticuloComprado;
import modelo.Compra;
import referencias.TABLAS;

/**
 * El Gestor para realizar operaciones sobre 
 * los articulos comprados.
 * @author Erlantz
 *
 */
public class GestorArticuloComprado {
	/**
	 * La lista de articulos comprados.
	 */
	private ArrayList<ArticuloComprado> listaArticulosComprados;

	/**
	 * El constructor.
	 */
	public GestorArticuloComprado() {
		super();
		listaArticulosComprados=new ArrayList<ArticuloComprado>();
	}
	/**Para coger la lista del gestor.
	 * @return La lista.
	 */
	public ArrayList<ArticuloComprado> getListaArticulosComprados() {
		return listaArticulosComprados;
	}
	/**Para coger una lista de articulos comprados.
	 * @param listaArticulosComprados La lista a copiar.
	 */
	public void setListaArticulosComprados(ArrayList<ArticuloComprado> listaArticulosComprados) {
		this.listaArticulosComprados = listaArticulosComprados;
	}
	/**Para cargar todos los articulos comprados
	 * de la base de datos.
	 * @param conexion La conexion.
	 * @return La lista de articulos comprados cargada.
	 * @throws SQLException Fallo en la conexion.
	 */
	public ArrayList<ArticuloComprado> cargarArticulosComprados(Connection conexion) throws SQLException{
		ArrayList<ArticuloComprado> lista=new ArrayList<ArticuloComprado>();
		ArticuloComprado arc=null;
		Statement comando = (Statement) conexion.createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULOSCOMPRADOS);
		while(carga.next()) {
		arc=new ArticuloComprado(carga.getInt(TABLAS.CODIGOCOMPRA),carga.getInt(TABLAS.IDARTICULO),
				carga.getInt(TABLAS.CANTIDAD),carga.getFloat(TABLAS.PRECIOART));
		lista.add(arc);
		}
		comando.close();
		return lista;
	}
	/**Para insertar un articulo comprado en la BBDD.
	 * @param conexion La conexion.
	 * @param com La compra a la que pertenece
	 * @param arc El articulocomprado a insertar.
	 * @throws SQLException
	 */
	public void insertarArticuloComprado(Connection conexion,Compra com,ArticuloComprado arc) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULOSCOMPRADOS+" VALUES ('"+com.getCodigoCompra()+"',"
				+ "'"+arc.getIdArticulo()+"','"+arc.getCantidad()+"','"+arc.getPrecioArt()+"')");
	}
	/**Para cambiar los datos de un articulocomprado.
	 * @param conexion La conexion.
	 * @param arc El articulo comprado a cambiar.
	 * @throws SQLException Fallo en la conexiop.
	 */
	public void cambiarArticuloComprado(Connection conexion,ArticuloComprado arc) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("UPDATE "+TABLAS.ARTICULOSCOMPRADOS+" SET "+TABLAS.CODIGOCOMPRA+"='"+arc.getCodigoCompra()+"',"
				+ " "+TABLAS.IDARTICULO+"='"+arc.getIdArticulo()+"', "+TABLAS.CANTIDAD+"='"+arc.getCantidad()+"',"
				+ " "+TABLAS.PRECIOART+"='"+arc.getPrecioArt()+"' WHERE "+TABLAS.CODIGOCOMPRA+"='"+arc.getCodigoCompra()+"'"
				+ " AND "+TABLAS.IDARTICULO+"='"+arc.getIdArticulo()+"'");
	}
	/**Para borrar un articulo comprado.
	 * @param conexion La conexion.
	 * @param arc El articulo comprado a borrar.
	 * @throws SQLException La conexion.
	 */
	public void borrarArticuloComprado(Connection conexion,ArticuloComprado arc) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("DELETE FROM "+TABLAS.ARTICULOSCOMPRADOS+" WHERE "+TABLAS.CODIGOCOMPRA+"='"+arc.getCodigoCompra()+"'"
				+ " AND "+TABLAS.IDARTICULO+"='"+arc.getIdArticulo()+"'");
		comando.close();
	}
	/**Para cargar articulos de una compra.
	 * @param conexion La conexion.
	 * @param com La compra a mirar.
	 * @return Lista de los articulos comrpados.
	 * @throws SQLException Fallo en la conexion.
	 */
	public ArrayList<ArticuloComprado> cargarArticulosDeUnaCompra(Connection conexion,Compra com) throws SQLException {
		ArrayList<ArticuloComprado> lista=new ArrayList<ArticuloComprado>();
		ArticuloComprado arc=null;
		Statement comando = (Statement) conexion.createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULOSCOMPRADOS+" WHERE "+TABLAS.CODIGOCOMPRA+"='"+com.getCodigoCompra()+"'");
		while(carga.next()) {
		arc=new ArticuloComprado(carga.getInt(TABLAS.CODIGOCOMPRA),carga.getInt(TABLAS.IDARTICULO),carga.getInt(TABLAS.CANTIDAD),carga.getFloat(TABLAS.PRECIOART));
		lista.add(arc);
		}
		comando.close();
		return lista;
	}
	/**Buscar articulos comprados de la lista.
	 * @param conexion LA conexion.
	 * @param ar El articulo a comprobar.
	 * @return El articulo comprado.
	 * @throws SQLException Fallo en la conexion.
	 */
	public ArticuloComprado buscarArticuloComprado(Connection conexion,Articulo ar) throws SQLException {
		ArrayList<ArticuloComprado> busca=cargarArticulosComprados(conexion);
		ArticuloComprado buscado=null;
		for(ArticuloComprado ac:busca) {
			if(ar.getIdArticulo()==ac.getIdArticulo()) {
				buscado=ac;
			}
		}
		return buscado;
	}
	/**Para cargar articulos en base a un codigo.
	 * @param conexion La conexion.
	 * @param codCompra EL codigo de compra.
	 * @return La lista de articulos comprados.
	 * @throws SQLException Fallo en la conexion.
	 */
	public ArrayList<ArticuloComprado> cargarArticuloCompradoCod(Connection conexion,int codCompra) throws SQLException{
		ArrayList<ArticuloComprado> lista=new ArrayList<ArticuloComprado>();
		ArticuloComprado arc=null;
		Statement comando = (Statement) conexion.createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULOSCOMPRADOS+" WHERE "+TABLAS.CODIGOCOMPRA+"="+codCompra+"");
		while(carga.next()) {
		arc=new ArticuloComprado(carga.getInt(TABLAS.CODIGOCOMPRA),carga.getInt(TABLAS.IDARTICULO),
				carga.getInt(TABLAS.CANTIDAD),carga.getFloat(TABLAS.PRECIOART));
		lista.add(arc);
		}
		comando.close();
		return lista;
	}
}
