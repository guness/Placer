package com.guness.placer.models;

/**
 * Created by guness on 11/08/16.
 */
public class Leg {

    /**
     * steps[] contains an array of steps denoting information about each separate step of the leg of the journey.
     */
    public Object steps[];

    /**
     * distance indicates the total distance covered by this leg, as a field with the following elements:
     */
    public Distance distance;

    /**
     * duration indicates the total duration of this leg, as a field with the following elements
     */
    public Duration duration;

    /**
     * duration_in_traffic indicates the total duration of this leg. This value is an estimate of the time
     * in traffic based on current and historical traffic conditions
     */
    public Object duration_in_traffic;

    /**
     * arrival_time contains the estimated time of arrival for this leg. This property is only returned for transit directions
     */
    public Time arrival_time;

    /**
     * departure_time contains the estimated time of departure for this leg, specified as a Time object. The departure_time is only available for transit directions.
     */
    public Time departure_time;

    /**
     * start_location contains the latitude/longitude coordinates of the origin of this leg.
     */
    public Location start_location;

    /**
     * end_location contains the latitude/longitude coordinates of the given destination of this leg.
     */
    public Location end_location;

    /**
     * start_address contains the human-readable address (typically a street address) resulting from reverse geocoding the start_location of this leg.
     */
    public String start_address;

    /**
     * end_address contains the human-readable address (typically a street address) from reverse geocoding the end_location of this leg.
     */
    public String end_address;


}
