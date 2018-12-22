
package com.seeker.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import com.seeker.calendar.controller.EventController;
import com.seeker.calendar.controller.drawer.DrawerController;
import com.seeker.calendar.controller.selected.SelectedController;
import com.seeker.calendar.utils.CalendarUtils;
import com.seeker.calendar.utils.Constants;

import java.util.Calendar;

@SuppressLint("ViewConstructor")
class MonthView extends View {

    private CalendarDay mCurrentYearMonth;
    private int mWeekDays = Constants.WEEK_DAYS;
    private int mNumDays = Constants.WEEK_DAYS;
    private int mNumRows = Constants.MAX_ROWS;
    private int mDayOfWeekStart = 0;

    private OnDayClickListener dayClickListener;
    private DrawerController drawerController;
    private SelectedController selectedController;

    private final RectF target = new RectF();

    private final Calendar mCalendar;

    public MonthView(Context context, DrawerController drawerController,SelectedController selectedController){
        super(context);
        this.drawerController = drawerController;
        this.selectedController = selectedController;
        this.drawerController.setInit(context.getResources());
        this.mCurrentYearMonth = new CalendarDay();
        this.mCalendar = Calendar.getInstance();
    }

    private int calculateNumRows() {
        int offset = findDayOffset();
        int dividend = (offset + mNumDays) / mWeekDays;
        int remainder = (offset + mNumDays) % mWeekDays;
        return (dividend + (remainder > 0 ? 1 : 0));
	}

	private void drawMonthTitle(Canvas canvas) {
        int dayCellWidth = getMeasuredWidth() / mWeekDays;
        int dayOffset = findDayOffset();
        drawerController.drawMonthHeader(canvas,mCurrentYearMonth,
                getMeasuredWidth(),
                dayCellWidth * dayOffset,dayCellWidth * dayOffset+dayCellWidth);

	}

	private int findDayOffset() {
        return (mDayOfWeekStart < Constants.weekStart ? (mDayOfWeekStart + mWeekDays) : mDayOfWeekStart)- Constants.weekStart;
	}

	private void onDayClick(CalendarDay calendarDay) {
        if (dayClickListener == null){
            return;
        }
        if ((calendarDay.isAfter(CalendarUtils.getToday()) && drawerController.enableAfterDays())
                ||(calendarDay.isBefore(CalendarUtils.getToday())&&drawerController.enablePreviousDay())
                || calendarDay.isSameDay(CalendarUtils.getToday())){
            selectedController.setSelectedDay(calendarDay);
            if (selectedController.getOnSelectedCallback() != null){
                SelectedController.OnSelectedCallback selectedCallback = selectedController.getOnSelectedCallback();
                selectedCallback.onDayOfMonthSelected(calendarDay);
                if (selectedController.isRanged()){
                    CalendarDay[] selected = selectedController.getSelected();
                    CalendarUtils.checkRangedValid(selected);
                    selectedCallback.onDayRangedSelected(selected[0],selected[1]);
                }
            }
            dayClickListener.onDayClick(this,calendarDay);
        }
	}

    private void drawMonthDays(Canvas canvas) {
        int top =  drawerController.headerLabelHeight();
        int dayCellWidth = getMeasuredWidth() / mWeekDays;
        int dayOffset = findDayOffset();
        EventController eventController = drawerController.getCustomEventController();
        CalendarDay temp = new CalendarDay(mCurrentYearMonth.year,mCurrentYearMonth.month,1);
        temp = temp.beforeDays(dayOffset);
        dayOffset = 0;
        int day = 1;
        int days = mNumRows * mWeekDays;
        while (day <= days) {
            temp = temp.afterDays(1);
            boolean isSelected = selectedController.contains(temp);
            int location = ifRangedLocation(temp,dayOffset);
            int left = dayCellWidth*dayOffset;
            int right = left + dayCellWidth;
            target.set(left,top,right,top+drawerController.rowHeight());
            drawerController.drawDayCell(canvas,mCurrentYearMonth,temp,target,isSelected,location,eventController.getEvent(temp));
            day++;
            dayOffset++;
            if (dayOffset == mWeekDays) {
                dayOffset = 0;
                top += drawerController.rowHeight();
            }
        }
    }

	private int ifRangedLocation(CalendarDay temp,int dayOffset){
        if (!selectedController.isRanged()){
            return DrawerController.LOCATION_NONE;
        }
        CalendarDay[] selected = selectedController.getSelected();
        CalendarUtils.checkRangedValid(selected);

        CalendarDay start = selected[0];
        CalendarDay end = selected[1];

        int location = DrawerController.LOCATION_NONE;

        if (temp.getTimeInDay() >= start.getTimeInDay() && temp.getTimeInDay() <= end.getTimeInDay()){
            location = DrawerController.LOCATION_INNER;
            if (start.isSameDay(temp) && start.isSameDay(end)){
                location = DrawerController.LOCATION_NONE;
            }else if (start.isSameDay(temp)){
                location = DrawerController.LOCATION_SELECTED_START_SIDE;
                if (temp.day == mNumDays || dayOffset == mWeekDays-1){
                    location = DrawerController.LOCATION_NONE;
                }
            }else if (end.isSameDay(temp)){
                location = DrawerController.LOCATION_SELECTED_END_SIDE;
                if (temp.day == 1 || dayOffset == 0){
                    location = DrawerController.LOCATION_NONE;
                }
            }else if (temp.day == 1 || dayOffset == 0){
                location = DrawerController.LOCATION_LEFT_SIDE;
                if (dayOffset == mWeekDays-1 || temp.day == mNumDays){
                    location = DrawerController.LOCATION_BOTH_SIDE;
                }
            }else if (temp.day == mNumDays || dayOffset == mWeekDays-1){
                location = DrawerController.LOCATION_RIGHT_SIDE;
            }
        }
        return location;
    }

	@Override
	protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
		drawMonthTitle(canvas);
		drawMonthDays(canvas);
	}
    @Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (Constants.orientation == Constants.VERTICAL) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                    drawerController.rowHeight() * mNumRows + drawerController.headerLabelHeight());
        }else {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                    drawerController.rowHeight() * Constants.MAX_ROWS + drawerController.headerLabelHeight());
        }
	}

    @Override
	public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            CalendarDay calendarDay = getDayFromLocation(event.getX(), event.getY());
            if (calendarDay != null) {
                onDayClick(calendarDay);
            }
        }
        return true;
	}

    public CalendarDay getDayFromLocation(float x, float y) {
        int rows = (int) (y - drawerController.headerLabelHeight()) / drawerController.rowHeight();
        int day = 1 + ((int) (x * mWeekDays / getMeasuredWidth()) - findDayOffset()) + rows * mWeekDays;
        if (mCurrentYearMonth.month > 11 || mCurrentYearMonth.month < 0 || mNumDays < day || day < 1)
            return null;
        return new CalendarDay(mCurrentYearMonth.year, mCurrentYearMonth.month, day);
    }

	public void setMonthParams(int currentMonth,int currentYear){
        this.mCurrentYearMonth.month = currentMonth;
        this.mCurrentYearMonth.year = currentYear;
        this.mCurrentYearMonth.day = -1;
        this.mCalendar.set(currentYear,currentMonth,1);
        this.mDayOfWeekStart = mCalendar.get(Calendar.DAY_OF_WEEK);
        this.mNumDays = CalendarUtils.getDaysInMonth(currentMonth, currentYear);
        if (Constants.orientation == Constants.VERTICAL) {
            this.mNumRows = calculateNumRows();
        }
    }

	public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        dayClickListener = onDayClickListener;
	}

	public interface OnDayClickListener {
		void onDayClick(MonthView simpleMonthView, CalendarDay calendarDay);
	}
}