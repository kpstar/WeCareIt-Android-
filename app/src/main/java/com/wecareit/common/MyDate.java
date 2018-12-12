package com.wecareit.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDate extends Date {

    private int year;
    private int month;
    private int date;

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int getMonth() {
        return month;
    }

    @Override
    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public int getDate() {
        return date;
    }

    @Override
    public void setDate(int date) {
        this.date = date;
    }

    public MyDate() {
        super();

        Date date = new Date();
        setYear(date.getYear() + 1900);
        setMonth(date.getMonth());
        setDate(date.getDate());
    }

    public MyDate(int year, int month, int date) {
        super(year, month, date);

        year += 1900;
    }

    public static MyDate getInstance() {
        return new MyDate();
    }

    public class MyDateFormat {
        public static final int ISO8601 = 0; // yyyy-MM-dd
        public static final int GENERAL = 1; // monday, 5 novembor
    };


    public String toString(int format) {
        String result = "";
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        switch (format) {
            case MyDateFormat.ISO8601:
                result = formater.format(this);
                break;
            case MyDateFormat.GENERAL:
                result = Global.weekDays[this.getDay()] + ", " + String.valueOf(this.getDate()) + " " + Global.months[this.getMonth()];
                break;
            default:
                result = formater.format(new Date());
                break;
        }
        return result;
    }

    public void prevDate() {
        this.setTime(this.getTime() - 86400000);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(this.getTime());
        this.setYear(cal.get(Calendar.YEAR));
        this.setMonth(cal.get(Calendar.MONTH));
        this.setDate(cal.get(Calendar.DATE));
    }

    public void nextDate() {
        this.setTime(this.getTime() + 86400000);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(this.getTime());
        this.setYear(cal.get(Calendar.YEAR));
        this.setMonth(cal.get(Calendar.MONTH));
        this.setDate(cal.get(Calendar.DATE));
    }

    public boolean isToday() {
        Date today = new Date();
        if (today.getYear() + 1900 == this.getYear() && today.getMonth() == this.getMonth() && today.getDate() == this.getDate()) {
            return true;
        } else {
            return false;
        }

    }
}
