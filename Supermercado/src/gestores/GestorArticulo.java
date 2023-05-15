package gestores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controlador.Metodos;
import excepciones.ErroresDeOperaciones;
import modelo.Articulo;
import modelo.Comida;
import modelo.Herramienta;
import modelo.Ropa;
import modelo.Seccion;
import otros.tipoArticulo;
import referencias.TABLAS;


/**
 * El gestor de articulos.
 * @author Erlantz
 *
 */
public class GestorArticulo {
	/**
	 * La lista de articulos.
	 */
	private ArrayList<Articulo> listaArticulos;
	
	/**
	 * El constructo.
	 */
	public GestorArticulo() {
		super();
		listaArticulos=new ArrayList<Articulo>();
	}
	/**Coger la lista de un gestor.
	 * @return La lista.
	 */
	public ArrayList<Articulo> getListaArticulos() {
		return listaArticulos;
	}
	/**Para dar una lista al gestor.
	 * @param listaArticulos La lista.
	 */
	public void setListaArticulos(ArrayList<Articulo> listaArticulos) {
		this.listaArticulos = listaArticulos;
	}
	/**Coger todos los articulos de la
	 * BBDD.
	 * @param conexion La conexion.
	 * @return La lista de articulos.
	 * @throws SQLException Fallo en la conexion.
	 */
	public ArrayList<Articulo> cargarArticulos(Connection conexion) throws SQLException{
		ArrayList<Articulo> lista=new ArrayList<Articulo>();
		Herramienta he=null;
		Comida co=null;
		Ropa ro=null;
		Statement comando = (Statement) conexion.createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.ELECTRICA+" OR "+TABLAS.GARANTIA+" IS NOT NULL");
		while(carga.next()) {
		he=new Herramienta(carga.getInt(TABLAS.IDARTICULO),carga.getString(TABLAS.NOMBREARTICULO),
				carga.getString(TABLAS.RUTAIMAGEN),carga.getString(TABLAS.DESCRIPCION),
				carga.getFloat(TABLAS.PRECIO),carga.getInt(TABLAS.STOCK),
				carga.getInt(TABLAS.ELECTRICA),carga.getInt(TABLAS.GARANTIA));
		lista.add(he);
		}
		ResultSet cargaC=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.FECHACADUCIDAD+" OR "+TABLAS.PROCEDENCIA+" IS NOT NULL");
		while(cargaC.next()) {
		co=new Comida(cargaC.getInt(TABLAS.IDARTICULO),cargaC.getString(TABLAS.NOMBREARTICULO),
				cargaC.getString(TABLAS.RUTAIMAGEN),cargaC.getString(TABLAS.DESCRIPCION),
				cargaC.getFloat(TABLAS.PRECIO),cargaC.getInt(TABLAS.STOCK),
				cargaC.getDate(TABLAS.FECHACADUCIDAD),cargaC.getString(TABLAS.PROCEDENCIA));
		lista.add(co);
		}
		ResultSet cargaR=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.TALLA+" OR "+TABLAS.MARCA+" IS NOT NULL");
		while(cargaR.next()) {
		ro=new Ropa(cargaR.getInt(TABLAS.IDARTICULO),cargaR.getString(TABLAS.NOMBREARTICULO),
				cargaR.getString(TABLAS.RUTAIMAGEN),cargaR.getString(TABLAS.DESCRIPCION),
				cargaR.getFloat(TABLAS.PRECIO),cargaR.getInt(TABLAS.STOCK),
				cargaR.getString(TABLAS.TALLA),cargaR.getString(TABLAS.MARCA));
		lista.add(ro);
		}
		comando.close();
		return lista;
		}
	/**
	 * Para insertar un articulo en la BBDD.
	 * @param mc Para cambiar el boolean.
	 * @param conexion La conexion.
	 * @param se La seccion del articulo.
	 * @param ar El articulo a insertar.
	 * @throws SQLException Fallo en la conexion.
	 * @throws ErroresDeOperaciones Errores propios.
	 */
	public void insertarArticulo(Metodos mc,Connection conexion,Seccion se,Articulo ar) throws SQLException, ErroresDeOperaciones {
		if(ar.getNombreArticulo().length()>50 || ar.getRutaImagen().length()>100) {
			throw new ErroresDeOperaciones("El nombre o la ruta son demasiado largas");
		}
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULO+" "
				+ "("+TABLAS.CODIGOSECCION+","+TABLAS.NOMBREARTICULO+","
				+ ""+TABLAS.RUTAIMAGEN+","+TABLAS.DESCRIPCION+","+TABLAS.PRECIO+","
				+ ""+TABLAS.STOCK+") "
				+ "VALUES ('"+se.getCodigoSeccion()+"','"+ar.getNombreArticulo()+"',"
				+ "'"+ar.getRutaImagen()+"','"+ar.getDescripcion()+"','"+ar.getPrecio()+"',"
				+ "'"+ar.getStockActual()+"')");
		if (ar instanceof Comida){
			Comida co=(Comida) ar;
			comando.executeUpdate("UPDATE "+TABLAS.ARTICULO+" SET "+TABLAS.FECHACADUCIDAD+"='"+co.getFechaCaducidad()+"',"
					+ " "+TABLAS.PROCEDENCIA+"='"+co.getProcedencia()+"' WHERE "+TABLAS.NOMBREARTICULO+"='"+co.getNombreArticulo()+"'"
					+ " AND "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
			comando.executeUpdate("UPDATE "+TABLAS.SECCION+" SET "+TABLAS.NUMAR+"=("+TABLAS.NUMAR+"+"+1+") WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
		}else if(ar instanceof Herramienta) {
			Herramienta he=(Herramienta) ar;
			comando.executeUpdate("UPDATE "+TABLAS.ARTICULO+" SET "+TABLAS.ELECTRICA+"='"+mc.pasarBoolean(he.getElectrica())+"',"
					+ " "+TABLAS.GARANTIA+"='"+he.getGarantia()+"' WHERE "+TABLAS.NOMBREARTICULO+"='"+he.getNombreArticulo()+"'"
					+ "AND "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
			comando.executeUpdate("UPDATE "+TABLAS.SECCION+" SET "+TABLAS.NUMAR+"=("+TABLAS.NUMAR+"+"+1+") WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
		}else {
			 Ropa ro=(Ropa) ar;
			comando.executeUpdate("UPDATE "+TABLAS.ARTICULO+" SET "+TABLAS.TALLA+"='"+ro.getTalla()+"',"
					+ " "+TABLAS.MARCA+"='"+ro.getMarca()+"' WHERE "+TABLAS.NOMBREARTICULO+"='"+ro.getNombreArticulo()+"'"
					+ "AND "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
			comando.executeUpdate("UPDATE "+TABLAS.SECCION+" SET "+TABLAS.NUMAR+"=("+TABLAS.NUMAR+"+"+1+") WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
		}
		comando.close();
	}
	/**Para cambiar un articulo.
	 * @param mc Cambia el int a boolean.
	 * @param conexion La conexion.
	 * @param ar El articulo a cambiar.
	 * @throws SQLException Fallo en la conexion.
	 * @throws ErroresDeOperaciones 
	 */
	public void cambiarArticulo(Metodos mc,Connection conexion,Articulo ar) throws SQLException, ErroresDeOperaciones {
		if(ar.getNombreArticulo().length()>50 || ar.getRutaImagen().length()>100) {
			throw new ErroresDeOperaciones("El nombre o la ruta son demasiado largas");
		}
		Ropa ro=null;
		Comida co=null;
		Herramienta he=null;
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("UPDATE "+TABLAS.ARTICULO+" SET "
				+ " "+TABLAS.NOMBREARTICULO+"='"+ar.getNombreArticulo()+"',"
				+ " "+TABLAS.RUTAIMAGEN+"='"+ar.getRutaImagen()+"', "+TABLAS.DESCRIPCION+"='"+ar.getDescripcion()+"',"
				+ " "+TABLAS.PRECIO+"='"+ar.getPrecio()+"', "+TABLAS.STOCK+"='"+ar.getStockActual()+"'"
				+ " WHERE "+TABLAS.IDARTICULO+"="+ar.getIdArticulo());
		if(ar instanceof Ropa) {
			ro=(Ropa) ar;
			comando.executeUpdate("UPDATE "+TABLAS.ARTICULO+" SET "+TABLAS.TALLA+"='"+ro.getTalla()+"', "+TABLAS.MARCA+"='"+ro.getMarca()+"' "
					+ " WHERE "+TABLAS.IDARTICULO+"="+ar.getIdArticulo());
		}else if( ar instanceof Herramienta) {
			he=(Herramienta) ar;
			comando.executeUpdate("UPDATE "+TABLAS.ARTICULO+" SET "+TABLAS.ELECTRICA+"="+mc.pasarBoolean(he.getElectrica())+","
					+ " "+TABLAS.GARANTIA+"="+he.getGarantia()+"  WHERE "+TABLAS.IDARTICULO+"="+ar.getIdArticulo());
		}else {
			co=(Comida) ar;
			comando.executeUpdate("UPDATE "+TABLAS.ARTICULO+" SET "+TABLAS.FECHACADUCIDAD+"='"+co.getFechaCaducidad()+"',"
					+ " "+TABLAS.PROCEDENCIA+"='"+co.getProcedencia()+"' WHERE "+TABLAS.IDARTICULO+"="+ar.getIdArticulo());
		}
		comando.close();
	}
	/**Borrar el articulo de la BBDD.
	 * @param conexion La conexion.
	 * @param ar El articulo.
	 * @throws SQLException Fallo en la conexion.
	 */
	public void borrarArticulo(Connection conexion,Articulo ar) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("DELETE FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.IDARTICULO+"='"+ar.getIdArticulo()+"'");
		comando.close();
	}
	/**Para cargar los articulos de una seccion.
	 * @param conexion La conexion.
	 * @param se La seccion a comprobar.
	 * @return Los articulos de la seccion.
	 * @throws SQLException Fallo en la conexion.
	 */
	public ArrayList<Articulo> cargarArticulosPorSeccion(Connection conexion,Seccion se) throws SQLException{
		ArrayList<Articulo> lista=new ArrayList<Articulo>();
		Herramienta he=null;
		Comida co=null;
		Ropa ro=null;
		Statement comando = (Statement) conexion.createStatement();
		if(se.getNombreSeccion().equals(tipoArticulo.Herramienta)) {
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
		while(carga.next()) {
		he=new Herramienta(carga.getInt(TABLAS.IDARTICULO),carga.getString(TABLAS.NOMBREARTICULO),
				carga.getString(TABLAS.RUTAIMAGEN),carga.getString(TABLAS.DESCRIPCION),
				carga.getFloat(TABLAS.PRECIO),carga.getInt(TABLAS.STOCK),
				carga.getInt(TABLAS.ELECTRICA),carga.getInt(TABLAS.GARANTIA));
		lista.add(he);
			}
		}
		if(se.getNombreSeccion().equals(tipoArticulo.Comida)) {
		ResultSet cargaC=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
		while(cargaC.next()) {
		co=new Comida(cargaC.getInt(TABLAS.IDARTICULO),cargaC.getString(TABLAS.NOMBREARTICULO),
				cargaC.getString(TABLAS.RUTAIMAGEN),cargaC.getString(TABLAS.DESCRIPCION),
				cargaC.getFloat(TABLAS.PRECIO),cargaC.getInt(TABLAS.STOCK),
				cargaC.getDate(TABLAS.FECHACADUCIDAD),cargaC.getString(TABLAS.PROCEDENCIA));
		lista.add(co);
			}
		}
		if(se.getNombreSeccion().equals(tipoArticulo.Ropa)) {
		ResultSet cargaR=comando.executeQuery("SELECT * FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.CODIGOSECCION+"='"+se.getCodigoSeccion()+"'");
		while(cargaR.next()) {
		ro=new Ropa(cargaR.getInt(TABLAS.IDARTICULO),cargaR.getString(TABLAS.NOMBREARTICULO),
				cargaR.getString(TABLAS.RUTAIMAGEN),cargaR.getString(TABLAS.DESCRIPCION),
				cargaR.getFloat(TABLAS.PRECIO),cargaR.getInt(TABLAS.STOCK),
				cargaR.getString(TABLAS.TALLA),cargaR.getString(TABLAS.MARCA));
		lista.add(ro);
			}
		}
		comando.close();
		return lista;
	}
	/**Para buscar los nombres de un articulo
	 * con una lista.
	 * @param busqueda Lo que se quiere buscar.
	 * @param listaArticulos Los articulos.
	 * @return La lista de articulo.
	 * @throws SQLException Fallo en la conexion.
	 */
	public ArrayList<Articulo> buscarArticulosPorNombre(String busqueda,ArrayList<Articulo> listaArticulos) throws SQLException{
		ArrayList<Articulo> lista=new ArrayList<Articulo>();
		for(Articulo ar:listaArticulos) {
			if(ar.getNombreArticulo().contains(busqueda)) {
				lista.add(ar);
			}
		}
		return lista;
	}
	/**Para ver si debe de anadir un artciulo nuevo
	 * al carrito.
	 * @param articuloNuevo El articulo a comprobar.
	 * @param listaCarrito Los articulos del carrito.
	 */
	public void diferenciarCarrito(Articulo articuloNuevo, ArrayList<Articulo> listaCarrito){
		boolean repetido=false;
		for(Articulo ar:listaCarrito) {
			if(articuloNuevo.getIdArticulo()==ar.getIdArticulo()) {
				repetido=true;
			}
		}
		if(!repetido) {
			listaCarrito.add(articuloNuevo);
		}
	}
	/**Coger un articulo de la lista.
	 * @param ar El articulo a revisar.
	 * @param lista La list de articulos.
	 * @return El articulo buscado.
	 */
	public Articulo buscarArticulo(Articulo ar,ArrayList<Articulo> lista) {
		Articulo a= ar;
		for(Articulo ar1:lista) {
			if(ar1.getNombreArticulo().equals(ar.getNombreArticulo())) {
				a=ar1;	
			}
		}
		return a;
	}
	
}

