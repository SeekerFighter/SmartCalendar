package com.seeker.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.seeker.calendar.controller.WeekDayController;
import com.seeker.calendar.utils.Constants;

/**
 * @author Seeker
 * @date 2018/12/19/019  17:21
 */
class WeekDayView extends View {

    private Context mContext;

    private WeekDayController weekController;

    private RectF target = new RectF();

    public WeekDayView(Context context) {
        this(context,null);
    }

    public WeekDayView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WeekDayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public void initController(WeekDayController weekController){
        this.weekController = weekController;
        this.weekController.setInit(mContext.getResources());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),weekController.weekHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (weekController.weeks().length != Constants.WEEK_DAYS){
            throw new RuntimeException("custom weekController,but weeks().length != " + Constants.WEEK_DAYS);
        }
        int dayCellWidth = getMeasuredWidth() / Constants.WEEK_DAYS;
        String[] weeks = weekController.weeks();
        target.top = 0;
        target.bottom = getMeasuredHeight();
        for (int i = 0; i< weeks.length;i++){
            target.left = dayCellWidth * i;
            target.right = target.left+dayCellWidth;
            weekController.drawWeekDay(canvas,weeks[i],target);
        }
    }
}
