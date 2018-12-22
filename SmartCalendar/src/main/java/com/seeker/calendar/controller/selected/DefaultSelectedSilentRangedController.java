package com.seeker.calendar.controller.selected;

import com.seeker.calendar.CalendarDay;
import com.seeker.calendar.utils.CalendarUtils;

/**
 * @author Seeker
 * @date 2018/12/20/020  11:22
 * @describe 自动形成闭合区间
 */
public class DefaultSelectedSilentRangedController extends BaseSelectedController{

    private CalendarDay[] selected = new CalendarDay[2];

    private int ranger = 1;

    @Override
    public void setSelectedDay(CalendarDay selectedDay) {
        this.selected[0] = selectedDay;
        int count = CalendarUtils.getRangerDays(selectedDay,CalendarUtils.getToday());
        this.selected[1] = selectedDay.afterDays(Math.max(0,Math.min(count,ranger)-1));
    }

    @Override
    public boolean isRanged() {
        return selected[0] != null && selected[1] != null;
    }

    @Override
    public void setMaxRanger(int maxRanger) {
        this.ranger = maxRanger;
    }

    @Override
    public int getMaxRanger() {
        return ranger;
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
