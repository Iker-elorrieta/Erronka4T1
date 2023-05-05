package modelo;

public abstract class Articulo {
	protected int idArticulo;
	protected String nombreArticulo;
	protected String rutaImagen;
	protected String descripcion;
	protected float precio;
	protected int stockActual;
	protected tipoArticulo tipo;
	
	public Articulo(int idArticulo, String nombreArticulo, String rutaImagen,String descripcion, float precio,int stockActual, tipoArticulo tipo) {
		super();
		this.idArticulo = idArticulo;
		this.nombreArticulo = nombreArticulo;
		this.rutaImagen = rutaImagen;
		this.descripcion=descripcion;
		this.precio = precio;
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
	public float getPrecio() {
		return precio;
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
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public void setStockActual(int stockActual) {
		this.stockActual = stockActual;
	}
	public void settipo(tipoArticulo tipo) {
		this.tipo = tipo;
	}
}
