package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

import Modelo.CarritoDeCompra;

class testCarritoDeCompra {
	DateFormat formateador= new SimpleDateFormat("dd/M/yy");
	Object o=new Object();
	String frase="CarritoDeCompra [carrito=[], cantidades=[0], precioTotal=189.9, fechaCompra=Sun Jul 16 00:00:00 CEST 2000, historial=[]]";
	@Test
	void test() {
		try {
			Date fecha = formateador.parse("16/07/2000");
			int [] cantidades=new int[1];
			ArrayList<Object> listaArticulos=new ArrayList<Object>();
			ArrayList<CarritoDeCompra> historial=new ArrayList<CarritoDeCompra>();
		CarritoDeCompra primero=new CarritoDeCompra(listaArticulos,cantidades,189.9,fecha,historial);
		CarritoDeCompra segundo=new CarritoDeCompra(null,null,0.0,null,null);
		segundo.setCantidades(cantidades);
		segundo.setCarrito(listaArticulos);
		segundo.setFechaCompra(fecha);
		segundo.setHistorial(historial);
		segundo.setPrecioTotal(189.9);
		assertEquals(primero.getCantidades(),segundo.getCantidades());
		assertEquals(primero.getCarrito(),segundo.getCarrito());
		assertEquals(primero.getFechaCompra(),segundo.getFechaCompra());
		assertEquals(primero.getHistorial(),segundo.getHistorial());
		assertEquals(primero.getPrecioTotal(),segundo.getPrecioTotal());
		assertEquals(primero.hashCode(),segundo.hashCode());
		assertEquals(primero.toString(),frase);
		assertTrue(primero.equals(segundo));
		assertTrue(primero.equals(primero));
		assertFalse(primero.equals(null));
		assertFalse(primero.equals(o));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
