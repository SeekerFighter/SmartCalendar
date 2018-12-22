package com.seeker.calendar.controller.drawer;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.seeker.calendar.CalendarDay;
import com.seeker.calendar.R;
import com.seeker.calendar.controller.DefaultEventController;
import com.seeker.calendar.controller.EventController;
import com.seeker.calendar.utils.CalendarUtils;
import com.seeker.calendar.utils.Constants;

/**
 * @author Seeker
 * @date 2018/12/18/018  14:19
 */
public class DefaultDrawerController<Event> implements DrawerController<Event> {

    public final Path path = new Path();
    public final RectF bg = new RectF();

    public Paint mMonthDayPaint;
    public Paint mMonthLabelPaint;
    public Paint mSelectedPaint;
    public Paint mEventPaint;

    private int monthHeaderHeight;
    private int mRowHeight;

    public EventController<Event> eventController;

    public DefaultDrawerController(){

    }

    @Override
    public int rowHeight() {
        return mRowHeight;
    }

    @Override
    public int headerLabelHeight() {
        return monthHeaderHeight;
    }

    @Override
    public void setInit(@NonNull Resources resources){

        monthHeaderHeight = resources.getDimensionPixelOffset(R.dimen.header_month_height);
        mRowHeight = resources.getDimensionPixelOffset(R.dimen.raw_height);

        mMonthLabelPaint = new Paint();
        mMonthLabelPaint.setAntiAlias(true);
        mMonthLabelPaint.setTextSize(resources.getDimensionPixelSize(R.dimen.text_size_month));
        mMonthLabelPaint.setTypeface(Constants.typeface);
        mMonthLabelPaint.setTextAlign(Paint.Align.CENTER);
        mMonthLabelPaint.setStyle(Paint.Style.FILL);

        mSelectedPaint = new Paint();
        mSelectedPaint.setFakeBoldText(true);
        mSelectedPaint.setAntiAlias(true);
        mSelectedPaint.setColor(Constants.daySelectedBgColor);
        mSelectedPaint.setTextAlign(Paint.Align.CENTER);
        mSelectedPaint.setStyle(Paint.Style.FILL);

        mMonthDayPaint = new Paint();
        mMonthDayPaint.setAntiAlias(true);
        mMonthDayPaint.setTextSize(resources.getDimensionPixelSize(R.dimen.text_size_day));
        mMonthDayPaint.setTypeface(Constants.typeface);
        mMonthDayPaint.setStyle(Paint.Style.FILL);
        mMonthDayPaint.setTextAlign(Paint.Align.CENTER);
        mMonthDayPaint.setFakeBoldText(false);

        mEventPaint = new Paint();
        mEventPaint.setFakeBoldText(true);
        mEventPaint.setAntiAlias(true);
        mEventPaint.setColor(Constants.daySelectedBgColor);
        mEventPaint.setTextAlign(Paint.Align.CENTER);
        mEventPaint.setStyle(Paint.Style.FILL);
        mEventPaint.setStrokeWidth(resources.getDimensionPixelSize(R.dimen.event_stroke_width));
    }

    @Override
    public void drawMonthHeader(Canvas canvas,CalendarDay currentYearMonth, float widgetWidth, float firstDayOffsetLeft,float firstDayOffsetRight) {
        CalendarDay today = CalendarUtils.getToday();
        String text = (currentYearMonth.month+1)+"月";
        Paint.FontMetrics fontMetrics = mMonthLabelPaint.getFontMetrics();
        RectF target = new RectF(firstDayOffsetLeft,0,firstDayOffsetRight,monthHeaderHeight);
        int baseLine = (int)((target.bottom + target.top - fontMetrics.bottom - fontMetrics.top) / 2.0f);
        if (currentYearMonth.year == today.year && currentYearMonth.month == today.month){
            mMonthLabelPaint.setColor(Constants.currentColor);
        }else if (currentYearMonth.year > today.year || (currentYearMonth.year == today.year && currentYearMonth.month > today.month)){
            mMonthLabelPaint.setColor(Constants.dayEnabledColor);
        }else {
            mMonthLabelPaint.setColor(Color.BLACK);
        }
        canvas.save();
        canvas.drawText(text,target.centerX(),baseLine,mMonthLabelPaint);
        canvas.restore();
    }

    @Override
    public void drawDayCell(Canvas canvas,CalendarDay currentYearMonth,CalendarDay drawedCalendar,RectF target, boolean isSelected,int location,Event event) {

        boolean isThisYearMonth = currentYearMonth.year == drawedCalendar.year && currentYearMonth.month == drawedCalendar.month;

        if (!drawOtherMonthDay() && !isThisYearMonth){
            return;
        }
        canvas.save();
        drawRangedPath(canvas,location,target,isThisYearMonth);
        drawSelectedBg(canvas,target,isSelected,isThisYearMonth);
        drawDayEvent(canvas,target,event);
        drawDayText(canvas, drawedCalendar, target,isSelected,isThisYearMonth);
        canvas.restore();
    }

    @Override
    public boolean enableAfterDays() {
        return false;
    }

    @Override
    public boolean enablePreviousDay() {
        return true;
    }

    @Override
    public EventController<Event> getCustomEventController() {
        if (eventController == null){
            eventController = new DefaultEventController<>();
        }
        return eventController;
    }

    public boolean drawOtherMonthDay(){
        return false;
    }

    public void drawRangedPath(Canvas canvas,int location,RectF target,boolean isThisYearMonth){
        if (location != LOCATION_NONE){
            float smaller = getSmallerSize(target);
            mSelectedPaint.setColor(Constants.daySelectedBgRangerColor);
            bg.left = target.left;
            bg.right = target.right;
            bg.top = target.centerY() - smaller * 0.6f;
            bg.bottom = target.centerY() + smaller * 0.6f;
            initPath(location,target);
            canvas.drawPath(path,mSelectedPaint);
        }
    }

    public void drawSelectedBg(Canvas canvas,RectF target,boolean selected,boolean isThisYearMonth){
        if (selected && isThisYearMonth) {
            mSelectedPaint.setColor(Constants.daySelectedBgColor);
            canvas.drawCircle(target.centerX(), target.centerY(), getSmallerSize(target) * 0.8f, mSelectedPaint);
        }
    }

    public void drawDayEvent(Canvas canvas,RectF target,Event event){
        if (event == null) return;
        float smaller = getSmallerSize(target);
        canvas.drawCircle(target.centerX(),target.bottom-smaller*0.15f,smaller*0.1f,mEventPaint);
    }

    public void drawDayText(Canvas canvas,CalendarDay drawedCalendar,RectF target,boolean selected,boolean isThisYearMonth){
        String text = drawedCalendar.isSameDay(CalendarUtils.getToday())?"今":String.valueOf(drawedCalendar.day);
        initDayPaintColor(drawedCalendar,selected,isThisYearMonth);
        Paint.FontMetrics fontMetrics = mMonthDayPaint.getFontMetrics();
        int baseLine = (int)((target.bottom + target.top - fontMetrics.bottom - fontMetrics.top) / 2.0f);
        canvas.drawText(text,target.centerX(),baseLine,mMonthDayPaint);
    }

    public void initDayPaintColor(CalendarDay current,boolean selected,boolean isThisYearMonth){
        if (!isThisYearMonth){
            mMonthDayPaint.setColor(Constants.dayEnabledColor);
        }else if (selected){
            mMonthDayPaint.setColor(Color.WHITE);
        }else if (current.isSameDay(CalendarUtils.getToday())){
            mMonthDayPaint.setColor(Constants.currentColor);
        }else if ((!(enableAfterDays() || current.isBefore(CalendarUtils.getToday())))){
            mMonthDayPaint.setColor(Constants.dayEnabledColor);
        }else {
            mMonthDayPaint.setColor(Constants.dayNormalColor);
        }
    }

    private void initPath(int location, RectF target){
        path.reset();
        switch (location){
            case LOCATION_SELECTED_START_SIDE:
                bg.left = target.centerX();
                path.addRoundRect(bg, Constants.leftRadiusModel, Path.Direction.CW);
                break;
            case LOCATION_LEFT_SIDE:
                bg.left = target.centerX()-target.width()*0.3f;
                path.addRoundRect(bg, Constants.leftRadiusModel, Path.Direction.CW);
                break;
            case LOCATION_INNER:
                path.addRoundRect(bg, Constants.normalRadiusModel, Path.Direction.CW);
                break;
            case LOCATION_RIGHT_SIDE:
                bg.right = target.centerX()+target.width()*0.3f;
                path.addRoundRect(bg, Constants.rightRadiusModel, Path.Direction.CW);
                break;
            case LOCATION_SELECTED_END_SIDE:
                bg.right = target.centerX();
                path.addRoundRect(bg, Constants.rightRadiusModel, Path.Direction.CW);
                break;
            case LOCATION_BOTH_SIDE:
                bg.left = target.centerX()-target.width()*0.3f;
                bg.right = target.centerX()+target.width()*0.3f;
                path.addRoundRect(bg, Constants.allRadiusModel, Path.Direction.CW);
                break;
        }
    }

    public float getSmallerSize(RectF target){
        return Math.min(target.width(),target.height())/2;
    }

}
