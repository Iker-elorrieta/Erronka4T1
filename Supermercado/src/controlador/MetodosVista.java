package controlador;

import java.awt.Component;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import modelo.Articulo;
import modelo.Cliente;
import modelo.Jefe;
import modelo.Persona;
import modelo.tipoPersona;

public class MetodosVista {
Metodos mts = new Metodos();
GestorArticulo ga=new GestorArticulo();
GestorPersona gp=new GestorPersona();
	
	public JTable cargarTabla(ArrayList<Articulo> listaArticulos) throws SQLException {
		listaArticulos = ga.cargarArticulos();
		String[][] datosTabla = new String[listaArticulos.size()][8];
		for(int i = 0;i<listaArticulos.size();i++){
			datosTabla[i][0] = String.valueOf(listaArticulos.get(i).getIdArticulo());
			datosTabla[i][1] = String.valueOf(listaArticulos.get(i).getNombreArticulo());
			datosTabla[i][2] = String.valueOf(listaArticulos.get(i).getRutaImagen());
			datosTabla[i][3] = String.valueOf(listaArticulos.get(i).getDescripcion());
			datosTabla[i][4] = String.valueOf(listaArticulos.get(i).getPrecio());
			datosTabla[i][5] = String.valueOf(listaArticulos.get(i).getStockActual());
			datosTabla[i][6] = String.valueOf(listaArticulos.get(i).gettipo());
		}
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(
			datosTabla,
			new String[] {
				"IdArticulo",  "NombreArticulo", "RutaImagen", "Descripcion", "Precio", "StockActual", "Tipo"
			}
		));
		return table;
	}
	public JTable tablaUsuarios(ArrayList<Persona> listaUsuarios) throws SQLException {
		listaUsuarios=gp.cargarPersonas();
		Cliente prueba =null;
		Jefe jefe=null;
		String[][] datosTabla = new String[listaUsuarios.size()][5];
		for(int i = 0;i<listaUsuarios.size();i++){
			datosTabla[i][0] = String.valueOf(listaUsuarios.get(i).getNombre());
			datosTabla[i][1] = String.valueOf(listaUsuarios.get(i).getApellidos());
			datosTabla[i][2] = String.valueOf(listaUsuarios.get(i).getEmail());
			datosTabla[i][3] = String.valueOf(listaUsuarios.get(i).getTipo());
			if(listaUsuarios.get(i).getTipo().equals(tipoPersona.Cliente)) {
				prueba=(Cliente)listaUsuarios.get(i);
				if(!prueba.isBloqueado()) {
				datosTabla[i][4] = String.valueOf("Bloqueado");
				}else {
				datosTabla[i][4] = String.valueOf("Cliente");	
				}
			}else {
				jefe=(Jefe)listaUsuarios.get(i);
				if(jefe.isDios()) {
				datosTabla[i][4] = String.valueOf("Root");
				}else {
				datosTabla[i][4] = String.valueOf("Administrador");	
				}
			}
		}
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(
			datosTabla,
			new String[] {
				"Nombre","Apellidos","E-Mail", "Tipo","Bloqueado/Dios"
			}
		));
		return table;
	}
	public ArrayList<Persona> realizarCambios(JTable tabla,ArrayList<Persona> lista) throws ErroresDeOperaciones {
		int fila=0;
		for(Persona per: lista) {
			Cliente cli=null;
			per.setNombre((String) tabla.getModel().getValueAt(fila, 0));
			per.setApellidos((String) tabla.getModel().getValueAt(fila, 1));
			per.setEmail((String) tabla.getModel().getValueAt(fila, 2));
			if(per instanceof Cliente) {
				cli=(Cliente) per;
				String accion=(String) tabla.getModel().getValueAt(fila, 4);
				if(accion.equals("Bloqueado")) {
				cli.setBloqueado(true);
				}else {
				cli.setBloqueado(false);
				}
			}
			fila++;
		};
		return lista;
}
	public void comprobarCampos(JPanel panel) throws ErroresDeOperaciones {
		for(Component componente:panel.getComponents()) {
			if(componente instanceof JTextField) {
				if(((JTextField) componente).getText().equals("")) {
					throw new ErroresDeOperaciones("Alguno de los campos esta vacio");
				}
			}
		}
	}
}

