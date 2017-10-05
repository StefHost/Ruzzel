package nl.stefhost.ruzzel;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Spel_Canvas extends android.support.v7.widget.AppCompatImageView {

    public Paint paint1 = new Paint();
    public Paint paint2 = new Paint();

    public Bitmap[] bitmap_animatie_1 = new Bitmap[11];
    public Bitmap[] bitmap_animatie_2 = new Bitmap[11];
    public Bitmap[] bitmap_animatie_3 = new Bitmap[11];
    public Bitmap[] bitmap_animatie_4 = new Bitmap[11];

    int winscore = 0;
    int score = 0;
    int score_geluid = 0;

    int fps = 0;
    int aantal_fps = 0;

    int tellen_x = 0;
    int tellen_y = 0;
    int tellen = 0;

    int breedte_plaatje;
    int marge_x = 0;
    int marge_y = 0;

    int breedte_spel;
    int hoogte_spel;
    int positie_speler;
    int positie_deur;

    List<Integer> achtergrond;
    List<Integer> speelveld;

    public Handler handler1;
    public Handler handler2;
    public Handler handler3;

    boolean deur_open = false;
    boolean game_loop = true;

    String animatie_status = "1";

    // Test vanaf PC !

    public Spel_Canvas(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        Log.d("SHG", "test");

        paint1.setTextSize(40);
        paint1.setColor(Color.GRAY);

        paint2.setTextSize(200);
        paint2.setColor(Color.BLACK);

        SharedPreferences sharedPreferences = this.getContext().getSharedPreferences("opties", 0);
        String spel_1 = sharedPreferences.getString("spel_1", "");
        String spel_2 = sharedPreferences.getString("spel_2", "");
        String spel_3 = sharedPreferences.getString("spel_3", "");
        String spel_4 = sharedPreferences.getString("spel_4", "");
        String spel_5 = sharedPreferences.getString("spel_5", "");
        String spel_6 = sharedPreferences.getString("spel_6", "");
        String spel_7 = sharedPreferences.getString("spel_7", "");
        String plaatjes = sharedPreferences.getString("plaatjes", "app");

        if (plaatjes.equals("app")) {

            bitmap_animatie_1[0] = BitmapFactory.decodeResource(getResources(), R.mipmap.muur);
            bitmap_animatie_1[1] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer);
            bitmap_animatie_1[2] = BitmapFactory.decodeResource(getResources(), R.mipmap.blok);
            bitmap_animatie_1[3] = BitmapFactory.decodeResource(getResources(), R.mipmap.speler_1);
            bitmap_animatie_1[4] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer_blok);
            bitmap_animatie_1[5] = BitmapFactory.decodeResource(getResources(), R.mipmap.deur_dicht);
            bitmap_animatie_1[6] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer_kapot_1);
            bitmap_animatie_1[7] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer_kapot_2);
            bitmap_animatie_1[8] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer_kapot_3);
            bitmap_animatie_1[9] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer_weg);
            bitmap_animatie_1[10] = BitmapFactory.decodeResource(getResources(), R.mipmap.val_1);

            bitmap_animatie_2[0] = BitmapFactory.decodeResource(getResources(), R.mipmap.muur);
            bitmap_animatie_2[1] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer);
            bitmap_animatie_2[2] = BitmapFactory.decodeResource(getResources(), R.mipmap.blok);
            bitmap_animatie_2[3] = BitmapFactory.decodeResource(getResources(), R.mipmap.speler_2);
            bitmap_animatie_2[4] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer_blok);
            bitmap_animatie_2[5] = BitmapFactory.decodeResource(getResources(), R.mipmap.deur_dicht);
            bitmap_animatie_2[6] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer_kapot_1);
            bitmap_animatie_2[7] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer_kapot_2);
            bitmap_animatie_2[8] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer_kapot_3);
            bitmap_animatie_2[9] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer_weg);
            bitmap_animatie_2[10] = BitmapFactory.decodeResource(getResources(), R.mipmap.val_2);

            bitmap_animatie_3[0] = BitmapFactory.decodeResource(getResources(), R.mipmap.muur);
            bitmap_animatie_3[1] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer);
            bitmap_animatie_3[2] = BitmapFactory.decodeResource(getResources(), R.mipmap.blok);
            bitmap_animatie_3[3] = BitmapFactory.decodeResource(getResources(), R.mipmap.animatie_1);
            bitmap_animatie_3[4] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer_blok);
            bitmap_animatie_3[5] = BitmapFactory.decodeResource(getResources(), R.mipmap.deur_dicht);
            bitmap_animatie_3[6] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer_kapot_1);
            bitmap_animatie_3[7] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer_kapot_2);
            bitmap_animatie_3[8] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer_kapot_3);
            bitmap_animatie_3[9] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer_weg);
            bitmap_animatie_3[10] = BitmapFactory.decodeResource(getResources(), R.mipmap.val_2);

            bitmap_animatie_4[0] = BitmapFactory.decodeResource(getResources(), R.mipmap.muur);
            bitmap_animatie_4[1] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer);
            bitmap_animatie_4[2] = BitmapFactory.decodeResource(getResources(), R.mipmap.blok);
            bitmap_animatie_4[3] = BitmapFactory.decodeResource(getResources(), R.mipmap.animatie_2);
            bitmap_animatie_4[4] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer_blok);
            bitmap_animatie_4[5] = BitmapFactory.decodeResource(getResources(), R.mipmap.deur_dicht);
            bitmap_animatie_4[6] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer_kapot_1);
            bitmap_animatie_4[7] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer_kapot_2);
            bitmap_animatie_4[8] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer_kapot_3);
            bitmap_animatie_4[9] = BitmapFactory.decodeResource(getResources(), R.mipmap.vloer_weg);
            bitmap_animatie_4[10] = BitmapFactory.decodeResource(getResources(), R.mipmap.val_2);

        }
        /*else{
            bitmap_animatie_1[0] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/muur.png");
            bitmap_animatie_1[1] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/vloer.png");
            bitmap_animatie_1[2] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/blok.png");
            bitmap_animatie_1[3] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/speler_1.png");
            bitmap_animatie_1[4] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/vloer_blok.png");
            bitmap_animatie_1[5] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/deur_dicht.png");
            bitmap_animatie_1[6] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/vloer_kapot_1.png");
            bitmap_animatie_1[7] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/vloer_kapot_2.png");
            bitmap_animatie_1[8] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/vloer_kapot_3.png");
            bitmap_animatie_1[9] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/vloer_weg.png");
            bitmap_animatie_1[10] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/val_1.png");

            bitmap_animatie_2[0] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/muur.png");
            bitmap_animatie_2[1] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/vloer.png");
            bitmap_animatie_2[2] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/blok.png");
            bitmap_animatie_2[3] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/speler_2.png");
            bitmap_animatie_2[4] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/vloer_blok.png");
            bitmap_animatie_2[5] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/deur_dicht.png");
            bitmap_animatie_2[6] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/vloer_kapot_1.png");
            bitmap_animatie_2[7] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/vloer_kapot_2.png");
            bitmap_animatie_2[8] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/vloer_kapot_3.png");
            bitmap_animatie_2[9] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/vloer_weg.png");
            bitmap_animatie_2[10] = BitmapFactory.decodeFile("/storage/emulated/0/Pixelmaker/val_2.png");
        }*/

        breedte_plaatje = bitmap_animatie_1[0].getWidth();

        hoogte_spel = Integer.parseInt(spel_1);
        breedte_spel = Integer.parseInt(spel_2);
        positie_speler = Integer.parseInt(spel_4);
        positie_deur = Integer.parseInt(spel_5);
        StringTokenizer stringTokenizer1 = new StringTokenizer(spel_6, ",");
        StringTokenizer stringTokenizer2 = new StringTokenizer(spel_7, ",");

        achtergrond = new ArrayList<>();
        while (stringTokenizer1.hasMoreTokens()){
            String string = stringTokenizer1.nextToken();
            if (string.equals("4")){
                winscore++;
            }
            achtergrond.add(Integer.parseInt(string));
        }

        speelveld = new ArrayList<>();
        while (stringTokenizer2.hasMoreTokens()){
            String string = stringTokenizer2.nextToken();
            speelveld.add(Integer.parseInt(string));
        }

        Log.d("SHG", "aantal:"+score);

        // code leeggooien
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("code", "");
        editor.apply();

        if (spel_3.equals("verticaal")){
            if (hoogte_spel != 20){
                marge_x = 60;
                marge_y = 200;
            }
        }

        handler1 = new Handler();
        handler2 = new Handler();
        handler3 = new Handler();

        handler1.postDelayed(fps_tellen, 1000);
        handler2.postDelayed(animatie_switch, 500);
    }

    private Runnable fps_tellen = new Runnable() {
        @Override
        public void run() {

            fps = aantal_fps;
            aantal_fps = 0;

            handler1.postDelayed(fps_tellen, 1000);
        }
    };

    private Runnable animatie_switch = new Runnable() {
        @Override
        public void run() {

            if (animatie_status.equals("1")) {
                animatie_status = "2";
            }else{
                animatie_status = "1";
            }
            if (game_loop) {
                handler2.postDelayed(animatie_switch, 500);
            }
        }
    };

    private Runnable stoppen = new Runnable() {
        @Override
        public void run() {

            ((Spel)getContext()).finish();
        }
    };

    public void onDraw(Canvas canvas){

        //achtergrond tekenen
        while (tellen_y < hoogte_spel) {

            while (tellen_x < breedte_spel) {
                if (animatie_status.equals("1")) {
                    canvas.drawBitmap(bitmap_animatie_1[achtergrond.get(tellen)], (tellen_x * breedte_plaatje) + marge_x, (tellen_y * breedte_plaatje) + marge_y, paint1);
                }else{
                    canvas.drawBitmap(bitmap_animatie_2[achtergrond.get(tellen)], (tellen_x * breedte_plaatje) + marge_x, (tellen_y * breedte_plaatje) + marge_y, paint1);
                }
                tellen++;
                tellen_x++;
            }

            tellen_x = 0;
            tellen_y++;
        }

        tellen_y = 0;
        tellen = 0;

        //speelveld tekenen
        while (tellen_y < hoogte_spel) {

            while (tellen_x < breedte_spel) {
                if (speelveld.get(tellen) != 0){
                    if (animatie_status.equals("1")) {
                        canvas.drawBitmap(bitmap_animatie_3[speelveld.get(tellen)], (tellen_x * breedte_plaatje) + marge_x, (tellen_y * breedte_plaatje) + marge_y, paint1);
                    }else{
                        canvas.drawBitmap(bitmap_animatie_4[speelveld.get(tellen)], (tellen_x * breedte_plaatje) + marge_x, (tellen_y * breedte_plaatje) + marge_y, paint1);
                    }
                }
                tellen++;
                tellen_x++;
            }

            tellen_x = 0;
            tellen_y++;
        }

        tellen_y = 0;
        tellen = 0;

        //FPS tekenen
        //canvas.drawText("FPS: "+fps, 0, 40, paint1);

        //Score tekenen
        //canvas.drawText("Score: "+score+"/"+winscore, 0, 80, paint1);

        //Rand tekenen
        paint2.setStrokeWidth(5);
        canvas.drawLine(marge_x, marge_y, marge_x, marge_y+(breedte_plaatje*hoogte_spel), paint2);
        canvas.drawLine(marge_x+(breedte_plaatje*breedte_spel), marge_y, marge_x+(breedte_plaatje*breedte_spel), marge_y+(breedte_plaatje*hoogte_spel), paint2);
        canvas.drawLine(marge_x, marge_y, marge_x+(breedte_plaatje*breedte_spel), marge_y, paint2);
        canvas.drawLine(marge_x, marge_y+(breedte_plaatje*hoogte_spel), marge_x+(breedte_plaatje*breedte_spel), marge_y+(breedte_plaatje*hoogte_spel), paint2);

        //Zoeken naar val
        if (achtergrond.get(positie_speler-1) == 10 && animatie_status.equals("2")){
            game_loop = false;
            Beginscherm.gewonnen_effect();
            Beginscherm.trillen(1000);
            handler2.postDelayed(stoppen, 1000);
        }

        //Zoeken naar gat in grond
        if (achtergrond.get(positie_speler-1) == 9){
            game_loop = false;
            Beginscherm.gewonnen_effect();
            Beginscherm.trillen(1000);
            handler2.postDelayed(stoppen, 1000);
        }

        aantal_fps++;
        if (game_loop) {
            invalidate();
        }

    }

    double x_down;
    double y_down;
    double x_up;
    double y_up;

    @Override
    public boolean onTouchEvent (MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN){
            int pointerId = event.getPointerId(0);
            int pointerIndex = event.findPointerIndex(pointerId);
            x_down = event.getX(pointerIndex);
            y_down = event.getY(pointerIndex);

            //Log.d("Ruzzel", "x_down: "+x_down+" | y_down: "+y_down);
        }

        if (event.getAction() == MotionEvent.ACTION_UP){
            int pointerId = event.getPointerId(0);
            int pointerIndex = event.findPointerIndex(pointerId);
            x_up = event.getX(pointerIndex);
            y_up = event.getY(pointerIndex);

            //Log.d("Ruzzel", "x_up: "+x_up+" | y_up: "+y_up);

            if ( ((y_up - y_down > x_up - x_down) && (y_up - y_down > x_down - x_up)) || ((y_down - y_up > x_up - x_down) && (y_down - y_up > x_down - x_up)) ){
                //Log.d("Ruzzel", "swipe verticaal");
                if (y_up > y_down){
                    beweeg("omlaag");
                }else if (y_down > y_up){
                    beweeg("omhoog");
                }
            }else{
                //Log.d("Ruzzel", "swipe horizontaal");
                if (x_up > x_down){
                    beweeg("rechts");
                }else if (x_down > x_up){
                    beweeg("links");
                }
            }

        }

        return true;
    }

    int nieuwe_positie;
    int nieuwe_positie_blok;

    public void beweeg (String keuze){

        int breedte;

        switch (keuze){
            case "rechts":
                nieuwe_positie = positie_speler +1;
                nieuwe_positie_blok = nieuwe_positie;
                break;
            case "links":
                nieuwe_positie = positie_speler -1;
                nieuwe_positie_blok = nieuwe_positie -2;
                break;
            case "omhoog":
                breedte = breedte_spel + 1;
                nieuwe_positie = positie_speler - breedte_spel;
                nieuwe_positie_blok = nieuwe_positie - breedte;
                break;
            case "omlaag":
                breedte = breedte_spel - 1;
                nieuwe_positie = positie_speler + breedte_spel;
                nieuwe_positie_blok = nieuwe_positie + breedte;
                break;

        }
        Log.d("SHG", ""+speelveld.get(nieuwe_positie-1));

        // Gewonnen
        if (achtergrond.get(nieuwe_positie-1) == 5){

            if (deur_open){
                Log.d("SHG", "gewonnen");
                speelveld.set(positie_speler-1, 0);
                speelveld.set(nieuwe_positie-1, 3);
                positie_speler = nieuwe_positie;

                SharedPreferences sharedPreferences = this.getContext().getSharedPreferences("opties", 0);

                int level = sharedPreferences.getInt("level", 1);
                int gekozen_level = sharedPreferences.getInt("gekozen_level", 0);
                if (gekozen_level == level) {
                    gekozen_level++;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("level", gekozen_level);
                    editor.apply();
                }
                game_loop = false;
                Beginscherm.gewonnen_effect();
                handler2.postDelayed(stoppen, 1000);
            }

            // Grond kapot
        }else if (achtergrond.get(nieuwe_positie-1) == 6 || achtergrond.get(nieuwe_positie-1) == 7 || achtergrond.get(nieuwe_positie-1) == 8) {

            if (speelveld.get(nieuwe_positie-1) == 2){
                speelveld.set(nieuwe_positie_blok, 2);
            }

            speelveld.set(positie_speler-1, 0);
            speelveld.set(nieuwe_positie-1, 3);
            achtergrond.set(nieuwe_positie-1, achtergrond.get(nieuwe_positie-1)+1);
            positie_speler = nieuwe_positie;

            // Geen grond
        //}else if (achtergrond.get(nieuwe_positie-1) == 9) {

            /*speelveld.set(positie_speler-1, 0);
            speelveld.set(nieuwe_positie-1, 3);

            game_loop = false;
            Beginscherm.gewonnen_effect();
            handler2.postDelayed(stoppen, 1000);*/

        }else if (achtergrond.get(nieuwe_positie-1) == 0) {
            // geen muur
            Beginscherm.trillen(100);

        }else{
            // geen muur

            // blok obstakel
            if (speelveld.get(nieuwe_positie-1) == 2) {
                if (speelveld.get(nieuwe_positie_blok) == 0 && achtergrond.get(nieuwe_positie_blok) != 0) {

                    if (achtergrond.get(nieuwe_positie_blok) == 9 && achtergrond.get(nieuwe_positie_blok) != 0) {
                        //speelveld.set(nieuwe_positie_blok, 2);
                        speelveld.set(nieuwe_positie - 1, 3);
                        speelveld.set(positie_speler - 1, 0);
                        positie_speler = nieuwe_positie;
                    }else{
                        speelveld.set(nieuwe_positie_blok, 2);
                        speelveld.set(nieuwe_positie - 1, 3);
                        speelveld.set(positie_speler - 1, 0);
                        positie_speler = nieuwe_positie;
                    }

                }

            }else{
                // geen obstakels
                speelveld.set(nieuwe_positie - 1, 3);
                speelveld.set(positie_speler - 1, 0);
                positie_speler = nieuwe_positie;
            }

        }

        //score bekijken
        score = 0;
        for (int i = 0; i < achtergrond.size(); i++) {
            int vakje_achtergrond = achtergrond.get(i);
            int vakje_speelveld = speelveld.get(i);
            if (vakje_achtergrond == 4 && vakje_speelveld == 2){
                score++;
                if (score_geluid < score) {
                    Beginscherm.block_effect();
                    score_geluid++;
                }
            }
            // Deur openen
            if (score == winscore && !deur_open){
                deur_open = true;
                bitmap_animatie_1[5] = BitmapFactory.decodeResource(getResources(), R.mipmap.deur_open);
                bitmap_animatie_2[5] = BitmapFactory.decodeResource(getResources(), R.mipmap.deur_open);
                Beginscherm.deur_effect();
            }
        }

    }

}
