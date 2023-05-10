package test;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import otros.DateLabelFormatter;

class TestDateLabelFormatter {

	@Test
	void test() {
		DateLabelFormatter dlf=new DateLabelFormatter();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String control="2020-06-17";
		Date fecha=null;
		try {
			fecha=(Date) dlf.stringToValue(control);
			assertEquals(dateFormatter.parse(control),fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
