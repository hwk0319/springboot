package com.it.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
	
	/**
	 * 获取24小时时间
	 * @return
	 */
	public static String get24Hour(){
		Date today = new Date();
		SimpleDateFormat f = new SimpleDateFormat("HH");
		String hour = f.format(today);
		return hour;
	}
	/**
	 * 获取时分秒
	 * @return
	 */
	public  static String getNowHHMMSS() {
		Date today = new Date();
		SimpleDateFormat f = new SimpleDateFormat("mm");
		return f.format(today);
	}
	/**
	 * 获取当前月
	 */
	public static String getCurMon(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		String mon = sdf.format(date);
		return mon;
	}
	/**
	 * 获取当前天
	 */
	public static String getCurDay(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		String day = sdf.format(date);
		return day;
	}
	/**
	 * 获取年月
	 */
	public static String getYYMM(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String ym = sdf.format(date);
		return ym;
	}
	 /**
     * 获取年月日
     * @return
     */
    public static String getYYMMDD(){
    	Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sdf.format(date);
		return str;
    }
    /**
     * 获取年月日时
     * @return
     */
    public static String getYYMMDDHH(){
    	Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		String str = sdf.format(date);
		return str;
    }
    /**
     * 获取当前时间
     * @return
     */
    public static String getDate(){
    	Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		return sdf.format(date);
    }
    /**
     * 获取上月当前日期
     */
    public static String getLastDate(){
    	Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }
}
