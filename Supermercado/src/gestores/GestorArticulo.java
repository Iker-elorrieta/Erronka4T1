package gestores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controlador.Metodos;
import modelo.Articulo;
import modelo.Comida;
import modelo.Herramienta;
import modelo.Ropa;
import modelo.Seccion;
import referencias.TABLAS;


public class GestorArticulo {
	Metodos mc=new Metodos();
	private ArrayList<Articulo> listaArticulos;
	public GestorArticulo() {
		super();
		listaArticulos=new ArrayList<Articulo>();
	}
	public ArrayList<Articulo> getListaArticulos() {
		return listaArticulos;
	}
	public void setListaArticulos(ArrayList<Articulo> listaArticulos) {
		this.listaArticulos = listaArticulos;
	}
	public ArrayList<Articulo> cargarArticulos() throws SQLException{
		ArrayList<Articulo> lista=new ArrayList<Articulo>();
		Herramienta he=null;
		Comida co=null;
		Ropa ro=null;
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
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
		return lista;
		}
	public void insertarArticulo(Seccion se,Articulo ar) throws SQLException {
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
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
	}
	public void cambiarArticulo(Articulo ar) throws SQLException {
		Ropa ro=null;
		Comida co=null;
		Herramienta he=null;
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
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
	}
	public void borrarArticulo(Articulo ar) throws SQLException {
		Statement comando;
		comando = (Statement) mc.conectarBaseDatos().createStatement();
		comando.executeUpdate("DELETE FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.IDARTICULO+"='"+ar.getIdArticulo()+"'");
	}
	public Articulo buscarArticulo(Articulo ar) {
		Articulo a= null;
		for(Articulo ar1:listaArticulos) {
			if(ar1.getNombreArticulo().equals(ar.getNombreArticulo())) {
				a=ar1;
			}
		}
		return a;
	}
}

