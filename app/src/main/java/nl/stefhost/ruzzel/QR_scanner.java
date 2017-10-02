package nl.stefhost.ruzzel;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class QR_scanner extends AppCompatActivity {

    SurfaceView surfaceView;
    //TextView textView;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        surfaceView = (SurfaceView)findViewById(R.id.camera_view);
        //textView = (TextView)findViewById(R.id.code_info);

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setAutoFocusEnabled(true).setRequestedPreviewSize(300, 300).build();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }else{
            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try{
                        if (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            cameraSource.start(surfaceView.getHolder());
                        }
                    }catch (IOException ie) {
                        Log.e("CAMERA SOURCE", ie.getMessage());
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });

        }

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    /*textView.post(new Runnable() {    // Use the post method of the TextView
                        public void run() {
                            textView.setText(    // Update the TextView
                                    barcodes.valueAt(0).displayValue
                            );
                        }
                    });*/

                    String resultaat = barcodes.valueAt(0).displayValue;
                    Log.d("Ruzzel", "barcode: "+resultaat);
                    code_opslaan(resultaat);
                }

            }
        });
    }

    public void code_opslaan(String code){
        SharedPreferences sharedPreferences = getSharedPreferences("opties", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("qrcode", code);
        editor.apply();

        finish();
    }

}
