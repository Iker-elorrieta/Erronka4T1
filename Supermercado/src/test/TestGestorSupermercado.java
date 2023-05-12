package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

import controlador.Metodos;
import gestores.GestorSupermercado;
import modelo.Jefe;
import modelo.Supermercado;
import otros.tipoPersona;
import referencias.CONEXION;
import referencias.TABLAS;

class TestGestorSupermercado {
	Metodos mc=new Metodos();
	GestorSupermercado g=new GestorSupermercado();
	Jefe jefe=new Jefe("77777777C","Test","Test",Date.valueOf("2001-01-21"),"testJefe@gmail.com","12345",tipoPersona.Jefe,Date.valueOf("2019-09-09"),(float)55.5,0);
	Supermercado su=new Supermercado("ABCDE","PruebaEmpresa","Errekamari",4,null);
	Connection conexion;
	@Test
	void testInsertarSupermercado() {
		Supermercado nuevo=null;
		try {
		conexion=(Connection) DriverManager.getConnection(CONEXION.URL, CONEXION.USER, CONEXION.PASS);
		g.insertarSupermercado(conexion, jefe, su);
		Statement comando= (Statement) conexion.createStatement();
		comando.executeUpdate("INSERT INTO `"+TABLAS.PERSONAS+"` (`dni`, `nombre`, `apellidos`, `fechaNacimiento`, `email`, `contrasena`, `tipo`, `fechaAdquisicion`, `porcentajeEmpresa`, `dios`) VALUES\r\n"
				+ "('00000000A', 'Administrador', 'Root', '2000-01-01', 'admin@gmail.com', 'Elorrieta00', 'Jefe', '2019-05-16', 70.5, 1);");
		ResultSet carga=comando.executeQuery("SELECT * FROM "+TABLAS.SUPERMERCADO+" WHERE "+TABLAS.DNIJEFE+"='"+jefe.getDni()+"'");
		while(carga.next()) {
			nuevo=new Supermercado(carga.getString(TABLAS.CODIGOSUPER),carga.getString(TABLAS.EMPRESA),carga.getString(TABLAS.DIRECCION), carga.getInt(TABLAS.NUMEROEMPLEADOS), null);
		}
		assertEquals(nuevo.getCodigoSuper(),su.getCodigoSuper());
		assertEquals(nuevo.getDireccion(),su.getDireccion());
		assertEquals(nuevo.getEmpresa(),su.getEmpresa());
		assertEquals(nuevo.getNumEmpleados(),su.getNumEmpleados());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
