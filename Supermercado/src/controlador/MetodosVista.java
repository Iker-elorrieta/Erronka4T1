package controlador;

import java.awt.Component;
import java.awt.Image;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import excepciones.ErroresDeOperaciones;
import gestores.GestorArticulo;
import gestores.GestorPersona;
import gestores.GestorSeccion;
import gestores.GestorSupermercado;
import modelo.Articulo;
import modelo.Cliente;
import modelo.Comida;
import modelo.Herramienta;
import modelo.Jefe;
import modelo.Persona;
import modelo.Ropa;
import modelo.Seccion;
import modelo.Supermercado;
import otros.tipoArticulo;
import otros.tipoPersona;
import referencias.TABLAS;

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
		String[][] datosTabla = new String[listaArticulos.size()][6];
		for(int i = 0;i<listaArticulos.size();i++){
			datosTabla[i][0] = String.valueOf(listaArticulos.get(i).getNombreArticulo());
			datosTabla[i][1] = String.valueOf(listaArticulos.get(i).getRutaImagen());
			datosTabla[i][2] = String.valueOf(listaArticulos.get(i).getDescripcion());
			datosTabla[i][3] = String.valueOf(listaArticulos.get(i).getPrecio());
			if(listaArticulos.get(i) instanceof Comida) {
				co=(Comida)listaArticulos.get(i);
				datosTabla[i][4] = co.getFechaCaducidad();
				datosTabla[i][5] = co.getProcedencia();
			}else if(listaArticulos.get(i) instanceof Ropa) {
				ro=(Ropa) listaArticulos.get(i);
				datosTabla[i][4] = ro.getTalla();
				datosTabla[i][5] = ro.getMarca();
			}else if(listaArticulos.get(i) instanceof Herramienta) {
				he=(Herramienta) listaArticulos.get(i);
				if(he.getElectrica()) {
					datosTabla[i][4] = "Si";
				}else {
					datosTabla[i][4] = "No";
				}
				datosTabla[i][5] = String.valueOf(he.getGarantia());
			}
		}
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(
			datosTabla,
			new String[] {
				TABLAS.NOMBREARTICULO, TABLAS.RUTAIMAGEN, TABLAS.DESCRIPCION, TABLAS.PRECIO,"Atributo 1","Atributo 2"
			}
		));
		return table;
	}
	public String descripcion(JTable tabla,ArrayList<Articulo> listaArticulos) throws SQLException {
		listaArticulos = ga.cargarArticulos();
		return listaArticulos.get(tabla.getSelectedRow()).getDescripcion();
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
		ArrayList<Supermercado> lista=gsm.cargarSupermercados();
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
				datosTabla[i][2]=String.valueOf(lista.get(i).getNumArticulo());
		}
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(
			datosTabla,
			new String[] {
				TABLAS.CODIGOSECCION,TABLAS.TIPO,TABLAS.NUMAR
			}
		));
		return table;
	}
	
	public void comprobarCampos(JPanel panel) throws ErroresDeOperaciones {
		for(Component componente:panel.getComponents()) {
			if(componente instanceof JTextField) {
				if(((JTextField) componente).getText().equals("") && componente.isVisible()) {
					throw new ErroresDeOperaciones("Alguno de los campos esta vacio");
				}
			}
			if(componente instanceof JComboBox) {
				JComboBox<?> combo=(JComboBox<?>) componente;
			if(combo.getItemAt(combo.getSelectedIndex())==null  && componente.isVisible()) {
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
	public void borrarSupermercadoTabla(JTable tabla,ArrayList<Supermercado> lista) throws SQLException {
		gsm.borrarSupermercado(lista.get(tabla.getSelectedRow()));
	}
	public void borrarSeccionTabla(JTable tabla,ArrayList<Seccion> lista) throws SQLException {
		gs.borrarSeccion(lista.get(tabla.getSelectedRow()));
}
	public void borrarArticuloTabla(JTable tabla, ArrayList<Articulo> lista) throws SQLException {
		// TODO Auto-generated method stub
		ga.borrarArticulo(lista.get(tabla.getSelectedRow()));
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
	public void modificarSupermercadoTabla(JTable tabla,ArrayList<Supermercado> lista) throws SQLException {
		int fila=0;
		for(Supermercado su: lista) {
			su.setEmpresa((String) tabla.getModel().getValueAt(fila, 1));
			su.setDireccion((String) tabla.getModel().getValueAt(fila, 2));
			su.setNumEmpleados(Integer.parseInt((String) tabla.getModel().getValueAt(fila, 3)));
			gsm.cambiarSupermercado(su);
			fila++;
		};
	}
	public void modificarSeccionTabla(JTable tabla,ArrayList<Seccion> lista) throws SQLException {
		int fila=0;
		for(Seccion se: lista) {
			se.setNombreSeccion(tipoArticulo.valueOf((String) tabla.getModel().getValueAt(fila, 1)));
			se.setNumArticulo(Integer.parseInt((String) tabla.getModel().getValueAt(fila, 2)));
			gs.cambiarSeccion(se);
			fila++;
		};
	}
	public void modificarArticuloTabla(JTable tabla, ArrayList<Articulo> lista) throws SQLException, ParseException {
	// TODO Auto-generated method stub
		int fila=0;
		Ropa ro=null;
		Comida co=null;
		Herramienta he=null;
		for(Articulo ar:lista) {
			ar.setNombreArticulo((String) tabla.getModel().getValueAt(fila, 0));
			ar.setRutaImagen((String) tabla.getModel().getValueAt(fila, 1));
			ar.setDescripcion((String) tabla.getModel().getValueAt(fila, 2));
			ar.setPrecio(Float.valueOf((String) tabla.getModel().getValueAt(fila, 3)));
			if(ar instanceof Ropa) {
				ro=(Ropa) ar;
				ro.setTalla((String) tabla.getModel().getValueAt(fila, 4));
				ro.setMarca((String) tabla.getModel().getValueAt(fila, 5));
				ga.cambiarArticulo(ro);
			}
			if(ar instanceof Comida) {
				co=(Comida)ar;
				co.setFechaCaducidad(mts.deStringADate(((String) tabla.getModel().getValueAt(fila, 4))));
				co.setProcedencia((String) tabla.getModel().getValueAt(fila, 5));
				ga.cambiarArticulo(co);
			}
			if(ar instanceof Herramienta) {
				he=(Herramienta) ar;
				if(tabla.getModel().getValueAt(fila, 4).equals("Si")|tabla.getModel().getValueAt(fila, 4).equals("si")) {
					he.setElectrica(true);
				}else if(tabla.getModel().getValueAt(fila, 4).equals("No")|tabla.getModel().getValueAt(fila, 4).equals("no")){
					he.setElectrica(false);
				}
				he.setGarantia(Integer.parseInt(((String) tabla.getModel().getValueAt(fila, 5))));
				ga.cambiarArticulo(he);
			}
			fila++;
		}
	}
	public JTable anadirDescripcion(JTable tabla, String text, ArrayList<Articulo> lista) throws SQLException {
		// TODO Auto-generated method stub
		lista=ga.cargarArticulos();
		lista.get(tabla.getSelectedRow()).setDescripcion(text);
		tabla.getModel().setValueAt(lista.get(tabla.getSelectedRow()).getDescripcion(), tabla.getSelectedRow(), 2);
		return tabla;
	}
	public JTextArea mostarArticulos(ArrayList<Articulo> lista) {
		JTextArea panel_Comprar = new JTextArea();
		Comida co=null;
		Ropa ro=null;
		Herramienta he=null;
		int largo=145;
		int conta=0;
		for(Articulo ar:lista) {
				ImageIcon imagenIcono = new ImageIcon(".\\Imagenes\\"+ar.getRutaImagen());
		        Image imagen = imagenIcono.getImage();
		        Image imagenRedimensionada = imagen.getScaledInstance(146, 132, java.awt.Image.SCALE_SMOOTH);
		        ImageIcon imagenRedimensionadaIcono = new ImageIcon(imagenRedimensionada);
				JLabel lblImagenAr = new JLabel(imagenRedimensionadaIcono);
				lblImagenAr.setBounds(10, 11+(largo*conta), 146, 132);
				panel_Comprar.add(lblImagenAr);
				panel_Comprar.setLayout(null);
				
				JLabel lblNombreAr = new JLabel("Nombre:");
				lblNombreAr.setBounds(166, 21+(largo*conta), 60, 14);
				panel_Comprar.add(lblNombreAr);
				
				JLabel lblPrecioAr = new JLabel("Precio: "+ar.getPrecio());
				lblPrecioAr.setBounds(166, 70+(largo*conta), 46, 14);
				panel_Comprar.add(lblPrecioAr);
				
				JLabel lblStockAr = new JLabel("Stock: "+ar.getStockActual());
				lblStockAr.setBounds(166, 95+(largo*conta), 94, 14);
				panel_Comprar.add(lblStockAr);
				
				JLabel lblDescripcionAr = new JLabel("Descripcion:");
				lblDescripcionAr.setBounds(436, 21+(largo*conta), 94, 14);
				panel_Comprar.add(lblDescripcionAr);
				
				JLabel lblMuestraNombre = new JLabel(ar.getNombreArticulo());
				lblMuestraNombre.setBounds(166, 46+(largo*conta), 131, 14);
				panel_Comprar.add(lblMuestraNombre);
				
				if(ar instanceof Comida) {
					co=(Comida)ar;
				JLabel lblAtributoUno = new JLabel("Fecha de caducidad:");
				lblAtributoUno.setBounds(302, 21+(largo*conta), 67, 14);
				panel_Comprar.add(lblAtributoUno);
				
				JLabel lblAtributoDos = new JLabel("Procedencia:");
				lblAtributoDos.setBounds(302, 70+(largo*conta), 46, 14);
				panel_Comprar.add(lblAtributoDos);
				
				JLabel lblMostrarAtributo1 = new JLabel(co.getFechaCaducidad());
				lblMostrarAtributo1.setBounds(302, 46+(largo*conta), 124, 14);
				panel_Comprar.add(lblMostrarAtributo1);
				
				JLabel lblMostrarAtributo2 = new JLabel(co.getProcedencia());
				lblMostrarAtributo2.setBounds(302, 95+(largo*conta), 124, 14);
				panel_Comprar.add(lblMostrarAtributo2);
				}
				if(ar instanceof Ropa) {
					ro=(Ropa)ar;
				JLabel lblAtributoUno = new JLabel("Talla:");
				lblAtributoUno.setBounds(302, 21+(largo*conta), 67, 14);
				panel_Comprar.add(lblAtributoUno);
				
				JLabel lblAtributoDos = new JLabel("Marca:");
				lblAtributoDos.setBounds(302, 70+(largo*conta), 46, 14);
				panel_Comprar.add(lblAtributoDos);
				
				JLabel lblMostrarAtributo1 = new JLabel(ro.getTalla());
				lblMostrarAtributo1.setBounds(302, 46+(largo*conta), 124, 14);
				panel_Comprar.add(lblMostrarAtributo1);
				
				JLabel lblMostrarAtributo2 = new JLabel(ro.getMarca());
				lblMostrarAtributo2.setBounds(302, 95+(largo*conta), 124, 14);
				panel_Comprar.add(lblMostrarAtributo2);
				}
				if(ar instanceof Herramienta) {
					he=(Herramienta)ar;
				JLabel lblAtributoUno = new JLabel("Electrica:");
				lblAtributoUno.setBounds(302, 21+(largo*conta), 67, 14);
				panel_Comprar.add(lblAtributoUno);
				
				JLabel lblAtributoDos = new JLabel("Garantia:");
				lblAtributoDos.setBounds(302, 70+(largo*conta), 46, 14);
				panel_Comprar.add(lblAtributoDos);
				
				JLabel lblMostrarAtributo1 = new JLabel(String.valueOf(he.getElectrica()));
				lblMostrarAtributo1.setBounds(302, 46+(largo*conta), 124, 14);
				panel_Comprar.add(lblMostrarAtributo1);
				
				JLabel lblMostrarAtributo2 = new JLabel(String.valueOf(he.getGarantia()));
				lblMostrarAtributo2.setBounds(302, 95+(largo*conta), 124, 14);
				panel_Comprar.add(lblMostrarAtributo2);
				}
				
				JLabel lblMostrarDes = new JLabel(ar.getDescripcion());
				lblMostrarDes.setBounds(436, 46+(largo*conta), 234, 97);
				panel_Comprar.add(lblMostrarDes);
				
				JButton btnCogerArticulo = new JButton("Anadir");
				btnCogerArticulo.setBounds(208, 120+(largo*conta), 89, 23);
				panel_Comprar.add(btnCogerArticulo);
				
				JSpinner cantidad = new JSpinner();
				cantidad.setModel(new SpinnerNumberModel(1, 1, 10, 1));
				cantidad.setBounds(396, 120+(largo*conta), 30, 20);
				panel_Comprar.add(cantidad);
				conta++;
		}
		return panel_Comprar;
	}
}


