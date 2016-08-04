package br.com.tramalho.googlelocationservices;

import java.util.HashMap;

/**
 * Created by trama on 03/08/16.
 */
public abstract class Const {

    public static String PACKAGE = Const.class.getPackage().getName();

    public static String BROADCAST_ACTION = PACKAGE.concat(".ACTIVITIES_BROADCAST");
    public static String PROBABLE_ACTIVIES_EXTRA = BROADCAST_ACTION.concat("PROBABLE_ACTIVIES_EXTRA");

    public static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;

    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    //public static final float GEOFENCE_RADIUS_IN_METERS = 1609; // 1 mile, 1.6 km
    public static final float GEOFENCE_RADIUS_IN_METERS = 1; // 1 mile, 1.6 km


    public static HashMap<String, LatLng> LANDMARKS = new HashMap<String, LatLng>();

    static {
        LANDMARKS.put("CI&T", new LatLng(-23.554995, -46.6629269));
    }

}
