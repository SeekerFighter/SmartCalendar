package com.seeker.calendar;

import android.support.annotation.NonNull;

import com.seeker.calendar.utils.Constants;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Seeker
 * @date 2018/12/18/018  17:31
 */
public class CalendarDay implements Serializable {

    private static final long serialVersionUID = -5456695978688356202L;

    private Calendar calendar;

    public int day;
    public int month;
    public int year;

    public CalendarDay(){
        calendar = Calendar.getInstance();
    }

    public CalendarDay(int year, int month, int day) {
        calendar = Calendar.getInstance();
        setDay(year, month, day);
    }

    public void setDay(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Date getDate() {
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    public long getTimeInDay(){
        calendar.set(year, month, day);
        return calendar.getTimeInMillis()/Constants.DAY;
    }

    public boolean isSameDay(@NonNull CalendarDay calendarDay){
        return this.year == calendarDay.year && this.month == calendarDay.month && this.day == calendarDay.day;
    }

    public boolean isAfter(@NonNull CalendarDay calendarDay){
        return getDate().after(calendarDay.getDate());
    }

    public boolean isBefore(@NonNull CalendarDay calendarDay){
        return getDate().before(calendarDay.getDate());
    }

    public long timeDelat(@NonNull CalendarDay calendarDay){
        return Math.abs(getTimeInDay() - calendarDay.getTimeInDay());
    }

    public CalendarDay afterDays(int days){
        Calendar temp = Calendar.getInstance();
        temp.setTimeInMillis((getTimeInDay()+days)*Constants.DAY);
        return new CalendarDay(temp.get(Calendar.YEAR),temp.get(Calendar.MONTH),temp.get(Calendar.DAY_OF_MONTH));
    }

    public CalendarDay beforeDays(int days){
        Calendar temp = Calendar.getInstance();
        temp.setTimeInMillis((getTimeInDay()-days-1)*Constants.DAY);
        return new CalendarDay(temp.get(Calendar.YEAR),temp.get(Calendar.MONTH),temp.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public String toString() {
        return year+"-"+(month+1)+"-"+day;
    }
}
