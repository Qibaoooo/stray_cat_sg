package nus.iss.sa57.csc_android;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

import nus.iss.sa57.csc_android.model.CatSighting;

public class CatSightingAdapter extends ArrayAdapter<Object> {
    private final Context context;
    protected List<CatSighting> catSightingList;

    public CatSightingAdapter(Context context, List<CatSighting> catSightingList) {
        super(context, R.layout.cat_sighting_list);
        this.context = context;
        this.catSightingList = catSightingList;
        addAll(new Object[catSightingList.size()]);
    }

    @Override
    public View getView(int pos, View view, @NonNull ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cat_sighting_list, parent, false);
        }
        TextView uploadby = view.findViewById(R.id.uploadedby);
        uploadby.setText(catSightingList.get(pos).getSightingName());
        TextView location = view.findViewById(R.id.location);
        location.setText("Latitude: " + catSightingList.get(pos).getLocationLat().toString()
                + " Longtitude: " + catSightingList.get(pos).getLocationLong().toString());
        TextView uploadtime = view.findViewById(R.id.uploadtime);
        uploadtime.setText(catSightingList.get(pos).getTime().toString());
        return view;
    }
}
