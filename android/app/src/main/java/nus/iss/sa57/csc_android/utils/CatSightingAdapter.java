package nus.iss.sa57.csc_android.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.File;
import java.util.List;

import nus.iss.sa57.csc_android.R;
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
        ImageView catphoto = view.findViewById(R.id.catphoto);
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File destFile = new File(externalFilesDir, ("img-" + catSightingList.get(pos).getId() + "-0"));
        Bitmap bitmap = BitmapFactory.decodeFile(destFile.getAbsolutePath());
        catphoto.setImageBitmap(bitmap);
        TextView uploadby = view.findViewById(R.id.uploadedby);
        uploadby.setText(catSightingList.get(pos).getSuggestedCatName());
        TextView location = view.findViewById(R.id.location);
        location.setText(GeocodeHelper.getAddressFromLocation(context,catSightingList.get(pos).
                        getLocationLat(),catSightingList.get(pos).getLocationLong()));
        TextView uploadtime = view.findViewById(R.id.uploadtime);
        uploadtime.setText(catSightingList.get(pos).getTime());
        return view;
    }
}
