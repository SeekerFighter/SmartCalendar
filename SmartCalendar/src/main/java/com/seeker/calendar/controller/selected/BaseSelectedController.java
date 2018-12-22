package com.seeker.calendar.controller.selected;

/**
 * @author Seeker
 * @date 2018/12/21/021  15:13
 */
public abstract class BaseSelectedController implements SelectedController{

    private OnSelectedCallback selectedCallback;

    @Override
    public void setOnSelectedCallback(OnSelectedCallback callback) {
        this.selectedCallback = callback;
    }

    @Override
    public OnSelectedCallback getOnSelectedCallback() {
        return selectedCallback;
    }
}
