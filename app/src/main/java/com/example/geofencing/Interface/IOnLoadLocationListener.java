package com.example.geofencing.Interface;

import com.example.geofencing.MyLatLng;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface IOnLoadLocationListener {
    void onLoadLocationSuccess(List<MyLatLng> latLngs);
    void onLoadLocationfailed(String message);

}
