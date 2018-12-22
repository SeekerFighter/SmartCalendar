package com.seeker.smartcalendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.seeker.calendar.CalendarDay;
import com.seeker.calendar.CalendarScrollCallback;
import com.seeker.calendar.SmartCalendarView;
import com.seeker.calendar.controller.selected.SelectedController;

/**
 * @author Seeker
 * @date 2018/12/22/022  9:41
 * @describe TODO
 */
public abstract class BaseActivity extends AppCompatActivity {

    SmartCalendarView smartCalendarView;

    CalendarDay calendarDay = new CalendarDay();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentViewLayId());
        smartCalendarView = findViewById(R.id.smartCalendarView);
        smartCalendarView.setCalendarScrollCallback(new CalendarScrollCallback() {

            @Override
            public void onInitShowCalendar(CalendarDay initCalendar) {
                setTitle(initCalendar.year+"/"+(initCalendar.month+1));
                calendarDay.year = initCalendar.year;
                calendarDay.month = initCalendar.month;
            }

            @Override
            public void onScrollIdelDateRanger(int startYear, int startMonth, int endYear, int endMonth) {
                setTitle(startYear+"/"+(startMonth+1));
                calendarDay.year = startYear;
                calendarDay.month = startMonth;
            }

            @Override
            public void onScrollingDateRanger(int startYear, int startMonth, int endYear, int endMonth) {

            }
        });

        smartCalendarView.setOnSelectedCallback(new SelectedController.OnSelectedCallback() {
            @Override
            public void onDayOfMonthSelected(CalendarDay calendarDay) {
                makeToast(calendarDay.toString());
            }

            @Override
            public void onDayRangedSelected(CalendarDay start, CalendarDay end) {
                makeToast("From "+start.toString()+" to "+end.toString());
            }
        });
    }

    public void makeToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    public abstract int contentViewLayId();

}
