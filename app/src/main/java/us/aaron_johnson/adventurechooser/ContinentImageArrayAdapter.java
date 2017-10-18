package us.aaron_johnson.adventurechooser;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


import java.util.List;

/**
 * Created by combu on 10/17/2017.
 */

public class ContinentImageArrayAdapter extends ArrayAdapter<ImageDownloaderPrameters> {
    protected List<ImageDownloaderPrameters> items;
    protected Context context;
    protected int layoutResource;

    public ContinentImageArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ImageDownloaderPrameters> objects) {
        super(context, -1, objects);
        this.items = objects;
        this.context = context;
        this.layoutResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d("AC_CONTENT_ADAPTER", "Get View");
        if(convertView == null){
            LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.continent_image_list_item, parent, false);
        }
        items.get(position).imageToPlace = (ImageView)convertView.findViewById(R.id.image);
        return convertView;
    }
}
