package controlador;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import excepciones.ErroresDeOperaciones;
import gestores.GestorArticulo;
import gestores.GestorCompra;
import gestores.GestorPersona;
import gestores.GestorSeccion;
import gestores.GestorSupermercado;
import modelo.Articulo;
import modelo.ArticuloComprado;
import modelo.Cliente;
import modelo.Comida;
import modelo.Compra;
import modelo.Herramienta;
import modelo.Jefe;
import modelo.Persona;
import modelo.Ropa;
import modelo.Seccion;
import modelo.Supermercado;
import otros.tipoArticulo;
import referencias.TITULOS;

/**Este clase se utilizara para operar los elementos
 * dinamicos de la aplicacion.
 * @author Erlantz
 *
 */
public class MetodosVista {
/**
 * La clase de metodos, principalmente para recoger
 * datos y cambiar formatos.
 */
Metodos mts = new Metodos();
/**
 * El gestor de articulo que utilizaremos sobre las 
 * operaciones con articulos.
 */
GestorArticulo ga=new GestorArticulo();
/**
 * El gestor de persona que utilizaremos sobre las 
 * operaciones con las cuentas de las personas.
 */
GestorPersona gp=new GestorPersona();
/**
 * El gestor de supermercado que utilizaremos sobre las 
 * operaciones con los supermercados.
 */
GestorSupermercado gsm=new GestorSupermercado();
/**
 * El gestor de secciones que utilizaremos sobre las 
 * operaciones con las secciones y sus articulos.
 */
GestorSeccion gs=new GestorSeccion();
/**
 * El gestor de compras que utilizaremos sobre las 
 * operaciones con las compras.
 */
GestorCompra gc=new GestorCompra();
	
	/**La tablas de articulos
	 * @param datosTabla Los datos
	 * @return La tabla con los datos cargados.
	 */
	public JTable cargarTabla(String [][] datosTabla) {
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(
			datosTabla,
			new String[] {
				TITULOS.NOMBRE, TITULOS.NOMBREIMAGEN, TITULOS.DESCRIPCION, TITULOS.PRECIO,TITULOS.ATRIBUTO1,TITULOS.ATRIBUTO2
			}
		));
		return table;
	}
/**Para cargar la tabla con el historial de compras.
 * @param per La persona a la que le pertenecen las compra.
 * @param datosTabla Los datos de las compras.
 * @return La tabla.
 */
public JTable cargarHistorialCompras(Persona per,String [][] datosTabla) {
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(
			datosTabla,
			new String[] {
				TITULOS.CODIGO, TITULOS.PRECIO, ""
			}
		));
		return table;
	}
/**Coger el codigo de compra para cargar
 * los articulos comprados.
 * @param tabla La tabla de compras.
 * @param listaCompras La lista de compras.
 * @return La compra.
 * @throws ErroresDeOperaciones Fila no seleccionada.
 */
public Compra cogerCodigoCompra(JTable tabla,ArrayList<Compra> listaCompras) throws ErroresDeOperaciones {
	if(tabla.getSelectedRow()==-1) {
		throw new ErroresDeOperaciones("No selecciono una linea");
	}
	return listaCompras.get(tabla.getSelectedRow());
}
/**Para cargar la lista de articulos comprados.
 * @param datosTabla Los datos a mostrar.
 * @return La tabla a mostrar-
 */
public JTable cargaArticulosComprados(String [][] datosTabla) {
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(
			datosTabla,
			new String[] {
				TITULOS.NOMBRE, TITULOS.CANTIDAD, TITULOS.PRECIO
			}
		));
		return table;
}
/**Coger el articulo seleccionado.
 * @param tabla La tabla.
 * @param listaArC La lista de articulos comprados.
 * @return El articulocomprado seleccionado.
 */
public ArticuloComprado cogerArticuloComprado(JTable tabla,ArrayList<ArticuloComprado> listaArC) {
	return listaArC.get(tabla.getSelectedRow());
}
	/**La tabla con los datos de la recarga de articulos.
	 * @param datosTabla Los datos a mostrar.
	 * @return La tabla.
	 */
	public JTable tablaRecargArticulos(String [][]datosTabla) {
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(
			datosTabla,
			new String[] {
				TITULOS.ENCARGADO, TITULOS.IDENTIFICADOR, TITULOS.NOMBRE, TITULOS.PRECIO,TITULOS.STOCKNECESARIO,TITULOS.COSTE
			}
		));
		return table;
	}
	/**Para coger la descripcion del articulo seleccionado.
	 * @param tabla La tabla de articulos.
	 * @param listaArticulos Los articulos en un Array.
	 * @return La descripcion del articulo escogido.
	 */
	public String descripcion(JTable tabla,ArrayList<Articulo> listaArticulos) {
		return listaArticulos.get(tabla.getSelectedRow()).getDescripcion();
	}
	/**La carga de la tabla de usuarios.
	 * @param datosTabla Los datos a mostrar.
	 * @return La tabla con los datos.
	 */
	public JTable tablaUsuarios(String [][] datosTabla) {
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(
			datosTabla,
			new String[] {
				TITULOS.NOMBRE,TITULOS.APELLIDOS,TITULOS.EMAIL, TITULOS.TIPO,TITULOS.ESTADO
			}
		));
		return table;
	}
	/**Para cargar la tabla de supermercados.
	 * @param datosTabla Los datos a mostar.
	 * @return La tabla con los datos.
	 */
	public JTable tablaSupermercados(String [][] datosTabla) {
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(
			datosTabla,
			new String[] {
				TITULOS.CODIGO,TITULOS.EMPRESA,TITULOS.DIRECCION, TITULOS.NUMEMPLEADOS
			}
		));
		return table;
	}
	/**Para cargar la tabla de las secciones.
	 * @param datosTabla Los datos a mostrar.
	 * @return La tabla de secciones.
	 */
	public JTable tablaSecciones(String [][] datosTabla) {
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(
			datosTabla,
			new String[] {
				TITULOS.CODIGO,TITULOS.TIPO,TITULOS.NUMARTICULOS
			}
		));
		return table;
	}
	/**Para comprobar si quedan campos vacios de un panel.
	 * @param panel El panel a comprobar.
	 * @throws ErroresDeOperaciones Fallo en las operaciones.
	 */
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
	/**Para vaciar los textfields del panel.
	 * @param panel El panel a cambiar.
	 */
	public void vaciarCampos(JPanel panel) {
		for(Component componente:panel.getComponents()) {
			if(componente instanceof JTextField) {
				((JTextField) componente).setText("");
			}
		}
	}
	/**Para borrar una persona seleccionada de la tabla.
	 * @param conexion La conexion.
	 * @param tabla La tabla de personas.
	 * @param lista La lista de personas a cambiar.
	 * @throws SQLException Fallo en laconexion.
	 * @throws ErroresDeOperaciones No se puede borrar el root.
	 */
	public void borrarPorTabla(Connection conexion,JTable tabla,ArrayList<Persona> lista) throws SQLException, ErroresDeOperaciones {
		if(lista.get(tabla.getSelectedRow()) instanceof Cliente){
		gp.darseBajaPersona(conexion,lista.get(tabla.getSelectedRow()));
		}
	}
	/**Para cambiar el estado de un usuario.
	 * @param conexion La conexion.
	 * @param tabla La tabla de personas.
	 * @param lista La lista de personas.
	 * @param accion Si se bloquea o no.
	 * @throws SQLException Fallo en la conexion.
	 */
	public void accionPorTabla(Connection conexion,JTable tabla,ArrayList<Persona> lista,boolean accion) throws SQLException {
		if(lista.get(tabla.getSelectedRow()) instanceof Cliente) {
		gp.cambiarEstadoUsuario(mts,conexion,(Cliente)lista.get(tabla.getSelectedRow()), accion);
		}
	}
	/**Para borrar el supermercado seleccionado.
	 * @param conexion La conexion.
	 * @param tabla La tabla de supermercados.
	 * @param lista La lista de supermercados.
	 * @throws SQLException Fallo en la conexion.
	 */
	public void borrarSupermercadoTabla(Connection conexion,JTable tabla,ArrayList<Supermercado> lista) throws SQLException {
		gsm.borrarSupermercado(conexion,lista.get(tabla.getSelectedRow()));
	}
	/**Para borrar la seccion seleccionada.
	 * @param conexion La conexion.
	 * @param tabla La tabla de secciones
	 * @param lista La lista de secciones.
	 * @throws SQLException Fallo en la conexion.
	 */
	public void borrarSeccionTabla(Connection conexion,JTable tabla,ArrayList<Seccion> lista) throws SQLException {
		gs.borrarSeccion(conexion,lista.get(tabla.getSelectedRow()));

}
	/**Borrar el articulo seleccionado.
	 * @param conexion La conexion.
	 * @param tabla La tabla de articulos.
	 * @param lista El Array de articulos.
	 * @throws SQLException
	 */
	public void borrarArticuloTabla(Connection conexion,JTable tabla, ArrayList<Articulo> lista) throws SQLException {
		// TODO Auto-generated method stub
		ga.borrarArticulo(conexion,lista.get(tabla.getSelectedRow()));
	}
	
	/**Para aplicar los cambios a la base de datos.
	 * @param tabla La tabla a cambiar
	 * @param lista La lista de personas.
	 * @return El Array de personas modificadas.
	 */
	public ArrayList<Persona> realizarCambios(JTable tabla,ArrayList<Persona> lista) {
		int fila=0;
		for(Persona per: lista) {
			per.setNombre((String) tabla.getModel().getValueAt(fila, 0));
			per.setApellidos((String) tabla.getModel().getValueAt(fila, 1));
			per.setEmail((String) tabla.getModel().getValueAt(fila, 2));
			fila++;
		};
		return lista;
}
	/**Para aplicar los cambios de la tabla a la BBDD.
	 * @param conexion La conexion.
	 * @param tabla La tabla modificada.
	 * @param lista La lista de supermercados.
	 * @throws SQLException Fllo en la conexion.
	 * @throws ErroresDeOperaciones 
	 */
	public void modificarSupermercadoTabla(Connection conexion,JTable tabla,ArrayList<Supermercado> lista) throws SQLException, ErroresDeOperaciones {
		int fila=0;
		for(Supermercado su: lista) {
			su.setEmpresa((String) tabla.getModel().getValueAt(fila, 1));
			su.setDireccion((String) tabla.getModel().getValueAt(fila, 2));
			su.setNumEmpleados(Integer.parseInt((String) tabla.getModel().getValueAt(fila, 3)));
			gsm.cambiarSupermercado(conexion,su);
			fila++;
		};
	}
	/**Para cambiar la tabla de secciones en base a los cambios de la JTable.
	 * @param conexion La conexion.
	 * @param tabla La tabla de las secciones.
	 * @param lista La lista de secciones a cambiar.
	 * @throws SQLException
	 */
	public void modificarSeccionTabla(Connection conexion,JTable tabla,ArrayList<Seccion> lista) throws SQLException {
		int fila=0;
		for(Seccion se: lista) {
			se.setNombreSeccion(tipoArticulo.valueOf((String) tabla.getModel().getValueAt(fila, 1)));
			se.setNumArticulo(Integer.parseInt((String) tabla.getModel().getValueAt(fila, 2)));
			gs.cambiarSeccion(conexion,se);
			fila++;
		};
	}
	/**Para coger los datos de todos los articulos
	 * de la BBDD.
	 * @param conexion La conexion.
	 * @param tabla La tabla a cambiar.
	 * @param lista La lista de articulos.
	 * @throws SQLException La conexion.
	 * @throws ParseException Fallo en el cambio de datos.
	 * @throws ErroresDeOperaciones Errores en cambios.
	 */
	public void modificarArticuloTabla(Connection conexion,JTable tabla, ArrayList<Articulo> lista) throws SQLException, ParseException, ErroresDeOperaciones {
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
					ga.cambiarArticulo(mts,conexion,ro);
			}
			if(ar instanceof Comida) {
				co=(Comida)ar;
				co.setFechaCaducidad(mts.deStringADate(((String) tabla.getModel().getValueAt(fila, 4))));
				co.setProcedencia((String) tabla.getModel().getValueAt(fila, 5));
				ga.cambiarArticulo(mts,conexion,co);
			}
			if(ar instanceof Herramienta) {
				he=(Herramienta) ar;
				if(tabla.getModel().getValueAt(fila, 4).equals("Si")|tabla.getModel().getValueAt(fila, 4).equals("si")) {
					he.setElectrica(true);
				}else if(tabla.getModel().getValueAt(fila, 4).equals("No")|tabla.getModel().getValueAt(fila, 4).equals("no")){
					he.setElectrica(false);
				}
				he.setGarantia(Integer.parseInt(((String) tabla.getModel().getValueAt(fila, 5))));
				ga.cambiarArticulo(mts,conexion,he);
			}
		}
			fila++;
}
	
	/**Se utlizará para recarga los stocks de los
	 * articulos de la BBDD.
	 * @param conexion La conexion.
	 * @param je El jefe que hace la compra.
	 * @param tabla La tabla a cambiar.
	 * @param lista La lista de articulos a modificar.
	 * @throws SQLException Fallo en la conexion.
	 * @throws ErroresDeOperaciones 
	 */
	public void recargarStocks(Connection conexion,Jefe je,JTable tabla, ArrayList<Articulo> lista) throws SQLException, ErroresDeOperaciones {
		lista=ga.cargarArticulos(conexion);
		for(Articulo ar:lista) {
			if(Integer.parseInt((String) tabla.getModel().getValueAt(tabla.getSelectedRow(), 1))==ar.getIdArticulo()) {
				ar.setStockActual(100);
				ga.cambiarArticulo(mts,conexion,ar);
				JOptionPane.showMessageDialog(null, je.comprarArticulos((Float) tabla.getModel().getValueAt(tabla.getSelectedRow(), 5)));
			}
		}
	}
	/**
	 * Para permitir una modificacion de las descripciones.
	 * @param conexion La conexion.
	 * @param tabla La tabla de articulos a alterar.
	 * @param text El texto de la descripcion.
	 * @param lista Los articulos a modificar.
	 * @return La tabla alterada.
	 * @throws SQLException Fallo en la conexion
	 */
	public JTable anadirDescripcion(Connection conexion,JTable tabla, String text, ArrayList<Articulo> lista) throws SQLException {
		// TODO Auto-generated method stub
		lista=ga.cargarArticulos(conexion);
		lista.get(tabla.getSelectedRow()).setDescripcion(text);
		tabla.getModel().setValueAt(lista.get(tabla.getSelectedRow()).getDescripcion(), tabla.getSelectedRow(), 2);
		return tabla;
	}
	/**Para mostrar los articulos que las personas podran
	 * comprar.
	 * @param lista La lista de los articulos.
	 * @param carrito Para permitir a la persona anadir articulos para
	 * realizar la compra.
	 * @param verPrecio El label de precio total.
	 * @param articulosMostrar Los articulos que se mostrarn
	 * @return El Panel con los datos a mostar.
	 */
	public JPanel mostrarArticulos(ArrayList<Articulo> lista,Compra carrito,JLabel verPrecio,ArrayList<Articulo> articulosMostrar) {
		JPanel panel_Comprar = new JPanel();
		Color customColor = new Color(18,20,28); 
		Comida co=null;
		Ropa ro=null;
		Herramienta he=null;
		for(Articulo ar:lista) {
			ImageIcon imagenIcono = new ImageIcon(".\\Imagenes\\"+ar.getRutaImagen());
			Image imagen = imagenIcono.getImage();
			Image imagenRedimensionada = imagen.getScaledInstance(146, 132, java.awt.Image.SCALE_SMOOTH);
			ImageIcon imagenRedimensionadaIcono = new ImageIcon(imagenRedimensionada);
			JLabel lblImagenAr = new JLabel(imagenRedimensionadaIcono);
			lblImagenAr.setBounds(0, 0, 152, 152);
			panel_Comprar.add(lblImagenAr);
						
			JPanel panelAtributos = new JPanel();
			panelAtributos.setBounds(152, 0, 248, 152);
			panel_Comprar.add(panelAtributos);
			panelAtributos.setLayout(null);
			panelAtributos.setBackground(customColor);
			
			JLabel lblNombreAr = new JLabel("Nombre: "+ar.getNombreArticulo());
			lblNombreAr.setForeground(new Color(255, 255, 255));
			lblNombreAr.setBounds(10, 0, 207, 14);
			panelAtributos.add(lblNombreAr);
			
			JLabel lblPrecioAr = new JLabel("Precio: "+ar.getPrecio());
			lblPrecioAr.setForeground(new Color(255, 255, 255));
			lblPrecioAr.setBounds(10, 11, 207, 14);
			panelAtributos.add(lblPrecioAr);
			
			JLabel lblStockAr = new JLabel("Stock: "+ar.getStockActual());
			lblStockAr.setForeground(new Color(255, 255, 255));
			lblStockAr.setBounds(10, 25, 207, 14);
			panelAtributos.add(lblStockAr);
			if(ar instanceof Comida) {
				co=(Comida)ar;
			JLabel lblAtributoDos = new JLabel("Procedencia :"+co.getProcedencia());
			lblAtributoDos.setForeground(new Color(255, 255, 255));
			lblAtributoDos.setBounds(10, 36, 207, 14);
			panelAtributos.add(lblAtributoDos);
			
			JLabel lblAtributoUno = new JLabel("Fecha de caducidad: "+co.getFechaCaducidad());
			lblAtributoUno.setForeground(new Color(255, 255, 255));
			lblAtributoUno.setBounds(10, 50, 207, 14);
			panelAtributos.add(lblAtributoUno);
			}
			if(ar instanceof Ropa) {
				ro=(Ropa)ar;
				JLabel lblAtributoDos = new JLabel("Talla :"+ro.getTalla());
				lblAtributoDos.setForeground(new Color(255, 255, 255));
				lblAtributoDos.setBounds(10, 36, 207, 14);
				panelAtributos.add(lblAtributoDos);
				
				JLabel lblAtributoUno = new JLabel("Marca: "+ro.getMarca());
				lblAtributoUno.setForeground(new Color(255, 255, 255));
				lblAtributoUno.setBounds(10, 50, 207, 14);
				panelAtributos.add(lblAtributoUno);
			}
			if(ar instanceof Herramienta) {
				he=(Herramienta)ar;
				JLabel lblAtributoDos = new JLabel("Electrica :"+he.getElectrica());
				lblAtributoDos.setForeground(new Color(255, 255, 255));
				lblAtributoDos.setBounds(10, 36, 207, 14);
				panelAtributos.add(lblAtributoDos);
				
				JLabel lblAtributoUno = new JLabel("Garantia: "+he.getGarantia());
				lblAtributoUno.setForeground(new Color(255, 255, 255));
				lblAtributoUno.setBounds(10, 50, 207, 14);
				panelAtributos.add(lblAtributoUno);
			}
										
			JSpinner cantidad = new JSpinner();
			cantidad.setBounds(123, 70, 39, 20);
			panelAtributos.add(cantidad);
			cantidad.setModel(new SpinnerNumberModel(1, 1, 100, 1));
			
			JButton btnCogerArticulo = new JButton("Anadir");
			btnCogerArticulo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					carrito.anadirArticulo(ar, (Integer)cantidad.getValue());
					ga.diferenciarCarrito(ar, articulosMostrar);
					carrito.setPrecioTotal(carrito.calcularPrecioTotal());
					verPrecio.setText("Precio del carrito: "+carrito.getPrecioTotal());
				}
			});
			btnCogerArticulo.setBounds(10, 69, 74, 23);
			btnCogerArticulo.setForeground(new Color(192, 192, 192));
			btnCogerArticulo.setBackground(new Color(0,76,255));
			panelAtributos.add(btnCogerArticulo);
			
			JPanel panel = new JPanel();
			panel.setBounds(402, 0, 248, 152);
			panel_Comprar.add(panel);
			panel.setLayout(null);
			JLabel lblDescripcionAr = new JLabel("Descripcion:");
			lblDescripcionAr.setForeground(new Color(255, 255, 255));
			lblDescripcionAr.setBounds(10, 11, 175, 14);
			panel.add(lblDescripcionAr);
			
			JTextArea muestraDes = new JTextArea(ar.getDescripcion());
			muestraDes.setLineWrap(true);
			muestraDes.setEnabled(false);
			muestraDes.setEditable(false);
			muestraDes.setBounds(10, 36, 207, 125);
			muestraDes.setBackground(new Color(128, 128, 128));
			muestraDes.setForeground(new Color(255, 255, 255));
			panel.add(muestraDes);
			panel.setBackground(customColor);
		}	
		panel_Comprar.setLayout(new GridLayout(0, 3));
		panel_Comprar.setBackground(customColor);
		carrito.setPrecioTotal(carrito.calcularPrecioTotal());
		verPrecio.setText("Precio del carrito: "+carrito.getPrecioTotal());
		return panel_Comprar;
	}
	/**El panel en el que se mostraran los carritos que las personas
	 * estan utilizando.
	 * @param muestraArticulos Articulos a mostrar en el panel.
	 * @param carrito El carrito de la persona.
	 * @param verPrecio Para cambiar el precio total
	 * @return El panel con todos los datos.
	 */
	public JPanel mostrarCarrito(ArrayList<Articulo> muestraArticulos,Compra carrito,JLabel verPrecio) {
		JPanel panel_Comprar = new JPanel();
		Comida co=null;
		Ropa ro=null;
		Herramienta he=null;
		int contador=0;
		Color customColor = new Color(18,20,28); 
		for(Articulo ar:muestraArticulos) {
			ImageIcon imagenIcono = new ImageIcon(".\\Imagenes\\"+ar.getRutaImagen());
			Image imagen = imagenIcono.getImage();
			Image imagenRedimensionada = imagen.getScaledInstance(146, 132, java.awt.Image.SCALE_SMOOTH);
			ImageIcon imagenRedimensionadaIcono = new ImageIcon(imagenRedimensionada);
			JLabel lblImagenAr = new JLabel(imagenRedimensionadaIcono);
			lblImagenAr.setBounds(0, 0, 152, 152);
			panel_Comprar.add(lblImagenAr);
						
			JPanel panelAtributos = new JPanel();
			panelAtributos.setBounds(152, 0, 248, 152);
			panel_Comprar.add(panelAtributos);
			panelAtributos.setLayout(null);
			
			JLabel lblNombreAr = new JLabel("Nombre: "+ar.getNombreArticulo());
			lblNombreAr.setForeground(new Color(255, 255, 255));
			lblNombreAr.setBounds(10, 0, 207, 14);
			panelAtributos.add(lblNombreAr);
			
			JLabel lblPrecioAr = new JLabel("Precio: "+ar.getPrecio());
			lblPrecioAr.setForeground(new Color(255, 255, 255));
			lblPrecioAr.setBounds(10, 11, 207, 14);
			panelAtributos.add(lblPrecioAr);
			
			JLabel lblStockAr = new JLabel("Stock: "+ar.getStockActual());
			lblStockAr.setForeground(new Color(255, 255, 255));
			lblStockAr.setBounds(10, 25, 207, 14);
			panelAtributos.add(lblStockAr);
			if(ar instanceof Comida) {
				co=(Comida)ar;
			JLabel lblAtributoDos = new JLabel("Procedencia :"+co.getProcedencia());
			lblAtributoDos.setForeground(new Color(255, 255, 255));
			lblAtributoDos.setBounds(10, 36, 207, 14);
			panelAtributos.add(lblAtributoDos);
			
			JLabel lblAtributoUno = new JLabel("Fecha de caducidad: "+co.getFechaCaducidad());
			lblAtributoUno.setForeground(new Color(255, 255, 255));
			lblAtributoUno.setBounds(10, 50, 207, 14);
			panelAtributos.add(lblAtributoUno);
			}
			if(ar instanceof Ropa) {
				ro=(Ropa)ar;
				JLabel lblAtributoDos = new JLabel("Talla :"+ro.getTalla());
				lblAtributoDos.setForeground(new Color(255, 255, 255));
				lblAtributoDos.setBounds(10, 36, 207, 14);
				panelAtributos.add(lblAtributoDos);
				
				JLabel lblAtributoUno = new JLabel("Marca: "+ro.getMarca());
				lblAtributoUno.setForeground(new Color(255, 255, 255));
				lblAtributoUno.setBounds(10, 50, 207, 14);
				panelAtributos.add(lblAtributoUno);
			}
			if(ar instanceof Herramienta) {
				he=(Herramienta)ar;
				JLabel lblAtributoDos = new JLabel("Electrica :"+he.getElectrica());
				lblAtributoDos.setForeground(new Color(255, 255, 255));
				lblAtributoDos.setBounds(10, 36, 207, 14);
				panelAtributos.add(lblAtributoDos);
				
				JLabel lblAtributoUno = new JLabel("Garantia: "+he.getGarantia());
				lblAtributoUno.setForeground(new Color(255, 255, 255));
				lblAtributoUno.setBounds(10, 50, 207, 14);
				panelAtributos.add(lblAtributoUno);
			}
				
			JSpinner cantidad = new JSpinner();
			cantidad.setBounds(123, 70, 39, 20);
			panelAtributos.add(cantidad);
			cantidad.setModel(new SpinnerNumberModel(carrito.getListaCantidades().get(contador).getCantidad(), 1, carrito.getListaCantidades().get(contador).getCantidad(), 1));
			
			JButton btnCogerArticulo = new JButton("Cambiar:");
			btnCogerArticulo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					carrito.cambiarArticulo(ar, (Integer)cantidad.getValue());
					carrito.setPrecioTotal(carrito.calcularPrecioTotal());
					verPrecio.setText("Precio del carrito: "+carrito.getPrecioTotal());
				}
			});
			btnCogerArticulo.setBounds(10, 69, 95, 23);
			btnCogerArticulo.setForeground(new Color(192, 192, 192));
			btnCogerArticulo.setBackground(new Color(0,76,255));
			panelAtributos.add(btnCogerArticulo);
			panelAtributos.setBackground(customColor);
			
			JPanel panel = new JPanel();
			panel.setBounds(402, 0, 248, 152);
			panel_Comprar.add(panel);
			panel.setLayout(null);
			
			JLabel lblDescripcionAr = new JLabel("Descripcion:");
			lblDescripcionAr.setForeground(new Color(255, 255, 255));
			lblDescripcionAr.setBounds(10, 11, 175, 14);
			panel.add(lblDescripcionAr);
			panel.setBackground(customColor);
			
			JTextArea muestraDes = new JTextArea(ar.getDescripcion());
			muestraDes.setLineWrap(true);
			muestraDes.setEnabled(false);
			muestraDes.setEditable(false);
			muestraDes.setBackground(new Color(128, 128, 128));
			muestraDes.setForeground(new Color(255, 255, 255));
			muestraDes.setBounds(10, 36, 207, 125);
			panel.add(muestraDes);
			contador++;
		}	
		carrito.setPrecioTotal(carrito.calcularPrecioTotal());
		verPrecio.setText("Precio del carrito: "+carrito.getPrecioTotal());
		
		panel_Comprar.setBackground(customColor);
		panel_Comprar.setLayout(new GridLayout(0, 3));
		return panel_Comprar;
	}
}