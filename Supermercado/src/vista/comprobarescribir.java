package vista;

import java.io.IOException;
import java.util.ArrayList;

import Controlador.Metodos;
import Modelo.*;

public class comprobarescribir {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Metodos mc=new Metodos();
		Ropa ro=new Ropa(1,"Leggin","ruta","",16.89,250,75,tipoArticulo.Ropa,"XL","Rojo","Algodon","Nike");
		ArrayList<Articulo> lista=new ArrayList<Articulo>();
		lista.add(ro);
		System.out.println(ro.toString());
		try {
			mc.guardarInventario(lista);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}

}
