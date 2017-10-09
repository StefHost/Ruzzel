package nl.stefhost.ruzzel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.StringTokenizer;

public class Avontuur extends AppCompatActivity {

    int level;
    int keuze_wereld;

    public Button button;

    public Button buttons[] = new Button[10];

    public String wereld_levels = "wereld";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avontuur);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        button = (Button) findViewById(R.id.button);

        buttons[0] = (Button) findViewById(R.id.button1);
        buttons[1] = (Button) findViewById(R.id.button2);
        buttons[2] = (Button) findViewById(R.id.button3);
        buttons[3] = (Button) findViewById(R.id.button4);
        buttons[4] = (Button) findViewById(R.id.button5);
        buttons[5] = (Button) findViewById(R.id.button6);
        buttons[6] = (Button) findViewById(R.id.button7);
        buttons[7] = (Button) findViewById(R.id.button8);
        buttons[8] = (Button) findViewById(R.id.button9);
        buttons[9] = (Button) findViewById(R.id.button10);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/telegrama.otf");
        button.setTypeface(typeface);
        buttons[0].setTypeface(typeface);
        buttons[1].setTypeface(typeface);
        buttons[2].setTypeface(typeface);
        buttons[3].setTypeface(typeface);
        buttons[4].setTypeface(typeface);
        buttons[5].setTypeface(typeface);
        buttons[6].setTypeface(typeface);
        buttons[7].setTypeface(typeface);
        buttons[8].setTypeface(typeface);
        buttons[9].setTypeface(typeface);

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = this.getSharedPreferences("opties", 0);
        level = sharedPreferences.getInt("level", 0);
        if (level == 0){
            level = 1;
        }
        Log.d("SHG", level+"");

        if (wereld_levels.equals("wereld")) {
            werelden();
        }else{
            levels();
        }

        // tijdelijk direct levels laten zien
        keuze_wereld = 1;
        levels();
    }

    public void werelden(){

        button.setText(getResources().getString(R.string.avontuur_1));

        int wereld = (int) Math.floor(level / 10);
        int tellen = 9;

        while (tellen > wereld){
            int tellen2 = tellen +1;
            buttons[tellen].setText(String.valueOf(tellen2));
            buttons[tellen].setEnabled(false);
            tellen--;
        }
        while (tellen > -1){
            int tellen2 = tellen +1;
            buttons[tellen].setText(String.valueOf(tellen2));
            buttons[tellen].setEnabled(true);
            tellen--;
        }
    }

    public void levels(){
        button.setText(getResources().getString(R.string.avontuur_2));

        int tellen1 = 0;
        int tellen2 = (keuze_wereld - 1)*10 +1;
        int tellen3 = tellen2 + 10;

        while (tellen2 < tellen3){
            buttons[tellen1].setText(String.valueOf(tellen2));

            if (level < tellen2){
                buttons[tellen1].setEnabled(false);
            }else{
                buttons[tellen1].setEnabled(true);
            }
            tellen1++;
            tellen2++;
        }
        wereld_levels = "levels";
    }

    public void button(View view){
        String tag = view.getTag().toString();

        if (wereld_levels.equals("wereld")) {
            keuze_wereld = Integer.parseInt(tag);
            levels();
        }else if (tag.equals("debug")){

            String level = "10|10|verticaal|73|89|0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,1,4,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,0,1,6,1,9,1,1,1,1,1,0,1,1,1,1,1,10,1,1,0,0,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,5,0,0,0,0,0,0,0,0,0,0,0|0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";

            StringTokenizer stringTokenizer = new StringTokenizer(level, "|");
            String spel_1 = stringTokenizer.nextToken();
            String spel_2 = stringTokenizer.nextToken();
            String spel_3 = stringTokenizer.nextToken();
            String spel_4 = stringTokenizer.nextToken();
            String spel_5 = stringTokenizer.nextToken();
            String spel_6 = stringTokenizer.nextToken();
            String spel_7 = stringTokenizer.nextToken();

            SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            //editor.putInt("gekozen_level", gekozen_level);
            editor.putString("spel_1", spel_1);
            editor.putString("spel_2", spel_2);
            editor.putString("spel_3", spel_3);
            editor.putString("spel_4", spel_4);
            editor.putString("spel_5", spel_5);
            editor.putString("spel_6", spel_6);
            editor.putString("spel_7", spel_7);
            editor.apply();

            Intent intent = new Intent(this, Spel.class);
            startActivity(intent);

        }else{
            // Level starten
            int gekozen_level = (keuze_wereld - 1) * 10 +Integer.parseInt(tag);

            Log.d("SHG", gekozen_level+" gekozen_level");

            if (gekozen_level == 6){
                AlertDialog.Builder builder = new AlertDialog.Builder(Avontuur.this);
                builder.setTitle("Niet beschikbaar")
                        .setMessage("Sorry.. dit level is nog niet beschikbaar..")
                        .setPositiveButton("OK", null);
                builder.show();
            }else {

                int resourceId = this.getResources().getIdentifier("level_" + gekozen_level, "string", this.getPackageName());
                String level = getString(resourceId);

                StringTokenizer stringTokenizer = new StringTokenizer(level, "|");
                String spel_1 = stringTokenizer.nextToken();
                String spel_2 = stringTokenizer.nextToken();
                String spel_3 = stringTokenizer.nextToken();
                String spel_4 = stringTokenizer.nextToken();
                String spel_5 = stringTokenizer.nextToken();
                String spel_6 = stringTokenizer.nextToken();
                String spel_7 = stringTokenizer.nextToken();

                SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("gekozen_level", gekozen_level);
                editor.putString("spel_1", spel_1);
                editor.putString("spel_2", spel_2);
                editor.putString("spel_3", spel_3);
                editor.putString("spel_4", spel_4);
                editor.putString("spel_5", spel_5);
                editor.putString("spel_6", spel_6);
                editor.putString("spel_7", spel_7);
                editor.apply();

                Intent intent = new Intent(this, Spel.class);
                startActivity(intent);
            }

        }

    }

    @Override
    public void onBackPressed() {

        if (wereld_levels.equals("levels")){
            wereld_levels = "wereld";
            werelden();
        }else{
            super.onBackPressed();
        }
    }
}
