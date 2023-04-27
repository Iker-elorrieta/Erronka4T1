package Modelo;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Comprador {
	void comprarArticulos(Compra compra,ArrayList<ArticulosComprados> lista) throws SQLException;

}
