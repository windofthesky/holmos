package holmos.webtest.customunitls;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUnitls {
	/**
	 * 将给定的Date对象转换为指定格式Date信息
	 * @param date 指定的Date日期对象
	 * @param pattern 指定的格式
	 * @return 转换后的String信息
	 */
	public static String dateToStr(Date date,String pattern) {
		if(pattern == null){
			pattern = "yyyy-MM-dd HH:mm:ss.SSS";
		}
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}
	/**
	 * 将给定格式的Date信息转换为Date对象
	 * @param strDate 给定的Date信息
	 * @param pattern 给定的Date信息格式
	 * @return 转换好的Date对象
	 * @throws ParseException
	 */
	public static Date strToDate(String strDate,String pattern) throws ParseException {
		if(pattern == null){
			pattern = "yyyy-MM-dd HH:mm:ss.SSS";
		}
		DateFormat dataFormat = new SimpleDateFormat(pattern);
		return dataFormat.parse(strDate);
	}
	
	/**
	 * 
	 * @return,如 2009-2-20
	 */
	public static Date getToday(){
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}
	/**
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return,如 2009-2-20
	 */
	public static Date makeDate(int year,int month,int date){
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month-1, date);
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		dataFormat.format(calendar.getTime()); 
		return calendar.getTime();
	}
	/**
	 * 
	 * @param date1,如2008-12-19
	 * @param date2,如2008-12-20
	 * @return true 代表两个日期相同，否则不同
	 */
	public static  boolean compareDate(Date date1,Date date2) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm");
		return dateFormat.format(date1).equals(dateFormat.format(date2));
	}
	/**
	 * 获得该指定天在这一年的第几个星期里
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static int getWeekOrder(int year,int month,int day){
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH,month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return  calendar.get(Calendar.WEEK_OF_YEAR); 
	}
	/**
	 * 获得两个指定日期间的间隔天数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public int getDaysBetween(Calendar startCalendar, Calendar endCalendar){
        if (startCalendar.after(endCalendar)){
            java.util.Calendar swap = startCalendar;
            startCalendar = endCalendar;
            endCalendar = swap;
        }
        int days = endCalendar.get(Calendar.DAY_OF_YEAR) - startCalendar.get(Calendar.DAY_OF_YEAR);
        int year = endCalendar.get(Calendar.YEAR);
        if (startCalendar.get(Calendar.YEAR) != year){
        	startCalendar = (Calendar) startCalendar.clone();
            do{
                days += startCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);//得到当年的实际天数
                startCalendar.add(Calendar.YEAR, 1);
            } while (startCalendar.get(Calendar.YEAR) != year);
        }
        return days;
    } 
}
