package com.seeker.calendar.utils;

import android.graphics.Typeface;

import java.util.Calendar;

/**
 * @author Seeker
 * @date 2018/12/19/019  17:26
 */
public final class Constants {

    public static final int currentColor = 0xFFFF1C5E;
    public static final int dayNormalColor = 0xFF666666;
    public static final int dayEnabledColor = 0xFFEAEAEA;
    public static final int daySelectedBgColor = 0XFF4886FF;
    public static final int daySelectedBgRangerColor = 0XFFDAE7FF;
    public static final Typeface typeface = Typeface.create("sans-serif", Typeface.NORMAL);
    private static final float RADIUS = 45;
    public static final float[] leftRadiusModel = { RADIUS, RADIUS, 0f, 0f, 0f, 0f,RADIUS, RADIUS };
    public static final float[] normalRadiusModel = { 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f };
    public static final float[] rightRadiusModel = { 0f, 0f, RADIUS, RADIUS, RADIUS, RADIUS, 0f, 0f };
    public static final float[] allRadiusModel = { RADIUS,RADIUS, RADIUS, RADIUS, RADIUS, RADIUS, RADIUS,RADIUS};

    public static final int DAY = 24*60*60*1000;

    public static final int MAX_ROWS = 6;//横向滚动最大行数

    public static final int WEEK_DAYS = 7;//一周7天

    public static final int HORIZONTAL = 0;//横向滚动

    public static final int VERTICAL = 1;//纵向滚动

    public static int orientation = VERTICAL;//滚动方向

    public static int weekStart = Calendar.SUNDAY;//从周几开始

}
