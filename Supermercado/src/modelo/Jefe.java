package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import com.mysql.jdbc.Statement;

import controlador.GestorCompra;
import controlador.Metodos;
import controlador.TABLAS;

public class Jefe extends Persona{
	Metodos mc=new Metodos();
	private Date fechaAdquisicion;
	private float porcentajeEmpresa;
	private boolean dios;
	private Supermercado supermercado;

	public Jefe(String dni, String nombre, String apellidos, Date fechaNacimiento, String email,String contrasena, tipoPersona tipo,Date fechaAdquisicion, float porcentajeEmpresa,int dios) {
		super(dni, nombre, apellidos, fechaNacimiento, email,contrasena,tipo);
		this.fechaAdquisicion=fechaAdquisicion;
		this.porcentajeEmpresa=porcentajeEmpresa;
		this.dios=mc.pasarIntABoolean(dios);
	}
	public Supermercado getSupermercado() {
		return supermercado;
	}
	public void setSupermercado(Supermercado supermercado) {
		this.supermercado = supermercado;
	}
	public boolean isDios() {
		return dios;
	}
	public void setDios(boolean dios) {
		this.dios = dios;
	}
	public String getFechaAdquisicion() {
		return mc.formatearFecha(fechaAdquisicion);
	}
	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}
	public float getPorcentajeEmpresa() {
		return porcentajeEmpresa;
	}
	public void setPorcentajeEmpresa(float porcentajeEmpresa) {
		this.porcentajeEmpresa = porcentajeEmpresa;
	}
	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(dni, other.dni);
	}
	@Override
	public String toString() {
		return "Jefe [dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos+ ", fechaNacimiento=" + fechaNacimiento + ", email=" + email + "]";
	}
	@Override
	public void comprarArticulos(Compra compra,ArrayList<ArticuloComprado> lista) throws SQLException, ParseException {
		// TODO Auto-generated method stub
		Metodos mc=new Metodos();
		GestorCompra gc=new GestorCompra();
		Compra codigo=null;
		Statement comando;
			comando = (Statement) mc.conectarBaseDatos().createStatement();
			comando.executeUpdate("INSERT INTO "+TABLAS.COMPRAS+" ("+TABLAS.DNI+","+TABLAS.PRECIOFINAL+","+TABLAS.FECHACOMPRA+")"
					+ "VALUES ('"+getDni()+"','"+compra.getPrecioTotal()+"','"+LocalDateTime.now()+"')");
			codigo=gc.buscarCompraReciente();
			for(ArticuloComprado ar:lista) {
			comando.executeUpdate("INSERT INTO "+TABLAS.ARTICULOSCOMPRADOS+" VALUES ('"+codigo.getCodigoCompra()+"','"+ar.getIdArticulo()+"','"+ar.getCantidad()+"','"+ar.getPrecioArt()+"')");
			comando.executeUpdate("UPDATE "+TABLAS.ARTICULO+" SET "+TABLAS.STOCK+"=("+TABLAS.STOCK+"-"+ar.getCantidad()+") WHERE "+TABLAS.IDARTICULO+"='"+ar.getIdArticulo()+"'");
			}
	}
	public void cancelarUltimaCompra() throws SQLException, ParseException {
		// TODO Auto-generated method stub
		Metodos mc=new Metodos();
		GestorCompra gc=new GestorCompra();
		Compra codigo=gc.buscarCompraReciente();
		codigo.setPrecioTotal(codigo.calcularPrecioTotal());
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		Statement segundo = (Statement) mc.conectarBaseDatos().createStatement();
		ResultSet ca=comando.executeQuery("SELECT "+TABLAS.CANTIDAD+","+TABLAS.IDARTICULO+" FROM "+TABLAS.ARTICULOSCOMPRADOS+" WHERE "+TABLAS.CODIGOCOMPRA+"='"+codigo.getCodigoCompra()+"'");
		while(ca.next()) {
			segundo.executeUpdate("UPDATE "+TABLAS.ARTICULO+" SET "+TABLAS.STOCK+"=("+TABLAS.STOCK+"+"+ca.getInt(TABLAS.CANTIDAD)+") WHERE "+TABLAS.IDARTICULO+"='"+ca.getString(TABLAS.IDARTICULO)+"'");
		}
		comando.executeUpdate("DELETE FROM "+TABLAS.COMPRAS+" WHERE "+TABLAS.DNI+"='"+getDni()+"' AND "+TABLAS.CODIGOCOMPRA+"='"+codigo.getCodigoCompra()+"'");
}
	public void devolverUnArticulo(ArticuloComprado arc,int numeroDevolver) throws SQLException {
		Metodos mc=new Metodos();
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		if(arc.getCantidad()==numeroDevolver) {
			comando.executeUpdate("DELETE FROM "+TABLAS.ARTICULOSCOMPRADOS+" WHERE "+TABLAS.IDARTICULO+"='"+arc.getIdArticulo()+"' AND "+TABLAS.CODIGOCOMPRA+"='"+arc.getCodigoCompra()+"'");
		}else{
			comando.executeUpdate("UPDATE "+TABLAS.ARTICULOSCOMPRADOS+" SET "+TABLAS.CANTIDAD+"=("+TABLAS.CANTIDAD+"-"+numeroDevolver+") WHERE "+TABLAS.IDARTICULO+"='"+arc.getIdArticulo()+"'"
					+ "AND  "+TABLAS.CODIGOCOMPRA+"='"+arc.getCodigoCompra()+"'");
		}
		comando.executeUpdate("UPDATE "+TABLAS.ARTICULO+" SET "+TABLAS.STOCK+"=("+TABLAS.STOCK+"+"+arc.getCantidad()+") WHERE "+TABLAS.IDARTICULO+"='"+arc.getIdArticulo()+"'");
	}
}
