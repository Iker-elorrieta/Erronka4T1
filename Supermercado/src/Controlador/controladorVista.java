package Controlador;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Modelo.Articulo;

public class controladorVista {
Metodos mts = new Metodos();
	
	
	public JTable cargarTabla(ArrayList<Articulo> listaArticulos) throws SQLException {
	
		
		listaArticulos = mts.cargarArticulos();
		
		
		String[][] datosTabla = new String[listaArticulos.size()][8];
		for(int i = 0;i<listaArticulos.size();i++){
			datosTabla[i][0] = String.valueOf(listaArticulos.get(i).getIdArticulo());
			datosTabla[i][1] = String.valueOf(listaArticulos.get(i).getNombreArticulo());
			datosTabla[i][2] = String.valueOf(listaArticulos.get(i).getRutaImagen());
			datosTabla[i][3] = String.valueOf(listaArticulos.get(i).getDescripcion());
			datosTabla[i][4] = String.valueOf(listaArticulos.get(i).getPrecio());
			datosTabla[i][5] = String.valueOf(listaArticulos.get(i).getStockMaximo());
			datosTabla[i][6] = String.valueOf(listaArticulos.get(i).getStockActual());
			datosTabla[i][7] = String.valueOf(listaArticulos.get(i).gettipo());
			
			
		}
		
		
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(
			datosTabla,
			new String[] {
				"IdArticulo",  "NombreArticulo", "RutaImagen", "Descripcion", "Precio", "StockMaximo", "StockActual", "Tipo"
			}
		));
		return table;
	
	
	
	}
	
}
