package com.guness.placer.models;

/**
 * Created by guness on 11/08/16.
 */
public class Route {

    /**
     * summary contains a short textual description for the route, suitable for naming and disambiguating the route from alternatives.
     */
    public String summary;

    /**
     * legs[] contains an array which contains information about a leg of the route,
     * between two locations within the given route. A separate leg will be present for each waypoint or destination specified.
     */
    public Leg[] legs;

    /**
     * waypoint_order (or <waypoint_index> in XML) contains an array indicating the order of any waypoints
     * in the calculated route. This waypoints may be reordered if the request was passed optimize:true within its waypoints parameter.
     */
    public int[] waypoint_order;

    /**
     * overview_polyline contains a single points object that holds an encoded polyline representation of the route.
     * This polyline is an approximate (smoothed) path of the resulting directions.
     */
    public Polyline overview_polyline;

    /**
     * bounds contains the viewport bounding box of the overview_polyline.
     */
    public Object bounds;

    /**
     * copyrights contains the copyrights text to be displayed for this route. You must handle and display this information yourself.
     */
    public String copyrights;

    /**
     * warnings[] contains an array of warnings to be displayed when showing these directions.
     * You must handle and display these warnings yourself.
     */
    public String[] warnings;

    /**
     * fare: If present, contains the total fare (that is, the total ticket costs) on this route.
     */
    public Object fare;

}
