package com.seeker.smartcalendar.custom;

import com.seeker.calendar.controller.ShowController;
import com.seeker.calendar.utils.CalendarUtils;
import android.support.v7.widget.RecyclerView;
/**
 * @author Seeker
 * @date 2018/12/22/022  11:06
 * @describe 显示自定义
 */
public class CustomShowController implements ShowController {
    @Override
    public int[] fromYearMonth() {
        return new int[]{2000,0};
    }

    @Override
    public int[] toYearMonth() {
        return new int[]{2030,11};
    }

    @Override
    public void initCurrentCalendar(RecyclerView recyclerView) {
        recyclerView.scrollToPosition(CalendarUtils.getInFactPosition(fromYearMonth(),CalendarUtils.getToday()));
    }
}
