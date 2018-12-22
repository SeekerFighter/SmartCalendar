package com.seeker.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.seeker.calendar.controller.ShowController;
import com.seeker.calendar.controller.drawer.DrawerController;
import com.seeker.calendar.controller.selected.SelectedController;
import com.seeker.calendar.utils.CalendarUtils;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.ViewHolder> implements MonthView.OnDayClickListener {

    private final Context mContext;
    private DrawerController drawerController;
    private SelectedController selectedController;
    private int[] fromYearMonth;
    private int[] toYearMonth;

    MonthAdapter(Context context,
                        DrawerController drawerController,
                        SelectedController selectedController,
                        ShowController showController) {
        this.drawerController = drawerController;
        this.selectedController = selectedController;
        this.fromYearMonth = showController.fromYearMonth();
        this.toYearMonth = showController.toYearMonth();
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final MonthView monthView = new MonthView(mContext, drawerController,selectedController);
        monthView.setLayoutParams(new RecyclerView.LayoutParams(-1,-2));
        return new ViewHolder(monthView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final MonthView monthView = viewHolder.monthView;
        monthView.setTag(position);
        monthView.setMonthParams(
                CalendarUtils.getCurrentMonth(fromYearMonth,position),
                CalendarUtils.getCurrentYear(fromYearMonth,position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return CalendarUtils.getMonthCount(fromYearMonth,toYearMonth);
    }

    @Override
    public void onDayClick(MonthView simpleMonthView, CalendarDay calendarDay) {
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final MonthView monthView;

        ViewHolder(View itemView, MonthView.OnDayClickListener onDayClickListener) {
            super(itemView);
            monthView = (MonthView) itemView;
            monthView.setClickable(true);
            monthView.setOnDayClickListener(onDayClickListener);
        }
    }

}