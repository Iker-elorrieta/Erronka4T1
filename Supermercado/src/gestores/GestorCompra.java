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

/**El gestor de las compras.
 * @author Erlantz
 *
 */
public class GestorCompra {
	/**
	 * La lista que usara.
	 */
	private ArrayList<Compra> listaCompras;
	/**
	 * El constructor.
	 */
	public GestorCompra() {
		super();
		new ArrayList<Compra>();
	}
	/**Coger la lista del gestor.
	 * @return La lista.
	 */
	public ArrayList<Compra> getListaCompras() {
		return listaCompras;
	}
	/**Para dar una lista al gestor.
	 * @param listaCompras Lista da dar.
	 */
	public void setListaCompras(ArrayList<Compra> listaCompras) {
		this.listaCompras = listaCompras;
	}
	/**Para cargar las todas las compras de la base de datos.
	 * @param mc El metodos para cambiar el LocalDateTime.
	 * @param conexion La conexion.
	 * @return La lista de compras cargadas.
	 * @throws SQLException Fallo en la conexion.
	 * @throws ParseException Fallo al cambiar el dato.
	 */
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
	/**Insertar una compra a la base de datos.
	 * @param conexion La conexion.
	 * @param cli El cliente al que le pertenece la compra.
	 * @param com La compra a insertar.
	 * @throws SQLException Fallo en la conexion.
	 */
	public void insertarCompra(Connection conexion,Persona cli,Compra com) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO "+TABLAS.COMPRAS+" ("+TABLAS.DNI+","+TABLAS.PRECIOFINAL+","+TABLAS.FECHACOMPRA+")VALUES ('"+cli.getDni()+"'"
				+ ",'"+com.getPrecioTotal()+"','"+LocalDateTime.now()+"')");
		comando.close();
	}
	/**Para borrar una compra.
	 * @param conexion La conexion.
	 * @param co La compra a borrar.
	 * @throws SQLException Fallo en la conexion.
	 */
	public void borrarCompra(Connection conexion,Compra co) throws SQLException {
		Statement comando;
		comando = (Statement) conexion.createStatement();
		comando.executeUpdate("DELETE FROM "+TABLAS.COMPRAS+" WHERE "+TABLAS.CODIGOCOMPRA+"='"+co.getCodigoCompra()+"'");
		comando.close();
	}
	/**Para cambiar los datos de una compra.
	 * @param conexion La conexion.
	 * @param cli El cliente al que le pertenece la conexion.
	 * @param com La compra a cambiar.
	 * @throws SQLException
	 */
	public void cambiarCompra(Connection conexion,Cliente cli, Compra com) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("UPDATE "+TABLAS.COMPRAS+" SET "+TABLAS.DNI+"='"+cli.getDni()+"',"
				+ " "+TABLAS.CODIGOCOMPRA+"='"+com.getCodigoCompra()+"', "+TABLAS.PRECIOFINAL+"='"+com.getPrecioTotal()+"',"
				+ " "+TABLAS.FECHACOMPRA+"='"+com.getFechaCompra()+"' WHERE "+TABLAS.CODIGOCOMPRA+"='"+com.getCodigoCompra()+"'");
		comando.close();
	}
	/**Para coge la compra mas reciente.
	 * @param mc Para cargar los datos.
	 * @param conexion La conexion.
	 * @return La compra mas reciente.
	 * @throws SQLException Fallo en la conexion.
	 * @throws ParseException No pudo cambiar el formato del tiempo.
	 */
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
	/**Para buscar todas las compras de una persona.
	 * @param mc Para cmabiar el formato del tiempo.
	 * @param conexion La conexion.
	 * @param per La persona a buscar.
	 * @return La lista de compras.
	 * @throws SQLException Fallo en la conexion.
	 * @throws ParseException No se cambio el formato.
	 */
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
