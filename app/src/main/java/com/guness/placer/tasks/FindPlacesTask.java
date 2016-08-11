package com.guness.placer.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.guness.placer.R;
import com.guness.placer.models.PlaceResponse;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by guness on 11/08/16.
 */
public class FindPlacesTask extends AsyncTask<OnPlaceResponseListener, Void, PlaceResponse> {

    private static final String ENDPOINT = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private static final String PARAM_LOCATION = "location=";
    private static final String PARAM_RANKBY = "rankby=";
    private static final String PARAM_TYPES = "types=";
    private static final String PARAM_KEY = "key=";

    private static final String TYPE = "mosque";
    private static final String RANK = "distance";
    private static final String TAG = FindPlacesTask.class.getSimpleName();

    private OnPlaceResponseListener mOnPlaceResponseListener;

    @Nullable
    private URL mURL;

    public FindPlacesTask(Context context, double lat, double lng) {
        try {
            mURL = new URL(ENDPOINT + PARAM_LOCATION + lat + "," + lng + "&" + PARAM_RANKBY + RANK + "&" + PARAM_TYPES + TYPE + "&" + PARAM_KEY + context.getString(R.string.google_places_key));
        } catch (MalformedURLException e) {
            mURL = null;
            e.printStackTrace();
        }
    }

    @Override
    protected PlaceResponse doInBackground(OnPlaceResponseListener... onPlaceResponseListener) {
        if (onPlaceResponseListener.length == 1) {
            mOnPlaceResponseListener = onPlaceResponseListener[0];
        }
        if (mURL != null) {
            // https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&rankby=distance&types=mosque&key=AIzaSyCHqOWSlow12hLel8yeXPwRw1gUBrK_rpo
            HttpURLConnection urlConnection = null;

            try {
                urlConnection = (HttpURLConnection) mURL.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Gson gson = new Gson();
                PlaceResponse places = gson.fromJson(new InputStreamReader(in), PlaceResponse.class);
                Log.e(TAG, "URL: " + mURL);
                Log.e(TAG, gson.toJson(places));
                return places;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(PlaceResponse placeResponse) {
        if (mOnPlaceResponseListener != null) {
            mOnPlaceResponseListener.onSuccess(placeResponse);
        }
    }
}
