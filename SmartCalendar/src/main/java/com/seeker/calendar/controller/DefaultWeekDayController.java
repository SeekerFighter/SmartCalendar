package com.seeker.calendar.controller;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.seeker.calendar.R;

import java.util.Calendar;

/**
 * @author Seeker
 * @date 2018/12/19/019  17:23
 */
public class DefaultWeekDayController implements WeekDayController {

    private Paint paint;

    private int height;

    @Override
    public String[] weeks() {
        return new String[]{"日","一","二","三","四","五","六"};
    }

    @Override
    public int weekStart() {
        return Calendar.SUNDAY;
    }

    @Override
    public int weekHeight() {
        return height;
    }

    @Override
    public void setInit(@NonNull Resources resources) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(resources.getDimensionPixelSize(R.dimen.text_size_day));
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setFakeBoldText(false);
        paint.setColor(0XFF4886FF);
        this.height = resources.getDimensionPixelOffset(R.dimen.raw_height);
    }

    @Override
    public void drawWeekDay(Canvas canvas, String text, RectF target) {
        canvas.save();
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        int baseLine = (int)((target.bottom + target.top - fontMetrics.bottom - fontMetrics.top) / 2.0f);
        canvas.drawText(text,target.centerX(),baseLine,paint);
        canvas.restore();
    }
}
