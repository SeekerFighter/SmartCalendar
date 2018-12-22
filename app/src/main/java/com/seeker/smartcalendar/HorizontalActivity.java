package com.seeker.smartcalendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.seeker.calendar.CalendarDay;
import com.seeker.calendar.controller.EventController;
import com.seeker.calendar.utils.CalendarUtils;

import java.util.Random;

/**
 * @author Seeker
 * @date 2018/12/22/022  9:31
 * @describe TODO
 */
public class HorizontalActivity extends BaseActivity {

    Random random = new Random();

    @Override
    public int contentViewLayId() {
        return R.layout.activity_horizontal;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final EventController<Person> eventController = smartCalendarView.getEventController();

        findViewById(R.id.addEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventController.removeEvent(calendarDay);
                calendarDay.day = nextDay();
                Person person = new Person("Seeker",30);
                eventController.addEvent(calendarDay,person);
                smartCalendarView.refreshUI();
                makeToast(person.toString());
            }
        });

        findViewById(R.id.getEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = eventController.getEvent(calendarDay);
                makeToast(person.toString());
            }
        });

        findViewById(R.id.removeEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventController.removeEvent(calendarDay);
                smartCalendarView.refreshUI();
            }
        });
    }


    private int nextDay(){
        return random.nextInt(28)+1;
    }

}
