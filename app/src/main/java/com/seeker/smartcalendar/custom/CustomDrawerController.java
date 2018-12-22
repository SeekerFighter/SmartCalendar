package com.seeker.smartcalendar.custom;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.seeker.calendar.CalendarDay;
import com.seeker.smartcalendar.Person;
import com.seeker.calendar.controller.drawer.DefaultDrawerController;
import com.seeker.calendar.utils.CalendarUtils;
import com.seeker.calendar.utils.Constants;

/**
 * @author Seeker
 * @date 2018/12/22/022  10:48
 * @describe 在layout对应的xml中配置此类的绝对路径即可
 */
public class CustomDrawerController extends DefaultDrawerController<Person> {

    @Override
    public void drawMonthHeader(Canvas canvas, CalendarDay currentYearMonth,
                                float widgetWidth, float firstDayOffsetLeft, float firstDayOffsetRight) {
        CalendarDay today = CalendarUtils.getToday();
        String text = (currentYearMonth.month+1)+"月";
        Paint.FontMetrics fontMetrics = mMonthLabelPaint.getFontMetrics();
        RectF target = new RectF(0,0,widgetWidth,headerLabelHeight());
        int baseLine = (int)((target.bottom + target.top - fontMetrics.bottom - fontMetrics.top) / 2.0f);
        if (currentYearMonth.year == today.year && currentYearMonth.month == today.month){
            mMonthLabelPaint.setColor(Constants.currentColor);
        }else {
            mMonthLabelPaint.setColor(Color.BLACK);
        }
        canvas.save();
        canvas.drawText(text,target.centerX(),baseLine,mMonthLabelPaint);
        canvas.restore();
    }

    @Override
    public boolean enableAfterDays() {
        return true;
    }
}
