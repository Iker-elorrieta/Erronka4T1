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
import modelo.Comida;
import modelo.Herramienta;
import modelo.Jefe;
import modelo.Persona;
import modelo.Ropa;
import modelo.Seccion;
import modelo.Supermercado;
import modelo.tipoPersona;

public class MetodosVista {
Metodos mts = new Metodos();
GestorArticulo ga=new GestorArticulo();
GestorPersona gp=new GestorPersona();
GestorSupermercado gsm=new GestorSupermercado();
GestorSeccion gs=new GestorSeccion();
	
	public JTable cargarTabla(ArrayList<Articulo> listaArticulos) throws SQLException {
		listaArticulos = ga.cargarArticulos();
		Comida co=null;
		Ropa ro=null;
		Herramienta he=null;
		String[][] datosTabla = new String[listaArticulos.size()][13];
		for(int i = 0;i<listaArticulos.size();i++){
			datosTabla[i][0] = String.valueOf(listaArticulos.get(i).getNombreArticulo());
			datosTabla[i][1] = String.valueOf(listaArticulos.get(i).getRutaImagen());
			datosTabla[i][2] = String.valueOf(listaArticulos.get(i).getDescripcion());
			datosTabla[i][3] = String.valueOf(listaArticulos.get(i).getPrecio());
			if(listaArticulos.get(i) instanceof Comida) {
				co=(Comida)listaArticulos.get(i);
				datosTabla[i][6] = co.getFechaCaducidad();
				datosTabla[i][7] = co.getProcedencia();
			}else if(listaArticulos.get(i) instanceof Ropa) {
				ro=(Ropa) listaArticulos.get(i);
				datosTabla[i][4] = ro.getTalla();
				datosTabla[i][5] = ro.getMarca();
			}else if(listaArticulos.get(i) instanceof Herramienta) {
				he=(Herramienta) listaArticulos.get(i);
				if(he.getElectrica()) {
					datosTabla[i][8] = "Si";
				}else {
					datosTabla[i][8] = "No";
				}
				datosTabla[i][8] = String.valueOf(he.getGarantia());
			}
		}
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(
			datosTabla,
			new String[] {
				TABLAS.NOMBREARTICULO, TABLAS.RUTAIMAGEN, TABLAS.DESCRIPCION, TABLAS.PRECIO,TABLAS.TALLA,TABLAS.MARCA,TABLAS.FECHACADUCIDAD,TABLAS.PROCEDENCIA,TABLAS.ELECTRICA,TABLAS.GARANTIA
			}
		));
		return table;
	}
	public JTable tablaUsuarios(ArrayList<Persona> listaUsuarios) throws SQLException {
		listaUsuarios=gp.cargarPersonas();
		Cliente prueba =null;
		Jefe jefe=null;
		String[][] datosTabla = new String[listaUsuarios.size()][6];
		for(int i = 0;i<listaUsuarios.size();i++){
			datosTabla[i][0] = String.valueOf(listaUsuarios.get(i).getNombre());
			datosTabla[i][1] = String.valueOf(listaUsuarios.get(i).getApellidos());
			datosTabla[i][2] = String.valueOf(listaUsuarios.get(i).getEmail());
			datosTabla[i][3] = String.valueOf(listaUsuarios.get(i).getTipo());
			if(listaUsuarios.get(i).getTipo().equals(tipoPersona.Cliente)) {
				prueba=(Cliente)listaUsuarios.get(i);
				if(prueba.isBloqueado()) {
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
				TABLAS.NOMBRE,TABLAS.APELLIDOS,TABLAS.EMAIL, TABLAS.TIPO,"Bloqueado/Dios"
			}
		));
		return table;
	}
	public JTable tablaSupermercados() throws SQLException {
		ArrayList<Supermercado> lista=gsm.todoSupermercados(gp.cargarPersonas());
		String[][] datosTabla = new String[lista.size()][4];
		for(int i = 0;i<lista.size();i++){
			datosTabla[i][0] = String.valueOf(lista.get(i).getCodigoSuper());
			datosTabla[i][1] = String.valueOf(lista.get(i).getEmpresa());
			datosTabla[i][2] = String.valueOf(lista.get(i).getDireccion());
			datosTabla[i][3] = String.valueOf(lista.get(i).getNumEmpleados());
		}
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(
			datosTabla,
			new String[] {
				TABLAS.CODIGOSUPER,TABLAS.EMPRESA,TABLAS.DIRECCION, TABLAS.NUMEROEMPLEADOS
			}
		));
		return table;
	}
	public JTable tablaSecciones() throws SQLException {
		ArrayList<Seccion> lista=gs.cargarSecciones();
		String[][] datosTabla = new String[gs.cargarSecciones().size()][3];
		for(int i = 0;i<lista.size();i++) {
				datosTabla[i][0]=lista.get(i).getCodigoSeccion();
				datosTabla[i][1]=String.valueOf(lista.get(i).getNombreSeccion());
		}
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(
			datosTabla,
			new String[] {
				TABLAS.CODIGOSECCION,TABLAS.TIPO
			}
		));
		return table;
	}
	public ArrayList<Persona> realizarCambios(JTable tabla,ArrayList<Persona> lista) throws ErroresDeOperaciones, SQLException {
		int fila=0;
		for(Persona per: lista) {
			per.setNombre((String) tabla.getModel().getValueAt(fila, 0));
			per.setApellidos((String) tabla.getModel().getValueAt(fila, 1));
			per.setEmail((String) tabla.getModel().getValueAt(fila, 2));
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
	public void vaciarCampos(JPanel panel) {
		for(Component componente:panel.getComponents()) {
			if(componente instanceof JTextField) {
				((JTextField) componente).setText("");
			}
		}
	}
	public void borrarPorTabla(JTable tabla,ArrayList<Persona> lista) throws SQLException {
		if(lista.get(tabla.getSelectedRow()) instanceof Cliente){
		gp.darseBajaPersona(lista.get(tabla.getSelectedRow()));
		}
	}
	public void accionPorTabla(JTable tabla,ArrayList<Persona> lista,boolean accion) throws SQLException {
		if(lista.get(tabla.getSelectedRow()) instanceof Cliente) {
		gp.cambiarEstadoUsuario((Cliente)lista.get(tabla.getSelectedRow()), accion);
		}
	}
}

