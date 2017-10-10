package nl.stefhost.ruzzel;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Spel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = this.getSharedPreferences("opties", 0);
        String spel_3 = sharedPreferences.getString("spel_3", "");
        if (spel_3.equals("verticaal")){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_spel);
        Spel_Canvas.game_loop = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Spel_Canvas.game_loop = false;
    }

    public void animatie_aan(View view){
        Spel_Canvas.animatie_aan();
        view.setVisibility(View.GONE);
    }

    public void animatie_uit(View view){
        Spel_Canvas.animatie_uit();
    }

}
