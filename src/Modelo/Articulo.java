package Modelo;

public abstract class Articulo {
	protected int idArticulo;
	protected String nombreArticulo;
	protected String rutaImagen;
	protected String descripcion;
	protected Double precio;
	protected int stockMaximo;
	protected int stockActual;
	protected tipoArticulo tipo;
	


	public Articulo(int idArticulo, String nombreArticulo, String rutaImagen,String descripcion, Double precio, int stockMaximo,
			int stockActual, tipoArticulo tipo) {
		super();
		this.idArticulo = idArticulo;
		this.nombreArticulo = nombreArticulo;
		this.rutaImagen = rutaImagen;
		this.descripcion=descripcion;
		this.precio = precio;
		this.stockMaximo = stockMaximo;
		this.stockActual = stockActual;
		this.tipo = tipo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getIdArticulo() {
		return idArticulo;
	}
	public String getNombreArticulo() {
		return nombreArticulo;
	}
	public String getRutaImagen() {
		return rutaImagen;
	}
	public Double getPrecio() {
		return precio;
	}
	public int getStockMaximo() {
		return stockMaximo;
	}
	public int getStockActual() {
		return stockActual;
	}
	public tipoArticulo gettipo() {
		return tipo;
	}
	public void setIdArticulo(int idArticulo) {
		this.idArticulo = idArticulo;
	}
	public void setNombreArticulo(String nombreArticulo) {
		this.nombreArticulo = nombreArticulo;
	}
	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public void setStockMaximo(int stockMaximo) {
		this.stockMaximo = stockMaximo;
	}
	public void setStockActual(int stockActual) {
		this.stockActual = stockActual;
	}
	public void settipo(tipoArticulo tipo) {
		this.tipo = tipo;
	}
	
	
	
	
}
