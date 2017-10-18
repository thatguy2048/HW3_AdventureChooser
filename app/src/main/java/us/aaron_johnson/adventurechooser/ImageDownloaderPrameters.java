package us.aaron_johnson.adventurechooser;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by combu on 10/16/2017.
 */

public class ImageDownloaderPrameters {
    String imageUrl;
    ImageView imageToPlace;
    Bitmap btmp;

    protected boolean already_set = false;

    public ImageDownloaderPrameters(String imageUrl, ImageView imageToPlace) {
        this.imageUrl = imageUrl;
        this.imageToPlace = imageToPlace;
    }

    public void setImageFromBitmap(){
        if(btmp != null && imageToPlace != null && !already_set) {
            already_set = true;
            imageToPlace.setImageBitmap(btmp);
        }
    }

    public void setImageFromBitmap(Bitmap bitmap){
        btmp = bitmap;
        setImageFromBitmap();
    }
}
