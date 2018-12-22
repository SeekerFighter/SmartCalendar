package com.seeker.calendar.controller.drawer;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.seeker.calendar.controller.EventController;
import com.seeker.calendar.CalendarDay;

/**
 * @author Seeker
 * @date 2018/12/18/018  13:22
 * @describe 月绘制方式提供
 */
public interface DrawerController<Event> {

    int LOCATION_NONE = -1;

    int LOCATION_SELECTED_START_SIDE = 0;

    int LOCATION_LEFT_SIDE = 1;

    int LOCATION_INNER = 2;

    int LOCATION_RIGHT_SIDE = 3;

    int LOCATION_SELECTED_END_SIDE = 4;

    int LOCATION_BOTH_SIDE = 5;

    int rowHeight();

    int headerLabelHeight();

    void setInit(@NonNull Resources resources);

    /**
     * 绘制月份标题
     */
    void drawMonthHeader(Canvas canvas,CalendarDay currentYearMonth,float widgetWidth,
                         float firstDayOffsetLeft,float firstDayOffsetRight);

    /**
     * 绘制天
     * @param currentYearMonth 当前页面绘制的年，月
     * @param drawedCalendar 当前页面将要绘制的实际的年月日，有可能是上一个月或者下一个月的数据
     */
    void drawDayCell(Canvas canvas,CalendarDay currentYearMonth,CalendarDay drawedCalendar, RectF target,boolean isSelected,int location,Event event);

    /**
     * 今天后面的日期是否可选
     * @return
     */
    boolean enableAfterDays();

    /**
     * 今天前面的日期是否可选
     * @return
     */
    boolean enablePreviousDay();

    EventController<Event> getCustomEventController();
}
