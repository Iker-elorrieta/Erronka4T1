package gestores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controlador.Metodos;
import modelo.Articulo;
import modelo.ArticuloComprado;
import modelo.Compra;
import referencias.TABLAS;

public class GestorArticuloComprado {
	Metodos mc=new Metodos();
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
	public ArrayList<ArticuloComprado> cargarArticulosComprados() throws SQLException{
		ArrayList<ArticuloComprado> lista=new ArrayList<ArticuloComprado>();
		ArticuloComprado arc=null;
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULOSCOMPRADOS);
		while(carga.next()) {
		arc=new ArticuloComprado(carga.getInt(TABLAS.CODIGOCOMPRA),carga.getInt(TABLAS.IDARTICULO),
				carga.getInt(TABLAS.CANTIDAD),carga.getFloat(TABLAS.PRECIOART));
		lista.add(arc);
		}
		return lista;
	}
	public void insertarArticuloComprado(Compra com,ArticuloComprado arc) throws SQLException {
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULOSCOMPRADOS+" VALUES ('"+com.getCodigoCompra()+"',"
				+ "'"+arc.getIdArticulo()+"','"+arc.getCantidad()+"','"+arc.getPrecioArt()+"')");
	}
	public void cambiarArticuloComprado(ArticuloComprado arc) throws SQLException {
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		comando.executeUpdate("UPDATE "+TABLAS.ARTICULOSCOMPRADOS+" SET "+TABLAS.CODIGOCOMPRA+"='"+arc.getCodigoCompra()+"',"
				+ " "+TABLAS.IDARTICULO+"='"+arc.getIdArticulo()+"', "+TABLAS.CANTIDAD+"='"+arc.getCantidad()+"',"
				+ " "+TABLAS.PRECIOART+"='"+arc.getPrecioArt()+"' WHERE "+TABLAS.CODIGOCOMPRA+"='"+arc.getCodigoCompra()+"'"
				+ " AND "+TABLAS.IDARTICULO+"='"+arc.getIdArticulo()+"'");
	}
	public void borrarArticuloComprado(ArticuloComprado arc) throws SQLException {
		Statement comando;
		comando = (Statement) mc.conectarBaseDatos().createStatement();
		comando.executeUpdate("DELETE FROM "+TABLAS.ARTICULOSCOMPRADOS+" WHERE "+TABLAS.CODIGOCOMPRA+"='"+arc.getCodigoCompra()+"'"
				+ " AND "+TABLAS.IDARTICULO+"='"+arc.getIdArticulo()+"'");
	}
	public ArrayList<ArticuloComprado> cargarArticulosDeUnaCompra(Compra com) throws SQLException {
		ArrayList<ArticuloComprado> lista=new ArrayList<ArticuloComprado>();
		ArticuloComprado arc=null;
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULOSCOMPRADOS+" WHERE "+TABLAS.CODIGOCOMPRA+"='"+com.getCodigoCompra()+"'");
		while(carga.next()) {
		arc=new ArticuloComprado(carga.getInt(TABLAS.CODIGOCOMPRA),carga.getInt(TABLAS.IDARTICULO),carga.getInt(TABLAS.CANTIDAD),carga.getFloat(TABLAS.PRECIOART));
		lista.add(arc);
		}
		return lista;
	}
	public ArticuloComprado buscarArticuloComprado(Articulo ar) throws SQLException {
		ArrayList<ArticuloComprado> busca=cargarArticulosComprados();
		ArticuloComprado buscado=null;
		for(ArticuloComprado ac:busca) {
			if(ar.getIdArticulo()==ac.getIdArticulo()) {
				buscado=ac;
			}
		}
		return buscado;
	}
}
