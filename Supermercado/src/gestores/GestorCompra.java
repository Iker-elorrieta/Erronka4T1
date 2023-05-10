package gestores;

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
	Metodos mc=new Metodos();
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
	public ArrayList<Compra> cargarCompras() throws SQLException, ParseException{
		ArrayList<Compra> lista=new ArrayList<Compra>();
		Compra arc=null;
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.COMPRAS);
		while(carga.next()) {
		arc=new Compra(carga.getInt(TABLAS.CODIGOCOMPRA),carga.getFloat(TABLAS.PRECIOFINAL),mc.deStringALocalDateTime(carga.getString(TABLAS.FECHACOMPRA)));
		lista.add(arc);
		}
		return lista;
	}
	public void insertarCompra(Persona cli,Compra com) throws SQLException {
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		comando.executeUpdate("INSERT INTO "+TABLAS.COMPRAS+" ("+TABLAS.DNI+","+TABLAS.PRECIOFINAL+","+TABLAS.FECHACOMPRA+")VALUES ('"+cli.getDni()+"'"
				+ ",'"+com.getPrecioTotal()+"','"+LocalDateTime.now()+"')");
	}
	public void borrarCompra(Compra co) throws SQLException {
		Statement comando;
		comando = (Statement) mc.conectarBaseDatos().createStatement();
		comando.executeUpdate("DELETE FROM "+TABLAS.COMPRAS+" WHERE "+TABLAS.CODIGOCOMPRA+"='"+co.getCodigoCompra()+"'");
	}
	public void cambiarCompra(Cliente cli, Compra com) throws SQLException {
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		comando.executeUpdate("UPDATE "+TABLAS.COMPRAS+" SET "+TABLAS.DNI+"='"+cli.getDni()+"',"
				+ " "+TABLAS.CODIGOCOMPRA+"='"+com.getCodigoCompra()+"', "+TABLAS.PRECIOFINAL+"='"+com.getPrecioTotal()+"',"
				+ " "+TABLAS.FECHACOMPRA+"='"+com.getFechaCompra()+"' WHERE "+TABLAS.CODIGOCOMPRA+"='"+com.getCodigoCompra()+"'");
	}
	public Compra buscarCompraReciente() throws SQLException, ParseException {
		listaCompras=cargarCompras();
		Compra nueva=null;
		LocalDateTime fechaMasTemprana = null;
		for(Compra c: listaCompras) {
			if (fechaMasTemprana == null || c.getFechaCompra().isBefore(fechaMasTemprana)) {
		        nueva=c;
		      }
		}
		return nueva;
	}
}
