package gestores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import excepciones.ErroresDeOperaciones;
import modelo.Jefe;
import modelo.Persona;
import modelo.Seccion;
import modelo.Supermercado;
import otros.tipoArticulo;
import referencias.TABLAS;

/**La clase que utilizaremos para realizar operaciones
 * en los supermercados.
 * @author Erlantz
 *
 */
public class GestorSupermercado {
	/**
	 * La lista de supermercados.
	 */
	private ArrayList<Supermercado> listaSupers;
	
	/**
	 * El constructor.
	 */
	public GestorSupermercado() {
		super();
		listaSupers = new ArrayList<Supermercado>();
	}
	/**Para coger la lista de supermercados.
	 * @return La lista.
	 */
	public ArrayList<Supermercado> getListaSupers() {
		return listaSupers;
	}

	/**Para cambiar la lista de supermercados.
	 * @param listaSupers La lista a copiar.
	 */
	public void setListaSupers(ArrayList<Supermercado> listaSupers) {
		this.listaSupers = listaSupers;
	}
	/**Para cargar todos los supermercados de la base de datos.
	 * @param conexion La conexion.
	 * @return La lista cargada.
	 * @throws SQLException Fallo en la conexion.
	 */
	public ArrayList<Supermercado> cargarSupermercados(Connection conexion) throws SQLException{
		ArrayList<Supermercado> lista=new ArrayList<Supermercado>();
		Supermercado su=null;
		Statement comando = (Statement) conexion.createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO);
		while(carga.next()) {
		su=new Supermercado(carga.getString(TABLAS.CODIGOSUPER),carga.getString(TABLAS.EMPRESA),carga.getString(TABLAS.DIRECCION),carga.getInt(TABLAS.NUMEROEMPLEADOS),null);
		lista.add(su);
		}
		comando.close();
		return lista;
	}
	/**Para insertar una clase supermercado en su tabal.
	 * @param conexion La conexion.
	 * @param jefe EL jefe al que pertenence.
	 * @param su El objeto supermercado a insertar.
	 * @throws SQLException Fallo en la conexion.
	 */
	public void insertarSupermercado(Connection conexion,Jefe jefe,Supermercado su) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO "+TABLAS.SUPERMERCADO+""
				+ " VALUES ('"+jefe.getDni()+"',"
				+ "'"+su.getCodigoSuper()+"',"
				+ "'"+su.getEmpresa()+"',"
				+ "'"+su.getDireccion()+"',"
				+ "'"+su.getNumEmpleados()+"')");
		comando.close();
	}
	/**Para cambiar un supermercado en la tabla.
	 * @param conexion La conexion.
	 * @param su El supermercado a cambiar en la base.
	 * @throws SQLException Fallo en la conexion.
	 */
	public void cambiarSupermercado(Connection conexion,Supermercado su) throws SQLException {
		Statement comando = (Statement) conexion.createStatement();
		comando.executeUpdate("UPDATE "+TABLAS.SUPERMERCADO+
				" SET "+TABLAS.DIRECCION+"='"+su.getDireccion()+"', "+TABLAS.EMPRESA+"='"+su.getEmpresa()+"',"
				+ " "+TABLAS.NUMEROEMPLEADOS+"='"+su.getNumEmpleados()+"' "
				+ " WHERE "+TABLAS.CODIGOSUPER+"='"+su.getCodigoSuper()+"'");
		comando.close();
	}
	/**Para borrar un objeto de supermercado de la base de datos.
	 * @param conexion La conexion.
	 * @param su El supermercado a borrar.
	 * @throws SQLException Fallo en la conexion.
	 */
	public void borrarSupermercado(Connection conexion,Supermercado su) throws SQLException {
		Statement comando;
			comando = (Statement) conexion.createStatement();
			comando.executeUpdate("DELETE FROM "+TABLAS.SUPERMERCADO+" WHERE "+TABLAS.CODIGOSUPER+"='"+su.getCodigoSuper()+"'");
			comando.close();
	}
	/**Buscar un supermercado en base a un Jefe
	 * @param conexion La conexion.
	 * @param je El jefe del que coger el dni
	 * @return El supermercado del jefe.
	 * @throws SQLException
	 */
	public Supermercado buscarSupermercado(Connection conexion,Jefe je) throws SQLException {
		Supermercado su=null;
		Statement comando;
		comando = (Statement) conexion.createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO+" WHERE "+TABLAS.DNIJEFE+"='"+je.getDni()+"'");
		while(carga.next()) {
			su=new Supermercado(carga.getString(TABLAS.CODIGOSUPER),carga.getString(TABLAS.EMPRESA),carga.getString(TABLAS.DIRECCION),carga.getInt(TABLAS.NUMEROEMPLEADOS),null);
		}
		cogerSeccionesSuper(conexion,su);
		comando.close();
		return su;
	}
	/**Para coger todos los supermercados que tienen un jefe.
	 * @param conexion La conexion.
	 * @param personas Las personas a filtrar.
	 * @return El array de supermercados a devolver.
	 * @throws SQLException Fallo en la conexion.
	 */
	public ArrayList<Supermercado> todoSupermercados(Connection conexion,ArrayList<Persona> personas) throws SQLException{
		ArrayList<Supermercado> todos=new ArrayList<Supermercado>();
		Jefe temporal=null;
		for(Persona per:personas) {
			if(per instanceof Jefe) {
				temporal=(Jefe) per;
				todos.add(buscarSupermercado(conexion,temporal));
			}
		}
		return todos;
	}
	/**Buscar los supermercados en base a la empresa.
	 * @param conexion La conexion.
	 * @param empresa La empresa a buscar.
	 * @return Los supermercados filtrados.
	 * @throws SQLException Fallo en la conexion.
	 * @throws ErroresDeOperaciones No se encontro el supermercado.
	 */
	public ArrayList<Supermercado> buscarSuperEmpresa(Connection conexion,String empresa) throws SQLException, ErroresDeOperaciones {
		ArrayList<Supermercado> lista=new ArrayList<Supermercado>();
		Supermercado su=null;
		Statement comando = (Statement) conexion.createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO+" WHERE "+TABLAS.EMPRESA+"='"+empresa+"'");
		while(carga.next()) {
			su=new Supermercado(carga.getString(TABLAS.CODIGOSUPER),carga.getString(TABLAS.EMPRESA),carga.getString(TABLAS.DIRECCION),carga.getInt(TABLAS.NUMEROEMPLEADOS),null);
			lista.add(su);
		}
		if(su==null) {
			throw new ErroresDeOperaciones("No se pudo encontrar el supermercado escogido");
		}else {
		su=cogerSeccionesSuper(conexion,su);
		}
		comando.close();
		return lista;
	}
	/**Para buscar un supermercado en base a la empresa y direccion.
	 * @param conexion La conexion.
	 * @param empresa La empresa a buscar.
	 * @param direccion Y su direccion.
	 * @return El supermercado buscado.
	 * @throws SQLException Fallo en la conexion.
	 * @throws ErroresDeOperaciones No se encontro.
	 */
	public Supermercado buscarSuperEmpresaDireccion(Connection conexion,String empresa,String direccion) throws SQLException, ErroresDeOperaciones {
		Supermercado su=null;
		Statement comando = (Statement) conexion.createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO+" WHERE "+TABLAS.EMPRESA+"='"+empresa+"' AND "+TABLAS.DIRECCION+"='"+direccion+"'");
		while(carga.next()) {
			su=new Supermercado(carga.getString(TABLAS.CODIGOSUPER),carga.getString(TABLAS.EMPRESA),carga.getString(TABLAS.DIRECCION),carga.getInt(TABLAS.NUMEROEMPLEADOS),null);
		}
		if(su==null) {
			throw new ErroresDeOperaciones("No se pudo encontrar el supermercado escogido");
		}else {
		su=cogerSeccionesSuper(conexion,su);
		comando.close();
		}
		return su;
	}
	/**Coger las secciones de un supermercado.
	 * @param conexion La conexion.
	 * @param su El supermercado a buscar.
	 * @return El supermercado cargado.
	 * @throws SQLException Fallo en la conexion.
	 */
	public Supermercado cogerSeccionesSuper(Connection conexion,Supermercado su) throws SQLException{
		ArrayList<Seccion> listaSe=new ArrayList<Seccion>();
		Seccion se=null;
		Statement comando = (Statement) conexion.createStatement();
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SECCION+" WHERE "+TABLAS.CODIGOSUPER+"='"+su.getCodigoSuper()+"'");
		while(carga.next()) {
			se=new Seccion(carga.getString(TABLAS.CODIGOSECCION), tipoArticulo.valueOf(carga.getString(TABLAS.TIPO)),carga.getInt(TABLAS.NUMAR), null);
			listaSe.add(se);
		}
		su.setArraySecciones(listaSe);
		comando.close();
		return su;
	}
	/** Para coger las secciones de muchos supermercados.
	 * @param conexion La conexion.
	 * @param lista LA lista a rellenar.
	 * @return Los supermercados cargados.
	 * @throws SQLException Fallo en la conexion.
	 */
	public ArrayList<Supermercado> cogerSeccionesMultiplesSu(Connection conexion,ArrayList<Supermercado> lista) throws SQLException{
		for(Supermercado su:lista) {
			su=cogerSeccionesSuper(conexion,su);
		}
		return lista;
	}
}
