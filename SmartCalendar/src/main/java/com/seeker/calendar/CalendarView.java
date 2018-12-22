package com.seeker.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.seeker.calendar.controller.ShowController;
import com.seeker.calendar.controller.drawer.DrawerController;
import com.seeker.calendar.controller.selected.SelectedController;
import com.seeker.calendar.utils.CalendarUtils;
import com.seeker.calendar.utils.Constants;

class CalendarView extends RecyclerView {

    private MonthAdapter mAdapter;
    private CalendarScrollCallback mCalendarScrollCallback;
    private DrawerController drawerController;
    private SelectedController selectedController;
    private ShowController showController;
    private LinearLayoutManager layoutManager;

    private CalendarDay initCalendarDay;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void initController(DrawerController drawerController, SelectedController selectedController, ShowController showController) {
        this.drawerController = drawerController;
        this.selectedController = selectedController;
        this.showController = showController;
    }

    public void setCalendarScrollCallback(CalendarScrollCallback callback) {
        this.mCalendarScrollCallback = callback;
        if (mCalendarScrollCallback != null && initCalendarDay != null){
            mCalendarScrollCallback.onInitShowCalendar(initCalendarDay);
        }
    }

    private void init(Context paramContext) {
        setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(paramContext);
        setLayoutManager(layoutManager);
        addOnScrollListener(scrollListener);
        setUpListView();
    }

    public void setOrientation(int orientation){
        Constants.orientation = orientation;
        layoutManager.setOrientation(orientation);
        if (orientation == Constants.HORIZONTAL) {
            PagerSnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(this);
        }
    }

    public void setUpAdapter() {
        if (mAdapter == null) {
            mAdapter = new MonthAdapter(getContext(), drawerController, selectedController, showController);
        }
        setAdapter(mAdapter);
        showController.initCurrentCalendar(this);
        post(new Runnable() {
            @Override
            public void run() {
                int[] from = showController.fromYearMonth();
                int position = layoutManager.findFirstVisibleItemPosition();
                int year = CalendarUtils.getCurrentYear(from,position);
                int month = CalendarUtils.getCurrentMonth(from,position);
                initCalendarDay = new CalendarDay(year,month,-1);
                if (mCalendarScrollCallback != null){
                    mCalendarScrollCallback.onInitShowCalendar(initCalendarDay);
                }
            }
        });
    }

    private void setUpListView() {
        setVerticalScrollBarEnabled(false);
        setFadingEdgeLength(0);
    }

    private final OnScrollListener scrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                callback(0);
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            callback(1);
        }

        private void callback(int state){
            if (mCalendarScrollCallback == null) return;
            int start = layoutManager.findFirstVisibleItemPosition();
            int end = layoutManager.findLastVisibleItemPosition();
            int[] from = showController.fromYearMonth();
            int startYear = CalendarUtils.getCurrentYear(from, start);
            int startMonth = CalendarUtils.getCurrentMonth(from, start);
            int endYear =  CalendarUtils.getCurrentYear(from, end);
            int endMonth = CalendarUtils.getCurrentMonth(from, end);
            if (state == 0){
                mCalendarScrollCallback.onScrollIdelDateRanger(startYear,startMonth,endYear,endMonth);
            }else {
                mCalendarScrollCallback.onScrollingDateRanger(startYear,startMonth,endYear,endMonth);
            }
        }
    };

}