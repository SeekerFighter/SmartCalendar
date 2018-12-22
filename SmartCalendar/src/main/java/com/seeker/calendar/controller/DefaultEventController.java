package com.seeker.calendar.controller;

import android.util.LongSparseArray;

import com.seeker.calendar.CalendarDay;

/**
 * @author Seeker
 * @date 2018/12/20/020  16:50
 */
public class DefaultEventController<Event> implements EventController<Event>{

    private LongSparseArray<Event> events = new LongSparseArray<>();

    @Override
    public void addEvent(CalendarDay keyDay, Event o) {
        long key = keyDay.getTimeInDay();
        events.remove(key);
        events.put(key,o);
    }

    @Override
    public void removeEvent(CalendarDay keyDay) {
        long key = keyDay.getTimeInDay();
        events.remove(key);
    }

    @Override
    public Event getEvent(CalendarDay keyDay) {
        return events.get(keyDay.getTimeInDay(),null);
    }
}
