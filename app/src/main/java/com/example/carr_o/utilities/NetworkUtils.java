package com.example.carr_o.utilities;

import android.net.Uri;
import android.util.Log;

import com.example.carr_o.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final static String BASE_URL =
            "https://marketcheck-prod.apigee.net/v1/vin/";

//    https://marketcheck-prod.apigee.net/v1/vin/JTDKB20U667066229/specs?api_key=O7fu3tAaYsKoqTLxHG7q2g8xrSm4iNtG

    static String VIN = "JTDKB20U667066229";

    final static String PARAM_SOURCE = "source";
    final static String SOURCE = "the-next-web";

    final static String PARAM_SORT_BY = "sortBy";
    final static String SORT_BY = "latest";

    final static String PARAM_API_KEY = "api_key";
    final static String API_KEY = BuildConfig.ApiKey;
//    final static String API_KEY = BuildConfig.ApiKey;

    public static URL buildUrl(String vin) {
        if(vin != null) VIN = vin;

        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(VIN)
                .appendPath("specs")
//                .appendQueryParameter(PARAM_SOURCE, SOURCE)
//                .appendQueryParameter(PARAM_SORT_BY, SORT_BY)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d("IN_BUILD_URL", url.toString());
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
