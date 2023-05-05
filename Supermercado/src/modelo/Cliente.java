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


public class Cliente extends Persona {
	Metodos mc=new Metodos();
	private float dinero;
	private boolean bloqueado;
	private ArrayList<Compra> arrayCompras;
	
	public Cliente(String dni, String nombre, String apellidos, Date fechaNacimiento, String email,String contrasena,tipoPersona tipo, float dinero,int bloqueado) {
		super(dni, nombre, apellidos, fechaNacimiento, email, contrasena,tipo);
		this.dinero = dinero;
		this.bloqueado=mc.pasarIntABoolean(bloqueado);
		arrayCompras=new ArrayList<Compra>();
	}
	@Override
	public String toString() {
		return "Cliente [dinero=" + dinero + ", dni=" + dni + ", nombre="
				+ nombre + ", apellidos=" + apellidos + ", fechaNacimiento=" + fechaNacimiento + ", email=" + email
				+ "]";
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
	
	public float getDinero() {
		return dinero;
	}
	public ArrayList<Compra> getArrayCompras() {
		return arrayCompras;
	}
	public void setDinero(float dinero) {
		this.dinero = dinero;
	}
	public void setArrayCompras(ArrayList<Compra> arrayCompras) {
		this.arrayCompras = arrayCompras;
	}
	public boolean isBloqueado() {
		return bloqueado;
	}
	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
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
			comando.executeUpdate("UPDATE "+TABLAS.PERSONAS+" SET "+TABLAS.DINERO+"=("+TABLAS.DINERO+"-"+compra.calcularPrecioTotal()+") WHERE "+TABLAS.DNI+"='"+getDni()+"'");
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
		comando.executeUpdate("UPDATE "+TABLAS.PERSONAS+" SET "+TABLAS.DINERO+"=("+TABLAS.DINERO+"+"+codigo.getPrecioTotal()+") WHERE "+TABLAS.DNI+"='"+getDni()+"'");
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
		comando.executeUpdate("UPDATE "+TABLAS.PERSONAS+" SET "+TABLAS.DINERO+"=("+TABLAS.DINERO+"+"+(arc.getPrecioArt()*numeroDevolver)+") WHERE "+TABLAS.DNI+"='"+getDni()+"'");
	}
	
}
