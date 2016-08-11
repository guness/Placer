package com.guness.placer.models;

/**
 * Created by guness on 11/08/16.
 */
public class PlaceResponse {
    /**
     * next_page_token contains a token that can be used to return up to 20 additional results.
     * A next_page_token will not be returned if there are no additional results to display.
     * The maximum number of results that can be returned is 60.
     * There is a short delay between when a next_page_token is issued, and when it will become valid.
     */
    public String next_page_token;

    /**
     * html_attributions contain a set of attributions about this listing which must be displayed to the user.
     */
    public String[] html_attributions;

    /**
     * "results" contains an array of places, with information about each.
     * See Search Results for information about these results.
     * The Places API returns up to 20 establishment results per query.
     * Additionally, political results may be returned which serve to identify the area of the request.
     */
    public Place[] results;

    /**
     * "status" contains metadata on the request. See Status Codes: OK, ZERO_RESULTS, OVER_QUERY_LIMIT, REQUEST_DENIED, INVALID_REQUEST
     */
    public String status;
}
