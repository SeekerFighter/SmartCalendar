package com.seeker.calendar.utils;

import android.text.format.Time;

import com.seeker.calendar.CalendarDay;

import java.util.Calendar;


public final class CalendarUtils{

    private static final CalendarDay today = new CalendarDay();
    private static final Time time = new Time(Time.getCurrentTimezone());

	public static int getDaysInMonth(int month, int year) {
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                return 31;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            case Calendar.FEBRUARY:
                return ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) ? 29 : 28;
            default:
                throw new IllegalArgumentException("Invalid Month");
        }
	}

	public static CalendarDay getToday(){
        time.setToNow();
        today.setDay(time.year,time.month,time.monthDay);
	    return today;
    }

    public static int getMonthCount(int[] fromYearMonth,int[] toYearMonth){
	    if (fromYearMonth.length != 2 || toYearMonth.length != 2){
	        throw new RuntimeException("fromYearMonth and  toYearMonth length must = 2");
        }
        if (fromYearMonth[1] > 11 || fromYearMonth[1] < 0 || toYearMonth[1] > 11 || toYearMonth[1] < 0){
            throw new RuntimeException("month ranger must (0-11)");
        }
        return (toYearMonth[0]-fromYearMonth[0])*12-fromYearMonth[1] + toYearMonth[1]+1;
    }

    public static int getCurrentYear(int[] fromYearMonth,int position){
        return fromYearMonth[0] +((fromYearMonth[1] + position)/12);
    }

    public static int getCurrentMonth(int[] fromYearMonth,int position){
        return (fromYearMonth[1] + position)%12;
    }

    public static int getInFactPosition(int[] fromYearMonth,CalendarDay calendarDay){
	    return getMonthCount(fromYearMonth,new int[]{calendarDay.year,calendarDay.month})-1;
    }

    public static int getRangerDays(CalendarDay start, CalendarDay end){
	    return (int) (end.getTimeInDay() - start.getTimeInDay()+1);
    }

    public static void checkRangedValid(CalendarDay[] selected){
        if (selected == null || selected.length != 2 || selected[0] == null || selected[1] == null){
            throw new RuntimeException("when you want set a ranger,selected.len == 2,and element must not == null");
        }
    }

}
