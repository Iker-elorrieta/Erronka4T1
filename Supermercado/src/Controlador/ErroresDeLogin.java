package Controlador;

public class ErroresDeLogin extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ErroresDeLogin(String mensaje) {
		super(mensaje);
	}
}
