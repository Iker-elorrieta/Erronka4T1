package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

import controlador.Metodos;
import excepciones.ErroresDeRegistro;
import gestores.GestorArticulo;
import gestores.GestorPersona;
import gestores.GestorSeccion;
import gestores.GestorSupermercado;
import modelo.Comida;
import modelo.Herramienta;
import modelo.Jefe;
import modelo.Ropa;
import modelo.Seccion;
import modelo.Supermercado;
import otros.tipoArticulo;
import otros.tipoPersona;
import referencias.TABLAS;

class TestGestorArticulo {
	Metodos mc=new Metodos();
	@Test
	void test() {
		GestorArticulo ga=new GestorArticulo();
		GestorSeccion g=new GestorSeccion();
		GestorSupermercado g2=new GestorSupermercado();
		GestorPersona g1=new GestorPersona();
		Jefe jefe=new Jefe("77777777C","Test","Test",Date.valueOf("2001-01-21"),"testJefe@gmail.com","12345",tipoPersona.Jefe,Date.valueOf("2019-09-09"),(float)55.5,0);
		
		Supermercado su=new Supermercado("ABCDE","PruebaEmpresa","Errekamari",4,null);
		Seccion se=new Seccion("OOOO",tipoArticulo.Herramienta,0,null);
		Ropa ro=new Ropa(0, "Chanclas", "chanclas.png", "", (float)5.88, 99, "XL", "Supreme");
		Comida co=new Comida(0, "Fruta del dragon", "fdd.png", "", (float)3.49, 99, Date.valueOf("2023-12-31"),"Brasil");
		Herramienta he=new Herramienta(0, "Desatornillador", "desatornillador.png", "", (float)8.99, 99, 1, 4);
		try {
			ga.setListaArticulos(ga.cargarArticulos());
			assertTrue(ga.getListaArticulos().size()>0);
			int antesDeInsertar=ga.getListaArticulos().size();
			assertEquals(ga.getListaArticulos().get(0).getIdArticulo(),4);
			assertEquals(ga.getListaArticulos().get(0).getNombreArticulo(),"Martillo");
			assertEquals(ga.getListaArticulos().get(2).getIdArticulo(),1);
			assertEquals(ga.getListaArticulos().get(2).getNombreArticulo(),"Patatas bravas");
			assertEquals(ga.getListaArticulos().get(5).getIdArticulo(),6);
			assertEquals(ga.getListaArticulos().get(5).getNombreArticulo(),"Pantalones");
			
			g1.insertarPersona(jefe);
			g2.insertarSupermercado(jefe, su);
			g.insertarSeccion(su, se);
			ga.insertarArticulo(se, ro);
			ga.insertarArticulo(se, co);
			ga.insertarArticulo(se, he);
			ga.setListaArticulos(ga.cargarArticulos());
			assertEquals(ga.getListaArticulos().size(),(antesDeInsertar+3));
			
			
			Statement comando = (Statement) mc.conectarBaseDatos().createStatement();
			ResultSet cargaHe=comando.executeQuery("SELECT "+TABLAS.IDARTICULO+" FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.ELECTRICA+" OR "+TABLAS.GARANTIA+" IS NOT NULL ORDER BY "+TABLAS.IDARTICULO+" DESC LIMIT 1");
			while(cargaHe.next()) {
				he=new Herramienta(cargaHe.getInt(TABLAS.IDARTICULO), "Desatornillador", "desatornillador.png", "", (float)8.99, 99, 0, 2);
			}
			ResultSet cargaRo=comando.executeQuery("SELECT "+TABLAS.IDARTICULO+" FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.TALLA+" OR "+TABLAS.MARCA+" IS NOT NULL ORDER BY "+TABLAS.IDARTICULO+" DESC LIMIT 1");
			while(cargaRo.next()) {
			ro=new Ropa(cargaRo.getInt(TABLAS.IDARTICULO), "Chanclas", "chanclas.png", "", (float)5.88, 99, "S", "Mercadona");
			}
			ResultSet cargaCo=comando.executeQuery("SELECT "+TABLAS.IDARTICULO+" FROM "+TABLAS.ARTICULO+" WHERE "+TABLAS.FECHACADUCIDAD+" OR "+TABLAS.PROCEDENCIA+" IS NOT NULL ORDER BY "+TABLAS.IDARTICULO+" DESC LIMIT 1");
			while(cargaCo.next()) {
				co=new Comida(cargaCo.getInt(TABLAS.IDARTICULO), "Fruta del dragon", "fdd.png", "", (float)3.49, 99, Date.valueOf("2023-12-31"),"Cuenca");
			}
			ga.cambiarArticulo(he);
			ga.cambiarArticulo(co);
			ga.cambiarArticulo(ro);
			ga.setListaArticulos(ga.cargarArticulos());
			Ropa r=null;
			Herramienta h=null;
			Comida c=null;
			h=(Herramienta) ga.buscarArticulo(he);
			c=(Comida) ga.buscarArticulo(co);
			r=(Ropa) ga.buscarArticulo(ro);
			assertEquals(h.getNombreArticulo(),he.getNombreArticulo());
			assertEquals(h.getGarantia(),he.getGarantia());
			assertEquals(h.getElectrica(),he.getElectrica());
			assertEquals(c.getNombreArticulo(),co.getNombreArticulo());
			assertEquals(c.getProcedencia(),co.getProcedencia());
			assertEquals(r.getNombreArticulo(),r.getNombreArticulo());
			assertEquals(r.getTalla(),r.getTalla());
			assertEquals(r.getMarca(),r.getMarca());
			
			ga.borrarArticulo(r);
			ga.borrarArticulo(h);
			ga.borrarArticulo(c);
			ga.setListaArticulos(ga.cargarArticulos());
			assertEquals(ga.getListaArticulos().size(),antesDeInsertar);
			g1.darseBajaPersona(jefe);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErroresDeRegistro e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
