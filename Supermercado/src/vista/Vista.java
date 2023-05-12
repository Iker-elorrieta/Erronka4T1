     package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JTextField;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controlador.*;
import excepciones.ErroresDeLogin;
import excepciones.ErroresDeOperaciones;
import excepciones.ErroresDeRegistro;
import gestores.GestorArticulo;
import gestores.GestorArticuloComprado;
import gestores.GestorCompra;
import gestores.GestorPersona;
import gestores.GestorSeccion;
import gestores.GestorSupermercado;
import modelo.*;
import otros.DateLabelFormatter;
import otros.tipoArticulo;
import otros.tipoPersona;
import referencias.CONEXION;

import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Properties;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;

public class Vista {

	private JFrame frame;
	private JTextField cemail;
	private JPasswordField campoContrasena;
	private JTextField textDNI;
	private JTextField textNombre;
	private JTextField textApellidos;
	private JPasswordField contrasenaRegi;
	private JTextField textMail;
	private JDatePickerImpl datePicker;
	private JDatePickerImpl datePicker_1;
	private JDatePickerImpl datePicker_2;
	private JDatePickerImpl datePicker_3;
	private JDatePickerImpl datePicker_4;
	private JTextField textCNombre;
	private JTextField textCApellidos;
	private JTextField textCEmail;
	private JPasswordField passCContrasena;
	private JTextField textDineroActual;
	private JTextField textDineroExtra;
	private JTextField textDNIJefe;
	private JTextField textNombreJefe;
	private JTextField textApellidosJefe;
	private JTextField textGmailJefe;
	private JPasswordField passJefe;
	private JTextField textCodigoSuper;
	private JTextField textEmpresa;
	private JTextField textDireccion;
	private JTextField textPrimeraSe;
	private JTextField textSegundaSe;
	private JTextField textTerceraSe;
	private JTextField textNombreArticulo;
	private JTextField textImagen;
	private JTextField textPrecio;
	private JTextField textTalla;
	private JTextField textMarca;
	private JTextField textProcedencia;
	private JTextField textBuscador;
	
	private JTable tabla;
	private JTable tablaApoyo;
	
	private Connection conexion;
	
	private Comida nuevaComida;
	private Ropa nuevaRopa;
	private Herramienta nuevaHerramienta;
	private Seccion insercion;
	private Jefe nuevoJefe;
	private Supermercado supermercado;
	private Seccion seccion;
	private ArticuloComprado arDevolver;
	private Compra devolucion=new Compra();
	
	private Compra carrito=new Compra();
	private Jefe admin;
	private Persona login;
	private Cliente cliente;
	private Supermercado su;
	
	private int manejaCambio=0;
	private int cuentaSecciones=0;
	private Boolean cambios=true;
	private String [] Tipos={"Comida","Herramienta","Ropa"};
	
	private ArrayList<Articulo> listaArticulos;
	private ArrayList<Articulo> articulosCarrito=new ArrayList<Articulo>();
	private ArrayList<Persona> usuarios;
	private ArrayList<Supermercado> supermercados;
	private ArrayList<Seccion> seccionesDelSuper;
	private ArrayList<Compra> historial;
	private ArrayList<ArticuloComprado> historialArticulosComprados;
	
	private Metodos mc=new Metodos();
	private MetodosVista cv = new MetodosVista();
	private GestorPersona gp=new GestorPersona();
	private GestorSupermercado gsm=new GestorSupermercado();
	private GestorSeccion gs=new GestorSeccion();
	private GestorArticulo ga=new GestorArticulo();
	private GestorCompra gc=new GestorCompra();
	private GestorArticuloComprado gac=new GestorArticuloComprado();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vista window = new Vista();
					if(!(window.frame==null)) {
					window.frame.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Vista() {
			initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		try {
			conexion= (Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		} catch (SQLException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		try {
			usuarios=gp.cargarPersonas(conexion);
			supermercados=gsm.cargarSupermercados(conexion);
			listaArticulos=ga.cargarArticulos(conexion);
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
		}
		
		frame = new JFrame();
		frame.setBounds(100, 100, 740, 492);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane paneles = new JTabbedPane(JTabbedPane.TOP);
		paneles.setBounds(0, 0, 724, 453);
		frame.getContentPane().add(paneles);
		
		JPanel panel_Bienvenido = new JPanel();
		paneles.addTab("Primera", null, panel_Bienvenido, null);
		panel_Bienvenido.setLayout(null);
		Color customColor = new Color(18,20,28); 
		panel_Bienvenido.setBackground(customColor);
		
		JButton btnIniSes = new JButton("Iniciar sesion");
		btnIniSes.setForeground(new Color(192, 192, 192));
		btnIniSes.setBackground(new Color(0,76,255));
		btnIniSes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paneles.setSelectedIndex(1);
			}
		});
		btnIniSes.setBounds(263, 181, 127, 23);
		panel_Bienvenido.add(btnIniSes);
		
		JButton btnRegis = new JButton("Registrarse");
		btnRegis.setForeground(new Color(192, 192, 192));
		btnRegis.setBackground(new Color(0,76,255));
		btnRegis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paneles.setSelectedIndex(2);
			}
		});
		btnRegis.setBounds(263, 252, 127, 23);
		panel_Bienvenido.add(btnRegis);
		
		JLabel lblMensajeIni = new JLabel("Bienvenido al Super Elorrieta!");
		lblMensajeIni.setForeground(new Color(255, 255, 255));
		lblMensajeIni.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblMensajeIni.setBounds(74, 31, 536, 81);
		panel_Bienvenido.add(lblMensajeIni);
		
		JLabel lblIniSes = new JLabel("Pulse en este botón para acceder a su cuenta.");
		lblIniSes.setForeground(new Color(255, 255, 255));
		lblIniSes.setBounds(214, 156, 283, 14);
		panel_Bienvenido.add(lblIniSes);
		
		JLabel lblCreaCuen = new JLabel("Pulse este si quiere crear una cuenta.");
		lblCreaCuen.setForeground(new Color(255, 255, 255));
		lblCreaCuen.setBounds(227, 227, 239, 14);
		panel_Bienvenido.add(lblCreaCuen);
		
		JPanel panel_IniciarSesion = new JPanel();
		paneles.addTab("Segundo", null, panel_IniciarSesion, null);
		panel_IniciarSesion.setLayout(null);
		panel_IniciarSesion.setBackground(customColor);
		
		cemail = new JTextField();
		cemail.setForeground(new Color(255, 255, 255));
		cemail.setBackground(new Color(128, 128, 128));
		cemail.setBounds(231, 107, 181, 20);
		panel_IniciarSesion.add(cemail);
		cemail.setColumns(10);
		
		JLabel lblemail = new JLabel("E-mail:");
		lblemail.setForeground(new Color(255, 255, 255));
		lblemail.setBounds(24, 110, 46, 14);
		panel_IniciarSesion.add(lblemail);
		
		JLabel lblContra = new JLabel("Contraseña:");
		lblContra.setForeground(new Color(255, 255, 255));
		lblContra.setBounds(24, 206, 72, 14);
		panel_IniciarSesion.add(lblContra);
		
		campoContrasena = new JPasswordField();
		campoContrasena.setForeground(new Color(255, 255, 255));
		campoContrasena.setBackground(new Color(128, 128, 128));
		campoContrasena.setBounds(231, 203, 181, 20);
		panel_IniciarSesion.add(campoContrasena);
		
		JPanel panel_PerfilUtilidades = new JPanel();
		panel_PerfilUtilidades.setForeground(new Color(192, 192, 192));
		UtilDateModel modelo = new UtilDateModel();
		Properties po = new Properties();
		JDatePanelImpl datePanelo = new JDatePanelImpl(modelo, po);
		datePicker_1 = new JDatePickerImpl(datePanelo, new DateLabelFormatter());
		datePicker_1.getJFormattedTextField().setForeground(new Color(255, 255, 255));
		datePicker_1.getJFormattedTextField().setBackground(new Color(128, 128, 128));
		datePicker_1.getJFormattedTextField().setEnabled(false);
		datePicker_1.setBounds(156, 104, 125, 20);
		panel_PerfilUtilidades.add(datePicker_1);
		datePicker_1.setEnabled(false);
		datePicker_1.setForeground(new Color(255, 255, 255));
		datePicker_1.setBackground(new Color(128, 128, 128));
		panel_PerfilUtilidades.setBackground(customColor);
		
		JLabel lblModifica = new JLabel("Modificaciones");
		lblModifica.setForeground(new Color(255, 255, 255));
		JButton btnAnadirSecciones = new JButton("Secciones");
		btnAnadirSecciones.setForeground(new Color(192, 192, 192));
		btnAnadirSecciones.setBackground(new Color(255, 255, 0));
		JButton btnPaginaSuper = new JButton("Supermercado");
		btnPaginaSuper.setForeground(new Color(192, 192, 192));
		btnPaginaSuper.setBackground(new Color(255, 255, 0));
		JButton btnInfo = new JButton("Datos");
		btnInfo.setForeground(new Color(192, 192, 192));
		btnInfo.setBackground(new Color(255, 255, 0));
		JLabel lblCrear = new JLabel("Creacion");
		lblCrear.setForeground(new Color(255, 255, 255));
		JButton btnCreacion = new JButton("Administrador");
		btnCreacion.setForeground(new Color(192, 192, 192));
		btnCreacion.setBackground(new Color(255, 255, 0));
		JButton btnAnadirArticulo = new JButton("Articulos");
		btnAnadirArticulo.setForeground(new Color(192, 192, 192));
		btnAnadirArticulo.setBackground(new Color(255, 255, 0));
		JLabel lblSaludo = new JLabel("");
		lblSaludo.setForeground(new Color(255, 255, 255));
		JLabel lblErrorInicioSesion = new JLabel("");
		JButton btnIniciar = new JButton("Iniciar sesion");
		btnIniciar.setForeground(new Color(192, 192, 192));
		btnIniciar.setBackground(new Color(0,76,255));
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					login=gp.iniciarSesion(usuarios,cemail.getText(),String.valueOf(campoContrasena.getPassword()));
					lblErrorInicioSesion.setText("");
					cemail.setText("");
					campoContrasena.setText("");
					lblSaludo.setText("Saludos "+login.getNombre());
					textCNombre.setText(login.getNombre());
					textCEmail.setText(login.getEmail());
					textCApellidos.setText(login.getApellidos());
					datePicker_1.getJFormattedTextField().setText(login.getFechaNacimiento());
					if(login.getTipo().equals(tipoPersona.Cliente)) {
						cliente=(Cliente) login;
						if(cliente.isBloqueado()) {
							JOptionPane.showMessageDialog(null,"Estas bloqueado, no puedes iniciar sesion");
							frame.dispose();
						}else {
							lblCrear.setVisible(false);
							btnCreacion.setVisible(false);
							btnAnadirArticulo.setVisible(false);
							btnPaginaSuper.setVisible(false);
							btnAnadirSecciones.setVisible(false);
							btnInfo.setVisible(false);
							paneles.setSelectedIndex(3);
							lblModifica.setVisible(false);
					textDineroActual.setText(String.valueOf(cliente.getDinero()));
						}
					}else {
						admin=(Jefe) login;
						lblCrear.setVisible(true);
						btnCreacion.setVisible(true);
						btnAnadirArticulo.setVisible(true);
						btnInfo.setVisible(true);
						btnPaginaSuper.setVisible(true);
						btnAnadirSecciones.setVisible(true);
						btnInfo.setVisible(true);
						lblModifica.setVisible(true);
						paneles.setSelectedIndex(3);
					}
				} catch (ErroresDeLogin e1) {
					// TODO Auto-generated catch block
					lblErrorInicioSesion.setText(e1.getMessage());
				}
			}
		});
		btnIniciar.setBounds(251, 309, 141, 23);
		panel_IniciarSesion.add(btnIniciar);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.setForeground(new Color(192, 192, 192));
		btnAtras.setBackground(new Color(0,76,255));
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paneles.setSelectedIndex(0);
				cemail.setText("");
				campoContrasena.setText("");
			}
		});
		btnAtras.setBounds(24, 391, 89, 23);
		panel_IniciarSesion.add(btnAtras);
		
		lblErrorInicioSesion.setForeground(new Color(255, 0, 0));
		lblErrorInicioSesion.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErrorInicioSesion.setBounds(239, 264, 386, 14);
		panel_IniciarSesion.add(lblErrorInicioSesion);
		
		JPanel panel_Registrarse = new JPanel();
		paneles.addTab("Tercera", null, panel_Registrarse, null);
		panel_Registrarse.setLayout(null);
		panel_Registrarse.setBackground(customColor);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setForeground(new Color(255, 255, 255));
		lblDni.setBounds(10, 22, 46, 14);
		panel_Registrarse.add(lblDni);
		
		textDNI = new JTextField();
		textDNI.setBounds(212, 19, 188, 20);
		panel_Registrarse.add(textDNI);
		textDNI.setColumns(10);
		textDNI.setForeground(new Color(255, 255, 255));
		textDNI.setBackground(new Color(128, 128, 128));
		
		textNombre = new JTextField();
		textNombre.setBounds(212, 101, 188, 20);
		panel_Registrarse.add(textNombre);
		textNombre.setColumns(10);
		textNombre.setForeground(new Color(255, 255, 255));
		textNombre.setBackground(new Color(128, 128, 128));
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setForeground(new Color(255, 255, 255));
		lblNombre.setBounds(10, 104, 68, 14);
		panel_Registrarse.add(lblNombre);
		
		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setForeground(new Color(255, 255, 255));
		lblApellidos.setBounds(10, 150, 68, 14);
		panel_Registrarse.add(lblApellidos);
		
		textApellidos = new JTextField();
		textApellidos.setBounds(212, 147, 188, 20);
		panel_Registrarse.add(textApellidos);
		textApellidos.setColumns(10);
		textApellidos.setForeground(new Color(255, 255, 255));
		textApellidos.setBackground(new Color(128, 128, 128));
		
		JLabel lblFechaNa = new JLabel("Fecha de nacimiento:");
		lblFechaNa.setForeground(new Color(255, 255, 255));
		lblFechaNa.setBounds(10, 198, 127, 14);
		panel_Registrarse.add(lblFechaNa);
		
		JLabel lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setForeground(new Color(255, 255, 255));
		lblContrasena.setBounds(10, 243, 127, 14);
		panel_Registrarse.add(lblContrasena);
		
		contrasenaRegi = new JPasswordField();
		contrasenaRegi.setBounds(212, 240, 188, 20);
		contrasenaRegi.setForeground(new Color(255, 255, 255));
		contrasenaRegi.setBackground(new Color(128, 128, 128));
		panel_Registrarse.add(contrasenaRegi);
		
		JButton btnAtra1 = new JButton("Atras");
		btnAtra1.setForeground(new Color(192, 192, 192));
		btnAtra1.setBackground(new Color(0,76,255));
		btnAtra1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paneles.setSelectedIndex(0);
				contrasenaRegi.setText("");
				textApellidos.setText("");
				datePicker.getJFormattedTextField().setText("");
				textDNI.setText("");
				textNombre.setText("");
				textMail.setText("");
			}
		});
		btnAtra1.setBounds(10, 391, 89, 23);
		panel_Registrarse.add(btnAtra1);
		
		UtilDateModel model = new UtilDateModel();
		//model.setDate(2022, 5, 6);
		Properties p = new Properties();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.getJFormattedTextField().setForeground(new Color(255, 255, 255));
		datePicker.getJFormattedTextField().setBackground(new Color(128, 128, 128));
		datePicker.setBounds(212, 195, 188, 20);
		datePicker.setForeground(new Color(255, 255, 255));
		datePicker.setBackground(new Color(128, 128, 128));
		panel_Registrarse.add(datePicker);

		JLabel lblErroresLogin = new JLabel("");
		JButton btnRegistrarse1 = new JButton("Registrarse");
		btnRegistrarse1.setForeground(new Color(192, 192, 192));
		btnRegistrarse1.setBackground(new Color(0,76,255));
		btnRegistrarse1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					gp.comprobarCampos(textNombre.getText(),textApellidos.getText(),
					String.valueOf(contrasenaRegi.getPassword()), textDNI.getText()
					, datePicker.getJFormattedTextField().getText(), textMail.getText());
					
					gp.comprobarNacimiento(datePicker.getJFormattedTextField().getText());
					gp.comprobarEmail(textMail.getText());
					gp.comprobarDNI(textDNI.getText());
					Cliente cli;
					try {
						cli = new Cliente(textDNI.getText(),textNombre.getText(),textApellidos.getText(),
						 mc.deStringADate(datePicker.getJFormattedTextField().getText()), textMail.getText(),
						 String.valueOf(contrasenaRegi.getPassword()),tipoPersona.Cliente, (float)0,0);
						gp.insertarPersona(mc,conexion,cli);
						usuarios=gp.cargarPersonas(conexion);
						paneles.setSelectedIndex(1);
					} catch (ParseException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
					contrasenaRegi.setText("");
					textApellidos.setText("");
					datePicker.getJFormattedTextField().setText("");
					textDNI.setText("");
					textNombre.setText("");
					textMail.setText("");
					lblErroresLogin.setText("");
				} catch (ErroresDeRegistro e1) {
					// TODO Auto-generated catch block
					lblErroresLogin.setText(e1.getMessage());
				}
			}
		});
		btnRegistrarse1.setBounds(255, 339, 106, 23);
		panel_Registrarse.add(btnRegistrarse1);
		
		JLabel lblEmail = new JLabel("E-Mail:");
		lblEmail.setForeground(new Color(255, 255, 255));
		lblEmail.setBounds(10, 62, 46, 14);
		panel_Registrarse.add(lblEmail);
		
		textMail = new JTextField();
		textMail.setBounds(212, 59, 188, 20);
		panel_Registrarse.add(textMail);
		textMail.setColumns(10);
		textMail.setForeground(new Color(255, 255, 255));
		textMail.setBackground(new Color(128, 128, 128));
		
		lblErroresLogin.setForeground(new Color(255, 0, 0));
		lblErroresLogin.setBounds(212, 286, 188, 14);
		panel_Registrarse.add(lblErroresLogin);
		
		paneles.addTab("Cuarta", null, panel_PerfilUtilidades, null);
		panel_PerfilUtilidades.setLayout(null);
		
		lblSaludo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSaludo.setBounds(323, 11, 226, 14);
		panel_PerfilUtilidades.add(lblSaludo);
		
		JButton btnCerrarSesion = new JButton("Cerrar Sesion");
		btnCerrarSesion.setForeground(new Color(192, 192, 192));
		btnCerrarSesion.setBackground(new Color(0,76,255));
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login=null;
				paneles.setSelectedIndex(0);
				lblSaludo.setText("");
				textCNombre.setText("");
				textCEmail.setText("");
				textCApellidos.setText("");
				datePicker_1.getJFormattedTextField().setText("");
			}
		});
		btnCerrarSesion.setBounds(10, 391, 119, 23);
		panel_PerfilUtilidades.add(btnCerrarSesion);
		
		JButton btnBorrarPerfil = new JButton("Borrar Perfil");
		btnBorrarPerfil.setForeground(new Color(192, 192, 192));
		btnBorrarPerfil.setBackground(new Color(0,76,255));
		btnBorrarPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog (null, "¿Estas seguro?","AVISO", 0)==0) {
					try {
						gp.darseBajaPersona(conexion,login);
						paneles.setSelectedIndex(0);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnBorrarPerfil.setBounds(171, 391, 110, 23);
		panel_PerfilUtilidades.add(btnBorrarPerfil);
		
		JLabel lblErroresCambio = new JLabel("");
		JButton btnCambiarPerfil = new JButton("Cambiar Perfil");
		btnCambiarPerfil.setForeground(new Color(192, 192, 192));
		btnCambiarPerfil.setBackground(new Color(0,76,255));
		btnCambiarPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		if(cambios) {
			JOptionPane.showMessageDialog(null, "Para realizar los cambios en su perfil, primero le permitiremos cambiar sus datos.\r\n Tras esto pulse de nuevo este botón para confirmar los cambios");
			textCNombre.setEnabled(true);
			textCApellidos.setEnabled(true);
			passCContrasena.setEnabled(true);
			datePicker_1.setEnabled(true);
			textCEmail.setEnabled(true);
			cambios=false;
		}else {
			try {
				cliente.setNombre(textCNombre.getText());
				cliente.setApellidos(textCApellidos.getText());
				cliente.setContrasena(String.valueOf(passCContrasena.getPassword()));
				cliente.setEmail(textCEmail.getText());
				cliente.setFechaNacimiento(mc.deStringADate(datePicker_1.getJFormattedTextField().getText()));
				gp.comprobarCampos(cliente.getNombre(),cliente.getApellidos() , cliente.getContrasena(),cliente.getDni(), cliente.getFechaNacimiento(), cliente.getEmail());
				gp.cambiarPerfilCliente(mc,conexion,cliente);
				textCNombre.setEnabled(false);
				textCApellidos.setEnabled(false);
				passCContrasena.setEnabled(false);
				datePicker_1.setEnabled(false);
				textCEmail.setEnabled(false);
				cambios=true;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "No se pudo conectar a la BBDD o la operación no se pudo realizar.");
			} catch (ErroresDeRegistro e1) {
				// TODO Auto-generated catch block
				lblErroresCambio.setText(e1.getMessage());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				lblErroresCambio.setText("No se pudieron transformar los datos.");
			}
			}
			}
		});
		btnCambiarPerfil.setBounds(323, 391, 119, 23);
		panel_PerfilUtilidades.add(btnCambiarPerfil);
		
		JLabel lblCNombre = new JLabel("Nombre:");
		lblCNombre.setForeground(new Color(255, 255, 255));
		lblCNombre.setBounds(10, 36, 55, 14);
		panel_PerfilUtilidades.add(lblCNombre);
		
		JLabel lblCApellidos = new JLabel("Apellidos:");
		lblCApellidos.setForeground(new Color(255, 255, 255));
		lblCApellidos.setBounds(10, 61, 46, 14);
		panel_PerfilUtilidades.add(lblCApellidos);
		
		JLabel lblCGmail = new JLabel("E-mail:");
		lblCGmail.setForeground(new Color(255, 255, 255));
		lblCGmail.setBounds(10, 85, 46, 14);
		panel_PerfilUtilidades.add(lblCGmail);
		
		JLabel lblCFechaNacimiento = new JLabel("Fecha de Nacimiento:");
		lblCFechaNacimiento.setForeground(new Color(255, 255, 255));
		lblCFechaNacimiento.setBounds(10, 110, 136, 14);
		panel_PerfilUtilidades.add(lblCFechaNacimiento);
		
		JLabel lblCContrasena = new JLabel("Contrasena:");
		lblCContrasena.setForeground(new Color(255, 255, 255));
		lblCContrasena.setBounds(10, 135, 89, 14);
		panel_PerfilUtilidades.add(lblCContrasena);
		
		textCNombre = new JTextField();
		textCNombre.setForeground(new Color(255, 255, 255));
		textCNombre.setBackground(new Color(128, 128, 128));
		textCNombre.setEnabled(false);
		textCNombre.setBounds(156, 33, 125, 20);
		panel_PerfilUtilidades.add(textCNombre);
		textCNombre.setColumns(10);
		
		
		textCApellidos = new JTextField();
		textCApellidos.setForeground(new Color(255, 255, 255));
		textCApellidos.setBackground(new Color(128, 128, 128));
		textCApellidos.setEnabled(false);
		textCApellidos.setBounds(156, 58, 125, 20);
		panel_PerfilUtilidades.add(textCApellidos);
		textCApellidos.setColumns(10);
		
		textCEmail = new JTextField();
		textCEmail.setForeground(new Color(255, 255, 255));
		textCEmail.setBackground(new Color(128, 128, 128));
		textCEmail.setEnabled(false);
		textCEmail.setBounds(156, 82, 125, 20);
		panel_PerfilUtilidades.add(textCEmail);
		textCEmail.setColumns(10);
		
		passCContrasena = new JPasswordField();
		passCContrasena.setForeground(new Color(255, 255, 255));
		passCContrasena.setBackground(new Color(128, 128, 128));
		passCContrasena.setEnabled(false);
		passCContrasena.setBounds(156, 132, 125, 20);
		panel_PerfilUtilidades.add(passCContrasena);
		
		JLabel lblAumentarDinero = new JLabel("Cartera");
		lblAumentarDinero.setForeground(new Color(255, 255, 255));
		lblAumentarDinero.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAumentarDinero.setBounds(63, 196, 66, 14);
		panel_PerfilUtilidades.add(lblAumentarDinero);
		
		JLabel lblDinero = new JLabel("Dinero actual:");
		lblDinero.setForeground(new Color(255, 255, 255));
		lblDinero.setBounds(10, 221, 89, 14);
		panel_PerfilUtilidades.add(lblDinero);
		
		textDineroActual = new JTextField();
		textDineroActual.setForeground(new Color(255, 255, 255));
		textDineroActual.setBackground(new Color(128, 128, 128));
		textDineroActual.setEnabled(false);
		textDineroActual.setBounds(116, 218, 86, 20);
		panel_PerfilUtilidades.add(textDineroActual);
		textDineroActual.setColumns(10);
		
		textDineroExtra = new JTextField();
		textDineroExtra.setForeground(new Color(255, 255, 255));
		textDineroExtra.setBackground(new Color(128, 128, 128));
		textDineroExtra.setBounds(116, 248, 86, 20);
		panel_PerfilUtilidades.add(textDineroExtra);
		textDineroExtra.setColumns(10);
		
		JLabel lblDineroExtra = new JLabel("Dinero extra:");
		lblDineroExtra.setForeground(new Color(255, 255, 255));
		lblDineroExtra.setBounds(10, 251, 89, 14);
		panel_PerfilUtilidades.add(lblDineroExtra);
		
		JButton btnSumarDinero = new JButton("Agregar");
		btnSumarDinero.setForeground(new Color(192, 192, 192));
		btnSumarDinero.setBackground(new Color(0,76,255));
		btnSumarDinero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					gp.AumentarDineroCliente(conexion,cliente, Integer.valueOf(textDineroExtra.getText()));
					cliente.setDinero(cliente.getDinero()+Integer.valueOf(textDineroExtra.getText()));
					textDineroExtra.setText("");
					textDineroActual.setText(String.valueOf(cliente.getDinero()));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					textDineroExtra.setText("");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					textDineroExtra.setText("");
				} catch (ErroresDeOperaciones e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		btnSumarDinero.setBounds(63, 276, 89, 23);
		panel_PerfilUtilidades.add(btnSumarDinero);
		
		lblErroresCambio.setForeground(new Color(255, 0, 0));
		lblErroresCambio.setBounds(10, 160, 303, 14);
		panel_PerfilUtilidades.add(lblErroresCambio);
		
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paneles.setSelectedIndex(4);
			}
		});
		btnInfo.setBounds(558, 101, 110, 23);
		panel_PerfilUtilidades.add(btnInfo);
		
		btnCreacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paneles.setSelectedIndex(5);
			}
		});
		btnCreacion.setBounds(377, 101, 134, 23);
		panel_PerfilUtilidades.add(btnCreacion);
		JComboBox<String> tipoArticuloCombo = new JComboBox<String>();
		JComboBox<String> suAnadirArticulo = new JComboBox<String>();
		suAnadirArticulo.setBackground(new Color(128, 128, 128));
		suAnadirArticulo.setForeground(new Color(255, 255, 255));
		btnAnadirArticulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paneles.setSelectedIndex(8);
				try {
					su=gsm.buscarSupermercado(conexion,admin);
					admin.setSupermercado(su);
					String [] cargaSuper=new String [1];
					cargaSuper[0]=admin.getSupermercado().getEmpresa();
					suAnadirArticulo.setModel(new DefaultComboBoxModel<String>(cargaSuper));
					tipoArticuloCombo.setModel(new DefaultComboBoxModel<String>(	mc.cargarNombreSeccion(admin.getSupermercado().getArraySecciones())));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAnadirArticulo.setBounds(377, 194, 134, 23);
		panel_PerfilUtilidades.add(btnAnadirArticulo);
		
		
		lblCrear.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCrear.setBounds(405, 59, 81, 14);
		panel_PerfilUtilidades.add(lblCrear);
		
		JLabel lblErrorSeleccion = new JLabel("");
		lblErrorSeleccion.setForeground(new Color(255, 0, 0));
		lblErrorSeleccion.setBounds(377, 251, 291, 14);
		panel_PerfilUtilidades.add(lblErrorSeleccion);
		
		JComboBox<String> escogeJefe = new JComboBox<String>();
		escogeJefe.setBackground(new Color(128, 128, 128));
		escogeJefe.setForeground(new Color(255, 255, 255));
		btnPaginaSuper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					escogeJefe.setModel(new DefaultComboBoxModel<String>(mc.cargarNombreJefe(gp.cargarJefesSinSupermercado(conexion,gsm.cargarSupermercados(conexion).size()))));
					paneles.setSelectedIndex(6);
					lblErrorSeleccion.setText("");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					lblErrorSeleccion.setText(e1.getMessage());
				} catch (ErroresDeOperaciones e1) {
					// TODO Auto-generated catch block
					lblErrorSeleccion.setText(e1.getMessage());
				}
				
			}
		});
		btnPaginaSuper.setBounds(377, 131, 134, 23);
		panel_PerfilUtilidades.add(btnPaginaSuper);
		
		JComboBox<String> escogeSuper = new JComboBox<String>();
		escogeSuper.setForeground(new Color(255, 255, 255));
		escogeSuper.setBackground(new Color(128, 128, 128));
		btnAnadirSecciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					escogeSuper.setModel(new DefaultComboBoxModel<String>(mc.cargarEmpresa(gsm.cargarSupermercados(conexion))));
					paneles.setSelectedIndex(7);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAnadirSecciones.setBounds(377, 165, 134, 23);
		panel_PerfilUtilidades.add(btnAnadirSecciones);
		
		lblModifica.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblModifica.setBounds(558, 61, 110, 14);
		panel_PerfilUtilidades.add(lblModifica);
		
		JButton btnComprar = new JButton("Comprar");
		btnComprar.setForeground(new Color(192, 192, 192));
		btnComprar.setBackground(new Color(0,76,255));
		btnComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					paneles.setSelectedIndex(9);
			}
		});
		btnComprar.setBounds(579, 391, 89, 23);
		panel_PerfilUtilidades.add(btnComprar);
		
		JScrollPane historialCompras = new JScrollPane();
		JButton btnVerHistorial = new JButton("Historial");
		btnVerHistorial.setForeground(new Color(192, 192, 192));
		btnVerHistorial.setBackground(new Color(0,76,255));
		btnVerHistorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					historial=gc.buscarComprasPersona(mc,conexion,login);
					tabla=cv.cargarHistorialCompras(login,mc.cargarHistorialCompras(conexion,historial));
					historialCompras.setViewportView(tabla);
					paneles.setSelectedIndex(10);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnVerHistorial.setBounds(467, 391, 89, 23);
		panel_PerfilUtilidades.add(btnVerHistorial);
		
		JPanel panel_Otros = new JPanel();
		panel_Otros.setLayout(null);
		panel_Otros.setBackground(customColor);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 39, 699, 238);
		panel_Otros.add(scrollPane);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setForeground(new Color(192, 192, 192));
		JTextArea cambiaDe = new JTextArea();
		cambiaDe.setForeground(new Color(255, 255, 255));
		cambiaDe.setBackground(new Color(128, 128, 128));
		JButton btnDescrip = new JButton("Descripcion");
		btnDescrip.setForeground(new Color(192, 192, 192));
		JButton btnBloquea = new JButton("Bloquear");
		btnBloquea.setForeground(new Color(192, 192, 192));
		JButton btnDesbloquea = new JButton("Desbloquear");
		btnDesbloquea.setForeground(new Color(192, 192, 192));
		paneles.addTab("Quinta", null, panel_Otros, null);
		JButton verArticulos = new JButton("Articulos");
		verArticulos.setForeground(new Color(192, 192, 192));
		verArticulos.setBackground(new Color(255, 0, 0));
		verArticulos.setBounds(479, 5, 89, 23);
		verArticulos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btnAceptar.setVisible(false);
					btnBloquea.setVisible(false);
					btnDesbloquea.setVisible(false);
					btnDescrip.setVisible(true);
					manejaCambio=3;
					tabla=cv.cargarTabla(mc.cargarArticulos(conexion,listaArticulos));
					scrollPane.setViewportView(tabla);
				} catch (SQLException ex1) {
					// TODO Auto-generated catch block
					ex1.getMessage();
				} catch (Exception ex3) {
					// TODO Auto-generated catch block
					ex3.getMessage();
				}
			}
		});
		panel_Otros.add(verArticulos);
		
		JButton btnVerUsuarios = new JButton("Usuarios");
		btnVerUsuarios.setForeground(new Color(192, 192, 192));
		btnVerUsuarios.setBackground(new Color(255, 0, 0));
		btnVerUsuarios.setBounds(10, 5, 89, 23);
		btnVerUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btnAceptar.setVisible(false);
				cambiaDe.setVisible(false);
				manejaCambio=1;
				tabla=cv.tablaUsuarios(mc.tablaUsuarios(conexion,usuarios));
				scrollPane.setViewportView(tabla);
				btnBloquea.setVisible(true);
				btnDesbloquea.setVisible(true);
				btnDescrip.setVisible(false);
				} catch (SQLException ex1) {
					// TODO Auto-generated catch block
					ex1.getMessage();
				} catch (Exception ex3) {
					// TODO Auto-generated catch block
					ex3.getMessage();
				}
			}
		});
		panel_Otros.add(btnVerUsuarios);
		
		JButton btnAtras1 = new JButton("Atras");
		btnAtras1.setForeground(new Color(192, 192, 192));
		btnAtras1.setBackground(new Color(0,76,255));
		btnAtras1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paneles.setSelectedIndex(3);
				btnBloquea.setVisible(false);
				btnDesbloquea.setVisible(false);
			}
		});
		btnAtras1.setBounds(10, 391, 89, 23);
		panel_Otros.add(btnAtras1);
		
		JButton btnVerSuper = new JButton("Supermercados");
		btnVerSuper.setForeground(new Color(192, 192, 192));
		btnVerSuper.setBackground(new Color(255, 0, 0));
		btnVerSuper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cambiaDe.setVisible(false);
					btnBloquea.setVisible(false);
					btnDesbloquea.setVisible(false);
					manejaCambio=2;
					/** Posible problema**/
					tabla=cv.tablaSupermercados(mc.cargaSuper(conexion,supermercados));
					scrollPane.setViewportView(tabla);
					btnDescrip.setVisible(false);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnVerSuper.setBounds(128, 5, 139, 23);
		panel_Otros.add(btnVerSuper);
		
		btnDesbloquea.setBackground(new Color(0,76,255));
		btnDesbloquea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cv.accionPorTabla(conexion,tabla, usuarios, false);
					btnVerUsuarios.doClick();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnDesbloquea.setBounds(138, 391, 113, 23);
		panel_Otros.add(btnDesbloquea);
		btnDesbloquea.setVisible(false);
		
		btnBloquea.setBackground(new Color(0,76,255));
		btnBloquea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cv.accionPorTabla(conexion,tabla, usuarios, true);
					btnVerUsuarios.doClick();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnBloquea.setBounds(471, 391, 97, 23);
		panel_Otros.add(btnBloquea);
		btnBloquea.setVisible(false);
		JButton btnVerSecciones = new JButton("Secciones");
		btnVerSecciones.setForeground(new Color(192, 192, 192));
		btnVerSecciones.setBackground(new Color(255, 0, 0));
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.setForeground(new Color(192, 192, 192));
		btnBorrar.setBackground(new Color(0,76,255));
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(manejaCambio==1) {
					cv.borrarPorTabla(conexion,tabla, usuarios);
					btnVerUsuarios.doClick();
					}
					if(manejaCambio==2) {
						cv.borrarSupermercadoTabla(conexion,tabla, gsm.cargarSupermercados(conexion));
						btnVerSuper.doClick();
					}
					if(manejaCambio==3) {
						cv.borrarArticuloTabla(conexion,tabla,ga.cargarArticulos(conexion));
					    verArticulos.doClick();
					}
					if(manejaCambio==4) {
						cv.borrarSeccionTabla(conexion,tabla, gs.cargarSecciones(conexion));
						btnVerSecciones.doClick();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnBorrar.setBounds(612, 391, 97, 23);
		panel_Otros.add(btnBorrar);
		
		btnVerSecciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btnAceptar.setVisible(false);
					cambiaDe.setVisible(false);
					btnBloquea.setVisible(false);
					btnDesbloquea.setVisible(false);
					manejaCambio=4;
					/** posible error**/
					tabla=cv.tablaSecciones(mc.cargaSecciones(conexion,seccionesDelSuper));
					scrollPane.setViewportView(tabla);
					btnDescrip.setVisible(false);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnVerSecciones.setBounds(327, 5, 103, 23);
		panel_Otros.add(btnVerSecciones);
		
		JButton btnCargarStocks = new JButton("Recargas");
		btnCargarStocks.setForeground(new Color(192, 192, 192));
		btnCargarStocks.setBackground(new Color(255, 0, 0));
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setForeground(new Color(192, 192, 192));
		btnModificar.setBackground(new Color(0,76,255));
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(manejaCambio==1) {
						usuarios=cv.realizarCambios(tabla, usuarios);
						btnVerUsuarios.doClick();
					}
					if(manejaCambio==2) {
						cv.modificarSupermercadoTabla(conexion,tabla, gsm.cargarSupermercados(conexion));
						btnVerSuper.doClick();
					}
					if(manejaCambio==3) {
						cv.modificarArticuloTabla(conexion,tabla,ga.cargarArticulos(conexion));
					    verArticulos.doClick();
					}
					if(manejaCambio==4) {
						cv.modificarSeccionTabla(conexion,tabla, gs.cargarSecciones(conexion));
						btnVerSecciones.doClick();
					}
					if(manejaCambio==5) {
						cv.recargarStocks(conexion,tabla, listaArticulos);
						btnCargarStocks.doClick();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ErroresDeOperaciones e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnModificar.setBounds(308, 391, 103, 23);
		panel_Otros.add(btnModificar);
		
		btnDescrip.setBackground(new Color(0,76,255));
		btnDescrip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btnAceptar.setVisible(true);
					cambiaDe.setVisible(true);
					cambiaDe.setText(cv.descripcion(tabla, listaArticulos));
					tabla.setEnabled(false);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		cambiaDe.setLineWrap(true);
		cambiaDe.setBounds(128, 288, 507, 92);
		cambiaDe.setVisible(false);
		panel_Otros.add(cambiaDe);
		
		btnDescrip.setBounds(10, 288, 103, 23);
		btnDescrip.setVisible(false);
		panel_Otros.add(btnDescrip);
		
		btnAceptar.setBackground(new Color(0,76,255));
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tabla=cv.anadirDescripcion(conexion,tabla,cambiaDe.getText(),listaArticulos);
					tabla.updateUI();
					btnAceptar.setVisible(false);
					cambiaDe.setText("");
					cambiaDe.setVisible(false);
					tabla.setEnabled(true);
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnAceptar.setBounds(10, 340, 103, 23);
		btnAceptar.setVisible(false);
		panel_Otros.add(btnAceptar);
		
		btnCargarStocks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btnAceptar.setVisible(false);
					btnBloquea.setVisible(false);
					btnDesbloquea.setVisible(false);
					btnDescrip.setVisible(false);
					manejaCambio=5;
					tabla=cv.tablaRecargArticulos(mc.cargarRecargaArticulos(conexion));
					scrollPane.setViewportView(tabla);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCargarStocks.setBounds(620, 5, 89, 23);
		panel_Otros.add(btnCargarStocks);
		
		UtilDateModel model2 = new UtilDateModel();
		Properties p2 = new Properties();
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p2);
		UtilDateModel model3 = new UtilDateModel();
		Properties p3 = new Properties();
		JDatePanelImpl datePanel3 = new JDatePanelImpl(model3, p3);
		
		JPanel panel_Admin = new JPanel();
		paneles.addTab("Sexta", null, panel_Admin, null);
		panel_Admin.setLayout(null);
		panel_Admin.setBackground(customColor);
		
		datePicker_3 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		datePicker_3.getJFormattedTextField().setForeground(new Color(255, 255, 255));
		datePicker_3.getJFormattedTextField().setBackground(new Color(128, 128, 128));
		datePicker_3.getJFormattedTextField().setEnabled(false);
		datePicker_3.setBounds(295, 145, 125, 20);
		datePicker_3.setForeground(new Color(255, 255, 255));
		datePicker_3.setBackground(new Color(128, 128, 128));
		panel_Admin.add(datePicker_3);
		datePicker_2 = new JDatePickerImpl(datePanel3, new DateLabelFormatter());
		datePicker_2.getJFormattedTextField().setBackground(new Color(128, 128, 128));
		datePicker_2.getJFormattedTextField().setForeground(new Color(255, 255, 255));
		datePicker_2.setForeground(new Color(255, 255, 255));
		datePicker_2.setBackground(new Color(128, 128, 128));
		datePicker_2.getJFormattedTextField().setEnabled(false);
		datePicker_2.setBounds(295, 176, 125, 20);
		panel_Admin.add(datePicker_2);
		
		textDNIJefe = new JTextField();
		textDNIJefe.setForeground(new Color(255, 255, 255));
		textDNIJefe.setBackground(new Color(128, 128, 128));
		textDNIJefe.setBounds(305, 24, 115, 20);
		panel_Admin.add(textDNIJefe);
		textDNIJefe.setColumns(10);
		
		JLabel lblDniJ = new JLabel("DNI:");
		lblDniJ.setForeground(new Color(255, 255, 255));
		lblDniJ.setBounds(192, 27, 77, 14);
		panel_Admin.add(lblDniJ);
		
		textNombreJefe = new JTextField();
		textNombreJefe.setForeground(new Color(255, 255, 255));
		textNombreJefe.setBackground(new Color(128, 128, 128));
		textNombreJefe.setBounds(305, 55, 115, 20);
		panel_Admin.add(textNombreJefe);
		textNombreJefe.setColumns(10);
		
		JLabel lblCrearAdmin = new JLabel("Creacion del Administrador");
		lblCrearAdmin.setForeground(new Color(255, 255, 255));
		lblCrearAdmin.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCrearAdmin.setBounds(240, 2, 213, 14);
		panel_Admin.add(lblCrearAdmin);
		
		JLabel lblNombreJefe = new JLabel("Nombre:");
		lblNombreJefe.setForeground(new Color(255, 255, 255));
		lblNombreJefe.setBounds(175, 58, 77, 14);
		panel_Admin.add(lblNombreJefe);
		
		JLabel lblApellidosJefe = new JLabel("Apellidos:");
		lblApellidosJefe.setForeground(new Color(255, 255, 255));
		lblApellidosJefe.setBounds(165, 89, 77, 14);
		panel_Admin.add(lblApellidosJefe);
		
		textApellidosJefe = new JTextField();
		textApellidosJefe.setForeground(new Color(255, 255, 255));
		textApellidosJefe.setBackground(new Color(128, 128, 128));
		textApellidosJefe.setBounds(305, 86, 115, 20);
		panel_Admin.add(textApellidosJefe);
		textApellidosJefe.setColumns(10);
		
		textGmailJefe = new JTextField();
		textGmailJefe.setForeground(new Color(255, 255, 255));
		textGmailJefe.setBackground(new Color(128, 128, 128));
		textGmailJefe.setBounds(305, 117, 115, 20);
		panel_Admin.add(textGmailJefe);
		textGmailJefe.setColumns(10);
		
		JLabel lblGmailJefe = new JLabel("E-mail:");
		lblGmailJefe.setForeground(new Color(255, 255, 255));
		lblGmailJefe.setBounds(179, 120, 46, 14);
		panel_Admin.add(lblGmailJefe);
		
		JLabel lblFechaNacimientoJefe = new JLabel("Fecha de nacimiento:");
		lblFechaNacimientoJefe.setForeground(new Color(255, 255, 255));
		lblFechaNacimientoJefe.setBounds(111, 151, 131, 14);
		panel_Admin.add(lblFechaNacimientoJefe);
		
		JLabel lblFechaAdquisicion = new JLabel("Fecha adquisicion:");
		lblFechaAdquisicion.setForeground(new Color(255, 255, 255));
		lblFechaAdquisicion.setBounds(121, 176, 131, 14);
		panel_Admin.add(lblFechaAdquisicion);
		
		JLabel lblPorcentajeEmpresa = new JLabel("Porcentaje empresa:");
		lblPorcentajeEmpresa.setForeground(new Color(255, 255, 255));
		lblPorcentajeEmpresa.setBounds(111, 216, 131, 14);
		panel_Admin.add(lblPorcentajeEmpresa);
		
		JLabel lblContrasenaJefe = new JLabel("Contrasena:");
		lblContrasenaJefe.setForeground(new Color(255, 255, 255));
		lblContrasenaJefe.setBounds(148, 241, 77, 14);
		panel_Admin.add(lblContrasenaJefe);
		
		passJefe = new JPasswordField();
		passJefe.setForeground(new Color(255, 255, 255));
		passJefe.setBackground(new Color(128, 128, 128));
		passJefe.setBounds(305, 238, 115, 20);
		panel_Admin.add(passJefe);
		JButton btnCancelarJefe = new JButton("Cancelar");
		btnCancelarJefe.setForeground(new Color(192, 192, 192));
		JSpinner porcentajeEmpresa = new JSpinner();
		porcentajeEmpresa.setForeground(new Color(255, 255, 255));
		porcentajeEmpresa.setBackground(new Color(128, 128, 128));
		JLabel lblErroresASS = new JLabel("");
		JButton btnConfirmarCreacion = new JButton("Confirmar");
		btnConfirmarCreacion.setForeground(new Color(192, 192, 192));
		btnConfirmarCreacion.setBackground(new Color(0,76,255));
		btnConfirmarCreacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cv.comprobarCampos(panel_Admin);
					nuevoJefe=new Jefe(textDNIJefe.getText(),textNombreJefe.getText(),textApellidos.getText(),
					mc.deStringADate(datePicker_3.getJFormattedTextField().getText()),textGmailJefe.getText(),
					String.valueOf(passJefe.getPassword()),tipoPersona.Jefe,
					mc.deStringADate(datePicker_2.getJFormattedTextField().getText()),(Float)porcentajeEmpresa.getValue(),0);
					gp.insertarPersona(mc,conexion,nuevoJefe);
					usuarios=gp.cargarPersonas(conexion);
					datePicker_3.getJFormattedTextField().setText("");
					datePicker_2.getJFormattedTextField().setText("");
					btnCancelarJefe.doClick();
					paneles.setSelectedIndex(3);
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					lblErroresASS.setText(e1.getMessage());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					lblErroresASS.setText(e1.getMessage());
				} catch (ErroresDeRegistro e1) {
					// TODO Auto-generated catch block
					lblErroresASS.setText(e1.getMessage());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					lblErroresASS.setText(e1.getMessage());
				} catch (ErroresDeOperaciones e1) {
					// TODO Auto-generated catch block
					lblErroresASS.setText(e1.getMessage());
				}
			}
		});
		btnConfirmarCreacion.setBounds(588, 377, 104, 23);
		panel_Admin.add(btnConfirmarCreacion);
		
		btnCancelarJefe.setBackground(new Color(0,76,255));
		btnCancelarJefe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textDNIJefe.setText("");
				textNombreJefe.setText("");
				textApellidosJefe.setText("");
				textGmailJefe.setText("");
				porcentajeEmpresa.setValue(51);
				passJefe.setText("");
			}
		});
		btnCancelarJefe.setBounds(295, 377, 89, 23);
		panel_Admin.add(btnCancelarJefe);
		
		JButton btnCancelar = new JButton("Atras");
		btnCancelar.setForeground(new Color(192, 192, 192));
		btnCancelar.setBackground(new Color(0,76,255));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancelarJefe.doClick();
				paneles.setSelectedIndex(3);
				lblErroresASS.setText("");
			}
		});
		btnCancelar.setBounds(10, 377, 89, 23);
		panel_Admin.add(btnCancelar);
		
		lblErroresASS.setForeground(new Color(255, 0, 0));
		lblErroresASS.setBounds(136, 323, 429, 14);
		panel_Admin.add(lblErroresASS);
		
		porcentajeEmpresa.setModel(new SpinnerNumberModel(Float.valueOf(51), Float.valueOf(51), Float.valueOf(100), Float.valueOf(1)));
		porcentajeEmpresa.setBounds(358, 207, 46, 20);
		panel_Admin.add(porcentajeEmpresa);
		
		JPanel panel_Supermercado = new JPanel();
		paneles.addTab("Septima", null, panel_Supermercado, null);
		panel_Supermercado.setLayout(null);
		panel_Supermercado.setBackground(customColor);
		
		JButton btnCancelarSuper = new JButton("Cancelar");
		btnCancelarSuper.setForeground(new Color(192, 192, 192));
		btnCancelarSuper.setBounds(304, 278, 89, 23);
		panel_Supermercado.add(btnCancelarSuper);
		
		JLabel lblCrearSuper = new JLabel("Creacion del Supermercado");
		lblCrearSuper.setForeground(new Color(255, 255, 255));
		lblCrearSuper.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCrearSuper.setBounds(259, 12, 201, 14);
		panel_Supermercado.add(lblCrearSuper);
		
		JLabel lblCodigoSuper = new JLabel("Codigo:");
		lblCodigoSuper.setForeground(new Color(255, 255, 255));
		lblCodigoSuper.setBounds(259, 81, 46, 14);
		panel_Supermercado.add(lblCodigoSuper);
		
		textCodigoSuper = new JTextField();
		textCodigoSuper.setForeground(new Color(255, 255, 255));
		textCodigoSuper.setBackground(new Color(128, 128, 128));
		textCodigoSuper.setBounds(345, 78, 115, 20);
		panel_Supermercado.add(textCodigoSuper);
		textCodigoSuper.setColumns(10);
		
		JLabel lblEmpresa = new JLabel("Empresa:");
		lblEmpresa.setForeground(new Color(255, 255, 255));
		lblEmpresa.setBounds(259, 112, 77, 14);
		panel_Supermercado.add(lblEmpresa);
		
		textEmpresa = new JTextField();
		textEmpresa.setForeground(new Color(255, 255, 255));
		textEmpresa.setBackground(new Color(128, 128, 128));
		textEmpresa.setBounds(345, 109, 115, 20);
		panel_Supermercado.add(textEmpresa);
		textEmpresa.setColumns(10);
		
		JLabel lblDireccion = new JLabel("Direccion:");
		lblDireccion.setForeground(new Color(255, 255, 255));
		lblDireccion.setBounds(259, 143, 77, 14);
		panel_Supermercado.add(lblDireccion);
		
		textDireccion = new JTextField();
		textDireccion.setForeground(new Color(255, 255, 255));
		textDireccion.setBackground(new Color(128, 128, 128));
		textDireccion.setBounds(345, 140, 115, 20);
		panel_Supermercado.add(textDireccion);
		textDireccion.setColumns(10);
		
		JLabel lblNumEmple = new JLabel("Numero empleados:");
		lblNumEmple.setForeground(new Color(255, 255, 255));
		lblNumEmple.setBounds(262, 168, 131, 14);
		panel_Supermercado.add(lblNumEmple);
		
		JSpinner NumEmple = new JSpinner();
		NumEmple.setBackground(new Color(128, 128, 128));
		NumEmple.setForeground(new Color(255, 255, 255));
		NumEmple.setModel(new SpinnerNumberModel(1, 1, 25, 1));
		NumEmple.setBounds(386, 165, 46, 20);
		panel_Supermercado.add(NumEmple);
		
		JButton btnAtrasSuper = new JButton("Atras");
		btnAtrasSuper.setForeground(new Color(192, 192, 192));
		JLabel lblErroresSuper = new JLabel("");
		JButton btnConfirmarSupermercado = new JButton("Confirmar");
		btnConfirmarSupermercado.setForeground(new Color(192, 192, 192));
		btnConfirmarSupermercado.setBackground(new Color(0,76,255));
		btnConfirmarSupermercado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cv.comprobarCampos(panel_Supermercado);
					supermercado=new Supermercado(textCodigoSuper.getText(),textEmpresa.getText(),textDireccion.getText(),(Integer) NumEmple.getValue(),null);
					nuevoJefe=(Jefe) gp.buscarPersona(String.valueOf(escogeJefe.getItemAt(escogeJefe.getSelectedIndex())), usuarios);
					gsm.insertarSupermercado(conexion,nuevoJefe, supermercado);
					btnAtrasSuper.doClick();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					lblErroresSuper.setText(e1.getMessage());
				} catch (ErroresDeOperaciones e1) {
					// TODO Auto-generated catch block
					lblErroresSuper.setText(e1.getMessage());
				}
			}
		});
		btnConfirmarSupermercado.setBounds(588, 391, 104, 23);
		panel_Supermercado.add(btnConfirmarSupermercado);
		
		btnAtrasSuper.setBackground(new Color(0,76,255));
		btnAtrasSuper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancelarSuper.doClick();
				paneles.setSelectedIndex(3);
			}
		});
		btnAtrasSuper.setBounds(10, 391, 89, 23);
		panel_Supermercado.add(btnAtrasSuper);
		
		
		escogeJefe.setBounds(345, 37, 115, 22);
		panel_Supermercado.add(escogeJefe);
		
		JLabel lblJefe = new JLabel("Jefes disponibles:");
		lblJefe.setForeground(new Color(255, 255, 255));
		lblJefe.setBounds(221, 41, 115, 14);
		panel_Supermercado.add(lblJefe);
		
		lblErroresSuper.setForeground(new Color(255, 0, 0));
		lblErroresSuper.setBounds(259, 220, 201, 14);
		panel_Supermercado.add(lblErroresSuper);
		
		btnCancelarSuper.setBackground(new Color(0,76,255));
		btnCancelarSuper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textCodigoSuper.setText("");
				textEmpresa.setText("");
				textDireccion.setText("");
				NumEmple.setValue(0);
			}
		});
		
		JPanel panel_Seccion = new JPanel();
		paneles.addTab("Octava", null, panel_Seccion, null);
		panel_Seccion.setLayout(null);
		panel_Seccion.setBackground(customColor);
		
		JLabel lblCreaSecciones = new JLabel("Creacion de Secciones");
		lblCreaSecciones.setForeground(new Color(255, 255, 255));
		lblCreaSecciones.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCreaSecciones.setBounds(221, 5, 152, 17);
		 panel_Seccion.add(lblCreaSecciones);
		 
		 textPrimeraSe = new JTextField();
		 textPrimeraSe.setForeground(new Color(255, 255, 255));
		 textPrimeraSe.setBackground(new Color(128, 128, 128));
		textPrimeraSe.setEnabled(false);
		textPrimeraSe.setBounds(286, 160, 112, 20);
		panel_Seccion.add(textPrimeraSe);
		textPrimeraSe.setColumns(10);
		textPrimeraSe.setVisible(false);
			
			JLabel lblPrimeraSe = new JLabel("Primera:");
			lblPrimeraSe.setForeground(new Color(255, 255, 255));
			lblPrimeraSe.setBounds(181, 163, 69, 14);
			panel_Seccion.add(lblPrimeraSe);
			lblPrimeraSe.setVisible(false);
			
			JLabel lblSegundaSe = new JLabel("Segunda:");
			lblSegundaSe.setForeground(new Color(255, 255, 255));
			lblSegundaSe.setBounds(181, 195, 69, 14);
			panel_Seccion.add(lblSegundaSe);
			lblSegundaSe.setVisible(false);
			
			textSegundaSe = new JTextField();
			textSegundaSe.setForeground(new Color(255, 255, 255));
			textSegundaSe.setBackground(new Color(128, 128, 128));
			textSegundaSe.setEnabled(false);
			textSegundaSe.setBounds(286, 192, 112, 20);
			panel_Seccion.add(textSegundaSe);
			textSegundaSe.setColumns(10);
			textSegundaSe.setVisible(false);
			
			JLabel lblTerceraSe = new JLabel("Tercera:");
			lblTerceraSe.setForeground(new Color(255, 255, 255));
			lblTerceraSe.setBounds(181, 226, 69, 14);
			panel_Seccion.add(lblTerceraSe);
			lblTerceraSe.setVisible(false);
			
			textTerceraSe = new JTextField();
			textTerceraSe.setForeground(new Color(255, 255, 255));
			textTerceraSe.setBackground(new Color(128, 128, 128));
			textTerceraSe.setEnabled(false);
			textTerceraSe.setBounds(286, 223, 112, 20);
			panel_Seccion.add(textTerceraSe);
			textTerceraSe.setColumns(10);
			textTerceraSe.setVisible(false);
		
		JComboBox<String> tipoSeccion = new JComboBox<String>();
		tipoSeccion.setForeground(new Color(255, 255, 255));
		tipoSeccion.setBackground(new Color(128, 128, 128));
		tipoSeccion.setBounds(286, 118, 112, 20);
		tipoSeccion.setModel(new DefaultComboBoxModel<String>(Tipos));
		panel_Seccion.add(tipoSeccion);
		tipoSeccion.setVisible(false);
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setForeground(new Color(255, 255, 255));
		lblTipo.setBounds(196, 121, 35, 14);
		panel_Seccion.add(lblTipo);
		lblTipo.setVisible(false);
		
		JButton btnSelecSe = new JButton("Seleccionar");
		btnSelecSe.setForeground(new Color(192, 192, 192));
		btnSelecSe.setBackground(new Color(0,76,255));
		btnSelecSe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipoSeccion.setEnabled(false);
				if(cuentaSecciones==0) {
					textPrimeraSe.setText(tipoSeccion.getItemAt(tipoSeccion.getSelectedIndex()));
					cuentaSecciones++;
					Tipos=mc.modificarComboBox(Tipos, tipoSeccion.getItemAt(tipoSeccion.getSelectedIndex()));
					tipoSeccion.setModel(new DefaultComboBoxModel<String>(Tipos));
					tipoSeccion.updateUI();
				}else if(cuentaSecciones==1) {
					textSegundaSe.setText(tipoSeccion.getItemAt(tipoSeccion.getSelectedIndex()));
					cuentaSecciones++;
					Tipos=mc.modificarComboBox(Tipos, tipoSeccion.getItemAt(tipoSeccion.getSelectedIndex()));
					tipoSeccion.setModel(new DefaultComboBoxModel<String>(Tipos));
					tipoSeccion.updateUI();
				}else if(cuentaSecciones==2){
					textTerceraSe.setText(tipoSeccion.getItemAt(tipoSeccion.getSelectedIndex()));
					Tipos=mc.modificarComboBox(Tipos, tipoSeccion.getItemAt(tipoSeccion.getSelectedIndex()));
					tipoSeccion.setModel(new DefaultComboBoxModel<String>(Tipos));
					tipoSeccion.updateUI();
					tipoSeccion.setEnabled(false);
					cuentaSecciones++;
				}
			}
		});
		btnSelecSe.setBounds(394, 268, 112, 23);
		 panel_Seccion.add(btnSelecSe);
		
		JButton btnAnadir = new JButton("Mas");
		btnAnadir.setForeground(new Color(192, 192, 192));
		btnAnadir.setBackground(new Color(0,76,255));
		btnAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipoSeccion.setEnabled(true);
				if(cuentaSecciones==1) {
				lblSegundaSe.setVisible(true);
				textSegundaSe.setVisible(true);
				}else if(cuentaSecciones==2) {
				lblTerceraSe.setVisible(true);
				textTerceraSe.setVisible(true);
				} 
			}
		});
		btnAnadir.setBounds(298, 268, 75, 23);
		panel_Seccion.add(btnAnadir);
		
		JButton btnConfirmarSe = new JButton("Confirmar");
		btnConfirmarSe.setForeground(new Color(192, 192, 192));
		JLabel lblEscogeDireccion = new JLabel("Direccion:");
		lblEscogeDireccion.setForeground(new Color(255, 255, 255));
		lblEscogeDireccion.setVisible(false);
		JComboBox<String> escogeDireccion = new JComboBox<String>();
		escogeDireccion.setForeground(new Color(255, 255, 255));
		escogeDireccion.setBackground(new Color(128, 128, 128));
		JButton btnCancelarSe = new JButton("Cancelar");
		btnCancelarSe.setForeground(new Color(192, 192, 192));
		btnCancelarSe.setBackground(new Color(0,76,255));
		btnCancelarSe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPrimeraSe.setText("");
				textSegundaSe.setText("");
				textTerceraSe.setText("");
				Tipos=mc.modificarComboBox(Tipos,null);
				tipoSeccion.setEnabled(true);
				tipoSeccion.setModel(new DefaultComboBoxModel<String>(Tipos));
				tipoSeccion.updateUI();
				lblTerceraSe.setVisible(false);
				textTerceraSe.setVisible(false);
				lblSegundaSe.setVisible(false);
				textSegundaSe.setVisible(false);
				lblPrimeraSe.setVisible(false);
		 		textPrimeraSe.setVisible(false);
		 		tipoSeccion.setVisible(false);
		 		lblTipo.setVisible(false);
		 		escogeDireccion.setVisible(false);
		 		lblEscogeDireccion.setVisible(false);
		 		cuentaSecciones=0;
		 		btnConfirmarSe.setEnabled(true);
			}
		});
		btnCancelarSe.setBounds(164, 268, 95, 23);
		 panel_Seccion.add(btnCancelarSe);
		 
		 JButton btnAtrasSe = new JButton("Atras");
		 btnAtrasSe.setForeground(new Color(192, 192, 192));
		 btnAtrasSe.setBackground(new Color(0,76,255));
		 btnAtrasSe.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		btnCancelarSe.doClick();
		 		paneles.setSelectedIndex(3);
		 	}
		 });
		 btnAtrasSe.setBounds(10, 391, 89, 23);
		 panel_Seccion.add(btnAtrasSe);
		 
		 btnConfirmarSe.setBackground(new Color(0,76,255));
		 btnConfirmarSe.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		try {	
		 			if(!textPrimeraSe.getText().equals("") && textPrimeraSe.isVisible() && supermercado.getArraySecciones().size()==0) {
		 				seccion=new Seccion(supermercado.getCodigoSuper()+"1",tipoArticulo.valueOf(textPrimeraSe.getText()),0,null);
		 					gs.insertarSeccion(conexion,supermercado, seccion);
		 			} 
		 			if(!textSegundaSe.getText().equals("") && textSegundaSe.isVisible()&& supermercado.getArraySecciones().size()==1) {
		 				seccion=new Seccion(supermercado.getCodigoSuper()+"2",tipoArticulo.valueOf(textSegundaSe.getText()),0,null);
		 					gs.insertarSeccion(conexion,supermercado, seccion);
		 			}
		 			if(!textSegundaSe.getText().equals("") && textTerceraSe.isVisible()&& supermercado.getArraySecciones().size()==2) {
		 				seccion=new Seccion(supermercado.getCodigoSuper()+"3",tipoArticulo.valueOf(textTerceraSe.getText()),0,null);
		 					gs.insertarSeccion(conexion,supermercado, seccion);
		 			}
		 			btnCancelarSe.doClick();
		 			paneles.setSelectedIndex(3);
		 		} catch (SQLException e1) {
		 			// TODO Auto-generated catch block
		 			e1.printStackTrace();
		 		}
		 	}
		 });
		 btnConfirmarSe.setBounds(580, 391, 112, 23);
		 panel_Seccion.add(btnConfirmarSe);
		 
		 escogeSuper.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		escogeSuper.getItemAt(escogeSuper.getSelectedIndex());
		 		escogeDireccion.setVisible(true);
		 		lblEscogeDireccion.setVisible(true);
		 		try {
					escogeDireccion.setModel(new DefaultComboBoxModel<String>(mc.cargarDireccionSuper(gsm.buscarSuperEmpresa(conexion,escogeSuper.getItemAt(escogeSuper.getSelectedIndex())))));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ErroresDeOperaciones e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		 	}
		 });
		 escogeSuper.setBounds(286, 33, 178, 22);
		 escogeDireccion.setVisible(false);
		 panel_Seccion.add(escogeSuper);
		 
		 JLabel lblSuperPadre = new JLabel("Supermercado:");
		 lblSuperPadre.setForeground(new Color(255, 255, 255));
		 lblSuperPadre.setBounds(164, 37, 112, 14);
		 panel_Seccion.add(lblSuperPadre);
		 
		 escogeDireccion.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		try {
		 			lblPrimeraSe.setVisible(true);
			 		textPrimeraSe.setVisible(true);
			 		tipoSeccion.setVisible(true);
			 		lblTipo.setVisible(true);
					supermercado=gsm.buscarSuperEmpresaDireccion(conexion,escogeSuper.getItemAt(escogeSuper.getSelectedIndex()),escogeDireccion.getItemAt(escogeDireccion.getSelectedIndex()));
					if(supermercado.getArraySecciones().size()>=1) {
						textPrimeraSe.setText(String.valueOf(supermercado.getArraySecciones().get(0).getNombreSeccion()));
						lblSegundaSe.setVisible(true);
				 		textSegundaSe.setVisible(true);
				 		Tipos=mc.modificarComboBox(Tipos, String.valueOf(supermercado.getArraySecciones().get(0).getNombreSeccion()));
				 		tipoSeccion.setModel(new DefaultComboBoxModel<String>(Tipos));
				 		cuentaSecciones++;
					}
					if(supermercado.getArraySecciones().size()==2) {
				 		lblTerceraSe.setVisible(true);
				 		textTerceraSe.setVisible(true);
				 		textSegundaSe.setText(String.valueOf(supermercado.getArraySecciones().get(1).getNombreSeccion()));
				 		Tipos=mc.modificarComboBox(Tipos, String.valueOf(supermercado.getArraySecciones().get(1).getNombreSeccion()));
				 		tipoSeccion.setModel(new DefaultComboBoxModel<String>(Tipos));
				 		cuentaSecciones++;
					}
					if(supermercado.getArraySecciones().size()==3) {
				 		textTerceraSe.setText(String.valueOf(supermercado.getArraySecciones().get(2).getNombreSeccion()));
				 		Tipos=mc.modificarComboBox(Tipos, String.valueOf(supermercado.getArraySecciones().get(2).getNombreSeccion()));
				 		tipoSeccion.setModel(new DefaultComboBoxModel<String>(Tipos));
				 		cuentaSecciones++;
				 		btnConfirmarSe.setEnabled(false);
					}
				} catch (SQLException | ErroresDeOperaciones e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		 	}
		 });
		 escogeDireccion.setBounds(286, 66, 178, 22);
		 panel_Seccion.add(escogeDireccion);
		 
		 
		 lblEscogeDireccion.setBounds(174, 70, 68, 14);
		 panel_Seccion.add(lblEscogeDireccion);
		
		JPanel panel_Articulos = new JPanel();
		paneles.addTab("Novena", null, panel_Articulos, null);
		panel_Articulos.setLayout(null);
		
		
		suAnadirArticulo.setBounds(228, 37, 156, 22);
		panel_Articulos.add(suAnadirArticulo);
		
		JLabel lblNombreArticulo = new JLabel("Nombre:");
		lblNombreArticulo.setForeground(new Color(255, 255, 255));
		lblNombreArticulo.setBounds(10, 83, 75, 14);
		panel_Articulos.add(lblNombreArticulo);
		
		textNombreArticulo = new JTextField();
		textNombreArticulo.setForeground(new Color(255, 255, 255));
		textNombreArticulo.setBackground(new Color(128, 128, 128));
		textNombreArticulo.setBounds(95, 80, 123, 20);
		panel_Articulos.add(textNombreArticulo);
		textNombreArticulo.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Descripcion(Opcional):");
		lblDescripcion.setForeground(new Color(255, 255, 255));
		lblDescripcion.setBounds(10, 116, 156, 14);
		panel_Articulos.add(lblDescripcion);
		
		JTextArea textDescripcion = new JTextArea();
		textDescripcion.setBackground(new Color(128, 128, 128));
		textDescripcion.setForeground(new Color(255, 255, 255));
		textDescripcion.setLineWrap(true);
		textDescripcion.setBounds(10, 141, 234, 227);
		panel_Articulos.add(textDescripcion);
		
		JLabel lblImagen = new JLabel("Imagen y formato:");
		lblImagen.setForeground(new Color(255, 255, 255));
		lblImagen.setBounds(254, 83, 179, 14);
		panel_Articulos.add(lblImagen);
		
		textImagen = new JTextField();
		textImagen.setForeground(new Color(255, 255, 255));
		textImagen.setBackground(new Color(128, 128, 128));
		textImagen.setBounds(272, 125, 161, 20);
		panel_Articulos.add(textImagen);
		textImagen.setColumns(10);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setForeground(new Color(255, 255, 255));
		lblPrecio.setBounds(254, 175, 46, 14);
		panel_Articulos.add(lblPrecio);
		
		textPrecio = new JTextField();
		textPrecio.setForeground(new Color(255, 255, 255));
		textPrecio.setBackground(new Color(128, 128, 128));
		textPrecio.setBounds(318, 172, 115, 20);
		panel_Articulos.add(textPrecio);
		textPrecio.setColumns(10);
		
		JSpinner garantia = new JSpinner();
		garantia.setForeground(new Color(255, 255, 255));
		garantia.setBackground(new Color(128, 128, 128));
		garantia.setVisible(false);
		JCheckBox chckElectrica = new JCheckBox("Electrica");
		
		tipoArticuloCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insercion=mc.cogeSeccion(admin.getSupermercado().getArraySecciones(), tipoArticuloCombo.getItemAt(tipoArticuloCombo.getSelectedIndex()));
				if(tipoArticuloCombo.getSelectedItem().equals("Comida")) {
					datePicker_4.setVisible(true);
					textProcedencia.setVisible(true);
					garantia.setVisible(false);
					chckElectrica.setVisible(false);
					textTalla.setVisible(false);
					textMarca.setVisible(false);
				}else if(tipoArticuloCombo.getSelectedItem().equals("Herramienta")){
					garantia.setVisible(true);
					chckElectrica.setVisible(true);
					datePicker_4.setVisible(false);
					textProcedencia.setVisible(false);
					textTalla.setVisible(false);
					textMarca.setVisible(false);
				}else {
					garantia.setVisible(false);
					chckElectrica.setVisible(false);
					datePicker_4.setVisible(false);
					textProcedencia.setVisible(false);
					textTalla.setVisible(true);
					textMarca.setVisible(true);
				}
			}
		});
		tipoArticuloCombo.setModel(new DefaultComboBoxModel<String>(Tipos));
		tipoArticuloCombo.setBounds(323, 217, 110, 22);
		panel_Articulos.add(tipoArticuloCombo);
		
		JLabel lblSeccion = new JLabel("Seccion:");
		lblSeccion.setForeground(new Color(255, 255, 255));
		lblSeccion.setBounds(254, 221, 66, 14);
		panel_Articulos.add(lblSeccion);
		
		JLabel lblTalla = new JLabel("Talla:");
		lblTalla.setForeground(new Color(255, 255, 255));
		lblTalla.setBounds(472, 83, 46, 14);
		panel_Articulos.add(lblTalla);
		
		textTalla = new JTextField();
		textTalla.setForeground(new Color(255, 255, 255));
		textTalla.setBackground(new Color(128, 128, 128));
		textTalla.setBounds(543, 80, 123, 20);
		panel_Articulos.add(textTalla);
		textTalla.setColumns(10);
		textTalla.setVisible(false);
		
		JLabel lblMarca = new JLabel("Marca:");
		lblMarca.setForeground(new Color(255, 255, 255));
		lblMarca.setBounds(472, 128, 46, 14);
		panel_Articulos.add(lblMarca);
		
		textMarca = new JTextField();
		textMarca.setForeground(new Color(255, 255, 255));
		textMarca.setBackground(new Color(128, 128, 128));
		textMarca.setBounds(543, 125, 123, 20);
		panel_Articulos.add(textMarca);
		textMarca.setColumns(10);
		textMarca.setVisible(false);
		
		JLabel lblFechaDeCaducidad = new JLabel("Fecha de Caducidad");
		lblFechaDeCaducidad.setForeground(new Color(255, 255, 255));
		lblFechaDeCaducidad.setBounds(525, 163, 141, 14);
		panel_Articulos.add(lblFechaDeCaducidad);
		
		JLabel lblProcedencia = new JLabel("Procedencia:");
		lblProcedencia.setForeground(new Color(255, 255, 255));
		lblProcedencia.setBounds(449, 236, 84, 14);
		panel_Articulos.add(lblProcedencia);
		
		textProcedencia = new JTextField();
		textProcedencia.setForeground(new Color(255, 255, 255));
		textProcedencia.setBackground(new Color(128, 128, 128));
		textProcedencia.setBounds(543, 233, 123, 20);
		panel_Articulos.add(textProcedencia);
		textProcedencia.setColumns(10);
		textProcedencia.setVisible(false);
		panel_Articulos.setBackground(customColor);
		
		chckElectrica.setBounds(553, 260, 97, 23);
		panel_Articulos.add(chckElectrica);
		chckElectrica.setVisible(false);
		
		
		garantia.setModel(new SpinnerNumberModel(2, 2, 5, 1));
		garantia.setBounds(588, 297, 30, 20);
		panel_Articulos.add(garantia);
		
		JLabel lblGarantia = new JLabel("Garantia:");
		lblGarantia.setForeground(new Color(255, 255, 255));
		lblGarantia.setBounds(471, 300, 60, 14);
		panel_Articulos.add(lblGarantia);
		
		UtilDateModel modelx = new UtilDateModel();
		Properties px = new Properties();
		JDatePanelImpl datePanelx = new JDatePanelImpl(modelx, px);
		datePicker_4 = new JDatePickerImpl(datePanelx, new DateLabelFormatter());
		datePicker_4.getJFormattedTextField().setBackground(new Color(128, 128, 128));
		datePicker_4.getJFormattedTextField().setForeground(new Color(255, 255, 255));
		datePicker_4.setBounds(541, 199, 125, 20);
		datePicker_4.setForeground(new Color(255, 255, 255));
		datePicker_4.setBackground(new Color(128, 128, 128));
		panel_Articulos.add(datePicker_4);
		datePicker_4.setVisible(false);
		JLabel lblErroresArti = new JLabel("");
		
		JButton btnInsertar = new JButton("Confirmar");
		btnInsertar.setForeground(new Color(192, 192, 192));
		btnInsertar.setBackground(new Color(0,76,255));
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				if(tipoArticuloCombo.getSelectedItem().equals("Comida")) {
						nuevaComida=new Comida(0, textNombreArticulo.getText(), textImagen.getText(), textDescripcion.getText(), Float.valueOf(textPrecio.getText()), 100, mc.deStringADate(datePicker_4.getJFormattedTextField().getText()), textProcedencia.getText());
						ga.insertarArticulo(mc,conexion,insercion, nuevaComida);
				}else if(tipoArticuloCombo.getSelectedItem().equals("Herramienta")) {
						nuevaHerramienta=new Herramienta(0, textNombreArticulo.getText(), textImagen.getText(), textDescripcion.getText(), Float.valueOf(textPrecio.getText()), 100, mc.pasarBoolean(chckElectrica.isSelected()), (Integer)garantia.getValue());
						ga.insertarArticulo(mc,conexion,insercion, nuevaHerramienta);
				}else if(tipoArticuloCombo.getSelectedItem().equals("Ropa")) {
						nuevaRopa=new Ropa(0, textNombreArticulo.getText(), textImagen.getText(), textDescripcion.getText(), Float.valueOf(textPrecio.getText()), 100, textTalla.getText(), textMarca.getText());
						ga.insertarArticulo(mc,conexion,insercion, nuevaRopa);
				}
				cv.vaciarCampos(panel_Articulos);
				datePicker_4.getJFormattedTextField().setText("");
				textDescripcion.setText("");
				lblErroresArti.setText("");
				} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						lblErroresArti.setText("El formato del precio es incorrecto");
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						lblErroresArti.setText("El formato del precio es incorrecto");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						lblErroresArti.setText("No se pudo conectar a la BBDD o hubo un fallo en la insercion");
					}
			}
		});
		btnInsertar.setBounds(541, 379, 97, 23);
		panel_Articulos.add(btnInsertar);
		
		JButton btnCancelarArticulo = new JButton("Cancelar");
		btnCancelarArticulo.setForeground(new Color(192, 192, 192));
		btnCancelarArticulo.setBackground(new Color(0,76,255));
		btnCancelarArticulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cv.vaciarCampos(panel_Articulos);
				tipoArticuloCombo.setModel(new DefaultComboBoxModel<String>(Tipos));
				datePicker_4.getJFormattedTextField().setText("");
				textDescripcion.setText("");
				paneles.setSelectedIndex(3);
			}
		});
		btnCancelarArticulo.setBounds(66, 379, 89, 23);
		panel_Articulos.add(btnCancelarArticulo);
		
		
		lblErroresArti.setForeground(new Color(255, 0, 0));
		lblErroresArti.setBounds(228, 356, 247, 22);
		panel_Articulos.add(lblErroresArti);
		
		JPanel panel_Compras = new JPanel();
		paneles.addTab("Decima", null, panel_Compras, null);
		panel_Compras.setLayout(null);
		panel_Compras.setBackground(customColor);
		
		JLabel lblPrecioTotal = new JLabel("Precio de su carrito:");
		lblPrecioTotal.setForeground(new Color(255, 255, 255));
		JComboBox<String> boxSeccion = new JComboBox<String>();
		boxSeccion.setForeground(new Color(255, 255, 255));
		boxSeccion.setBackground(new Color(128, 128, 128));
		JScrollPane buscaArticulos=new JScrollPane();
		buscaArticulos.setViewportView(cv.mostrarArticulos(listaArticulos,carrito,lblPrecioTotal,articulosCarrito));
		JComboBox<String>  boxSuper = new JComboBox<String> ();
		boxSuper.setForeground(new Color(255, 255, 255));
		boxSuper.setBackground(new Color(128, 128, 128));
		boxSuper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					supermercados=gsm.cogerSeccionesMultiplesSu(conexion,supermercados);
					seccionesDelSuper=supermercados.get(boxSuper.getSelectedIndex()).getArraySecciones();
					boxSeccion.setModel(new DefaultComboBoxModel<String>(mc.cargarNombreSeccion(seccionesDelSuper)));
					boxSeccion.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		boxSuper.setModel(new DefaultComboBoxModel<String>(mc.cargarEmpresa(supermercados)));
		boxSuper.setBounds(10, 11, 125, 22);
		panel_Compras.add(boxSuper);
		
		boxSeccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					listaArticulos=ga.cargarArticulosPorSeccion(conexion,seccionesDelSuper.get(boxSeccion.getSelectedIndex()));
					buscaArticulos.setViewportView(cv.mostrarArticulos(listaArticulos,carrito,lblPrecioTotal,articulosCarrito));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		boxSeccion.setVisible(false);
		boxSeccion.setBounds(145, 11, 125, 22);
		panel_Compras.add(boxSeccion);
		
		buscaArticulos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		buscaArticulos.setBounds(10, 55, 702, 317);
		panel_Compras.add(buscaArticulos);
		
		textBuscador = new JTextField();
		textBuscador.setForeground(new Color(255, 255, 255));
		textBuscador.setBackground(new Color(128, 128, 128));
		textBuscador.setBounds(386, 12, 207, 20);
		panel_Compras.add(textBuscador);
		textBuscador.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setForeground(new Color(192, 192, 192));
		btnBuscar.setBackground(new Color(0,76,255));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textBuscador.getText().equals("")) {
						listaArticulos=ga.cargarArticulos(conexion);
						buscaArticulos.setViewportView(cv.mostrarArticulos(listaArticulos,carrito,lblPrecioTotal,articulosCarrito));
					}else {
					listaArticulos=ga.buscarArticulosPorNombre(textBuscador.getText(),listaArticulos);
					buscaArticulos.setViewportView(cv.mostrarArticulos(listaArticulos,carrito,lblPrecioTotal,articulosCarrito));
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnBuscar.setBounds(287, 11, 89, 23);
		panel_Compras.add(btnBuscar);
		
		JButton btnAtrasCom = new JButton("Atras");
		btnAtrasCom.setForeground(new Color(192, 192, 192));
		btnAtrasCom.setBackground(new Color(0,76,255));
		btnAtrasCom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paneles.setSelectedIndex(3);
			}
		});
		btnAtrasCom.setBounds(10, 383, 89, 23);
		panel_Compras.add(btnAtrasCom);
		
		JButton btnVerCarrito = new JButton("Carrito");
		btnVerCarrito.setForeground(new Color(192, 192, 192));
		btnVerCarrito.setBackground(new Color(0,76,255));
		btnVerCarrito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(articulosCarrito.size()==0) {
				buscaArticulos.setViewportView(null);
				}else {
				buscaArticulos.setViewportView(cv.mostrarCarrito(articulosCarrito,carrito,lblPrecioTotal));
				}
			}
		});
		btnVerCarrito.setBounds(603, 11, 89, 23);
		panel_Compras.add(btnVerCarrito);
		
		lblPrecioTotal.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPrecioTotal.setBounds(338, 385, 228, 14);
		panel_Compras.add(lblPrecioTotal);
		
		JButton btnRealizarCompra = new JButton("Comprar");
		btnRealizarCompra.setForeground(new Color(192, 192, 192));
		btnRealizarCompra.setBackground(new Color(0,76,255));
		btnRealizarCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int ultimocodigo=gp.insertarCompraYCobrar(conexion,login, carrito);
					gp.insertarArticulos(conexion,carrito.getListaCantidades(),ultimocodigo);
					buscaArticulos.setViewportView(cv.mostrarArticulos(listaArticulos,carrito,lblPrecioTotal,articulosCarrito));
					articulosCarrito=new ArrayList<Articulo>();
					carrito=new Compra();
				} catch (SQLException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRealizarCompra.setBounds(603, 383, 89, 23);
		panel_Compras.add(btnRealizarCompra);
		
		JButton btnGuardarCarrito = new JButton("Guardar");
		btnGuardarCarrito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnGuardarCarrito.setForeground(new Color(255, 255, 255));
		btnGuardarCarrito.setBackground(new Color(0, 76, 255));
		btnGuardarCarrito.setBounds(119, 383, 89, 23);
		panel_Compras.add(btnGuardarCarrito);
		
		JButton btnCargarCarrito = new JButton("Cargar");
		btnCargarCarrito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCargarCarrito.setForeground(new Color(255, 255, 255));
		btnCargarCarrito.setBackground(new Color(0, 76, 255));
		btnCargarCarrito.setBounds(228, 383, 89, 23);
		panel_Compras.add(btnCargarCarrito);
		
		JPanel panelHistorial = new JPanel();
		paneles.addTab("Undecima", null, panelHistorial, null);
		panelHistorial.setLayout(null);
		panelHistorial.setBackground(customColor);
		
		historialCompras.setBounds(10, 46, 376, 300);
		panelHistorial.add(historialCompras);
		
		JLabel lblHistorial = new JLabel("Historial de Compras");
		lblHistorial.setForeground(new Color(255, 255, 255));
		lblHistorial.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblHistorial.setBounds(10, 21, 222, 14);
		panelHistorial.add(lblHistorial);
		
		JScrollPane articulosComprados = new JScrollPane();
		articulosComprados.setBounds(389, 46, 313, 300);
		panelHistorial.add(articulosComprados);
		
		JLabel lblArticulosComprados = new JLabel("Articulos comprados");
		lblArticulosComprados.setForeground(new Color(255, 255, 255));
		lblArticulosComprados.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblArticulosComprados.setBounds(394, 21, 200, 14);
		panelHistorial.add(lblArticulosComprados);
		
		JButton btnAtrasHis = new JButton("Atras");
		btnAtrasHis.setForeground(new Color(192, 192, 192));
		btnAtrasHis.setBackground(new Color(0,76,255));
		btnAtrasHis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paneles.setSelectedIndex(3);
			}
		});
		btnAtrasHis.setBounds(10, 391, 89, 23);
		panelHistorial.add(btnAtrasHis);
		
		JButton btnVerArticulosCom = new JButton("Seleccionar");
		btnVerArticulosCom.setForeground(new Color(192, 192, 192));
		btnVerArticulosCom.setBackground(new Color(0,76,255));
		btnVerArticulosCom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tabla.setEnabled(false);
					historialArticulosComprados=gac.cargarArticuloCompradoCod(conexion,cv.cogerCodigoCompra(tabla, historial).getCodigoCompra());
					tablaApoyo=cv.cargaArticulosComprados(mc.cargarArticulosComprados(conexion,historialArticulosComprados));
					articulosComprados.setViewportView(tablaApoyo);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnVerArticulosCom.setBounds(273, 357, 103, 23);
		panelHistorial.add(btnVerArticulosCom);
		
		JButton btnCancelarCompra = new JButton("Devolver");
		btnCancelarCompra.setForeground(new Color(192, 192, 192));
		btnCancelarCompra.setBackground(new Color(0,76,255));
		btnCancelarCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					devolucion=cv.cogerCodigoCompra(tabla, historial);
					gp.cancelarArticulos(conexion,devolucion);
					gp.cancelarCompra(conexion,login,devolucion);
					historial=gc.buscarComprasPersona(mc,conexion,login);
					tabla=cv.cargarHistorialCompras(login,mc.cargarHistorialCompras(conexion,historial));
					historialCompras.setViewportView(tabla);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCancelarCompra.setBounds(10, 357, 103, 23);
		panelHistorial.add(btnCancelarCompra);
		
		JSpinner cantidadDevolver = new JSpinner();
		JButton btnSeleccionarArCom = new JButton("Modificar");
		btnSeleccionarArCom.setForeground(new Color(192, 192, 192));
		btnSeleccionarArCom.setBackground(new Color(0,76,255));
		btnSeleccionarArCom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arDevolver=cv.cogerArticuloComprado(tablaApoyo, historialArticulosComprados);
				cantidadDevolver.setModel(new SpinnerNumberModel( arDevolver.getCantidad(),0, arDevolver.getCantidad(), 1));
				tablaApoyo.setEnabled(false);
			}
		});
		btnSeleccionarArCom.setBounds(389, 357, 97, 23);
		panelHistorial.add(btnSeleccionarArCom);
		
		
		cantidadDevolver.setModel(new SpinnerNumberModel(0, 0, 0, 1));
		cantidadDevolver.setBounds(534, 358, 45, 20);
		panelHistorial.add(cantidadDevolver);
		
		JButton btnAplicar = new JButton("Confirmar");
		btnAplicar.setForeground(new Color(192, 192, 192));
		btnAplicar.setBackground(new Color(0,76,255));
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					gp.devolverUnArticulo(conexion,login, arDevolver, (Integer)cantidadDevolver.getValue());
					gp.compruebaDevolucionArticulo(conexion,arDevolver);
					historialCompras.setViewportView(tabla);
					btnVerArticulosCom.doClick();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
		btnAplicar.setBounds(589, 357, 103, 23);
		panelHistorial.add(btnAplicar);
		
		JButton btnCancelarSeleccion = new JButton("Cancelar");
		btnCancelarSeleccion.setForeground(new Color(192, 192, 192));
		btnCancelarSeleccion.setBackground(new Color(0,76,255));
		btnCancelarSeleccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabla.setEnabled(true);
				tablaApoyo.setEnabled(true);
				tablaApoyo=null;
				articulosComprados.setViewportView(tablaApoyo);
			}
		});
		btnCancelarSeleccion.setBounds(143, 357, 97, 23);
		panelHistorial.add(btnCancelarSeleccion);
		
		JButton btnCancelarDevolucion = new JButton("Cancelar");
		btnCancelarDevolucion.setForeground(new Color(192, 192, 192));
		btnCancelarDevolucion.setBackground(new Color(0,76,255));
		btnCancelarDevolucion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cantidadDevolver.setModel(new SpinnerNumberModel(0, 0, 0, 1));
				tablaApoyo.setEnabled(true);
			}
		});
		btnCancelarDevolucion.setBounds(387, 391, 99, 23);
		panelHistorial.add(btnCancelarDevolucion);
	}
}