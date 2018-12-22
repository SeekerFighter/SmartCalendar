package com.seeker.calendar.controller;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.NonNull;

/**
 * @author Seeker
 * @date 2018/12/19/019  17:23
 */
public interface WeekDayController {

    String[] weeks();

    int weekHeight();

    void setInit(@NonNull Resources resources);

    void drawWeekDay(Canvas canvas, String text, RectF target);

    int weekStart();

}
