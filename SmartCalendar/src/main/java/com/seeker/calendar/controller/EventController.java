package com.seeker.calendar.controller;

import com.seeker.calendar.CalendarDay;

/**
 * @author Seeker
 * @date 2018/12/20/020  16:46
 * 事件控制
 */
public interface EventController<Event> {

    void addEvent(CalendarDay keyDay, Event event);

    void removeEvent(CalendarDay keyDay);

    Event getEvent(CalendarDay keyDay);

}
