package nl.stefhost.ruzzel;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Beginscherm extends AppCompatActivity {

    ImageView imageView1;
    ImageView imageView2;
    MediaPlayer mediaPlayer1;
    static MediaPlayer mediaPlayer2;
    static MediaPlayer mediaPlayer3;
    static MediaPlayer mediaPlayer4;
    Handler handler;
    public static Context context;

    boolean muziek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginscherm);

        context = this;

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/telegrama.otf");
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        textView1.setTypeface(typeface);
        button1.setTypeface(typeface);
        button2.setTypeface(typeface);

        mediaPlayer1 = MediaPlayer.create(Beginscherm.this, R.raw.muziek);
        mediaPlayer2 = MediaPlayer.create(Beginscherm.this, R.raw.deur_open);
        mediaPlayer3 = MediaPlayer.create(Beginscherm.this, R.raw.gewonnen);
        mediaPlayer4 = MediaPlayer.create(Beginscherm.this, R.raw.lopen);

        SharedPreferences sharedPreferences = this.getSharedPreferences("opties", 0);
        String aan_uit = sharedPreferences.getString("muziek", "aan");
        if (aan_uit.equals("aan")) {
            mediaPlayer1.setLooping(true);
            mediaPlayer1.start();
            muziek = true;
        }else{
            imageView1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.volume_uit));
            muziek = false;
        }

        handler = new Handler();
        handler.postDelayed(animatie, 500);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.beginscherm, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences sharedPreferences = this.getSharedPreferences("opties", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (item.getItemId()) {
            case R.id.reset:
                editor.putInt("level", 1);
                editor.apply();
                return true;
            case R.id.plaatjes:
                String plaatjes = sharedPreferences.getString("plaatjes", "app");
                if (plaatjes.equals("app")){
                    editor.putString("plaatjes", "sd");
                    editor.apply();
                }else{
                    editor.putString("plaatjes", "app");
                    editor.apply();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer1.stop();
    }

    int animatie_status = 0;

    private Runnable animatie = new Runnable() {
        @Override
        public void run() {

            if (animatie_status == 0){
                animatie_status = 1;
                imageView2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.speler_2_groot));
            }else{
                animatie_status = 0;
                imageView2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.speler_1_groot));
            }

            handler.postDelayed(animatie, 500);
        }
    };

    public void avontuur(View view){
        Intent intent = new Intent(this, Avontuur.class);
        startActivity(intent);
    }

    public void workshop(View view){
        Intent intent = new Intent(this, Workshop.class);
        startActivity(intent);
    }

    public void geluid_aan_uit(View view){
        String aan_uit;
        if (muziek){
            mediaPlayer1.pause();
            imageView1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.volume_uit));
            aan_uit = "uit";
            muziek = false;
        }else{
            mediaPlayer1.start();
            imageView1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.volume_aan));
            aan_uit = "aan";
            muziek = true;
        }

        SharedPreferences sharedPreferences = this.getSharedPreferences("opties", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("muziek", aan_uit);
        editor.apply();
    }

    static public void deur_effect(){
        mediaPlayer2.start();
    }

    static public void gewonnen_effect(){
        mediaPlayer3.start();
    }

    static public void block_effect(){
        mediaPlayer4.start();
    }

    static public void trillen(int tijd){
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(tijd);
    }

}
