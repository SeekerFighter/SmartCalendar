package com.seeker.calendar.controller;

import android.support.v7.widget.RecyclerView;

import com.seeker.calendar.utils.CalendarUtils;

import java.util.Calendar;
import java.util.Locale;

/**
 * @author Seeker
 * @date 2018/12/19/019  16:59
 * @describe TODO
 */
public class DefaultShowController implements ShowController{

    private int[] fromYearMonth = new int[2];

    private int[] toYearMonth = new int[2];

    public DefaultShowController(){

        Calendar calendar = Calendar.getInstance(Locale.CHINA);

        toYearMonth[0] = calendar.get(Calendar.YEAR);
        toYearMonth[1] = calendar.get(Calendar.MONTH);

        if (toYearMonth[1] >= 6){
            fromYearMonth[0] = toYearMonth[0];
            fromYearMonth[1] = toYearMonth[1]-6;
        }else {
            fromYearMonth[0] = toYearMonth[0]-1;
            fromYearMonth[1] = toYearMonth[1]+5;
        }

    }

    @Override
    public int[] fromYearMonth() {
        return fromYearMonth;
    }

    @Override
    public int[] toYearMonth() {
        return toYearMonth;
    }

    @Override
    public void initCurrentCalendar(RecyclerView recyclerView) {
        recyclerView.scrollToPosition(CalendarUtils.getInFactPosition(fromYearMonth(),CalendarUtils.getToday()));
    }
}
