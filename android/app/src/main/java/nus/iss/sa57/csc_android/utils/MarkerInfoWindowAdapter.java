package nus.iss.sa57.csc_android.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.io.File;
import java.util.List;
import java.util.Optional;

import nus.iss.sa57.csc_android.DetailsActivity;
import nus.iss.sa57.csc_android.R;
import nus.iss.sa57.csc_android.model.CatSighting;

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{
    private final View window;
    private final Context context;
    private List<CatSighting> csList;

    public MarkerInfoWindowAdapter(Context context, List<CatSighting> csList) {
        this.context = context;
        this.window = LayoutInflater.from(context).inflate(R.layout.marker_info_window, null);
        this.csList = csList;
    }

    private void render(Marker marker, View view) {
        ImageView imageView = view.findViewById(R.id.info_img);
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Integer csId = (Integer) marker.getTag();
        File destFile = new File(externalFilesDir, ("img-" + String.valueOf(csId) + "-0"));
        Bitmap bitmap = BitmapFactory.decodeFile(destFile.getAbsolutePath());
        imageView.setImageBitmap(bitmap);
        TextView csNameView = view.findViewById(R.id.info_name);
        CatSighting markerSighting = csList.stream()
                .filter(cs -> cs.getId() == csId.intValue())
                .findFirst()
                .get();
        csNameView.setText(markerSighting.getSightingName());
    }

    @Override
    public View getInfoWindow(Marker marker) {
        render(marker, window);
        return window;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
