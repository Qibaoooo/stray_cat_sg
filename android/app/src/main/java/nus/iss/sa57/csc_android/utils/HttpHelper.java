package nus.iss.sa57.csc_android.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {
    public static String getResponse(String urlString){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String responseData = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                //read response data line by line
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                responseData = stringBuilder.toString();
            } else {
                Log.e("DetailsActivity", "HTTP error code: " + responseCode);
            }
        } catch (IOException e) {
            Log.e("DetailsActivity", "Error fetching data from server: " + e.getMessage());
        } finally {
            //Close and release used resources
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("DetailsActivity", "Error closing reader: " + e.getMessage());
                }
            }
        }
        return responseData;
    }
}
