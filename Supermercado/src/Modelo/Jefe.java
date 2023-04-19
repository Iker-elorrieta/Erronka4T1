package Modelo;

import java.util.Objects;

public class Jefe {
	private String titulo;

	public Jefe(String titulo) {
		super();
		this.titulo = titulo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jefe other = (Jefe) obj;
		return Objects.equals(titulo, other.titulo);
	}

	@Override
	public String toString() {
		return "Jefe [titulo=" + titulo + "]";
	}

	
	
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
	
	
	
	
}
