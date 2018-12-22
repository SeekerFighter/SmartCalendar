package com.seeker.calendar;

public interface CalendarScrollCallback {

	/**
	 * 只在控件加载完毕之后第一次回调，返回的是日历初始化时候的年月，
	 * 静止数据由{@link #onScrollIdelDateRanger(int, int, int, int)}回传
	 * 滚动中数据由{@link #onScrollingDateRanger(int, int, int, int)}回传
	 * @param initCalendar
	 */
	void onInitShowCalendar(CalendarDay initCalendar);

	void onScrollIdelDateRanger(int startYear,int startMonth,int endYear,int endMonth);

	void onScrollingDateRanger(int startYear,int startMonth,int endYear,int endMonth);

}