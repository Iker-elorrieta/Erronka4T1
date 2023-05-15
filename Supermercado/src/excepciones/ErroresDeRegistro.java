package excepciones;

@SuppressWarnings("javadoc")
public class ErroresDeRegistro extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ErroresDeRegistro(String mensaje) {
		super(mensaje);
	}
}
