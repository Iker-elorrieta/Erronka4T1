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

public class GestorArticuloComprado {
	private ArrayList<ArticuloComprado> listaArticulosComprados;

	public GestorArticuloComprado() {
		super();
		listaArticulosComprados=new ArrayList<ArticuloComprado>();
	}
	public ArrayList<ArticuloComprado> getListaArticulosComprados() {
		return listaArticulosComprados;
	}
	public void setListaArticulosComprados(ArrayList<ArticuloComprado> listaArticulosComprados) {
		this.listaArticulosComprados = listaArticulosComprados;
	}
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
	public void insertarArticuloComprado(Connection conexion,Compra com,ArticuloComprado arc) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULOSCOMPRADOS+" VALUES ('"+com.getCodigoCompra()+"',"
				+ "'"+arc.getIdArticulo()+"','"+arc.getCantidad()+"','"+arc.getPrecioArt()+"')");
	}
	public void cambiarArticuloComprado(Connection conexion,ArticuloComprado arc) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("UPDATE "+TABLAS.ARTICULOSCOMPRADOS+" SET "+TABLAS.CODIGOCOMPRA+"='"+arc.getCodigoCompra()+"',"
				+ " "+TABLAS.IDARTICULO+"='"+arc.getIdArticulo()+"', "+TABLAS.CANTIDAD+"='"+arc.getCantidad()+"',"
				+ " "+TABLAS.PRECIOART+"='"+arc.getPrecioArt()+"' WHERE "+TABLAS.CODIGOCOMPRA+"='"+arc.getCodigoCompra()+"'"
				+ " AND "+TABLAS.IDARTICULO+"='"+arc.getIdArticulo()+"'");
	}
	public void borrarArticuloComprado(Connection conexion,ArticuloComprado arc) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("DELETE FROM "+TABLAS.ARTICULOSCOMPRADOS+" WHERE "+TABLAS.CODIGOCOMPRA+"='"+arc.getCodigoCompra()+"'"
				+ " AND "+TABLAS.IDARTICULO+"='"+arc.getIdArticulo()+"'");
		comando.close();
	}
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
