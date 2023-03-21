package business.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	
	private DateUtils() {
	}
	
	/**
	 * Mock current date for tests
	 * @return date for 01/05/2021
	 */
	public static Date getCurrentTime() {
		return new GregorianCalendar(2021, Calendar.MAY, 1).getTime();
	}
	
	
}
