package com.seeker.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.seeker.calendar.controller.DefaultShowController;
import com.seeker.calendar.controller.DefaultWeekDayController;
import com.seeker.calendar.controller.EventController;
import com.seeker.calendar.controller.ShowController;
import com.seeker.calendar.controller.WeekDayController;
import com.seeker.calendar.controller.drawer.DefaultDrawerController;
import com.seeker.calendar.controller.drawer.DefaultHDrawerController;
import com.seeker.calendar.controller.drawer.DrawerController;
import com.seeker.calendar.controller.selected.DefaultSingleSelectedController;
import com.seeker.calendar.controller.selected.SelectedController;
import com.seeker.calendar.utils.CalendarUtils;
import com.seeker.calendar.utils.Clazz;
import com.seeker.calendar.utils.Constants;

/**
 * @author Seeker
 * @date 2018/12/19/019  17:41
 */
public class SmartCalendarView extends LinearLayout {

    private Context mContext;

    private DrawerController drawerController;
    private SelectedController selectedController;
    private ShowController showController;
    private WeekDayController weekController;

    private CalendarView calendarView;

    public SmartCalendarView(Context context) {
        this(context,null);
    }

    public SmartCalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SmartCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.setOrientation(LinearLayout.VERTICAL);
        if (!isInEditMode()) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SmartCalendarView);
            int orientation = typedArray.getInt(R.styleable.SmartCalendarView_orientation,Constants.VERTICAL);
            drawerController = Clazz.getConstructor(typedArray.getString(R.styleable.SmartCalendarView_drawerController));
            if (drawerController == null) {
                if (orientation == Constants.HORIZONTAL){
                    drawerController = new DefaultHDrawerController();
                }else {
                    drawerController = new DefaultDrawerController();
                }
            }
            selectedController = Clazz.getConstructor(typedArray.getString(R.styleable.SmartCalendarView_selectedController));
            if (selectedController == null){
                selectedController = new DefaultSingleSelectedController();
            }
            showController = Clazz.getConstructor(typedArray.getString(R.styleable.SmartCalendarView_showController));
            if(showController == null){
                showController = new DefaultShowController();
            }
            weekController = Clazz.getConstructor(typedArray.getString(R.styleable.SmartCalendarView_weekDayController));
            if(weekController == null){
                weekController = new DefaultWeekDayController();
            }
            typedArray.recycle();
            addWeekView();
            addSmartCalendarView(orientation);
        }
    }

    private void addWeekView(){
        Constants.weekStart = weekController.weekStart();
        WeekDayView weekView = new WeekDayView(mContext);
        weekView.initController(weekController);
        addView(weekView,new LayoutParams(-1,-2));
    }

    private void addSmartCalendarView(int orientation){
        calendarView = new CalendarView(mContext);
        calendarView.initController(drawerController,selectedController,showController);
        calendarView.setOrientation(orientation);
        calendarView.setUpAdapter();
        addView(calendarView,new LayoutParams(-1,-2));
    }

    public void refreshUI(){
        RecyclerView.Adapter adapter = calendarView.getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void backToToday(){
        calendarView.smoothScrollToPosition(
                CalendarUtils.getInFactPosition(showController.fromYearMonth(),CalendarUtils.getToday()));
    }

    public void setCalendarScrollCallback(CalendarScrollCallback callback) {
        calendarView.setCalendarScrollCallback(callback);
    }

    public void setOnSelectedCallback(SelectedController.OnSelectedCallback callback){
        selectedController.setOnSelectedCallback(callback);
    }

    public SelectedController getSelectedController() {
        return selectedController;
    }

    public EventController getEventController(){
        return drawerController.getCustomEventController();
    }
}
