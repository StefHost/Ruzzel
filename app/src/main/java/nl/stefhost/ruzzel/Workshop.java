package nl.stefhost.ruzzel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;

import nl.stefhost.ruzzel.functies.laad_plaatje;

public class Workshop extends AppCompatActivity {

    public ProgressDialog ProgressDialog;
    public String resultaat;
    public String code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/telegrama.otf");
        Button button1 = (Button) findViewById(R.id.button1);
        //Button button2 = (Button) findViewById(R.id.button2);
        EditText editText1 = (EditText) findViewById(R.id.editText1);
        //EditText editText2 = (EditText) findViewById(R.id.editText2);

        button1.setTypeface(typeface);
        //button2.setTypeface(typeface);
        editText1.setTypeface(typeface);
        //editText2.setTypeface(typeface);
    }

    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = this.getSharedPreferences("opties", 0);
        String qrcode = sharedPreferences.getString("qrcode", "");

        if (!qrcode.equals("")){
            code = qrcode;
            ProgressDialog = android.app.ProgressDialog.show(this, "Spel zoeken", "Even geduld aub..", true, false);
            new spel_zoeken().execute();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("qrcode", "");
            editor.apply();
        }

    }

    public void button1(View view){
        EditText editText = (EditText) findViewById(R.id.editText1);
        code = editText.getText().toString();

        if (code.equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(Workshop.this);
            builder.setTitle("Geen Code")
                    .setMessage("Probeer het nog een keer!");
            builder.show();
        }else{
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            ProgressDialog = android.app.ProgressDialog.show(this, "Spel zoeken", "Even geduld aub..", true, false);
            new spel_zoeken().execute();
        }
    }

    /*public void button2(View view){

        EditText editText = (EditText) findViewById(R.id.editText2);
        String tekst = editText.getText().toString();

        StringTokenizer stringTokenizer = new StringTokenizer(tekst, "|");
        String spel_1 = stringTokenizer.nextToken();
        String spel_2 = stringTokenizer.nextToken();
        String spel_3 = stringTokenizer.nextToken();
        String spel_4 = stringTokenizer.nextToken();
        String spel_5 = stringTokenizer.nextToken();
        String spel_6 = stringTokenizer.nextToken();
        String spel_7 = stringTokenizer.nextToken();

        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
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
    }*/

    public void button3(View view){
        ProgressDialog = android.app.ProgressDialog.show(this, "Spel laden", "Even geduld aub..", true, false);
        new spel_laden_code().execute();
    }

    private class spel_zoeken extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL("http://www.stefhost.nl/ruzzel/paginas/app/spel_zoeken.php?code="+code);
            } catch (MalformedURLException e) {
                System.out.println("MalformedURLException");
            }

            if (url != null){
                try{
                    urlConnection = url.openConnection();
                }catch (java.io.IOException e){
                    System.out.println("java.io.IOException");
                }
            }

            if (urlConnection != null){
                try{
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }catch (java.io.IOException e) {
                    System.out.println("java.io.IOException");
                }
            }

            if (inputStream != null){
                resultaat = inputStream.toString();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                try{
                    resultaat = bufferedReader.readLine();
                }catch (java.io.IOException e) {
                    System.out.println("java.io.IOException");
                }

            }else{
                resultaat = "ERROR";
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            ProgressDialog.dismiss();
            spel_zoeken_klaar();
        }

    }

    private void spel_zoeken_klaar() {

        if (!resultaat.equals("ERROR")){

            if (resultaat.equals("CODE")){
                AlertDialog.Builder builder = new AlertDialog.Builder(Workshop.this);
                builder.setTitle("Verkeerde Code")
                        .setMessage("Er is geen level met deze code.\nProbeer het nog een keer!");
                builder.show();
            }else{
                StringTokenizer stringTokenizer = new StringTokenizer(resultaat, "|");
                String naam = stringTokenizer.nextToken();
                String maker = stringTokenizer.nextToken();

                RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText("Code: "+code+"\nNaam: "+naam+"\nMaker: "+maker);
                new laad_plaatje((ImageView) findViewById(R.id.imageView)).execute("http://ruzzel.stefhost.nl/spellen/"+code+".png");
                relativeLayout.setVisibility(View.VISIBLE);
            }
        }

    }

    private class spel_laden_code extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL("http://www.stefhost.nl/ruzzel/paginas/app/spel_laden.php?code="+code);
            } catch (MalformedURLException e) {
                System.out.println("MalformedURLException");
            }

            if (url != null){
                try{
                    urlConnection = url.openConnection();
                }catch (java.io.IOException e){
                    System.out.println("java.io.IOException");
                }
            }

            if (urlConnection != null){
                try{
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }catch (java.io.IOException e) {
                    System.out.println("java.io.IOException");
                }
            }

            if (inputStream != null){
                resultaat = inputStream.toString();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                try{
                    resultaat = bufferedReader.readLine();
                }catch (java.io.IOException e) {
                    System.out.println("java.io.IOException");
                }

            }else{
                resultaat = "ERROR";
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            ProgressDialog.dismiss();
            spel_laden_code_klaar();
        }

    }

    private void spel_laden_code_klaar() {

        if (!resultaat.equals("ERROR")){

            if (resultaat.equals("CODE")){
                AlertDialog.Builder builder = new AlertDialog.Builder(Workshop.this);
                builder.setTitle("Verkeerde Code")
                        .setMessage("Probeer het nog een keer!")
                        .setPositiveButton("OK", null);
                builder.show();
            }else{
                StringTokenizer stringTokenizer = new StringTokenizer(resultaat, "|");
                String spel_1 = stringTokenizer.nextToken();
                String spel_2 = stringTokenizer.nextToken();
                String spel_3 = stringTokenizer.nextToken();
                String spel_4 = stringTokenizer.nextToken();
                String spel_5 = stringTokenizer.nextToken();
                String spel_6 = stringTokenizer.nextToken();
                String spel_7 = stringTokenizer.nextToken();

                SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
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
        if (code.equals("")){
            super.onBackPressed();
        }else{
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
            relativeLayout.setVisibility(View.GONE);
            code = "";
        }
    }

    public void camera(View view){
        Intent intent = new Intent(this, QR_scanner.class);
        startActivity(intent);
    }
}
