package Modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import com.mysql.jdbc.Statement;

import Controlador.Metodos;
import Controlador.Tablas;


public class Cliente extends Persona {
	
	private double dinero;
	private boolean tarjetaCliente;
	private ArrayList<Compra> arrayCompras;
	private boolean bloqueado;
	
	public Cliente(String dni, String nombre, String apellidos, Date fechaNacimiento, String email,String contrasena, double dinero,
			boolean tarjetaCliente, boolean bloqueado) {
		super(dni, nombre, apellidos, fechaNacimiento, email, contrasena);
		this.dinero = dinero;
		this.tarjetaCliente = tarjetaCliente;
		this.bloqueado=bloqueado;
		arrayCompras=new ArrayList<Compra>();
	}
	@Override
	public String toString() {
		return "Cliente [dinero=" + dinero + ", tarjetaCliente=" + tarjetaCliente + ", dni=" + dni + ", nombre="
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
	
	public double getDinero() {
		return dinero;
	}
	public boolean isTarjetaCliente() {
		return tarjetaCliente;
	}
	public ArrayList<Compra> getArrayCompras() {
		return arrayCompras;
	}
	public void setDinero(double dinero) {
		this.dinero = dinero;
	}
	public void setTarjetaCliente(boolean tarjetaCliente) {
		this.tarjetaCliente = tarjetaCliente;
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
	public void comprarArticulos(Compra compra,ArrayList<ArticulosComprados> lista) throws SQLException {
		// TODO Auto-generated method stub
		Metodos mc=new Metodos();
		Calendar cal=Calendar.getInstance();
		String fecha=cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.DAY_OF_MONTH);
		Statement comando;
		int codC=-1;
			comando = (Statement) mc.conectarBaseDatos().createStatement();
			comando.executeUpdate("INSERT INTO "+Tablas.Compras+" ("+Tablas.DNI+","+Tablas.precioFinal+","+Tablas.fechaCompra+")"
					+ "VALUES ('"+getDni()+"','"+compra.getPrecioTotal()+"','"+fecha+"')");
			ResultSet codCompra=comando.executeQuery("SELECT "+Tablas.codigoCompra+" FROM "+Tablas.Compras+" WHERE "+Tablas.DNI+"='"+dni+"' "
					+ "ORDER BY "+Tablas.codigoCompra+" DESC LIMIT 1");
			while(codCompra.next()) {
				codC=codCompra.getInt(Tablas.codigoCompra);
			}
			for(ArticulosComprados ar:lista) {
			comando.executeUpdate("INSERT INTO "+Tablas.ArticulosComprados+" VALUES ('"+codC+"','"+ar.getIdArticulo()+"','"+ar.getCantidad()+"','"+ar.getPrecioArt()+"')");
			comando.executeUpdate("UPDATE "+Tablas.Articulo+" SET "+Tablas.StockActual+"=("+Tablas.StockActual+"-"+ar.getCantidad()+") WHERE "+Tablas.IdArticulo+"='"+ar.getIdArticulo()+"'");
			}
			comando.executeUpdate("UPDATE "+Tablas.Cliente+" SET "+Tablas.Dinero+"=("+Tablas.Dinero+"-"+compra.getPrecioTotal()+") WHERE "+Tablas.DNI+"='"+getDni()+"'");
	}
	public void cancelarCompra(Compra com,ArrayList<ArticulosComprados> lista) throws SQLException {
		// TODO Auto-generated method stub
		Metodos mc=new Metodos();
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		int codigoCompra=-1;
		for(ArticulosComprados arc:lista) {
		comando.executeUpdate("UPDATE "+Tablas.Articulo+" SET "+Tablas.StockActual+"=("+Tablas.StockActual+"+"+arc.getCantidad()+") WHERE "+Tablas.IdArticulo+"='"+arc.getIdArticulo()+"'");
		codigoCompra=arc.getCodigoCompra();
		}
		comando.executeUpdate("DELETE FROM "+Tablas.Compras+" WHERE "+Tablas.DNI+"='"+getDni()+"' AND "+Tablas.codigoCompra+"='"+codigoCompra+"'");
		comando.executeUpdate("UPDATE "+Tablas.Cliente+" SET "+Tablas.Dinero+"=("+Tablas.Dinero+"+"+com.getPrecioTotal()+") WHERE "+Tablas.DNI+"='"+getDni()+"'");
}
	public void devolverUnArticulo(ArticulosComprados arc,int numeroDevolver) throws SQLException {
		Metodos mc=new Metodos();
		Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
		if(arc.getCantidad()==numeroDevolver) {
			comando.executeUpdate("DELETE FROM "+Tablas.ArticulosComprados+" WHERE "+Tablas.IdArticulo+"='"+arc.getIdArticulo()+"' AND "+Tablas.codigoCompra+"='"+arc.getCodigoCompra()+"'");
		}else{
			comando.executeUpdate("UPDATE "+Tablas.ArticulosComprados+" SET "+Tablas.Cantidad+"=("+Tablas.Cantidad+"-"+numeroDevolver+") WHERE "+Tablas.IdArticulo+"='"+arc.getIdArticulo()+"'"
					+ "AND  "+Tablas.codigoCompra+"='"+arc.getCodigoCompra()+"'");
		}
		comando.executeUpdate("UPDATE "+Tablas.Articulo+" SET "+Tablas.StockActual+"=("+Tablas.StockActual+"+"+arc.getCantidad()+") WHERE "+Tablas.IdArticulo+"='"+arc.getIdArticulo()+"'");
		comando.executeUpdate("UPDATE "+Tablas.Cliente+" SET "+Tablas.Dinero+"=("+Tablas.Dinero+"+"+(arc.getPrecioArt()*numeroDevolver)+") WHERE "+Tablas.DNI+"='"+getDni()+"'");
	}
	
}
