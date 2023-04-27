package Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


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
	public void comprarArticulos(Compra compra,ArrayList<ArticulosComprados> lista) {
		// TODO Auto-generated method stub
		/**Metodos mc=new Metodos();
		Calendar cal=Calendar.getInstance();
		String fecha=cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.DAY_OF_MONTH);
		Statement comando;
		//int codC=-1;
		try {
			comando = (Statement) mc.conectarBaseDatos().createStatement();
			comando.executeUpdate("INSERT INTO "+Tablas.Compras+" ('"+Tablas.DNI+"',,'"+Tablas.precioFinal+"','"+Tablas.fechaCompra+"')"
					+ "VALUES ('"+dni+"','"+compra.getPrecioTotal()+"','"+fecha+"')");
			ResultSet codCompra=comando.executeQuery("SELECT "+Tablas.codigoCompra+" FROM "+Tablas.Compras+" WHERE "+Tablas.DNI+"='"+dni+"' "
					+ "ORDER BY "+Tablas.codigoCompra+" DESC LIMIT 1");
			/**while(codCompra.next()) {
				codC=codCompra.getInt(Tablas.codigoCompra);
			}
			/**int i=0;
			for(Articulo ar: compra.getArrayArticulos()) {
			comando.executeUpdate("INSERT INTO "+Tablas.ArticulosComprados+" VALUES ('"+codC+"','"+ar.getIdArticulo()+"','"+lista.+"','"+ar.getPrecio()+"')");
			comando.executeUpdate("UPDATE "+Tablas.Articulo+" WHERE "+Tablas.IdArticulo+"='"+ar.getIdArticulo()+"' SET "+Tablas.StockActual+"='("+Tablas.StockActual+"-"+compra.getCantidades()[i]+")'");
			i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	
}
