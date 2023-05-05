package controlador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import modelo.*;

public class Metodos {
	private String [] Tipos= {"Comida","Herramienta","Ropa"}; 
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
	public Date deStringADate(String fecha) throws ParseException {
		return formatter.parse(fecha);
	}
	public String formatearFecha(Date fecha) {
		return formatter.format(fecha);
	}
	public LocalDateTime deStringALocalDateTime(String fecha) throws ParseException {
		LocalDateTime fechaS = LocalDateTime.parse(fecha, formatter1);
		return fechaS;
	}
	public Connection conectarBaseDatos() throws SQLException {
		Connection conexion = (Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		return conexion;
	}
	public int pasarBoolean(Boolean statement) {
		int resul= 0;
		if(statement) {
			resul=1;
		}
		return resul;
	}
	public boolean pasarIntABoolean(int statement) {
		boolean resul=true ;
		if(statement==0) {
			resul=false;
		}
		return resul;
	}
	public void guardarInventario(ArrayList<Articulo> lista) throws IOException {
			FileWriter fich = new FileWriter(".\\Inventario.txt");
			BufferedWriter buf=new BufferedWriter(fich);
			for(Articulo ar: lista) {
				buf.write(ar.toString());
			}
			buf.close();
	}
	public String[] modificarComboBox(String[] tipos,String seleccion) {
		String [] nuevo=null;
		if(seleccion==null) {
			 nuevo= Tipos;
		}else {
			 nuevo=new String [tipos.length-1];
		for(int i=0,x=0;i<tipos.length;i++) {
			if(!tipos[i].equals(seleccion)) {
				nuevo[x]=tipos[i];
				x++;
			}
		}
		}
		return nuevo;
	}
}