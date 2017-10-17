package us.aaron_johnson.adventurechooser;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Continent extends AppCompatActivity {

    public String continent_name = null;
    public ArrayList<ImageDownloader> image_downloaders;
    public LinearLayout mainLayout;
    public Button titleButton;

    protected ArrayList<ImageDownloaderPrameters> dl_parameters;

    public AlertDialog.Builder selectDialogBuilder;
    public AlertDialog selectContinentDialog;

    public void LOGD(String toLog){
        Log.d("AC_CONTINENT", toLog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continent);

        mainLayout = (LinearLayout) findViewById(R.id.scroll_layout);
        titleButton = (Button)findViewById(R.id.title_button);

        selectDialogBuilder = new AlertDialog.Builder(Continent.this);
        selectDialogBuilder.setTitle("Select Continent");

        selectDialogBuilder.setItems(R.array.continents, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String continent = (String)((AlertDialog)dialogInterface).getListView().getAdapter().getItem(i);
                Intent nci = new Intent(getApplicationContext(), Continent.class);
                nci.putExtra("continent", continent);
                startActivity(nci);
            }
        });
        selectContinentDialog = selectDialogBuilder.create();

        if(savedInstanceState != null) {
            continent_name = savedInstanceState.getString("continent_name");
            LOGD("Get data from saved instance state");
        }else{
            //get from extras data
            Bundle extras = getIntent().getExtras();
            if(extras != null){
                continent_name = extras.getString("continent");
            }
            LOGD("Get data from extras");
        }

        if(continent_name != null){
            LOGD("Continent Name: "+continent_name);

            titleButton.setText(continent_name);
            titleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LOGD("Select A New Continent");
                    selectContinentDialog.show();
                }
            });


            int aid =  getResources().getIdentifier(continent_name.replaceAll(" ","").toLowerCase()+"_images", "array", getPackageName());
            String[] image_urls = getResources().getStringArray(aid);

            image_downloaders = new ArrayList<>();
            dl_parameters = new ArrayList<>();

            for(int i = 0; i < image_urls.length; ++i){
                LOGD("URL: "+image_urls[i]);

                ImageDownloaderPrameters idp = new ImageDownloaderPrameters(
                        image_urls[i],
                        new ImageView(this)
                );

                dl_parameters.add(idp);

                idp.imageToPlace.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                LOGD("Add View");
                mainLayout.addView(idp.imageToPlace);

                if(savedInstanceState != null){
                    LOGD("Loading From Saved Instance");
                    byte[] idta = savedInstanceState.getByteArray(image_urls[i]);
                    idp.setImageFromBitmap(
                            BitmapFactory.decodeByteArray(idta, 0, idta.length)
                    );
                }else {
                    LOGD("Start Download");
                    ImageDownloader id = new ImageDownloader();
                    id.execute(idp);
                    image_downloaders.add(id);
                }
            }
            LOGD("Finished OnCreate");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        LOGD("Save Instance State");
        if(continent_name != null) {
            outState.putString("continent_name", continent_name);
            outState.putInt("number_of_images", dl_parameters.size());
            for (int i = 0; i < dl_parameters.size(); ++i) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                dl_parameters.get(i).btmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                outState.putByteArray(dl_parameters.get(i).imageUrl, baos.toByteArray());
                LOGD("Saved"+dl_parameters.get(i).imageUrl);
            }
        }
    }
}
