package com.guness.placer.models;

/**
 * Created by guness on 11/08/16.
 */
public class DirectionResponse {

    /**
     * status contains metadata on the request
     */
    public String status;

    /**
     * geocoded_waypoints contains an array with details about the geocoding of origin, destination and waypoints.
     */
    public Waypoint[] geocoded_waypoints;

    /**
     * routes contains an array of routes from the origin to the destination. See Routes below. Routes consist of nested Legs and Steps.
     */
    public Route[] routes;

    /**
     * available_travel_modes contains an array of available travel modes.
     * This field is returned when a request specifies a travel mode and gets no results.
     */
    public Object[] available_travel_modes;
}
