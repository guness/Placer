package com.guness.placer.models;

/**
 * Created by guness on 11/08/16.
 */
public class Place {
    /**
     * icon contains the URL of a recommended icon which may be displayed to the user when indicating this result.
     */
    public String icon;

    /**
     * id contains a unique stable identifier denoting this place. This identifier may not be used to
     * retrieve information about this place, but is guaranteed to be valid across sessions.
     * It can be used to consolidate data about this place, and to verify the identity of a place across separate searches.
     * Note: The id is now deprecated in favor of place_id.
     */
    public String id;

    /**
     * geometry contains geometry information about the result, generally including the location (geocode)
     * of the place and (optionally) the viewport identifying its general area of coverage.
     */
    public Geometry geometry;


    /**
     * name contains the human-readable name for the returned result. For establishment results, this is usually the business name.
     */
    public String name;

    //TODO:
    /**
     *
     */
    public Object opening_hours;

    /**
     * photos[] — an array of photo objects, each containing a reference to an image. A Place Search will return at most one photo object.
     * Performing a Place Details request on the place may return up to ten photos.
     */
    public Object[] photos;

    /**
     * place_id — a textual identifier that uniquely identifies a place. To retrieve information about the place,
     * pass this identifier in the placeId field of a Places API request
     */
    public String place_id;

    /**
     * scope — Indicates the scope of the place_id. The possible values are: APP, GOOGLE
     */
    public String scope;

    /**
     * alt_ids — An array of zero, one or more alternative place IDs for the place, with a scope
     * related to each alternative ID. Note: This array may be empty or not present.
     * If present, it contains the following fields: place_id, scope
     */
    public String[] alt_ids;

    /**
     * price_level — The price level of the place, on a scale of 0 to 4. The exact amount indicated by a specific value will vary from region to region.
     */
    public int price_level;

    /**
     * rating contains the place's rating, from 1.0 to 5.0, based on aggregated user reviews.
     */
    public double rating;

    /**
     * reference contains a unique token that you can use to retrieve additional information about
     * this place in a Place Details request. Although this token uniquely identifies the place,
     * the converse is not true. A place may have many valid reference tokens. It's not guaranteed that
     * the same token will be returned for any given place across different searches. Note: The reference is now deprecated in favor of place_id
     */
    public String reference;

    /**
     * types[] contains an array of feature types describing the given result.
     * See the list of supported types for more information.
     * XML responses include multiple <type> elements if more than one type is assigned to the result.
     */
    public String[] types;

    /**
     * vicinity contains a feature name of a nearby location. Often this feature refers to a street or
     * neighborhood within the given results. The vicinity property is only returned for a Nearby Search.
     */
    public String vicinity;

    /**
     * formatted_address is a string containing the human-readable address of this place.
     * Often this address is equivalent to the "postal address".
     * The formatted_address property is only returned for a Text Search.
     */
    public String formatted_address;

    /**
     * permanently_closed is a boolean flag indicating whether the place has permanently shut down (value true).
     * If the place is not permanently closed, the flag is absent from the response.
     */
    public boolean permanently_closed;

}
