package controlador;

public class ErroresDeOperaciones extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ErroresDeOperaciones(String mensaje) {
		super(mensaje);
	}
}
