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
import modelo.*;

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
	
	private Jefe nuevoJefe=null;
	private Supermercado supermercado=null;
	private Seccion seccion;
	private Boolean cambios=true;
	private int cuentaSecciones=0;
	private ArrayList<Articulo> listaArticulos;
	private ArrayList<Persona> usuarios;
	private Persona login;
	private Cliente cliente;
	private Metodos mc=new Metodos();
	private MetodosVista cv = new MetodosVista();
	private GestorPersona gp=new GestorPersona();
	private String [] Tipos={"Comida","Herramienta","Ropa"};
	private JTextField textPrimeraSe;
	private JTextField textSegundaSe;
	private JTextField textTerceraSe;
	private GestorSupermercado gsm=new GestorSupermercado();
	private GestorSeccion gs=new GestorSeccion();
	private JTextField textNombreArticulo;
	private JTextField textImagen;
	private JTextField textPrecio;
	private JTextField textTalla;
	private JTextField textMarca;
	private JTextField textProcedencia;
	/**
	private GestorArticuloComprado gac=new GestorArticuloComprado();
	
	private GestorArticulo ga=new GestorArticulo();
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
					window.frame.setVisible(true);
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
			usuarios=gp.cargarPersonas();
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		frame = new JFrame();
		frame.setBounds(100, 100, 723, 492);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 707, 453);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Primera", null, panel, null);
		panel.setLayout(null);
		
		JButton btnIniSes = new JButton("Iniciar sesion");
		btnIniSes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(1);
			}
		});
		btnIniSes.setBounds(263, 181, 127, 23);
		panel.add(btnIniSes);
		
		JButton btnRegis = new JButton("Registrarse");
		btnRegis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(2);
			}
		});
		btnRegis.setBounds(263, 252, 127, 23);
		panel.add(btnRegis);
		
		JLabel lblMensajeIni = new JLabel("Bienvenido al Super Elorrieta!");
		lblMensajeIni.setFont(new Font("Tahoma", Font.BOLD, 34));
		lblMensajeIni.setBounds(74, 31, 536, 81);
		panel.add(lblMensajeIni);
		
		JLabel lblIniSes = new JLabel("Pulse en este botón para acceder a su cuenta.");
		lblIniSes.setBounds(214, 156, 283, 14);
		panel.add(lblIniSes);
		
		JLabel lblCreaCuen = new JLabel("Pulse este si quiere crear una cuenta.");
		lblCreaCuen.setBounds(227, 227, 239, 14);
		panel.add(lblCreaCuen);
		
		JButton btnInvi = new JButton("Invitado");
		btnInvi.setBounds(283, 316, 89, 23);
		panel.add(btnInvi);
		
		JLabel lblInvi = new JLabel("Si solo va a mirar nuestros productos pulse el botón de Invitado.");
		lblInvi.setBounds(182, 291, 362, 14);
		panel.add(lblInvi);
		
		JPanel panel_IniciarSesion = new JPanel();
		tabbedPane.addTab("Segundo", null, panel_IniciarSesion, null);
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
		//model.setDate(2022, 5, 6);
		Properties po = new Properties();
		JDatePanelImpl datePanelo = new JDatePanelImpl(modelo, po);
		datePicker_1 = new JDatePickerImpl(datePanelo, new DateLabelFormatter());
		datePicker_1.getJFormattedTextField().setEnabled(false);
		datePicker_1.setBounds(116, 104, 125, 20);
		panel_PerfilUtilidades.add(datePicker_1);
		datePicker_1.setEnabled(false);
		
		JLabel lblSaludo = new JLabel("");
		lblSaludo.setForeground(new Color(0, 0, 255));
		JLabel lblErrorInicioSesion = new JLabel("");
		JButton btnIniciar = new JButton("Iniciar sesion");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					login=gp.iniciarSesion(usuarios,cemail.getText(),String.valueOf(campoContrasena.getPassword()));
					tabbedPane.setSelectedIndex(3);
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
					textDineroActual.setText(String.valueOf(cliente.getDinero()));
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
				tabbedPane.setSelectedIndex(0);
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
		tabbedPane.addTab("Tercera", null, panel_Registrarse, null);
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
				tabbedPane.setSelectedIndex(0);
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
						tabbedPane.setSelectedIndex(1);
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
		
		
		tabbedPane.addTab("Cuarta", null, panel_PerfilUtilidades, null);
		panel_PerfilUtilidades.setLayout(null);
		
		lblSaludo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSaludo.setBounds(323, 11, 226, 14);
		panel_PerfilUtilidades.add(lblSaludo);
		
		JButton btnCerrarSesion = new JButton("Cerrar Sesion");
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login=null;
				tabbedPane.setSelectedIndex(0);
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
						tabbedPane.setSelectedIndex(0);
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
		lblCFechaNacimiento.setBounds(10, 110, 103, 14);
		panel_PerfilUtilidades.add(lblCFechaNacimiento);
		
		JLabel lblCContrasena = new JLabel("Contrasena:");
		lblCContrasena.setBounds(10, 135, 89, 14);
		panel_PerfilUtilidades.add(lblCContrasena);
		
		textCNombre = new JTextField();
		textCNombre.setEnabled(false);
		textCNombre.setBounds(116, 33, 125, 20);
		panel_PerfilUtilidades.add(textCNombre);
		textCNombre.setColumns(10);
		
		
		textCApellidos = new JTextField();
		textCApellidos.setEnabled(false);
		textCApellidos.setBounds(116, 58, 125, 20);
		panel_PerfilUtilidades.add(textCApellidos);
		textCApellidos.setColumns(10);
		
		textCEmail = new JTextField();
		textCEmail.setEnabled(false);
		textCEmail.setBounds(116, 82, 125, 20);
		panel_PerfilUtilidades.add(textCEmail);
		textCEmail.setColumns(10);
		
		passCContrasena = new JPasswordField();
		passCContrasena.setEnabled(false);
		passCContrasena.setBounds(116, 132, 125, 20);
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
		
		JButton btnInfo = new JButton("Datos");
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(4);
			}
		});
		btnInfo.setBounds(558, 101, 110, 23);
		panel_PerfilUtilidades.add(btnInfo);
		
		JButton btnCreacion = new JButton("Supermercado");
		btnCreacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(5);
			}
		});
		btnCreacion.setBounds(401, 101, 110, 23);
		panel_PerfilUtilidades.add(btnCreacion);
		
		JButton btnAñadirArticulo = new JButton("Articulos");
		btnAñadirArticulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane.setSelectedIndex(7);
			}
		});
		btnAñadirArticulo.setBounds(401, 159, 110, 23);
		panel_PerfilUtilidades.add(btnAñadirArticulo);
		
		JLabel lblCrear = new JLabel("Anadir");
		lblCrear.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCrear.setBounds(420, 59, 66, 14);
		panel_PerfilUtilidades.add(lblCrear);
		
		JPanel panel_Otros = new JPanel();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 49, 682, 163);
		panel_Otros.add(scrollPane);
		
		
		tabbedPane.addTab("Quinta", null, panel_Otros, null);
		JButton verArticulos = new JButton("Ver Articulos");
		verArticulos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
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
		panel_Otros.setLayout(null);
		verArticulos.setBounds(304, 5, 132, 23);
		panel_Otros.add(verArticulos);
		
		JButton btnVerUsuarios = new JButton("Usuarios");
		btnVerUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				tabla=cv.tablaUsuarios(usuarios);
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
		btnVerUsuarios.setBounds(10, 5, 89, 23);
		panel_Otros.add(btnVerUsuarios);
		
		JButton btnEjecutarCambios = new JButton("Confirmar");
		btnEjecutarCambios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					usuarios=cv.realizarCambios(tabla, usuarios);
					for(int i=0;i<usuarios.size();i++) {
						System.out.println(usuarios.get(i).toString());
						if(usuarios.get(i) instanceof Cliente) {
							Cliente cli=(Cliente)usuarios.get(i);
							System.out.print(cli.isBloqueado());						}
					}
				} catch (ErroresDeOperaciones e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnEjecutarCambios.setBounds(236, 391, 103, 23);
		panel_Otros.add(btnEjecutarCambios);
		
		JLabel lblAviso = new JLabel("Para bloquear un usuario escriba :'Bloqueado' en la cuarta columna.");
		lblAviso.setForeground(new Color(0, 128, 192));
		lblAviso.setBounds(10, 222, 413, 23);
		panel_Otros.add(lblAviso);
		
		JLabel lblAviso2 = new JLabel("Cualquier otro texto se interpretara como un desbloqueo.");
		lblAviso2.setForeground(new Color(0, 128, 192));
		lblAviso2.setBounds(10, 240, 329, 14);
		panel_Otros.add(lblAviso2);
		
		JLabel lblAviso3 = new JLabel("Los Jefes no pueden alterar su estado.");
		lblAviso3.setForeground(new Color(0, 128, 192));
		lblAviso3.setBounds(10, 256, 329, 14);
		panel_Otros.add(lblAviso3);
		
		JLabel lblAviso4 = new JLabel("Para realizar los cambios dele al <Enter> en la celda.");
		lblAviso4.setForeground(new Color(255, 128, 128));
		lblAviso4.setBounds(10, 271, 329, 14);
		panel_Otros.add(lblAviso4);
		
		JPanel panel_AdminSuperSeccion = new JPanel();
		tabbedPane.addTab("Sexta", null, panel_AdminSuperSeccion, null);
		panel_AdminSuperSeccion.setLayout(null);
		
		UtilDateModel model2 = new UtilDateModel();
		//model.setDate(2022, 5, 6);
		Properties p2 = new Properties();
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p2);
		datePicker_3 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		datePicker_3.getJFormattedTextField().setEnabled(false);
		datePicker_3.setBounds(136, 145, 125, 20);
		panel_AdminSuperSeccion.add(datePicker_3);
		
		UtilDateModel model3 = new UtilDateModel();
		//model.setDate(2022, 5, 6);
		Properties p3 = new Properties();
		JDatePanelImpl datePanel3 = new JDatePanelImpl(model3, p3);
		datePicker_2 = new JDatePickerImpl(datePanel3, new DateLabelFormatter());
		datePicker_2.getJFormattedTextField().setEnabled(false);
		datePicker_2.setBounds(136, 176, 125, 20);
		panel_AdminSuperSeccion.add(datePicker_2);
		
		
		
		textDNIJefe = new JTextField();
		textDNIJefe.setBounds(137, 24, 115, 20);
		panel_AdminSuperSeccion.add(textDNIJefe);
		textDNIJefe.setColumns(10);
		
		JLabel lblDniJ = new JLabel("DNI:");
		lblDniJ.setBounds(10, 27, 77, 14);
		panel_AdminSuperSeccion.add(lblDniJ);
		
		textNombreJefe = new JTextField();
		textNombreJefe.setBounds(137, 55, 115, 20);
		panel_AdminSuperSeccion.add(textNombreJefe);
		textNombreJefe.setColumns(10);
		
		JLabel lblCrearAdmin = new JLabel("Creacion del Administrador");
		lblCrearAdmin.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCrearAdmin.setBounds(10, 0, 213, 14);
		panel_AdminSuperSeccion.add(lblCrearAdmin);
		
		JLabel lblNombreJefe = new JLabel("Nombre:");
		lblNombreJefe.setBounds(10, 58, 77, 14);
		panel_AdminSuperSeccion.add(lblNombreJefe);
		
		JLabel lblApellidosJefe = new JLabel("Apellidos:");
		lblApellidosJefe.setBounds(10, 89, 77, 14);
		panel_AdminSuperSeccion.add(lblApellidosJefe);
		
		textApellidosJefe = new JTextField();
		textApellidosJefe.setBounds(137, 86, 115, 20);
		panel_AdminSuperSeccion.add(textApellidosJefe);
		textApellidosJefe.setColumns(10);
		
		textGmailJefe = new JTextField();
		textGmailJefe.setBounds(137, 117, 115, 20);
		panel_AdminSuperSeccion.add(textGmailJefe);
		textGmailJefe.setColumns(10);
		
		JLabel lblGmailJefe = new JLabel("E-mail:");
		lblGmailJefe.setBounds(10, 120, 46, 14);
		panel_AdminSuperSeccion.add(lblGmailJefe);
		
		JLabel lblFechaNacimientoJefe = new JLabel("Fecha de nacimiento:");
		lblFechaNacimientoJefe.setBounds(10, 151, 131, 14);
		panel_AdminSuperSeccion.add(lblFechaNacimientoJefe);
		
		JLabel lblFechaAdquisicion = new JLabel("Fecha adquisicion:");
		lblFechaAdquisicion.setBounds(10, 182, 131, 14);
		panel_AdminSuperSeccion.add(lblFechaAdquisicion);
		
		JLabel lblPorcentajeEmpresa = new JLabel("Porcentaje empresa:");
		lblPorcentajeEmpresa.setBounds(10, 210, 131, 14);
		panel_AdminSuperSeccion.add(lblPorcentajeEmpresa);
		
		JLabel lblContrasenaJefe = new JLabel("Contrasena:");
		lblContrasenaJefe.setBounds(10, 241, 77, 14);
		panel_AdminSuperSeccion.add(lblContrasenaJefe);
		
		passJefe = new JPasswordField();
		passJefe.setBounds(136, 238, 115, 20);
		panel_AdminSuperSeccion.add(passJefe);
		
		JLabel lblCrearSuper = new JLabel("Creacion del Supermercado");
		lblCrearSuper.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCrearSuper.setBounds(259, 0, 201, 14);
		panel_AdminSuperSeccion.add(lblCrearSuper);
		
		JLabel lblCodigoSuper = new JLabel("Codigo:");
		lblCodigoSuper.setBounds(269, 27, 46, 14);
		panel_AdminSuperSeccion.add(lblCodigoSuper);
		
		textCodigoSuper = new JTextField();
		textCodigoSuper.setBounds(345, 24, 115, 20);
		panel_AdminSuperSeccion.add(textCodigoSuper);
		textCodigoSuper.setColumns(10);
		
		JLabel lblEmpresa = new JLabel("Empresa:");
		lblEmpresa.setBounds(268, 58, 77, 14);
		panel_AdminSuperSeccion.add(lblEmpresa);
		
		textEmpresa = new JTextField();
		textEmpresa.setBounds(345, 55, 115, 20);
		panel_AdminSuperSeccion.add(textEmpresa);
		textEmpresa.setColumns(10);
		
		JLabel lblDireccion = new JLabel("Direccion:");
		lblDireccion.setBounds(262, 89, 77, 14);
		panel_AdminSuperSeccion.add(lblDireccion);
		
		textDireccion = new JTextField();
		textDireccion.setBounds(345, 86, 115, 20);
		panel_AdminSuperSeccion.add(textDireccion);
		textDireccion.setColumns(10);
		
		JLabel lblNumEmple = new JLabel("Numero empleados:");
		lblNumEmple.setBounds(262, 120, 131, 14);
		panel_AdminSuperSeccion.add(lblNumEmple);
		
		JSpinner NumEmple = new JSpinner();
		NumEmple.setModel(new SpinnerNumberModel(1, 1, 25, 1));
		NumEmple.setBounds(373, 120, 46, 20);
		panel_AdminSuperSeccion.add(NumEmple);
		
		JLabel lblCreaSecciones = new JLabel("Creacion de Secciones");
		lblCreaSecciones.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCreaSecciones.setBounds(470, 0, 171, 14);
		panel_AdminSuperSeccion.add(lblCreaSecciones);
		
		JComboBox<String> tipoSeccion = new JComboBox<String>();
		tipoSeccion.setBounds(525, 23, 104, 22);
		tipoSeccion.setModel(new DefaultComboBoxModel<String>(Tipos));
		panel_AdminSuperSeccion.add(tipoSeccion);
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(469, 27, 46, 14);
		panel_AdminSuperSeccion.add(lblTipo);
		
		JButton btnCancelarSuper = new JButton("Cancelar");
		JButton btnCancelarSe = new JButton("Cancelar");
		JButton btnCancelarJefe = new JButton("Cancelar");
		JSpinner porcentajeEmpresa = new JSpinner();
		JLabel lblErroresASS = new JLabel("");
		JButton btnConfirmarCreacion = new JButton("Confirmar");
		btnConfirmarCreacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cv.comprobarCampos(panel_AdminSuperSeccion);
					nuevoJefe=new Jefe(textDNIJefe.getText(),textNombreJefe.getText(),textApellidos.getText(),
					mc.deStringADate(datePicker_3.getJFormattedTextField().getText()),textGmailJefe.getText(),
					String.valueOf(passJefe.getPassword()),tipoPersona.Jefe,
					mc.deStringADate(datePicker_2.getJFormattedTextField().getText()),(Float)porcentajeEmpresa.getValue(),0);
					supermercado=new Supermercado(textCodigoSuper.getText(),textEmpresa.getText(),textDireccion.getText(),(Integer) NumEmple.getValue(),null);
					gp.insertarPersona(nuevoJefe);
					gsm.insertarSupermercado(nuevoJefe, supermercado);
					if(!textPrimeraSe.getText().equals("")) {
						seccion=new Seccion(supermercado.getCodigoSuper()+"1",tipoArticulo.valueOf(textPrimeraSe.getText()),null);
						gs.insertarSeccion(supermercado, seccion);
					} 
					if(!textSegundaSe.getText().equals("")) {
						seccion=new Seccion(supermercado.getCodigoSuper()+"2",tipoArticulo.valueOf(textPrimeraSe.getText()),null);
						gs.insertarSeccion(supermercado, seccion);
					}
					if(!textSegundaSe.getText().equals("")) {
						seccion=new Seccion(supermercado.getCodigoSuper()+"3",tipoArticulo.valueOf(textPrimeraSe.getText()),null);
						gs.insertarSeccion(supermercado, seccion);
					}
					btnCancelarSuper.doClick();
					btnCancelarSe.doClick();
					btnCancelarJefe.doClick();
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
		btnConfirmarCreacion.setBounds(411, 377, 104, 23);
		panel_AdminSuperSeccion.add(btnConfirmarCreacion);
		
		textPrimeraSe = new JTextField();
		textPrimeraSe.setEnabled(false);
		textPrimeraSe.setBounds(543, 55, 86, 20);
		panel_AdminSuperSeccion.add(textPrimeraSe);
		textPrimeraSe.setColumns(10);
		
		JLabel lblPrimeraSe = new JLabel("Primera:");
		lblPrimeraSe.setBounds(469, 58, 64, 14);
		panel_AdminSuperSeccion.add(lblPrimeraSe);
		
		JLabel lblSegundaSe = new JLabel("Segunda:");
		lblSegundaSe.setBounds(470, 89, 63, 14);
		panel_AdminSuperSeccion.add(lblSegundaSe);
		
		textSegundaSe = new JTextField();
		textSegundaSe.setEnabled(false);
		textSegundaSe.setBounds(543, 86, 86, 20);
		panel_AdminSuperSeccion.add(textSegundaSe);
		textSegundaSe.setColumns(10);
		
		JLabel lblTerceraSe = new JLabel("Tercera:");
		lblTerceraSe.setBounds(469, 120, 64, 14);
		panel_AdminSuperSeccion.add(lblTerceraSe);
		
		textTerceraSe = new JTextField();
		textTerceraSe.setEnabled(false);
		textTerceraSe.setBounds(543, 117, 86, 20);
		panel_AdminSuperSeccion.add(textTerceraSe);
		textTerceraSe.setColumns(10);
		
		JButton btnSelecSe = new JButton("Seleccionar");
		btnSelecSe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnSelecSe.setBounds(575, 166, 104, 23);
		panel_AdminSuperSeccion.add(btnSelecSe);

		btnCancelarSe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPrimeraSe.setText("");
				textSegundaSe.setText("");
				textTerceraSe.setText("");
				Tipos=mc.modificarComboBox(Tipos,null);
				tipoSeccion.setEnabled(true);
				tipoSeccion.setModel(new DefaultComboBoxModel<String>(Tipos));
				tipoSeccion.updateUI();
				cuentaSecciones=0;
			}
		});
		btnCancelarSe.setBounds(476, 166, 89, 23);
		panel_AdminSuperSeccion.add(btnCancelarSe);
		
		
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
		btnCancelarJefe.setBounds(80, 265, 89, 23);
		panel_AdminSuperSeccion.add(btnCancelarJefe);
		
		
		btnCancelarSuper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textCodigoSuper.setText("");
				textEmpresa.setText("");
				textDireccion.setText("");
				NumEmple.setValue(0);
			}
		});
		btnCancelarSuper.setBounds(311, 161, 89, 23);
		panel_AdminSuperSeccion.add(btnCancelarSuper);
		
		JButton btnCancelar = new JButton("Atras");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog (null, "¿Estas seguro?.Si sales se perderán todos los datos","AVISO", 0)==0) {
				btnCancelarSuper.doClick();
				btnCancelarSe.doClick();
				btnCancelarJefe.doClick();
				tabbedPane.setSelectedIndex(3);
				lblErroresASS.setText("");
				}
			}
		});
		btnCancelar.setBounds(163, 377, 89, 23);
		panel_AdminSuperSeccion.add(btnCancelar);
		
		
		lblErroresASS.setForeground(new Color(255, 0, 0));
		lblErroresASS.setBounds(136, 323, 429, 14);
		panel_AdminSuperSeccion.add(lblErroresASS);
		
		porcentajeEmpresa.setModel(new SpinnerNumberModel(Float.valueOf(51), Float.valueOf(51), Float.valueOf(100), Float.valueOf(1)));
		porcentajeEmpresa.setBounds(177, 207, 46, 20);
		panel_AdminSuperSeccion.add(porcentajeEmpresa);
		
		JPanel panel_Articulos = new JPanel();
		tabbedPane.addTab("Septima", null, panel_Articulos, null);
		panel_Articulos.setLayout(null);
		
		JComboBox<String> suAnadirArticulo = new JComboBox<String>();
		suAnadirArticulo.setBounds(228, 37, 156, 22);
		panel_Articulos.add(suAnadirArticulo);
		
		JLabel lblNombreArticulo = new JLabel("Nombre:");
		lblNombreArticulo.setBounds(10, 83, 46, 14);
		panel_Articulos.add(lblNombreArticulo);
		
		textNombreArticulo = new JTextField();
		textNombreArticulo.setBounds(66, 80, 123, 20);
		panel_Articulos.add(textNombreArticulo);
		textNombreArticulo.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Descripcion(Opcional):");
		lblDescripcion.setBounds(10, 116, 156, 14);
		panel_Articulos.add(lblDescripcion);
		
		JTextArea textDescripcion = new JTextArea();
		textDescripcion.setBounds(10, 141, 208, 198);
		panel_Articulos.add(textDescripcion);
		
		JLabel lblImagen = new JLabel("Imagen y formato:");
		lblImagen.setBounds(254, 83, 179, 14);
		panel_Articulos.add(lblImagen);
		
		textImagen = new JTextField();
		textImagen.setBounds(228, 125, 181, 20);
		panel_Articulos.add(textImagen);
		textImagen.setColumns(10);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(228, 175, 46, 14);
		panel_Articulos.add(lblPrecio);
		
		textPrecio = new JTextField();
		textPrecio.setBounds(284, 172, 135, 20);
		panel_Articulos.add(textPrecio);
		textPrecio.setColumns(10);
		
		JComboBox<String> tipoArticulo = new JComboBox<String>();
		tipoArticulo.setBounds(288, 217, 131, 22);
		panel_Articulos.add(tipoArticulo);
		
		JLabel lblSeccion = new JLabel("Seccion:");
		lblSeccion.setBounds(228, 221, 66, 14);
		panel_Articulos.add(lblSeccion);
		
		JLabel lblTalla = new JLabel("Talla:");
		lblTalla.setBounds(429, 83, 46, 14);
		panel_Articulos.add(lblTalla);
		
		textTalla = new JTextField();
		textTalla.setBounds(515, 80, 123, 20);
		panel_Articulos.add(textTalla);
		textTalla.setColumns(10);
		
		JLabel lblMarca = new JLabel("Marca:");
		lblMarca.setBounds(429, 128, 46, 14);
		panel_Articulos.add(lblMarca);
		
		textMarca = new JTextField();
		textMarca.setBounds(515, 125, 123, 20);
		panel_Articulos.add(textMarca);
		textMarca.setColumns(10);
		
		JLabel lblFechaDeCaducidad = new JLabel("Fecha de Caducidad");
		lblFechaDeCaducidad.setBounds(497, 175, 141, 14);
		panel_Articulos.add(lblFechaDeCaducidad);
		
		JLabel lblProcedencia = new JLabel("Procedencia:");
		lblProcedencia.setBounds(429, 236, 84, 14);
		panel_Articulos.add(lblProcedencia);
		
		textProcedencia = new JTextField();
		textProcedencia.setBounds(515, 233, 123, 20);
		panel_Articulos.add(textProcedencia);
		textProcedencia.setColumns(10);
		
		JCheckBox chckElectrica = new JCheckBox("Electrica");
		chckElectrica.setBounds(497, 267, 97, 23);
		panel_Articulos.add(chckElectrica);
		
		JSpinner Garantia = new JSpinner();
		Garantia.setModel(new SpinnerNumberModel(2, 2, 5, 1));
		Garantia.setBounds(564, 297, 30, 20);
		panel_Articulos.add(Garantia);
		
		JLabel lblGarantia = new JLabel("Garantia:");
		lblGarantia.setBounds(443, 300, 46, 14);
		panel_Articulos.add(lblGarantia);
		
		JButton btnInsertar = new JButton("Confirmar");
		btnInsertar.setBounds(284, 379, 89, 23);
		panel_Articulos.add(btnInsertar);
		
	}
}