package modelo;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public interface Comprador {
	void comprarArticulos(Compra compra,ArrayList<ArticuloComprado> lista) throws SQLException, ParseException;

}
