package com.seeker.calendar.controller.selected;

import android.support.annotation.NonNull;

import com.seeker.calendar.CalendarDay;

/**
 * @author Seeker
 * @date 2018/12/19/019  10:31
 */
public class DefaultSingleSelectedController extends BaseSelectedController{

    private CalendarDay[] selected = new CalendarDay[1];

    @Override
    public void setSelectedDay(@NonNull CalendarDay selectedDay) {
        if ((selected[0] != null && selected[0].isSameDay(selectedDay))){
            return;
        }
        this.selected[0] = selectedDay;

    }

    @Override
    public boolean isRanged() {
        return false;
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
        return selected[0] != null && selected[0].isSameDay(calendarDay);
    }
}
