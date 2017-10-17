package us.aaron_johnson.adventurechooser;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public Button continentButton;
    public Button socialButton;
    public Button displaySettingsButton;
    public Button showMapButton;

    public AlertDialog.Builder selectDialogBuilder;
    public AlertDialog selectContinentDialog;

    public static void LOGV(String toLog){
        Log.v("AC_MAIN_ACTIVITY", toLog);
    }
    public static void LOGD(String toLog){
        Log.d("AC_MAIN_ACTIVITY", toLog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectDialogBuilder = new AlertDialog.Builder(MainActivity.this);
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

        continentButton = (Button)findViewById(R.id.continent_button);
        socialButton = (Button)findViewById(R.id.social_button);
        displaySettingsButton = (Button)findViewById(R.id.settings_button);
        showMapButton = (Button)findViewById(R.id.map_button);

        displaySettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LOGV("Starting Display Settings Activity");
                startActivity(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
            }
        });

        socialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent si = new Intent(Intent.ACTION_SEND);
                si.putExtra(Intent.EXTRA_TEXT, "I love geography");
                si.setType("text/plain");
                ComponentName sn = si.resolveActivity(getPackageManager());
                if(null != sn){
                    Intent ni = Intent.createChooser(si, "Share Using");
                    if(null != ni){
                        LOGV("Start sharing activity chooser.");
                        startActivity(ni);
                    }else{
                        LOGD("Failed to choose sharing activity");
                    }
                }else{
                    LOGD("Failed to find a valid sharing program.");
                }
            }
        });

        continentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LOGV("Show select dialog");
                selectContinentDialog.show();
            }
        });

        showMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mi = new Intent(Intent.ACTION_VIEW);
                Uri location = Uri.parse("geo:39.7119994,-75.1221752?z=18");
                mi.setData(location);
                ComponentName cn = mi.resolveActivity(getPackageManager());
                if(null != cn){
                    startActivity(mi);
                }else{
                    LOGD("Failed to resolve location activity");
                }
            }
        });
    }
}
