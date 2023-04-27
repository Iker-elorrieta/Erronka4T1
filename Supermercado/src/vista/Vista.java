package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import Controlador.ErroresDeLogin;
import Controlador.ErroresDeRegistro;
import Controlador.Metodos;
import Modelo.Cliente;
import Modelo.DateLabelFormatter;
import Modelo.Persona;

import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Properties;
import java.awt.event.ActionEvent;

public class Vista {

	private JFrame frame;
	private JTextField cemail;
	private JPasswordField campoContrasena;
	private JTextField textDNI;
	private JTextField textNombre;
	private JTextField textApellidos;
	private JPasswordField contrasenaRegi;
	private Metodos mc=new Metodos();
	private JTextField textMail;
	private JDatePickerImpl datePicker;
	private ArrayList<Persona> usuarios;
	private Persona login;
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
		
		usuarios=mc.cargarPersonas();
		
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
		
		JButton btnRegis = new JButton("Resgistrarse");
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
		
		JButton btnIniciar = new JButton("Iniciar sesion");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					login=mc.iniciarSesion(usuarios,cemail.getText(),String.valueOf(campoContrasena.getPassword()));
					login.toString();
					tabbedPane.setSelectedIndex(3);
				} catch (ErroresDeLogin e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage());
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

		
		JButton btnRegistrarse1 = new JButton("Registrarse");
		btnRegistrarse1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mc.comprobarCampos(textNombre.getText(),textApellidos.getText(),
					String.valueOf(contrasenaRegi.getPassword()), textDNI.getText()
					, datePicker.getJFormattedTextField().getText(), textMail.getText());
					
					mc.comprobarNacimiento(datePicker.getJFormattedTextField().getText());
					mc.comprobarEmail(textMail.getText());
					mc.comprobarDNI(textDNI.getText());
					Cliente cli;
					try {
						cli = new Cliente(textDNI.getText(),textNombre.getText(),textApellidos.getText(),
						 mc.deStringADate(datePicker.getJFormattedTextField().getText()), textMail.getText(),
						 String.valueOf(contrasenaRegi.getPassword()),0,false,false);
						mc.registrarse(cli);
					} catch (ParseException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
			
					
					usuarios=mc.cargarPersonas();
					
					contrasenaRegi.setText("");
					textApellidos.setText("");
					datePicker.getJFormattedTextField().setText("");
					textDNI.setText("");
					textNombre.setText("");
					textMail.setText("");
				} catch (ErroresDeRegistro e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage());	
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
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Cuarta", null, panel_1, null);
		panel_1.setLayout(null);
	}
}
