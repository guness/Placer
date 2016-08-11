package com.guness.placer.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.guness.placer.R;
import com.guness.placer.models.DirectionResponse;

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
public class FindDirectionTask extends AsyncTask<OnDirectionResponseListener, Void, DirectionResponse> {


    //https://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&key=YOUR_API_KEY
    private static final String ENDPOINT = "https://maps.googleapis.com/maps/api/directions/json?";
    private static final String PARAM_ORIGIN = "origin=";
    private static final String PARAM_DESTINATION = "destination=place_id:";
    private static final String PARAM_MODE = "mode=";
    private static final String PARAM_KEY = "key=";

    private static final String MODE = "walking";
    private static final String TAG = FindDirectionTask.class.getSimpleName();

    private OnDirectionResponseListener mOnDirectionResponseListener;

    @Nullable
    private URL mURL;

    public FindDirectionTask(Context context, double lat, double lng, String placeId) {
        try {
            mURL = new URL(ENDPOINT + PARAM_ORIGIN + lat + "," + lng + "&" + PARAM_DESTINATION + placeId + "&" + PARAM_MODE + MODE + "&" + PARAM_KEY + context.getString(R.string.google_directions_key));
        } catch (MalformedURLException e) {
            mURL = null;
            e.printStackTrace();
        }
    }

    @Override
    protected DirectionResponse doInBackground(OnDirectionResponseListener... onDirectionResponseListeners) {
        if (onDirectionResponseListeners.length == 1) {
            mOnDirectionResponseListener = onDirectionResponseListeners[0];
        }
        if (mURL != null) {
            HttpURLConnection urlConnection = null;

            try {
                urlConnection = (HttpURLConnection) mURL.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Gson gson = new Gson();
                DirectionResponse direction = gson.fromJson(new InputStreamReader(in), DirectionResponse.class);
                Log.e(TAG, "URL: " + mURL);
                Log.e(TAG, gson.toJson(direction));
                return direction;
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
    protected void onPostExecute(DirectionResponse directionResponse) {
        if (mOnDirectionResponseListener != null) {
            mOnDirectionResponseListener.onSuccess(directionResponse);
        }
    }
}
