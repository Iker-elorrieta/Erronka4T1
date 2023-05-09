package otros;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFormattedTextField.AbstractFormatter;

public class DateLabelFormatter extends AbstractFormatter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String datePattern = "yyyy-MM-dd";
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
	
	@Override
	public Object stringToValue(String text) throws ParseException {
		return dateFormatter.parseObject(text);
	}

	@Override
	public String valueToString(Object value) throws ParseException {
		if (value != null) {
			if(value instanceof Calendar) {
			Calendar cal = (Calendar) value;
			return dateFormatter.format(cal.getTime());
			}else {
			Date fecha=(Date) value;
			return dateFormatter.format(fecha.getTime());
			}
		}
		
		return "";
	}

}