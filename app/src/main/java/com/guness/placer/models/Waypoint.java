package com.guness.placer.models;

/**
 * Created by guness on 11/08/16.
 */
public class Waypoint {

    /**
     * geocoder_status indicates the status code resulting from the geocoding operation. This field may contain the following values.
     */
    public String geocoder_status;

    /**
     * partial_match indicates that the geocoder did not return an exact match for the original request,
     * though it was able to match part of the requested address. You may wish to examine the original request
     * for misspellings and/or an incomplete address.
     */
    public boolean partial_match;

    /**
     * place_id is a unique identifier that can be used with other Google APIs.
     */
    public String place_id;

    /**
     * types indicates the address type of the geocoding result used for calculating directions.
     */
    public String[] types;
}
