package Modelo;

import java.util.Objects;

public class Ropa extends Articulo{
	private String talla;
	private String color;
	private String material;
	private String marca;
	
	
	public Ropa(int idArticulo, String nombreArticulo, Double precio, int stockMaximo, int stockActual,
			tipoArticulo type, String talla, String color, String material, String marca) {
		super(idArticulo, nombreArticulo, precio, stockMaximo, stockActual, type);
		this.talla = talla;
		this.color = color;
		this.material = material;
		this.marca = marca;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(idArticulo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Articulo other = (Articulo) obj;
		return idArticulo == other.idArticulo;
	}


	@Override
	public String toString() {
		return "Ropa [talla=" + talla + ", color=" + color + ", material=" + material + ", marca=" + marca + "]";
	}


	public String getTalla() {
		return talla;
	}


	public String getColor() {
		return color;
	}


	public String getMaterial() {
		return material;
	}


	public String getMarca() {
		return marca;
	}

	
	
	
	
	
	
	public void setTalla(String talla) {
		this.talla = talla;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
}
