package com.sxcapp.www.Util;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wenleitao on 2017/7/11.
 */

public class TimeFormat {
    private Date date;

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    public static String getDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String md = format.format(date);
        return md.substring(5, 10);
    }
    public static long getTimeStamp(String ss) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(ss, pos);
        return strtodate.getTime();
    }


    public static String getDate(String ss) {
        long s = Long.parseLong(ss);
        Date date = new Date(s);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String md = format.format(date);
        return md.substring(5, 10);
    }

    public static String gethour(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String md = format.format(date);
        return md.substring(11, 16);
    }

    public static String gethour(String ss) {
        long s = Long.parseLong(ss);
        Date date = new Date(s);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String md = format.format(date);
        return md.substring(11, 16);
    }

    public static String getDay(Date date) {
        String Week = "星期";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String pTime = mdformat.format(date);
            pTime = pTime.substring(0, 10);
            c.setTime(format.parse(pTime));

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            Week += "天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            Week += "六";
        }

        return Week;
    }

    public static String getNowTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static Date getNowDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date dt1 = null;
        try {
            dt1 = df.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt1;
    }
    /**
     * @return 获取网络当前时间（网站参考百度）
     */
    public static  long getNetNowDate() {
        URL url;//取得资源对象
        try {
            url = new URL("http://www.baidu.com");
            URLConnection uc = url.openConnection();//生成连接对象
            uc.connect(); //发出连接
            return uc.getDate(); //取得网站日期时间
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param DATE1
     * @param DATE2
     * @return 返回1 d1比d2晚 返回-1 d1比d2早
     */
    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2后");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2前");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取一天后的时间
     */
    public static Date getToDate() {
        long t = System.currentTimeMillis() + 24 * 60 * 60 * 1000;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(t);//获取当前时间
        String str = formatter.format(curDate);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date dt1 = null;
        try {
            dt1 = df.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt1;
    }


    public static long getLeaseDay(Date lease, Date giveBack) {
        long diff = giveBack.getTime() - lease.getTime();
        if (diff > (24 * 60 * 60 * 1000)) {
            long day = (int) ((diff) / (24 * 60 * 60 * 1000));
            long ll = diff - 24 * 60 * 60 * 1000 * day;
            if (ll > 4 * 60 * 60 * 1000) {
                day = day + 1;
            }
            return day;
        } else {
            return 1;
        }
    }

    public static boolean isOneDayLater(Date lease, Date giveBack) {
        long diff = giveBack.getTime() - lease.getTime();
        if (diff > (24 * 60 * 60 * 1000) || diff == (24 * 60 * 60 * 1000)) {
            return true;
        }
        return false;
    }

    //    判断是否在租车时间内
    public static boolean isInStoreTime(String hour, String start_time, String end_time) {
        int h = Integer.parseInt(hour.substring(0, 2));
        int s = Integer.parseInt(start_time.substring(0, 2));
        int e = Integer.parseInt(end_time.substring(0, 2));
        if (h < s) {
            return false;
        } else if (h > e) {
            return false;
        } else if (h == e) {

            int h_min = Integer.parseInt(hour.substring(3, 5));
            int e_min = Integer.parseInt(end_time.substring(3, 5));
            if (h_min > e_min) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    public static String getStringFormMill(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(time);
    }

    public static Date getDateFromString(String s) {
        long lt = Long.parseLong(s);
        Date date = new Date(lt);
        return date;
    }

    public static boolean isToday(String showtime)  {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = new Date(Long.parseLong(showtime));
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

}
