package com.seeker.calendar.controller.selected;

import android.support.annotation.NonNull;

import com.seeker.calendar.CalendarDay;

/**
 * @author Seeker
 * @date 2018/12/19/019  10:31
 */
public class DefaultSelectedRangedController extends BaseSelectedController{

    private CalendarDay[] selected = new CalendarDay[2];

    @Override
    public void setSelectedDay(@NonNull CalendarDay slectedDay) {

        if ((selected[0] != null && selected[0].isSameDay(slectedDay))){
            selected[0] = null;
            return;
        }

        if ((selected[1] != null && selected[1].isSameDay(slectedDay))){
            selected[1] = null;
            return;
        }

        if (selected[0] == null){
            selected[0] = slectedDay;
        }else if (selected[1] == null){
            selected[1] = slectedDay;
        }else if (slectedDay.isBefore(selected[0])){
            selected[0] = slectedDay;
        }else if (slectedDay.isAfter(selected[1])){
            selected[1] = slectedDay;
        }else if (selected[0].timeDelat(slectedDay) >= selected[1].timeDelat(slectedDay)){
            selected[1] = slectedDay;
        }else {
            selected[0] = slectedDay;
        }

        if (selected[0] != null && selected[1] != null && selected[0].isAfter(selected[1])){
            CalendarDay temp = selected[0];
            selected[0] = selected[1];
            selected[1] = temp;
        }

    }

    @Override
    public boolean isRanged() {
        return selected[0] != null && selected[1] != null;
    }

    @Override
    public void setMaxRanger(int ranger) {

    }

    @Override
    public int getMaxRanger() {
        return 0;
    }

    @Override
    public CalendarDay[] getSelected() {
        return selected;
    }

    @Override
    public boolean contains(CalendarDay calendarDay) {
        for (CalendarDay c:selected){
            if (c != null && c.isSameDay(calendarDay)){
                return true;
            }
        }
        return false;
    }
}
