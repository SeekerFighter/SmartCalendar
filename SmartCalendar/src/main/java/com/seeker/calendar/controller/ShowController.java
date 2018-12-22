package com.seeker.calendar.controller;

import android.support.v7.widget.RecyclerView;

/**
 * @author Seeker
 * @date 2018/12/19/019  16:58
 * @describe 界面显示控制
 */
public interface ShowController {

    int[] fromYearMonth();

    int[] toYearMonth();

    void initCurrentCalendar(RecyclerView recyclerView);

}
