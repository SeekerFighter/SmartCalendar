package com.seeker.calendar.controller.drawer;

import android.graphics.Canvas;

import com.seeker.calendar.CalendarDay;

/**
 * @author Seeker
 * @date 2018/12/21/021  10:26
 * @describe 横向滚动日历默认绘制方式
 */
public class DefaultHDrawerController<Event> extends DefaultDrawerController<Event>{

    @Override
    public int headerLabelHeight() {
        return 0;
    }

    @Override
    public void drawMonthHeader(Canvas canvas, CalendarDay currentYearMonth,
                                float widgetWidth, float firstDayOffsetLeft, float firstDayOffsetRight) {

    }

    @Override
    public boolean drawOtherMonthDay() {
        return true;
    }

    @Override
    public boolean enableAfterDays() {
        return true;
    }
}
