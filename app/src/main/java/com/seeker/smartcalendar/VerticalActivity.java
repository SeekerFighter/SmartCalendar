package com.seeker.smartcalendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * @author Seeker
 * @date 2018/12/22/022  9:31
 * @describe TODO
 */
public class VerticalActivity extends BaseActivity {
    @Override
    public int contentViewLayId() {
        return R.layout.activity_vertical;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.backToday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smartCalendarView.backToToday();
            }
        });
    }
}
