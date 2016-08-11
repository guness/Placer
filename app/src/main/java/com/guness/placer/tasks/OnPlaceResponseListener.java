package com.guness.placer.tasks;

import com.guness.placer.models.PlaceResponse;

/**
 * Created by guness on 11/08/16.
 */
public interface OnPlaceResponseListener {
    void onSuccess(PlaceResponse response);
}
