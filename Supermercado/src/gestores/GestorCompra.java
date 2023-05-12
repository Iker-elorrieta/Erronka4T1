package gestores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import controlador.Metodos;
import modelo.Cliente;
import modelo.Compra;
import modelo.Persona;
import referencias.TABLAS;

public class GestorCompra {
	private ArrayList<Compra> listaCompras;
	public GestorCompra() {
		super();
		new ArrayList<Compra>();
	}
	public ArrayList<Compra> getListaCompras() {
		return listaCompras;
	}
	public void setListaCompras(ArrayList<Compra> listaCompras) {
		this.listaCompras = listaCompras;
	}
	public ArrayList<Compra> cargarCompras(Metodos mc,Connection conexion) throws SQLException, ParseException{
		ArrayList<Compra> lista=new ArrayList<Compra>();
		Compra arc=null;
		Statement comando = (Statement) conexion.createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.COMPRAS);
		while(carga.next()) {
		arc=new Compra(carga.getInt(TABLAS.CODIGOCOMPRA),carga.getFloat(TABLAS.PRECIOFINAL),mc.deStringALocalDateTime(carga.getString(TABLAS.FECHACOMPRA)));
		lista.add(arc);
		}
		comando.close();
		return lista;
	}
	public void insertarCompra(Connection conexion,Persona cli,Compra com) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO "+TABLAS.COMPRAS+" ("+TABLAS.DNI+","+TABLAS.PRECIOFINAL+","+TABLAS.FECHACOMPRA+")VALUES ('"+cli.getDni()+"'"
				+ ",'"+com.getPrecioTotal()+"','"+LocalDateTime.now()+"')");
		comando.close();
	}
	public void borrarCompra(Connection conexion,Compra co) throws SQLException {
		Statement comando;
		comando = (Statement) conexion.createStatement();
		comando.executeUpdate("DELETE FROM "+TABLAS.COMPRAS+" WHERE "+TABLAS.CODIGOCOMPRA+"='"+co.getCodigoCompra()+"'");
		comando.close();
	}
	public void cambiarCompra(Connection conexion,Cliente cli, Compra com) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("UPDATE "+TABLAS.COMPRAS+" SET "+TABLAS.DNI+"='"+cli.getDni()+"',"
				+ " "+TABLAS.CODIGOCOMPRA+"='"+com.getCodigoCompra()+"', "+TABLAS.PRECIOFINAL+"='"+com.getPrecioTotal()+"',"
				+ " "+TABLAS.FECHACOMPRA+"='"+com.getFechaCompra()+"' WHERE "+TABLAS.CODIGOCOMPRA+"='"+com.getCodigoCompra()+"'");
		comando.close();
	}
	public Compra buscarCompraReciente(Metodos mc,Connection conexion) throws SQLException, ParseException {
		listaCompras=cargarCompras(mc, conexion);
		Compra nueva=null;
		LocalDateTime fechaMasTemprana = null;
		for(Compra c: listaCompras) {
			if (fechaMasTemprana == null || c.getFechaCompra().isBefore(fechaMasTemprana)) {
		        nueva=c;
		      }
		}
		return nueva;
	}
	public ArrayList<Compra> buscarComprasPersona(Metodos mc,Connection conexion,Persona per) throws SQLException, ParseException{
		ArrayList<Compra> lista=new ArrayList<Compra>();
		Compra arc=null;
		Statement comando = (Statement) conexion.createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.COMPRAS+" WHERE "+TABLAS.DNI+"='"+per.getDni()+"'");
		while(carga.next()) {
		arc=new Compra(carga.getInt(TABLAS.CODIGOCOMPRA),carga.getFloat(TABLAS.PRECIOFINAL),mc.deStringALocalDateTime(carga.getString(TABLAS.FECHACOMPRA)));
		lista.add(arc);
		}
		comando.close();
		return lista;
	}
}
