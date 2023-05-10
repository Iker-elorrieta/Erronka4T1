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
import gestores.GestorPersona;
import gestores.GestorSeccion;
import gestores.GestorSupermercado;
import modelo.*;
import otros.DateLabelFormatter;
import otros.tipoArticulo;
import otros.tipoPersona;

import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
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
	private JTable tabla;
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
	
	private Compra carrito=new Compra();
	private Comida nuevaComida;
	private Ropa nuevaRopa;
	private Herramienta nuevaHerramienta;
	private Seccion insercion;
	private Jefe nuevoJefe;
	private Supermercado supermercado;
	private Seccion seccion;
	
	private Jefe admin;
	private Persona login;
	private Cliente cliente;
	private Supermercado su;
	
	private int manejaCambio=0;
	private int cuentaSecciones=0;
	private Boolean cambios=true;
	private String [] Tipos={"Comida","Herramienta","Ropa"};
	
	private ArrayList<Articulo> listaArticulos;
	private ArrayList<Persona> usuarios;
	private ArrayList<Supermercado> supermercados;
	private ArrayList<Seccion> seccionesDelSuper;
	
	private Metodos mc=new Metodos();
	private MetodosVista cv = new MetodosVista();
	private GestorPersona gp=new GestorPersona();
	private GestorSupermercado gsm=new GestorSupermercado();
	private GestorSeccion gs=new GestorSeccion();
	private GestorArticulo ga=new GestorArticulo();
	
	/**
	private GestorArticuloComprado gac=new GestorArticuloComprado();
	private GestorCompra gc=new GestorCompra();
	*/
	
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
		try {
			usuarios=gp.cargarPersonas();
			supermercados=gsm.cargarSupermercados();
			listaArticulos=ga.cargarArticulos();
			initialize();
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 723, 492);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane paneles = new JTabbedPane(JTabbedPane.TOP);
		paneles.setBounds(0, -26, 707, 479);
		frame.getContentPane().add(paneles);
		
		
		
		JPanel panel_Bienvenido = new JPanel();
		paneles.addTab("Primera", null, panel_Bienvenido, null);
		panel_Bienvenido.setLayout(null);
		
		JButton btnIniSes = new JButton("Iniciar sesion");
		btnIniSes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paneles.setSelectedIndex(1);
			}
		});
		btnIniSes.setBounds(263, 181, 127, 23);
		panel_Bienvenido.add(btnIniSes);
		
		JButton btnRegis = new JButton("Registrarse");
		btnRegis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paneles.setSelectedIndex(2);
			}
		});
		btnRegis.setBounds(263, 252, 127, 23);
		panel_Bienvenido.add(btnRegis);
		
		JLabel lblMensajeIni = new JLabel("Bienvenido al Super Elorrieta!");
		lblMensajeIni.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblMensajeIni.setBounds(74, 31, 536, 81);
		panel_Bienvenido.add(lblMensajeIni);
		
		JLabel lblIniSes = new JLabel("Pulse en este botón para acceder a su cuenta.");
		lblIniSes.setBounds(214, 156, 283, 14);
		panel_Bienvenido.add(lblIniSes);
		
		JLabel lblCreaCuen = new JLabel("Pulse este si quiere crear una cuenta.");
		lblCreaCuen.setBounds(227, 227, 239, 14);
		panel_Bienvenido.add(lblCreaCuen);
		
		JPanel panel_IniciarSesion = new JPanel();
		paneles.addTab("Segundo", null, panel_IniciarSesion, null);
		panel_IniciarSesion.setLayout(null);
		
		cemail = new JTextField();
		cemail.setBounds(231, 107, 181, 20);
		panel_IniciarSesion.add(cemail);
		cemail.setColumns(10);
		
		JLabel lblemail = new JLabel("E-mail:");
		lblemail.setBounds(24, 110, 46, 14);
		panel_IniciarSesion.add(lblemail);
		
		JLabel lblContra = new JLabel("Contraseña:");
		lblContra.setBounds(24, 206, 72, 14);
		panel_IniciarSesion.add(lblContra);
		
		campoContrasena = new JPasswordField();
		campoContrasena.setBounds(231, 203, 181, 20);
		panel_IniciarSesion.add(campoContrasena);
		
		JPanel panel_PerfilUtilidades = new JPanel();
		UtilDateModel modelo = new UtilDateModel();
		Properties po = new Properties();
		JDatePanelImpl datePanelo = new JDatePanelImpl(modelo, po);
		datePicker_1 = new JDatePickerImpl(datePanelo, new DateLabelFormatter());
		datePicker_1.getJFormattedTextField().setEnabled(false);
		datePicker_1.setBounds(156, 104, 125, 20);
		panel_PerfilUtilidades.add(datePicker_1);
		datePicker_1.setEnabled(false);
		
		JLabel lblModifica = new JLabel("Modificaciones");
		JButton btnAnadirSecciones = new JButton("Secciones");
		JButton btnPaginaSuper = new JButton("Supermercado");
		JButton btnInfo = new JButton("Datos");
		JLabel lblCrear = new JLabel("Creacion");
		JButton btnCreacion = new JButton("Administrador");
		JButton btnAnadirArticulo = new JButton("Articulos");
		JLabel lblSaludo = new JLabel("");
		lblSaludo.setForeground(new Color(0, 0, 255));
		JLabel lblErrorInicioSesion = new JLabel("");
		JButton btnIniciar = new JButton("Iniciar sesion");
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
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(10, 22, 46, 14);
		panel_Registrarse.add(lblDni);
		
		textDNI = new JTextField();
		textDNI.setBounds(212, 19, 188, 20);
		panel_Registrarse.add(textDNI);
		textDNI.setColumns(10);
		
		textNombre = new JTextField();
		textNombre.setBounds(212, 101, 188, 20);
		panel_Registrarse.add(textNombre);
		textNombre.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 104, 68, 14);
		panel_Registrarse.add(lblNombre);
		
		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setBounds(10, 150, 68, 14);
		panel_Registrarse.add(lblApellidos);
		
		textApellidos = new JTextField();
		textApellidos.setBounds(212, 147, 188, 20);
		panel_Registrarse.add(textApellidos);
		textApellidos.setColumns(10);
		
		JLabel lblFechaNa = new JLabel("Fecha de nacimiento:");
		lblFechaNa.setBounds(10, 198, 127, 14);
		panel_Registrarse.add(lblFechaNa);
		
		JLabel lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setBounds(10, 243, 127, 14);
		panel_Registrarse.add(lblContrasena);
		
		contrasenaRegi = new JPasswordField();
		contrasenaRegi.setBounds(212, 240, 188, 20);
		panel_Registrarse.add(contrasenaRegi);
		
		JButton btnAtra1 = new JButton("Atras");
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
		datePicker.setBounds(212, 195, 188, 20);
		panel_Registrarse.add(datePicker);

		JLabel lblErroresLogin = new JLabel("");
		JButton btnRegistrarse1 = new JButton("Registrarse");
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
						gp.insertarPersona(cli);
						usuarios=gp.cargarPersonas();
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
		lblEmail.setBounds(10, 62, 46, 14);
		panel_Registrarse.add(lblEmail);
		
		textMail = new JTextField();
		textMail.setBounds(212, 59, 188, 20);
		panel_Registrarse.add(textMail);
		textMail.setColumns(10);
		
		lblErroresLogin.setForeground(new Color(255, 0, 0));
		lblErroresLogin.setBounds(212, 286, 188, 14);
		panel_Registrarse.add(lblErroresLogin);
		
		
		paneles.addTab("Cuarta", null, panel_PerfilUtilidades, null);
		panel_PerfilUtilidades.setLayout(null);
		
		lblSaludo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSaludo.setBounds(323, 11, 226, 14);
		panel_PerfilUtilidades.add(lblSaludo);
		
		JButton btnCerrarSesion = new JButton("Cerrar Sesion");
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
		btnBorrarPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog (null, "¿Estas seguro?","AVISO", 0)==0) {
					try {
						gp.darseBajaPersona(login);
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
				gp.cambiarPerfilCliente(cliente);
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
		lblCNombre.setBounds(10, 36, 55, 14);
		panel_PerfilUtilidades.add(lblCNombre);
		
		JLabel lblCApellidos = new JLabel("Apellidos:");
		lblCApellidos.setBounds(10, 61, 46, 14);
		panel_PerfilUtilidades.add(lblCApellidos);
		
		JLabel lblCGmail = new JLabel("E-mail:");
		lblCGmail.setBounds(10, 85, 46, 14);
		panel_PerfilUtilidades.add(lblCGmail);
		
		JLabel lblCFechaNacimiento = new JLabel("Fecha de Nacimiento:");
		lblCFechaNacimiento.setBounds(10, 110, 136, 14);
		panel_PerfilUtilidades.add(lblCFechaNacimiento);
		
		JLabel lblCContrasena = new JLabel("Contrasena:");
		lblCContrasena.setBounds(10, 135, 89, 14);
		panel_PerfilUtilidades.add(lblCContrasena);
		
		textCNombre = new JTextField();
		textCNombre.setEnabled(false);
		textCNombre.setBounds(156, 33, 125, 20);
		panel_PerfilUtilidades.add(textCNombre);
		textCNombre.setColumns(10);
		
		
		textCApellidos = new JTextField();
		textCApellidos.setEnabled(false);
		textCApellidos.setBounds(156, 58, 125, 20);
		panel_PerfilUtilidades.add(textCApellidos);
		textCApellidos.setColumns(10);
		
		textCEmail = new JTextField();
		textCEmail.setEnabled(false);
		textCEmail.setBounds(156, 82, 125, 20);
		panel_PerfilUtilidades.add(textCEmail);
		textCEmail.setColumns(10);
		
		passCContrasena = new JPasswordField();
		passCContrasena.setEnabled(false);
		passCContrasena.setBounds(156, 132, 125, 20);
		panel_PerfilUtilidades.add(passCContrasena);
		
		JLabel lblAumentarDinero = new JLabel("Cartera");
		lblAumentarDinero.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAumentarDinero.setBounds(63, 196, 66, 14);
		panel_PerfilUtilidades.add(lblAumentarDinero);
		
		JLabel lblDinero = new JLabel("Dinero actual:");
		lblDinero.setBounds(10, 221, 89, 14);
		panel_PerfilUtilidades.add(lblDinero);
		
		textDineroActual = new JTextField();
		textDineroActual.setEnabled(false);
		textDineroActual.setBounds(116, 218, 86, 20);
		panel_PerfilUtilidades.add(textDineroActual);
		textDineroActual.setColumns(10);
		
		textDineroExtra = new JTextField();
		textDineroExtra.setBounds(116, 248, 86, 20);
		panel_PerfilUtilidades.add(textDineroExtra);
		textDineroExtra.setColumns(10);
		
		JLabel lblDineroExtra = new JLabel("Dinero extra:");
		lblDineroExtra.setBounds(10, 251, 89, 14);
		panel_PerfilUtilidades.add(lblDineroExtra);
		
		JButton btnSumarDinero = new JButton("Agregar");
		btnSumarDinero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					gp.AumentarDineroCliente(cliente, Integer.valueOf(textDineroExtra.getText()));
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
		btnAnadirArticulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paneles.setSelectedIndex(8);
				try {
					su=gsm.buscarSupermercado(admin);
					admin.setSupermercado(su);
					String [] cargaSuper=new String [1];
					cargaSuper[0]=admin.getSupermercado().getEmpresa();
					suAnadirArticulo.setModel(new DefaultComboBoxModel<String>(cargaSuper));
					tipoArticuloCombo.setModel(new DefaultComboBoxModel<String>(	mc.deArrayListAStringArrayNombreSeccion(admin.getSupermercado().getArraySecciones())));
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
		
		btnPaginaSuper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					escogeJefe.setModel(new DefaultComboBoxModel<String>(mc.deArrayListAStringArrayJefe(gp.cargarJefesSinSupermercado(gsm.cargarSupermercados().size()))));
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
		
		btnAnadirSecciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					escogeSuper.setModel(new DefaultComboBoxModel<String>(mc.deArrayListAStringArraySuper(gsm.cargarSupermercados())));
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
		btnComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					
					paneles.setSelectedIndex(9);
				
				
			}
		});
		btnComprar.setBounds(579, 391, 89, 23);
		panel_PerfilUtilidades.add(btnComprar);
		
		JPanel panel_Otros = new JPanel();
		panel_Otros.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 39, 682, 238);
		panel_Otros.add(scrollPane);
		JButton btnAceptar = new JButton("Aceptar");
		JTextArea cambiaDe = new JTextArea();
		JButton btnDescrip = new JButton("Descripcion");
		JButton btnBloquea = new JButton("Bloquear");
		JButton btnDesbloquea = new JButton("Desbloquear");
		paneles.addTab("Quinta", null, panel_Otros, null);
		JButton verArticulos = new JButton("Articulos");
		verArticulos.setBounds(479, 5, 89, 23);
		verArticulos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btnAceptar.setVisible(false);
					btnBloquea.setVisible(false);
					btnDesbloquea.setVisible(false);
					btnDescrip.setVisible(true);
					manejaCambio=3;
					tabla=cv.cargarTabla(listaArticulos);
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
		btnVerUsuarios.setBounds(10, 5, 89, 23);
		btnVerUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btnAceptar.setVisible(false);
				cambiaDe.setVisible(false);
				manejaCambio=1;
				tabla=cv.tablaUsuarios(usuarios);
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
		btnVerSuper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cambiaDe.setVisible(false);
					btnBloquea.setVisible(false);
					btnDesbloquea.setVisible(false);
					manejaCambio=2;
					tabla=cv.tablaSupermercados();
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
		
		btnDesbloquea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cv.accionPorTabla(tabla, usuarios, false);
					btnVerUsuarios.doClick();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnDesbloquea.setBounds(233, 391, 113, 23);
		panel_Otros.add(btnDesbloquea);
		btnDesbloquea.setVisible(false);
		
		
		btnBloquea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cv.accionPorTabla(tabla, usuarios, true);
					btnVerUsuarios.doClick();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnBloquea.setBounds(364, 391, 97, 23);
		panel_Otros.add(btnBloquea);
		btnBloquea.setVisible(false);
		JButton btnVerSecciones = new JButton("Secciones");
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(manejaCambio==1) {
					cv.borrarPorTabla(tabla, usuarios);
					btnVerUsuarios.doClick();
					}
					if(manejaCambio==2) {
						cv.borrarSupermercadoTabla(tabla, gsm.cargarSupermercados());
						btnVerSuper.doClick();
					}
					if(manejaCambio==3) {
						cv.borrarArticuloTabla(tabla,ga.cargarArticulos());
					    verArticulos.doClick();
					}
					if(manejaCambio==4) {
						cv.borrarSeccionTabla(tabla, gs.cargarSecciones());
						btnVerSecciones.doClick();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnBorrar.setBounds(471, 391, 97, 23);
		panel_Otros.add(btnBorrar);
		
		
		btnVerSecciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btnAceptar.setVisible(false);
					cambiaDe.setVisible(false);
					btnBloquea.setVisible(false);
					btnDesbloquea.setVisible(false);
					manejaCambio=4;
					tabla=cv.tablaSecciones();
					scrollPane.setViewportView(tabla);
					btnDescrip.setVisible(false);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnVerSecciones.setBounds(319, 5, 103, 23);
		panel_Otros.add(btnVerSecciones);
		
		JButton btnCargarStocks = new JButton("Recargas");
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(manejaCambio==1) {
						usuarios=cv.realizarCambios(tabla, usuarios);
						btnVerUsuarios.doClick();
					}
					if(manejaCambio==2) {
						cv.modificarSupermercadoTabla(tabla, gsm.cargarSupermercados());
						btnVerSuper.doClick();
					}
					if(manejaCambio==3) {
						cv.modificarArticuloTabla(tabla,ga.cargarArticulos());
					    verArticulos.doClick();
					}
					if(manejaCambio==4) {
						cv.modificarSeccionTabla(tabla, gs.cargarSecciones());
						btnVerSecciones.doClick();
					}
					if(manejaCambio==5) {
						cv.recargarStocks(tabla, listaArticulos);
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
		btnModificar.setBounds(109, 391, 103, 23);
		panel_Otros.add(btnModificar);
		
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
		
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tabla=cv.anadirDescripcion(tabla,cambiaDe.getText(),listaArticulos);
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
					tabla=cv.tablaRecargArticulos();
					scrollPane.setViewportView(tabla);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCargarStocks.setBounds(603, 5, 89, 23);
		panel_Otros.add(btnCargarStocks);
		
		UtilDateModel model2 = new UtilDateModel();
		//model.setDate(2022, 5, 6);
		Properties p2 = new Properties();
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p2);
		
		UtilDateModel model3 = new UtilDateModel();
		//model.setDate(2022, 5, 6);
		Properties p3 = new Properties();
		JDatePanelImpl datePanel3 = new JDatePanelImpl(model3, p3);
		
		JPanel panel_Admin = new JPanel();
		paneles.addTab("Sexta", null, panel_Admin, null);
		panel_Admin.setLayout(null);
		datePicker_3 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		datePicker_3.getJFormattedTextField().setEnabled(false);
		datePicker_3.setBounds(295, 145, 125, 20);
		panel_Admin.add(datePicker_3);
		datePicker_2 = new JDatePickerImpl(datePanel3, new DateLabelFormatter());
		datePicker_2.getJFormattedTextField().setEnabled(false);
		datePicker_2.setBounds(295, 176, 125, 20);
		panel_Admin.add(datePicker_2);
		
		
		
		textDNIJefe = new JTextField();
		textDNIJefe.setBounds(305, 24, 115, 20);
		panel_Admin.add(textDNIJefe);
		textDNIJefe.setColumns(10);
		
		JLabel lblDniJ = new JLabel("DNI:");
		lblDniJ.setBounds(192, 27, 77, 14);
		panel_Admin.add(lblDniJ);
		
		textNombreJefe = new JTextField();
		textNombreJefe.setBounds(305, 55, 115, 20);
		panel_Admin.add(textNombreJefe);
		textNombreJefe.setColumns(10);
		
		JLabel lblCrearAdmin = new JLabel("Creacion del Administrador");
		lblCrearAdmin.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCrearAdmin.setBounds(240, 2, 213, 14);
		panel_Admin.add(lblCrearAdmin);
		
		JLabel lblNombreJefe = new JLabel("Nombre:");
		lblNombreJefe.setBounds(175, 58, 77, 14);
		panel_Admin.add(lblNombreJefe);
		
		JLabel lblApellidosJefe = new JLabel("Apellidos:");
		lblApellidosJefe.setBounds(165, 89, 77, 14);
		panel_Admin.add(lblApellidosJefe);
		
		textApellidosJefe = new JTextField();
		textApellidosJefe.setBounds(305, 86, 115, 20);
		panel_Admin.add(textApellidosJefe);
		textApellidosJefe.setColumns(10);
		
		textGmailJefe = new JTextField();
		textGmailJefe.setBounds(305, 117, 115, 20);
		panel_Admin.add(textGmailJefe);
		textGmailJefe.setColumns(10);
		
		JLabel lblGmailJefe = new JLabel("E-mail:");
		lblGmailJefe.setBounds(179, 120, 46, 14);
		panel_Admin.add(lblGmailJefe);
		
		JLabel lblFechaNacimientoJefe = new JLabel("Fecha de nacimiento:");
		lblFechaNacimientoJefe.setBounds(111, 151, 131, 14);
		panel_Admin.add(lblFechaNacimientoJefe);
		
		JLabel lblFechaAdquisicion = new JLabel("Fecha adquisicion:");
		lblFechaAdquisicion.setBounds(121, 176, 131, 14);
		panel_Admin.add(lblFechaAdquisicion);
		
		JLabel lblPorcentajeEmpresa = new JLabel("Porcentaje empresa:");
		lblPorcentajeEmpresa.setBounds(111, 216, 131, 14);
		panel_Admin.add(lblPorcentajeEmpresa);
		
		JLabel lblContrasenaJefe = new JLabel("Contrasena:");
		lblContrasenaJefe.setBounds(148, 241, 77, 14);
		panel_Admin.add(lblContrasenaJefe);
		
		passJefe = new JPasswordField();
		passJefe.setBounds(305, 238, 115, 20);
		panel_Admin.add(passJefe);
		JButton btnCancelarJefe = new JButton("Cancelar");
		JSpinner porcentajeEmpresa = new JSpinner();
		JLabel lblErroresASS = new JLabel("");
		JButton btnConfirmarCreacion = new JButton("Confirmar");
		btnConfirmarCreacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cv.comprobarCampos(panel_Admin);
					nuevoJefe=new Jefe(textDNIJefe.getText(),textNombreJefe.getText(),textApellidos.getText(),
					mc.deStringADate(datePicker_3.getJFormattedTextField().getText()),textGmailJefe.getText(),
					String.valueOf(passJefe.getPassword()),tipoPersona.Jefe,
					mc.deStringADate(datePicker_2.getJFormattedTextField().getText()),(Float)porcentajeEmpresa.getValue(),0);
					gp.insertarPersona(nuevoJefe);
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
		
		JButton btnCancelarSuper = new JButton("Cancelar");
		btnCancelarSuper.setBounds(304, 278, 89, 23);
		panel_Supermercado.add(btnCancelarSuper);
		
		JLabel lblCrearSuper = new JLabel("Creacion del Supermercado");
		lblCrearSuper.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCrearSuper.setBounds(259, 12, 201, 14);
		panel_Supermercado.add(lblCrearSuper);
		
		JLabel lblCodigoSuper = new JLabel("Codigo:");
		lblCodigoSuper.setBounds(259, 81, 46, 14);
		panel_Supermercado.add(lblCodigoSuper);
		
		textCodigoSuper = new JTextField();
		textCodigoSuper.setBounds(345, 78, 115, 20);
		panel_Supermercado.add(textCodigoSuper);
		textCodigoSuper.setColumns(10);
		
		JLabel lblEmpresa = new JLabel("Empresa:");
		lblEmpresa.setBounds(259, 112, 77, 14);
		panel_Supermercado.add(lblEmpresa);
		
		textEmpresa = new JTextField();
		textEmpresa.setBounds(345, 109, 115, 20);
		panel_Supermercado.add(textEmpresa);
		textEmpresa.setColumns(10);
		
		JLabel lblDireccion = new JLabel("Direccion:");
		lblDireccion.setBounds(259, 143, 77, 14);
		panel_Supermercado.add(lblDireccion);
		
		textDireccion = new JTextField();
		textDireccion.setBounds(345, 140, 115, 20);
		panel_Supermercado.add(textDireccion);
		textDireccion.setColumns(10);
		
		JLabel lblNumEmple = new JLabel("Numero empleados:");
		lblNumEmple.setBounds(262, 168, 131, 14);
		panel_Supermercado.add(lblNumEmple);
		
		JSpinner NumEmple = new JSpinner();
		NumEmple.setModel(new SpinnerNumberModel(1, 1, 25, 1));
		NumEmple.setBounds(386, 165, 46, 20);
		panel_Supermercado.add(NumEmple);
		
		JButton btnAtrasSuper = new JButton("Atras");
		JLabel lblErroresSuper = new JLabel("");
		JButton btnConfirmarSupermercado = new JButton("Confirmar");
		btnConfirmarSupermercado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					cv.comprobarCampos(panel_Supermercado);
					supermercado=new Supermercado(textCodigoSuper.getText(),textEmpresa.getText(),textDireccion.getText(),(Integer) NumEmple.getValue(),null);
					nuevoJefe=(Jefe) gp.buscarPersona(String.valueOf(escogeJefe.getItemAt(escogeJefe.getSelectedIndex())), usuarios);
					gsm.insertarSupermercado(nuevoJefe, supermercado);
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
		lblJefe.setBounds(221, 41, 115, 14);
		panel_Supermercado.add(lblJefe);
		
		
		lblErroresSuper.setForeground(new Color(255, 0, 0));
		lblErroresSuper.setBounds(259, 220, 201, 14);
		panel_Supermercado.add(lblErroresSuper);
		
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
		
		JLabel lblCreaSecciones = new JLabel("Creacion de Secciones");
		lblCreaSecciones.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCreaSecciones.setBounds(221, 5, 152, 17);
		 panel_Seccion.add(lblCreaSecciones);
		 
		 textPrimeraSe = new JTextField();
			textPrimeraSe.setEnabled(false);
			textPrimeraSe.setBounds(286, 160, 112, 20);
			panel_Seccion.add(textPrimeraSe);
			textPrimeraSe.setColumns(10);
			textPrimeraSe.setVisible(false);
			
			JLabel lblPrimeraSe = new JLabel("Primera:");
			lblPrimeraSe.setBounds(181, 163, 69, 14);
			panel_Seccion.add(lblPrimeraSe);
			lblPrimeraSe.setVisible(false);
			
			JLabel lblSegundaSe = new JLabel("Segunda:");
			lblSegundaSe.setBounds(181, 195, 69, 14);
			panel_Seccion.add(lblSegundaSe);
			lblSegundaSe.setVisible(false);
			
			textSegundaSe = new JTextField();
			textSegundaSe.setEnabled(false);
			textSegundaSe.setBounds(286, 192, 112, 20);
			panel_Seccion.add(textSegundaSe);
			textSegundaSe.setColumns(10);
			textSegundaSe.setVisible(false);
			
			JLabel lblTerceraSe = new JLabel("Tercera:");
			lblTerceraSe.setBounds(181, 226, 69, 14);
			panel_Seccion.add(lblTerceraSe);
			lblTerceraSe.setVisible(false);
			
			textTerceraSe = new JTextField();
			textTerceraSe.setEnabled(false);
			textTerceraSe.setBounds(286, 223, 112, 20);
			panel_Seccion.add(textTerceraSe);
			textTerceraSe.setColumns(10);
			textTerceraSe.setVisible(false);
		
		JComboBox<String> tipoSeccion = new JComboBox<String>();
		tipoSeccion.setBounds(286, 118, 112, 20);
		tipoSeccion.setModel(new DefaultComboBoxModel<String>(Tipos));
		panel_Seccion.add(tipoSeccion);
		tipoSeccion.setVisible(false);
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(196, 121, 35, 14);
		panel_Seccion.add(lblTipo);
		lblTipo.setVisible(false);
		
		JButton btnSelecSe = new JButton("Seleccionar");
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
		JLabel lblEscogeDireccion = new JLabel("Direccion:");
		lblEscogeDireccion.setVisible(false);
		JComboBox<String> escogeDireccion = new JComboBox<String>();
		JButton btnCancelarSe = new JButton("Cancelar");
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
		 btnAtrasSe.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		btnCancelarSe.doClick();
		 		paneles.setSelectedIndex(3);
		 	}
		 });
		 btnAtrasSe.setBounds(10, 391, 89, 23);
		 panel_Seccion.add(btnAtrasSe);
		 
		 btnConfirmarSe.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		try {	
		 			if(!textPrimeraSe.getText().equals("") && textPrimeraSe.isVisible() && supermercado.getArraySecciones().size()==0) {
		 				seccion=new Seccion(supermercado.getCodigoSuper()+"1",tipoArticulo.valueOf(textPrimeraSe.getText()),0,null);
		 					gs.insertarSeccion(supermercado, seccion);
		 			} 
		 			if(!textSegundaSe.getText().equals("") && textSegundaSe.isVisible()&& supermercado.getArraySecciones().size()==1) {
		 				seccion=new Seccion(supermercado.getCodigoSuper()+"2",tipoArticulo.valueOf(textSegundaSe.getText()),0,null);
		 					gs.insertarSeccion(supermercado, seccion);
		 			}
		 			if(!textSegundaSe.getText().equals("") && textTerceraSe.isVisible()&& supermercado.getArraySecciones().size()==2) {
		 				seccion=new Seccion(supermercado.getCodigoSuper()+"3",tipoArticulo.valueOf(textTerceraSe.getText()),0,null);
		 					gs.insertarSeccion(supermercado, seccion);
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
					escogeDireccion.setModel(new DefaultComboBoxModel<String>(mc.deArrayListAStringArrayDireccion(gsm.buscarSuperEmpresa(escogeSuper.getItemAt(escogeSuper.getSelectedIndex())))));
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
		 lblSuperPadre.setBounds(164, 37, 112, 14);
		 panel_Seccion.add(lblSuperPadre);
		 
		 escogeDireccion.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		try {
		 			lblPrimeraSe.setVisible(true);
			 		textPrimeraSe.setVisible(true);
			 		tipoSeccion.setVisible(true);
			 		lblTipo.setVisible(true);
					supermercado=gsm.buscarSuperEmpresaDireccion(escogeSuper.getItemAt(escogeSuper.getSelectedIndex()),escogeDireccion.getItemAt(escogeDireccion.getSelectedIndex()));
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
		lblNombreArticulo.setBounds(10, 83, 75, 14);
		panel_Articulos.add(lblNombreArticulo);
		
		textNombreArticulo = new JTextField();
		textNombreArticulo.setBounds(95, 80, 123, 20);
		panel_Articulos.add(textNombreArticulo);
		textNombreArticulo.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Descripcion(Opcional):");
		lblDescripcion.setBounds(10, 116, 156, 14);
		panel_Articulos.add(lblDescripcion);
		
		JTextArea textDescripcion = new JTextArea();
		textDescripcion.setLineWrap(true);
		textDescripcion.setBounds(10, 141, 234, 227);
		panel_Articulos.add(textDescripcion);
		
		JLabel lblImagen = new JLabel("Imagen y formato:");
		lblImagen.setBounds(254, 83, 179, 14);
		panel_Articulos.add(lblImagen);
		
		textImagen = new JTextField();
		textImagen.setBounds(272, 125, 161, 20);
		panel_Articulos.add(textImagen);
		textImagen.setColumns(10);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(254, 175, 46, 14);
		panel_Articulos.add(lblPrecio);
		
		textPrecio = new JTextField();
		textPrecio.setBounds(318, 172, 115, 20);
		panel_Articulos.add(textPrecio);
		textPrecio.setColumns(10);
		
		JSpinner Garantia = new JSpinner();
		Garantia.setVisible(false);
		JCheckBox chckElectrica = new JCheckBox("Electrica");
		
		tipoArticuloCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insercion=mc.cogeSeccion(admin.getSupermercado().getArraySecciones(), tipoArticuloCombo.getItemAt(tipoArticuloCombo.getSelectedIndex()));
				if(tipoArticuloCombo.getSelectedItem().equals("Comida")) {
					datePicker_4.setVisible(true);
					textProcedencia.setVisible(true);
					Garantia.setVisible(false);
					chckElectrica.setVisible(false);
					textTalla.setVisible(false);
					textMarca.setVisible(false);
				}else if(tipoArticuloCombo.getSelectedItem().equals("Herramienta")){
					Garantia.setVisible(true);
					chckElectrica.setVisible(true);
					datePicker_4.setVisible(false);
					textProcedencia.setVisible(false);
					textTalla.setVisible(false);
					textMarca.setVisible(false);
				}else {
					Garantia.setVisible(false);
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
		lblSeccion.setBounds(254, 221, 66, 14);
		panel_Articulos.add(lblSeccion);
		
		JLabel lblTalla = new JLabel("Talla:");
		lblTalla.setBounds(472, 83, 46, 14);
		panel_Articulos.add(lblTalla);
		
		textTalla = new JTextField();
		textTalla.setBounds(543, 80, 123, 20);
		panel_Articulos.add(textTalla);
		textTalla.setColumns(10);
		textTalla.setVisible(false);
		
		JLabel lblMarca = new JLabel("Marca:");
		lblMarca.setBounds(472, 128, 46, 14);
		panel_Articulos.add(lblMarca);
		
		textMarca = new JTextField();
		textMarca.setBounds(543, 125, 123, 20);
		panel_Articulos.add(textMarca);
		textMarca.setColumns(10);
		textMarca.setVisible(false);
		
		JLabel lblFechaDeCaducidad = new JLabel("Fecha de Caducidad");
		lblFechaDeCaducidad.setBounds(525, 163, 141, 14);
		panel_Articulos.add(lblFechaDeCaducidad);
		
		JLabel lblProcedencia = new JLabel("Procedencia:");
		lblProcedencia.setBounds(449, 236, 84, 14);
		panel_Articulos.add(lblProcedencia);
		
		textProcedencia = new JTextField();
		textProcedencia.setBounds(543, 233, 123, 20);
		panel_Articulos.add(textProcedencia);
		textProcedencia.setColumns(10);
		textProcedencia.setVisible(false);
		
		chckElectrica.setBounds(553, 260, 97, 23);
		panel_Articulos.add(chckElectrica);
		chckElectrica.setVisible(false);
		
		
		Garantia.setModel(new SpinnerNumberModel(2, 2, 5, 1));
		Garantia.setBounds(588, 297, 30, 20);
		panel_Articulos.add(Garantia);
		
		JLabel lblGarantia = new JLabel("Garantia:");
		lblGarantia.setBounds(471, 300, 60, 14);
		panel_Articulos.add(lblGarantia);
		
		UtilDateModel modelx = new UtilDateModel();
		//model.setDate(2022, 5, 6);
		Properties px = new Properties();
		JDatePanelImpl datePanelx = new JDatePanelImpl(modelx, px);
		datePicker_4 = new JDatePickerImpl(datePanelx, new DateLabelFormatter());
		datePicker_4.setBounds(541, 199, 125, 20);
		panel_Articulos.add(datePicker_4);
		datePicker_4.setVisible(false);
		JLabel lblErroresArti = new JLabel("");
		
		JButton btnInsertar = new JButton("Confirmar");
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				if(tipoArticuloCombo.getSelectedItem().equals("Comida")) {
						nuevaComida=new Comida(0, textNombreArticulo.getText(), textImagen.getText(), textDescripcion.getText(), Float.valueOf(textPrecio.getText()), 100, mc.deStringADate(datePicker_4.getJFormattedTextField().getText()), textProcedencia.getText());
						ga.insertarArticulo(insercion, nuevaComida);
				}else if(tipoArticuloCombo.getSelectedItem().equals("Herramienta")) {
						nuevaHerramienta=new Herramienta(0, textNombreArticulo.getText(), textImagen.getText(), textDescripcion.getText(), Float.valueOf(textPrecio.getText()), 100, mc.pasarBoolean(chckElectrica.isSelected()), (Integer)Garantia.getValue());
						ga.insertarArticulo(insercion, nuevaHerramienta);
				}else if(tipoArticuloCombo.getSelectedItem().equals("Ropa")) {
						nuevaRopa=new Ropa(0, textNombreArticulo.getText(), textImagen.getText(), textDescripcion.getText(), Float.valueOf(textPrecio.getText()), 100, textTalla.getText(), textMarca.getText());
						ga.insertarArticulo(insercion, nuevaRopa);
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
		
		JLabel lblPrecioTotal = new JLabel("Precio de su carrito:");
		JComboBox<String> boxSeccion = new JComboBox<String>();
		JScrollPane buscaArticulos=new JScrollPane();
		buscaArticulos.setViewportView(cv.mostrarArticulos(listaArticulos,carrito,lblPrecioTotal));
		JComboBox<String>  boxSuper = new JComboBox<String> ();
		boxSuper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					supermercados=gsm.cogerSeccionesMultiplesSu(supermercados);
					seccionesDelSuper=supermercados.get(boxSuper.getSelectedIndex()).getArraySecciones();
					boxSeccion.setModel(new DefaultComboBoxModel<String>(mc.deArrayListAStringArrayNombreSeccion(seccionesDelSuper)));
					boxSeccion.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		boxSuper.setModel(new DefaultComboBoxModel<String>(mc.deArrayListAStringArraySuper(supermercados)));
		boxSuper.setBounds(10, 11, 125, 22);
		panel_Compras.add(boxSuper);
		
		boxSeccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					listaArticulos=ga.cargarArticulosPorSeccion(seccionesDelSuper.get(boxSeccion.getSelectedIndex()));
					buscaArticulos.setViewportView(cv.mostrarArticulos(listaArticulos,carrito,lblPrecioTotal));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		boxSeccion.setVisible(false);
		boxSeccion.setBounds(10, 44, 125, 22);
		panel_Compras.add(boxSeccion);
		
		
		buscaArticulos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		buscaArticulos.setBounds(0, 77, 702, 324);
		panel_Compras.add(buscaArticulos);
		
		textBuscador = new JTextField();
		textBuscador.setBounds(354, 26, 207, 20);
		panel_Compras.add(textBuscador);
		textBuscador.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					listaArticulos=ga.buscarArticulosPorNombre(textBuscador.getText());
					buscaArticulos.setViewportView(cv.mostrarArticulos(listaArticulos,carrito,lblPrecioTotal));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnBuscar.setBounds(255, 25, 89, 23);
		panel_Compras.add(btnBuscar);
		
		JButton btnTodo = new JButton("Todo");
		btnTodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					listaArticulos=ga.cargarArticulos();
					buscaArticulos.setViewportView(cv.mostrarArticulos(listaArticulos,carrito,lblPrecioTotal));
					boxSeccion.setVisible(false);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnTodo.setBounds(156, 25, 89, 23);
		panel_Compras.add(btnTodo);
		
		JButton btnAtrasCom = new JButton("Atras");
		btnAtrasCom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paneles.setSelectedIndex(3);
			}
		});
		btnAtrasCom.setBounds(10, 402, 89, 23);
		panel_Compras.add(btnAtrasCom);
		
		JButton btnVerCarrito = new JButton("Carrito");
		btnVerCarrito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscaArticulos.setViewportView(cv.mostrarCarrito(carrito,lblPrecioTotal));
			}
		});
		btnVerCarrito.setBounds(571, 25, 89, 23);
		panel_Compras.add(btnVerCarrito);
		
		
		lblPrecioTotal.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPrecioTotal.setBounds(265, 404, 189, 14);
		panel_Compras.add(lblPrecioTotal);
		
		JButton btnRealizarCompra = new JButton("Comprar");
		btnRealizarCompra.setBounds(603, 402, 89, 23);
		panel_Compras.add(btnRealizarCompra);
		
		
	}
}