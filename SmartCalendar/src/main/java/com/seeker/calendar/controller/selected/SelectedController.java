package com.seeker.calendar.controller.selected;

import com.seeker.calendar.CalendarDay;

/**
 * @author Seeker
 * @date 2018/12/19/019  10:29
 * @describe 被点击选中结果处理
 */
public interface SelectedController {

    void setSelectedDay(CalendarDay selectedDay);

    boolean isRanged();

    void setMaxRanger(int maxRanger);

    int getMaxRanger();

    CalendarDay[] getSelected();

    boolean contains(CalendarDay calendarDay);

    void setOnSelectedCallback(OnSelectedCallback callback);

    OnSelectedCallback getOnSelectedCallback();

    interface OnSelectedCallback{

        void onDayOfMonthSelected(CalendarDay calendarDay);

        void onDayRangedSelected(CalendarDay start,CalendarDay end);

    }

}
